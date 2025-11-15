package com.example.todo.controller;

import com.example.todo.entity.UserEntity;
import com.example.todo.model.ApiResponse;
import com.example.todo.model.user.UserLoginRequest;
import com.example.todo.model.user.UserRegisterRequest;
import com.example.todo.model.user.UserResponse;
import com.example.todo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    public static final String SESSION_USER_KEY = "currentUser";

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody UserRegisterRequest request,
            HttpSession session) {
        log.info("Begin to register with email: {}", request.getEmail());

        try {
            if (userService.getByEmail(request.getEmail()) != null) {
                log.warn("email: {} already exist", request.getEmail());
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("email already exist"));
            }

            UserEntity user = userService.register(request.getEmail(), request.getPassword());
            log.info("Finish register with email: {}", user.getEmail());
            session.setAttribute(SESSION_USER_KEY, user);

            UserResponse userResponse = convertToResponse(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Register successfully", userResponse));

        } catch (IllegalArgumentException e) {
            log.error("Register failed with invalid argument: {}", e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Register failed with error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Register failed, please try again later"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(
            @Valid @RequestBody UserLoginRequest request,
            HttpSession session) {
        log.info("Begin to login with email: {}", request.getEmail());

        try {
            UserEntity user = userService.login(request.getEmail(), request.getPassword());

            if (user == null) {
                log.warn("Login failed for email: {}", request.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Invalid email or password"));
            }

            log.info("{} login successfully", user.getEmail());
            session.setAttribute(SESSION_USER_KEY, user);

            UserResponse userResponse = convertToResponse(user);
            return ResponseEntity.ok()
                    .body(ApiResponse.success("Login success", userResponse));

        } catch (IllegalArgumentException e) {
            log.error("Login failed with invalid argument: {}", e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Failed to login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Login failed, please try again later"));
        }
    }

    private UserResponse convertToResponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        return response;
    }
}