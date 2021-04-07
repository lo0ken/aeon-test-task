package com.looken.aeon.security.bruteforce;

import com.looken.aeon.dto.UserDto;
import com.looken.aeon.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultBruteForceProtectionService implements BruteForceProtectionService {


    private final static int maxFailedLogins = 3;

    private final UserService userService;

    @Override
    public void registerLoginFailure(String login) {
        UserDto user = userService.findByLogin(login);

        if (user.getIsLoginDisabled()) {
            return;
        }

        int failedCounter = user.getFailedLoginAttempts();

        if (maxFailedLogins < failedCounter) {
            userService.disableLogin(user.getId());
        } else {
            userService.increaseFailedLoginAttempt(user.getId());
        }

    }

    @Override
    public void resetBruteForceCounter(String login) {
        userService.resetBruteForceCounter(login);
    }
}
