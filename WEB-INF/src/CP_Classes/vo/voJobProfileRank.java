 package CP_Classes.vo;

public class voJobProfileRank {

	private int	iPKJobProfileRank	= 0;
	private int iFKAssignment	= 0;
	private int	iFKJobProfileQuestion	= 0;
	private int	iRankingAnswer	= 0;
	
	public int getFKAssignment() {
		return iFKAssignment;
	}
	public void setFKAssignment(int assignment) {
		iFKAssignment = assignment;
	}
	public int getFKJobProfileQuestion() {
		return iFKJobProfileQuestion;
	}
	public void setFKJobProfileQuestion(int jobProfileQuestion) {
		iFKJobProfileQuestion = jobProfileQuestion;
	}
	public int getPKJobProfileRank() {
		return iPKJobProfileRank;
	}
	public void setPKJobProfileRank(int jobProfileRank) {
		iPKJobProfileRank = jobProfileRank;
	}
	public int getRankingAnswer() {
		return iRankingAnswer;
	}
	public void setRankingAnswer(int rankingAnswer) {
		iRankingAnswer = rankingAnswer;
	}
	
}
