package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import siniflar.BlogYazi;
import siniflar.User;
import siniflar.YemekTarifleri;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import veritabanibaglanti.VeriTabani;

@ManagedBean
@SessionScoped
public class VeriBean {

	private VeriTabani veriTabani;
	private static final Logger logger = Logger.getLogger(VeriBean.class.getName());

	public VeriBean() throws Exception {

		veriTabani = VeriTabani.getRefDatabase();
		kullaniciListesi = new ArrayList();
		yemektarifleri = new ArrayList();
		blogyazi = new ArrayList();
		blogyaziadi = new ArrayList();
		yemektarifleriadi = new ArrayList();
		yemektariflerianasayfa = new ArrayList();
	}

	private List<User> kullaniciListesi;
	private List<YemekTarifleri> yemektarifleri;
	private List<YemekTarifleri> yemektarifleriadi;
	private List<YemekTarifleri> yemektariflerianasayfa;
	private List<BlogYazi> blogyazi;
	private List<BlogYazi> blogyaziadi;


	public List<User> getKullaniciListesi() {
		return kullaniciListesi;
	}

	public void setKullaniciListesi(List<User> kullaniciListesi) {
		this.kullaniciListesi = kullaniciListesi;
	}

	public List<YemekTarifleri> getYemektarifleri() {
		return yemektarifleri;
	}

	public void setYemektarifleri(List<YemekTarifleri> yemektarifleri) {
		this.yemektarifleri = yemektarifleri;
	}

	public List<YemekTarifleri> getYemektarifleriadi() {
		return yemektarifleriadi;
	}

	public void setYemektarifleriadi(List<YemekTarifleri> yemektarifleriadi) {
		this.yemektarifleriadi = yemektarifleriadi;
	}

	public List<YemekTarifleri> getYemektariflerianasayfa() {
		return yemektariflerianasayfa;
	}

	public void setYemektariflerianasayfa(List<YemekTarifleri> yemektariflerianasayfa) {
		this.yemektariflerianasayfa = yemektariflerianasayfa;
	}

	public List<BlogYazi> getBlogyazi() {
		return blogyazi;
	}

	public void setBlogyazi(List<BlogYazi> blogyazi) {
		this.blogyazi = blogyazi;
	}
	public List<BlogYazi> getBlogyaziadi() {
		return blogyaziadi;
	}

	public void setBlogyaziadi(List<BlogYazi> blogyaziadi) {
		this.blogyaziadi = blogyaziadi;
	}


	public VeriTabani getVeriTabani() {
		return veriTabani;
	}

	public void setVeriTabani(VeriTabani veriTabani) {
		this.veriTabani = veriTabani;
	}

	User user;

	private String username;
	private String password;
	private String email;
	private String izin;
	private int id;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIzin() {
		return izin;
	}

