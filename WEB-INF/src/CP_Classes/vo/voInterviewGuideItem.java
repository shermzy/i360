package CP_Classes.vo;

public class voInterviewGuideItem {
	
	private int	iPKInterviewGuideItem	= 0;
	private int	iFKCompetency	= 0;
	private int	iFKInterviewGuide	= 0;
	private int	iRankPosition	= 0;
	
	public int getFKCompetency() {
		return iFKCompetency;
	}
	public void setFKCompetency(int competency) {
		iFKCompetency = competency;
	}
	public int getFKInterviewGuide() {
		return iFKInterviewGuide;
	}
	public void setFKInterviewGuide(int interviewGuide) {
		iFKInterviewGuide = interviewGuide;
	}
	public int getPKInterviewGuideItem() {
		return iPKInterviewGuideItem;
	}
	public void setPKInterviewGuideItem(int interviewGuideItem) {
		iPKInterviewGuideItem = interviewGuideItem;
	}
	public int getRankPosition() {
		return iRankPosition;
	}
	public void setRankPosition(int rankPosition) {
		iRankPosition = rankPosition;
	}
		
}
