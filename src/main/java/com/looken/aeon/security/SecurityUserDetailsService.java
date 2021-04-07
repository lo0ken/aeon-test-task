package com.looken.aeon.security;

import com.looken.aeon.dto.UserDto;
import com.looken.aeon.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDto user = userService.findByLogin(login);

        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
