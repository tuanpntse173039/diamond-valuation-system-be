package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Record;
import com.letitbee.diamondvaluationsystem.enums.RecordType;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.RecordDTO;
import com.letitbee.diamondvaluationsystem.repository.RecordRepository;
import com.letitbee.diamondvaluationsystem.service.RecordService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;
    private ModelMapper mapper;

    public RecordServiceImpl(RecordRepository recordRepository, ModelMapper mapper) {
        this.recordRepository = recordRepository;
        this.mapper = mapper;
    }

    @Override
    public RecordDTO createRecord(RecordDTO recordDTO) {
        recordDTO.setCreationDate(new Date());
        return mapToDTO(recordRepository.save(mapToEntity(recordDTO)));
    }

    @Override
    public RecordDTO updateRecord(Long id ,RecordDTO recordDTO) {
        Record record = recordRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Record", "id", id.toString()));
        record.setLink(recordDTO.getLink());
        record.setStatus(recordDTO.getStatus());
        record.setType(recordDTO.getType());
        record.setCreationDate(recordDTO.getCreationDate());
        recordRepository.save(record);
        return mapToDTO(record);
    }

    @Override
    public RecordDTO getRecord(Long id) {
        return null;
    }

    @Override
    public void deleteRecord(Long id) {

    }

    @Override
    public List<RecordDTO> getRecordsByRequestId(Long requestId) {
        List<Record> records = recordRepository.findAllByValuationRequestId(requestId).orElseThrow(()
                -> new ResourceNotFoundException("Record", "valuationRequestId", requestId.toString()));
        return records.stream().map(this::mapToDTO).toList();
    }

    @Override
    public RecordDTO getRecordByRequestIdAndType(Long requestId, RecordType type) {
        Record record = recordRepository.findByValuationRequestIdAndType(requestId, type.toString()).orElseThrow(()
                -> new ResourceNotFoundException("Record", "valuationRequestId", requestId.toString()));
        return mapToDTO(record);
    }

    private RecordDTO mapToDTO(Record record) {
        return mapper.map(record, RecordDTO.class);
    }

    private Record mapToEntity(RecordDTO recordDTO) {
        return mapper.map(recordDTO, Record.class);
    }
}
