package PomocneKlase;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import EvidencijaSvetskihPrvenstava.SvetskoPrvenstvo;

public class PrvenstvoGodinaComparator implements Comparator<SvetskoPrvenstvo>{
	
	int direction = 1;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

	public PrvenstvoGodinaComparator(int direction) {
		if(direction!=1 && direction!=-1){
			direction = 1;
		}
		this.direction = direction;
	}

	public int compare(SvetskoPrvenstvo ob1, SvetskoPrvenstvo ob2) {
		int retVal = 0;
		if(ob1!= null && ob2!=null){
			retVal = formatter.format(ob1.getGodina()).compareTo(formatter.format(ob2.getGodina()));
		}
		return retVal * direction;
	}

}
