package com.shopwell.api.utils;

import com.shopwell.api.model.VOs.request.*;
import com.shopwell.api.model.VOs.response.CartItemResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.VOs.response.OrderItemVO;
import com.shopwell.api.model.VOs.response.ProductResponseVO;
import com.shopwell.api.model.entity.*;
import com.shopwell.api.repository.RoleRepository;
import com.shopwell.api.services.BrandService;
import com.shopwell.api.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapperUtils {
    private final BrandService brandService;

    private final CategoryService categoryService;

    private final RoleRepository roleRepository;

    public Product ProductRegistrationVOToProductEntity(ProductRegistrationVO productRegistrationVO) {
        Brand brand = getOrCreateBrand(productRegistrationVO.getBrandName());

        Category category = getOrCreateCategory(productRegistrationVO.getCategoryName());

        return Product.builder()
                .productName(productRegistrationVO.getProductName())
                .productDescription(productRegistrationVO.getProductDescription())
                .productPrice(Double.parseDouble(productRegistrationVO.getProductPrice()))
                .brand(brand)
                .category(category)
                .quantityAvailable(Integer.parseInt(productRegistrationVO.getQuantityAvailable()))
                .build();
    }

    public ProductResponseVO productEntityToProductVO(Product product) {
        return ProductResponseVO.builder()
                .productId(product.getProductNumber())
                .productName(product.getProductName())
                .brandName(product.getBrand().getBrandName())
                .categoryName(product.getCategory().getCategoryName())
                .productDescription(product.getProductDescription())
                .productPrice(String.valueOf(product.getProductPrice()))
                .quantityAvailable(product.getQuantityAvailable())
                .build();
    }

    public Customer customerVOToCustomerEntity(RegistrationVO registrationVO) {
        RoleEntity roleEntity = roleRepository.findByRoleName("user");

        Customer customer = new Customer();
        customer.setFirstName(registrationVO.getFirstName());
        customer.setEmail(registrationVO.getEmail());
        customer.setPhoneNumber(registrationVO.getPhoneNumber());
        customer.setStatus(false);
        customer.setRole(roleEntity);
        registrationVO.setCity(registrationVO.getCity());
        registrationVO.setDateOfBirth(registrationVO.getDateOfBirth());
        registrationVO.setStreetAddress(registrationVO.getStreetAddress());

        return customer;
    }

    public AdminUser adminVOToAdminEntity(AdminRegistrationRequest adminRegistrationRequest) {
        RoleEntity roleEntity = roleRepository.findByRoleName("admin");

        AdminUser admin = new AdminUser();
        admin.setFirstName(adminRegistrationRequest.getFirstName());
        admin.setEmail(adminRegistrationRequest.getEmail());
        admin.setPhoneNumber(adminRegistrationRequest.getPhoneNumber());
        admin.setStatus(false);
        admin.setRole(roleEntity);
        adminRegistrationRequest.setCity(adminRegistrationRequest.getCity());
        adminRegistrationRequest.setDateOfBirth(adminRegistrationRequest.getDateOfBirth());
        adminRegistrationRequest.setStreetAddress(adminRegistrationRequest.getStreetAddress());

        return admin;
    }

    public List<ProductImage> imageUrlsToProductImageEntity(List<String> imageURLs) {
        return imageURLs.stream().map(url -> ProductImage.builder()
                .imageUrl(url)
                .build()).collect(Collectors.toList());
    }

    public OrderItem cartItemToOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuotedPrice(cartItem.getQuotedPrice());
        orderItem.setQuantityOrdered(cartItem.getQuantityOrdered());
        orderItem.setProduct(cartItem.getProduct());

        return orderItem;
    }

    public OrderItemVO orderItemToOrderItemVO(OrderItem orderItem) {
        return OrderItemVO.builder()
                .productId(orderItem.getProduct().getProductNumber())
                .productPrice(String.valueOf(orderItem.getQuotedPrice()))
                .quantityOrdered(String.valueOf(orderItem.getQuantityOrdered()))
                .build();
    }

    public CustomerResponseVO customerEntityToCustomerResponseVO(Customer customer) {
        return CustomerResponseVO.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .emailAddress(customer.getEmail())
                .build();
    }

    public CartItemResponseVO cartItemToCartItemResponseVO(CartItem cartItem) {
        List<ProductImage> productImages = cartItem.getProduct().getProductImageURLs();
        String productImageURL = "";

        if (!productImages.isEmpty()) {
            productImageURL = productImages.get(0).getImageUrl();
        }

        return CartItemResponseVO.builder()
                .productId(cartItem.getProduct().getProductNumber())
                .productName(cartItem.getProduct().getProductName())
                .quantity(cartItem.getQuantityOrdered())
                .productPrice(String.valueOf(cartItem.getProduct().getProductPrice()))
                .productImage(productImageURL)
                .build();
    }

    private Brand getOrCreateBrand(String brandName) {
        Brand brand = brandService.findProductByBrandName(brandName);
        if (brand == null) {
            BrandRegistrationVO newBrand = new BrandRegistrationVO(brandName);
            brand = brandService.registerBrand(newBrand);
        }
        return brand;
    }

    private Category getOrCreateCategory(String categoryName) {
        Category category = categoryService.findProductByCategory(categoryName);
        if (category == null) {
            CategoryRegistrationVO newCategory = new CategoryRegistrationVO(categoryName);
            category = categoryService.registerCategory(newCategory);
        }
        return category;
    }
}
