package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.KategoriEntity;
import com.entity.UrunEntity;
import com.service.KategoriServiceImpl;
import com.service.UrunServiceImpl;

@Controller
public class RootController {
	
	@Autowired
    private KategoriServiceImpl KategorServiceImpl;
	@Autowired
    private UrunServiceImpl UrunServiceImpl;
    
	
	/*
	@GetMapping(path =
	{ "", "index", "index.html" })
	public ModelAndView home()
	{
		ModelAndView modelAndView = new ModelAndView("index");
		//modelAndView.addObject("posts", this.all_posts);
		return modelAndView;
	}

    @GetMapping("/urun/addnew")
    public ModelAndView newUrun() {
        UrunEntity urun = new UrunEntity();
        ModelAndView mav = new ModelAndView("urunPage");
        mav.addObject("urun", urun);
         
        List<KategoriEntity> kategoriler = (List<KategoriEntity>) KategorServiceImpl.getAll();
        mav.addObject("kategoriler", kategoriler);
         
        return mav;    
    } 
	
    @PostMapping("/urun/save2")
    public ModelAndView saveUrun2(@ModelAttribute UrunEntity urun, Model model) {
    	ModelAndView mav = new ModelAndView("urunPage");
    	model.addAttribute("urun", urun);
         
    	UrunServiceImpl.save(urun);
         
        return mav;    
    }
    
    @PostMapping("/urun/save")
    public ModelAndView saveUrun(@ModelAttribute("urun") UrunEntity urun) {
    	ModelAndView mav = new ModelAndView("urunPage");
         
    	UrunServiceImpl.save(urun);
         
        return mav;    
    }*/
    
}
