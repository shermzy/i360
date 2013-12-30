package CP_Classes;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import java.io.*;
import java.text.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.read.biff.BiffException;

import CP_Classes.EventViewer;
import CP_Classes.common.ConnectionBean;
import CP_Classes.common.ErrorLog;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voKeyBehaviour;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblAssignment;
import CP_Classes.vo.votblDRA;
import CP_Classes.vo.votblDRARES;
import CP_Classes.vo.votblSurvey;

import util.Utils;

/**
 * 
 * Change Log
 * ==========
 *
 * Date        By				Method(s)            					Change(s) 
 * ================================================================================================
 * 01/06/12   Albert		   importUser()					            Made changes to accommodate importing user with the field "Round"
 * 
 * 
 * 
 * 20/06/12   Liu Taichen      importQuestionnaire(String, int, int)    Changed the way the next comment cell is found 
 *                                                                      to cater to the fact that the comment box of the last keybehavior in 
 *                                                                      each competency could not be found using the way used in competency level survey 
 *								 
 */
/**
 * This class defines the methods needed to import different data into the database.
 * @author Unknown
 * Created Date: Unknown
 * Last Updated on: 21 Sept 2008
 * Last Updated by: Chun Pong
 * Rewritten by Chun Pong on 11 Jun 2008 to use ErrorLog Class and add Event logging using Event Viewer.
 */
public class Import {
	
	//Excel File/Workbook
	private File inputWorkbook = null;
	private WorkbookSettings ws = null;
	private Workbook workbook = null;
	//Error Logging
	private ErrorLog errLog = null;
	private Division division; //Used by importUser(), importDivision(), importDepartment(), importGroup()
	private Department department; //Used by importUser(), importDepartment(), importGroup()
	private Group group; //Used by importUser(), importGroup()
	private User user; //Used by importUser(), importTarget(), importCompetency(), importKeyBehaviour(), importDevelopmentActivities(), importDevelopmentResources, importDivision(), importDepartment(), importGroup()
	private Competency competency; //Used by importCompetency(), importKeyBehaviour(), importDevelopmentActivities(), importDevelopmentResources
	private KeyBehaviour keyBehaviour; //Used by importKeyBehaviour()
	private DevelopmentActivities developmentActivities; //Used by importDevelopmentActivities()
	private DevelopmentResources developmentResources; //Used by importDevelopmentResources()
	private SurveyResult surveyResult; //Used by importTarget()
	private AssignTarget_Rater assignTargetRater; //Used by importTarget()
	private RaterRelation relation; //Used by importRater()
	//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
	//Date: 18 Jun 2008
	//By Chun Pong
	//private DemographicEntry demographic;

	/**
	 * Creates a new instance of Import Class.
	 */
	public Import() {
	} //End of Import()

