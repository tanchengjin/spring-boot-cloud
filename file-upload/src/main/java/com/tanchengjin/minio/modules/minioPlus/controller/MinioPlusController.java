package com.tanchengjin.minio.modules.minioPlus.controller;

import com.tanchengjin.minio.dto.FileDto;
import com.tanchengjin.minio.modules.minioPlus.exception.FileUploadException;
import com.tanchengjin.minio.modules.minioPlus.service.FileService;
import com.tanchengjin.minio.modules.minioPlus.vo.FileVo;
import com.tanchengjin.util.ServerResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RestController
@RequestMapping("/minioPlus")
public class MinioPlusController {
    private final FileService fileService;

    public MinioPlusController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/chunk")
    public ServerResponse<FileVo> chunkUpload(@RequestBody FileDto dto) {
        FileVo fileVo = new FileVo();
        try {
            fileVo = fileService.uploadByChunk(dto);
        } catch (Exception e) {
            e.printStackTrace();
            fileVo.setMsg("服务器错误");
        }
        return ServerResponse.responseWithSuccess(fileVo);
    }


    /**
     * 兼容前端for-data格式的请求
     *
     * @param filename   文件名
     * @param fileMd5    文件MD5
     * @param chunkCount 可分块的数量
     * @return R<FileVo>
     */
    @PostMapping("/chunkByFormData")
    public ServerResponse<FileVo> chunkUploadByFormData(@RequestParam("fileName") String filename, @RequestParam("fileMd5") String fileMd5, @RequestParam("chunkCount") Integer chunkCount) {
        FileDto dto = new FileDto();
        dto.setChunkCount(chunkCount);
        dto.setFileName(filename);
        dto.setFileMd5(fileMd5);
        FileVo fileVo = new FileVo();
        try {
            fileVo = fileService.uploadByChunk(dto);
        } catch (Exception e) {
            e.printStackTrace();
            fileVo.setMsg("服务器错误");
        }
        return ServerResponse.responseWithSuccess(fileVo);
    }

    @PostMapping("/compose")
    public ServerResponse<FileVo> composeFile(@RequestBody FileDto dto) {
        FileVo fileVo = new FileVo();
        try {
            fileVo = fileService.compose(dto);
        } catch (FileUploadException e) {
            e.printStackTrace();
            fileVo.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fileVo.setMsg("服务器错误");
        }
        return ServerResponse.responseWithSuccess(fileVo);
    }
}
