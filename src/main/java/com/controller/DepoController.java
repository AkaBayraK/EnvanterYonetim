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

import com.entity.DepoEntity;
import com.service.DepoServiceImpl;


@RestController
@RequestMapping("/api/depo")
public class DepoController {

	@Autowired
    private DepoServiceImpl DepoServiceImpl;
	
	
    @GetMapping("/depolistesi")
    public ModelAndView showFormForList() {
    	ModelAndView modelAndView = new ModelAndView("depolistesi");
    	modelAndView.addObject("depolist", DepoServiceImpl.getAll());
		return modelAndView;
    }
    
    @GetMapping("/showFormForSave")
    public ModelAndView showFormForSave() {
        DepoEntity kt = new DepoEntity();        
    	ModelAndView modelAndView = new ModelAndView("deposavepage");
    	modelAndView.addObject("depo", kt);
        return modelAndView;
    }

    @GetMapping("/showFormForUpdate/{id}")
    public ModelAndView showFormForUpdate(@PathVariable(value = "id") long id) {
    	DepoEntity ent = DepoServiceImpl.getById(id);
    	ModelAndView modelAndView = new ModelAndView("depoupdatepage");
    	modelAndView.addObject("depo", ent);
        return modelAndView;
    }
	
    @GetMapping("/showFormForDelete/{id}")
    public ModelAndView showFormForDelete(@PathVariable(value = "id") long id) {
    	DepoServiceImpl.delete(id);
    	ModelAndView modelAndView = new ModelAndView("depolistesi");
    	modelAndView.addObject("depolist", DepoServiceImpl.getAll());
        return modelAndView;
    }
	
    @PostMapping("/saveDepo")
    public ModelAndView saveEnvanter(@ModelAttribute("depo") DepoEntity ent) {
    	System.out.println("save Depo.......");
		try {
			DepoServiceImpl.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("depolistesi");
    	modelAndView.addObject("depolist", DepoServiceImpl.getAll());
		return modelAndView;
    }
    
    @PostMapping("/updateDepo")
    public ModelAndView updateEnvanter(@ModelAttribute("depo") DepoEntity ent) {
    	System.out.println("update Depo.......");
		try {
			DepoServiceImpl.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("depolistesi");
    	modelAndView.addObject("depolist", DepoServiceImpl.getAll());
		return modelAndView;
    }
    
    /***************/	
	
	
	
	
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
