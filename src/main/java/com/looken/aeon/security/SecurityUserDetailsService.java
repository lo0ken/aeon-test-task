package com.looken.aeon.security;

import com.looken.aeon.dto.UserDto;
import com.looken.aeon.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserDto> userOptional = userService.findByLogin(login);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with login: " + login + " was not found!");
        }
        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setPassword(userOptional.get().getPassword());
        return userDto;
    }
}
