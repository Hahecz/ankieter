package Package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;



public class Polecenia {
	public static Connection connection = null;
	public static Statement statement = null;
	public static PreparedStatement pstmt = null;
	
	public static void connect() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.3.34.2:3306/ankieta?useUnicode=true&characterEncoding=UTF-8","adminfAC4vmX", "bnptf8NxNTra");		
		statement = connection.createStatement();
		//connection = (Connection) DriverManager.getConnection("jdbc:mysql://10.132.216.22:3306/ankiety?useUnicode=true&characterEncoding=UTF-8","arek", "arek");		
		//statement = connection.createStatement();
	}
	
	public static void close() throws SQLException{
		statement.close();
        connection.close();        
	}
	
	public static int dodajAnkiete(String nazwaAnkiety, String uzytkownik, String czyAktywna) throws SQLException, ClassNotFoundException{
		int id_nowej_ankiety = 0;	
		connect();
		if (connection != null) {
			if(!nazwaAnkiety.equals("")){
				int idUzytkownika = 0;
				int ilosc_ankiet = 0;
				pstmt = connection.prepareStatement("select ID from uzytkownicy where Login = ?");
				pstmt.setString(1, uzytkownik);
				ResultSet resultSet = pstmt.executeQuery();
		        while (resultSet.next()) {
		            idUzytkownika = Integer.parseInt(resultSet.getString(1));
		        }
		        pstmt = connection.prepareStatement("select Ilosc_ankiet from uzytkownicy where Login = ?");
		        pstmt.setString(1, uzytkownik);
		        resultSet = pstmt.executeQuery();
		        while (resultSet.next()) {
		            ilosc_ankiet = Integer.parseInt(resultSet.getString(1));
		            ilosc_ankiet = ilosc_ankiet+1;
		        }
		        pstmt = connection.prepareStatement("INSERT INTO ankieta VALUES(null,?,?,?)");
		        pstmt.setInt(1, idUzytkownika);
		        pstmt.setString(2, nazwaAnkiety);
		        if(czyAktywna.equals("T")){
		        	pstmt.setString(3, "T");
		        } else {
		        	pstmt.setString(3, "N");
		        }
		        pstmt.executeUpdate();
		        
		        pstmt = connection.prepareStatement("UPDATE uzytkownicy SET Ilosc_ankiet = ? WHERE Login = ?");
		        pstmt.setInt(1, ilosc_ankiet);
		        pstmt.setString(2, uzytkownik);
		        pstmt.executeUpdate();
		        
		        pstmt = connection.prepareStatement("SELECT ID FROM ankieta WHERE ID_uzytkownika = ? AND Nazwa = ?");
		        pstmt.setInt(1, idUzytkownika);
		        pstmt.setString(2, nazwaAnkiety);
		        resultSet = pstmt.executeQuery();
		        while (resultSet.next()){
					id_nowej_ankiety = Integer.parseInt(resultSet.getString(1));
				}
			}
		}
		close();
		return id_nowej_ankiety;
	}
	
	public static String aktualizujAnkiete(int id_ankiety, String nazwa_ankiety) throws SQLException, ClassNotFoundException {
		String komunikat = "";
		connect();		
	    if (connection != null) {
	        int wynik = statement.executeUpdate("UPDATE ankieta SET Nazwa = '"+nazwa_ankiety+"' WHERE ID = "+id_ankiety+";");
	        if(wynik==1){
	        	komunikat = "Twoje pytanie zosta³o zaktualizowanie poprawnie.";
	        }
	    }
	    close();
		return komunikat;
	}

	public static String podajAnkiete(int id_ankiety) throws SQLException, ClassNotFoundException{
		String linia="";
		connect();		
		if (connection != null) {
			ResultSet resultSet = statement.executeQuery("Select Nazwa from ankieta where ID="+id_ankiety+";");
	        while (resultSet.next()) {
	          	linia = resultSet.getString(1);
	        }
		}	
		close();
		return linia;
	}
	
	public static int podajWlascicielaAnkiety(int id_ankiety) throws SQLException, ClassNotFoundException{
		int id_wlasciciela = 0;
		connect();		
		if (connection != null) {
			ResultSet resultSet = statement.executeQuery("Select ID_uzytkownika from ankieta where ID="+id_ankiety+";");
	        while (resultSet.next()) {
	          	id_wlasciciela = resultSet.getInt(1);
	        }
		}	
		close();
		return id_wlasciciela;
	}

	public static ArrayList<Integer> idAnkiet(String uzytkownik) throws SQLException, ClassNotFoundException{
		ArrayList<Integer> ankiety = new ArrayList<Integer>();
		connect();
		if (connection != null) {
		    int id = 0;
			ResultSet resultSet1 = statement.executeQuery("select ID from uzytkownicy where Login='"+uzytkownik+"';");
		    while (resultSet1.next()) {
		        id = Integer.parseInt(resultSet1.getString(1));
		    }
			ResultSet resultSet = statement.executeQuery("Select * from ankieta where ID_uzytkownika = "+id+";");
	        while (resultSet.next()) {
	            ankiety.add(Integer.parseInt(resultSet.getString(1)));
	        }
		}	
		close();
		return ankiety;
	}
	
	public static ArrayList<Integer> idAktywnychAnkiet() throws SQLException, ClassNotFoundException{
		ArrayList<Integer> ankiety = new ArrayList<Integer>();
		connect();
		if (connection != null) {		    
			ResultSet resultSet = statement.executeQuery("Select * from ankieta where czy_aktywna = 'T';");
	        while (resultSet.next()) {
	            ankiety.add(Integer.parseInt(resultSet.getString(1)));
	        }
		}	
		close();
		return ankiety;
	}

	public static int dodajPytanie(String pytanie, int id_ankiety, int numer_pytania, int id_typu_pytania) throws ClassNotFoundException, SQLException{	
		int id_nowegoPytania = 0;
		connect();
		if (connection != null) {
	        statement.executeUpdate("INSERT INTO pytania VALUES(null,"+id_ankiety+",'"+pytanie+"',"+numer_pytania+","+id_typu_pytania+");");
	        ResultSet resultSet = statement.executeQuery("SELECT MAX(ID) FROM pytania;");
			while (resultSet.next()){
				id_nowegoPytania = Integer.parseInt(resultSet.getString(1));
			}
		}
		close();
		return id_nowegoPytania;
	}
	
	public static String aktualizujPytanie(int id_pytania, String pytanie) throws SQLException, ClassNotFoundException {
		String komunikat = "";
		connect();	
	    if (connection != null) {
	        int wynik = statement.executeUpdate("UPDATE pytania SET Pytanie = '"+pytanie+"' WHERE ID = "+id_pytania+";");
	        if(wynik==1){
	        	komunikat = "Twoje pytanie zosta³o zaktualizowanie poprawnie.";
	        }
	    }
	    close();
		return komunikat;
	}
	
	public static int podajMaxNumerPytania(int id_ankiety) throws SQLException, ClassNotFoundException {
		int numer = 0;
		connect();	
	    if (connection != null) {
	    	ResultSet resultSet1 = statement.executeQuery("SELECT MAX(Numer_pytania)+1 FROM pytania WHERE ID_ankieta = "+id_ankiety+";");
	        if(resultSet1.next()){
	        	numer = resultSet1.getInt(1);
	        }
	    }
	    close();
		return numer;
	}
	
	public static int podajNumerPytania(int id_pytania) throws SQLException, ClassNotFoundException {
		int numer = 0;
		connect();	
	    if (connection != null) {
	    	ResultSet resultSet1 = statement.executeQuery("SELECT Numer_pytania FROM pytania WHERE ID = "+id_pytania+";");
	        if(resultSet1.next()){
	        	numer = resultSet1.getInt(1);
	        }
	    }
	    close();
		return numer;
	}
	
	public static int podajIdPytaniaPoNumerze(int id_ankiety, int numer_pytania) throws SQLException, ClassNotFoundException {
		int numer = 0;
		connect();	
	    if (connection != null) {
	    	ResultSet resultSet1 = statement.executeQuery("SELECT ID FROM pytania WHERE ID_ankieta = "+id_ankiety+" AND Numer_pytania = "+numer_pytania+";");
	        if(resultSet1.next()){
	        	numer = resultSet1.getInt(1);
	        }
	    }
	    close();
		return numer;
	}
	
	public static void zmienNumerPytania(int id_pytania, int numer_pytania) throws SQLException, ClassNotFoundException {
		connect();	
	    if (connection != null) {
	        statement.executeUpdate("UPDATE pytania SET Numer_pytania = "+numer_pytania+" WHERE ID = "+id_pytania+";");
	    }
	    close();
	}
	
	public static String usunPytanie(int id_pytania) throws SQLException, ClassNotFoundException {
		String komunikat = "";
		connect();	
	    if (connection != null) {
	        int wynik = statement.executeUpdate("DELETE FROM pytania WHERE ID = "+id_pytania+";");
	        if(wynik==1){
	        	komunikat = "Twoje pytanie zosta³o usuniête poprawnie.";
	        }
	    }
	    close();
		return komunikat;
	}

	public static String podajPytanie(int id_pytania) throws SQLException, ClassNotFoundException{
		String pytanie = "";
		connect();		
		if (connection != null) {
			ResultSet resultSet1 = statement.executeQuery("Select pytanie from pytania where ID = "+id_pytania+";");
	        while (resultSet1.next()) {
	        	pytanie = resultSet1.getString(1);
	        }
		}	
		close();
		return pytanie;
	}
	

	public static ArrayList<Integer> idPytan(int id_ankiety) throws SQLException, ClassNotFoundException{
		ArrayList<Integer> pytania = new ArrayList<Integer>();
		connect();		
		if (connection != null) {                                    
			ResultSet resultSet1 = statement.executeQuery("Select ID from pytania where ID_ankieta="+id_ankiety+" order by Numer_pytania asc;");
	        while (resultSet1.next()) {
	          	pytania.add(Integer.parseInt(resultSet1.getString(1)));
	        }
		}
		close();
		return pytania;
	}

	
	public static int dodajOdpowiedz(String odpowiedz, int id_pytania, int dlugosc_odpowiedzi) throws ClassNotFoundException, SQLException{
		int id_nowejOdpowiedzi = 0;
		connect();		 
		if (connection != null) {
			statement.executeUpdate("INSERT INTO dostepne_odpowiedzi VALUES(null,"+id_pytania+",'"+odpowiedz+"',"+dlugosc_odpowiedzi+");");
	        ResultSet resultSet = statement.executeQuery("SELECT ID FROM dostepne_odpowiedzi;");
			while (resultSet.next()){
				id_nowejOdpowiedzi = Integer.parseInt(resultSet.getString(1));
			}
	    }
		close();
		return id_nowejOdpowiedzi;
	}
	
	
	public static String aktualizujOdpowiedz(int id_odpowiedzi, String odpowiedz, int dlugosc_odpowiedzi) throws SQLException, ClassNotFoundException {
		String komunikat = "";
		connect();	
	    if (connection != null) {
	        int wynik = statement.executeUpdate("UPDATE dostepne_odpowiedzi SET Odpowiedz = '"+odpowiedz+"', MAX_dlugosc = "+dlugosc_odpowiedzi+" WHERE ID = "+id_odpowiedzi+";");
	        if(wynik==1){
	        	komunikat = "Twoja odpowiedŸ zosta³a zaktualizowania poprawnie.";
	        }
	    }
	    close();
		return komunikat;
	}
	

	public static String podajOdpowiedz(int id_odpowiedzi) throws SQLException, ClassNotFoundException{
		String odpowiedz = "";
		connect();
		if (connection != null) {
			ResultSet resultSet1 = statement.executeQuery("Select Odpowiedz from dostepne_odpowiedzi where ID = "+id_odpowiedzi+";");
            while (resultSet1.next()) {
               	odpowiedz = resultSet1.getString(1);
            }
		}
		close();
		return odpowiedz;
	}
	
	public static int podajDlugoscOdpowiedzi(int id_odpowiedzi) throws SQLException, ClassNotFoundException{
		int dlugosc = 0;
		connect();
		if (connection != null) {
			ResultSet resultSet1 = statement.executeQuery("Select MAX_dlugosc from dostepne_odpowiedzi where ID = "+id_odpowiedzi+";");
            while (resultSet1.next()) {
               	dlugosc = resultSet1.getInt(1);
            }
		}
		close();
		return dlugosc;
	}
	
	
	public static ArrayList<Integer> idOdpowiedzi(int id_pytania) throws SQLException, ClassNotFoundException{
		ArrayList<Integer> odpowiedzi = new ArrayList<Integer>();
		connect();		
		if (connection != null) {                                    
			    ResultSet resultSet1 = statement.executeQuery("Select ID from dostepne_odpowiedzi where ID_pytania="+id_pytania+";");
	            while (resultSet1.next()) {
	            	odpowiedzi.add(Integer.parseInt(resultSet1.getString(1)));
	            }
		}
		close();
		return odpowiedzi;
	}
	
	
	public static int podajRodzajPytania(int id_pytania) throws ClassNotFoundException, SQLException {
		connect();
		int rodzaj_pytania = 0;
		if (connection != null) { 
			ResultSet resultSet1 = statement.executeQuery("Select ID_typu_pytania from pytania where ID="+id_pytania+";");
			while (resultSet1.next()) {
            	rodzaj_pytania = resultSet1.getInt(1);
            }
		}		
		close();
		return rodzaj_pytania;
	}
	
	
	public static int wstawOdpowiedz(int id_ankiety, int id_pytania, int id_uzytkownika, int id_odpowiedzi, String odpowiedz) throws ClassNotFoundException, SQLException{
		connect();		
		int wynik = 0;
        if (connection != null) {
            java.sql.Statement stmt = connection.createStatement();
            wynik = stmt.executeUpdate("INSERT INTO odpowiedzi VALUES(null,"+id_ankiety+","+id_pytania+","+id_uzytkownika+","+id_odpowiedzi+",'"+odpowiedz+"');");
        }
        close();
        return wynik;
	}
	
	public static int usunOdpowiedz(int id_ankiety, int id_uzytkownika) throws ClassNotFoundException, SQLException{
		connect();		
		int wynik = 0;
        if (connection != null) {
            java.sql.Statement stmt = connection.createStatement();
            wynik = stmt.executeUpdate("DELETE FROM odpowiedzi WHERE ID_ankieta="+id_ankiety+" AND ID_uzytkownika="+id_uzytkownika+";");
        }
        close();
        return wynik;
	}	
	
	public static int podajIdUzytkownika(String uzytkownik) throws SQLException, ClassNotFoundException {
		int id_uzytkownika = 0;
		connect();	
	    if (connection != null) {
	        ResultSet resultSet = statement.executeQuery("SELECT ID FROM uzytkownicy WHERE Login = '"+uzytkownik+"';");
	        resultSet.next();
	        String s = resultSet.getString(1);
	        id_uzytkownika = Integer.parseInt(s);
	    }
	    close();
	    return id_uzytkownika;
	}
	

	public static String wyswietlWyniki(int id_ankiety) throws SQLException, ClassNotFoundException{
		String wynik = "";
		connect();
        if (connection != null) {
            wynik = new String();
            ResultSet resultSet = statement.executeQuery("SELECT pytania.Pytanie AS Pytanie, uzytkownicy.Login AS Login, odpowiedzi.Odpowiedz AS Odpowiedz FROM odpowiedzi "+
			"left JOIN ankieta ON ankieta.ID=odpowiedzi.ID_ankieta "+
			"left JOIN pytania ON pytania.ID=odpowiedzi.ID_pytania "+
			"left JOIN uzytkownicy ON uzytkownicy.ID=odpowiedzi.ID_uzytkownika "+
			"right JOIN dostepne_odpowiedzi ON dostepne_odpowiedzi.ID=odpowiedzi.ID_odpowiedzi "+
			"WHERE pytania.ID_ankieta = "+id_ankiety+" AND pytania.ID_typu_pytania = 3 ORDER BY pytania.Numer_pytania ASC;");
            int licznik = 0;
            String temp1 = "";
            String temp2 = "";
            ArrayList<Integer> rowspany = new ArrayList<Integer>();
            while (resultSet.next()) {
                temp1 = resultSet.getString(1);
                if(!temp1.equals(temp2)){
                    temp2 = temp1;
                    rowspany.add(licznik);
                    licznik = 0;
                }
                licznik++;
            }
            rowspany.add(licznik);
            ResultSet resultSet1 = statement.executeQuery("SELECT pytania.Pytanie AS Pytanie, uzytkownicy.Login AS Login, odpowiedzi.Odpowiedz AS Odpowiedz FROM odpowiedzi "+
			"left JOIN ankieta ON ankieta.ID=odpowiedzi.ID_ankieta "+
			"left JOIN pytania ON pytania.ID=odpowiedzi.ID_pytania "+
			"left JOIN uzytkownicy ON uzytkownicy.ID=odpowiedzi.ID_uzytkownika "+
			"right JOIN dostepne_odpowiedzi ON dostepne_odpowiedzi.ID=odpowiedzi.ID_odpowiedzi "+
			"WHERE pytania.ID_ankieta = "+id_ankiety+" AND pytania.ID_typu_pytania = 3 ORDER BY pytania.Numer_pytania ASC;");
            int index = 1;
            while (resultSet1.next()) {
                wynik = wynik + "<tr>";
                temp1 = resultSet1.getString(1);
                if(!temp1.equals(temp2)){
                    temp2 = temp1;
                    wynik = wynik + "<td rowspan="+rowspany.get(index)+" style=\"border: 1px solid white; border-collapse: collapse; padding: 5px;\">" + resultSet1.getString("Pytanie") + "</td>";
                    index++;
                }
                wynik = wynik + "<td style=\"border: 1px solid white; border-collapse: collapse; padding: 5px;\">" + resultSet1.getString("Odpowiedz") + "</td>";
                wynik = wynik + "</tr>";
            }           
        }
        close();
		return wynik;
	}
	
	
	public static ArrayList<Object> procentOdpNaPytanieJednokrotnegoWyboru(int id_pytania) throws SQLException, ClassNotFoundException{
		ArrayList<Object> lista = new ArrayList<Object>();
		connect();
	    if (connection != null) {
	        ResultSet resultSet = statement.executeQuery("SELECT a.Pytanie, b.Odpowiedz, ((SELECT COUNT(ID) FROM odpowiedzi WHERE ID_odpowiedzi = b.ID) / (SELECT COUNT(ID) FROM odpowiedzi WHERE ID_pytania = a.ID)) as procent FROM pytania as a "+ 
	        "JOIN dostepne_odpowiedzi as b ON b.ID_pytania = a.ID "+
	        "WHERE a.ID = "+id_pytania+
	        " Group By b.Odpowiedz "+
	        "Order by procent DESC;");
	        while(resultSet.next()){
	        	lista.add(resultSet.getString("Odpowiedz"));
	        	if(resultSet.getString("procent")!=null){
	        		lista.add(Double.parseDouble(resultSet.getString("procent"))*100);
	        	} else {
	        		lista.add(0.0);
	        	}
	        } 
	    }
	    close();
		return lista;
	}
	
	public static ArrayList<Object> procentOdpNaPytanieWielokrotnegoWyboru(int id_pytania) throws SQLException, ClassNotFoundException{
		ArrayList<Object> lista = new ArrayList<Object>();
		connect();
	    if (connection != null) {
	        ResultSet resultSet = statement.executeQuery("SELECT a.Pytanie, b.Odpowiedz, "+
	        "((SELECT COUNT(ID_uzytkownika) FROM odpowiedzi WHERE ID_odpowiedzi = b.ID) / "+
	        "(SELECT COUNT(DISTINCT ID_uzytkownika) FROM odpowiedzi WHERE ID_pytania = a.ID)) as procent "+
	        "FROM pytania as a "+
	        "JOIN dostepne_odpowiedzi as b ON b.ID_pytania = a.ID "+
	        "WHERE a.ID = "+id_pytania+
	        " Group By b.Odpowiedz "+
	        "Order by procent DESC");
	        while(resultSet.next()){
	        	lista.add(resultSet.getString("Odpowiedz"));
	        	if(resultSet.getString("procent")!=null){
	        		lista.add(Double.parseDouble(resultSet.getString("procent"))*100);
	        	} else {
	        		lista.add(0.0);
	        	}
	        }
	    }		
	    close();
		return lista;
	}

	public static String usunAnkiete(int id_ankiety) throws SQLException, ClassNotFoundException{
		String komunikat = null;
		connect();
		if (connection != null) {
			if(id_ankiety!=0){
	            int id_uzytkownika = 0;
	            int ilosc_ankiet = 0;
	            ResultSet resultSet2 = statement.executeQuery("select ID_uzytkownika from ankieta where ID = "+id_ankiety+";");
			    while (resultSet2.next()) {
			    	id_uzytkownika = Integer.parseInt(resultSet2.getString(1));
			    }
	           	ResultSet resultSet = statement.executeQuery("select Ilosc_ankiet from uzytkownicy where ID = "+id_uzytkownika+";");
			    while (resultSet.next()) {
			    	ilosc_ankiet = Integer.parseInt(resultSet.getString(1));
			        ilosc_ankiet = ilosc_ankiet-1;
			    }
	            statement.executeUpdate("DELETE FROM ankieta WHERE ID="+id_ankiety+";");
	            statement.executeUpdate("UPDATE uzytkownicy SET Ilosc_ankiet = '"+ilosc_ankiet+"' WHERE ID = "+id_uzytkownika+";");
	            komunikat = "Twoja ankieta zosta³a usuniêta poprawnie.";
			} else
				komunikat = "Niestety coœ posz³o nie tak. \\nPrzepraszamy za problem.";
		}
		close();
		return komunikat;
	}
	
	public static boolean czyAnkietaAktywna(int idAnkiety) throws SQLException, ClassNotFoundException{
		String czyAktywna = "";
		connect();
		if (connection != null) {
			if(idAnkiety!=0){	           
	           	ResultSet resultSet = statement.executeQuery("select czy_aktywna from ankieta where ID = "+idAnkiety+";");
			    while (resultSet.next()) {
			    	czyAktywna = resultSet.getString(1);
			    }
			}
		}
		close();
		return czyAktywna.equals("T")?true:false;
	}

	public static void ustawStanAnkiety(String idAnkiety, String akcja) throws SQLException, ClassNotFoundException {
		String czyAktywna = "";
		connect();
		if (connection != null) {
			if(akcja.equals("aktywuj")){
				statement.executeUpdate("update ankieta set czy_aktywna = 'T' where id = "+idAnkiety+";");
			} else {
				statement.executeUpdate("update ankieta set czy_aktywna = 'N' where id = "+idAnkiety+";");
			}
		}
		close();
	}
	
	public static void wyczyscWynikiObiadow() throws SQLException, ClassNotFoundException {
		String czyAktywna = "";
		String idAnkietyRodzaju = pobierzParametrKonfiguracji("mealtypeid");
		
		connect();
		if (connection != null) {
			statement.executeUpdate("delete from odpowiedzi;");
		}
		close();
		
		connect();
		if (connection != null) {
			statement.executeUpdate("update ankieta set czy_aktywna = 'N';");
		}
		
		close();
		
		connect();
		if (connection != null) {
			statement.executeUpdate("update ankieta set czy_aktywna = 'T' where id = "+idAnkietyRodzaju+";");
		}
		close();
		
	}
	
	public static String pobierzParametrKonfiguracji(String nazwaParametru) throws SQLException, ClassNotFoundException {
		String wynik = "";
		connect();
		if (connection != null) {
			ResultSet resultSet = statement.executeQuery("select wartosc from konfiguracja where nazwa_parametru = '"+nazwaParametru+"';");
		    while (resultSet.next()) {
		    	wynik = resultSet.getString(1);
		    }
		}
		close();
		return wynik;
	}
	
	public static HashMap<String, String> pobierzWynikiUzytkownikow(int idAnkiety) throws SQLException, ClassNotFoundException{
		HashMap<String, String> wyniki = new HashMap<>();
		connect();
		if (connection != null) {
			ResultSet resultSet = statement.executeQuery("select uzytkownicy.login, GROUP_CONCAT(odpowiedzi.Odpowiedz SEPARATOR ', ') from uzytkownicy, odpowiedzi where odpowiedzi.ID_ankieta = "+idAnkiety+" and odpowiedzi.ID_uzytkownika = uzytkownicy.ID group by uzytkownicy.login;");
		    while (resultSet.next()) {
		    	wyniki.put(resultSet.getString(1), resultSet.getString(2));
		    }
		}
		close();
		return wyniki;
	}
	
	public static boolean czyGlosowal(int idAnkiety, int idUzytkownika) throws SQLException, ClassNotFoundException{
		boolean wynik = false;
		connect();
		if (connection != null) {
			ResultSet resultSet = Polecenia.statement.executeQuery("SELECT count(1) FROM odpowiedzi WHERE ID_ankieta = "+idAnkiety+" AND id_uzytkownika = "+idUzytkownika+";");			
		    while(resultSet.next()) {
		      	int s = Integer.parseInt(resultSet.getString(1));
		      	wynik = s>0?true:false;
		  	}
		}
		close();
		return wynik;
	}
}
