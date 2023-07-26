package com.service;

import java.util.List;

import com.entity.InventoryEntity;


public interface InventoryService {

	List<InventoryEntity> getAll();
	InventoryEntity getById(Long id);
	InventoryEntity save(InventoryEntity ent);
	InventoryEntity update(Long id, InventoryEntity ent);
	InventoryEntity delete(Long id);
	
	List<InventoryEntity> search(InventoryEntity ent);
	InventoryEntity extraction(InventoryEntity ent);
    
}
