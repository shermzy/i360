package CP_Classes.vo;

import java.util.*;
/**
 * 
 * Change Log
 * ==========
 *
 * Date        By                Method(s)            			Change(s) 
 * =====================================================================================================
 * 15/06/12	   Albert			Constructor					add a new data member useCluster including its setter and getter method
 *
 * 12/07/2012  Liu Taichen      Constructor                 add a new field HideCompDesc and its setter and getter
 *
 *
 * 
 * 09/07/12	   Albert			Constructor					add a new data member breakCPR including its setter and getter method
 */

public class votblSurvey {
	
	private int	iSurveyID	= 0 ;
	private String	sSurveyName	= "" ;
	private int	iJobPositionID	= 0 ;
	private String	sMonthYear	= "" ;
	private String	sDateOpened	= "" ;
	private int	iLevelOfSurvey	= 0 ;
	private String	sDeadlineSubmission	= "" ;
	private int	iSurveyStatus	= 0 ;
	private String	sAnalysisDate	= "" ;
	private int	iInclude_Exclude	= 0 ;
	private int	iSurveyType	= 0 ;
	private int	iPurposeID	= 0 ;
	private int	iJobFutureID	= 0 ;
	private int	iTimeFrameID	= 0 ;
	private int	iReliabilityCheck	= 0 ;
	private String	sEntryDate	= "" ;
	private int	iKBLevel	= 0 ;
	private int	iNA_Included	= 0 ;
	private int	iFKOrganization	= 0 ;
	private int	iFKCompanyID	= 0 ;
	private float	dMIN_Gap	= 0.0f ;
	private float	dMAX_Gap	= 0.0f ;
	private int	iComment_Included	= 0 ;
	private int	iAdminAssigned	= 0 ;
	private int	iSelf_Comment_Included	= 0 ;
	private String	sNominationStartDate	= "" ;
	private String	sNominationEndDate	= "" ;
	private String sOrganizationName="";
	private String sCompanyName="";
	private String sJobPosition = "";
	private int iDMapBreakdown = 0;
	private int iNameSequence = 0;
	
	private String sJobLevelName = "";
	
	private String sRaterCode = "";
	private int iTargetLoginID = 0;
	private int iRaterLoginID = 0;
	
	private int useCluster = 0;

	private int HideCompDesc = 0;

	private int breakCPR = 0;
	private int hideKBDesc = 0;
	private int prelimQ = 0;
	
	private int MergeRelation = 0;

	private Date dDeadlineSubmissionDate = null;
	private Date dOpenedDate = null;
	private Date dStartDateNomination = null;
	private Date dEndDateNomination = null;
        
        // Added by DeZ, 19/06/08, to add option to enable/disable automatic assign Self and/or Superior as rater
        private boolean bAutoSelf = true;
        private boolean bAutoSup = true;
        
        public boolean getAutoSelf() {
                //System.out.println(">>votblSurvey >> autoSelf in transfer: " + this.bAutoSelf);
		return this.bAutoSelf;
	}
	public void setAutoSelf(boolean bAutoSelf) {
		this.bAutoSelf = bAutoSelf;
                //System.out.println(">>votblSurvey >> autoSelf Set to: " + this.bAutoSelf);
	}
        
        public boolean getAutoSup() {
                //System.out.println(">>votblSurvey >> autoSup in transfer: " + this.bAutoSup);
		return bAutoSup;
	}
	public void setAutoSup(boolean bAutoSup) {
		this.bAutoSup = bAutoSup;
                //System.out.println(">>votblSurvey >> autoSup Set to: " + this.bAutoSup);
	}

	public int getUseCluster(){
		return useCluster;
	}
	public void setUseCluster(int value){
		useCluster = value;
	}
	
	public int getBreakCPR(){
		return breakCPR;
	}
	public void setBreakCPR(int value){
		breakCPR = value;
	}
	
	public int getHideKBDesc(){
		return hideKBDesc;
	}
	public void setHideKBDesc(int value){
		hideKBDesc = value;
	}
	
	public int getPrelimQ(){
		return prelimQ;
	}
	public void setPrelimQ(int value){
		prelimQ = value;
	}
	
	public String getCompanyName() {
		return sCompanyName;
	}
	public void setCompanyName(String companyName) {
		sCompanyName = companyName;
	}
	public String getOrganizationName() {
		return sOrganizationName;
	}
	public void setOrganizationName(String organizationName) {
		sOrganizationName = organizationName;
	}
	
