package org.playentropy.user;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserSecurityDetails implements UserDetails {

    private User user;

    public UserSecurityDetails(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public String getPassword() {
        return this.user.getPasswordHash();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
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