	/**
	 * Excel File Initialisation method
	 * @param sFileName - The filename of the file to be imported
	 * @throws BiffException - Error getting workbook
	 * @throws IOException 
	 * @throws FileNotFoundException
	 */
	public void initializeExcel(String sFileName) throws IOException, BiffException, FileNotFoundException {
		String sMethodName = "initializeExcel"; //For printLog()

		//Print log to console        
		printLog(sMethodName, "Begin Initialize Excel - Set Excel Workbook to " + sFileName);

		//Set Workbook
		inputWorkbook = new File(sFileName);

		//Workbook File Null Check
		if(inputWorkbook == null) {
			throw new FileNotFoundException();
		} //End of Workbook File Null Check

		//Print log to console   
		printLog(sMethodName, "Initial Workbook Settings");

		//Initial Workbook Settings
		ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));

		//Get Workbook
		workbook = Workbook.getWorkbook(inputWorkbook);

		//Print log to console   
		printLog(sMethodName, "End Initialize Excel");
	} //End initializeExcel()

	/**
	 * Import data into the database from File
	 * @param iImportType  - The Import Tyep of the Table to be checked
	 * 						 Possible Values:
	 * 					       1) Import User
	 * 					       2) Import Assignment - Target & Rater
	 * 					       3) Import Assignment - Target Only
	 * 					       4) Import Competency
	 * 						   5) Import Key Behaviour
	 * 						   6) Import Development Activities
	 * 					       7) Import Development Resources
	 * 						   8) Import Division
	 * 					       9) Import Department
	 * 					      10) Import Group/Section
	 * @param sFileName    - The file name of the Excel file containing the data
	 * @param iCompanyID   - The Company ID
	 * @param iOrgID       - The Organisation ID
	 * @param sCompanyName - The Company ID
	 * @param sOrgName     - The Organisation ID
	 * @param isAutoAdd    - Indicates if the non-exisiting objects needs to be added automatically
	 * 						 NOTE: For future use. Not Used by any import method yet
	 * @param sUserName    - The User Name of the user who invoke the method
	 */
	//Added loginUserType to method parameters list for validating whether it is SA account
	//Mark Oei 11 Mar 2010 
	public String[] importFromFile(int iImportType, String sFileName, int iCompanyID, int iOrgID,
			String sCompanyName, String sOrgName, boolean isAutoAdd, 
			String sUserName, int loginUserType) {
		//Event Viewer
		EventViewer evtViewer = new EventViewer();
		
		//Create new Error Log
		errLog = new ErrorLog();

		//Status messages - to indicate the status for Import/Delete operations
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sImportInfo = new String[2];
		String[] sDeleteInfo = null;

		String sImportType = "";
		String sMethodName = "importFromFile"; //For printLog(), logError()

		//Try-Catch
		try {
			//Initial Excel file
			initializeExcel(sFileName);

			//Get all worksheets from workbook
			Sheet[] wSheets = workbook.getSheets();

			//Print log to console
			printLog(sMethodName, "Worksheet Initialization Completed");
			//Start Import Type Switch Case
			switch (iImportType) {
			
			case 1:
				sImportType = "Import User";
				// Added loginUserType to method parameters list to check whether login user is SA
				// Mark Oei 11 Mar 2010
				sImportInfo = importUser(sFileName, iCompanyID, iOrgID, sUserName, wSheets, loginUserType);
				break;
			case 2:
				sImportType = "Import Assignment - Add Target and Rater";
				sImportInfo = importAssignment(sFileName, iCompanyID, iOrgID, sUserName, wSheets, true);
				break;
			case 3:
				sImportType = "Import Assignment - Add Target Only";
				sImportInfo = importAssignment(sFileName, iCompanyID, iOrgID, sUserName, wSheets, false);
				break;
			case 4:
				sImportType = "Import Competency";
				sImportInfo = importCompetency(sFileName, iCompanyID, iOrgID, sUserName, wSheets);
				break;
			case 5:
				sImportType = "Import Key Behaviour";
				sImportInfo = importKeyBehaviour(sFileName, iCompanyID, iOrgID, sUserName, wSheets);
				break;
			case 6:
				sImportType = "Import Development Activities";
				
				//Added additional parameter to pass loginUserType in the method importDevelopmentActivities, Sebastian 29 July 2010
				sImportInfo = importDevelopmentActivities(sFileName, iCompanyID, iOrgID, sUserName, wSheets, loginUserType);
				
				//Get Delete Status Info
				if(wSheets.length > 1){
					sDeleteInfo = deleteDevelopmentActivities(sFileName, iCompanyID, iOrgID, sUserName, wSheets);
				}
			

				//Import & Delete Status Check
				if(sImportInfo[0].startsWith("Column(s) missing"))
					sImportInfo[0] = "Import Development Activities - " + sImportInfo[0] ;
				
				if(sDeleteInfo != null){
					if(sDeleteInfo[0].startsWith("Column(s) missing"))
						sDeleteInfo[0] = "Delete Development Activities - " + sDeleteInfo[0] ;                	
	
					sImportInfo[0] += "<br>" + sDeleteInfo[0]; //Append Import & Delete Status Info
				}
				//End of Import & Delete Status Check                    
				break;
			case 7:
				
				sImportType = "Import Development Resources";
				//Added additional parameter to pass loginUserType in the method importDevelopmentResources, Sebastian 29 July 2010
				sImportInfo = importDevelopmentResources(sFileName, iCompanyID, iOrgID, sUserName, wSheets, loginUserType);

				//Get Delete Status Info
				//sDeleteInfo = deleteDevelopmentResources(sFileName, iCompanyID, iOrgID, sUserName, wSheets);

				//Import & Delete Status Check
				if(sImportInfo[0].startsWith("Column(s) missing"))
					sImportInfo[0] = "Import Development Resources - " + sImportInfo[0] ;

				//if(sDeleteInfo[0].startsWith("Column(s) missing"))
				//	sDeleteInfo[0] = "Delete Development Resources - " + sDeleteInfo[0] ;

				//sImportInfo[0] += "<br>" + sDeleteInfo[0]; //Append Import & Delete Status Info
				//End of Import & Delete Status Check
				break;
			case 8:
				sImportType = "Import Division";
				sImportInfo = importDivision(sFileName, iCompanyID, iOrgID, sUserName, wSheets);
				break;
			case 9:
				sImportType = "Import Department";
				sImportInfo = importDepartment(sFileName, iCompanyID, iOrgID, sUserName, wSheets);
				break;
			case 10:
				sImportType = "Import Group/Section";
				sImportInfo = importGroup(sFileName, iCompanyID, iOrgID, sUserName, wSheets);
				break;
				//04 Jan 2010 Qiao Li
			case 11:
				sImportType = "Import Questionnaire";
				sImportInfo = importQuestionnaire(sFileName, iCompanyID, iOrgID, sUserName, wSheets);
				break;
			case 13:
				sImportType = "Import Cluster";
				sImportInfo = importCluster(sFileName, iCompanyID, iOrgID, sUserName, wSheets);
				break;
			case 14:
				sImportType = "Import Nomination";
				sImportInfo = importNomination(sFileName, iCompanyID, iOrgID, sUserName, wSheets, loginUserType);
				break;
			default:
				//Set Import Status
				sImportInfo[0] = sImportInfo[1] = "Invalid Import Type";

			//Add Error Log
			logError(sMethodName, sImportInfo[0], isAutoAdd, iCompanyID, iOrgID);
			break;
			} //End of Import Type Switch Case

			//Print log to console
			printLog(sMethodName, sImportType + " completed");
		} catch (FileNotFoundException e) {
			//Set Import Status 
			sImportInfo[0] = "Error locating Import File. Please contact the Administrator for assistant.";
			sImportInfo[1] = "FileNotFound Exception.";
			//Add Error Log
			logError(sMethodName, sImportInfo[1], isAutoAdd, iCompanyID, iOrgID);
		} catch (BiffException e) {
			//Set Import Status 
			sImportInfo[0] = "Error accessing Import File. Please contact the Administrator for assistant.";
			sImportInfo[1] = "BiffException Exception.";
			//Add Error Log
			logError(sMethodName, sImportInfo[1], isAutoAdd, iCompanyID, iOrgID);
		} catch (IOException e) {
			//Set Import Status 
			sImportInfo[0] = "Error Reading Import File. Please contact the Administrator for assistant.";
			sImportInfo[1] = "IOException.";
			//Add Error Log
			logError(sMethodName, sImportInfo[1], isAutoAdd, iCompanyID, iOrgID);
		} finally {
			//Close Workbook
			if(workbook != null)
				workbook.close();
		} //End of Try-Catch

		//Start Save Error Log Try-Catch
		try {
			//Has Error Check
			if(errLog.hasError()) {
				//Error Log file path -> "Report\<File Name>"
				String sErrorLogFilePath = errLog.writeErrorLog(sUserName);
				//Excel File Error Check
				if(sImportInfo[0].endsWith("Please contact the Administrator for assistant.")) {
					//Write Error Log and return an error message with path of the Error Log
					sImportInfo[0] = sImportInfo[0] + 
					" Please refer to <a href='" + sErrorLogFilePath + 
					"'>Error Log</a> for more information.";
				} else {
					//Write Error Log and return an error message with path of the Error Log
					sImportInfo[0] = sImportInfo[0] + 
					" Error(s) had occurred during the import process, please refer to <a href='" +
					sErrorLogFilePath + "'>Error Log</a>";
				} //End of Excel File Error Check     
				//Print log to console
				printLog(sMethodName, "Exception while adding Event Viewer Log - " + sImportInfo[0]);
			} //End of Has Error Check
		} catch (IOException e) {
			sImportInfo[0] = sImportInfo[0] + " Error(s) during the import process and " +
			"an I/O Exception have occurred while saving the Error Log";
			//Print log to console
			printLog(sMethodName, "Exception while adding Event Viewer Log - " + sImportInfo[0]);
		} finally {
			//Event Viewer Log Try-Catch
			try {
				//Write import status to Event Viewer
				evtViewer.addRecord("Import", sMethodName, sImportInfo[1], sUserName, sCompanyName, sOrgName);
			} catch (SQLException e) {
				//Print log to console
				printLog(sMethodName, "SQL Exception while adding Event Viewer Log - " + sImportInfo[1]);
			} catch (Exception e) {
				//Print log to console
				printLog(sMethodName, "Exception while adding Event Viewer Log - " + sImportInfo[1]);
			} //End of Event Viewer Log Try-Catch
		} //End of Save Error Log Try-Catch

		//Print log to console
		printLog(sMethodName, "End " + sMethodName);

		//Return status message
		return sImportInfo;
	} //End of importFromFile()

	public String[] importNomination(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets, int loginUserType) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		
		//Invalid Data Row Status
		String[] sErrorStatus = new String[3];
		
		TreeMap<Integer, LinkedList<Integer>> userDict = new TreeMap<Integer, LinkedList<Integer>>();
		TreeMap<Integer, LinkedList<Integer>> assignDict = new TreeMap<Integer, LinkedList<Integer>>();
 		relation = new RaterRelation();
		String sSurveyName = null;
		surveyResult = new SurveyResult();
		assignTargetRater = new AssignTarget_Rater();
		//Initial validation checkers' objects
		division = new Division();
		department = new Department();
		group = new Group();
		user = new User();
		votblSurvey voSurvey = null; 
		String sMethodName = "importNomination"; //For printLog(), logError(), logDuplication

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if multiple sheets are used
		Sheet wCurrentSheet = wSheets[0];
		
		//indicate survey existence
		boolean surveyExist = true;
		
		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iRoundCount = 0; // keep track of missing round
		int iTotalDuplicate = 0; // keep track of duplicate user data
		//int iOfficeTel = 0; // keep track of missing office tel
		//int iHandphone = 0; // keep track of missing hand phone
		//int iMobileProvider = 0; // keep track of missing mobile provider
		//int iRemark = 0; // keep track of missing remark

		//added iTotalUpdatedSupervisors, To track the number of times user relation with supervisors changed, Sebastian 05 July 2010
		int iTotalUpdatedSupervisors = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;



		//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
		//Date: 18 Jun 2008
		//By Chun Pong
		//demographic = new DemographicEntry();

		//Get Cells of Critical Column
		//Cell cFKUserType360 = wCurrentSheet.findCell("FKUserType360");
		Cell cSurveyName = wCurrentSheet.findCell("SurveyName");
		Cell cTarFamilyName = wCurrentSheet.findCell("FamilyName");
		Cell cTarGivenName = wCurrentSheet.findCell("GivenName");
		Cell cFamilyName = null;		
		Cell cGivenName = null;
		Cell cEmail = null;
		Cell cRelation = null;
		if(cTarGivenName != null){
			int rCol = cTarGivenName.getColumn();
			int rRow = cTarGivenName.getRow();
			cFamilyName = wCurrentSheet.findCell("FamilyName", rCol+1, rRow, rCol+10, rRow, false);
			if(cFamilyName == null)
				cFamilyName = cTarFamilyName;
			cGivenName = wCurrentSheet.findCell("GivenName", rCol+1, rRow, rCol+10, rRow, false);
			if(cGivenName == null)
				cGivenName = cTarGivenName;
			cEmail = wCurrentSheet.findCell("Email");
			cRelation = wCurrentSheet.findCell("Rater Relationship");
		}	

		//Get Cells - Non-Critical Column
		Cell cDesignation = wCurrentSheet.findCell("Designation");
		Cell cIDNumber = wCurrentSheet.findCell("IDNumber");
		Cell cRTSpecific = wCurrentSheet.findCell("RTSpecific");
		//Cell cSupervisorLoginName = wCurrentSheet.findCell("SupervisorLoginName");
		Cell cRound = wCurrentSheet.findCell("Round");
		Cell cWave = wCurrentSheet.findCell("Wave");
		Cell cOfficeTel = wCurrentSheet.findCell("Office Contact Number");
		Cell cHandphone = wCurrentSheet.findCell("Mobile Number");
		Cell cMobileProvider = wCurrentSheet.findCell("Mobile Provider");
		Cell cRemark = wCurrentSheet.findCell("Remarks");
		Cell cDivision = wCurrentSheet.findCell("Division");
		Cell cDepartment = wCurrentSheet.findCell("Department");
		Cell cGroup_Section = wCurrentSheet.findCell("Group_Section");

		//Get Column ID of Critical Column
		//int iColFKUserType360 = -1;
		
		// posiiton of survey name
		int iColSurveyName = -1;
		int iRowSurveyName = -1;
		
		int iColFamilyName = -1;
		int iColGivenName = -1;
		int iColEmail = -1;
		int iColRelation = -1;

		//Get Column ID of Non-Critical Column
		int iColDesignation = -1;
		int iColIDNumber = -1;
		//int iColSupervisorLoginName = -1;
		int iColRTSpecific = -1;
		int iColRound = -1;
		int iColWave = -1;
		int iColOfficeTel = -1;
		int iColHandphone = -1;
		int iColMobileProvider = -1;
		int iColRemark = -1;
		int iColDivision = -1;
		int iColDepartment = -1;
		int iColGroup_Section = -1;

		//Critical Column Missing Try-Catch
		try {        	
			String sCriticalColumnMissing = ""; //To store the Column Header of the column(s) missing
			
			if(cSurveyName == null){
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "Survey name";
			} else {
				iColSurveyName = cSurveyName.getColumn()+1;
				iRowSurveyName = cSurveyName.getRow();
				sSurveyName = Utils.SQLFixer(wCurrentSheet.getCell(iColSurveyName, iRowSurveyName).getContents().trim());
				voSurvey = surveyResult.SurveyInfo(sSurveyName, iOrgID);
				// check invalid surveyName
				if(voSurvey.getSurveyID() == 0){
					surveyExist = false;
				}
			} //End of Critical Column Missing Check - SurveyName 
			//Critical Column Missing Check - FamilyName 
			if(cFamilyName == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "FamilyName";
			} else {
				iColFamilyName = cFamilyName.getColumn();
			} //End of Critical Column Missing Check - FamilyName 

			//Critical Column Missing Check - GivenName 
			if(cGivenName == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "GivenName";
			} else {
				iColGivenName = cGivenName.getColumn();
			} //End of Critical Column Missing Check - GivenName 
			
			//Critical Column Missing Check - Email 
			if(cEmail == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "Email";
			} else {
				iColEmail = cEmail.getColumn();
			} //End of Critical Column Missing Check - Email 
			
			//Critical Column Missing Check - Rater Relationship 
			if(cRelation == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "Rater Relationship";
			} else {
				iColRelation = cRelation.getColumn();
			} //End of Critical Column Missing Check - Rater Relationship 
			
			// Survey name Error check
			if(!surveyExist){
				sErrorStatus[0] = "Survey not exist";
				sErrorStatus[1] = "No such survey found in database";
				sErrorStatus[2] = "Please update survey record and reload";
				
				sOperationStatus[0] = "Survey not exist -"+ sSurveyName+".";
				sOperationStatus[1] = "";
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				return sOperationStatus;
			}

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				//Print to Console Log
				
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Get Non-Critical Column ID
			if(cRTSpecific != null)
				iColRTSpecific = cRTSpecific.getColumn();
			
			if(cDesignation != null)
				iColDesignation = cDesignation.getColumn();

			if(cIDNumber != null)
				iColIDNumber = cIDNumber.getColumn();
			
			if(cDivision != null)
				iColDivision = cDivision.getColumn();
			
			if(cDepartment != null)
				iColDepartment = cDepartment.getColumn();
			
			if(cGroup_Section != null)
				iColGroup_Section = cGroup_Section.getColumn();

			if(cRound != null)
				iColRound = cRound.getColumn();
			
			if(cWave != null)
				iColWave = cWave.getColumn();
			
			if(cOfficeTel != null)
				iColOfficeTel = cOfficeTel.getColumn();

			if(cHandphone != null)
				iColHandphone = cHandphone.getColumn();
			
			if(cMobileProvider != null)
				iColMobileProvider = cMobileProvider.getColumn();
				
			if(cRemark != null)
				iColRemark = cRemark.getColumn();
				

			//Set Start Index, Current Index & Total Number of Rows            
			iStartRowIndex = cTarFamilyName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			
			//Target Information IDs
			int iPKTarget = -1;
			String sTarFamilyName = "";
			String sTarGivenName = "";
			int sTarLoginID = -1;
			int iTarRound = 1;
			
			// max empty line margin
			int MAXEMPTYROW = 4;
			int emptyRowCount = 0;
			
			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;

				//Add/Edit Opertaion -> True => Add Operation and False => Edit Operation 
				boolean isAddOperation = true;

				//User Information IDs
				int iDivision = -1;
				int iDepartment = -1;
				int iGroup_Section = -1;
				int iFKUserType360 = -1;
				int iPKUser = -1;
				
				// keep track whether this row is target
				boolean isTarget = false;

				//check if this row of excel contains target
				String sRelation = Utils.SQLFixer(wCurrentSheet.getCell(iColRelation, iCurrentRowIndex).getContents().trim());
				if(sRelation.toLowerCase().equals("self")){
					sTarFamilyName = Utils.SQLFixer(wCurrentSheet.getCell(iColFamilyName, iCurrentRowIndex).getContents().trim());
					sTarGivenName = Utils.SQLFixer(wCurrentSheet.getCell(iColGivenName, iCurrentRowIndex).getContents().trim());
					isTarget = true;
				}
				
				//Get User Info from Excel file	- Critical Column
				String sFamilyName = Utils.SQLFixer(wCurrentSheet.getCell(iColFamilyName, iCurrentRowIndex).getContents().trim());
				String sGivenName = Utils.SQLFixer(wCurrentSheet.getCell(iColGivenName, iCurrentRowIndex).getContents().trim());
				String sEmail = Utils.SQLFixer(wCurrentSheet.getCell(iColEmail, iCurrentRowIndex).getContents().trim());
				
				//Get User Info from Excel file - Non-Critical Column	
				String sRTSpecific = "";
				String sDivision = "";
				String sDepartment = "";
				String sGroup_Section = "";	
				String sDesignation = "";
				String sIDNumber = "";
				int iWave = 1;
				String sOfficeTel = null;
				String sHandphone = null;
				String sMobileProvider = null;
				String sRemark = null;

				if(iColRTSpecific != -1)
					sRTSpecific = Utils.SQLFixer(wCurrentSheet.getCell(iColRTSpecific, iCurrentRowIndex).getContents().trim());
				
				if(iColDivision != -1)
					sDivision = Utils.SQLFixer(wCurrentSheet.getCell(iColDivision, iCurrentRowIndex).getContents().trim());
				
				if(iColDepartment != -1)
					sDepartment = Utils.SQLFixer(wCurrentSheet.getCell(iColDepartment, iCurrentRowIndex).getContents().trim());
				
				if(iColGroup_Section != -1)
					sGroup_Section = Utils.SQLFixer(wCurrentSheet.getCell(iColGroup_Section, iCurrentRowIndex).getContents().trim());
				
				if(iColDesignation != -1)
					sDesignation = Utils.SQLFixer(wCurrentSheet.getCell(iColDesignation, iCurrentRowIndex).getContents().trim());
				
				if(iColIDNumber != -1)
					sIDNumber = Utils.SQLFixer(wCurrentSheet.getCell(iColIDNumber, iCurrentRowIndex).getContents().trim());
				
				if(isTarget){
					try{
						iTarRound = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColRound,iCurrentRowIndex).getContents().trim()));
					}catch(Exception e){
							iRoundCount++;
							iTarRound = 1;
					}
				}
				
				try{
					iWave = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColWave,iCurrentRowIndex).getContents().trim()));
				}catch(Exception e){
						iWave = 1;
				}
				
				try{
					sOfficeTel = Utils.SQLFixer(wCurrentSheet.getCell(iColOfficeTel, iCurrentRowIndex).getContents().trim());
					if(sOfficeTel.equals("")){
						//iOfficeTel++;
						sOfficeTel = null;
					}
				}catch(Exception e){
					//iOfficeTel++;
				}
				try{
					sHandphone = Utils.SQLFixer(wCurrentSheet.getCell(iColHandphone,iCurrentRowIndex).getContents().trim());
					if(sHandphone.equals("")){
						//iHandphone++;
						sHandphone = null;
					}
				}catch(Exception e){
					//iHandphone++;
				}

				try{
					sMobileProvider = Utils.SQLFixer(wCurrentSheet.getCell(iColMobileProvider, iCurrentRowIndex).getContents().trim());
					if(sMobileProvider.equals("")){
						//iMobileProvider++;
						sMobileProvider = null;
					}
				}catch(Exception e){
					//iMobileProvider++;
				}

				try{
					sRemark = Utils.SQLFixer(wCurrentSheet.getCell(iColRemark, iCurrentRowIndex).getContents().trim());
					if(sRemark.equals("")){
						//iRemark++;
						sRemark = null;
					}
				}catch(Exception e){
					//iRemark++;
				}

				//Check for Empty String value Division, Department, Gender Description
				if(sDivision.equalsIgnoreCase("") || sDivision.equalsIgnoreCase("N/A")) 
					sDivision = "NA";

				if(sDepartment.equalsIgnoreCase("") || sDepartment.equalsIgnoreCase("N/A")) 
					sDepartment = "NA";                

				if(sGroup_Section.equalsIgnoreCase("") || sGroup_Section.equalsIgnoreCase("N/A"))
					sGroup_Section = "NA";

				//Check for Empty String for Critical Columns                
				/*if(sTarFamilyName.equalsIgnoreCase("")) {
					if(++emptyRowCount == MAXEMPTYROW){
						//sRowErrorStatus = "Invalid Target FamilyName - Empty String at row " + (iCurrentRowIndex + 1);
						//isInvalidDataRow = true;
						emptyRowCount = 0;
					}else{
						iCurrentRowIndex++;
						continue;
					}
					
				}else if(sTarGivenName.equalsIgnoreCase("")) {
					if(++emptyRowCount == MAXEMPTYROW){
						sRowErrorStatus = "Invalid Target GivenName - Empty String at row " + (iCurrentRowIndex + 1);
						isInvalidDataRow = true;
						emptyRowCount = 0;
						break;
					}else{
						iCurrentRowIndex++;
						continue;
					}
				} else */
				if(sFamilyName.equalsIgnoreCase("")) {
					if(++emptyRowCount == MAXEMPTYROW){
						sErrorStatus[0] = "Invalid Family Name at row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "The Family Name for this user is empty.";
						sErrorStatus[2] = "Please double check your data.";
						//sRowErrorStatus = "Invalid FamilyName - Empty String at row " + (iCurrentRowIndex + 1);
						isInvalidDataRow = true;
						emptyRowCount = 0;
						break;
					}else{
						iCurrentRowIndex++;
						continue;
					}
				} else if(sGivenName.equalsIgnoreCase("")) {
					if(++emptyRowCount == MAXEMPTYROW){
						sErrorStatus[0] = "Invalid Given Name at row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "The Given Name for this user is empty.";
						sErrorStatus[2] = "Please double check your data.";
						//sRowErrorStatus = "Invalid GivenName - Empty String at row " + (iCurrentRowIndex + 1);
						isInvalidDataRow = true;
						emptyRowCount = 0;
						break;
					}else{
						iCurrentRowIndex++;
						continue;
					}
				} else if(sEmail.equalsIgnoreCase("")) {
					if(++emptyRowCount == MAXEMPTYROW){
						sErrorStatus[0] = "Invalid Email at row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "The Email for this user is empty.";
						sErrorStatus[2] = "Please double check your data.";
						//sRowErrorStatus = "Invalid Email - Empty String at row " + (iCurrentRowIndex + 1);
						isInvalidDataRow = true;
						emptyRowCount = 0;
						break;
					}else{
						iCurrentRowIndex++;
						continue;
					}
				} else if(sRelation.equalsIgnoreCase("")) {
					if(++emptyRowCount == MAXEMPTYROW){
						sErrorStatus[0] = "Invalid Relation at row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "The Relation for this user is empty.";
						sErrorStatus[2] = "Please double check your data.";
						//sRowErrorStatus = "Invalid Rater Relationship - Empty String at row " + (iCurrentRowIndex + 1);
						isInvalidDataRow = true;
						emptyRowCount = 0;
						break;
					}else{
						iCurrentRowIndex++;
						continue;
					}
				}else{
					emptyRowCount = 0;
				}
				//End of Check for Empty String for Critical Columns and Invalid Gender Description
				
				//Label: RowDataValidation
				//This labeled code fragment validate the Division, Department, Group_Section, FKUserType360,
				//JobFunction, JobLevel, Gender, EthnicGroup and Location
				RowDataValidation: if(!isInvalidDataRow) {
					//Get Division ID
					iDivision = division.checkDivExist(sDivision, iOrgID);

					//User Division Check
					if(iDivision == 0) {
						sErrorStatus[0] = "Invalid Division at row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Division: ["+sDivision+"] does not exist in database";
						sErrorStatus[2] = "Please update Division and reload.";
						//sRowErrorStatus = "Invalid Division at row " + (iCurrentRowIndex + 1);
						isInvalidDataRow = true;
						break RowDataValidation;
					}//End of User Division Check

					//Get Department ID
					iDepartment = department.checkDeptExist(sDepartment, iDivision, iOrgID);

					//User Department Check
					if(iDepartment == 0) {
						sErrorStatus[0] = "Invalid Department at row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Department: ["+sDepartment+"] does not exist in database";
						sErrorStatus[2] = "Please update Department and reload.";
						//sRowErrorStatus = "Invalid Department at row " + (iCurrentRowIndex + 1);
						isInvalidDataRow = true;
						break RowDataValidation;
					} //End of User Department Check

					//Get Group ID
					iGroup_Section = group.checkGroupExist(sGroup_Section, iOrgID, iDepartment);

					//User Group_Section Check
					if(iGroup_Section == 0) {
						sErrorStatus[0] = "Invalid Group_Section at row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Group_Section: ["+sGroup_Section+"] does not exist in database";
						sErrorStatus[2] = "Please update Group_Section and reload.";
						//sRowErrorStatus = "Invalid Group_Section at row " + (iCurrentRowIndex + 1);
						isInvalidDataRow = true;
						break RowDataValidation;
					} //End of User Group Check

					//Get FKUserType360 ID
					iFKUserType360 = 8; // rater or target

				
				} //End of Not isInvalidDataRow Check			
				
				if(isInvalidDataRow) {
					//Add Error Log
					logError(sMethodName, sErrorStatus);
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					iTotalErrorRecords++;
				} else {
					//Check for Existing User with the same login name in the given Organisation/Company
					String sLoginName = generateLoginName(sEmail);
					iPKUser = user.checkUserExist(sLoginName, iCompanyID, iOrgID);
					if(iPKUser <= 0){
						iPKUser = user.checkPossibleSameUser(sFamilyName, sGivenName, iCompanyID, iOrgID);
					}
					///// import user //////////////////////////////////////////////////////////////////
					//User Exist Check
					if(iPKUser <= 0) {
						//Randomize Password
						Random random = new Random(System.nanoTime());
						DecimalFormat decimalFormat = new DecimalFormat("0000");
						String sPassword = sLoginName + decimalFormat.format((random.nextInt(9999) + 1));
						//Add User Check
						if(user.addRecordImport(iDepartment, iDivision, iFKUserType360,
								sFamilyName, sGivenName, sLoginName, sDesignation,
								sIDNumber, iGroup_Section, sPassword, 1,
								iCompanyID, iOrgID, sEmail, iTarRound+"", sOfficeTel, sHandphone, sMobileProvider, sRemark)) {

							//Add User Successful
							iPKUser = user.checkUserExist(sLoginName, iCompanyID, iOrgID);
							//Print to Console Log
							printLog(sMethodName, "Add User Successful (PKUser:" + iPKUser + ") at row " + 
									(iCurrentRowIndex + 1));
						} else {
							//Add User Failed
							sErrorStatus[0] = "Add User Failed at row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Unknow reason!";
							sErrorStatus[2] = "Please contact administrator.";
							//sRowErrorStatus = "Add User Record Failed at row " + (iCurrentRowIndex + 1);
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} //End of Add User Check
					} else {
						//Set Operation Type for counter increment
						isAddOperation = false;
						boolean isConsistence = checkDataInconsistence(iPKUser, iDepartment, iDivision, iFKUserType360,
								sFamilyName, sGivenName, sLoginName, sDesignation,
								sIDNumber, sGroup_Section, sEmail, iTarRound+"", 
								sOfficeTel, sHandphone, sMobileProvider, sRemark);
						if(!isConsistence){
							sErrorStatus[0] = "User info inconsistency detected at Row " + (iCurrentRowIndex + 1);
							
							//sRowErrorStatus = "User info inconsistence detected: (PKUser:" + iPKUser + " LoginName:" + sLoginName + ") at row " + 
									//(iCurrentRowIndex + 1);
							LinkedList<Integer>userRowList = userDict.get(iPKUser);
							if(userRowList == null){
								TreeMap<String, String> originData = getOriginUserData(iPKUser, sDepartment, sDivision,
										sFamilyName, sGivenName, sDesignation,
										sIDNumber, sGroup_Section, sEmail, iTarRound+"", 
										sOfficeTel, sHandphone, sMobileProvider, sRemark);
								//sRowErrorStatus += ". User information here is different from data store in database, old data have been overwritten, data being" +
								//		" overwritten are:";
								sErrorStatus[1] = "Previous database record overwritten:";
								sErrorStatus[2] = "Please check which data is valid, update record and reload if necessary";
								
								for(Map.Entry<String, String> entry : originData.entrySet()){
									//sRowErrorStatus += " "+ entry.getKey()+" ["+entry.getValue()+"];";
									sErrorStatus[1] += " "+ entry.getKey()+" ["+entry.getValue()+"];";
								}
							}
							else{
								TreeMap<String, String> originData = getOriginUserData(iPKUser, sDepartment, sDivision,
										sFamilyName, sGivenName, sDesignation,
										sIDNumber, sGroup_Section, sEmail, iTarRound+"", 
										sOfficeTel, sHandphone, sMobileProvider, sRemark);
								sErrorStatus[1] = "Same username with different";
								for(Map.Entry<String, String> entry : originData.entrySet()){
									//sRowErrorStatus += " "+ entry.getKey()+" ["+entry.getValue()+"];";
									sErrorStatus[1] += " "+ entry.getKey()+" ";
								}
								sErrorStatus[1] += " at row" + userRowList.get(userRowList.size()-1);
								sErrorStatus[2] = "Please update Division and reload.";
								//sRowErrorStatus += ". Please refer to this row for information about the same user: ";
								//sRowErrorStatus += userRowList.get(userRowList.size()-1)+", ";
							}
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						}else{
							//sRowErrorStatus = "User info duplication detected: (PKUser:" + iPKUser + " LoginName:" + sLoginName + ") at row " + 
							//		(iCurrentRowIndex + 1);
							sErrorStatus[0] = "User info duplication detected at Row "+(iCurrentRowIndex + 1);
							LinkedList<Integer>userRowList = userDict.get(iPKUser);
							if(userRowList == null){
								/*TreeMap<String, String> originData = getOriginUserData(iPKUser, iDepartment, iDivision,
										sFamilyName, sGivenName, sDesignation,
										sIDNumber, sGroup_Section, sEmail, iTarRound+"", 
										sOfficeTel, sHandphone, sMobileProvider, sRemark);*/
								//sRowErrorStatus += ". same user was already stored in database.";
								sErrorStatus[1] = "(PKUser:" + iPKUser + " LoginName:" + sLoginName + ") same data exists in database.";
								sErrorStatus[2] = "";
							}
							else{
								//sRowErrorStatus += ". Please refer to these row for information about the same user: ";
								sErrorStatus[1] = "Same user at ";
								for(Integer i : userRowList)
									//sRowErrorStatus += i+"  ";
									sErrorStatus[1] += " row "+ i;
								sErrorStatus[2] = "";
							}
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalDuplicate++;
						}
						//Edit User Check
						if(user.editRecordImport(iPKUser, iDepartment, iDivision, iFKUserType360,
								sFamilyName, sGivenName, sLoginName, sDesignation,
								sIDNumber, iGroup_Section, 1, sEmail, iTarRound+"", 
								sOfficeTel, sHandphone, sMobileProvider, sRemark)) {
							//Print to Console Log
							printLog(sMethodName, "Edit User Successful (PKUser:" + iPKUser + ") at row " + 
									(iCurrentRowIndex + 1));
						} else {
							//Edit User Failed
							//sRowErrorStatus = "Edit User Record Failed at row " + (iCurrentRowIndex + 1);
							sErrorStatus[0] = "Edit User Failed at row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Unknow reason!";
							sErrorStatus[2] = "Please contact administrator.";
							//Add Error Log
							logError(sMethodName, sErrorStatus);
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							iTotalErrorRecords++;
						}//End of Edit User Check
					} //End of user import//////////////////////////////////////////////////////////
					
					LinkedList<Integer>rowList = userDict.get(iPKUser);
					if(rowList == null)
						rowList = new LinkedList<Integer>();
					rowList.add(iCurrentRowIndex+1);
					userDict.put(iPKUser, rowList);
					
					// if is target, then store target id here
					if(isTarget){
						iPKTarget = iPKUser;
						sTarLoginID = iPKUser;
					}
					
					/// import assignment/////////////////////////////////////////////////////////////
					int iAssignmentID = -1;
					// import rater
					iAssignmentID = assignTargetRater.getAssignmentID(voSurvey.getSurveyID(), iPKUser, iPKTarget);
					int iRaterRelation = relation.getRelationHigh(sRelation);
					if(iRaterRelation == 0){
						/*sRowErrorStatus = "Invalid relation: (PKUser:" + iPKUser + " Relation:" + sRelation + ") at row " + 
								(iCurrentRowIndex + 1);
						
						sRowErrorStatus = "Add Rater Failed at row " + (iCurrentRowIndex + 1) +
						" - Survey Name:" + voSurvey.getSurveyName() +
						" [ID:" + voSurvey.getSurveyID() + "], Target:" + 
						sTarLoginID + " [ID:" + iPKTarget + "]";*/
						sErrorStatus[0] = "Invalid relation at row "+(iCurrentRowIndex + 1);
						sErrorStatus[1] = "Relation:[" + sRelation +"] does not exist in database";
						sErrorStatus[2] = "Please update relation and reload";
						//Add Error Log
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					}
					int iRaterRelationsSpecific = 0;
					if(!sRTSpecific.equalsIgnoreCase(""))
						iRaterRelationsSpecific = relation.getRelationSpecific(sRTSpecific);
					String sRaterCode = assignTargetRater.RaterCode(voSurvey.getSurveyID(), iRaterRelation, iRaterRelationsSpecific, iPKTarget);
					if(iAssignmentID <= 0 && iRaterRelation != 0) {
						//Add rater
						if(assignTargetRater.addRater(voSurvey.getSurveyID(), iPKTarget,iPKUser, iRaterRelation, iRaterRelationsSpecific, sRaterCode, iTarRound, iWave)) {
							printLog(sMethodName, "Added Rater Successfully at row " + 
									(iCurrentRowIndex + 1) + " - Survey Name:" + 
									voSurvey.getSurveyName() + " [ID:" + 
									voSurvey.getSurveyID() + "], Target:" + 
									sTarLoginID + " [ID:" + iPKTarget + "]");
						} else {
							/*sRowErrorStatus = "Add Rater Failed at row " + (iCurrentRowIndex + 1) +
							" - Survey Name:" + voSurvey.getSurveyName() +
							" [ID:" + voSurvey.getSurveyID() + "], Target:" + 
							sTarLoginID + " [ID:" + iPKTarget + "]";*/
							sErrorStatus[0] = "Add Rater Failed at row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Unknow reason!";
							sErrorStatus[2] = "Please contact administrator.";
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} //End of Add Self As Rater Check
					}else if (iRaterRelation != 0){
						// the overwritten data is different from the old data
						if(!checkAssignmentInconsistence(iAssignmentID, iRaterRelation, iRaterRelationsSpecific, iTarRound, iWave)){
	
							//sRowErrorStatus = "Assignment info overwritten: (PKUser:" + iPKUser + " LoginName:" + sLoginName + ") at row " + 
							//		(iCurrentRowIndex + 1);
							sErrorStatus[0] = "Assignment info overwritten at Row " + (iCurrentRowIndex + 1);
							LinkedList<Integer> assignRowList = assignDict.get(iAssignmentID);
							if(assignRowList == null){
								TreeMap<String, String> originData = getOriginAssignData(iAssignmentID, iRaterRelation, iRaterRelationsSpecific,
										iTarRound, iWave);
								sErrorStatus[1] = "Previous database record overwritten:(PKUser:" +iPKUser +" LoginName:" + sLoginName+")";
								sErrorStatus[2] = "Please check which data is valid, update record and reload if necessary.";
								//sRowErrorStatus += ". Assignment information here is different from data store in database, old data have been overwritten, data being" +
								//		" overwritten are:";
								for(Map.Entry<String, String> entry : originData.entrySet()){
									//sRowErrorStatus += " "+ entry.getKey()+" ["+entry.getValue()+"];";
									sErrorStatus[1] += " "+ entry.getKey()+" ["+entry.getValue()+"];";
								}
							}
							else{
								//sRowErrorStatus += ". Plase refer to this row for information about the same assignment: ";
								//sRowErrorStatus += assignRowList.get(assignRowList.size()-1)+", ";
								TreeMap<String, String> originData = getOriginAssignData(iAssignmentID, iRaterRelation, iRaterRelationsSpecific,
										iTarRound, iWave);
								sErrorStatus[1] = "Same assignment with different";
								for(Map.Entry<String, String> entry : originData.entrySet()){
									//sRowErrorStatus += " "+ entry.getKey()+" ["+entry.getValue()+"];";
									sErrorStatus[1] += " "+ entry.getKey()+" ";
								}
								sErrorStatus[1] += " at row" + assignRowList.get(assignRowList.size()-1);
								sErrorStatus[2] = "Please check which data is valid, update record and reload if necessary";
							}
							
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
							//Edit rater
							String oldRaterCode = assignTargetRater.getRaterCode(iAssignmentID);
							if(assignTargetRater.checkRaterCodeGenre(oldRaterCode, sRaterCode)){
								sRaterCode = oldRaterCode;
							}else{
								// update others' rater code
								assignTargetRater.updateOthersRaterCode(oldRaterCode, voSurvey.getSurveyID(), iPKTarget);
							}
							if(assignTargetRater.editRater(iPKTarget, iAssignmentID, iRaterRelation, iRaterRelationsSpecific, 
									sRaterCode, iTarRound, iWave)) {
								
								printLog(sMethodName, "Updated Rater Successfully at row " + 
										(iCurrentRowIndex + 1) + " - Survey Name:" + 
										voSurvey.getSurveyName() + " [ID:" + 
										voSurvey.getSurveyID() + "], Target:" + 
										sTarLoginID + " [ID:" + iPKTarget + "]");
	
							} else {
								/*sRowErrorStatus = "Updated Rater Failed at row " + (iCurrentRowIndex + 1) +
								" - Survey Name:" + voSurvey.getSurveyName() +
								" [ID:" + voSurvey.getSurveyID() + "], Target:" + 
								sTarLoginID + " [ID:" + iPKTarget + "]";*/
								sErrorStatus[0] = "Edit Rater Failed at row " + (iCurrentRowIndex + 1);
								sErrorStatus[1] = "Unknow reason!";
								sErrorStatus[2] = "Please contact administrator.";
								//Add Error Log
								//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							} 
						}
					}//End of assignment import
					
					LinkedList<Integer> rowList2 = assignDict.get(iAssignmentID);
					if(rowList2 == null)
						rowList2 = new LinkedList<Integer>();
					rowList2.add(iCurrentRowIndex+1);
					assignDict.put(iAssignmentID, rowList2);
					
				//Update Relationship - Is Invalid Data Row Check
				if(!isInvalidDataRow) {
					//Removed the If condition to handle supervisor assignment and performed later in the next while loop below, In order to properly handle changes in user relation with supervisor, Sebastian 05 July 2010
					
					//Is Add Operation Check
					if(isAddOperation) {
						iTotalAddedRecords++;
					} else {
						iTotalUpdatedRecords++;
					} //End of Is Add Operation Check
				} //End of Update Relationship - Is Invalid Data Row Check				
				
				}
				iCurrentRowIndex++;
			}//End of Data Row While-Loop
			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Nomination Unsuccessful.";
				else
					sOperationStatus[0] = "Import Nomination Completed.";

				sOperationStatus[1] = "Error(s) Encountered.";
			} else{
				if(iRoundCount == 0){
				sOperationStatus[0] = "Import Nomination Successfully.";
				sOperationStatus[1] = "OK.";  
				}else{
					sOperationStatus[1] = "Round attribute missing.";
					sOperationStatus[0] = "Import Successfully. ";
					if(iRoundCount >0)
						sOperationStatus[0]+=iRoundCount+" Round attribute missing, which have been automatically set to 1.<br>";
					/*if(iOfficeTel > 0)
						sOperationStatus[0]+=iOfficeTel+" officeTel attribute missing, which have been automatically set to NULL.<br>";
					if(iHandphone > 0)
						sOperationStatus[0]+=iHandphone+" hand phone attribute missing, which have been automatically set to NULL.<br>";
					if(iMobileProvider > 0)
						sOperationStatus[0]+=iMobileProvider+" mobile provider attribute missing, which have been automatically set to NULL.<br>";
					if(iRemark > 0)
						sOperationStatus[0]+=iRemark+" Remark attribute missing, which have been automatically set to NULL.";*/
				}
			} //End of Error Log Check


			//Added an additional Total Supervisor Assignments Edited into the sRecordsStatus, To include tracking of number of times target/raters are assigned supervisors, Sebastian 05 July 2010
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Edited:" + iTotalUpdatedRecords + ", Total Supervisor Assignments Edited:" + iTotalUpdatedSupervisors + 
			", Total Duplicated:" + iTotalDuplicatedRecords + ", Total Errors:" +  iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
			} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log" +e.getMessage();
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;
	}//End of importNomination()
	
	/**
	 * Import user information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	// Add loginUserType to method parameters list
	// Mark Oei 11 Mar 2010
	public String[] importUser(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets, int loginUserType) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		
		//Invalid Data Row Status
		String[] sErrorStatus = new String[3];
		
		String sMethodName = "importUser"; //For printLog(), logError(), logDuplication

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if multiple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iRound = 0; // keep track of missing round
		int iOfficeTel = 0; // keep track of missing office tel
		int iHandphone = 0; // keep track of missing hand phone
		int iMobileProvider = 0; // keep track of missing mobile provider
		int iRemark = 0; // keep track of missing remark

		//added iTotalUpdatedSupervisors, To track the number of times user relation with supervisors changed, Sebastian 05 July 2010
		int iTotalUpdatedSupervisors = 0;

		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Initial validation checkers' objects
		division = new Division();
		department = new Department();
		group = new Group();
		user = new User();

		//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
		//Date: 18 Jun 2008
		//By Chun Pong
		//demographic = new DemographicEntry();

		//Get Cells of Critical Column
		Cell cFKUserType360 = wCurrentSheet.findCell("FKUserType360");
		Cell cFamilyName = wCurrentSheet.findCell("FamilyName");
		Cell cGivenName = wCurrentSheet.findCell("GivenName");
		Cell cLoginName = wCurrentSheet.findCell("LoginName");
		Cell cFKDivision = wCurrentSheet.findCell("FKDivision");
		Cell cFKDepartment = wCurrentSheet.findCell("FKDepartment");
		Cell cGroup_Section = wCurrentSheet.findCell("Group_Section");
		Cell cIsEnabled = wCurrentSheet.findCell("IsEnabled");

		//Get Cells - Non-Critical Column
		Cell cDesignation = wCurrentSheet.findCell("Designation");
		Cell cIDNumber = wCurrentSheet.findCell("IDNumber");
		Cell cEmail = wCurrentSheet.findCell("Email");
		Cell cSupervisorLoginName = wCurrentSheet.findCell("SupervisorLoginName");
		Cell cRound = wCurrentSheet.findCell("Round");
		Cell cOfficeTel = wCurrentSheet.findCell("OfficeTel");
		Cell cHandphone = wCurrentSheet.findCell("Handphone");
		Cell cMobileProvider = wCurrentSheet.findCell("Mobile_Provider");
		Cell cRemark = wCurrentSheet.findCell("Remark");
		//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
		//Date: 18 Jun 2008
		//By Chun Pong
		//Cell cGenderDesc = wCurrentSheet.findCell("GenderDesc"); //<- Critical Column
		//Cell cJobFunction = wCurrentSheet.findCell("JobFunctionName");
		//Cell cJobLevel = wCurrentSheet.findCell("JobLevelName");
		//Cell cEthnicGroup = wCurrentSheet.findCell("EthnicDesc");
		//Cell cLocation = wCurrentSheet.findCell("LocationName");

		//Get Column ID of Critical Column
		int iColFKUserType360 = -1;
		int iColFamilyName = -1;
		int iColGivenName = -1;
		int iColLoginName = -1;
		int iColFKDivision = -1;
		int iColFKDepartment = -1;
		int iColGroup_Section = -1;
		int iColIsEnabled = -1;

		//Get Column ID of Non-Critical Column
		int iColDesignation = -1;
		int iColIDNumber = -1;
		int iColEmail = -1;
		int iColSupervisorLoginName = -1;
		int iColRound = -1;
		int iColOfficeTel = -1;
		int iColHandphone = -1;
		int iColMobileProvider = -1;
		int iColRemark = -1;
		//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
		//Date: 18 Jun 2008
		//By Chun Pong
		//int iColGenderDesc = -1;
		//int iColJobFunction = -1;
		//int iColJobLevel = -1;
		//int iColEthnicGroup = -1;
		//int iColLocation = -1;

		//Critical Column Missing Try-Catch
		try {        	
			String sCriticalColumnMissing = ""; //To store the Column Header of the column(s) missing

			//Critical Column Missing Check - FKUserType360 
			if(cFKUserType360 == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "FKUserType360";
			} else {
				iColFKUserType360 = cFKUserType360.getColumn();
			} //End of Critical Column Missing Check - FKUserType360 

			//Critical Column Missing Check - FamilyName 
			if(cFamilyName == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "FamilyName";
			} else {
				iColFamilyName = cFamilyName.getColumn();
			} //End of Critical Column Missing Check - FamilyName 

			//Critical Column Missing Check - GivenName 
			if(cGivenName == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "GivenName";
			} else {
				iColGivenName = cGivenName.getColumn();
			} //End of Critical Column Missing Check - GivenName 

			//Critical Column Missing Check - LoginName 
			if(cLoginName == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "LoginName";
			} else {
				iColLoginName = cLoginName.getColumn();
			} //End of Critical Column Missing Check - LoginName 

			//Critical Column Missing Check - FKDivision 
			if(cFKDivision == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "FKDivision";
			} else {
				iColFKDivision = cFKDivision.getColumn();
			} //End of Critical Column Missing Check - FKDivision 

			//Critical Column Missing Check - FKDepartment 
			if(cFKDepartment == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "FKDepartment";
			} else {
				iColFKDepartment = cFKDepartment.getColumn();
			} //End of Critical Column Missing Check - FKDepartment 

			//Critical Column Missing Check - Group_Section 
			if(cGroup_Section == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "Group_Section";
			} else {
				iColGroup_Section = cGroup_Section.getColumn();
			} //End of Critical Column Missing Check - Group_Section 

			//Critical Column Missing Check - IsEnabled 
			if(cIsEnabled == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "IsEnabled";
			} else {
				iColIsEnabled = cIsEnabled.getColumn();
			} //End of Critical Column Missing Check - IsEnabled 

			//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
			//Date: 18 Jun 2008
			//By Chun Pong
			//Critical Column Missing Check - GenderDec 
			//if(cGenderDesc == null) {
			//	//Append Missing Critical Column
			//	if(!sCriticalColumnMissing.equals(""))
			//		sCriticalColumnMissing += ", ";
			//	sCriticalColumnMissing= "GenderDesc";
			//} else {
			//	iColGenderDesc = cGenderDesc.getColumn();
			//} //End of Critical Column Missing Check - GenderDec 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Get Non-Critical Column ID
			if(cDesignation != null)
				iColDesignation = cDesignation.getColumn();

			if(cIDNumber != null)
				iColIDNumber = cIDNumber.getColumn();

			if(cEmail != null)
				iColEmail = cEmail.getColumn();

			if(cSupervisorLoginName != null) 
				iColSupervisorLoginName = cSupervisorLoginName.getColumn();            
			
			if(cRound != null)
				iColRound = cRound.getColumn();
			
			if(cOfficeTel != null)
				iColOfficeTel = cOfficeTel.getColumn();
			
			if(cHandphone != null)
				iColHandphone = cHandphone.getColumn();
			
			if(cMobileProvider != null)
				iColMobileProvider = cMobileProvider.getColumn();
				
			if(cRemark != null)
				iColRemark = cRemark.getColumn();
				
			//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
			//Date: 18 Jun 2008
			//By Chun Pong
			//if(cJobFunction != null)
			//	iColJobFunction = cJobFunction.getColumn();

			//if(cJobLevel != null)
			//	iColJobLevel = cJobLevel.getColumn();

			//if(cEthnicGroup != null)
			//	iColEthnicGroup = cEthnicGroup.getColumn();

			//if(cLocation != null)
			//	iColLocation = cLocation.getColumn();
			//End of Get Non-Critical Column ID

			//Set Start Index, Current Index & Total Number of Rows            
			iStartRowIndex = cFKUserType360.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				String sRowErrorStatus = "";
				//Add/Edit Opertaion -> True => Add Operation and False => Edit Operation 
				boolean isAddOperation = true;

				//User Information IDs
				int iDivision = -1;
				int iDepartment = -1;
				int iGroup_Section = -1;
				int iFKUserType360 = -1;
				int iPKUser = -1;
				//int iSupervisor = -1;
				//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
				//Date: 18 Jun 2008
				//By Chun Pong
				//int iJobFunction = -1;
				//int iJobLevel = -1;
				//int iGenderDesc = -1;
				//int iEthnicGroup = -1;
				//int iLocation = -1;

				//Get User Info from Excel file	- Critical Column			
				String sFKUserType360 = Utils.SQLFixer(wCurrentSheet.getCell(iColFKUserType360, iCurrentRowIndex).getContents().trim());
				String sFamilyName = Utils.SQLFixer(wCurrentSheet.getCell(iColFamilyName, iCurrentRowIndex).getContents().trim());
				String sGivenName = Utils.SQLFixer(wCurrentSheet.getCell(iColGivenName, iCurrentRowIndex).getContents().trim());
				String sLoginName = Utils.SQLFixer(wCurrentSheet.getCell(iColLoginName, iCurrentRowIndex).getContents().trim());
				String sFKDivision = Utils.SQLFixer(wCurrentSheet.getCell(iColFKDivision, iCurrentRowIndex).getContents().trim());
				String sFKDepartment = Utils.SQLFixer(wCurrentSheet.getCell(iColFKDepartment, iCurrentRowIndex).getContents().trim());
				String sGroup_Section = Utils.SQLFixer(wCurrentSheet.getCell(iColGroup_Section, iCurrentRowIndex).getContents().trim());
				String sIsEnabled = wCurrentSheet.getCell(iColIsEnabled, iCurrentRowIndex).getContents();

				//Get User Info from Excel file - Non-Critical Column		
				String sDesignation = Utils.SQLFixer(wCurrentSheet.getCell(iColDesignation, iCurrentRowIndex).getContents().trim());
				String sIDNumber = Utils.SQLFixer(wCurrentSheet.getCell(iColIDNumber, iCurrentRowIndex).getContents().trim());
				String sEmail = Utils.SQLFixer(wCurrentSheet.getCell(iColEmail, iCurrentRowIndex).getContents().trim());
				String sRound = "1";
				String sOfficeTel = null;
				String sHandphone = null;
				String sMobileProvider = null;
				String sRemark = null;

				try{
					sRound = Utils.SQLFixer(wCurrentSheet.getCell(iColRound,iCurrentRowIndex).getContents().trim());
					Integer.parseInt(sRound);
				}catch(Exception e){
						iRound++;
						sRound = "1";
				}
				try{
					sOfficeTel = Utils.SQLFixer(wCurrentSheet.getCell(iColOfficeTel, iCurrentRowIndex).getContents().trim());
					if(sOfficeTel.equals("")){
						iOfficeTel++;
						sOfficeTel = null;
					}
				}catch(Exception e){
					iOfficeTel++;
				}
				try{
					sHandphone = Utils.SQLFixer(wCurrentSheet.getCell(iColHandphone,iCurrentRowIndex).getContents().trim());
					if(sHandphone.equals("")){
						iHandphone++;
						sHandphone = null;
					}
				}catch(Exception e){
					iHandphone++;
				}

				try{
					sMobileProvider = Utils.SQLFixer(wCurrentSheet.getCell(iColMobileProvider, iCurrentRowIndex).getContents().trim());
					if(sMobileProvider.equals("")){
						iMobileProvider++;
						sMobileProvider = null;
					}
				}catch(Exception e){
					iMobileProvider++;
				}

				try{
					sRemark = Utils.SQLFixer(wCurrentSheet.getCell(iColRemark, iCurrentRowIndex).getContents().trim());
					if(sRemark.equals("")){
						iRemark++;
						sRemark = null;
					}
				}catch(Exception e){
					iRemark++;
				}

				//Variable sSupervisorLoginName removed and performed in the next while loop below, In order to properly handle changes in user relation with supervisor, Sebastian 05 July 2010
				
				//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
				//Date: 18 Jun 2008
				//By Chun Pong
				//String sGenderDesc = Utils.SQLFixer(wCurrentSheet.getCell(iColGenderDesc, iCurrentRowIndex).getContents().trim());
				//String sJobFunction = Utils.SQLFixer(wCurrentSheet.getCell(iColJobFunction , iCurrentRowIndex).getContents().trim());
				//String sJobLevel = Utils.SQLFixer(wCurrentSheet.getCell(iColJobLevel, iCurrentRowIndex).getContents().trim());
				//String sEthnicGroup = Utils.SQLFixer(wCurrentSheet.getCell(iColEthnicGroup, iCurrentRowIndex).getContents().trim());
				//String sLocation = Utils.SQLFixer(wCurrentSheet.getCell(iColLocation, iCurrentRowIndex).getContents().trim());

				//Check for Empty String value Division, Department, Gender Description
				if(sFKDivision.equalsIgnoreCase("") || sFKDivision.equalsIgnoreCase("N/A")) 
					sFKDivision = "NA";

				if(sFKDepartment.equalsIgnoreCase("") || sFKDepartment.equalsIgnoreCase("N/A")) 
					sFKDepartment = "NA";                

				//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
				//Date: 18 Jun 2008
				//By Chun Pong
				//if(sGenderDesc.equalsIgnoreCase("") || sGenderDesc.equalsIgnoreCase("N/A"))					
				//	sGenderDesc = "NA";	
				//End of Check for Empty String value Division, Department, Gender Description 

				//Check for Empty String for Critical Columns                
				if(sFKUserType360.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid FKUserType360 - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid FKUserType360 at Row" + (iCurrentRowIndex + 1);
					sErrorStatus[1] = "Emtpty String for FKUserType360";
					sErrorStatus[2] = "Please update the excel file and reload";
					isInvalidDataRow = true;
				} else if(sFamilyName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid FamilyName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid FamilyName at Row" + (iCurrentRowIndex + 1);
					sErrorStatus[1] = "Emtpty String for FamilyName";
					sErrorStatus[2] = "Please update the excel file and reload";
					isInvalidDataRow = true;
				} else if(sGivenName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid GivenName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid GivenName at Row" + (iCurrentRowIndex + 1);
					sErrorStatus[1] = "Emtpty String for GivenName";
					sErrorStatus[2] = "Please update the excel file and reload";
					isInvalidDataRow = true;
				} else if(sLoginName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid LoginName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid LoginName at Row" + (iCurrentRowIndex + 1);
					sErrorStatus[1] = "Emtpty String for LoginName";
					sErrorStatus[2] = "Please update the excel file and reload";
					isInvalidDataRow = true;
				} else if(sFKDepartment.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid FKDepartment - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid FKDepartment at Row" + (iCurrentRowIndex + 1);
					sErrorStatus[1] = "Emtpty String for FKDepartment";
					sErrorStatus[2] = "Please update the excel file and reload";
					isInvalidDataRow = true;
				} else if(sFKDivision.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid FKDivision - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid FKDivision at Row" + (iCurrentRowIndex + 1);
					sErrorStatus[1] = "Emtpty String for FKDivision";
					sErrorStatus[2] = "Please update the excel file and reload";
					isInvalidDataRow = true;
				} else if(sGroup_Section.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid Group_Section - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Group_Section at Row" + (iCurrentRowIndex + 1);
					sErrorStatus[1] = "Emtpty String for Group_Section";
					sErrorStatus[2] = "Please update the excel file and reload";
					isInvalidDataRow = true;
				} else if(sIsEnabled.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid IsEnabled - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid isEnabled at Row" + (iCurrentRowIndex + 1);
					sErrorStatus[1] = "Emtpty String for isEnabled";
					sErrorStatus[2] = "Please update the excel file and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns and Invalid Gender Description

				//Label: RowDataValidation
				//This labeled code fragment validate the Division, Department, Group_Section, FKUserType360,
				//JobFunction, JobLevel, Gender, EthnicGroup and Location
				RowDataValidation: if(!isInvalidDataRow) {
					//Get Division ID
					iDivision = division.checkDivExist(sFKDivision, iOrgID);

					//User Division Check
					if(iDivision == 0) {
						//sRowErrorStatus = "Invalid Division at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Division at Row" + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Division: ["+sFKDivision+"] not exist in database";
						sErrorStatus[2] = "Please update the Division and reload";
						isInvalidDataRow = true;
						break RowDataValidation;
					}//End of User Division Check

					//Get Department ID
					iDepartment = department.checkDeptExist(sFKDepartment, iDivision, iOrgID);

					//User Department Check
					if(iDepartment == 0) {
						//sRowErrorStatus = "Invalid Department at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Department at Row" + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Department: ["+sFKDepartment+"] not exist in database";
						sErrorStatus[2] = "Please update Department and reload";
						isInvalidDataRow = true;
						break RowDataValidation;
					} //End of User Department Check

					//Get Group ID
					iGroup_Section = group.checkGroupExist(sGroup_Section, iOrgID, iDepartment);

					//User Group_Section Check
					if(iGroup_Section == 0) {
						//sRowErrorStatus = "Invalid Group_Section at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Group_Section at Row" + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Group_Section: ["+sGroup_Section+"] not exist in database";
						sErrorStatus[2] = "Please update Group_Section and reload";
						isInvalidDataRow = true;
						break RowDataValidation;
					} //End of User Group Check

					//Get FKUserType360 ID
					//Parameter for getPKUserType() -> String UserTypeName, int ApplicationType
					//Possible Values for ApplicationType: 1 - cProfiler and 2 - 3-Sixty Profiler
					iFKUserType360 = user.getPKUserType(sFKUserType360, 2);

					//FKUserType360 Check
					//Added check to prevent Administrator from importing Administrator account and allow only 
					//SA to import an Admin account but not another SA account and improve error messages to be displayed
					//Mark Oei 11 Mar 2010
					if (iFKUserType360 == 1){
						//sRowErrorStatus = "Row " + (iCurrentRowIndex + 1) + " : A SA or Administrator is not allowed to import a SA account";
						sErrorStatus[0] = "Import User Failed at" + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "A SA or Administrator is not allowed to import a SA account";
						sErrorStatus[2] = "";
						isInvalidDataRow = true;
						break RowDataValidation;
					} else if (iFKUserType360 == 0){
						//sRowErrorStatus = "Typing errors found at row " + (iCurrentRowIndex + 1);
						//sRowErrorStatus += " - Please check against these standard spellings - Data Entry Personnel or Participant (rater or target)";
						
						isInvalidDataRow = true;
						break RowDataValidation;
					} else if ((!(loginUserType == 1) && iFKUserType360 == 6)){
						//sRowErrorStatus = "Row " + (iCurrentRowIndex + 1) + " : An Administrator is not allowed to import another Administrator account";
						sErrorStatus[0] = "Import User Failed at" + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "A Administrator is not allowed to import another Administrator account";
						sErrorStatus[2] = "";
						isInvalidDataRow = true;
						break RowDataValidation;
					} //End of FKUserType360 Check

					//Get Is Enabled Parameter Check
					if(!(sIsEnabled.equals("0") || sIsEnabled.equals("1"))) {
						//sRowErrorStatus = "Invalid IsEnabled at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid IsEnabled at row" + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "IsEnabled value can only be 0 and 1";
						sErrorStatus[2] = "";
						isInvalidDataRow = true;
						break RowDataValidation;
					} //End of Is Enabled Parameter Check

					//Removed the If condition to check supervisor login and performed in the next while loop below, In order to properly handle changes in user relation with supervisor, Sebastian 05 July 2010
					

					//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
					//Date: 18 Jun 2008
					//By Chun Pong
					//Empty Job Function String Check
					//if(!sJobFunction.equals("")) {
					//	//Get Job Function ID
					//	iJobFunction = demographic.getPKJobFunction(sJobFunction, iOrgID);
					//	
					//	//User Job Function Check
					//	if(iJobFunction == 0) {
					//		sRowErrorStatus = "Invalid JobFunction at row " + (iCurrentRowIndex + 1);
					//		isInvalidDataRow = true;
					//		break RowDataValidation;
					//	} //End of User Job Function Check
					//} //End of Empty Job Function String Check
					//	
					//Empty Job Level String Check
					//if(!sJobLevel.equals("")) {
					//	iJobLevel = demographic.getPKJobLevel(sJobLevel, iOrgID);
					//	
					//	//User Job Level Check
					//	if(iJobLevel == 0) {
					//		sRowErrorStatus = "Invalid JobLevel at row " + (iCurrentRowIndex + 1);
					//		isInvalidDataRow = true;
					//		break RowDataValidation;
					//	} //End of User Job Level Check
					//} //Empty Job Level String Check
					//					
					//Empty Gender Description String Check
					//if(!sGenderDesc.equals("")) {
					//	iGenderDesc = demographic.getPKGender(sGenderDesc);
					//	
					//	//User Gender Description Check
					//	if(iGenderDesc == 0) {
					//		sRowErrorStatus = "Invalid GenderDesc at row " + (iCurrentRowIndex + 1);
					//		isInvalidDataRow = true;
					//		break RowDataValidation;
					//	} //End of User Gender Description Check
					//} //Empty Gender Description String Check
					//
					//Empty Ethnic Group String Check
					//if(!sEthnicGroup.equals("")) {
					//	iEthnicGroup = demographic.getPKEthnic(sEthnicGroup, iOrgID);
					//	
					//	//User Ethnic Group Check
					//	if(iEthnicGroup == 0) {
					//		sRowErrorStatus = "Invalid EthnicGroup at row " + (iCurrentRowIndex + 1);
					//		isInvalidDataRow = true;
					//		break RowDataValidation;
					//	} //End of User Ethnic Group Check
					//} //Empty Ethnic Group String Check
					//
					//Empty Location String Check
					//if(!sLocation.equals("")) {
					//	iLocation = demographic.getPKLocation(sLocation, iOrgID);
					//	
					//	//User Location Check
					//	if(iLocation == 0) {
					//		sRowErrorStatus = "Invalid Location at row " + (iCurrentRowIndex + 1);
					//		isInvalidDataRow = true;
					//		break RowDataValidation;
					//	} //End of User Location Check
					//} //Empty Location String Check
				} //End of Not isInvalidDataRow Check			

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Check for Existing User with the same login name in the given Organisation/Company
					iPKUser = user.checkUserExist(sLoginName, iCompanyID, iOrgID);

					//User Exist Check
					if(iPKUser <= 0) {
						//Randomize Password
						Random random = new Random(System.nanoTime());
						DecimalFormat decimalFormat = new DecimalFormat("0000");
						String sPassword = sLoginName + decimalFormat.format((random.nextInt(9999) + 1));
						//Add User Check
						if(user.addRecordImport(iDepartment, iDivision, iFKUserType360,
								sFamilyName, sGivenName, sLoginName, sDesignation,
								sIDNumber, iGroup_Section, sPassword, Integer.parseInt(sIsEnabled),
								iCompanyID, iOrgID, sEmail, sRound, sOfficeTel, sHandphone, sMobileProvider, sRemark)) {

							//Add User Successful
							iPKUser = user.checkUserExist(sLoginName, iCompanyID, iOrgID);
							//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
							//Date: 18 Jun 2008
							//By Chun Pong
							//Add Demographic Data
							//if(demographic.InsertRecord(iPKUser, 0, iEthnicGroup, iGenderDesc, iJobFunction,
							//	   						 iJobLevel, iLocation)) {
							//	//Add Dempgraphic Data Successful
							//} else {
							//	//Add Dempgraphic Data Failed
							//} //End of Add Demographic Data

							//Print to Console Log
							printLog(sMethodName, "Add User Successful (PKUser:" + iPKUser + ") at row " + 
									(iCurrentRowIndex + 1));
						} else {
							//Add User Failed
							//sRowErrorStatus = "Add User Record Failed at row " + (iCurrentRowIndex + 1);
							sErrorStatus[0] = "Add User Failed at" + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Unknown reason";
							sErrorStatus[2] = "Please contract Administrator";
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} //End of Add User Check
					} else {
						//Set Operation Type for counter increment
						isAddOperation = false;

						//Edit User Check
						if(user.editRecordImport(iPKUser, iDepartment, iDivision, iFKUserType360,
								sFamilyName, sGivenName, sLoginName, sDesignation,
								sIDNumber, iGroup_Section, Integer.parseInt(sIsEnabled), sEmail, sRound, 
								sOfficeTel, sHandphone, sMobileProvider, sRemark)) {
							//Demographic Records are not essential. Commented out as instructed by Ms Rosalind
							//Date: 18 Jun 2008
							//By Chun Pong
							//Update Demographic Data
							//if(demographic.pdateRecord(iPKUser, 0, iEthnicGroup, iGenderDesc, iJobFunction,
							//							 iJobLevel, iLocation)) {
							//	//Update Dempgraphic Data Successful
							//} else {
							//	//Update Dempgraphic Data Failed
							//} //End of Update Demographic Data
							//Print to Console Log
							printLog(sMethodName, "Edit User Successful (PKUser:" + iPKUser + ") at row " + 
									(iCurrentRowIndex + 1));
						} else {
							//Edit User Failed
							//sRowErrorStatus = "Edit User Record Failed at row " + (iCurrentRowIndex + 1);
							sErrorStatus[0] = "Edit User Record Failed at" + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Unknow reason";
							sErrorStatus[2] = "Please contact Administrator";
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						}//End of Edit User Check
					} //End of User Exist Check
				} //End of Invalid Data Row Check

				//Update Relationship - Is Invalid Data Row Check
				if(!isInvalidDataRow) {
					//Removed the If condition to handle supervisor assignment and performed later in the next while loop below, In order to properly handle changes in user relation with supervisor, Sebastian 05 July 2010
					
					//Is Add Operation Check
					if(isAddOperation) {
						iTotalAddedRecords++;
					} else {
						iTotalUpdatedRecords++;
					} //End of Is Add Operation Check
				} //End of Update Relationship - Is Invalid Data Row Check				
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Added an additional while loop, To perform assignment of supervisors from the excel file imported, Sebastian 05 July 2010
			iCurrentRowIndex = 1; //reset to 1 to view from the beginning row entry of the worksheet again

			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status

				int iPKUser = -1;
				int iSupervisor = -1;

				//Get User Info from Excel file	- Critical Column
				String sLoginName = Utils.SQLFixer(wCurrentSheet.getCell(iColLoginName, iCurrentRowIndex).getContents().trim());

				//Get User Info from Excel file - Non-Critical Column		
				String sSupervisorLoginName = Utils.SQLFixer(wCurrentSheet.getCell(iColSupervisorLoginName, iCurrentRowIndex).getContents().trim());


				//Empty Supervisor String Check
				if(!sSupervisorLoginName.equals("")) {
					iSupervisor = user.checkUserExist(sSupervisorLoginName, iCompanyID, iOrgID);

					//User Supervisor Check
					if(iSupervisor == 0) {
						//sRowErrorStatus = "Invalid Supervisor(" + sSupervisorLoginName + ") " + "at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Supervisor at Row" + (iCurrentRowIndex + 1);
						sErrorStatus[1] = sSupervisorLoginName + " is invalid Supervisor";
						sErrorStatus[2] = "Please update supervisor and reload";
						isInvalidDataRow = true;
					} //End of User Supervisor Check
				} //Empty Supervisor String Check		

				//Return the primary key of the login user
				iPKUser = user.checkUserExist(sLoginName, iCompanyID, iOrgID);
				
				if(isInvalidDataRow) {
					//Add Error Log
					logError(sMethodName, sErrorStatus);
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					iTotalErrorRecords++;
					
					//Supervisor assigned cannot be found, set relation to without supervisor or revert to previous relation
					user.updateNoRelation(iPKUser, true);
					//Print to Console Log
					printLog(sMethodName, "Update No Relation - PKUser:" + iPKUser);
				} 
				//Update Relationship - Is Invalid Data Row Check
				else if(!isInvalidDataRow) {

					//Supervisor Check
					if(iSupervisor > 0) {
						//User has supervisor
						user.updateRelation(iPKUser, iSupervisor);
						//Print to Console Log
						printLog(sMethodName, "Update Relation - PKUser:" + iPKUser + ", Supervisor:" + iSupervisor);
					} else {
						user.updateNoRelation(iPKUser, false);
						//Print to Console Log
						printLog(sMethodName, "Update No Relation - PKUser:" + iPKUser);
					} //End of Supservisor Check		

					//track the number of supervisors assignment being updated
					iTotalUpdatedSupervisors++;
				} //End of Update Relationship - Is Invalid Data Row Check				
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import User Unsuccessful.";
				else
					sOperationStatus[0] = "Import User Completed.";

				sOperationStatus[1] = "Error(s) Encountered.";
			} else{
				if(iRound == 0){
				sOperationStatus[0] = "Import User Successfully.";
				sOperationStatus[1] = "OK.";  
				}else{
					sOperationStatus[1] = "Round attribute missing.";
					sOperationStatus[0] = "Import Successfully. ";
					if(iRound >0)
						sOperationStatus[0]+=iRound+" Round attribute missing, which have been automatically set to 1.<br>";
					if(iOfficeTel > 0)
						sOperationStatus[0]+=iOfficeTel+" officeTel attribute missing, which have been automatically set to NULL.<br>";
					if(iHandphone > 0)
						sOperationStatus[0]+=iHandphone+" hand phone attribute missing, which have been automatically set to NULL.<br>";
					if(iMobileProvider > 0)
						sOperationStatus[0]+=iMobileProvider+" mobile provider attribute missing, which have been automatically set to NULL.<br>";
					if(iRemark > 0)
						sOperationStatus[0]+=iRemark+" Remark attribute missing, which have been automatically set to NULL.";
				}
			} //End of Error Log Check

			//Added an additional Total Supervisor Assignments Edited into the sRecordsStatus, To include tracking of number of times target/raters are assigned supervisors, Sebastian 05 July 2010
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Edited:" + iTotalUpdatedRecords + ", Total Supervisor Assignments Edited:" + iTotalUpdatedSupervisors + 
			", Total Duplicated:" + iTotalDuplicatedRecords + ", Total Errors:" +  iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;
	}//End of importUser()

	/**
	 * Import user information into the database from File
	 * @param sFileName       	      - The file name of the Excel file containing the date
	 * @param iCompanyID      	      - The Company ID
	 * @param iOrgID          	      - The Organisation ID
	 * @param wSheets                 - Contains the worksheets of the workbook
	 * @param isAssignmentTargetRater - True indicate Import Assignment Target & Rater operation, 
	 *                                  False indicates Import Assignment Target Only operation
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] importAssignment(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets, boolean isAssignmentTargetRater) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];   
		
		//Invalid Data Row Status
		String[] sErrorStatus = new String[3];
		
		String sMethodName = "importAssignment"; //For printLog(), logError()

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Get Cells of Critical Column
		Cell cSurveyName = wCurrentSheet.findCell("SurveyName");
		Cell cRTRelation = wCurrentSheet.findCell("RTRelation");
		Cell cRTSpecific = wCurrentSheet.findCell("RTSpecific");
		Cell cRaterLoginID = wCurrentSheet.findCell("RaterLoginID");
		Cell cTargetLoginID = wCurrentSheet.findCell("TargetLoginID");
		

		String sCriticalColumnMissing = "";

		//Critical Column Missing Check - SurveyName 
		if(cSurveyName == null) {
			//Append Missing Critical Column
			sCriticalColumnMissing = "SurveyName";
		} //End of Critical Column Missing Check - SurveyName 

		//Critical Column Missing Check - RTRelation 
		if(isAssignmentTargetRater && cRTRelation == null) {
			//Append Missing Critical Column
			if(!sCriticalColumnMissing.equals("")) {
				sCriticalColumnMissing += ", ";
			}
			sCriticalColumnMissing = "RTRelation";
		} //End of Critical Column Missing Check - RTRelation 


		//Critical Column Missing Check - RTSpecific 
		if(isAssignmentTargetRater && cRTSpecific == null) {
			//Append Missing Critical Column
			if(!sCriticalColumnMissing.equals("")) {
				sCriticalColumnMissing += ", ";
			}
			sCriticalColumnMissing = "RTSpecific";
		} //End of Critical Column Missing Check - RTSpecific 

		//Critical Column Missing Check - RaterLoginID 
		if(isAssignmentTargetRater && cRaterLoginID == null) {
			//Append Missing Critical Column
			if(!sCriticalColumnMissing.equals("")) {
				sCriticalColumnMissing += ", ";
			}
			sCriticalColumnMissing = "RaterLoginID";
		} //End of Critical Column Missing Check - RaterLoginID 


		//Critical Column Missing Check - TargetLoginID 
		if(cTargetLoginID == null) {
			//Append Missing Critical Column
			if(!sCriticalColumnMissing.equals("")) {
				sCriticalColumnMissing += ", ";
			}
			sCriticalColumnMissing = "TargetLoginID";
		} //End of Critical Column Missing Check - TargetLoginID 

		//Critical Column Missing Error Check
		if(!sCriticalColumnMissing.equals("")) {
			///Write Error Log and return an error message with path of the Error Log
			sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
			sOperationStatus[1] = "One or more Critical Column is missing";
			//Print to Console Log
			//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
			//Return status messages
			sErrorStatus[0] = "Critial Column missing";
			sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
			sErrorStatus[2] = "Please update the excel file and reload";
			logError(sMethodName, sErrorStatus);
			return sOperationStatus;
		} //End of Critical Column Missing Error Check

		if(isAssignmentTargetRater) {
			//Get Import Assignment - Rater Status Info
			sOperationStatus = importRater(sFileName,iCompanyID,iOrgID,sUserName,wSheets[0]);

		}else{
			sOperationStatus = importTarget(sFileName,iCompanyID,iOrgID,sUserName,wSheets[0], isAssignmentTargetRater);
		}
		return sOperationStatus;    	
	} //End of importAssignment

	/**
	 * Import Target of an assignmentinto the database from File
	 * @param sFileName  	          - The file name of the Excel file containing the data
	 * @param iCompanyID 	          - The Company ID
	 * @param iOrgID     	          - The Organisation ID
	 * @param wCurrentSheet           - Contains the worksheet to be imported
	 * @param isAssignmentTargetRater - True indicate Import Assignment Target & Rater operation, 
	 *                                  False indicates Import Assignment Target Only operation
	 * @return A String[] containing the Import Status Message and Import Error message
	 * 
	 * Note: The validation of Critical Columns are done by importAssignment(), therefore not included in
	 *       this method. Unlike other import methods in the class, the two cells (cSurveyName and cTargetLoginID)
	 *       will never be null, hence it is alright to get the Column ID for the columns directly in
	 *       the "Get Column ID of Critical Column" section of the method
	 */
	public String[] importTarget(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet wCurrentSheet, boolean isAssignmentTargetRater) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];        
		String[] sErrorStatus = new String[3];
		String sMethodName = "importTarget"; //For printLog(), logError(), logDuplication()

		surveyResult = new SurveyResult();
		assignTargetRater = new AssignTarget_Rater();
		user = new User();

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords= 0;
		int iTotalDuplicatedRecords= 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cSurveyName = wCurrentSheet.findCell("SurveyName");
		Cell cTargetLoginID = wCurrentSheet.findCell("TargetLoginID");

		//Get Column ID of Critical Column
		int iColSurveyName = cSurveyName.getColumn();
		int iColTargetLoginID = cTargetLoginID.getColumn();
		
		//Get Cells of non-Critical Column
		Cell cRound = wCurrentSheet.findCell("Round");
		Cell cWave = wCurrentSheet.findCell("Wave");
		
		//Get Column ID of non-Critical Column
		int iColRound = -1;
		int iColWave = -1;
		if(cRound != null){
			iColRound = cRound.getColumn();
		}
		if(cWave != null){
			iColWave = cWave.getColumn();
		}

		try {
			//Set Start & Current Row Index
			iStartRowIndex = cSurveyName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";
				sErrorStatus[0] = "";
				sErrorStatus[1] = "";
				sErrorStatus[2] = "";
				//Survey Info, Target ID
				votblSurvey voSurvey = null;
				int iPKTarget = -1;

				//Get Competency Info from Excel file	- Critical Column	
				String sSurveyName = Utils.SQLFixer(wCurrentSheet.getCell(iColSurveyName, iCurrentRowIndex).getContents().trim());
				String sTargetLoginID = Utils.SQLFixer(wCurrentSheet.getCell(iColTargetLoginID, iCurrentRowIndex).getContents().trim());
				
				//Get info from Excel file  - non-Critical Column
				int iRound = 1;
				int iWave = 1;
				try{
					iWave = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColWave,iCurrentRowIndex).getContents().trim()));
				}catch(Exception e){
						iWave = 1;
				}
				try{
					iRound = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColRound,iCurrentRowIndex).getContents().trim()));
				}catch(Exception e){
						iRound = 1;
				}

				//Check for Empty String for Critical Columns
				if(sSurveyName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid SurveyName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid SurveyName";
					sErrorStatus[1] = "Empty SurveyName at row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update survey name and reload";
					isInvalidDataRow = true;
				} else if(sTargetLoginID.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid TargetLoginID - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Target LoginID";
					sErrorStatus[1] = "Empty Target LoginID at row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Target LoginID and reload";
					isInvalidDataRow = true;
				} else { 
					//Get Survey Info
					voSurvey = surveyResult.SurveyInfo(sSurveyName, iOrgID);     

					//Survey Info Null Check
					if(voSurvey == null|| voSurvey.getSurveyID() == 0) {
						//sRowErrorStatus = "Invalid SurveyName at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid SurveyName";
						sErrorStatus[1] = "Survey "+sSurveyName +" not exist in database";
						sErrorStatus[2] = "Please update survey record and reload";
						isInvalidDataRow = true;
					} else {
						//Get Target ID
						iPKTarget = user.checkUserExist(sTargetLoginID, iCompanyID, iOrgID);

						//Target ID Check
						if(iPKTarget <= 0) {
							//sRowErrorStatus = "Invalid TargetLoginID at row " + (iCurrentRowIndex + 1);
							sErrorStatus[0] = "Invalid Target LoginID";
							sErrorStatus[1] = "Target "+sTargetLoginID +" not exist in database";
							sErrorStatus[2] = "Please update target record and reload";
							isInvalidDataRow = true;
						} //End of Target ID Check
					} //End of Survey Info Null Check
				} //End of Check for Empty String for Critical Columns

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;                    
				} else {
					//Get Assignment ID
					int iAssignmentID = assignTargetRater.getAssignmentID(voSurvey.getSurveyID(), iPKTarget);

					//Assignment ID Check
					if(iAssignmentID <= 0) {
						//Add Target Check
						if(assignTargetRater.addTarget(voSurvey.getSurveyID(), iPKTarget)) {
							//Get Assignment ID
							iAssignmentID = assignTargetRater.getAssignmentID(voSurvey.getSurveyID(), iPKTarget);
							//Print to Console Log
							printLog(sMethodName, "Add Target - Survey Name:" + voSurvey.getSurveyName() +
									" [ID:" + voSurvey.getSurveyID() + "], Target:" + 
									sTargetLoginID + " [ID:" + iPKTarget + "]");
							//Is Import Assignment - Target & Rater Check
							if(isAssignmentTargetRater) {
								//Auto Self Check
								if(voSurvey.getAutoSelf()) {
									//Get Self As Rater Assignment ID
									int iTempAssignmentID = assignTargetRater.getAssignmentID(voSurvey.getSurveyID(), iPKTarget, iPKTarget);
									//Self As Rater Exists Check 
									if(iTempAssignmentID <= 0) {
										//Add Self As Rater Check
										if(assignTargetRater.addRater(voSurvey.getSurveyID(), iPKTarget, iPKTarget, 2, 0, "SELF", iRound, iWave)) {
											printLog(sMethodName, "Added Target as Rater Successfully at row " + 
													(iCurrentRowIndex + 1) + " - Survey Name:" + 
													voSurvey.getSurveyName() + " [ID:" + 
													voSurvey.getSurveyID() + "], Target:" + 
													sTargetLoginID + " [ID:" + iPKTarget + "]");
										} else {
											/*sRowErrorStatus = "Add Target as Rater Failed at row " + (iCurrentRowIndex + 1) +
											" - Survey Name:" + voSurvey.getSurveyName() +
											" [ID:" + voSurvey.getSurveyID() + "], Target:" + 
											sTargetLoginID + " [ID:" + iPKTarget + "]";*/
											//Add Error Log
											sErrorStatus[0] = "Add Target as Rater failed at Row " + (iCurrentRowIndex + 1);
											sErrorStatus[1] = "Unknow reason";
											sErrorStatus[2] = "Please contact administrator";
											logError(sMethodName, sErrorStatus);
											//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
											iTotalErrorRecords++;
										} //End of Add Self As Rater Check
									} //End of Self As Rater Exists Check 
								} //Auto Superior Check
								if(voSurvey.getAutoSup()) {
									//Get Direct Superior ID
									int iTempSupPKUser = user.getSupPKUser(iPKTarget);
									//Get Direct Superior As Rater Assignment ID
									int iTempAssignmentID = assignTargetRater.getAssignmentID(voSurvey.getSurveyID(), iTempSupPKUser, iPKTarget);
									//Direct Superior As Rater Exists Check 
									if(iTempAssignmentID <= 0) {
										//Add Superior As Rater Check
										if(assignTargetRater.addSupRater(voSurvey.getSurveyID(), iPKTarget)){
											printLog(sMethodName, "Added Superior of Target as Rater Successfully at row " + 
													(iCurrentRowIndex + 1) + " - Survey Name:" + 
													voSurvey.getSurveyName() + " [ID:" + 
													voSurvey.getSurveyID() + "], Target:" + 
													sTargetLoginID + " [ID:" + iPKTarget + "]");
										} else {
											/*sRowErrorStatus = "Add Superior of Target as Rater Failed at row " + (iCurrentRowIndex + 1) +
											" - Survey Name:" + voSurvey.getSurveyName() +
											" [ID:" + voSurvey.getSurveyID() + "], Target:" + 
											sTargetLoginID + " [ID:" + iPKTarget + "]";*/
											sErrorStatus[0] = "Add Superior of Target as Rater Failed at Row " + (iCurrentRowIndex +1);
											sErrorStatus[1] = "Target :[" + sTargetLoginID+"] Survey :["+voSurvey.getSurveyName()+"]";
											sErrorStatus[2] = "Please contact Administrator";
											//Add Error Log
											//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
											logError(sMethodName, sErrorStatus);
											iTotalErrorRecords++;
										} //End of Add Superior As Rater Check
									} //End of Direct Superior As Rater Exists Check 
								} //End of Auto Superior Check
							} //End of Is Import Assignment - Target & Rater Check
						} else {
							/*sRowErrorStatus = "Add Target Failed at row " + (iCurrentRowIndex + 1) +
							" - Survey Name:" + voSurvey.getSurveyName() +
							" [ID:" + voSurvey.getSurveyID() + "], Target:" + 
							sTargetLoginID + " [ID:" + iPKTarget + "]";*/
							sErrorStatus[0] = "Add Target Failed at Row " + (iCurrentRowIndex +1);
							sErrorStatus[1] = "Target :[" + sTargetLoginID+"] Survey :["+voSurvey.getSurveyName()+"]";
							sErrorStatus[2] = "Please contact Administrator";
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} //End of Add Target Check
					} else {
						/*sRowErrorStatus = "Target Assignment Exists at row " + (iCurrentRowIndex + 1) + 
						" - Survey Name:" + voSurvey.getSurveyName() +
						" [ID:" + voSurvey.getSurveyID() + "], Target:" + sTargetLoginID + 
						" [ID:" + iPKTarget + "]";*/
						sErrorStatus[0] = "Target Assignment exists at Row " + (iCurrentRowIndex +1);
						sErrorStatus[1] = "Target :[" + sTargetLoginID+"] Survey :["+voSurvey.getSurveyName()+"]";
						sErrorStatus[2] = "";
						//Add Duplication Log
						//logDuplication(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						logError(sMethodName, sErrorStatus);
						iTotalDuplicatedRecords++;
					} //End of Assignment ID Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop  

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Assignment - Target Unsuccessful.";
				else
					sOperationStatus[0] = "Import Assignment - Target Completed.";
				if(iTotalErrorRecords == 0)
					sOperationStatus[1] = "Duplication(s) Encountered.";
				else
					sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Assignment - Target Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Duplicated:" + iTotalDuplicatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			e.printStackTrace();
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			e.printStackTrace();
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;     	 	
	} //End of importTarget()

	/**
	 * Import Rater of an assignment into the database from File
	 * @param sFileName  	- The file name of the Excel file containing the data
	 * @param iCompanyID 	- The Company ID
	 * @param iOrgID     	- The Organisation ID
	 * @param wCurrentSheet - Contains the worksheet to be imported
	 * @return A String[] containing the Import Status Message and Import Error message
	 * 
	 * Note: The validation of Critical Columns are done by importAssignment(), therefore not included in
	 *       this method. Unlike other import methods in the class, the two cells (cSurveyName and cTargetLoginID)
	 *       will never be null, hence it is alright to get the Column ID for the columns directly in
	 *       the "Get Column ID of Critical Column" section of the method
	 */
	public String[] importRater(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet wCurrentSheet) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];     
		String[] sErrorStatus = new String[3];
		// store mapping from assignment to line
		TreeMap<Integer, LinkedList<Integer>> assignDict = new TreeMap<Integer, LinkedList<Integer>>();

		String sMethodName = "importRater"; //For printLog(), logError(), logDuplication()

		//Create new Hashtable to store Direct Superior to be imported to tblUser
		//Date: 21 Sept 2008
		//By Chun Pong
		Hashtable  htImportSuperior = new Hashtable();

		surveyResult = new SurveyResult();
		assignTargetRater = new AssignTarget_Rater();
		user = new User();
		relation = new RaterRelation();

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cSurveyName = wCurrentSheet.findCell("SurveyName");
		Cell cRTRelation = wCurrentSheet.findCell("RTRelation");
		Cell cRTSpecific = wCurrentSheet.findCell("RTSpecific");
		Cell cRaterLoginID = wCurrentSheet.findCell("RaterLoginID");
		Cell cTargetLoginID = wCurrentSheet.findCell("TargetLoginID");
		
		//Get Cells of non-Critical Column
		Cell cRound = wCurrentSheet.findCell("Round");
		Cell cWave = wCurrentSheet.findCell("Wave");

		//Get Column ID of Critical Column
		int iColSurveyName = cSurveyName.getColumn();
		int iColRTRelation = cRTRelation.getColumn();
		int iColRTSpecific = cRTSpecific.getColumn();
		int iColRaterLoginID = cRaterLoginID.getColumn();
		int iColTargetLoginID = cTargetLoginID.getColumn();
		
		//Get Column ID of non-Critical Column
		int iColRound = -1;
		int iColWave = -1;
		if(cRound != null){
			iColRound = cRound.getColumn();
		}
		if(cWave != null){
			iColWave = cWave.getColumn();
		}

		try {
			//Set Start & Current Row Index
			iStartRowIndex = cSurveyName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);
			
			// default round value for all
			int iRound = 1;
			
			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;

				//Survey Info, Target ID
				votblSurvey voSurvey = null;
				int iPKTarget = -1;
				int iPKRater = -1;
				int iAssignmentID = -1;
				int iRaterRelation = -1;
				int iRaterRelationsSpecific =  -1;
				String sRaterCode = "";
				// whether this row store target
				boolean isTarget = false;
				
				//Get Competency Info from Excel file	- Critical Column	
				String sSurveyName = Utils.SQLFixer(wCurrentSheet.getCell(iColSurveyName, iCurrentRowIndex).getContents().trim());
				String sRTRelation = Utils.SQLFixer(wCurrentSheet.getCell(iColRTRelation, iCurrentRowIndex).getContents().trim());
				String sRTSpecific = Utils.SQLFixer(wCurrentSheet.getCell(iColRTSpecific, iCurrentRowIndex).getContents().trim());
				String sRaterLoginID = Utils.SQLFixer(wCurrentSheet.getCell(iColRaterLoginID, iCurrentRowIndex).getContents().trim());
				String sTargetLoginID = Utils.SQLFixer(wCurrentSheet.getCell(iColTargetLoginID, iCurrentRowIndex).getContents().trim());

				//Check for Empty String for Critical Columns
				if(sSurveyName.equalsIgnoreCase("")) {
					sErrorStatus[0] = "Inavlid SurveyName detected";
					sErrorStatus[1] = "SurveyName is empty at Row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update SurveyName and reload";
					isInvalidDataRow = true;
				} else if(sRTRelation.equalsIgnoreCase("")) {
					sErrorStatus[0] = "Inavlid RTRelation detected";
					sErrorStatus[1] = "RTRelation is empty at Row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update RTRelation and reload";
					isInvalidDataRow = true;
				} else if(sRaterLoginID.equalsIgnoreCase("")) {
					sErrorStatus[0] = "Inavlid RaterLoginID detected";
					sErrorStatus[1] = "RaterLoginID is empty at Row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update RaterLoginID and reload";
					isInvalidDataRow = true;
				} else if(sTargetLoginID.equalsIgnoreCase("")) {
					sErrorStatus[0] = "Inavlid TargetLoginID detected";
					sErrorStatus[1] = "TargetLoginID is empty at Row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update TargetLoginID and reload";
					isInvalidDataRow = true;
				} else { 
					//Get Survey Info
					voSurvey = surveyResult.SurveyInfo(sSurveyName, iOrgID);     

					//Survey Info Null Check
					if(voSurvey == null) {
						sErrorStatus[0] ="Invalid SurveyName detected";
						sErrorStatus[1] = "SurveyName [" + sSurveyName + "] does not exist in database";
						sErrorStatus[2] = "Please update SurveyName and reload";
						isInvalidDataRow = true;
					} else {
						//Get Target ID
						iPKTarget = user.checkUserExist(sTargetLoginID, iCompanyID, iOrgID);

						//Target ID Check
						if(iPKTarget <= 0) {
							sErrorStatus[0] ="Invalid TargetLoginID detected";
							sErrorStatus[1] = "SurveyName [" + sTargetLoginID + "] does not exist in database";
							sErrorStatus[2] = "Please update TargetLoginID and reload";
							isInvalidDataRow = true;
						} else { 
							//Get Rater ID
							iPKRater = user.checkUserExist(sRaterLoginID, iCompanyID, iOrgID);

							//Rater ID Check
							if(iPKRater <= 0) {
								sErrorStatus[0] ="Invalid RaterLoginID detected";
								sErrorStatus[1] = "SurveyName [" + sRaterLoginID + "] does not exist in database";
								sErrorStatus[2] = "Please update RaterLoginID and reload";
								isInvalidDataRow = true;
							} else {
								//Get Assignment ID
								iAssignmentID = assignTargetRater.getAssignmentID(voSurvey.getSurveyID(), iPKRater, iPKTarget);

								//Assignment ID Check
							/*	if(iAssignmentID > 0) {
									sRowErrorStatus = "Survey-Rater Assignment Exists at row " + 
									(iCurrentRowIndex + 1) + " - Survey Name:" + sSurveyName +
									" , Rater:" + sRaterLoginID + " [ID:" + iPKRater + "]" +
									" , Target:" + sTargetLoginID + " [ID:" + iPKTarget + "]";
									isInvalidDataRow = true;
								} else { 
							 */
									//Get Relationship Type
									if(sRTRelation.equalsIgnoreCase("SELF") || sRTSpecific.equalsIgnoreCase("SELF") || 
											sRaterLoginID.equals(sTargetLoginID)) {
										iRaterRelation = relation.getRelationHigh("SELF");
										isTarget = true;
									} else {
										iRaterRelation = relation.getRelationHigh(sRTRelation);                                    	
									} //End of Get Relationship Type

									//Relationship Type Check
									if(iRaterRelation  == 0) {
										sErrorStatus[0] = "Invalid Rater Relation detected at Row " + (iCurrentRowIndex + 1) ;
										sErrorStatus[1] = "RTRelation [" + sRTRelation + "] for Target [" + sTargetLoginID + "] does not exists in database";
										sErrorStatus[2] = "Please update RTRelation and reload";
										isInvalidDataRow = true;
									} else { 
										//Get Relation Specific
										if(sRTSpecific.equalsIgnoreCase("")) { 
											iRaterRelationsSpecific = 0;
										} else {
											iRaterRelationsSpecific = relation.getRelationSpecific(sRTSpecific);

											//-1 Check
											if(iRaterRelationsSpecific == -1) {
												sErrorStatus[0] = "Invalid Rater Specific detected at Row " + (iCurrentRowIndex + 1) ;
												sErrorStatus[1] = "RTSpecific [" + sRTSpecific + "] for Target [" + sTargetLoginID + "] does not exists in database";
												sErrorStatus[2] = "Please update RTSpecific and reload";
												isInvalidDataRow = true;
											} //End of -1 Check
											//New import Direct Superior Code - Add new Direct Superior Import record
											//Date: 21 Sept 2008
											//By Chun Pong
											//Direct Superior Relation Specific Check
											if(sRTSpecific.equalsIgnoreCase("Direct Superior")){
												//Target-Rater Direct Superior Relation Exists Check
												if(htImportSuperior.containsKey(sTargetLoginID)){  
													//Remove entry
													htImportSuperior.remove(sTargetLoginID);
												} else {
													//Add new entry
													htImportSuperior.put(sTargetLoginID, sRaterLoginID);
												} //End of Target-Rater Direct Superior Relation Exists Check
											} //End of Direct Superior Relation Specific Check
										} //End of Get Relation Specific
									} //End of Relationship Type Check
								//} //Assignment ID Check
							} //End of Rater ID Check
						} //End of Target ID Check
					} //End of Survey Info Null Check
				} //End of Check for Empty String for Critical Columns
				
				//Get info from Excel file  - non-Critical Column
				int iWave = 1;
				try{
					iWave = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColWave,iCurrentRowIndex).getContents().trim()));
				}catch(Exception e){
						iWave = 1;
				}
				if(isTarget){
					try{
						iRound = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColRound,iCurrentRowIndex).getContents().trim()));
					}catch(Exception e){
							iRound = 1;
					}
				}

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;                    
				} else {
					//Assignment ID Check
					//Get New Rater Code
					sRaterCode = assignTargetRater.RaterCode(voSurvey.getSurveyID(), iRaterRelation, iRaterRelationsSpecific, iPKTarget);
					if(iAssignmentID <= 0) {
						System.out.println(">>" + iRaterRelation + ":" + iRaterRelationsSpecific + ":" + iPKTarget + ">>" + sRaterCode);
						//Add Rater Check
						if(assignTargetRater.addRater(voSurvey.getSurveyID(), iPKTarget, iPKRater,
								iRaterRelation, iRaterRelationsSpecific, sRaterCode, iRound, iWave)) {
							//Get Assignment ID
							iAssignmentID = assignTargetRater.getAssignmentID(voSurvey.getSurveyID(),iPKRater , iPKTarget);
							//Print to Console Log
							printLog(sMethodName, "Added Rater [ID::" + iPKRater + "] to Target [ID:" + 
									iPKTarget + "] to '" + voSurvey.getSurveyName() + "' Survey");
						} else {
							sErrorStatus[0] = "Add Rater Failed at Row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Unknown reason";
							sErrorStatus[2] = "Please contact administrator";
							//Add Error Log
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} //End of Add Target Check
					//}else if!(voSurvey.getAutoSelf() && (iPKRater == iPKTarget)) && !(sRTSpecific.equalsIgnoreCase("Direct Superior") && voSurvey.getAutoSup())
					} else {
						//The above if condition is used to supress error message when the assignment being flagged is
						//a Self assignment for a auto self survey or a Direct Superior assignment for a auto sup survey
						
						// check if the new data is complete duplica for existing data, in not then overwrite existing data
						if(!checkAssignmentInconsistence(iAssignmentID, iRaterRelation, iRaterRelationsSpecific, iRound, iWave)){
								sErrorStatus[0] = "Assignment info overwritten at Row " + (iCurrentRowIndex + 1);
								LinkedList<Integer> assignRowList = assignDict.get(iAssignmentID);
								if(assignRowList == null){
									TreeMap<String, String> originData = getOriginAssignData(iAssignmentID, iRaterRelation, iRaterRelationsSpecific,
											iRound, iWave);
									sErrorStatus[1] = ". Assignment information here is different from data store in database, old data have been overwritten, data being" +
											" overwritten are:";
									for(Map.Entry<String, String> entry : originData.entrySet()){
										sErrorStatus[1] += " "+ entry.getKey()+" ["+entry.getValue()+"];";
									}
								}
								else{
									sErrorStatus[1] = ". Plase refer to this row for information about the same assignment: ";
									sErrorStatus[1] += assignRowList.get(assignRowList.size()-1)+", ";
								}
								sErrorStatus[2] = "Please double check data and reload if necessary";
								//Add Error Log
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
								//Edit rater
								String oldRaterCode = assignTargetRater.getRaterCode(iAssignmentID);
								if(assignTargetRater.checkRaterCodeGenre(oldRaterCode, sRaterCode)){
									sRaterCode = oldRaterCode;
								}else{
									// update others' rater code
									assignTargetRater.updateOthersRaterCode(oldRaterCode, voSurvey.getSurveyID(), iPKTarget);
								}
								if(assignTargetRater.editRater(iPKTarget, iAssignmentID, iRaterRelation, iRaterRelationsSpecific, 
										sRaterCode, iRound, iWave)) {
									
									printLog(sMethodName, "Updated Rater Successfully at row " + 
											(iCurrentRowIndex + 1) + " - Survey Name:" + 
											voSurvey.getSurveyName() + " [Survey ID:" + 
											voSurvey.getSurveyID() + "], [Target ID:" + 
											iPKTarget+"], [Rater ID:" + iPKRater + "]");
		
								} else {
									sErrorStatus[0] = "Update Rater Failed at Row " + (iCurrentRowIndex + 1);
									sErrorStatus[1] = "Assignment with target [" + sTargetLoginID + "], rater [" + sRaterLoginID + "]," +
											" Survey [" + sSurveyName + "] fail to update for unknown reason";
									sErrorStatus[2] = "Please contact administrator";
									//Add Error Log
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								} 
						}else{
						sErrorStatus[0] = "Rater Assignment duplication detected at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Assignment with Target [" + sTargetLoginID + "], Rater [" + sRaterLoginID + "]" +
								", Survey [" + sSurveyName + "] aleady exist in database";
						sErrorStatus[2] = "";
						//Add Duplication Log
						logError(sMethodName, sErrorStatus);
						iTotalDuplicatedRecords++;
						}
					} //End of Assignment ID Check
				} //End of Invalid Data Row Check
				LinkedList<Integer> rowList = assignDict.get(iAssignmentID);
				if(rowList == null)
					rowList = new LinkedList<Integer>();
				rowList.add(iCurrentRowIndex+1);
				assignDict.put(iAssignmentID, rowList);
				iCurrentRowIndex++;
			} //End of Data Row While-Loop  

			//New import Direct Superior Code - Import new Direct Superior record into tblUser
			//Date: 21 Sept 2008
			//By Chun Pong
			//Remarks: No error logging
			//Import Superior Code          
			for(Iterator itImport = htImportSuperior.entrySet().iterator();itImport.hasNext();) {  
				Map.Entry record = (Map.Entry)itImport.next(); 
				String sTargetLoginID = (String) record.getKey(); 
				String sRaterLoginID = (String)record.getValue(); 

				//Get Target ID
				int iPKTarget = user.checkUserExist(sTargetLoginID, iCompanyID, iOrgID);
				//Get Rater ID
				int iPKRater = user.checkUserExist(sRaterLoginID, iCompanyID, iOrgID);
				user.updateRelation(iPKTarget, iPKRater);            	
			} 
			//End of Import Superior Code

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Assignment - Rater & Target Unsuccessful.";
				else
					sOperationStatus[0] = "Import Assignment - Rater & Target Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Assignment - Rater & Target Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Edited:" + iTotalUpdatedRecords + ", Total Duplicated:" + 
			iTotalDuplicatedRecords + ", Total Errors:" + iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log"+e.getMessage();
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;   	 	
	} //End of importRater()
	
	/**
	 * Import Cluster information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] importCluster(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String[] sErrorStatus = new String[3];
		String sMethodName = "importCluster"; //For printLog(), logError(), logDuplication

		//Initial validation checkers' objects
		Cluster cluster = new Cluster();
		user = new User();

		//Similarity Percentage
		double dSimilarityPercentage = 5.0;

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cClusterName = wCurrentSheet.findCell("ClusterName");
		
        
		//Get Column ID of Critical Column
		int iColClusterName = -1;
		

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - CompetencyName 
			if(cClusterName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "ClusterName";
			} else {
				iColClusterName = cClusterName.getColumn();
			} //End of Critical Column Missing Check - CompetencyName 

			

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			iStartRowIndex = cClusterName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sClusterName = Utils.SQLFixer(wCurrentSheet.getCell(iColClusterName, iCurrentRowIndex).getContents().trim());
			

				//Check for Empty String for Critical Columns
				if(sClusterName.equalsIgnoreCase("")) {
					sErrorStatus[0] = "Invalid ClusterName detected";
					sErrorStatus[1] = "ClusterName is emtpy at Row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = "Plase update ClusterName and reload";
					isInvalidDataRow = true;
				} 
				
				if(isInvalidDataRow) {
					//Add Error Log
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					Vector vCompName = new Vector();
					boolean isSimilarStringFound = false;

					//Get Competency Name Vector List
				

					//Similar String Found Check
					if(!isSimilarStringFound) {
						//PKUser and PKUserType
						int iPKUser = -1;                    	
						int iPKUserType = -1;

						//Get PKUser
						iPKUser = user.checkUserExist(sUserName, iCompanyID, iOrgID);

						//PKUser Exists Check
						if(iPKUser <= 0) {
							sErrorStatus[0] = "Unable to retrieve User's ID at Row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "User [" + sUserName + "] does not exist in database";
							sErrorStatus[2] = "";
									
							//Add Error Log
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} else {                    	
							//Get PKUser Type
							iPKUserType = user.getPKUserType(iPKUser);

							//PKUserType Exists Check
							if(iPKUser <= 0) {
								sErrorStatus[0] = "Unable to retrieve User's Type at Row " + (iCurrentRowIndex + 1);
								sErrorStatus[1] = "User type for User [" + sUserName + "] failed to retrieve";
								sErrorStatus[2] = "Please contact administrator";
								//Add Error Log
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							} else { 
								//Add Competency		     
								cluster.insertCluster(sClusterName,
										iOrgID, iPKUser);
								//Print to Console Log
								printLog(sMethodName, "Added Cluster Name: '" + sClusterName 
									);
								iTotalAddedRecords++;	
							} //End PKUserType Exists Check
						}//End PKUser Exists Check
					} //End of Similar String Found Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Cluster Unsuccessful.";
				else
					sOperationStatus[0] = "Import Cluster Completed.";

				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Cluster Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Edited:" + iTotalUpdatedRecords + ", Total Duplicated:" + 
			iTotalDuplicatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;  
	} //End of importCompetency()

	/**
	 * Import Competency information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] importCompetency(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String[] sErrorStatus = new String[3];
		String sMethodName = "importCompetency"; //For printLog(), logError(), logDuplication

		//Initial validation checkers' objects
		competency = new Competency();
		user = new User();

		//Similarity Percentage
		double dSimilarityPercentage = 5.0;

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cCompetencyName = wCurrentSheet.findCell("CompetencyName");
		Cell cCompetencyDefinition = wCurrentSheet.findCell("CompetencyDefinition");
		Cell cCompetencyName2 = wCurrentSheet.findCell("CompetencyName2");
		Cell cCompetencyDefinition2 = wCurrentSheet.findCell("CompetencyDefinition2");
		Cell cLang = wCurrentSheet.findCell("Language");
		
		boolean includeMulLang = false;
		//Get Column ID of non-Critical Column
		int iColCompetencyName2 = -1;
		int iColCompetencyDefinition2 = -1;
		int iLang = -1;
		//non-Critical Column Missing check
		if(cCompetencyName2 != null && cCompetencyDefinition2 != null && cLang != null){
			iColCompetencyName2 = cCompetencyName2.getColumn();
			iColCompetencyDefinition2 = cCompetencyDefinition2.getColumn();
			iLang = cLang.getColumn();
			includeMulLang = true;
		}

		//Get Column ID of Critical Column
		int iColCompetencyName = -1;
		int iColCompetencyDefinition = -1;

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - CompetencyName 
			if(cCompetencyName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "CompetencyName";
			} else {
				iColCompetencyName = cCompetencyName.getColumn();
			} //End of Critical Column Missing Check - CompetencyName 

			//Critical Column Missing Check - CompetencyDefinition 
			if(cCompetencyDefinition == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "CompetencyDefinition";
			} else {
				iColCompetencyDefinition = cCompetencyDefinition.getColumn();
			} //End of Critical Column Missing Check - CompetencyDefinition 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			iStartRowIndex = cCompetencyName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";
				//Get Competency Info from Excel file	- Critical Column	
				String sCompetencyName = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyName, iCurrentRowIndex).getContents().trim());
				if(sCompetencyName.length() == 0) break; // end of file
				String sCompetencyDefinition = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyDefinition, iCurrentRowIndex).getContents().trim());
				
				String sCompetencyName2 = null;
				String sCompetencyDefinition2 = null;
				int lang = 0;
				if(includeMulLang){
					sCompetencyName2 = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyName2, iCurrentRowIndex).getContents().trim());
					sCompetencyDefinition2 = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyDefinition2, iCurrentRowIndex).getContents().trim());
					lang = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iLang, iCurrentRowIndex).getContents().trim()));
				}
				//Check for Empty String for Critical Columns
				if(sCompetencyName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid CompetencyName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Competency Name Detected";
					sErrorStatus[1] = "Competency Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Competency Name and reload";
					isInvalidDataRow = true;
				} else if(sCompetencyDefinition.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid CompetencyDefinition - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid CompetencyDefinition Detected";
					sErrorStatus[1] = "CompetencyDefinition is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update CompetencyDefinition and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns

				if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					Vector vCompName = new Vector();
					boolean isSimilarStringFound = false;

					//Get Competency Name Vector List
					vCompName = Utils.getAllRecordsBasedOnLength("CompetencyName", sCompetencyName.length(), 
							"", 0, dSimilarityPercentage, iOrgID);

					//vCompName Null Check
					if(vCompName == null) {
						//SQL Exception occurs in getting vCompName
						sErrorStatus[0] = "SQL Exceptions - Error getting Competency List at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Error occur when retrieveing Competency List from database";
						sErrorStatus[2] = "";
						//Add Error Log
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					} else {
						//Start Competency Name Best Match For-Loop
						CompetencyNameForLoop: for (int i = 0; i < vCompName.size(); i++) {
							//Get Competency
							voCompetency voComp = (voCompetency) vCompName.elementAt(i);
							//Get Current Competency's Name (i.e. get element of index i in vCompName)
							String sCompNameTemp = voComp.getCompetencyName();

							//Compute LDistance value and get Similartiy Value
							double dSimilarityCompNameValue = Utils.getSimilarityValue(sCompetencyName, sCompNameTemp);

							//Competency Name Similarity Check
							if(dSimilarityCompNameValue <= dSimilarityPercentage) {
								//Same Record Check
								if(dSimilarityCompNameValue == 0) {
									sErrorStatus[0] = "Competency Name Similarity Check Failed at Row " + (iCurrentRowIndex + 1);
									sErrorStatus[1] = "Competency [" + sCompetencyName + "] already exists in database";
									sErrorStatus[2] = "";
									//Add Duplication Log
									logError(sMethodName, sErrorStatus);
									iTotalDuplicatedRecords++;
								} else {
									sErrorStatus[0] = "Competency Name Similarity Check Failed at Row " + (iCurrentRowIndex + 1); 
									sErrorStatus[1] = "Competency [" + sCompetencyName + "] is similar to [" + sCompNameTemp +"] in database";
									sErrorStatus[2] = "";
									//Add Error Log
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								} //End of Same Record Check	
								isSimilarStringFound = true;                                
								break CompetencyNameForLoop;
							} //End of Competency Name Similarity Check
						} //End of Competency Name Best Match For-Loop
					} //End of vCompName Null Check

					//Similar String Found Check
					if(!isSimilarStringFound) {
						//PKUser and PKUserType
						int iPKUser = -1;                    	
						int iPKUserType = -1;
						
						//Get PKUser
						iPKUser = user.checkUserExist(sUserName, iCompanyID, iOrgID);

						//PKUser Exists Check
						if(iPKUser <= 0) {
							sErrorStatus[0] = "Unable to retrieve User's ID at Row "+ (iCurrentRowIndex + 1);
							sErrorStatus[1] = "User [" + sUserName + "] does not exist in database";
							sErrorStatus[2] = "";
							//Add Error Log
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} else {                    	
							//Get PKUser Type
							iPKUserType = user.getPKUserType(iPKUser);

							//PKUserType Exists Check
							if(iPKUser <= 0) {
								sErrorStatus[0] = "Unable to retrieve User's Type at Row "+ (iCurrentRowIndex + 1);
								sErrorStatus[1] = "User type for user [" + sUserName + "] is unable to retrieved";
								sErrorStatus[2] = "";
								//Add Error Log
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							} else { 
								
								//Add Competency	
								if(includeMulLang){
									competency.addRecord(sCompetencyName, sCompetencyDefinition, sCompetencyName2, sCompetencyDefinition2, lang, iCompanyID, iOrgID, iPKUser, iPKUserType);
								}else{
									competency.addRecord(sCompetencyName, sCompetencyDefinition, iCompanyID, 
											iOrgID, iPKUser, iPKUserType);
								}
								//Print to Console Log
								printLog(sMethodName, "Added Competency Name: '" + sCompetencyName + 
										"' Definition='" + sCompetencyDefinition + "'");
								iTotalAddedRecords++;	
							} //End PKUserType Exists Check
						}//End PKUser Exists Check
					} //End of Similar String Found Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Competency Unsuccessful.";
				else
					sOperationStatus[0] = "Import Competency Completed.";

				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Competency Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Edited:" + iTotalUpdatedRecords + ", Total Duplicated:" + 
			iTotalDuplicatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;  
	} //End of importCompetency()

	/**
	 * Import Key Behaviours information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] importKeyBehaviour(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String[] sErrorStatus = new String[3];
		String sMethodName = "importKeyBehaviour"; //For printLog(), logError(), logDuplication

		//Initial validation checkers' objects
		competency = new Competency();
		keyBehaviour = new KeyBehaviour();
		user = new User();

		//Similarity Percentage
		double dSimilarityPercentage = 5.0;

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cCompetencyName = wCurrentSheet.findCell("CompetencyName");
		Cell cKeyBehaviour = wCurrentSheet.findCell("KeyBehaviour");
		Cell cKBLevel = wCurrentSheet.findCell("KBLevel");
		
		// Get Cells of non-Crititcal Column
		Cell cKeyBehaviour2 = wCurrentSheet.findCell("KeyBehaviour2");
		Cell cLang = wCurrentSheet.findCell("Language");

		//Get Column ID of Critical Column
		int iColCompetencyName = -1;
		int iColKeyBehaviour = -1;
		int iColKBLevel = -1;
		
		//Get Column ID of non-Critical Column
		int iColKeyBehaviour2 = -1;
		int iColLang = -1;
		
		boolean includeMulLang = false;
		

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - CompetencyName 
			if(cCompetencyName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "CompetencyName";
			} else {
				iColCompetencyName = cCompetencyName.getColumn();
			} //End of Critical Column Missing Check - CompetencyName 

			//Critical Column Missing Check - KeyBehaviour 
			if(cKeyBehaviour == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "KeyBehaviour";
			} else {
				iColKeyBehaviour = cKeyBehaviour.getColumn();
			} //End of Critical Column Missing Check - KeyBehaviour 

			//Critical Column Missing Check - KBLevel 
			if(cKBLevel == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "KBLevel";
			} else {
				iColKBLevel = cKBLevel.getColumn();
			} //End of Critical Column Missing Check - KBLevel 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check
			
			// Find non-Critical Column
			if(cKeyBehaviour2 != null && cLang != null){
				iColKeyBehaviour2 = cKeyBehaviour2.getColumn();
				iColLang = cLang.getColumn();
				includeMulLang = true;
			}

			//Set Start & Current Row Index
			iStartRowIndex = cCompetencyName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sCompetencyName = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyName, iCurrentRowIndex).getContents().trim());
				if(sCompetencyName.length() == 0) break; // end of file
				String sKeyBehaviour = Utils.SQLFixer(wCurrentSheet.getCell(iColKeyBehaviour, iCurrentRowIndex).getContents().trim());
				String sKBLevel = Utils.SQLFixer(wCurrentSheet.getCell(iColKBLevel, iCurrentRowIndex).getContents().trim());

				
				String sKeyBehaviour2 = null;
				int lang = 0;
				if(includeMulLang){
					sKeyBehaviour2 = Utils.SQLFixer(wCurrentSheet.getCell(iColKeyBehaviour2, iCurrentRowIndex).getContents().trim());
					lang = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColLang, iCurrentRowIndex).getContents().trim()));
				}
				//Key Behaviour Level
				int iKBLevel = -1;

				//Check for Empty String for Critical Columns
				if(sCompetencyName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid CompetencyName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Competency Name Detected";
					sErrorStatus[1] = "Competency Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Competency Name and reload";
					isInvalidDataRow = true;
				} else if(sKeyBehaviour.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid KeyBehaviour - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid KeyBehaviour Detected";
					sErrorStatus[1] = "KeyBehaviour is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update KeyBehaviour and reload";
					isInvalidDataRow = true;
				} else if(sKBLevel.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid KBLevel - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid KeyBehaviour Level Detected";
					sErrorStatus[1] = "KeyBehaviour Level is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update KeyBehaviour Level and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns

				//Key Behaviour Level Validation
				if(!sKBLevel.equalsIgnoreCase("")) {
					try {
						iKBLevel = Integer.parseInt(sKBLevel);	                	
						if(!(iKBLevel > 0 && iKBLevel <  11)) {
							//sRowErrorStatus = "Invalid KBLevel at row " + (iCurrentRowIndex + 1) + 
							//" - KBLevel value must be between 1 to 10";
							sErrorStatus[0] = "Invalid KeyBehaviour Level Detected";
							sErrorStatus[1] = "KeyBehaviour level [" + sKBLevel + "] is not between 1 to 10";
							sErrorStatus[2] = "Please update KBLevel and reload";
							isInvalidDataRow = true;
						}
					} catch (NumberFormatException e) {
						//sRowErrorStatus = "Invalid KBLevel - KBLevel at row " + (iCurrentRowIndex + 1) + 
						//" is not in numerical format";
						sErrorStatus[0] = "Invalid KeyBehaviour Level Detected";
						sErrorStatus[1] = "KeyBehaviour level [" + sKBLevel + "] is not in numerical format";
						sErrorStatus[2] = "Please update KBLevel and reload";
						isInvalidDataRow = true;
					}
				} //End of Key Behaviour Level Validation

				if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Print to Console Log
					printLog(sMethodName, "Importing Key Behaviour - Competency Name: '" + sCompetencyName + 
							"' Key Behaviour='" + sKeyBehaviour + "' KB Level='" + sKBLevel + "'");

					boolean isSimilarStringFound = false;
					//Get Competency ID
					int iPKCompetency = competency.getCompetencyID_Import(sCompetencyName, iCompanyID, iOrgID);
					Vector vKeyBehaviour = null;

					//Competency ID Check
					if(iPKCompetency <= 0) {
						//Exception occurs in getting Competency ID
						//sRowErrorStatus = "Invalid CompetencyName at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Competency Name at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Competency Name [" + sCompetencyName + "] does not exists in the database";
						sErrorStatus[2] = "Please update Competency Name and reload";
						//Add Error Log
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					} else {

						//Get KeyBehaviour Name Vector List
						vKeyBehaviour = Utils.getAllRecordsBasedOnLength("KeyBehaviour", 
								sKeyBehaviour.length(), 
								"FKCompetency = '" + iPKCompetency + 
								"'", iKBLevel,  
								dSimilarityPercentage, iOrgID);

						//vKeyBehaviour List Null Check
						if(vKeyBehaviour == null) {
							//SQL Exception occurs in getting vKeyBehaviour
							sErrorStatus[0] = "SQL Exceptions when processing Row" + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Error getting KeyBehaviour List for Competency [" + sCompetencyName + "]";
							sErrorStatus[2] = "Please update Competency Name and reload";
							//Add Error Log
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} else {
							//Start Key Behaviour Best Match For-Loop
							KeyBehaviourForLoop: for(int i = 0; i < vKeyBehaviour.size(); i++) {

								//Get Key Behaviour
								voKeyBehaviour voKB = (voKeyBehaviour) vKeyBehaviour.elementAt(i);
								//Get Current Key Behaviour (i.e. get element of index i in vCompName)
								String sKeyBehaviourTemp = voKB.getKeyBehaviour();

								//Compute LDistance value and get Similartiy Value
								double dSimilarityKBNameValue = Utils.getSimilarityValue(sKeyBehaviour, sKeyBehaviourTemp);

								//Key Behaviour Similarity Check
								if(dSimilarityKBNameValue <= dSimilarityPercentage) {
									//Same Record Check
									if(dSimilarityKBNameValue == 0) {
										sErrorStatus[0] = "Key Behaviour Similarity Check Fail at Row " + (iCurrentRowIndex + 1);
										sErrorStatus[1] = "Key Behaviour [" + sKeyBehaviour + "] exists in database";
										sErrorStatus[2] = "";
										//Add Duplication Log
										logError(sMethodName, sErrorStatus);
										iTotalDuplicatedRecords++;
									} else {
										sErrorStatus[0] = "Key Behaviour Similarity Check Fail at Row " + (iCurrentRowIndex + 1);
										sErrorStatus[1] = "Key Behaviour [" + sKeyBehaviour + "] is similar to [" + sKeyBehaviourTemp + "] in database";
										sErrorStatus[2] = "";
										//Add Error Log
										logError(sMethodName, sErrorStatus);
										iTotalErrorRecords++;
									} //End of Same Record Check	
									isSimilarStringFound = true;                                	
									break KeyBehaviourForLoop;
								} //End of Key Behaviour Name Similarity Check
							} //End of Key Behaviour Best Match For-Loop
						} //End of vKeyBehaviour List Null Check

						//Similar String Found Check
						if(!isSimilarStringFound) {
							//PKUser and PKUserType
							int iPKUser = -1;                    	
							int iPKUserType = -1;

							//Get PKUser
							iPKUser = user.checkUserExist(sUserName, iCompanyID, iOrgID);

							//PKUser Exists Check
							if(iPKUser <= 0) {
								sErrorStatus[0] = "Unable to retrieve User's ID at Row "+ (iCurrentRowIndex + 1);
								sErrorStatus[1] = "User [" + sUserName + "] does not exist in database";
								sErrorStatus[2] = "";
								//Add Error Log
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							} else {
								//Get PKUser Type
								iPKUserType = user.getPKUserType(iPKUser);

								//PKUserType Exists Check
								if(iPKUser <= 0) {
									sErrorStatus[0] = "Unable to retrieve User's Type at Row "+ (iCurrentRowIndex + 1);
									sErrorStatus[1] = "User type for user [" + sUserName + "] is unable to retrieved";
									sErrorStatus[2] = "";
									//Add Error Log
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								} else { 
									//Add Key Behaviour
									if(includeMulLang){
										keyBehaviour.addRecord(iPKCompetency, sKeyBehaviour, sKeyBehaviour2, lang, iKBLevel, iCompanyID, iOrgID, 
												iPKUser, iPKUserType);									
									}else{
										keyBehaviour.addRecord(iPKCompetency, sKeyBehaviour, iKBLevel, iCompanyID, iOrgID, 
												iPKUser, iPKUserType);
									}
									//Print to Console Log
									printLog(sMethodName, "Added Competency Name: '" + sCompetencyName + 
											"' Key Behaviour='" + sKeyBehaviour + "' KB Level='" + iKBLevel + "'");
									iTotalAddedRecords++;	
								} //End PKUserType Exists Check
							} //End PKUser Exists Check
						} //End of Similar String Found Check
					} //End of Competency ID Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Key Behaviour Unsuccessful.";
				else
					sOperationStatus[0] = "Import Key Behaviour Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Key Behaviour Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Duplicated:" + iTotalDuplicatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;  
	} //End of importKeyBehaviour()

	/**
	 * Import Development Activities information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	//Added a new parameter loginUserType. To determine if system user is sa, Sebastian 29 July 2010
	public String[] importDevelopmentActivities(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets, int loginUserType) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String[] sErrorStatus = new String[3];
		String sMethodName = "importDevelopmentActivities"; //For printLog(), logError(), logDuplication

		//Initial validation checkers' objects
		competency = new Competency();
		developmentActivities = new DevelopmentActivities();
		user = new User();

		//Similarity Percentage
		double dSimilarityPercentage = 5.0;

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cCompetencyName = wCurrentSheet.findCell("CompetencyName");
		Cell cDRAStatement = wCurrentSheet.findCell("DRAStatement");
		//Add new variable cIsSystemGenerated. To capture the cell column IsSystemGenerated in the import file, Sebastian 29 July 2010
		Cell cIsSystemGenerated = wCurrentSheet.findCell("IsSystemGenerated");
		
		//Get Cells of non-Critical Column
		Cell cDRAStatement2 = wCurrentSheet.findCell("DRAStatement2");
		Cell cLang = wCurrentSheet.findCell("Language");

		//Get Column ID of Critical Column
		int iColCompetencyName = -1;
		int iColDRAStatement = -1;
		//Add new variable iIsSystemGenerated. To capture the column index of IsSystemGenerated in the import file, Sebastian 29 July 2010
		int iIsSystemGenerated = -1;
		
		//Get Column ID of non-Critical Column
		int iColDRAStatement2 = -1;
		int iColLang = -1;
		
		boolean includeMulLang = false;


		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - CompetencyName 
			if(cCompetencyName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "CompetencyName";
			} else {
				iColCompetencyName = cCompetencyName.getColumn();
			} //End of Critical Column Missing Check - CompetencyName 

			//Critical Column Missing Check - DRAStatement 
			if(cDRAStatement == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "DRAStatement";
			} else {
				iColDRAStatement = cDRAStatement.getColumn();
			} //End of Critical Column Missing Check - DRAStatement 
			
			//Critical Column Missing Check - cIsSystemGenerated, Sebastian 29 July 2010
			if(cIsSystemGenerated == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "IsSystemGenerated";
			} else {
				iIsSystemGenerated = cIsSystemGenerated.getColumn();
			} //End of Critical Column Missing Check - DRAStatement 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check
			
			if(cDRAStatement2 != null && cLang != null){
				iColDRAStatement2 = cDRAStatement2.getColumn();
				iColLang = cLang.getColumn();
				includeMulLang = true;
			}

			//Set Start & Current Row Index
			iStartRowIndex = cCompetencyName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {

				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sCompetencyName = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyName, iCurrentRowIndex).getContents().trim());
				if(sCompetencyName.length() == 0) break;
				String sDRAStatement = Utils.SQLFixer(wCurrentSheet.getCell(iColDRAStatement, iCurrentRowIndex).getContents().trim());
				//Add new variable sIsSystemGenerated. To capture the cell value in the IsSystemGenerated column in the import file, Sebastian 29 July 2010
				String sIsSystemGenerated = Utils.SQLFixer(wCurrentSheet.getCell(iIsSystemGenerated, iCurrentRowIndex).getContents().trim());

				String sDRAStatement2 = null;
				int lang = 0;
				if(includeMulLang){
					sDRAStatement2 = Utils.SQLFixer(wCurrentSheet.getCell(iColDRAStatement2, iCurrentRowIndex).getContents().trim());
					lang = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColLang, iCurrentRowIndex).getContents().trim()));
				}
				
				//Check for Empty String for Critical Columns
				if(sCompetencyName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid CompetencyName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Competency Name Detected";
					sErrorStatus[1] = "Competency Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Competency Name and reload";
					isInvalidDataRow = true;
				} else if(sDRAStatement.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DRAStatement - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid DRAStatement Detected";
					sErrorStatus[1] = "DRAStatement is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update DRAStatement and reload";
					isInvalidDataRow = true;
				} else if(sIsSystemGenerated.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid IsSystemGenerated - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid IsSystemGenerated Detected";
					sErrorStatus[1] = "IsSystemGenerated is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update IsSystemGenerated and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Print to Console Log
					printLog(sMethodName, "Importing Competency Name: '" + sCompetencyName + 
							"' Development Activities Statement='" + sDRAStatement + "'");

					boolean isSimilarStringFound = false;
					
					int iPKCompetency = 0;

					//Get Competency ID based on Competency name, company ID and organisation ID
					iPKCompetency = competency.getCompetencyID_Import(sCompetencyName, iCompanyID, iOrgID);

					//Note: DevelopmentActivties is denoted as DRA in othe parts of the code
					Vector vDevelopmentActivties = null;

					//Competency ID Check
					if(iPKCompetency <= 0) {
						//Exception occurs in getting Competency ID
						//sRowErrorStatus = "Invalid CompetencyName at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Competency Name at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Competency Name [" + sCompetencyName + "] does not exists in the database";
						sErrorStatus[2] = "Please update Competency Name and reload";
						//Add Error Log
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					} else {                    
						//Get Development Activties Name Vector List
						vDevelopmentActivties = Utils.getAllRecordsBasedOnLength("DevelopmentActivties", 
								sDRAStatement.length(), 
								"CompetencyID = '" + 
								iPKCompetency + "'", 0,  
								dSimilarityPercentage, iOrgID);
						//vDevelopmentActivties List Null Check
						if(vDevelopmentActivties == null) {
							//SQL Exception occurs in getting vDevelopmentActivties
							//sRowErrorStatus = "SQL Exceptions - Error getting Development Activties List row " + (iCurrentRowIndex + 1);
							sErrorStatus[0] = "SQL Exceptions happen when procssing Row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Error getting Development Activities list for Competency [" + sCompetencyName + "]";
							sErrorStatus[2] = "Please contact administrator";
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} else {
							//Start Development Activities Best Match For-Loop
							DevelopmentActivitiesForLoop: for (int i = 0; i < vDevelopmentActivties.size(); i++) {
								//Get Development Activties
								votblDRA voDA = (votblDRA) vDevelopmentActivties.elementAt(i);
								//Get Current Development Activities Statement (i.e. get element of index i in vDevelopmentActivties)
								String sDRAStatementTemp = voDA.getDRAStatement();

								//Compute LDistance value and get Similartiy Value
								double dSimilarityKBNameValue = Utils.getSimilarityValue(sDRAStatement, sDRAStatementTemp);

								//Development Activties Similarity Check
								if(dSimilarityKBNameValue <= dSimilarityPercentage) {
									//Same Record Check
									if(dSimilarityKBNameValue == 0) {
										//sRowErrorStatus = "Development Activities Statement Similarity Check Failed (Existing Record) at row " + 
										//(iCurrentRowIndex + 1) + " - '" + sDRAStatement + 
										//"' is same as '" + sDRAStatementTemp + "'";
										sErrorStatus[0] = "Development Activities Statement Similarity check Failed at Row " + (iCurrentRowIndex + 1);
										sErrorStatus[1] = "Development Activities [" + sDRAStatement + "] existing in database";
										sErrorStatus[2] = "";
										//Add Duplication Log
										//logDuplication(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
										logError(sMethodName, sErrorStatus);
										iTotalDuplicatedRecords++;
									} else {
										//sRowErrorStatus = "Development Activities Statement Check Failed (Similar Record) at row " + 
										//(iCurrentRowIndex + 1) + " - '" + sDRAStatement + 
										//"' is similar to '" + sDRAStatementTemp+ "'";
										sErrorStatus[0] = "Development Activities statement Similarity check Failed at Row " + (iCurrentRowIndex + 1);
										sErrorStatus[1] = "Development Activities [" + sDRAStatement + "] is similar to [" + sDRAStatementTemp +"] in database";
										sErrorStatus[2] = "";
										//Add Error Log
										//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
										logError(sMethodName, sErrorStatus);
										iTotalErrorRecords++;
									} //End of Same Record Check	
									isSimilarStringFound = true;                            	
									break DevelopmentActivitiesForLoop;
								} //End of Development Activties Similarity Check
							} //End of Development Activties Best Match For-Loop
						} //End of vDevelopmentActivties List Null Check

						//Similar String Found Check
						if(!isSimilarStringFound) {
							//PKUser and PKUserType
							int iPKUser = -1;                    	
							int iPKUserType = -1;

							//if system user is sa = 1, bypass the checking if user exist and instead get the PK of sa. To allow sa to add Develpoment Activities for other organisations, Sebastian 29 July 2010
							if (loginUserType == 1)
							{
								iPKUser = user.getSAPKUser(sUserName); 
							}
							else
							{
								//Get PKUser
								iPKUser = user.checkUserExist(sUserName, iCompanyID, iOrgID);	
							}

							//PKUser Exists Check
							if(iPKUser <= 0) {
								//sRowErrorStatus = "Unable to retrieve User's ID";
								sErrorStatus[0] = "Unable to retrieve User's ID";
								sErrorStatus[1] = "UserName [" + sUserName + "] does not exist in database";
								sErrorStatus[2] = "";
								//Add Error Log
								//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							} else { 
								//Get PKUser Type
								iPKUserType = user.getPKUserType(iPKUser);

								//PKUserType Exists Check
								if(iPKUser <= 0) {
									//sRowErrorStatus = "Unable to retrieve User's Type";
									sErrorStatus[0] = "Unable to retrieve User's Type";
									sErrorStatus[1] = "User's Type was not found for User [" + sUserName + "]";
									sErrorStatus[2] = "";
									//Add Error Log
									//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								} else { 
									if(includeMulLang){
										//Add Development Activities	     
										if(developmentActivities.addRecord(iPKCompetency, sDRAStatement, sDRAStatement2, lang, sIsSystemGenerated, iCompanyID, iOrgID, 
												iPKUser, iPKUserType)){
										//Print to Console Log
											printLog(sMethodName, "Added Competency Name: '" + sCompetencyName + 
													"' Development Activities Statement='" + sDRAStatement + "'");
											iTotalAddedRecords++;
											System.out.println("DRA import correctly");
										}else{
											System.out.println("DRA not import correctly");
										}
									}else{
										//Add Development Activities	     
										if(developmentActivities.addRecord(iPKCompetency, sDRAStatement, sIsSystemGenerated, iCompanyID, iOrgID, 
												iPKUser, iPKUserType)){
										//Print to Console Log
											printLog(sMethodName, "Added Competency Name: '" + sCompetencyName + 
													"' Development Activities Statement='" + sDRAStatement + "'");
											iTotalAddedRecords++;
											System.out.println("DRA import correctly");
										}else{
											System.out.println("DRA not import correctly");
										}
									}
								} //End PKUserType Exists Check
							} //End PKUser Exists Check
						} //End of Similar String Found Check
					} //End of Competency ID Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Department Activities Unsuccessful.";
				else
					sOperationStatus[0] = "Import Department Activities Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Development Activities Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Duplicated:" + iTotalDuplicatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;  
	} //End of importDevelopmentActivities()	

	/**
	 * Remove all Development Activities information specified from the database
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] deleteDevelopmentActivities(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String[] sErrorStatus = new String[3];
		String sMethodName = "deleteDevelopmentActivities"; //For printLog(), logError()

		//Initial validation checkers' objects
		competency = new Competency();
		developmentActivities = new DevelopmentActivities();

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[1];
		
		//Counters
		int iTotalRecords = 0;
		int iTotalDeletedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cCompetencyName = wCurrentSheet.findCell("CompetencyName");
		Cell cDRAStatement = wCurrentSheet.findCell("DRAStatement");
		
		//Get Cells of non-Critical Column
		Cell cDRAStatement2 = wCurrentSheet.findCell("DRAStatement2");
		Cell cLang = wCurrentSheet.findCell("Lang");

		//Get Column ID of Critical Column
		int iColCompetencyName = -1;
		int iColDRAStatement = -1;

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - CompetencyName 
			if(cCompetencyName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "CompetencyName";
			} else {
				iColCompetencyName = cCompetencyName.getColumn();
			} //End of Critical Column Missing Check - CompetencyName 

			//Critical Column Missing Check - DRAStatement 
			if(cDRAStatement == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "DRAStatement";
			} else {
				iColDRAStatement = cDRAStatement.getColumn();
			} //End of Critical Column Missing Check - DRAStatement 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				logError(sMethodName, sErrorStatus);
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			iStartRowIndex = cCompetencyName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sCompetencyName = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyName, iCurrentRowIndex).getContents().trim());
				String sDRAStatement = Utils.SQLFixer(wCurrentSheet.getCell(iColDRAStatement, iCurrentRowIndex).getContents().trim());

				//Development Activitise
				int iPKDevAct = -1;

				//Check for Empty String for Critical Columns
				if(sCompetencyName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid CompetencyName - Empty String at row " + (iCurrentRowIndex + 1);					
					sErrorStatus[0] = "Invalid Competency Name Detected";
					sErrorStatus[1] = "Competency Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Competency Name and reload";
					isInvalidDataRow = true;
				} else if(sDRAStatement.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DRAStatement - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid DRAStatement Detected";
					sErrorStatus[1] = "DRAStatement is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update DRAStatement and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Print to Console Log
					printLog(sMethodName, "Deleting Competency Name: '" + sCompetencyName + 
							"' Development Activities Statement='" + sDRAStatement + "'");  
					
					//Get Competency ID
					int iPKCompetency = competency.getCompetencyID_Import(sCompetencyName, iCompanyID, iOrgID);

					//Competency ID Check
					if(iPKCompetency <= 0) {
						//Exception occurs in getting Competency ID
						//sRowErrorStatus = "Invalid CompetencyName at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Competency Name at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Competency Name [" + sCompetencyName + "] does not exists in the database";
						sErrorStatus[2] = "Please update Competency Name and reload";
						//Add Error Log
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					} else {                    
						//Get Development Activities ID
						iPKDevAct = developmentActivities.getDRAID(iPKCompetency, sDRAStatement, iCompanyID, iOrgID);
					} //End of Competency ID Check

					//Development Activities ID Exisits Check
					if(iPKDevAct <= 0) {
						//Invalid Development Activities ID
						//sRowErrorStatus = "Invalid Development Activities ID - Error getting Development Activties ID at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Development Activities at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Development Activities [" + sDRAStatement + "] does not exists in the database";
						sErrorStatus[2] = "Please update Development Activities and reload";
						//Add Error Log
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					} else {
						developmentActivities.importDeleteRecord(iPKCompetency,sDRAStatement, iCompanyID, iOrgID);
						//Print to Console Log
						printLog(sMethodName,"Delete Development Activities [ID:" + iPKDevAct + 
						"] Successfully");
						iTotalDeletedRecords++;
					} //End of Development Activities ID Exisits Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				sOperationStatus[0] = "Delete Development Activities Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Delete Development Activities Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Deleted:" + iTotalDeletedRecords +
			", Total Errors:" + iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;   
	} //End of deleteDevelopmentActivities()

	/**
	 * Import Development Resources information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	//Added a new parameter loginUserType. To determine if system user is sa, Sebastian 29 July 2010
	public String[] importDevelopmentResources(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets, int loginUserType) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String[] sErrorStatus = new String[3];
		String sMethodName = "importDevelopmentResources"; //For printLog(), logError(), logDuplication

		//Initial validation checkers' objects
		competency = new Competency();
		developmentResources= new DevelopmentResources();
		user = new User();

		//Similarity Percentage
		double dSimilarityPercentage = 5.0;

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cCompetencyName = wCurrentSheet.findCell("CompetencyName");
		Cell cResource = wCurrentSheet.findCell("Resource");
		Cell cResType= wCurrentSheet.findCell("ResType");
		//Add new variable cIsSystemGenerated. To capture the cell column IsSystemGenerated in the import file, Sebastian 29 July 2010
		Cell cIsSystemGenerated = wCurrentSheet.findCell("IsSystemGenerated");
		
		//Get Cells of non-Critical Column
		Cell cResource2 = wCurrentSheet.findCell("Resource2");
		Cell cLang = wCurrentSheet.findCell("Language");

		//Get Column ID of Critical Column
		int iColCompetencyName = -1;
		int iColResource  = -1;
		int iColResType = -1;
		//Add new variable iIsSystemGenerated. To capture the column index of IsSystemGenerated in the import file, Sebastian 29 July 2010
		int iIsSystemGenerated = -1;
		
		//Get Column ID of non-Critical Column
		int iColResource2 = -1;
		int iColLang = -1;
		
		boolean includeMulLang = false;

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - CompetencyName 
			if(cCompetencyName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "CompetencyName";
			} else {
				iColCompetencyName = cCompetencyName.getColumn();
			} //End of Critical Column Missing Check - CompetencyName 

			//Critical Column Missing Check - Resource 
			if(cResource == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "Resource";
			} else {
				iColResource = cResource.getColumn();
			} //End of Critical Column Missing Check - Resource 

			//Critical Column Missing Check - ResType 
			if(cResType == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "ResType";
			} else {
				iColResType = cResType.getColumn();
			} //End of Critical Column Missing Check - ResType 
			
			//Critical Column Missing Check - cIsSystemGenerated, Sebastian 29 July 2010
			if(cIsSystemGenerated == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "IsSystemGenerated";
			} else {
				iIsSystemGenerated = cIsSystemGenerated.getColumn();
			} //End of Critical Column Missing Check - DRAStatement 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				logError(sMethodName, sErrorStatus);
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check
			
			if(cResource2 != null && cLang != null){
				iColResource2 = cResource2.getColumn();
				iColLang = cLang.getColumn();
				includeMulLang = true;
			}

			//Set Start & Current Row Index
			iStartRowIndex = cCompetencyName.getRow() + 1;
			System.out.println("StartRowIndex: " + iStartRowIndex);
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {

				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sCompetencyName = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyName, iCurrentRowIndex).getContents().trim());
				if(sCompetencyName.length() == 0) break; // end of file
				String sResource = Utils.SQLFixer(wCurrentSheet.getCell(iColResource, iCurrentRowIndex).getContents().trim());
				String sResType = Utils.SQLFixer(wCurrentSheet.getCell(iColResType, iCurrentRowIndex).getContents().trim());
				//Add new variable sIsSystemGenerated. To capture the cell value in the IsSystemGenerated column in the import file, Sebastian 29 July 2010
				String sIsSystemGenerated = Utils.SQLFixer(wCurrentSheet.getCell(iIsSystemGenerated, iCurrentRowIndex).getContents().trim());

				String sResource2 = null;
				int lang = 0;
				if(includeMulLang){
					sResource2 = Utils.SQLFixer(wCurrentSheet.getCell(iColResource2, iCurrentRowIndex).getContents().trim());
					lang = Integer.parseInt(Utils.SQLFixer(wCurrentSheet.getCell(iColLang, iCurrentRowIndex).getContents().trim()));
				}

				//Check for Empty String for Critical Columns
				if(sCompetencyName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid CompetencyName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Competency Name Detected";
					sErrorStatus[1] = "Competency Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Competency Name and reload";
					isInvalidDataRow = true;
				} else if(sResource.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid Resource - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Resource Detected";
					sErrorStatus[1] = "Resource is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Resource and reload";
					isInvalidDataRow = true;
				} else if(sResType.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid ResType - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Resource Type Detected";
					sErrorStatus[1] = "Resource Type is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Resource Type and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Print to Console Log
					printLog(sMethodName, "Importing Competency Name: '" + sCompetencyName + 
							"' Development Resources ='" + sResource + "' Resource Type='" + 
							sResType + "'");

					boolean isSimilarStringFound = false;

					int iPKCompetency = 0;
					
					//Get Competency ID based on Competency name, company ID and organisation ID
					iPKCompetency = competency.getCompetencyID_Import(sCompetencyName, iCompanyID, iOrgID);
				
					//Get Competency & Resource Type ID
					int iPKResType = 0;                    
					//Note: DevelopmentResources is denoted as DRARes in othe parts of the code
					Vector vDevelopmentResources = null;

					//Get Resource Type ID
					if (sResType.equals("Book"))
						iPKResType = 1;
					else if (sResType.equals("Web Resource"))
						iPKResType = 2;
					else if (sResType.equals("Training Course"))
						iPKResType = 3;
					else if (sResType.equals("AV Resource")) // Change Resource category from "In-house Resource" to "AV Resource", Desmond 10 May 2011
						iPKResType = 4;
					//End of Get Resource Type ID

					//Competency ID Check
					if(iPKCompetency <= 0) {
						//Exception occurs in getting Competency ID
						//sRowErrorStatus = "Invalid CompetencyName at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Competency Name at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Competency Name [" + sCompetencyName + "] does not exists in the database";
						sErrorStatus[2] = "Please update Competency Name and reload";
						//Add Error Log
						logError(sMethodName, sErrorStatus);
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						iTotalErrorRecords++;
					} else if(iPKResType <= 0) { 
						//Exception occurs in getting Competency Type
						//sRowErrorStatus = "Invalid ResType at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Resource Type at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Resource Type [" + sResType +"] does not exists in the database";
						sErrorStatus[2] = "Please update Resource Type and reload";
						//Add Error Log
						logError(sMethodName, sErrorStatus);
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						iTotalErrorRecords++;
					} else {                    
						//Get Development Resources Vector List
						vDevelopmentResources = Utils.getAllRecordsBasedOnLength("DevelopmentResources", 
								sResource.length(), 
								"CompetencyID = '" + 
								iPKCompetency + "' AND ResType = " +
								iPKResType, 0,  
								dSimilarityPercentage, iOrgID);

						//vDevelopmentResources List Null Check
						if(vDevelopmentResources == null) {
							//SQL Exception occurs in getting vDevelopmentResources
							//sRowErrorStatus = "SQL Exceptions - Error getting Development Resources List row " + (iCurrentRowIndex + 1);
							sErrorStatus[0] = "Unable to retrieve Development Resources at Row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = " Development Resources [" + sResource +"] does not exists in database";
							sErrorStatus[2] = " Please Update Development Resources and reload";
							//Add Error Log
							logError(sMethodName, sErrorStatus);
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							iTotalErrorRecords++;
						} else {
							//Start Development Resources Best Match For-Loop
							DevelopmentResourcesForLoop: for (int i = 0; i < vDevelopmentResources.size(); i++) {
								//Get Development Resources
								votblDRARES voDR = (votblDRARES) vDevelopmentResources.elementAt(i);
								//Get Current Development Resources Statement (i.e. get element of index i in vDevelopmentResources)
								String sResourceTemp = voDR.getResource();

								//Compute LDistance value and get Similartiy Value
								double dSimilarityKBNameValue = Utils.getSimilarityValue(sResource, sResourceTemp);

								//Development Resources Similarity Check
								if(dSimilarityKBNameValue <= dSimilarityPercentage) {
									//Same Record Check
									if(dSimilarityKBNameValue == 0) {
										//sRowErrorStatus = "Development Resources Statement Similarity Check Failed (Existing Record) at row " + 
										//(iCurrentRowIndex + 1) + " - " + sResource + 
										//" is same as " + sResourceTemp;
										sErrorStatus[0] = "Development Resources Statement Similarity Check Failed at Row" + (iCurrentRowIndex + 1);
										sErrorStatus[1] = "Development Resources Statement [" + sResource + "] is duplicate to existing Record ["+sResourceTemp+"] in database";
										sErrorStatus[2] = "";
										//Add Duplication Log
										logError(sMethodName, sErrorStatus);
										//logDuplication(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
										iTotalDuplicatedRecords++;
									} else {
										//sRowErrorStatus = "Development Resources Statement Check Failed (Similar Record) at row " + 
										//(iCurrentRowIndex + 1) + " - " + sResource + 
										//" is similar to " + sResourceTemp;
										sErrorStatus[0] = "Development Resources Statement Similarity Check Failed at Row " + (iCurrentRowIndex + 1);
										sErrorStatus[1] = "Development Resources Statement [" + sResource + "] is similar to existing Record [" +sResourceTemp+"] in database";
										sErrorStatus[2] = "";
										//Add Error Log
										logError(sMethodName, sErrorStatus);
										//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
										iTotalErrorRecords++;
									} //End of Same Record Check	
									isSimilarStringFound = true;	                                
									break DevelopmentResourcesForLoop;
								} //End of Development Resources Name Similarity Check
							} //End of Development Resources Best Match For-Loop
						} //End of vDevelopmentResources List Null Check

						//Similar String Found Check
						if(!isSimilarStringFound && !isInvalidDataRow) {
							//PKUser and PKUserType
							int iPKUser = -1;                    	
							int iPKUserType = -1;
							
							//if system user is sa, bypass the checking if user exist and instead get the PK of sa. To allow sa to add DR for other organisations, Sebastian 29 July 2010
							if (loginUserType == 1)
							{
								iPKUser = user.getSAPKUser(sUserName); 
							}
							else
							{
								//Get PKUser
								iPKUser = user.checkUserExist(sUserName, iCompanyID, iOrgID);
							}

							//PKUser Exists Check
							if(iPKUser <= 0) {
								//sRowErrorStatus = "Unable to retrieve User's ID";
								sErrorStatus[0] = "Invalid User Name";
								sErrorStatus[1] = "User [" + sUserName + "] does not exist in database";
								sErrorStatus[2] = "Please update the User and reload";
								//Add Error Log
								logError(sMethodName, sErrorStatus);
								//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
								iTotalErrorRecords++;
							} //End PKUser Exists Check

							//Get PKUser Type
							iPKUserType = user.getPKUserType(iPKUser);

							//PKUserType Exists Check
							if(iPKUserType <= 0) {
								//sRowErrorStatus = "Unable to retrieve User's Type";
								sErrorStatus[0] = "Unable to retrieve User's Type";
								sErrorStatus[1] = "Unknown reason";
								sErrorStatus[2] = "Please contact administrator";
								//Add Error Log
								logError(sMethodName, sErrorStatus);
								//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
								iTotalErrorRecords++;
							} else { 
								//Add Development Resources	  
								if(includeMulLang){
									developmentResources.addRecord(iPKCompetency, sResource, sResource2, lang, iPKResType, sIsSystemGenerated, iCompanyID, iOrgID, 
											iPKUser, iPKUserType);
								}else{
									developmentResources.addRecord(iPKCompetency, sResource, iPKResType, sIsSystemGenerated, iCompanyID, iOrgID, 
											iPKUser, iPKUserType);
								}
								//Print to Console Log
								printLog(sMethodName, "Added Competency Name: '" + sCompetencyName + "' " +
										"Development Resources ='" + sResource + "' Resource Type='" + 
										sResType + "'");
								iTotalAddedRecords++;
							} //End PKUserType Exists Check
						} //End of Similar String Found Check

					} //End of Competency ID Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Development Resources Unsuccessful.";
				else
					sOperationStatus[0] = "Import Development Resources Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Development Resources Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Duplicated:" +  iTotalDuplicatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;  
	} //End of importDevelopmentResources()	

	/**
	 * Remove all Development Resources information specified from the database
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] deleteDevelopmentResources(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String [] sErrorStatus = new String[3];
		String sMethodName = "deleteDevelopmentResources"; //For printLog(), logError()

		//Initial validation checkers' objects
		competency = new Competency();
		developmentResources = new DevelopmentResources();

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalDeletedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		//Get Cells of Critical Column
		Cell cCompetencyName = wCurrentSheet.findCell("CompetencyName");
		Cell cResource = wCurrentSheet.findCell("Resource");
		Cell cResType= wCurrentSheet.findCell("ResType");

		//Get Column ID of Critical Column
		int iColCompetencyName = -1;
		int iColResource  = -1;
		int iColResType = -1;

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - CompetencyName 
			if(cCompetencyName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "CompetencyName";
			} else {
				iColCompetencyName = cCompetencyName.getColumn();
			} //End of Critical Column Missing Check - CompetencyName 

			//Critical Column Missing Check - Resource 
			if(cResource == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "Resource";
			} else {
				iColResource = cResource.getColumn();
			} //End of Critical Column Missing Check - Resource 

			//Critical Column Missing Check - ResType 
			if(cResType == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "ResType";
			} else {
				iColResType = cResType.getColumn();
			} //End of Critical Column Missing Check - ResType 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			iStartRowIndex = cCompetencyName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);
			
			System.out.println("Number of Rows: " + iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {

				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sCompetencyName = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyName, iCurrentRowIndex).getContents().trim());
				String sResource = Utils.SQLFixer(wCurrentSheet.getCell(iColResource, iCurrentRowIndex).getContents().trim());
				String sResType = Utils.SQLFixer(wCurrentSheet.getCell(iColResType, iCurrentRowIndex).getContents().trim());

				//Development Resources
				int iPKDevRes = -1;
				int iPKResType = 0;

				//Check for Empty String for Critical Columns
				if(sCompetencyName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid CompetencyName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Competency Name Detected";
					sErrorStatus[1] = "Competency Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Competency Name and reload";
					isInvalidDataRow = true;
				} else if(sResource.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid Resource - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Resource Detected";
					sErrorStatus[1] = "Resource is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Resource and reload";
					isInvalidDataRow = true;
				} else if(sResType.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid ResType - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Resource Type Detected";
					sErrorStatus[1] = "Resource Type is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Resource Type and reload";
					isInvalidDataRow = true;
				} else { 
					//Get Resource Type ID
					if (sResType.equals("Book"))
						iPKResType = 1;
					else if (sResType.equals("Web Resource"))
						iPKResType = 2;
					else if (sResType.equals("Training Course"))
						iPKResType = 3;
					else if (sResType.equals("AV Resource")) // Change Resource category from "In-house Resource" to "AV Resource", Desmond 10 May 2011
						iPKResType = 4;
					else {
						//sRowErrorStatus = "Invalid ResType at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Resource Type Detected";
						sErrorStatus[1] = "Resource Type [" + sResType + "] does not exists in the database";
						sErrorStatus[2] = "Please update Resource Type and reload";
						isInvalidDataRow = true;
					}//End of Get Resource Type ID
				} //End of Check for Empty String for Critical Columns

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;

				} else {
					//Print to Console Log
					printLog(sMethodName,"Deleting Competency Name: '" + sCompetencyName + 
							"' Development Resources Statement='" + sResource + "' Resource Type='" + 
							sResType + "'");                   

					//Get Competency ID
					int iPKCompetency = competency.getCompetencyID_Import(sCompetencyName, iCompanyID, iOrgID);

					//Competency ID Check
					if(iPKCompetency <= 0) {
						//Exception occurs in getting Competency ID
						//sRowErrorStatus = "Invalid CompetencyName at row " + (iCurrentRowIndex + 1);
						//Add Error Log
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						sErrorStatus[0] = "Invalid Competency Name at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Competency Name [" + sCompetencyName + "] does not exists in the database";
						sErrorStatus[2] = "Please update Competency Name and reload";
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++; 
					} else {                    
						//Get Development Resources ID
						iPKDevRes = developmentResources.checkExist(iPKCompetency, sResource, iPKResType, iCompanyID, iOrgID);
						//Development Resources ID Exisits Check
						if(iPKDevRes <= 0) {
							//Invalid Development Resources ID
							//sRowErrorStatus = "Invalid Development Resources ID - Error getting Development Resources ID at row " + (iCurrentRowIndex + 1);
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							sErrorStatus[0] = "Invalid Development Resources at Row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Development Resources [" + sResource + "] does not exists in the database";
							sErrorStatus[2] = "Please update Development Resources and reload";
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;                        
						} else {
							developmentResources.importDeleteRecord(iPKCompetency, sResource, iPKResType, iCompanyID, iOrgID);
							//Print to Console Log
							printLog(sMethodName,"Deleted Development Resources [ID:" + iPKDevRes + 
							"] Successfully");
							iTotalDeletedRecords++;
						} //End of Development Resources ID Exisits Check
					} //End of Competency ID Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				sOperationStatus[0] = "Delete Development Resources Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Delete Development Resources Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Deleted:" + iTotalDeletedRecords +
			", Total Errors:" + iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;   
	} //End of deleteDevelopmentActivities()

	/**
	 * Import division information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] importDivision(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String [] sErrorStatus = new String[3];
		String sMethodName = "importDivision"; //For printLog(), logError()

		//Initial validation checkers' objects
		division = new Division();
		user = new User();

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cDivisionName = wCurrentSheet.findCell("DivisionName");
		Cell cDivisionCode = wCurrentSheet.findCell("DivisionCode");

		//Get Column ID of Critical Column
		int iColDivisionName = -1;
		int iColDivisionCode = -1;

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - DivisionName 
			if(cDivisionName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "DivisionName";
			} else {
				iColDivisionName = cDivisionName.getColumn();
			} //End of Critical Column Missing Check - DivisionName 

			//Critical Column Missing Check - DivisionCode 
			if(cDivisionCode == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "DivisionCode";
			} else {
				iColDivisionCode = cDivisionCode.getColumn();
			} //End of Critical Column Missing Check - DivisionCode 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				logError(sMethodName, sErrorStatus);
				
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			iStartRowIndex = cDivisionName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Division/User ID
				int iPKDivision = -1;
				int iPKUser = -1;

				//Get Division Info from Excel file	- Critical Column	
				String sDivisionName = Utils.SQLFixer(wCurrentSheet.getCell(iColDivisionName, iCurrentRowIndex).getContents().trim());
				String sDivisionCode = Utils.SQLFixer(wCurrentSheet.getCell(iColDivisionCode, iCurrentRowIndex).getContents().trim());


				//Check for Empty String for Critical Columns
				if(sDivisionName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DivisionName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Division Name";
					sErrorStatus[1] = " Division Name is empty at Row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = " Please update Division Name and reload";
					isInvalidDataRow = true;
				} else if(sDivisionCode.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DivisionCode - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Division Code";
					sErrorStatus[1] = " Division Code is empty at Row " + (iCurrentRowIndex + 1);
					sErrorStatus[2] = " Please update Division Code and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Check Division ID
					iPKDivision = division.checkDivExist(sDivisionName, iOrgID);
					iPKUser = user.checkUserExist(sUserName, iCompanyID, iOrgID);

					//User Exist Check
					if(iPKUser <= 0) {
						//Invalid User
						//sRowErrorStatus = "Invalid User at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid User at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = " User [" + sUserName +"] does not exists in database";
						sErrorStatus[2] = "Please update user and reload";
						//Add Error Log
						logError(sMethodName, sErrorStatus);
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						iTotalErrorRecords++;
					} else { 
						//Division Exist Check
						if(iPKDivision <= 0) {
							//Add Division Check
							if(division.addRecord(sDivisionName, sDivisionCode, iOrgID, iPKUser)) {
								//Add Division Successful
								iPKDivision = division.checkDivExist(sDivisionName, sDivisionCode, iOrgID);
								iTotalAddedRecords++;
							} else {
								//Add Division Failed
								//sRowErrorStatus = "Add Division Record Failed at row " + (iCurrentRowIndex + 1);
								sErrorStatus[0] = "Add Division Failed at Row " + (iCurrentRowIndex + 1);
								sErrorStatus[1] = "Unknown reason";
								sErrorStatus[2] = "Please contact administrator";
								//Add Error Log
								logError(sMethodName, sErrorStatus);
								//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
								iTotalErrorRecords++;
							} //End of Add Division Check
						} else {
							//Edit Division Check
							if(division.editRecord(iPKDivision, sDivisionName, sDivisionCode, iOrgID, iPKUser)) {
								//Edit Division Successful
								iTotalUpdatedRecords++;
							} else {
								//Edit Division Failed
								//sRowErrorStatus = "Edit Division Record Failed at row " + (iCurrentRowIndex + 1);
								sErrorStatus[0] = "Edit Division Failed at Row " + (iCurrentRowIndex + 1);
								sErrorStatus[1] = "Unknown reason";
								sErrorStatus[2] = "Please contact administrator";
								//Add Error Log
								logError(sMethodName, sErrorStatus);
								//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
								iTotalErrorRecords++;
							}//End of Edit Division Check
						} //End of Division Exist Check
					} //End of User Exist Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Division Unsuccessful.";
				else
					sOperationStatus[0] = "Import Division Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Division Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Edited:" + iTotalUpdatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;   
	} //End of importDivision()

	/**
	 * Import department information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] importDepartment(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String[] sErrorStatus = new String[3];
		String sMethodName = "importDepartment"; //For printLog(), logError()

		//Initial validation checkers' objects
		division = new Division();
		department = new Department();
		user = new User();

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cDivisionName = wCurrentSheet.findCell("DivisionName");
		Cell cDepartmentName = wCurrentSheet.findCell("DepartmentName");
		Cell cDepartmentCode = wCurrentSheet.findCell("DepartmentCode");

		//Get Column ID of Critical Column
		int iColDivisionName = -1;
		int iColDepartmentName = -1;
		int iColDepartmentCode = -1;

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - DivisionName 
			if(cDivisionName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "DivisionName";
			} else {
				iColDivisionName = cDivisionName.getColumn();
			} //End of Critical Column Missing Check - DivisionName 

			//Critical Column Missing Check - DepartmentName 
			if(cDepartmentName == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "DepartmentName";
			} else {
				iColDepartmentName = cDepartmentName.getColumn();
			} //End of Critical Column Missing Check - DepartmentName 

			//Critical Column Missing Check - DepartmentCode 
			if(cDepartmentCode == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "DepartmentCode";
			} else {
				iColDepartmentCode = cDepartmentCode.getColumn();
			} //End of Critical Column Missing Check - DepartmentCode 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Set Import Info Message
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			iStartRowIndex = cDivisionName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Division/Department/User ID
				int iPKDivision = -1;
				int iPKDepartment = -1;
				int iPKUser = -1;

				//Get Department Info from Excel file	- Critical Column	
				String sDivisionName = Utils.SQLFixer(wCurrentSheet.getCell(iColDivisionName, iCurrentRowIndex).getContents().trim());
				String sDepartmentName = Utils.SQLFixer(wCurrentSheet.getCell(iColDepartmentName, iCurrentRowIndex).getContents().trim());
				String sDepartmentCode = Utils.SQLFixer(wCurrentSheet.getCell(iColDepartmentCode, iCurrentRowIndex).getContents().trim());

				//Check for Empty String for Critical Columns
				if(sDivisionName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DivisionName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Division Name Detected";
					sErrorStatus[1] = "Division Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Division Name and reload";
					isInvalidDataRow = true;
				} else if(sDepartmentName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DepartmentName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Department Name Detected";
					sErrorStatus[1] = "Department Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Department Name and reload";
					isInvalidDataRow = true;
				} else if(sDepartmentCode.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DepartmentCode - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Department Code Detected";
					sErrorStatus[1] = "Department Code is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Department Code and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Print to Console Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;;
				} else {
					//Check Division/Department/User ID
					iPKDivision = division.checkDivExist(sDivisionName, iOrgID);

					//Division Exist Check
					if(iPKDivision <= 0) {
						//Invalid Division
						//sRowErrorStatus = "Invalid Division at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Division at Row " + (iCurrentRowIndex + 1);
						sErrorStatus[1] = "Division [" + sDivisionName +"] does not exist in database";
						sErrorStatus[2] = "Please update Division and reload";
						logError(sMethodName, sErrorStatus);
						//Print to Console Log
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						iTotalErrorRecords++;
					} else {
						iPKDepartment = department.checkDeptExist(sDepartmentName, iPKDivision, iOrgID);
						iPKUser = user.checkUserExist(sUserName, iCompanyID, iOrgID);

						//User Exist Check
						if(iPKUser <= 0) {
							//Invalid User
							//sRowErrorStatus = "Invalid User at row " + (iCurrentRowIndex + 1);
							//Print to Console Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							sErrorStatus[0] = "Invalid User at Row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "User [" + sUserName +"] does not exist in database";
							sErrorStatus[2] = "Please update User and reload";
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						} else { 
							//Department Exist Check
							if(iPKDepartment <= 0) {
								//Add Department Check
								if(department.addRecord(sDepartmentName, sDepartmentCode, iOrgID, iPKUser, iPKDivision)) {
									//Add Department Successful
									iPKDepartment = department.checkDeptExist(sDepartmentName, iPKDivision, iOrgID);
									iTotalAddedRecords++;
									//Print to Console Log
									printLog(sMethodName, "Add Department Successful (ID:" + iPKDepartment + 
											") at row " + (iCurrentRowIndex + 1));
								} else {
									//Add Department Failed
									//sRowErrorStatus = "Add Department Record Failed at row " + (iCurrentRowIndex + 1);
									//Print to Console Log
									//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
									sErrorStatus[0] = "Add Department failed at Row " + (iCurrentRowIndex + 1);
									sErrorStatus[1] = "Unknown reason";
									sErrorStatus[2] = "Please contact administrator";
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								} //End of Add Department Check
							} else {
								//Edit Department Check
								if(department.editRecord(iPKDepartment, sDepartmentName, sDepartmentCode, iOrgID, iPKUser)) {
									//Edit Department Successful
									iTotalUpdatedRecords++;
									//Print to Console Log
									printLog(sMethodName, "Edit Department Successful (ID:" + iPKDepartment + 
											") at row " + (iCurrentRowIndex + 1));
								} else {
									//Edit Department Failed
									//sRowErrorStatus = "Edit Department Record Failed at row " + (iCurrentRowIndex + 1);
									//Print to Console Log
									//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
									sErrorStatus[0] = "Edit Department failed at Row " + (iCurrentRowIndex + 1);
									sErrorStatus[1] = "Unknown reason";
									sErrorStatus[2] = "Please contact administrator";
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;;
								}//End of Edit Department Check
							} //End of Department Exist Check
						} //End of User Exist Check
					}//End of Division Exist Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Department Unsuccessful.";
				else
					sOperationStatus[0] = "Import Department Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Department Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Edited:" + iTotalUpdatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;            
	} //End of importDepartment()

	/**
	 * Import group information into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 */
	public String[] importGroup(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String[] sErrorStatus = new String[3];
		String sMethodName = "importGroup"; //For printLog(), logError()

		//Initial validation checkers' objects
		division = new Division();
		department = new Department();
		group = new Group();
		user = new User();

		//Start Row & Current Row index
		int iStartRowIndex = 0;
		int iCurrentRowIndex = 0;

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Get Cells of Critical Column
		Cell cDivisionName = wCurrentSheet.findCell("DivisionName");
		Cell cDepartmentName = wCurrentSheet.findCell("DepartmentName");
		Cell cGroupName = wCurrentSheet.findCell("GroupName");

		//Get Column ID of Critical Column
		int iColDivisionName = -1;
		int iColDepartmentName = -1;
		int iColGroupName = -1;

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - DivisionName 
			if(cDivisionName == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "DivisionName";
			} else {
				iColDivisionName = cDivisionName.getColumn();
			} //End of Critical Column Missing Check - DivisionName 

			//Critical Column Missing Check - DepartmentName 
			if(cDepartmentName == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "DepartmentName";
			} else {
				iColDepartmentName = cDepartmentName.getColumn();
			} //End of Critical Column Missing Check - DepartmentName 

			//Critical Column Missing Check - GroupName 
			if(cGroupName == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing = "GroupName";
			} else {
				iColGroupName = cGroupName.getColumn();
			} //End of Critical Column Missing Check - GroupName 

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			iStartRowIndex = cDivisionName.getRow() + 1;
			iCurrentRowIndex = iStartRowIndex;
			iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);

			//Data Row While-Loop
			while (iCurrentRowIndex < wCurrentSheet.getRows()) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				//String sRowErrorStatus = "";

				//Division/Department/User ID
				int iPKDivision = -1;
				int iPKDepartment = -1;
				int iPKGroup = -1;
				int iPKUser = -1;

				//Get Department Info from Excel file	- Critical Column	
				String sDivisionName = Utils.SQLFixer(wCurrentSheet.getCell(iColDivisionName, iCurrentRowIndex).getContents().trim());
				String sDepartmentName = Utils.SQLFixer(wCurrentSheet.getCell(iColDepartmentName, iCurrentRowIndex).getContents().trim());
				String sGroupName = Utils.SQLFixer(wCurrentSheet.getCell(iColGroupName, iCurrentRowIndex).getContents().trim());

				//Check for Empty String for Critical Columns
				if(sDivisionName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DivisionName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Division Name Detected";
					sErrorStatus[1] = "Division Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Division Name and reload";
					isInvalidDataRow = true;
				} else if(sDepartmentName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid DepartmentName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Department Name  Detected";
					sErrorStatus[1] = "Department Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Department Name and reload";
					isInvalidDataRow = true;
				} else if(sGroupName.equalsIgnoreCase("")) {
					//sRowErrorStatus = "Invalid GroupName - Empty String at row " + (iCurrentRowIndex + 1);
					sErrorStatus[0] = "Invalid Group Name  Detected";
					sErrorStatus[1] = "Group Name is empty at Row "+(iCurrentRowIndex + 1);
					sErrorStatus[2] = "Please update Group Name and reload";
					isInvalidDataRow = true;
				} //End of Check for Empty String for Critical Columns

				IsInvalidDataRow: if(isInvalidDataRow) {
					//Add Error Log
					//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
					logError(sMethodName, sErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Check Division/Department/User ID
					iPKDivision = division.checkDivExist(sDivisionName, iOrgID);

					//Division Exist Check
					if(iPKDivision <= 0) {
						//Invalid Division
						//sRowErrorStatus = "Invalid Division at row " + (iCurrentRowIndex + 1);
						sErrorStatus[0] = "Invalid Division Detected at row " +(iCurrentRowIndex + 1);
						sErrorStatus[1] = "Division ["+sDivisionName+"] does not exist in database";
						sErrorStatus[2] = "Please update Division and reload";
						//Add Error Log
						//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					} else {
						iPKDepartment = department.checkDeptExist(sDepartmentName, iPKDivision, iOrgID);

						//Department Exist Check
						if(iPKDepartment <= 0) {
							//Invalid Division
							//sRowErrorStatus = "Invalid Department at row " + (iCurrentRowIndex + 1);
							sErrorStatus[0] = "Invalid Department at Row " + (iCurrentRowIndex + 1);
							sErrorStatus[1] = "Department [" + sDepartmentName+"] does not exist in database";
							sErrorStatus[2] = "Please update Department and reload";
							logError(sMethodName, sErrorStatus);
							//Add Error Log
							//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
							iTotalErrorRecords++;
						} else {
							iPKGroup = group.checkGroupExist(sGroupName, iOrgID, iPKDepartment);
							iPKUser = user.checkUserExist(sUserName, iCompanyID, iOrgID);

							//User Exist Check
							if(iPKUser <= 0) {
								//Invalid User
								//sRowErrorStatus = "Invalid User at row " + (iCurrentRowIndex + 1);
								//Add Error Log
								//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
								sErrorStatus[0] = "Invalid User at Row " + (iCurrentRowIndex + 1);
								sErrorStatus[1] = "User [" + sUserName +"] does not exist in database";
								sErrorStatus[2] = "Please update User and reload";
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							} else {                                 
								//Group Exist Check
								if(iPKGroup <= 0) {
									//Add Group Check
									if(group.addRecord(sGroupName, iOrgID, iPKUser, iPKDepartment)) {
										//Add Group Successful
										iPKDepartment = department.checkDeptExist(sDepartmentName, iPKDivision, iOrgID);
										iTotalAddedRecords++;
									} else {
										//Add Group Failed
										//sRowErrorStatus = "Add Group Record Failed at row " + (iCurrentRowIndex + 1);
										//Add Error Log
										//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
										sErrorStatus[0] = "Add Group Record Failed at Row" + (iCurrentRowIndex + 1);
										sErrorStatus[1] = "Unknown reason";
										sErrorStatus[2] = "Please contact administrator";
										logError(sMethodName, sErrorStatus);
										iTotalErrorRecords++;
									} //End of Add Group Check
								} else {
									//Add Group-Failed - Record Exists
									//sRowErrorStatus = "Add Group Record Failed at row " + (iCurrentRowIndex + 1) +
									//" - Record Exists";
									//Add Error Log
									//logError(sMethodName, sRowErrorStatus, iCompanyID, iOrgID);
									sErrorStatus[0] = "Group Record exists";
									sErrorStatus[1] = "Add Group record failed at Row " + (iCurrentRowIndex + 1)+" because it already exists in database";
									sErrorStatus[2] = "";
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								} //End of Group Exist Check
							} //End of User Exist Check
						}//End of Department Exist Check
					}//End of Division Exist Check
				} //End of Invalid Data Row Check
				iCurrentRowIndex++;
			} //End of Data Row While-Loop

			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Group/Section Unsuccessful.";
				else
					sOperationStatus[0] = "Import Group/Section Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Group/Section Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			String sRecordsStatus = "Total Records:" + iTotalRecords + ", Total Added:" + iTotalAddedRecords +
			", Total Edited:" + iTotalUpdatedRecords + ", Total Errors:" + 
			iTotalErrorRecords;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			//Append the Records Status to the end of sOperationStatus[1];
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (IOException e) {
			//If I/O Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an I/O Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;
	} //End of importGroup()

	/**
	 * retrieve the max rating value according to scaleID
	 * @param iScaleID
	 * @return max Rating Value
	 * @throws SQLException
	 * @author Qiao Li 05 Jan 2010
	 */
	public int maxRS(int iScaleID) throws SQLException {
		int max = 0;

		String query = "SELECT MAX(tblScaleValue.HighValue) AS MAXIMUM ";
		query += "FROM tblScaleValue WHERE ";
		query += "ScaleID = " + iScaleID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try 
		{          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				max = rs.getInt(1);

		}
		catch(Exception E) 
		{

			System.err.println("Import.java - maxRS - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}

		return max;
	}
	/**
	 * Import Questionnaire ratings into the database from File
	 * @param sFileName   - The file name of the Excel file containing the data
	 * @param iCompanyID  - The Company ID
	 * @param iOrgID      - The Organisation ID
	 * @param wSheets     - Contains the worksheets of the workbook
	 * @return A String[] containing the Import Status Message and Import Error message
	 * @author Qiao Li 31 Dec 2009
	 * precondition: the imported file should follow the format of QuestionnaireTemplate.xls
	 */
	public String[] importQuestionnaire(String sFileName, int iCompanyID, int iOrgID,
			String sUserName, Sheet[] wSheets) {
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		String [] sErrorStatus = new String[3];
		String sMethodName = "importQuestionnaire"; //For printLog(), logError()

		//Initial validation checkers' objects
		Questionnaire questionnaire = new Questionnaire(); 
		Create_Edit_Survey CESurvey = new Create_Edit_Survey();
		KeyBehaviour KB = new KeyBehaviour();
		competency = new Competency();

		//Get Current Sheet - Note: Convert to while loop if mulitple sheets are used
		Sheet wCurrentSheet = wSheets[0];

		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalErrorRecords = 0;

		//retrieve survey name, target name and rater code
		Cell cSurveyName = wCurrentSheet.findCell(Pattern.compile("Survey Name: "+".+"), 0, 0, 44, 36, false);
		Cell cTargetName = wCurrentSheet.findCell("Target Name: ");
		cTargetName = wCurrentSheet.getCell(cTargetName.getColumn(), cTargetName.getRow()+1);
		Cell cRTCode = wCurrentSheet.findCell(Pattern.compile("Rater: "+".+"),0,0,44,36,false);

		//initialize values
		String surveyName = "";
		String targetName = "";
		String RTCode = "";
		String raterName = "";
		int surveyID = -1;
		int assignmentID = -1;
		Vector ratingTask = new Vector();
		Vector surveyCompetency = new Vector();//vector of voCompetency
		Vector surveyKB = new Vector();//vector of voKeyBehaviour
		Vector<AdditionalQuestion> additionalQuestion = new Vector<AdditionalQuestion>();// vector of additional questions
		Vector<PrelimQuestion> prelimQuestion = new Vector<PrelimQuestion>();// vector of prelim questions
		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			if(cSurveyName == null) {
				sCriticalColumnMissing += "Survey Name";
			} else {
				surveyName = cSurveyName.getContents();				
				int start = surveyName.indexOf(":")+2;//assume QuestionaireTemplate.xls
				surveyName = surveyName.substring(start);
				System.out.println("Survey Name is " + surveyName);

			} 
			if(cTargetName == null) {
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing += "Target Name";
			} else {
				targetName = cTargetName.getContents();
				System.out.println("targetName is " + targetName);
			} 

			//Critical Column Missing Check - GroupName 
			if(cRTCode == null) {
				//Append Missing Critical Column
				if(!sCriticalColumnMissing.equals("")) {
					sCriticalColumnMissing += ", ";
				}
				sCriticalColumnMissing += "Rater";
			} else {
				RTCode = cRTCode.getContents();
				assignmentID = Integer.parseInt(RTCode.substring(RTCode.indexOf("(")+1,
						RTCode.indexOf(")")));
				int start = RTCode.indexOf(":")+2;
				int end = RTCode.indexOf("(");
				RTCode = RTCode.substring(start, end-1);
				voUser rater = questionnaire.getRaterInfo(assignmentID);
				raterName = rater.getFamilyName()+" "+ rater.getGivenName();
				System.out.println("raterName is " + raterName);
			} 
			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Information missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Information is missing";
				
				sErrorStatus[0] = "Critial Column missing";
				sErrorStatus[1] = "Unable to find "+sCriticalColumnMissing +" in excel file.";
				sErrorStatus[2] = "Please update the excel file and reload";
				//Print to Console Log
				//logError(sMethodName, sOperationStatus[0], iCompanyID, iOrgID);
				logError(sMethodName, sErrorStatus);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check
			//if the rating has been completed, note down and print to the error log later
			boolean completed = questionnaire.checkSurveyCompleted(assignmentID);
			if (completed) System.out.println("Completed!");
			else System.out.println("Not completed!");

			//retrieve assignment ID for this questionnaire
			surveyID = CESurvey.getSurveyID(surveyName);
			System.out.println("SurveyID is " + surveyID);

			int surveyLevel = CESurvey.getSurveyLevel(surveyID);

			ratingTask = questionnaire.getSurveyRating(surveyID);
			surveyCompetency = questionnaire.getCompetency(surveyID);
			additionalQuestion = questionnaire.getAdditionalQuestion(surveyID);
			prelimQuestion = questionnaire.getPrelimQuestion(surveyID);
			
			//store all the results as array: 1:
			Vector allResult = new Vector();
			Vector allComment = new Vector();
			// store additional question result
			Vector<Vector<String>> additionalResult = new Vector<Vector<String>>();
			Vector<String> prelimResult = new Vector<String>();
			//String curRowError = "";
			//String sRowErrorStatus = "";
			
			// prelim question import here
			if(prelimQuestion.size()>0){
				Cell curRT = wCurrentSheet.findCell("Preliminary Questions");
				//set the search range according to the position of the KBs
				int curRTRow = curRT.getRow()+3;
				int curRTCol = curRT.getColumn()+1;
				for(int n = 0; n < prelimQuestion.size(); n++){
					String ans = null; // store ans for this question
					int scaleID = prelimQuestion.get(n).getPrelimRatingScaleId();
					int numOfLine = questionnaire.getPrelimQnOptionNum(scaleID);
					int selectedCount = 0;// count the number of option selected
					for(int row = 0; row < numOfLine; row++){
						curRT = wCurrentSheet.getCell(curRTCol,curRTRow+row);
						Colour curColor = curRT.getCellFormat().getBackgroundColour();
						if(curColor.equals(Colour.YELLOW)){
							ans = curRT.getContents();
							selectedCount ++;
						}
					}
					if(selectedCount == 0){
						//curRowError = n+" "+scaleID +prelimQuestion.get(n).getQuestion()+ "No selection of rating results at preliminary question No. "+ (n+1);
						//logError(sMethodName, curRowError, iCompanyID, iOrgID);
						sErrorStatus[0] = "Invalid result in prelim question section.";
						sErrorStatus[1] = "Result for preliminary question No." +(n+1) +" is empty";
						sErrorStatus[2] = "Please update the questionnaire and reload";
						logError(sMethodName, sErrorStatus);
					}
					if(selectedCount > 1){
						//curRowError = "Multiple selections of rating results at preliminary question No. "+ (n+1);
						//logError(sMethodName, curRowError, iCompanyID, iOrgID);
						sErrorStatus[0] = "Multiple result detected in prelim question section.";
						sErrorStatus[1] = "More than one result for preliminary question No." +(n+1)+ " deteced. First result been recorded as answer.";
						sErrorStatus[2] = "Please update the questionnaire and reload if necessary.";
						logError(sMethodName, sErrorStatus);
					}
					curRTRow = curRTRow + numOfLine + 2; // move to next question
					prelimResult.add(ans);

				}
			}
			System.out.println("surveyLevel is " + surveyLevel);
			System.out.println("surveyCompetency.size() = " + surveyCompetency.size());
			if (surveyLevel == 0 ){//Competency Level Survey
				for (int i = 0; i < surveyCompetency.size();i++){
					voCompetency curComp = (voCompetency) surveyCompetency.get(i);
					voCompetency nextComp = null;
					Cell curCompName = null;
					Cell nextCompName = null;
					//get the current and next competencies, retrieve the last sentence if no more next competency
					if (i != surveyCompetency.size()-1){
						nextComp = (voCompetency) surveyCompetency.get(i+1);
						curCompName = wCurrentSheet.findCell(curComp.getCompetencyName());
						nextCompName = wCurrentSheet.findCell(nextComp.getCompetencyName());
						if(i == 0){// two copy of the question 1 in excel, need to skip the first one which in the example of how to rate
							curCompName = wCurrentSheet.findCell(curComp.getCompetencyName(),0, curCompName.getRow()+1, 44, nextCompName.getRow(),false);
							sOperationStatus[0] = curCompName.getColumn()+" "+curCompName.getRow()+" ";
						}
					}
					else{
						if(additionalQuestion.size() > 0){
							curCompName = wCurrentSheet.findCell(curComp.getCompetencyName());
							nextCompName = wCurrentSheet.findCell("Additional Questions");
							
						}else{
							curCompName = wCurrentSheet.findCell(curComp.getCompetencyName());
							nextCompName = wCurrentSheet.findCell("Thank you for your participation"); // make sure the y in "you" matches the one the excel --xukun
						}
					}
					if (curCompName == null){//check whether there is short of competency of this survey
						//curRowError = "Missing Competency: "+ curComp.getCompetencyName();
						//logError(sMethodName, curRowError,iCompanyID, iOrgID);
						sErrorStatus[0] = "Missing Competency detected";
						sErrorStatus[1] = curComp.getCompetencyName() +" is missing from the file";
						sErrorStatus[2] = "Please update the file and reload";
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					}
					else{//found the competency, then retrieve the rating results
						for (int j = 0 ; j < ratingTask.size(); j++){
							int[] RTinfo = (int[]) ratingTask.get(j);
							Cell curRT = wCurrentSheet.findCell(questionnaire.getRatingTask(RTinfo[0]), 
									0, curCompName.getRow(), 44, curCompName.getRow()+2+ratingTask.size(), false);
							//set the search range according to the position of the current competency names
							int curRTRow = curRT.getRow();
							int curRTCol = curRT.getColumn()+12;
							boolean hideNA = questionnaire.getHideNAOption(surveyID);
							int scale = maxRS(RTinfo[1]);
							if (!hideNA)//one more column for 0 if no hideNA
								scale++;
							int RT = -1;
							for (int k = 0; k < scale; k++, curRTCol++){
								Cell curCell = wCurrentSheet.getCell(curRTCol,curRTRow);
								Colour curColor = curCell.getCellFormat().getBackgroundColour();
								if (curColor.equals(Colour.YELLOW)){//The cell is highlighted with yellow color
									if (RT != -1){//there are more than one highlighted column error
										//curRowError = "Multiple selection of rating results at row "+ (curRTRow+1);
										// Add Error Log
										//logError(sMethodName, curRowError,iCompanyID, iOrgID);
										sErrorStatus[0] = "Multiple selection detected";
										sErrorStatus[1] = "Multiple selection of rating results at row "+(curRTRow+1);
										sErrorStatus[2] = "Please update the file and reload";
										logError(sMethodName, sErrorStatus);
										break;
									}
									else{
										RT = Integer.parseInt(curCell.getContents());
										System.out.println("RT = " + RT);
									}
								}
							}
							if (RT == -1){//no selection error
								//curRowError = "No selection of rating results at row "+ (curRTRow+1);
								//logError(sMethodName, curRowError, iCompanyID, iOrgID);
								sErrorStatus[0] = "Empty selection detected";
								sErrorStatus[1] = "Empty selection of rating results at Row "+ (curRTRow + 1);
								sErrorStatus[2] = "Please update the file and reload";
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							}
							else{
								int[] curResult = new int[3];
								curResult[0] = curComp.getCompetencyID();
								curResult[1] = RTinfo[0];
								curResult[2] = RT;
								allResult.add(curResult);
							}
						}
						//get comments for each competency
						//Added codes to handle permutations of include/exclude comment & include/exclude self comment. Kian Hwee 16 March 2010
						int selfCommentIncluded = questionnaire.SelfCommentIncluded(surveyID);
						//System.out.println("selfcommentincluded = " + selfCommentIncluded);
						//System.out.println("questionnaire.commentIncluded(surveyID)" + questionnaire.commentIncluded(surveyID));
						
						if(questionnaire.commentIncluded(surveyID)==1){//includes comments in this survey Kian Hwee 15 March 2010							
							Cell comment = wCurrentSheet.findCell("Comments:", 1,curCompName.getRow(), 1, nextCompName.getRow(), false);
							//search the rows between two competencies
							if (selfCommentIncluded==1){// self comment is included in this survey. Kian Hwee 15 March 2010
								if(comment==null){// no comment box found
									//curRowError = "Comments is included but no comment box is found for competency: " + curComp.getCompetencyName()+" Please check your import form.";
									//logError(sMethodName, curRowError, iCompanyID, iOrgID);
									sErrorStatus[0] = "Missing Comment box";
									sErrorStatus[1] = "Commet is included but no comment box is found for competency: "+ curComp.getCompetencyName();
									sErrorStatus[2] = "Please update the file and reload";
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								}
								else{//comment box exists
									String curComment = wCurrentSheet.getCell(comment.getColumn(), comment.getRow()+1).getContents();
									allComment.add(curComment);
								}
								
							}
							else{//self comments is excluded in this survey. Kian Hwee 15 March 2010
								if(RTCode.equals("SELF")&&comment!=null){//Rater is SELF but the form contains comments box 15 March 2010
									//curRowError = "Self comments is excluded but comment box is found for competency: " + curComp.getCompetencyName()+" Please check your import form.";
									//logError(sMethodName, curRowError, iCompanyID, iOrgID);
									sErrorStatus[0] = "Extra Comment box detected";
									sErrorStatus[1] = "Self comments is excluded but comment box is found for competency: "+ curComp.getCompetencyName();
									sErrorStatus[2] = "Please update the file and reload";
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								}
								else if((!RTCode.equals("SELF"))&&comment!=null){//Rater not SELF, the form contains comments box 15 March 2010
									String curComment = wCurrentSheet.getCell(comment.getColumn(), comment.getRow()+1).getContents();
									allComment.add(curComment);
								} 
								else if((!RTCode.equals("SELF"))&&comment==null){//Rater not SELF, but the form doesn't contains comment box 15 March 2010
									//curRowError = "Comments is included but no comment box is found for competency: " + curComp.getCompetencyName()+" Please check your import form.";
									//logError(sMethodName, curRowError, iCompanyID, iOrgID);
									sErrorStatus[0] = "Missing Comment box";
									sErrorStatus[1] = "Commet is included but no comment box is found for competency: "+ curComp.getCompetencyName();
									sErrorStatus[2] = "Please update the file and reload";
									logError(sMethodName, sErrorStatus);
									iTotalErrorRecords++;
								}
								else{
									//positive case, do nothing 
								}
								
							}// end self comment is excluded in this survey.
						}// end comments is included in this survey.
						else if(questionnaire.commentIncluded(surveyID)!=1){// comments not includes in this survey. Kian Hwee 15 March 2010
							Cell comment = wCurrentSheet.findCell("Comments:", 1,curCompName.getRow(), 1, nextCompName.getRow(), false);
							//System.out.println("cell comment contains " + comment.getContents());
							if (comment != null){
								//curRowError = "Comments is not included but found comment box for competency: " + curComp.getCompetencyName()+" Please check your import form.";
								//logError(sMethodName, curRowError, iCompanyID, iOrgID);
								sErrorStatus[0] = "Extra Comment box detected";
								sErrorStatus[1] = "Commet is excluded but comment box is found for competency: "+ curComp.getCompetencyName();
								sErrorStatus[2] = "Please update the file and reload";
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							}// end comment box exists when comments is not included in this survey
						}// end comments is not included in this survey
					}//end Competency is not missing.
				}//end for each Competency
			}//end Competency Level Survey
			else if (surveyLevel != 0 ){//KB Level Survey
				for (int i = 0; i < surveyCompetency.size();i++){
					voCompetency curComp = (voCompetency) surveyCompetency.get(i);
					surveyKB = questionnaire.getKBSurvey(surveyID, curComp.getCompetencyID());
					Cell curCompName = wCurrentSheet.findCell(curComp.getCompetencyName());
					if (curCompName == null){
						//curRowError = "Missing Competency: "+ curComp.getCompetencyName();
						//logError(sMethodName, curRowError, iCompanyID, iOrgID);
						sErrorStatus[0] = "Missing Competency";
						sErrorStatus[1] = curComp.getCompetencyName() +" is missing from the file";
						sErrorStatus[2] = "Please update the file and reload";
						logError(sMethodName, sErrorStatus);
						iTotalErrorRecords++;
					}
					else{
						for (int j = 0; j < surveyKB.size();j++){
							//get the current and next KB, if no more next KB, retrieve the last line
							voKeyBehaviour curKB = (voKeyBehaviour)surveyKB.get(j);;
							voKeyBehaviour nextKB = null;
							Cell curKBDesc = null;
							Cell nextKBDesc = null;
							if (j != surveyKB.size()-1){                    			
								nextKB = (voKeyBehaviour)surveyKB.get(j+1);
								curKBDesc = wCurrentSheet.findCell(curKB.getKeyBehaviour());
								nextKBDesc = wCurrentSheet.findCell(nextKB.getKeyBehaviour());
							}
							else{
								curKBDesc = wCurrentSheet.findCell(curKB.getKeyBehaviour());
								
								/*
								 *Change(s):changed the way the next comment cell is found
								 *Reason(s):to cater to the fact that the comment box of the last keybehavior in 
								 *          each competency could not be found using the way used in competency level survey 
								 * Updated By : Liu Taichen
								 * Updated On: 20/6/2012
								 */
								nextKBDesc = wCurrentSheet.getCell(curKBDesc.getColumn(), (curKBDesc.getRow() + 9) );

							}
							if (curKBDesc == null){
								//curRowError = "Missing Key Behaviour: "+ curKB.getKeyBehaviour();
								//logError(sMethodName, curRowError, iCompanyID, iOrgID);
								sErrorStatus[0] = "Missing Key Behaviour";
								sErrorStatus[1] = curKB.getKeyBehaviour() +" is missing from the file";
								sErrorStatus[2] = "Please update the file and reload";
								logError(sMethodName, sErrorStatus);
								iTotalErrorRecords++;
							}
							else{
								for (int k = 0 ; k < ratingTask.size(); k++){
									int[] RTinfo = (int[]) ratingTask.get(k);
									Cell curRT = wCurrentSheet.findCell(questionnaire.getRatingTask(RTinfo[0]), 
											0, curKBDesc.getRow(), 44, curKBDesc.getRow()+2+ratingTask.size(), false);
									//set the search range according to the position of the KBs
									int curRTRow = curRT.getRow();
									int curRTCol = curRT.getColumn()+12;
									boolean hideNA = questionnaire.getHideNAOption(surveyID);
									int scale = maxRS(RTinfo[1]);
									if (!hideNA)
										scale++;
									int RT = -1;
									for (int l = 0; l < scale; l++, curRTCol++){
										Cell curCell = wCurrentSheet.getCell(curRTCol,curRTRow);
										Colour curColor = curCell.getCellFormat().getBackgroundColour();
										if (curColor.equals(Colour.YELLOW)){//The cell is highlighted with yellow color
											if (RT != -1){//there are more than one highlighted column error
												//curRowError = "Multiple selection of rating results at row "+ (curRTRow+1);
												//logError(sMethodName, curRowError, iCompanyID, iOrgID);
												sErrorStatus[0] = "Multiple selection detected";
												sErrorStatus[1] = "Multiple selection of rating results at row " + (curRTRow+1);
												sErrorStatus[2] = "Please update the file and reload";
												logError(sMethodName, sErrorStatus);
												iTotalErrorRecords++;
												break;
											}
											else{
												RT = Integer.parseInt(curCell.getContents());
											}
										}
									}
									if (RT == -1){//no selection error
										//curRowError = "No selection of rating results at row "+ (curRTRow+1);
										//logError(sMethodName, curRowError, iCompanyID, iOrgID);
										sErrorStatus[0] = "Empty selection detected";
										sErrorStatus[1] = "Empty selection of rating results at row "+(curRTRow+1);
										sErrorStatus[2] = "Please update the file and reload";
										logError(sMethodName, sErrorStatus);
										iTotalErrorRecords++;
									}
									else{
										int[] curResult = new int[3];
										curResult[0] = curKB.getKeyBehaviourID();
										curResult[1] = RTinfo[0];
										curResult[2] = RT;
										allResult.add(curResult);
									}
								}
								//get comments between rows of two KBs
								////Added codes to handle permutations of include/exclude comment & include/exclude self comment. Kian Hwee 16 March 2010
								int selfCommentIncluded=questionnaire.SelfCommentIncluded(surveyID);
								if(questionnaire.commentIncluded(surveyID)==1){//comments is included in this survey. Kian Hwee  15 March 2010
									Cell comment = wCurrentSheet.findCell("Comments:", 1, curKBDesc.getRow(), 1, nextKBDesc.getRow(), false);//hard coded as range 20 first
									if(selfCommentIncluded==1){// self comments are included in this survey. Kian Hwee 15 March 2010
										if (comment == null){
											//curRowError = "Comments is included but no comment box is found for Key Behaviour: " + curKB.getKeyBehaviour()+" Please check your import form.";
											//logError(sMethodName, curRowError, iCompanyID, iOrgID);
											sErrorStatus[0] = "Missing Comment box";
											sErrorStatus[1] = "Commet is included but no comment box is found for Key Behaviour: "+ curKB.getKeyBehaviour();
											sErrorStatus[2] = "Please update the file and reload";
											logError(sMethodName, sErrorStatus);
											iTotalErrorRecords++;
										}
										else{
											String curComment = wCurrentSheet.getCell(comment.getColumn(), comment.getRow()+1).getContents();
											allComment.add(curComment);
										}
									}// end self comments are included in this survey
									else{//self comments is not included in this survey. Kian Hwee 15 March 2010
										if(RTCode.equals("SELF")&&comment!=null){//Rater is SELF but the form contains comments box 15 March 2010
											//curRowError = "Self comments is excluded but comment box is found for Key Behaviour: " + curKB.getKeyBehaviour()+" Please check your import form.";
											//logError(sMethodName, curRowError, iCompanyID, iOrgID);
											
											sErrorStatus[0] = "Extra Comment box detected";
											sErrorStatus[1] = "Self comments is excluded but comment box is found for Key Behaviour: " + curKB.getKeyBehaviour();
											sErrorStatus[2] = "Please update the file and reload";
											logError(sMethodName, sErrorStatus);
											iTotalErrorRecords++;
										}
										else if((!RTCode.equals("SELF"))&&comment!=null){//Rater not SELF, the form contains comments box 15 March 2010
											String curComment = wCurrentSheet.getCell(comment.getColumn(), comment.getRow()+1).getContents();
											allComment.add(curComment);
										}
										else if((!RTCode.equals("SELF"))&&comment==null){//Rater not SELF, but the form doesn't contains comment box 15 March 2010
											//curRowError = "Comments is included but no comment box is found for Key Behaviour: " + curKB.getKeyBehaviour()+" Please check your import form.";
											//logError(sMethodName, curRowError, iCompanyID, iOrgID);
											sErrorStatus[0] = "Missing Comment box";
											sErrorStatus[1] = "comments is included but no comment box is found for Key Behaviour: " + curKB.getKeyBehaviour();
											sErrorStatus[2] = "Please update the file and reload";
											logError(sMethodName, sErrorStatus);
											iTotalErrorRecords++;
										}
										else{
											//positive case, do nothing
										}
									}// end self comments are not included in this survey.
								}// end comments are included in this survey.
								else{//comments are not included in this survey, Kian Hwee 15 March 2010
									Cell comment = wCurrentSheet.findCell("Comments:", 1, curKBDesc.getRow(), 1, nextKBDesc.getRow(), false);
									if (comment != null){
										//curRowError = "Comments is not included but found comment box for Key Behaviour: " + curKB.getKeyBehaviour()+" Please check your import form.";
										//logError(sMethodName, curRowError, iCompanyID, iOrgID);
										sErrorStatus[0] = "Extra Comment box detected";
										sErrorStatus[1] = "comments is excluded but comment box is found for Key Behaviour: " + curKB.getKeyBehaviour();
										sErrorStatus[2] = "Please update the file and reload";
										logError(sMethodName, sErrorStatus);
										iTotalErrorRecords++;
									}// end comments box exists in input file when comments is excluded in this survey.
								}//end comments are not included in this survey
							}// end Key Behaviour is not missing
						}// end for every Key Behaviour
					}// end current competency is not null
				}
			}
			if(additionalQuestion.size()>0){
				Cell curRT = wCurrentSheet.findCell("Additional Questions");
				//set the search range according to the position of the KBs
				int curRTRow = curRT.getRow()+3;
				int curRTCol = curRT.getColumn()+4;
				for(int n = 0; n < additionalQuestion.size(); n++){
					Vector<String> ans = new Vector<String>(); // store ans for this question
					for(int i = 0; i < 3; i++){ // 3 is the numbers of point for each question, lol shouldnot be hardcoded
						Cell curCell = wCurrentSheet.getCell(curRTCol,curRTRow);
						ans.add(curCell.getContents());
						curRTRow+=2;
					}
					additionalResult.add(ans);
					curRTRow++; // skip question statement
				}
			}
			// counters for error log
			int iPrelimUpdate = 0, iAddiUpdate = 0;
			int prelimTotal = 0, addiTotal = 0;
			//boolean isUpdate = (questionnaire.checkRatingCompleted(assignmentID)==1);
			//System.out.println("curRowError is " + curRowError);
			if(!(sErrorStatus[0] == null || sErrorStatus[0].length() == 0)) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Invalid Data.";
				sOperationStatus[1] = "Invalid ratings or comments";
				//Return status messages
				return sOperationStatus;
			} 
			else{//add in the results	
				// add additional answer
				for(int n =0; n < additionalQuestion.size(); n++){
					addiTotal++;
					boolean result = false;
					String error = "adding";
					if(questionnaire.checkAdditionalAnsExist(additionalQuestion.get(n).getAddQnId(), assignmentID)){
						result = questionnaire.updateAdditionalAns(additionalQuestion.get(n).getAddQnId(), additionalResult.get(n), assignmentID);
						iAddiUpdate++;
						error = "updating";
					}else{
						result = questionnaire.addAdditionalAns(additionalQuestion.get(n).getAddQnId(), additionalResult.get(n), assignmentID);
					}
					if(!result)
						throw new Exception("db refuse when "+error+" additional question answer");
				}
				
				// add prelim answer
				for(int n =0; n < prelimQuestion.size(); n++){
					boolean result = false;
					prelimTotal++;
					String error = "adding";
					if(questionnaire.checkPrelimAnsExist(prelimQuestion.get(n).getPrelimQnId(), assignmentID)){
						result = questionnaire.updatePrelimAns(prelimQuestion.get(n).getPrelimQnId(), prelimResult.get(n), assignmentID);	
						iPrelimUpdate++;
						error = "updating" +  prelimResult.get(n) +prelimQuestion.get(n).getPrelimQnId();
					}else{
						result = questionnaire.addPrelimAns(prelimQuestion.get(n).getPrelimQnId(), prelimResult.get(n), assignmentID);
					}
					
					if(!result)
						throw new Exception("db refuse when "+error+" prelim question answer");
				}
				boolean isUpdate;
				boolean added = false;
				String dbErrorStatus = "";
				if (surveyLevel == 0 ){//Competency Level Survey
					//adding in the results				
					for (int i = 0; i < allResult.size();i++){
						isUpdate = false;
						int [] curResult = (int[])allResult.get(i);
						if (questionnaire.CheckOldResultExist(assignmentID, curResult[0], curResult[1])!= -1){
							added = questionnaire.updateOldResult(assignmentID,curResult[0], curResult[1], curResult[2]);
							isUpdate = true;
						}
						else{
							added = questionnaire.addResult(assignmentID, curResult[0], curResult[1], curResult[2]);
						}
						if (!added){
							//dbErrorStatus = "Error importing "+ questionnaire.getRatingTask(curResult[1])
							//+ " rating for "+targetName +" by "+raterName+" for Competency: "+competency.CompetencyName(curResult[0]);
							//logError(sMethodName, dbErrorStatus, iCompanyID, iOrgID);
							sErrorStatus[0] = "Fail to import Competency Rating";
							sErrorStatus[1] = "rating for "+targetName +" by "+raterName+" for Competency "+competency.CompetencyName(curResult[0])+" import failed because of unknown reason";
							sErrorStatus[2] = "Please contact administrator";
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						}
						else if (isUpdate)
							iTotalUpdatedRecords++;
						else
							iTotalAddedRecords++;
						iTotalRecords++;
					}
					//adding in the comments
					for (int i = 0; i < allComment.size(); i++){
						isUpdate = false;
						int compID = ((voCompetency)surveyCompetency.get(i)).getCompetencyID();
						String compName = ((voCompetency)surveyCompetency.get(i)).getCompetencyName();
						if (questionnaire.checkCommentExist(assignmentID, compID, 0)!= null){
							added = questionnaire.updateComment(assignmentID, compID, 0, (String)allComment.get(i));
							isUpdate = true;
						}
						else{
							added = questionnaire.addComment(assignmentID, compID, 0, (String)allComment.get(i));
						}
						if (!added){
							//dbErrorStatus = "Error importing comments for "+targetName +" by "+raterName+
							//" for Competency: " + compName;
							//logError(sMethodName, dbErrorStatus, iCompanyID, iOrgID);
							sErrorStatus[0] = "Fail to import comment";
							sErrorStatus[1] = "Comments for "+targetName +" by "+raterName+" import failed because of unknown reason";
							sErrorStatus[2] = "Please contact administrator";
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						}
						else if (isUpdate)
							iTotalUpdatedRecords++;
						else
							iTotalAddedRecords++;
						iTotalRecords++;
					}
				}
				else{//KB Level Survey				
					for (int i = 0; i < allResult.size();i++){
						isUpdate =false;
						int [] curResult = (int[])allResult.get(i);
						if (questionnaire.CheckOldResultExist(assignmentID, curResult[0], curResult[1])!= -1){
							added = questionnaire.updateOldResult(assignmentID, curResult[0], curResult[1], curResult[2]);
							isUpdate = true;
						}
						else{
							added = questionnaire.addResult(assignmentID, curResult[0], curResult[1], curResult[2]);
						}
						if (!added){
							//dbErrorStatus = "Error importing "+ questionnaire.getRatingTask(curResult[1])
							//+ " rating for "+targetName +" by "+raterName+" for Key Behaviour: "+KB.KBStatement(curResult[0]);
							//logError(sMethodName, dbErrorStatus, iCompanyID, iOrgID);
							sErrorStatus[0] = "Fail to import Key Behaviour Rating";
							sErrorStatus[1] = "rating for "+targetName +" by "+raterName+" for Key Behaviour "+KB.KBStatement(curResult[0])+" import failed because of unknown reason";
							sErrorStatus[2] = "Please contact administrator";
							logError(sMethodName, sErrorStatus);
							iTotalErrorRecords++;
						}
						else if (isUpdate)
							iTotalUpdatedRecords++;
						else
							iTotalAddedRecords++;
						iTotalRecords++;
					}
					int num = 0;
					// Added codes to resolve null pointer exceptions that occur because the Vector allComment is empty even when case is positive. Kian Hwee 16 March 2010
					int selfCommentIncluded=questionnaire.SelfCommentIncluded(surveyID);
					int commentIncluded=questionnaire.commentIncluded(surveyID);
					if(commentIncluded==1){//Comments is included in survey. Kian Hwee 15 March 2010.
						if(selfCommentIncluded==1 ||(selfCommentIncluded==0 && !RTCode.equals("SELF"))){
							for (int i = 0; i < surveyCompetency.size(); i++){
								int compID = ((voCompetency)surveyCompetency.get(i)).getCompetencyID();
								surveyKB = questionnaire.getKBSurvey(surveyID, ((voCompetency)surveyCompetency.get(i)).getCompetencyID());
								for (int j = 0; j < surveyKB.size(); j++, num++, iTotalRecords++){
									isUpdate = false;
									if (questionnaire.checkCommentExist(assignmentID, compID, ((voKeyBehaviour)surveyKB.get(j)).getKeyBehaviourID())!= null){
										added = questionnaire.updateComment(assignmentID, compID, ((voKeyBehaviour)surveyKB.get(j)).getKeyBehaviourID(), (String)allComment.get(num));
										isUpdate = true;
									}
									else{
										added = questionnaire.addComment(assignmentID, compID,((voKeyBehaviour)surveyKB.get(j)).getKeyBehaviourID(), (String)allComment.get(num));
									}
									if (!added){
										//dbErrorStatus = "Error importing comments for "+targetName +" by "+raterName+
										//" for Key Behaviour: "+ ((voKeyBehaviour)surveyKB.get(j)).getKeyBehaviour();
										//logError(sMethodName, dbErrorStatus, iCompanyID, iOrgID);
										sErrorStatus[0] = "Fail to import Key Behaviour Rating";
										sErrorStatus[1] = "rating for "+targetName +" by "+raterName+" for Key Behaviour "+((voKeyBehaviour)surveyKB.get(j)).getKeyBehaviour()+" import failed because of unknown reason";
										sErrorStatus[2] = "Please contact administrator";
										logError(sMethodName, sErrorStatus);
										iTotalErrorRecords++;
									}
									else if (isUpdate)
										iTotalUpdatedRecords++;
									else
										iTotalAddedRecords++;
								}//end for each Key Behaviour
							}//end for each competency
						}//end when self comment is included or when self comment is excluded and rater is not SELF
					}//end if comments is included in this survey
				}//end KB Level Survey
			}
			//Error Log Check
			
			System.out.println("Error Log Check Done.");
			
			if(errLog.hasError()) {
				sOperationStatus[0] = "Import Questionnaire Unsuccessful.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				questionnaire.SetRaterStatus(assignmentID, 1);
				sOperationStatus[0] = "Import Questionnaire Successfully.";
				sOperationStatus[1] = "OK.";                
			} //End of Error Log Check
			
			// record for questionnaire question
			String sRecordsStatus = "Questionnaire Records:" + iTotalRecords + ", Questionnaire Added:" + iTotalAddedRecords +
			", Questionnaire Edited:" + iTotalUpdatedRecords + ", Questionnaire Errors:" + 
			iTotalErrorRecords;
			// record for prelim question
			sRecordsStatus += " Preliminary Records:" +prelimTotal+", Preliminary Added:"+(prelimTotal-iPrelimUpdate)+
					", Preliminary Edited:"+iPrelimUpdate;
			// record for additional question
			sRecordsStatus += " Additional Records:" +addiTotal+", Additional Added:"+(addiTotal-iAddiUpdate)+
					", Additional Edited:"+iAddiUpdate;
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0] + " " + sOperationStatus[1]);
			printLog(sMethodName, sRecordsStatus);
			//Append the Records Status to the end of sOperationStatus[1];
			if (completed){
				//logError(sMethodName, "All rating results already exist. " + sRecordsStatus, iCompanyID, iOrgID); 
				sErrorStatus[0] = "All Rating results already exist";
				sErrorStatus[1] = sRecordsStatus;
				sErrorStatus[2] = "";
				logError(sMethodName, sErrorStatus);
			}
			sOperationStatus[1] += " " + sRecordsStatus;
		} catch (Exception e) {
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log:\n"
			+e.getMessage();
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} 
		//Return status messages
		return sOperationStatus;
	} //End of importQuesionnaire()

	/**
	 * This method prints a message to Console Log
	 * @param sMethodName - The method name of the method the log is created for
	 * @param sMessage    - The message of this log entry
	 * @see Used by all import methods
	 */
	private void printLog(String sMethodName, String sMessage) {
		//Print to Console Log
		System.out.println("Import.java - " + sMethodName + "() - " + sMessage);
	} //End of logError()

	/**
	 * This method prints an error log to Console and add an Error log into errLog object
	 * @param sMethodName   - The method name of the method the log is created for
	 * @param sErrorMessage - The error message of this error log entry
	 * @param iCompanyID    - The Company ID
	 * @param iOrgID        - The Organisation ID
	 * @see Used by all import methods
	 */
	/*private void logError(String sMethodName, String sErrorMessage, int iCompanyID, int iOrgID) {
		//Record to Error Log
		errLog.addError("Import File Exception", "Import.java - " + sMethodName + "()",
				sErrorMessage, "Company ID:" + iCompanyID + 
				"; Organisation ID:" + iOrgID);
		//Print to Console Log
		printLog(sMethodName, sErrorMessage);
	} //End of logError()
	*/
	private void logError(String sMethodName, String[] errorDetail){
		errLog.addError("Import File Exception", errorDetail[0], errorDetail[1], errorDetail[2]);
		printLog(sMethodName,errorDetail[0]);
	}

	/**
	 * This method prints an error log to Console and add an Error log into errLog object
	 * @param sMethodName   - The method name of the method the log is created for
	 * @param sErrorMessage - The error message of this error log entry
	 * @param iCompanyID    - The Company ID
	 * @param iOrgID        - The Organisation ID
	 * @see Used by all import methods
	 */
	private void logError(String sMethodName, String sErrorMessage, boolean isAutoAdd, int iCompanyID, int iOrgID) {
		//Record to Error Log
		errLog.addError("Import File Exception", "Import.java - " + sMethodName + "()",
				sErrorMessage, "Is Auto Add:" + (isAutoAdd ? "True" : "False") + 
				"; Company ID:" + iCompanyID + "; Organisation ID:" + iOrgID);
		//Print to Console Log
		printLog(sMethodName, sErrorMessage);
	} //End of logError()

	/**
	 * This method prints an error log to Console and add an Error log into errLog object for duplication
	 * of records
	 * @param sMethodName   - The method name of the method the log is created for
	 * @param sErrorMessage - The error message of this error log entry
	 * @param iCompanyID    - The Company ID
	 * @param iOrgID        - The Organisation ID
	 * @see Used by all import methods
	 */
	private void logDuplication(String sMethodName, String sErrorMessage, int iCompanyID, int iOrgID) {
		//Record to Error Log
		errLog.addError("Import File Duplication", "Import.java - " + sMethodName + "()",
				sErrorMessage, "Company ID:" + iCompanyID + 
				"; Organisation ID:" + iOrgID);
		//Print to Console Log
		printLog(sMethodName, sErrorMessage);
	} //End of logDuplication()
	
	private String generateLoginName(String email){
		return email.substring(0, email.indexOf("@"));
	}
	
	private boolean isTargetFKDetailChanged(int PKUser, int iDepartment, int iDivision, int iGroup_Section){
		String[] originData;
		try {
			originData = user.getUserDetailWithRound(PKUser);
		
		// Department
		if(Integer.parseInt(originData[13])== iDepartment){
			return true;
		}
		
		// Division
		if(Integer.parseInt(originData[14])== iDivision){
			return true;
		}
		
		// Group_Section
		if(Integer.parseInt(originData[15])== iGroup_Section){
			return true;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	private TreeMap<String, String> getOriginUserData(int iPKUser, String sDepartment, String sDivision,
			String sFamilyName, String sGivenName, String sDesignation,
			String sIDNumber, String sGroup_Section, String sEmail, String sRound, 
			String sOfficeTel, String sHandphone, String sMobileProvider, String sRemark){
		TreeMap<String, String> result = new TreeMap<String, String>();
		String[] originData = null;
		try{
			originData = user.getUserDetailWithRound(iPKUser);
			// familyName
			if(originData[0] == null && sFamilyName != null){
				result.put("FamilyName", originData[0]);
			}else if(sFamilyName == null && !(originData[0] == null || originData[0].equals("") || originData[0].toLowerCase().equals("null"))){
				result.put("FamilyName", originData[0]);
			}else if(sFamilyName != null && !originData[0].toLowerCase().equals(sFamilyName.toLowerCase())){
				result.put("FamilyName", originData[0]);
			}	
			// givenName
			if(originData[1] == null && sGivenName != null){
				result.put("GivenName", originData[1]);
			}else if(sGivenName == null && !(originData[1] == null || originData[1].equals("") || originData[1].toLowerCase().equals("null"))){
				result.put("GivenName", originData[1]);
			}else if(sGivenName != null && !originData[1].toLowerCase().equals(sGivenName.toLowerCase())){
				result.put("GivenName", originData[1]);
			}
			// Designation
			if(originData[3] == null && sDesignation != null){
				result.put("Designation", originData[3]);
			}else if(sDesignation == null && !(originData[3] == null || originData[3].equals("") || originData[3].toLowerCase().equals("null"))){
				result.put("Designation", originData[3]);
			}else if(sDesignation != null && !originData[3].equals(sDesignation)){
				result.put("Designation", originData[3]);
			}
			// IDNumber
			if(originData[4] == null && sIDNumber != null){
				result.put("IDNumber", originData[4]);
			}else if(sIDNumber == null && !(originData[4] == null || originData[4].equals("") || originData[4].toLowerCase().equals("null"))){
				result.put("IDNumber", originData[4]);
			}else if(sIDNumber != null && !originData[4].equals(sIDNumber)){
				result.put("IDNumber", originData[4]);
			}
			// Group_Section
			if(originData[9] == null && sGroup_Section != null){
				result.put("Group_Section", originData[9]);
			}else if(sGroup_Section == null && !(originData[9] == null || originData[9].equals("") || originData[9].toLowerCase().equals("null"))){
				result.put("Group_Section", originData[9]);
			}else if(sGroup_Section != null && !originData[9].equals(sGroup_Section)){
				result.put("Group_Section", originData[9]);
			}
			// Email
			if(originData[12] == null && sEmail != null){
				result.put("Email", originData[12]);
			}else if(sEmail == null && !(originData[12] == null || originData[12].equals("") || originData[12].toLowerCase().equals("null"))){
				result.put("Email", originData[12]);
			}else if(sEmail != null && !originData[12].toLowerCase().equals(sEmail.toLowerCase())){
				result.put("Email", originData[12]);
			}
			// Round
			if(originData[16] == null && sRound != null){
				result.put("Round", originData[16]);
			}else if(sRound == null && !(originData[16] == null || originData[16].equals("") || originData[16].toLowerCase().equals("null"))){
				result.put("Round", originData[16]);
			}else if(sRound != null && !originData[16].equals(sRound)){
				result.put("Round", originData[16]);
			}
			// OfficeTel
			if(originData[17] == null && sOfficeTel != null){
				result.put("OfficeTel", originData[17]);
			}else if(sOfficeTel == null && !(originData[17] == null || originData[17].equals("") || originData[17].toLowerCase().equals("null"))){
				result.put("OfficeTel", originData[17]);
			}else if(sOfficeTel != null && !originData[17].equals(sOfficeTel)){
				result.put("OfficeTel", originData[17]);
			}
			// Handphone
			if(originData[18] == null && sHandphone != null){
				result.put("Handphone", originData[18]);
			}else if(sHandphone == null && !(originData[18] == null || originData[18].equals("") || originData[18].toLowerCase().equals("null"))){
				result.put("Handphone", originData[18]);
			}else if(sHandphone != null && !originData[18].equals(sHandphone)){
				result.put("Handphone", originData[18]);
			}
			// MobileProvider
			if(originData[19] == null && sMobileProvider != null){
				result.put("Mobile Provider", originData[19]);
			}else if(sMobileProvider == null && !(originData[19] == null || originData[19].equals("") || originData[19].toLowerCase().equals("null"))){
				result.put("Mobile Provider", originData[19]);
			}else if(sMobileProvider != null && !originData[19].equals(sMobileProvider)){
				result.put("Mobile Provider", originData[19]);
			}
			// Remark
			if(originData[20] == null && sRemark != null){
				result.put("Remark", originData[20]);
			}else if(sRemark == null && !(originData[20] == null || originData[20].equals("") || originData[20].toLowerCase().equals("null"))){
				result.put("Remark", originData[20]);
			}else if(sRemark != null && !originData[20].equals(sRemark)){
				result.put("Remark", originData[20]);
			}
			
			// Department
			if(originData[6] == null && sDepartment != null){
				result.put("Department", originData[6]);
			}else if(sDepartment == null && !(originData[6] == null || originData[6].equals("") || originData[6].toLowerCase().equals("null"))){
				result.put("Department", originData[6]);
			}else if(sDepartment != null && !originData[6].equals(sDepartment)){
				result.put("Department", originData[6]);
			}
			
			// Division
			if(originData[7] == null && sDivision != null){
				result.put("Division", originData[7]);
			}else if(sDivision == null && !(originData[7] == null || originData[7].equals("") || originData[7].toLowerCase().equals("null"))){
				result.put("Division", originData[7]);
			}else if(sDivision != null && !originData[7].equals(sDivision)){
				result.put("Division", originData[7]);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private TreeMap<String, String> getOriginAssignData(int assignID, int iRaterRelation, int iRaterRelationSpecific, int round, int wave) throws SQLException, Exception{
		TreeMap<String, String> result = new TreeMap<String, String>();
		Vector originData = assignTargetRater.getAssignmentDetail(assignID);
		votblAssignment vo = (votblAssignment) originData.get(0);
		/*if(!vo.getRaterCode().equals(raterCode)){
			result.put("RaterCode", vo.getRaterCode());
		}*/
		if(vo.getWave() != wave){
			result.put("Wave", vo.getWave()+"");
		}
		if(vo.getRound() != round){
			result.put("Round", vo.getRound()+"");
		}
		if(vo.getRTRelation() != iRaterRelation){
			result.put("Rater Relation", vo.getRTRelation()+"");
		}
		if(vo.getRTSpecific() != iRaterRelationSpecific){
			result.put("Rater Relation Specific", vo.getRTSpecific()+"");
		}
		return result;
	}
	
	private boolean checkAssignmentInconsistence (int assignID, int iRaterRelation, int iRaterRelationSpecific, int round, int wave){
		try{
			Vector originData = assignTargetRater.getAssignmentDetail(assignID);
			votblAssignment vo = (votblAssignment) originData.get(0);
			if(vo.getWave() != wave){
				return false;
			}else if(vo.getRound() != round){
				return false;
			}else if(vo.getRTRelation() != iRaterRelation){
				return false;
			}else if(vo.getRTSpecific() != iRaterRelationSpecific){
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean checkDataInconsistence (int iPKUser, int iDepartment, int iDivision, int iFKUserType360,
			String sFamilyName, String sGivenName, String sLoginName, String sDesignation,
			String sIDNumber, String sGroup_Section, String sEmail, String sRound, 
			String sOfficeTel, String sHandphone, String sMobileProvider, String sRemark){
		try {
			String[] originData = user.getUserDetailWithRound(iPKUser);
			// familyName
			if(originData[0] == null && sFamilyName != null){
				return false;
			}else if(sFamilyName == null && !(originData[0] == null || originData[0].equals("") || originData[0].toLowerCase().equals("null"))){
				return false;
			}else if(sFamilyName != null && !originData[0].toLowerCase().equals(sFamilyName.toLowerCase())){
				return false;
			}	
			// givenName
			if(originData[1] == null && sGivenName != null){
				return false;
			}else if(sGivenName == null && !(originData[1] == null || originData[1].equals("") || originData[1].toLowerCase().equals("null"))){
				return false;
			}else if(sGivenName != null && !originData[1].toLowerCase().equals(sGivenName.toLowerCase())){
				return false;
			}
			// loginName
			if(originData[2] == null && sLoginName != null){
				return false;
			}else if(sLoginName == null && !(originData[2] == null || originData[2].equals("") || originData[2].toLowerCase().equals("null"))){
				return false;
			}else if(sLoginName != null && !originData[2].toLowerCase().equals(sLoginName.toLowerCase())){
				return false;
			}
			// Designation
			if(originData[3] == null && sDesignation != null){
				return false;
			}else if(sDesignation == null && !(originData[3] == null || originData[3].equals("") || originData[3].toLowerCase().equals("null"))){
				return false;
			}else if(sDesignation != null && !originData[3].equals(sDesignation)){
				return false;
			}
			// IDNumber
			if(originData[4] == null && sIDNumber != null){
				return false;
			}else if(sIDNumber == null && !(originData[4] == null || originData[4].equals("") || originData[4].toLowerCase().equals("null"))){
				return false;
			}else if(sIDNumber != null && !originData[4].equals(sIDNumber)){
				return false;
			}
			// Group_Section
			if(originData[9] == null && sGroup_Section != null){
				return false;
			}else if(sGroup_Section == null && !(originData[9] == null || originData[9].equals("") || originData[9].toLowerCase().equals("null"))){
				return false;
			}else if(sGroup_Section != null && !originData[9].equals(sGroup_Section)){
				return false;
			}
			// Email
			if(originData[12] == null && sEmail != null){
				return false;
			}else if(sEmail == null && !(originData[12] == null || originData[12].equals("") || originData[12].toLowerCase().equals("null"))){
				return false;
			}else if(sEmail != null && !originData[12].toLowerCase().equals(sEmail.toLowerCase())){
				return false;
			}
			// Round
			if(originData[16] == null && sRound != null){
				return false;
			}else if(sRound == null && !(originData[16] == null || originData[16].equals("") || originData[16].toLowerCase().equals("null"))){
				return false;
			}else if(sRound != null && !originData[16].equals(sRound)){
				return false;
			}
			// OfficeTel
			if(originData[17] == null && sOfficeTel != null){
				return false;
			}else if(sOfficeTel == null && !(originData[17] == null || originData[17].equals("") || originData[17].toLowerCase().equals("null"))){
				return false;
			}else if(sOfficeTel != null && !originData[17].equals(sOfficeTel)){
				return false;
			}
			// Handphone
			if(originData[18] == null && sHandphone != null){
				return false;
			}else if(sHandphone == null && !(originData[18] == null || originData[18].equals("") || originData[18].toLowerCase().equals("null"))){
				return false;
			}else if(sHandphone != null && !originData[18].equals(sHandphone)){
				return false;
			}
			// MobileProvider
			if(originData[19] == null && sMobileProvider != null){
				return false;
			}else if(sMobileProvider == null && !(originData[19] == null || originData[19].equals("") || originData[19].toLowerCase().equals("null"))){
				return false;
			}else if(sMobileProvider != null && !originData[19].equals(sMobileProvider)){
				return false;
			}
			// Remark
			if(originData[20] == null && sRemark != null){
				return false;
			}else if(sRemark == null && !(originData[20] == null || originData[20].equals("") || originData[20].toLowerCase().equals("null"))){
				return false;
			}else if(sRemark != null && !originData[20].equals(sRemark)){
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	/**
	 * Import data into the database from File using main()
	 * Note: For testing purpose
	 * @param args
	 */
	public static void main(String[] args) {
		//Start main() Try-Catch
		try {
			Import it = new Import();
			Setting ST = new Setting();
			//Added to allow checking of UserType
			//Mark Oei 11 Mar 2010
			System.out.println((it.importFromFile(8, ST.getOOReportPath() + "Import Development Resources.xls", 1, 1, "Test", "Test", false, "sa", 1))[0]);
		} catch (Exception e) {
			System.out.println("An Exception has occurred. " + e.getMessage());
		} finally {
			System.exit(0);
		} //End main() Try-Catch
	} //End of main()
} //End of Import Class