package com.looken.aeon.service.api;

import com.looken.aeon.dto.UserDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> findByLogin(String login);

    UserDto decreaseBalance(Integer userId, BigDecimal amount);
}
