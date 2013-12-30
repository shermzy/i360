 package CP_Classes.vo;

public class voJobProfile {

	private int	iPKJobProfile	= 0;
	private String	sJobProfileName	= "";
	private int	iAnalysisLevelEnum	= 0;
	private String	sEntryDate	= "";
	private String	sIRAnalyzeDate	= "";
	private int	iIRFKUser	= 0;
	private String	sRRAnalyzeDate	= "";
	private String	sRRFKUser	= "";
	private String	sFAnalyzeDate	= "";
	private String	sFFKUser	= "";
	
	public int getAnalysisLevelEnum() {
		return iAnalysisLevelEnum;
	}
	public void setAnalysisLevelEnum(int analysisLevelEnum) {
		iAnalysisLevelEnum = analysisLevelEnum;
	}
	public String getEntryDate() {
		return sEntryDate;
	}
	public void setEntryDate(String entryDate) {
		sEntryDate = entryDate;
	}
	public String getFAnalyzeDate() {
		return sFAnalyzeDate;
	}
	public void setFAnalyzeDate(String analyzeDate) {
		sFAnalyzeDate = analyzeDate;
	}
	public String getFFKUser() {
		return sFFKUser;
	}
	public void setFFKUser(String user) {
		sFFKUser = user;
	}
	public String getIRAnalyzeDate() {
		return sIRAnalyzeDate;
	}
	public void setIRAnalyzeDate(String analyzeDate) {
		sIRAnalyzeDate = analyzeDate;
	}
	public int getIRFKUser() {
		return iIRFKUser;
	}
	public void setIRFKUser(int user) {
		iIRFKUser = user;
	}
	public String getJobProfileName() {
		return sJobProfileName;
	}
	public void setJobProfileName(String jobProfileName) {
		sJobProfileName = jobProfileName;
	}
	public int getPKJobProfile() {
		return iPKJobProfile;
	}
	public void setPKJobProfile(int jobProfile) {
		iPKJobProfile = jobProfile;
	}
	public String getRRAnalyzeDate() {
		return sRRAnalyzeDate;
	}
	public void setRRAnalyzeDate(String analyzeDate) {
		sRRAnalyzeDate = analyzeDate;
	}
	public String getRRFKUser() {
		return sRRFKUser;
	}
	public void setRRFKUser(String user) {
		sRRFKUser = user;
	}
	
	
	
}
