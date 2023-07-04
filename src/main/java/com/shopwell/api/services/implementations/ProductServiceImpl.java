package com.shopwell.api.services.implementations;

import com.shopwell.api.exceptions.ImageUploadException;
import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.VOs.request.BrandRegistrationVO;
import com.shopwell.api.model.VOs.request.CategoryRegistrationVO;
import com.shopwell.api.model.VOs.request.ProductRegistrationVO;
import com.shopwell.api.model.VOs.request.ProductSearchRequestVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.ProductResponseVO;
import com.shopwell.api.model.VOs.response.ProductSearchResponseVO;
import com.shopwell.api.model.entity.Brand;
import com.shopwell.api.model.entity.Category;
import com.shopwell.api.model.entity.Product;
import com.shopwell.api.model.entity.ProductImage;
import com.shopwell.api.repository.ProductRepository;
import com.shopwell.api.services.BrandService;
import com.shopwell.api.services.CategoryService;
import com.shopwell.api.services.ProductService;
import com.shopwell.api.services.image.ProductImageService;
import com.shopwell.api.utils.MapperUtils;
import com.shopwell.api.utils.search.ProductSpecificationBuilder;
import com.shopwell.api.utils.search.SearchCriteria;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final BrandService brandService;

    private final CategoryService categoryService;

    private final ProductImageService productImageService;

    private final MapperUtils mapperUtils;

    @Override
    @Transactional
    public ApiResponseVO<?> saveProduct(ProductRegistrationVO productRegistrationVO) {
        try {
            Product product = mapperUtils.mapProductRegistrationVOToProduct(productRegistrationVO);

            List<MultipartFile> imageFiles = productRegistrationVO.getImageFiles();
            var savedProduct = productRepository.save(product);

            List<String> imageURLs = saveProductImages(savedProduct, imageFiles);
            savedProduct.setProductImageURLs(mapperUtils.imageUrlsToProductImageEntity(imageURLs));
            productRepository.save(savedProduct);

            return ApiResponseVO.builder()
                    .message("Product saved successfully")
                    .payload(productRegistrationVO)
                    .build();

        } catch (Exception e) {
            return ApiResponseVO.builder()
                    .message(String.format("Product was not saved:: %s", e.getMessage()))
                    .build();
        }
    }

    @Override
    public ApiResponseVO<?> getProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponseVO> products = productPage.getContent().stream()
                .map(mapperUtils::productEntityToProductVO).collect(Collectors.toList());

        return ApiResponseVO.builder()
                .payload(new PageImpl<>(products, pageable, productPage.getTotalElements()))
                .build();
    }

    @Override
    @Transactional
    public ApiResponseVO<?> editProduct(Long id, ProductRegistrationVO productRegistrationVO) {
        try {
            Product foundProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            foundProduct.setProductName(productRegistrationVO.getProductName());
            foundProduct.setProductDescription(productRegistrationVO.getProductDescription());
            foundProduct.setProductPrice(Double.parseDouble(productRegistrationVO.getProductPrice()));
            foundProduct.setQuantityAvailable(Integer.parseInt(productRegistrationVO.getQuantityAvailable()));

            Brand brand = brandService.findProductByBrandName(productRegistrationVO.getBrandName());

            if (brand == null) {
                Brand newBrand = Brand.builder()
                        .brandName(productRegistrationVO.getBrandName())
                        .build();
                brand = brandService.registerBrand(new BrandRegistrationVO(newBrand.getBrandName()));
            }

            foundProduct.setBrand(brand);

            Category category = categoryService.findProductByCategory(productRegistrationVO.getCategoryName());

            if (category == null) {
                Category newCategory = Category.builder()
                        .categoryName(productRegistrationVO.getCategoryName())
                        .build();
                category = categoryService.registerCategory(new CategoryRegistrationVO(newCategory.getCategoryName()));
            }

            foundProduct.setCategory(category);

            var savedProduct = productRepository.save(foundProduct);

            return ApiResponseVO.builder()
                    .message("Product saved successfully")
                    .payload(mapperUtils.productEntityToProductVO(savedProduct))
                    .build();
        } catch (ProductNotFoundException e) {
            return ApiResponseVO.builder()
                    .message(e.getMessage())
                    .build();

        } catch (Exception e) {
            return ApiResponseVO.builder()
                    .message(String.format("Product update failed: %s", e.getMessage()))
                    .build();
        }
    }

    @Override
    public ApiResponseVO<?> getProduct(Long id) throws ProductNotFoundException {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ApiResponseVO.builder()
                .message("Product saved successfully")
                .payload(mapperUtils.productEntityToProductVO(foundProduct))
                .build();
    }

    @Override
    @Transactional
    public ApiResponseVO<?> deleteProduct(Long id) throws ProductNotFoundException {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        foundProduct.getProductImageURLs().forEach(productImage -> productImageService
                .deleteImage(productImage.getImageUrl()));

        productRepository.deleteById(id);
        return ApiResponseVO.builder()
                .message("Product deleted!")
                .build();
    }

    @Override
    public ApiResponseVO<ProductSearchResponseVO> searchProductsByCriteria(int pageNumber, int pageSize, ProductSearchRequestVO searchRequestVO) {

        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();

        List<SearchCriteria> criteriaList = searchRequestVO.getSearchCriteriaList();

        if (criteriaList != null) {
            criteriaList.forEach(criteria -> {
                criteria.setDataOption(searchRequestVO.getDataOption());
                builder.with(criteria);
            });
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by("productPrice").ascending()
                        .and(Sort.by("productName")).ascending()
                        .and(Sort.by("brand.brandName")).ascending()
                        .and(Sort.by("category.categoryName")).ascending());

        Page<Product> productPage = productRepository.findAll(builder.build(), pageable);

        List<ProductResponseVO> products = productPage.getContent().stream()
                .map(mapperUtils::productEntityToProductVO)
                .collect(Collectors.toList());

        ProductSearchResponseVO searchResponseVO = new ProductSearchResponseVO();
        searchResponseVO.setProducts(products);
        searchResponseVO.setPageSize(productPage.getSize());
        searchResponseVO.setPageNumber(productPage.getNumber());
        searchResponseVO.setTotalPages(productPage.getTotalPages());
        searchResponseVO.setTotalElements(productPage.getTotalElements());

        return new ApiResponseVO<>("Search results", searchResponseVO);
    }

    private List<String> saveProductImages(Product product, List<MultipartFile> imageFiles) {
        List<String> imageURLs = new ArrayList<>();

        if(imageFiles != null && !imageFiles.isEmpty()) {
            int maxNumberOfImages = Math.min(imageFiles.size(), 5);
            List<ProductImage> productImages = new ArrayList<>();

            for (int index = 0; index < maxNumberOfImages; index++) {
                MultipartFile imageFile = imageFiles.get(index);
                try {
                    String imageURL = productImageService.uploadImage(product.getProductNumber(), imageFile);
                    imageURLs.add(imageURL);
                    ProductImage productImage = ProductImage.builder()
                            .imageUrl(imageURL)
                            .product(product)
                            .build();
                    productImages.add(productImage);
                } catch (ImageUploadException e) {
                    throw new RuntimeException("Could not upload image");
                }
            }
            product.setProductImageURLs(productImages);
        }
        return imageURLs;
    }
}
