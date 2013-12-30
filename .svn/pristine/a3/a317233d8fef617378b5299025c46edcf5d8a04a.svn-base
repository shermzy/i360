package CP_Classes;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voJobCategory;

import java.sql.*;
import java.util.Vector;

/**
 * This class implements all the operations for Job Category.
 */
public class JobCategory
{
	/**
	 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Competency.
	 */
	private Database db;

	/**
	 * Bean Variable for sorting purposes. Total Array depends on total SortType.
	 * 0 = ASC
	 * 1 = DESC
	 */
	private int Toggle [];	// 0=asc, 1=desc
	
	/**
	 * Bean Variable to store the Sorting type, such as sort by JobCategoryName and Origin.
	 */
	public int SortType;
	
	/**
	 * Bean Variable to store the ID of Job Category.
	 */
	public int JobCatID;
	
	/**
	 * Bean Variable to store the status whether it is a new added job category.
	 */
	public int IsAdd;
	

	/**
	 * Creates a new intance of Competency object.
	 */
	public JobCategory(){
		db = new Database();
		
		Toggle = new int [2];
		
		for(int i=0; i<2; i++)
			Toggle[i] = 0;
			
		SortType = 1;
		JobCatID = 0;
		IsAdd = 0;

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
	 * Store Bean Variable IsAdd.
	 */
	public void setIsAdd(int IsAdd) {
		this.IsAdd = IsAdd;
	}

	/**
	 * Get Bean Variable IsAdd.
	 */
	public int getIsAdd() {
		return IsAdd;
	}	
	
	
	/**
	 * Store Bean Variable Job CatID.
	 */
	public void setJobCatID(int JobCatID) {
		this.JobCatID = JobCatID;
	}

	/**
	 * Get Bean Variable JobCatID.
	 */
	public int getJobCatID() {
		return JobCatID;
	}	

	/**
	 * Add a new record to the JobCategory table.
	 *
	 * Parameters:
	 *		name - the name of the JobCategory.
	 *
	 * Returns:
	 *		pk Job Category just added.
	 */
	public boolean addRecord(String name, int OrgID, int pkUser, int userType) throws SQLException, Exception {											
		int isSysGenerated = 0;
		String sql = "";
		Connection con = null;
		Statement st = null;
		boolean bIsAdded=false;

		name = db.SQLFixer(name.trim());
					
		if(userType == 1)	
			isSysGenerated = 1;
		
		sql = "Insert into JobCategory (JobCategoryName, IsSystemGenerated, FKOrganisation) values ('" + name + "', " + isSysGenerated + ", ";
		sql = sql + OrgID + ")";
		/*		
		db.openDB();		
		PreparedStatement ps = db.con.prepareStatement(sql);			
		ps.executeUpdate();			
		db.closeDB();
		*/
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsAdded=true;
		}catch(Exception e){
			System.out.println("JobCategory.java - addRecord - "+e);
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return bIsAdded;
	}
	
	/**
	 * Edit a record in the JobCategory table.
	 *
	 * Parameters:
	 *		name - the name of the JobCategory.
	 *		pkJobCategory - the primary key of JobCategory to determine which record to be edited.
	 *
	 * Returns:
	 *		a boolean that represents the success of editing to the database.
	 */
	public boolean editRecord(String name, int pkJobCategory, int pkUser) throws SQLException, Exception {
		
		name = db.SQLFixer(name.trim());
		
		String sql = "Update JobCategory Set JobCategoryName = '" + name +
						"' where PKJobCategory = " + pkJobCategory;
		Connection con = null;
		Statement st = null;
		boolean bIsUpdated=false;
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
		*/
		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		int iSuccess = st.executeUpdate(sql);
		
		if(iSuccess!=0)
		bIsUpdated=true;
		}catch(Exception ex){
			System.out.println("JobCategory.java - editRecord - "+ex);
		}finally{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
/*		String [] UserInfo = U.getUserDetail(pkUser);		
		try {
			EV.addRecord("Update", "Motivation Factor", "(" + compBefore + ") - (" + name + ")", UserInfo[2], UserInfo[11], UserInfo[10]);
		} catch(SQLException SE) {
			System.out.println(SE.getMessage());
		}
*/			
		return bIsUpdated;
	}

	/**
	 * Delete an existing record from the JobCategory table.
	 *
	 * Parameters:
	 *		pkJobCategory - the primary key of JobCategory to determine which record to be deleted.
	 */
	public boolean deleteRecord(int pkJobCategory, int pkUser) throws SQLException, Exception {
		String sql = "Delete from JobCategory where PKJobCategory = " + pkJobCategory;
		boolean bIsDeleted=false;
		Connection con = null;
		Statement st = null;
		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		int iSuccess = st.executeUpdate(sql);
		
		if(iSuccess!=0)
		bIsDeleted=true;

		}catch(Exception ex){
			System.out.println("JobCategory.java - deleteRecord - "+ex);
		}finally{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


	
		}
		
/*		String [] UserInfo = U.getUserDetail(pkUser);		
		try {
			EV.addRecord("Delete", "Motivation Factor", name, UserInfo[2], UserInfo[11], UserInfo[10]);
		} catch(SQLException SE) {
			System.out.println(SE.getMessage());
		}
*/
		return bIsDeleted;
	}

	/**
	 * Retrieves Vector the Job Categories from JobCategory table order by JobCategoryName.
	 */
	public Vector getAllRecord() throws SQLException, Exception {
		Vector v = new Vector();
		
		String query = "Select * from JobCategory order by JobCategoryName";
	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		*/
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);


