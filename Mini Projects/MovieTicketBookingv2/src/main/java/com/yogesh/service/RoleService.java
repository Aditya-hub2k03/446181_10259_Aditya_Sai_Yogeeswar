package com.yogesh.service;

import com.yogesh.dao.RoleDAO;
import com.yogesh.dto.RoleDTO;
import com.yogesh.model.Role;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDAO roleDAO;

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleDAO.getAllRoles();
        return roles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Role> getRolesByUserId(int userId) {
        return roleDAO.getRolesByUserId(userId);
    }

    public boolean assignRoleToUser(int userId, int roleId) {
        return roleDAO.assignRoleToUser(userId, roleId);
    }

    public boolean removeRoleFromUser(int userId, int roleId) {
        return roleDAO.removeRoleFromUser(userId, roleId);
    }

    public boolean hasRole(int userId, String roleName) {
        List<Role> roles = getRolesByUserId(userId);
        return roles.stream().anyMatch(role -> role.getRoleName().equals(roleName));
    }

    public boolean isApplicationAdmin(int userId) {
        return hasRole(userId, "APPLICATION_ADMIN");
    }

    public boolean isTheatreAdmin(int userId) {
        return hasRole(userId, "THEATRE_ADMIN");
    }

    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName());
        return dto;
    }

    private Role convertToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleId(roleDTO.getRoleId());
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }
}
