package com.cassey.house.image.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageUtil {
    /**
     * 读取图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Map<String, BufferedImage> readImages(String path) {
        Map<String, BufferedImage> bufferedImageMap = new HashMap<>();

        try {
            List<String> allFiles = allFiles(path);
            allFiles.forEach(fileName -> {
                try {
                    if (isImage(fileName)) {
                        bufferedImageMap.put(fileName, ImageIO.read(new File(path + "/" + fileName)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            //bufferedImage = readCmykImage(file);
        }

        return bufferedImageMap;
    }

    /**
     * 读取图片
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static BufferedImage readImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * list all file
     *
     * @param filePath
     * @return
     */
    public static List<String> allFiles(String filePath) {
        List<String> fileList = new ArrayList<>();
        File dirFile = new File(filePath);
        if (dirFile.exists()) {
            File files[] = dirFile.listFiles();
            for (File file : files) {
                if(file.isFile() && !file.getName().equalsIgnoreCase(".DS_Store")) fileList.add(file.getName());
            }
        }
        return fileList;
    }

    public static boolean isImage(String fileName) {
        if (fileName == null) {
            return false;
        }

        if (fileName.contains(".")) {
            int lastIndex = fileName.lastIndexOf(".");
            String extentName = fileName.substring(lastIndex);

            for (String type : Consts.SUPPORT_IMAGE) {
                if (type.equalsIgnoreCase(extentName)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isPng() {
        return false;
    }
}