	public void setIzin(String izin) {
		this.izin = izin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String adminGirisi() {
		try {
			user = veriTabani.kullaniciBilgileri(username, password);
			if (this.user != null && this.user.getIzin().charAt(0) == 'A') {
				return "index?redirect=true";
			} else {
				System.out.println("Yonetici Yetkiniz Yoktur veya Kullanıcı adı ya da Sifreniz Yanlis " + username);
				logger.info("Hata. Lutfen Tekrar Deneyin.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String kullaniciGirisi() throws Exception {

		user = veriTabani.kullaniciBilgileri(username, password);
		if (this.user != null) {
			return "index.jsf?redirect=true";
		}
		return null;
	}

	public boolean kullaniciAdminmi() {
		if (this.user.getIzin().charAt(0) == 'A') {
			return true;
		} else {
			return false;
		}
	}

	public String adminSayfaYonlendir() {
		return "admin/index.jsf?faces-redirect=true";
	}
	public void kullaniciListesiLoad() {

		logger.info("Kullanıcılar Yükleniyor");
		try {
			kullaniciListesi = veriTabani.getKullaniciListele();
		} catch (Exception hata) {
			logger.log(Level.SEVERE, "Kullanıcılar Yüklenirken bir Hata Oluştu", hata);
		}
	}

	public String kullaniciDuzenle(int gelenKullaniciID) {

		logger.info("Kullanıcı = " + gelenKullaniciID);

		try {

			User user = veriTabani.getKullaniciGetir(gelenKullaniciID);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("user", user);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Aradığınız Kullanıcı Bulunamadı", e);
		}
		return "kullaniciduzenle.jsf?redirect=true";
	}

	public String kullaniciGuncelle(User user) {

		try {

			if (user != null) {
				veriTabani.getkullaniciGuncelle(user);
			} else {
				logger.log(Level.SEVERE, "Güncelleme sırasında bir hata oluştu", user);
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "kullanicilar.jsf?redirect=true";
	}

	public String kullaniciSil(int gelenkullaniciID) {

		try {
			veriTabani.getkullaniciSil(gelenkullaniciID);
		} catch (Exception e) {
			System.out.println("Kullanıcı Silinirken Bir Hata Oluştu: " + gelenkullaniciID);
		}
		;

		return "kullanicilar.jsf?redirect=true";
	}

	public String kullaniciEkle(User user) {

		try {

			if (user != null) {
				veriTabani.getKullaniciEkle(user);
				System.out.println("Kullanıcı başarıyla eklendi" + user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Kullanıcı eklenirken hata oluştu" + e.getMessage().toString());
		}

		return "kullanicilar.jsf?redirect=true";
	}

	public String kullaniciOlustur(User user) throws Exception {

		try {
			if (!veriTabani.kullaniciKayitlimi(user.getUsername().toString())) {
				veriTabani.getKullaniciUyeOl(user);
				logger.log(Level.SEVERE, "Kullanıcı başarıyla eklendi", user);
				return "uyegiris.jsf?redirect=true";
			} else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage bilgiMesaj = new FacesMessage("Bu kullanıcı zaten kayıtlı.");
				facesContext.addMessage(null, bilgiMesaj);
				logger.log(Level.SEVERE, "Bu kullanıcı zaten kayıtlı", user);
				return null;
			}
		} catch (Exception hata) {
			logger.log(Level.SEVERE, "Bu kullanıcı eklenirken hata oluştu", hata);
			return null;
		}

	}

	public void yemekTarifleriLoad() {

		logger.info("Tarifler Yükleniyor");
		try {
			yemektarifleri = veriTabani.getTarifListele();
		} catch (Exception hata) {
			logger.log(Level.SEVERE, "Tarifler Yüklenirken bir Hata Oluştu", hata);
		}
	}

	public void yemekTarifleriAdiLoad() {

		logger.info("Tarifler Yükleniyor");
		try {
			yemektarifleriadi = veriTabani.getTarifListeleAdi();
		} catch (Exception hata) {
			logger.log(Level.SEVERE, "Tarifler Yüklenirken bir Hata Oluştu", hata);
		}
	}

	public void yemekTarifleriAnasayfaLoad() {

		logger.info("Tarifler Yükleniyor");
		try {
			yemektariflerianasayfa = veriTabani.getTarifListeleAnasayfa();
		} catch (Exception hata) {
			logger.log(Level.SEVERE, "Tarifler Yüklenirken bir Hata Oluştu", hata);
		}
	}

	public String tarifDuzenle(int gelentarifID) {

		logger.info("Tarif = " + gelentarifID);

		try {

			YemekTarifleri yemektarifleri = veriTabani.getTarifGetir(gelentarifID);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("yemektarifleri", yemektarifleri);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Aradığınız Tarif Bulunamadı", e);
		}
		return "tarifduzenle.jsf?redirect=true";
	}

	public String tarifGuncelle(YemekTarifleri tarif) {

		try {

			if (tarif != null) {
				veriTabani.getTarifGuncelle(tarif);
			} else {
				logger.log(Level.SEVERE, "Güncelleme sırasında bir hata oluştu", user);
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "tarifler.jsf?redirect=true";
	}

	public String tarifSil(int gelentarifID) {

		try {
			veriTabani.getTarifSil(gelentarifID);
		} catch (Exception e) {
			System.out.println("Tarif Silinirken Bir Hata Oluştu: " + gelentarifID);
		}
		;

		return "tarifler.jsf?redirect=true";
	}

	public String tarifEkle(YemekTarifleri tarif) {

		try {

			if (tarif != null) {
				veriTabani.getTarifEkle(tarif);
				System.out.println("Tarif başarıyla eklendi" + tarif);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Makale eklenirken hata oluştu" + e.getMessage().toString());
		}

		return "tarifler.jsf?redirect=true";
	}

	public String tarifeGit(int gelentarifID) throws Exception {
		YemekTarifleri yemektarifleri;
		yemektarifleri = veriTabani.getTarifeGit(gelentarifID);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		requestMap.put("yemektarifleri", yemektarifleri);
		return "tarifdetay.jsf";
	}
	public void blogYaziLoad() {

		logger.info("Yazılar Yükleniyor");
		try {
			blogyazi = veriTabani.getYaziListele();
		} catch (Exception hata) {
			logger.log(Level.SEVERE, "Yazılar Yüklenirken bir Hata Oluştu", hata);
		}
	}
	public void blogYaziadiLoad() {

		logger.info("Yazılar Yükleniyor");
		try {
			blogyaziadi = veriTabani.getYaziListeleAdi();
		} catch (Exception hata) {
			logger.log(Level.SEVERE, "Yazılar Yüklenirken bir Hata Oluştu", hata);
		}
	}
	public void blogYazianasayfaLoad() {

		logger.info("Yazılar Yükleniyor");
		try {
			blogyazi = veriTabani.getYaziListeleAnasayfa();
		} catch (Exception hata) {
			logger.log(Level.SEVERE, "Yazılar Yüklenirken bir Hata Oluştu", hata);
		}
	}

	public String yaziDuzenle(int gelenyaziID) {

		logger.info("Yazı = " + gelenyaziID);

		try {

			BlogYazi blogyazi = veriTabani.getYaziGetir(gelenyaziID);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("blogyazi", blogyazi);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Aradığınız Yazı Bulunamadı", e);
		}
		return "blogyaziduzenle.jsf?redirect=true";
	}

	public String yaziGuncelle(BlogYazi yazi) {

		try {

			if (yazi != null) {
				veriTabani.getYaziGuncelle(yazi);
			} else {
				logger.log(Level.SEVERE, "Güncelleme sırasında bir hata oluştu", user);
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "blogyazilari.jsf?redirect=true";
	}

	public String yaziSil(int gelenyaziID) {

		try {
			veriTabani.getYaziSil(gelenyaziID);
		} catch (Exception e) {
			System.out.println("Yazı Silinirken Bir Hata Oluştu: " + gelenyaziID);
		}
		;

		return "blogyazilari.jsf?redirect=true";
	}

	public String yaziEkle(BlogYazi yazi) {

		try {

			if (yazi != null) {
				veriTabani.getYaziEkle(yazi);
				System.out.println("Yazı başarıyla eklendi" + yazi);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Yazı eklenirken hata oluştu" + e.getMessage().toString());
		}

		return "blogyazilari.jsf?redirect=true";
	}
	public String yaziyaGit(int gelenyaziID) throws Exception {
		BlogYazi blogyazi;
		blogyazi = veriTabani.getYaziGetir(gelenyaziID);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		requestMap.put("blogyazi", blogyazi);
		return "yazidetay.jsf";
	}


}
