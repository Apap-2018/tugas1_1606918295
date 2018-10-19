package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Jabatan Controller
 */

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@RequestMapping(value = "/jabatan", method = RequestMethod.GET)
	private String findJabatan(@RequestParam("jabatan") Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		if(jabatan!=null) {
			model.addAttribute("jabatan", jabatan);
			return "lihat-jabatan";
		}
		return "404-notfound";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "tambah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("nama", jabatan.getNama());
		return "tambah-jabatan-sukses";
	}
	
	@RequestMapping(value = "/jabatan/ubah/{id_jabatan}", method = RequestMethod.GET)
	private String updateJabatan(@PathVariable(value = "id_jabatan") String id_jabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(id_jabatan));
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("jabatanBaru", new JabatanModel());
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatanBaru, Model model) {
		jabatanService.updateJabatan(jabatanBaru);
		model.addAttribute("nama", jabatanBaru.getNama());
		return "update-jabatan-sukses";
	}
	
	@RequestMapping(value = "/jabatan/hapus/{id_jabatan}", method = RequestMethod.GET)
	private String deleteJabatan(@PathVariable(value = "id_jabatan") String id_jabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(id_jabatan));
		List<PegawaiModel> listPegawai = jabatan.getListPegawai();
		if(listPegawai.isEmpty()) {
			jabatanService.deleteJabatan(jabatan);
		} else {
			for(PegawaiModel pegawai : listPegawai) {
				pegawaiService.deleteListJabatan(pegawai.getListJabatan(), Long.parseLong(id_jabatan));
			}
			jabatanService.deleteJabatan(jabatan);
		}
		model.addAttribute("nama", jabatan.getNama());
		return "hapus";
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String lihatSemuaJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "lihat-semua-jabatan";
	}
	
	
}
