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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.entity.InventoryEntity;
import com.entity.InventoryHstryEntity;
import com.entity.ProductEntity;
import com.exceptions.ApiException;
import com.hibernate.HibernateMySQLUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class InventoryServiceImpl implements InventoryService {
	
	public static Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

	@Autowired
	ProductServiceImpl productServiceImpl;
	
	public List<InventoryEntity> search(InventoryEntity ent) {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		List<InventoryEntity>	result	=	new ArrayList<InventoryEntity>();
		Session	ses	=	null;
//		try {
			ses = HibernateMySQLUtil.openSession();
			result  = search(ses, ent);
	        if (result == null || result.size()==0) {
	        	HibernateMySQLUtil.close(ses);
	            throw new ApiException(
	                    "Inventory-not-found",
	                    String.format("Inventory with search not found" + ent.toString()),
	                    HttpStatus.NOT_FOUND
	            );
	        } else {
	        	HibernateMySQLUtil.close(ses);
	        }
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			HibernateMySQLUtil.close(ses);
//		}
        return result;
	}
	
	public List<InventoryEntity> search(Session	ses, InventoryEntity ent) {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		List<InventoryEntity>	result	=	new ArrayList<InventoryEntity>();
		try {
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<InventoryEntity> cq = cb.createQuery(InventoryEntity.class);
	        Root<InventoryEntity> kural = cq.from(InventoryEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
	        
	        // Ürün id sine göre filitreler
	        if (ent.getProduct()!=null && ent.getProduct().getId()!=null && ent.getProduct().getId()!=0L) {
				criteria.add(cb.equal(kural.get("product").get("id") , ent.getProduct().getId()));	        	
	        }
	        // arama kriterine kategori de eklenmiş ise filitreler
	        if (ent.getProduct()!=null && ent.getProduct().getCategory()!=null && ent.getProduct().getCategory().getId()!=null && ent.getProduct().getCategory().getId()!=0L) {
	        	criteria.add(cb.equal(kural.get("product").get("category").get("id") , ent.getProduct().getCategory().getId()));	        	
	        }
	        // arama kriterine depo da eklenmiş ise filitreler
	        if (ent.getWarehouse()!=null && ent.getWarehouse().getId()!=null && ent.getWarehouse().getId()!=0L) {
				criteria.add(cb.equal(kural.get("warehouse").get("id") , ent.getWarehouse().getId()));	        	
	        }
	        // arama kriterine deponun bulunduğu bölge görede eklenmiş ise filitreler
	        if (ent.getWarehouse()!=null && ent.getWarehouse().getRegionName()!=null && !"".equalsIgnoreCase(ent.getWarehouse().getRegionName()) ) {
				criteria.add(cb.like(kural.get("warehouse").get("regionName") , ent.getWarehouse().getRegionName()));	        	
	        }
	        // arama kriterine deponun bulunduğu şehire görede eklenmiş ise filitreler
	        if (ent.getWarehouse()!=null && ent.getWarehouse().getCityName()!=null && !"".equalsIgnoreCase(ent.getWarehouse().getCityName()) ) {
				criteria.add(cb.like(kural.get("warehouse").get("cityName") , ent.getWarehouse().getCityName()));	        	
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
	public List<InventoryEntity> getAll() {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		Session	ses 		=	null;
		List<InventoryEntity> entlist	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();
				
				entlist = HibernateMySQLUtil.loadAllData(InventoryEntity.class, ses);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateMySQLUtil.close(ses);
		}
		
        return entlist;
	}
	
	@Override 
	public InventoryEntity save(InventoryEntity ent) {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		Session	ses 	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
			
	    	ent.setErrorMessages(new ArrayList<String>());
	    	ent.setWarningMessages(new ArrayList<String>());
			
			if (ent.getInputDate()==null) {
				ent.getErrorMessages().add("Envantere ürün eklenecek ise giriş tarihi zorunludur.");
				return ent;
			}
			if (ent.getOutDate()!=null) {
				ent.getErrorMessages().add("Envantere ürün ekleme işlemnide cikis tarihi girilmez.");
				return ent;
			}
			
			ses.beginTransaction();			

			ses.persist(ent);
			ses.evict(ent);
			
			// historye insert
			InventoryHstryEntity entHstry = ent.createAutomaticHistory();
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
	public InventoryEntity update(Long id, InventoryEntity ent) {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		Session	ses 	=	null;
		InventoryEntity entdb = new InventoryEntity();
		Format f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ses = HibernateMySQLUtil.openSession();
			
	    	ent.setErrorMessages(new ArrayList<String>());
	    	ent.setWarningMessages(new ArrayList<String>());
	    	
			if (id!=null) {
				entdb =  getById(id);
				// eğer db de var ise update etsin yok ise etmesin hata versin.
				if (entdb!=null && entdb.getId()!=null) {
					if ((ent.getInputDate()==null && entdb.getInputDate()!=null) 
							|| (ent.getInputDate()!=null && entdb.getInputDate()!=null && !f.format(ent.getInputDate()).toString().equals(f.format(entdb.getInputDate()).toString()))
							) {
						ent.getErrorMessages().add("giriş tarihi değiştirilemez.");
						return ent;
					}
					if ((ent.getOutDate()==null && entdb.getOutDate()!=null)
							|| (ent.getOutDate()!=null && entdb.getOutDate()!=null && !f.format(ent.getOutDate()).toString().equals(f.format(entdb.getOutDate()).toString()))
							) {
						ent.getErrorMessages().add("çıkış tarihi değiştirilemez.");
						return ent;
					}					
					ses.beginTransaction();
					entdb.setProduct(ent.getProduct());
					entdb.setWarehouse(ent.getWarehouse());
					entdb.setInputDate(ent.getInputDate());
					entdb.setOutDate(ent.getOutDate());
					entdb.setPiece(ent.getPiece());
					
					ses.merge(entdb);
					ses.evict(entdb);
					// historye insert
					InventoryHstryEntity entHstry = entdb.createAutomaticHistory();
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
	public InventoryEntity extraction(InventoryEntity ent) {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		Session	ses 	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
			
			if (ent.getOutDate()==null) {
				ent.getErrorMessages().add("Envantere ürün çıkarmak için çıkış tarihi zorunludur.");
				return ent;
			}
			if (ent.getInputDate()!=null) {
				ent.getErrorMessages().add("Envantere ürün çıkarma işleminde giriş tarihi girilmez.");
				return ent;
			}
			
			ses.beginTransaction();			

			ses.persist(ent);
			ses.evict(ent);
			
			// historye insert
			InventoryHstryEntity entHstry = ent.createAutomaticHistory();
			ses.persist(entHstry);
			ses.flush();

			// ürün eksiltmesinde adet kontrolü eklenecek, ürün sayısı 10 adetin aşağısına düşer ise uyarı mesajı verecek. WARNING
			
			BigDecimal tplKalanUrunAdeti = BigDecimal.ZERO;
			List<InventoryEntity>	result	=	 getUrunById(ent.getProduct().getId());			
			BigDecimal	girenUrunAdedi = BigDecimal.ZERO;
			BigDecimal	cikanUrunAdedi = BigDecimal.ZERO;
			try {
				girenUrunAdedi = result.stream().filter(x->x.getInputDate()!=null).map(InventoryEntity::getPiece).reduce((a,b)->a.add(b)).get();
			} catch (Exception e) {
				girenUrunAdedi = BigDecimal.ZERO;
			}
			try {
				cikanUrunAdedi = result.stream().filter(x->x.getOutDate()!=null).map(InventoryEntity::getPiece).reduce((a,b)->a.add(b)).get();
			} catch (Exception e) {
				cikanUrunAdedi = BigDecimal.ZERO;
			}
			tplKalanUrunAdeti = (girenUrunAdedi==null?BigDecimal.ZERO:girenUrunAdedi).subtract(cikanUrunAdedi==null?BigDecimal.ZERO:cikanUrunAdedi);
			System.out.println(" Üründe kalan toplam adet sayısı : "+tplKalanUrunAdeti);
			
			//Urun ı sine göre envanterdeki tüm listesi çek ve girş tarihi ürünün giren adet sayisi , cikis tarihi olanlar ise ürünün cikan adet sayisi , aradaki farkta kalanı versin.
			//java 8 deki sortu kullan
			try {
				ProductEntity urun = productServiceImpl.getById(ent.getProduct().getId());				
				if (tplKalanUrunAdeti.compareTo(urun.getMinPiece())==-1) {
					ent.getWarningMessages().add(" Üründe kalan toplam adet sayısı : "+tplKalanUrunAdeti);
					System.out.println(" Üründe kalan toplam adet sayısı : "+tplKalanUrunAdeti);
				}				
			} catch (Exception e) {
				ent.getErrorMessages().add(" Ürüne ait min Adet sayı bilgisi belirlenemedi. URUN_ID : "+ent.getProduct().getId()+ " EXCEPTION : "+ e.getMessage());
				e.printStackTrace();
			}

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
	public InventoryEntity getById(Long id) {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		Session	ses 		=	null;
		InventoryEntity	ent	=	null;
//		try {
			ses = HibernateMySQLUtil.openSession();			
			ent =  (InventoryEntity)ses.get(InventoryEntity.class, id);
	        if (ent == null) {
	        	HibernateMySQLUtil.close(ses);
	            throw new ApiException(
	                    "Inventory-not-found",
	                    String.format("Inventory with id=%d not found", id),
	                    HttpStatus.NOT_FOUND
	            );
	        } else {
	        	HibernateMySQLUtil.close(ses);
	        }
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			HibernateMySQLUtil.close(ses);
//		}
        return ent;
	}
	
	public List<InventoryEntity> getUrunById(Long id) {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		List<InventoryEntity>	result	=	new ArrayList<InventoryEntity>();
		Session	ses	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<InventoryEntity> cq = cb.createQuery(InventoryEntity.class);
	        Root<InventoryEntity> kural = cq.from(InventoryEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.equal(kural.get("product").get("id"), id));
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
	public InventoryEntity delete(Long id) {
		LOGGER.info(InventoryEntity.class.getSimpleName()+"_"+Thread.currentThread().getStackTrace()[2].getMethodName());
		Session	ses 		=	null;
		InventoryEntity	ent	=	null;
		try {
				ses = HibernateMySQLUtil.openSession();			
				ent =  (InventoryEntity)ses.get(InventoryEntity.class, id);
				// eğer db de var ise kaydı bulsun ve transection açsın ve silsin
				if (ent!=null && ent.getId()!=null) {
					ses.beginTransaction();
					
					// historye insert
					InventoryHstryEntity entHstry = ent.createAutomaticHistory();
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