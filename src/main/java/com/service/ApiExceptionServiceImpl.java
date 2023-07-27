package com.service;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.entity.ApiExceptionEntity;
import com.exceptions.ApiErrorResponse;
import com.hibernate.HibernateMySQLUtil;


@Service
public class ApiExceptionServiceImpl implements ApiExceptionService {
	
	public static Logger LOGGER = LoggerFactory.getLogger(ApiExceptionServiceImpl.class);

	@Override 
	public void save(ApiErrorResponse response) {	
		Session	ses 	=	null;
		try {
			ses = HibernateMySQLUtil.openSession();
			
			ses.beginTransaction();
			
			ApiExceptionEntity ent = new ApiExceptionEntity();
			ent.setGuid(response.getGuid());
			ent.setErrorCode(response.getErrorCode());
			ent.setMessage(response.getMessage());
			ent.setStatusCode(response.getStatusCode());
			ent.setStatusName(response.getStatusName());
			ent.setPath(response.getPath());
			ent.setMethod(response.getMethod());
			ent.setTimestamp(response.getTimestamp());

			ses.persist(ent);
			ses.evict(ent);
			
			ses.getTransaction().commit();
		} catch (Exception e) {
			ses.getTransaction().rollback();
			e.printStackTrace();
		} finally{
			HibernateMySQLUtil.close(ses);
		}
	}


}