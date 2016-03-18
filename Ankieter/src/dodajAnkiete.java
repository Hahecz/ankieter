import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class dodajAnkiete
 */
@WebServlet("/dodajAnkiete")
public class dodajAnkiete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dodajAnkiete() {
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
		String ilosc_pytan = request.getParameter("ilosc_pytan");
		String nazwa_ankiety = request.getParameter("nazwa_ankiety");
		String[] czyAktywna = request.getParameterValues("czy_aktywna");
		
		if(czyAktywna == null) {
			session.setAttribute("czy_aktywna", "N");
		} else {
			session.setAttribute("czy_aktywna", "T");
		}
		
		session.setAttribute("nazwa_ankiety", nazwa_ankiety);
		session.setAttribute("ilosc_pytan", ilosc_pytan);		
		response.sendRedirect("Tworzenie_ankiety.jsp");
	}
}