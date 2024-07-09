package com.letitbee.diamondvaluationsystem.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerUpdate {
    @Size(min = 2, max = 24, message = "First name must be between 2 and 24 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    private String firstName;
    @Size(min = 2, max = 24, message = "Last name must be between 2 and 24 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit")
    private String phone;
    private String address;
    private String avatar;
    @Size(min = 12, max = 12, message = "Invalid identity document")
    private String identityDocument;
    @Email(message = "Invalid email")
    private String newEmail;
}
