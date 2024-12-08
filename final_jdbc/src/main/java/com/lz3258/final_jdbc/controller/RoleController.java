package com.lz3258.final_jdbc.controller;

import com.lz3258.final_jdbc.entity.Role;
import com.lz3258.final_jdbc.service.RoleService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}
