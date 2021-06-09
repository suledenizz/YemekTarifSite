package siniflar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class YemekTarifleri {
	
	private int tarifid;
	private String kategori;
	private String yemekadi;
	private String malzemeler;
	private String tarif;
	private String yazar;
	public int getTarifid() {
		return tarifid;
	}
	public void setTarifid(int tarifid) {
		this.tarifid = tarifid;
	}
	public String getKategori() {
		return kategori;
	}
	public void setKategori(String kategori) {
		this.kategori = kategori;
	}
	public String getYemekadi() {
		return yemekadi;
	}
	public void setYemekadi(String yemekadi) {
		this.yemekadi = yemekadi;
	}
	public String getMalzemeler() {
		return malzemeler;
	}
	public void setMalzemeler(String malzemeler) {
		this.malzemeler = malzemeler;
	}
	public String getTarif() {
		return tarif;
	}
	public void setTarif(String tarif) {
		this.tarif = tarif;
	}
	public String getYazar() {
		return yazar;
	}
	public void setYazar(String yazar) {
		this.yazar = yazar;
	}
	@Override
	public String toString() {
		return "YemekTarifleri [tarifid=" + tarifid + ", kategori=" + kategori + ", yemekadi=" + yemekadi
				+ ", malzemeler=" + malzemeler + ", tarif=" + tarif + ", yazar=" + yazar + "]";
	}
	public YemekTarifleri() {
		super();
	}
	public YemekTarifleri(int tarifid, String kategori, String yemekadi, String malzemeler, String tarif,
			String yazar) {
		super();
		this.tarifid = tarifid;
		this.kategori = kategori;
		this.yemekadi = yemekadi;
		this.malzemeler = malzemeler;
		this.tarif = tarif;
		this.yazar = yazar;
	}
	
	
}
