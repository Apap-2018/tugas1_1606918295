package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.ProvinsiModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Provinsi Service
 */

public interface ProvinsiService {
	ProvinsiModel getProvinsiDetailById(int id);
	List<ProvinsiModel> findAllProvinsi();
}
