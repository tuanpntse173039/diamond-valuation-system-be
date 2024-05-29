package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Service;
import com.letitbee.diamondvaluationsystem.entity.ServicePriceList;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.ServicePriceListDTO;
import com.letitbee.diamondvaluationsystem.repository.ServicePriceListRepository;
import com.letitbee.diamondvaluationsystem.repository.ServiceRepository;
import com.letitbee.diamondvaluationsystem.service.ServicePriceListService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServicePriceListImpl implements ServicePriceListService {
    private ServicePriceListRepository servicePriceListRepository;
    private ServiceRepository  serviceRepository;
    private ModelMapper mapper;

    public ServicePriceListImpl(ServicePriceListRepository servicePriceListRepository, ServiceRepository serviceRepository, ModelMapper mapper) {
        this.servicePriceListRepository = servicePriceListRepository;
        this.serviceRepository = serviceRepository;
        this.mapper = mapper;
    }


    @Override
    public List<ServicePriceListDTO> getAllServicePriceList(long serviceId) {
        Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new ResourceNotFoundException("Service", "id", serviceId));

        List<ServicePriceList> servicePriceLists = servicePriceListRepository.findByServiceId(serviceId);
        return servicePriceLists.stream().map(servicePriceList -> mapToDto(servicePriceList)).collect(Collectors.toList());
    }

    @Override
    public ServicePriceListDTO getServicePriceListById(long serviceId, long id) {
        Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new ResourceNotFoundException("Service", "id", serviceId));

        ServicePriceList servicePriceList = servicePriceListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ServicePriceList", "id", id));

        if (servicePriceList.getService().getId() == service.getId()) {
            return mapToDto(servicePriceList);
        }
        else {
            throw new APIException(HttpStatus.BAD_REQUEST, "ServicePriceList with id " + id + " does not belong to Service with id " + serviceId);
        }

    }

    @Override
    public ServicePriceListDTO createServicePriceList(long serviceId,ServicePriceListDTO servicePriceListDto) {
        ServicePriceList servicePriceList = mapToEntity(servicePriceListDto);

        Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new ResourceNotFoundException("Service", "id", serviceId));

        servicePriceList.setService(service);

        return mapToDto(servicePriceListRepository.save(servicePriceList));
    }

    private ServicePriceListDTO mapToDto(ServicePriceList servicePriceList) {
        return mapper.map(servicePriceList, ServicePriceListDTO.class);
    }
    private ServicePriceList mapToEntity(ServicePriceListDTO servicePriceListDto) {
        return mapper.map(servicePriceListDto, ServicePriceList.class);
    }
}
