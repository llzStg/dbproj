package com.lz3258.final_jdbc.dao;

import com.lz3258.final_jdbc.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RoleDao {

    private JdbcTemplate jdbcTemplate;

    public List<Role> getAllRoles() {
        String sql = "SELECT * FROM Role";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class));
    }
}
