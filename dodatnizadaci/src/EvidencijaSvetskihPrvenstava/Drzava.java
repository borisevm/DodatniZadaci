package EvidencijaSvetskihPrvenstava;

import java.util.ArrayList;

public class Drzava {
	
	private static int brojacId = 0;
	
	protected int idDrzave;
	protected String nazivDrzave;
	
	protected ArrayList<SvetskoPrvenstvo> svaPrvenstva = new ArrayList<SvetskoPrvenstvo>();

	public Drzava(int idDrzave, String nazivDrzave) {
		if (idDrzave == 0) {
			brojacId++;
			idDrzave = brojacId;
		}
		this.idDrzave = idDrzave;
		this.nazivDrzave = nazivDrzave;
	}

	public Drzava(int idDrzave, String nazivDrzave, ArrayList<SvetskoPrvenstvo> svaPrvenstva) {
		if (idDrzave == 0) {
			brojacId++;
			idDrzave = brojacId;
		}
		this.idDrzave = idDrzave;
		this.nazivDrzave = nazivDrzave;
		this.svaPrvenstva = svaPrvenstva;
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

	public ArrayList<SvetskoPrvenstvo> getSvaPrvenstva() {
		return svaPrvenstva;
	}

	public void setSvaPrvenstva(ArrayList<SvetskoPrvenstvo> svaPrvenstva) {
		this.svaPrvenstva = svaPrvenstva;
	}
}
