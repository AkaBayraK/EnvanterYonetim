package com.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.entity.UrunEntity;
import com.hibernate.HibernateMySQLUtil;


@Service
public class UrunServiceImpl implements UrunService {
	
	
	@Override 
	public List<UrunEntity> getAll() {
		Session	ses 		=	null;
		List<UrunEntity> urunlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				urunlist =  ses.find(null, urunlist);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return urunlist;
	}

	
	@Override 
	public UrunEntity save(UrunEntity ent) {	
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
	public UrunEntity update(UrunEntity urun) {
		Session	ses 	=	null;
		UrunEntity urundb = new UrunEntity();
		try {
			ses = HibernateMySQLUtil.openSession();
			if (urun!=null && urun.getId()!=null) {
				urundb =  (UrunEntity)ses.get(UrunEntity.class, urun.getId());
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (urundb!=null && urun.getId()!=null) {
					ses.beginTransaction();
					
					urundb.setAdi(urun.getAdi());
					urundb.setKategoriId(urun.getKategoriId());
					urundb.setAdet(urun.getAdet());


					ses.merge(urundb);
					ses.evict(urundb);
					
					ses.getTransaction().commit();
				} else {
					urundb.getErrorMessages().add("DB den güncellenecek data bulunamadı");
				}
			} else {
				urun.getErrorMessages().add("gelen ürün bilgisi hatalı....");
			}

		} catch (Exception e) {
			urun.getErrorMessages().add(e.getMessage());
			ses.getTransaction().rollback();
			e.printStackTrace();
		} finally{
			HibernateMySQLUtil.close(ses);
		}
		return urundb;
    }
	 
	@Override 
	public UrunEntity getById(Long id) {
		Session	ses 		=	null;
		UrunEntity	urun	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				urun =  (UrunEntity)ses.get(UrunEntity.class, id);
			
		} catch (Exception e) {
			urun.getErrorMessages().add(e.getMessage());
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return urun;
	}
	
	@Override 
	public UrunEntity delete(long id) {
		Session	ses 		=	null;
		UrunEntity	urun	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				urun =  (UrunEntity)ses.get(UrunEntity.class, id);
				// eğer db de var ise kaydı bulsun ve transection açsın ve silsin
				if (urun!=null && urun.getId()!=null) {
					ses.beginTransaction();
					ses.remove(urun);
					ses.flush();
					ses.getTransaction().commit();
				} else {
					urun.getErrorMessages().add("DB den silinecek data bulunamadı");
				}
		} catch (Exception e) {
			urun.getErrorMessages().add(e.getMessage());
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		} 
		return urun;
	}


}
