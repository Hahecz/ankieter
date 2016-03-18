package Package1;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Wyczyszczacz extends HttpServlet {

	public void init() throws ServletException {
		
		runna uruchom = new runna();
		Thread t = new Thread(uruchom);
		t.start();
	}

	public static void pr(Object o) {
		System.out.println(o.toString());
	}

	public class runna implements Runnable {
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			try {
				int godzina = 23;				
				//¿eby czyszczenie wykona³o sie conajmniej raz przy starcie
				while (true) {	
					
					if (godzina == 23) {
						pr("czyszczenie wyników ankiety");
						Polecenia.wyczyscWynikiObiadow();
						pr("wyczyszczono odpowiedzi ankiet");
					} else {
						pr("to nie pora na czyszczenie");
					}					
					Thread.sleep(1800000);
					Date data = new Date();
					godzina = data.getHours();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}