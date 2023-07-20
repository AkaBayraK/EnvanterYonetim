package com.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.entity.DepoUrunEntity;
import com.hibernate.HibernateMySQLUtil;


@Service
public class DepoUrunServiceImpl implements DepoUrunService {
	
	
	@Override 
	public List<DepoUrunEntity> getAll() {
		Session	ses 		=	null;
		List<DepoUrunEntity> entlist	=	null;
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
	public DepoUrunEntity save(DepoUrunEntity ent) {	
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
	public DepoUrunEntity update(DepoUrunEntity ent) {
		Session	ses 	=	null;
		DepoUrunEntity entdb = new DepoUrunEntity();
		try {
			ses = HibernateMySQLUtil.openSession();
			if (ent!=null && ent.getId()!=null) {
				entdb =  (DepoUrunEntity)ses.get(DepoUrunEntity.class, ent.getId());
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && ent.getId()!=null) {
					ses.beginTransaction();
					
					entdb.setDepoId(ent.getDepoId());
					entdb.setUrunId(ent.getUrunId());
					entdb.setAdet(ent.getAdet());					

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
	public DepoUrunEntity getById(Long id) {
		Session	ses 		=	null;
		DepoUrunEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (DepoUrunEntity)ses.get(DepoUrunEntity.class, id);
			
		} catch (Exception e) {
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return ent;
	}
	
	@Override 
	public DepoUrunEntity delete(long id) {
		Session	ses 		=	null;
		DepoUrunEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (DepoUrunEntity)ses.get(DepoUrunEntity.class, id);
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
