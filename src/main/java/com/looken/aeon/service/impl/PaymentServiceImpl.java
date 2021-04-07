package com.looken.aeon.service.impl;

import com.looken.aeon.dto.PaymentDto;
import com.looken.aeon.dto.UserDto;
import com.looken.aeon.entity.Payment;
import com.looken.aeon.entity.User;
import com.looken.aeon.mapper.PaymentMapper;
import com.looken.aeon.repository.PaymentRepository;
import com.looken.aeon.service.api.PaymentService;
import com.looken.aeon.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final UserService userService;

    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserService userService) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.paymentMapper = Mappers.getMapper(PaymentMapper.class);
    }

    @Override
    public PaymentDto makePayment(Integer userId, BigDecimal amount) {
        UserDto userDto = userService.decreaseBalance(userId, amount);

        Payment payment = Payment.builder()
                .user(new User(userId))
                .amount(amount)
                .newBalance(userDto.getBalance())
                .creationDate(LocalDateTime.now())
                .build();

        Payment paymentEntity = paymentRepository.save(payment);

        log.debug("User with id: {} made a payment with amount: {}", userDto, amount);

        return paymentMapper.map(paymentEntity);
    }
}
