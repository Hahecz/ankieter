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
 * Servlet implementation class Rejestracja
 */
@WebServlet("/Rejestracja")
public class Rejestracja extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rejestracja() {
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
		String haslo = request.getParameter("Haslo");
		String potwierdz_haslo = request.getParameter("PotwierdzHaslo");
		String komunikat_rejestracja = null;
		if(login.length()<=50){
			if(haslo.length()<=50){
				if(!login.equals("")){
					if(!haslo.equals("")){
						if(haslo.equals(potwierdz_haslo)){
							 try {
									connect();
									ResultSet resultSet2 = statement.executeQuery("select Login from uzytkownicy where Login='"+login+"'");
							        while (resultSet2.next()) {
							           	login1 = resultSet2.getString(1);
							        }					
									if(!login.equals(login1)){					
										statement.executeUpdate("INSERT INTO uzytkownicy VALUES(null,'"+login+"','"+CryptWithMD5.cryptWithMD5(haslo)+"',0,'N');");	       
										komunikat_rejestracja = "Twoje konto zosta³o utworzone poprawnie.\\n Dziêkujemy za rejestracjê.";
										session.setAttribute("logowanieKomunikat", komunikat_rejestracja);
										response.sendRedirect("Zaloguj.jsp");
									}
									else {
										komunikat_rejestracja = "Ten login jest ju¿ zajêty !\\n Prosimy wybraæ inny.";
										session.setAttribute("logowanieKomunikat", komunikat_rejestracja);
										response.sendRedirect("Rejestracja.jsp");
									}			        
									close();	
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
						else {
							komunikat_rejestracja = "Has³a nie s¹ takie same !";
							session.setAttribute("logowanieKomunikat", komunikat_rejestracja);
							response.sendRedirect("Rejestracja.jsp");
						}
					}
					else{
						komunikat_rejestracja = "Nie poda³eœ ¿adnego has³a !";
						session.setAttribute("logowanieKomunikat", komunikat_rejestracja);
						response.sendRedirect("Rejestracja.jsp");
					}
				}
				else {
					komunikat_rejestracja = "Nie poda³eœ ¿adnego loginu !";
					session.setAttribute("logowanieKomunikat", komunikat_rejestracja);
					response.sendRedirect("Rejestracja.jsp");
				}
			} else {
				komunikat_rejestracja = "Has³o nie mo¿e byæ d³u¿sze ni¿ 50 znaków !";
				session.setAttribute("logowanieKomunikat", komunikat_rejestracja);
				response.sendRedirect("Rejestracja.jsp");
			}
		} else {
			komunikat_rejestracja = "Login nie mo¿e byæ d³u¿szy ni¿ 50 znaków !";
			session.setAttribute("logowanieKomunikat", komunikat_rejestracja);
			response.sendRedirect("Rejestracja.jsp");
		}		
	}
}