package siniflar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BlogYazi {

	private int yaziid;
	private String baslik;
	private String yazi;
	private String yazar;

	public int getYaziid() {
		return yaziid;
	}

	public void setYaziid(int yaziid) {
		this.yaziid = yaziid;
	}

	public String getBaslik() {
		return baslik;
	}

	public void setBaslik(String baslik) {
		this.baslik = baslik;
	}

	public String getYazi() {
		return yazi;
	}

	public void setYazi(String yazi) {
		this.yazi = yazi;
	}

	public BlogYazi() {
		super();
	}

	public String getYazar() {
		return yazar;
	}

	@Override
	public String toString() {
		return "BlogYazi [yaziid=" + yaziid + ", baslik=" + baslik + ", yazi=" + yazi + ", yazar=" + yazar + "]";
	}

	public void setYazar(String yazar) {
		this.yazar = yazar;
	}

	public BlogYazi(int yaziid, String baslik, String yazi, String yazar) {
		super();
		this.yaziid = yaziid;
		this.baslik = baslik;
		this.yazi = yazi;
		this.yazar = yazar;
	}
}