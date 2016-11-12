package PomocneKlase;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import EvidencijaSvetskihPrvenstava.SvetskoPrvenstvo;

public class PrvenstvoComparator implements Comparator<SvetskoPrvenstvo> {
	
	int direction = 1;
	String orderByAttributeName;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

	public PrvenstvoComparator(int direction, String orderByAttributeName) {
		if(direction!=1 && direction!=-1){
			direction = 1;
		}
		this.direction = direction;
		this.orderByAttributeName = orderByAttributeName;
	}
	
	@Override
	public int compare(SvetskoPrvenstvo ob1, SvetskoPrvenstvo ob2) {
		int retVal = 0;
		if(orderByAttributeName.equals("naziv"))
			retVal = ob1.getNazivPrvenstva().compareTo(ob2.getNazivPrvenstva()) ;
		else if (orderByAttributeName.equals("godina")){
			//retVal =ob1.getGodina()-ob2.getGodina();
			retVal = formatter.format(ob1.getGodina()).compareTo(formatter.format(ob2.getGodina()));
		}
		return retVal * direction;
	}

}
