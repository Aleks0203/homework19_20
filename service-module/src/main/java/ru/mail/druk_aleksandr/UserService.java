package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.UserDTO;

public interface UserService {
    UserDTO loadUserByUserName(String username);
}
