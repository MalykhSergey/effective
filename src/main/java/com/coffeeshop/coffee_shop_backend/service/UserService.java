package com.coffeeshop.coffee_shop_backend.service;

import com.coffeeshop.coffee_shop_backend.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public void updateUser(String username, UserDTO userDTO);
}
