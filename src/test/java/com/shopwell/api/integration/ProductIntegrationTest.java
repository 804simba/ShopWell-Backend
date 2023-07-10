package com.shopwell.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopwell.api.model.VOs.request.ProductRegistrationVO;
import com.shopwell.api.model.entity.*;
import com.shopwell.api.repository.AdminRepository;
import com.shopwell.api.repository.ProductRepository;
import com.shopwell.api.repository.RoleRepository;
import com.shopwell.api.security.JwtService;
import com.shopwell.api.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Slf4j
public class ProductIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = createMockAdmin();
        AdminUser admin = (AdminUser) createMockAdmin();
        adminRepository.save(admin);
        Map<String, Object> claims = new HashMap<>();

        String token = generateToken(claims, userDetails);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        log.info(token);
    }

    @Test
    public void testSaveProduct() throws Exception {
        ProductRegistrationVO productRegistrationVO = createMockProductRegistrationVO();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(productRegistrationVO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken())
                        .content(requestJson))
                .andExpect(status().isCreated());

        List<Product> products = productRepository.findAll();
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getProductName());
    }

    @Test
    public void testEditProduct() throws Exception {
        ProductRegistrationVO registrationVO = createMockProductRegistrationVO();
        Product savedProduct = createMockProduct();

        productService.saveProduct(registrationVO);

        registrationVO.setProductName("Updated Product");
        registrationVO.setProductDescription("Updated Product Description");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(registrationVO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{productId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken())
                        .content(requestJson))
                .andExpect(status().isOk());

        Product updatedProduct = productRepository.findById(savedProduct.getProductNumber()).orElse(null);
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getProductName());
    }

    // TODO: Write similar tests for other methods such as deleteProduct, getProductById, getProducts, searchProducts, etc.

    public ProductRegistrationVO createMockProductRegistrationVO() {
        return ProductRegistrationVO.builder()
                .productName("Test Product")
                .productDescription("Test Description")
                .productPrice("10.99")
                .brandName("Test Brand")
                .categoryName("Test Category")
                .quantityAvailable("100")
                .imageFiles(createMockImageFiles())
                .build();
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
        Brand brand = Brand.builder()
                .brandName("testbrand")
                .build();
        Category category = Category.builder()
                .categoryName("testcategory")
                .build();

        return Product.builder()
                .productNumber(1L)
                .productName("Yam")
                .productPrice(200.88)
                .productDescription("Great food for you")
                .brand(brand)
                .category(category)
                .build();
    }

    private UserDetails createMockAdmin() {
        RoleEntity roleEntity = roleRepository.findByRoleName("admin");
        // Create a mock user for testing
        AdminUser admin = new AdminUser();
        admin.setId(1L);
        admin.setFirstName("test");
        admin.setLastName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("testpassword");
        admin.setRole(roleEntity);

        return admin;
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        extraClaims.put("roles", roles);
        return jwtService.generateToken(extraClaims, userDetails);
    }

    private String getToken() {
        Map<String, Object> claims = new HashMap<>();
        UserDetails userDetails = createMockAdmin();
        return generateToken(claims, userDetails);
    }
}
