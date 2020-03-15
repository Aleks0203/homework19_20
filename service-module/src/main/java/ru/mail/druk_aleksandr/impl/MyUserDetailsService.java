package ru.mail.druk_aleksandr.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mail.druk_aleksandr.UserService;
import ru.mail.druk_aleksandr.model.AppUser;
import ru.mail.druk_aleksandr.model.UserDTO;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.loadUserByUserName(username);
        if (userDTO == null) {
            throw new UsernameNotFoundException("Not found");
        }
        return new AppUser(userDTO);
    }
}
