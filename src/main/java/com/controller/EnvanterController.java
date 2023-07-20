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

import com.entity.EnvanterEntity;
import com.service.EnvanterServiceImpl;


@RestController
@RequestMapping("/api/envanter")
public class EnvanterController {

	@Autowired
    private EnvanterServiceImpl EnvanterServiceImpl;
	
	@GetMapping("/all")
    public List<EnvanterEntity> getAll() {
		System.out.println("all Controller.......");
        return EnvanterServiceImpl.getAll();
    }
	
	@GetMapping("/{id}")
	public EnvanterEntity getById(@PathVariable long id) {
		EnvanterEntity result= null;
		try {
			result	=	EnvanterServiceImpl.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    @PostMapping("/save")
    public EnvanterEntity save(@RequestBody EnvanterEntity ent) {
    	System.out.println("save Controller.......");
    	EnvanterEntity result= null;
		try {
			result	=	EnvanterServiceImpl.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @PutMapping("/update")
    public EnvanterEntity update(@RequestBody EnvanterEntity ent) {
    	System.out.println("update Controller.......");
    	EnvanterEntity result= null;
		try {
			result	=	EnvanterServiceImpl.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
    
    @PostMapping("/extraction")
    public EnvanterEntity extraction(@RequestBody EnvanterEntity ent) {
    	System.out.println("extraction Controller.......");
    	EnvanterEntity result= null;
		try {
			result	=	EnvanterServiceImpl.extraction(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @DeleteMapping("/delete/{id}")
    public EnvanterEntity delete(@PathVariable(value = "id") long id) {
    	System.out.println("delete Controller.......");
    	EnvanterEntity result= null;
		try {
			result	=	EnvanterServiceImpl.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
    
}
