package com.geosieben.gsbworkday.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private HttpSession httpSession;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
                    // 1. PLACE ALL PERMIT-ALL ROUTES AT THE VERY TOP
                    .requestMatchers("/static/**", "/images/**", "/uploads/**").permitAll()
                    .requestMatchers("/login", "/register").permitAll()
                    .requestMatchers("/admin/project/getAssinee").hasAnyAuthority("user","admin")
                    .requestMatchers("/admin/project/getAllotmentInfo").hasAnyAuthority("user","admin")                
                    .requestMatchers("/admin/project/getAllocationData").hasAnyAuthority("user","admin")                         
                    .requestMatchers("/admin/project/deliverTask").hasAnyAuthority("user","admin")
                    .requestMatchers("/admin/project/assignTask").hasAnyAuthority("user","admin")  
                    .requestMatchers("/admin/project/updateAllotment").hasAnyAuthority("user","admin")                                                            
                    // 2. Restrictive/Specific routes next
                    .requestMatchers("/admin/addemployee").hasAuthority("admin")
                    .requestMatchers("/admin/**").hasAnyAuthority("admin","hr")
                    .requestMatchers("/user/**").hasAnyAuthority("user","admin","hr","it")

                    
                    // 3. Catch-all at the bottom
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    var authorities = authentication.getAuthorities();
                                        String redirectUrl = "/dashboard";
                                        String role=authorities.toString();
                                        String autho=role.substring(1,role.length()-1);  
                                        System.out.println(autho);                                     
                    if(autho.equals("admin") || autho.equals("hr")){
                        redirectUrl="/admin/dashboard";
                    }
                    httpSession.setAttribute("role",autho.toUpperCase());
                    response.sendRedirect(redirectUrl);
                })
                .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            );

    return http.build();
}



}
