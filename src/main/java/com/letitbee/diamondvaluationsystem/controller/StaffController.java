package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.StaffDTO;
import com.letitbee.diamondvaluationsystem.service.StaffService;
import com.letitbee.diamondvaluationsystem.utils.AppConstraint;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staffs")
public class StaffController {
    private StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<Response<StaffDTO>> getAllStaffs(@RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE,required = false) int pageSize,
                                                 @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
                                                 @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir) {
        return new ResponseEntity<>(staffService.getAllStaffs(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StaffDTO>> getStaffByIdOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(
            @RequestParam(value = "name", required = false) String name){
        return ResponseEntity.ok(staffService.getStaffByName(name));
    }

    @PostMapping
    @Valid
    public ResponseEntity<StaffDTO> createStaffInformation(@RequestBody StaffDTO staffDto){
        StaffDTO response = staffService.createStaffInformation(staffDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Valid
    public ResponseEntity<StaffDTO> updateStaffInformation(@RequestBody StaffDTO staffDto, @PathVariable("id") long id){
        StaffDTO response = staffService.updateStaffInformation(staffDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaffById(@PathVariable("id") long id) {
        staffService.deleteStaffById(id);
        return new ResponseEntity<>("Staff deleted successfully", HttpStatus.OK);
    }
}
