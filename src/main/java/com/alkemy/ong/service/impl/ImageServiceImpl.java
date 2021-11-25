package com.alkemy.ong.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.alkemy.ong.config.AmazonS3Config;
import com.alkemy.ong.model.Base64MultipartFile;
import com.alkemy.ong.service.ImageService;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    AmazonS3Config amazonS3Config;

    @Override
    public String uploadFile(Base64MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = amazonS3Config.getEndpointUrl() + "/" + amazonS3Config.getBucketName() + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private File convertMultiPartToFile(Base64MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(Base64MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        amazonS3Config.initialize().putObject(new PutObjectRequest(amazonS3Config.getBucketName(), fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public Base64MultipartFile convert(String image) {
        byte[] result = Base64.getDecoder().decode(image);
        Base64MultipartFile file = new Base64MultipartFile(result);
        return file;
    }

    
}
