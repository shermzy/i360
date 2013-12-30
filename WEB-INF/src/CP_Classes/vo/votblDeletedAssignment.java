package CP_Classes.vo;

public class votblDeletedAssignment {
	
	private int	iDeletedID	= 0 ;
	private int	iSurveyID	= 0 ;
	private int iRTRelation	= 0 ;
	private int	iRTSpecific	= 0 ;
	private String	sRaterCode	= "" ;
	private int	iRaterStatus	= 0 ;
	private double	dReliabilityScore = 0 ;
	private String	sDeadLineSubmission	= "" ;
	private String	sEntryDate	= "" ;
	private int	iRaterLoginID	= 0 ;
	private int	iTargetLoginID	= 0 ;
	private int	iIsDeletedSurvey	= 0 ;
	
	public String getDeadLineSubmission() {
		return sDeadLineSubmission;
	}
	public void setDeadLineSubmission(String deadLineSubmission) {
		sDeadLineSubmission = deadLineSubmission;
	}
	public int getDeletedID() {
		return iDeletedID;
	}
	public void setDeletedID(int deletedID) {
		iDeletedID = deletedID;
	}
	public String getEntryDate() {
		return sEntryDate;
	}
	public void setEntryDate(String entryDate) {
		sEntryDate = entryDate;
	}
	public int getIsDeletedSurvey() {
		return iIsDeletedSurvey;
	}
	public void setIsDeletedSurvey(int isDeletedSurvey) {
		iIsDeletedSurvey = isDeletedSurvey;
	}
	public String getRaterCode() {
		return sRaterCode;
	}
	public void setRaterCode(String raterCode) {
		sRaterCode = raterCode;
	}
	public int getRaterLoginID() {
		return iRaterLoginID;
	}
	public void setRaterLoginID(int raterLoginID) {
		iRaterLoginID = raterLoginID;
	}
	public int getRaterStatus() {
		return iRaterStatus;
	}
	public void setRaterStatus(int raterStatus) {
		iRaterStatus = raterStatus;
	}
	public double getReliabilityScore() {
		return dReliabilityScore;
	}
	public void setReliabilityScore(double reliabilityScore) {
		dReliabilityScore = reliabilityScore;
	}
	public int getRTRelation() {
		return iRTRelation;
	}
	public void setRTRelation(int relation) {
		iRTRelation = relation;
	}
	public int getRTSpecific() {
		return iRTSpecific;
	}
	public void setRTSpecific(int specific) {
		iRTSpecific = specific;
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
