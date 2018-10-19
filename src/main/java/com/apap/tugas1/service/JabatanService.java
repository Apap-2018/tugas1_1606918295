package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Jabatan Service
 */

public interface JabatanService {
	void addJabatan (JabatanModel jabatan);
	void deleteJabatan (JabatanModel jabatan);
	void updateJabatan (JabatanModel jabatan);
	JabatanModel getJabatanDetailById(Long id);
	List<JabatanModel> findAllJabatan();
}
