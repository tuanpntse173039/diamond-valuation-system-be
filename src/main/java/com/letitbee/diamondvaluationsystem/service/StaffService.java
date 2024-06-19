package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.*;

import java.util.List;

public interface StaffService {
    Response<StaffDTO> getAllStaffs(int pageNo, int pageSize, String sortBy, String sortDir, Role role);

    StaffDTO getStaffById(Long id);

    List<StaffDTO> getStaffByName(String name);

    StaffDTO updateStaffInformation(StaffDTO staffDto, Long id);

    StaffDTO createStaffInformation(StaffDTO staffDto);

    void deleteStaffById(Long id);

    Response<DiamondValuationAssignResponse> getAllValuationRequestsByStaffId(Long staffId, int pageNo, int pageSize, String sortBy, String sortDir);
}
