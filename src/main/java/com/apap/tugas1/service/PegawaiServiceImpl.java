package com.apap.tugas1.service;

import java.time.LocalDate;
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
	public PegawaiModel addPegawai(PegawaiModel pegawai) {
	
		pegawai.setInstansi(instansiDb.getOne(pegawai.getInstansi().getId()));
		
		List<JabatanModel> jabatanList = new ArrayList<>();
		for(JabatanModel jabatan : pegawai.getListJabatan()) {
			jabatanList.add(jabatanDb.findJabatanById(jabatan.getId()));
		}
		
		pegawai.setListJabatan(jabatanList);
		pegawai.setNip(this.generateNipForPegawai(pegawai));
		
		pegawaiDb.save(pegawai);
		return pegawai;
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
	public PegawaiModel ubahPegawai(PegawaiModel pegawai) {
		PegawaiModel pegawaiLama = this.pegawaiDb.getOne(pegawai.getId());
		
		pegawaiLama.setInstansi(instansiDb.getOne(pegawai.getInstansi().getId()));
		pegawaiLama.setNama(pegawai.getNama());
		
		pegawaiLama.setTahunMasuk(pegawai.getTahunMasuk());
		pegawaiLama.setTanggalLahir(pegawai.getTanggalLahir());
		pegawaiLama.setTempatLahir(pegawai.getTempatLahir());
		
		List<JabatanModel> jabatanList = new ArrayList<>();
		for(JabatanModel jabatan : pegawai.getListJabatan()) {
			jabatanList.add(jabatanDb.findJabatanById(jabatan.getId()));
		}
		
		pegawaiLama.setListJabatan(jabatanList);
		pegawaiLama.setNip(this.generateNipForPegawai(pegawaiLama));
		
		pegawaiDb.save(pegawaiLama);
		return pegawaiLama;
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
	
	public String generateNipForPegawai(PegawaiModel pegawai) {
        Long instansiDigit = pegawai.getInstansi().getId();


        LocalDate tanggalLahir = pegawai.getTanggalLahir().toLocalDate();
        int day = tanggalLahir.getDayOfMonth();
        int month = tanggalLahir.getMonthValue();
        int year = tanggalLahir.getYear() % 100; // get last two digit


        String tanggalLahirDigit = Integer.toString(year);
        tanggalLahirDigit = (month < 10 ? "0" + month : month) + tanggalLahirDigit;
        tanggalLahirDigit = (day < 10 ? "0" + day : day) + tanggalLahirDigit;


        String nipPegawaiWithoutSequence = instansiDigit + tanggalLahirDigit + pegawai.getTahunMasuk();


        int totalPegawaiByTahunMasuk = this.pegawaiDb.countByNipStartingWith(nipPegawaiWithoutSequence) + 1;
        String seqDigit = totalPegawaiByTahunMasuk < 10 ?
                "0" + totalPegawaiByTahunMasuk :
                Integer.toString(totalPegawaiByTahunMasuk);


        return nipPegawaiWithoutSequence + seqDigit;
    }
	
	public List<PegawaiModel> cariPegawai(Long idInstansi, Long idProvinsi, Long idJabatan) {
		if(idInstansi == null && idProvinsi == null && idJabatan == null) {
			return pegawaiDb.findAll();
		} else {
			return pegawaiDb.findDistinctByInstansiIdOrInstansi_ProvinsiIdOrListJabatan_Id(idInstansi, idProvinsi, idJabatan);

		}
	}
}	
