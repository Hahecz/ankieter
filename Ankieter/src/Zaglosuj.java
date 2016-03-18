import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Package1.Polecenia;
import static Package1.Polecenia.*;

/**
 * Servlet implementation class Zaglosuj
 */
@WebServlet("/Zaglosuj")
public class Zaglosuj extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Zaglosuj() {
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
		int id_ankiety = Integer.parseInt(request.getParameter("id_ankiety"));		
		ArrayList<Integer> pytania;
		try {
			int id_uzytkownika = podajIdUzytkownika((String)session.getAttribute("Login"));
			if(czyGlosowal(id_ankiety,id_uzytkownika)) {
				throw new ZaglosowalException("");
			}
			
			int wynik = 0;
			pytania = idPytan(id_ankiety);
			for(int k=0; k<pytania.size(); k++){
				int id_pytania = pytania.get(k);
				id_uzytkownika = podajIdUzytkownika((String)session.getAttribute("Login"));
				ArrayList<Integer> odpowiedzi = idOdpowiedzi(id_pytania);
				int id_odpowiedzi = 0;
				int rodzaj_pytania = Polecenia.podajRodzajPytania(id_pytania);
				if(rodzaj_pytania==1 || rodzaj_pytania==4){
					String odpowiedz = request.getParameter("pytanie"+k);
					for(int i=0; i<odpowiedzi.size(); i++){
						int io = odpowiedzi.get(i);
						if(odpowiedz.equals(Polecenia.podajOdpowiedz(io))){
							id_odpowiedzi = io;
						}
					}
					wynik = Polecenia.wstawOdpowiedz(id_ankiety, id_pytania, id_uzytkownika, id_odpowiedzi, odpowiedz);
				} else if(rodzaj_pytania==2){
					String[] odpowiedz = request.getParameterValues("pytanie"+k);
					if(odpowiedz != null){
						for(int i=0; i<odpowiedz.length; i++){
							for(int j=0; j<odpowiedzi.size(); j++){
								int io = odpowiedzi.get(j);
								if(odpowiedz[i].equals(Polecenia.podajOdpowiedz(io))){
									id_odpowiedzi = io;
								}
							}
							wynik = Polecenia.wstawOdpowiedz(id_ankiety, id_pytania, id_uzytkownika, id_odpowiedzi, odpowiedz[i]);
						}
					}
				} else {
					String odpowiedz = request.getParameter("pytanie"+k);
					if(odpowiedz!=null && !odpowiedz.equals("") && !odpowiedz.equals("Podaj odpowiedŸ na pytanie...")){
						if(odpowiedz.length()<=podajDlugoscOdpowiedzi(odpowiedzi.get(0))){
							wynik = Polecenia.wstawOdpowiedz(id_ankiety, id_pytania, id_uzytkownika, odpowiedzi.get(0), odpowiedz);
						} else {
							wynik = 0;
							usunOdpowiedz(id_ankiety, id_uzytkownika);
							session.setAttribute("komunikatDlaAnkieta", "OdpowiedŸ do pytania nr."+(k+1)+" musi byæ krótsza ni¿ "+podajDlugoscOdpowiedzi(odpowiedzi.get(0))+" !");
							break;
						}
					} else {
						wynik = 0;
						usunOdpowiedz(id_ankiety, id_uzytkownika);
						session.setAttribute("komunikatDlaAnkieta", "W polu z odpowiedzi¹ do pytania nr."+(k+1)+" nic nie wpisa³eœ !");
						break;
					}
				}
			}
			if(wynik!=0){
				session.setAttribute("komunikatDlaWynikDlaGoscia", "Dziêkujemy za wype³nienie ankiety.");
				response.sendRedirect("WynikDlaGoscia.jsp?id_ankiety="+id_ankiety);
			} else {
				response.sendRedirect("Ankieta.jsp?id_ankiety="+id_ankiety);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ZaglosowalException e) {
			// TODO Auto-generated catch block
			session.setAttribute("komunikatDlaWynikDlaGoscia", "Ju¿ zag³osowa³eœ na t¹ ankietê.");
			response.sendRedirect("WynikDlaGoscia.jsp?id_ankiety="+id_ankiety);
		}
	}
	
	private class ZaglosowalException extends Exception {
	    public ZaglosowalException(String message) {
	        super(message);
	    }
	}
}