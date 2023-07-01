package com.shopwell.api.repository;

import com.shopwell.api.model.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken,Integer> {
    Optional<JwtToken> findByToken(String token);

}
