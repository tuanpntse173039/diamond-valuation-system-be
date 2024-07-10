package com.letitbee.diamondvaluationsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardTopStaff {
    private String avatar;
    private String staffName;
    private String staffEmail;
    private String staffPhone;
    private int totalAppointments;
    private double totalServicePrice;
    private int totalValuation;
    private int valuationCount;

}
