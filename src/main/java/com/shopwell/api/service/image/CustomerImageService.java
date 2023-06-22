package com.shopwell.api.service.image;

import com.shopwell.api.config.CloudinaryConfig;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.Product;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerImageService extends CloudinaryImageServiceImpl<Customer>{
    public CustomerImageService(CloudinaryConfig config) {
        super(config);
    }

    @Override
    public String getPublicIdPrefix() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "customer" + uuid + "/";
    }
}
