package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Set;

@Data
public class StaffNoValuationDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int experience;
    private String certificateLink;
    private RoleDTO roleDTO;

}
