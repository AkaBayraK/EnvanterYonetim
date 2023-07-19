package com.service;

import java.util.List;

import com.entity.KategoriEntity;


public interface KategoriService {

	List<KategoriEntity> getAll();
	KategoriEntity getById(Long id);
	KategoriEntity save(KategoriEntity Note);
	KategoriEntity update(KategoriEntity Note);
	KategoriEntity delete(long id);
    
}
