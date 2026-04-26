package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.controller.docs.FileControllerDocs;
import com.rest_api.spring_boot_rest_api.dto.v1.UploadFileResponseDto;
import com.rest_api.spring_boot_rest_api.service.FilesService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/uploadMultipleFiles")
    @Override
    public List<UploadFileResponseDto> uploadMultipleFile(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream().map(f -> uploadFile(f)).collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    @Override
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = service.loadFileAsResource(fileName);
        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath()); // get mimetype via extension
        } catch (Exception e) {
            logger.error("Could not get file type.");
        }
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, // attach in header of response.
                   "attachment; filename=\""
                                + resource.getFilename() + "\"")
                .body(resource);
    }
}
