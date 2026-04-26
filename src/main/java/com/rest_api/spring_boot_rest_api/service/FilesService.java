package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.config.FileStorageConfig;
import com.rest_api.spring_boot_rest_api.controller.FileController;
import com.rest_api.spring_boot_rest_api.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FilesService {

    private final Path fileStorageLocation;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FilesService(FileStorageConfig fileStorageConfig) {

        //create directory in disk.

        this.fileStorageLocation = Paths.get(fileStorageConfig
                .getUploadDir())
                .toAbsolutePath()
                .toAbsolutePath().normalize();

        try {
            logger.info("Creating directory;");
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("Could create directory for save file");
            throw new FileStorageException("Could create directory for save file.", e.getCause());
        }
    }

    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try{
            if (fileName.contains("..")) throw new FileStorageException("Sorry, fileName contains invalid name");

            logger.info("Saving file in disk");

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            logger.info("Upload directory: {}", this.fileStorageLocation);

            return fileName;
        } catch (Exception e){
            logger.error("Could not store file {} try again.", fileName);
            throw new FileStorageException("Could not store file " + fileName + " try again.", e.getCause());
        }
    }
}
