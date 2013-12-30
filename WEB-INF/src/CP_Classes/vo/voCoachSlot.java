/*
 * Author: Dai Yong
 * June 2013
 */
package CP_Classes.vo;

public class voCoachSlot {
	int PK;
	int FKCoachSlotGroup;
	int startingtime;
	int endingtime;
	public voCoachSlot() {
		super();
	}
	public voCoachSlot(int pK, int startingtime, int endingtime) {
		super();
		PK = pK;
		this.startingtime = startingtime;
		this.endingtime = endingtime;
	}
	public int getPK() {
		return PK;
	}
	public void setPK(int pK) {
		PK = pK;
	}
	public int getStartingtime() {
		return startingtime;
	}
	public void setStartingtime(int startingtime) {
		this.startingtime = startingtime;
	}
	public int getEndingtime() {
		return endingtime;
	}
	public void setEndingtime(int endingtime) {
		this.endingtime = endingtime;
	}
	public int getFKCoachSlotGroup() {
		return FKCoachSlotGroup;
	}
	public void setFKCoachSlotGroup(int fKCoachSlotGroup) {
		FKCoachSlotGroup = fKCoachSlotGroup;
	}
	
}
