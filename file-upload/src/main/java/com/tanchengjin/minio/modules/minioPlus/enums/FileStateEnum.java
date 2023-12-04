package com.tanchengjin.minio.modules.minioPlus.enums;

import lombok.Getter;


@Getter
public enum FileStateEnum {
    UPLOAD_SUCCESS(1, "上传完成"),

    CHUNK_UPLOAD(2, "分片上传中"),

    CHUNK_UPLOAD_SUCCESS(3, "分片上传完成");

    private final Integer code;
    private final String msg;

    FileStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
