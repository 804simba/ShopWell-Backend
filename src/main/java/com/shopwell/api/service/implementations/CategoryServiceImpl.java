package com.shopwell.api.service.implementations;

import com.shopwell.api.model.VOs.request.CategoryRegistrationVO;
import com.shopwell.api.model.entity.Category;
import com.shopwell.api.repository.CategoryRepository;
import com.shopwell.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category registerCategory(CategoryRegistrationVO categoryRegistrationVO) {
        return categoryRepository.save(mapCategoryRegistrationVO(categoryRegistrationVO));
    }

    @Override
    public Category findProductByCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).orElse(null);
    }

    private Category mapCategoryRegistrationVO(CategoryRegistrationVO categoryRegistrationVO) {
        return Category.builder()
                .categoryName(categoryRegistrationVO.getCategoryName())
                .build();
    }
}
