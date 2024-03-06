package com.prom.todotask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prom.todotask.cotrolller.AuthenticationController;
import com.prom.todotask.dto.user.RegistrationDTO;
import com.prom.todotask.dto.user.UserRequestDTO;
import com.prom.todotask.entity.User;
import com.prom.todotask.exception.Unauthorized401Exception;
import com.prom.todotask.security.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthenticationControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authService;

    @BeforeEach
    void printApplicationContext() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Arrays.stream(webApplicationContext.getBeanDefinitionNames())
                .map(name -> webApplicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    void login_ValidCredentials_ShouldReturnUserDTOWithToken() throws Exception {
        UserRequestDTO validCredentials = UserRequestDTO.builder()
                .login("testUser")
                .password("password")
                .build();
        User user = new User();
        when(authService.login("testUser", "password")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCredentials)))
                .andExpect(status().isOk());
    }

    @Test
    void login_InvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        UserRequestDTO invalidCredentials = UserRequestDTO.builder()
                .login("testUser")
                .password("wrongPassword")
                .build();

        // Mock the behavior of the authService.login method to throw Unauthorized401Exception
        doThrow(Unauthorized401Exception.class).when(authService).login("testUser", "wrongPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidCredentials)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegister_ValidRegistration_ShouldReturnCreated() throws Exception {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUsername("testUser");
        registrationDTO.setPassword("password");

        doNothing().when(authService).register(registrationDTO.getUsername(), registrationDTO.getPassword());

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registrationDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
