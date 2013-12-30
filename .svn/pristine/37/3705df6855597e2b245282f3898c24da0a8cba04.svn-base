package CP_Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblSurveyDemos;

public class SurveyDemo {

	public Vector getSurveyDemo(int iSurveyID) throws SQLException, Exception {
		Vector v=new Vector();
		
		String query ="SELECT * FROM tblSurveyDemos a, tblDemographicSelection b WHERE a.DemographicID= b.DemographicID AND SurveyID ="+iSurveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				votblSurveyDemos vo=new votblSurveyDemos();
				vo.setDemographicID(rs.getInt("DemographicID"));
				vo.setDemographicName(rs.getString("DemographicName"));
		
				v.add(vo);
			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyDemo.java - getSurveyDemo - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	
	public Vector getDemoNotAssigned(int iSurveyID) throws SQLException, Exception {
		Vector v=new Vector();
		
		String query ="SELECT * FROM tblDemographicSelection WHERE (DemographicID NOT IN (SELECT DemographicID FROM tblSurveyDemos WHERE SurveyID = "+iSurveyID+"))";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				votblSurveyDemos vo=new votblSurveyDemos();
				vo.setDemographicID(rs.getInt("DemographicID"));
				vo.setDemographicName(rs.getString("DemographicName"));
		
				v.add(vo);
			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyDemo.java - getSurveyDemo - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
}
