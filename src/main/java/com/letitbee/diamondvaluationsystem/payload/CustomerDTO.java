package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

import java.util.Set;

@Data
public class CustomerDTO {
      private long id;
      private String firstName;
      private String lastName;
      private String email;
      private String phone;
      private String address;
      private String avatar;
      private String identityDocument;
      private Set<ValuationRequestDTO> valuationRequests;
      private AccountDTO accountDTO;
}
