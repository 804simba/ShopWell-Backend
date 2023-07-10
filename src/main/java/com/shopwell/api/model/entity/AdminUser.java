package com.shopwell.api.model.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
public class AdminUser extends BaseUser implements Serializable {
    public AdminUser() {
    }
}
