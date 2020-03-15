package ru.mail.druk_aleksandr.impl;

import org.springframework.stereotype.Repository;
import ru.mail.druk_aleksandr.ItemRepository;
import ru.mail.druk_aleksandr.model.Item;
import ru.mail.druk_aleksandr.model.StatusEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Item> implements ItemRepository {
    @Override
    public List<Item> findAll(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, name, status FROM item")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    Item item = getItem(resultSet);
                    items.add(item);
                }
                return items;
            }
        }
    }

    @Override
    public List<Item> findItemByStatus(Connection connection, String statusName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, name, status FROM item WHERE status=?")) {
            statement.setString(1, statusName);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    Item item = getItem(resultSet);
                    items.add(item);
                }
                return items;
            }
        }
    }

    @Override
    public void add(Connection connection, Item item) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO item(name, status) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, String.valueOf(item.getStatusEnum()));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId((long) generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void updateStatusById(Connection connection, long itemId, String statusName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE item SET status=? WHERE id=?")) {
            statement.setString(1, statusName);
            statement.setLong(2, itemId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update item failed, no rows affected.");
            }
        }
    }

    @Override
    public void deleteByStatus(Connection connection, String statusName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM item WHERE status=?")) {
            statement.setString(1, statusName);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting item failed, no rows affected.");
            }
        }
    }

    @Override
    public List<Long> findIds(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM item")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Long> ids = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    ids.add(id);
                }
                return ids;
            }
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        long id = resultSet.getLong("id");
        item.setId(id);
        String name = resultSet.getString("name");
        item.setName(name);
        StatusEnum statusEnum = StatusEnum.valueOf(resultSet.getString("status"));
        item.setStatusEnum(statusEnum);
        return item;
    }
}
