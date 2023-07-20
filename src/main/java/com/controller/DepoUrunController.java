package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.DepoUrunEntity;
import com.service.DepoUrunServiceImpl;


@RestController
@RequestMapping("/api/depoUrun")
public class DepoUrunController {

	@Autowired
    private DepoUrunServiceImpl service;

	/**
	 * Ürünler depoya, deponun bölgesine, deponun şehrine veya kategorisine göre filtrelenebilir. 
	 * @param ent
	 * @return
	 */
	@GetMapping("/search")
    public List<DepoUrunEntity> search(@RequestBody DepoUrunEntity ent) {
		System.out.println("search Controller.......");
        return service.search(ent);
    }
	
	@GetMapping("/all")
    public List<DepoUrunEntity> getAll() {
		System.out.println("all Controller.......");
        return service.getAll();
    }
	
	@GetMapping("/{id}")
	public DepoUrunEntity getById(@PathVariable long id) {
		DepoUrunEntity result= null;
		try {
			result	=	service.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    @PostMapping("/save")
    public DepoUrunEntity save(@RequestBody DepoUrunEntity ent) {
    	System.out.println("save Controller.......");
    	DepoUrunEntity result= null;
		try {
			result	=	service.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @PutMapping("/update")
    public DepoUrunEntity update(@RequestBody DepoUrunEntity ent) {
    	System.out.println("update Controller.......");
    	DepoUrunEntity result= null;
		try {
			result	=	service.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @DeleteMapping("/delete/{id}")
    public DepoUrunEntity delete(@PathVariable(value = "id") long id) {
    	System.out.println("delete Controller.......");
    	DepoUrunEntity result= null;
		try {
			result	=	service.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
    
}
