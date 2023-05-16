package com.qb.security.entity;

import com.qb.security.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // get user roles
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // get username
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // check the account timeline
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // check the account lockout
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // check the identity information timeline
        return true;
    }

    @Override
    public boolean isEnabled() {
        // check the account activity
        return true;
    }
}
