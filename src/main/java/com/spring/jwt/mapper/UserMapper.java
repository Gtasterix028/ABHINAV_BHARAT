package com.spring.jwt.mapper;

import com.gtasterix.AbhinavNGO.DTO.UserDTO;
import com.gtasterix.AbhinavNGO.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileno(user.getMobileno());
        userDTO.setAmount(user.getAmount());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {

    User user = new User();
    user.setId(userDTO.getId());
    user.setName(userDTO.getName());
    user.setMobileno(userDTO.getMobileno());
    user.setEmail(userDTO.getEmail());
    user.setAmount(userDTO.getAmount());
    return user;


}}