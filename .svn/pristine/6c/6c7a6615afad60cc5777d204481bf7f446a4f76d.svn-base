package CP_Classes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;

import util.Utils;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voDevelopmentPlan;
import CP_Classes.vo.voKeyBehaviour;
import CP_Classes.vo.votblSurvey;

/**
 * This class implements all the method related to development plan
 * 
 * @author yuni
 *
 */
public class DevelopmentPlan {
	
	private static String DEVELOPMENTACTIVITY = "Development Activities";
	private static String BOOK = "Books";
	private static String WEBRESOURCE = "Web Resources";
	private static String TRAININGCOURSE = "Training Courses";
	private static String AVRESOURCE = "AV Resources"; // Change Resource category from "In-house Resource" to "AV Resource", Desmond 10 May 2011
	
	private int iFKComp = 0;
	private int iOption = 0;
	private int iFKSurvey = 0;
	private int iPKDevelopmentPlan = 0;
	private Setting ST;
	private Create_Edit_Survey CE_Survey;
	
	public DevelopmentPlan() {
		ST = new Setting();
		CE_Survey = new Create_Edit_Survey();
	}
	
	/**
	 * Retrieve the list of competency that falls under the gap for a particular target in the survey
	 * @param iSurveyID
	 * @param TargetID
	 * @return Vector of voCompetency object
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception
	 */
	public Vector getDevelopmentCompetency(int iSurveyID, int TargetID) throws SQLException, IOException, Exception
	{
		Vector v = new Vector();
		
		int levelOfSurvey=0;
		double MinGap =0;
		
		int arr[] = new int[1];
		
		if(iSurveyID == 0) {
			Vector vSurvey = getSurveys(TargetID);
			arr = new int[vSurvey.size()];
			for(int i=0; i<vSurvey.size(); i++) {
				votblSurvey vo = (votblSurvey)vSurvey.elementAt(i);
			
				int pkSurvey = vo.getSurveyID();
				arr[i] = pkSurvey;
				
			}
		} else {
			arr[0] = iSurveyID;
			
		}
		
		for(int k=0; k<arr.length; k++) {
			votblSurvey voSurvey = getSurveyDetail(arr[k]);
			
			levelOfSurvey = voSurvey.getLevelOfSurvey();
			MinGap = voSurvey.getMIN_Gap();
			
			String Sql ="";
			
			if(levelOfSurvey == 1)
			{
				/*
				Sql = "SELECT * FROM tblGap INNER JOIN Competency ON tblGap.CompetencyID = Competency.PKCompetency"; 
				Sql = Sql+" INNER JOIN [User] ON tblGap.TargetLoginID = [User].PKUser INNER JOIN ";
				Sql = Sql+"KeyBehaviour ON tblGap.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour AND Competency.PKCompetency = KeyBehaviour.FKCompetency";
				Sql = Sql+" WHERE (tblGap.SurveyID = "+SurveyID+") AND (tblGap.TargetLoginID = "+TargetID+") AND Gap <= "+MinGap;
				*/
				Sql = "SELECT CompetencyID AS PKCompetency, CompetencyName, CompetencyDefinition, Gap FROM "+
						"(SELECT tblGap.CompetencyID, AVG(tblGap.Gap) AS Gap, Competency.CompetencyName, Competency.CompetencyDefinition "+
						"FROM tblGap INNER JOIN Competency ON tblGap.CompetencyID = Competency.PKCompetency "+
						"WHERE (tblGap.SurveyID = "+arr[k]+") AND (tblGap.TargetLoginID = "+TargetID+") "+
						"GROUP BY tblGap.CompetencyID, Competency.CompetencyName, Competency.CompetencyDefinition) DERIVEDTBL "+
					  "WHERE (Gap <= "+MinGap+")";
			}
			else if(levelOfSurvey == 0)
			{
				Sql = "SELECT * FROM tblGap INNER JOIN Competency ON tblGap.CompetencyID = Competency.PKCompetency"; 
				Sql = Sql+" INNER JOIN [User] ON tblGap.TargetLoginID = [User].PKUser";
				Sql = Sql+" WHERE (tblGap.SurveyID = "+arr[k]+") AND (tblGap.TargetLoginID = "+TargetID+") AND Gap <= "+MinGap;
			}
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

	        try
	        {          
	        	con=ConnectionBean.getConnection();
	        	st=con.createStatement();
	        	rs=st.executeQuery(Sql);
				
				while(rs.next())	
				{	
					voCompetency vo = new voCompetency();
					int iFKComp = rs.getInt("PKCompetency");
					String CompetencyName = rs.getString("CompetencyName");
					vo.setCompetencyID(iFKComp);
					vo.setCompetencyName(CompetencyName);
					v.add(vo);
				}
				
	        }
	        catch(Exception E) 
	        {
	            System.err.println("DevelopmentPlan.java - getDevelopmentCompetency - " + E);
	        }
	        finally
	        {
		        ConnectionBean.closeRset(rs); //Close ResultSet
		        ConnectionBean.closeStmt(st); //Close statement
		        ConnectionBean.close(con); //Close connection
	        }
		}
		return v;

	}

