package com.shopwell.api.services;

import com.shopwell.api.model.VOs.request.BrandRegistrationVO;
import com.shopwell.api.model.entity.Brand;

public interface BrandService {

    Brand registerBrand(BrandRegistrationVO brandRegistrationVO);

    Brand findProductByBrandName(String brandName);
}
