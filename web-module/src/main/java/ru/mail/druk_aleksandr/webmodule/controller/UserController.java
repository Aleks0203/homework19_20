package ru.mail.druk_aleksandr.webmodule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mail.druk_aleksandr.ItemService;
import ru.mail.druk_aleksandr.UserService;
import ru.mail.druk_aleksandr.model.ItemDTO;
import ru.mail.druk_aleksandr.model.StatusEnum;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final ItemService itemService;

    public UserController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String getUsers(Model model) {
        List<ItemDTO> itemDTOS = itemService.findItemByStatus(String.valueOf(StatusEnum.COMPLETED));
        model.addAttribute("items", itemDTOS);
        return "user";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        List<ItemDTO> itemDTOS = itemService.findAll();
        model.addAttribute("items", itemDTOS);
        return "admin";
    }
}

