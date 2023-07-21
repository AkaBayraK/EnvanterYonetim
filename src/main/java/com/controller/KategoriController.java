package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.entity.KategoriEntity;
import com.service.KategoriServiceImpl;


@RestController
@RequestMapping("/api/kategori")
public class KategoriController {

	@Autowired
    private KategoriServiceImpl KategoriServiceImpl;
	
    @GetMapping("/kategorilistesi")
    public ModelAndView viewHomePage() {
    	ModelAndView modelAndView = new ModelAndView("kategorilistesi");
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());
		return modelAndView;
    }
    
    @GetMapping("/addNewKategori")
    public ModelAndView addNewKategori() {
        KategoriEntity kt = new KategoriEntity();        
    	ModelAndView modelAndView = new ModelAndView("kategorinewpage");
    	modelAndView.addObject("kategori", kt);
        return modelAndView;
    }
    
    @GetMapping("/showFormForUpdate/{id}")
    public ModelAndView showFormForUpdate(@PathVariable(value = "id") long id) {
    	KategoriEntity ent = KategoriServiceImpl.getById(id);
    	ModelAndView modelAndView = new ModelAndView("kategoriupdatepage");
    	modelAndView.addObject("kategori", ent);
        return modelAndView;
    }
	
    @GetMapping("/showFormForDelete/{id}")
    public ModelAndView showFormForDelete(@PathVariable(value = "id") long id) {
    	KategoriServiceImpl.delete(id);
    	ModelAndView modelAndView = new ModelAndView("kategorilistesi");
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());
        return modelAndView;
    }
	
    @PostMapping("/saveKategori")
    public ModelAndView saveKategori(@ModelAttribute("kategori") KategoriEntity ent) {
    	System.out.println("save Kategori.......");
		try {
			KategoriServiceImpl.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("kategorilistesi");
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());
		return modelAndView;
    }
    
    @PostMapping("/updateKategori")
    public ModelAndView updateKategori(@ModelAttribute("kategori") KategoriEntity ent) {
    	System.out.println("update Kategori.......");
		try {
			KategoriServiceImpl.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("kategorilistesi");
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());
		return modelAndView;
    }	
	
	
	
    
    
    
    
	@GetMapping("/all")
    public List<KategoriEntity> getAll() {
		System.out.println("all Controller.......");
        return KategoriServiceImpl.getAll();
    }	
	
	@GetMapping("/{id}")
	public KategoriEntity getById(@PathVariable long id) {
		KategoriEntity result= null;
		try {
			result	=	KategoriServiceImpl.getById(id);
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
			result	=	KategoriServiceImpl.save(ent);
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
			result	=	KategoriServiceImpl.update(ent);
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
			result	=	KategoriServiceImpl.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

    }
    
}
