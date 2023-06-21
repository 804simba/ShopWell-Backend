package com.shopwell.api.service.image;

import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import com.shopwell.api.config.CloudinaryConfig;
import com.shopwell.api.exceptions.ImageDeleteException;
import com.shopwell.api.exceptions.ImageUploadException;
import com.shopwell.api.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryImageServiceImpl<T> implements ImageService<T> {
    private final CloudinaryConfig config;

    @Override
    public String uploadImage(Long productId, MultipartFile imageFile) {
        try {
            if (imageFile.isEmpty()) {
                throw new ImageUploadException("Image file is empty");
            }

            String publicId = getPublicIdPrefix() + productId;

            Transformation<?> transformation = new Transformation<>()
                    .width(800)
                    .height(800)
                    .crop("limit")
                    .quality(80);

            Map<?, ?> uploadResult = config.cloudinary().uploader()
                    .upload(imageFile.getBytes(), ObjectUtils.asMap("public_id", publicId, "transformation", transformation));

            String imageURL = uploadResult.get("secure_url").toString();

            if (StringUtils.isEmpty(imageURL)) {
                throw new ImageUploadException("Failed to retrieve image URL");
            }

            return imageURL;
        } catch (IOException e) {
            throw new ImageUploadException("Failed to upload image" + e.getMessage());
        }
    }

    public String getPublicIdPrefix() {
        return "";
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
