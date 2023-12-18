package com.zj.union.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public Object upload(MultipartFile fileUpload);
}
