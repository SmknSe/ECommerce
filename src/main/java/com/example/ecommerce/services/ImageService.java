package com.example.ecommerce.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {
    @Value("${application.paths.images}")
    private String folderPath;

    public byte[] downloadImg(String path) throws IOException{
        return Files.readAllBytes(new File(folderPath+path).toPath());
    }
}
