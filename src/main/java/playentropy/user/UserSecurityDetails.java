package org.playentropy.user;

import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.annotation.Transient;

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
        return new ArrayList<GrantedAuthority>();
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
