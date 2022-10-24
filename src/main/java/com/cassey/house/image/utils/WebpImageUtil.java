package com.cassey.house.image.utils;

import com.luciad.imageio.webp.ImageDataException;
import com.luciad.imageio.webp.WebPReadParam;
import com.luciad.imageio.webp.WebPWriteParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WebpImageUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebpImageUtil.class);

    /**
     * 转换成webp格式
     *
     * @param
     */
    public static void toWebp() {
        FileImageOutputStream outputStream = null;
        try {
            outputStream = new FileImageOutputStream(new File("imageInfo.getOutputFileName()"));
            BufferedImage image = ImageUtil.readImage("imageInfo.getFullThumbFileName()");

            // Obtain a WebP ImageWriter instance
            ImageWriter writer = ImageIO.getImageWritersByMIMEType(Consts.WEBP_MINE_TYPE).next();

            // Configure encoding parameters
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(WebPWriteParam.MODE_DEFAULT);

            // Configure the output on the ImageWriter
            writer.setOutput(outputStream);

            // Encode
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } catch (Exception ex) {
            throw new RuntimeException("webp convert error", ex);
        } finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("webp convert error", e);
                }
            }
        }
    }

    public static void webpToJpg(String sourceFileName, String targetFileName) {
        try {
            // Obtain a WebP ImageReader instance
            ImageReader reader = ImageIO.getImageReadersByMIMEType(Consts.WEBP_MINE_TYPE).next();

            // Configure decoding parameters
            WebPReadParam readParam = new WebPReadParam();
            readParam.setBypassFiltering(true);

            // Configure the input on the ImageReader
            reader.setInput(new FileImageInputStream(new File(sourceFileName)));

            // Decode the image
            BufferedImage image = reader.read(0, readParam);

            ImageIO.write(image, Consts.IMGE_TYPE_JPG, new File(targetFileName));
        } catch (Exception ex) {
            logger.error("webp convert jpg error, image:" + sourceFileName , ex);
        }
    }
}
