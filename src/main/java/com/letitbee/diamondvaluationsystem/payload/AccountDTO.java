package com.letitbee.diamondvaluationsystem.payload;

import com.letitbee.diamondvaluationsystem.enums.Role;
import lombok.Data;

@Data
public class AccountDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private Boolean is_active;
    private Role role;
}
