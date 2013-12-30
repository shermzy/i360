package CP_Classes;

import java.util.*;
import java.util.Date;
import java.sql.*;

public class GlobalFunc
{
	/* to run on itself
	public GlobalFunc()
	{
		Date dt;
		dt = new Date();
		dt = addDay(dt, 3);	
	}
	
	public static void main (String args[])
	{
		int arr[] = new int[10];
		GlobalFunc test = new GlobalFunc();
		System.out.println(test.ArrayToString(arr));
	
	}	
	*/
	public static void main (String args[])
	{
		GlobalFunc glb = new GlobalFunc();
		Vector test = glb.getTblFieldname("tblEmail");
		System.out.println(test.size());
		
		/* Encrypt test 
		System.out.println("Encrypting 'rianto'");
		String encrypted = Encrypt("rianto");
		System.out.println("Encrypted 'rianto' = '" + encrypted + "'");
		String decrypted = Decrypt(encrypted);
		System.out.println("Decrypted '" + encrypted + "' = '" + decrypted + "'");
		*/
	}
	
	
	/** putArrayListToString is to convert Array to Comma Concatenated String
	 *	E.g: 
	 *
	 *	Parameters - int arr[]
	 */
	public String putArrayListToString(int arr[])
	{
		//Convert ArrayList [a,b,c] into String "a,b,c"
		String sResult="";
		for(int i=0; i<arr.length; i++)
		{
			sResult = sResult + arr[i];
			if(i != (arr.length - 1) )
				sResult = sResult + ",";
		}
		return sResult;
	}
	
	/** putArrayListToString is to convert Array to Comma Concatenated String
	 *	E.g: 
	 *
	 *	Parameters - String arr[]
	 */
	public String putArrayListToString(String arr[])
	{
		//Convert ArrayList [a,b,c] into String "a,b,c"
		String sResult="";
		for(int i=0; i<arr.length; i++)
		{
			sResult = sResult + arr[i];
			if(i != (arr.length - 1) )
				sResult = sResult + ",";
		}
		return sResult;
	}
	
	public Date addDay(Date dt, int noDays)
	{
		/* Format to correct date after noDays addition */
		//int maxDay;
		int curDay = dt.getDate();
		int curMonth = dt.getMonth() ;
		int curYear = dt.getYear();
		
		curDay = curDay + noDays;
		//maxDay = getMaxDay(curMonth + 1, curYear + 1900);
				
		while (curDay > getMaxDay(curMonth + 1, curYear + 1900))
		{
			//current Day > no of max day, format to correct date
			curDay = curDay - getMaxDay(curMonth + 1, curYear + 1900);
			curMonth = curMonth + 1; //increase 1 month
			if (curMonth > 12)
			{	//reset month, increase year
				curMonth = 1;
				curYear = curYear + 1;
			}
		} 

		Date dtNew = new Date(curYear, curMonth, curDay);
		return dtNew;
	}
	
	public int getMaxDay(int curMonth, int curYear)
	{
		/* Determine max no of Days in the month */
		int maxDay;
		
		if(curMonth <= 7)
		{
			if((curMonth % 2) == 0)
			{
				//Even Month within first 7 months
				maxDay = 30;
				if(curMonth == 2)
				{
					//February
					if(curYear % 4 == 0)
						maxDay = 29;
					else
						maxDay = 28;
				}			
			}
			else
			{
				//Odd Month within first 7 months
				maxDay = 31;
			}
		}
		else
		{
			if((curMonth % 2) == 0)
			{
				//Even Month within last 5 months
				maxDay = 31;
			}
			else
			{
				//Odd Month within last 5 months
				maxDay = 30;
			}
		}	
		return maxDay;
	}
	
	public Vector getTblFieldname(String tblName)
	{
		Vector tblFieldname = new Vector();

		if(tblName.equals("tblEmail"))
		{
			tblFieldname.add("EmailID");
			tblFieldname.add("SenderEmail");
			tblFieldname.add("ReceiverEmail");
			tblFieldname.add("Header");
			tblFieldname.add("Content");
		}

		return tblFieldname;
	}
	
    public static String Encrypt(String text)
	{
    	Setting setting = new Setting();
		String password = setting.encryptKey;
        		
        byte[] textBytes;
        byte[] passBytes;
        byte[] finalBytes;
        int tmpt, tmpp, tmpf;
        		
        textBytes = text.getBytes();
        		
        password = MakeSameSize(password, text.length());
        		
        passBytes = password.getBytes();
        		
        finalBytes = text.getBytes(); // throw some stuff in it so we can compile
        		
        for (int i=0;i<=text.length()-1;i++)
		{
        	tmpt = (int)textBytes[i];
            tmpp = (int)passBytes[i];
            			
            tmpf = tmpt + tmpp;
            finalBytes[i] = (byte)tmpf;
      	}
        return new String(finalBytes);
	}
            	
    public static String Decrypt(String text)
	{
    	Setting setting = new Setting();
		String password = setting.encryptKey;
                		
        byte[] textBytes;
        byte[] passBytes;
        byte[] finalBytes;
		int tmpt, tmpp, tmpf;
                		
        textBytes = text.getBytes();
                		
        password = MakeSameSize(password, text.length());
                		
        passBytes = password.getBytes();
                		
        finalBytes = text.getBytes(); // throw some stuff in it so we can compile
                		
        for (int i=0;i<=text.length()-1;i++)
		{
        	tmpt = (int)textBytes[i];
            tmpp = (int)passBytes[i];
                    			
            tmpf = tmpt - tmpp;
            finalBytes[i] = (byte)tmpf;
      	}
        return new String(finalBytes);
 	}
                    	
