package com.service;

import java.util.List;

import com.entity.EnvanterEntity;


public interface EnvanterService {

	List<EnvanterEntity> getAll();
	EnvanterEntity getById(Long id);
	EnvanterEntity save(EnvanterEntity Note);
	EnvanterEntity update(EnvanterEntity Note);
	EnvanterEntity delete(long id);
    
}
