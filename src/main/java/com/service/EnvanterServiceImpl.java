package com.service;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.EnvanterEntity;
import com.entity.EnvanterHstryEntity;
import com.entity.UrunEntity;
import com.hibernate.HibernateMySQLUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class EnvanterServiceImpl implements EnvanterService {
	
	public static Logger LOGGER = LoggerFactory.getLogger(EnvanterServiceImpl.class);
	
	@Autowired
    private UrunServiceImpl UrunServiceImpl;
	
	public List<EnvanterEntity> search(EnvanterEntity ent) {
		List<EnvanterEntity>	result	=	new ArrayList<EnvanterEntity>();
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
	
	public List<EnvanterEntity> search(Session	ses, EnvanterEntity ent) {
		List<EnvanterEntity>	result	=	new ArrayList<EnvanterEntity>();
		try {
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<EnvanterEntity> cq = cb.createQuery(EnvanterEntity.class);
	        Root<EnvanterEntity> kural = cq.from(EnvanterEntity.class);
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
	        if (ent.getDepo()!=null && ent.getDepo().getBolgeAdi()!=null && !"".equalsIgnoreCase(ent.getDepo().getBolgeAdi()) ) {
				criteria.add(cb.like(kural.get("depo").get("bolgeAdi") , ent.getDepo().getBolgeAdi()));	        	
	        }
	        // arama kriterine deponun bulunduğu şehire görede eklenmiş ise filitreler
	        if (ent.getDepo()!=null && ent.getDepo().getIlAdi()!=null && !"".equalsIgnoreCase(ent.getDepo().getIlAdi()) ) {
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
	public List<EnvanterEntity> getAll() {
		Session	ses 		=	null;
		List<EnvanterEntity> entlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist = HibernateMySQLUtil.loadAllData(EnvanterEntity.class, ses);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return entlist;
	}
	
	@Override 
	public EnvanterEntity save(EnvanterEntity ent) {
		LOGGER.info("save" + "-" + ent.getEntityClass());
		Session	ses 	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
			
	    	ent.setErrorMessages(new ArrayList<String>());
	    	ent.setWarningMessages(new ArrayList<String>());
			
			if (ent.getGirisTrh()==null) {
				ent.getErrorMessages().add("Envantere ürün eklenecek ise giriş tarihi zorunludur.");
				return ent;
			}
			if (ent.getCikisTrh()!=null) {
				ent.getErrorMessages().add("Envantere ürün ekleme işlemnide cikis tarihi girilmez.");
				return ent;
			}
			
			ses.beginTransaction();			

			ses.persist(ent);
			ses.evict(ent);
			
			// historye insert
			EnvanterHstryEntity entHstry = ent.createAutomaticHistory();
			ses.persist(entHstry);
			ses.flush();
			
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
	public EnvanterEntity update(EnvanterEntity ent) {
		Session	ses 	=	null;
		EnvanterEntity entdb = new EnvanterEntity();
		Format f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ses = HibernateMySQLUtil.openSession();
			
	    	ent.setErrorMessages(new ArrayList<String>());
	    	ent.setWarningMessages(new ArrayList<String>());
	    	
			if (ent!=null && ent.getId()!=null) {
				entdb =  getById(ent.getId());
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && ent.getId()!=null) {
					/*
					String girisDate = ent.getGirisTrh()==null?"":f.format(ent.getGirisTrh()).toString();
					System.out.println("Current Date = "+girisDate);
					
					f = new SimpleDateFormat("yyyy-MM-dd");
					String cikisDate = f.format(ent.getGirisTrh());
					System.out.println("Current Date = "+cikisDate);
					*/
					if ((ent.getGirisTrh()==null && entdb.getGirisTrh()!=null) 
							|| (ent.getGirisTrh()!=null && entdb.getGirisTrh()!=null && !f.format(ent.getGirisTrh()).toString().equals(f.format(entdb.getGirisTrh()).toString()))
							) {
						ent.getErrorMessages().add("giriş tarihi değiştirilemez.");
						return ent;
					}
					if ((ent.getCikisTrh()==null && entdb.getCikisTrh()!=null)
							|| (ent.getCikisTrh()!=null && entdb.getCikisTrh()!=null && !f.format(ent.getCikisTrh()).toString().equals(f.format(entdb.getCikisTrh()).toString()))
							) {
						ent.getErrorMessages().add("çıkış tarihi değiştirilemez.");
						return ent;
					}					
					ses.beginTransaction();
					
					entdb.setUrun(ent.getUrun());
					entdb.setDepo(ent.getDepo());
					entdb.setGirisTrh(ent.getGirisTrh());
					entdb.setCikisTrh(ent.getCikisTrh());
					entdb.setAdet(ent.getAdet());
					
					ses.merge(entdb);
					ses.evict(entdb);
					// historye insert
					EnvanterHstryEntity entHstry = ent.createAutomaticHistory();
					ses.persist(entHstry);
					ses.flush();
					
					
					ses.getTransaction().commit();
				} else {
					ent.getErrorMessages().add("db de kayıt yok.");
				}
			} else {
				ent.getErrorMessages().add("ent boş.");
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
	
	/**
	 * Envanterden çıkartma işlemi yapılabilmelidir. 
	 * Her bir ürün için bir kritik eşik belirlenmeli ve envanterden çıkartma işlemi yapıldıktan sonra eğer o eşiğin altına düşerse mail atma işlemi yapılmalıdır(mail atmak opsiyoneldir, log da basılabilir.) 
	 * @param ent
	 * @return
	 */
	public EnvanterEntity extraction(EnvanterEntity ent) {
		LOGGER.info("extraction" + "-" + ent.getEntityClass());
		Session	ses 	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
			
			if (ent.getCikisTrh()==null) {
				ent.getErrorMessages().add("Envantere ürün çıkarmak için çıkış tarihi zorunludur.");
				return ent;
			}
			if (ent.getGirisTrh()!=null) {
				ent.getErrorMessages().add("Envantere ürün çıkarma işleminde giriş tarihi girilmez.");
				return ent;
			}
			
			ses.beginTransaction();			

			ses.persist(ent);
			ses.evict(ent);
			
			// historye insert
			EnvanterHstryEntity entHstry = ent.createAutomaticHistory();
			ses.persist(entHstry);
			ses.flush();

			ses.getTransaction().commit();

			// ürün eksiltmesinde adet kontrolü eklenecek, ürün sayısı 10 adetin aşağısına düşer ise uyarı mesajı verecek. WARNING
			
			BigDecimal tplKalanUrunAdeti = BigDecimal.ZERO;
			List<EnvanterEntity>	result	=	 getUrunById(ent.getUrun().getId());			
			BigDecimal	girenUrunAdedi = BigDecimal.ZERO;
			BigDecimal	cikanUrunAdedi = BigDecimal.ZERO;
			try {
				girenUrunAdedi = result.stream().filter(x->x.getGirisTrh()!=null).map(EnvanterEntity::getAdet).reduce((a,b)->a.add(b)).get();
			} catch (Exception e) {
				girenUrunAdedi = BigDecimal.ZERO;
			}
			try {
				cikanUrunAdedi = result.stream().filter(x->x.getCikisTrh()!=null).map(EnvanterEntity::getAdet).reduce((a,b)->a.add(b)).get();
			} catch (Exception e) {
				cikanUrunAdedi = BigDecimal.ZERO;
			}
			tplKalanUrunAdeti = (girenUrunAdedi==null?BigDecimal.ZERO:girenUrunAdedi).subtract(cikanUrunAdedi==null?BigDecimal.ZERO:cikanUrunAdedi);
			System.out.println(" Üründe kalan toplam adet sayısı : "+tplKalanUrunAdeti);
			
			//Urun ı sine göre envanterdeki tüm listesi çek ve girş tarihi ürünün giren adet sayisi , cikis tarihi olanlar ise ürünün cikan adet sayisi , aradaki farkta kalanı versin.
			//java 8 deki sortu kullan
			try {
				UrunEntity urun = UrunServiceImpl.getById(ent.getUrun().getId());				
				if (tplKalanUrunAdeti.compareTo(urun.getMinAdet())==-1) {
					ent.getWarningMessages().add(" Üründe kalan toplam adet sayısı : "+tplKalanUrunAdeti);
					System.out.println(" Üründe kalan toplam adet sayısı : "+tplKalanUrunAdeti);
				}				
			} catch (Exception e) {
				ent.getErrorMessages().add(" Ürüne ait min Adet sayı bilgisi belirlenemedi. URUN_ID : "+ent.getUrun().getId()+ " EXCEPTION : "+ e.getMessage());
				e.printStackTrace();
			}

			
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
	public EnvanterEntity getById(Long id) {
		Session	ses 		=	null;
		EnvanterEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (EnvanterEntity)ses.get(EnvanterEntity.class, id);
			
		} catch (Exception e) {
			ent.getErrorMessages().add(e.getMessage());
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
        return ent;
	}
	
	public List<EnvanterEntity> getUrunById(Long id) {
		List<EnvanterEntity>	result	=	new ArrayList<EnvanterEntity>();
		Session	ses	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<EnvanterEntity> cq = cb.createQuery(EnvanterEntity.class);
	        Root<EnvanterEntity> kural = cq.from(EnvanterEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.equal(kural.get("urun").get("id"), id));
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
	public EnvanterEntity delete(long id) {
		Session	ses 		=	null;
		EnvanterEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (EnvanterEntity)ses.get(EnvanterEntity.class, id);
				// eğer db de var ise kaydı bulsun ve transection açsın ve silsin
				if (ent!=null && ent.getId()!=null) {
					ses.beginTransaction();
					
					// historye insert
					EnvanterHstryEntity entHstry = ent.createAutomaticHistory();
					ses.persist(entHstry);
					ses.flush();
					
					ses.remove(ent);
					ses.flush();
					ses.getTransaction().commit();
				} else {
					ent.getErrorMessages().add("db de silinecek data yok.");
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
