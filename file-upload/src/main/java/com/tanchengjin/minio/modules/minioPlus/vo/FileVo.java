package com.tanchengjin.minio.modules.minioPlus.vo;

import com.tanchengjin.minio.dto.UploadDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
public class FileVo {
    /**
     * 上传状态码
     */
    private Integer uploadCode;
    /**
     * 分块上传url
     */
    private List<UploadDto> chunkUploadUrls;
    /**
     * 文件Url
     */
    private String fileUrl;

    private String msg="操作成功";
}
