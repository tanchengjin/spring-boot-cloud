package com.tanchengjin.minio.dto;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UploadDto {
    /**
     * 分片序号
     */
    private Integer partNumber;

    /**
     * 上传地址
     */
    private String uploadUrl;

    private String orgUploadUrl;
}
