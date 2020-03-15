package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> findAll();

    List<ItemDTO> findItemByStatus(String statusName);

    void addItem(ItemDTO itemDTO);

    void updateItem(String statusName, long itemId);

    void deleteItemByStatusName(String statusName);

    List<Long> findIds();
}
