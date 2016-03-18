import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DodajAnkieteCz2
 */
@WebServlet("/DodajAnkieteCz2")
public class DodajAnkieteCz2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DodajAnkieteCz2() {
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
		int ilosc_pytan = 0;
		int ilosc_odpowiedzi = 0;
		String ip =  (String) session.getAttribute("ilosc_pytan");
		ilosc_pytan = Integer.parseInt(ip);
		for(int i=1; i<=ilosc_pytan; i++){
			komunikat = "";
			String pytanie = request.getParameter("pytanie"+i);
			if(!pytanie.equals("")){
				if(pytanie.length()<=200){
					String io = request.getParameter("ilosc_odpowiedzi_na_pytanie"+i);
					String rodzaj_odpowiedzi = request.getParameter("rodzaj_odpowiedzi_"+i);
					if(rodzaj_odpowiedzi.equals("tekstowa")){
						String dlugosc = request.getParameter("dlugosc_odpowiedzi_na_pytanie"+i);
						if(dlugosc!=null && !dlugosc.equals("")){
							try{
								int dlugosc_odpowiedzi = Integer.parseInt(dlugosc);
								if(dlugosc_odpowiedzi>0 && dlugosc_odpowiedzi<=99999){
									komunikat = "OK";
									session.setAttribute("pytanie"+i, pytanie);
									session.setAttribute("rodzaj_odpowiedzi_"+i, rodzaj_odpowiedzi);
									session.setAttribute("dlugosc_odpowiedzi_na_pytanie"+i, dlugosc_odpowiedzi);
								} else {
									session.setAttribute("komunikatDlaTworzenie_ankiety", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+i+" poda³eœ zbyt du¿¹/ma³¹ liczbê ! \\nD³ugoœæ odpowiedzi musi zawieraæ siê w przedziale [1,99999].");
									break;
								}
							} catch (NumberFormatException e) {
								session.setAttribute("komunikatDlaTworzenie_ankiety", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+i+" nie poda³eœ poprawnej liczby !");
								break;
							}
						} else {
							session.setAttribute("komunikatDlaTworzenie_ankiety", "W polu z maksymaln¹ d³ugoœci¹ odpowiedzi do pytania nr."+i+" nie poda³eœ ¿adnej liczby !");
							break;
						}
					} else {
						if(!io.equals("")){
							try{
								ilosc_odpowiedzi = Integer.parseInt(io);
								if(ilosc_odpowiedzi>0){
									komunikat = "OK";
									session.setAttribute("pytanie"+i, pytanie);
									session.setAttribute("rodzaj_odpowiedzi_"+i, rodzaj_odpowiedzi);
									session.setAttribute("ilosc_odpowiedzi_na_pytanie"+i, ilosc_odpowiedzi);
								} else {
									session.setAttribute("komunikatDlaTworzenie_ankiety", "W polu z iloœci¹ odpowiedzi do pytania nr."+i+" poda³eœ zbyt ma³¹ liczbê !\\nMinimalna iloœæ odpowiedzi to 1 !");
									break;
								}
							} catch (NumberFormatException e) {
								session.setAttribute("komunikatDlaTworzenie_ankiety", "W polu z iloœci¹ odpowiedzi do pytania nr."+i+" nie poda³eœ poprawnej liczby !");
								break;
							}
						} else {
							session.setAttribute("komunikatDlaTworzenie_ankiety", "W polu z iloœci¹ odpowiedzi do pytania nr."+i+" nie poda³eœ ¿adnej liczby !");
							break;
						}
					}
				} else {
					session.setAttribute("komunikatDlaTworzenie_ankiety", "Pytanie nr."+i+"jest zbyt d³ugie !\\nPytania nie mog¹ byæ d³u¿sze ni¿ 200 znaków.");
					break;
				}
			} else {
				session.setAttribute("komunikatDlaTworzenie_ankiety", "W polu z pytaniem nr."+i+" nic nie wpisa³eœ !");
				break;
			}
		}
		if(komunikat.equals("OK")){
			response.sendRedirect("Tworzenie_ankiety_cz2.jsp");
		} else {
			response.sendRedirect("Tworzenie_ankiety.jsp");
		}
	}
}