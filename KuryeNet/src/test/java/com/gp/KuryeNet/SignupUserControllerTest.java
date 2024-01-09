package com.gp.KuryeNet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.KuryeNet.core.business.abstracts.AuthService;
import com.gp.KuryeNet.core.entities.SignupDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SignupUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Test
    public void createUser_ValidInput_ReturnsOk() throws Exception {
        // Arrange
        SignupDto signupDto = new SignupDto("John", "Doe", "swe@gmail.com", "Password123.");
        String jsonRequest = objectMapper.writeValueAsString(signupDto);

        // Mock the authService behavior
        // You may use Mockito to mock the AuthService behavior if needed.

        // Act
        ResultActions result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
        
        // assert
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("User created Successfully"))
        .andExpect(jsonPath("$.data.id").exists())
        .andExpect(jsonPath("$.data.name").value("John"))
        .andExpect(jsonPath("$.data.surname").value("Doe"))
        .andExpect(jsonPath("$.data.email").value("swe@gmail.com"));
    }

    // Add more test cases based on different scenarios (e.g., invalid input, error cases) if needed.
}
