package CP_Classes;

import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.votblEvent;

import jxl.read.biff.BiffException;
import jxl.write.*; 
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.Label;
import jxl.write.WriteException;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;


/**
 * This class implements all the operations for ExcelRaterResults in Excel.
 * It implements JExcelAPI Interface.
 */ 
public class ExcelEventViewer
{
	/**
	 * Declaration of new object of class Setting.
	 */
	private Setting ST;
	
	/**
	 * Declaration of new object of class Database.
	 */
	private Database db;
	
	
	/**
	 * Declaration of new object of class User.
	 */
	private User_Jenty U;
	
	/**
	 * Declaration of new object of class EventViewer.
	 */
	private EventViewer EV;
	
	private String fileName;
	private String companyName;
	private String orgName;	
	private int row;
	
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;	
	private WritableFont fontBold, fontFace;
	private WritableWorkbook workbook;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat bordersData3;
	private WritableCellFormat No_Borders;
	private File outputWorkBook, inputWorkBook;
	
	/**
	 * Create new instance of ExcelEventViewer object.
	 */
	public ExcelEventViewer() {
		db = new Database();
		U = new User_Jenty();
		EV = new EventViewer();
		ST = new Setting();	
		row = 0;
	}
	
	/**
	 * FROM WHICH JSP?
	 */
	/**
	 * Retrieves Event Details.
	 */
	public Vector getAllRecords(int userType) throws SQLException, Exception {
	
		String query = "";
		
		query = query + "Select * from tblEvent";
		
		if(!companyName.equals("All") && orgName.equals("All")) 
			query = query + " where CompanyName = '" + companyName + "'";

		else if(!orgName.equals("All")) 
			query = query + " where OrganizationName = '" + orgName + "'";
		
		if(userType != 1)
			query += " and LoginName != 'sa'";
		
		query = query + " order by DeletedDate";

		
		Vector v = new Vector();
    	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
        	
            while(rs.next())
            {
            	votblEvent vo = new votblEvent();
            	
            	
            	vo.setDescription(rs.getString("Description"));
            	vo.setDeletedDate(rs.getDate("DeletedDate"));
            	vo.setEventAction(rs.getString("EventAction"));
            	vo.setItem(rs.getString("Item"));
            	vo.setLoginName(rs.getString("LoginName"));
            	
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("ExcelEventViewer.java - getAllRecords - " + E);
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
	 * Retrieves username based on name sequence.
	 * @param nameSeq
	 * @param targetID
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String UserName(int nameSeq, int targetID) throws SQLException, Exception {
		String name = "";
		
		String query = "SELECT FamilyName, GivenName FROM [User] WHERE PKUser = " + targetID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
        	if(rs.next()) {
    			String familyName = rs.getString(1);
    			String GivenName = rs.getString(2);
    				
    			if(nameSeq == 0)
    				name = familyName + " " + GivenName;
    			else
    				name = GivenName + " " + familyName;
    			
    			
    		}
    			
        }
        catch(Exception E) 
        {
            System.err.println("ExcelEventViewer.java - UserName - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
			
		return name;
	}
	
	/**
	 * Initialize and set all excel application and alignment.
	 */
	public void write() throws IOException, WriteException, BiffException
	{
		String output = ST.getReport_Path() + "\\" + fileName;
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(ST.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("Event Details");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
		cellBOLD = new WritableCellFormat(fontBold);
		
		bordersData1 = new WritableCellFormat(fontFace);
		bordersData1.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData1.setAlignment(Alignment.CENTRE);
		bordersData1.setWrap(true);
		bordersData1.setVerticalAlignment(VerticalAlignment.CENTRE);
					
		bordersData2 = new WritableCellFormat(fontFace);
		bordersData2.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData2.setWrap(true);
		bordersData2.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		bordersData3 = new WritableCellFormat(fontBold);
		bordersData3.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData3.setAlignment(Alignment.CENTRE);
		bordersData2.setWrap(true);
		bordersData3.setBackground(Colour.GRAY_25);
		

		No_Borders = new WritableCellFormat(fontBold);
		No_Borders.setBorder(Border.NONE, BorderLineStyle.NONE);
		
		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
			
	}
	
	/**
	 * Writes header on excel.
	 */
	public void Header() 
		throws IOException, WriteException, SQLException, Exception
	{		
		
		Label label = new Label(0, 0, "Event Details",cellBOLD);
		if (ST.LangVer == 2)
			label = new Label(0, 0, "DETIL KEJADIAN",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 3, 0);
				
		row = 2;
		writesheet.setColumnView(0, 4);
		writesheet.setColumnView(1, 10);
		writesheet.setColumnView(2, 10);
		writesheet.setColumnView(3, 38);
		writesheet.setColumnView(4, 12);
		writesheet.setColumnView(5, 12);
		
		if(companyName != "") {
			label= new Label(0, row, "Company:",cellBOLD);
			if (ST.LangVer == 2)
				label= new Label(0, row, "Nama Perusahaan:",cellBOLD);
			writesheet.addCell(label); 								

			label= new Label(3, row, companyName, No_Borders);
			writesheet.addCell(label); 
			
			row += 2;
		}
		
		if(orgName != "") {
	 		label= new Label(0, row, "Organisation:",cellBOLD);
	 		if (ST.LangVer == 2)
	 			label= new Label(0, row, "Nama Organisasi:",cellBOLD);
			writesheet.addCell(label); 
				
			label= new Label(3, row, orgName ,No_Borders);
			writesheet.addCell(label); 
			row += 2;
		}
	}
	
	/**
	 * Writes results on excel.
	 */
	public void printResults(int userType) throws IOException, WriteException, SQLException, Exception
	{
		int c = 0;		
		row++;
		int no = 0;
		
		if (ST.LangVer == 1){
			label = new Label(c++, row, "No", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Action", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Item", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Description", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Login Name", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Action Date", bordersData3); 
			writesheet.addCell(label);
		}
		else if (ST.LangVer == 2){
			label = new Label(c++, row, "No", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Aksi", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Barang", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Deskripsi", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Nama Login", bordersData3); 
			writesheet.addCell(label);
			label = new Label(c++, row, "Tanggal Aksi", bordersData3); 
			writesheet.addCell(label);
		}
		Vector rsEvent = getAllRecords(userType);

		SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
		
		for(int i=0; i<rsEvent.size(); i++) {
			votblEvent vo = (votblEvent)rsEvent.elementAt(i);
			
			c = 0;
			row++;
			no++;
			
			label= new Label(c++, row, Integer.toString(no), bordersData1);
			writesheet.addCell(label);
			
			label= new Label(c++, row, vo.getEventAction(),bordersData2);
			writesheet.addCell(label); 			
			
			label= new Label(c++, row, vo.getItem(),bordersData2);
			writesheet.addCell(label);
			
			label= new Label(c++, row, vo.getDescription(),bordersData2);
			writesheet.addCell(label);
			
			label= new Label(c++, row, vo.getLoginName(),bordersData2);
			writesheet.addCell(label);
			
			label= new Label(c++, row, day_view.format(vo.getDeletedDate()),bordersData1);
			writesheet.addCell(label);						
		}

	}
	
	
	/**
	 * Method called from JSP.
	 */
	public void WriteToReport(String compName, String orgName, int pkUser, int userType, String fileName) 
		throws IOException, WriteException, SQLException, Exception
	{
			this.fileName = fileName;
			this.companyName = compName;
			this.orgName = orgName;
						
			write();
			Header();	
			printResults(userType);
				
			workbook.write();
			workbook.close(); 
			
			String [] UserInfo = U.getUserDetail(pkUser);
		
			try {
				EV.addRecord("Print", "Event Details", "Print Event Details", UserInfo[2], UserInfo[11], UserInfo[10]);
			} catch(SQLException SE) {
				System.out.println(SE.getMessage());
			}
	}

	
	public static void main (String[] args)throws SQLException, Exception
	{
		ExcelEventViewer Rpt = new ExcelEventViewer();

		Rpt.WriteToReport("Test", "test", 0, 0 , "event viewer.xls"); // kb
		
			
	}
}