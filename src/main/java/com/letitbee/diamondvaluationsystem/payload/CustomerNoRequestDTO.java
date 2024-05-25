package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

@Data
public class CustomerNoRequestDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private String identityDocument;
}
