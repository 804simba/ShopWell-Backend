package com.shopwell.api.services.image;

import com.shopwell.api.config.CloudinaryConfig;
import com.shopwell.api.model.entity.ProductImage;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductImageService extends CloudinaryImageServiceImpl<ProductImage>{
    public ProductImageService(CloudinaryConfig config) {
        super(config);
    }

    @Override
    public String getPublicIdPrefix() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "product_" + uuid + "/";
    }
}
