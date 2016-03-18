import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Package1.Polecenia;
import static Package1.Polecenia.*;

/**
 * Servlet implementation class Usun
 */
@WebServlet("/Usun")
public class Usun extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Usun() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int id_ankiety = Integer.parseInt(request.getParameter("id_ankiety"));
		int id_uzytkownika = Integer.parseInt(request.getParameter("id_uzytkownika"));
		String komunikat_usuwanie = "";
		try {
			if(Polecenia.podajWlascicielaAnkiety(id_ankiety)==id_uzytkownika){
				try {
					komunikat_usuwanie = usunAnkiete(id_ankiety);
					response.sendRedirect("Profil.jsp");
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					komunikat_usuwanie = "Przepraszamy coœ posz³o nie tak.";
					response.sendRedirect("Profil.jsp");
				}
				session.setAttribute("komunikatDlaProfil", komunikat_usuwanie);
			} else {
				session.setAttribute("zalogowanoKomunikat", "Nie mo¿esz usun¹æ ankiety, której nie jesteœ w³aœcicielem !");
			   	response.sendRedirect("Zalogowano.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}