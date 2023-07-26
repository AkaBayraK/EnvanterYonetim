package com.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CategoryEntity;
import com.service.CategoryService;


@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;
    
    public CategoryController(
    		CategoryService categoryService
    		) {
    	this.categoryService = categoryService;
    }

	@GetMapping
    public List<CategoryEntity> getAll() {
        return categoryService.getAll();
    }	
	
	@GetMapping("/{id}")
	public CategoryEntity getById(@PathVariable Long id) {
		return categoryService.getById(id);
	}

    @PostMapping
    public CategoryEntity save(@RequestBody CategoryEntity ent) {
		return categoryService.save(ent);
    }

    @PutMapping("/{id}")
    public CategoryEntity update(@PathVariable Long id, @RequestBody CategoryEntity ent) {
		return categoryService.update(id, ent);
    }

    @DeleteMapping("/{id}")
    public CategoryEntity delete(@PathVariable Long id) {
		return categoryService.delete(id);

    }
    
}