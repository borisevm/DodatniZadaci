package EvidencijaSvetskihPrvenstava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import PomocneKlase.DrzavaNameComparator;
import PomocneKlase.PrvenstvoGodinaComparator;
import PomocneKlase.PrvenstvoNameComparator;
import rs.ac.uns.ftn.informatika.dosk.java.vezbe05.primer05.utils.PomocnaKlasa;

public class TestPrvenstvo {
	
	public static HashMap<Integer, Drzava> sveDrzave = new HashMap<Integer, Drzava>();
	public static HashMap<String, SvetskoPrvenstvo> svaPrvenstva = new HashMap<String, SvetskoPrvenstvo>();
	
	protected static SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
	
	public static Drzava pronadjiDrzavu(Integer id) {
		return sveDrzave.get(id);
	}
	
	public static Drzava pronadjiDrzavuPoNazivu(String naziv) {
		Drzava drzava = null;
		for (Drzava drzavaVal : sveDrzave.values()) {
			if (drzavaVal.getNazivDrzave().equalsIgnoreCase(naziv)) {
				drzava = drzavaVal;
			}
		}
		return drzava;
	}
	
	public static SvetskoPrvenstvo pronadjiSvetskoPrvenstvo(String godina) {
		return svaPrvenstva.get(godina);
	}
			
	public static void prikaziSveDrzave() {
		for (Drzava drzava : sveDrzave.values()) {
			System.out.println(drzava.toString());
		}
	}
	
	public static void prikaziSveDrzave2() {
		System.out.println("Drzave domacini:\n");
		for (Integer drzavaId : sveDrzave.keySet()) {
			System.out.println(sveDrzave.get(drzavaId));
		}
		System.out.println("**************************");
	}
	
	public static void ispisiSortiraneDrzave() {
		for (Drzava drzava : sortirajDrzave()) {
			System.out.println(drzava);
		}
	}
	
	public static void prikaziSvaSvPrevenstva() {
		for (SvetskoPrvenstvo prvenstvo : svaPrvenstva.values()) {
			System.out.println("Svetsko prvenstvo pod nazivom: \"" + prvenstvo.getNazivPrvenstva() 
			+ "\" odrzano je " + formatter.format(prvenstvo.getGodina()) 
			+ ". godine - drzava domacin: " + prvenstvo.getDrzava().getNazivDrzave());
		}
	}
	
	public static void ispisiSortiranaPrvenstva() {
		for (SvetskoPrvenstvo svPrv : sortirajSvetskaPrvenstva()) {
			System.out.println(svPrv);
		}
	}
	
	//unos
	public static void unesiDrzavu() {		
		while (PomocnaKlasa.ocitajOdlukuOPotvrdi("da unesete novu drzavu?")=='Y') { 
			System.out.println("Unesite naziv drzave: ");
			String naziv = PomocnaKlasa.ocitajTekst();
			while (pronadjiDrzavuPoNazivu(naziv) != null) {
				System.out.println("Drzava " + naziv + " postoji, unesite drugi naziv");
				naziv = PomocnaKlasa.ocitajTekst();
			}
			Drzava novaDrz = new Drzava(0, naziv);
			sveDrzave.put(novaDrz.getIdDrzave(), novaDrz);
		}
	}
	
	//varijanta sa unosom naziva drzave
	public static void unesiSvetskoPrvenstvo() {
		Date godina = null;
		System.out.println("Unesite godinu: ");
		String godinaTekst = PomocnaKlasa.ocitajTekst();
		String nazivPrvenstva = null;
		String nazivDrzave = null;
		Drzava drzava;
		while (pronadjiSvetskoPrvenstvo(godinaTekst) != null) {
			System.out.println("Godina " + godinaTekst + " postoji, unesite novu");
			godinaTekst = PomocnaKlasa.ocitajTekst();
		}
		System.out.println("Unesite naziv svetskog prvenstva: ");
		nazivPrvenstva = PomocnaKlasa.ocitajTekst();
		System.out.println("Unesite naziv drzave: ");
		nazivDrzave = PomocnaKlasa.ocitajTekst();
		while ((drzava = pronadjiDrzavuPoNazivu(nazivDrzave)) == null) {
			System.out.println("Drzava " + nazivDrzave + " ne postoji u bazi. Unesite drugi naziv drzave ili zeljenu unesite unesite u bazu");
			if ((PomocnaKlasa.ocitajOdlukuOPotvrdi("da unesete novu drzavu u bazu?")=='Y')) {				
				unesiDrzavu();
				continue;
			}
			System.out.println("Ponovite unos drzave: ");
			nazivDrzave = PomocnaKlasa.ocitajTekst();			
		}
		try {
			godina = formatter.parse(godinaTekst);
			} catch (ParseException e){
				e.printStackTrace();			
			}

		SvetskoPrvenstvo svPrv = new SvetskoPrvenstvo(godina, nazivPrvenstva, drzava);
		svaPrvenstva.put(godinaTekst, svPrv);		
	}
	
