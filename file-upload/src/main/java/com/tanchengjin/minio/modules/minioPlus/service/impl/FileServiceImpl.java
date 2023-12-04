package com.tanchengjin.minio.modules.minioPlus.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanchengjin.minio.conf.MinioProperties;
import com.tanchengjin.minio.dto.FileDto;
import com.tanchengjin.minio.dto.UploadDto;
import com.tanchengjin.minio.modules.minioPlus.dao.FileMapper;
import com.tanchengjin.minio.modules.minioPlus.entity.FileEntity;
import com.tanchengjin.minio.modules.minioPlus.enums.FileStateEnum;
import com.tanchengjin.minio.modules.minioPlus.exception.FileUploadException;
import com.tanchengjin.minio.modules.minioPlus.service.FileService;
import com.tanchengjin.minio.modules.minioPlus.service.OssService;
import com.tanchengjin.minio.modules.minioPlus.vo.FileVo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

    private final MinioProperties minioProperties;

    private final OssService ossService;

    public FileServiceImpl(MinioProperties minioProperties, OssService ossService) {
        this.minioProperties = minioProperties;
        this.ossService = ossService;
    }

    @Override
    public FileVo uploadByChunk(FileDto dto) {
        // 根据文件MD5作为主键查询,查询文件是否上传过
        FileEntity fileEntity = this.getById(dto.getFileMd5());

        if (fileEntity != null) {
            // 文件已上传完成，实现秒传
            if (fileEntity.getFileState().equals(FileStateEnum.UPLOAD_SUCCESS.getCode())) {
                return new FileVo()
                        .setUploadCode(fileEntity.getFileState())
                        .setFileUrl(fileEntity.getFileUrl());
            }

            // 文件断点续传
            if (fileEntity.getFileState().equals(FileStateEnum.CHUNK_UPLOAD.getCode())) {
                return this.breakpointResume(dto);
            }
        }
        FileVo upload = this.createUpload(dto);
        this.save(new FileEntity()
                .setFileName(dto.getFileName())
                .setId(dto.getFileMd5())
                .setFileState(FileStateEnum.CHUNK_UPLOAD.getCode()));
        return upload;
    }

    /**
     * 断点续传
     *
     * @param dto
     * @return
     */
    private FileVo breakpointResume(FileDto dto) {

//        MinioClient.Builder builder = MinioClient.builder();
//        MinioClient build = builder.endpoint().build();
        // 已上传成功的分片
        Map<Integer, String> okChunkMap = ossService.mapChunkObjectNames(minioProperties.getBucket(), dto.getFileMd5());
        // 没有分片记录创建分片上传
        if (okChunkMap == null) {
            return this.createUpload(dto);
        }
        List<UploadDto> chunkUploadUrls = new ArrayList<>();
        for (int i = 1; i <= dto.getChunkCount(); i++) {
            // 判断当前分片是否上传完成
            if (!okChunkMap.containsKey(i)) {
                chunkUploadUrls.add(new UploadDto()
                        .setPartNumber(i)
                        .setUploadUrl(ossService.createUploadChunkUrl(minioProperties.getBucket(), dto.getFileMd5(), i)));
            }
        }
        // 分片全部上传完成
        if (chunkUploadUrls.size() == 0) {
            return new FileVo().setUploadCode(FileStateEnum.CHUNK_UPLOAD_SUCCESS.getCode());
        }

        return new FileVo()
                .setUploadCode(FileStateEnum.CHUNK_UPLOAD.getCode())
                .setChunkUploadUrls(chunkUploadUrls);
    }


    /**
     * 新建分片上传
     *
     * @param dto
     * @return
     */
    private FileVo createUpload(FileDto dto) {
        List<UploadDto> chunkUploadUrls = new ArrayList<>();
        for (int i = 1; i <= dto.getChunkCount(); i++) {
            chunkUploadUrls.add(new UploadDto()
                    .setPartNumber(i)
                    .setUploadUrl(ossService.createUploadChunkUrl(minioProperties.getBucket(), dto.getFileMd5(), i)));
        }
        return new FileVo()
                .setUploadCode(FileStateEnum.CHUNK_UPLOAD.getCode())
                .setChunkUploadUrls(chunkUploadUrls);
    }

    @Override
    public FileVo compose(FileDto dto) throws FileUploadException {
        // 分片文件
        List<String> chunks = ossService.listObjectNames(minioProperties.getBucket(), dto.getFileMd5());

        if (!ossService.composeObject(minioProperties.getBucket(), minioProperties.getBucket(), chunks, dto.getFileName())) {
//            fileService.removeById(dto.getFileMd5());
            throw new FileUploadException("文件合并失败，请重新上传");
        }
        String fileUrl = minioProperties.getEndpoint() + File.separator + minioProperties.getBucket() + "/" + dto.getFileName();
        // 更新数据库文件信息
        this.updateById(new FileEntity()
                .setId(dto.getFileMd5())
                .setFileState(FileStateEnum.UPLOAD_SUCCESS.getCode())
                .setFileName(dto.getFileName())
                .setFileUrl(fileUrl));
        return (new FileVo()
                .setFileUrl(fileUrl)
                .setUploadCode(FileStateEnum.UPLOAD_SUCCESS.getCode()));
    }
}