package com.tanchengjin.minio.modules.minioPlus.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanchengjin.minio.modules.minioPlus.entity.FileEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMapper extends BaseMapper<FileEntity> {
}
