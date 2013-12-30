/**
 * This SurveyRelationSpecific class is used to manage relation specific
 * @author Liu Taichen
 * @version 1.0      4 June 2012
 */

package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.*;

public class SurveyRelationSpecific {
	
	private EventViewer ev;
	private Create_Edit_Survey user;
	private String sDetail[] = new String[13];
 	private String itemName = "SurveyRelationSpecific";
	
	public SurveyRelationSpecific(){
		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	
	public static void main(String[] args){
		
		//populate the tblSurveyRelationSpecific table
		SurveyRelationSpecific srs = new SurveyRelationSpecific();
		RaterRelation rr = new RaterRelation();
		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM tblRelationSpecific";
		
		  try
	        {          
			  con=ConnectionBean.getConnection();
			  st=con.createStatement();
			  rs=st.executeQuery(sql);
	                        
	            while(rs.next())
	            {
	            	votblRelationSpecific vo = new votblRelationSpecific();
	            	
	            	vo.setSpecificID(rs.getInt("SpecificID"));
	            	vo.setRelationSpecific(rs.getString("RelationSpecific"));
	        		vo.setRelationID(rs.getInt("RelationID"));
	        		vo.setFKOrganization(rs.getInt("FKOrganization"));
	        		vo.setFKCompanyID(rs.getInt("FKCompanyID"));
	            	v.add(vo);
	            }
	            
	           
	        }
	        catch(Exception E) 
	        {
	        	 System.err.println("RaterRelation.java - getAllRelationHigh test- " + E);
	        }
	        finally
	        {
	        	ConnectionBean.closeRset(rs); //Close ResultSet
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection
	        }

		Vector s = new Vector();
		  for(Object o : v){
			 votblRelationSpecific rSpecific = (votblRelationSpecific)o;
			 
			  int orgID = rSpecific.getFKOrganization();
			  int comID = rSpecific.getFKCompanyID();
			   s = srs.user.getSurveys(comID, orgID);
			   for(Object survey:s){
					  int surveyid = ((votblSurvey)survey).getSurveyID();
					  try {
						srs.insert(surveyid, rSpecific.getRelationID(), rSpecific.getRelationSpecific(), 1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
					  //get the specificID of the new entry
					sql = "select * from tblSurveyRelationSpecific where SurveyID = " + surveyid + " and RelationID =" + rSpecific.getRelationID() + " and RelationSpecific = '" + rSpecific.getRelationSpecific() + "'" ; 
					votblSurveyRelationSpecific tempVo = new votblSurveyRelationSpecific(); 
					try{con=ConnectionBean.getConnection();
					  
					  st=con.createStatement();
					  rs=st.executeQuery(sql);
			                        
			            while(rs.next())
			            {
			            	
			            	
			            	tempVo.setSpecificID(rs.getInt("SpecificID"));
			            	tempVo.setRelationSpecific(rs.getString("RelationSpecific"));
			        		tempVo.setRelationID(rs.getInt("RelationID"));
			        		tempVo.setSurveyID(rs.getInt("SurveyID"));
			            	
			            }
			            
			           
			        }
			        catch(Exception E) 
			        {
			        	 System.err.println("RaterRelation.java - main getting specificID- " + E);
			        }
			        finally
			        {
			        	ConnectionBean.closeRset(rs); //Close ResultSet
			        	ConnectionBean.closeStmt(st); //Close statement
			        	ConnectionBean.close(con); //Close connection
			        }
					
					//change the specificid in the assignment table to the correct one
					
					sql = "Update tblAssignment set RTSpecific = " + tempVo.getSpecificID() + " where SurveyID = " + surveyid + " and RTSpecific =" + rSpecific.getSpecificID(); 
					PreparedStatement ps=null;
					try{con=ConnectionBean.getConnection();
					  
					  st=con.createStatement();
					  ps=con.prepareStatement(sql);
					  ps.executeUpdate();
			                        
			           
			           
			        }
			        catch(Exception E) 
			        {
			        	 System.err.println("RaterRelation.java - main - updateing assigmenttable- " + E);
			        }
			        finally
			        {
			        	ConnectionBean.closeRset(rs); //Close ResultSet
			        	ConnectionBean.closeStmt(st); //Close statement
			        	ConnectionBean.close(con); //Close connection
			        }
					
					

					  
				  }
				
			  
			  
			  
		  }
		  
		 
	}
	
	/**
	 * This method retrieve the description of a relation specific
	 * @param SpecificID
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 5 June 2012
	 */
	
	public String getRelationSpecific(int SpecificID){
		String relationSpecific = "";
		
		boolean bIsAdded=false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM tblSurveyRelationSpecific WHERE SpecificID=" + SpecificID;
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
		System.out.println(sql);
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql);
			
			bIsAdded=true;
			
			 while(rs.next())
	            {
	            	relationSpecific = rs.getString("RelationSpecific");
	            	
	            }

		}
		catch(Exception E)
		{
            System.err.println("SurveyRelationSpecific-getRelationSpecific " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


        }
		
		
		
		return relationSpecific;
	}
	
	/**
	 * This method inserts one row into the database table tblSurveyRelationSpecific
	 * @param SurveyID - The ID of the survey
     * @param SpecificID    - The ID of the relation specific
     * @param RelationID  - The ID of the rater relation
     * @param RelationSpecific  - The description of the relation specific
     * @param PKUer   - The ID of the user
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 4 June 2012
	 */
	
	public boolean insert(int SurveyID, int RelationID, String RelationSpecific, int PKUser)throws SQLException, Exception {
		
		boolean bIsAdded=false;
		Connection con = null;
		Statement st = null;
        

		String sql = "INSERT INTO tblSurveyRelationSpecific (SurveyID, RelationID, RelationSpecific) VALUES ("+ SurveyID +","+RelationID+", '"+RelationSpecific +"')";
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
            System.err.println("SurveyRelationSpecific-insert " + E);
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
	 * This method inserts one row into the database table tblSurveyRelationSpecific
	 * @param SurveyID - The ID of the survey
     * @param iRelID - The ID of the rater relation
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 4 June 2012
	 */
	
	public Vector getRelationSpecific(int iRelID,int surveyID){
		
		Vector v = new Vector();
		String query = "SELECT * FROM tblSurveyRelationSpecific WHERE RelationID = "+iRelID+" AND SurveyID= "+surveyID+" ORDER BY RelationSpecific";
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
            	votblSurveyRelationSpecific vo = new votblSurveyRelationSpecific();
            	vo.setSurveyID(rs.getInt("SurveyID"));
            	vo.setSpecificID(rs.getInt("SpecificID"));
            	vo.setRelationSpecific(rs.getString("RelationSpecific"));
              	vo.setRelationID(rs.getInt("RelationID"));
        		v.add(vo);
            }
           
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRelationSpecific.java - getRelationSpecific - " + E);
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
	 * This method retrieve the information of a relation specific
	 * @param SpecificID
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 7 June 2012
	 */
	
	public votblSurveyRelationSpecific getSpecific(int SpecificID){
		votblSurveyRelationSpecific vo = new votblSurveyRelationSpecific();
		
		String command = "SELECT * FROM tblSurveyRelationSpecific WHERE SpecificID = "+ SpecificID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          
           
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
          
            if(rs.next())
            {
            	vo.setRelationSpecific(rs.getString("RelationSpecific"));
            	vo.setSpecificID(SpecificID);
            	vo.setRelationID(rs.getInt("RelationID"));
            	vo.setSurveyID(rs.getInt("SurveyID"));
            	
            }
       
       
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRelationSpecific.java - getSpecific - " + E);
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
	 * This method updates one row into the database table tblSurveyRelationSpecific
	 * @param SpecificID - primary key of the relation specific
     * @param RelationSpecific  - decscription of the relation specific
     * @param PKUser - user id of the user
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 5 June 2012
	 */
	public boolean update(int SpecificID, String RelationSpecific, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM tblSurveyRelationSpecific WHERE SpecificID = "+ SpecificID;

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
            System.err.println("SurveyRelationSpecific.java - update - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		
		String sql = "UPDATE tblSurveyRelationSpecific SET RelationSpecific = '" + RelationSpecific + "' WHERE SpecificID = " + SpecificID;
		
       boolean bIsUpdated = false;


		try	
		{
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsUpdated=true;

		}
			
		catch(Exception E)
		{
	        System.err.println("SurveyRelationSpecific.java - update- " + E);
		}
		
		finally
    	{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

    	}
		
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("update", itemName, "("+OldName+") - ("+RelationSpecific+")", sDetail[2], sDetail[11], sDetail[10]);
		return bIsUpdated;
	}
	
	/**
	 * This method deletes one row into the database table tblSurveyRelationSpecific
	 * @param SpecificID - primary key of the relation specific
     * @param PKUser - user id of the user
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 5 June 2012
	 */
	 
	public boolean delete(int SpecificID, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM tblSurveyRelationSpecific WHERE SpecificID = "+ SpecificID;
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
            System.err.println("SurveyRelationSpecific.java - delete - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		
		
		String sql = "Delete from tblSurveyRelationSpecific where SpecificID = " + SpecificID;
		
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
				System.err.println("SurveyRelationSpecific.java - delete - " + E);
				
			}

			finally
			{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("delete", itemName, OldName, sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsDeleted;
	}

}

