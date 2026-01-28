package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.entity.User;
import com.geosieben.gsbworkday.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmployeeBasicInfo_EID(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        httpSession.setAttribute("eid",user.getEmployeeBasicInfo().getEID());
        System.out.println("Role: " + user.getEmployeeBasicInfo().getEID());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // already encoded in DB
                .authorities(user.getRole())  // if DB stores ROLE_ADMIN, ROLE_USER, etc.
                .build();


    }



}

