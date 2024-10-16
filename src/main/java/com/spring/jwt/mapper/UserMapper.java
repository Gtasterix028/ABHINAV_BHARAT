package com.spring.jwt.mapper;


import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setMobile_no(user.getMobileNo());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    // Method to map UserDTO to User entity
    public static User toEntity(UserDTO userDTO) {
        User user = new User();

        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setMobileNo(userDTO.getMobile_no());
        user.setRole(userDTO.getRole());
        return user;
    }
}