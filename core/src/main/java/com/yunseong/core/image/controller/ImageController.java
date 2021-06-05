package com.yunseong.core.image.controller;

import com.yunseong.core.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping(value = "images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestPart(required = false, name = "file") MultipartFile[] files,
                                         @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        this.imageService.save(principal.getAttribute("user_name"), files);
        return ResponseEntity
                .created(URI.create("/" + principal.getAttribute("user_name") + "/{fileName}"))
                .build();
    }
}
