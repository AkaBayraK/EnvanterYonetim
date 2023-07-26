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

import com.entity.WarehouseProductEntity;
import com.service.WarehouseProductService;


@RestController
@RequestMapping("/api/warehouseProduct")
public class WarehouseProductController {	

    private WarehouseProductService warehouseProductService;

    public WarehouseProductController(
    		WarehouseProductService warehouseProductService
    		) {
    	this.warehouseProductService = warehouseProductService;
    }
    
	/**
	 * Ürünler depoya, deponun bölgesine, deponun şehrine veya kategorisine göre filtrelenebilir. 
	 * @param ent
	 * @return
	 */
	@GetMapping("/search")
    public List<WarehouseProductEntity> search(@RequestBody WarehouseProductEntity ent) {
        return warehouseProductService.search(ent);
    }
	
	@GetMapping
    public List<WarehouseProductEntity> getAll() {
        return warehouseProductService.getAll();
    }
	
	@GetMapping("/{id}")
	public WarehouseProductEntity getById(@PathVariable long id) {
		return warehouseProductService.getById(id);
	}

    @PostMapping
    public WarehouseProductEntity save(@RequestBody WarehouseProductEntity ent) {
		return warehouseProductService.save(ent);
    }

    @PutMapping("/{id}")
    public WarehouseProductEntity update(@PathVariable Long id, @RequestBody WarehouseProductEntity ent) {
		return warehouseProductService.update(id, ent);
    }

    @DeleteMapping("/{id}")
    public WarehouseProductEntity delete(@PathVariable Long id) {
		return warehouseProductService.delete(id);
    }
    
}