import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static Package1.Polecenia.*;

/**
 * Servlet implementation class ZmienKolejnoscPytan
 */
@WebServlet("/ZmienKolejnoscPytan")
public class ZmienKolejnoscPytan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZmienKolejnoscPytan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int id_ankiety = Integer.parseInt(request.getParameter("id_ankiety"));
		int id_uzytkownika = Integer.parseInt(request.getParameter("id_uzytkownika"));
		int id_pytania = Integer.parseInt(request.getParameter("id_pytania"));
		String gdzie = request.getParameter("gdzie");
		try {
			if(gdzie.equals("gora")){
				int numer_pytania = podajNumerPytania(id_pytania);
				int id_pytania_wczesniejszego = podajIdPytaniaPoNumerze(id_ankiety, numer_pytania-1);
				zmienNumerPytania(id_pytania_wczesniejszego, numer_pytania);
				zmienNumerPytania(id_pytania, (numer_pytania-1));
			} else {
				int numer_pytania = podajNumerPytania(id_pytania);
				int id_pytania_nastepnego = podajIdPytaniaPoNumerze(id_ankiety, numer_pytania+1);
				zmienNumerPytania(id_pytania_nastepnego, numer_pytania);
				zmienNumerPytania(id_pytania, (numer_pytania+1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety+"&id_uzytkownika="+id_uzytkownika);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
