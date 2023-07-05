package com.shopwell.api.services;

import com.shopwell.api.model.VOs.request.AdminRegistrationRequest;
import com.shopwell.api.model.VOs.response.ApiResponseVO;

public interface AdminService {
    ApiResponseVO<String> registerAdmin(AdminRegistrationRequest customerRegistrationVO);
}
