package com.gp.KuryeNet;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.gp.KuryeNet.API.controllers.AuthenticationController;
import com.gp.KuryeNet.core.business.concretes.UserDetailsManager;
import com.gp.KuryeNet.core.entities.AuthenticationRequest;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;

import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsManager userDetailsManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAuthenticationToken_ValidAuthenticationRequest_ShouldReturnSuccessDataResult() throws Exception {
        // Arrange
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("deneme@gmail.com", "Deneme1");
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any()))
                .thenReturn(null);
        when(userDetailsManager.loadUserByUsername(authenticationRequest.getEmail()))
                .thenReturn(userDetails);
        when(jwtUtil.generateToken(authenticationRequest.getEmail()))
                .thenReturn("testToken");

        // Act & Assert
        mockMvc.perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\": \"deneme@gmail.com\", \"password\": \"Deneme1\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Successfully login and JWT created successfully for 24 hours"))
                .andExpect(jsonPath("$.data.token").value("testToken"));

        // Verify
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userDetailsManager, times(1)).loadUserByUsername(authenticationRequest.getEmail());
        verify(jwtUtil, times(1)).generateToken(authenticationRequest.getEmail());
    }
}
