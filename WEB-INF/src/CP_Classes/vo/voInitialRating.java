package CP_Classes.vo;

public class voInitialRating {
	
	private int	iPKInitialRating	= 0;
	private int	iFKAssignment	= 0;
	private int	iFKCompetencyQuestion	= 0;
	private int	iFKKeyBehaviourQuestion	= 0;
	private int	iInitialRatingValue	= 0;
	
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
	public int getFKKeyBehaviourQuestion() {
		return iFKKeyBehaviourQuestion;
	}
	public void setFKKeyBehaviourQuestion(int keyBehaviourQuestion) {
		iFKKeyBehaviourQuestion = keyBehaviourQuestion;
	}
	public int getInitialRatingValue() {
		return iInitialRatingValue;
	}
	public void setInitialRatingValue(int initialRatingValue) {
		iInitialRatingValue = initialRatingValue;
	}
	public int getPKInitialRating() {
		return iPKInitialRating;
	}
	public void setPKInitialRating(int initialRating) {
		iPKInitialRating = initialRating;
	}
	
	
	
}