	//izmena
	public static void izmeniDrzavu() {
		Drzava drzava = null;
		String naziv = null;
		System.out.println("Lista drzava: ");
		prikaziSveDrzave();
		System.out.println();
		System.out.println("Unesi id drzave: ");
		int id = PomocnaKlasa.ocitajCeoBroj();
		while ((drzava = pronadjiDrzavu(id)) == null) {
			System.out.println("Drzava ne postoji u bazi, ponovite unos: ");
			id = PomocnaKlasa.ocitajCeoBroj();
		}
		System.out.println("Izmenite naziv: ");
		naziv = PomocnaKlasa.ocitajTekst();		
		drzava.setNazivDrzave(naziv);
	}
	
	public static void izmeniSvetskoPrvenstvo() {
		SvetskoPrvenstvo svPrv = null;
		//Date godina = null;
		String nazivPrvenstva = null;
		int idDrzave = 0;
		Drzava drzava = null;
		System.out.println("Unesite godinu: ");
		String godinaTekst = PomocnaKlasa.ocitajTekst();		
		while ((svPrv = pronadjiSvetskoPrvenstvo(godinaTekst)) == null) {
			System.out.println("Godina " + godinaTekst + " ne postoji, unesite novu");
			godinaTekst = PomocnaKlasa.ocitajTekst();
		}
		if ((PomocnaKlasa.ocitajOdlukuOPotvrdi("da izmenite naziv svetskog prvenstva?")=='Y')) {
			System.out.println("Unesite novi naziv svetskog prvenstva: ");
			nazivPrvenstva = PomocnaKlasa.ocitajTekst();
		} else {
			nazivPrvenstva = svPrv.getNazivPrvenstva();
		}
		if ((PomocnaKlasa.ocitajOdlukuOPotvrdi("da izmenite drzavu domacina?")=='Y')) {			
			System.out.println("Unesite id drzave: ");
			idDrzave = PomocnaKlasa.ocitajCeoBroj();
			while ((drzava = pronadjiDrzavu(idDrzave)) == null) {
				System.out.println("Drzava ne postoji u bazi.");
				if ((PomocnaKlasa.ocitajOdlukuOPotvrdi("da da pogledate listu drzava?")=='Y')) {				
					prikaziSveDrzave();
					continue;
				}
				System.out.println("Ponovite unos drzave: ");
				idDrzave = PomocnaKlasa.ocitajCeoBroj();
			}
		} else {
			drzava = svPrv.getDrzava();
		}			
		svPrv.setNazivPrvenstva(nazivPrvenstva);
		svPrv.setDrzava(drzava);
	}
	
	//sortiranje
	public static ArrayList<Drzava> sortirajDrzave() {
		ArrayList<Drzava> sortiraneDrzave = new ArrayList<>(sveDrzave.values());
		System.out.println("Unesite redosled sortiranja: \n\t1 za rastuæi  \n\t2 za opadajuæi");
		int redosled = PomocnaKlasa.ocitajCeoBroj();
		switch (redosled) {
		
		case 1:
			Collections.sort(sortiraneDrzave, new DrzavaNameComparator(1));
			break;
		case 2:
			Collections.sort(sortiraneDrzave, new DrzavaNameComparator(-1));			
			break;
		default: 
			System.out.println("Opcije su 1 ili 2");
			break;
		}
		return sortiraneDrzave;		
	}
	
	public static ArrayList<SvetskoPrvenstvo> sortirajSvetskaPrvenstva() {
		ArrayList<SvetskoPrvenstvo> sortiranaPrvenstva = new ArrayList<>(svaPrvenstva.values());
		System.out.println("Unesite kriteriju sortiranja: \n\t1 za sortiranje po nazivu  \n\t2 za sortiranje po godini");
		int redosled = PomocnaKlasa.ocitajCeoBroj();
		switch (redosled) {
		
		case 1:
			Collections.sort(sortiranaPrvenstva, new PrvenstvoNameComparator(1));
			break;
		case 2:
			Collections.sort(sortiranaPrvenstva, new PrvenstvoGodinaComparator(1));			
			break;
		default: 
			System.out.println("Opcije su 1 ili 2");
			break;
		}
		return sortiranaPrvenstva;		
	}
	
