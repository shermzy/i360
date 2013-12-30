package CP_Classes;

import java.io.IOException;

public class Setting 
{
	/* Fixed settings, change manually if required */
	public int CompanySetting = 1; // 1=PCC, 2=Demo, 3=Toyota (Own Server), 4=MineBea (Own Server)
	public boolean isTestEnv = false; //TEST ENV, REMEMBER TO set to FALSE before delivering to customer
	
	// Change BCC Email from 3SixtyAdmin@gmail.com to new one, i.e. pccpobox@gmail.com, Desmond 09 Oct 09
	private String BCC_Email = "pccpobox@gmail.com"; //password=check with person in charge
	public String encryptKey = "ipod";
	/* END Fixed settings, change manually if required */
	
	/* Below settings will be auto set according to organisation in constructor "Setting()" */
	public int NomModule = 1; // 0=Nomination Module OFF, 1=Nomination Module ON
	public int PasswordSetting = 1; // 0= Hide user's password from Admin, 1=Admin can see Admin's password and export user to display the password.
	public int LangVer = 1; // 1=Eng, 2=INA
	public int allowBCCEmail = 1; // 0= BCC email disabled, 1 = BCC email enabled
	private boolean IsStandalone = true;
	
	private String ServerName = "PCC";
	
	// Changed by Desmond 07 Oct in order to use Singnet SMTP, by default use Starhub one for development purposes
	//private String EmailHost = "pcc.com.sg";
	//private String EmailHost = "smtp.pacific.net.sg"; // For PacNet
	private String EmailHost = "smtp.starhub.net.sg"; // For Starhub
	
	// Added by Desmond 9 Oct, for sending email using Singnet SMTP
	private String EmailUser = "smtpacct@pcc.com.sg";
	private String EmailPwd = "GRace0fGod";
	
	private String HREmail = "3SixtyAdmin@pcc.com.sg";
	private String AdminEmail = "3SixtyAdmin@pcc.com.sg";
	private String Email_Hyperlink = "3SixtyAdmin@pcc.com.sg";
	private String Website_Hyperlink = "http://119.73.212.178/i360_Pool_SVNJSP/index.jsp";
	private boolean allowDeleteFunc = true; //true -->capture deletion record (Not allowed)
	
	//Web Server Path change from static to dynamic -> Code shift to Setting()
	//By: Chun Pong 
	//Date: 06 Jun 2008
	//Reason: FileNotFound exception if web server is not installed at "c:\resin\"
	//Old Code:
	//private String App_Path = "C:\\resin\\webapps\\i360_Pool_CVSJSP";	
	//private String OOAppPath = "C:/resin/webapps/i360_pool_cvsjsp/";
	private String App_Path = "";
	private String OOAppPath  	= "";
	
	//Updated Folder Name to i360_Pool_SVNJSP
	//By: Chun Pong
	//Date: 29 Jul 2008
	//Reason: Report path updated to SVN from CVS
	//private String folderName = "Recruit_Pool";
	private String folderName = "i360_Pool_SVNJSP";
	
	/* Reports Path Settings */
	//Paths dynamically defined in Setting(), because paths are determined by App_Path which may be different in 
	//each organisation
	private String Report_Path;
	private String Report_Path_Template;
	private String inputWorkbook;
	private String Deleted_Path;
	private String logoPath;
	private String CoachFilePath;
	private String UploadPath;
	private String OOReportPath;
	private String OOReportTemplatePath;
	private String OOLogoPath;
	/* End Reports Path Settings */
	
	/* Rianto (26-oct-05) : to be deleted once running smoothly
	private String Report_Path = App_Path  + "\\Report";
	private String Report_Path_Template = Report_Path + "\\Template";
	private String inputWorkbook = Report_Path + "\\Template\\DevelopmentGuide Template.xls";
	private String Deleted_Path = App_Path + "\\Deleted";
	private String logoPath = App_Path + "\\Logo";
	private String UploadPath = App_Path + "\\Upload";
	
	//OpenOffice Application Path
	//Added by Jenty in implementation of OpenOffice, on 18 Aug 05.
	private String OOAppPath 			= "C:/tomcat/webapps/i360/";
	//private String OOAppPath 			= "C:/Program Files/Program/Tomcat 4.1/webapps/i360/";
	
	private String OOReportPath 		= OOAppPath + "Report/";
	private String OOReportTemplatePath = OOReportPath + "Template/";
	private String OOLogoPath	 		= OOAppPath + "Logo/";
	*/
	public static void main(String args[])
	{
		//Setting a = new Setting();
	}
	
