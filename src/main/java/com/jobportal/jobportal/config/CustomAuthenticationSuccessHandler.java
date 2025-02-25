package com.jobportal.jobportal.config;

import com.jobportal.jobportal.util.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        boolean hasRecruiterRole = userDetails.getAuthorities().stream()
                .anyMatch(it -> it.getAuthority().equals("RECRUITER"));
        boolean hasCandidateRole = userDetails.getAuthorities().stream()
                .anyMatch(it -> it.getAuthority().equals("CANDIDATE"));

        if (hasRecruiterRole || hasCandidateRole) {
            response.sendRedirect("/dashboard");
        } else {
            response.sendRedirect("/");
        }

    }
}
