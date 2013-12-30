package CP_Classes.vo;
 
public class votblSurveyRating {
	
	
	private int	iSurveyID	= 0 ;
	private int	iRatingTaskID	= 0 ;
	private int	iScaleID	= 0 ;
	private int	iAdminSetup	= 0 ;
	private String	sRatingTaskName	= "" ;
	private String sRatingCode="";
	private String sRatingTask="";
	private String sScaleDescription="";
	private int iScaleRange=0;
	private int iPKKeyBehaviour=0;
	private String sKeyBehaviour="";
	private int iCompetencyID=0;
	private String sCompetencyName="";
	private int sHideNA = 0; //To capture Hide NA status of the survey
	
	public String getRatingTaskName() {
		return sRatingTaskName;
	}
	public void setRatingTaskName(String ratingTaskName) {
		sRatingTaskName = ratingTaskName;
	}
	public int getAdminSetup() {
		return iAdminSetup;
	}
	public void setAdminSetup(int adminSetup) {
		iAdminSetup = adminSetup;
	}
	public int getRatingTaskID() {
		return iRatingTaskID;
	}
	public void setRatingTaskID(int ratingTaskID) {
		iRatingTaskID = ratingTaskID;
	}
	
	public int getScaleID() {
		return iScaleID;
	}
	public void setScaleID(int scaleID) {
		iScaleID = scaleID;
	}
	public int getSurveyID() {
		return iSurveyID;
	}
	public void setSurveyID(int surveyID) {
		iSurveyID = surveyID;
	}
	public int getScaleRange() {
		return iScaleRange;
	}
	public void setScaleRange(int scaleRange) {
		iScaleRange = scaleRange;
	}
	public String getScaleDescription() {
		return sScaleDescription;
	}
	public void setScaleDescription(String scaleDescription) {
		sScaleDescription = scaleDescription;
	}
	public String getRatingCode() {
		return sRatingCode;
	}
	public void setRatingCode(String ratingCode) {
		sRatingCode = ratingCode;
	}
	public String getRatingTask() {
		return sRatingTask;
	}
	public void setRatingTask(String ratingTask) {
		sRatingTask = ratingTask;
	}
	
	public int getPKKeyBehaviour() {
		return iPKKeyBehaviour;
	}
	public void setPKKeyBehaviour(int keyBehaviour) {
		iPKKeyBehaviour = keyBehaviour;
	}
	public String getCompetencyName() {
		return sCompetencyName;
	}
	public void setCompetencyName(String competencyName) {
		sCompetencyName = competencyName;
	}
	public int getCompetencyID() {
		return iCompetencyID;
	}
	public void setCompetencyID(int competencyID) {
		iCompetencyID = competencyID;
	}
	public String getKeyBehaviour() {
		return sKeyBehaviour;
	}
	public void setKeyBehaviour(String keyBehaviour) {
		sKeyBehaviour = keyBehaviour;
	}
	/**
	 * Capture the survey status if rater can rate NA or not
	 * @param sHideNA the sHideNA to set
	 * @author Sebastian
	 * @since v.1.3.12.84 (28 July 2010)
	 */
	public void setSHideNA(int sHideNA) {
		this.sHideNA = sHideNA;
	}
	/**
	 * Retrieve the survey status if rater can rate NA or not
	 * @return the sHideNA
	 * @author Sebastian
	 * @since v.1.3.12.84 (28 July 2010)
	 */
	public int getSHideNA() {
		return sHideNA;
	}
}
