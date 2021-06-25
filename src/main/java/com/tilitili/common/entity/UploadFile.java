package com.tilitili.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.awt.image.BufferedImage;
import java.io.File;

@Data
@Accessors(chain = true)
public class UploadFile {
    private String key;
    private String fileName;
    private String fileType;
    private BufferedImage bufferedImage;
    private File file;
}
