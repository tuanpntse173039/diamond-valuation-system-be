package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.StaffDTO;

public interface StaffService {
    Response<StaffDTO> getAllStaffs(int pageNo, int pageSize, String sortBy, String sortDir);

    StaffDTO getStaffById(Long id);

    StaffDTO updateStaff(StaffDTO staffDto, Long id);

    void deleteStaffById(Long id);

    StaffDTO createStaffInformation(StaffDTO staffDto, Long id);
}
