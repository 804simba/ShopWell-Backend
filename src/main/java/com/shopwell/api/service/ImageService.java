package com.shopwell.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService<T> {

    String uploadImage(Long productId, MultipartFile imageFile);

    void deleteImage(String imageURL);
}
