package com.tanchengjin.minio.modules.minioPlus.service;

import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

public interface OssService {

    @SneakyThrows
    String createUploadUrl(String bucketName, String objectName, Integer expiry);

    List<String> createUploadChunkUrlList(String bucketName, String objectMD5, Integer chunkCount);

    String createUploadChunkUrl(String bucketName, String objectMD5, Integer partNumber);

    String createUploadChunkUrl(String bucketName, String objectMD5, Integer partNumber, String baseUrl, String newBaseUrl);

    @SneakyThrows
    String createUploadUrl(String bucketName, String objectName, Integer expiry, String baseUrl, String newBaseUrl);

    @SneakyThrows
    List<String> listObjectNames(String bucketName, String prefix);

    List<String> listChunkObjectNames(String bucketName, String objectMd5);

    Map<Integer, String> mapChunkObjectNames(String bucketName, String objectMd5);

    @SneakyThrows
    boolean composeObject(String chunkBucKetName, String composeBucketName, List<String> chunkNames, String objectName);
}
