 package CP_Classes.vo;

public class voJobProfileRate {

	private int	iPKJobProfileRate	= 0;
	private int iFKAssignment	= 0;
	private int	iFKCompetencyQuestion	= 0;
	private int	iRateAnswer	= 0;
	
	public int getFKAssignment() {
		return iFKAssignment;
	}
	public void setFKAssignment(int assignment) {
		iFKAssignment = assignment;
	}
	public int getFKCompetencyQuestion() {
		return iFKCompetencyQuestion;
	}
	public void setFKCompetencyQuestion(int competencyQuestion) {
		iFKCompetencyQuestion = competencyQuestion;
	}
	public int getPKJobProfileRate() {
		return iPKJobProfileRate;
	}
	public void setPKJobProfileRate(int jobProfileRate) {
		iPKJobProfileRate = jobProfileRate;
	}
	public int getRateAnswer() {
		return iRateAnswer;
	}
	public void setRateAnswer(int rateAnswer) {
		iRateAnswer = rateAnswer;
	}
	

	
}
