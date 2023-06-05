package com.Reboot.Minty.member.controller;

import com.Reboot.Minty.member.entity.User;
import com.Reboot.Minty.member.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserShopController {

    private final UserRepository userRepository;

    public UserShopController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping(value = "usershop")
    public String usershop(Model model, HttpSession session)  {

        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        System.out.println(user.getNickName());
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("errorMessage", "회원 정보를 찾을 수 없습니다.");
        }
        return "member/userShop";
    }
}
