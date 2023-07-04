package com.shopwell.api.service;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.VOs.request.ProductRegistrationVO;
import com.shopwell.api.model.VOs.request.ProductSearchRequestVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.ProductSearchResponseVO;

public interface ProductService {

    ApiResponseVO<?> saveProduct(ProductRegistrationVO productRegistrationVO);

    ApiResponseVO<?> editProduct(Long id, ProductRegistrationVO productRegistrationVO) throws ProductNotFoundException;

    ApiResponseVO<?> getProduct(Long id) throws ProductNotFoundException;

    ApiResponseVO<ProductSearchResponseVO> searchProductsByCriteria(int pageNumber, int pageSize, ProductSearchRequestVO productSearchRequestVO);

    ApiResponseVO<?> deleteProduct(Long id) throws ProductNotFoundException;

    ApiResponseVO<?> getProducts(int pageNumber, int pageSize);
}
