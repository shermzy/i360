package CP_Classes.vo;

public class votblScale {
	
	private int	iScaleID	= 0 ;
	private String	sScaleDescription	= "" ;
	private String	sScaleType	= "" ;
	private String sDescription="";
	private int	iScaleDefault	= 0 ;
	private int	iFKCompanyID	= 0 ;
	private int	iFKOrganizationID	= 0 ;
	private int	iScaleRange	= 0 ;
	private int	iIsSystemGenerated	= 0 ;
	
	public String getDescription() {
		return sDescription;
	}
	public void setDescription(String description) {
		sDescription = description;
	}
	public int getFKCompanyID() {
		return iFKCompanyID;
	}
	public void setFKCompanyID(int companyID) {
		iFKCompanyID = companyID;
	}
	public int getFKOrganizationID() {
		return iFKOrganizationID;
	}
	public void setFKOrganizationID(int organizationID) {
		iFKOrganizationID = organizationID;
	}
	public int getIsSystemGenerated() {
		return iIsSystemGenerated;
	}
	public void setIsSystemGenerated(int isSystemGenerated) {
		iIsSystemGenerated = isSystemGenerated;
	}
	public int getScaleDefault() {
		return iScaleDefault;
	}
	public void setScaleDefault(int scaleDefault) {
		iScaleDefault = scaleDefault;
	}
	public String getScaleDescription() {
		return sScaleDescription;
	}
	public void setScaleDescription(String scaleDescription) {
		sScaleDescription = scaleDescription;
	}
	public int getScaleID() {
		return iScaleID;
	}
	public void setScaleID(int scaleID) {
		iScaleID = scaleID;
	}
	public int getScaleRange() {
		return iScaleRange;
	}
	public void setScaleRange(int scaleRange) {
		iScaleRange = scaleRange;
	}
	public String getScaleType() {
		return sScaleType;
	}
	public void setScaleType(String scaleType) {
		sScaleType = scaleType;
	}
	
	
}
