package com.service;

import java.util.List;

import com.entity.DepoEntity;


public interface DepoService {

	List<DepoEntity> getAll();
	DepoEntity getById(Long id);
	DepoEntity save(DepoEntity Note);
	DepoEntity update(DepoEntity Note);
	DepoEntity delete(long id);
    
}
