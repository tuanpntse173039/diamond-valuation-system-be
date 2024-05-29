package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondImage;
import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.DiamondImageDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationNoteDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondValuationNoteRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondValuationNoteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DiamondValuationNoteServiceImpl implements DiamondValuationNoteService {

    private ModelMapper mapper;
    private DiamondValuationNoteRepository diamondValuationNoteRepository;

    public DiamondValuationNoteServiceImpl(ModelMapper mapper, DiamondValuationNoteRepository diamondValuationNoteRepository) {
        this.mapper = mapper;
        this.diamondValuationNoteRepository = diamondValuationNoteRepository;
    }

    @Override
    public DiamondValuationNoteDTO updateDiamondValuationNote(long id,
                                                              DiamondValuationNoteDTO diamondValuationNoteDTO) {
        DiamondValuationNote diamondValuationNote = diamondValuationNoteRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diamond Valuation Note", "id", id));
        diamondValuationNote.setDiamondOrigin(diamondValuationNoteDTO.getDiamondOrigin());
        diamondValuationNote.setClarity(diamondValuationNoteDTO.getClarity());
        diamondValuationNote.setCaratWeight(diamondValuationNoteDTO.getCaratWeight());
        diamondValuationNote.setCertificateId(diamondValuationNoteDTO.getCertificateId());
        diamondValuationNote.setClarityCharacteristic(diamondValuationNoteDTO.getClarityCharacteristic());
        diamondValuationNote.setColor(diamondValuationNoteDTO.getColor());
        diamondValuationNote.setCut(diamondValuationNoteDTO.getCut());
        diamondValuationNote.setFluorescence(diamondValuationNoteDTO.getFluorescence());
        diamondValuationNote.setPolish(diamondValuationNoteDTO.getPolish());
        diamondValuationNote.setProportions(diamondValuationNoteDTO.getProportions());
        diamondValuationNote.setShape(diamondValuationNoteDTO.getShape());
        diamondValuationNote.setSymmetry(diamondValuationNoteDTO.getSymmetry());
        diamondValuationNote.setStatus(diamondValuationNoteDTO.isStatus());

        diamondValuationNote = diamondValuationNoteRepository.save(diamondValuationNote);
        return mapToDTO(diamondValuationNote);
    }

    private DiamondValuationNote mapToEntity(DiamondValuationNoteDTO valuationNoteDTO) {
        return mapper.map(valuationNoteDTO, DiamondValuationNote.class);
    }

    private DiamondValuationNoteDTO mapToDTO(DiamondValuationNote valuationNote) {
        return mapper.map(valuationNote, DiamondValuationNoteDTO.class);
    }

}
