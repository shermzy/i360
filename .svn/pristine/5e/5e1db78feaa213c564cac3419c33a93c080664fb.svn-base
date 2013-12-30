package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;

/**
 * This class implements all the operations for Job Category Detail in iSelect.
 */
public class JobCategoryDetail
{
	/**
	 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Competency.
	 */
	//private Database db;

	/**
	 * Bean Variable for sorting purposes. Total Array depends on total SortType.
	 * 0 = ASC
	 * 1 = DESC
	 */
	private int Toggle [];	// 0=asc, 1=desc
	
	/**
	 * Bean Variable to store the Sorting type, such as sort by Motivation Factor.
	 */
	public int SortType;
		

	/**
	 * Creates a new intance of Competency object.
	 */
	public JobCategoryDetail(){
		//db = new Database();
		
		Toggle = new int [3];
		
		for(int i=0; i<3; i++)
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
	 * Retrieves all Competencies based on Organization ID.
	 * All competencies retrieved are those that have been assigned to the JOb Category.
	 */
	public Vector getCompetencyAssigned(int CatID, int OrgID) throws SQLException, Exception {
		
		Vector v =  new Vector();
		
		String query = "SELECT JobCategoryItem.PKJobCategoryItem, Competency.PKCompetency, Competency.CompetencyName, Competency.CompetencyDefinition ";
		query = query + "FROM Competency INNER JOIN JobCategoryItem ON ";
		query = query + "Competency.PKCompetency = JobCategoryItem.FKCompetency WHERE ";
		query = query + "(Competency.IsSystemGenerated = 1 OR Competency.FKOrganizationID = " + OrgID + ") AND ";
		query = query + "(JobCategoryItem.FKJobCategory = " + CatID + ") order by ";
		
		if(SortType == 1)
			query = query + "CompetencyName";
		else if(SortType == 2)
			query = query + "CompetencyDefinition";
		else if(SortType == 3)
			query = query + "IsSystemGenerated";

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
		
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()) {
				voCompetency vo = new voCompetency();
				vo.setPKJobCategoryItem(rs.getInt("PKJobCategoryItem"));
				vo.setCompetencyID(rs.getInt("PKCompetency"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				vo.setCompetencyDefinition(rs.getString("CompetencyDefinition"));
				v.add(vo);
			}
				
		}catch(Exception ex){
			System.out.println(" JobCategoryDetail.java - getCompetencyAssigned - "+ex.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		
		return v;
		
	}	
	
	/**
	 * Add a new record to the JobCategoryItem table.
	 *
	 * Parameters:
	 *		FKComp - the Competency assigned to the the particular Job Category.
	 */
	public boolean addCompetency(int FKJobCat, int FKComp) throws SQLException, Exception {											
		String sql = "";
		sql = "Insert into JobCategoryItem (FKJobCategory, FKCompetency) values (" + FKJobCat + ", " + FKComp + ")";
		boolean bIsAdded=false;
	
		Connection con = null;
		Statement st = null;


		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsAdded=true;

		}catch(Exception ex){
			System.out.println(" JobCategoryDetail.java - addCompetency - "+ex.getMessage());
				
		}finally{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		return bIsAdded;
	}
		
	/**
	 * Delete competency assigned to the Job Category.
	 *
	 * Parameters:
	 *		PK - the Competency assigned to the the particular Job Category.
	 */
	public boolean DeleteCompetency(int PKJobCategoryItem) throws SQLException, Exception {											
		String sql = "Delete from JobCategoryItem where PKJobCategoryItem = " + PKJobCategoryItem;
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
				System.out.println(" JobCategoryDetail.java - addCompetency - "+ex.getMessage());
					
			}finally{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
			return bIsDeleted;
	}
	
	/**
	 * Retrieves all Competencies based on Organization ID.
	 * All competencies retrieved are those that have not been assigned to the JOb Category.
	 */
	public Vector getRestCompetency(int CatID, int OrgID) throws SQLException, Exception {

		Vector v = new Vector();
		
		String query = "select * from Competency WHERE ";
		query = query + "(Competency.IsSystemGenerated = 1 OR Competency.FKOrganizationID = " + OrgID + ") ";
		query = query + "and PKCompetency NOT IN ";
		query = query + "(SELECT FKCompetency from JobCategoryItem where FKJobCategory = " + CatID + ") order by ";
		
		if(SortType == 1)
			query = query + "CompetencyName";
		else if(SortType == 2)
			query = query + "CompetencyDefinition";
		else if(SortType == 3)
			query = query + "IsSystemGenerated";

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
		
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()) {
				voCompetency vo = new voCompetency();
				vo.setCompetencyID(rs.getInt("PKCompetency"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				vo.setCompetencyDefinition(rs.getString("CompetencyDefinition"));
				v.add(vo);
			}
			
		}catch(Exception ex){
			System.out.println(" JobCategoryDetail.java - getRestCompetency - "+ex.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		
	
		
		return v;
	}
}