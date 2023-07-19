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

import com.entity.KategoriEntity;
import com.service.KategoriServiceImpl;


@RestController
@RequestMapping("/api/kategori")
public class KategoriController {

	@Autowired
    private KategoriServiceImpl KategorServiceImpl;
	
	@GetMapping("/all")
    public List<KategoriEntity> getAll() {
		System.out.println("all Controller.......");
        return KategorServiceImpl.getAll();
    }
	
	@GetMapping("/{id}")
	public KategoriEntity getById(@PathVariable long id) {
		KategoriEntity result= null;
		try {
			result	=	KategorServiceImpl.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    @PostMapping("/save")
    public KategoriEntity save(@RequestBody KategoriEntity ent) {
    	System.out.println("save Controller.......");
    	KategoriEntity result= null;
		try {
			result	=	KategorServiceImpl.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @PutMapping("/update")
    public KategoriEntity update(@RequestBody KategoriEntity ent) {
    	System.out.println("update Controller.......");
    	KategoriEntity result= null;
		try {
			result	=	KategorServiceImpl.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @DeleteMapping("/delete/{id}")
    public KategoriEntity delete(@PathVariable(value = "id") long id) {
    	System.out.println("delete Controller.......");
    	KategoriEntity result= null;
		try {
			result	=	KategorServiceImpl.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

    }
    
}
