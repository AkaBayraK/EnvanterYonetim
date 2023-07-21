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

import com.dto.SearchEnvanterDTO;
import com.entity.EnvanterEntity;
import com.service.DepoServiceImpl;
import com.service.EnvanterServiceImpl;
import com.service.KategoriServiceImpl;
import com.service.UrunServiceImpl;


@RestController
@RequestMapping("/api/envanter")
public class EnvanterController {

	@Autowired
    private EnvanterServiceImpl EnvanterServiceImpl;
	@Autowired
    private DepoServiceImpl DepoServiceImpl;
	@Autowired
    private UrunServiceImpl UrunServiceImpl;
	@Autowired
    private KategoriServiceImpl KategoriServiceImpl;
	
    @GetMapping("/envanterlistesi")
    public ModelAndView showFormForList() {
    	ModelAndView modelAndView = new ModelAndView("envanterlistesi");
//    	SearchEnvanterDTO dto = new SearchEnvanterDTO();
//    	modelAndView.addObject("searchEnvanterDTO", dto);
    	EnvanterEntity en = new EnvanterEntity();
    	modelAndView.addObject("searchEnvanter", en);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());
    	modelAndView.addObject("envanterlist", EnvanterServiceImpl.getAll());
		return modelAndView;
    }
    
    @PostMapping("/showFormForSearch1")
    public ModelAndView showFormForSearch1(@ModelAttribute("searchEnvanterDTO") SearchEnvanterDTO searchDTO) {
    	System.out.println("search Envanter.......");
    	ModelAndView modelAndView = new ModelAndView("envanterlistesi");
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());
    	
    	EnvanterEntity ent = new EnvanterEntity();
    	if (searchDTO.getUrunId()!=null && searchDTO.getUrunId()!=0L) {
    		ent.getUrun().setId(searchDTO.getUrunId());
    	}
    	
    	modelAndView.addObject("envanterlist", EnvanterServiceImpl.search(ent));
		return modelAndView;
    }
    
    @PostMapping("/showFormForSearch")
    public ModelAndView showFormForSearch(@ModelAttribute("searchEnvanter") EnvanterEntity search) {
    	System.out.println("search Envanter.......");
    	ModelAndView modelAndView = new ModelAndView("envanterlistesi");
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());   	
    	modelAndView.addObject("envanterlist", EnvanterServiceImpl.search(search));
		return modelAndView;
    }
    
    @GetMapping("/showFormForSave")
    public ModelAndView showFormForSave() {
        EnvanterEntity kt = new EnvanterEntity();        
    	ModelAndView modelAndView = new ModelAndView("envantersavepage");
    	modelAndView.addObject("envanter", kt);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
        return modelAndView;
    }
    
    @GetMapping("/showFormForExtraction")
    public ModelAndView showFormForExtraction() {
        EnvanterEntity kt = new EnvanterEntity();        
    	ModelAndView modelAndView = new ModelAndView("envanterextractionpage");
    	modelAndView.addObject("envanter", kt);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
        return modelAndView;
    }

    @GetMapping("/showFormForUpdate/{id}")
    public ModelAndView showFormForUpdate(@PathVariable(value = "id") long id) {
    	EnvanterEntity ent = EnvanterServiceImpl.getById(id);
    	ModelAndView modelAndView = new ModelAndView("envanterupdatepage");
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("envanter", ent);
        return modelAndView;
    }
	
    @GetMapping("/showFormForDelete/{id}")
    public ModelAndView showFormForDelete(@PathVariable(value = "id") long id) {
    	EnvanterServiceImpl.delete(id);
    	ModelAndView modelAndView = new ModelAndView("envanterlistesi");
    	modelAndView.addObject("envanterlist", EnvanterServiceImpl.getAll());

    	EnvanterEntity en = new EnvanterEntity();
    	modelAndView.addObject("searchEnvanter", en);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());

    	return modelAndView;
    }
	
    @PostMapping("/saveEnvanter")
    public ModelAndView saveEnvanter(@ModelAttribute("envanter") EnvanterEntity ent) {
    	System.out.println("save Envanter.......");
		try {
			EnvanterServiceImpl.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("envanterlistesi");
    	modelAndView.addObject("envanterlist", EnvanterServiceImpl.getAll());
    	
    	EnvanterEntity en = new EnvanterEntity();
    	modelAndView.addObject("searchEnvanter", en);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());

		return modelAndView;
    }
    
    @PostMapping("/extractionEnvanter")
    public ModelAndView extractionEnvanter(@ModelAttribute("envanter") EnvanterEntity ent) {
    	System.out.println("save Envanter.......");
		try {
			EnvanterServiceImpl.extraction(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("envanterlistesi");
    	modelAndView.addObject("envanterlist", EnvanterServiceImpl.getAll());
    	
    	EnvanterEntity en = new EnvanterEntity();
    	modelAndView.addObject("searchEnvanter", en);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());

		return modelAndView;
    }
    
    @PostMapping("/updateEnvanter")
    public ModelAndView updateEnvanter(@ModelAttribute("envanter") EnvanterEntity ent) {
    	System.out.println("update Envanter.......");
		try {
			EnvanterServiceImpl.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("envanterlistesi");
    	modelAndView.addObject("envanterlist", EnvanterServiceImpl.getAll());
    	
    	EnvanterEntity en = new EnvanterEntity();
    	modelAndView.addObject("searchEnvanter", en);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());

		return modelAndView;
    }	

    
	/**
	 * Ürünler depoya, deponun bölgesine, deponun şehrine veya kategorisine göre filtrelenebilir. (Envanter içinde yapıldı) 
	 * @param ent
	 * @return
	 */
	@GetMapping("/search")
    public List<EnvanterEntity> search(@RequestBody EnvanterEntity ent) {
		System.out.println("search Controller.......");
        return EnvanterServiceImpl.search(ent);
    }
	
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
