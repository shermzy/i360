package CP_Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.votblSurveyCompetency;

public class SurveyCompetency {

	/**
	 * Added by Xuehai, 02 Jun 2011. Add sorting by Name.
	 * 
	 * @param sortToggle, 0:asc, default; 1:desc;
	 * @param sortType, 1:CompetencyName. By far, only support by Name.
	 */
	public Vector getSurveyCompetency(int iSurveyID, int sortToggle, int sortType) throws SQLException, Exception {

		Vector v=new Vector();
		
		String query ="SELECT * FROM tblSurveyCompetency b, Competency c WHERE b.CompetencyID = c.PKCompetency AND b.SurveyID ="+ iSurveyID
		+ " ORDER BY " ;
		//+ " ORDER BY isSystemGenerated, CompetencyName" ;
		if(sortType == 1){
			query = query + "CompetencyName";
		}else{
			query = query + "CompetencyName";
		}
		if(sortToggle==1){
			query = query + " DESC";
		}
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				votblSurveyCompetency vo=new votblSurveyCompetency();
				vo.setCompetencyDefinition(rs.getString("CompetencyDefinition"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				vo.setCompetencyID(rs.getInt("PKCompetency"));
				vo.setCompetencyLevel(rs.getInt("CompetencyLevel"));
				vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
		
				v.add(vo);
			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyCompetency.java - getSurveyCompetency - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	public Vector getSurveyCompetency(int iSurveyID) throws SQLException, Exception {
		return getSurveyCompetency(iSurveyID, 0, 1);
	}
	
	/**
	 * Added by Albert, 21 Jun 2012.
	 * 
	 * @param sortToggle, 0:asc, default; 1:desc;
	 * @param sortType, 1:CompetencyName. By far, only support by Name.
	 */
	public Vector getSurveyClusterCompetency(int iSurveyID, int iClusterID, int sortToggle, int sortType) throws SQLException, Exception {

		Vector v=new Vector();
		
		String query ="SELECT * FROM tblSurveyCompetency b, Competency c WHERE b.CompetencyID = c.PKCompetency AND b.SurveyID ="+ iSurveyID+" AND b.ClusterID ="+iClusterID+" ORDER BY " ;
		//+ " ORDER BY isSystemGenerated, CompetencyName" ;
		if(sortType == 1){
			query = query + "CompetencyName";
		}else{
			query = query + "CompetencyName";
		}
		if(sortToggle==1){
			query = query + " DESC";
		}
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				votblSurveyCompetency vo=new votblSurveyCompetency();
				vo.setCompetencyDefinition(rs.getString("CompetencyDefinition"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				vo.setCompetencyID(rs.getInt("PKCompetency"));
				vo.setCompetencyLevel(rs.getInt("CompetencyLevel"));
				vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
		
				v.add(vo);
			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyCompetency.java - getSurveyClusterCompetency - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	public Vector getSurveyClusterCompetency(int iSurveyID, int iClusterID) throws SQLException, Exception {
		return getSurveyClusterCompetency(iSurveyID, iClusterID, 0, 1);
	}
	
	public int getCompetencyLevel(int iSurveyID, int iCompID) {
		String query ="SELECT * FROM tblSurveyCompetency WHERE SurveyID = "+iSurveyID+" AND CompetencyID = "+iCompID;
		
		int iCompLevel = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()){
				iCompLevel = rs.getInt("CompetencyLevel");
			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyCompetency.java - getCompetencyLevel - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		
		return iCompLevel;
	}
	
}
