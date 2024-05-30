package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Payment;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.payload.PaymentDTO;
import com.letitbee.diamondvaluationsystem.repository.PaymentRepository;
import com.letitbee.diamondvaluationsystem.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private ModelMapper mapper;
    private PaymentRepository paymentRepository;


    public PaymentServiceImpl(ModelMapper mapper, PaymentRepository paymentRepository) {
        this.mapper = mapper;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = mapToEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return mapToDTO(payment);
    }

    private Payment mapToEntity(PaymentDTO paymentDTO) {
        return mapper.map(paymentDTO, Payment.class);
    }

    private PaymentDTO mapToDTO(Payment payment) {
        return mapper.map(payment, PaymentDTO.class);
    }

}
