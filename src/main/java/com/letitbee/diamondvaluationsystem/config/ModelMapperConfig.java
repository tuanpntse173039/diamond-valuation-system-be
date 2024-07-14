//package com.letitbee.diamondvaluationsystem.config;
//
//import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
//import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
//import com.letitbee.diamondvaluationsystem.payload.*;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.modelmapper.PropertyMap;
//
//import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
//import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
//
//import java.util.stream.Collectors;
//@Configuration
//public class ModelMapperConfig {
//
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//        modelMapper.addMappings(new PropertyMap<DiamondValuationNote, DiamondValuationNoteDTO>() {
//            @Override
//            protected void configure() {
//                map(source.getCutScore()).setCutScore(0.0f);
//                map().setDiamondImages(source.getDiamondImage().stream()
//                        .map(diamondImage -> modelMapper.map(diamondImage, DiamondImageDTO.class))
//                        .collect(Collectors.toSet()));
//            }
//        });
//
//        modelMapper.addMappings(new PropertyMap<ValuationRequestDetail, ValuationRequestDetailDTO>() {
//            @Override
//            protected void configure() {
//                map(source.getDiamondValuationNote()).setDiamondValuationNote(modelMapper.map(source.getDiamondValuationNote(), DiamondValuationNoteDTO.class));
//                map().setDiamondValuationAssign(modelMapper.map(source.getDiamondValuationAssign(), DiamondValuationAssignDTO.class));
//                map().setValuationRequestID(source.getValuationRequest().getId());
//                map().setDiamondValuationAssigns(source.getDiamondValuationAssigns().stream()
//                        .map(diamondValuationAssign -> modelMapper.map(diamondValuationAssign, DiamondValuationAssignDTO.class))
//                        .collect(Collectors.toSet()));
//            }
//        });
//
//        modelMapper.addMappings(new PropertyMap<ValuationRequest, ValuationRequestResponse>() {
//            @Override
//            protected void configure() {
//                map().setId(source.getId());
//                map().setCreationDate(source.getCreationDate());
//                map().setService(modelMapper.map(source.getService(), ServiceDTO.class));
//                map().setCustomer(modelMapper.map(source.getCustomer(), CustomerDTO.class));
//                map().setStatus(source.getStatus());
//                map().setDiamondAmount(source.getValuationRequestDetails().size());
//            }
//        });
//
//        modelMapper.addMappings(new PropertyMap<ValuationRequest, ValuationRequestResponseV2>() {
//            @Override
//            protected void configure() {
//                map().setId(source.getId());
//                map().setCreationDate(source.getCreationDate());
//                map().setServiceName(source.getService().getServiceName());
//                map().setCustomerFirstName(source.getCustomer().getFirstName());
//                map().setCustomerLastName(source.getCustomer().getLastName());
//                map().setReturnDate(source.getReturnDate());
//                map().setDiamondAmount(source.getValuationRequestDetails().size());
//                map().setFeedback(source.getFeedback());
//                map().setCancelReason(source.getCancelReason());
//                map().setStatus(source.getStatus());
//            }
//        });
//
//
//        return modelMapper;
//    }
//
//}
//
