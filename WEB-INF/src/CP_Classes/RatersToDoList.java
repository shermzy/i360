package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.*;

/**
 * This class implements all the operations for Rater's To Do List.
 */

/**
 * 
 * Change Log
 * ==========
 *
 * Date        By				Method(s)            								Change(s) 
 * =========================================================================================================================================
					 
 * 08/06/12   Liu Taichen       getToDoList(int)                                   -Changed the method to retrieve relation specific when there is one
 * 
 * 
 */

public class RatersToDoList
{
	/**
	 * Declaration of new object of class Database.
	 */
	private Database db;
	
	
	/**
	 * Bean Variable for sorting purposes. Total Array depends on total SortType.
	 * 0 = ASC
	 * 1 = DESC
	 */
	private int Toggle [];	// 0=asc, 1=desc
	
	/**
	 * Bean Variable to store the Sorting type.
	 */
	private int SortType;
	
	/**
	 * Create new instance of RatersToDoList object.
	 */
	public RatersToDoList(){
		db = new Database();
		
		Toggle = new int [6];
		
		for(int i=0; i<6; i++)
			Toggle[i] = 0;
			
		SortType = 3;		
	}
	
	/**
	 * Store Bean Variable toggle either 1 or 0.
	 */	
	public void setToggle(int toggle) {
		Toggle[SortType - 1] = toggle;
	}

	/**
	 * Get Bean Variable toggle.
	 */
	public int getToggle() {
		return Toggle [SortType - 1];
	}	
	
	/**
	 * Store Bean Variable Sort Type.
	 */
	public void setSortType(int SortType) {
		this.SortType = SortType;
	}

