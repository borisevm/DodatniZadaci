package PomocneKlase;

import java.util.Comparator;

import EvidencijaSvetskihPrvenstava.Drzava;

public class DrzavaNameComparator implements Comparator<Drzava>{

	int direction = 1;
	
	public DrzavaNameComparator(int direction) {
		if(direction!=1 && direction!=-1){
			direction = 1;
		}
		this.direction = direction;
	}

	@Override
	public int compare(Drzava ob1, Drzava ob2) {
		int retVal = 0;
		if(ob1!= null && ob2!=null){
			retVal = ob1.getNazivDrzave().compareTo(ob2.getNazivDrzave());
		}
		return retVal * direction;
	}
}