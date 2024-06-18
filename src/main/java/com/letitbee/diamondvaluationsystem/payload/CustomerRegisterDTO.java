package com.letitbee.diamondvaluationsystem.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRegisterDTO {
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
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 2, max = 24, message = "Last name must be between 2 and 24 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain only digits")
    private String phone;
    private String address;
    private String avatar;
    @NotEmpty(message = "Identity document cannot be empty")
    @Size(min = 12, max = 12, message = "Invalid identity document")
    private String identityDocument;

}