	public void setFKComp(int iFKComp) {
		this.iFKComp = iFKComp;
	}

	public int getFKComp() {
		return iFKComp;
	}
	
	/**
	 * Retrieve the Development Plan
	 * @param iFKAssignment
	 * @param iFKCompetency
	 * @param iOption
	 * @return
	 */
	public Vector getDevelopmentPlan(int iFKAssignment, int iFKCompetency, int iOption) {
		Vector v = new Vector();	
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM tblDevelopmentPlan INNER JOIN Competency ON Competency.PKCompetency = tblDevelopmentPlan.FKCompetency ";
			
			if(iOption == 1) {
				sql += " INNER JOIN tblDRA ON tblDRA.DRAID = tblDevelopmentPlan.DRAID ";
			} else if (iOption > 1) {
				sql += " INNER JOIN tblDRARes ON tblDRARes.ResID = tblDevelopmentPlan.ResID ";
			}
			
			sql += " WHERE TargetLoginID = "+iFKAssignment;
			
			
			if(iFKCompetency != 0)
				sql += " AND FKCompetency = "+iFKCompetency;
				
			if(iOption != 0) {
				
				switch(iOption) {
				case 1:
					sql += " AND tblDevelopmentPlan.ResID = 0" ;
					break;
				case 2:
					sql += " AND ResType = " + (iOption-1) ;
					break;
				case 3:
					sql += " AND ResType = " + (iOption-1) ;
					break;
				case 4:
					sql += " AND ResType = " + (iOption-1) ;
					break;
				case 5:
					sql += " AND ResType = " + (iOption-1) ;
					break;
				default:
					break;
				}
			}

      
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
        	
            while(rs.next())
            {
				voDevelopmentPlan vo = new voDevelopmentPlan();
				vo.setPKDevPlan(rs.getInt("PKDevelopmentPlan"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				
				int DRAID = rs.getInt("DRAID");
				int ResID = rs.getInt("ResID");
				vo.setDRAID(DRAID);
				vo.setResID(ResID);
	
				if(iOption != 0) {
					if(ResID == 0) {
						vo.setResource(rs.getString("DRAStatement"));
						vo.setProcess(getReviewProcess(0));
					} else {
						vo.setResource(rs.getString("Resource"));
						vo.setProcess(getReviewProcess(iOption-1));
					}
				} else {
					if(ResID == 0) {
						vo.setResource(getDevelopmentActivity(DRAID));
						vo.setProcess(getReviewProcess(0));
					} else {
						String ArrDev[] = getDevelopmentResource(ResID);
						vo.setResource(ArrDev[0]);
						vo.setProcess(getReviewProcess(Integer.parseInt(ArrDev[1])));
					}
				}
					
				vo.setProposedDate(Utils.convertDateFormat(rs.getDate("ProposedDate")));
				
				String sCompletionDate = Utils.convertDateFormat(rs.getDate("CompletionDate"));
				
				if(sCompletionDate.equals("01 Jan 1900"))
					vo.setCompletionDate("");
				else
					vo.setCompletionDate(sCompletionDate);
				
				v.add(vo);
            }
		}

        catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getDevelopmentPlan - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return v;
	}
	
	//Added by Roger 20 June 2008
	//include surveyid parameter
	/**
	 * Retrieve the Development Plan
	 * @param iFKAssignment
	 * @param iFKCompetency
	 * @param iOption
	 * @return
	 */
	public Vector getDevelopmentPlanBySurveyId(int iFKAssignment, int iFKCompetency, int iOption, int surveyId) {
		Vector v = new Vector();	
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM tblDevelopmentPlan INNER JOIN Competency ON Competency.PKCompetency = tblDevelopmentPlan.FKCompetency ";
			
			if(iOption == 1) {
				sql += " INNER JOIN tblDRA ON tblDRA.DRAID = tblDevelopmentPlan.DRAID ";
			} else if (iOption > 1) {
				sql += " INNER JOIN tblDRARes ON tblDRARes.ResID = tblDevelopmentPlan.ResID ";
			}
			
			sql += " WHERE TargetLoginID = "+iFKAssignment;
			sql += " AND FKSurveyID="+ surveyId;
			
			
			if(iFKCompetency != 0)
				sql += " AND FKCompetency = "+iFKCompetency;
				
			if(iOption != 0) {
				
				switch(iOption) {
				case 1:
					sql += " AND tblDevelopmentPlan.ResID = 0" ;
					break;
				case 2:
					sql += " AND ResType = " + (iOption-1) ;
					break;
				case 3:
					sql += " AND ResType = " + (iOption-1) ;
					break;
				case 4:
					sql += " AND ResType = " + (iOption-1) ;
					break;
				case 5:
					sql += " AND ResType = " + (iOption-1) ;
					break;
				default:
					break;
				}
			}

      
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
        	
            while(rs.next())
            {
				voDevelopmentPlan vo = new voDevelopmentPlan();
				vo.setPKDevPlan(rs.getInt("PKDevelopmentPlan"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				
				int DRAID = rs.getInt("DRAID");
				int ResID = rs.getInt("ResID");
				vo.setDRAID(DRAID);
				vo.setResID(ResID);
	
				if(iOption != 0) {
					if(ResID == 0) {
						vo.setResource(rs.getString("DRAStatement"));
						vo.setProcess(getReviewProcess(0));
					} else {
						vo.setResource(rs.getString("Resource"));
						vo.setProcess(getReviewProcess(iOption-1));
					}
				} else {
					if(ResID == 0) {
						vo.setResource(getDevelopmentActivity(DRAID));
						vo.setProcess(getReviewProcess(0));
					} else {
						String ArrDev[] = getDevelopmentResource(ResID);
						vo.setResource(ArrDev[0]);
						vo.setProcess(getReviewProcess(Integer.parseInt(ArrDev[1])));
					}
				}
					
				vo.setProposedDate(Utils.convertDateFormat(rs.getDate("ProposedDate")));
				
				String sCompletionDate = Utils.convertDateFormat(rs.getDate("CompletionDate"));
				
				if(sCompletionDate.equals("01 Jan 1900"))
					vo.setCompletionDate("");
				else
					vo.setCompletionDate(sCompletionDate);
				
				v.add(vo);
            }
		}

        catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getDevelopmentPlan - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return v;
	}
	
	/**
	 * Retrieve the Development Plan details for a particular Development Plan ID
	 * @param iFKDevelopmentPlan
	 * @return voDevelopmentPlan object
	 */
	public voDevelopmentPlan getDevelopmentPlan(int iFKDevelopmentPlan) {
		voDevelopmentPlan vo = new voDevelopmentPlan();	

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	
			String sql = "SELECT * FROM tblDevelopmentPlan INNER JOIN Competency ON Competency.PKCompetency = tblDevelopmentPlan.FKCompetency ";
			
			sql += " WHERE PKDevelopmentPlan = "+iFKDevelopmentPlan;
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
			
			while(rs.next())	
			{	
				
				vo.setPKDevPlan(rs.getInt("PKDevelopmentPlan"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				
				int DRAID = rs.getInt("DRAID");
				int ResID = rs.getInt("ResID");
				vo.setDRAID(DRAID);
				vo.setResID(ResID);

				
				if(ResID == 0) {
					vo.setResource(getDevelopmentActivity(DRAID));
					vo.setProcess(getReviewProcess(0));
				} else {
					String ArrDev[] = getDevelopmentResource(ResID);
					vo.setResource(ArrDev[0]);
					vo.setProcess(getReviewProcess(Integer.parseInt(ArrDev[1])));
				}
			
				vo.setProposedDate(Utils.convertDateFormat(rs.getDate("ProposedDate")));
				
				String sCompletionDate = Utils.convertDateFormat(rs.getDate("CompletionDate"));
				
				if(sCompletionDate.equals("01 Jan 1900"))
					vo.setCompletionDate("");
				else
					vo.setCompletionDate(sCompletionDate);
				
			}
        }
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getDevelopmentPlan - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return vo;
	}
	
	/**
	 * Retrieve the Development Plan for particular target and Competency ID
	 * 
	 * @param iTargetLoginID
	 * @param iFKCompetency
	 * @return Vector of voDevelopmentPlan objects
	 */
	public Vector getDevelopmentPlan(int iTargetLoginID, int iFKCompetency) {
		Vector v = new Vector();	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		
		try {
			String sql = "SELECT * FROM tblDevelopmentPlan WHERE TargetLoginID = "+iTargetLoginID;
			
			
			if(iFKCompetency != 0)
				sql += " AND FKCompetency = "+iFKCompetency;
			
			sql += " ORDER BY ProposedDate";
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
        	
			while(rs.next())	
			{	
				voDevelopmentPlan vo = new voDevelopmentPlan();
				vo.setPKDevPlan(rs.getInt("PKDevelopmentPlan"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				
				int DRAID = rs.getInt("DRAID");
				int ResID = rs.getInt("ResID");
				vo.setDRAID(DRAID);
				vo.setResID(ResID);

				
				if(ResID == 0) {
					vo.setResource(getDevelopmentActivity(DRAID));
					vo.setProcess(getReviewProcess(0));
					vo.setType(getResourceType(0));
					
				} else {
					String ArrDev[] = getDevelopmentResource(ResID);
					vo.setResource(ArrDev[0]);
					vo.setProcess(getReviewProcess(Integer.parseInt(ArrDev[1])));
					vo.setType(getResourceType(Integer.parseInt(ArrDev[1])));
				}
			
				vo.setProposedDate(Utils.convertDateFormat(rs.getDate("ProposedDate")));
				
				String sCompletionDate = Utils.convertDateFormat(rs.getDate("CompletionDate"));
				
				if(sCompletionDate.equals("01 Jan 1900"))
					vo.setCompletionDate("");
				else
					vo.setCompletionDate(sCompletionDate);
				
				v.add(vo);
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getDevelopmentPlan - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return v;
	}
	
	
	//Added by Roger 20 June 2008
	//add surveyid parameter
	/**
	 * Retrieve the Development Plan for particular target and Competency ID
	 * 
	 * @param iTargetLoginID
	 * @param iFKCompetency
	 * @return Vector of voDevelopmentPlan objects
	 */
	public Vector getDevelopmentPlanBySurveyId(int iTargetLoginID, int iFKCompetency, int surveyId) {
		Vector v = new Vector();	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		
		try {
			String sql = "SELECT * FROM tblDevelopmentPlan WHERE TargetLoginID = "+iTargetLoginID;
			
			
			if(iFKCompetency != 0)
				sql += " AND FKCompetency = "+iFKCompetency;
			
			sql += " AND FKSurveyID="+surveyId;
			
			sql += " ORDER BY ProposedDate";
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
        	
			while(rs.next())	
			{	
				voDevelopmentPlan vo = new voDevelopmentPlan();
				vo.setPKDevPlan(rs.getInt("PKDevelopmentPlan"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				
				int DRAID = rs.getInt("DRAID");
				int ResID = rs.getInt("ResID");
				vo.setDRAID(DRAID);
				vo.setResID(ResID);

				
				if(ResID == 0) {
					vo.setResource(getDevelopmentActivity(DRAID));
					vo.setProcess(getReviewProcess(0));
					vo.setType(getResourceType(0));
					
				} else {
					String ArrDev[] = getDevelopmentResource(ResID);
					vo.setResource(ArrDev[0]);
					vo.setProcess(getReviewProcess(Integer.parseInt(ArrDev[1])));
					vo.setType(getResourceType(Integer.parseInt(ArrDev[1])));
				}
			
				vo.setProposedDate(Utils.convertDateFormat(rs.getDate("ProposedDate")));
				
				String sCompletionDate = Utils.convertDateFormat(rs.getDate("CompletionDate"));
				
				if(sCompletionDate.equals("01 Jan 1900"))
					vo.setCompletionDate("");
				else
					vo.setCompletionDate(sCompletionDate);
				
				v.add(vo);
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getDevelopmentPlan - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return v;
	}
	
	
	/**
	 * Retrieve the review process
	 * 
	 * @param iResType
	 * @return the review process
	 */
	public String getReviewProcess(int iResType) {
		int iType = 0;
		
		if(iResType == 0)
			iType = 0;
		else {
			iType = 1;
		}
		
		String sProcess = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		
		try {
			String sql = "SELECT * FROM tblDevReviewProcess WHERE ResType = "+iResType+" AND Type = "+iType;
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
			
			if(rs.next())	
			{	
				sProcess = rs.getString("ReviewProcess");
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getReviewProcess - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return sProcess;
	}
	
	/**
	 * Retrieve the Development Resource and the type based on the resource ID
	 * 
	 * @param iResID
	 * @return 
	 */
	public String[] getDevelopmentResource(int iResID) {
		
		String arr [] = new String [2];
		String sResource = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM tblDRARes WHERE ResID = " + iResID;
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
			
			if(rs.next())	
			{	
				sResource = rs.getString("Resource");
				
				arr[0] = sResource;
				arr[1] = rs.getString("ResType");
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getDevelopmentResource - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return arr;
	}
	
	/**
	 * Retrieve the development Activity based on the activity ID
	 * @param iDRAID
	 * @return development activity
	 */
	public String getDevelopmentActivity(int iDRAID) {
		
		String sActivity = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM tblDRA WHERE DRAID = " + iDRAID;
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
			
			if(rs.next())	
			{	
				sActivity = rs.getString("DRAStatement");
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getDevelopmentActivity - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return sActivity;
	}

	public void setOption(int iOption) {
		this.iOption = iOption;
	}

	public int getOption() {
		return iOption;
	}

	/**
	 * Retrieve all the surveys based on Rater ID
	 * @param raterID
	 * @return Vector of votblSurvey objects
	 * @throws SQLException
	 * @throws Exception
	 */
	public Vector getSurveys(int raterID) throws SQLException, Exception {		
		
		Vector v = new Vector();

		String query = "SELECT DISTINCT tblSurvey.SurveyID, tblSurvey.SurveyName ";
		query = query + "FROM tblAssignment INNER JOIN tblSurvey ON ";
		query = query + "tblAssignment.SurveyID = tblSurvey.SurveyID ";
		query = query + "WHERE tblAssignment.RaterLoginID = " + raterID;
		query = query + " AND tblAssignment.RaterStatus <> 0 ";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try 
        {          
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
			
			while(rs.next())	
    	    {
				votblSurvey vo = new votblSurvey();
				int iSurveyID = rs.getInt(1);
				vo.setSurveyID(iSurveyID);
    			String sSurvey = rs.getString(2);
    			vo.setSurveyName(sSurvey);
    	  		v.add(vo);
    	  	}
            
        }
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getSurveys - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }

	  	return v;
	}
	
	//Add by Roger 1 July 2008
	//Use by DevelopmentPlan.jsp to display the survey drop down list
	/**
	 * Retrieve all the surveys based on Target ID and those with dev competency
	 * @param raterID
	 * @return Vector of votblSurvey objects
	 * @throws SQLException
	 * @throws Exception
	 */
	public Vector getSurveysByTargetDevCompetency(int raterID) throws SQLException, Exception {		
		
		Vector v = new Vector();

		String query = "SELECT DISTINCT tblSurvey.SurveyID, tblSurvey.SurveyName ";
		query = query + "FROM tblAssignment INNER JOIN tblSurvey ON ";
		query = query + "tblAssignment.SurveyID = tblSurvey.SurveyID ";
		query = query + "WHERE tblAssignment.TargetLoginID = " + raterID;
		query = query + " AND tblAssignment.RaterStatus <> 0 ";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try 
        {          
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
			
			while(rs.next())	
    	    {
				votblSurvey vo = new votblSurvey();
				int iSurveyID = rs.getInt(1);
				//Edited by Roger 1 July 2008
				//return those survey that has develpment competency
				Vector temp = this.getDevelopmentCompetency(iSurveyID, raterID);
				if (temp.size() > 0) {
					vo.setSurveyID(iSurveyID);
	    			String sSurvey = rs.getString(2);
	    			vo.setSurveyName(sSurvey);
	    	  		v.add(vo);
				}
    	  	}
            
        }
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getSurveys - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }

	  	return v;
	}

	public void setFKSurvey(int iFKSurvey) {
		this.iFKSurvey = iFKSurvey;
	}

	public int getFKSurvey() {
		return iFKSurvey;
	}
	
	/**
	 * Retrieve the survey Detail based on the Survey ID
	 * 
	 * @param SurveyID
	 * @return votblSurvey object
	 * @throws SQLException
	 * @throws Exception
	 */
	public votblSurvey getSurveyDetail(int SurveyID) throws SQLException, Exception 
	{
		votblSurvey vo = new votblSurvey();
		
		String sql = "SELECT * FROM tblSurvey a, tblJobPosition b, tblOrganization c, tblConsultingCompany d WHERE c.FKCompanyID = d.CompanyID AND a.JobPositionID=b.JobPositionID AND a.FKOrganization= c.PKOrganization AND SurveyID = "+SurveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try 
        {          
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);

			if(rs.next())	
    	    {
				
				int levelOfSurvey = rs.getInt("LevelOfSurvey");
				float MinGap = rs.getFloat("Min_Gap");
				vo.setLevelOfSurvey(levelOfSurvey);
				vo.setMIN_Gap(MinGap);
    	  		
    	  	}
            
        }
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getSurveyDetail - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }

	  	return vo;
	
	}
	
	/**
	 * Insert a record into the tblDevelopmentPlan table
	 * @param iTargetLoginID
	 * @param iFKCompetency
	 * @param DRAID
	 * @param ResID
	 * @param ProposedDate
	 * @param CompletionDate
	 * @return true if added successfully
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean addRecord(int iTargetLoginID, int iFKCompetency, int DRAID, int ResID, String ProposedDate, String CompletionDate) throws SQLException, Exception 
	{
		//Edited by Roger 1 July 2008
		//Save null if completion date is empty instead of default date
		if (CompletionDate == null || "".equals(CompletionDate.trim())) {
			CompletionDate = "NULL";
		} else {
			CompletionDate = "'" + CompletionDate + "'";
		}
		
		String sql = "INSERT INTO tblDevelopmentPlan (TargetLoginID, FKCompetency, DRAID, ResID, ProposedDate, CompletionDate) VALUES ";
		sql += " ("+ iTargetLoginID+", "+iFKCompetency+", "+DRAID+", "+ResID+", '"+ProposedDate+"', "+CompletionDate+")";
		
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsAdded=true;

		}
		catch(Exception E)
		{
            System.err.println("DevelopmentPlan.java - addRecord - " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		return bIsAdded;
		
	}
	
	
	//Added by Roger 20 June 2008
	//include surveyid when adding dev plans
	/**
	 * Insert a record into the tblDevelopmentPlan table
	 * @param iTargetLoginID
	 * @param iFKCompetency
	 * @param DRAID
	 * @param ResID
	 * @param ProposedDate
	 * @param CompletionDate
	 * @return true if added successfully
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean addRecord(int iTargetLoginID, int iFKCompetency, int DRAID, int ResID, String ProposedDate, String CompletionDate, int surveyId) throws SQLException, Exception 
	{
		//Edited by Roger 1 July 2008
		//Save null if completion date is empty instead of default date
		if (CompletionDate == null || "".equals(CompletionDate.trim())) {
			CompletionDate = "NULL";
		} else {
			CompletionDate = "'" + CompletionDate + "'";
		}
		
		String sql = "INSERT INTO tblDevelopmentPlan (TargetLoginID, FKCompetency, DRAID, ResID, ProposedDate, CompletionDate, FKSurveyID) VALUES ";
		sql += " ("+ iTargetLoginID+", "+iFKCompetency+", "+DRAID+", "+ResID+", '"+ProposedDate+"', "+CompletionDate+", "+surveyId+")";
		
		Connection con = null;
		Statement st = null;


		boolean bIsAdded = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsAdded=true;

		}
		catch(Exception E)
		{
            System.err.println("DevelopmentPlan.java - addRecord - " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		return bIsAdded;
		
	}
	
	/**
	 * Remove the record from tblDevelopmentPlan
	 * 
	 * @param iPKDevelopmentPlan
	 * @return true if deleted successfully
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean deleteRecord(int iPKDevelopmentPlan) throws SQLException, Exception 
	{
		String sql = "DELETE FROM tblDevelopmentPlan WHERE PKDevelopmentPlan = " + iPKDevelopmentPlan;
		
		Connection con = null;
		Statement st = null;


		boolean bIsDeleted = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsDeleted=true;

		}
		catch(Exception E)
		{
            System.err.println("DevelopmentPlan.java - deleteRecord - " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		return bIsDeleted;
	}

	/**
	 * Update Development Plan Completion Date based on the DevelopmentPlan ID
	 * 
	 * @param iPKDevelopmentPlan
	 * @param sCompletionDate
	 * @return true if updated successfully
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean updateRecord(int iPKDevelopmentPlan, String sCompletionDate) throws SQLException, Exception 
	{
		String sql = "UPDATE tblDevelopmentPlan SET CompletionDate = '"+sCompletionDate+"' WHERE PKDevelopmentPlan = " + iPKDevelopmentPlan;
		
		Connection con = null;
		Statement st = null;


		boolean bIsUpdated = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsUpdated=true;

		}
		catch(Exception E)
		{
            System.err.println("DevelopmentPlan.java - updateRecord - " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		return bIsUpdated;
		
	}
	public void setPKDevelopmentPlan(int iPKDevelopmentPlan) {
		this.iPKDevelopmentPlan = iPKDevelopmentPlan;
	}

	public int getPKDevelopmentPlan() {
		return iPKDevelopmentPlan;
	}

	/**
	 * Retrieve the list of competencies based on the Survey ID and Target Login ID
	 * 
	 * @param iSurveyID
	 * @param iTargetLoginID
	 * @return voDevelopmentPlan object
	 */
	public Vector getCompetencyList(int iSurveyID, int iTargetLoginID) {
		
		Vector v = new Vector();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT DISTINCT FKCompetency, CompetencyName FROM tblDevelopmentPlan INNER JOIN Competency ON Competency.PKCompetency = tblDevelopmentPlan.FKCompetency ";
			
			sql += " WHERE TargetLoginID = "+iTargetLoginID + " AND FKCompetency IN (SELECT CompetencyID FROM tblSurveyCompetency WHERE SurveyID = "+iSurveyID+")";
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
			
			while(rs.next())	
			{
				voDevelopmentPlan vo = new voDevelopmentPlan();	
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				
				v.add(vo);
				
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getCompetencyList - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
        
		return v;
	}

	//Added by Roger 20 June 2008
	/**
	 * Retrieve the list of competencies based on the Survey ID and Target Login ID
	 * 
	 * @param iSurveyID
	 * @param iTargetLoginID
	 * @return voDevelopmentPlan object
	 */
	public Vector getCompetencyListByDevelopmentPlanSurveyId(int iSurveyID, int iTargetLoginID) {
		
		Vector v = new Vector();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT DISTINCT FKCompetency, CompetencyName FROM tblDevelopmentPlan INNER JOIN Competency ON Competency.PKCompetency = tblDevelopmentPlan.FKCompetency ";
			
			sql += " WHERE TargetLoginID = "+iTargetLoginID + " AND FKCompetency IN (SELECT CompetencyID FROM tblSurveyCompetency WHERE SurveyID = "+iSurveyID+")";
			sql += " AND FKSurveyID="+iSurveyID;
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
			
			while(rs.next())	
			{
				voDevelopmentPlan vo = new voDevelopmentPlan();	
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				
				v.add(vo);
				
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getCompetencyList - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
        
		return v;
	}
	
	/**
	 * Retrieve the list of key behaviours based on the Competency ID
	 * @param iFKCompetency
	 * @return Vector of voKeyBehaviour objects
	 */
	public Vector getKeyBehaviourList(int iFKCompetency) {
		Vector v = new Vector();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT KeyBehaviour FROM KeyBehaviour INNER JOIN Competency ON "+
						"KeyBehaviour.FKCompetency = Competency.PKCompetency WHERE PKCompetency = "+ iFKCompetency;
			
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
			
			while(rs.next())	
			{
				voKeyBehaviour vo = new voKeyBehaviour();
				vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
				v.add(vo);
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getKeyBehaviourList - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return v;
		
	}
	
	/**
	 * Retrieve the Resource type
	 * 
	 * @param type
	 * @return type
	 */
	public static String getResourceType(int type){
		
		switch(type) {
			case 0:
				return DEVELOPMENTACTIVITY;
			case 1:
				return BOOK;
			case 2:
				return WEBRESOURCE;
			case 3:
				return TRAININGCOURSE;
			case 4:
				return AVRESOURCE; // Change Resource category from "In-house Resource" to "AV Resource", Desmond 10 May 2011
		}
		
		return BOOK;
	}

	/**
	 * Retrieve the survey info based on the Survey ID and Target Login ID.
	 * 
	 * @param iSurveyID
	 * @param iTargetLoginID
	 * @return String array
	 * @throws SQLException
	 */
	public String[] getSurveyInfo(int iSurveyID, int iTargetLoginID) throws SQLException {
		String arr[] = new String[3];
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v = new Vector();
		String query = "SELECT DISTINCT tblJobPosition.JobPosition, tblSurvey.FKOrganization, ";
		query = query + "[User].GivenName, [User].FamilyName, tblOrganization.NameSequence, tblOrganization.OrganizationName ";
		query = query + "FROM tblSurvey INNER JOIN ";
		query = query + "tblAssignment ON tblSurvey.SurveyID = tblAssignment.SurveyID INNER JOIN ";
		query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser ";
		query = query + "INNER JOIN tblOrganization ON tblSurvey.FKOrganization = tblOrganization.PKOrganization ";
		query = query + "INNER JOIN tblJobPosition ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID ";
		query = query + " AND tblAssignment.TargetLoginID = " + iTargetLoginID;
		
		try {
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
	
			if(rs.next()) {
				String sName = rs.getString("FamilyName") +" " + rs.getString("GivenName");
				String sJobPosition = rs.getString("JobPosition");
				String sOrganisationName = rs.getString("OrganizationName");
				
				arr[0] = sName;
				arr[1] = sJobPosition;
				arr[2] = sOrganisationName;
			}
			
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getSurveyInfo - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
        
		return arr;
	}
	
	/**
	 * Retrieve the supervisor name
	 * @param iTargetLoginID
	 * @return supervisor name
	 */
	public String getSupervisorName(int iTargetLoginID) {
		
		String sSupervisorName = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int iUserID = 0;
		
		try {
			String sql = "SELECT User2 FROM [User] INNER JOIN tblUserRelation ON [User].PKUser = tblUserRelation.User1 WHERE " +
						"RelationType =1 AND User1 != User2 AND User1 = " + iTargetLoginID;
System.out.println(">> getSupervisorName >> sql = " + sql);
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
			
			if(rs.next())	
			{	
				iUserID = rs.getInt("User2");
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getSupervisorName - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		sSupervisorName = UserName(iUserID);
		
		return sSupervisorName;
	}
	
	/**
	 * Retrieve the Full Name of the target
	 * @param targetID
	 * @return full name
	 */
	public String UserName(int targetID) {
		String name = "";
		String query = "SELECT FamilyName, GivenName FROM [User] WHERE PKUser = " + targetID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
			
			if(rs.next()) {
				String familyName = rs.getString(1);
				String GivenName = rs.getString(2);
				
				name = familyName + " " + GivenName;
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - UserName - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return name;
	}
	
	/**
	 * Retrieve Scores based on the survey level and Reliability check and Rating Task Code
	 * @param iSurveyID
	 * @param iTargetID
	 * @param RTCode
	 * @return
	 * @throws SQLException
	 */
	public HashMap CPCPR(int iSurveyID, int iTargetID, String RTCode) throws SQLException 
	{
		HashMap PointMap = new HashMap();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String query = "";
		int surveyLevel = getLevelOfSurvey(iSurveyID);
		int reliabilityCheck = ReliabilityCheck(iSurveyID);
		
		if(reliabilityCheck == 0) 
		{			
			query = "SELECT tblTrimmedMean.CompetencyID, Competency.CompetencyName, tblTrimmedMean.TrimmedMean as Result ";
			query = query + "FROM tblTrimmedMean INNER JOIN tblRatingTask ON ";
			query = query + "tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query = query + "INNER JOIN Competency ON ";
			query = query + "tblTrimmedMean.CompetencyID = Competency.PKCompetency ";
			query = query + "WHERE tblTrimmedMean.SurveyID = " + iSurveyID + " AND ";
			query = query + "tblTrimmedMean.TargetLoginID = " + iTargetID + " AND tblTrimmedMean.Type = 1 AND ";
			query = query + "tblRatingTask.RatingCode = '" + RTCode + "' ";
			query = query + "ORDER BY Competency.CompetencyName";
		} 
		else 
		{
			if(surveyLevel == 0) 
			{
				query = "SELECT tblAvgMean.CompetencyID, Competency.CompetencyName, tblAvgMean.AvgMean as Result ";
				query = query + "FROM tblAvgMean INNER JOIN tblRatingTask ON ";
				query = query + "tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
				query = query + "INNER JOIN Competency ON ";
				query = query + "tblAvgMean.CompetencyID = Competency.PKCompetency ";
				query = query + "WHERE tblAvgMean.SurveyID = " + iSurveyID + " AND ";
				query = query + "tblAvgMean.TargetLoginID = " + iTargetID + " AND tblAvgMean.Type = 1 AND ";
				query = query + "tblRatingTask.RatingCode = '" + RTCode + "' ORDER BY Competency.CompetencyName";
			} 
			else 
			{
				query = "SELECT tblAvgMean.CompetencyID, Competency.CompetencyName, ";
				query = query + "CAST(AVG(tblAvgMean.AvgMean) AS numeric(38, 2)) AS Result ";
				query = query + "FROM tblRatingTask INNER JOIN tblAvgMean ON ";
				query = query + "tblRatingTask.RatingTaskID = tblAvgMean.RatingTaskID ";
				query = query + "INNER JOIN Competency ON ";
				query = query + "tblAvgMean.CompetencyID = Competency.PKCompetency ";
				query = query + "WHERE tblAvgMean.SurveyID = " + iSurveyID + " AND ";
				query = query + "tblAvgMean.TargetLoginID = " + iTargetID + " AND tblAvgMean.Type = 1 AND ";
				query = query + "tblRatingTask.RatingCode = '" + RTCode + "' GROUP BY tblAvgMean.CompetencyID, ";
				query = query + "Competency.CompetencyName order by Competency.CompetencyName";
			}
		}


		try {
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
	
			while(rs.next()) {
				int iCompetencyID = rs.getInt("CompetencyID");
				double dAvg = rs.getDouble("Result");
				PointMap.put(new Integer(iCompetencyID), new Double(dAvg));
				
			}
			
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - CPCPR - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return PointMap;
	}	
	
	/**
	 * Retrieve the reliability check 
	 * @param surveyID
	 * @return reliability check
	 */
	public int ReliabilityCheck(int surveyID)
	{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		int check = 0;
		String query = "select ReliabilityCheck from tblSurvey where SurveyID = " + surveyID;

		try {
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
			
			if(rs.next()) {
				check = rs.getInt(1);
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - RealibilityCheck - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return check;
	}
	
	/**
	 * Retrieve the level of Survey
	 * 
	 * @param surveyID
	 * @return the level of survey
	 */
	public int getLevelOfSurvey(int surveyID)
	{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		int check = 0;
		String query = "select LevelOfSurvey from tblSurvey where SurveyID = " + surveyID;

		try {
			con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
			
			if(rs.next()) {
				check = rs.getInt(1);
			}
		}
		catch(Exception E) 
        {
            System.err.println("DevelopmentPlan.java - getLevelOfSurvey- " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		return check;
	}
}
