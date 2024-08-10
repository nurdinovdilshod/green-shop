package com.company.config.security;

import com.company.entity.AuthUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SessionUser {

    public AuthUser user() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getAuthUser();
        }
        return null;
    }

    public String id() {
        AuthUser user = user();
        if (Objects.isNull(user)) {
            return "-1";
        }
        return user().getId();
    }
}
