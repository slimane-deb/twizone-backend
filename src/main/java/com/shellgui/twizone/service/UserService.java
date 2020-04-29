package com.shellgui.twizone.service;

import com.shellgui.twizone.dto.RegisterDTO;
import com.shellgui.twizone.exception.UserAlreadyExistsException;
import com.shellgui.twizone.model.Profile;
import com.shellgui.twizone.model.User;
import com.shellgui.twizone.model.Visitor;
import com.shellgui.twizone.repository.ProfileRepository;
import com.shellgui.twizone.repository.UserRepository;
import com.shellgui.twizone.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(Integer id) {
        return userRepository.getOne(id);
    }

    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public int add(RegisterDTO registrationData) throws UserAlreadyExistsException {
        String role =
                (registrationData.getRole() == Role.VISITOR.getRole()) ? Role.VISITOR.name() : Role.CUSTOMER.name();
        if (!userDetailsManager.userExists(registrationData.getUsername())) {
            userDetailsManager.createUser(new org.springframework.security.core.userdetails.User(
                    registrationData.getUsername(),
                    passwordEncoder.encode(registrationData.getPassword()),
                    AuthorityUtils.createAuthorityList(createRoleString(role)))
            );
            // TODO Refactor
            User user = getUserByName(registrationData.getUsername());
            if (registrationData.getRole() == Role.VISITOR.getRole()) {
                // Register a VISITOR
                System.out.println("Add visitor to DB");
                Visitor visitor = new Visitor();
                visitor.setUser(user);
                visitor.setEmail(registrationData.getUsername());
                visitor.setRegistrationDate(new Date());
                visitorRepository.save(visitor);
            } else if (registrationData.getRole() == Role.CUSTOMER.getRole()) {
                // Register a CUSTOMER
                System.out.println("Add profile to DB");
                Profile profile = new Profile();
                profile.setUser(user);
                profile.setEmail(registrationData.getUsername());
                profile.setFirstName(registrationData.getFirstName());
                profile.setLastName(registrationData.getLastName());
                profile.setPhoneNumber(registrationData.getPhoneNumber());
                profile.setProfessionId(registrationData.getProfessionId());
                profile.setRegistrationDate(new Date());
                profileRepository.save(
                        profile.getUser().getId(),
                        profile.getEmail(),
                        profile.getFirstName(),
                        profile.getLastName(),
                        profile.getPhoneNumber(),
                        profile.getProfessionId(),
                        profile.getPosition()
                );
            }
            // user.setRegistrationDate(new Date());
            return HttpStatus.CREATED.value(); // 201 - RESOURCE CREATED
        } else {
            throw new UserAlreadyExistsException("Repeated registration attempt. User already exists.");
        }
    }

    /**
     * @param role String (e.g., VISITOR, CUSTOMER)
     * @return String with "ROLE_" prefix
     */
    private String createRoleString(String role) {
        return "ROLE_" + role;
    }

    private enum Role {
        VISITOR(0),
        CUSTOMER(1);

        private final int value;

        Role(int value) {
            this.value = value;
        }

        private int getRole() {
            return this.value;
        }
    }
}
