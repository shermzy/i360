package CP_Classes.vo;

public class votblJobPosition {
	
	private String	sJobPosition = "" ;
	private int	iJobPositionID = 0 ;
	private int	iFKOrganization = 0 ;
	
	public int getFKOrganization() {
		return iFKOrganization;
	}

	public void setFKOrganization(int organization) {
		iFKOrganization = organization;
	}

	public int getJobPositionID() {
		return iJobPositionID;
	}

	public void setJobPositionID(int jobPositionID) {
		iJobPositionID = jobPositionID;
	}

	public String getJobPosition() {
		return sJobPosition;
	}

	public void setJobPosition(String jobPosition) {
		sJobPosition = jobPosition;
	}	
}
