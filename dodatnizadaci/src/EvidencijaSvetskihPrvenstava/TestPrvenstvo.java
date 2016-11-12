package EvidencijaSvetskihPrvenstava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import PomocneKlase.DrzavaNameComparator;
import PomocneKlase.PrvenstvoComparator;
import PomocneKlase.PrvenstvoGodinaComparator;
import PomocneKlase.PrvenstvoNameComparator;
import rs.ac.uns.ftn.informatika.dosk.java.vezbe05.primer05.utils.PomocnaKlasa;

public class TestPrvenstvo {
	
	public static HashMap<Integer, Drzava> sveDrzave = new HashMap<Integer, Drzava>();
	public static HashMap<String, SvetskoPrvenstvo> svaPrvenstva = new HashMap<String, SvetskoPrvenstvo>();
	public static Workbook wb = null;
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
		for (SvetskoPrvenstvo svPrv : sortirajSvetskaPrvenstva2()) {
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
		drzava.getSvaSvPrvenstva().add(svPrv);
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
	
	public static void statistika() {
		System.out.println("Unesite pocetnu godinu: ");
		String godinaOdTekst = PomocnaKlasa.ocitajTekst();		
		System.out.println("Unesite zavrsnu godinu: ");
		String godinaDoTekst = PomocnaKlasa.ocitajTekst();
		Date datumOd = null;
		Date datumDo = null;
		try {
			datumOd = formatter.parse(godinaOdTekst);
			datumDo = formatter.parse(godinaDoTekst);
		} catch (ParseException e){
			e.printStackTrace();
		}
		int rbr = 0;
		int brojac = 0;
		boolean odgovara = false;
		for (Drzava drzava : sveDrzave.values()) {
			if (!drzava.getSvaSvPrvenstva().isEmpty()) {
				for (int i = 0; i < drzava.getSvaSvPrvenstva().size(); i++) {
					if ((drzava.getSvaSvPrvenstva().get(i).getGodina().compareTo(datumOd)!=-1) && 
							(drzava.getSvaSvPrvenstva().get(i).getGodina().compareTo(datumDo)!=1)) {
						brojac++;
						odgovara = true;
					}
				}
				if (odgovara) {
					rbr++;
					System.out.print(rbr+". " + drzava.getNazivDrzave());
					if (brojac > 1) {
						System.out.println(" " + brojac);
					} else
						System.out.println();
					brojac = 0;
				}
			}
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
		System.out.println("Opcija 9 - statistika");
		System.out.println("-----");
		System.out.println("Opcija 0 - izlaz iz programa");		
	}

	public static void ucitavanjePodatakaDrzavaExcel() {

		try {
			String sP = System.getProperty("file.separator");
			FileInputStream in = new FileInputStream("."+sP+"data"+sP+"drzave.xlsx");
			wb = WorkbookFactory.create(in);
			Sheet sheet = wb.getSheetAt(0);
			// prolaz
			for (Row row : sheet) {
				// izbegnemo prvu vrstu (zaglavlje)
				if (row.getRowNum() == 0)
					continue;
				Drzava drzava = new Drzava(row);
				sveDrzave.put(drzava.getIdDrzave(), drzava);
				
			}
			in.close();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void snimanjePodatakaDrzavaExcel() {		
		Sheet sheet = wb.getSheetAt(0); // wb.getSheet("Sheet1")
		Row row;		
		Set <Integer> keyid = sveDrzave.keySet();
		int rowid = 1;			
		sheet.createRow(0);
		sheet.getRow(0).createCell(0);
		sheet.getRow(0).createCell(1);
		Drzava.toExcelFileHeader(sheet.getRow(0));
		for (Integer key : keyid) {
			Drzava  drzava = sveDrzave.get(key);			
			row = sheet.createRow(rowid++);			
			int cellid = 0;			
			Cell cell = row.createCell(cellid++);
			cell.setCellValue((Integer)drzava.getIdDrzave());
			cell = row.createCell(cellid++);
			cell.setCellValue((String)drzava.getNazivDrzave());	
		}
		
		FileOutputStream fileOut;
		try {
			String sP = System.getProperty("file.separator");
			fileOut = new FileOutputStream("."+sP+"data"+sP+"drzave.xlsx");
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("drzave.xlsx je uspesno zapisan");
	}
		
		
	public static void ucitavanjePodatakaPrvenstvaExcel() {

		try {
			String sP = System.getProperty("file.separator");
			FileInputStream in = new FileInputStream("."+sP+"data"+sP+"svetska_prvenstva.xlsx");
			wb = WorkbookFactory.create(in);
			Sheet sheet = wb.getSheetAt(0); // wb.getSheet("Sheet1")
			// prolaz
			for (Row row : sheet) {				
				if (row.getRowNum() == 0)
					continue;
				SvetskoPrvenstvo svPrv = new SvetskoPrvenstvo(row);
				String godinaTekst = formatter.format(svPrv.getGodina());
				svaPrvenstva.put(godinaTekst, svPrv);				
			}
			in.close();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void snimanjePodatakaPrvenstvaExcel() {		
		Sheet sheet = wb.getSheetAt(0); // wb.getSheet("Sheet1")
		Row row;		
		Set <String> keyid = svaPrvenstva.keySet();
		int rowid = 1;		
		sheet.createRow(0);
		sheet.getRow(0).createCell(0);
		sheet.getRow(0).createCell(1);
		sheet.getRow(0).createCell(2);
		SvetskoPrvenstvo.toExcelFileHeader(sheet.getRow(0)); //Ispisivanje zaglavlja
		for (String key : keyid) {
			SvetskoPrvenstvo  svPrv = svaPrvenstva.get(key);			
			row = sheet.createRow(rowid++);			
			int cellid = 0;
			Cell cell = row.createCell(cellid++);
			String godinaTekst = formatter.format(svPrv.getGodina());
			cell.setCellValue((String)godinaTekst);
			cell = row.createCell(cellid++);
			cell.setCellValue((String)svPrv.getNazivPrvenstva());	
			cell = row.createCell(cellid++);
			cell.setCellValue((Integer)svPrv.getDrzava().getIdDrzave());			
		}			
		
		FileOutputStream fileOut;
		try {
			String sP = System.getProperty("file.separator");
			fileOut = new FileOutputStream("."+sP+"data"+sP+"svetska_prvenstva.xlsx");
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("svetska_prvenstva.xlsx je uspesno zapisan");
	}	
	
	
	public static void meniAplikacija() {
		int odluka = -1;
		while (odluka != 0) {
			ispisiMeni();
			System.out.println("Izaberite opciju rada aplikacije: ");
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
				statistika();
				break;
			default:
				System.out.println("Nepostojeca komanda");
				break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Izaberite vrstu I/O fajlova - C za csv - E za xlsx - X za izlaz");
		
		char izbor = ' ';
		while (izbor != 'X') {
			izbor = PomocnaKlasa.ocitajKarakter();
			switch (izbor) {
			case 'X':
				System.out.println("kraj programa");
				break;
			case 'C':
				String sP = System.getProperty("file.separator");		
				File drzaveFajl = new File("."+sP+"data"+sP+"drzave.csv");
				citajIzFajlaDrzave(drzaveFajl);
				File svetskaPrvenstvaFajl = new File("."+sP+"data"+sP+"svetska_prvenstva.csv");
				citajIzFajlaSvetskaPrvenstva(svetskaPrvenstvaFajl);	
				meniAplikacija();
				pisiUFajlDrzave(drzaveFajl);
				pisiUFajlsvaPrvenstva(svetskaPrvenstvaFajl);				
				break;
			case 'E':
				ucitavanjePodatakaDrzavaExcel();
				ucitavanjePodatakaPrvenstvaExcel();
				meniAplikacija();
				snimanjePodatakaDrzavaExcel();
				snimanjePodatakaPrvenstvaExcel();
				break;
			default:
				System.out.println("Nepostojeca komanda");
				break;		
			}
		}
		System.out.print("Program izvrsen");

	}
	
	public static ArrayList<SvetskoPrvenstvo> sortirajSvetskaPrvenstva2() {
		ArrayList<SvetskoPrvenstvo> sortiranaPrvenstva = new ArrayList<>(svaPrvenstva.values());
		System.out.println("Unesite kriteriju sortiranja: \n\t1 za sortiranje po nazivu  \n\t2 za sortiranje po godini");
		int redosled = PomocnaKlasa.ocitajCeoBroj();
		switch (redosled) {
		
		case 1:
			Collections.sort(sortiranaPrvenstva, new PrvenstvoComparator(1, "naziv"));
			break;
		case 2:
			Collections.sort(sortiranaPrvenstva, new PrvenstvoComparator(1, "godina"));			
			break;
		default: 
			System.out.println("Opcije su 1 ili 2");
			break;
		}
		return sortiranaPrvenstva;		
	}
}

