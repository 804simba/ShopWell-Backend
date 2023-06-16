package com.shopwell.api.model.seeder;

import com.shopwell.api.model.entity.Brand;
import com.shopwell.api.model.entity.Category;
import com.shopwell.api.repository.BrandRepository;
import com.shopwell.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final static String[] brandNames = new String[]{"nike", "samsung", "tecno", "lg", "indomie", "adidas"};
    private final static String[] categoryNames = new String[]{"fashion", "electronics", "smartphones", "groceries", "laptops"};
    @Override
    public void run(String... args) throws Exception {
        List<Brand> brands = Arrays.stream(brandNames)
                .map(brandName -> Brand.builder().brandName(brandName).build()).collect(Collectors.toList());
        brandRepository.saveAll(brands);

        List<Category> categories = Arrays.stream(categoryNames)
                .map(categoryName -> Category.builder().categoryName(categoryName).build()).collect(Collectors.toList());
        categoryRepository.saveAll(categories);
    }
}
