package com.cassey.house.image;

import com.cassey.house.image.utils.ImageUtil;
import com.cassey.house.image.utils.WebpImageUtil;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class ImageEntry {
    public static void main(String[] args) {
        String pathName = "live";

        webpToJpg(pathName);

        //jpgToJpg(pathName);

        //gifToGif();
    }

    private static void gifToGif() {
        String basePath = "/Users/dealmoon/Downloads/images/";

        Thumbnail.resizeFitGif(basePath + "/1662540931544.gif", basePath + "/1662540931544-1.gif");
    }


    private static void jpgToJpg(String pathName) {
        String sourcePath = "/Users/dealmoon/Downloads/images/" + pathName;
        String targetPath = sourcePath + "/jpg";
        Thumbnail.resizeWithoutCut(sourcePath, targetPath);
    }

    private static void webpToJpg(String pathName) {
        String basePath = "/Users/dealmoon/Downloads/images/" + pathName;
        String targetPath = basePath + "/jpg";
        File tagetPaths = new File(targetPath);
        if (!tagetPaths.exists()) {
            tagetPaths.mkdir();
        }

        List<String> imageFileList = ImageUtil.allFiles(basePath);
        imageFileList.forEach(fileName -> {
            String targetFile = basePath + "/jpg/" + UUID.randomUUID().toString().replace("-", "") + ".jpg";
            WebpImageUtil.webpToJpg(basePath + "/" + fileName, targetFile);
        });
    }
}
