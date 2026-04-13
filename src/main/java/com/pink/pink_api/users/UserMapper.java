package com.pink.pink_api.users;

import com.pink.pink_api.users.dtos.UserDto;
import com.pink.pink_api.users.dtos.UserRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    User toEntity(UserRegisterRequest request){
        if(request== null)
            return null;

        var user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);
        user.setActive(true);

        return user;
    }

    UserDto toDto(User user){
        if(user == null)
            return null;

        var userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setActive(user.isActive());

        return userDto;
    }
}
