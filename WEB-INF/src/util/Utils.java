package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import CP_Classes.DevelopmentResources;
import CP_Classes.Setting;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voKeyBehaviour;
import CP_Classes.vo.votblDRA;
import CP_Classes.vo.votblDRARES;

public class Utils {

	private Setting server = new Setting();
	
	public static String convertDateFormat(Date dt)
	{
		String sDate = "";
		
        try {
			Date timeStamp = dt;
			SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
			if(timeStamp != null)
				sDate  =  dFormat.format(timeStamp);
			//System.out.println(sDate);
        }
        catch(Exception E) {
        	System.out.println("Parsing Exception in Utils = " + E);
        }
		return sDate;
	}
	
	//*****************************
	// Compute Levenshtein distance
	//*****************************
	public static int computeLD (String s, String t) {
	  int d[][]; // matrix
	  int n; // length of s
	  int m; // length of t
	  int i; // iterates through s
	  int j; // iterates through t
	  char s_i; // ith character of s
	  char t_j; // jth character of t
	  int cost; // cost

	    // Step 1

	    n = s.length ();
	    m = t.length ();
	    if (n == 0) {
	      return m;
	    }
	    if (m == 0) {
	      return n;
	    }
	    d = new int[n+1][m+1];

	    // Step 2

	    for (i = 0; i <= n; i++) {
	      d[i][0] = i;
	    }

	    for (j = 0; j <= m; j++) {
	      d[0][j] = j;
	    }

	    // Step 3

	    for (i = 1; i <= n; i++) {

	      s_i = s.charAt (i - 1);

	      // Step 4

	      for (j = 1; j <= m; j++) {

	        t_j = t.charAt (j - 1);

	        // Step 5

	        if (s_i == t_j) {
	          cost = 0;
	        }
	        else {
	          cost = 1;
	        }

	        // Step 6

	        d[i][j] = Minimum (d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);

	      }

	    }

	    // Step 7

	    return d[n][m];

	}
	
	//****************************
	// Get minimum of three values
	//****************************
	private static int Minimum (int a, int b, int c) {
	  int mi;

	    mi = a;
	    if (b < mi) {
	      mi = b;
	    }
	    if (c < mi) {
	      mi = c;
	    }
	    return mi;

	}
	
	/**
     * Fix the String input to SQL Database.
     * Add one more aphrostrophy to each aphrostrophy found in the String.
     * @param statement
     * @return statement that has been fixed
     */
    public static String SQLFixer(String statement) 
    {
        String newStatement = statement.trim();
        String left = "";
        String right = "";

        int start = 0;
        int end = statement.indexOf("'");

        if(end >= 0)
           newStatement = "";

        while(end >= 0) {
            left = statement.substring(start, end+1) + "'";	
            right = statement.substring(end+1, statement.length());
            newStatement = newStatement + left;

            statement = right;

            end = right.indexOf("'");									
        }

        newStatement = newStatement + right;

        
        return newStatement;
    }
    
    public static double getSimilarityValue(String sCompareText, String sCompareOther) {
    	int iLD = Utils.computeLD(sCompareText, sCompareOther);
        double dSimilarityValue = ((double) iLD / (double) sCompareText.length()) * 100;
        return dSimilarityValue;
	} //End of getSimilarityValue()
    
