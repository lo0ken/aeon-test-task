package com.looken.aeon.mapper;

import com.looken.aeon.dto.PaymentDto;
import com.looken.aeon.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PaymentMapper {
    Payment map(PaymentDto paymentDto);

    @Mapping(source = "user.id", target = "userId")
    PaymentDto map(Payment payment);
}
