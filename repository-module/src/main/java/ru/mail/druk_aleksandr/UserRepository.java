package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserRepository extends GenericRepository<User> {
    User findByName(Connection connection, String name) throws SQLException;
}
