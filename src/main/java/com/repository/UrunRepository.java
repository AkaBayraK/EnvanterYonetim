package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.UrunEntity;

@Repository
public interface UrunRepository extends JpaRepository<UrunEntity, Long> {

}
