package com.shopwell.api.utils.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCriteria {

    private String filterKey;

    private Object value;

    private String operation;

    private String dataOption;
}