	// citanje i pisanje u fajl
	public static void citajIzFajlaDrzave(File dokument) throws IOException {
		if (dokument.exists()) {		
			BufferedReader ulaz = new BufferedReader(new FileReader(dokument));

			ulaz.mark(1);
			if (ulaz.read()!='\ufeff') {
				ulaz.reset();			
			}
			String red;
			while ((red = ulaz.readLine()) !=null) {
				Drzava drzava = new Drzava(red);
				sveDrzave.put(drzava.getIdDrzave(), drzava);			
			}
			ulaz.close();
		} else {
			System.out.println("Fajl nije pronadjen!");
		}
	}
	
	public static void pisiUFajlDrzave (File dokument) throws IOException {
		if (dokument.exists()) {
			PrintWriter izlaz = new PrintWriter(new FileWriter(dokument));
			for (Drzava drzava : sveDrzave.values()) {
				izlaz.println(drzava.toFileRepresentation());
			}
			izlaz.flush();
			izlaz.close();
		} else {
			System.out.println("Greska, fajl nije pronadjen!");
		}			
	}
	
	public static void citajIzFajlaSvetskaPrvenstva(File dokument) throws IOException {
		if (dokument.exists()) {
			BufferedReader ulaz = new BufferedReader(new FileReader(dokument));	
			
			ulaz.mark(1);
			if (ulaz.read()!='\ufeff') {
				ulaz.reset();
			}			
			String red;
			while ((red = ulaz.readLine()) != null) {
				SvetskoPrvenstvo svPrvenstvo = new SvetskoPrvenstvo(red);
				String godinaTekst = formatter.format(svPrvenstvo.getGodina());
				svaPrvenstva.put(godinaTekst, svPrvenstvo);
			}
			ulaz.close();
		} else {
			System.out.println("Fajl nije pronadjen!");
		}			
	}
	
	public static void pisiUFajlsvaPrvenstva(File dokument) throws IOException {
		if (dokument.exists()) {
			PrintWriter izlaz = new PrintWriter(new FileWriter(dokument));
			for (SvetskoPrvenstvo svPrvenstvo : svaPrvenstva.values()) {
				izlaz.println(svPrvenstvo.toFileRepresentaton());
			}
			izlaz.flush();
			izlaz.close();
		} else {
			System.out.println("Greska, fajl nije pronadjen!");
		}
	}
	
	public static void ispisiMeni() {
		System.out.println("*****************************************************");
		System.out.println("Opcija 1 - prikaz svih drzava");
		System.out.println("Opcija 2 - prikaz svih svetskih prvenstava");
		System.out.println("Opcija 3 - unos nove drzave");
		System.out.println("Opcija 4 - unos novog svetskog prvenstva");
		System.out.println("Opcija 5 - izmena drzave");
		System.out.println("Opcija 6 - izmena svetskog prvenstva");
		System.out.println("Opcija 7 - sortiranje i prikaz svih drzava");
		System.out.println("Opcija 8 - sortiranje i prikaz svih svetskih prvenstava");
		System.out.println("-----");
		System.out.println("Opcija 0 - izlaz iz programa");		
	}

	public static void main(String[] args) throws IOException {
		
		String sP = System.getProperty("file.separator");		
		File drzaveFajl = new File("."+sP+"data"+sP+"drzave.csv");
		citajIzFajlaDrzave(drzaveFajl);
		File svetskaPrvenstvaFajl = new File("."+sP+"data"+sP+"svetska_prvenstva.csv");
		citajIzFajlaSvetskaPrvenstva(svetskaPrvenstvaFajl);
		
		//prikaziSveDrzave2();
		
		int odluka = -1;
		while (odluka != 0) {
			ispisiMeni();
			System.out.println("Izaberite opciju: ");
			odluka = PomocnaKlasa.ocitajCeoBroj();
			switch (odluka) {
			
			case 0:
				System.out.println("kraj programa");				
				break;
			case 1:
				prikaziSveDrzave2();
				break;
			case 2:
				prikaziSvaSvPrevenstva();
				break;
			case 3:
				unesiDrzavu();
				break;
			case 4:
				unesiSvetskoPrvenstvo();
				break;
			case 5:
				izmeniDrzavu();
				break;
			case 6:
				izmeniSvetskoPrvenstvo();
				break;
			case 7:				
				ispisiSortiraneDrzave();
				break;
			case 8:
				ispisiSortiranaPrvenstva();
				break;
			case 9:
				
				break;
			default:
				System.out.println("Nepostojeca komanda");
				break;
			}
		}		
		
		
		pisiUFajlDrzave(drzaveFajl);
		pisiUFajlsvaPrvenstva(svetskaPrvenstvaFajl);
		System.out.print("Program izvrsen");

	}
}
