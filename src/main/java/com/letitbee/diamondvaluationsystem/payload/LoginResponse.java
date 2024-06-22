package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.Role;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long customerOrStaffId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Role role;
    private JwtAuthResponse userToken;
}
