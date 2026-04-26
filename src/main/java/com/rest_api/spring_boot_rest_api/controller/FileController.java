package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.controller.docs.FileControllerDocs;
import com.rest_api.spring_boot_rest_api.dto.v1.UploadFileResponseDto;
import com.rest_api.spring_boot_rest_api.service.FilesService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {


    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FilesService service;

    @PostMapping("/uploadFile") // POST for via MultiPart use body of request.
    @Override
    public UploadFileResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
        var fileName = service.storeFile(file); // save file and return file name

        // mount current path for download http://localhost:8080/api/file/v1/downloadFile
        var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponseDto(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @Override
    public List<UploadFileResponseDto> uploadMultipleFile(MultipartFile[] file) {
        return List.of();
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
        return null;
    }
}
