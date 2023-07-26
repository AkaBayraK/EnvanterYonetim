package com.service;

import java.util.List;

import com.entity.ProductEntity;
import com.entity.WarehouseProductEntity;


public interface ProductService {

	List<ProductEntity> getAll();
	ProductEntity getById(Long id);
	ProductEntity save(ProductEntity ent);
	ProductEntity update(Long id, ProductEntity ent);
	ProductEntity delete(Long id);
	
	List<WarehouseProductEntity> getProductWarehouses(Long id);
	List<ProductEntity> getCategoryProducts(Long id);
    
}
