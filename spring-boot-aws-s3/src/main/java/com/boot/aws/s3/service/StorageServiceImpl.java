package com.boot.aws.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

@Service
public class StorageServiceImpl implements StorageService {


    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;


    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        File file = translateMultipartToFile(multipartFile);
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete();
        return "File" + multipartFile.getOriginalFilename()+ "has been uploaded successfully in bucket "+bucketName;
    }


    public File translateMultipartToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public byte[] downloadFile(String filename) {
        S3Object s3Object = amazonS3.getObject(bucketName, filename);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(s3ObjectInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName){
        amazonS3.deleteObject(bucketName, fileName);
        return "File "+fileName + " has been removed from bucket - "+bucketName;
    }
}
