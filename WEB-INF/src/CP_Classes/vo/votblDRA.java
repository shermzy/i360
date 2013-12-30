package CP_Classes.vo;

public class votblDRA {

	private int	iDRAID	= 0 ;
	private int	iCompetencyID	= 0 ;
	private String[]	sDRAStatement	= new String[6] ;
	private String sDescription="";
	private int	iDRACounter	= 0 ;
	private int	iIsSystemGenerated	= 0 ;
	private int	iFKCompanyID	= 0 ;
	private int	iFKOrganizationID	= 0 ;
	
	public int getCompetencyID() {
		return iCompetencyID;
	}
	public void setCompetencyID(int competencyID) {
		iCompetencyID = competencyID;
	}
	public int getDRACounter() {
		return iDRACounter;
	}
	public void setDRACounter(int counter) {
		iDRACounter = counter;
	}
	public int getDRAID() {
		return iDRAID;
	}
	public void setDRAID(int draid) {
		iDRAID = draid;
	}
	public String getDRAStatement() {
		return sDRAStatement[0];
	}
	
	public String getDRAStatement(int lang){
		return sDRAStatement[lang];
	}
	public String[] getAllDRAStatement(){
		return sDRAStatement;
	}
	public void setDRAStatement(String statement) {
		sDRAStatement[0] = statement;
	}
	
	public void setDRAStatement(int lang, String statement){
		sDRAStatement[lang] = statement;
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
	public String getDescription() {
		return sDescription;
	}
	public void setDescription(String description) {
		sDescription = description;
	}
	
	

}