	public Setting()
	{		
		//added by Alvis on 01-Sep-09 to generate dynamic filepath to allow different folder name
		folderName = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
	    int startindex = folderName.indexOf("webapps/");
	    startindex = startindex + 8;//move to end of "webapps/"
	    int endindex = folderName.indexOf('/', startindex);
	    folderName = folderName.substring(startindex, endindex);
	    
		//1) Change from static to dynamic path and 
		//2) removing redundant Tomcat Server path declaration
		//By: Chun Pong
		//Date: 06 Jun 2008
		//Reason: 1) Change server path from static to dynamic 
		//        2) Dynamic path allow merge of Resin and Tomcat's path code
		if(System.getProperty("user.dir").indexOf("webapps")== -1){
			
			
	
			App_Path = System.getProperty("user.dir") + "/webapps/" + folderName;
		
		}
		else {
			App_Path = System.getProperty("user.dir").substring(0,(System.getProperty("user.dir").indexOf("webapps")+8)) + folderName;
		}
			
		
		//Note: If path's value is directly assigned to "OO_Path", it will cause error due to the fact
		//that all "\\" will become "\".
		OOAppPath = App_Path.replace("\\","/");
		
		/**** Toyota Env**************/
		if (CompanySetting == 3)
		{
			NomModule = 1; // 0=Nomination Module OFF, 1=Nomination Module ON
			PasswordSetting = 1; // 0= Hide user's password from Admin, 1=Admin can see Admin's password and export user to display the password.
			LangVer = 1; // 1=Eng, 2=INA
			allowBCCEmail = 0; //0= not allowed, 1=allowed
			IsStandalone = true;
	
			ServerName = "Toyota";
			EmailHost = "172.17.1.13";
			//EmailHost = "smtp.pacific.net.sg";
			HREmail = "academy@toyota.co.th";
			AdminEmail = "academy@toyota.co.th";
			Email_Hyperlink = "academy@toyota.co.th";
			Website_Hyperlink = "http://www.toyotaacademy.com/showmessage.php?t=message2&m_id=43?uncode=toyota&ucode=";
			//Website_Hyperlink = "http://toyotaacademy:8080/i360/index.jsp";
			//Website_Hyperlink = "http://172.17.1.36/ta/showmessage.php-t=message2&m_id=43-uncode=toyota&ucode=.htm";
			//Website_Hyperlink = "http://www.toyotaacademy.com//index.html"; 
			allowDeleteFunc = true; //default not allowed
	
			/** TMT'S PATH, PLEASE DO NOT DELETE ***/	
			App_Path 	= "F:\\TA-360\\resin\\webapps\\i360";
			OOAppPath = "F:/TA-360/resin/webapps/i360/";
	
		}
		/**** END Toyota Env**************/
		
		/**** MINEBEA Env**************/
		if (CompanySetting == 4)
		{
			NomModule = 0; // 0=Nomination Module OFF, 1=Nomination Module ON
			PasswordSetting = 1; // 0= Hide user's password from Admin, 1=Admin can see Admin's password and export user to display the password.
			LangVer = 1; // 1=Eng, 2=INA
			IsStandalone = true;
			
			//Redundant Code
			//By:Chun Pong
			//By: Chun Pong
			//Date: 06 Jun 2008
			//By: Chun Pong
			//Date: 06 Jun 2008
			//Reason: Change server path from static to dynamic 
			//Old Code:
			//App_Path = "C:\\resin\\webapps\\i360";
			//OOAppPath = "C:/resin/webapps/i360/";
		}
		/**** END MINEBEA Env**************/
		
		/******* TESTING ENVIRONMENT ********/
		if(isTestEnv == true)
		{	
			//System.out.println("TEST ENVIRONMENT");
			//Usage of test environment : When CompanySetting must be > 1, and need to test in PCC's computer
			NomModule = 1;
			PasswordSetting = 1;
			LangVer = 1;
			allowBCCEmail = 1;
			IsStandalone = true;
			ServerName = "Toyota";
			EmailHost = "smtp.pacific.net.sg";
			HREmail = "3SixtyAdmin@pcc.com.sg";
			AdminEmail = "3SixtyAdmin@pcc.com.sg";
			Email_Hyperlink = "3SixtyAdmin@pcc.com.sg";
			Website_Hyperlink = "http://www.pcc.netdns.net/i360/index.jsp";
			allowDeleteFunc = true; //default not allowed
			
			/** JENTY'S PATH, PLEASE DO NOT DELETE ***/					
//			App_Path = "C:\\Program Files\\Program\\Apache Group\\resin 4.1\\webapps\\i360";
//			OOAppPath = "C:/Program Files/Program/Apache Group/resin 4.1/webapps/i360/";	
			
			//Redundant Code
			//By:Chun Pong
			//Date: 06 Jun 2008
			//Reason: Change server path from static to dynamic 
			//Old Code:
			//App_Path = "C:\\resin\\webapps\\i360";
			//OOAppPath = "C:/resin/webapps/i360/";
		}
		/******* END TESTING ENVIRONMENT ********/

		/* Reports Path Settings */
		Report_Path 			= App_Path + "\\Report";
		Report_Path_Template 	= Report_Path + "\\Template";
		inputWorkbook 			= Report_Path + "\\Template\\DevelopmentGuide Template.xls";
		Deleted_Path 			= App_Path + "\\Deleted";
		logoPath 				= App_Path + "\\Logo";
		CoachFilePath 			= App_Path + "\\CoachFilePath";
		UploadPath 				= App_Path + "\\Upload";
		OOReportPath 			= OOAppPath + "/Report/";
		OOReportTemplatePath 	= OOReportPath + "Template/";
		OOLogoPath	 			= OOAppPath + "/Logo/";	// Added slash to fix path to logo so that they appear in reports, Desmond 19 Nov 09
		/* END Reports Path Settings */	
	}
	
	
   	public String getServerName() 
	{
    	return ServerName;
	}
	
