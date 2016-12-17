package com.metoo.utils;

import com.metoo.core.MetooSystem;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/17
 */
public class FileuploadUtils {

    public static final String IMAGES_LOCATION = "images\\";

    public static String storePicture(MultipartFile file, MetooSystem metooSystem) throws Exception {
        byte[] bytes = file.getBytes();
        String md5 = MD5Utils.encode(bytes);
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        String pictureKey = md5 + suffix;
        String location = metooSystem.getFileuploadLocation();
        File dir = new File(location);
        if (!dir.exists()) dir.mkdirs();
        File picture = new File(dir, IMAGES_LOCATION + pictureKey);
        file.transferTo(picture);
        return pictureKey;
    }

    public static File readPicture(String pictureKey, MetooSystem metooSystem) {
        if (!StringUtils.hasText(pictureKey)) {
            return null;
        }
        String location = metooSystem.getFileuploadLocation();
        return new File(location + IMAGES_LOCATION + pictureKey);
    }
}
