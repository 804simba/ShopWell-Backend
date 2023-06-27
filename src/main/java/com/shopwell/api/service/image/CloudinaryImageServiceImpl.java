package com.shopwell.api.service.image;

import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import com.shopwell.api.config.CloudinaryConfig;
import com.shopwell.api.exceptions.ImageDeleteException;
import com.shopwell.api.exceptions.ImageUploadException;
import com.shopwell.api.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryImageServiceImpl<T> implements ImageService<T> {
    private final CloudinaryConfig config;

    @Override
    public String uploadImage(Long productId, MultipartFile imageFile) {
        try {
            Map<?, ?> uploadResult = config.cloudinary().uploader().upload(imageFile.getBytes(), ObjectUtils.asMap(
                    "public_id", getPublicIdPrefix() + productId,
                    "timestamp", String.valueOf(System.currentTimeMillis()),
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
            ));
            return String.valueOf(uploadResult.get("secure-url"));
        } catch (IOException e) {
            throw new ImageUploadException("Error uploading image" + e.getMessage());
        }
    }

    public String getPublicIdPrefix() {
        return "/";
    }

    @Override
    public void deleteImage(String imageURL) {
        try {
            String publicId = extractProductID(imageURL);

            if(StringUtils.isEmpty(publicId)) {
                throw new ImageDeleteException("Invalid image URL");
            }

            config.cloudinary().uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ImageDeleteException("Failed to delete image" + e.getMessage());
        }
    }

    private String extractProductID(String imageURL) {
        int startIndex = imageURL.indexOf('/') + 1;
        int endIndex = imageURL.lastIndexOf('.');

        if (startIndex >= endIndex) {
            throw new IllegalArgumentException("Image URL cannot be null");
        }

        return imageURL.substring(startIndex, endIndex);
    }
}
