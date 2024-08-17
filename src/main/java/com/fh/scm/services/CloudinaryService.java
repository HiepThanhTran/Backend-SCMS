package com.fh.scm.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    
    String uploadImage(MultipartFile file);
}
