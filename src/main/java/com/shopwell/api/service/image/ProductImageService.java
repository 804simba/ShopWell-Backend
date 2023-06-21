package com.shopwell.api.service.image;

import com.shopwell.api.config.CloudinaryConfig;
import com.shopwell.api.model.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService extends CloudinaryImageServiceImpl<Product>{
    public ProductImageService(CloudinaryConfig config) {
        super(config);
    }

    @Override
    public String getPublicIdPrefix() {
        return "product_";
    }
}
