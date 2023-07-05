package com.shopwell.api.services.implementations;

import com.shopwell.api.model.VOs.request.ProductRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.ProductResponseVO;
import com.shopwell.api.model.entity.Product;
import com.shopwell.api.model.entity.ProductImage;
import com.shopwell.api.repository.ProductRepository;
import com.shopwell.api.services.BrandService;
import com.shopwell.api.services.CategoryService;
import com.shopwell.api.services.image.ProductImageService;
import com.shopwell.api.utils.MapperUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {


    @Mock
    private ProductRepository productRepository;

    @Mock
    BrandService brandService;

    @Mock
    CategoryService categoryService;

    @Mock
    ProductImageService productImageService;

    @Mock
    private MapperUtils mapperUtils;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository, brandService, categoryService, productImageService, mapperUtils);
    }

    @Test
    public void testSaveProduct() {
        ProductRegistrationVO productRegistrationVO = createMockProductRegistrationVO();
        List<MultipartFile> imageFiles = createMockImageFiles();
        productRegistrationVO.setImageFiles(imageFiles);

        Product product = createMockProduct();
        Product savedProduct = createMockProduct();
        savedProduct.setProductImageURLs(createMockProductImageList());

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(savedProduct);

        when(mapperUtils.ProductRegistrationVOToProductEntity(Mockito.any(ProductRegistrationVO.class)))
                .thenReturn(product);
        when(mapperUtils.imageUrlsToProductImageEntity(Mockito.anyList()))
                .thenReturn(createMockProductImageList());

        when(productImageService.uploadImage(Mockito.anyLong(), Mockito.any(MultipartFile.class)))
                .thenReturn("image_url");

        ApiResponseVO<?> response = productService.saveProduct(productRegistrationVO);

        assertEquals("Product saved successfully", response.getMessage());
        assertEquals(productRegistrationVO, response.getPayload());

        verify(productRepository, Mockito.times(2)).save(Mockito.any(Product.class));
        verify(mapperUtils, Mockito.times(1))
                .ProductRegistrationVOToProductEntity(Mockito.any(ProductRegistrationVO.class));
        verify(mapperUtils, Mockito.times(1))
                .imageUrlsToProductImageEntity(Mockito.anyList());
    }

    @Test
    public void testGetProducts() {
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = createMockProductList();
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(productPage);

        ApiResponseVO<?> response = productService.getProducts(pageNumber, pageSize);

        Assertions.assertNotNull(response.getPayload());
        assertEquals(products.size(), ((Page<ProductResponseVO>) response.getPayload()).getContent().size());

        verify(productRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
    }

    @Test
    void editProduct() {
        ProductRegistrationVO productRegistrationVO = createMockProductRegistrationVO();
        List<MultipartFile> imageFiles = createMockImageFiles();
        productRegistrationVO.setImageFiles(imageFiles);

        Product product = createMockProduct();
        Product savedProduct = createMockProduct();
        savedProduct.setProductImageURLs(createMockProductImageList());

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(savedProduct);

        when(mapperUtils.ProductRegistrationVOToProductEntity(Mockito.any(ProductRegistrationVO.class)))
                .thenReturn(product);
        when(mapperUtils.imageUrlsToProductImageEntity(Mockito.anyList()))
                .thenReturn(createMockProductImageList());

        when(productImageService.uploadImage(Mockito.anyLong(), Mockito.any(MultipartFile.class)))
                .thenReturn("image_url");

        ApiResponseVO<?> response = productService.saveProduct(productRegistrationVO);

        assertEquals("Product saved successfully", response.getMessage());
        assertEquals(productRegistrationVO, response.getPayload());

        verify(productRepository, Mockito.times(2)).save(Mockito.any(Product.class));
        verify(mapperUtils, Mockito.times(1))
                .ProductRegistrationVOToProductEntity(Mockito.any(ProductRegistrationVO.class));
        verify(mapperUtils, Mockito.times(1))
                .imageUrlsToProductImageEntity(Mockito.anyList());
    }

    @Test
    @SneakyThrows
    void deleteProduct() {
        Long productId = 1L;
        Product product = createMockProduct();

        product.setProductImageURLs(createMockProductImageList());

        when(productRepository.findById(product.getProductNumber())).thenReturn(Optional.of(product));

        ApiResponseVO<?> response = productService.deleteProduct(productId);

        assertEquals("Product deleted!", response.getMessage());

        verify(productRepository, times(1)).deleteById(productId);
    }

    private List<MultipartFile> createMockImageFiles() {
        List<MultipartFile> imageFiles = new ArrayList<>();

        MockMultipartFile file1 = new MockMultipartFile("file", "image1.jpg", "image/jpeg", new byte[0]);
        MockMultipartFile file2 = new MockMultipartFile("file", "image2.jpg", "image/jpeg", new byte[0]);

        imageFiles.add(file1);
        imageFiles.add(file2);

        return imageFiles;
    }

    private Product createMockProduct() {
        return Product.builder()
                .productNumber(1L)
                .productName("Yam")
                .productPrice(200.88)
                .productDescription("Great food for you").build();
    }

    private List<ProductImage> createMockProductImageList() {
        Product product1 = createMockProduct();
        Product product2 = createMockProduct();
        product2.setProductNumber(2L);


        ProductImage productImage1 = ProductImage.builder()
                .product(product1)
                .imageUrl("image.jpg")
                .build();

        ProductImage productImage2 = ProductImage.builder()
                .product(product2)
                .imageUrl("image.png")
                .build();

        product1.setProductImageURLs(List.of(productImage1, productImage2));
        product2.setProductImageURLs(List.of(productImage1, productImage2));

        return List.of(productImage1, productImage2);
    }

    private List<Product> createMockProductList() {
        Product product1 = createMockProduct();

        Product product2 = createMockProduct();
        product2.setProductNumber(2L);

        return List.of(product1, product2);
    }

    public ProductRegistrationVO createMockProductRegistrationVO() {
        return ProductRegistrationVO.builder()
                .productName("rice")
                .productDescription("lorem Ipsum")
                .productPrice("20.34")
                .brandName("thai-rice")
                .categoryName("groceries")
                .quantityAvailable("20")
                .build();
    }
}