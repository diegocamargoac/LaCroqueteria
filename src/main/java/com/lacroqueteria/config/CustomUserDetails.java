package com.lacroqueteria.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lacroqueteria.model.UserModel;

public class CustomUserDetails implements UserDetails {

    private final UserModel user;

    public CustomUserDetails(UserModel user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getFullName() {
        return user.getName();
    }

    public String getRoleName() {
        return user.getRole().getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return java.util.Arrays.asList(
    		    (GrantedAuthority) () -> "ROLE_" + user.getRole().getRole()
    		);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
	
}
