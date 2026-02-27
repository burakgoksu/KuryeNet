package com.gp.KuryeNet.core.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("permissionPolicy")
public class PermissionPolicy {

    private static final Set<String> ADMIN_AUTHORITIES = Set.of("ROLE_ADMIN", "ADMIN");
    private static final Set<String> COURIER_AUTHORITIES = Set.of("ROLE_COURIER", "COURIER");
    private static final Set<String> CUSTOMER_AUTHORITIES = Set.of("ROLE_CUSTOMER", "CUSTOMER");

    public boolean isAdmin(Authentication authentication) {
        return hasAnyAuthority(authentication, ADMIN_AUTHORITIES);
    }

    public boolean isCourier(Authentication authentication) {
        return hasAnyAuthority(authentication, COURIER_AUTHORITIES);
    }

    public boolean isCustomer(Authentication authentication) {
        return hasAnyAuthority(authentication, CUSTOMER_AUTHORITIES);
    }

    public boolean isCourierOrAdmin(Authentication authentication) {
        return isCourier(authentication) || isAdmin(authentication);
    }

    public boolean isCustomerOrAdmin(Authentication authentication) {
        return isCustomer(authentication) || isAdmin(authentication);
    }

    public boolean isCustomerCourierOrAdmin(Authentication authentication) {
        return isCustomer(authentication) || isCourier(authentication) || isAdmin(authentication);
    }

    private boolean hasAnyAuthority(Authentication authentication, Set<String> expectedAuthorities) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            return false;
        }
        Set<String> granted = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            if (authority != null && authority.getAuthority() != null) {
                granted.add(authority.getAuthority());
            }
        }
        for (String expected : expectedAuthorities) {
            if (granted.contains(expected)) {
                return true;
            }
        }
        return false;
    }
}
