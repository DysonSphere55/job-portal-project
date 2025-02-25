package com.jobportal.jobportal.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    private final static String[] PUBLIC_URL = {
            "/",
            "/global-search/**",
            "/register/**",
            "/error",
            "/css/**", "/js/**", "/images/**"
    };

    @Test
    void shouldAllowAccessToPublicUrls() throws Exception {

        for (String url : PUBLIC_URL) {
            mockMvc.perform(get(url))
                    .andExpect(result -> {
                        int status = result.getResponse().getStatus();
                        assertTrue(status != 401);
                    });
        }
    }

    @Test
    void shouldRequireAuthenticationForPrivateEndpoints() throws Exception {

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value = "recruiter@test.email")
    void shouldAllowAuthenticatedUserAccess() throws Exception {

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectInvalidLogin() throws Exception {

        mockMvc.perform(SecurityMockMvcRequestBuilders
                .formLogin()
                .user("wrongUsername")
                .password("wrongPassword"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user", roles = {"RECRUITER"})
    void shouldLogoutUser() throws Exception {

        mockMvc.perform(SecurityMockMvcRequestBuilders
                        .logout())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
