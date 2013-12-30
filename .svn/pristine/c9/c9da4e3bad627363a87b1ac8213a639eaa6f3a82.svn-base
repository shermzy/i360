package CP_Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblPurpose;


public class Purpose {

	/**
	 * get All Purpose
	 * @return
	 */
	public Vector getAllPurpose() {
		String query ="SELECT * FROM tblPurpose WHERE PurposeID !=10 ";
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        Vector v = new Vector();
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
      
        	while(rs.next())
            {
        		votblPurpose vo = new votblPurpose();
        		vo.setPurposeID(rs.getInt("PurposeID"));
        		vo.setPurposeName(rs.getString("PurposeName"));
        		
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("Purpose.java - getAllPurpose - " + E);
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
