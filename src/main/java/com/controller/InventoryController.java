package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<InventoryEntity>> getAll() {
        //return ResponseEntity.ok(inventoryService.getAll());
        List<InventoryEntity> searchInventory = inventoryService.getAll();
        return new ResponseEntity<List<InventoryEntity>>(searchInventory, HttpStatus.OK);
    }
	/**
	 * ExceptionHandler kulanıldı
	 * ApiException kullanildi. InventoryServerImp.java da search methodunun içerisinde kullanildi HATA yakalandığında response olarak ApiErrrorResponse dönüyor. hata yok ise InventoryEntity dönüyor.
	 */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryEntity> getById(@PathVariable final Long id) {
        //return ResponseEntity.ok(inventoryService.getById(id));
    	InventoryEntity inventory = inventoryService.getById(id);
        return new ResponseEntity<InventoryEntity>(inventory, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryEntity> save(@RequestBody InventoryEntity ent) {
    	//ResponseEntity.ok(inventoryService.save(ent));
    	InventoryEntity savedInventory = inventoryService.save(ent);
		return new ResponseEntity<InventoryEntity>(savedInventory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryEntity> update(@PathVariable Long id, @RequestBody InventoryEntity ent) {
		//return ResponseEntity.ok(inventoryService.update(id, ent));
    	InventoryEntity updatedInventory = inventoryService.update(id, ent);
        return new ResponseEntity<InventoryEntity>(updatedInventory, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<InventoryEntity> delete(@PathVariable Long id) {
		//return ResponseEntity.ok(inventoryService.delete(id));
    	InventoryEntity deleteInventory = inventoryService.delete(id);
        return new ResponseEntity<InventoryEntity>(deleteInventory, HttpStatus.ACCEPTED);
    }
    
	/**
	 * Ürünler depoya, deponun bölgesine, deponun şehrine veya kategorisine göre filtrelenebilir. (Envanter içinde yapıldı) 
	 * ApiException kullanildi. InventoryServerImp.java da search methodunun içerisinde kullanildi HATA yakalandığında response olarak ApiErrrorResponse dönüyor. hata yok ise InventoryEntity dönüyor.
	 */
	@GetMapping("/search")
    public ResponseEntity<List<InventoryEntity>> search(@RequestBody InventoryEntity ent) {
        //return ResponseEntity.ok(inventoryService.search(ent));
       List<InventoryEntity> searchInventory = inventoryService.search(ent);
       return new ResponseEntity<List<InventoryEntity>>(searchInventory, HttpStatus.OK);
    }
	
    @PostMapping("/extraction")
    public ResponseEntity<InventoryEntity> extraction(@RequestBody InventoryEntity ent) {
		//return inventoryService.extraction(ent);
    	InventoryEntity savedInventory = inventoryService.extraction(ent);
		return new ResponseEntity<InventoryEntity>(savedInventory, HttpStatus.CREATED);
    }
    
}