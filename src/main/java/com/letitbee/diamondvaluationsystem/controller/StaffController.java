package com.letitbee.diamondvaluationsystem.controller;

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

    @PreAuthorize("hasAnyAuthority('MANAGER')")
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

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<StaffDTO> createStaffInformation(@RequestBody @Valid StaffDTO staffDto){
        StaffDTO response = staffService.createStaffInformation(staffDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CONSULTANT_STAFF', 'VALUATION_STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaffInformation(@RequestBody @Valid StaffDTO staffDto, @PathVariable("id") long id){
        StaffDTO response = staffService.updateStaffInformation(staffDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaffById(@PathVariable("id") long id) {
        staffService.deleteStaffById(id);
        return new ResponseEntity<>("Staff deleted successfully", HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('MANAGER' , 'CONSULTANT_STAFF')")
    @GetMapping("/{id}/valuation-requests")
    public ResponseEntity<Response<ValuationRequestResponseV2>> getValuationRequestResponseByStaff(
            @RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir,
            @PathVariable("id") Long staffId){
        return new ResponseEntity<>(valuationRequestService.
                getValuationRequestResponseByStaff(pageNo, pageSize, sortBy, sortDir,staffId), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }
    @PreAuthorize("hasAnyAuthority('MANAGER', 'VALUATION_STAFF')")
    @GetMapping("/{id}/diamond-assigns")
    public ResponseEntity<Response<DiamondValuationAssignDTO>> getValuationRequestDetailsByStaffId(@RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
                                                                                                   @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE,required = false) int pageSize,
                                                                                                   @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
                                                                                                   @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir,
                                                                                                   @PathVariable("id") Long id) {
        return new ResponseEntity<>(staffService.getAllValuationRequestsByStaffId(id, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


}
