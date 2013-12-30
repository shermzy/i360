package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voAge;
import CP_Classes.vo.voJobFunction;
import CP_Classes.vo.votblAssignment;
import CP_Classes.vo.votblRelationHigh;
import CP_Classes.vo.votblRelationSpecific;

/**
 * This class implements all the operations for Rater Relation table in the database.
 */
 
public class RaterRelation
{
	votblRelationHigh b=new votblRelationHigh();
/**
 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
 */
	
	//private Database db;
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Rater Relation";
	
	private int RelSpec = 0;
	private int RelHigh = 0;

 
	public RaterRelation()
	{
		//db = new Database();
		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	
	/**
	 * Add a new record to the Rater Relation database.
	 *
	 * Parameters:
	 *		RelationID
	 *		RelationSpecific
	 */
	public boolean addRecord(int RelationID, String RelationSpecific, int FKCompanyID, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		boolean bIsAdded=false;
		Connection con = null;
		Statement st = null;


		String sql = "INSERT INTO tblRelationSpecific (RelationID, RelationSpecific, FKCompanyID, FKOrganization) VALUES ("+ RelationID +",'"+ RelationSpecific +"', "+FKCompanyID+", "+FKOrganization+")";
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
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
            System.err.println("RaterRelation.java - addRecord- " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


        }
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, RelationSpecific, sDetail[2], sDetail[11], sDetail[10]);
		return bIsAdded;
		
	}
	
	/**
	 * Edit a record in the Rater Relation database.
	 *
	 * Parameters:
	 *		SpecificID		- primary key
	 *		RelationSpecific
	 *
	 */
	 
