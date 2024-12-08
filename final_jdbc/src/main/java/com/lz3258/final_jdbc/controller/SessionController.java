package com.lz3258.final_jdbc.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/session")
public class SessionController {

    @GetMapping("/roleID")
    public ResponseEntity<String> getSessionRoleID(HttpSession httpSession) {
        Object roleID = httpSession.getAttribute("roleID");
        String roleIDStr = roleID.toString();
        if (roleID != null) {
            return ResponseEntity.ok(roleIDStr);

        }
        return ResponseEntity.status(403).body("userName not in session");
    }

    @GetMapping("/userName")
    public ResponseEntity<String> getSessionUserName(HttpSession httpSession) {
        Object userName = httpSession.getAttribute("user");
        String userNameStr = userName.toString();
        if (userNameStr != null) {
            return ResponseEntity.ok(userNameStr);

        }
        return ResponseEntity.status(403).body("userName not in session");
    }
}
