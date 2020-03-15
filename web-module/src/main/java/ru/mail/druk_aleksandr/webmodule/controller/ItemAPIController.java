package ru.mail.druk_aleksandr.webmodule.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.mail.druk_aleksandr.ItemService;
import ru.mail.druk_aleksandr.UserService;
import ru.mail.druk_aleksandr.model.ItemDTO;
import ru.mail.druk_aleksandr.model.StatusEnum;
import ru.mail.druk_aleksandr.model.UpdateItemDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemAPIController {
    private final UserService userService;
    private final ItemService itemService;

    public ItemAPIController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDTO> getItems() {
        List<ItemDTO> itemDTOS = itemService.findAll();
        return itemDTOS;
    }

    @PostMapping("/add")
    public String addItem(@Valid @RequestBody ItemDTO item,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return allErrors.toString();
        } else {
            itemService.addItem(item);
            return "Item successfully added";
        }
    }

    @PostMapping("/update")
    public String updateItem(@Valid @RequestBody UpdateItemDTO updateItemDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return allErrors.toString();
        }
        List<Long> ids = itemService.findIds();
        for (Long id : ids) {
            if (id == updateItemDTO.getId()) {
                itemService.updateItem(updateItemDTO.getStatusEnum(), updateItemDTO.getId());
                return "Item successfully updated";
            }
        }
        return "Id not found";
    }

    @PostMapping("/delete")
    public String deleteItem() {
        itemService.deleteItemByStatusName(String.valueOf(StatusEnum.COMPLETED));
        return "Items deleted";
    }
}

