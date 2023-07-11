package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paystack_detail")
@EntityListeners(AuditingEntityListener.class)
public class PaymentDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Customer customer;

    @Column(name = "reference")
    private String reference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "gateway_response")
    private String gatewayResponse;

    @Column(name = "paid_at")
    private String paidAt;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "channel")
    private String channel;

    @Column(name = "currency")
    private String currency;

    @Column(name = "ip_address")
    private String ipAddress;
}
