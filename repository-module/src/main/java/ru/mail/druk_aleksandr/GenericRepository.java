package ru.mail.druk_aleksandr;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T> {
    Connection getConnection() throws SQLException;

    List<T> findAll(Connection connection) throws SQLException;

    void add(Connection connection, T t) throws SQLException;
}
