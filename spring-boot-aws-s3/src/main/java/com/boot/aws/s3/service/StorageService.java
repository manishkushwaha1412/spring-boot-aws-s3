package com.boot.aws.s3.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {

    public String uploadFile(MultipartFile multipartFile);

    public byte[] downloadFile(String filename);

    public String deleteFile(String fileName);

}
