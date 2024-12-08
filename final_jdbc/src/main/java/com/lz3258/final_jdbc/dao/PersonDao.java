package com.lz3258.final_jdbc.dao;

import com.lz3258.final_jdbc.dto.PersonRegisterRequest;
import com.lz3258.final_jdbc.entity.Person;
import com.lz3258.final_jdbc.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
@AllArgsConstructor
public class PersonDao {

    private JdbcTemplate jdbcTemplate;

    public List<Role> getAllRoles() {
        String sql = "SELECT * FROM role";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class));
    }

    /**
     * 根据输入的userName获取一个Person的全部信息
     * @param userName
     * @return
     */
    public Person findByUsername(String userName) {
        String sql = "SELECT * FROM Person WHERE userName = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userName}, (resultSet, rowNum) -> {
                Person person = new Person();
                person.setUserName(resultSet.getString("userName"));
                person.setPassword(resultSet.getString("password"));
                person.setFName(resultSet.getString("fname"));
                person.setLName(resultSet.getString("lname"));
                person.setEmail(resultSet.getString("email"));
                return person;
            });
        } catch (EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用于判断系统中是否有重复的用户名
     * @param userName
     * @return
     */
    public boolean isUsernameTaken(String userName) {
        String sql = "SELECT COUNT(*) FROM Person WHERE userName = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userName}, Integer.class);
            return count != null && count > 0; // 如果 count > 0，表示用户名已存在
        } catch (Exception e) {
            e.printStackTrace();
            return true; // 保守处理：发生异常时默认用户名已存在
        }
    }

    public boolean registerPerson(PersonRegisterRequest personRegisterRequest) {
            String insertPersonSql = "INSERT INTO Person (userName, password, fname, lname, email) VALUES (?, ?, ?, ?, ?)";
            String insertPhoneSql = "INSERT INTO PersonPhone (userName, phone) VALUES (?, ?)";
            String insertActSql = "INSERT INTO Act (userName, roleID) VALUES (?, ?)";

            try {
                jdbcTemplate.update(insertPersonSql,
                    personRegisterRequest.getUserName(),
                    personRegisterRequest.getPassword(),
                    personRegisterRequest.getFName(),
                    personRegisterRequest.getLName(),
                    personRegisterRequest.getEmail());

                jdbcTemplate.update(insertActSql, personRegisterRequest.getUserName(), personRegisterRequest.getRoleID());

                for (String phone : personRegisterRequest.getPhones()) {
                    jdbcTemplate.update(insertPhoneSql, personRegisterRequest.getUserName(), phone);
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
    }

    public String getRoleByUserName(String userName) {
        String sql = """
            SELECT r.roleID
            FROM Act a
            JOIN Role r ON a.roleID = r.roleID
            WHERE a.userName = ?
            """;
        try {
            // 查询数据库，获取角色描述
            String role = jdbcTemplate.queryForObject(sql, new Object[]{userName}, String.class);
            return role != null ? role : "普通用户"; // 如果查询结果为 null，返回默认值
        } catch (EmptyResultDataAccessException e) {
            // 如果用户没有关联角色，返回默认角色
            return "0";
        } catch (Exception e) {
            // 记录异常并抛出运行时异常
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch role for user: " + userName, e);
        }
    }

}
