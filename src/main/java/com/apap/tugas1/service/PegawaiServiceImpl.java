package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.JabatanPegawaiDb;
import com.apap.tugas1.repository.PegawaiDb;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Pegawai Service Implementation
 */

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Autowired
	private InstansiDb instansiDb;
	
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}
	
	@Override
	public void deletePegawai(PegawaiModel pegawai) {
		pegawaiDb.delete(pegawai);
	}
	
	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}
	
	@Override
	public Optional<PegawaiModel> getPegawaiDetailById(Long id) {
		return pegawaiDb.findById(id);
	}
	
	@Override
	public List<PegawaiModel> findByTahunMasukAndInstansi(String tahunMasuk, InstansiModel instansi) {
		return pegawaiDb.findByTahunMasukAndInstansi(tahunMasuk, instansi);
	}
	
	@Override
	public PegawaiModel getPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}
	
	@Override
	public void deleteListElement(List<PegawaiModel> listPegawai, int tahunLahir) {
		Iterator<PegawaiModel> i = listPegawai.iterator();
		while (i.hasNext()) {
			PegawaiModel pegawai = i.next();
			if(Integer.parseInt(pegawai.getTahunLahir()) != tahunLahir) {
				i.remove();
			}
		}
	}
	
	@Override
	public void updatePegawai(String nip, PegawaiModel pegawai) {
		PegawaiModel pegawaiLama = this.getPegawaiByNip(nip);
		pegawaiLama.setInstansi(pegawai.getInstansi());
		pegawaiLama.setNama(pegawai.getNama());
		pegawaiLama.setNip(pegawai.getNip());
		pegawaiLama.setTahunMasuk(pegawai.getTahunMasuk());
		pegawaiLama.setTanggalLahir(pegawai.getTanggalLahir());
		pegawaiLama.setTempatLahir(pegawai.getTempatLahir());
		pegawaiLama.setListJabatan(pegawai.getListJabatan());
	}
	
	@Override
	public List<PegawaiModel> findByInstansi(InstansiModel instansi) {
		List<PegawaiModel> listPegawai = pegawaiDb.findAllByInstansi(instansi);
		return listPegawai;
	}
	
	@Override
	public int findJabatanList(List<JabatanModel> listJabatan, Long id) {
		int a = 0;
		Iterator<JabatanModel> i = listJabatan.iterator();
		while (i.hasNext()) {
			JabatanModel jabatan = i.next();
			if(jabatan.getId() == id) {
				a++;
			}
		}
		return a;
	}
	
	@Override
	public void deleteListJabatan(List<JabatanModel> listJabatan, Long id) {
		Iterator<JabatanModel> i = listJabatan.iterator();
		while (i.hasNext()) {
			JabatanModel jabatan = i.next();
			if(jabatan.getId() == id) {
				i.remove();
			}
		} 
	}
	
	@Override
	public List<PegawaiModel> getFilterPegawai(String idInstansi, String idJabatan) {
		List<PegawaiModel> list = new ArrayList<PegawaiModel>();
		InstansiModel instansi = instansiDb.findInstansiById(Long.parseLong(idInstansi));
		List<PegawaiModel> listPegawai = pegawaiDb.findAllByInstansi(instansi);
		JabatanModel jabatan = jabatanDb.findJabatanById(Long.parseLong(idJabatan));
		for(PegawaiModel pegawai : listPegawai) {
			for(JabatanModel jabatanLain : pegawai.getListJabatan()) {
				if(jabatanLain.getId() == Long.parseLong(idJabatan)) {
					list.add(pegawai);
				}
			}
		}
		return list;
	}
}	
