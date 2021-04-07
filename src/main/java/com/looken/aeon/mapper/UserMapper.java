package com.looken.aeon.mapper;

import com.looken.aeon.dto.UserDto;
import com.looken.aeon.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User map(UserDto userDto);
    UserDto map(User user);
}
