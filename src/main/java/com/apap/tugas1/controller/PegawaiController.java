package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Pegawai Controller
 */

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;

	@Autowired
	private JabatanService jabatanService;

	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String homepage(Model model) {
		List<JabatanModel> daftarJabatan = jabatanService.findAllJabatan();
		List<InstansiModel> daftarInstansi = instansiService.findAllInstansi();
		model.addAttribute("listOfJabatan", daftarJabatan);
		model.addAttribute("listOfInstansi", daftarInstansi);
		return "homepage";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String findPegawai(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		if(pegawai != null) {
			model.addAttribute("pegawai", pegawai);
			return "lihat-pegawai";
		}
		return "404-notfound";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setInstansi(new InstansiModel());
		pegawai.getInstansi().setProvinsi(new ProvinsiModel());
		pegawai.setListJabatan(new ArrayList<>());
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		List<ProvinsiModel> daftarProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> daftarJabatan = jabatanService.findAllJabatan();
		List<InstansiModel> daftarInstansi = instansiService.findAllInstansi();
		
		model.addAttribute("listOfProvinsi", daftarProvinsi);
		model.addAttribute("listOfJabatan", daftarJabatan);
		model.addAttribute("listOfInstansi", daftarInstansi);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST) 
	private	String addPegawaiSubmit(@RequestParam("instansi") String idInstansi,
									@RequestParam("provinsi") String idProvinsi,
									@RequestParam("jabatan") String idJabatan,
									@ModelAttribute PegawaiModel pegawai, Model model) {
			System.out.println(pegawai.getNama());
			InstansiModel instansi = instansiService.findById(Long.parseLong(idInstansi));
			
			List<String> idListJabatan = new ArrayList<String>(Arrays.asList(idJabatan.split(",")));
			List<JabatanModel> listJabatan = new ArrayList<JabatanModel>();
			for(String str : idListJabatan) {
				JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(str));
				listJabatan.add(jabatan);
			}
			
			pegawai.setListJabatan(listJabatan);
			pegawai.setInstansi(instansi);
			System.out.println(pegawai.getTanggalLahir());
			System.out.println(instansi.getId());
			
			String nip = instansi.getId() + pegawai.getTanggalLahirStr() + pegawai.getTahunMasuk();
			
			List<PegawaiModel> pegawaiLain = pegawaiService.findByTahunMasukAndInstansi(pegawai.getTahunMasuk(), instansi);
			if(pegawaiLain.isEmpty()) {
				nip+="01";
			} else {
				pegawaiService.deleteListElement(pegawaiLain, Integer.parseInt(pegawai.getTahunLahir()));
				pegawaiLain.sort(new Comparator<PegawaiModel>() {
					@Override
					public int compare(PegawaiModel b1, PegawaiModel b2) {
						return b2.getNip().compareTo(b1.getNip());
					}
				});
				System.out.println(pegawaiLain.get(0).getNip());
				Long num = Long.parseLong(pegawaiLain.get(0).getNip());
				num += 1;
				nip += Long.toString(num).substring(14);
			}
			pegawai.setNip(nip);
			System.out.println(pegawai.toString());
			pegawaiService.addPegawai(pegawai);
			model.addAttribute("nip", nip);
			return "tambah-pegawai-sukses";
	}
	
	@RequestMapping(value = "/pegawai/ubah/{nip}", method = RequestMethod.GET)
	private String updatePegawai(@PathVariable(value = "nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("pegawaiBaru", new PegawaiModel());
		List<ProvinsiModel> daftarProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> daftarJabatan = jabatanService.findAllJabatan();
		List<InstansiModel> daftarInstansi = instansiService.findAllInstansi();
		
		model.addAttribute("listOfProvinsi", daftarProvinsi);
		model.addAttribute("listOfJabatan", daftarJabatan);
		model.addAttribute("listOfInstansi", daftarInstansi);
		
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah/{nip}", method = RequestMethod.POST)
	private String updatePegawai(@ModelAttribute PegawaiModel pegawaiBaru, @RequestParam("instansiA") String idInstansi,
								@RequestParam("provinsi") String idProvinsi, @RequestParam("jabatan") String idJabatan,
								@PathVariable(value = "nip") String nip, Model model) {
		System.out.println(nip);
		InstansiModel instansiBaru = instansiService.findById(Long.parseLong(idInstansi));
		System.out.println(instansiBaru.getNama());
		
		List<String> idListJabatanBaru = new ArrayList<String>(Arrays.asList(idJabatan.split(",")));
		List<JabatanModel> listJabatanBaru = new ArrayList<JabatanModel>();
		for(String str : idListJabatanBaru) {
			JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(str));
			listJabatanBaru.add(jabatan);
		}
		
		pegawaiBaru.setListJabatan(listJabatanBaru);
		pegawaiBaru.setInstansi(instansiBaru);
		System.out.println(pegawaiBaru.getInstansi().getNama()+"ini");
		String nipBaru = instansiBaru.getId() + pegawaiBaru.getTanggalLahirStr() + pegawaiBaru.getTahunMasuk();
		
		List<PegawaiModel> pegawaiLainBaru = pegawaiService.findByTahunMasukAndInstansi(pegawaiBaru.getTahunMasuk(), instansiBaru);
		if(pegawaiLainBaru.isEmpty()) {
			nipBaru+="01";
		} else {
			pegawaiService.deleteListElement(pegawaiLainBaru, Integer.parseInt(pegawaiBaru.getTahunLahir()));
			pegawaiLainBaru.sort(new Comparator<PegawaiModel>() {
				@Override
				public int compare(PegawaiModel b1, PegawaiModel b2) {
					return b2.getNip().compareTo(b1.getNip());
				}
			});
			System.out.println(pegawaiLainBaru.get(0).getNip());
			Long num = Long.parseLong(pegawaiLainBaru.get(0).getNip());
			num += 1;
			nip += Long.toString(num).substring(14);
		}
		pegawaiBaru.setNip(nip);
		System.out.println(pegawaiBaru.toString());
		pegawaiService.addPegawai(pegawaiBaru);
		model.addAttribute("nip", nip);
		return "update-pegawai-sukses";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String findPegawai(Model model) {
		List<ProvinsiModel> daftarProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> daftarJabatan = jabatanService.findAllJabatan();
		List<InstansiModel> daftarInstansi = instansiService.findAllInstansi();
		
		model.addAttribute("listOfProvinsi", daftarProvinsi);
		model.addAttribute("listOfJabatan", daftarJabatan);
		model.addAttribute("listOfInstansi", daftarInstansi);
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", params = {"cari"}, method = RequestMethod.POST)
	private String findPegawaiSubmit(@ModelAttribute PegawaiModel pegawaiBaru, @RequestParam("instansi") String idInstansi,
									@RequestParam("provinsi") String idProvinsi, 
									@RequestParam("jabatan") String idJabatan, Model model) {
		InstansiModel instansi = instansiService.findById(Long.parseLong(idInstansi));
		JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(idJabatan));
		List<PegawaiModel> listPegawai = pegawaiService.getFilterPegawai(idInstansi, idJabatan);
		System.out.println(listPegawai);
		model.addAttribute("nama", instansi.getNama());
		model.addAttribute("namaJabatan", jabatan.getNama());
	    model.addAttribute("listPegawai", listPegawai);
	    
	    List<ProvinsiModel> daftarProv = provinsiService.findAllProvinsi();
		List<JabatanModel> daftarJabatan = jabatanService.findAllJabatan();
		List<InstansiModel> daftarInstansi = instansiService.findAllInstansi();
		
	    model.addAttribute("listOfProvinsi", daftarProv);
	    model.addAttribute("listOfJabatan", daftarJabatan);
	    model.addAttribute("listOfInstansi", daftarInstansi);
	    
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua/", method = RequestMethod.GET)
	private String termudaTertua(@RequestParam("instansi") Long id, @ModelAttribute PegawaiModel pegawaiBaru, Model model) {
		InstansiModel instansi = instansiService.findById(id);
		List<PegawaiModel> listPegawai = instansi.getPegawaiInstansi();
		listPegawai.sort(new Comparator<PegawaiModel>() {
		    @Override
		    public int compare(PegawaiModel b1, PegawaiModel b2) {
		    	return b2.getTanggalLahir().compareTo(b2.getTanggalLahir());
		     }
		});
		
		PegawaiModel pegawaiTertua = listPegawai.get(0);
		String namaInstansiTertua = pegawaiTertua.getInstansi().getNama();
		String namaProvinsiTertua = pegawaiTertua.getInstansi().getProvinsi().getNama();
		List<JabatanModel> listJabatanTertua = pegawaiTertua.getListJabatan();  
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		model.addAttribute("namaInstansiTertua", namaInstansiTertua);
		model.addAttribute("namaProvinsiTertua", namaProvinsiTertua);
		model.addAttribute("listJabatanTertua", listJabatanTertua);
		
		PegawaiModel pegawaiTermuda = listPegawai.get(listPegawai.size()-1);
		String namaInstansiTermuda = pegawaiTermuda.getInstansi().getNama();
		String namaProvinsiTermuda = pegawaiTermuda.getInstansi().getProvinsi().getNama();
		List<JabatanModel> listJabatanTermuda = pegawaiTermuda.getListJabatan();
		model.addAttribute("pegawaiTermuda",pegawaiTermuda);
		model.addAttribute("namaInstansiTermuda",namaInstansiTermuda);
		model.addAttribute("namaProvinsiTermuda",namaProvinsiTermuda);
		model.addAttribute("listJabatanTermuda",listJabatanTermuda);
		
		return "termuda-tertua";
	}
}	