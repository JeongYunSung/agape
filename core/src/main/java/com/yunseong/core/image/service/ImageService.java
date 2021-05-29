package com.yunseong.core.image.service;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImageService {

    private final Resource resource = new DefaultResourceLoader().getResource("file:C:/Users/123db/Desktop/MyFolder/Project/SpringBoot/Delivery/src/main/resources/images/");

    public void save(String owner, MultipartFile[] files) {
        owner = owner.replace(".", "");
        if (files != null && files.length > 0) {
            try {
                Path path = this.resource.getFile().toPath();
                Files.createDirectory(path.resolve(owner));
                for (MultipartFile file : files)
                    Files.copy(file.getInputStream(), path.resolve(owner + "/" + file.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
