package com.response;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.KategoriEntity;

@Repository
public interface KategoriRepository extends JpaRepository<KategoriEntity, Long> {

}
