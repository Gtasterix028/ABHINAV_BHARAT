package com.spring.jwt.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.Interfaces.UserService;
import com.spring.jwt.dto.RegisterDto;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.exception.UserAlreadyExistException;
import com.spring.jwt.utils.BaseResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AccountController.class})
@ExtendWith(SpringExtension.class)
class AccountControllerDiffblueTest {
    @Autowired
    private AccountController accountController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link AccountController#register(RegisterDto)}
     */
    @Test
    void testRegister() throws Exception {
        // Arrange
        BaseResponseDTO buildResult = BaseResponseDTO.builder().code("Code").message("Not all who wander are lost").build();
        when(userService.registerAccount(Mockito.<RegisterDto>any())).thenReturn(buildResult);

        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("jane.doe@example.org");
        registerDto.setMobileNo("Mobile No");
        registerDto.setName("Name");
        registerDto.setPassword("iloveyou");
        registerDto.setRoles("Roles");
        String content = (new ObjectMapper()).writeValueAsString(registerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"code\":\"Successful\",\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test: {@link AccountController#register(RegisterDto)}
     */
    @Test
    void testRegister2() throws Exception {
        // Arrange
        when(userService.registerAccount(Mockito.<RegisterDto>any()))
                .thenThrow(new UserAlreadyExistException("Successful", "An error occurred"));

        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("jane.doe@example.org");
        registerDto.setMobileNo("Mobile No");
        registerDto.setName("Name");
        registerDto.setPassword("iloveyou");
        registerDto.setRoles("Roles");
        String content = (new ObjectMapper()).writeValueAsString(registerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"code\":\"Unsuccessful\",\"message\":\"User already exists\"}"));
    }

    /**
     * Method under test: {@link AccountController#register(RegisterDto)}
     */
    @Test
    void testRegister3() throws Exception {
        // Arrange
        when(userService.registerAccount(Mockito.<RegisterDto>any()))
                .thenThrow(new BaseException("Successful", "An error occurred"));

        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("jane.doe@example.org");
        registerDto.setMobileNo("Mobile No");
        registerDto.setName("Name");
        registerDto.setPassword("iloveyou");
        registerDto.setRoles("Roles");
        String content = (new ObjectMapper()).writeValueAsString(registerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"code\":\"Unsuccessful\",\"message\":\"Invalid role\"}"));
    }
}
