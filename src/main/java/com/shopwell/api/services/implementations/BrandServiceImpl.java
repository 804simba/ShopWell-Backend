package com.shopwell.api.services.implementations;

import com.shopwell.api.model.VOs.request.BrandRegistrationVO;
import com.shopwell.api.model.entity.Brand;
import com.shopwell.api.repository.BrandRepository;
import com.shopwell.api.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Brand registerBrand(BrandRegistrationVO brandRegistrationVO) {
        return brandRepository.save(mapBrandRegistrationVO(brandRegistrationVO));
    }

    @Override
    public Brand findProductByBrandName(String brandName) {
        return brandRepository.findByBrandName(brandName)
                .orElse(null);
    }

    private Brand mapBrandRegistrationVO(BrandRegistrationVO brandRegistrationVO) {
        return Brand.builder()
                .brandName(brandRegistrationVO.getBrandName())
                .build();
    }
}
