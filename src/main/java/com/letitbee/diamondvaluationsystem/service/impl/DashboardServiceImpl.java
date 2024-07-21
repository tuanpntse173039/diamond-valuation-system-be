package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestDetailRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestRepository;
import com.letitbee.diamondvaluationsystem.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    private ValuationRequestRepository valuationRequestRepository;
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    private AccountRepository accountRepository;

    public DashboardServiceImpl(ValuationRequestRepository valuationRequestRepository,
                                 ValuationRequestDetailRepository valuationRequestDetailRepository,
                                 AccountRepository accountRepository) {
        this.valuationRequestRepository = valuationRequestRepository;
        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<DashboardMonthlyData> getPriceMonthlyData() {
        List<Object[]> results = valuationRequestRepository.findMonthlyTotalServicePriceByMonths();
        // Get all years from the database that have data
        Set<Integer> yearsWithData = results.stream()
                .map(result -> (Integer) result[0])
                .collect(Collectors.toSet());
        // Initialize map for monthly data
        Map<String, DashboardMonthlyData> monthlyDataMap = new HashMap<>();
        for (int month = 1; month <= 12; month++) {
            String monthName = Month.of(month).name();
            DashboardMonthlyData monthlyData = new DashboardMonthlyData(new HashMap<>(), monthName);
            // Ensure all months have entries for years with data
            for (int year : yearsWithData) {
                monthlyData.getData().put(year, 0.0);
            }
            monthlyDataMap.put(monthName, monthlyData);
        }
        // Populate data from query results
        for (Object[] result : results) {
            int year = (Integer) result[0];
            int month = (Integer) result[1];
            double totalValue = (Double) result[2];
            String monthName = Month.of(month).name();
            if (monthlyDataMap.containsKey(monthName)) {
                monthlyDataMap.get(monthName).getData().put(year, totalValue);
            }
        }
        // Convert map values to a list and sort by month
        List<DashboardMonthlyData> dashboardMonthlyDataList = monthlyDataMap.values().stream()
                .sorted(Comparator.comparingInt(data -> Month.valueOf(data.getMonth()).getValue()))
                .collect(Collectors.toList());

        return dashboardMonthlyDataList;
    }

    @Override
    public List<DashboardAppointmentMonthlyData> getAppointmentMonthlyData() {
        List<Object[]> results = valuationRequestRepository.findMonthlyAppointmentByMonths();
        Set<Integer> yearsWithData = results.stream()
                .map(result -> (Integer) result[0])
                .collect(Collectors.toSet());
        Map<String, DashboardAppointmentMonthlyData> monthlyDataMap = new HashMap<>();
        for (int month = 1; month <= 12; month++) {
            String monthName = Month.of(month).name();
            DashboardAppointmentMonthlyData monthlyData = new DashboardAppointmentMonthlyData(new HashMap<>(), monthName);
            for (int year : yearsWithData) {
                monthlyData.getData().put(year, 0);
            }
            monthlyDataMap.put(monthName, monthlyData);
        }
        for (Object[] result : results) {
            int year = (Integer) result[0];
            int month = (Integer) result[1];
            int totalAppointment = ((Number) result[2]).intValue();
            String monthName = Month.of(month).name();
            if (monthlyDataMap.containsKey(monthName)) {
                monthlyDataMap.get(monthName).getData().put(year, totalAppointment);
            }
        }
        List<DashboardAppointmentMonthlyData> dashboardMonthlyDataList = monthlyDataMap.values().stream()
                .sorted(Comparator.comparingInt(data -> Month.valueOf(data.getMonth()).getValue()))
                .collect(Collectors.toList());

        return dashboardMonthlyDataList;
    }

    @Override
    public List<DashboardTopCus> getTopCustomer() {
        int currentMonth = LocalDate.now().getMonth().getValue();
        List<Object[]> results = valuationRequestRepository.findTopCustomers(currentMonth);
        List<DashboardTopCus> topCustomers = new ArrayList<>();
        for (Object[] result : results) {
            String customerName = (String) result[0];
            String avatar = (String) result[1];
            int totalAppointments = ((Number) result[2]).intValue();
            double totalServicePrice = ((Number) result[3]).doubleValue();
            topCustomers.add(new DashboardTopCus(customerName, avatar, totalAppointments, totalServicePrice));
        }
        return topCustomers;
    }

    @Override
    public List<DashboardTopStaff> getTopConsultant() {
        int currentMonth = LocalDate.now().getMonth().getValue();
        List<Object[]> results = valuationRequestRepository.findTopConsultant(currentMonth);
        List<DashboardTopStaff> topConsultants = new ArrayList<>();
        for (Object[] result : results) {
            String avatar = (String) result[0];
            String staffName = (String) result[1];
            String email = (String) result[2];
            String phone = (String) result[3];
            int totalAppointments = ((Integer) result[4]);
            double totalServicePrice = ((Number) result[5]).doubleValue();
            int totalValuation = 0;
            int valuationCount = 0;
            topConsultants.add(new DashboardTopStaff(avatar, staffName, email,phone, totalAppointments, totalServicePrice, totalValuation, valuationCount));
        }
        return topConsultants;
    }

    @Override
    public List<DashboardTopStaff> getTopValuation() {
        List<Object[]> results = valuationRequestDetailRepository.findTopValuation();
        List<DashboardTopStaff> topValuations = new ArrayList<>();
        for (Object[] result : results) {
            String avatar = (String) result[0];
            String staffName = (String) result[1];
            String email = (String) result[2];
            String phone = (String) result[3];
            int totalValuation = ((Number) result[4]).intValue();
            int valuationCount = ((Number) result[5]).intValue();
            int  totalAppointments = 0;
            double  totalServicePrice  = 0;
            topValuations.add(new DashboardTopStaff(avatar, staffName, email,phone, totalAppointments, totalServicePrice, totalValuation, valuationCount));
        }
        return topValuations;
    }

    @Override
    public DashboardOverall getOverall() {
        int currentMonth = LocalDate.now().getMonth().getValue();
        List<Object[]> resultPriceService = valuationRequestRepository.findTotalServicePriceCurrentAndPreviousMonth();
        List<Object[]> resultAppointment = valuationRequestRepository.findTotalAppointmentCurrentAndPreviousMonth();
        List<Object[]> resultTopValuation = valuationRequestDetailRepository.findTotalDiamondValuationCurrentAndPreviousMonth();
        List<Object[]> resultNewCustomer = accountRepository.findNewCustomerAccountCurrentAndPreviousWeek();
        DashboardOverall dashboardOverall = new DashboardOverall();

        if (!resultPriceService.isEmpty()) {
            Object[] result = resultPriceService.get(0);
            double totalServicePriceCurrentMonth = ((Number) result[0]).doubleValue();
            double totalServicePricePreviousMonth = ((Number) result[1]).doubleValue();
            double percent = (( totalServicePriceCurrentMonth / totalServicePricePreviousMonth) - 1) * 100;
            if(totalServicePricePreviousMonth == 0){
                percent = 100;
            }
            dashboardOverall.getRevenue().put("total", String.valueOf(totalServicePriceCurrentMonth));
            dashboardOverall.getRevenue().put("percent", String.valueOf(percent));
            if (percent > 0) {
                dashboardOverall.getRevenue().put("status", "true");
            } else if (percent < 0) {
                dashboardOverall.getRevenue().put("status", "false");
            } else {
                dashboardOverall.getRevenue().put("status", "equal");
            }
        }

        if (!resultNewCustomer.isEmpty()) {
            Object[] result = resultNewCustomer.get(0);
            int currentWeekCount = ((Number) result[0]).intValue();
            int previousWeekCount = ((Number) result[1]).intValue();
            double percent = (((double) currentWeekCount / previousWeekCount) - 1) * 100;
            if (previousWeekCount == 0) {
                percent = 100;
            }
            dashboardOverall.getUser().put("total", String.valueOf(currentWeekCount));
            dashboardOverall.getUser().put("percent", String.valueOf(percent));
            if (percent > 0) {
                dashboardOverall.getUser().put("status", "true");
            } else if (percent < 0) {
                dashboardOverall.getUser().put("status", "false");
            } else {
                dashboardOverall.getUser().put("status", "equal");
            }
        }

        if (!resultAppointment.isEmpty()) {
            Object[] result = resultAppointment.get(0);
            int totalAppointmentCurrentMonth = ((Number) result[0]).intValue();
            int totalAppointmentPreviousMonth = ((Number) result[1]).intValue();
            double percent = (((double) totalAppointmentCurrentMonth / totalAppointmentPreviousMonth) - 1) * 100;
            if (totalAppointmentPreviousMonth == 0) {
                percent = 100;
            }
            dashboardOverall.getAppointment().put("total", String.valueOf(totalAppointmentCurrentMonth));
            dashboardOverall.getAppointment().put("percent", String.valueOf(percent));
            if (percent > 0) {
                dashboardOverall.getAppointment().put("status", "true");
            } else if (percent < 0) {
                dashboardOverall.getAppointment().put("status", "false");
            } else {
                dashboardOverall.getAppointment().put("status", "equal");
            }
        }

        if (!resultTopValuation.isEmpty()) {
            Object[] result = resultTopValuation.get(0);
            int totalValuationCurrentMonth = ((Number) result[0]).intValue();
            int totalValuationPreviousMonth = ((Number) result[1]).intValue();
            double percent = (((double) totalValuationCurrentMonth / totalValuationPreviousMonth) - 1) * 100;
            if (totalValuationPreviousMonth == 0) {
                percent = 100;
            }
            dashboardOverall.getValuation().put("total", String.valueOf(totalValuationCurrentMonth));
            dashboardOverall.getValuation().put("percent", String.valueOf(percent));
            if (percent > 0) {
                dashboardOverall.getValuation().put("status", "true");
            } else if (percent < 0) {
                dashboardOverall.getValuation().put("status", "false");
            } else {
                dashboardOverall.getValuation().put("status", "equal");
            }
        }


        return dashboardOverall;
    }

}
