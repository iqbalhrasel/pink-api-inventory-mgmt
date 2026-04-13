package com.pink.pink_api.users.dtos;

import com.pink.pink_api.users.Role;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private String username;
    private Role role;
    private boolean active;
}
