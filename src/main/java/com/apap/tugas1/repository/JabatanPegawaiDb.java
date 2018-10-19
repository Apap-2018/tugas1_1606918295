package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.JabatanPegawaiModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Jabatan Pegawai DB
 */

@Repository
public interface JabatanPegawaiDb extends JpaRepository<JabatanPegawaiModel,Long>{

}
