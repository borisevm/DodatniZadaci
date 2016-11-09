package EvidencijaSvetskihPrvenstava;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class Drzava {
	
	private static int brojacId = 0;
	private static final int IDX_ID = 0;
	private static final int IDX_NAZIV = 1;
	
	protected int idDrzave;
	protected String nazivDrzave;
	
	protected ArrayList<SvetskoPrvenstvo> svaSvPrvenstva = new ArrayList<SvetskoPrvenstvo>();

	public Drzava(int idDrzave, String nazivDrzave) {
		if (idDrzave == 0) {
			brojacId++;
			idDrzave = brojacId;
		}
		this.idDrzave = idDrzave;
		this.nazivDrzave = nazivDrzave;
	}

	public Drzava(int idDrzave, String nazivDrzave, ArrayList<SvetskoPrvenstvo> svaSvPrvenstva) {
		if (idDrzave == 0) {
			brojacId++;
			idDrzave = brojacId;
		}
		this.idDrzave = idDrzave;
		this.nazivDrzave = nazivDrzave;
		this.svaSvPrvenstva = svaSvPrvenstva;
	}
	
	public Drzava(String tekst) {
		String[] tokeni = tekst.split(",");
		
		if (tokeni.length != 2) {
			System.out.println("Greska prilikom ucitavanja drzave " + tekst);
		}
		idDrzave = Integer.parseInt(tokeni[0]);
		nazivDrzave = tokeni[1];
		
		if (brojacId < idDrzave) {
			brojacId = idDrzave;
		}
	}
	
	public Drzava (Row row) {
		Cell idDrzave = row.getCell(IDX_ID);
		this.idDrzave = (int) idDrzave.getNumericCellValue();
		Cell nazivDrzave = row.getCell(IDX_NAZIV);
		this.nazivDrzave = nazivDrzave.getStringCellValue();
		}
	
	public String toFileRepresentation() {
		StringBuilder sb = new StringBuilder(idDrzave + "," + nazivDrzave);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Drzava [idDrzave=" + idDrzave + ", nazivDrzave=" + nazivDrzave + "]";
	}
	
	
	public boolean equalsId(int id) {
		boolean isti = false;
		if (idDrzave == id) {
			isti = true;
		}
		return isti;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drzava other = (Drzava) obj;
		if (idDrzave != other.idDrzave)
			return false;
		return true;
	}	
	
	public void toExcelFile(Row row) {
		row.getCell(IDX_ID).setCellValue(idDrzave);
		row.getCell(IDX_NAZIV).setCellValue(nazivDrzave);
	}

	public int getIdDrzave() {
		return idDrzave;
	}

	public void setIdDrzave(int idDrzave) {
		this.idDrzave = idDrzave;
	}

	public String getNazivDrzave() {
		return nazivDrzave;
	}

	public void setNazivDrzave(String nazivDrzave) {
		this.nazivDrzave = nazivDrzave;
	}

	public ArrayList<SvetskoPrvenstvo> getSvaSvPrvenstva() {
		return svaSvPrvenstva;
	}

	public void setSvaSvPrvenstva(ArrayList<SvetskoPrvenstvo> svaSvPrvenstva) {
		this.svaSvPrvenstva = svaSvPrvenstva;
	}
}
