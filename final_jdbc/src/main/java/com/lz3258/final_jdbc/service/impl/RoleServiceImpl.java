package com.lz3258.final_jdbc.service.impl;

import com.lz3258.final_jdbc.dao.RoleDao;
import com.lz3258.final_jdbc.entity.Role;
import com.lz3258.final_jdbc.service.RoleService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }
}
