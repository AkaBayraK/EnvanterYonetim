package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.InventoryEntity;
import com.service.InventoryService;

/**
 * getById ve search methodlarında,
 * ApiException kullanildi. InventoryServerImp.java da search methodunun içerisinde kullanildi HATA yakalandığından response olarak ApiErrrorResponse dönüyor. hata yok ise InventoryEntity dönüyor.
 * ExceptionHandler
 * 
 *  extraction methodunda ise Exception lar InventoryEntity üzerinde de yer alan errorMessages (hatalar) ve warningMessages (uyarılar) response olarak enitity dönerek görülebiliyor. Service tarafında çalıştırılacak
 *  validate ile hem hata ve hemde uyarılar setlenecek. service de veritabanına hatalara göre commit veya rollback olacak
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
    	this.inventoryService = inventoryService;
    }

	@GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(inventoryService.getAll());
    }
	/**
	 * ExceptionHandler kulanıldı
	 * ApiException kullanildi. InventoryServerImp.java da search methodunun içerisinde kullanildi HATA yakalandığında response olarak ApiErrrorResponse dönüyor. hata yok ise InventoryEntity dönüyor.
	 */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(inventoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InventoryEntity ent) {
		return ResponseEntity.ok(inventoryService.save(ent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody InventoryEntity ent) {
		return ResponseEntity.ok(inventoryService.update(id, ent));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
		return ResponseEntity.ok(inventoryService.delete(id));
    }
    
	/**
	 * Ürünler depoya, deponun bölgesine, deponun şehrine veya kategorisine göre filtrelenebilir. (Envanter içinde yapıldı) 
	 * ApiException kullanildi. InventoryServerImp.java da search methodunun içerisinde kullanildi HATA yakalandığında response olarak ApiErrrorResponse dönüyor. hata yok ise InventoryEntity dönüyor.
	 */
	@GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody InventoryEntity ent) {
        return ResponseEntity.ok(inventoryService.search(ent));
    }
	
    @PostMapping("/extraction")
    public InventoryEntity extraction(@RequestBody InventoryEntity ent) {
		return inventoryService.extraction(ent);
    }
    
}