	public int getAdminAssigned() {
		return iAdminAssigned;
	}
	public void setAdminAssigned(int adminAssigned) {
		iAdminAssigned = adminAssigned;
	}
	public String getAnalysisDate() {
		return sAnalysisDate;
	}
	public void setAnalysisDate(String analysisDate) {
		sAnalysisDate = analysisDate;
	}
	public int getComment_Included() {
		return iComment_Included;
	}
	public void setComment_Included(int comment_Included) {
		iComment_Included = comment_Included;
	}
	public String getDateOpened() {
		return sDateOpened;
	}
	public void setDateOpened(String dateOpened) {
		sDateOpened = dateOpened;
	}
	public String getDeadlineSubmission() {
		return sDeadlineSubmission;
	}
	public void setDeadlineSubmission(String deadlineSubmission) {
		sDeadlineSubmission = deadlineSubmission;
	}
	public String getEntryDate() {
		return sEntryDate;
	}
	public void setEntryDate(String entryDate) {
		sEntryDate = entryDate;
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
	public int getInclude_Exclude() {
		return iInclude_Exclude;
	}
	public void setInclude_Exclude(int include_Exclude) {
		iInclude_Exclude = include_Exclude;
	}
	public int getJobFutureID() {
		return iJobFutureID;
	}
	public void setJobFutureID(int jobFutureID) {
		iJobFutureID = jobFutureID;
	}
	public int getJobPositionID() {
		return iJobPositionID;
	}
	public void setJobPositionID(int jobPositionID) {
		iJobPositionID = jobPositionID;
	}
	public int getKBLevel() {
		return iKBLevel;
	}
	public void setKBLevel(int level) {
		iKBLevel = level;
	}
	public int getLevelOfSurvey() {
		return iLevelOfSurvey;
	}
	public void setLevelOfSurvey(int levelOfSurvey) {
		iLevelOfSurvey = levelOfSurvey;
	}
	public float getMAX_Gap() {
		return dMAX_Gap;
	}
	public void setMAX_Gap(float gap) {
		dMAX_Gap = gap;
	}
	public float getMIN_Gap() {
		return dMIN_Gap;
	}
	public void setMIN_Gap(float gap) {
		dMIN_Gap = gap;
	}
	public String getMonthYear() {
		return sMonthYear;
	}
	public void setMonthYear(String monthYear) {
		sMonthYear = monthYear;
	}
	public int getNA_Included() {
		return iNA_Included;
	}
	public void setNA_Included(int included) {
		iNA_Included = included;
	}
	public String getNominationEndDate() {
		return sNominationEndDate;
	}
	public void setNominationEndDate(String nominationEndDate) {
		sNominationEndDate = nominationEndDate;
	}
	public String getNominationStartDate() {
		return sNominationStartDate;
	}
	public void setNominationStartDate(String nominationStartDate) {
		sNominationStartDate = nominationStartDate;
	}
	public int getPurposeID() {
		return iPurposeID;
	}
	public void setPurposeID(int purposeID) {
		iPurposeID = purposeID;
	}
	public int getReliabilityCheck() {
		return iReliabilityCheck;
	}
	public void setReliabilityCheck(int reliabilityCheck) {
		iReliabilityCheck = reliabilityCheck;
	}
	public int getSelf_Comment_Included() {
		return iSelf_Comment_Included;
	}
	public void setSelf_Comment_Included(int self_Comment_Included) {
		iSelf_Comment_Included = self_Comment_Included;
	}
	public int getSurveyID() {
		return iSurveyID;
	}
	public void setSurveyID(int surveyID) {
		iSurveyID = surveyID;
	}
	public String getSurveyName() {
		return sSurveyName;
	}
	public void setSurveyName(String surveyName) {
		sSurveyName = surveyName;
	}
	public int getSurveyStatus() {
		return iSurveyStatus;
	}
	public void setSurveyStatus(int surveyStatus) {
		iSurveyStatus = surveyStatus;
	}
	public int getSurveyType() {
		return iSurveyType;
	}
	public void setSurveyType(int surveyType) {
		iSurveyType = surveyType;
	}
	public int getTimeFrameID() {
		return iTimeFrameID;
	}
	public void setTimeFrameID(int timeFrameID) {
		iTimeFrameID = timeFrameID;
	}
	public void setJobPosition(String sJobPosition) {
		this.sJobPosition = sJobPosition;
	}
	public String getJobPosition() {
		return sJobPosition;
	}
	public void setDMapBreakdown(int iDMapBreakdown) {
		this.iDMapBreakdown = iDMapBreakdown;
	}
	public int getDMapBreakdown() {
		return iDMapBreakdown;
	}
	public void setNameSequence(int iNameSequence) {
		this.iNameSequence = iNameSequence;
	}
	public int getNameSequence() {
		return iNameSequence;
	}
	public void setJobLevelName(String sJobLevelName) {
		this.sJobLevelName = sJobLevelName;
	}
	public String getJobLevelName() {
		return sJobLevelName;
	}
	public void setRaterCode(String sRaterCode) {
		this.sRaterCode = sRaterCode;
	}
	public String getRaterCode() {
		return sRaterCode;
	}
	public void setTargetLoginID(int iTargetLoginID) {
		this.iTargetLoginID = iTargetLoginID;
	}
	public int getTargetLoginID() {
		return iTargetLoginID;
	}
	public void setRaterLoginID(int iRaterLoginID) {
		this.iRaterLoginID = iRaterLoginID;
	}
	public int getRaterLoginID() {
		return iRaterLoginID;
	}
	public void setDeadlineSubmissionDate(Date dDeadlineSubmissionDate) {
		this.dDeadlineSubmissionDate = dDeadlineSubmissionDate;
	}
	public Date getDeadlineSubmissionDate() {
		return dDeadlineSubmissionDate;
	}
	public void setOpenedDate(Date dOpenedDate) {
		this.dOpenedDate = dOpenedDate;
	}
	public Date getOpenedDate() {
		return dOpenedDate;
	}
	public void setStartDateNomination(Date dStartDateNomination) {
		this.dStartDateNomination = dStartDateNomination;
	}
	public Date getStartDateNomination() {
		return dStartDateNomination;
	}
	public void setEndDateNomination(Date dEndDateNomination) {
		this.dEndDateNomination = dEndDateNomination;
	}
	public Date getEndDateNomination() {
		return dEndDateNomination;
	}
	public int getHideCompDesc() {
		return HideCompDesc;
	}
	public void setHideCompDesc(int hideCompDesc) {
		this.HideCompDesc = hideCompDesc;
	}
	public int getMergeRelation() {
		return MergeRelation;
	}
	public void setMergeRelation(int mergeRelation) {
		MergeRelation = mergeRelation;
	}
	
	
	
	
}
