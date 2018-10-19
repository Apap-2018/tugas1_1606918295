package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.JabatanModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Jabatan DB
 */

@Repository
public interface JabatanDb extends JpaRepository<JabatanModel,Long>{
	JabatanModel findJabatanById(long id);
}
