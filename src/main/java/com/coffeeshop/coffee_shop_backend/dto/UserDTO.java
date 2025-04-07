package com.coffeeshop.coffee_shop_backend.dto;


import com.coffeeshop.coffee_shop_backend.model.Role;
import com.coffeeshop.coffee_shop_backend.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "You can only use Latin letters, numbers, \"_\" and \"-\"")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "The password must contain numbers, special characters, uppercase and lowercase letters."
    )
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "You can only use Latin letters, numbers, \"_\" and \"-\"")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "You can only use Latin letters, numbers, \"_\" and \"-\"")
    private String lastName;

    private Set<String> roles;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        this.password = user.getPassword();
    }
}