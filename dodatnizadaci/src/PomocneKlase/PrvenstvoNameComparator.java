package PomocneKlase;

import java.util.Comparator;

import EvidencijaSvetskihPrvenstava.SvetskoPrvenstvo;

public class PrvenstvoNameComparator implements Comparator<SvetskoPrvenstvo>{
	
int direction = 1;
	
	public PrvenstvoNameComparator(int direction) {
		if(direction!=1 && direction!=-1){
			direction = 1;
		}
		this.direction = direction;
	}

	@Override
	public int compare(SvetskoPrvenstvo ob1, SvetskoPrvenstvo ob2) {
		int retVal = 0;
		if(ob1!= null && ob2!=null){
			retVal = ob1.getNazivPrvenstva().compareTo(ob2.getNazivPrvenstva());
		}
		return retVal * direction;
	}
}
