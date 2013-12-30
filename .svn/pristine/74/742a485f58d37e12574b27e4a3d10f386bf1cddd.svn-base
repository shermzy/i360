package CP_Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import util.Utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import CP_Classes.common.ConnectionBean;
import CP_Classes.common.ErrorLog;

public class ImportLanguage {
	
	private ErrorLog errLog = null;
	private File inputWorkbook = null;
	private WorkbookSettings ws = null;
	private Workbook workbook = null;
	private String language = "";
	
	public ImportLanguage(){
	}
	
	/**
	 * Initialize the excel document
	 * @param sFileName
	 * @throws IOException
	 * @throws BiffException
	 * @throws FileNotFoundException
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public void initializeExcel(String sFileName) throws IOException, BiffException, FileNotFoundException {

		//Set Workbook
		inputWorkbook = new File(sFileName);

		//Workbook File Null Check
		if(inputWorkbook == null) {
			throw new FileNotFoundException();
		} //End of Workbook File Null Check

		//Initial Workbook Settings
		ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));

		//Get Workbook
		workbook = Workbook.getWorkbook(inputWorkbook);

	} //End initializeExcel()
	
	/**
	 * Imports the excel document from the user
	 * @param iImportType
	 * @param lang - The type of language selected by the user
	 * @param sFileName - The name of the file
	 * @return
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public String[] importFromFile(int iImportType, int lang, String sFileName){
		
		/*Set the language type base on what the user selected.
		 * 1 - Indonesian
		 * 2 - Thai
		 * 3 - Korean
		 * 4 - Chinese (Simplified)
		 * 5 - Chinese (Traditional) 
		 *If the lang == 0, the default is English, Chun Yeong 1 Aug 2011*/
		if(lang == 1){ language = "1"; } 
		else if (lang == 2){ language = "2"; } 
		else if (lang == 3){ language = "3"; } 
		else if (lang == 4){ language = "4"; } 
		else if (lang == 5){ language = "5"; } 
		else { language = ""; }

		//Create new Error Log
		errLog = new ErrorLog();

		//Status messages - to indicate the status for Import/Delete operations
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sImportInfo = new String[2];
		String[] sImportInfoTemp = new String[2];
		
		String sImportType = "";
		
		String sMethodName = "importFromFile"; //For printLog(), logError()
		
		try {
			//Initial Excel file
			initializeExcel(sFileName);
			
			//Get all worksheets from workbook
			Sheet[] wSheets = workbook.getSheets();
			
			/**
			 * Modified by WeiHan
			 * 25/01/2012
			 * Do a simple check on the worksheet names to ensure that the uploaded file is the template provided 
			 */	
			
			if(!wSheets[1].getName().equals("Competency")||!wSheets[2].getName().equals("KeyBehaviour")||!wSheets[3].getName().equals("Translation"))
			{
				sImportInfo[0] = "Invalid template uploaded, please upload using the template provided";
				sImportInfo[1] = "Invalid file Template Exception";
				printLog(sMethodName, "Invalid template uploaded, please upload using the template provided");
			}
			else
			{
			//wSheets[1] - Competency
			sImportType = "Import Competency";
			sImportInfoTemp = importComptency(sFileName, wSheets[1]);
			sImportInfo[0] = sImportInfoTemp[0] + " <br>";
			sImportInfo[1] = sImportInfoTemp[1] + " ";
			//Print log to console
			printLog(sMethodName, sImportType + " completed");
			
			//wSheets[2] - Key Behaviour
			sImportType = "Import Key Behaviour";
			sImportInfoTemp = importKeyBehaviour(sFileName, wSheets[2]);
			sImportInfo[0] += sImportInfoTemp[0] + " <br>";
			sImportInfo[1] += sImportInfoTemp[1] + " ";
			//Print log to console
			printLog(sMethodName, sImportType + " completed");
			
			//wSheets[3] - TB_translated
			sImportType = "Import Translation";
			sImportInfoTemp = importTranslated(sFileName, wSheets[3]);
			sImportInfo[0] += sImportInfoTemp[0] + " <br>";
			sImportInfo[1] += sImportInfoTemp[1];
			//Print log to console
			printLog(sMethodName, sImportType + " completed");
			}
			
		} catch (FileNotFoundException e) {
			//Set Import Status 
			sImportInfo[0] = "Error locating Import File. Please contact the Administrator for assistant.";
			sImportInfo[1] = "FileNotFound Exception.";
			//Add Error Log
			logError(sMethodName, sImportInfo[1]);
		} catch (BiffException e) {
			//Set Import Status 
			sImportInfo[0] = "Error accessing Import File. Please contact the Administrator for assistant.";
			sImportInfo[1] = "BiffException Exception.";
			//Add Error Log
			logError(sMethodName, sImportInfo[1]);
		} catch (IOException e) {
			//Set Import Status 
			sImportInfo[0] = "Error Reading Import File. Please contact the Administrator for assistant.";
			sImportInfo[1] = "IOException.";
			//Add Error Log
			logError(sMethodName, sImportInfo[1]);
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
				String sErrorLogFilePath = errLog.writeErrorLog("ImportLanguage");
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
		} //End of Save Error Log Try-Catch
		
