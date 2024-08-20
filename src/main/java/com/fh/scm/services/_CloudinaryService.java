package com.fh.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface _CloudinaryService {

    String uploadImage(MultipartFile file);
}
