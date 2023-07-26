package com.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.entity.CategoryEntity;
import com.hibernate.HibernateMySQLUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	public static Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	public List<CategoryEntity> search(CategoryEntity ent) {
		Session	ses 		=	null;
		List<CategoryEntity>	result	=	new ArrayList<CategoryEntity>();
		try {
			ses = HibernateMySQLUtil.openSession();
			result = search(ses, ent);
		} catch (Exception e) {
			result.get(0).getErrorMessages().add(e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return result;
	}	
	public List<CategoryEntity> search(Session	ses, CategoryEntity ent) {
		List<CategoryEntity>	result	=	new ArrayList<CategoryEntity>();
		try {
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<CategoryEntity> cq = cb.createQuery(CategoryEntity.class);
	        Root<CategoryEntity> kural = cq.from(CategoryEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
	        
	        if (ent!=null && ent.getId()!=null) {
				criteria.add(cb.equal(kural.get("id") , ent.getId()));	        	
	        }
	        if (ent!=null && ent.getName()!=null) {
				criteria.add(cb.like(kural.get("name") , ent.getName()));	        	
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
	public List<CategoryEntity> getAll() {
		Session	ses 		=	null;
		List<CategoryEntity> entlist	=	new ArrayList<CategoryEntity>();
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist = HibernateMySQLUtil.loadAllData(CategoryEntity.class, ses);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return entlist;
	}

	@Override 
	public CategoryEntity save(CategoryEntity ent) {	
		Session	ses 	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
			
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
	public CategoryEntity update(Long id, CategoryEntity ent) {
		Session	ses 	=	null;
		CategoryEntity entdb = new CategoryEntity();
		try {
			ses = HibernateMySQLUtil.openSession();
			if (id!=null) {
				
				entdb = getById(id);
				
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && entdb.getId()!=null) {
					ses.beginTransaction();
					
					entdb.setName(ent.getName());

					ses.merge(entdb);
					ses.evict(entdb);
					
					ses.getTransaction().commit();
				} else {
					// hata verecek
				}
			} else {
				// hata ver
			}

		} catch (Exception e) {
			ent.getErrorMessages().add(e.getMessage());
			ses.getTransaction().rollback();
			e.printStackTrace();
		} finally{
			HibernateMySQLUtil.close(ses);
		}
		return entdb;
    }
	 
	@Override 
	public CategoryEntity getById(Long id) {
		Session	ses 		=	null;
		CategoryEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (CategoryEntity)ses.get(CategoryEntity.class, id);
			
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
	public CategoryEntity delete(Long id) {
		Session	ses 		=	null;
		CategoryEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (CategoryEntity)ses.get(CategoryEntity.class, id);
				// eğer db de var ise kaydı bulsun ve transection açsın ve silsin
				if (ent!=null && ent.getId()!=null) {
					ses.beginTransaction();
					ses.remove(ent);
					ses.flush();
					ses.getTransaction().commit();
				} else {
					
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