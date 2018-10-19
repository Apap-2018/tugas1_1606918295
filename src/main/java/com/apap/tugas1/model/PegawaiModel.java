package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author IRFANI RAMADIANTI
 * Pegawai Model
 */

@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable = false, unique = true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;

	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempatLahir;
	
	@NotNull
	@Column(name = "tanggal_lahir")
	private Date tanggalLahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahunMasuk;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
	@JoinTable(name = "jabatan_pegawai",
		joinColumns = { @JoinColumn(name="id_pegawai", referencedColumnName="id") },
		inverseJoinColumns = {@JoinColumn(name="id_jabatan", referencedColumnName="id") })
	private List<JabatanModel> listJabatan;

	private static Comparator<JabatanModel> pegawaiComparator = new Comparator<JabatanModel>() {
		@Override
		public int compare(JabatanModel a1, JabatanModel a2) {
			if(a1.getGajiPokok()<a2.getGajiPokok()) {
				return -1;	
			}
			else if(a1.getGajiPokok()>a2.getGajiPokok()) {
				return 1;
			}
			return 0;
		}
	};
	
	public double getGaji() {
		double gajiUtama = 0;
		double tunjangan = this.instansi.getProvinsi().getPersentaseTunjangan();
		for(JabatanModel jabatan : getListJabatan()) {
			if(gajiUtama < jabatan.getGajiPokok()) {
				gajiUtama = jabatan.getGajiPokok();
			}
		}
		return gajiUtama + ((tunjangan / 100) * gajiUtama);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}

	public List<JabatanModel> getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(List<JabatanModel> listJabatan) {
		this.listJabatan = listJabatan;
	}
	
	public String getTanggalLahirStr() {
		LocalDate date = getTanggalLahir().toLocalDate();
		return String.format("%d%d%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear() % 100);
	}
	
	public String getTahunLahir() {
		DateFormat dateformat = new SimpleDateFormat("yyyy");
		String tahunLahir = dateformat.format(tanggalLahir);
		return tahunLahir;
	}
	
	public static Comparator<JabatanModel> getPegawaiComparator() {
		return pegawaiComparator;
	}

	public static void setPegawaiComparator(Comparator<JabatanModel> pegawaiComparator) {
		PegawaiModel.pegawaiComparator = pegawaiComparator;
	}
	
	@Override
	public String toString() {
		return "PegawaiModel [id = " + id + ", nip = " + nip + ", nama = " + nama + ", tempat lahir = " + tempatLahir
				+ ", tanggal lahir = " + tanggalLahir + ", tahun masuk = " + tahunMasuk + ", instansi = " + instansi
				+ ", daftar jabatan = " + listJabatan + "]";
	}
}
