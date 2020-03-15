package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemRepository extends GenericRepository<Item> {
    List<Item> findItemByStatus(Connection connection, String statusName) throws SQLException;

    void updateStatusById(Connection connection, long itemId, String statusName) throws SQLException;

    void deleteByStatus(Connection connection, String statusName) throws SQLException;

    List<Long> findIds(Connection connection) throws SQLException;
}
