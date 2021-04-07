package com.looken.aeon.controller;

import com.looken.aeon.dto.PaymentDto;
import com.looken.aeon.dto.UserDto;
import com.looken.aeon.service.api.PaymentService;
import com.looken.aeon.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final static BigDecimal PAYMENT_AMOUNT = BigDecimal.valueOf(1.1);

    private final PaymentService paymentService;

    private final UserService userService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<PaymentDto> payment(Authentication authentication) {
        Integer userId = getAuthenticatedUserId(authentication);

        PaymentDto paymentDto = paymentService.makePayment(userId, PAYMENT_AMOUNT);

        return ResponseEntity.ok(paymentDto);
    }

    private Integer getAuthenticatedUserId(Authentication authentication) {
        UserDto principal = (UserDto) authentication.getPrincipal();
        UserDto userDto = userService.findByLogin(principal.getLogin()).orElseThrow(() ->
                new AuthenticationCredentialsNotFoundException("Something went wrong with your credentials!"));

        return userDto.getId();
    }
}
