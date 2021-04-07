package com.looken.aeon.service.impl;

import com.looken.aeon.dto.UserDto;
import com.looken.aeon.entity.User;
import com.looken.aeon.mapper.UserMapper;
import com.looken.aeon.repository.UserRepository;
import com.looken.aeon.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Override
    public Optional<UserDto> findByLogin(String login) {
        log.debug("searching user with login: {}", login);

        Optional<User> userOptional = userRepository.findByLogin(login);
        return userOptional.map(userMapper::map);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserDto> userOptional = findByLogin(login);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + login + " was not found!");
        }
        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setPassword(userOptional.get().getPassword());
        return userDto;
    }
}
