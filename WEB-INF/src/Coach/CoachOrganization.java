/*
 * Author: Dai Yong
 * June 2013
 */
package Coach;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblOrganization;

public class CoachOrganization {

	
	public Vector<votblOrganization> getAllOrganizations() 
	{
		Vector<votblOrganization> v=new Vector<votblOrganization>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
    	String sSQL = "SELECT * from tblOrganization order by OrganizationName";
		try {
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sSQL);

			   while(rs.next())
	            {
	            	votblOrganization vo = new votblOrganization();
	            	vo.setEmailNom(rs.getString("EmailNom"));
	            	vo.setEmailNomRemind(rs.getString("EmailNomRemind"));
	            	vo.setEmailPart(rs.getString("EmailPart"));
	            	vo.setEmailPartRemind(rs.getString("EmailPartRemind"));
	            	vo.setExtraModule(rs.getInt("ExtraModule"));
	            	vo.setFKCompanyID(rs.getInt("FKCompanyID"));
	            	vo.setNameSequence(rs.getInt("NameSequence"));
	            	vo.setOrganizationCode(rs.getString("OrganizationCode"));
	            	vo.setOrganizationLogo(rs.getString("OrganizationLogo"));
	            	vo.setOrganizationName(rs.getString("OrganizationName"));
	            	vo.setPKOrganization(rs.getInt("PKOrganization"));
	            	v.add(vo);
	            }
		} catch (SQLException SE) {
			System.err.println("CoachOrganization.java - getAllOrganizations - "+SE.getMessage());
		}
		finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
			return v;
	}
	
}
