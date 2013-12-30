/*
 * Author: Dai Yong
 * June 2013
 */
package CP_Classes.vo;

import java.util.Date;

public class voCoachSession {
	int PK=0;
	int SessionMax=0;
	String name="";
	String description="";
	Date closeDate=new Date();
	public voCoachSession() {
		super();
	}
	public voCoachSession(int pK, String name, String description) {
		super();
		PK = pK;
		this.name = name;
		this.description = description;
	}
	public int getPK() {
		return PK;
	}
	public void setPK(int pK) {
		PK = pK;
	}
	public int getSessionMax() {
		return SessionMax;
	}
	public void setSessionMax(int sessionMax) {
		SessionMax = sessionMax;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	
	

}
