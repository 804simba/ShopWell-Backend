package com.shopwell.api.service;

import com.shopwell.api.model.VOs.request.BrandRegistrationVO;
import com.shopwell.api.model.entity.Brand;

public interface BrandService {

    Brand registerBrand(BrandRegistrationVO brandRegistrationVO);

    Brand findProductByBrandName(String brandName);
}
