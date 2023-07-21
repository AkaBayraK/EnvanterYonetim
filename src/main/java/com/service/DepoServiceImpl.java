package com.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.entity.DepoEntity;
import com.hibernate.HibernateMySQLUtil;


@Service
public class DepoServiceImpl implements DepoService {
	
	
	@Override 
	public List<DepoEntity> getAll() {
		Session	ses 		=	null;
		List<DepoEntity> entlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist = HibernateMySQLUtil.loadAllData(DepoEntity.class, ses);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return entlist;
	}

	
	@Override 
	public DepoEntity save(DepoEntity ent) {	
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
	public DepoEntity update(DepoEntity ent) {
		Session	ses 	=	null;
		DepoEntity entdb = new DepoEntity();
		try {
			ses = HibernateMySQLUtil.openSession();
			if (ent!=null && ent.getId()!=null) {
				entdb = getById(ent.getId());
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && ent.getId()!=null) {
					ses.beginTransaction();
					
					entdb.setAdi(ent.getAdi());
					entdb.setBolgeAdi(ent.getBolgeAdi());
					entdb.setIlAdi(ent.getIlAdi());

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
			ent.getErrorMessages().add(e.getMessage());
			ses.getTransaction().rollback();
			e.printStackTrace();
		} finally{
			HibernateMySQLUtil.close(ses);
		}
		return entdb;
    }
	 
	@Override 
	public DepoEntity getById(Long id) {
		Session	ses 		=	null;
		DepoEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (DepoEntity)ses.get(DepoEntity.class, id);
			
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
	public DepoEntity delete(long id) {
		Session	ses 		=	null;
		DepoEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (DepoEntity)ses.get(DepoEntity.class, id);
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
