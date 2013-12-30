package CP_Classes.vo;

public class voLocation {

	private int	iPKLocation	= 0;
	private String	sLocationName	= "";
	private int	iFKOrganization = 0;
	
	public int getFKOrganization() {
		return iFKOrganization;
	}
	public void setFKOrganization(int organization) {
		iFKOrganization = organization;
	}
	public String getLocationName() {
		return sLocationName;
	}
	public void setLocationName(String locationName) {
		sLocationName = locationName;
	}
	public int getPKLocation() {
		return iPKLocation;
	}
	public void setPKLocation(int location) {
		iPKLocation = location;
	}
	
	
	
	
}
