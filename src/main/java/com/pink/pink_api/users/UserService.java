package com.pink.pink_api.users;

import com.pink.pink_api.users.dtos.UserDto;
import com.pink.pink_api.users.dtos.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerNewUser(UserRegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new DuplicateUserException();

        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return userMapper.toDto(user);
    }

//    public void registerNewAdmin(UserRegisterRequest request) {
//        if(userRepository.existsByUsername(request.getUsername()))
//            throw new DuplicateUserException();
//
//        var user = userMapper.toEntity(request);
//        user.setRole(Role.ADMIN);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        userRepository.save(user);
//    }

    public List<UserDto> getAllUsers() {
        var users = userRepository.findAll();
        return users.stream()
                .map(u-> userMapper.toDto(u))
                .toList();
    }

    public void changeUserActivation(Integer userId) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setActive(!user.isActive());
        userRepository.save(user);
    }
}
