package com.yogesh.service;

import com.yogesh.dao.UserDAO;
import com.yogesh.dto.RoleDTO;
import com.yogesh.dto.UserDTO;
import com.yogesh.model.Role;
import com.yogesh.model.User;
import com.yogesh.util.PasswordUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleService roleService;

    /**
     * Authenticates a user by email and password
     * @param email The user's email
     * @param password The user's password
     * @return The authenticated User object, or null if authentication fails
     */
    public User authenticate(String email, String password) {
        User user = userDAO.findUserByEmail(email);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            // Get roles for the user
            List<Role> roles = roleService.getRolesByUserId(user.getUserId());
            user.setRoles(roles);
            return user;
        }
        return null;
    }

    /**
     * Registers a new user
     * @param user The user to register
     * @return true if registration was successful, false otherwise
     */
    public boolean registerUser(User user) {
        // Hash the password before storing
        user.setPasswordHash(PasswordUtil.hashPassword(user.getPassword()));

        boolean success = userDAO.registerUser(user);
        if (success) {
            // Assign default USER role
            roleService.assignRoleToUser(user.getUserId(), 1);
        }
        return success;
    }

    /**
     * Updates a user's password
     * @param userId The ID of the user
     * @param password The new password
     * @return true if the password was updated successfully, false otherwise
     */
    public boolean updatePassword(int userId, String password) {
        String hashedPassword = PasswordUtil.hashPassword(password);
        return userDAO.updatePassword(userId, hashedPassword);
    }

    /**
     * Gets all users
     * @return List of UserDTO objects
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets users by role
     * @param roleName The name of the role
     * @return List of UserDTO objects with the specified role
     */
    public List<UserDTO> getUsersByRole(String roleName) {
        List<User> users = userDAO.getUsersByRole(roleName);
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds a user by ID
     * @param userId The ID of the user
     * @return The User object, or null if not found
     */
    public User findUserById(int userId) {
        return userDAO.findUserById(userId);
    }

    /**
     * Checks if an email is already registered
     * @param email The email to check
     * @return true if the email is registered, false otherwise
     */
    public boolean isEmailRegistered(String email) {
        return userDAO.findUserByEmail(email) != null;
    }

    /**
     * Updates a user's profile information
     * @param userDTO The user DTO with updated information
     * @return true if the update was successful, false otherwise
     */
    public boolean updateProfileInformation(UserDTO userDTO) {
        User user = userDAO.findUserById(userDTO.getUserId());
        if (user == null) {
            return false;
        }

        user.setUserName(userDTO.getUserName());
        user.setCity(userDTO.getCity());
        user.setPreferredGenre(userDTO.getPreferredGenre());

        // Update user in database
        // Note: You'll need to implement this method in UserDAO
        return userDAO.updateUser(user);
    }

    /**
     * Gets roles for a user by user ID
     * @param userId The ID of the user
     * @return List of Role objects
     */
    public List<Role> getRolesByUserId(int userId) {
        return roleService.getRolesByUserId(userId);
    }

    /**
     * Converts a User object to a UserDTO object
     * @param user The User object to convert
     * @return The converted UserDTO object
     */
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setCity(user.getCity());
        dto.setPreferredGenre(user.getPreferredGenre());

        // Convert roles to RoleDTOs
        if (user.getRoles() != null) {
            List<RoleDTO> roleDTOs = user.getRoles().stream()
                    .map(role -> {
                        RoleDTO roleDTO = new RoleDTO();
                        roleDTO.setRoleId(role.getRoleId());
                        roleDTO.setRoleName(role.getRoleName());
                        return roleDTO;
                    })
                    .collect(Collectors.toList());
            dto.setRoles(roleDTOs);
        }

        return dto;
    }
    
    /**
     * Checks if a user has a specific role
     * @param userId The user's ID
     * @param roleName The role name to check
     * @return true if the user has the role, false otherwise
     */
    public boolean hasRole(int userId, String roleName) {
        return roleService.hasRole(userId, roleName);
    }

    /**
     * Checks if a user is an application admin
     * @param userId The user's ID
     * @return true if the user is an application admin, false otherwise
     */
    public boolean isApplicationAdmin(int userId) {
        return roleService.isApplicationAdmin(userId);
    }

    /**
     * Checks if a user is a theatre admin
     * @param userId The user's ID
     * @return true if the user is a theatre admin, false otherwise
     */
    public boolean isTheatreAdmin(int userId) {
        return roleService.isTheatreAdmin(userId);
    }

    /**
     * Converts a UserDTO object to a User object
     * @param userDTO The UserDTO object to convert
     * @return The converted User object
     */
    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setCity(userDTO.getCity());
        user.setPreferredGenre(userDTO.getPreferredGenre());

        // Convert RoleDTOs to Roles
        if (userDTO.getRoles() != null) {
            List<Role> roles = userDTO.getRoles().stream()
                    .map(roleDTO -> {
                        Role role = new Role();
                        role.setRoleId(roleDTO.getRoleId());
                        role.setRoleName(roleDTO.getRoleName());
                        return role;
                    })
                    .collect(Collectors.toList());
            user.setRoles(roles);
        }

        return user;
    }

    /**
     * Authenticates a user by ID and password
     * @param userId The user's ID
     * @param password The user's password
     * @return The authenticated User object, or null if authentication fails
     */
    public User authenticateById(int userId, String password) {
        User user = userDAO.findUserById(userId);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            // Get roles for the user
            List<Role> roles = roleService.getRolesByUserId(userId);
            user.setRoles(roles);
            return user;
        }
        return null;
    }

    /**
     * Checks if a password matches the user's stored password hash
     * @param userId The user's ID
     * @param password The password to check
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(int userId, String password) {
        User user = userDAO.findUserById(userId);
        if (user != null) {
            return PasswordUtil.verifyPassword(password, user.getPasswordHash());
        }
        return false;
    }
}
