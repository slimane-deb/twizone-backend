package com.shellgui.twizone.controller;

import com.shellgui.twizone.interfaces.RegisteredUser;
import com.shellgui.twizone.model.User;
import com.shellgui.twizone.service.ProfileService;
import com.shellgui.twizone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @GetMapping("")
    public RegisteredUser get(Principal principal) {
        User user = userService.getUserByName(principal.getName());
        if (isRole(UserRole.VISITOR.getRole())) {
            return user;
        } else if (isRole(UserRole.CUSTOMER.getRole())) {
            // returns Profile object
            return profileService.getOneByUserId(user.getId());
        } else {
            // TODO handle
            return null;
        }
    }

    @DeleteMapping("")
    public void delete(HttpSession session) {
        session.invalidate();
    }

    private boolean isRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(role));
    }

    private enum UserRole {
        VISITOR("ROLE_VISITOR"),
        CUSTOMER("ROLE_CUSTOMER");

        private final String value;

        UserRole(String value) {
            this.value = value;
        }

        private String getRole() {
            return this.value;
        }
    }
}
