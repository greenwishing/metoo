package com.metoo.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/17
 */
public class FileuploadUtils {

    private static final String FILEUPLOAD_LOCATION = "D:\\metoo\\fileupload\\";
    private static final String PICTURE_LOCATION = FILEUPLOAD_LOCATION + "images\\";

    public static String storePicture(MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        String md5 = MD5Utils.encode(bytes);
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        String pictureKey = md5 + suffix;
        File dir = new File(PICTURE_LOCATION);
        if (!dir.exists()) dir.mkdirs();
        File picture = new File(dir, pictureKey);
        file.transferTo(picture);
        return pictureKey;
    }

    public static File readPicture(String pictureKey) {
        if (!StringUtils.hasText(pictureKey)) {
            return null;
        }
        return new File(PICTURE_LOCATION + pictureKey);
    }
}
