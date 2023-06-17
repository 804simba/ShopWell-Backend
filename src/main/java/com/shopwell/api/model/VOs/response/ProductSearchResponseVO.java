package com.shopwell.api.model.VOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResponseVO {

    private Page<ProductResponseVO> products;
}
