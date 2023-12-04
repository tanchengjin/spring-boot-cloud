package com.tanchengjin.minio.dto;

import lombok.Data;


@Data
public class FileDto {
    /**
     * 分片数量
     */
    private Integer chunkCount;
    /**
     * 文件的md5
     */
    private String fileMd5;
    /**
     * 文件名称
     */
    private String fileName;
}
