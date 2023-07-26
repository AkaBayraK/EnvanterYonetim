package com.service;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.entity.WarehouseEntity;
import com.hibernate.HibernateMySQLUtil;


@Service
public class WarehouseServiceImpl implements WarehouseService {
	
	public static Logger LOGGER = LoggerFactory.getLogger(WarehouseServiceImpl.class);
		
	@Override 
	public List<WarehouseEntity> getAll() {
		Session	ses 		=	null;
		List<WarehouseEntity> entlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist = HibernateMySQLUtil.loadAllData(WarehouseEntity.class, ses);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return entlist;
	}
	
	@Override 
	public WarehouseEntity save(WarehouseEntity ent) {	
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
	public WarehouseEntity update(Long id, WarehouseEntity ent) {
		Session	ses 	=	null;
		WarehouseEntity entdb = new WarehouseEntity();
		try {
			ses = HibernateMySQLUtil.openSession();
			if (id!=null) {
				entdb = getById(id);
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && entdb.getId()!=null) {
					ses.beginTransaction();
					
					entdb.setName(ent.getName());
					entdb.setRegionName(ent.getRegionName());
					entdb.setCityName(ent.getCityName());

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
	public WarehouseEntity getById(Long id) {
		Session	ses 		=	null;
		WarehouseEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (WarehouseEntity)ses.get(WarehouseEntity.class, id);
			
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
	public WarehouseEntity delete(Long id) {
		Session		ses	=	null;
		WarehouseEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (WarehouseEntity)ses.get(WarehouseEntity.class, id);
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