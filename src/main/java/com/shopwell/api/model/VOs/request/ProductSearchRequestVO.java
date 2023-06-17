package com.shopwell.api.model.VOs.request;

import com.shopwell.api.utils.search.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchRequestVO {

    private List<SearchCriteria> searchCriteriaList;

    private String dataOption;
}
