package com.looken.aeon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class PaymentDto {
    private Integer id;

    private Integer userId;

    private BigDecimal amount;

    private BigDecimal newBalance;

    private LocalDateTime creationDate;
}
