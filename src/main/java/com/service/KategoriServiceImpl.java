package com.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.entity.KategoriEntity;
import com.hibernate.HibernateMySQLUtil;


@Service
public class KategoriServiceImpl implements KategoriService {
	
	
	@Override 
	public List<KategoriEntity> getAll() {
		Session	ses 		=	null;
		List<KategoriEntity> entlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist =  ses.find(null, entlist);
			
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
				entdb =  (KategoriEntity)ses.get(KategoriEntity.class, ent.getId());
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
