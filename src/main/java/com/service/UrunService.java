package com.service;

import java.util.List;

import com.entity.UrunEntity;


public interface UrunService {

	List<UrunEntity> getAll();
	UrunEntity getById(Long id);
	UrunEntity save(UrunEntity Note);
	UrunEntity update(UrunEntity Note);
	UrunEntity delete(long id);
    
}
