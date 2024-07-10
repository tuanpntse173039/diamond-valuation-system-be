package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StaffRegisterDTO {

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
    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 2, max = 24, message = "First name must be between 2 and 24 characters")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 2, max = 24, message = "Last name must be between 2 and 24 characters")
    private String lastName;
    @Size(min = 10, max = 10, message = "Invalid phone number")
    private String phone;
    @Min(value = 0, message = "Experience must be greater than or equal to 0")
    private int experience;
    private String certificateLink;
    private String avatar;
    private Role role;
}
