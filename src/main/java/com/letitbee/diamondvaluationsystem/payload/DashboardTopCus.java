package com.letitbee.diamondvaluationsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardTopCus {
    private String avatar;
    private String customerName;
    private int totalAppointments;
    private double money;
}
