package com.shopwell.api.service.implementations;

import com.shopwell.api.model.DTOs.request.BrandRegistrationVO;
import com.shopwell.api.model.entity.Brand;
import com.shopwell.api.repository.BrandRepository;
import com.shopwell.api.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public void registerBrand(BrandRegistrationVO brandRegistrationVO) {
        brandRepository.save(mapBrandRegistrationVO(brandRegistrationVO));
    }

    private Brand mapBrandRegistrationVO(BrandRegistrationVO brandRegistrationVO) {
        return Brand.builder()
                .brandName(brandRegistrationVO.getBrandName())
                .build();
    }
}
