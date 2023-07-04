package com.shopwell.api.services;

import com.shopwell.api.model.VOs.request.CustomerLoginRequestVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;

public interface AuthService {
    CustomerResponseVO authenticate(CustomerLoginRequestVO customerLoginRequestVO);

}
