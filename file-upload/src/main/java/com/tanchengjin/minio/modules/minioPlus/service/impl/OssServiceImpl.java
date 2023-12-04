package com.tanchengjin.minio.modules.minioPlus.service.impl;

import com.tanchengjin.minio.modules.minioPlus.service.OssService;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Service
public class OssServiceImpl implements OssService {

    private final static Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);

    private final MinioClient minioClient;

    public OssServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }


    /**
     * 创建上传文件对象的外链
     *
     * @param bucketName 存储桶名称
     * @param objectName 欲上传文件对象的名称
     * @param expiry     过期时间(天) 最大为7天 超过7天则默认最大值
     * @return uploadUrl
     */
    @Override
    @SneakyThrows
    public String createUploadUrl(String bucketName, String objectName, Integer expiry) {
        expiry = expiry * 24 * 60 * 60;
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(bucketName)
                .object(objectName)
                .expiry(expiry).build();
        return minioClient.getPresignedObjectUrl(build);
//        return minioClient.getPresignedObjectUrl(Method.PUT,bucketName,objectName,expiry,new HashMap<>());
    }

    /**
     * 批量创建分片上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param chunkCount 分片数量
     * @return uploadChunkUrls
     */
    @Override
    public List<String> createUploadChunkUrlList(String bucketName, String objectMD5, Integer chunkCount) {
        if (null == objectMD5) {
            return null;
        }
        objectMD5 += "/";
        if (null == chunkCount || 0 == chunkCount) {
            return null;
        }
        List<String> urlList = new ArrayList<>(chunkCount);
        for (int i = 1; i <= chunkCount; i++) {
            String objectName = objectMD5 + i + ".chunk";
            urlList.add(createUploadUrl(bucketName, objectName, 7));
        }
        return urlList;
    }

    /**
     * 创建指定序号的分片文件上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param partNumber 分片序号
     * @return uploadChunkUrl
     */
    @Override
    public String createUploadChunkUrl(String bucketName, String objectMD5, Integer partNumber) {
        if (null == objectMD5) {
            return null;
        }
        objectMD5 += "/" + partNumber + ".chunk";
        return createUploadUrl(bucketName, objectMD5, 7);
    }

    /**
     * 创建指定序号的分片文件上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param partNumber 分片序号
     * @return uploadChunkUrl
     */
    @Override
    public String createUploadChunkUrl(String bucketName, String objectMD5, Integer partNumber, String baseUrl, String newBaseUrl) {
        if (null == objectMD5) {
            return null;
        }
        objectMD5 += "/" + partNumber + ".chunk";
        return createUploadUrl(bucketName, objectMD5, 7, baseUrl, newBaseUrl);
    }

    /**
     * 创建上传文件对象的外链并使用新的url地址
     *
     * @param bucketName 存储桶名称
     * @param objectName 欲上传文件对象的名称
     * @param expiry     过期时间(天) 最大为7天 超过7天则默认最大值
     * @return uploadUrl
     */
    @Override
    @SneakyThrows
    public String createUploadUrl(String bucketName, String objectName, Integer expiry, String baseUrl, String newBaseUrl) {
        String uploadUrl = this.createUploadUrl(bucketName, objectName, expiry);
        logger.info("uploadUrl: [{}]", uploadUrl);
        String lastW = newBaseUrl.substring(newBaseUrl.length() - 1);
        System.out.println(lastW);
        if (!"/".equals(lastW) && !"\\".equals(lastW) && !lastW.equals(File.separator)) {
            newBaseUrl = newBaseUrl + "/";
        }
        uploadUrl = uploadUrl.replace(baseUrl, newBaseUrl);

        logger.info("uploadUrl replace: [{}]", uploadUrl);
        return uploadUrl;
    }

    /**
     * 获取对象文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param prefix     对象名称前缀
     * @return objectNames
     */
    @Override
    @SneakyThrows
    public List<String> listObjectNames(String bucketName, String prefix) {
        ListObjectsArgs build = ListObjectsArgs.builder()
                .bucket(bucketName)
                .prefix(prefix + "/")
                .build();
        Iterable<Result<Item>> chunks = minioClient.listObjects(build);
//        Iterable<Result<Item>> chunks = minioClient.listObjects(bucketName, prefix + "/");
        List<String> chunkPaths = new ArrayList<>();
        for (Result<Item> item : chunks) {
            chunkPaths.add(item.get().objectName());
        }
        chunkPaths.sort((o1, o2) -> {
            if (o1.length() == o2.length()) {
                return o1.compareTo(o2);
            }
            if (o1.length() > o2.length()) {
                return 1;
            }
            return -1;
        });
        return chunkPaths;
    }

    /**
     * 获取分片文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param objectMd5  对象Md5
     * @return objectChunkNames
     */
    @Override
    public List<String> listChunkObjectNames(String bucketName, String objectMd5) {
        if (null == objectMd5) {
            return null;
        }
        return listObjectNames(bucketName, objectMd5);
    }

    /**
     * 获取分片名称地址HashMap key=分片序号 value=分片文件地址
     *
     * @param bucketName 存储桶名称
     * @param objectMd5  对象Md5
     * @return objectChunkNameMap
     */
    @Override
    public Map<Integer, String> mapChunkObjectNames(String bucketName, String objectMd5) {
        if (null == objectMd5) {
            return null;
        }
        List<String> chunkPaths = listObjectNames(bucketName, objectMd5);
        if (null == chunkPaths || chunkPaths.size() == 0) {
            return null;
        }
        Map<Integer, String> chunkMap = new HashMap<>(chunkPaths.size());
        for (String chunkName : chunkPaths) {
            Integer partNumber = Integer.parseInt(chunkName.substring(chunkName.indexOf("/") + 1, chunkName.lastIndexOf(".")));
            chunkMap.put(partNumber, chunkName);
        }
        return chunkMap;
    }

    /**
     * 合并分片文件成对象文件
     *
     * @param chunkBucKetName   分片文件所在存储桶名称
     * @param composeBucketName 合并后的对象文件存储的存储桶名称
     * @param chunkNames        分片文件名称集合
     * @param objectName        合并后的对象文件名称
     * @return true/false
     */
    @Override
    @SneakyThrows
    public boolean composeObject(String chunkBucKetName, String composeBucketName, List<String> chunkNames, String objectName) {
//        minioClient
        List<ComposeSource> sourceObjectList = new ArrayList<>(chunkNames.size());
        for (String chunk : chunkNames) {
            sourceObjectList.add(ComposeSource.builder()
                    .bucket(chunkBucKetName)
                    .object(chunk)
                    .build());
        }
        minioClient.composeObject(ComposeObjectArgs.builder()
                .bucket(composeBucketName)
                .object(objectName)
                .sources(sourceObjectList)
                .build());
        // 合并文件后删除分块
        for (String chunk : chunkNames) {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(chunkBucKetName)
                    .object(chunk)
                    .build());
        }
        return true;
    }
}
