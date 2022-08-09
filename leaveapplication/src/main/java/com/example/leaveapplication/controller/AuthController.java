package com.example.leaveapplication.controller;

import com.example.leaveapplication.configuration.CustomUserDetailsService;
import com.example.leaveapplication.configuration.JwTokenProvider;
import com.example.leaveapplication.configuration.JwtAuthenticationResponse;
import com.example.leaveapplication.dto.UserDTO;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.repository.UserRepository;
import com.example.leaveapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user){
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.generateToken(auth);

        UserDetails userdetails = (UserDetails) auth.getPrincipal();
        if(userdetails == null){
            Map<String, String> response = new HashMap<>();
            response.put("message", "LOCKED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
                //header(HttpHeaders.AUTHORIZATION, String.valueOf(new JwtAuthenticationResponse(jwt)));
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO));
    }


}
