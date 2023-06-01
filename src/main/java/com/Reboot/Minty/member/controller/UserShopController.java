package com.Reboot.Minty.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserShopController {
    @GetMapping(value = "usershop")
    public String usershop()  {
        return "member/userShop";
    }
}
