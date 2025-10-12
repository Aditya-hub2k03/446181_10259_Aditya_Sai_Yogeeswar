package com.yogesh.service;

import com.yogesh.dao.UserDAO;
import com.yogesh.dto.UserDTO;
import com.yogesh.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserDTO fetchUserInformation(int userId) {
        User user = userDAO.fetchUserInformation(userId);
        return convertToDTO(user);
    }
    public User authenticate(String email, String password) {
        return userDAO.findUserByEmailAndPassword(email, password);
    }
    // Check if an email is already registered
    public boolean isEmailRegistered(String email) {
        return userDAO.isEmailRegistered(email);
    }

    // Register a new user
    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }

    public boolean updateProfileInformation(UserDTO userDTO) {
        User user = convertToModel(userDTO);
        return userDAO.updateProfileInformation(user);
    }

    public boolean addPaymentMethod(int userId, String cardNumber, String cardHolder, String expiryDate, String paymentType) {
        return userDAO.addPaymentMethod(userId, cardNumber, cardHolder, expiryDate, paymentType);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setCity(user.getCity());
        dto.setPreferredGenre(user.getPreferredGenre());
        return dto;
    }

    private User convertToModel(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setCity(userDTO.getCity());
        return user;
    }
}
