package com.boot.aws.s3.controller;

import com.boot.aws.s3.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile){
        return new ResponseEntity<>(storageService.uploadFile(multipartFile), HttpStatus.OK) ;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName){
        byte[] output = storageService.downloadFile(fileName);
        ByteArrayResource byteArrayResource = new ByteArrayResource(output);
        ResponseEntity.ok().contentLength(output.length).header("Content-type", "application/octent-stream")
                .header("Content-disposition", "attachment; filename=\""+fileName+"\"").body(byteArrayResource);
        return new ResponseEntity<>(byteArrayResource, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName){
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }
}
