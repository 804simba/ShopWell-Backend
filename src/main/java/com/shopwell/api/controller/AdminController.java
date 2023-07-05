package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.AdminRegistrationRequest;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Admin")
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @Operation(
            summary = "This is an endpoint for registering the admin."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin registered",
                    content = { @Content(mediaType = "multipart/form-data")}),
            @ApiResponse(responseCode = "401", description = "Invalid / malformed request"),
            @ApiResponse(responseCode = "403", description = "Invalid / expired token"),
    })
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseVO<String> registerAdmin(@Validated @ModelAttribute AdminRegistrationRequest adminRegistrationRequest) {
        log.info("Registering admin: " + adminRegistrationRequest);
        return adminService.registerAdmin(adminRegistrationRequest);
    }
}
