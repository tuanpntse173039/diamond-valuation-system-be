package com.letitbee.diamondvaluationsystem.payload;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class DashboardMonthlyData {
    private Map<Integer, Double> data;
    private String month;

    public DashboardMonthlyData(Map<Integer, Double> data, String month) {
        this.data = data;
        this.month = month;
    }

    public DashboardMonthlyData(int year, int month, double totalValue) {
        this.data = new HashMap<>();
        this.data.put(year, totalValue);
        this.month = Month.of(month).name();
    }

    public DashboardMonthlyData() {
        this.data = new HashMap<>();
    }

    // getters and setters

    public Map<Integer, Double> getData() {
        return data;
    }

    public void setData(Map<Integer, Double> data) {
        this.data = data;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
