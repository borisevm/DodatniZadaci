package EvidencijaSvetskihPrvenstava;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SvetskoPrvenstvo {
	
	protected Date godina;
	protected String nazivPrvenstva;
	protected Drzava drzava;
	
	protected static SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
	
	public SvetskoPrvenstvo(Date godina, String nazivPrvenstva, Drzava drzava) {
		this.godina = godina;
		this.nazivPrvenstva = nazivPrvenstva;
		this.drzava = drzava;
	}
	
	public SvetskoPrvenstvo(String tekst) {
		String[] tokeni = tekst.split(",");
		
		if (tokeni.length != 3) {
			System.out.println("Greska prilikom ucitavanja sv. prevenstava " + tekst);
			System.exit(0);
		}
		
		try {
		godina = formatter.parse(tokeni[0]);
		} catch (ParseException e){
			e.printStackTrace();			
		}
		nazivPrvenstva = tokeni[1];
		drzava = TestPrvenstvo.pronadjiDrzavu(Integer.parseInt(tokeni[2]));
		
		//punjenje liste u klasi Drzava
		drzava.getSvaPrvenstva().add(this);
	}
	
	public String toFileRepresentaton() {
		String godinaTekst = formatter.format(godina);
		StringBuilder sb = new StringBuilder(godinaTekst + "," + nazivPrvenstva + "," + drzava.getIdDrzave());
		return sb.toString();
	}	

	@Override
	public String toString() {
		return "SvetskoPrvenstvo [godina=" + formatter.format(godina) + ", nazivPrvenstva=" + nazivPrvenstva + ", drzava=" + drzava + "]";
	}
	
	public boolean equalsGodinaString(String godina) {
		boolean isti = false;
		if (this.godina.equals(godina)) {
			isti = true;
		}
		return isti;
	}

	public boolean equalsGodinaDate(Date godina) {
		boolean isti = false;
		if (this.godina.compareTo(godina)==0) {
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
		SvetskoPrvenstvo other = (SvetskoPrvenstvo) obj;
		if (godina == null) {
			if (other.godina != null)
				return false;
		} else if (!godina.equals(other.godina))
			return false;
		return true;
	}

	public Date getGodina() {
		return godina;
	}

	public void setGodina(Date godina) {
		this.godina = godina;
	}

	public String getNazivPrvenstva() {
		return nazivPrvenstva;
	}

	public void setNazivPrvenstva(String nazivPrvenstva) {
		this.nazivPrvenstva = nazivPrvenstva;
	}

	public Drzava getDrzava() {
		return drzava;
	}

	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}	
}
