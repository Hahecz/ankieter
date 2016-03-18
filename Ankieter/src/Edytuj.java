import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
 * Servlet implementation class Edytuj
 */
@WebServlet("/Edytuj")
public class Edytuj extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edytuj() {
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
		try {
			if(Polecenia.podajWlascicielaAnkiety(id_ankiety)==id_uzytkownika){
				try {
					String linia = "";
				    connect();
			        ResultSet resultSet = statement.executeQuery("SELECT * FROM odpowiedzi WHERE ID_ankieta = "+id_ankiety);
			        ResultSetMetaData rsmd = resultSet.getMetaData();
			        int liczbaKolumn = rsmd.getColumnCount();
			        while(resultSet.next()) {
			        	linia = "";
			            for(int i=1; i<=liczbaKolumn; i++){
			            	linia = linia + resultSet.getString(i) + " ";
			            }
			        }
			        if(linia.equals("")){
			         	response.sendRedirect("Edytuj.jsp");
			           	session.setAttribute("id_ankiety", request.getParameter("id_ankiety"));
			        } else {
			           	session.setAttribute("komunikatDlaProfil", "Przepraszamy ale nie mo¿esz edytowaæ ankiety, \\nw której ktoœ ju¿ bra³ udzia³.");
			           	response.sendRedirect("Profil.jsp");
			        }
			        close();
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				session.setAttribute("zalogowanoKomunikat", "Nie mo¿esz edytowaæ ankiety, której nie jesteœ w³aœcicielem !");
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