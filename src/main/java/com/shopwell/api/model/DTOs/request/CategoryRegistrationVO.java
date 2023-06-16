package com.shopwell.api.model.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRegistrationVO {

    private String categoryName;

    private String categoryDescription;
}
