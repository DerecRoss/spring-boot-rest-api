package com.rest_api.spring_boot_rest_api.controller.docs;

import com.rest_api.spring_boot_rest_api.dto.v1.UploadFileResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Endpoint")
public interface FileControllerDocs {

    UploadFileResponseDto uploadFile(MultipartFile file);
    List<UploadFileResponseDto> uploadMultipleFile(MultipartFile[] file);
    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
