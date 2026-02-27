package com.gp.KuryeNet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.KuryeNet.core.business.abstracts.AuthService;
import com.gp.KuryeNet.core.entities.SignupDto;
import com.gp.KuryeNet.core.entities.UserDto;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SignupUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    public void createUser_ValidInput_ReturnsOk() throws Exception {
        // Arrange
        SignupDto signupDto = new SignupDto("John", "Doe", "swe@gmail.com", "Password123.");
        String jsonRequest = objectMapper.writeValueAsString(signupDto);

        UserDto userDto = new UserDto(1, "John", "Doe", "swe@gmail.com");
        when(authService.createUserCustomer(any(SignupDto.class)))
                .thenReturn(new SuccessDataResult<>(userDto, "User created Successfuly"));

        // Act
        MvcResult mvcResult = mockMvc.perform(post("/auth/registerCustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(request().asyncStarted())
                .andReturn();
        
        // assert
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User created Successfuly"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("John"))
                .andExpect(jsonPath("$.data.surname").value("Doe"))
                .andExpect(jsonPath("$.data.email").value("swe@gmail.com"));
    }

    // Add more test cases based on different scenarios (e.g., invalid input, error cases) if needed.
}
