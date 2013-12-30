package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voDepartment;

/**
 * This class implements all the operations for Department table in the
 * database.
 */

public class Department {

	/**
	 * Declaration of new object of class Database. This object is declared
	 * private, which is to make sure that it is only accessible within this
	 * class Age.
	 */

	private EventViewer ev;

	private Create_Edit_Survey user;

	private String sDetail[] = new String[13];

	private String itemName = "Department";

	public Department() {

		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}

	/**
	 * Check Department Exist in the database.
	 * 
	 * Parameters: String DepartmentName
	 * 
	 */
	public int checkDeptExist(String DepartmentName) throws SQLException,
			Exception {
		int iPKDepartment = 0;

		String command = "SELECT * FROM [Department] WHERE DepartmentName = '"
				+ DepartmentName + "'";
		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) iPKDepartment =
		 * rs1.getInt("PKDepartment");
		 * 
		 * rs1.close(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next()) {
				iPKDepartment = rs.getInt("PKDepartment");
			}

		} catch (Exception E) {
			System.err.println("Department.java - checkDeptExist - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return iPKDepartment;
	}

	/**
	 * Check Department Exist in the database.
	 * 
	 * Parameters: String DepartmentName, int FKOrganization
	 * 
	 */
	public int checkDeptExist(String DepartmentName, int FKOrganization)
			throws SQLException, Exception {
		int iPKDepartment = 0;

		String command = "SELECT * FROM [Department] WHERE DepartmentName = '"
				+ DepartmentName + "' and FKOrganization = " + FKOrganization;

		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) iPKDepartment =
		 * rs1.getInt("PKDepartment"); rs1.close(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next()) {
				iPKDepartment = rs.getInt("PKDepartment");
			}

		} catch (Exception E) {
			System.err.println("Department.java - checkDeptExist - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return iPKDepartment;

	}

	public int checkDeptExist(String sDeptName, int FKDiv, int iOrgID)
			throws SQLException, Exception {
		int iPKDepartment = 0;

		String command = "SELECT * FROM [Department] WHERE FKDivision = "
				+ FKDiv + " AND DepartmentName = '" + sDeptName
				+ "' AND FKOrganization = " + iOrgID;

		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) iPKDepartment =
		 * rs1.getInt("PKDepartment");
		 * 
		 * rs1.close(); db.closeDB();
		 */

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next()) {
				iPKDepartment = rs.getInt("PKDepartment");
			}

		} catch (Exception E) {
			System.err.println("Department.java - checkDeptExist - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return iPKDepartment;

	}

	public int checkDeptExist(String sDeptName, String sDeptCode, int FKDiv,
			int iOrgID) throws SQLException, Exception {
		int iPKDepartment = 0;

		String command = "SELECT * FROM [Department] WHERE FKDivision = "
				+ FKDiv + " AND (DepartmentName = '" + sDeptName
				+ "' OR DepartmentCode = '" + sDeptCode
				+ "') AND FKOrganization = " + iOrgID;

		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) iPKDepartment =
		 * rs1.getInt("PKDepartment");
		 * 
		 * rs1.close(); db.closeDB();
		 */

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next()) {
				iPKDepartment = rs.getInt("PKDepartment");
			}

		} catch (Exception E) {
			System.err.println("Department.java - checkDeptExist - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return iPKDepartment;

	}

	/**
	 * Add a new record to the Department Table.
	 * 
	 * Parameters: String DepartmentName, int FKOrganization, int PKUser
	 * 
	 */
	public boolean addRecord(String DepartmentName, int FKOrganization,
			int PKUser) throws SQLException, Exception {
		// db.openDB();
		boolean bIsAdded = false;
		String sql = "INSERT INTO Department (DepartmentName,FKOrganization) VALUES ('"
				+ DepartmentName + "', " + FKOrganization + ")";
		// System.out.println("sql "+sql);
		// PreparedStatement ps = db.con.prepareStatement(sql);
		// ps.executeUpdate();
		Connection con = null;
		Statement st = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("Department.java - addRecord- " + E);
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		System.out.println("Department");

		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, DepartmentName, sDetail[2],
				sDetail[11], sDetail[10]);
		return bIsAdded;
	}

	/**
	 * Add a new record to the Department Table.
	 * 
	 * Parameters: String DepartmentName, String DepartmentCode, int
	 * FKOrganization, int PKUser
	 * 
	 */
	public boolean addRecord(String DepartmentName, String DepartmentCode,
			int FKOrganization, int PKUser) throws SQLException, Exception {
		// db.openDB();
		boolean bIsAdded = false;
		String sql = "INSERT INTO Department (DepartmentName,DepartmentCode,FKOrganization) VALUES ('"
				+ DepartmentName
				+ "', '"
				+ DepartmentCode
				+ "', "
				+ FKOrganization + ")";
		// System.out.println("sql "+sql);
		// PreparedStatement ps = db.con.prepareStatement(sql);
		// ps.executeUpdate();
		Connection con = null;
		Statement st = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;
		} catch (Exception E) {
			System.err.println("Department.java - addRecord- " + E);
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		System.out.println("Department");

		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, DepartmentName + ", Code = "
				+ DepartmentCode, sDetail[2], sDetail[11], sDetail[10]);

		return bIsAdded;
	}

	/**
	 * Add a new record to the Department Table without capturing into tblEvent
	 * 
	 * Parameters: String DepartmentName, int FKOrganization
	 * 
	 */
	public boolean addRecord(String DepartmentName, int FKOrganization)
			throws SQLException, Exception {

		// db.openDB();
		boolean bIsAdded = false;
		String sql = "INSERT INTO Department (DepartmentName,FKOrganization) VALUES ('"
				+ DepartmentName + "', " + FKOrganization + ")";
		// System.out.println("sql "+sql);
		// PreparedStatement ps = db.con.prepareStatement(sql);
		// ps.executeUpdate();
		Connection con = null;
		Statement st = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;
		} catch (Exception E) {
			System.err.println("Department.java - addRecord- " + E);
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		System.out.println("Department");
		return bIsAdded;
	}

	/**
	 * Add a new record to the Department Table without capturing into tblEvent
	 * 
	 * @param DepartmentName
	 * @param DepartmentCode
	 * @param FKOrganization
	 * @param PKUser
	 * @param FKDivision
	 * @throws SQLException
	 * @throws Exception
	 * @author Su See
	 */
	public boolean addRecord(String DepartmentName, String DepartmentCode,
			int FKOrganization, int PKUser, int FKDivision)
			throws SQLException, Exception {
		// db.openDB();
		boolean bIsAdded = false;
		String sql = "INSERT INTO Department (FKDivision, DepartmentName, DepartmentCode, FKOrganization) VALUES ("
				+ FKDivision
				+ ", '"
				+ DepartmentName
				+ "', '"
				+ DepartmentCode
				+ "', " + FKOrganization + ")";
		// Debug information : 2008 - June
		System.out.println("sql " + sql);
		// PreparedStatement ps = db.con.prepareStatement(sql);
		// ps.executeUpdate();
		// System.out.println("Department");
		Connection con = null;
		Statement st = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("Department.java - addRecord- " + E);
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		if (PKUser != 0) {
			sDetail = user.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, DepartmentName, sDetail[2],
					sDetail[11], sDetail[10]);
		}
		return bIsAdded;
	}

	/**
	 * Edit a record in the database.
	 * 
	 * Parameters: PKDepartment - primary key DepartmentName
	 * 
	 */

	public boolean editRecord(int PKDepartment, String DepartmentName,
			int FKOrganization, int PKUser) throws SQLException, Exception {
		String OldName = "";
		String command = "SELECT * FROM Department WHERE PKDepartment = "
				+ PKDepartment;
		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) OldName =
		 * rs1.getString("DepartmentName");
		 * 
		 * rs1.close(); db.openDB();
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next()) {
				OldName = rs.getString("DepartmentName");
			}

		} catch (Exception E) {
			System.err.println("Department.java - editRecord - " + E);
		} finally {

			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		String sql = "UPDATE Department SET DepartmentName  = '"
				+ DepartmentName + "' WHERE PKDepartment = " + PKDepartment
				+ " AND FKOrganization=" + FKOrganization;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */
		boolean bIsUpdated = false;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsUpdated = true;

		}

		catch (Exception E) {
			System.err.println("Department.java - editRecord- " + E);
		}

		finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		if (bIsUpdated) {
			sDetail = user.getUserDetail(PKUser);
			ev.addRecord("Update", itemName, "(" + OldName + ") - ("
					+ DepartmentName + ")", sDetail[2], sDetail[11],
					sDetail[10]);
		}
		return bIsUpdated;
	}

	/**
	 * Edit a record in the database.
	 * 
	 * Parameters: PKDepartment - primary key DepartmentName DepartmentCode
	 * 
	 */

	public boolean editRecord(int PKDepartment, String DepartmentName,
			String DepartmentCode, int FKOrganization, int PKUser)
			throws SQLException, Exception {
		String OldName = "";
	
		String command = "SELECT * FROM Department WHERE PKDepartment = "
				+ PKDepartment;
		// Edit By James 30-June 2008
		// String command = "SELECT * FROM Department WHERE(DepartmentName =
		// '"+DepartmentName+"'OR DepartmentCode = '"+DepartmentCode+"') AND
		// (FKDivision = 4)";
		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) OldName =
		 * rs1.getString("DepartmentName"); rs1.close(); db.openDB();
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int division = -1;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next()) {
				OldName = rs.getString("DepartmentName");
				division = rs.getInt("FKDivision");
			}

		} catch (Exception E) {
			System.err.println("Department.java - editRecord - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		//Added By James 30-June 2008
		boolean newDate = true;
		command = "select * from Department where PKDepartment !='"
				+ PKDepartment + "' AND FKDivision ='"+division+"' AND (DepartmentName= '" + DepartmentName
				+ "' OR DepartmentCode ='" + DepartmentCode+"')";
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next()) {
				newDate = false;
			}
		} catch (Exception E) {
			System.err.println("Department.java - editRecord - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		//End of Added By James 30-June 2008

		String sql = "UPDATE Department SET DepartmentName  = '"
				+ DepartmentName + "', DepartmentCode = '" + DepartmentCode
				+ "' WHERE PKDepartment = " + PKDepartment
				+ " AND FKOrganization=" + FKOrganization;
		// System.out.println(sql);
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */

		boolean bIsUpdated = false;
		if (newDate) {
			try {
				con = ConnectionBean.getConnection();
				st = con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if (iSuccess != 0)
					bIsUpdated = true;

			}

			catch (Exception E) {
				System.err.println("Department.java - editRecord- " + E);
			}

			finally {

				ConnectionBean.closeStmt(st); // Close statement
				ConnectionBean.close(con); // Close connection

			}

			if (PKUser != 0 && bIsUpdated) {
				sDetail = user.getUserDetail(PKUser);
				ev.addRecord("Update", itemName, "(" + OldName + ") - ("
						+ DepartmentName + ", Code = " + DepartmentCode + ")",
						sDetail[2], sDetail[11], sDetail[10]);
			}
		}

		return bIsUpdated;
	}

	/**
	 * Delete an existing record from the database.
	 * 
	 * Parameters: PKDepartment - primary key
	 */

	public boolean deleteRecord(int PKDepartment, int FKOrganization, int PKUser)
			throws SQLException, Exception {
		String OldName = "";
		String command = "SELECT * FROM Department WHERE PKDepartment = "
				+ PKDepartment;
		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) OldName =
		 * rs1.getString("DepartmentName"); rs1.close(); db.openDB();
		 */

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next()) {
				OldName = rs.getString("DepartmentName");
			}

		} catch (Exception E) {
			System.err.println("Department.java - deleteRecord - " + E);
		} finally {

			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		String sql = "Delete from Department where PKDepartment = "
				+ PKDepartment + " AND FKOrganization=" + FKOrganization;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		boolean bIsDeleted = false;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception E) {
			System.err.println("Department.java - deleteRecord - " + E);

		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Delete", itemName, OldName, sDetail[2], sDetail[11],
				sDetail[10]);

		return bIsDeleted;
	}

	/**
	 * This function will link up Department with Division
	 * 
	 * @param iDivision
	 * @param iDepartment
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean linkDepartment(int iDivision, int iDepartment)
			throws SQLException, Exception {
		String sSQL = "UPDATE Department SET FKDivision = " + iDivision
				+ " WHERE PKDepartment = " + iDepartment;

		/*
		 * db.openDB();
		 * 
		 * PreparedStatement ps = db.con.prepareStatement(sSQL);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */

		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sSQL);
			if (iSuccess != 0)
				bIsUpdated = true;

		}

		catch (Exception E) {
			System.err.println("Department.java - linkDepartment- " + E);
		}

		finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return bIsUpdated;

	}

	/**
	 * Get Department
	 * 
	 * @param iFKOrg
	 * @param iDivID
	 * @return
	 * @author James
	 */

	public Vector getAllDepartments(int iFKOrg, int iDivID) {

		Vector v = new Vector();
		String querySql = "SELECT * FROM Department WHERE FKOrganization="
				+ iFKOrg + " AND FKDivision=" + iDivID
				+ " ORDER BY DepartmentName";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(querySql);

			while (rs.next()) {

				voDepartment vo = new voDepartment();
				vo.setPKDepartment(rs.getInt("PKDepartment"));
				vo.setDepartmentName(rs.getString("DepartmentName"));
				vo.setDepartmentCode(rs.getString("DepartmentCode"));
				vo.setFKDivision(rs.getInt("FKDivision"));
				vo.setFKOrganization(rs.getInt("FKOrganization"));
				vo.setLocation(rs.getString("Location"));

				v.add(vo);
			}

		} catch (Exception E) {
			System.err.println("Department.java - getDepartment - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/**
	 * Get Target Departments
	 * 
	 * @param iFKOrg
	 * @param iDivID
	 * @return
	 * @author Yuni
	 */

	public Vector getTargetDepartments(int iSurveyID, int iDivID) {

		Vector v = new Vector();

		String command2 = "SELECT DISTINCT Department.PKDepartment, Department.DepartmentName, Department.Location ";
		command2 = command2 + "FROM tblAssignment INNER JOIN Department ON ";
		command2 = command2
				+ "tblAssignment.FKTargetDepartment = Department.PKDepartment ";
		command2 = command2 + "WHERE (tblAssignment.SurveyID = " + iSurveyID
				+ ") ";
		command2 = command2 + "AND Department.FKDivision = " + iDivID + " ";
		command2 = command2 + "ORDER BY Department.DepartmentName ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command2);

			while (rs.next()) {

				voDepartment vo = new voDepartment();
				vo.setPKDepartment(rs.getInt("PKDepartment"));
				vo.setDepartmentName(rs.getString("DepartmentName"));

				v.add(vo);
			}

		} catch (Exception E) {
			System.err.println("Department.java - getDepartment - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}
}