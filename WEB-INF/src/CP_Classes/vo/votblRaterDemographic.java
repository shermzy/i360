package CP_Classes.vo;

public class votblRaterDemographic {
	

	private int	iAgeGroupID = 0 ;
	private int	iGenderID = 0 ;
	private int	iEthnicGroupID = 0 ;
	private int	iJobFunctionID = 0 ;
	private int	iJobLevelID = 0 ;
	private int	iLocationID = 0 ;
	private int	iDepartmentID = 0 ;
	
	private String	sLoginID = "" ;

	public int getAgeGroupID() {
		return iAgeGroupID;
	}

	public void setAgeGroupID(int ageGroupID) {
		iAgeGroupID = ageGroupID;
	}

	public int getDepartmentID() {
		return iDepartmentID;
	}

	public void setDepartmentID(int departmentID) {
		iDepartmentID = departmentID;
	}

	public int getEthnicGroupID() {
		return iEthnicGroupID;
	}

	public void setEthnicGroupID(int ethnicGroupID) {
		iEthnicGroupID = ethnicGroupID;
	}

	public int getGenderID() {
		return iGenderID;
	}

	public void setGenderID(int genderID) {
		iGenderID = genderID;
	}

	public int getJobFunctionID() {
		return iJobFunctionID;
	}

	public void setJobFunctionID(int jobFunctionID) {
		iJobFunctionID = jobFunctionID;
	}

	public int getJobLevelID() {
		return iJobLevelID;
	}

	public void setJobLevelID(int jobLevelID) {
		iJobLevelID = jobLevelID;
	}

	public int getLocationID() {
		return iLocationID;
	}

	public void setLocationID(int locationID) {
		iLocationID = locationID;
	}

	public String getLoginID() {
		return sLoginID;
	}

	public void setLoginID(String loginID) {
		sLoginID = loginID;
	}
	
	

}
