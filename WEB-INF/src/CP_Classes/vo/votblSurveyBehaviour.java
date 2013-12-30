package CP_Classes.vo;

public class votblSurveyBehaviour {
	
	
	private int	iSurveyID	= 0 ;
	private int	iCompetencyID	= 0 ;
	private int	iKeyBehaviourID	= 0 ;
	private String sCompetencyName = "";
	private String sKBName = "";
	
	
	public int getCompetencyID() {
		return iCompetencyID;
	}
	public void setCompetencyID(int competencyID) {
		iCompetencyID = competencyID;
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

	public void setKBName(String sKBName) {
		this.sKBName = sKBName;
	}
	public String getKBName() {
		return sKBName;
	}
	public void setCompetencyName(String sCompetencyName) {
		this.sCompetencyName = sCompetencyName;
	}
	public String getCompetencyName() {
		return sCompetencyName;
	}
	

}
