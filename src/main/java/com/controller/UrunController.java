package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.entity.DepoUrunEntity;
import com.entity.UrunEntity;
import com.service.DepoServiceImpl;
import com.service.DepoUrunServiceImpl;
import com.service.KategoriServiceImpl;
import com.service.UrunServiceImpl;





@RestController
@RequestMapping("/api/urun")
public class UrunController {

	@Autowired
    private UrunServiceImpl UrunServiceImpl;
	@Autowired
    private KategoriServiceImpl KategoriServiceImpl;
	@Autowired
    private DepoServiceImpl DepoServiceImpl;
	@Autowired
    private DepoUrunServiceImpl DepoUrunServiceImpl;	
	
    @GetMapping("/urunlistesi")
    public ModelAndView showFormForList() {
    	ModelAndView modelAndView = new ModelAndView("urunlistesi");
    	DepoUrunEntity en = new DepoUrunEntity();
    	modelAndView.addObject("searchDepoUrun", en);
    	modelAndView.addObject("urunlist", UrunServiceImpl.getAll());
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll()); 
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
		return modelAndView;
    }
    
    @PostMapping("/showFormForSearch")
    public ModelAndView showFormForSearch(@ModelAttribute("searchDepoUrun") DepoUrunEntity search) {
    	System.out.println("search Urun.......");
    	ModelAndView modelAndView = new ModelAndView("urunlistesi");
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("listurun", UrunServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll());
    	
    	// ürüne gör ise 
    	List<UrunEntity> urunListesi = new ArrayList<UrunEntity>();
    	if (search!=null && search.getId()!=null && search.getId()!=0L) {
    		urunListesi.add(UrunServiceImpl.getById(search.getId()));
    	} else if (search!=null && search.getUrun().getKategori()!=null && search.getUrun().getKategori().getId()!=null && search.getUrun().getKategori().getId()!=0L) { // kategoriye göre ise
    		urunListesi.addAll(UrunServiceImpl.getKategoriById(search.getUrun().getKategori().getId()));
    	} else { // depoya göre ise
        	List<DepoUrunEntity> depurun = DepoUrunServiceImpl.search(search);

    	    for (Iterator iterator = depurun.iterator(); iterator.hasNext();) {
    			DepoUrunEntity depoUrunEntity = (DepoUrunEntity) iterator.next();
    			if (urunListesi.size()==0) {
    				urunListesi.add(depoUrunEntity.getUrun());
    			} else {
    				urunListesi.forEach(y->{

    						if (!depoUrunEntity.getUrun().equals(y)) {
    							urunListesi.add(depoUrunEntity.getUrun());
    						}					

    						
    					 });
    			}
    		}    		
    	}


    	modelAndView.addObject("urunlist", urunListesi);
		return modelAndView;
    }
    
    @GetMapping("/showFormForSave")
    public ModelAndView showFormForSave() {
        UrunEntity kt = new UrunEntity();        
    	ModelAndView modelAndView = new ModelAndView("urunsavepage");
    	modelAndView.addObject("urun", kt);
    	modelAndView.addObject("listkategori", KategoriServiceImpl.getAll());
        return modelAndView;
    }

    @GetMapping("/showFormForUpdate/{id}")
    public ModelAndView showFormForUpdate(@PathVariable(value = "id") long id) {
    	UrunEntity ent = UrunServiceImpl.getById(id);
    	ModelAndView modelAndView = new ModelAndView("urunupdatepage");
    	modelAndView.addObject("listkategori", KategoriServiceImpl.getAll());
    	modelAndView.addObject("urun", ent);
        return modelAndView;
    }
	
    @GetMapping("/showFormForDelete/{id}")
    public ModelAndView showFormForDelete(@PathVariable(value = "id") long id) {
    	UrunServiceImpl.delete(id);
    	ModelAndView modelAndView = new ModelAndView("urunlistesi");
    	modelAndView.addObject("urunlist", UrunServiceImpl.getAll());
    	// filtre için
    	DepoUrunEntity en = new DepoUrunEntity();
    	modelAndView.addObject("searchDepoUrun", en);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll()); 

        return modelAndView;
    }
	
    @PostMapping("/saveUrun")
    public ModelAndView saveEnvanter(@ModelAttribute("urun") UrunEntity ent) {
    	System.out.println("save Envanter.......");
		try {
			UrunServiceImpl.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("urunlistesi");
    	modelAndView.addObject("urunlist", UrunServiceImpl.getAll());
    	//filtre için
    	DepoUrunEntity en = new DepoUrunEntity();
    	modelAndView.addObject("searchDepoUrun", en);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll()); 

		return modelAndView;
    }
    
    @PostMapping("/updateUrun")
    public ModelAndView updateEnvanter(@ModelAttribute("urun") UrunEntity ent) {
    	System.out.println("update Urun.......");
		try {
			UrunServiceImpl.update(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView modelAndView = new ModelAndView("urunlistesi");
    	modelAndView.addObject("urunlist", UrunServiceImpl.getAll());
    	// filtre için
    	DepoUrunEntity en = new DepoUrunEntity();
    	modelAndView.addObject("searchDepoUrun", en);
    	modelAndView.addObject("listdepo", DepoServiceImpl.getAll());
    	modelAndView.addObject("kategorilist", KategoriServiceImpl.getAll()); 

		return modelAndView;
    }
    
    /***************/
	
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
