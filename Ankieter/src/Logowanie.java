import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Package1.CryptWithMD5;
import static Package1.Polecenia.*;

/**
 * Servlet implementation class Logowanie
 */
@WebServlet("/Logowanie")
public class Logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logowanie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String login = request.getParameter("Login");
		String login1 = null;
		String haslo = CryptWithMD5.cryptWithMD5(request.getParameter("Haslo"));
		String haslo1 = null;
		String logowanieKomunikat = null;
		String czyAdmin = null;
		session.setAttribute("Login", login);
        try {
			connect();
	        ResultSet resultSet1 = statement.executeQuery("select Login, czy_administrator from uzytkownicy where Login='"+login+"'");
	        while (resultSet1.next()) {
	           	login1 = resultSet1.getString(1);
	           	czyAdmin =  resultSet1.getString(2);
	        }
	        ResultSet resultSet2 = statement.executeQuery("select Haslo from uzytkownicy where Login='"+login+"'");
	        while (resultSet2.next()) {
	           	haslo1 = resultSet2.getString(1);
	        }
			close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if(login.equals(login1)){
			if(haslo.equals(haslo1)){
				String zmienna = request.getParameter("id_ankiety"); 
				if(!zmienna.equals("null")){
					response.sendRedirect("Ankieta.jsp?id_ankiety="+request.getParameter("id_ankiety"));
				} else {
					response.sendRedirect("Aktywne.jsp");
				}
				session.setAttribute("czy_admin",czyAdmin);
			}
			else {
				logowanieKomunikat = "Nie poprawny login i/lub has³o ! \\nPamiêtaj o ro¿ró¿nianiu du¿ych i ma³ych liter.";
				session.setAttribute("logowanieKomunikat", logowanieKomunikat);
				response.sendRedirect("Zaloguj.jsp?id_ankiety="+request.getParameter("id_ankiety"));
			}
		}
		else {
			logowanieKomunikat = "Nie poprawny login i/lub has³o ! \\nPamiêtaj o ro¿ró¿nianiu du¿ych i ma³ych liter.";
			session.setAttribute("logowanieKomunikat", logowanieKomunikat);
			response.sendRedirect("Zaloguj.jsp?id_ankiety="+request.getParameter("id_ankiety"));			
		}
	}
}