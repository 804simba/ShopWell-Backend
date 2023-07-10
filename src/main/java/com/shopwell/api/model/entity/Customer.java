package com.shopwell.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@Table(name = "customers")
public class Customer extends BaseUser implements Serializable {
        public Customer() {
        }
}
