package com.tanchengjin.minio.controller;

import com.tanchengjin.util.ServerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Value("${app.upload.local}")
    private String uploadDir;

    private final boolean storagePrefix = true;

    /**
     * 文件上传
     *
     * @param file file
     */
    @RequestMapping("/upload")
    public ServerResponse uploadImage(MultipartFile file) {
        try {
            String s = this.uploadHandler(file);
            return ServerResponse.responseWithSuccess(buildResult(s));
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.responseWithFailureMessage("文件保存失败，请重试");
        }
    }

    /**
     * 存储文件并返回文件的存储路径
     *
     * @param file file
     * @return String
     * @throws IOException
     */
    public String uploadHandler(MultipartFile file) throws IOException {
        String dir = "/upload/images/";
        String datePath = this.generateDatetimePath();

        String filename = this.randomFilename(this.getFileSuffix(file));
        String filePath = dir + datePath + "/" + filename;

        String storagePath = getStoragePath();
        File target = new File(storagePath + filePath);

        File spt = new File(storagePath + dir + datePath + File.separator);
        if (!spt.exists()) {
            if (!spt.mkdirs()) {
                throw new IOException("file mkdir error");
            }
        }
        file.transferTo(target);
        if (this.storagePrefix) filePath = "/storage" + filePath;
        return filePath;
    }

    /**
     * 获取存储路径
     */
    private String getStoragePath() {
        String storagePath = uploadDir;
        //去除尾部特殊符号
        if (storagePath.endsWith("/") || storagePath.endsWith("\\")) {
            storagePath = storagePath.substring(0, storagePath.length() - 1);
        }
        return storagePath;
    }

    /**
     * 生成日期格式的存储路径
     *
     * @return String
     */
    public String generateDatetimePath() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取文件后缀
     *
     * @param file MultipartFile
     * @return String
     */
    public String getFileSuffix(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null : "file must not null";
        int i = originalFilename.lastIndexOf(".");
        String suffix = "";
        if (i >= 1) {
            suffix = originalFilename.substring(i);
        }
        return suffix;
    }

    /**
     * 生成随机文件名
     *
     * @param suffix String
     * @return String
     */
    private String randomFilename(String suffix) {
        return UUID.randomUUID().toString() + suffix;
    }

    private String randomFilename() {
        return UUID.randomUUID().toString();
    }

    private Map buildResult(String src) {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("src", src);
        return objectObjectHashMap;
    }
}
