package CP_Classes.vo;

public class voInterviewGuide {
	
	private int	iPKInterviewGuide = 0;
	private	String	sPosition	 = "";
	private	String	sComingFrom	 = "";
	private	String	sEntryDate	 = "";
	private int	iFKComingFrom = 0;
	
	public String getComingFrom() {
		return sComingFrom;
	}
	public void setComingFrom(String comingFrom) {
		sComingFrom = comingFrom;
	}
	public String getEntryDate() {
		return sEntryDate;
	}
	public void setEntryDate(String entryDate) {
		sEntryDate = entryDate;
	}
	public int getFKComingFrom() {
		return iFKComingFrom;
	}
	public void setFKComingFrom(int comingFrom) {
		iFKComingFrom = comingFrom;
	}
	public int getPKInterviewGuide() {
		return iPKInterviewGuide;
	}
	public void setPKInterviewGuide(int interviewGuide) {
		iPKInterviewGuide = interviewGuide;
	}
	public String getPosition() {
		return sPosition;
	}
	public void setPosition(String position) {
		sPosition = position;
	}
	
		
}
