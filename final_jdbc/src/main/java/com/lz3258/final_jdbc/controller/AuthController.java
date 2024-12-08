package com.lz3258.final_jdbc.controller;

import com.lz3258.final_jdbc.dto.PersonRegisterRequest;
import com.lz3258.final_jdbc.entity.Person;
import com.lz3258.final_jdbc.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @CrossOrigin("*")令这个controller 允许所有的cross origin
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userName,
                                        @RequestParam String password,
                                        HttpSession session) {
        boolean isValidUser = authService.validateLogin(userName, password);

        if (isValidUser) {
            String roleID = authService.getRoleByUserName(userName);
            session.setAttribute("user", userName); // 将用户名存储到会话中 b
            session.setAttribute("roleID", roleID); // 将角色存储到会话中
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 销毁会话
        return ResponseEntity.ok("Logged out successfully");
    }

//    @GetMapping("/secure-endpoint")
//    public ResponseEntity<String> secureEndpoint(HttpSession session) {
//        if (session.getAttribute("user") != null) {
//            return ResponseEntity.ok("You are authorized to access this endpoint");
//        }
//        return ResponseEntity.status(403).body("Access denied");
//    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody PersonRegisterRequest personRequest) {
        boolean isRegistered = authService.registerUser(personRequest);
        if (isRegistered) {
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.badRequest().body("Registratoin failed");
    }

}
