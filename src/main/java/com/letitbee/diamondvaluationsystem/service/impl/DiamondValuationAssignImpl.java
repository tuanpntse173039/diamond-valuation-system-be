package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationAssignDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondValuationAssignRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondValuationAssignService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DiamondValuationAssignImpl implements DiamondValuationAssignService {

    private ModelMapper mapper;
    private DiamondValuationAssignRepository diamondValuationAssignRepository;

    public DiamondValuationAssignImpl(ModelMapper mapper, DiamondValuationAssignRepository diamondValuationAssignRepository) {
        this.mapper = mapper;
        this.diamondValuationAssignRepository = diamondValuationAssignRepository;
    }

    @Override
    public DiamondValuationAssignDTO createDiamondValuationAssign(DiamondValuationAssignDTO diamondValuationAssignDTO) {
        DiamondValuationAssign diamondValuationAssign = mapToEntity(diamondValuationAssignDTO);
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
        if (diamondValuationAssign.isStatus()) {
            diamondValuationAssign.setCreationDate((new Date()).toString());
        } // update date when status is true
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
