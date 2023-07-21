package com.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.entity.DepoUrunEntity;
import com.hibernate.HibernateMySQLUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class DepoUrunServiceImpl implements DepoUrunService {
	
	public List<DepoUrunEntity> search(DepoUrunEntity ent) {
		List<DepoUrunEntity>	result	=	new ArrayList<DepoUrunEntity>();
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
	
	public List<DepoUrunEntity> search(Session	ses, DepoUrunEntity ent) {
		List<DepoUrunEntity>	result	=	new ArrayList<DepoUrunEntity>();
		try {
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<DepoUrunEntity> cq = cb.createQuery(DepoUrunEntity.class);
	        Root<DepoUrunEntity> kural = cq.from(DepoUrunEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
	        
	        // Ürün id sine göre filitreler
	        if (ent.getUrun()!=null && ent.getUrun().getId()!=null && ent.getUrun().getId()!=0L) {
				criteria.add(cb.equal(kural.get("urun").get("id") , ent.getUrun().getId()));	        	
	        }
	        // arama kriterine kategori de eklenmiş ise filitreler
	        if (ent.getUrun()!=null && ent.getUrun().getKategori()!=null && ent.getUrun().getKategori().getId()!=null && ent.getUrun().getKategori().getId()!=0L) {
				criteria.add(cb.equal(kural.get("urun").get("kategori").get("id") , ent.getUrun().getKategori().getId()));	        	
	        }
	        // arama kriterine depo da eklenmiş ise filitreler
	        if (ent.getDepo()!=null && ent.getDepo().getId()!=null && ent.getDepo().getId()!=0L) {
				criteria.add(cb.equal(kural.get("depo").get("id") , ent.getDepo().getId()));	        	
	        }
	        // arama kriterine deponun bulunduğu bölge görede eklenmiş ise filitreler
	        if (ent.getDepo()!=null && ent.getDepo().getBolgeAdi()!=null && !"".equalsIgnoreCase(ent.getDepo().getBolgeAdi())) {
				criteria.add(cb.like(kural.get("depo").get("bolgeAdi") , ent.getDepo().getBolgeAdi()));	        	
	        }
	        // arama kriterine deponun bulunduğu şehire görede eklenmiş ise filitreler
	        if (ent.getDepo()!=null && ent.getDepo().getIlAdi()!=null && !"".equalsIgnoreCase(ent.getDepo().getIlAdi())) {
				criteria.add(cb.like(kural.get("depo").get("ilAdi") , ent.getDepo().getIlAdi()));	        	
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
	public List<DepoUrunEntity> getAll() {
		Session	ses 		=	null;
		List<DepoUrunEntity> entlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist = HibernateMySQLUtil.loadAllData(DepoUrunEntity.class, ses);
			
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
				entdb =  getById(ent.getId());
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && ent.getId()!=null) {
					ses.beginTransaction();
					
					entdb.setDepo(ent.getDepo());
					entdb.setUrun(ent.getUrun());
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
