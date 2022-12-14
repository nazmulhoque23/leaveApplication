package com.example.leaveapplication.controller;

import com.example.leaveapplication.configuration.CustomUserDetails;
import com.example.leaveapplication.configuration.CustomUserDetailsService;
import com.example.leaveapplication.configuration.JwTokenProvider;
import com.example.leaveapplication.dto.UserDTO;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.service.UserService;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
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
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = tokenProvider.generateToken(auth);


        CustomUserDetails userdetails = (CustomUserDetails) auth.getPrincipal();


        if(userdetails == null){
            Map<String, String> response = new HashMap<>();
            response.put("message", "LOCKED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(token);
    }

    @PostMapping("/registerUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        User existing = userService.findByEmail(userDTO.getEmail());
        if(existing != null){
            return ResponseEntity.badRequest().body("User already registered");
        }

        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PostMapping("/registerManager")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> registerManager(@RequestBody UserDTO userDTO){
        User existing = userService.findByEmail(userDTO.getEmail());
        if(existing != null){
            return ResponseEntity.badRequest().body("User already registered");
        }

        return ResponseEntity.ok(userService.createManager(userDTO));
    }

    /*@PostMapping("/logout")
    public ResponseEntity<?> logoutUser(){
        ResponseCookie cookie = tokenProvider.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("you have been logged out");
    }*/

    @PutMapping("/change-password")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    public ResponseEntity<?> changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword){
        userService.changePassword(oldPassword, newPassword);
        return ResponseEntity.ok("Password has been changed");
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    public ResponseEntity<?> logOutFunction(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("You have been logged out");
    }


}
