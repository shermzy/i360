package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblRatingTask;
import CP_Classes.vo.votblScale;
import CP_Classes.vo.votblScaleValue;

/**
 * This class implements all the operations for Rating Scale, which is to be used in System Libraries, Survey, and Report..
 */
public class RatingScale
{
	
	/**
	 * Declaration of new object of class User.
	 */
	private User_Jenty U;
	
	/**
	 * Declaration of new object of class EventViewer.
	 */
	private EventViewer EV;
	
	/**
	 * Bean Variable to store the rating scale id.
	 */
	public int RS;		// rating scale
	
	/**
	 * Bean Variable to store the rating scale type.
	 */
	public String RSType;
	
	/**
	 * Bean Variable to store the rating scale id.
	 */
	public int ScaleID;
	
	/**
	 * Bean Variable for sorting purposes. Total Array depends on total SortType.
	 * 0 = ASC
	 * 1 = DESC
	 */
	public int Toggle [];	// 0=asc, 1=desc
	
	/**
	 * Bean Variable to store the Sorting type.
	 */
	public int SortType;

	/**
	 * Creates a new intance of Rating Scale object.
	 */
	public RatingScale() {
	
		U = new User_Jenty();
		EV = new EventViewer();
		
		Toggle = new int [4];
		
		for(int i=0; i<4; i++)
			Toggle[i] = 0;
			
		SortType = 1;
	}

	/**
	 * Store Bean Variable toggle either 1 or 0.
	 */
	public void setToggle(int toggle) {
		Toggle[SortType - 1] = toggle;
	}

	/**
	 * Get Bean Variable toggle.
	 */
	public int getToggle() {
		return Toggle [SortType - 1];
	}	

	/**
	 * Store Bean Variable Sort Type.
	 */
	public void setSortType(int SortType) {
		this.SortType = SortType;
	}

	/**
	 * Get Bean Variable SortType.
	 */
	public int getSortType() {
		return SortType;
	}	

	/**
	 * Store Bean Variable of Rating Scale ID.
	 */
	public void setRS(int RS) {
		this.RS = RS;
	}
	
	/**
	 * Get Bean Variable of Rating Scale ID.
	 */
	public int getRS() {
		return RS;
	}
	
	/**
	 * Store Bean Variable of Rating Scale ID.
	 */
	public void setScaleID(int ScaleID) {
		this.ScaleID = ScaleID;
	}
	
	/**
	 * Get Bean Variable of Rating Scale ID.
	 */
	public int getScaleID() {
		return ScaleID;
	}

	/**
	 * Store Bean Variable of Rating Scale Type.
	 */
	public void setRSType(String RSType) {
		this.RSType = RSType;
	}
	
	/**
	 * Get Bean Variable of Rating Scale Type.
	 */
	public String getRSType() {
		return RSType;
	}

	/**
	 * Add a new record to the Rating Scale table (tblScale).
	 *
	 * Parameters:
	 *		desc 		 - the description of the Rating Scale.
	 *		type 		 - proficiency / importance.
	 *		defaultValue - if it is set default, then it will appear as 1st record in creating survey (add Rating Scale).
	 *		companyID	 - company ID.
	 *		orgID		 - organization ID.
	 *		scaleRange	 - range of 5 to 10. (ex. scale range 5 = 0-5)
	 *
	 * Returns:
	 *		a boolean that represents the success of inserting to the database.
	 */
	public boolean addtblScale(String desc, String type, int defaultValue, int companyID, int orgID, int scaleRange, int pkUser, int userType) throws SQLException, Exception {
		int isSysGenerated = 0;
		
		//if(companyID == 1 && orgID == 1)
		if(userType == 1)
			isSysGenerated = 1;

		String sql = "Insert into tblScale (ScaleDescription, ScaleType, ScaleDefault, FKCompanyID, FKOrganizationID, ";
		sql = sql + "ScaleRange, IsSystemGenerated) values ('" + desc + "','" + type + "'," + defaultValue + "," + companyID;
		sql = sql + "," + orgID + "," + scaleRange + ", " + isSysGenerated + ")";
			
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
			
		db.closeDB();
			*/

		Connection con = null;
		Statement st = null;
		boolean bIsAdded=false;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			
			if(iSuccess!=0)
			bIsAdded=true;


		}catch(Exception ex){
			System.out.println("RatingScale.java - addtblScale - "+ex.getMessage());
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		
		String [] UserInfo = U.getUserDetail(pkUser);		
		try {
			EV.addRecord("Insert", "Rating Scale", desc, UserInfo[2], UserInfo[11], UserInfo[10]);
		} catch(SQLException SE) {
			System.out.println(SE.getMessage());
		}
			
		return bIsAdded;
	}