    private static String MakeSameSize(String str, int size)
	{
        while (str.length() < size)
		{
        	str = str.concat(new String(str));
      	}
		return str;
	}
	
	
	/************************************* SORTING **************************************************************/
	
	/**
	 * This function only applicable for Vector with 2 elements
	 * 1st Element is either competency name, id, etc
	 * 2nd Element is the value that needs to be sorted
	 * @param int type	0=ASC
	 *					1=DESC
	 */
	public Vector sorting(Vector vUnsorted, int type) throws SQLException, Exception 
	{
		Vector vSorted = new Vector();
		double max 	= 0;	//highest score
		double temp = 0;	//temp score
		int curr 	= 0;	//curr highest element
			
		while(!vUnsorted.isEmpty()) {

			max = Double.valueOf(((String [])vUnsorted.elementAt(0))[1]).doubleValue();
			curr = 0;
			
			// do sorting here
			for(int t=1; t<vUnsorted.size(); t++) {
				temp = Double.valueOf(((String [])vUnsorted.elementAt(t))[1]).doubleValue();
				
				if(type == 1) {	//descending
				
					if(temp > max) {				
						max = temp;
						curr = t;
					}
				}else {
					
					if(temp < max) {				
						max = temp;
						curr = t;
					}
				}
			}

			String info [] = {((String [])vUnsorted.elementAt(curr))[0], ((String [])vUnsorted.elementAt(curr))[1]};
			vSorted.add(info);
											
			vUnsorted.removeElementAt(curr);
		}
		
		
		return vSorted;
	}
	/**
	 * This function only applicable for Vector with 3 elements
	 * 1st Element is either competency name, id, etc
	 * 2nd Element is the value that needs to be sorted
	 * 3rd Element is the ID for row if the 1st element is not the identifier.
	 * @param int type	0=ASC
	 *					1=DESC
	 */
	public Vector sortingWithID(Vector vUnsorted, int type) throws SQLException, Exception 
	{
		Vector vSorted = new Vector();
		double max 	= 0;	//highest score
		double temp = 0;	//temp score
		int curr 	= 0;	//curr highest element
			
		while(!vUnsorted.isEmpty()) {

			max = Double.valueOf(((String [])vUnsorted.elementAt(0))[1]).doubleValue();
			curr = 0;
			
			// do sorting here
			for(int t=1; t<vUnsorted.size(); t++) {
				temp = Double.valueOf(((String [])vUnsorted.elementAt(t))[1]).doubleValue();
				
				if(type == 1) {	//descending
				
					if(temp > max) {				
						max = temp;
						curr = t;
					}
				}else {
					
					if(temp < max) {				
						max = temp;
						curr = t;
					}
				}
			}

			String info [] = {((String [])vUnsorted.elementAt(curr))[0], ((String [])vUnsorted.elementAt(curr))[1],((String [])vUnsorted.elementAt(curr))[2]};
			vSorted.add(info);
											
			vUnsorted.removeElementAt(curr);
		}
		
		
		return vSorted;
	}
	
	/**
	 * This function only applicable for Vector with 3 elements
	 * 1st & 2nd Element can be either competency name, id, etc
	 * 3rd Element is the value that needs to be sorted
	 * @param int type	0=ASC
	 *					1=DESC
	 */
	public Vector sortVector(Vector vUnsorted, int type) throws SQLException, Exception 
	{
		Vector vSorted = new Vector();
		double max 	= 0;	//highest score
		double temp = 0;	//temp score
		int curr 	= 0;	//curr highest element
			
		while(!vUnsorted.isEmpty()) {

			max = Double.valueOf(((String [])vUnsorted.elementAt(0))[2]).doubleValue();
			curr = 0;
			
			// do sorting here
			for(int t=1; t<vUnsorted.size(); t++) {
				temp = Double.valueOf(((String [])vUnsorted.elementAt(t))[2]).doubleValue();
				
				if(type == 1) {	//descending
				
					if(temp > max) {				
						max = temp;
						curr = t;
					}
				}else {
					
					if(temp < max) {				
						max = temp;
						curr = t;
					}
				}
			}
			
			String info [] = {((String [])vUnsorted.elementAt(curr))[0], ((String [])vUnsorted.elementAt(curr))[1], ((String [])vUnsorted.elementAt(curr))[2]};
			vSorted.add(info);
											
			vUnsorted.removeElementAt(curr);
		}
		
		return vSorted;
	}
    
    /**
	 * Insertion Sort
	 * This sorting algorithm has a complexity of n square.
	 */
	void insertionSort(int numbers[]) {
	  int curr, smallest;
	
	  for (int i=1; i<numbers.length; i++) {
	  	
	    smallest = numbers[i];
	    curr = i;
	    
	    //look for the smallest number to be shifted to curr position
	    while ( curr>0 && (numbers[curr-1] > smallest)) {
	      numbers[curr] = numbers[curr-1];
	      curr = curr - 1;
	    }
	    
	    //shift smallest to curr position
	    numbers[curr] = smallest;
	    
	  }
	}
	
	
	/**
	 * Bubble Sort
	 * The bubble sort is the oldest and simplest sort in use. Unfortunately, it's also the slowest.
	 */
	void bubbleSort(double numbers[]) {
	  
	  int total = numbers.length;
	  double temp = 0;
	  
	  for(int i=total-1; i>=0; i--)	
  		for(int j=1; j<=i; j++) 	  			
  			if(numbers[j-1] > numbers[j]) {	//compare the two neighbours
  				
  				temp = numbers[j-1];
  				
  				//do swapping here
  				numbers[j-1] = numbers[j];
  				numbers[j] = temp;
  			}

	}
}