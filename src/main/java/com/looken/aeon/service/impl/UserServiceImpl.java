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
    public Integer getIdByLogin(String login) {
        return findByLoginIfExists(login).getId();
    }

    @Override
    public UserDto findByLogin(String login) {
        log.debug("searching user with login: {}", login);

        User user = findByLoginIfExists(login);
        return userMapper.map(user);
    }

    private User findByLoginIfExists(String login) {
        return userRepository.findByLogin(login).orElseThrow(() ->
                new IllegalArgumentException("User with login: {} not found!"));
    }

    @Override
    public UserDto decreaseBalance(Integer userId, BigDecimal amount) {
        User user = findById(userId);

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

    @Override
    public void disableLogin(Integer userId) {
        User user = findById(userId);
        user.setIsLoginDisabled(true);

        userRepository.save(user);

        log.debug("Disabled login: {}", user.getLogin());
    }

    @Override
    public void increaseFailedLoginAttempt(Integer userId) {
        User user = findById(userId);
        user.setFailedLoginAttempts(
                user.getFailedLoginAttempts() + 1
        );

        userRepository.save(user);

        log.debug("Increased failed login attempt of user with login: {}", user.getLogin());
    }

    @Override
    public void resetBruteForceCounter(String login) {
        User user = findByLoginIfExists(login);
        user.setIsLoginDisabled(false);
        user.setFailedLoginAttempts(0);
    }

    private User findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User with id: " + userId + " not found"));
    }
}
