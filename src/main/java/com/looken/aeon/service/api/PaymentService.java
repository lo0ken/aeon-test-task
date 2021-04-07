package com.looken.aeon.service.api;

import com.looken.aeon.dto.PaymentDto;

import java.math.BigDecimal;

public interface PaymentService {

    PaymentDto makePayment(Integer userId, BigDecimal amount);
}
