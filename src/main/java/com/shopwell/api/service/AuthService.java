package com.shopwell.api.service;

import com.shopwell.api.model.VOs.request.CustomerLoginRequestVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.VOs.response.ResponseOTPVO;

public interface AuthService {
    CustomerResponseVO authenticate(CustomerLoginRequestVO customerLoginRequestVO);

}
