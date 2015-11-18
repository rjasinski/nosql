import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BookToCSV {

    public static void main(String[] args) {
	FileReader fr = null;
	String linia = "";
	String[] slowa = null;
	StringBuilder sb = null;
	long nrLini = 1;
	long id = 1;
	ArrayList<String> stopword = new ArrayList<String>();
	char[] litery = null;
	FileWriter fw = null;

	//Wczytanie stopword
	try {
	    fr = new FileReader("stopword.txt");
	} catch (FileNotFoundException e) {
	    System.err.println("BŁĄD PRZY OTWIERANIU PLIKU!");
	    System.exit(1);
	}
	BufferedReader bfrs = new BufferedReader(fr);
	// ODCZYT KOLEJNYCH LINII Z PLIKU:
	try {
	    while((linia = bfrs.readLine()) != null){
		stopword.add(linia);
	    }
	} catch (IOException e) {
	    System.err.println("BŁĄD ODCZYTU Z PLIKU!");
	    System.exit(2);
	}
	// ZAMYKANIE PLIKU
	try {
	    fr.close();
	} catch (IOException e) {
	    System.err.println("BŁĄD PRZY ZAMYKANIU PLIKU!");
	    System.exit(3);
        }

	// OTWIERANIE PLIKU:
	try {
	    fr = new FileReader(args[0]);
	} catch (FileNotFoundException e) {
	    System.err.println("BŁĄD PRZY OTWIERANIU PLIKU!");
	    System.exit(1);
	}
	try {
	    fw = new FileWriter(args[1]);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	BufferedReader bfr = new BufferedReader(fr);
	BufferedWriter bw = new BufferedWriter(fw);
	//stworzenie nagłówka
	try {
	    bw.write("ID,word,line");
	    bw.newLine();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	// ODCZYT KOLEJNYCH LINII Z PLIKU:
	try {
	    while((linia = bfr.readLine()) != null){
		//Przetworzenie lini
		slowa = linia.split(" ");
		for (int i = 0; i < slowa.length; i++)
		    {
			if (isStopword(slowa[i], stopword)) {
			    continue;
			}
			sb = new StringBuilder();
			sb.append(id);
			sb.append(',');
			sb.append(slowa[i]);
			sb.append(',');
			sb.append(nrLini);
			//zapis do pliku csv			
			try {
			    bw.write(sb.toString());
			    bw.newLine();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			//
			id++;
		    }
		nrLini++;
	    }
	} catch (IOException e) {
	    System.err.println("BŁĄD ODCZYTU Z PLIKU!");
	    System.exit(2);
	}

	// ZAMYKANIE PLIKU
	try {
	    fr.close();
	} catch (IOException e) {
	    System.out.println("BŁĄD PRZY ZAMYKANIU PLIKU!");
	    System.exit(3);
        }
	try {
	    bw.close();
	    fw.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static boolean isStopword(String slowo, ArrayList<String> stopword) {
	for (int i = 0; i < stopword.size(); i++)
	    {
		if (slowo.equals(stopword.get(i))) {
		    return true;
		}
	    }
	return false;
    }
}