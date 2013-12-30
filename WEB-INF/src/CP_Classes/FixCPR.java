package CP_Classes;

import java.sql.*;

/**
 * Checked with Rianto on 26Nov2007. 
 * Dont need this anymore. Commented off in the meantime.
 * 
 * @author ?
 *
 */
public class FixCPR
{
	/*private Database db = new Database();
	private int iRater[];
	//private String sRaterCode[];
	private int iSurvey[];
	private int iComp[];
	private int iBehv[];
	private double dAvg[][];	// [Survey][Competency]
	
	public FixCPR()
	{
		iSurvey = new int [4];
		iComp = new int [12];		
		dAvg = new double [4][12];	// [Survey Size][Competency Size]
		
		iSurvey[0] = 459;
		iSurvey[1] = 460;
		iSurvey[2] = 461;
		iSurvey[3] = 462;
		
		iComp[0] = 622;
		iComp[1] = 623;
		iComp[2] = 624;
		iComp[3] = 625;
		iComp[4] = 626;
		iComp[5] = 627;
		iComp[6] = 628;
		iComp[7] = 629;
		iComp[8] = 630;
		iComp[9] = 631;
		iComp[10] = 632;
		iComp[11] = 633;
		
		dAvg[0][0] = 6.7;
		dAvg[0][1] = 6.87;
		dAvg[0][2] = 6.58;
		dAvg[0][3] = 6.89;
		dAvg[0][4] = 6.84;
		dAvg[0][5] = 6.72;
		dAvg[0][6] = 6.69;
		dAvg[0][7] = 6.87;
		dAvg[0][8] = 6.87;
		dAvg[0][9] = 6.75;
		dAvg[0][10] = 6.9;
		dAvg[0][11] = 6.7;
		
		dAvg[1][0] = 6.86;
		dAvg[1][1] = 7.05;
		dAvg[1][2] = 6.76;
		dAvg[1][3] = 7.13;
		dAvg[1][4] = 7.05;
		dAvg[1][5] = 6.96;
		dAvg[1][6] = 6.98;
		dAvg[1][7] = 7.14;
		dAvg[1][8] = 7.04;
		dAvg[1][9] = 6.89;
		dAvg[1][10] = 7.08;
		dAvg[1][11] = 7;
		
		dAvg[2][0] = 7.25;
		dAvg[2][1] = 7.33;
		dAvg[2][2] = 7.24;
		dAvg[2][3] = 7.2;
		dAvg[2][4] = 7.32;
		dAvg[2][5] = 7.15;
		dAvg[2][6] = 7.13;
		dAvg[2][7] = 7.15;
		dAvg[2][8] = 7.29;
		dAvg[2][9] = 7.26;
		dAvg[2][10] = 7.34;
		dAvg[2][11] = 7.2;
		
		dAvg[3][0] = 7.43;
		dAvg[3][1] = 7.61;
		dAvg[3][2] = 7.46;
		dAvg[3][3] = 7.53;
		dAvg[3][4] = 7.51;
		dAvg[3][5] = 7.25;
		dAvg[3][6] = 7.35;
		dAvg[3][7] = 7.47;
		dAvg[3][8] = 7.39;
		dAvg[3][9] = 7.54;
		dAvg[3][10] = 7.5;
		dAvg[3][11] = 7.44;
	}
	
	public void updateResultBehaviourCPR() throws SQLException, Exception 
	{
		String sMsg;
		String sSQL;
		int loop = 0;
		ResultSet rsBehv = null;
		PreparedStatement ps = null;
		
		for(int s=0;s<iSurvey.length;s++)
		{
			sMsg = "Survey = " + iSurvey[s];
			sSQL = "SELECT COUNT(DISTINCT AssignmentID) AS Total FROM tblAssignment WHERE RaterStatus <> 0 AND SurveyID = "+ iSurvey[s];
			db.openDB();
			ResultSet rs1 = db.getRecord(sSQL);
			
			if(rs1.next())
			{
				int iTotal = rs1.getInt("Total");
				iRater = new int [iTotal];
			}
			
			sSQL = "SELECT DISTINCT AssignmentID FROM tblAssignment WHERE RaterStatus <> 0 AND SurveyID = " + iSurvey[s];
			rs1 = db.getRecord(sSQL);
			
			loop = 0;
			while(rs1.next())
			{
				iRater[loop] = rs1.getInt("AssignmentID");
				loop++;
			}
						
			for(int c=0;c<iComp.length;c++)
			{
				sMsg = sMsg + ", Comp = " + iComp[c];
				for(int r=0;r<iRater.length;r++)
				{
					sSQL = "SELECT COUNT(KeyBehaviourID) AS Total FROM tblSurveyBehaviour WHERE CompetencyID = " + iComp[c] +
							" AND SurveyID = " + iSurvey[s];
					rsBehv = db.getRecord(sSQL);
					
					if(rsBehv.next())
					{
						int iTot = rsBehv.getInt("Total");
						iBehv = new int[iTot];
					}
					
					loop = 0;
					rsBehv = getKB(iComp[c], iSurvey[s]);
					while(rsBehv.next())
					{
						iBehv[loop] = rsBehv.getInt("KeyBehaviourID");
						loop++;
					}
					
					for(int b=0;b<iBehv.length;b++)
					{
						sSQL = "UPDATE tblResultBehaviour SET Result = "+dAvg[s][c]+" WHERE AssignmentID = " + iRater[r] +
								" AND KeyBehaviourID = "+iBehv[b]+" AND RatingTaskID = 2 ";
						
						db.openDB();
						ps = db.con.prepareStatement(sSQL);
						ps.executeUpdate();
						db.closeDB();
					}
				}
				System.out.println(sMsg);
				sMsg = "Survey = " + iSurvey[s];
			}
		}
	}
	
	public void updateTrimMeanCPR() throws SQLException, Exception
	{
		String sMsg;
		String sSQL;
		int loop = 0;
		PreparedStatement ps = null;
		
		for(int s=0;s<iSurvey.length;s++)
		{
			sMsg = "Survey = " + iSurvey[s];
			sSQL = "SELECT COUNT(DISTINCT TargetLoginID) AS Total FROM tblAssignment WHERE RaterStatus <> 0 AND SurveyID = "+ iSurvey[s];
			db.openDB();
			ResultSet rs1 = db.getRecord(sSQL);
			
			if(rs1.next())
			{
				int iTotal = rs1.getInt("Total");
				iRater = new int [iTotal];
			}
			
			sSQL = "SELECT DISTINCT TargetLoginID FROM tblAssignment WHERE RaterStatus <> 0 AND SurveyID = " + iSurvey[s];
			rs1 = db.getRecord(sSQL);
			
			loop = 0;
			while(rs1.next())
			{
				iRater[loop] = rs1.getInt("TargetLoginID");
				loop++;
			}
			
			for(int c=0;c<iComp.length;c++)
			{
				sMsg = sMsg + ", Comp = " + iComp[c];
				
				for(int r=0;r<iRater.length;r++)
				{
					if(checkExistTrimMean(iSurvey[s], iComp[c], iRater[r]))
					{
						sSQL = "UPDATE tblTrimmedMean SET TrimmedMean = "+dAvg[s][c]+" WHERE SurveyID = " + iSurvey[s] +
							" AND CompetencyID = "+iComp[c]+" AND TargetLoginID = "+iRater[r]+" AND RatingTaskID = 2 ";
				
						db.openDB();
						ps = db.con.prepareStatement(sSQL);
						ps.executeUpdate();
						db.closeDB();
					}
					else
					{
						sSQL = "INSERT INTO tblTrimmedMean VALUES (" +
								iSurvey[s] + ", " + iRater[r] + ", " + "2, " + iComp[c] + ", " + dAvg[s][c] + ", 1)";
				
						db.openDB();
						ps = db.con.prepareStatement(sSQL);
						ps.executeUpdate();
						db.closeDB();
					}
					
				}
				System.out.println(sMsg);
				sMsg = "Survey = " + iSurvey[s];
			}
		}
	}
	
	public void updateAvgMeanCPR() throws SQLException, Exception 
	{
		String sMsg;
		String sSQL;
		int loop = 0;		
		ResultSet rsBehv = null;
		PreparedStatement ps = null;
		
		for(int s=0;s<iSurvey.length;s++)
		{
			sMsg = "Survey = " + iSurvey[s];
			sSQL = "SELECT COUNT(DISTINCT RaterLoginID) AS Total FROM tblAssignment WHERE RaterStatus <> 0 AND SurveyID = "+ iSurvey[s];
			db.openDB();
			ResultSet rs1 = db.getRecord(sSQL);
			
			if(rs1.next())
			{
				int iTotal = rs1.getInt("Total");
				iRater = new int [iTotal];
				//sRaterCode = new String [iTotal];
			}
			
			sSQL = "SELECT DISTINCT RaterLoginID FROM tblAssignment WHERE RaterStatus <> 0 AND SurveyID = " + iSurvey[s];
			rs1 = db.getRecord(sSQL);
			
			loop = 0;
			while(rs1.next())
			{
				iRater[loop] = rs1.getInt("RaterLoginID");
				//sRaterCode[loop] = rs1.getString("RaterCode").substring(0,2);
				loop++;
			}
			
			for(int c=0;c<iComp.length;c++)
			{
				sMsg = sMsg + ", Comp = " + iComp[c];
				for(int r=0;r<iRater.length;r++)
				{
					if(checkExist(iSurvey[s], iComp[c], iRater[r]))
					{
						// Exist, UPDATE
						sSQL = "UPDATE tblAvgMean SET AvgMean = "+dAvg[s][c]+" WHERE SurveyID = " + iSurvey[s] +
								" AND CompetencyID = "+iComp[c]+" AND TargetLoginID = "+iRater[r]+" AND RatingTaskID = 2 ";
						
						db.openDB();
						ps = db.con.prepareStatement(sSQL);
						ps.executeUpdate();
						db.closeDB();
					}
					else
					{
						sSQL = "SELECT COUNT(KeyBehaviourID) AS Total FROM tblSurveyBehaviour WHERE CompetencyID = " + iComp[c] +
								" AND SurveyID = " + iSurvey[s];
						rsBehv = db.getRecord(sSQL);
						
						if(rsBehv.next())
						{
							int iTot = rsBehv.getInt("Total");
							iBehv = new int[iTot];
						}
						
						loop = 0;
						rsBehv = getKB(iComp[c], iSurvey[s]);
						while(rsBehv.next())
						{
							iBehv[loop] = rsBehv.getInt("KeyBehaviourID");
							loop++;
						}
						
						for(int b=0;b<iBehv.length;b++)
						{
							db.openDB();
							
							// Not Exist, INSERT
							sSQL = "INSERT INTO tblAvgMean VALUES (" +
								iSurvey[s] + "," + iRater[r] + "," + "2," + iComp[c] + "," + iBehv[b] + "," + dAvg[s][c] + ",1)";
							ps = db.con.prepareStatement(sSQL);
							ps.executeUpdate();
							
							sSQL = "INSERT INTO tblAvgMean VALUES (" +
								iSurvey[s] + "," + iRater[r] + "," + "2," + iComp[c] + "," + iBehv[b] + "," + dAvg[s][c] + ",2)";
							ps = db.con.prepareStatement(sSQL);
							ps.executeUpdate();
							
							sSQL = "INSERT INTO tblAvgMean VALUES (" +
								iSurvey[s] + "," + iRater[r] + "," + "2," + iComp[c] + "," + iBehv[b] + "," + dAvg[s][c] + ",3)";
							ps = db.con.prepareStatement(sSQL);
							ps.executeUpdate();
							
							sSQL = "INSERT INTO tblAvgMean VALUES (" +
								iSurvey[s] + "," + iRater[r] + "," + "2," + iComp[c] + "," + iBehv[b] + "," + dAvg[s][c] + ",4)";
							ps = db.con.prepareStatement(sSQL);
							ps.executeUpdate();
						
							db.closeDB();
						}
					}
				}
				System.out.println(sMsg);
				sMsg = "Survey = " + iSurvey[s];
			}
		}
	}
	
	public void updateAvgMeanByRaterCPR() throws SQLException, Exception
	{
		String sMsg;
		String sSQL;
		int loop = 0;
		PreparedStatement ps = null;
		
		for(int s=0;s<iSurvey.length;s++)
		{
			sMsg = "Survey = " + iSurvey[s];
			sSQL = "SELECT COUNT(DISTINCT AssignmentID) AS Total FROM tblAssignment WHERE RaterStatus <> 0 AND SurveyID = "+ iSurvey[s];
			db.openDB();
			ResultSet rs1 = db.getRecord(sSQL);
			
			if(rs1.next())
			{
				int iTotal = rs1.getInt("Total");
				iRater = new int [iTotal];
			}
			
			sSQL = "SELECT DISTINCT AssignmentID FROM tblAssignment WHERE RaterStatus <> 0 AND SurveyID = " + iSurvey[s];
			rs1 = db.getRecord(sSQL);
			
			loop = 0;
			while(rs1.next())
			{
				iRater[loop] = rs1.getInt("AssignmentID");
				loop++;
			}
			
			for(int c=0;c<iComp.length;c++)
			{
				sMsg = sMsg + ", Comp = " + iComp[c];
				for(int r=0;r<iRater.length;r++)
				{
					if(checkExistAvgMeanByRater(iSurvey[s], iComp[c], iRater[r]))
					{
						// Exist, UPDATE
						sSQL = "UPDATE tblAvgMeanByRater SET AvgMean = "+dAvg[s][c]+" WHERE SurveyID = " + iSurvey[s] +
								" AND CompetencyID = "+iComp[c]+" AND AssignmentID = "+iRater[r]+" AND RatingTaskID = 2 ";
						
						db.openDB();
						ps = db.con.prepareStatement(sSQL);
						ps.executeUpdate();
						db.closeDB();
					}
					else
					{	
						db.openDB();
						
						// Not Exist, INSERT
						sSQL = "INSERT INTO tblAvgMeanByRater VALUES (" +
							iSurvey[s] + ", 2, " + iComp[c] + ", " + iRater[r] + ", " + dAvg[s][c] + ")";
						ps = db.con.prepareStatement(sSQL);
						ps.executeUpdate();	
					}
				}
				System.out.println(sMsg);
				sMsg = "Survey = " + iSurvey[s];
			}
		}
	}
	
	private boolean checkExistResultBehv(int iSurvey, int iComp, int iTarget) throws SQLException, Exception
	{
		boolean b = false;
		
		String sSQL = "SELECT AvgMean FROM tblAvgMean "+
		"WHERE SurveyID = " + iSurvey + "AND CompetencyID = "+iComp+" AND TargetLoginID = "+iTarget+" AND RatingTaskID = 2";
		
		ResultSet rs = db.getRecord(sSQL);
		
		if(rs.next() && rs != null)
			b = true;
		
		return b;
	}
	
	private boolean checkExist(int iSurvey, int iComp, int iTarget) throws SQLException, Exception
	{
		boolean b = false;
		
		String sSQL = "SELECT AvgMean FROM tblAvgMean "+
		"WHERE SurveyID = " + iSurvey + "AND CompetencyID = "+iComp+" AND TargetLoginID = "+iTarget+" AND RatingTaskID = 2";
		
		ResultSet rs = db.getRecord(sSQL);
		
		if(rs.next() && rs != null)
			b = true;
		
		return b;
	}
	
	private boolean checkExistTrimMean(int iSurvey, int iComp, int iTarget) throws SQLException, Exception
	{
		boolean b = false;
		
		String sSQL = "SELECT TrimmedMean FROM tblTrimmedMean "+
		"WHERE SurveyID = " + iSurvey + "AND CompetencyID = "+iComp+" AND TargetLoginID = "+iTarget+" AND RatingTaskID = 2";
		
		ResultSet rs = db.getRecord(sSQL);
		
		if(rs.next() && rs != null)
			b = true;
		
		return b;
	}
	
	private boolean checkExistAvgMeanByRater(int iSurvey, int iComp, int iAsg) throws SQLException, Exception
	{
		boolean b = false;
		
		String sSQL = "SELECT AvgMean FROM tblAvgMeanByRater "+
		"WHERE SurveyID = " + iSurvey + "AND CompetencyID = "+iComp+" AND AssignmentID = "+iAsg+" AND RatingTaskID = 2";
		
		ResultSet rs = db.getRecord(sSQL);
		
		if(rs.next() && rs != null)
			b = true;
		
		return b;
	}
	
	private ResultSet getKB(int iComp, int iSurvey) throws SQLException, Exception
	{
		ResultSet rs = null;
		
		String sSQL = "SELECT KeyBehaviourID FROM tblSurveyBehaviour WHERE CompetencyID = " + iComp + "AND SurveyID = " + iSurvey;
		rs = db.getRecord(sSQL);
		
		return rs;
	}
	
	public static void main(String args []) throws SQLException, Exception
	{
		FixCPR FC = new FixCPR();
		
		//FC.updateResultBehaviourCPR();
		//FC.updateTrimMeanCPR();
		//FC.updateAvgMeanCPR();
		FC.updateAvgMeanByRaterCPR();
	}*/
}