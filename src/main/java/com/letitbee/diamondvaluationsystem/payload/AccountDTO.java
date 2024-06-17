package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 6, max = 24, message = "Username must be between 6 and 24 characters")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9]).{6,24}$", message = "Password must contain at least one numeric digit")
    @Size(min = 6, max = 24, message = "Password must be between 6 and 24 characters")
    private String password;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    private String email;
    private Boolean is_active;
    private Role role;
}
