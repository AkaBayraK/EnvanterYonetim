package com.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.entity.WarehouseProductEntity;
import com.hibernate.HibernateMySQLUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class WarehouseProductServiceImpl implements WarehouseProductService {
	
	public static Logger LOGGER = LoggerFactory.getLogger(WarehouseProductServiceImpl.class);
	
	@Override
	public List<WarehouseProductEntity> search(WarehouseProductEntity ent) {
		List<WarehouseProductEntity>	result	=	new ArrayList<WarehouseProductEntity>();
		Session	ses	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
			result  = search(ses, ent);
		} catch (Exception e) {
			HibernateMySQLUtil.rollBack(ses);
			result.get(0).getErrorMessages().add(e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return result;
	}
	
	public List<WarehouseProductEntity> search(Session	ses, WarehouseProductEntity ent) {
		List<WarehouseProductEntity>	result	=	new ArrayList<WarehouseProductEntity>();
		try {
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<WarehouseProductEntity> cq = cb.createQuery(WarehouseProductEntity.class);
	        Root<WarehouseProductEntity> kural = cq.from(WarehouseProductEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
	        
	        // Ürün id sine göre filitreler
	        if (ent.getProduct()!=null && ent.getProduct().getId()!=null && ent.getProduct().getId()!=0L) {
				criteria.add(cb.equal(kural.get("product").get("id") , ent.getProduct().getId()));	        	
	        }
	        // arama kriterine kategori de eklenmiş ise filitreler
	        if (ent.getProduct()!=null && ent.getProduct().getCategory()!=null && ent.getProduct().getCategory().getId()!=null && ent.getProduct().getCategory().getId()!=0L) {
				criteria.add(cb.equal(kural.get("product").get("category").get("id") , ent.getProduct().getCategory().getId()));	        	
	        }
	        // arama kriterine depo da eklenmiş ise filitreler
	        if (ent.getWarehouse()!=null && ent.getWarehouse().getId()!=null && ent.getWarehouse().getId()!=0L) {
				criteria.add(cb.equal(kural.get("warehouse").get("id") , ent.getWarehouse().getId()));	        	
	        }
	        // arama kriterine deponun bulunduğu bölge görede eklenmiş ise filitreler
	        if (ent.getWarehouse()!=null && ent.getWarehouse().getRegionName()!=null && !"".equalsIgnoreCase(ent.getWarehouse().getRegionName())) {
				criteria.add(cb.like(kural.get("warehouse").get("regionName") , ent.getWarehouse().getRegionName()));	        	
	        }
	        // arama kriterine deponun bulunduğu şehire görede eklenmiş ise filitreler
	        if (ent.getWarehouse()!=null && ent.getWarehouse().getCityName()!=null && !"".equalsIgnoreCase(ent.getWarehouse().getCityName())) {
				criteria.add(cb.like(kural.get("warehouse").get("cityName") , ent.getWarehouse().getCityName()));	        	
	        }
			cq.select(kural).where(criteria.toArray(new Predicate[]{}));
	        result = ses.createQuery(cq).getResultList();
		} catch (Exception e) {
			result.get(0).getErrorMessages().add(e.getMessage());
			e.printStackTrace();
		} finally {}
        return result;
	}
	
	@Override 
	public List<WarehouseProductEntity> getAll() {
		Session	ses 		=	null;
		List<WarehouseProductEntity> entlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist = HibernateMySQLUtil.loadAllData(WarehouseProductEntity.class, ses);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return entlist;
	}
	
	@Override 
	public WarehouseProductEntity save(WarehouseProductEntity ent) {	
		Session	ses 	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
			// validate konulabilir
			ses.beginTransaction();			

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
	public WarehouseProductEntity update(Long id, WarehouseProductEntity ent) {
		Session	ses 	=	null;
		WarehouseProductEntity entdb = new WarehouseProductEntity();
		try {
			ses = HibernateMySQLUtil.openSession();
			if (id!=null) {
				entdb =  getById(id);
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && entdb.getId()!=null) {
					ses.beginTransaction();
					
					entdb.setWarehouse(ent.getWarehouse());
					entdb.setProduct(ent.getProduct());
					entdb.setPiece(ent.getPiece());					

					ses.merge(entdb);
					ses.evict(entdb);
					
					ses.getTransaction().commit();
				} else {
					// hata verecek
				}
			} else {
				entdb.getErrorMessages().add("DB de güncellenecek data bulunamadı.");
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
	public WarehouseProductEntity getById(Long id) {
		Session	ses 		=	null;
		WarehouseProductEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (WarehouseProductEntity)ses.get(WarehouseProductEntity.class, id);
			
		} catch (Exception e) {
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return ent;
	}
	
	@Override 
	public WarehouseProductEntity delete(Long id) {
		Session	ses 		=	null;
		WarehouseProductEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (WarehouseProductEntity)ses.get(WarehouseProductEntity.class, id);
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
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		} 
		return ent;
	}

}