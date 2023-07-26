package com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.WarehouseProductEntity;
import com.entity.ProductEntity;
import com.hibernate.HibernateMySQLUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class ProductServiceImpl implements ProductService {
	
	public static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
    private WarehouseProductServiceImpl warehouseProductServiceImpl;
	
	@Override 
	public List<WarehouseProductEntity> getProductWarehouses(Long productId) {
		Session	ses 		=	null;
		List<WarehouseProductEntity> productlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();				
				WarehouseProductEntity ent = new WarehouseProductEntity();
				ent.setProduct(new ProductEntity());
				ent.getProduct().setId(productId);			
				productlist = warehouseProductServiceImpl.search(ses, ent);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return productlist;
	}
	
	@Override 
	public List<ProductEntity> getCategoryProducts(Long id) {
		List<ProductEntity>	result	=	new ArrayList<ProductEntity>();
		Session	ses	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<ProductEntity> cq = cb.createQuery(ProductEntity.class);
	        Root<ProductEntity> kural = cq.from(ProductEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.equal(kural.get("category").get("id"), id));
	        cq.select(kural).where(criteria.toArray(new Predicate[]{}));
	        result = ses.createQuery(cq).getResultList();
		} catch (Exception e) {
			HibernateMySQLUtil.rollBack(ses);
			result.get(0).getErrorMessages().add(e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return result;
	}
	
	@Override 
	public List<ProductEntity> getAll() {
		Session	ses 		=	null;
		List<ProductEntity> productlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();				
				productlist = HibernateMySQLUtil.loadAllData(ProductEntity.class, ses);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return productlist;
	}
	
	@Override 
	public ProductEntity save(ProductEntity ent) {	
		Session	ses 	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();			
			ses.beginTransaction();
			ent.setMinPiece(ent.getPiece().divide(new BigDecimal(3),0,BigDecimal.ROUND_DOWN));
			ses.persist(ent);
			ses.evict(ent);
			ses.getTransaction().commit();
		} catch (Exception e) {
			ent.getErrorMessages().add(e.getMessage());
			ses.getTransaction().rollback();
			e.printStackTrace();
		} finally{
			HibernateMySQLUtil.close(ses);
		}
		return ent;
	}
	
	@Override 
	public ProductEntity update(Long id, ProductEntity ent) {
		Session	ses 	=	null;
		ProductEntity entdb = new ProductEntity();
		try {
			ses = HibernateMySQLUtil.openSession();
			if (id!=null) {
				entdb = getById(id);
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && entdb.getId()!=null) {
					ses.beginTransaction();					
					entdb.setName(ent.getName());
					entdb.setCategory(ent.getCategory());
					entdb.setPiece(ent.getPiece());
					entdb.setMinPiece(ent.getPiece().divide(new BigDecimal(3),0,BigDecimal.ROUND_DOWN));
					ses.merge(entdb);
					ses.evict(entdb);					
					ses.getTransaction().commit();
				} else {
					entdb.getErrorMessages().add("DB den güncellenecek data bulunamadı");
				}
			} else {
				entdb.getErrorMessages().add("gelen ürün bilgisi hatalı....");
			}

		} catch (Exception e) {
			entdb.getErrorMessages().add(e.getMessage());
			ses.getTransaction().rollback();
			e.printStackTrace();
		} finally{
			HibernateMySQLUtil.close(ses);
		}
		return entdb;
    }
	 
	@Override 
	public ProductEntity getById(Long id) {
		Session	ses 		=	null;
		ProductEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (ProductEntity)ses.get(ProductEntity.class, id);			
		} catch (Exception e) {
			ent.getErrorMessages().add(e.getMessage());
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return ent;
	}
	
	@Override 
	public ProductEntity delete(Long id) {
		Session	ses 		=	null;
		ProductEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (ProductEntity)ses.get(ProductEntity.class, id);
				// eğer db de var ise kaydı bulsun ve transection açsın ve silsin
				if (ent!=null && ent.getId()!=null) {
					ses.beginTransaction();
					ses.remove(ent);
					ses.flush();
					ses.getTransaction().commit();
				} else {
					ent.getErrorMessages().add("DB den silinecek data bulunamadı.");
				}
		} catch (Exception e) {
			ent.getErrorMessages().add(e.getMessage());
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		} 
		return ent;
	}

}