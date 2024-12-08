package com.lz3258.final_jdbc.service;

import com.lz3258.final_jdbc.dao.CategoryDao;
import com.lz3258.final_jdbc.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryDao.findAllCategories();
    }
}
