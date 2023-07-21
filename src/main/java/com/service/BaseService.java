package com.service;

import java.util.Date;

import com.entity.BaseEntity;


public class BaseService {

//	List<DepoEntity> getAll();
//	DepoEntity getById(Long id);
//	DepoEntity save(DepoEntity Note);
//	DepoEntity update(DepoEntity Note);
//	DepoEntity delete(long id);
	
	
	public static  <T extends BaseEntity> void setBaseFieldsToCreate(T entity) {
		Date sysDate = new Date();
		entity.setGmt(sysDate);
		entity.setCreateGmt(sysDate);
		entity.setUsrId(0L);
		entity.setCreateUsrId(0L);
		if(entity.getStatus()==null ||"".equalsIgnoreCase(entity.getStatus())) {
			entity.setStatus(BaseEntity.NO);
		}
		if(entity.getComment()==null ||"".equalsIgnoreCase(entity.getComment())) {
			entity.setComment(BaseEntity.CREATE);
		}
	}

	public static  <T extends BaseEntity> void setBaseFieldsToUpdate(T entity,Long userId) {
		Date sysDate = new Date();
		entity.setGmt(sysDate);
		entity.setUsrId(userId);
		if(entity.getStatus()==null ||"".equalsIgnoreCase(entity.getStatus())) {
			entity.setStatus(BaseEntity.NO);
		}

		if(entity.getComment()==null ||"".equalsIgnoreCase(entity.getComment())) {
			entity.setComment(BaseEntity.UPDATE);
		}
	}

    
}
