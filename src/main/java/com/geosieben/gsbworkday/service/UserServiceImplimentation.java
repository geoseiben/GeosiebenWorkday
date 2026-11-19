package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.entity.User;
import com.geosieben.gsbworkday.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplimentation implements UserServices {
    private final UserRepository userRepository;

    UserServiceImplimentation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User addUser(User user) {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

//     @Scheduled(cron="")
// public void executeEveryMinute() {
//     System.out.println("Task executed every minute!");
// }
}
