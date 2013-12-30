package CP_Classes;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Locale;

import CP_Classes.common.ConnectionBean;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class ImportTranslation {
	
	private Database db;
	
	public ImportTranslation()
	{
		db = new Database();
	}
	
	private File inputWorkbook;
	private WorkbookSettings ws;
    private Workbook w1;
    
    /**
	 * Initialize the excel workbook.
	 */
	public void InitializeExcel(String fileName) throws IOException, WriteException, BiffException {	
			
		inputWorkbook = new File(fileName);
		
		ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));
		
		w1 = Workbook.getWorkbook(inputWorkbook);
	}

	public void Import(String fileName) throws IOException, WriteException, BiffException, Exception {}
	
	public int Import(int language, String fileName, int sheet, String sql, int pkCol, int transCol) 
					throws WriteException, BiffException, IOException, SQLException
	{
		InitializeExcel(fileName);		
		Sheet [] Sh = w1.getSheets();
		
		int c = pkCol;
		int r = 1;
		int totalRow = 0;
		
		Connection con = null;
		PreparedStatement ps = null;
		
		
		try
		{
			con=ConnectionBean.getConnection();
			ps = con.prepareStatement(sql);

		}
		catch(Exception E)
		{
            System.err.println("ImportTranslation.java - Import - " + E);
		}
		finally
        {

			ConnectionBean.closePStmt(ps); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		Cell content 	= Sh[sheet].getCell(c, r);
		String word = db.SQLFixer(content.getContents().trim());
		
		String sSQL = "";
		
		while(word != null && !word.equals("") && r < Sh[sheet].getRows())
		{			
			c += transCol;
			content 	= Sh[sheet].getCell(c++, r);
			String trans = db.SQLFixer(content.getContents().trim());
			
			sSQL = "UPDATE TB_Translation SET IndoText = '"+ trans + "' WHERE EngText = '" + word + "'";
			
			//ps.setString(1, word);
			//ps.setString(2, trans);
			try
			{
				ps = con.prepareStatement(sSQL);
				ps.executeUpdate();
				totalRow++;
			}
			catch(Exception E)
			{
	            System.err.println("ImportTranslation.java - Import - " + E);
			}
			finally
	        {

				ConnectionBean.closePStmt(ps); //Close statement
				ConnectionBean.close(con); //Close connection

	        }
			
			r++;
			
			if(r < Sh[sheet].getRows()) {
				c = 0;
			
				content = Sh[sheet].getCell(c, r);
				word	= content.getContents();
			}
		}
		
		w1.close();
		
		return totalRow;
	}
	
	
	public int ImportNew(int language, String fileName, int sheet, String sql, int pkCol, int transCol) 
			throws WriteException, BiffException, IOException, SQLException
	{	
		InitializeExcel(fileName);		
		Sheet [] Sh = w1.getSheets();
		
		int c = pkCol;
		int r = 1;
		int totalRow = 0;
		
		Cell content 	= Sh[sheet].getCell(c, r);
		String strEng	= content.getContents();
		

		Connection con = null;
		PreparedStatement ps = null;
		
		
		try
		{
			con=ConnectionBean.getConnection();
			ps = con.prepareStatement(sql);

		}
		catch(Exception E)
		{
            System.err.println("ImportTranslation.java - ImportNew - " + E);
		}
		finally
        {

			ConnectionBean.closePStmt(ps); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		while(strEng != null && !strEng.equals("") && r < Sh[sheet].getRows())
		{			
			c += transCol;
			content 	= Sh[sheet].getCell(c++, r);
			String name = db.SQLFixer(content.getContents().trim());
			
			
			try
			{
				ps.setString(1, strEng);
				ps.setString(2, name);
				
				ps.executeUpdate();
				totalRow++;
			}
			catch(Exception E)
			{
	            System.err.println("ImportTranslation.java - Import - " + E);
			}
			finally
	        {

				ConnectionBean.closePStmt(ps); //Close statement
				ConnectionBean.close(con); //Close connection

	        }
			
			r++;
			
			if(r < Sh[sheet].getRows()) {
				c = 0;
			
				content = Sh[sheet].getCell(c, r);
				strEng	= content.getContents();
			}
		}
		
		w1.close();
		
		return totalRow;
	}

	
	/*************************************** GUI ************************************************************/
	
	public void ImportGUI(int language, String fileName, int sheet, int exist) 
				throws IOException, WriteException, BiffException, Exception
	{								
		String sql = "Update TB_Translation Set IndoText = ? where EngText = ?";
		int col = 1;
		int totalRow = 0;
		
		if(exist == 0) {
			sql = "Insert into TB_Translation(EngText, IndoText) values (?, ?)";
			col = 1;
			totalRow = ImportNew(language, fileName, sheet, sql, 0, col);
		}else
			totalRow = Import(language, fileName, sheet, sql, 0, col);
		
		System.out.println("GUI - TOTAL " + totalRow + " IMPORTED!");
	}
	
	
	public void ImportCompetency(String filename, int iCompanyID, int iOrgID) throws IOException, WriteException, BiffException, Exception
	{
		InitializeExcel(filename);
		
		int iPKComp = 0;
		
		Sheet [] Sh = w1.getSheets();
		
		Cell hCompName = Sh[0].findCell("CompetencyName");
		Cell hCompName1 = Sh[0].findCell("EnglishCompetencyName");
		Cell hCompDef  = Sh[0].findCell("CompetencyDefinition");
		
		int row = hCompName.getRow();
		row ++;
		
		int cCompName = hCompName.getColumn();
		int cCompName1 = hCompName1.getColumn();
		int cCompDef  = hCompDef.getColumn();
		
		System.out.println("Importing Competencies in Progress ...");
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		while(row < Sh[0].getRows())
		{
			Cell CompName = Sh[0].getCell(cCompName, row);
			Cell CompName1 = Sh[0].getCell(cCompName1, row);
			Cell CompDef  = Sh[0].getCell(cCompDef, row);
			
			String sCompName = db.SQLFixer(CompName.getContents().trim());
			String sCompName1 = db.SQLFixer(CompName1.getContents().trim());
			String sCompDef  = db.SQLFixer(CompDef.getContents().trim());
			
			String sSQL = "SELECT * FROM Competency WHERE CompetencyName1 = '" + sCompName1 + "' AND FKCompanyID = " + iCompanyID;
			sSQL = sSQL + " AND FKOrganizationID = " + iOrgID;

			
			iPKComp = 0;
	  	   	
			try{
	  		   con=ConnectionBean.getConnection();
	  		   st=con.createStatement();
	  		   rs=st.executeQuery(sSQL);
	  		
	  			if(rs.next()) {
	  				iPKComp = rs.getInt("PKCompetency");
	  			}
	  			
	  	   	}catch(Exception ex){
				System.out.println("ImportTranslation.java - ImportCompetency - "+ex.getMessage());
			}
			finally{
				ConnectionBean.closeRset(rs); //Close ResultSet
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
			
			if(iPKComp != 0)
			{
				sSQL = "UPDATE Competency SET ";
				sSQL = sSQL + "CompetencyName = '" + sCompName + "', ";
				sSQL = sSQL + "CompetencyDefinition = '" + sCompDef + "' ";
				sSQL = sSQL + "WHERE PKCompetency = " + iPKComp;
			}
			else
			{	
				//Record does not exist, insert into system library
				sSQL = "INSERT INTO Competency (CompetencyName, CompetencyDefinition, IsSystemGenerated, FKCompanyID, FKOrganizationID) ";
				sSQL = sSQL + "VALUES ('"+sCompName+"', '"+sCompDef+"', 0, "+iCompanyID+", "+iOrgID+")";
			}
			
			boolean bIsAdded = false;
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess=st.executeUpdate(sSQL);
				
				if(iSuccess!=0)
					bIsAdded=true;

			}
			catch(Exception E)
			{
	            System.err.println("ImportTranslation.java - Import Competency- " + E);
			}
			finally
	        {
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection

	        }
			
			row++;
		}
		System.out.println("Importing Competencies Completed");
	}
	
	
	public void ImportBehaviour(String filename, int iCompanyID, int iOrgID) throws IOException, WriteException, BiffException, Exception
	{
		InitializeExcel(filename);
		
		//int iPKComp = 0;
		int iPKBehv = 0;
		
		PreparedStatement ps = null;
		
		Sheet [] Sh = w1.getSheets();
		
		Cell hBehvName1 = Sh[0].findCell("KeyBehaviour1");
		Cell hBehvName  = Sh[0].findCell("KeyBehaviour");
		
		int row = hBehvName1.getRow();
		row ++;
		
		int cBehvName1 = hBehvName1.getColumn();
		int cBehvName = hBehvName.getColumn();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		System.out.println("Importing KB in Progress ...");
		while(row < Sh[0].getRows())
		{
			Cell BehvName1 = Sh[0].getCell(cBehvName1, row);
			Cell BehvName  = Sh[0].getCell(cBehvName, row);
			
			String sBehvName1 = db.SQLFixer(BehvName1.getContents().trim());
			String sBehvName = db.SQLFixer(BehvName.getContents().trim());
			
			String sSQL = "SELECT * FROM KeyBehaviour WHERE KeyBehaviour1 = '" + sBehvName1 + "'";
			iPKBehv = 0;
	  	   	
			try{
	  		   con=ConnectionBean.getConnection();
	  		   st=con.createStatement();
	  		   rs=st.executeQuery(sSQL);
	  		
	  			if(rs.next()) {
	  				iPKBehv = rs.getInt("PKKeyBehaviour");
	  			}
	  			
	  	   	}catch(Exception ex){
				System.out.println("ImportTranslation.java - ImportBehaviour - "+ex.getMessage());
			}
			finally{
				ConnectionBean.closeRset(rs); //Close ResultSet
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
			
			// If KB exists, perform Update
			if (iPKBehv != 0)
			{
			
				sSQL = "UPDATE KeyBehaviour SET ";
				sSQL = sSQL + "KeyBehaviour = '" + sBehvName + "' ";
				sSQL = sSQL + "WHERE PKKeyBehaviour = " + iPKBehv;
			}
			
			boolean bIsAdded = false;
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess=st.executeUpdate(sSQL);
				
				if(iSuccess!=0)
					bIsAdded=true;

			}
			catch(Exception E)
			{
	            System.err.println("ImportTranslation.java - Import Behvaiour- " + E);
			}
			finally
	        {
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection

	        }
			
			row++;
		}

		System.out.println("Importing KB Completed");
	}
	
	
	public static void main (String [] args)throws SQLException, Exception 
	{	
		ImportTranslation IT = new ImportTranslation();		
		
		//IT.ImportBehaviour("D:\\4 - Documents\\PCC\\Translation\\i360 KB Import KOR.xls", 1, 1);

		IT.ImportGUI(1,"C:\\update trans.xls",0,1);
		//IT.ImportGUI(1,"D:\\3 - Documents\\PCC\\Translation\\GUI KOR.XLS",0,0);
	}
}