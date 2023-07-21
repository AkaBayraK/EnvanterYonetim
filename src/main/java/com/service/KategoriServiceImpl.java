package com.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.entity.KategoriEntity;
import com.hibernate.HibernateMySQLUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class KategoriServiceImpl implements KategoriService {

	public List<KategoriEntity> search(KategoriEntity ent) {
		Session	ses 		=	null;
		List<KategoriEntity>	result	=	new ArrayList<KategoriEntity>();
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
	public List<KategoriEntity> search(Session	ses, KategoriEntity ent) {
		List<KategoriEntity>	result	=	new ArrayList<KategoriEntity>();
		try {
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<KategoriEntity> cq = cb.createQuery(KategoriEntity.class);
	        Root<KategoriEntity> kural = cq.from(KategoriEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
	        
	        if (ent!=null && ent.getId()!=null) {
				criteria.add(cb.equal(kural.get("id") , ent.getId()));	        	
	        }
	        if (ent!=null && ent.getAdi()!=null) {
				criteria.add(cb.like(kural.get("adi") , ent.getAdi()));	        	
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
	public List<KategoriEntity> getAll() {
		Session	ses 		=	null;
		List<KategoriEntity> entlist	=	new ArrayList<KategoriEntity>();
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist = HibernateMySQLUtil.loadAllData(KategoriEntity.class, ses);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return entlist;
	}

	
	@Override 
	public KategoriEntity save(KategoriEntity ent) {	
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
	public KategoriEntity update(KategoriEntity ent) {
		Session	ses 	=	null;
		KategoriEntity entdb = new KategoriEntity();
		try {
			ses = HibernateMySQLUtil.openSession();
			if (ent!=null && ent.getId()!=null) {
				
				entdb = getById(ent.getId());
				
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && ent.getId()!=null) {
					ses.beginTransaction();
					
					entdb.setAdi(ent.getAdi());

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
	public KategoriEntity getById(Long id) {
		Session	ses 		=	null;
		KategoriEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (KategoriEntity)ses.get(KategoriEntity.class, id);
			
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
	public KategoriEntity delete(long id) {
		Session	ses 		=	null;
		KategoriEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (KategoriEntity)ses.get(KategoriEntity.class, id);
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
