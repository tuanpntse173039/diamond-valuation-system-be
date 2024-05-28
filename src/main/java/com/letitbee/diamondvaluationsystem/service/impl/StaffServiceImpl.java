package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.PostDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.StaffDTO;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.service.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {
    private StaffRepository staffRepository;
    private AccountRepository accountRepository;
    private ModelMapper mapper;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository, ModelMapper modelMapper) {
        this.staffRepository = staffRepository;
        this.mapper = modelMapper;
    }

    @Override
    public Response getAllStaffs(int pageNo, int pageSize, String sortBy, String sortDir) {

        //create Pageable intance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Staff> staffs = staffRepository.findAll(pageable);
        //get content for page obj
        List<Staff> listOfStaff = staffs.getContent();
        List<StaffDTO> content =  listOfStaff.stream().map(staff -> mapToDto(staff)).collect(Collectors.toList());

        Response<StaffDTO> staffResponse = new Response<>();

        staffResponse.setContent(content);
        staffResponse.setPageNumber(staffs.getNumber());
        staffResponse.setPageSize(staffs.getSize());
        staffResponse.setTotalElement(staffs.getTotalElements());
        staffResponse.setTotalPage(staffs.getTotalPages());
        staffResponse.setLast(staffs.isLast());

        return staffResponse;
    }

    @Override
    public StaffDTO getStaffById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff", "id", id));
        return mapToDto(staff);
    }

    @Override
    public StaffDTO updateStaff(StaffDTO staffDto, Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff", "id", id));
        return null;
    }

    @Override
    public void deleteStaffById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff", "id", id));
        Account accountWithStaffID =  staff.getAccount();
        accountWithStaffID.setIs_active(false);
        accountRepository.save(accountWithStaffID);
    }

    //convert Entity to DTO
    private StaffDTO mapToDto(Staff staff){
        StaffDTO staffDto = mapper.map(staff, StaffDTO.class);
        return staffDto;
    }
    //convert DTO to Entity
    private Staff mapToEntity(StaffDTO staffDto){
        Staff staff = mapper.map(staffDto, Staff.class);
        return staff;
    }
}
