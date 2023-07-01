package com.shopwell.api.model.entity;

import com.shopwell.api.model.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name= "jwt_token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;

    public boolean expired;

    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    public Customer customer;
    private Date generatedAt;
    private Date expiresAt;
    private Date refreshedAt;

}
