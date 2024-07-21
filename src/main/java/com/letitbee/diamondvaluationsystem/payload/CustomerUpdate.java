package com.letitbee.diamondvaluationsystem.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerUpdate {
    @Size(min = 2, max = 24, message = "First name must be between 2 and 24 characters")
    private String firstName;
    @Size(min = 2, max = 24, message = "Last name must be between 2 and 24 characters")
    private String lastName;
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    private String phone;
    private String address;
    private String avatar;
    @Size(min = 12, max = 12, message = "Invalid identity document")
    private String identityDocument;
    @Email(message = "Invalid email")
    private String newEmail;
}
