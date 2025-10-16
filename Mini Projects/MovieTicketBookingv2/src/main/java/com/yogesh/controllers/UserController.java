package com.yogesh.controllers;

import com.yogesh.dto.UserDTO;
import com.yogesh.model.User;
import com.yogesh.service.UserService;
import com.yogesh.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(@SessionAttribute("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        UserDTO userDTO = userService.convertToDTO(user);
        model.addAttribute("user", userDTO);
        return "profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute UserDTO userDTO, HttpSession session) {
        boolean success = userService.updateProfileInformation(userDTO);
        if (success) {
            session.setAttribute("userName", userDTO.getUserName());
            return "redirect:/user/profile?success";
        } else {
            return "redirect:/user/profile?error";
        }
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @SessionAttribute("userId") int userId,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match");
            return "change-password";
        }

        User user = userService.findUserById(userId);
        if (!PasswordUtil.verifyPassword(currentPassword, user.getPasswordHash())) {
            model.addAttribute("error", "Current password is incorrect");
            return "change-password";
        }

        userService.updatePassword(userId, newPassword);
        model.addAttribute("success", "Password changed successfully");
        return "change-password";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
