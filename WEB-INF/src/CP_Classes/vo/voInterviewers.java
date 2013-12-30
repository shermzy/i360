package CP_Classes.vo;

public class voInterviewers {
	
	private int	iPKInterviewers	= 0;
	private	String sInterviewersName	 = "";
	private int	iIsExpert	= 0;
	private int	iNTime	= 0;
	private int	iETime	= 0;
	private	String	sInterviewDate	 = "";
	private int	iFKInterviewGuide	= 0;
	
	public int getETime() {
		return iETime;
	}
	public void setETime(int time) {
		iETime = time;
	}
	public int getFKInterviewGuide() {
		return iFKInterviewGuide;
	}
	public void setFKInterviewGuide(int interviewGuide) {
		iFKInterviewGuide = interviewGuide;
	}
	public String getInterviewDate() {
		return sInterviewDate;
	}
	public void setInterviewDate(String interviewDate) {
		sInterviewDate = interviewDate;
	}
	public String getInterviewersName() {
		return sInterviewersName;
	}
	public void setInterviewersName(String interviewersName) {
		sInterviewersName = interviewersName;
	}
	public int getIsExpert() {
		return iIsExpert;
	}
	public void setIsExpert(int isExpert) {
		iIsExpert = isExpert;
	}
	public int getNTime() {
		return iNTime;
	}
	public void setNTime(int time) {
		iNTime = time;
	}
	public int getPKInterviewers() {
		return iPKInterviewers;
	}
	public void setPKInterviewers(int interviewers) {
		iPKInterviewers = interviewers;
	}
	
	
}
