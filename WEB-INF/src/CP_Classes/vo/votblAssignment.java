package CP_Classes.vo;

public class votblAssignment {
	
	private int	iAssignmentID	= 0 ;
	private int	iSurveyID	= 0 ;
	private int	iRTRelation	= 0 ;
	private int	iRTSpecific	= 0 ;
	private String	sRaterCode	= "";	
	private int	iRaterStatus	= 0 ;
	private int	iReliability	= 0 ;
	private String	sDeadLineSubmission	= "";	
	private String	sEntryDate	= "";	
	private int	iRaterLoginID	= 0 ;
	private int	iTargetLoginID	= 0 ;
	private int	iFKTargetDivision	= 0 ;
	private int	iFKTargetDepartment	= 0 ;
	private int	iFKTargetGroup	= 0 ;
	private int iWave = 0;
	private int iRound = 0;
	private String sRelationSpecific = "";
	private String sFullName="";
	
	private int iRatingTaskID = 0;
	private int iLevelOfSurvey = 0;
	private String sRatingTaskName = "";
	
	private String sLoginName = "";
	private int iFKGroup = 0;
	private String sGroupName = "";
	
	public int getAssignmentID() {
		return iAssignmentID;
	}
	public void setAssignmentID(int assignmentID) {
		iAssignmentID = assignmentID;
	}
	public String getDeadLineSubmission() {
		return sDeadLineSubmission;
	}
	public void setDeadLineSubmission(String deadLineSubmission) {
		sDeadLineSubmission = deadLineSubmission;
	}
	public String getEntryDate() {
		return sEntryDate;
	}
	public void setEntryDate(String entryDate) {
		sEntryDate = entryDate;
	}
	public int getFKTargetDepartment() {
		return iFKTargetDepartment;
	}
	public void setFKTargetDepartment(int targetDepartment) {
		iFKTargetDepartment = targetDepartment;
	}
	public int getFKTargetDivision() {
		return iFKTargetDivision;
	}
	public void setFKTargetDivision(int targetDivision) {
		iFKTargetDivision = targetDivision;
	}
	public int getFKTargetGroup() {
		return iFKTargetGroup;
	}
	public void setFKTargetGroup(int targetGroup) {
		iFKTargetGroup = targetGroup;
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
	public int getReliability() {
		return iReliability;
	}
	public void setReliability(int reliability) {
		iReliability = reliability;
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
	public void setWave(int wave){
		iWave = wave;
	}
	public void setRound(int round){
		iRound = round;
	}
	public void setRelationSpecific(String sRelationSpecific) {
		this.sRelationSpecific = sRelationSpecific;
	}
	public String getRelationSpecific() {
		return sRelationSpecific;
	}
	public String getFullName() {
		return sFullName;
	}
	public void setFullName(String fullName) {
		sFullName = fullName;
	}
	public void setRatingTaskID(int iRatingTaskID) {
		this.iRatingTaskID = iRatingTaskID;
	}
	public int getRatingTaskID() {
		return iRatingTaskID;
	}
	public void setLevelOfSurvey(int iLevelOfSurvey) {
		this.iLevelOfSurvey = iLevelOfSurvey;
	}
	public int getLevelOfSurvey() {
		return iLevelOfSurvey;
	}
	public void setRatingTaskName(String sRatingTaskName) {
		this.sRatingTaskName = sRatingTaskName;
	}
	public String getRatingTaskName() {
		return sRatingTaskName;
	}
	public void setLoginName(String sLoginName) {
		this.sLoginName = sLoginName;
	}
	public String getLoginName() {
		return sLoginName;
	}
	public void setFKGroup(int iFKGroup) {
		this.iFKGroup = iFKGroup;
	}
	public int getFKGroup() {
		return iFKGroup;
	}
	public void setGroupName(String sGroupName) {
		this.sGroupName = sGroupName;
	}
	public String getGroupName() {
		return sGroupName;
	}
	public int getWave(){
		return iWave;
	}
	
	public int getRound(){
		return iRound;
	}

}
