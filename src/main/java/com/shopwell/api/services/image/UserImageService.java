package com.shopwell.api.services.image;

import com.shopwell.api.config.CloudinaryConfig;
import com.shopwell.api.model.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserImageService extends CloudinaryImageServiceImpl<Customer>{
    public UserImageService(CloudinaryConfig config) {
        super(config);
    }

    @Override
    public String getPublicIdPrefix() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "customer" + uuid + "/";
    }
}
