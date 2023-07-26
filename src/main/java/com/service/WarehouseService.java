package com.service;

import java.util.List;

import com.entity.WarehouseEntity;


public interface WarehouseService {

	List<WarehouseEntity> getAll();
	WarehouseEntity getById(Long id);
	WarehouseEntity save(WarehouseEntity ent);
	WarehouseEntity update(Long id, WarehouseEntity ent);
	WarehouseEntity delete(Long id);
    
}
