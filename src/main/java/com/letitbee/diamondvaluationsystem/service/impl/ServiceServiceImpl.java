package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Service;
import com.letitbee.diamondvaluationsystem.entity.ServicePriceList;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ServiceDTO;
import com.letitbee.diamondvaluationsystem.payload.ServicePriceListDTO;
import com.letitbee.diamondvaluationsystem.repository.ServicePriceListRepository;
import com.letitbee.diamondvaluationsystem.repository.ServiceRepository;
import com.letitbee.diamondvaluationsystem.service.ServiceService;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private ServiceRepository serviceRepository;
    private ModelMapper mapper;
    private ServicePriceListRepository servicePriceListRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ServicePriceListRepository servicePriceListRepository,ModelMapper mapper) {
        this.serviceRepository = serviceRepository;
        this.mapper = mapper;
        this.servicePriceListRepository = servicePriceListRepository;
    }

    @Override
    public List<ServiceDTO> getAllService() {
        List<Service> services= serviceRepository.findAll();
        List<ServiceDTO> serviceResponse = services.stream().map(service -> mapToDto(service)).collect(Collectors.toList());
        return serviceResponse;
    }

    @Override
    public ServiceDTO getServiceById(long id) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service", "id", id + ""));
        return mapToDto(service);
    }

    @Override
    public ServiceDTO createService(ServiceDTO serviceDto) {
        Service service = mapToEntity(serviceDto);
        Service newService = serviceRepository.save(service);
        return mapToDto(newService);
    }

    @Override
    public ServiceDTO updateService(ServiceDTO serviceDto, long id) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service", "id", id + ""));
        service.setServiceName(serviceDto.getName());
        service.setDescription(serviceDto.getDescription());
        service.setPeriod(serviceDto.getPeriod());
        serviceRepository.save(service);
        return mapToDto(service);
    }

    @Override
    public void deleteServiceById(long id) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service", "id", id + ""));
        serviceRepository.delete(service);
    }

    @Override
    public List<ServicePriceListDTO> getAllServicePriceListByServiceId(Long serviceId) {
        List<ServicePriceList> servicePriceLists = servicePriceListRepository.findByServiceId(serviceId);
        return servicePriceLists.stream().map(servicePriceList -> mapper.map(servicePriceList,ServicePriceListDTO.class)).collect(Collectors.toList());
    }

    private ServiceDTO mapToDto(Service service) {
        return mapper.map(service, ServiceDTO.class);
    }
    private Service mapToEntity(ServiceDTO serviceDto) {
        return mapper.map(serviceDto, Service.class);
    }
}
