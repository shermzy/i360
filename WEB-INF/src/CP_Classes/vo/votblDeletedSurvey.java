package CP_Classes.vo;

public class votblDeletedSurvey {
	
	private int	iDeletedSurveyID	= 0 ;
	private int	iFKOrganization	= 0 ;
	private int iFKCompanyID	= 0 ;
	private String	sSurveyName	= "" ;
	private String	sFilename	= "" ;
	private String	sDeletedDate	= "" ;

	
	public String getFilename() {
		return sFilename;
	}
	public void setFilename(String filename) {
		sFilename = filename;
	}
	public String getDeletedDate() {
		return sDeletedDate;
	}
	public void setDeletedDate(String deletedDate) {
		sDeletedDate = deletedDate;
	}
	public int getDeletedSurveyID() {
		return iDeletedSurveyID;
	}
	public void setDeletedSurveyID(int deletedSurveyID) {
		iDeletedSurveyID = deletedSurveyID;
	}
	public int getFKCompanyID() {
		return iFKCompanyID;
	}
	public void setFKCompanyID(int companyID) {
		iFKCompanyID = companyID;
	}
	public int getFKOrganization() {
		return iFKOrganization;
	}
	public void setFKOrganization(int organization) {
		iFKOrganization = organization;
	}

	public String getSurveyName() {
		return sSurveyName;
	}
	public void setSurveyName(String surveyName) {
		sSurveyName = surveyName;
	}
	
	

}
