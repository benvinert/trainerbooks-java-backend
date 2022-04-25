package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.UserDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GroupUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isActive;
    private List<GrantedAuthority> authorities;
    private Long id;

    public GroupUserDetails(UserDAO userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.isActive = userEntity.getIsActive();
        this.authorities = Arrays.stream(userEntity.getRoles().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        this.id = userEntity.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
