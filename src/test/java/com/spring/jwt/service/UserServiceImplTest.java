package com.spring.jwt.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

import com.spring.jwt.dto.RegisterDto;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.exception.UserAlreadyExistException;
import com.spring.jwt.repository.RoleRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.utils.BaseResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void testRegisterAccount_Success() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("test@example.com");
        registerDto.setName("Test User");
        registerDto.setMobileNo("1234567890");
        registerDto.setPassword("password");
        registerDto.setRoles("USER");

        Role role = new Role();
        role.setName("USER");

        when(roleRepository.findByName(registerDto.getRoles())).thenReturn(role);
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(null);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());

        // Act
        BaseResponseDTO response = userServiceImpl.registerAccount(registerDto);

        // Assert
        assertEquals(HttpStatus.OK.value(), Integer.parseInt(response.getCode()));
        assertEquals("Account Created", response.getMessage());
    }

    @Test
    void testRegisterAccount_EmptyRegisterDto() {
        // Arrange
        RegisterDto registerDto = null;

        // Act and Assert
        BaseException thrown = assertThrows(BaseException.class, () -> {
            userServiceImpl.registerAccount(registerDto);
        });
        assertEquals("Data must not be empty", thrown.getMessage());
    }

    @Test
    void testRegisterAccount_ExistingUser() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("test@example.com");
        registerDto.setName("Test User");
        registerDto.setMobileNo("1234567890");
        registerDto.setPassword("password");
        registerDto.setRoles("USER");

        User user = new User();
        user.setEmail(registerDto.getEmail());

        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(user);

        // Act and Assert
        BaseException thrown = assertThrows(BaseException.class, () -> {
            userServiceImpl.registerAccount(registerDto);
        });
        assertEquals("Username already exists", thrown.getMessage());
    }

    @Test
    void testValidateAccount_EmptyRegisterDto() {
        // Arrange
        RegisterDto registerDto = null;

        // Act and Assert
        BaseException thrown = assertThrows(BaseException.class, () -> {
            userServiceImpl.validateAccount(registerDto);
        });
        assertEquals("Data must not be empty", thrown.getMessage());
    }

    @Test
    void testValidateAccount_InvalidRole() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("test@example.com");
        registerDto.setName("Test User");
        registerDto.setMobileNo("1234567890");
        registerDto.setPassword("password");
        registerDto.setRoles("INVALID_ROLE");

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        roles.add("ADMIN");

        when(roleRepository.findAll()).thenReturn(new ArrayList<>());

        // Act and Assert
        BaseException thrown = assertThrows(BaseException.class, () -> {
            userServiceImpl.validateAccount(registerDto);
        });
        assertEquals("Invalid role", thrown.getMessage());
    }
}