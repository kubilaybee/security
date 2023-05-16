package com.qb.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // get user roles
        return null;
    }

    @Override
    public String getUsername() {
        // get username
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // check the account timeline
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // check the account lockout
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // check the identity information timeline
        return false;
    }

    @Override
    public boolean isEnabled() {
        // check the account activity
        return false;
    }
}
