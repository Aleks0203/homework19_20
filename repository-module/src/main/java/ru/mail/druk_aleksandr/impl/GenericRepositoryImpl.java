package ru.mail.druk_aleksandr.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mail.druk_aleksandr.GenericRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class GenericRepositoryImpl<T> implements GenericRepository<T> {
    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
