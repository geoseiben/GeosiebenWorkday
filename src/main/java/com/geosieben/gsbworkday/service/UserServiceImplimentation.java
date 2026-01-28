package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.entity.User;
import com.geosieben.gsbworkday.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplimentation implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User addUser(User user) {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }
}
