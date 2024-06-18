package com.letitbee.diamondvaluationsystem.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse<T> {
    private T userInformation;
    private JwtAuthResponse userToken;
}
