package com.shopwell.api.utils.search;

import com.shopwell.api.model.entity.Brand;
import com.shopwell.api.model.entity.Category;
import com.shopwell.api.model.entity.Product;
import com.shopwell.api.model.enums.SearchOperation;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(@NonNull Root<Product> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder cb) {

        String stringToSearch = searchCriteria.getValue().toString().toLowerCase();

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS -> {
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + stringToSearch + "%");
            }
            case DOES_NOT_CONTAIN -> {
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + stringToSearch + "%");
            }
            case BEGINS_WITH -> {
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), stringToSearch + "%");
            }
            case DOES_NOT_BEGIN_WITH -> {
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), stringToSearch + "%");
            }
            case ENDS_WITH -> {
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + stringToSearch);
            }
            case DOES_NOT_END_WITH -> {
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + stringToSearch);
            }
            case EQUAL -> {
                if (searchCriteria.getFilterKey().equals("productPrice")) {
                    BigDecimal productPrice = new BigDecimal(stringToSearch);
                    return cb.equal(root.get(searchCriteria.getFilterKey()), productPrice);
                } else if (searchCriteria.getFilterKey().equals("brandName")) {
                    Join<Product, Brand> brandJoin = root.join("brand");
                    return cb.equal(cb.lower(brandJoin.get("brandName")), stringToSearch);
                } else if (searchCriteria.getFilterKey().equals("categoryName")) {
                    Join<Product, Category> categoryJoin = root.join("category");
                    return cb.equal(cb.lower(categoryJoin.get("categoryName")), stringToSearch);
                }
                return cb.equal(cb.lower(root.get(searchCriteria.getFilterKey())), stringToSearch);
            }
            case NOT_EQUAL -> {
                if (searchCriteria.getFilterKey().equals("productPrice")) {
                    BigDecimal productPrice = new BigDecimal(stringToSearch);
                    return cb.notEqual(root.get(searchCriteria.getFilterKey()), productPrice);
                } else if (searchCriteria.getFilterKey().equals("brandName")) {
                    Join<Product, Brand> brandJoin = root.join("brand");
                    return cb.notEqual(cb.lower(brandJoin.get("brandName")), stringToSearch);
                } else if (searchCriteria.getFilterKey().equals("categoryName")) {
                    Join<Product, Category> categoryJoin = root.join("category");
                    return cb.notEqual(cb.lower(categoryJoin.get("categoryName")), stringToSearch);
                }
                return cb.notEqual(cb.lower(root.get(searchCriteria.getFilterKey())), stringToSearch);
            }
        }
        return null;
    }
}
