package CP_Classes.vo;

public class voEthnic {
	
	private int	iPKEthnic	= 0;
	private String	sEthnicDesc = "";
	private int	iFKOrganization = 0;
	
	public String getEthnicDesc() {
		return sEthnicDesc;
	}
	public void setEthnicDesc(String ethnicDesc) {
		sEthnicDesc = ethnicDesc;
	}
	public int getFKOrganization() {
		return iFKOrganization;
	}
	public void setFKOrganization(int organization) {
		iFKOrganization = organization;
	}
	public int getPKEthnic() {
		return iPKEthnic;
	}
	public void setPKEthnic(int ethnic) {
		iPKEthnic = ethnic;
	}
	

}
