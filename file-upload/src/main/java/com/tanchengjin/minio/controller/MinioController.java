package com.tanchengjin.minio.controller;

import com.tanchengjin.minio.common.Base64ToMultipartFile;
import com.tanchengjin.minio.conf.MinioProperties;
import com.tanchengjin.util.ServerResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RestController
@RequestMapping("/minio")
public class MinioController {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public MinioController(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @RequestMapping(value = {"/upload"}, method = RequestMethod.POST)
    public String index(MultipartFile file) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String objectName = sdf.format(new Date()) + "/" + file.getOriginalFilename();
        PutObjectArgs putObjectArgs = PutObjectArgs
                .builder()
                .bucket(minioProperties.getBucket())
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType()).build();
        try {
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);
            System.out.println("1");
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
        return "app controller";
    }


    @SneakyThrows
    @PostMapping("/put-file-bs")
    public ServerResponse putFileBS(@RequestBody Map<String, Object> requestParams) {
        String base64 = (String) requestParams.get("base64");
        String filename;
        if (!requestParams.containsKey("filename")) {
            String suffix = "jpg";
            if (requestParams.containsKey("ext")) {
                suffix = (String) requestParams.get("ext");
            }
            filename = UUID.randomUUID() + "." + suffix;
        } else {
            filename = (String) requestParams.get("filename");
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64.split(",")[1]);

//            byte[] bytes = decoder.decodeBuffer(base64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
            PutObjectArgs putObjectArgs = PutObjectArgs
                    .builder()
                    .bucket(minioProperties.getBucket())
                    .object(filename)
                    .stream(inputStream, 1024, -1)
                    .contentType("jpg").build();
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);
            return ServerResponse.responseWithSuccessMessage("上传成功");
//            return R.data("");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ServerResponse.responseWithFailure("上传失败");
    }
}
