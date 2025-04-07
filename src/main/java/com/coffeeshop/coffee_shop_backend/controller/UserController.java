package com.coffeeshop.coffee_shop_backend.controller;

import com.coffeeshop.coffee_shop_backend.dto.MessageResponse;
import com.coffeeshop.coffee_shop_backend.dto.UserDTO;
import com.coffeeshop.coffee_shop_backend.exception.ErrorDetails;
import com.coffeeshop.coffee_shop_backend.security.UserDetailsImpl;
import com.coffeeshop.coffee_shop_backend.security.UserDetailsServiceImpl;
import com.coffeeshop.coffee_shop_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Map<String, Object> response = new HashMap<>();
        response.put("id", userDetails.getId());
        response.put("username", userDetails.getUsername());
        response.put("email", userDetails.getEmail());
        response.put("roles", userDetails.getAuthorities());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserDTO userDTO) {
        userService.updateUser(userDetails.getUsername(), userDTO);
        return ResponseEntity.ok(new MessageResponse("User updated."));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminAccess() {
        return ResponseEntity.ok(new HashMap<String, String>() {{
            put("message", "Admin Content");
        }});
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> userNotFound(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
