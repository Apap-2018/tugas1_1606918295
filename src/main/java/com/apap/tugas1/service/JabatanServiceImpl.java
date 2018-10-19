package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Jabatan Service Implementation
 */

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}
	
	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		jabatanDb.delete(jabatan);
	}
	
	@Override
	public void updateJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}
	
	@Override
	public JabatanModel getJabatanDetailById(Long id) {
		return jabatanDb.findJabatanById(id);
	}
	
	@Override
	public List<JabatanModel> findAllJabatan() {
		return jabatanDb.findAll();
	}
	

}
