package com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.DepoUrunEntity;
import com.entity.UrunEntity;
import com.hibernate.HibernateMySQLUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class UrunServiceImpl implements UrunService {
	
	@Autowired
    private DepoUrunServiceImpl depoUrunService;
	
	public List<DepoUrunEntity> depolar(Long urunId) {
		Session	ses 		=	null;
		List<DepoUrunEntity> urunlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				DepoUrunEntity depoUrunEnt = new DepoUrunEntity();
				depoUrunEnt.setUrun(new UrunEntity());
				depoUrunEnt.getUrun().setId(urunId);
				
				urunlist = depoUrunService.search(ses, depoUrunEnt);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return urunlist;
	}
	
	public List<UrunEntity> getKategoriById(Long id) {
		List<UrunEntity>	result	=	new ArrayList<UrunEntity>();
		Session	ses	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<UrunEntity> cq = cb.createQuery(UrunEntity.class);
	        Root<UrunEntity> kural = cq.from(UrunEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.equal(kural.get("kategori").get("id"), id));
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
	public List<UrunEntity> getAll() {
		Session	ses 		=	null;
		List<UrunEntity> urunlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				urunlist = HibernateMySQLUtil.loadAllData(UrunEntity.class, ses);
			
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
			// 1/3		
			ent.setMinAdet(ent.getAdet().divide(new BigDecimal(3),0,BigDecimal.ROUND_DOWN));
			
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
				urundb = getById(urun.getId());
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (urundb!=null && urun.getId()!=null) {
					ses.beginTransaction();
					
					urundb.setAdi(urun.getAdi());
					urundb.setKategori(urun.getKategori());
					urundb.setAdet(urun.getAdet());
					// 1/3		
					urundb.setMinAdet(urun.getAdet().divide(new BigDecimal(3),0,BigDecimal.ROUND_DOWN));

					ses.merge(urundb);
					ses.evict(urundb);
					
					ses.getTransaction().commit();
				} else {
					urundb.getErrorMessages().add("DB den güncellenecek data bulunamadı");
				}
			} else {
				urundb.getErrorMessages().add("gelen ürün bilgisi hatalı....");
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
					urun.getErrorMessages().add("DB den silinecek data bulunamadı.");
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
