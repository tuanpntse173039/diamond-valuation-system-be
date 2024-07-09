package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.*;

import java.util.List;

public interface DashboardService {
    List<DashboardMonthlyData> getPriceMonthlyData();
    List<DashboardAppointmentMonthlyData> getAppointmentMonthlyData();
    List<DashboardTopCus> getTopCustomer();
    List<DashboardTopStaff> getTopConsultant();
    List<DashboardTopStaff> getTopValuation();
    List<DashboardOverall> getOverall();
}
