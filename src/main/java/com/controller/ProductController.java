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

import com.entity.ProductEntity;
import com.entity.WarehouseProductEntity;
import com.service.ProductService;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    public ProductController(
    		ProductService productService
    		) {
    	this.productService = productService;
    }
	
	/**
	 * Bir ürünün aratılıp hangi depolarda olduğunu gösteren apinin yazılması gerekmektedir.
	 * @param urunId
	 * @return
	 */
	@GetMapping("/warehouses/{productId}")
    public List<WarehouseProductEntity> productWarehouses(@PathVariable long productId) {
        return productService.getProductWarehouses(productId);
    }
	
	@GetMapping
    public List<ProductEntity> getAll() {
        return productService.getAll();
    }
	
	@GetMapping("/{id}")
	public ProductEntity getById(@PathVariable Long id) {
		return productService.getById(id);
	}

    @PostMapping
    public ProductEntity save(@RequestBody ProductEntity ent) {
		return productService.save(ent);
    }

    @PutMapping("/{id}")
    public ProductEntity update(@PathVariable Long id, @RequestBody ProductEntity ent) {
		return productService.update(id, ent);
    }

    @DeleteMapping("/{id}")
    public ProductEntity delete(@PathVariable Long id) {
		return productService.delete(id);

    }
    
}