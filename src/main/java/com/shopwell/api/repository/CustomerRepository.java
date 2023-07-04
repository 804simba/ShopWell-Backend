package com.shopwell.api.repository;

import com.shopwell.api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByCustomerEmail(String email);

    @Query("SELECT c FROM Customer c WHERE MONTH(c.customerDateOfBirth) = :monthValue AND DAY(c.customerDateOfBirth) = :dayOfMonth")
    List<Customer> findByCustomersDateOfBirth(int monthValue, int dayOfMonth);
}
