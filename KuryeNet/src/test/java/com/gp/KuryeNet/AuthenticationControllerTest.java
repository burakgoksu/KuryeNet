package com.gp.KuryeNet;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.gp.KuryeNet.core.business.concretes.UserDetailsManager;
import com.gp.KuryeNet.core.entities.AuthenticationRequest;
import com.gp.KuryeNet.core.security.LoginAttemptService;
import com.gp.KuryeNet.core.security.TokenBlocklistService;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsManager userDetailsManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private LoginAttemptService loginAttemptService;

    @MockBean
    private TokenBlocklistService tokenBlocklistService;

    @Test
    public void createAuthenticationToken_ValidAuthenticationRequest_ShouldReturnSuccessDataResult() throws Exception {
        // Arrange
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("deneme@gmail.com", "Deneme1");
        UserDetails userDetails = User.withUsername(authenticationRequest.getEmail())
                .password("encoded")
                .authorities("ROLE_CUSTOMER")
                .build();

        when(authenticationManager.authenticate(any()))
                .thenReturn(null);
        when(userDetailsManager.loadUserByUsername(authenticationRequest.getEmail()))
                .thenReturn(userDetails);
        when(jwtUtil.generateAccessToken(userDetails))
                .thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(authenticationRequest.getEmail()))
                .thenReturn("refreshToken");
        when(jwtUtil.getAccessTokenExpirySeconds())
                .thenReturn(3600L);

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\": \"deneme@gmail.com\", \"password\": \"Deneme1\" }"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Successfully login and JWT created successfully"))
                .andExpect(jsonPath("$.data.jwt").value("accessToken"))
                .andExpect(jsonPath("$.data.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.data.refreshToken").value("refreshToken"))
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.data.expiresInSeconds").value(3600));

        // Verify
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userDetailsManager, times(1)).loadUserByUsername(authenticationRequest.getEmail());
        verify(jwtUtil, times(1)).generateAccessToken(userDetails);
        verify(jwtUtil, times(1)).generateRefreshToken(authenticationRequest.getEmail());
    }
}
