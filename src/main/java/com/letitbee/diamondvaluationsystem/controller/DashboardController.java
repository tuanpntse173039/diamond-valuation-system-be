package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/dashboard")
public class DashboardController {
     private DashboardService dashboardService;

     public DashboardController(DashboardService dashboardService) {
         this.dashboardService = dashboardService;
     }

     @GetMapping("/service-price-monthly-data")
     public ResponseEntity<List<DashboardMonthlyData>> getMonthlyData() {
         return ResponseEntity.ok(dashboardService.getPriceMonthlyData());
     }
     @GetMapping("/appointment-monthly-data")
        public ResponseEntity<List<DashboardAppointmentMonthlyData>> getAppointmentMonthlyData() {
            return ResponseEntity.ok(dashboardService.getAppointmentMonthlyData());
        }

     @GetMapping("/top-customer")
     public ResponseEntity<List<DashboardTopCus>> getTopCustomer() {
           return ResponseEntity.ok(dashboardService.getTopCustomer());
     }
     @GetMapping("/top-consultant")
        public ResponseEntity<List<DashboardTopStaff>> getTopConsultant() {
           return ResponseEntity.ok(dashboardService.getTopConsultant());
        }
     @GetMapping("/top-valuation")
        public ResponseEntity<List<DashboardTopStaff>> getTopValuation() {
           return ResponseEntity.ok(dashboardService.getTopValuation());
     }
     @GetMapping("/overall")
     public ResponseEntity<DashboardOverall> getOverall() {
           return ResponseEntity.ok(dashboardService.getOverall());
     }

}