		//Print log to console
		printLog(sMethodName, "End " + sMethodName);
		
		//Return status message
		return sImportInfo;
	}
	
	/**
	 * Reads the excel sheet and updates the database. No similar phrases found will be reported as an error.
	 * @param sFileName 
	 * @param wSheets
	 * @return String[0] - Import Status, String[1] Import Error Type
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public String[] importComptency(String sFileName, Sheet wSheets){
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		
		//Get Current Sheet - Note: Convert to while loop if multiple sheets are used
		Sheet wCurrentSheet = wSheets;
		
		//Get Cells of Critical Column
		Cell cCompetencyNameEng = wCurrentSheet.findCell("CompetencyName(English)");
		Cell cCompetencyNameTrans = wCurrentSheet.findCell("CompetencyName(Translation)");
		Cell cCompetencyDefinitionTrans = wCurrentSheet.findCell("CompetencyDefinition(Translation)");
		
		//Get Column ID of Critical Column
		int iColCompetencyName = -1;
		int iColCompetencyNameTrans = -1;
		int iColCompetencyDefinitionTrans = -1;
		
		String sMethodName = "importCompetency"; //For printLog(), logError(), logDuplication
		
		//Similarity Percentage
		double dSimilarityPercentage = 5.0;
		
		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;
		
		String sCriticalColumnMissing = "";
		
		try {

			//Critical Column Missing Check - CompetencyName 
			if(cCompetencyNameEng == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "CompetencyName(English)";
			} else {
				iColCompetencyName = cCompetencyNameEng.getColumn();
			} //End of Critical Column Missing Check - CompetencyName
			
			iColCompetencyNameTrans = cCompetencyNameTrans.getColumn();
			iColCompetencyDefinitionTrans = cCompetencyDefinitionTrans.getColumn();
			
			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				//Print to Console Log
				logError(sMethodName, sOperationStatus[0]);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus; 
			} //End of Critical Column Missing Error Check
			
			int iStartRowIndex = cCompetencyNameEng.getRow() + 2;
			int iCurrentRowIndex = iStartRowIndex;
			//iTotalRecords = (wCurrentSheet.getRows() - iStartRowIndex);
			
			/**
			 * Modified by WeiHan
			 * 19/01/2012
			 * To fix a bug regarding the application detecting empty rows and throwing error messages in the log
			 */	
			
			//create a temporary int to iterate through the rows to check if it is empty
			int currentRowTemp = iCurrentRowIndex;
			
			//loop to iterate through the rows one by one to check if there are empty rows, if emtpy row is found, break out of loop 
			while(true)
			{
				if(wCurrentSheet.getCell(iColCompetencyName, currentRowTemp).getContents().trim()=="")
				{
					break;
				}
				else
				{
					iTotalRecords++;
					currentRowTemp++;
				}
			
			}
			
			//Data Row While-Loop
			while (iCurrentRowIndex < iTotalRecords + iStartRowIndex) {
				//Invalid Data Row Status
				String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sCompetencyName = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyName, iCurrentRowIndex).getContents().trim());
				String sCompetencyNameTrans = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyNameTrans, iCurrentRowIndex).getContents().trim());
				String sCompetencyDefinitionTrans = Utils.SQLFixer(wCurrentSheet.getCell(iColCompetencyDefinitionTrans, iCurrentRowIndex).getContents().trim());

				Vector<String> vCompName = new Vector<String>();
				boolean isSimilarStringFound = false;

				//Get Competency Name Vector List
				vCompName = getAllCompetencies();

				//vCompName Null Check
				if(vCompName == null) {
					//SQL Exception occurs in getting vCompName
					sRowErrorStatus = "SQL Exceptions - Error getting Competency List row " + (iCurrentRowIndex + 1);
					//Add Error Log
					logError(sMethodName, sRowErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Start Competency Name Best Match For-Loop
					for (int i = 0; i < vCompName.size(); i++) {
						String compName = (String) vCompName.get(i);

						//Compute LDistance value and get Similartiy Value
						double dSimilarityCompNameValue = Utils.getSimilarityValue(sCompetencyName, compName.trim());

						//Competency Name Similarity Check
						if(dSimilarityCompNameValue <= dSimilarityPercentage) {
							//Same Record if dSimilarityCompNameValue == 0
							//Other than that, they are similar records
												
							isSimilarStringFound = true;
							iTotalUpdatedRecords++;
							
							//Do update statements
							/**
							 * Modified by Yiping
							 * 06/01/2012
							 * To support updating foreign characters to the database
							 */	
							String query = "UPDATE Competency SET CompetencyName"+ language + " = N'" + sCompetencyNameTrans + "', CompetencyDefinition" +
											language + " = N'" + sCompetencyDefinitionTrans + "' WHERE CompetencyName ='" + sCompetencyName + "'";
						
							Connection con = null;
							Statement st = null;
					  	   	try {
					  	   		con=ConnectionBean.getConnection();
					  	   		st=con.createStatement();
					  	   		st.execute(query);
					  	   	}catch(Exception ex){
								System.out.println("ImportLanguage.java - importComptency - " + ex.getMessage());
								//SQL Exception occurs in getting vTranslation
								sRowErrorStatus = "SQL Exceptions - Error updating Competency List row " + (iCurrentRowIndex + 1);
								//Add Error Log
								logError(sMethodName, sRowErrorStatus);
								iTotalErrorRecords++;
							}
							finally{
								ConnectionBean.closeStmt(st); //Close statement
								ConnectionBean.close(con); //Close connection
							}
						} //End of Competency Name Similarity Check
					} //End of Competency Name Best Match For-Loop
				
					//Similar String Found Check
					if(!isSimilarStringFound){
						//output to the error log to tell them there is no competency found
						sRowErrorStatus = "Competency Name Similarity Check Failed (No existing Record) at row " + 
						(iCurrentRowIndex + 1) + " - '" + sCompetencyName + "'";
						
						logError(sMethodName, sRowErrorStatus);
						iTotalErrorRecords++;
					} //End of Similar String Found Check
				} //End of vCompName Null Check
					
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
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		
		return sOperationStatus;
	}
	
	/**
	 * Reads the excel sheet and updates the database. No similar phrases found will be reported as an error.
	 * @param sFileName
	 * @param wSheets
	 * @return String[0] - Import Status, String[1] Import Error Type
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public String[] importKeyBehaviour(String sFileName, Sheet wSheets){
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		
		//Get Current Sheet - Note: Convert to while loop if multiple sheets are used
		Sheet wCurrentSheet = wSheets;
		
		//Get Cells of Critical Column
		Cell cKeyBehaviour = wCurrentSheet.findCell("KeyBehaviour(English)");
		Cell cKeyBehaviourTrans = wCurrentSheet.findCell("KeyBehaviour (Translation)");

		//Get Column ID of Critical Column
		int iColKeyBehaviour = -1;
		int iColKeyBehaviourTrans = -1;
		
		String sMethodName = "importKeyBehaviour"; //For printLog(), logError(), logDuplication
		
		//Similarity Percentage
		double dSimilarityPercentage = 5.0;
		
		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;

		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - KeyBehaviour 
			if(cKeyBehaviour == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "KeyBehaviour(English)";
			} else {
				iColKeyBehaviour = cKeyBehaviour.getColumn();
			} //End of Critical Column Missing Check - KeyBehaviour 

			iColKeyBehaviourTrans = cKeyBehaviourTrans.getColumn();

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				//Print to Console Log
				logError(sMethodName, sOperationStatus[0]);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			int iStartRowIndex = cKeyBehaviour.getRow() + 2;
			int iCurrentRowIndex = iStartRowIndex;
			
			/**
			 * Modified by WeiHan
			 * 19/01/2012
			 * To fix a bug regarding the application detecting empty rows and throwing error messages in the log
			 */	
			
			//create a temporary int to iterate through the rows to check if it is empty
			int currentRowTemp = iCurrentRowIndex;
			
			//loop to iterate through the rows one by one to check if there are empty rows, if emtpy row is found, break out of loop 
			while(true)
			{
				if(wCurrentSheet.getCell(iColKeyBehaviour, currentRowTemp).getContents().trim()=="")
				{
					break;
				}
				else
				{
					iTotalRecords++;
					currentRowTemp++;
				}
			
			}
			
			//Data Row While-Loop
			while (iCurrentRowIndex < iTotalRecords + iStartRowIndex) {
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sKeyBehaviour = Utils.SQLFixer(wCurrentSheet.getCell(iColKeyBehaviour, iCurrentRowIndex).getContents().trim());
				String sKeyBehaviourTrans = Utils.SQLFixer(wCurrentSheet.getCell(iColKeyBehaviourTrans, iCurrentRowIndex).getContents().trim());
				Vector<String> vKeyBehaviour = new Vector<String>();
				boolean isSimilarStringFound = false;
				vKeyBehaviour = getAllKeyBehaviours();

				//vKeyBehaviour List Null Check
				if(vKeyBehaviour == null) {
					//SQL Exception occurs in getting vKeyBehaviour
					sRowErrorStatus = "SQL Exceptions - Error getting KeyBehaviour List row " + (iCurrentRowIndex + 1);
					//Add Error Log
					logError(sMethodName, sRowErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Start Key Behaviour Best Match For-Loop
					for(int i = 0; i < vKeyBehaviour.size(); i++) {
						String sKeyBehaviourTemp = (String) vKeyBehaviour.get(i);

						//Compute LDistance value and get Similartiy Value
						double dSimilarityKBNameValue = Utils.getSimilarityValue(sKeyBehaviour, sKeyBehaviourTemp.trim());

						//Key Behaviour Similarity Check
						if(dSimilarityKBNameValue <= dSimilarityPercentage) {
							//Same Record if dSimilarityKBNameValue == 0
							//Other than that, they are similar records
							
							isSimilarStringFound = true;
							iTotalUpdatedRecords++;
							
							/**
							 * Modified by Yiping
							 * 06/01/2012
							 * To support updating foreign characters to the database
							 */	
							//Do update statements
							String query = "UPDATE KeyBehaviour SET KeyBehaviour"+ language + " = N'" + sKeyBehaviourTrans 
										+ "' WHERE KeyBehaviour ='" + sKeyBehaviour + "'";
						
							Connection con = null;
							Statement st = null;
					  	   	try {
					  	   		con=ConnectionBean.getConnection();
					  	   		st=con.createStatement();
					  	   		st.execute(query);
					  	   		//System.out.println("No of rows updated: " + st.getUpdateCount());
					  	   	}catch(Exception ex){
								System.out.println("ImportLanguage.java - importKeyBehaviour - " + ex.getMessage() + " - " + query);
								//SQL Exception occurs in getting vTranslation
								sRowErrorStatus = "SQL Exceptions - Error updating Key Behaviour List row " + (iCurrentRowIndex + 1);
								//Add Error Log
								logError(sMethodName, sRowErrorStatus);
								iTotalErrorRecords++;
							}
							finally{
								ConnectionBean.closeStmt(st); //Close statement
								ConnectionBean.close(con); //Close connection
							}
							
						} //End of Key Behaviour Name Similarity Check
						
						
					} //End of Key Behaviour Best Match For-Loop
				} //End of vKeyBehaviour List Null Check

				//Similar String Found Check
				if(!isSimilarStringFound) {
					//output to the error log to tell them there is no keybehaviour found
					sRowErrorStatus = "Key Behaviour Similarity Check Failed (No existing Record) at row " + 
					(iCurrentRowIndex + 1) + " - '" + sKeyBehaviour + "'";
					
					logError(sMethodName, sRowErrorStatus);
					iTotalErrorRecords++;
				} //End of Similar String Found Check
						

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
			e.printStackTrace();
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		printLog("total records for keybeh", iTotalRecords+"");
		printLog("total updated records for keybeh", iTotalUpdatedRecords+"");
		return sOperationStatus;
	}
	
	/**
	 * Reads from the excel sheet and updates the database. If there are no similar translated words found, they will be inserted into the database
	 * @param sFileName
	 * @param wSheets
	 * @return String[0] - Import Status, String[1] Import Error Type
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public String[] importTranslated(String sFileName, Sheet wSheets){
		//Status message - to indicate the status for
		// 0 - Import Status - The error message to be shown on the JSP page
		// 1 - Import Error Type - The error type of the Error
		String[] sOperationStatus = new String[2];
		
		//Get Current Sheet - Note: Convert to while loop if multiple sheets are used
		Sheet wCurrentSheet = wSheets;
		
		//Get Cells of Critical Column
		Cell cTranslation = wCurrentSheet.findCell("English");
		Cell cTranslationTrans = wCurrentSheet.findCell("Translation");
		
		//Get Column ID of Critical Column
		int iColTranslation = -1;
		int iColTranslationTrans = -1;
		
		String sMethodName = "importTranslated"; //For printLog(), logError(), logDuplication
		
		//Similarity Percentage
		double dSimilarityPercentage = 5.0;
		
		//Counters
		int iTotalRecords = 0;
		int iTotalAddedRecords = 0;
		int iTotalUpdatedRecords = 0;
		int iTotalDuplicatedRecords = 0;
		int iTotalErrorRecords = 0;
		
		//Critical Column Missing Try-Catch
		try {
			String sCriticalColumnMissing = "";

			//Critical Column Missing Check - Translation 
			if(cTranslation == null) {
				//Append Missing Critical Column
				sCriticalColumnMissing = "English";
			} else {
				iColTranslation = cTranslation.getColumn();
			} //End of Critical Column Missing Check - Translation 

			iColTranslationTrans = cTranslationTrans.getColumn();

			//Critical Column Missing Error Check
			if(!sCriticalColumnMissing.equals("")) {
				//Write Error Log and return an error message with path of the Error Log
				sOperationStatus[0] = "Column(s) missing - " + sCriticalColumnMissing + ".";
				sOperationStatus[1] = "One or more Critical Column is missing";
				//Print to Console Log
				logError(sMethodName, sOperationStatus[0]);
				iTotalErrorRecords++;
				//Return status messages
				return sOperationStatus;
			} //End of Critical Column Missing Error Check

			//Set Start & Current Row Index
			int iStartRowIndex = cTranslation.getRow() + 2;
			int iCurrentRowIndex = iStartRowIndex;

			/**
			 * Modified by WeiHan
			 * 19/01/2012
			 * To fix a bug regarding the application detecting empty rows and throwing error messages in the log
			 */	
			
			//create a temporary int to iterate through the rows to check if it is empty
			int currentRowTemp = iCurrentRowIndex;
			
			//loop to iterate through the rows one by one to check if there are empty rows, if emtpy row is found, break out of loop 
			while(true)
			{
				if(wCurrentSheet.getCell(iColTranslation, currentRowTemp).getContents().trim()=="")
				{
					break;
				}
				else
				{
					iTotalRecords++;
					currentRowTemp++;
				}
			
			}
			
			//Data Row While-Loop
			while (iCurrentRowIndex < iTotalRecords + iStartRowIndex) {
			
				//Error Indicator -> If true, skip row
				boolean isInvalidDataRow = false;
				//Invalid Data Row Status
				String sRowErrorStatus = "";

				//Get Competency Info from Excel file	- Critical Column	
				String sTranslation = Utils.SQLFixer(wCurrentSheet.getCell(iColTranslation, iCurrentRowIndex).getContents().trim());
				String sTranslationTrans = Utils.SQLFixer(wCurrentSheet.getCell(iColTranslationTrans, iCurrentRowIndex).getContents().trim());

				boolean isSimilarStringFound = false;
				Vector<String> vTranslation = getAllTranslation();

				//vTranslation List Null Check
				if(vTranslation == null) {
					//SQL Exception occurs in getting vTranslation
					sRowErrorStatus = "SQL Exceptions - Error getting Translation List row " + (iCurrentRowIndex + 1);
					//Add Error Log
					logError(sMethodName, sRowErrorStatus);
					iTotalErrorRecords++;
				} else {
					//Start Key Behaviour Best Match For-Loop
					for(int i = 0; i < vTranslation.size(); i++) {
						String sTranslationTemp = Utils.SQLFixer((String) vTranslation.get(i));

						//Compute LDistance value and get Similartiy Value
						double dSimilarityTranslationValue = Utils.getSimilarityValue(sTranslation, sTranslationTemp);

						//Key Behaviour Similarity Check
						if(dSimilarityTranslationValue <= dSimilarityPercentage) {
							//Same Record if dSimilarityTranslationValue == 0
							//Other than that, they are similar records
							
							isSimilarStringFound = true;
							iTotalUpdatedRecords++;
							
							/**
							 * Modified by Yiping
							 * 06/01/2012
							 * To support updating foreign characters to the database
							 */	
							//Do update statements
							String query = "UPDATE TB_Translation SET Text" + language + "=N'" + sTranslationTrans 
											+ "' WHERE EngText = '" + sTranslation + "'";
						
							Connection con = null;
							Statement st = null;
					  	   	try {
					  	   		con=ConnectionBean.getConnection();
					  	   		st=con.createStatement();
					  	   		st.execute(query);
					  	   		//System.out.println("No of rows updated: " + st.getUpdateCount());
					  	   	}catch(Exception ex){
								System.out.println("ImportLanguage.java - importKeyBehaviour (UPDATE)- " + ex.getMessage() + " - " + query);
								//SQL Exception occurs in getting vTranslation
								sRowErrorStatus = "SQL Exceptions - Error updating Translation List row " + (iCurrentRowIndex + 1);
								//Add Error Log
								logError(sMethodName, sRowErrorStatus);
								iTotalErrorRecords++;
							}
							finally{
								ConnectionBean.closeStmt(st); //Close statement
								ConnectionBean.close(con); //Close connection
							}
							
						} //End of Translation Similarity Check
						
					} //End of Translation Best Match For-Loop
				} //End of vTranslation List Null Check

				//Similar String Found Check
				if(!isSimilarStringFound) {
					iTotalAddedRecords++;
					String query = "INSERT INTO TB_Translation VALUES ('" + sTranslation + "', ";
					if(language.equals("1")) query += "'" + sTranslationTrans + "', "; else query += "'',";
					if(language.equals("2")) query += "'" + sTranslationTrans + "', "; else query += "'',";
					if(language.equals("3")) query += "'" + sTranslationTrans + "', "; else query += "'',";
					if(language.equals("4")) query += "'" + sTranslationTrans + "', "; else query += "'',";
					if(language.equals("5")) query += "'" + sTranslationTrans + "' "; else query += "''";
					query += ")";
					
					Connection con = null;
					Statement st = null;
			  	   	try {
			  	   		con=ConnectionBean.getConnection();
			  	   		st=con.createStatement();
			  	   		st.execute(query);
			  	   		//System.out.println("No of rows inserted: " + st.getUpdateCount());
			  	   	}catch(Exception ex){
						System.out.println("ImportLanguage.java - importKeyBehaviour (INSERT) - " + ex.getMessage() + " - " + query);
						//SQL Exception occurs in getting vTranslation
						sRowErrorStatus = "SQL Exceptions - Error inserting Translation List row " + (iCurrentRowIndex + 1);
						//Add Error Log
						logError(sMethodName, sRowErrorStatus);
						iTotalErrorRecords++;
					}
					finally{
						ConnectionBean.closeStmt(st); //Close statement
						ConnectionBean.close(con); //Close connection
					}
				} //End of Similar String Found Check
						
				iCurrentRowIndex++;
			} //End of Data Row While-Loop
			
			
			//Error Log Check
			if(errLog.hasError()) {
				if(iTotalRecords == iTotalErrorRecords)
					sOperationStatus[0] = "Import Translation Unsuccessful.";
				else
					sOperationStatus[0] = "Import Translation Completed.";
				sOperationStatus[1] = "Error(s) Encountered.";
			} else {
				sOperationStatus[0] = "Import Translation Successfully.";
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
			System.out.println("exception = " + e.getMessage());
			//If Exception occurs while writing Error Log, return an error message
			sOperationStatus[0] = sOperationStatus[1] + " and an Exception have occurred while saving the Error Log";
			//Print to Console Log
			printLog(sMethodName, sOperationStatus[0]);
		} //Critical Column Missing Try-Catch
		//Return status messages
		return sOperationStatus;
	}
	
	/**
	 * Retrieves all the competency names' column only from the database
	 * @return a Vector of competency names
	 * @throws SQLException
	 * @throws Exception
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public Vector<String> getAllCompetencies() throws SQLException, Exception {
		String query = "";	
		Vector<String> compNames = new Vector<String>();
		query = "SELECT Competency.CompetencyName FROM Competency";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   while(rs.next()) {
  			 compNames.add(rs.getString("CompetencyName"));
  		   }
  	   	}catch(Exception ex){
			System.out.println("ImportLanguage.java - getAllCompetencies - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return compNames;
	}
	
	/**
	 * Retrieves all the key behaviours' column only from the database
	 * @return a Vector of key behaviours
	 * @throws SQLException
	 * @throws Exception
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public Vector<String> getAllKeyBehaviours() throws SQLException, Exception {
		String query = "";	
		Vector<String> KBNames = new Vector<String>();
		query = "SELECT KeyBehaviour.KeyBehaviour FROM KeyBehaviour";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   while(rs.next()) {
  			 KBNames.add(rs.getString("KeyBehaviour"));
  		   }
  	   	}catch(Exception ex){
			System.out.println("ImportLanguage.java - getAllKeyBehaviours - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return KBNames;
	}

	/**
	 * Retrieves all the EngText column only from the database
	 * @return a Vector of EngText
	 * @throws SQLException
	 * @throws Exception
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public Vector<String> getAllTranslation() throws SQLException, Exception {
		String query = "";	
		Vector<String> KBNames = new Vector<String>();
		query = "SELECT TB_Translation.EngText FROM TB_Translation";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   while(rs.next()) {
  			 KBNames.add(rs.getString("EngText"));
  		   }
  	   	}catch(Exception ex){
			System.out.println("ImportLanguage.java - getAllTranslation - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return KBNames;
	}
	
	/**
	 * This method prints a message to Console Log
	 * @param sMethodName - The method name of the method the log is created for
	 * @param sMessage    - The message of this log entry
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	private void printLog(String sMethodName, String sMessage) {
		//Print to Console Log
		System.out.println("ImportLanguage.java - " + sMethodName + "() - " + sMessage);
	} //End of logError()
	
	/**
	 * This method prints an error log to Console and add an Error log into errLog object
	 * @param sMethodName   - The method name of the method the log is created for
	 * @param sErrorMessage - The error message of this error log entry
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	private void logError(String sMethodName, String sErrorMessage) {
		//Record to Error Log
		errLog.addError("Import File Exception", "ImportLanguage.java - " + sMethodName + "()",
				sErrorMessage, "");
		//Print to Console Log
		printLog(sMethodName, sErrorMessage);
	} //End of logError()
	
	/**
	 * Import data into the database from File using main()
	 * Note: For testing purpose
	 * @param args
	 */
	public static void main(String[] args) {
		//Start main() Try-Catch
		try {
			ImportLanguage it = new ImportLanguage();
			Setting ST = new Setting();
			//Added to allow checking of UserType
			//Mark Oei 11 Mar 2010
			it.importFromFile(0, 5, "D:/UploadLanguageTemplate.xls");
			//System.out.println((it.importFromFile(8, ST.getOOReportPath() + "Import Development Resources.xls", 1, 1, "Test", "Test", false, "sa", 1))[0]);
		} catch (Exception e) {
			System.out.println("An Exception has occurred. " + e.getMessage());
		} finally {
			System.exit(0);
		} //End main() Try-Catch
	} //End of main()
}