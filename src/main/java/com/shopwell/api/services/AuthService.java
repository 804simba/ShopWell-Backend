package com.shopwell.api.services;

import com.shopwell.api.model.VOs.request.LoginRequestVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;

public interface AuthService {
    CustomerResponseVO authenticate(LoginRequestVO loginRequestVO);

}
