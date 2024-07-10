package com.letitbee.diamondvaluationsystem.payload;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class DashboardAppointmentMonthlyData {
    private Map<Integer, Integer> data;
    private String month;

    public DashboardAppointmentMonthlyData(Map<Integer, Integer> data, String month) {
        this.data = data;
        this.month = month;
    }

    public DashboardAppointmentMonthlyData(int year, int month, int totalAppointments) {
        this.data = new HashMap<>();
        this.data.put(year, totalAppointments);
        this.month = Month.of(month).name();
    }

    public DashboardAppointmentMonthlyData() {
        this.data = new HashMap<>();
    }

    // getters and setters

    public Map<Integer, Integer> getData() {
        return data;
    }

    public void setData(Map<Integer, Integer> data) {
        this.data = data;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
