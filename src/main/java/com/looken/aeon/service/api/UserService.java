package com.looken.aeon.service.api;

import com.looken.aeon.dto.UserDto;

import java.math.BigDecimal;

public interface UserService {

    Integer getIdByLogin(String login);

    UserDto findByLogin(String login);

    UserDto decreaseBalance(Integer userId, BigDecimal amount);

    void disableLogin(Integer userId);

    void increaseFailedLoginAttempt(Integer userId);

    void resetBruteForceCounter(String login);
}
