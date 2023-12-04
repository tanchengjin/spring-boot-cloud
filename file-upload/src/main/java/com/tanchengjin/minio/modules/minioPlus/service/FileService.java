package com.tanchengjin.minio.modules.minioPlus.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tanchengjin.minio.modules.minioPlus.entity.FileEntity;
import com.tanchengjin.minio.modules.minioPlus.exception.FileUploadException;
import com.tanchengjin.minio.modules.minioPlus.vo.FileVo;
import com.tanchengjin.minio.dto.FileDto;

public interface FileService extends IService<FileEntity> {
    /**
     * 获得分片的url
     *
     * @param dto
     * @return
     */
    public FileVo uploadByChunk(FileDto dto);

    public FileVo compose(FileDto dto) throws FileUploadException;
}
