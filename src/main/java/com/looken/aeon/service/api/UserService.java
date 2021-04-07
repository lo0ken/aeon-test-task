package com.looken.aeon.service.api;

import com.looken.aeon.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> findByLogin(String login);
}
