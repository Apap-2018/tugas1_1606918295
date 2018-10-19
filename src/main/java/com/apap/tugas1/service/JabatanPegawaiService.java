package com.apap.tugas1.service;

import java.util.Optional;

import com.apap.tugas1.model.JabatanPegawaiModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Jabatan Pegawai Service
 */

public interface JabatanPegawaiService {
	void addJabatanPegawai (JabatanPegawaiModel jabatan);
	void deleteJabatanPegawai (JabatanPegawaiModel jabatan);
	void updateJabatanPegawai (JabatanPegawaiModel jabatan);
	Optional<JabatanPegawaiModel> getJabatanPegawaiDetailById(Long id);
}
