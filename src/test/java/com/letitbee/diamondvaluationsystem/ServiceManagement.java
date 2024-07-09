package com.letitbee.diamondvaluationsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.entity.Service;
import com.letitbee.diamondvaluationsystem.payload.ServiceDTO;
import com.letitbee.diamondvaluationsystem.payload.ServicePriceListDTO;
import com.letitbee.diamondvaluationsystem.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = {"MANAGER"})
public class ServiceManagement {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    @Transactional
    @Rollback
    public void testServiceNameBlank() throws Exception {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("");
        serviceDTO.setDescription("testDescription");
        serviceDTO.setPeriod(1);
        String serviceJson = objectMapper.writeValueAsString(serviceDTO);

        mockMvc.perform(post("/api/v1/services")
                        .contentType("application/json")
                        .content(serviceJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testServiceNameAlreadyExist() throws Exception {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("Normal valuation");
        serviceDTO.setDescription("testDescription");
        serviceDTO.setPeriod(1);
        String serviceJson = objectMapper.writeValueAsString(serviceDTO);
        if (serviceRepository.existsByServiceName(serviceDTO.getName())) {
            mockMvc.perform(post("/api/v1/services")
                            .contentType("application/json")
                            .content(serviceJson))
                    .andExpect(status().isBadRequest());
            System.out.println("Service name already exist - " + "Service Id: " + serviceRepository.findByServiceName(serviceDTO.getName()).getId() +
                    " Service name: " + serviceRepository.findByServiceName(serviceDTO.getName()).getServiceName() +
                    " Service description: " + serviceRepository.findByServiceName(serviceDTO.getName()).getDescription());
        }else{
            mockMvc.perform(post("/api/v1/services")
                            .contentType("application/json")
                            .content(serviceJson))
                    .andExpect(status().isCreated());
            System.out.println("Service created successfully");
        }


    }

    @Test
    @Transactional
    @Rollback
    public void testServiceDescriptionBlank() throws Exception {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("testName");
        serviceDTO.setDescription(null);
        serviceDTO.setPeriod(1);
        String serviceJson = objectMapper.writeValueAsString(serviceDTO);

        mockMvc.perform(post("/api/v1/services")
                        .contentType("application/json")
                        .content(serviceJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testServicePeriodLessThan1() throws Exception {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("testName");
        serviceDTO.setDescription("testDescription");
        serviceDTO.setPeriod(0);
        String serviceJson = objectMapper.writeValueAsString(serviceDTO);

        mockMvc.perform(post("/api/v1/services")
                        .contentType("application/json")
                        .content(serviceJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testMinSize() throws Exception {
        ServicePriceListDTO serviceDTO = new ServicePriceListDTO();
        serviceDTO.setId(1);
        serviceDTO.setInitPrice(1);
        serviceDTO.setMinSize(3);
        serviceDTO.setMaxSize(5);
        serviceDTO.setUnitPrice(1000);
        String serviceJson = objectMapper.writeValueAsString(serviceDTO);

        mockMvc.perform(post("/api/v1/service-price-lists")
                        .contentType("application/json")
                        .content(serviceJson))
                .andExpect(status().isBadRequest());
    }

}
