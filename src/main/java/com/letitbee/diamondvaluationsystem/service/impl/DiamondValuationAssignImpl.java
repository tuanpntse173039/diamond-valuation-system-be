package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import com.letitbee.diamondvaluationsystem.enums.RequestDetailStatus;
import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationAssignDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondValuationAssignRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestDetailRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondValuationAssignService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DiamondValuationAssignImpl implements DiamondValuationAssignService {

    private ModelMapper mapper;
    private DiamondValuationAssignRepository diamondValuationAssignRepository;
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    private StaffRepository staffRepository;

    public DiamondValuationAssignImpl(ModelMapper mapper,
                                      DiamondValuationAssignRepository diamondValuationAssignRepository,
                                      ValuationRequestDetailRepository valuationRequestDetailRepository,
                                      StaffRepository staffRepository) {
        this.mapper = mapper;
        this.diamondValuationAssignRepository = diamondValuationAssignRepository;
        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
        this.staffRepository = staffRepository;
    }
    @Override
    public DiamondValuationAssignDTO createDiamondValuationAssign(DiamondValuationAssignDTO diamondValuationAssignDTO) {
        DiamondValuationAssign diamondValuationAssign = new DiamondValuationAssign();
        Staff staff = staffRepository.findById(diamondValuationAssignDTO.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", diamondValuationAssignDTO.getStaffId()));
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                .findById(diamondValuationAssignDTO.getValuationRequestDetailId())
                .orElseThrow(() -> new ResourceNotFoundException("Valuation request detail", "id", diamondValuationAssignDTO.getValuationRequestDetailId()));

        diamondValuationAssign.setStaff(staff);
        diamondValuationAssign.setValuationRequestDetail(valuationRequestDetail);
        diamondValuationAssign.setValuationPrice(diamondValuationAssignDTO.getValuationPrice());
        diamondValuationAssign.setComment(diamondValuationAssignDTO.getComment());
        diamondValuationAssign.setStatus(diamondValuationAssignDTO.isStatus());
        if (diamondValuationAssign.isStatus()) {
            diamondValuationAssign.setCreationDate((new Date()));
        }
        //save to database
        diamondValuationAssign = diamondValuationAssignRepository.save(diamondValuationAssign);
        return mapToDTO(diamondValuationAssign);
    }

    @Override
    public DiamondValuationAssignDTO updateDiamondValuationAssign(long id, DiamondValuationAssignDTO diamondValuationAssignDTO) {
        DiamondValuationAssign diamondValuationAssign = diamondValuationAssignRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diamond valuation assign", "id", id));
        diamondValuationAssign.setValuationPrice(diamondValuationAssignDTO.getValuationPrice());
        diamondValuationAssign.setComment(diamondValuationAssignDTO.getComment());
        diamondValuationAssign.setStatus(diamondValuationAssignDTO.isStatus());
        diamondValuationAssign.setStaff(staffRepository.findById(diamondValuationAssignDTO.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", diamondValuationAssignDTO.getStaffId())));
        diamondValuationAssign.setValuationRequestDetailId(valuationRequestDetailRepository.findById(diamondValuationAssignDTO.getValuationRequestDetailId())
                .orElseThrow(() -> new ResourceNotFoundException("Valuation request detail", "id", diamondValuationAssignDTO.getValuationRequestDetailId())));
        if (diamondValuationAssign.isStatus()) {
            diamondValuationAssign.setCreationDate((new Date()));
        } // update date when status is true
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                .findById(diamondValuationAssignDTO.getValuationRequestDetailId())
                .orElseThrow(() -> new ResourceNotFoundException("Valuation request detail", "id", diamondValuationAssignDTO.getValuationRequestDetailId()));
        int flag = 0;
        if(valuationRequestDetail.getStatus().toString().equalsIgnoreCase(RequestDetailStatus.ASSESSED.toString())) {
            for (DiamondValuationAssign dva : valuationRequestDetail.getDiamondValuationAssigns()) {
                if (!dva.isStatus()) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                valuationRequestDetail.setStatus(RequestDetailStatus.VALUATED);
                valuationRequestDetailRepository.save(valuationRequestDetail);
            }
        }

        //save to database
        diamondValuationAssign = diamondValuationAssignRepository.save(diamondValuationAssign);
        return mapToDTO(diamondValuationAssign);
    }

    private DiamondValuationAssign mapToEntity(DiamondValuationAssignDTO diamondValuationAssignDTO) {
        return mapper.map(diamondValuationAssignDTO, DiamondValuationAssign.class);
    }

    private DiamondValuationAssignDTO mapToDTO(DiamondValuationAssign diamondValuationAssign) {
        return mapper.map(diamondValuationAssign, DiamondValuationAssignDTO.class);
    }

}
