package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.ValuationNote;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationNoteDTO;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationNoteRepository;
import com.letitbee.diamondvaluationsystem.service.ValuationNoteService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValuationNoteServiceImpl implements ValuationNoteService {

    private ModelMapper mapper;
    private ValuationNoteRepository valuationNoteRepository;
    public ValuationNoteServiceImpl(ModelMapper mapper, ValuationNoteRepository valuationNoteRepository) {
        this.mapper = mapper;
        this.valuationNoteRepository = valuationNoteRepository;
    }

    @Override
    public Response<ValuationNoteDTO> getAllValuationNotes(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        //Set size page and pageNo
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ValuationNote> page = valuationNoteRepository.findAll(pageable);
        List<ValuationNote> valuationNotes = page.getContent();

        List<ValuationNoteDTO> listDTO = valuationNotes.
                stream().
                map((valuationNote) -> mapToDTO(valuationNote)).toList();

        Response<ValuationNoteDTO> response = new Response<>();

        response.setContent(listDTO);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPage(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLast(page.isLast());

        return response;

    }

    @Override
    public ValuationNoteDTO getValuationNoteById(Long id) {
        ValuationNote valuationNote = valuationNoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valuation note", "id", id));
        return mapToDTO(valuationNote);
    }

    @Override
    public ValuationNoteDTO createValuationNote(ValuationNoteDTO valuationNoteDto) {
        return null;
    }

    @Override
    public void deleteValuationNoteById(Long id) {

    }

    private ValuationNote mapToEntity(ValuationNoteDTO valuationNoteDTO) {
        return mapper.map(valuationNoteDTO, ValuationNote.class);
    }

    private ValuationNoteDTO mapToDTO(ValuationNote valuationNote) {
        return mapper.map(valuationNote, ValuationNoteDTO.class);}
}
