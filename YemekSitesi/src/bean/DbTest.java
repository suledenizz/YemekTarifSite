package bean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/DbTest")
public class DbTest extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/webprogramlama")
	private DataSource dataSource;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		
		Connection con = null;
		Statement pstmt = null;
		ResultSet rs = null;

		try {
			con = dataSource.getConnection();

			String sql = "select * from kullanici";

			pstmt = con.createStatement();

			rs = pstmt.executeQuery(sql);

			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");

				
				String htmlGorunum = "<html>";
				htmlGorunum += "<h3>Kullanici Adi : " + username + "<br/>";      
				htmlGorunum += "Kullanici Sifre   : " + password + "</h3>";    
				htmlGorunum += "</html>";
				out.write(htmlGorunum);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			out.println(exc.getMessage());
		}
	}

}