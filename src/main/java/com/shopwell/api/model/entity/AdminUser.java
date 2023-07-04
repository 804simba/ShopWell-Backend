package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "admin_first_name")
    private String adminFirstName;

    @Column(name = "admin_last_name")
    private String adminLastName;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_password")
    private String adminPassword;

    @Temporal(TemporalType.DATE)
    @Column(name = "admin_dob")
    private Date adminDateOfBirth;

    @Column(name = "admin_phone_number")
    private String adminPhoneNumber;

    @Column(name = "admin_street_address")
    private String adminStreetAddress;

    @Column(name = "admin_status")
    private Boolean adminStatus;

    @Column(name = "admin_city")
    private String adminCity;

    @Column(name = "admin_image")
    private String adminImageURL;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.adminPassword;
    }

    @Override
    public String getUsername() {
        return this.adminEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
