package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.ProvinsiModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Provinsi DB
 */

@Repository
public interface ProvinsiDb extends JpaRepository<ProvinsiModel,Long>{
	ProvinsiModel findById(long id);
}
