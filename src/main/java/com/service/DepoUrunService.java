package com.service;

import java.util.List;

import com.entity.DepoUrunEntity;


public interface DepoUrunService {

	List<DepoUrunEntity> getAll();
	DepoUrunEntity getById(Long id);
	DepoUrunEntity save(DepoUrunEntity Note);
	DepoUrunEntity update(DepoUrunEntity Note);
	DepoUrunEntity delete(long id);
    
}
