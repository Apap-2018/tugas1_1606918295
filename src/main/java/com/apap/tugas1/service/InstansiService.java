package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Instansi Service
 */

public interface InstansiService {
	void addInstansi (InstansiModel instansi);
	void deleteInstansi (InstansiModel instansi);
	void updateInstansi (InstansiModel instansi);
	InstansiModel findById(Long id);
	InstansiModel getInstansiByNamaAndProvinsi(String nama, ProvinsiModel provinsi);
	List<InstansiModel> findAllInstansi();
	
}
