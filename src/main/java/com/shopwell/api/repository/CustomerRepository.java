package com.shopwell.api.repository;

import com.shopwell.api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE MONTH(c.dateOfBirth) = :monthValue AND DAY(c.dateOfBirth) = :dayOfMonth")
    List<Customer> findCustomersByDateOfBirth(@Param("monthValue") int monthValue, @Param("dayOfMonth") int dayOfMonth);

    List<Customer> findCustomersByEmailAndStatus(String email, boolean status);

    boolean existsByEmail(String email);
}
