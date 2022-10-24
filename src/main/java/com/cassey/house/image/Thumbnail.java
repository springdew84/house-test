package com.cassey.house.image;

import com.cassey.house.image.utils.Consts;
import com.cassey.house.image.utils.CornerMark;
import com.cassey.house.image.utils.ImageUtil;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Map;

public class Thumbnail {

    public static void resizeWithoutCut(String sourcePath, String targetPath) {
        File tagetPaths = new File(targetPath);
        if (!tagetPaths.exists()) {
            tagetPaths.mkdir();
        }
        resizeWithoutCut(sourcePath, targetPath, null);
    }

    public static void resizeWithoutCut(String sourcePath, String targetPath, CornerMark cornerMark) {
        Map<String, BufferedImage> bufferedImageMap = ImageUtil.readImages(sourcePath);

        bufferedImageMap.forEach((fileName, bufferedImage) -> {
            Thumbnails.Builder<BufferedImage> builder = Thumbnails.of(bufferedImage);
            if (ImageUtil.isPng()) {
                builder.imageType(BufferedImage.TYPE_INT_ARGB).outputFormat(Consts.IMGE_TYPE_PNG);
            }
            int with = (int)(bufferedImage.getWidth() * 0.96f);
            int height = (int)(bufferedImage.getHeight() * 0.96f);
            builder.size(with, height).outputQuality(Consts.QUALITY_HIGH);

            if (cornerMark != null) {
                builder.watermark(cornerMark.getPosition(), ImageUtil.readImage(cornerMark.getFileName()), Consts.WATER_MARK_OPACITY);
            }

            try {
                builder.toFile(targetPath + "/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void resizeFitGif(String sourcePath, String targetPathName) {
        byte[] sourceByte = readByByteArrayOutputStream(new File(sourcePath));
        ByteArrayInputStream stream = new ByteArrayInputStream(sourceByte);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GifDecoder decoder = new GifDecoder();
        decoder.read(stream);
        int cnt = decoder.getFrameCount();

        if (cnt == 1) {
            //只有一帧 无需要处理
            return;
        }
        int width = decoder.getImage().getWidth();
        int height = decoder.getImage().getHeight();
        //float scale = getScare(width, height);
        float scale = 0.95F;
        width = (int) (width * scale);
        height = (int) (height * scale);

        AnimatedGifEncoder e = new AnimatedGifEncoder();
        // 设置生成图片大小
        e.setSize(width, height);
        //保存到数组
        e.start(out);
        //重复次数 0表示无限重复 默认不重复
        e.setRepeat(0);
        //进行采样
        BufferedImage[] fs = getFrames(decoder);

        for (BufferedImage f : fs) {
            e.setDelay(decoder.getDelay(0));
            BufferedImage image = compressPicJpg(f, scale);
            e.addFrame(image);

        }
        e.finish();

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetPathName);
            fileOutputStream.write(out.toByteArray());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public static BufferedImage compressPicJpg(BufferedImage frame, float scale) {
        try {
            return Thumbnails.of(frame).outputFormat("jpg").scale(scale).outputQuality(scale).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

    private static BufferedImage[] getFrames(GifDecoder decoder) {
        int cnt = decoder.getFrameCount();
        //我这里只采了50帧
        int max = cnt > 45 ? Float.valueOf(cnt * 0.95f).intValue() : cnt;
        if (cnt <= max) {
            BufferedImage[] r = new BufferedImage[cnt];
            for (int i = 0; i < cnt; i++) {
                r[i] = decoder.getFrame(i);
            }
            return r;
        } else if (cnt < max * 2) {
            BufferedImage[] r = new BufferedImage[max];
            for (int i = 0; i < max; i++) {
                r[i] = decoder.getFrame(i);
            }
            return r;
        } else {
            BufferedImage[] r = new BufferedImage[max];
            int sec = cnt / max;
            int n = 0;
            for (int i = 0; i < cnt && n < max; i += sec) {
                r[n] = decoder.getFrame(i);
                n++;
            }
            return r;
        }
    }

    public static float getScare(int width, int height) {
        int n = Math.max(width, height);
        float rate;
        //大于450像素
        if (n >= 450) {
            //缩放到450*0.2=90像素
            rate = 0.2f;
        } else if (n >= 400) {
            rate = 0.26f;
        } else if (n >= 300) {
            rate = 0.3f;
        } else if (n >= 200) {
            rate = 0.4f;
        } else if (n >= 100) {
            rate = 0.6f;
        } else if (n >= 80) {
            rate = 0.7f;
        } else {
            rate = 0.8f;
        }
        return rate;
    }

    public static byte[] readByByteArrayOutputStream(File file) {
        checkFileExists(file);
        // 传统IO方式
        //1、定义一个Byte字节数组输出流，设置大小为文件大小
        //2、将打开的文件输入流转换为Buffer输入流，循环 读取buffer输入流到buffer[]缓冲，并将buffer缓冲数据输入到目标输出流。
        //3、将目标输出流转换为字节数组。
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream bin = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            while (bin.read(buffer) > 0) {
                bos.write(buffer);
            }
            return bos.toByteArray();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeInputStream(bin);
            closeOutputStream(bos);
        }
    }

    private static void closeOutputStream(OutputStream bos) {
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeInputStream(InputStream in) {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkFileExists(File file) {
        if (file == null || !file.exists()) {
            System.err.println("file is not null or exist !");
            return;
        }
    }
}
