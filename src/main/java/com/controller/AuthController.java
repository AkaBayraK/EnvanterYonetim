package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.CategoryEntity;
import com.entity.InventoryEntity;
import com.entity.ProductEntity;
import com.entity.WarehouseEntity;
import com.entity.WarehouseProductEntity;
import com.service.CategoryService;
import com.service.InventoryService;
import com.service.ProductService;
import com.service.WarehouseProductService;
import com.service.WarehouseService;


@Controller
@RequestMapping("/api/auth")
public class AuthController {
	
    private CategoryService categoryService;
    private InventoryService inventoryService;
    private ProductService productService;
    private WarehouseService warehouseService;
    private WarehouseProductService warehouseProductService;

    public AuthController(
    		CategoryService categoryService,
    		InventoryService inventoryService,
    		ProductService productService,
    		WarehouseService warehouseService,
    		WarehouseProductService warehouseProductService) {
    	this.categoryService = categoryService;
    	this.inventoryService = inventoryService;
    	this.productService = productService;
    	this.warehouseService = warehouseService;
    	this.warehouseProductService = warehouseProductService;
    }
    
    //dashboard
    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
    	ModelAndView modelAndView = new ModelAndView("dashboard");
		return modelAndView;
    }
    // category
    @GetMapping("/categorylistpage")
    public ModelAndView categorylistpage() {
    	ModelAndView modelAndView = new ModelAndView("categorylistpage");
    	modelAndView.addObject("categorylist", categoryService.getAll());
		return modelAndView;
    }
    @GetMapping("/categorynewpage")
    public ModelAndView categorynewpage() {
        CategoryEntity kt = new CategoryEntity();        
    	ModelAndView modelAndView = new ModelAndView("categorynewpage");
    	modelAndView.addObject("category", kt);
        return modelAndView;
    }    
    @GetMapping("/categoryupdatepage/{id}")
    public ModelAndView categoryupdatepage(@PathVariable(value = "id") long id) {
    	CategoryEntity ent = categoryService.getById(id);
    	ModelAndView modelAndView = new ModelAndView("categoryupdatepage");
    	modelAndView.addObject("category", ent);
        return modelAndView;
    }
    @PostMapping("/saveCategory")
    public ModelAndView saveCategory(@ModelAttribute("category") CategoryEntity ent) {
    	categoryService.save(ent);
    	ModelAndView modelAndView = new ModelAndView("categorylistpage");
    	modelAndView.addObject("categorylist", categoryService.getAll());
		return modelAndView;
    }
    
    @PostMapping("/updateCategory")
    public ModelAndView updateCategory(@ModelAttribute("category") CategoryEntity ent) {
		categoryService.update(ent.getId(), ent);
    	ModelAndView modelAndView = new ModelAndView("categorylistpage");
    	modelAndView.addObject("categorylist", categoryService.getAll());
		return modelAndView;
    }   
    @GetMapping("/deleteCategory/{id}")
    public ModelAndView deleteCategory(@PathVariable(value = "id") long id) {
    	categoryService.delete(id);
    	ModelAndView modelAndView = new ModelAndView("categorylistpage");
    	modelAndView.addObject("categorylist", categoryService.getAll());
        return modelAndView;
    }

    // warehouse
    @GetMapping("/warehouselistpage")
    public ModelAndView warehouselistpage() {
    	ModelAndView modelAndView = new ModelAndView("warehouselistpage");
    	modelAndView.addObject("warehouselist", warehouseService.getAll());
		return modelAndView;
    }
    @GetMapping("/warehousenewpage")
    public ModelAndView warehousenewpage() {
    	WarehouseEntity kt = new WarehouseEntity();        
    	ModelAndView modelAndView = new ModelAndView("warehousenewpage");
    	modelAndView.addObject("warehouse", kt);
        return modelAndView;
    }    
    @GetMapping("/warehouseupdatepage/{id}")
    public ModelAndView warehouseupdatepage(@PathVariable(value = "id") long id) {
    	WarehouseEntity ent = warehouseService.getById(id);
    	ModelAndView modelAndView = new ModelAndView("warehouseupdatepage");
    	modelAndView.addObject("warehouse", ent);
        return modelAndView;
    }
    @PostMapping("/saveWarehouse")
    public ModelAndView saveWarehouse(@ModelAttribute("warehouse") WarehouseEntity ent) {
    	warehouseService.save(ent);
    	ModelAndView modelAndView = new ModelAndView("warehouselistpage");
    	modelAndView.addObject("warehouselist", warehouseService.getAll());
		return modelAndView;
    }
    @PostMapping("/updateWarehouse")
    public ModelAndView updateWarehouse(@ModelAttribute("warehouse") WarehouseEntity ent) {
		warehouseService.update(ent.getId(), ent);
    	ModelAndView modelAndView = new ModelAndView("warehouselistpage");
    	modelAndView.addObject("warehouselist", warehouseService.getAll());
		return modelAndView;
    }   
    @GetMapping("/deleteWarehouse/{id}")
    public ModelAndView deleteWarehouse(@PathVariable(value = "id") long id) {
    	warehouseService.delete(id);
    	ModelAndView modelAndView = new ModelAndView("warehouselistpage");
    	modelAndView.addObject("warehouselist", warehouseService.getAll());
        return modelAndView;
    }
    // product
    @GetMapping("/productlistpage")
    public ModelAndView productlistpage() {
    	ModelAndView modelAndView = new ModelAndView("productlistpage");
    	modelAndView.addObject("productlist", productService.getAll());
    	WarehouseProductEntity en = new WarehouseProductEntity();
    	modelAndView.addObject("searchWarehouseProductent", en);
    	modelAndView.addObject("searchSelectWarehouse", warehouseService.getAll());
    	modelAndView.addObject("searchSelectProdcut", productService.getAll());
    	modelAndView.addObject("searchSelectCategory", categoryService.getAll());
		return modelAndView;
    }
    @GetMapping("/productnewpage")
    public ModelAndView productnewpage() {
    	ProductEntity kt = new ProductEntity();        
    	ModelAndView modelAndView = new ModelAndView("productnewpage");
    	modelAndView.addObject("product", kt);
    	modelAndView.addObject("selectcategory", categoryService.getAll());
        return modelAndView;
    }    
    @GetMapping("/productupdatepage/{id}")
    public ModelAndView productupdatepage(@PathVariable(value = "id") long id) {
    	ProductEntity ent = productService.getById(id);
    	ModelAndView modelAndView = new ModelAndView("productupdatepage");
    	modelAndView.addObject("product", ent);
    	modelAndView.addObject("selectcategory", categoryService.getAll());
        return modelAndView;
    }
    @PostMapping("/saveProduct")
    public ModelAndView saveProduct(@ModelAttribute("product") ProductEntity ent) {
    	productService.save(ent);
    	ModelAndView modelAndView = new ModelAndView("productlistpage");
    	modelAndView.addObject("productlist", productService.getAll());
    	
    	WarehouseProductEntity en = new WarehouseProductEntity();
    	modelAndView.addObject("searchWarehouseProductent", en);
    	modelAndView.addObject("searchSelectWarehouse", warehouseService.getAll());
    	modelAndView.addObject("searchSelectProdcut", productService.getAll());
    	modelAndView.addObject("searchSelectCategory", categoryService.getAll());
    	
		return modelAndView;
    }
    @PostMapping("/updateProduct")
    public ModelAndView updateProduct(@ModelAttribute("product") ProductEntity ent) {
		productService.update(ent.getId(), ent);
    	ModelAndView modelAndView = new ModelAndView("productlistpage");
    	modelAndView.addObject("productlist", productService.getAll());
    	
    	WarehouseProductEntity en = new WarehouseProductEntity();
    	modelAndView.addObject("searchWarehouseProductent", en);
    	modelAndView.addObject("searchSelectWarehouse", warehouseService.getAll());
    	modelAndView.addObject("searchSelectProdcut", productService.getAll());
    	modelAndView.addObject("searchSelectCategory", categoryService.getAll());
    	
		return modelAndView;
    }   
    @GetMapping("/deleteProduct/{id}")
    public ModelAndView deleteProduct(@PathVariable(value = "id") long id) {
    	productService.delete(id);
    	ModelAndView modelAndView = new ModelAndView("productlistpage");
    	modelAndView.addObject("productlist", productService.getAll());
    	
    	WarehouseProductEntity en = new WarehouseProductEntity();
    	modelAndView.addObject("searchWarehouseProductent", en);
    	modelAndView.addObject("searchSelectWarehouse", warehouseService.getAll());
    	modelAndView.addObject("searchSelectProdcut", productService.getAll());
    	modelAndView.addObject("searchSelectCategory", categoryService.getAll());
    	
        return modelAndView;
    }
    @PostMapping("/searchWarehouseProduct")
    public ModelAndView searchWarehouseProduct(@ModelAttribute("searchWarehouseProductent") WarehouseProductEntity search) {
    	ModelAndView modelAndView = new ModelAndView("productlistpage");
    	WarehouseProductEntity en = new WarehouseProductEntity();
    	modelAndView.addObject("searchWarehouseProductent", en);
    	modelAndView.addObject("searchSelectWarehouse", warehouseService.getAll());
    	modelAndView.addObject("searchSelectProdcut", productService.getAll());
    	modelAndView.addObject("searchSelectCategory", categoryService.getAll());
    	
    	// ürüne gör ise 
    	List<ProductEntity> productlist = new ArrayList();
    	if (search!=null && search.getId()!=null && search.getId()!=0L) {
    		productlist.add(productService.getById(search.getId()));
    	} else if (search!=null && search.getProduct().getCategory()!=null && search.getProduct().getCategory().getId()!=null && search.getProduct().getCategory().getId()!=0L) { // kategoriye göre ise
    		productlist.addAll(productService.getCategoryProducts(search.getProduct().getCategory().getId()));
    	} else { // depoya göre ise
        	List<WarehouseProductEntity> depurun = warehouseProductService.search(search);

    	    for (Iterator iterator = depurun.iterator(); iterator.hasNext();) {
    			WarehouseProductEntity depoUrunEntity = (WarehouseProductEntity) iterator.next();
    			if (productlist.size()==0) {
    				productlist.add(depoUrunEntity.getProduct());
    			} else {
    				productlist.forEach(y->{

    						if (!depoUrunEntity.getProduct().equals(y)) {
    							productlist.add(depoUrunEntity.getProduct());
    						}					

    					 });
    			}
    		}    		
    	}
    	modelAndView.addObject("productlist", productlist);
		return modelAndView;
    }    
    
    // warehouseProduct
    @GetMapping("/warehouseproductlistpage")
    public ModelAndView warehouseproductlistpage() {
    	ModelAndView modelAndView = new ModelAndView("warehouseproductlistpage");
    	modelAndView.addObject("warehouseproductlist", warehouseProductService.getAll());
		return modelAndView;
    }
    @GetMapping("/warehouseproductnewpage")
    public ModelAndView warehouseproductnewpage() {
    	WarehouseProductEntity kt = new WarehouseProductEntity();        
    	ModelAndView modelAndView = new ModelAndView("warehouseproductnewpage");
    	modelAndView.addObject("warehouseproduct", kt);
    	modelAndView.addObject("selectWarehouse", warehouseService.getAll());
    	modelAndView.addObject("selectProduct", productService.getAll());
        return modelAndView;
    }    
    @GetMapping("/warehouseproductupdatepage/{id}")
    public ModelAndView warehouseproductupdatepage(@PathVariable(value = "id") long id) {
    	WarehouseProductEntity ent = warehouseProductService.getById(id);
    	ModelAndView modelAndView = new ModelAndView("warehouseproductupdatepage");
    	modelAndView.addObject("warehouseproduct", ent);
    	modelAndView.addObject("selectWarehouse", warehouseService.getAll());
    	modelAndView.addObject("selectProduct", productService.getAll());
        return modelAndView;
    }
    @PostMapping("/saveWarehouseProduct")
    public ModelAndView saveWarehouseProduct(@ModelAttribute("warehouseproduct") WarehouseProductEntity ent) {
    	warehouseProductService.save(ent);
    	ModelAndView modelAndView = new ModelAndView("warehouseproductlistpage");
    	modelAndView.addObject("warehouseproductlist", warehouseProductService.getAll());
		return modelAndView;
    }
    @PostMapping("/updateWarehouseProduct")
    public ModelAndView updateWarehouseProduct(@ModelAttribute("warehouseproduct") WarehouseProductEntity ent) {
    	warehouseProductService.update(ent.getId(), ent);
    	ModelAndView modelAndView = new ModelAndView("warehouseproductlistpage");
    	modelAndView.addObject("warehouseproductlist", warehouseProductService.getAll());
		return modelAndView;
    }   
    @GetMapping("/deleteWarehouseProduct/{id}")
    public ModelAndView deleteWarehouseProduct(@PathVariable(value = "id") long id) {
    	warehouseProductService.delete(id);
    	ModelAndView modelAndView = new ModelAndView("warehouseproductlistpage");
    	modelAndView.addObject("warehouseproductlist", warehouseProductService.getAll());
        return modelAndView;
    }
   
    
    
    
    
    // inventory
    @GetMapping("/inventorylistpage")
    public ModelAndView inventorylistpage() {
    	ModelAndView modelAndView = new ModelAndView("inventorylistpage");
    	InventoryEntity en = new InventoryEntity();
    	modelAndView.addObject("searchInventoryent", en);
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
    	modelAndView.addObject("productlist", productService.getAll());
    	modelAndView.addObject("categorylist", categoryService.getAll()); 
    	modelAndView.addObject("inventorylist", inventoryService.getAll());
		return modelAndView;
    }   
    
    @PostMapping("/searchInventory")
    public ModelAndView searchInventory(@ModelAttribute("searchInventoryent") InventoryEntity search) {
    	ModelAndView modelAndView = new ModelAndView("inventorylistpage");
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
    	modelAndView.addObject("categorylist", categoryService.getAll());   	
    	modelAndView.addObject("inventorylist", inventoryService.search(search));
		return modelAndView;
    }
    
    @GetMapping("/inventorynewpage")
    public ModelAndView inventorynewpage() {
        InventoryEntity kt = new InventoryEntity();        
    	ModelAndView modelAndView = new ModelAndView("inventorynewpage");
    	modelAndView.addObject("inventory", kt);
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
        return modelAndView;
    }
    
    @GetMapping("/inventoryextractionpage")
    public ModelAndView inventoryextractionpage() {
        InventoryEntity kt = new InventoryEntity();        
    	ModelAndView modelAndView = new ModelAndView("inventoryextractionpage");
    	modelAndView.addObject("inventory", kt);
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
        return modelAndView;
    }

    @GetMapping("/inventoryupdatepage/{id}")
    public ModelAndView inventoryupdatepage(@PathVariable(value = "id") long id) {
    	InventoryEntity ent = inventoryService.getById(id);
    	ModelAndView modelAndView = new ModelAndView("inventoryupdatepage");
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
    	modelAndView.addObject("inventory", ent);
        return modelAndView;
    }
	
    @GetMapping("/deleteInventory/{id}")
    public ModelAndView deleteInventory(@PathVariable(value = "id") long id) {
    	inventoryService.delete(id);
    	ModelAndView modelAndView = new ModelAndView("inventorylistpage");
    	modelAndView.addObject("inventorylist", inventoryService.getAll());

    	InventoryEntity en = new InventoryEntity();
    	modelAndView.addObject("searchInventoryent", en);
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
    	modelAndView.addObject("categorylist", categoryService.getAll());

    	return modelAndView;
    }
	
    @PostMapping("/saveInventory")
    public ModelAndView saveInventory(@ModelAttribute("inventory") InventoryEntity ent) {
		inventoryService.save(ent);
    	ModelAndView modelAndView = new ModelAndView("inventorylistpage");
    	modelAndView.addObject("inventorylist", inventoryService.getAll());
    	
    	InventoryEntity en = new InventoryEntity();
    	modelAndView.addObject("searchInventoryent", en);
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
    	modelAndView.addObject("categorylist", categoryService.getAll());

		return modelAndView;
    }
    
    @PostMapping("/extractionInventory")
    public ModelAndView extractionInventory(@ModelAttribute("inventory") InventoryEntity ent) {
    	inventoryService.extraction(ent);
    	ModelAndView modelAndView = new ModelAndView("inventorylistpage");
    	modelAndView.addObject("inventorylist", inventoryService.getAll());
    	
    	InventoryEntity en = new InventoryEntity();
    	modelAndView.addObject("searchInventoryent", en);
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
    	modelAndView.addObject("categorylist", categoryService.getAll());

		return modelAndView;
    }
    
    @PostMapping("/updateInventory")
    public ModelAndView updateInventory(@ModelAttribute("inventory") InventoryEntity ent) {
		inventoryService.update(ent.getId(), ent);
    	ModelAndView modelAndView = new ModelAndView("inventorylistpage");
    	modelAndView.addObject("inventorylist", inventoryService.getAll());
    	
    	InventoryEntity en = new InventoryEntity();
    	modelAndView.addObject("searchInventoryent", en);
    	modelAndView.addObject("listwarehouse", warehouseService.getAll());
    	modelAndView.addObject("listproduct", productService.getAll());
    	modelAndView.addObject("categorylist", categoryService.getAll());

		return modelAndView;
    }
}
