package com.hibernate;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.EnvanterEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class HibernateMySQLUtil {
	
	
	public static void main(String[] args) {
		Session	ses	=	null;
		try {
			ses	=	HibernateMySQLUtil.openSession();
//			
//			
//			ses.beginTransaction();
			
			List<EnvanterEntity>	result = null;			
			BigDecimal tplKalanUrunAdeti = BigDecimal.ZERO;
//			List<EnvanterEntity>	result = HibernateMySQLUtil.loadEntityListByNamedQuery(EnvanterEntity.class, EnvanterEntity.FIND_ENVANTER_URUN_ALL,new Long[] {1L});		
			
		
	        CriteriaBuilder cb = ses.getCriteriaBuilder();
	        CriteriaQuery<EnvanterEntity> cq = cb.createQuery(EnvanterEntity.class);
	        Root<EnvanterEntity> kural = cq.from(EnvanterEntity.class);
	        List<Predicate> criteria = new ArrayList<Predicate>();
			criteria.add(cb.equal(kural.get("urunId"), 1L));
	        cq.select(kural).where(criteria.toArray(new Predicate[]{}));
	        result = ses.createQuery(cq).getResultList();
	        ses.close();
	        
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
			tplKalanUrunAdeti = (girenUrunAdedi==null?BigDecimal.ZERO:girenUrunAdedi).add(cikanUrunAdedi==null?BigDecimal.ZERO:cikanUrunAdedi);
			
			
			//Urun ı sine göre envanterdeki tüm listesi çek ve girş tarihi ürünün giren adet sayisi , cikis tarihi olanlar ise ürünün cikan adet sayisi , aradaki farkta kalanı versin.
			//java 8 deki sortu kullan
			System.out.println(" Üründe kalan toplam adet sayısı : "+tplKalanUrunAdeti);
			if (tplKalanUrunAdeti.compareTo(new BigDecimal(10))==-1) {
				System.out.println(" Üründe kalan toplam adet sayısı : "+tplKalanUrunAdeti);
			}
			
			
//			ses.merge();
//			ses.evict();
			
//			ses.getTransaction().commit();
			
		} catch (Throwable e) {
//			ses.getTransaction().rollback();
			HibernateMySQLUtil.rollBack(ses);
			e.printStackTrace();
		}finally {
			HibernateMySQLUtil.close(ses);
			System.out.println("islem tamamlandi.");
			System.exit(1);
		}
		
		
		
	}
	
	protected static Logger logger = LoggerFactory.getLogger(HibernateMySQLUtil.class);
	private final static Set<Class<?>> classes;
	private final static ArrayList<String> resources;

	static {
		classes = new HashSet<Class<?>>();
		resources = new ArrayList<String>();
		Reflections reflections = new Reflections(new ConfigurationBuilder()
		.addUrls(ClasspathHelper.forClassLoader())
		.addUrls(ClasspathHelper.forJavaClassPath())
		.setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner(), new ResourcesScanner()));

		Set<Class<?>> _class = reflections.getTypesAnnotatedWith(Entity.class);
		for (final Class<?> clazz : _class) {
			classes.add(clazz);
		}

	}

	private static StandardServiceRegistry registry;
	private static BufferedReader br;
	
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/envanterDB?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "none"); /* ENTİTY deki colonlara göre tablo drop edip tekrardan create eder*/

                configuration.setProperties(settings);

                //configuration.addAnnotatedClass(Student.class);
				for (final Class<?> clazz : classes) {
					configuration.addAnnotatedClass(clazz);
				}
				 for (final String str : resources) {
		            configuration.addResource(str);
		        }

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    
	public static Session openSession() {
		Session session	=	HibernateMySQLUtil.getSessionFactory().openSession();
		return session;
	}
	
	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	public static void rollBack(Session session) {
		try {
			if (session != null) {
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Session session) throws HibernateException {
		try {
			if (session != null) {
				if (session.isOpen()) {
					try {
						// rollback exception olursa session close olması için
						if (session.getTransaction().isActive()) {
							session.getTransaction().rollback();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				session.close();
				session = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T> List<T> loadAllData(Class<T> type, Session session) {
	    CriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<T> criteria = builder.createQuery(type);
	    criteria.from(type);
	    List<T> data = session.createQuery(criteria).getResultList();
	    return data;
	  }
	
	
}
