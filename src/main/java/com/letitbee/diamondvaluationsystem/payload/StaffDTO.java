package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Set;

@Data
public class StaffDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int experience;
    private String certificateLink;
    private Set<ValuationRequestDTO> valuationRequests;
    private Set<DiamondValuationAssignDTO> diamondValuations;
    private AccountDTO accountDTO;
}
