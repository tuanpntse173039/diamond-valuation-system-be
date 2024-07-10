package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class AccountDTO implements UserDetails {
    private Long id;
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 6, max = 24, message = "Username must be between 6 and 24 characters")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 24, message = "Password must be between 6 and 24 characters")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit")
    private String password;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    private String email;
    private Boolean is_active;
    private Role role;
    private String usernameOrEmail;
    private String verificationCode;
    private Date creationDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
