package CP_Classes.vo;

/*
 * Change Log
 * ===============================================================================================================
 * 	Date		Changed By		Method(s)								Change(s)
 * ===============================================================================================================
 * 18/06/2012	Albert			-								Added this java file to manage clusters of each survey
 */

public class votblSurveyCluster {
	
	
	private int	iSurveyID	= 0 ;
	private int	iClusterID	= 0 ;
	private String sClusterName = "";
	
	public int getClusterID() {
		return iClusterID;
	}
	public void setClusterID(int competencyID) {
		iClusterID = competencyID;
	}
	public int getSurveyID() {
		return iSurveyID;
	}
	public void setSurveyID(int surveyID) {
		iSurveyID = surveyID;
	}
	public void setClusterName(String sClusterName) {
		this.sClusterName = sClusterName;
	}
	public String getClusterName() {
		return sClusterName;
	}

}