	public String getEmailHost() 
	{
    	return EmailHost;
	}

	// Added by Desmond 9 Oct, for sending email using Singnet SMTP
	public String getEmailUser() {
		return EmailUser;
	}
	
	// Added by Desmond 9 Oct, for sending email using Singnet SMTP
	public String getEmailPwd() {
		return EmailPwd;
	}

	public String getHREmail() 
	{
    	return HREmail;
	}
	
	public String getAdminEmail() 
	{
    	return AdminEmail;
	}
	
	public String getEmail_Hyperlink() 
	{
    	return Email_Hyperlink;
	}
	
	public String getWebsite_Hyperlink() 
	{
    	return Website_Hyperlink;
	}
	
	public String getReport_Path() 
	{
    	return Report_Path;
	}
	
	public String get_inputWorkbook() 
	{
		if (LangVer == 2)
			inputWorkbook = Report_Path + "\\Template\\DevelopmentGuide Template_INA.xls";

    	return inputWorkbook;
	}
	
	public String getDeleted_Path() 
	{
    	return Deleted_Path;
	}
	
	public boolean getallowDeleteFunc () 
	{
    	return allowDeleteFunc;
	}
	public String getReport_Path_Template() 
	{
    	return Report_Path_Template;
	}


	public String getLogoPath() 
	{
    	return logoPath;
	}
	
	public String getBCC_Email()
	{
		return BCC_Email;
	}
	
	public boolean getIsStandalone()
	{
		return IsStandalone;
	}
	
	public String getUploadPath() 
	{
    	return UploadPath;
	}
	
	public int getCompanySetting() 
	{
    	return CompanySetting;
	}
	
	public int getPasswordSetting() 
	{
    	return PasswordSetting;
	}

	public int getNomModule() 
	{
    	return NomModule;
	}
	
	public int getAllowBCCEmail() 
	{
    	return allowBCCEmail;
	}
	
	/******************************************** OPEN OFFICE *****************************************************/
	
	public String getOOReportPath() {
		return OOReportPath;
	}
	
	public String getOOReportTemplatePath() {
		return OOReportTemplatePath;
	}
	
	public String getOOLogoPath() {
		return OOLogoPath;
	}

	public String getCoachFilePath() {
		return CoachFilePath;
	}

	public void setCoachFilePath(String coachFilePath) {
		CoachFilePath = coachFilePath;
	}
	
	
}