	/**
	 * Add a new record of the scale value to tblScaleValue.
	 *
	 * Parameters:
	 *		desc 	  - description of the value (very poor / poor / good / etc).
	 *		scaleID   - the foreign key of Scale ID that this value belongs to.
	 *		lowValue  - the lowest range of the value.
	 *		highValue - the highest range of the value.
	 *
	 *		ex: 1-2(very poor), means that lowValue is 1, highValue is 2.
	 *
	 * Returns:
	 *		a boolean that represents the success of inserting to the database.
	 */
	public boolean addtblScaleValue(String desc, int scaleID, int lowValue, int highValue) throws SQLException, Exception {
		String sql = "Insert into tblScaleValue (ScaleID, LowValue, HighValue, ScaleDescription) values ('" + scaleID + "'," +
					lowValue + "," + highValue + ",'" + desc + "')";
		/*		
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
			
		ps.executeUpdate();
			
		db.closeDB();
			*/
		Connection con = null;
		Statement st = null;
		boolean bIsAdded=false;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			
			if(iSuccess!=0)
			bIsAdded=true;


		}catch(Exception ex){
			System.out.println("RatingScale.java - addtblScaleValue - "+ex.getMessage());
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return bIsAdded;
	}
	
	/**
	 * Edit a record in the Rating Scale Value table (tblScaleValue).
	 *
	 * Parameters:
	 *		desc 	  - the description of the value.
	 *		valueID   - the primary key of the value..
	 *		lowValue  - lowest range.
	 *		highValue - highest range.
	 *
	 * Returns:
	 *		a boolean that represents the success of editing to the database.
	 */
	public boolean edittblScaleValue(String desc, int valueID, int lowValue, int highValue) throws SQLException, Exception {
		String sql = "Update tblScaleValue Set LowValue = " + lowValue +
					", HighValue = " + highValue + ", ScaleDescription = '" + desc + "' where ValueID = " + valueID;
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		return true;
		*/
		Connection con = null;
		Statement st = null;
		boolean bIsUpdated=false;
		try{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
		
			if(iSuccess!=0)
			bIsUpdated=true;

		}catch(Exception ex){
			System.out.println("RatingScale.java - edittblScaleValue - "+ex.getMessage());
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return bIsUpdated;
	}
	
	/**
	 * Edit a record in the Rating Scale table (tblScale).
	 *
	 * Parameters:
	 *		ScaleID   - the primary key of Rating Scale.
	 *		scaleType - type of Rating Scale (proficiency / importance).
	 *		desc	  - Scale description.
	 *		defValue  - defaultValue (only useful in create/edit survey).
	 *
	 * Returns:
	 *		a boolean that represents the success of editing to the database.
	 */
	public boolean edittblScale(int scaleID, String scaleType, String desc, int defValue, int range, int pkUser) throws SQLException, Exception {
		String oldStatement = ScaleDesc(scaleID);
		
		String sql = "Update tblScale Set ScaleRange = " + range + ", ScaleType = '" + scaleType +
					"', ScaleDescription = '" + desc + "', ScaleDefault = " + defValue + " where ScaleID = " + scaleID;
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
			*/
		Connection con = null;
		Statement st = null;
		boolean bIsUpdated=false;
		try{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
		
			if(iSuccess!=0)
			bIsUpdated=true;

		}catch(Exception ex){
			System.out.println("RatingScale.java - edittblScale - "+ex.getMessage());
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		
		String [] UserInfo = U.getUserDetail(pkUser);		
		try {
			EV.addRecord("Update", "Rating Scale", "(" + oldStatement + ") - (" + desc + ")", UserInfo[2], UserInfo[11], UserInfo[10]);
		} catch(SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsUpdated;
	}

	/**
	 * Delete an existing record from the Rating Scale table (tblScale).
	 *
	 * Parameters:
	 *		ScaleID - the primary key of Rating Scale to determine which record to be deleted.
	 *
	 * Returns:
	 *		a boolean that represents the success of deletion process.
	 */
	public boolean deletetblScale(int ScaleID, int pkUser)  throws SQLException, Exception {
		String oldStatement = ScaleDesc(ScaleID);
		
		String sql = "Delete from tblScale where ScaleID = " + ScaleID;
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
			*/
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted=false;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();		
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsDeleted=true;

		}catch(Exception ex){
			System.out.println("RatingScale.java - deletetblScale - "+ex.getMessage());
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		String [] UserInfo = U.getUserDetail(pkUser);		
		try {
			EV.addRecord("Delete", "Rating Scale", oldStatement, UserInfo[2], UserInfo[11], UserInfo[10]);
		} catch(SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsDeleted;
	}
	
	/**
	 * Delete an existing record from the Rating Scale Value table (tblScaleValue).
	 *
	 * Parameters:
	 *		valueID - the primary key of Rating Scale Value to determine which record to be deleted.
	 *
	 * Returns:
	 *		a boolean that represents the success of deletion process.
	 */
	public boolean deletetblScaleValue(int valueID)  throws SQLException, Exception {
		String sql = "Delete from tblScaleValue where ValueID = " + valueID;
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		return true;
		*/
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted=false;
		try{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsDeleted=true;


		}catch(Exception ex){
			System.out.println("RatingScale.java - deletetblScaleValue - "+ex.getMessage());
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return bIsDeleted;
	}
	
	
	/**
	 * Delete all records from the Rating Scale Value table (tblScaleValue) that is under the particular ScaleID.
	 *
	 * Parameters:
	 *		scaleID - the primary key of Rating Scale to determine which record to be deleted.	 
	 */
	public boolean deletetblScaleValuebyScale(int scaleID)  throws SQLException, Exception {
		String sql = "Delete from tblScaleValue where ScaleID = " + scaleID;
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
		*/
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted=false;
		try{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsDeleted=true;


		}catch(Exception ex){
			System.out.println("RatingScale.java - deletetblScaleValuebyScale - "+ex.getMessage());
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return bIsDeleted;
		
	}
	

	/**
	 * Retrieves all Rating Scale from tblScale.
	 */
	public Vector getAllRecord() throws SQLException, Exception {
		String query = "Select * from tblScale order by ScaleType, ScaleDescription";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		*/
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		while(rs.next()){
			votblScale  vo =new votblScale();
			vo.setFKCompanyID(rs.getInt("FKCompanyID"));
			vo.setFKOrganizationID(rs.getInt("FKOrganizationID"));
			vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
			vo.setScaleDefault(rs.getInt("ScaleDefault"));
			vo.setScaleDescription(rs.getString("ScaleDescription"));
			vo.setScaleID(rs.getInt("ScaleID"));
			vo.setScaleRange(rs.getInt("ScaleRange"));
			vo.setScaleType(rs.getString("ScaleType"));
			
			v.add(vo);
		}
		}catch(Exception ex){
			System.out.println("RatingScale.java - getAllRecord - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	
	/**
	 * Retrieves the Rating Scale ID based on CompanyID, OrganizationID, Scale Description, and Type.
	 * This is currently used to identify the primary key once the new rating scale is added, so that we can directly add the scale value under this ID.
	 */
	public int ScaleID(int compID, int orgID, String statement, String scaleType) throws SQLException, Exception {
		String query = "Select ScaleID from tblScale ";
		query = query + "where ScaleDescription = '" + statement + "' and ScaleType = '" + scaleType;
		query = query + "' and FKCompanyID = " + compID + " and FKOrganizationID = " + orgID;
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
			
		if(rs.next())
			return rs.getInt(1);
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		if(rs.next())
			return rs.getInt(1);
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - ScaleID - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return 0;
	}
	
	/**
	 * Retrieves all records based on range, company ID, and organizationID.
	 *
	 * Parameters:
	 *		range - optional.
	 */
	public Vector FilterRecord(int range, int companyID, int orgID) throws SQLException, Exception {
		String query = "";
		Vector v=new Vector();
		if(range == 0) {
			query = query + "SELECT ScaleID, ScaleDescription, ScaleType, ScaleDefault, ScaleRange, Description FROM tblScale ";
			query = query + " inner join tblOrigin on tblScale.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "WHERE (IsSystemGenerated = 1) or (FKCompanyID = " + companyID + " AND FKOrganizationID = " + orgID;
			query = query + ") order by ";
		}else {
			query = query + "SELECT ScaleID, ScaleDescription, ScaleType, ScaleDefault, ScaleRange, Description FROM tblScale ";
			query = query + " inner join tblOrigin on tblScale.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "WHERE (IsSystemGenerated = 1 and ScaleRange = " + range;
			query = query + ") or (FKCompanyID = " + companyID + " AND FKOrganizationID = " + orgID;
			query = query + " and ScaleRange = " + range + ") order by ";
		}
			
		if(SortType == 1)
			query = query + "ScaleDescription";
		else if(SortType == 2)
			query = query + "ScaleType";
		else if(SortType == 3)
			query = query + "ScaleDefault";
		else
			query = query + "Description";

		if(Toggle[SortType - 1] == 1)
			query = query + " DESC";
		/*		
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

//ScaleID, ScaleDescription, ScaleType, ScaleDefault, ScaleRange, Description
		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		while(rs.next()){
			votblScale  vo =new votblScale();
			
			vo.setScaleDefault(rs.getInt("ScaleDefault"));
			vo.setScaleDescription(rs.getString("ScaleDescription"));
			vo.setScaleID(rs.getInt("ScaleID"));
			vo.setScaleRange(rs.getInt("ScaleRange"));
			vo.setScaleType(rs.getString("ScaleType"));
			vo.setDescription(rs.getString("Description"));
		
			v.add(vo);
		}
			
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - FilterRecord - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	
	/**
	 * Get total Rating Scale in the table.
	 */
	public int getTotalRecord() throws SQLException, Exception {
		String query = "Select count(*) tblScale";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
			
		if(rs.next())
			return rs.getInt(1);
*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		
		if(rs.next())
			return rs.getInt(1);
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - getTotalRecord - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return 0;
	}
	
	/**
	 * Check whether the Rating Scale belonged to System Generated of User Generated.
	 */
	public int CheckSysLibRatingScale(int scaleID) throws SQLException, Exception {
		int check = 0;
		
		String query = "SELECT IsSystemGenerated FROM tblScale  ";
		query = query + "WHERE ScaleID = " + scaleID;
/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
								
		if(rs.next())
			check = rs.getInt(1);
			*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		
		if(rs.next())
			check = rs.getInt(1);
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - CheckSysLibRatingScale - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		return check;
	}
	
	/**
	 * Check the existance of the particular Rating Scale in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int CheckRSExist(String desc, String type, int defaultValue, int companyID, int orgID, int scaleRange) throws SQLException, Exception {
		int pkComp = 0;
		
		String query = "SELECT * FROM tblScale  ";
		query = query + "WHERE ScaleDescription = '" + desc + "' AND ScaleType = '" + type + "' and ";
		query = query + "ScaleDefault = " + defaultValue + " and ScaleRange = " + scaleRange;
		query = query + " and ((FKCompanyID = " + companyID + " and FKOrganizationID = " + orgID;
		query = query + ") or (IsSystemGenerated = 1))";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
			
		if(rs.next())
			pkComp = 1;
			*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		
		if(rs.next())
			pkComp = 1;
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - CheckRSExist - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return pkComp;
	}
	
	/**
	 * Check the existance of the particular Rating Scale in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int CheckRatingScaleExist(String desc, String type, int defaultValue, int companyID, int orgID, int scaleRange, int iScaleID) throws SQLException, Exception {
		int pkComp = 0;
		
		String query = "SELECT * FROM tblScale  ";
		query = query + "WHERE ScaleDescription = '" + desc + "' AND ScaleType = '" + type + "' and ";
		query = query + "ScaleDefault = " + defaultValue + " and ScaleRange = " + scaleRange;
		query = query + " and ((FKCompanyID = " + companyID + " and FKOrganizationID = " + orgID;
		query = query + ") or (IsSystemGenerated = 1)) AND ScaleID != " +iScaleID;
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
			
		if(rs.next())
			pkComp = 1;
			*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next())
				pkComp = 1;
			
		}catch(Exception ex){
			System.out.println("RatingScale.java - CheckRSExist - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return pkComp;
	}
	
	/**
	 * Check the existance of the particular Scale Value in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int CheckScaleExist(String desc, int scaleID, int lowValue, int highValue) throws SQLException, Exception {
		int pkComp = 0;

		String query = "SELECT * FROM tblScaleValue  ";
		query = query + "WHERE ScaleID = " + scaleID + " AND ScaleDescription = '" + desc + "' and ";
		query = query + "LowValue = " + lowValue + " and HighValue = " + highValue;
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
									
		if(rs.next())
			pkComp = 1;
			*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		
		if(rs.next())
			pkComp = 1;
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - CheckScaleExist - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
			
		return pkComp;
	}
	
	/**
	 * Check the existance of the default Rating Scale under the particular scale type in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int checkDefaultExist(String scaleType) throws SQLException, Exception {
		String query = "Select count(*) from tblScale where ScaleType = '" + scaleType + "'";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		if(rs.next())
			return 1;
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		
		if(rs.next())
			return 1;
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - checkDefaultExist - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return 0;
	}	
	
	
	/**
	 * Get the Rating Scale description based on Scale ID.
	 */
	public String ScaleDesc(int scaleID) throws SQLException, Exception {
		String desc = "";
		
		String query = "SELECT * from tblScale where ScaleID = " + scaleID;
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next())
			desc = rs.getString("ScaleDescription");
		
		rs.close();
		db.closeDB();
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		
		if(rs.next())
			desc = rs.getString("ScaleDescription");
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - ScaleDesc - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return desc;
	}	
	
	/**
	 * Get the maximum scale of the Survey
	 *
	 * @param	int SurveyID
	 * @return	int MaxScale
	 */
	public int getMaxScale(int surveyID) throws SQLException {
		String query = "";
		int total = 0;
		

			query = query + "SELECT MAX(tblScale.ScaleRange) AS Result FROM ";
			query = query + "tblScale INNER JOIN tblSurveyRating ON ";
			query = query + "tblScale.ScaleID = tblSurveyRating.ScaleID WHERE ";
			query = query + "tblSurveyRating.SurveyID = " + surveyID;
			/*	
			if(db.con == null)
				db.openDB();
				
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next())
				total = rs.getInt(1);
				*/
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;


			try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next())
				total = rs.getInt(1);;
			
			}catch(Exception ex){
				System.out.println("RatingScale.java - getMaxScale - "+ex.getMessage());
			}
			finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
			}
			return total;
	
	}
	
	public Vector getRecord() throws SQLException, Exception {
		//rs = Database.getRecord("Select * from tblScale where ScaleID = " + ScaleID);
		String query ="Select distinct[ScaleType] from tblScale order by ScaleType";
	
		//ResultSet SType = Database.getRecord("Select distinct[ScaleType] from tblScale order by ScaleType");
		Vector v=new Vector();
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		*/
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
			
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				votblScale  vo =new votblScale();
				vo.setScaleType(rs.getString("ScaleType"));
			
				v.add(vo);
			}
		}catch(Exception ex){
			System.out.println("RatingScale.java - getRecord - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	
	
	public votblScale getRecord(int iScaleID) throws SQLException, Exception {
	
		String query ="Select * from tblScale where ScaleID = " + iScaleID;
		votblScale  vo =new votblScale();
		//ResultSet SType = Database.getRecord("Select distinct[ScaleType] from tblScale order by ScaleType");
	
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		*/
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
			
		
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		if(rs.next()){
			
			
			
			vo.setScaleDefault(rs.getInt("ScaleDefault"));
			vo.setScaleDescription(rs.getString("ScaleDescription"));
			vo.setScaleRange( rs.getInt("ScaleRange"));
			vo.setScaleType(rs.getString("ScaleType"));
		
		}
		}catch(Exception ex){
			System.out.println("RatingScale.java - getRecord - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return vo;
	}
	
	public Vector getRatingScale(int iScaleRange, String sScaleType, int iOrgID) {
		String query2= "";
		
		query2 ="SELECT * FROM tblScale WHERE ScaleRange = "+iScaleRange+" AND ScaleType='"+sScaleType+"' AND IsSystemGenerated = 1 OR ScaleType='"+sScaleType+"' AND FKOrganizationID= "+iOrgID+" AND ScaleRange = "+iScaleRange;
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        Vector v = new Vector();
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query2);
      
        	while(rs.next())
            {
        		votblScale vo = new votblScale();
        		vo.setScaleID(rs.getInt("ScaleID"));
        		vo.setScaleDescription(rs.getString("ScaleDescription"));
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("RatingScale.java - getRatingScale - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return v;
		
	}
	
	public Vector getScaleValue(int iScaleID) {
		Vector v = new Vector();
		//Added ORDER BY part by HA 10/06/08
		
		String query2 = "Select * from tblScaleValue where ScaleID = " + iScaleID;
		query2 += " ORDER BY ValueID";
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query2);
      
        	while(rs.next())
            {
        		votblScaleValue vo = new votblScaleValue();        		
        		vo.setLowValue(rs.getInt(3));
        		vo.setHighValue(rs.getInt(4));
        		vo.setScaleDescription(rs.getString(5));
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("RatingScale.java - getRatingScale - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
		return v;
	}
	
	public Vector getValueID(int iScaleID) {
		String query = "Select ValueID from tblScaleValue where ScaleID = " + iScaleID;
	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()) {
				v.add(new Integer(rs.getInt(1)));
			}
		}catch(Exception ex){
			System.out.println("RatingScale.java - getValueID - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return v;
	}
	
	public int getTotal(int iScaleID) {
		String query = "Select count(*) from tblScaleValue where ScaleID = " + ScaleID;
	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int total = 0;
		
		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		
		if(rs.next())
			total = rs.getInt(1);;
		
		}catch(Exception ex){
			System.out.println("RatingScale.java - getTotal - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return total;
	}
}