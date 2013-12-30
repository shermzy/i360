package CP_Classes.vo;

public class voInitialRatingAnalyzed {
	
	private int	iPKInitialRatingAnalyzed	= 0;
	private int	iFKJobProfile = 0;
	private int	iFKCompetencyQuestion	= 0;
	private int	iFKKeyBehaviourQuestion	= 0;
	private double	dAvgValue	= 0.0;
	private int	iIsIncluded	= 0;
	private String supsize_ts	= "" ;
	
	public double getAvgValue() {
		return dAvgValue;
	}
	public void setAvgValue(double avgValue) {
		dAvgValue = avgValue;
	}
	public int getFKCompetencyQuestion() {
		return iFKCompetencyQuestion;
	}
	public void setFKCompetencyQuestion(int competencyQuestion) {
		iFKCompetencyQuestion = competencyQuestion;
	}
	public int getFKJobProfile() {
		return iFKJobProfile;
	}
	public void setFKJobProfile(int jobProfile) {
		iFKJobProfile = jobProfile;
	}
	public int getFKKeyBehaviourQuestion() {
		return iFKKeyBehaviourQuestion;
	}
	public void setFKKeyBehaviourQuestion(int keyBehaviourQuestion) {
		iFKKeyBehaviourQuestion = keyBehaviourQuestion;
	}
	public int getIsIncluded() {
		return iIsIncluded;
	}
	public void setIsIncluded(int isIncluded) {
		iIsIncluded = isIncluded;
	}
	public int getPKInitialRatingAnalyzed() {
		return iPKInitialRatingAnalyzed;
	}
	public void setPKInitialRatingAnalyzed(int initialRatingAnalyzed) {
		iPKInitialRatingAnalyzed = initialRatingAnalyzed;
	}
	public String getUpsize_ts() {
		return supsize_ts;
	}
	public void setUpsize_ts(String upsize_ts) {
		this.supsize_ts = upsize_ts;
	}
	
		
	
	
}

	

