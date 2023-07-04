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

    public Product mapProductRegistrationVOToProduct(ProductRegistrationVO productRegistrationVO) {
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

    public Customer customerVOToCustomerEntity(CustomerRegistrationVO customerRegistrationVO) {
        RoleEntity roleEntity = roleRepository.findByRoleName("user");

        return Customer.builder()
                .customerFirstName(customerRegistrationVO.getCustomerFirstName())
                .customerLastName(customerRegistrationVO.getCustomerLastName())
                .customerEmail(customerRegistrationVO.getCustomerEmail())
                .customerPhoneNumber(customerRegistrationVO.getCustomerPhoneNumber())
                .customerStatus(false)
                .role(roleEntity)
                .customerCity(customerRegistrationVO.getCustomerCity())
                .customerDateOfBirth(DateUtils.getDate(customerRegistrationVO.getCustomerDateOfBirth()))
                .customerStreetAddress(customerRegistrationVO.getCustomerStreetAddress())
                .build();
    }

    public AdminUser adminVOToAdminEntity(AdminRegistrationRequest adminRegistrationRequest) {
        RoleEntity roleEntity = roleRepository.findByRoleName("admin");

        return AdminUser.builder()
                .adminFirstName(adminRegistrationRequest.getAdminFirstName())
                .adminLastName(adminRegistrationRequest.getAdminLastName())
                .adminEmail(adminRegistrationRequest.getAdminEmail())
                .adminPhoneNumber(adminRegistrationRequest.getAdminPhoneNumber())
                .adminStatus(false)
                .role(roleEntity)
                .adminCity(adminRegistrationRequest.getAdminCity())
                .adminDateOfBirth(DateUtils.getDate(adminRegistrationRequest.getAdminDateOfBirth()))
                .adminStreetAddress(adminRegistrationRequest.getAdminStreetAddress())
                .build();
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
                .firstName(customer.getCustomerFirstName())
                .lastName(customer.getCustomerLastName())
                .emailAddress(customer.getCustomerEmail())
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
