package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Pegawai Service
 */

public interface PegawaiService {
	PegawaiModel addPegawai (PegawaiModel pegawai);
	void deletePegawai (PegawaiModel pegawai);
	void updatePegawai (PegawaiModel pegawai);
	Optional<PegawaiModel> getPegawaiDetailById(Long id);
	PegawaiModel getPegawaiByNip (String nip);
	List<PegawaiModel> findByTahunMasukAndInstansi (String tahunMasuk, InstansiModel instansi);
	void deleteListElement(List<PegawaiModel> listPegawai, int tahunLahir);
	PegawaiModel ubahPegawai(PegawaiModel pegawai);
	void deleteListJabatan(List<JabatanModel> listJabatan, Long id);
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	int findJabatanList(List<JabatanModel> listJabatan, Long id);
	List<PegawaiModel> getFilterPegawai(String idInstansi, String idJabatan);
	List<PegawaiModel> cariPegawai(Long idInstansi, Long idProvinsi, Long idJabatan);
}
