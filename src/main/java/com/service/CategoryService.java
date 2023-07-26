package com.service;

import java.util.List;

import com.entity.CategoryEntity;


public interface CategoryService {

	List<CategoryEntity> getAll();
	CategoryEntity getById(Long id);
	CategoryEntity save(CategoryEntity ent);
	CategoryEntity update(Long id, CategoryEntity ent);
	CategoryEntity delete(Long id);
    
}
