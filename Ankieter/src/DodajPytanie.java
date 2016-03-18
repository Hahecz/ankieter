import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static Package1.Polecenia.*;

/**
 * Servlet implementation class DodajPytanie
 */
@WebServlet("/DodajPytanie")
public class DodajPytanie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DodajPytanie() {
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
		String komunikat = "";
		String pytanie = request.getParameter("pytanie");
		int id_ankiety = Integer.parseInt(request.getParameter("id_ankiety"));
		try{
			if(!pytanie.equals("")){
				if(pytanie.length()<=200){
					String io = request.getParameter("ilosc_odpowiedzi_na_pytanie");
					String rodzaj_odpowiedzi = request.getParameter("rodzaj_odpowiedzi");
					if(rodzaj_odpowiedzi.equals("tekstowa")){
						String dlugosc = request.getParameter("dlugosc_odpowiedzi");
						if(dlugosc!=null && !dlugosc.equals("")){
							try{
								int dlugosc_odpowiedzi = Integer.parseInt(dlugosc);
								if(dlugosc_odpowiedzi>0 && dlugosc_odpowiedzi<=99999){
									komunikat = "ok";
									int id_pytania = dodajPytanie(pytanie, id_ankiety, podajMaxNumerPytania(id_ankiety), 3);
									dodajOdpowiedz("OdpowiedŸ typu tekstowego.", id_pytania, dlugosc_odpowiedzi);
								} else {
									komunikat = "D³ugoœæ odpowiedzi musi zawieraæ siê w przedziale [1,99999] !";
								}
							} catch (NumberFormatException e) {
								komunikat = "Nie poda³eœ poprawnej liczby w polu z d³ugoœci¹ odpowiedzi !";
							}
						} else {
							komunikat = "W polu z d³ugoœci¹ odpowiedzi nie poda³eœ ¿adnej liczby !";
						}
					} else if(rodzaj_odpowiedzi.equals("wielokrotnego wyboru")){
						if(!io.equals("")){
							try{
								int ilosc_odpowiedzi = Integer.parseInt(io);
								if(ilosc_odpowiedzi>0){
									komunikat = "ok";
									int id_pytania = dodajPytanie(pytanie, id_ankiety, podajMaxNumerPytania(id_ankiety), 2);
									for(int j=1; j<=ilosc_odpowiedzi; j++){
										dodajOdpowiedz("OdpowiedŸ"+j, id_pytania, 0);
									}
								} else {
									komunikat = "Minimalna iloœæ odpowiedzi to 1 !";
								}
							} catch (NumberFormatException e) {
								komunikat = "Nie poda³eœ poprawnej liczby w polu z iloœci¹ odpowiedzi !";
							}
						} else {
							komunikat = "W polu z iloœci¹ odpowiedzi nie poda³eœ ¿adnej liczby !";
						}
					} else if(rodzaj_odpowiedzi.equals("lista rozwijalna")){
						if(!io.equals("")){
							try{
								int ilosc_odpowiedzi = Integer.parseInt(io);
								if(ilosc_odpowiedzi>0){
									komunikat = "ok";
									int id_pytania = dodajPytanie(pytanie, id_ankiety, podajMaxNumerPytania(id_ankiety), 4);
									for(int j=1; j<=ilosc_odpowiedzi; j++){
										dodajOdpowiedz("OdpowiedŸ"+j, id_pytania, 0);
									}
								} else {
									komunikat = "Minimalna iloœæ odpowiedzi to 1 !";
								}
							} catch (NumberFormatException e) {
								komunikat = "Nie poda³eœ poprawnej liczby w polu z iloœci¹ odpowiedzi !";
							}
						} else {
							komunikat = "W polu z iloœci¹ odpowiedzi nie poda³eœ ¿adnej liczby !";
						}
					} else {
						if(!io.equals("")){
							try{
								int ilosc_odpowiedzi = Integer.parseInt(io);
								if(ilosc_odpowiedzi>0){
									komunikat = "ok";
									int id_pytania = dodajPytanie(pytanie, id_ankiety, podajMaxNumerPytania(id_ankiety), 1);
									for(int j=1; j<=ilosc_odpowiedzi; j++){
										dodajOdpowiedz("OdpowiedŸ"+j, id_pytania, 0);
									}
								} else {
									komunikat = "Minimalna iloœæ odpowiedzi to 1 !";
								}
							} catch (NumberFormatException e) {
								komunikat = "Nie poda³eœ poprawnej liczby w polu z iloœci¹ odpowiedzi !";
							}
						} else {
							komunikat = "W polu z iloœci¹ odpowiedzi nie poda³eœ ¿adnej liczby !";
						}
					}
				} else {
					komunikat = "Pytanie nie mo¿e byæ d³u¿sze ni¿ 200 znaków !";
				}
			} else {
				komunikat = "W polu z pytaniem nic nie wpisa³eœ !";
			}
		} catch(ClassNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(komunikat.equals("ok")){
			session.setAttribute("komunikatEdytuj", "Pytanie zosta³o pomyœlnie dodane.");
			response.sendRedirect("Edytuj.jsp?id_ankiety="+id_ankiety);
		} else {
			session.setAttribute("komunikatDlaDodajPytanie", komunikat);
			response.sendRedirect("DodajPytanie.jsp?id_ankiety="+id_ankiety);
		}
	}
}