	/**
	 * Get Bean Variable SortType.
	 */
	public int getSortType() {
		return SortType;
	}	
	
	
	/**
	 * Retrieves all To Do List.
	 */
	public Vector getToDoList(int raterID) throws SQLException, Exception {		
		
		Vector v = new Vector();
		SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
		
		String query = "";
		int nameSeq = nameSequence(raterID);
		
		query = query + "SELECT tblAssignment.AssignmentID, tblSurvey.SurveyName, ";
		
		if(nameSeq == 0)
			query = query + "[User].FamilyName + ' ' + [User].GivenName as Name";
		else
			query = query + "[User].GivenName + ' ' + [User].FamilyName as Name";
			 
		query = query + ", tblSurvey.DeadLineSubmission, ";
		query = query + "tblRelationHigh.RelationHigh, tblSurvey.SurveyStatus, tblSurvey.SurveyID, ";
		/*
		 * Change(s): Added one line of the query to retrieve relationID and SurveyID
		 * Reason(s): To display the specific relation on the raterToDolist page
		 * Updated By: Liu Taichen
		 * Updated On: 8/6/2012
		 */
		query = query + "tblRelationHigh.RelationID, tblSurvey.SurveyID, ";
		query = query + "tblAssignment.RaterStatus FROM tblRelationHigh INNER JOIN ";
		query = query + "tblAssignment INNER JOIN tblSurvey ON ";
		query = query + "tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
		query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser ON ";
		query = query + "tblRelationHigh.RelationID = tblAssignment.RTRelation ";
		query = query + "WHERE tblAssignment.RaterLoginID = " + raterID;
		query = query + " AND tblAssignment.RaterStatus = 0 ";
		query = query + " AND tblSurvey.SurveyStatus <> 2 ";
		query = query + "ORDER BY ";
		
		
		if(SortType == 1)
			query = query + "tblSurvey.SurveyName";
		else if(SortType == 2) {
			if(nameSeq == 0)
				query = query + "[User].FamilyName";
			else
				query = query + "[User].GivenName";
		}
		else if(SortType == 3)
			query = query + "tblAssignment.DeadLineSubmission";
		else if(SortType == 4)
			query = query + "tblAssignment.RTRelation";
		else if(SortType == 5)
			query = query + "tblSurvey.SurveyStatus";
		else if(SortType == 6)
			query = query + "tblAssignment.RaterStatus";
		else if(SortType == 7)
			query = query + "DeletedDate";

		if(Toggle[SortType - 1] == 1)
			query = query + " DESC";
	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try 
		{          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

    		while(rs != null && rs.next())
    	    {
    			String sToDoList[] = new String[7];
    			
    			sToDoList[0] = Integer.toString(rs.getInt("AssignmentID"));
    	    	sToDoList[1] = rs.getString("SurveyName");
    	    	sToDoList[2] = rs.getString("Name");
    	    	sToDoList[3] = day_view.format(rs.getDate("DeadlineSubmission"));
    	    	sToDoList[4] = rs.getString("RelationHigh");
    	    	sToDoList[5] = Integer.toString(rs.getInt("SurveyStatus"));
    	  		sToDoList[6] = Integer.toString(rs.getInt("RaterStatus"));
    			
    			/*
    			 * Change(s): change the relation name to specific relation name if there is one associated with the relation
    			 * Reason(s): To display the specific relation on the raterToDolist page
    			 * Updated By: Liu Taichen
    			 * Updated On: 8/6/2012
    			 */
    	  		SurveyRelationSpecific srs = new SurveyRelationSpecific();    	  		
    	  		int relationID = rs.getInt("RelationID");
    			int surveyID = rs.getInt("SurveyID");
    			Vector vSpec = srs.getRelationSpecific(relationID, surveyID);
    			if(!vSpec.isEmpty()){
    			String RelationSpecific = ((votblSurveyRelationSpecific)vSpec.elementAt(0)).getRelationSpecific();
    			sToDoList[4] = RelationSpecific;
    			}
    	  		v.add(sToDoList);
    	  	}
	                		
        }
        catch(Exception E) 
        {
            System.err.println("RatersToDoList.java - getToDoList - " + E);
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
	 * Retrieves all To Do List.
	 */
	public Vector getSurveys(int raterID) throws SQLException, Exception {		
		
		Vector v = new Vector();
		SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
		
		String query = "";
		int nameSeq = nameSequence(raterID);
		
		query = query + "SELECT tblAssignment.AssignmentID, tblSurvey.SurveyName, ";
		
		if(nameSeq == 0)
			query = query + "[User].FamilyName + ' ' + [User].GivenName as Name";
		else
			query = query + "[User].GivenName + ' ' + [User].FamilyName as Name";
			 
		query = query + ", tblSurvey.DeadLineSubmission, ";
		query = query + "tblRelationHigh.RelationHigh, tblSurvey.SurveyStatus, ";
		query = query + "tblAssignment.RaterStatus FROM tblRelationHigh INNER JOIN ";
		query = query + "tblAssignment INNER JOIN tblSurvey ON ";
		query = query + "tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
		query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser ON ";
		query = query + "tblRelationHigh.RelationID = tblAssignment.RTRelation ";
		query = query + "WHERE tblAssignment.RaterLoginID = " + raterID;
		query = query + " AND tblAssignment.RaterStatus <> 0 ";
		//query = query + " AND tblSurvey.SurveyStatus <> 2 ";
		query = query + "ORDER BY ";
		//System.out.println("-----"+query);
		
		if(SortType == 1)
			query = query + "tblSurvey.SurveyName";
		else if(SortType == 2) {
			if(nameSeq == 0)
				query = query + "[User].FamilyName";
			else
				query = query + "[User].GivenName";
		}
		else if(SortType == 3)
			query = query + "tblAssignment.DeadLineSubmission";
		else if(SortType == 4)
			query = query + "tblAssignment.RTRelation";
		else if(SortType == 5)
			query = query + "tblSurvey.SurveyStatus";
		else if(SortType == 6)
			query = query + "tblAssignment.RaterStatus";
		else if(SortType == 7)
			query = query + "DeletedDate";

		if(Toggle[SortType - 1] == 1)
			query = query + " DESC";
		
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
	           
    		while(rs != null && rs.next())
    	    {
    			String sToDoList[] = new String[7];
    			
    			sToDoList[0] = Integer.toString(rs.getInt("AssignmentID"));
    	    	sToDoList[1] = rs.getString("SurveyName");
    	    	sToDoList[2] = rs.getString("Name");
    	    	sToDoList[3] = day_view.format(rs.getDate("DeadlineSubmission"));
    	    	sToDoList[4] = rs.getString("RelationHigh");
    	    	sToDoList[5] = Integer.toString(rs.getInt("SurveyStatus"));
    	  		sToDoList[6] = Integer.toString(rs.getInt("RaterStatus"));
    			
    			
    	  		v.add(sToDoList);
    	  	}

        }
        catch(Exception E) 
        {
            System.err.println("RatersToDoList.java - getSurveys - " + E);
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
	 * Get the name sequence for the rater under a specific organization.
	 */
	public int nameSequence(int raterID) throws SQLException, Exception {		
		int data = 0;
		
		String query = "";
		
		query = query + "SELECT tblOrganization.NameSequence FROM tblOrganization INNER JOIN ";
		query = query + "[User] ON tblOrganization.PKOrganization = [User].FKOrganization ";
		query = query + "WHERE [User].PKUser = " + raterID;

		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
	           
    		if(rs != null && rs.next())
    	    {
		
    			data = rs.getInt(1);
    	    }
          	
        }
        catch(Exception E) 
        {
            System.err.println("RatersToDoList.java - nameSequence - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

		return data;
	}
	
	/**
	 * Get the survey, target, and rater ID for the particular assignment ID.
	 */
	public int [] assignmentInfo(int asgtID) throws SQLException, Exception {		
		int data [] = new int [3];
		
		String query = "";
		
		query = query + "SELECT SurveyID, TargetLoginID, RaterLoginID from tblAssignment where AssignmentID = " + asgtID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
	           
    		if(rs != null && rs.next())
    	    {
				for(int i=0; i<3; i++)		
					data[i] = rs.getInt(i+1);	
    	  	}
            
      	
        }
        catch(Exception E) 
        {
            System.err.println("RatersToDoList.java - assignmentInfo - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

	  	return data;

	}
	
	public static void main(String args []) throws SQLException, Exception
	{
		RatersToDoList RTD = new RatersToDoList();
		
		RTD.getToDoList(6416);
	}
}