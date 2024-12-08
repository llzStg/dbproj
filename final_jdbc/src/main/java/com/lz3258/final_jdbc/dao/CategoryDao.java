package com.lz3258.final_jdbc.dao;

import com.lz3258.final_jdbc.dto.CategoryDTO;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<CategoryDTO> findAllCategories() {
        String sql = "SELECT mainCategory, subCategory FROM Category";
        return jdbcTemplate.query(sql,  (rs, rowNum) -> new CategoryDTO(
                rs.getString("mainCategory"),
                rs.getString("subCategory")
        ));
    }
}
