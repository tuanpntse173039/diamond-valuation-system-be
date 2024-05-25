package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.StaffDTO;

public interface StaffService {
    Response getAllStaffs(int pageNo, int pageSize, String sortBy, String sortDir);

    StaffDTO getStaffById(Long id);

    StaffDTO createStaff(StaffDTO staffDto);

    StaffDTO updateStaff(StaffDTO staffDto, Long id);

    void deleteStaffById(Long id);
}
