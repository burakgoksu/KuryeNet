package com.gp.KuryeNet.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import com.gp.KuryeNet.core.config.properties.SecurityProperties;
import com.gp.KuryeNet.core.filter.JwtRequestFilter;
import com.gp.KuryeNet.core.security.PermissionPolicy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Autowired
    private JwtRequestFilter requestFilter;

    private final SecurityProperties securityProperties;

    private final PermissionPolicy permissionPolicy;

    public WebSecurityConfiguration(SecurityProperties securityProperties, PermissionPolicy permissionPolicy) {
        this.securityProperties = securityProperties;
        this.permissionPolicy = permissionPolicy;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**","/authentication/**","/api/customers/add","/api/v1/customers/add","/error",
								"/actuator/health","/actuator/info","/actuator/prometheus","/actuator/metrics",
								"/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
						.requestMatchers("/api/customersbaskets/add","/api/v1/customersbaskets/add","/api/customers/**","/api/v1/customers/**","/api/customersbaskets/**","/api/v1/customersbaskets/**")
						.access((authentication, context) -> new AuthorizationDecision(permissionPolicy.isCustomerOrAdmin(authentication.get())))
						.requestMatchers("/api/couriers/getCourierWithOrderDetails","/api/v1/couriers/getCourierWithOrderDetails","/api/orders/getByOrderNumber","/api/v1/orders/getByOrderNumber")
						.access((authentication, context) -> new AuthorizationDecision(permissionPolicy.isCustomerCourierOrAdmin(authentication.get())))
						.requestMatchers("/api/**","/api/v1/**")
						.access((authentication, context) -> new AuthorizationDecision(permissionPolicy.isCourierOrAdmin(authentication.get())))
						.anyRequest().authenticated()
				)
				.securityContext(securityContext -> securityContext
						.securityContextRepository(new RequestAttributeSecurityContextRepository())
						.requireExplicitSave(false)
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(securityProperties.getCors().getAllowedOrigins());
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		configuration.setExposedHeaders(List.of("Authorization"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
