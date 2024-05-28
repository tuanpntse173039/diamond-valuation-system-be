package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.StaffDTO;
import com.letitbee.diamondvaluationsystem.service.StaffService;
import com.letitbee.diamondvaluationsystem.utils.AppConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/staffs")
public class StaffController {
    private StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<Response> getAllStaffs(@RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE,required = false) int pageSize,
                                                 @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
                                                 @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir) {
        return new ResponseEntity<>(staffService.getAllStaffs(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{staffId}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable("staffId") long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @DeleteMapping("/{staffId}")
    public ResponseEntity<String> deleteStaffById(@PathVariable("staffId") long id) {
        staffService.deleteStaffById(id);
        return new ResponseEntity<>("Staff deleted successfully", HttpStatus.OK);
    }
}