package com.example.diplom.dto;

import com.example.diplom.entity.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements UserDetails {

    private int userId;

    @NotEmpty
    @NonNull
    private String login;
    private String userPassward;
    @NotEmpty
    @NonNull
    private boolean active;

    private Set<UserRole> roles = new HashSet<>();


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    public String getPassword() {
        return userPassward;
    }

    public String getUsername() {
        return login;
    }



    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return active;
    }
}