    public static Vector getAllRecordsBasedOnLength(String sRecordType, int iSearchTermLength, String sSearchKey, 
    										 		int iSearchLevel, double dSimilarityPercent, int iOrgID) {
    	Vector vRecords = new Vector();
	    double dRangeValue = Math.ceil((dSimilarityPercent / (double) 100) * (double) iSearchTermLength);
	    int iLowerRangeValue = (int) ((double) iSearchTermLength - dRangeValue);
	    int iUpperRangeValue = (int) ((double) iSearchTermLength + dRangeValue);
    	
	    //Form Query Statement
	    String sQuery = "SELECT * FROM <<sRecordType>> WHERE <<sSearchKey>> AND (FKOrganizationID = " + iOrgID + 
    					" OR IsSystemGenerated = 1) AND LEN(<<sSearchPK>>) BETWEEN " +
    					iLowerRangeValue + " AND " + iUpperRangeValue;
	    
	    //Record Type Check
	    if(sRecordType != null && sRecordType.equals("CompetencyName")) {
	    	sQuery = sQuery.replaceFirst("<<sSearchKey>> AND ", sSearchKey);	 
	    	sQuery = sQuery.replaceFirst("<<sRecordType>>", "Competency");
	    	sQuery = sQuery.replaceFirst("<<sSearchPK>>", sRecordType);	    	
	    } else if(sRecordType != null && sRecordType.equals("CompetencyDefinition")) {
	    	sQuery = sQuery.replaceFirst("<<sSearchKey>>", sRecordType + "='" + sSearchKey + "'");	 
	    	sQuery = sQuery.replaceFirst("<<sRecordType>>", "Competency");
	    	sQuery = sQuery.replaceFirst("<<sSearchPK>>", sRecordType);	    	
	    } else if(sRecordType != null && sRecordType.equals("KeyBehaviour")) {
	    	sQuery = sQuery.replaceFirst("<<sSearchKey>>", sSearchKey + 
	    								 " AND KBLevel=" + iSearchLevel);	 
	    	sQuery = sQuery.replaceFirst("<<sRecordType>>", "KeyBehaviour");
	    	sQuery = sQuery.replaceFirst("<<sSearchPK>>", sRecordType);	   
	    } else if(sRecordType != null && sRecordType.equals("DevelopmentActivties")) {
	    	sQuery = sQuery.replaceFirst("<<sSearchKey>>", sSearchKey);	 
			sQuery = sQuery.replaceFirst("<<sRecordType>>", "tblDRA");
			sQuery = sQuery.replaceFirst("<<sSearchPK>>", "DRAStatement");
    	} else if(sRecordType != null && sRecordType.equals("DevelopmentResources")) {
	    	sQuery = sQuery.replaceFirst("<<sSearchKey>>", sSearchKey);	 
			sQuery = sQuery.replaceFirst("<<sRecordType>>", "tblDRARes");
			sQuery = sQuery.replaceFirst("<<sSearchPK>>", "Resource");
    	}//End of Record Type Check
	    
	    System.out.println("getAllRecordsBasedOnLength() - Query - " + sQuery);
	
	    //Database Connections & ResultSet
	    Connection con = null;
	    Statement st = null;
	    ResultSet rs = null;
	    
	    //Start Try-Catch
	    try {	
	        con = ConnectionBean.getConnection();
	        st = con.createStatement();
	        rs = st.executeQuery(sQuery);
	        
	        //Start ResultSet While-Loop
	        while(rs.next()) {
	        	//Record Type Check
	        	if(sRecordType != null && sRecordType.startsWith("Competency")) {
		            voCompetency voComp = new voCompetency();
		            
		            voComp.setCompetencyName(rs.getString("CompetencyName"));
		            voComp.setCompetencyDefinition(rs.getString("CompetencyDefinition"));
		            voComp.setFKOrganizationID(rs.getInt("FKOrganizationID"));
		            
	        		vRecords.add(voComp);
	        	} else if(sRecordType != null && sRecordType.startsWith("KeyBehaviour")) { 
	        		voKeyBehaviour voKB = new voKeyBehaviour();
	        		
	        		voKB.setKeyBehaviour(rs.getString("KeyBehaviour"));
	        		voKB.setKBLevel(Integer.parseInt(rs.getString("KBLevel")));
	        		voKB.setFKCompetency(Integer.parseInt(rs.getString("FKCompetency")));
	        		voKB.setFKOrganizationID(rs.getInt("FKOrganizationID"));
	        		
	        		vRecords.add(voKB);
	        	} else if(sRecordType != null && sRecordType.startsWith("DevelopmentActivties")) { 
	                votblDRA voDA = new votblDRA();
	                
	                voDA.setDRAID(rs.getInt("DRAID"));
	                voDA.setCompetencyID(rs.getInt("CompetencyID"));
	                voDA.setDRAStatement(SQLFixer(rs.getString("DRAStatement").trim()));
	                voDA.setDRACounter(rs.getInt("DRACounter"));
	                voDA.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
	                voDA.setFKCompanyID(rs.getInt("FKCompanyID"));
	                voDA.setFKOrganizationID(rs.getInt("FKOrganizationID"));

	                vRecords.add(voDA);
	        	} else if(sRecordType != null && sRecordType.startsWith("DevelopmentResources")) { 
	                votblDRARES voDR = new votblDRARES();
	                
	                voDR.setResID(rs.getInt("ResID"));
	                voDR.setCompetencyID(rs.getInt("CompetencyID"));
	                voDR.setFKCompanyID(rs.getInt("FKCompanyID"));
	                voDR.setFKOrganizationID(rs.getInt("FKOrganizationID"));
	                voDR.setResource(SQLFixer(rs.getString("Resource").trim()));
	                voDR.setResType(rs.getInt("ResType"));

	                vRecords.add(voDR);
	        	} //End of Record Type Check
	        } //End of ResultSet While-Loop
	    } catch (SQLException e) {
	        System.err.println("Utils.java - getAllRecordsBasedOnLength - " + e);
	        return null;
	    } finally {
	        ConnectionBean.closeRset(rs); // Close ResultSet
	        ConnectionBean.closeStmt(st); // Close statement
	        ConnectionBean.close(con); // Close connection	
	    } //End of Try-Catch	
	    return vRecords;
    } //getAllRecordsBasedOnLength()
    
    /**
	  * To create a zip archive of the filenames specified
	  * @param String zipFileName - Specify the file name of the zip archive to be created
	  * @param Vector filenames - Specify the list of files that needs to be archive in zip.
	  * 				 		- filenames[0], where the file is located to archive
	  *                 		- filenames[1], the naming of the file in the archive  
	  * @return String - return the full file path name of the zip file when it is created sucessfully created. Return empty if it is created unsuccessfully. 
	  * @author Sebastian
	  * @since v.1.3.12.81 (26 July 2010)
	**/
	public String zipArchive(String zipFileName, Vector filenames)
	{
		//Create zip file putting all questionnaires generated into the zip Archive and prompt for download of zip file
		String output = server.getReport_Path() + "\\";
		byte[] buffer = new byte[1024];
		String[] file_name = new String[2]; //file_name[0] = input filename, file_name[1] = output filename
		String inputFile_name = "";
		String outputFile_name = "";
		String zipFilePathName = output + zipFileName;

		try {
			FileOutputStream fout = new FileOutputStream(zipFilePathName); //create object of FileOutputStream
			ZipOutputStream zout = new ZipOutputStream(fout); //create object of ZipOutputStream from FileOutputStream

			for (int i=0; i<filenames.size(); i++)
			{
				file_name = (String[]) filenames.get(i);
				
				inputFile_name = file_name[0];
				outputFile_name = file_name[1];

				FileInputStream fin = new FileInputStream(output + inputFile_name);
				zout.putNextEntry(new ZipEntry(outputFile_name)); //writing a new Zip entry to the zip file and positions the stream to the start of the entry data

				//write the file after every entry
				int length;

				while((length = fin.read(buffer)) > 0)
				{
					zout.write(buffer, 0, length);
				}

				zout.closeEntry(); //close entry
				fin.close(); //close the InputStream

			}

			zout.close();
			
			return zipFilePathName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return "";
	}
}
