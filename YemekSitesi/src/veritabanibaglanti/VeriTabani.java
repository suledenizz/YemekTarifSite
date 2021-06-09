package veritabanibaglanti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import siniflar.BlogYazi;
import siniflar.User;
import siniflar.YemekTarifleri;

public class VeriTabani {
	private static VeriTabani refDb;
	private DataSource dataSource;
	private String veriKaynagi = "java:comp/env/jdbc/webprogramlama";

	public static VeriTabani getRefDatabase() throws Exception {
		if (refDb == null) {
			refDb = new VeriTabani();
		}

		return refDb;
	}

	private VeriTabani() throws Exception {
		dataSource = getDbDataSource();
	}

	private DataSource getDbDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource newDataSource = (DataSource) context.lookup(veriKaynagi);

		return newDataSource;
	}

	@SuppressWarnings("unused")
	private Connection getDbConnection() throws Exception {

		Connection newCon = dataSource.getConnection();

		return newCon;
	}

	public User kullaniciBilgileri(String username, String password) throws SQLException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "select * from kullanici where username=? and password=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			User user = null;
			if (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setIzin(rs.getString("izin"));
				user.setId(rs.getInt("id"));
				return user;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}

		return null;
	}

	public List<User> getKullaniciListele() throws Exception {

		List<User> listKullanici = new ArrayList<User>();
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from kullanici";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sorgu);

			while (rs.next()) {

				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String izin = rs.getString("izin");
				int id = rs.getInt("id");
				User user = new User(username, password, email, izin, id);

				listKullanici.add(user);

			}
			return listKullanici;

		} finally {
			rs.close();
			stmt.close();
			con.close();
		}
	}

	public User getKullaniciGetir(int gelenKullaniciID) throws SQLException {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from kullanici where id=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setInt(1, gelenKullaniciID);
			rs = pstmt.executeQuery();
			User user = null;
			;
			if (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setIzin(rs.getString("izin"));
				user.setId(rs.getInt("id"));

			} else {
				System.out.println("Aradığınız kullanıcı bulunamadı." + gelenKullaniciID);
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}

		return null;
	}

	public void getkullaniciGuncelle(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "UPDATE Kullanici SET username=?, password=?, email=?, izin=? WHERE id=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getIzin());
			pstmt.setInt(5, user.getId());
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			con.close();
		}
	}

	public void getkullaniciSil(int gelenkullaniciID) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "delete FROM Kullanici where id=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setInt(1, gelenkullaniciID);
			pstmt.execute();
			System.out.println("Silinen Kullanıcı : " + gelenkullaniciID);
		} finally {
			pstmt.close();
			con.close();
		}
	}
	public void getKullaniciEkle(User user) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "insert into kullanici(username, password, email, izin) values(?,?,?,?)";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1, user.getUsername() );
			pstmt.setString(2, user.getPassword() );
			pstmt.setString(3, user.getEmail() );
			pstmt.setString(4, user.getIzin() );
			pstmt.execute();
		} finally {
			pstmt.close();
			con.close();
		}
	}
	public void getKullaniciUyeOl(User user) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "insert into kullanici(username, password, email, izin) values(?,?,?,'K')";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1, user.getUsername() );
			pstmt.setString(2, user.getPassword() );
			pstmt.setString(3, user.getEmail() ) ;
			pstmt.execute();
		} finally {
			pstmt.close();
			con.close();
		}
	}
	public boolean kullaniciKayitlimi(String gelenusername) throws Exception{
		List <User> kullaniciliste = new ArrayList<User>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		try {
			con = getDbConnection();
			String sorgu = "select * from kullanici where username=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1, gelenusername);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				String username = rs.getString("username");
				User user = new User(username);
				kullaniciliste.add(user);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<YemekTarifleri> getTarifListele() throws Exception {

		List<YemekTarifleri> tarifler = new ArrayList<YemekTarifleri>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from tarifler";
			pstmt = con.prepareStatement(sorgu);
			rs = pstmt.executeQuery(sorgu);

			while (rs.next()) {

				int tarifid = rs.getInt("tarifid");
				String kategori = rs.getString("kategori");
				String yemekadi = rs.getString("yemekadi");
				String malzemeler = rs.getString("malzemeler");
				String tarif = rs.getString("tarif");
				String yazar = rs.getString("yazar");
				YemekTarifleri yemektarifi = new YemekTarifleri(tarifid, kategori, yemekadi, malzemeler, tarif, yazar);

				tarifler.add(yemektarifi);

			}
			return tarifler;

		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}
	public List<YemekTarifleri> getTarifListeleAdi() throws Exception {

		List<YemekTarifleri> tarifler = new ArrayList<YemekTarifleri>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from tarifler";
			pstmt = con.prepareStatement(sorgu);
			rs = pstmt.executeQuery(sorgu);

			while (rs.next()) {

				int tarifid = rs.getInt("tarifid");
				String kategori = rs.getString("kategori");
				String yemekadi = rs.getString("yemekadi");
				String malzemeler = rs.getString("malzemeler");
				String tarif = rs.getString("tarif");
				String yazar = rs.getString("yazar");
				YemekTarifleri yemektarifi = new YemekTarifleri(tarifid, kategori, yemekadi, malzemeler, tarif, yazar);

				tarifler.add(yemektarifi);

			}
			return tarifler;

		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}
	public List<YemekTarifleri> getTarifListeleAnasayfa() throws Exception {

		List<YemekTarifleri> tarifler = new ArrayList<YemekTarifleri>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from tarifler";
			pstmt = con.prepareStatement(sorgu);
			rs = pstmt.executeQuery(sorgu);

			while (rs.next()) {

				int tarifid = rs.getInt("tarifid");
				String kategori = rs.getString("kategori");
				String yemekadi = rs.getString("yemekadi");
				String malzemeler = rs.getString("malzemeler");
				String tarif = rs.getString("tarif");
				String yazar = rs.getString("yazar");
				YemekTarifleri yemektarifi = new YemekTarifleri(tarifid, kategori, yemekadi, malzemeler, tarif, yazar);

				tarifler.add(yemektarifi);

			}
			return tarifler;

		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}
	
	public YemekTarifleri getTarifGetir(int gelentarifID) throws Exception {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from tarifler where tarifid=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setInt(1, gelentarifID);
			rs = pstmt.executeQuery();
			YemekTarifleri tarif = null;
			;
			if (rs.next()) {
				tarif = new YemekTarifleri();
				tarif.setTarifid(rs.getInt("tarifid"));
				tarif.setKategori(rs.getString("kategori"));
				tarif.setYemekadi(rs.getString("yemekadi"));
				tarif.setMalzemeler(rs.getString("malzemeler"));
				tarif.setTarif(rs.getString("tarif"));
				tarif.setYazar(rs.getString("yazar"));

			} else {
				System.out.println("Aradığınız Tarif bulunamadı." + gelentarifID);
			}
			return tarif;
		
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}
	public void getTarifGuncelle(YemekTarifleri tarif) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "UPDATE tarifler SET yemekadi=?, malzemeler=?, tarif=? WHERE tarifid=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1,tarif.getYemekadi());
			pstmt.setString(2,tarif.getMalzemeler());
			pstmt.setString(3,tarif.getTarif());
			pstmt.setInt(4,tarif.getTarifid());
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			con.close();
		}
	}
	public void getTarifSil(int gelentarifID) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "delete FROM tarifler where tarifid=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setInt(1, gelentarifID);
			pstmt.execute();
			System.out.println("Silinen Tarif : " + gelentarifID);
		} finally {
			pstmt.close();
			con.close();
		}
	}
	
	public void getTarifEkle(YemekTarifleri tarif) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "insert into tarifler(kategori, yemekadi, malzemeler, tarif, yazar) values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1, tarif.getKategori());
			pstmt.setString(2, tarif.getYemekadi());
			pstmt.setString(3, tarif.getMalzemeler());
			pstmt.setString(4, tarif.getTarif());
			pstmt.setString(5, tarif.getYazar());
			pstmt.execute();
		} finally {
			pstmt.close();
			con.close();
		}
	}
	public YemekTarifleri getTarifeGit(int gelentarifID) throws Exception {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from tarifler where tarifid=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setInt(1, gelentarifID);
			rs = pstmt.executeQuery();
			YemekTarifleri tarif = null;
			if (rs.next()) {
				tarif = new YemekTarifleri();
				tarif.setTarifid(rs.getInt("tarifid"));
				tarif.setKategori(rs.getString("kategori"));
				tarif.setYemekadi(rs.getString("yemekadi"));
				tarif.setMalzemeler(rs.getString("malzemeler"));
				tarif.setTarif(rs.getString("tarif"));
				tarif.setYazar(rs.getString("yazar"));

			} else {
				System.out.println("Aradığınız Tarif bulunamadı." + gelentarifID);
			}
			return tarif;
		
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}
	public List<BlogYazi> getYaziListele() throws Exception {

		List<BlogYazi> yazilar = new ArrayList<BlogYazi>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from blogyazi";
			pstmt = con.prepareStatement(sorgu);
			rs = pstmt.executeQuery(sorgu);

			while (rs.next()) {

				int yaziid = rs.getInt("yaziid");
				String baslik = rs.getString("baslik");
				String yazi = rs.getString("yazi");
				String yazar = rs.getString("yazar");
				BlogYazi blogyazi = new BlogYazi(yaziid, baslik, yazi, yazar);

				yazilar.add(blogyazi);

			}
			return yazilar;

		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}
	public BlogYazi getYaziGetir(int gelenyaziID) throws Exception {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from blogyazi where yaziid=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setInt(1, gelenyaziID);
			rs = pstmt.executeQuery();
			BlogYazi yazi = null;
			;
			if (rs.next()) {
				yazi = new BlogYazi();
				yazi.setYaziid(rs.getInt("yaziid"));
				yazi.setBaslik(rs.getString("baslik"));
				yazi.setYazi(rs.getString("yazi"));
				yazi.setYazar(rs.getString("yazar"));

			} else {
				System.out.println("Aradığınız Yazı bulunamadı." + gelenyaziID);
			}
			return yazi;
		
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}
	public void getYaziGuncelle(BlogYazi yazi) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "UPDATE blogyazi SET baslik=?, yazi=?, yazar=? WHERE yaziid=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1,yazi.getBaslik());
			pstmt.setString(2, yazi.getYazi());
			pstmt.setString(3, yazi.getYazar());
			pstmt.setInt(4, yazi.getYaziid());
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			con.close();
		}
	}
	public void getYaziSil(int gelenyaziID) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "delete FROM blogyazi where yaziid=?";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setInt(1, gelenyaziID);
			pstmt.execute();
			System.out.println("Silinen Yazı : " + gelenyaziID);
		} finally {
			pstmt.close();
			con.close();
		}
	}
	
	public void getYaziEkle(BlogYazi yazi) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getDbConnection();
			String sorgu = "insert into blogyazi(baslik, yazi,yazar) values(?,?,?)";
			pstmt = con.prepareStatement(sorgu);
			pstmt.setString(1, yazi.getBaslik());
			pstmt.setString(2, yazi.getYazi());
			pstmt.setString(3, yazi.getYazar());
			pstmt.execute();
		} finally {
			pstmt.close();
			con.close();
		}
	}
	public List<BlogYazi> getYaziListeleAdi() throws Exception {

		List<BlogYazi> yazilar = new ArrayList<BlogYazi>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from blogyazi";
			pstmt = con.prepareStatement(sorgu);
			rs = pstmt.executeQuery(sorgu);

			while (rs.next()) {

				int yaziid = rs.getInt("yaziid");
				String baslik = rs.getString("baslik");
				String yazi = rs.getString("yazi");
				String yazar = rs.getString("yazar");
				BlogYazi blogyazi = new BlogYazi(yaziid, baslik, yazi, yazar);

				yazilar.add(blogyazi);

			}
			return yazilar;

		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}
	public List<BlogYazi> getYaziListeleAnasayfa() throws Exception {

		List<BlogYazi> yazilar = new ArrayList<BlogYazi>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getDbConnection();
			String sorgu = "select * from blogyazi";
			pstmt = con.prepareStatement(sorgu);
			rs = pstmt.executeQuery(sorgu);

			while (rs.next()) {

				int yaziid = rs.getInt("yaziid");
				String baslik = rs.getString("baslik");
				String yazi = rs.getString("yazi");
				String yazar = rs.getString("yazar");
				BlogYazi blogyazi = new BlogYazi(yaziid, baslik, yazi, yazar);

				yazilar.add(blogyazi);

			}
			return yazilar;

		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
	}

}
