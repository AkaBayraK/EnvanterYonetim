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
import com.entity.UrunEntity;
import com.service.UrunServiceImpl;





@RestController
@RequestMapping("/api/urun")
public class UrunController {

	@Autowired
    private UrunServiceImpl UrunServiceImpl;
	
	/**
	 * Bir ürünün aratılıp hangi depolarda olduğunu gösteren apinin yazılması gerekmektedir.
	 * @param urunId
	 * @return
	 */
	@GetMapping("/depolar/{urunId}")
    public List<DepoUrunEntity> depolar(@PathVariable long urunId) {
		System.out.println("search Controller.......");
        return UrunServiceImpl.depolar(urunId);
    }
	
	@GetMapping("/all")
    public List<UrunEntity> getAll() {
		System.out.println("all Controller.......");
        return UrunServiceImpl.getAll();
    }
	
	@GetMapping("/{id}")
	public UrunEntity getById(@PathVariable long id) {
		UrunEntity result= null;
		try {
			result	=	UrunServiceImpl.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    @PostMapping("/save")
    public UrunEntity save(@RequestBody UrunEntity ent) {
    	System.out.println("save Controller.......");
    	UrunEntity result= null;
		try {
			result	=	UrunServiceImpl.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @PutMapping("/update")
    public UrunEntity update(@RequestBody UrunEntity ent) {
    	System.out.println("update Controller.......");
    	UrunEntity result= null;
		try {
			result	=	UrunServiceImpl.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }

    @DeleteMapping("/delete/{id}")
    public UrunEntity delete(@PathVariable(value = "id") long id) {
    	System.out.println("delete Controller.......");
    	UrunEntity result= null;
		try {
			result	=	UrunServiceImpl.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

    }
    
}
