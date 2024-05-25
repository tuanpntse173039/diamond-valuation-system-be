package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.StaffDTO;

public interface StaffService {
    StaffDTO getAllStaffs(int pageNo, int pageSize,String sortBy,String sortDir);

    StaffDTO getStaffById(Long id);

    StaffDTO createStaff(StaffDTO staffDto);

    StaffDTO updateStaff(StaffDTO staffDto, Long id);
}
