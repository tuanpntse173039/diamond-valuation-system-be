package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.service.StaffService;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestService;
import com.letitbee.diamondvaluationsystem.utils.AppConstraint;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/staffs")
public class StaffController {
    private StaffService staffService;
    private ValuationRequestService valuationRequestService;

    public StaffController(StaffService staffService, ValuationRequestService valuationRequestService) {
        this.staffService = staffService;
        this.valuationRequestService = valuationRequestService;
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CONSULTANT_STAFF', 'VALUATION_STAFF', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Response<StaffDTO>> getAllStaffs
            (@RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
             @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE,required = false) int pageSize,
             @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
             @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir,
             @RequestParam(name = "role", required = false) Role role) {
        return new ResponseEntity<>(staffService.getAllStaffs(pageNo, pageSize, sortBy, sortDir, role), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StaffDTO>> getStaffByIdOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(
            @RequestParam(value = "name", required = false) String name){
        return ResponseEntity.ok(staffService.getStaffByName(name));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CONSULTANT_STAFF', 'VALUATION_STAFF', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaffInformation(@RequestBody @Valid StaffDTO staffDto, @PathVariable("id") long id){
        StaffDTO response = staffService.updateStaffInformation(staffDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaffById(@PathVariable("id") long id) {
        staffService.deleteStaffById(id);
        return new ResponseEntity<>("Staff deleted successfully", HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('MANAGER' , 'CONSULTANT_STAFF', 'ADMIN')")
    @GetMapping("/{id}/valuation-requests")
    public ResponseEntity<Response<ValuationRequestResponseV2>> getValuationRequestResponseByStaff(
            @RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir,
            @RequestParam(name = "status", required = false) RequestStatus status,
            @RequestParam(name = "startDate", defaultValue = AppConstraint.START_DATE, required = false) String startDate,
            @RequestParam(name = "endDate", defaultValue = AppConstraint.END_DATE, required = false) String endDate,
            @RequestParam(name = "search", required = false) String searchValue,
            @PathVariable("id") Long staffId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate startDateLD = LocalDate.parse(startDate, formatter);
        LocalDate endDateLD = LocalDate.parse(endDate, formatter);

        LocalDateTime startOfDay = startDateLD.atStartOfDay();
        LocalDateTime endOfDay = endDateLD.atTime(23, 59, 59);

        Date start = java.sql.Timestamp.valueOf(startOfDay);
        Date end = java.sql.Timestamp.valueOf(endOfDay);
        return new ResponseEntity<>(valuationRequestService.
                getValuationRequestResponseByStaff(pageNo, pageSize, sortBy, sortDir,staffId,status, start, end, searchValue), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }
    @PreAuthorize("hasAnyAuthority('MANAGER', 'VALUATION_STAFF', 'ADMIN')")
    @GetMapping("/{id}/diamond-assigns")
    public ResponseEntity<Response<DiamondValuationAssignResponse>> getValuationRequestDetailsByStaffId(@RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
                                                                                                   @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE,required = false) int pageSize,
                                                                                                   @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
                                                                                                   @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir,
                                                                                                   @PathVariable("id") Long id) {
        return new ResponseEntity<>(staffService.getAllValuationRequestsByStaffId(id, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


}
