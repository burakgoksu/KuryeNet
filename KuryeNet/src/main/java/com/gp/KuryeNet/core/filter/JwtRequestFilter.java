package com.gp.KuryeNet.core.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gp.KuryeNet.core.business.concretes.UserDetailsManager;
import com.gp.KuryeNet.core.security.TokenBlocklistService;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;
import com.gp.KuryeNet.core.utulities.jwt.JwtTokenType;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private TokenBlocklistService tokenBlocklistService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception ex) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (username != null) {
            if (tokenBlocklistService.isRevoked(jwtUtil.extractTokenId(token))) {
                filterChain.doFilter(request, response);
                return;
            }
            var existingAuth = SecurityContextHolder.getContext().getAuthentication();
            if (existingAuth != null && !(existingAuth instanceof AnonymousAuthenticationToken)) {
                filterChain.doFilter(request, response);
                return;
            }
            UserDetails userDetails = userDetailsManager.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails, JwtTokenType.ACCESS)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);

    }
}
