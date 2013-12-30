/*
 * Author: Dai Yong
 * June 2013
 */
package CP_Classes.vo;

public class voCoachVenue {

	int VenuePK;
	String Venue1;
	String Venue2;
	String Venue3;
	
	public voCoachVenue() {
		super();
	}

	public voCoachVenue(int venuePK, String venue1, String venue2, String venue3) {
		super();
		VenuePK = venuePK;
		Venue1 = venue1;
		Venue2 = venue2;
		Venue3 = venue3;
	}

	public int getVenuePK() {
		return VenuePK;
	}

	public void setVenuePK(int venuePK) {
		VenuePK = venuePK;
	}

	public String getVenue1() {
		return Venue1;
	}

	public void setVenue1(String venue1) {
		Venue1 = venue1;
	}

	public String getVenue2() {
		return Venue2;
	}

	public void setVenue2(String venue2) {
		Venue2 = venue2;
	}

	public String getVenue3() {
		return Venue3;
	}

	public void setVenue3(String venue3) {
		Venue3 = venue3;
	}
	
}
