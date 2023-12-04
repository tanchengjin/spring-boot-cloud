package com.tanchengjin.minio.modules.minioPlus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("fileupload")
public class FileEntity {
    @TableId
    private String id;

    @TableField("file_name")
    private String fileName;

    @TableField("file_state")
    private Integer fileState;

    @TableField("file_url")
    private String fileUrl;
}
