import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static Package1.Polecenia.*;

/**
 * Servlet implementation class AktualizujAnkiete
 */
@WebServlet("/AktualizujAnkiete")
public class AktualizujAnkiete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AktualizujAnkiete() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String komunikat = "";
		try {
			int id_ankiety = Integer.parseInt(request.getParameter("id_ankiety"));
			String nazwa_ankiety = request.getParameter("nazwa_ankiety");
			if(nazwa_ankiety!=null && !nazwa_ankiety.equals("")){
				if(nazwa_ankiety.length()<149){
					komunikat = aktualizujAnkiete(id_ankiety, nazwa_ankiety);
					ArrayList<Integer> pytania = idPytan(id_ankiety);
					for(int i=0; i<pytania.size(); i++){
						String pytanie = request.getParameter("pytanie"+i);
						if(pytanie!=null && !pytanie.equals("")){
							if(pytanie.length()<199){
								komunikat = aktualizujPytanie(pytania.get(i), pytanie);
							} else {
								komunikat = "";
								session.setAttribute("komunikatEdytuj", "Pytanie nr."+(i+1)+"jest zbyt d³ugie !\\nPytania nie mog¹ byæ d³u¿sze ni¿ 200 znaków.");
								response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
								break;
							}
						} else {
							komunikat = "";
							session.setAttribute("komunikatEdytuj", "W polu z pytaniem nr."+(i+1)+" nic nie wpisa³eœ !");
							response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
							break;
						}
						ArrayList<Integer> odpowiedzi = idOdpowiedzi(pytania.get(i));
						if(podajRodzajPytania(pytania.get(i))==1 || podajRodzajPytania(pytania.get(i))==2 || podajRodzajPytania(pytania.get(i))==4){
							for(int j=0; j<odpowiedzi.size(); j++){
								String odpowiedz = request.getParameter("pytanie"+i+"odp"+j);
								if(odpowiedz!=null && !odpowiedz.equals("")){
									if(odpowiedz.length()<=100){
										komunikat = aktualizujOdpowiedz(odpowiedzi.get(j), odpowiedz, 0);
									} else {
										komunikat = "";
										session.setAttribute("komunikatEdytuj", "OdpowiedŸ nr."+(j+1)+" do pytania nr."+(i+1)+"jest zbyt d³uga !\\nOdpowiedzi nie mog¹ byæ d³u¿sze ni¿ 100 znaków.");
										response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
										break;
									}
								} else {
									komunikat = "";
									session.setAttribute("komunikatEdytuj", "W polu z odpowiedzi¹ nr."+(j+1)+" do pytania nr."+(i+1)+" nic nie wpisa³eœ !");
									response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
									break;
								}
							}
						} else {
							String dlugosc = request.getParameter("pytanie"+i+"dlugosc_odp");
							if(dlugosc!=null && !dlugosc.equals("")){
								try{
									int dlugosc_odpowiedzi = Integer.parseInt(dlugosc);
									if(dlugosc_odpowiedzi>0 && dlugosc_odpowiedzi<=99999){
										komunikat = "OK";
										komunikat = aktualizujOdpowiedz(odpowiedzi.get(0), "OdpowiedŸ typu tekstowego.", dlugosc_odpowiedzi);
									} else {
										komunikat = "";
										session.setAttribute("komunikatEdytuj", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+(i+1)+" poda³eœ zbyt du¿¹/ma³¹ liczbê ! \\nD³ugoœæ odpowiedzi musi zawieraæ siê w przedziale [1,99999].");
										response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
										break;
									}
								} catch (NumberFormatException e) {
									komunikat = "";
									session.setAttribute("komunikatEdytuj", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+(i+1)+" nie poda³eœ poprawnej liczby !");
									response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
									break;
								}
							} else {
								komunikat = "";
								session.setAttribute("komunikatEdytuj", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+(i+1)+" nie poda³eœ ¿adnej liczby !");
								response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
								break;
							}
						}
						if(komunikat.equals(""))
							break;
					}
				} else {
					komunikat = "";
					session.setAttribute("komunikatEdytuj", "Nazwa ankiety nie mo¿e byæ d³uzsza ni¿ 150 znaków !");
					response.sendRedirect("Edytuj.jsp");
				}
			} else {
				komunikat = "";
				session.setAttribute("komunikatEdytuj", "Pole z nazw¹ ankiety nie mo¿e byæ puste !");
				response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
			}
			if(!komunikat.equals("")){
				session.setAttribute("komunikatDlaProfil", "Twoja ankieta zosta³a zaktualizowana poprawnie");
				response.sendRedirect("Profil.jsp");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
