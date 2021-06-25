package com.tilitili.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class FileUtil {
    public static BufferedImage downloadImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            log.error("download image error.", e);
        }
        return null;
    }

    public static String md5Image(BufferedImage image, String imageType) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, imageType, outputStream);
            byte[] data = outputStream.toByteArray();
            return StringUtils.md5(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static BufferedImage pgm2png(BufferedImage image, String targetType) throws IOException {
        Raster source = image.getRaster();
        int width = image.getWidth();
        int height = image.getHeight();

        // 创建BufferedImage对象
        // BufferedImage image2 = new BufferedImage(width,
        // height,BufferedImage.TYPE_BYTE_BINARY);//黑色
        BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);// 灰色
        // BufferedImage image2 = new BufferedImage(width, height,
        // BufferedImage.TYPE_BYTE_INDEXED);//黄色
        image2.setData(source);

        return image2;
    }
}
