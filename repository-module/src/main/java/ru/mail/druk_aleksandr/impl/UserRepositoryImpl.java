package ru.mail.druk_aleksandr.impl;

import org.springframework.stereotype.Repository;
import ru.mail.druk_aleksandr.UserRepository;
import ru.mail.druk_aleksandr.model.RoleEnum;
import ru.mail.druk_aleksandr.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User> implements UserRepository {
    @Override
    public User findByName(Connection connection, String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, username, password, role from user WHERE username=?")) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Connection connection, User user) throws SQLException {
        throw new UnsupportedOperationException();
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        long id = resultSet.getLong("id");
        user.setId(id);
        String username = resultSet.getString("username");
        user.setUserName(username);
        String password = resultSet.getString("password");
        user.setPassword(password);
        RoleEnum roleEnum = RoleEnum.valueOf(resultSet.getString("role"));
        user.setRoleEnum(roleEnum);
        return user;
    }
}
