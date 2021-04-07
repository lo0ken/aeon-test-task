package com.looken.aeon.service.impl;

import com.looken.aeon.dto.UserDto;
import com.looken.aeon.entity.User;
import com.looken.aeon.mapper.UserMapper;
import com.looken.aeon.repository.UserRepository;
import com.looken.aeon.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

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
    public UserDto decreaseBalance(Integer userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User with id: " + userId +" not found"));

        User decreased = decreaseBalanceIfPossible(user, amount);

        log.debug("Decreased balance of user with id: {} on amount: {}. New balance: {}",
                user.getId(), amount, decreased.getBalance());

        return userMapper.map(decreased);
    }

    private User decreaseBalanceIfPossible(User user, BigDecimal amount) {
        BigDecimal currentBalance = user.getBalance();

        if (currentBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Not enough money on your balance!");
        }

        user.setBalance(currentBalance.subtract(amount));
        return user;
    }
}
