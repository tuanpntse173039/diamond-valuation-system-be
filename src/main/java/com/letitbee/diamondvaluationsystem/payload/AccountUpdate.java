package com.letitbee.diamondvaluationsystem.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountUpdate {
    @Size(min = 6, max = 24, message = "Password must be between 6 and 24 characters")
    private String oldPassword;
    @Size(min = 6, max = 24, message = "Password must be between 6 and 24 characters")
    private String newPassword;
}
