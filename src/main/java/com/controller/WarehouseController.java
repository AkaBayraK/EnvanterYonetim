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

import com.entity.WarehouseEntity;
import com.service.WarehouseService;


@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {


    private WarehouseService warehouseService;

    public WarehouseController(
    		WarehouseService warehouseService
    		) {
    	this.warehouseService = warehouseService;
    }	
	
	@GetMapping
    public List<WarehouseEntity> getAll() {
        return warehouseService.getAll();
    }
	
	@GetMapping("/{id}")
	public WarehouseEntity getById(@PathVariable Long id) {
		return warehouseService.getById(id);
	}

    @PostMapping
    public WarehouseEntity save(@RequestBody WarehouseEntity ent) {
		return warehouseService.save(ent);
    }

    @PutMapping("/{id}")
    public WarehouseEntity update(@PathVariable Long id, @RequestBody WarehouseEntity ent) {
		return warehouseService.update(id, ent);
    }

    @DeleteMapping("/{id}")
    public WarehouseEntity delete(@PathVariable Long id) {
		return warehouseService.delete(id);
    }
    
}