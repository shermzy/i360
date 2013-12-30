package CP_Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*; 
import java.sql.*;
import java.io.*;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;

import jxl.*;
import jxl.write.WriteException;
import jxl.read.biff.BiffException;

public class DeleteDatabase {
	Database db;
	/**
	 * A file object for the input workbook.
	 */
	private File inputWorkbook;

	private WorkbookSettings ws;
	private Workbook w1;
	
	public DeleteDatabase() 
	{
		db = new Database();
	}
	
	public boolean deleteRecord(Vector v) throws SQLException, Exception {
		
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted = false;
		
		for (int i = 0; i < v.size(); i++) 
		{
			String [] str = (String[])v.elementAt(i);
			String sql = "Delete FROM "+str[0] ;
			
			if(!str[1].equals("") && str[1] != null)
				sql += " WHERE "+ str[1]+" NOT IN ("+str[2]+") " ;
			
			try	
			{

				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0)
					bIsDeleted=true;
			}
				
			catch(Exception E)
			{
		        System.err.println("DeleteDatabase.java - deleteRecord - " + E);
			}
			
			finally
	    	{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


	    	}
	    	
        }
		
		return bIsDeleted;
	
	}
	
	public String getFKOrg(String ColumnName, String TableName, String OrgName) throws SQLException {
		
		String sql = "SELECT * FROM "+TableName+" WHERE "+ ColumnName +" IN ("+ OrgName + ")";
		
		
		String FKOrg = "";
		int count = 0;
		
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
        	
        	while(rs.next()) {
    			if(count != 0) 
    				FKOrg += ",";
    			
    			FKOrg = FKOrg + Integer.toString(rs.getInt(1));
    			count++;
    		}
            
        }
        catch(Exception E) 
        {
            System.err.println("DeleteDatabase.java - getFKOrg - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
	
		return FKOrg;
	}

	public void InitializeExcel(String fileName) throws IOException, WriteException, BiffException {	
			
		inputWorkbook = new File(fileName);
		
		ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));
		
		w1 = Workbook.getWorkbook(inputWorkbook);
	}


	public Vector ImportPatches(String file_Name) throws IOException, WriteException, BiffException, Exception {							
		
		InitializeExcel(file_Name);
		
		Vector v = new Vector();
		
		Sheet [] Sh = w1.getSheets();
	
		int column= 0;
		int row = 0;
	
		Cell content = Sh[0].getCell(column, row++);		
		String fileName = content.getContents();
		
		int count = 0;
		String FKOrg = "";
		
		while(!fileName.equals("") && row < Sh[0].getRows())
		{
			content 		= Sh[0].getCell(column++, row);
			String sTable = content.getContents().trim();
			content 		= Sh[0].getCell(column++, row);
			String sColumn = content.getContents().trim();
			content 		= Sh[0].getCell(column, row);
			String sID = content.getContents().trim();
			
			row++;
			String [] files = new String[3];
			files[0] = sTable;
			files[1] = sColumn;
			
			if(sID.startsWith("<FKOrg>"))
				files[2] = FKOrg;
			else 
				files[2] = sID;
			
			if(row < Sh[0].getRows()) {
				column = 0;
				content = Sh[0].getCell(column, row);
				fileName 	= content.getContents();
			}
			
			if(count == 0) {
				FKOrg = getFKOrg(sColumn, sTable, sID);
			} else {
				v.add(files);
			}
			
			count++;
		}

		return v;
	}
	
	public static void main(String [] args) throws SQLException, Exception {
		DeleteDatabase upd = new DeleteDatabase();
		
		Vector v = upd.ImportPatches("C:\\Documents and Settings\\yuni\\Desktop\\Projects\\Tables.xls");
		
		for(int i = 0; i<v.size(); i++) {
			String [] files = (String[])v.elementAt(i);
			System.out.println(files[0] + "---" + files[1]);
		}
		
		upd.deleteRecord(v);
		
		System.out.println("OK");
	}
	
}
