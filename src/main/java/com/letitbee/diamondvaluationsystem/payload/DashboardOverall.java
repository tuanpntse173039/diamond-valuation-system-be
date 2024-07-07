package com.letitbee.diamondvaluationsystem.payload;
import java.util.HashMap;
import java.util.Map;

public class DashboardOverall {
    private Map<String, String> revenue;
    private Map<String, String> user;
    private Map<String, String> appointment;
    private Map<String, String> valuation;

    public DashboardOverall() {
        this.revenue = new HashMap<>();
        this.user = new HashMap<>();
        this.appointment = new HashMap<>();
        this.valuation = new HashMap<>();
    }

    public Map<String, String> getRevenue() {
        return revenue;
    }

    public void setRevenue(Map<String, String> revenue) {
        this.revenue = revenue;
    }

    public Map<String, String> getUser() {
        return user;
    }

    public void setUser(Map<String, String> user) {
        this.user = user;
    }

    public Map<String, String> getAppointment() {
        return appointment;
    }

    public void setAppointment(Map<String, String> appointment) {
        this.appointment = appointment;
    }

    public Map<String, String> getValuation() {
        return valuation;
    }

    public void setValuation(Map<String, String> valuation) {
        this.valuation = valuation;
    }

}
