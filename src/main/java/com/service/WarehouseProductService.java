package com.service;

import java.util.List;

import com.entity.WarehouseProductEntity;


public interface WarehouseProductService {

	List<WarehouseProductEntity> getAll();
	WarehouseProductEntity getById(Long id);
	WarehouseProductEntity save(WarehouseProductEntity ent);
	WarehouseProductEntity update(Long id, WarehouseProductEntity ent);
	WarehouseProductEntity delete(Long id);
	
	List<WarehouseProductEntity> search(WarehouseProductEntity ent);
    
}
