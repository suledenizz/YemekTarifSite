package siniflar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	private String izin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIzin() {
		return izin;
	}

	public void setIzin(String izin) {
		this.izin = izin;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", izin="
				+ izin + ", id=" + id +  "]";
	}

	public User() {
		super();
	}

	public User(String username, String password, String email, String izin, int id) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.izin = izin;
		this.id = id;
	}

	public User(String username) {
		this.username = username;
	}
}
