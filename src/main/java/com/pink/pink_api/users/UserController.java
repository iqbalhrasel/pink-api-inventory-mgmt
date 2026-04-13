package com.pink.pink_api.users;

import com.pink.pink_api.common.ErrorDto;
import com.pink.pink_api.users.dtos.UserDto;
import com.pink.pink_api.users.dtos.UserRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody @Valid UserRegisterRequest request){
        UserDto userDto = userService.registerNewUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/activation/{userId}")
    public ResponseEntity<?> changeUserActivation(@PathVariable(name = "userId") Integer userId){
        userService.changeUserActivation(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @PostMapping("/register/admin/{passCode}")
//    public ResponseEntity<?> registerNewAdmin(@PathVariable(name = "passCode") String passCode,
//                                                 @RequestBody @Valid UserRegisterRequest request){
//
//        if(!passCode.equals(authValues.getAdminPass()))
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//
//        userService.registerNewAdmin(request);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorDto> handleDuplicateUser(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto("User already exists."));
    }

    /*
    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7); // Remove "Bearer "
            return "Received JWT: " + jwtToken;
        } else {
            return "No JWT token found in request";
        }
    }
    */
}
