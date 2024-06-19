package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.*;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.repository.DiamondValuationAssignRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestRepository;
import com.letitbee.diamondvaluationsystem.service.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {
    private StaffRepository staffRepository;
    private AccountRepository accountRepository;
    private ValuationRequestRepository valuationRequestRepository;

    private DiamondValuationAssignRepository diamondValuationAssignRepository;
    private ModelMapper mapper;

    public StaffServiceImpl(StaffRepository staffRepository,
                            AccountRepository accountRepository,
                            ValuationRequestRepository valuationRequestRepository,
                            DiamondValuationAssignRepository diamondValuationAssignRepository,
                            ModelMapper mapper) {
        this.staffRepository = staffRepository;
        this.accountRepository = accountRepository;
        this.valuationRequestRepository = valuationRequestRepository;
        this.diamondValuationAssignRepository = diamondValuationAssignRepository;
        this.mapper = mapper;
    }

    @Override
    @Cacheable(value = "staffs")
    public Response<StaffDTO> getAllStaffs(int pageNo, int pageSize, String sortBy, String sortDir,Role role) {

        //create Pageable intance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Staff> staffs = staffRepository.findStaffByRole(role, pageable);
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
    @Cacheable(value = "staffs")
    public StaffDTO getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "Id", id + ""));
        return mapToDto(staff);
    }

    @Override
    public List<StaffDTO> getStaffByName(String name) {
        String[] keywords = name.trim().split("\\s+");


        List<Staff> staff = new ArrayList<>();
        for (String keyword : keywords) {
            staff = staffRepository.findStaffByFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(
                    "%" + keyword + "%", "%" + keyword + "%");
        }
        return staff.stream().map(staff1 -> mapToDto(staff1)).collect(Collectors.toList());
    }


    @Override
    public StaffDTO updateStaffInformation(StaffDTO staffDto, Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "Id", id + ""));
        staff.setFirstName(staffDto.getFirstName());
        staff.setLastName(staffDto.getLastName());
        staff.setPhone(staffDto.getPhone());
        staff.setExperience(staffDto.getExperience());
        staff.setCertificateLink(staffDto.getCertificateLink());

        return mapToDto(staffRepository.save(staff));
    }

    @Override
    public StaffDTO createStaffInformation(StaffDTO staffDto) {
        if (staffDto.getAccount().getRole().toString().equalsIgnoreCase(Role.CUSTOMER.toString()))
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid Role");
        Account account = mapper.map(staffDto.getAccount(), Account.class) ;
        Staff staff = new Staff();
        staff.setFirstName(staffDto.getFirstName());
        staff.setLastName(staffDto.getLastName());
        staff.setPhone(staffDto.getPhone());
        staff.setExperience(staffDto.getExperience());
        staff.setCertificateLink(staffDto.getCertificateLink());
        staff.setAccount(account);

        return mapToDto(staffRepository.save(staff));
    }


    @Override
    public void deleteStaffById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff", "id", id + ""));
        Account accountWithStaffID =  staff.getAccount();
        accountWithStaffID.setIs_active(false);
        accountRepository.save(accountWithStaffID);
    }

    @Override
    @Cacheable(value = "staffs")
    public Response<DiamondValuationAssignResponse> getAllValuationRequestsByStaffId(Long staffId, int pageNo, int pageSize, String sortBy, String sortDir) {

        //create Pageable intance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff", "Id", staffId + ""));
        Page<DiamondValuationAssign> diamondValuationAssigns = staffRepository.findAllByValuationStaff(staff, pageable);

        List<DiamondValuationAssign> listOfStaff = diamondValuationAssigns.getContent();
        List<DiamondValuationAssignResponse> content =
                listOfStaff.stream().map(
                        diamondValuationAssign -> mapToDiamondValuationAssignResponse(diamondValuationAssign)).collect(Collectors.toList());

        Response<DiamondValuationAssignResponse> staffResponse = new Response<>();

        staffResponse.setContent(content);
        staffResponse.setPageNumber(diamondValuationAssigns.getNumber());
        staffResponse.setPageSize(diamondValuationAssigns.getSize());
        staffResponse.setTotalElement(diamondValuationAssigns.getTotalElements());
        staffResponse.setTotalPage(diamondValuationAssigns.getTotalPages());
        staffResponse.setLast(diamondValuationAssigns.isLast());

        return staffResponse;

    }

    //convert Entity to DTO
    private StaffDTO mapToDto(Staff staff){
        StaffDTO staffDto = mapper.map(staff, StaffDTO.class);
        int countProject = 0;
        int countCurrentProject = 0;
        if(staff.getAccount().getRole().toString().equalsIgnoreCase(Role.CONSULTANT_STAFF.toString())) {
            countProject = valuationRequestRepository.countValuationRequestsByStaff(staff);
            countCurrentProject = valuationRequestRepository.countValuationRequestsIsProcessedByStaff(staff);
        } else if(staff.getAccount().getRole().toString().equalsIgnoreCase(Role.VALUATION_STAFF.toString())) {
            countProject = diamondValuationAssignRepository.countDiamondValuationAssignByStaff(staff);
            countCurrentProject = diamondValuationAssignRepository.countDiamondValuationAssignIsProcessedByStaff(staff);
        }
        staffDto.setCountProject(countProject);
        staffDto.setCurrentTotalProject(countCurrentProject);

        return staffDto;
    }

    private DiamondValuationAssignResponse mapToDiamondValuationAssignResponse(DiamondValuationAssign diamondValuationAssign){
        DiamondValuationAssignResponse diamondValuationAssignResponse = new DiamondValuationAssignResponse();
        ValuationRequest valuationRequest = valuationRequestRepository.findValuationRequestByValuationRequestDetails(diamondValuationAssign.getValuationRequestDetail());
        diamondValuationAssignResponse.setId(diamondValuationAssign.getId());
        diamondValuationAssignResponse.setCertificateId(diamondValuationAssign.getValuationRequestDetail().getDiamondValuationNote().getCertificateId());
        diamondValuationAssignResponse.setCaratWeight(diamondValuationAssign.getValuationRequestDetail().getDiamondValuationNote().getCaratWeight());
        diamondValuationAssignResponse.setDiamondOrigin(diamondValuationAssign.getValuationRequestDetail().getDiamondValuationNote().getDiamondOrigin());
        diamondValuationAssignResponse.setStaffName(diamondValuationAssign.getStaff().getFirstName() + " " + diamondValuationAssign.getStaff().getLastName());
        diamondValuationAssignResponse.setDeadline(valuationRequest.getReturnDate());
        diamondValuationAssignResponse.setServiceName(valuationRequest.getService().getServiceName());
        diamondValuationAssignResponse.setStatus(diamondValuationAssign.getValuationRequestDetail().getStatus());
        diamondValuationAssignResponse.setValuationPrice(diamondValuationAssign.getValuationPrice());
        return diamondValuationAssignResponse;
    }
    //convert DTO to Entity
    private Staff mapToEntity(StaffDTO staffDto){
        Staff staff = mapper.map(staffDto, Staff.class);
        return staff;
    }
}
