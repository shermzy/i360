package CP_Classes.vo;

public class votblGap {

	private int	iSurveyID	= 0 ;
	private int	iTargetLoginID	= 0 ;
	private int	iCompetencyID	= 0 ;
	private int	iKeyBehaviourID	= 0 ;
	private double	dGap	= 0.0 ;
	
	public int getCompetencyID() {
		return iCompetencyID;
	}
	public void setCompetencyID(int competencyID) {
		iCompetencyID = competencyID;
	}
	public double getGap() {
		return dGap;
	}
	public void setGap(double gap) {
		dGap = gap;
	}
	public int getKeyBehaviourID() {
		return iKeyBehaviourID;
	}
	public void setKeyBehaviourID(int keyBehaviourID) {
		iKeyBehaviourID = keyBehaviourID;
	}
	public int getSurveyID() {
		return iSurveyID;
	}
	public void setSurveyID(int surveyID) {
		iSurveyID = surveyID;
	}
	public int getTargetLoginID() {
		return iTargetLoginID;
	}
	public void setTargetLoginID(int targetLoginID) {
		iTargetLoginID = targetLoginID;
	}
	
	
	
}
