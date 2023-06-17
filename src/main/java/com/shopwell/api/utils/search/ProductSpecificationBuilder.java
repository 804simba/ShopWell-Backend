package com.shopwell.api.utils.search;

import com.shopwell.api.model.entity.Product;
import com.shopwell.api.model.enums.SearchOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductSpecificationBuilder {

    private final List<SearchCriteria> criteriaList;

    public ProductSpecificationBuilder() {
        this.criteriaList = new ArrayList<>();
    }

    public final ProductSpecificationBuilder with(SearchCriteria searchCriteria) {
        criteriaList.add(searchCriteria);
        return this;
    }

    public Specification<Product> build() {
        if (criteriaList.size() == 0) {
            return null;
        }

        Specification<Product> result = new ProductSpecification(criteriaList.get(0));

        for (int index = 1; index < criteriaList.size(); index++) {
            SearchCriteria searchCriteria = criteriaList.get(index);
            result = SearchOperation.getDataOption(searchCriteria.getDataOption()) == SearchOperation.ALL ?
                    Specification.where(result).and(new ProductSpecification(searchCriteria)) :
                    Specification.where(result).or(new ProductSpecification(searchCriteria));
        }
        return result;
    }
}
