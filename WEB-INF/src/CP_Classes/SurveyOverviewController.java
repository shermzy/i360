package CP_Classes;

import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import CP_Classes.common.ConnectionBean;


public class SurveyOverviewController{
	public SurveyOverviewController()
	{	
	}

	public int[] getSurveyRaterStatus(int targetLoginID,String raterCode){
		{
			int[] results = new int[2]; 
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String query ="SELECT count(distinct RaterLoginID) as Distribution,count(RaterStatus)as Rated ";
					query +="  FROM tbl_PrelimQnAns a inner join tblAssignment b on a.FKRaterID=b.RaterLoginID  and b.AssignmentID=a.FKAssignmentID";
					 query+=" inner join tbl_PrelimQn c on a.FKPrelimQnID=c.PrelimQnID where TargetLoginID="+ targetLoginID + " and RaterCode like" + raterCode ;
			
			try{
				
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(query);
				
				while(rs != null && rs.next())
				{
					results[0] = rs.getInt("Distribution");
					results[1] = rs.getInt("Rated");
				}
				
			}catch(Exception E) 
	        {
	            System.err.println("PrelimQuestionController.java - updatePrelimQnHeader - " + E);
	        }
	        finally
	        {
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection
	        }
			return results;	
		}
		
		
	}
}