package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Instansi DB
 */

@Repository
public interface InstansiDb extends JpaRepository<InstansiModel,Long>{
	InstansiModel findByNamaAndProvinsi(String nama, ProvinsiModel provinsi);
	InstansiModel findInstansiById(long id);
}
