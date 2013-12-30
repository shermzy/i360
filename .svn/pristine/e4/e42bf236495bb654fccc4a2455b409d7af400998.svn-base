package CP_Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblSurveyBehaviour;
/*
 * Change Log
 * ===============================================================================================================
 * 	Date		Changed By		Method(s)								Change(s)
 * ===============================================================================================================
 * 21/06/2012	Albert			getSurveyClusterKB()		Added a new method to get all KBs based on the cluster
 * 
 */
public class SurveyKB {
	
	/**
	 * Added by Xuehai, 02 Jun 2011. Add sorting by Name.
	 * 
	 * @param sortToggle, 0:asc, default; 1:desc;
	 * @param sortType, 1:CompetencyName. By far, only support by Name.
	 */
	public Vector getSurveyKB(int iSurveyID, int sortToggle, int sortType) throws SQLException, Exception {

		Vector v=new Vector();
		
		String query ="SELECT * FROM tblSurveyBehaviour a, keyBehaviour b, Competency c WHERE a.KeyBehaviourID = b.PKKeyBehaviour AND b.FKCompetency = c.PKCompetency AND a.SurveyID ="+ iSurveyID
		+ " ORDER BY " ;
		//+ " ORDER BY isSystemGenerated, CompetencyName" ;
		if(sortType == 1){
			query = query + "c.CompetencyName";
		}else{
			query = query + "c.CompetencyName";
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
				votblSurveyBehaviour vo=new votblSurveyBehaviour();
				vo.setCompetencyName(rs.getString("CompetencyName"));
				vo.setCompetencyID(rs.getInt("PKCompetency"));
				vo.setKeyBehaviourID(rs.getInt("PKKeyBehaviour"));
				vo.setKBName(rs.getString("KeyBehaviour"));
				
				v.add(vo);
			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyKB.java - getSurveyKB - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	
	public Vector getSurveyKB(int iSurveyID) throws SQLException, Exception {
		return getSurveyKB(iSurveyID, 0, 1);
	}
	
	/**
	 * Added by Albert, 21 Jun 2012.
	 * 
	 * @param sortToggle, 0:asc, default; 1:desc;
	 * @param sortType, 1:CompetencyName. By far, only support by Name.
	 */
	public Vector getSurveyClusterKB(int iSurveyID, int sortToggle, int sortType, int iClusterID) throws SQLException, Exception {

		Vector v=new Vector();
		
		String query ="SELECT * FROM tblSurveyBehaviour a, keyBehaviour b, Competency c WHERE a.KeyBehaviourID = b.PKKeyBehaviour AND b.FKCompetency = c.PKCompetency AND a.SurveyID ="+ iSurveyID+" AND a.ClusterID = "+iClusterID+ " ORDER BY " ;

		if(sortType == 1){
			query = query + "c.CompetencyName";
		}else{
			query = query + "c.CompetencyName";
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
				votblSurveyBehaviour vo=new votblSurveyBehaviour();
				vo.setCompetencyName(rs.getString("CompetencyName"));
				vo.setCompetencyID(rs.getInt("PKCompetency"));
				vo.setKeyBehaviourID(rs.getInt("PKKeyBehaviour"));
				vo.setKBName(rs.getString("KeyBehaviour"));
				
				v.add(vo);
			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyKB.java - getSurveyClusterKB - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	
	public Vector getSurveyClusterKB(int iSurveyID, int iClusterID) throws SQLException, Exception {
		return getSurveyClusterKB(iSurveyID, 0, 1, iClusterID);
	}
	
	public Vector getKBNotAssigned(int iSurveyID, int iCompID, int iKBLevel) throws SQLException, Exception {
		Vector v=new Vector();
		
		String query1 ="SELECT * FROM KeyBehaviour WHERE (PKKeyBehaviour NOT IN (SELECT KeyBehaviourID FROM tblSurveyBehaviour";
		query1 = query1+" WHERE SurveyID = "+iSurveyID+")) AND FKCompetency= "+iCompID+" AND KBLevel="+iKBLevel;
		

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query1);
			while(rs.next()){
				votblSurveyBehaviour vo=new votblSurveyBehaviour();
				vo.setKeyBehaviourID(rs.getInt("PKKeyBehaviour"));
				vo.setKBName(rs.getString("KeyBehaviour"));
				
				v.add(vo);
			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyKB.java - getKBNotAssigned - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	
}