		while(rs.next()) {
			voJobCategory vo = new voJobCategory();
			vo.setPKJobCategory(rs.getInt("PKJobCategory"));
			vo.setJobCategoryName(rs.getString("JobcategoryName"));
			v.add(vo);
			
		}
		}catch(Exception e){
			System.out.println("JobCategory.java - getAllRecord - "+e);
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
	
		
		return v;
	}

	/**
	 * Retrieves all Job Categories based on Company and Organization.
	 */
	public Vector FilterRecord(int OrgID) throws SQLException, Exception {
		
		Vector v = new Vector();
		String query = "SELECT PKJobCategory, JobCategoryName, IsSystemGenerated, ";
		query = query + "tblOrigin.Description FROM JobCategory ";
		query = query + "INNER JOIN tblOrigin ON ";
		query = query + "JobCategory.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
		query = query + "WHERE (IsSystemGenerated = 1) or (FKOrganisation = " + OrgID + ") order by ";
		
		if(SortType == 1)
			query = query + "JobCategoryName";
		else if(SortType == 2)
			query = query + "Description";

		if(Toggle[SortType - 1] == 1)
			query = query + " DESC";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);


			while(rs.next()) {
				voJobCategory vo = new voJobCategory();
				vo.setPKJobCategory(rs.getInt("PKJobCategory"));
				vo.setJobCategoryName(rs.getString("JobcategoryName"));
				v.add(vo);
				
				}
		}catch(Exception e){
			System.out.println("JobCategory.java - FilterRecord - "+e);
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
	
		
		
	
			
		return v;
	}


	/**
	 * Get total JobCategory in the table.
	 */
	public int getTotalRecord() throws SQLException, Exception {
		int total = 0;
		
		String query = "Select count(*) from JobCategory";
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

			if(rs.next())
				total = rs.getInt(1);
				
		}catch(Exception e){
			System.out.println("JobCategory.java - getTotalRecord - "+e);
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
	
	

		
		return total;
	}
	
	/**
	 * Check the existance of the particular JobCategory in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int CheckJobCategoryExist(String JobCategoryName, int OrgID) throws SQLException, Exception {
		int pkComp = 0;

		JobCategoryName = db.SQLFixer(JobCategoryName.trim());
		
		String query = "SELECT * FROM JobCategory WHERE JobCategoryName = '" + JobCategoryName + "' AND ";
		query = query + "(FKOrganisation = " + OrgID + " or IsSystemGenerated = 1)";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			
			if(rs.next())
				pkComp = rs.getInt(1);
				
		}catch(Exception e){
			System.out.println("JobCategory.java - CheckJobCategoryExist - "+e);
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
	
	
		return pkComp;
	}
	
	/**
	 * Check whether the JobCategory belonged to System Generated of User Generated.
	 */
	public int CheckSysLibJobCategory(int JobCategoryID) throws SQLException, Exception {
		int pkComp = 0;

		String query = "SELECT IsSystemGenerated FROM JobCategory  ";
		query = query + "WHERE PKJobCategory = " + JobCategoryID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			
			if(rs.next())
				pkComp = rs.getInt(1);
				
		}catch(Exception e){
			System.out.println("JobCategory.java - CheckSysLibJobCategory - "+e);
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return pkComp;
	}
	/**
	 * @author Thant Thura
	 * @date 8 jan 2008
	 * @param iFKJobCat
	 * @return Vector of integer
	 * @see jobCatUpdate.jsp
	 */
	
	public Vector getCompetency(int iFKJobCat){
		Vector v=new Vector();
		String query="Select FKCompetency from JobCategoryItem where FKJobCategory="+iFKJobCat;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next()){
				Integer iFKComp=new Integer(rs.getInt("FKCompetency"));
				v.add(iFKComp);
			}
				
			
				
		}catch(Exception e){
			System.out.println("JobCategory.java - getCompetency - "+e);
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		
		
		return v;
		
	}
	
	
	/**
	 * Retrives the JobCategory Name based on JobCategory ID.
	 */
	public String JobCategoryName(int pkJobCategory) throws SQLException, Exception {
		String sName = "";
		String query = "Select JobCategoryName from JobCategory where PKJobCategory = " + pkJobCategory;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				sName = rs.getString(1);
			
				
		}catch(Exception e){
			System.out.println("JobCategory.java - JobCategoryName - "+e);
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		
	
		return sName;				
	}

	/**
	 * Retrieves all Competency Description based Organization ID.
	 */
	public Vector getJobCategory(int iFKOrg) throws SQLException, Exception {

		String query = "SELECT PKJobCategory, JobCategoryName, JobCategory.IsSystemGenerated, ";
		query = query + "tblOrigin.Description FROM JobCategory ";
		query = query + "INNER JOIN tblOrigin ON ";
		query = query + "JobCategory.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
		query = query + "WHERE (JobCategory.IsSystemGenerated = 1) or (FKOrganisation = " + iFKOrg + ") ";		
		
		query = query + "order by ";
		
		if(SortType == 1)
			query = query + "JobCategoryName";
		else if(SortType == 2)
			query = query + "Description";

		if(Toggle[SortType - 1] == 1)
			query = query + " DESC";

		
		System.out.println("Rianto: " + query);
		
		Vector v = new Vector();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs != null && rs.next()){
				voJobCategory vo = new voJobCategory();
				vo.setPKJobCategory(rs.getInt("PKJobCategory"));
				vo.setJobCategoryName(rs.getString("JobCategoryName"));
				vo.setOrigin(rs.getString("Description"));		
				v.add(vo);
				
			}
			
			
		}catch(Exception e){
			System.out.println("JobCategory.java - getJobCategory - "+e);
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}

		return v;
	}
	
	public static void main(String arg[]) throws Exception
	{
		JobCategory JC = new JobCategory();
		
		Vector v = JC.getJobCategory(1);
	}
}