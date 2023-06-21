package com.shopwell.api.model.VOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResponseVO {

    private List<ProductResponseVO> products;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;
}
