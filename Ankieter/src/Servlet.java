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
 * Servlet implementation class AktualizujAnkiete
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idAnkiety = request.getParameter("id_ankiety");
		String akcja = request.getParameter("akcja");
		try {
			if(idAnkiety != null) {
				Polecenia.ustawStanAnkiety(idAnkiety, akcja);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("Profil.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String komunikat = "";
		int id_ankiety = 0;
		int id_pytania = 0;
		String nazwa_ankiety = (String) session.getAttribute("nazwa_ankiety");
		String uzytkownik = (String) session.getAttribute("Login");
		String czyAktywna = (String) session.getAttribute("czy_aktywna");
		try {
			id_ankiety = dodajAnkiete(nazwa_ankiety, uzytkownik, czyAktywna);
			int ilosc_pytan = Integer.parseInt(request.getParameter("ilosc_pytan"));
			for(int i=1; i<=ilosc_pytan; i++){
				String pytanie = request.getParameter("pytanie"+i);
				String rodzaj_odpowiedzi = (String) session.getAttribute("rodzaj_odpowiedzi_"+i);
				int ilosc_odpowiedzi = 0;
				if(!rodzaj_odpowiedzi.equals("tekstowa")){
					ilosc_odpowiedzi = (Integer) session.getAttribute("ilosc_odpowiedzi_na_pytanie"+i);
				}
				if(!pytanie.equals("")){
					if(pytanie.length()<199){
						if(rodzaj_odpowiedzi.equals("tekstowa")){
							id_pytania = dodajPytanie(pytanie, id_ankiety,i , 3);
						} else if(rodzaj_odpowiedzi.equals("wielokrotnego wyboru")){
							id_pytania = dodajPytanie(pytanie, id_ankiety,i , 2);
						} else if(rodzaj_odpowiedzi.equals("jednokrotnego wyboru")){
							id_pytania = dodajPytanie(pytanie, id_ankiety,i , 1);
						} else {
							id_pytania = dodajPytanie(pytanie, id_ankiety,i , 4);
						}
					} else {
						komunikat = "";
						usunAnkiete(id_ankiety);
						session.setAttribute("komunikatDlaTworzenie_ankiety_cz2", "Pytanie nr."+i+"jest zbyt d³ugie !\\nPytania nie mog¹ byæ d³u¿sze ni¿ 200 znaków.");
						response.sendRedirect("Tworzenie_ankiety_cz2.jsp");
						break;
					}
				} else {
					komunikat = "";
					usunAnkiete(id_ankiety);
					session.setAttribute("komunikatDlaTworzenie_ankiety_cz2", "W polu z pytaniem nr."+i+" nic nie wpisa³eœ !");
					response.sendRedirect("Tworzenie_ankiety_cz2.jsp");
					break;
				}
				if(rodzaj_odpowiedzi.equals("tekstowa")){
					String dlugosc = request.getParameter("dlugosc_odpowiedzi_na_pytanie"+i);
					if(dlugosc!=null && !dlugosc.equals("")){
						try{
							int dlugosc_odpowiedzi = Integer.parseInt(dlugosc);
							if(dlugosc_odpowiedzi>0 && dlugosc_odpowiedzi<=99999){
								dodajOdpowiedz("OdpowiedŸ typu tekstowego.", id_pytania, dlugosc_odpowiedzi);
								komunikat = "Wszystko OK";
							} else {
								komunikat = "";
								usunAnkiete(id_ankiety);
								session.setAttribute("komunikatDlaTworzenie_ankiety_cz2", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+i+" poda³eœ zbyt du¿¹/ma³¹ liczbê ! \\nD³ugoœæ odpowiedzi musi zawieraæ siê w przedziale [1,99999].");
								response.sendRedirect("Tworzenie_ankiety_cz2.jsp");
								break;
							}
						} catch (NumberFormatException e) {
							komunikat = "";
							usunAnkiete(id_ankiety);
							session.setAttribute("komunikatDlaTworzenie_ankiety_cz2", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+i+" nie poda³eœ poprawnej liczby !");
							response.sendRedirect("Tworzenie_ankiety_cz2.jsp");
							break;
						}
					} else {
						komunikat = "";
						usunAnkiete(id_ankiety);
						session.setAttribute("komunikatDlaTworzenie_ankiety_cz2", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+i+" nie poda³eœ ¿adnej liczby !");
						response.sendRedirect("Tworzenie_ankiety_cz2.jsp");
						break;
					}
				} else {
					for(int j=1; j<=ilosc_odpowiedzi; j++){
						String odpowiedz = request.getParameter("pytanie"+i+"_odpowiedz"+j);
						if(!odpowiedz.equals("")){						
							if(odpowiedz.length()<=100){	
								dodajOdpowiedz(odpowiedz, id_pytania, 0);
								komunikat = "Wszystko OK";
							} else {
								komunikat = "";
								usunAnkiete(id_ankiety);
								session.setAttribute("komunikatDlaTworzenie_ankiety_cz2", "OdpowiedŸ nr."+j+" do pytania nr."+i+" jest zbyt d³uga !\\nOdpowiedŸ nie mo¿e byæ d³u¿sza ni¿ 100 znaków.");
								response.sendRedirect("Tworzenie_ankiety_cz2.jsp");
								break;
							}	
						} else {
							komunikat = "";
							usunAnkiete(id_ankiety);
							session.setAttribute("komunikatDlaTworzenie_ankiety_cz2", "W polu z odpowiedzi¹ nr."+j+" do pytania nr."+i+" nic nie wpisa³eœ !");
							response.sendRedirect("Tworzenie_ankiety_cz2.jsp");
							break;
						}
					}
				}
				if(komunikat.equals(""))
					break;
			}
			if(!komunikat.equals("")){
				session.setAttribute("komunikatDlaProfil", "Twoja ankieta zosta³a utworzona pomyœlnie.");
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