	public boolean editRecord(int SpecificID, String RelationSpecific, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM tblRelationSpecific WHERE SpecificID = "+ SpecificID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          
            //ResultSet rs=sqlMethod.rsQuery(command);
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
          
            if(rs.next())
            {
            	OldName = rs.getString("RelationSpecific");
            }
       
       
        }
        catch(Exception E) 
        {
            System.err.println("RaterRelation.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		/**
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("RelationSpecific");
		
		rs1.close();
		db.openDB();
		*/
		String sql = "UPDATE tblRelationSpecific SET RelationSpecific = '" + RelationSpecific + "' WHERE SpecificID = " + SpecificID;
		/*
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		*/
       boolean bIsUpdated = false;


		try	
		{
			//bIsUpdated = sqlMethod.bUpdate(sql);

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsUpdated=true;

		}
			
		catch(Exception E)
		{
	        System.err.println("JobFunction.java - JobFunction- " + E);
		}
		
		finally
    	{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

    	}
		
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+RelationSpecific+")", sDetail[2], sDetail[11], sDetail[10]);
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the Rater Relation database.
	 *
	 * Parameters:
	 *		SpecificID 	- primary key
	 */
	 
	public boolean deleteRecord(int SpecificID, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM tblRelationSpecific WHERE SpecificID = "+ SpecificID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try
        {          
            //ResultSet rs=sqlMethod.rsQuery(command);
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
                  
            if(rs.next())
            {
            	OldName = rs.getString("RelationSpecific");
            }
            
         
       
        }
        catch(Exception E) 
        {
            System.err.println("RaterRelation.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("RelationSpecific");
		
		rs1.close();
		db.openDB();
		*/
		
		String sql = "Delete from tblRelationSpecific where SpecificID = " + SpecificID;
		/*
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		*/
		 boolean bIsDeleted = false;
			
			try
			{

				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0)
				bIsDeleted=true;
	  		
			} 
			catch (Exception E)
			{
				System.err.println("JobFunction.java - deleteRecord - " + E);
				
			}

			finally
			{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Delete", itemName, OldName, sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsDeleted;
	}
	
	
	/**
	 * Get RaterRelations
	 * 
	 * @param iRelID
	 * @param iFKOrg
	 * @param iFKCompany
	 * @return
	 * 
	 * @author James
	 */
	public Vector getAllRaterRelations(int iRelID,int iFKOrg,int iFKCompany){
		
		Vector v = new Vector();
		String query = "SELECT * FROM tblRelationSpecific WHERE RelationID = "+iRelID+" AND FKOrganization= "+iFKOrg+" AND FKCompanyID= "+iFKCompany+" ORDER BY RelationSpecific";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
        try
        { 
        	
        	
           // ResultSet rs=sqlMethod.rsQuery(query);
        	con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
                        
            while(rs.next())
            {
            	votblRelationSpecific vo = new votblRelationSpecific();
            	
            	vo.setSpecificID(rs.getInt("SpecificID"));
            	vo.setRelationSpecific(rs.getString("RelationSpecific"));
            	vo.setFKCompanyID(rs.getInt("FKCompanyID"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	vo.setRelationID(rs.getInt("RelationID"));
        		v.add(vo);
            }
           
        }
        catch(Exception E) 
        {
            System.err.println("RaterRelation.java - getRaterRelation - " + E);
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
	 * This method returns the Direct Superior Specific ID from tblRelationSpecific
	 * @param iCompanyID - The Company ID
     * @param iOrgID     - The Organisation ID
	 * @throws SQLException
	 * @throws Exception
	 * Created by: Chun Pong
	 * Created on: 26 Jun 2008
	 * Last Updated by: Chun Pong
	 * Last Updated on: 19 Sept 2008
	 * Replaced: getSpecificID() 
	 */
	public int getDirectSupSpecificID(int iCompanyID, int iOrgID) throws SQLException, Exception {
		int iSpecificID = -1;
		
		String sQuery = "SELECT SpecificID FROM tblRelationSpecific " + 
						"WHERE RelationSpecific = 'Direct Superior' " +
						"AND FKCompanyID = " + iCompanyID + " " +
						"AND FKOrganization =  " + iOrgID;	
		
		//Database Connection, SQL Statement object and ResutlSet
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		//Start of Try-Catch
		try{
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sQuery);
		
			if(rs.next())
				iSpecificID = rs.getInt("SpecificID");
		}catch(Exception e){
			 System.err.println("RaterRelation.java - getDirectSupSpecificID - " + e.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		} //End of Try-Catch
		
		return iSpecificID;		
	} //End getDirectSupSpecificID()
	
	/*	This function is to get the Relation Specific statement by SpecificID 
	 *
	 *	@param iSpecificID	Integer SpecificID
	 *
	 *	@return	RelSpec		String Relation Specific
	 */
	public String getRelSpec(int iSpecificID) throws SQLException, Exception
	{
		String sRelSpec = "";
		
		String command = "SELECT RelationSpecific FROM tblRelationSpecific WHERE (SpecificID = " + iSpecificID + ")";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(command);
		//ResultSet rs1 = db.getRecord(command);
		if(rs.next())
			sRelSpec = rs.getString("RelationSpecific");
		
	
		
		}catch(Exception E){
			 System.err.println("RaterRelation.java - getRelSpec - " + E);
		}finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return sRelSpec;
	}
	
	/**
	 *	Get PKRelation from RelationHigh
	 *	@param RelationHigh name
	 *	@return RelationID
	 */
	public int getRelationHigh(String sInput) throws SQLException, Exception
	{
		int iRelation = 0;
		String sSQL = "SELECT RelationID FROM tblRelationHigh WHERE RelationHigh = '" + sInput + "'";
		/**
		db.openDB();
		ResultSet rs = db.getRecord(sSQL);
		if(rs.next())		
			iRelation = rs.getInt("RelationID");
		rs.close();
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(sSQL);
		//ResultSet rs1 = db.getRecord(command);
		if(rs.next())
			iRelation = rs.getInt("RelationID");
	
		
		}catch(Exception E){
			 System.err.println("RaterRelation.java - getRelationHigh - " + E);
		}finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return iRelation;
	}	
	
	/**
	 *	Get PKRelation from RelationSpecific.
	 *	@param RelationSpecific name
	 *	@return SpecificID
	 * Last Updated By: Chun Pong
	 * Last Updated On: 17 Sep 2008
	 */
	public int getRelationSpecific(String sRelationSpecific) throws SQLException, Exception {
		
		int iRelation = -1;		
		String sSelectQuery = "SELECT RS.SpecificID FROM tblRelationHigh RH, tblRelationSpecific RS " +
							  "WHERE RS.RelationID = RH.RelationID AND RS.RelationSpecific = ?";
		/**
		db.openDB();
		ResultSet rs = db.getRecord(sSQL);
		if(rs.next())
			iRelation = rs.getInt("SpecificID");
		
		rs.close();
		*/
		
		Connection con = null;
		PreparedStatement psSelect = null;
		ResultSet rs = null;
		
		//Start of Try-Catch
		try{
			con = ConnectionBean.getConnection();
			psSelect = con.prepareStatement(sSelectQuery);
			psSelect.setString(1, sRelationSpecific);
			rs = psSelect.executeQuery();		
		
			if(rs.next())
				iRelation = rs.getInt("SpecificID");
		}catch(Exception e){
			 System.err.println("RaterRelation.java - getRelationSpecific() - " + e.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(psSelect); //Close statement
			ConnectionBean.close(con); //Close connection
		} //End of Try-Catch
		return iRelation;
	} //End of getRelationSpecific()
	
	/**
	 * Get the total relations defined in a survey
	 * @param iSurveyID
	 * @param bBreakDown - True = Break down to relation specific
	 * @return int total
	 * @throws SQLException
	 * @throws Exception
	 * @author Maruli
	 */
	public int getTotalRelation(int iSurveyID, boolean bBreakDown) throws SQLException, Exception
	{
		int iTotal = 0;
		String sSQL = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		if(bBreakDown == false)
		{
			sSQL = "SELECT COUNT(DISTINCT RTRelation) AS Total FROM tblAssignment WHERE SurveyID = " + iSurveyID;
			/*
			db.openDB();
			ResultSet rs = db.getRecord(sSQL);
			
			if(rs.next())
				iTotal = rs.getInt("Total");
				*/
		
			try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sSQL);
			
			if(rs.next())
				iTotal = rs.getInt("Total");
		
			
			}catch(Exception E){
				 System.err.println("RaterRelation.java - getTotalRelation - " + E);
			}finally{

				ConnectionBean.closeRset(rs); //Close ResultSet
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
			
			
		}
		else
		{
			sSQL = "SELECT DISTINCT tblRelationSpecific.RelationSpecific FROM tblAssignment LEFT OUTER JOIN "+
					"tblRelationSpecific ON tblAssignment.RTSpecific = tblRelationSpecific.SpecificID "+
					"WHERE tblAssignment.SurveyID = " + iSurveyID;
			/*
			db.openDB();
			ResultSet rs = db.getRecord(sSQL);
			
			// RTRelation = 0 for SELF. If we are using COUNT in the SQL, it will exclude counting it
			while(rs.next())
			{
				iTotal++;
			}
			*/
			try{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(sSQL);
				
				while(rs.next())
				{
					iTotal++;
				}
			
				
				}catch(Exception E){
					 System.err.println("RaterRelation.java - getTotalRelation - " + E);
				}finally{

					ConnectionBean.closeRset(rs); //Close ResultSet
					ConnectionBean.closeStmt(st); //Close statement
					ConnectionBean.close(con); //Close connection
				}
				
		}
		
		return iTotal;
	}
	
	/**
	 * Get Relation high used in a survey (exclude SELF)
	 * @param iSurveyID
	 * @return ResultSet
	 * @author Maruli
	 * @see IndividualReport#writeBreakDownQuadrantScore()
	 */
	public Vector getRelationHigh(int iSurveyID)
	{
		String sSQL = "SELECT DISTINCT tblAssignment.RTRelation, tblRelationHigh.RelationHigh "+
						"FROM tblAssignment INNER JOIN tblRelationHigh ON tblAssignment.RTRelation = tblRelationHigh.RelationID "+
						"WHERE tblAssignment.RTRelation <> 2 AND tblAssignment.SurveyID = " + iSurveyID;
	
		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		  try
	        {          
			  con=ConnectionBean.getConnection();
			  st=con.createStatement();
			  rs=st.executeQuery(sSQL);
	                        
	            while(rs.next())
	            {
	            	votblRelationHigh vo = new votblRelationHigh();
	            	vo.setRelationHigh(rs.getString("RelationHigh"));
	        		vo.setRTRelation(rs.getInt("RTRelation"));
	            	v.add(vo);
	            }
	            
	           
	        }
	        catch(Exception E) 
	        {
	        	 System.err.println("RaterRelation.java - getRelationHigh - " + E);
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
	 * get all relation high
	 * 
	 * @return Vector
	 */
	public Vector getAllRelationHigh()
	{
		String query = "SELECT * FROM tblRelationHigh";
		
		Vector v = new Vector();
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
	            	votblRelationHigh vo = new votblRelationHigh();
	            	vo.setRelationHigh(rs.getString("RelationHigh"));
	        		vo.setRelationID(rs.getInt("RelationID"));
	            	v.add(vo);
	            }
	            
	           
	        }
	        catch(Exception E) 
	        {
	        	 System.err.println("RaterRelation.java - getAllRelationHigh - " + E);
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
	 * Get specific relation used in a survey (exclude SELF)
	 * @param iSurveyID
	 * @param iRelationHigh
	 * @return ResultSet
	 * @author Maruli
	 * @see IndividualReport#writeBreakDownQuadrantScore(int)
	 */
	public Vector getRelationSpecific(int iSurveyID, int iRelationHigh)
	{
		String sSQL = "SELECT DISTINCT tblAssignment.RTSpecific, tblRelationSpecific.RelationSpecific FROM tblAssignment "+
						"INNER JOIN tblRelationSpecific ON tblAssignment.RTSpecific = tblRelationSpecific.SpecificID "+
						"WHERE tblAssignment.RTRelation <> 2 AND tblAssignment.SurveyID = "+iSurveyID +
						" AND tblAssignment.RTRelation = "+iRelationHigh;
		/*
		db.openDB();
		ResultSet rs = db.getRecord(sSQL);
		*/
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		  try
	        {          
			  con=ConnectionBean.getConnection();
			  st=con.createStatement();
			  rs=st.executeQuery(sSQL);
	                        
	            while(rs.next())
	            {
	            	votblAssignment vo = new votblAssignment();
	            	vo.setRTSpecific(rs.getInt("RTSpecific"));
	            	vo.setRelationSpecific(rs.getString("RelationSpecific"));
	            	v.add(vo);
	            }
	            
	           
	        }
	        catch(Exception E) 
	        {
	        	 System.err.println("RaterRelation.java - getRelationHigh - " + E);
	        }
	        finally
	        {
	        	ConnectionBean.closeRset(rs); //Close ResultSet
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection
	        }

		
		return v;
	}
	
	public void setRelHigh(int RelHigh) 
	{
		this.RelHigh = RelHigh;
	}

	public int getRelHigh() 
	{
		return RelHigh;
	}
	
	public void setRelSpec(int RelSpec) 
	{
		this.RelSpec = RelSpec;
	}

	public int getRelSpec() 
	{
		return RelSpec;
	}
	
	public Vector getAllRelationHigh(int iPKUser, int iSelectedTargetID)
	{
		String sqla = "SELECT * FROM tblRelationHigh";
		
		if(iSelectedTargetID == iPKUser)
		{
			sqla = sqla+" WHERE RelationID = 2";
			
		}
		else
		{
			sqla = sqla+" WHERE RelationID != 2";
			
		}
		sqla = sqla+" ORDER BY RelationHigh";
		
		
		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		  try
	        {          
			  con=ConnectionBean.getConnection();
			  st=con.createStatement();
			  rs=st.executeQuery(sqla);
	                        
	            while(rs.next())
	            {
	            	votblRelationHigh vo = new votblRelationHigh();
	            	vo.setRelationHigh(rs.getString("RelationHigh"));
	        		vo.setRelationID(rs.getInt("RelationID"));
	            	v.add(vo);
	            }
	            
	           
	        }
	        catch(Exception E) 
	        {
	        	 System.err.println("RaterRelation.java - getAllRelationHigh - " + E);
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
	 * Get specific relation used in a survey (exclude SELF)
	 * @param iSurveyID
	 * @param iRelationHigh
	 * @return ResultSet
	 * @author Maruli
	 * @see IndividualReport#writeBreakDownQuadrantScore(int)
	 */
	public Vector getAllRelationSpecific(int iOrgID, int iPKUser, int iSelectedTargetID)
	{
		String sql1 = "SELECT * FROM tblRelationSpecific WHERE FKOrganization="+iOrgID;
		
		if(iSelectedTargetID == iPKUser)
		{
			sql1 = sql1+" AND RelationID = 2";
		}
		else
		{
			sql1 = sql1+" AND RelationID != 2";
		}
		
		sql1 = sql1+" ORDER BY RelationSpecific";

		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql1);
                        

            while(rs.next())
            {
            	votblRelationSpecific vo = new votblRelationSpecific();
            	vo.setRelationSpecific(rs.getString("RelationSpecific"));
            	vo.setRelationID(rs.getInt("RelationID"));

                vo.setSpecificID(rs.getInt("SpecificID")); // Changed by DeZ 17/06/08 to display rater type properly
            	v.add(vo);
            }
	            
	           
       }
       catch(Exception E) 
       {
        	System.err.println("RaterRelation.java - getRelationSpecifics - " + E);
       }
       finally
       {
	    	ConnectionBean.closeRset(rs); //Close ResultSet
	    	ConnectionBean.closeStmt(st); //Close statement
	    	ConnectionBean.close(con); //Close connection
       }

		
       return v;
	}
}