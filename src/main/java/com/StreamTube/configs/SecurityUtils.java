package com.StreamTube.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.StreamTube.models.User;

public class SecurityUtils {
	 public static User getCurrentUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication == null || !authentication.isAuthenticated()) {
	            throw new IllegalStateException("No authenticated user found");
	        }
	        return ((CustomUserDetails) authentication.getPrincipal()).getUser();
	    }
}
