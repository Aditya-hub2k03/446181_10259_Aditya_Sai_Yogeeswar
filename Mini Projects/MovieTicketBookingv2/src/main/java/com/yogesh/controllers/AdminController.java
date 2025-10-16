package com.yogesh.controllers;

import com.yogesh.dto.RoleDTO;
import com.yogesh.dto.TheatreAdminDTO;
import com.yogesh.dto.TheatreDTO;
import com.yogesh.dto.UserDTO;
import com.yogesh.model.Role;
import com.yogesh.model.User;
import com.yogesh.service.RoleService;
import com.yogesh.service.TheatreAdminService;
import com.yogesh.service.TheatreService;
import com.yogesh.service.UserService;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TheatreAdminService theatreAdminService;

    @Autowired
    private TheatreService theatreService;

    @GetMapping("/dashboard")
    public String adminDashboard(@SessionAttribute("userId") int userId, Model model, HttpSession session) {
        // Check if the user is an application admin
        boolean isAdmin = roleService.hasRole(userId, "APPLICATION_ADMIN");
        if (!isAdmin) {
            return "redirect:/home/common";
        }

        // Store admin status in session for view access
        session.setAttribute("isAdmin", true);

        return "admin-dashboard";
    }

    @GetMapping("/users")
    public String manageUsers(@SessionAttribute("userId") int userId, Model model, HttpSession session) {
        // Check if the user is an application admin
        boolean isAdmin = roleService.hasRole(userId, "APPLICATION_ADMIN");
        if (!isAdmin) {
            return "redirect:/home/common";
        }

        List<UserDTO> users = userService.getAllUsers();
        List<RoleDTO> allRoles = roleService.getAllRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", allRoles);
        return "manage-users";
    }

    @PostMapping("/assign-role")
    public String assignRole(
            @SessionAttribute("userId") int adminId,
            @RequestParam("userId") int userId,
            @RequestParam("roleId") int roleId,
            HttpSession session) {

        // Check if the current user is an application admin
        boolean isAdmin = roleService.hasRole(adminId, "APPLICATION_ADMIN");
        if (!isAdmin) {
            return "redirect:/home/common";
        }

        // Assign the role
        roleService.assignRoleToUser(userId, roleId);

        // If assigning THEATRE_ADMIN role, update session data
        if (roleId == 2) { // 2 is the role_id for THEATRE_ADMIN
            User user = userService.findUserById(userId);
            if (user != null) {
                // Invalidate any cached role data for this user
                session.removeAttribute("userRoles_" + userId);
            }
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/theatres")
    public String manageTheatres(@SessionAttribute("userId") int userId, Model model, HttpSession session) {
        // Check if the user is an application admin
        boolean isAdmin = roleService.hasRole(userId, "APPLICATION_ADMIN");
        if (!isAdmin) {
            return "redirect:/home/common";
        }

        List<TheatreDTO> theatres = theatreService.getAllTheatres();
        List<UserDTO> theatreAdmins = userService.getUsersByRole("THEATRE_ADMIN");
        model.addAttribute("theatres", theatres);
        model.addAttribute("theatreAdmins", theatreAdmins);
        return "manage-theatres";
    }

    @PostMapping("/assign-theatre")
    public String assignTheatre(
            @SessionAttribute("userId") int adminId,
            @RequestParam("adminId") int theatreAdminId,
            @RequestParam("theatreId") int theatreId,
            HttpSession session) {

        // Check if the current user is an application admin
        boolean isAdmin = roleService.hasRole(adminId, "APPLICATION_ADMIN");
        if (!isAdmin) {
            return "redirect:/home/common";
        }

        // Verify the target user is a theatre admin
        boolean isTheatreAdmin = roleService.hasRole(theatreAdminId, "THEATRE_ADMIN");
        if (!isTheatreAdmin) {
            session.setAttribute("error", "User is not a theatre admin");
            return "redirect:/admin/theatres";
        }

        theatreAdminService.assignTheatreToAdmin(theatreAdminId, theatreId);
        return "redirect:/admin/theatres";
    }
}
