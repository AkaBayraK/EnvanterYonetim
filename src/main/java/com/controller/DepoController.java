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

import com.entity.DepoEntity;
import com.service.DepoServiceImpl;


@RestController
@RequestMapping("/api/depo")
public class DepoController {

	@Autowired
    private DepoServiceImpl DepoServiceImpl;
	
	@GetMapping("/all")
    public List<DepoEntity> getAll() {
		System.out.println("all Controller.......");
        return DepoServiceImpl.getAll();
    }
	
	@GetMapping("/{id}")
	public DepoEntity getById(@PathVariable long id) {
		DepoEntity result= null;
		try {
			result	=	DepoServiceImpl.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    @PostMapping("/save")
    public DepoEntity save(@RequestBody DepoEntity ent) {
    	System.out.println("save Controller.......");
    	DepoEntity result= null;
		try {
			result	=	DepoServiceImpl.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @PutMapping("/update")
    public DepoEntity update(@RequestBody DepoEntity ent) {
    	System.out.println("update Controller.......");
    	DepoEntity result= null;
		try {
			result	=	DepoServiceImpl.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @DeleteMapping("/delete/{id}")
    public DepoEntity delete(@PathVariable(value = "id") long id) {
    	System.out.println("delete Controller.......");
    	DepoEntity result= null;
		try {
			result	=	DepoServiceImpl.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
    
}
