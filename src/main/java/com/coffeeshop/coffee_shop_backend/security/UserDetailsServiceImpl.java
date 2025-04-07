package com.coffeeshop.coffee_shop_backend.security;

import com.coffeeshop.coffee_shop_backend.dto.UserDTO;
import com.coffeeshop.coffee_shop_backend.model.User;
import com.coffeeshop.coffee_shop_backend.repository.UserRepository;
import com.coffeeshop.coffee_shop_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserDetailsServiceImpl implements UserService {

    private final PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    public void updateUser(String username, UserDTO userDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDTO.getUsername()));
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }
}