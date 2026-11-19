package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.entity.User;
import com.geosieben.gsbworkday.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {
    private UserRepository userRepository;

    private HttpSession httpSession;

    public CustomUserDetails(UserRepository userRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmployeeBasicInfo_EID(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        httpSession.setAttribute("eid",user.getEmployeeBasicInfo().getEID());
        httpSession.setAttribute("uname", user.getUsername());
        httpSession.setAttribute("username", user);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmployeeBasicInfo().getEID())
                .password(user.getPassword()) // already encoded in DB
                .authorities(user.getRole())  // if DB stores ROLE_ADMIN, ROLE_USER, etc.
                .build();

    }



}

