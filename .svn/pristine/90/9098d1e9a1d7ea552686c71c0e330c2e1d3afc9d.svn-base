package CP_Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCluster;
import CP_Classes.vo.votblSurveyCluster;
/*
 * Change Log
 * ===============================================================================================================
 * 	Date		Changed By		Method(s)								Change(s)
 * ===============================================================================================================
 * 18/06/2012	Albert			-								Added this java file to manage clusters of each survey
 */
public class SurveyCluster {
	
	public Vector getSurveyCluster(int iSurveyID, int sortToggle, int sortType) throws SQLException, Exception {

		Vector v=new Vector();
		
		String query ="SELECT * FROM tblSurveyCluster b, Cluster c WHERE b.ClusterID = c.PKCluster AND b.SurveyID ="+ iSurveyID
		+ " ORDER BY " ;
		if(sortType == 1){
			query = query + "ClusterName";
		}else{
			query = query + "ClusterName";
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
				votblSurveyCluster vo = new votblSurveyCluster();
				vo.setClusterName(rs.getString("ClusterName"));
				vo.setClusterID(rs.getInt("PKCluster"));
		
				v.add(vo);			
			}

		}catch(SQLException SE)
		{
			System.out.println("SurveyCluster.java - getSurveyCluster - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	
	public Vector getSurveyCluster(int iSurveyID) throws SQLException, Exception {
		return getSurveyCluster(iSurveyID, 0, 1);
	}
	
	public boolean getUseCluster(int iSurveyID) throws SQLException, Exception{
		boolean isUseCluster = false;
		int useCluster = 0;
		
		String query = "SELECT useCluster FROM  tblSurvey WHERE SurveyID = "+ iSurveyID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()){
				useCluster = rs.getInt("useCluster");
			}
			if(useCluster==1) isUseCluster = true;

		} catch(SQLException SE){
			System.out.println("SurveyCluster.java - getUseCluster - "+SE.getMessage());
		} finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return isUseCluster;
	}
	
}
