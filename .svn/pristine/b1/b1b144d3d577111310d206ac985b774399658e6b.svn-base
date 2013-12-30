package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voKeyBehaviour;

/**
 * This class implements all the operations for Key Behaviour, which is to be
 * used in System Libraries, Survey, and Report..
 */
public class KeyBehaviour {
	/**
	 * Declaration of new object of class Database. This object is declared
	 * private, which is to make sure that it is only accessible within this
	 * class.
	 */
	// private Database db;
	/**
	 * Declaration of new object of class User.
	 */
	private User_Jenty U;

	/**
	 * Declaration of new object of class EventViewer.
	 */
	private EventViewer EV;

	/**
	 * Bean Variable to store the Competency foreign key.
	 */
	public int FKComp;

	/**
	 * Bean Variable to store the Level of Key Behaviour.
	 */
	public int KBLevel;

	/**
	 * Bean Variable to store the total of Key Behaviour added (at least must be
	 * 3).
	 */
	public int Added;

	/**
	 * Bean Variable to store whether the Key Behaviour is added from the
	 * Competency just added.
	 */
	public int IsComp;

	/**
	 * Bean Variable for sorting purposes. Total Array depends on total
	 * SortType. 0 = ASC 1 = DESC
	 */
	public int Toggle[]; // 0=asc, 1=desc

	/**
	 * Bean Variable to store the Sorting type.
	 */
	public int SortType;

	/**
	 * Bean Variable to store the primary key of KB for editing purpose.
	 */
	public int KBID;

	/**
	 * Creates a new intance of Key Behaviour object.
	 */
	public KeyBehaviour() {
		// db = new Database();
		U = new User_Jenty();
		EV = new EventViewer();

		Toggle = new int[2];

		for (int i = 0; i < 2; i++)
			Toggle[i] = 0;

		SortType = 1;
		FKComp = 0;
		KBLevel = 0;
		Added = 0;
		IsComp = 0;
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
		return Toggle[SortType - 1];
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
	 * Store Bean Variable of foreign key CompetencyID.
	 */
	public void setFKComp(int FKComp) {
		this.FKComp = FKComp;
	}

	/**
	 * Get Bean Variable of foreign key CompetencyID.
	 */
	public int getFKComp() {
		return FKComp;
	}

	/**
	 * Store Bean Variable of KBID.
	 */
	public void setKBID(int KBID) {
		this.KBID = KBID;
	}

	/**
	 * Get Bean Variable of foreign key KBID.
	 */
	public int getKBID() {
		return KBID;
	}

	/**
	 * Store Bean Variable of whether it is from Competency which just added.
	 */
	public void setIsComp(int IsComp) {
		this.IsComp = IsComp;
	}

	/**
	 * Get Bean Variable of whether it is from Competency which just added.
	 */
	public int getIsComp() {
		return IsComp;
	}

	/**
	 * Store Bean Variable of total Key Behaviour added.
	 */
	public void setAdded(int Added) {
		this.Added = Added;
	}

	/**
	 * Get Bean Variable of total Key Behaviour added.
	 */
	public int getAdded() {
		return Added;
	}

	/**
	 * Store Bean Variable of the Key Behaviour Level.
	 */
	public void setKBLevel(int KBLevel) {
		this.KBLevel = KBLevel;
	}

	/**
	 * Get Bean Variable of the Key Behaviour Level.
	 */
	public int getKBLevel() {
		return KBLevel;
	}

	/**
	 * This method ads a new Key Behaviour
	 * 
	 * @param iFKCompetency
	 * @param sKeyBehaviour
	 * @param iKBLevel
	 * @param iCompanyID
	 * @param iOrgID
	 * @return True for successful add operation, else return false
	 * @throws SQLException
	 * @throws Exception
	 *             Created By: Chun Pong Created Date: 23 Jun 2008 Last Updated
	 *             By: Chun Pong Last Updated Date: 23 Jun 2008
	 */
	public boolean addRecord(int iFKCompetency, String sKeyBehaviour,
			int iKBLevel, int iCompanyID, int iOrgID) throws SQLException,
			Exception {
		// added "N" by alvis on 22-Sep-09 to allow chinese support
		String sQuery = "Insert into KeyBehaviour (FKCompetency, KeyBehaviour, IsSystemGenerated, "
				+ "KBLevel, FKCompanyID, FKOrganizationID) values ("
				+ iFKCompetency
				+ ",N'"
				+ sKeyBehaviour
				+ "', 0,"
				+ KBLevel
				+ ", " + iCompanyID + ", " + iOrgID + ")";

		// Connection & Statement
		Connection con = null;
		Statement st = null;

		con = ConnectionBean.getConnection();
		st = con.createStatement();
		int iStatus = st.executeUpdate(sQuery);

		ConnectionBean.closeStmt(st); // Close statement
		ConnectionBean.close(con); // Close connection

		if (iStatus != 0)
			return true;
		return false;
	} // End of addRecord()

	/**
	 * Add a new record to the KeyBehaviour table.
	 * 
	 * Parameters: fkCompetency - foreign key of CompetencyID. statement - the
	 * Key Behaviour statement. KBLevel - the level of Competency that this KB
	 * belongs to.
	 * 
	 * Returns: a boolean that represents the success of inserting to the
	 * database.
	 */
	public boolean addRecord(int fkCompetency, String statement, int KBLevel,
			int companyID, int orgID, int pkUser, int userType)
			throws SQLException, Exception {
		int IsSysGenerated = 0;

		// if(companyID == 1 && orgID == 1)
		if (userType == 1)
			IsSysGenerated = 1;
		// added "N" by alvis on 22-Sep-09 to allow chinese support
		String sql = "Insert into KeyBehaviour (FKCompetency, KeyBehaviour, IsSystemGenerated, KBLevel, FKCompanyID, ";
		sql = sql + "FKOrganizationID) values (" + fkCompetency + ",N'"
				+ statement + "'," + IsSysGenerated + "," + KBLevel + ", "
				+ companyID + ", " + orgID + ")";

		/*
		 * db.openDB(); PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - addRecord - " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		String[] UserInfo = U.getUserDetail(pkUser);

		try {
			EV.addRecord("Insert", "Key Behaviour", statement, UserInfo[2],
					UserInfo[11], UserInfo[10]);
		} catch (SQLException SE) {
			System.err.println("KeyBehaviour.java - addRecord - " + SE);
		}

		return bIsAdded;
	}

	public boolean addRecord(int fkCompetency, String statement[], int KBLevel,
			int companyID, int orgID, int pkUser, int userType)
			throws SQLException, Exception {
		int IsSysGenerated = 0;

		// if(companyID == 1 && orgID == 1)
		if (userType == 1)
			IsSysGenerated = 1;
		// added "N" by alvis on 22-Sep-09 to allow chinese support
		String sql = "Insert into KeyBehaviour (FKCompetency, KeyBehaviour, KeyBehaviour1, KeyBehaviour2, "
				+ "KeyBehaviour3, KeyBehaviour4 ,KeyBehaviour5, IsSystemGenerated, KBLevel, FKCompanyID, "
				+ "FKOrganizationID) values (" + fkCompetency + ",";
		sql += "N'" + statement[0] + "', ";
		if(statement[1].trim().length() != 0)
			sql += "N'" + statement[1] + "', ";
		else
			sql +="null,";
		if(statement[2].trim().length() != 0)
			sql += "N'" + statement[2] + "', ";
		else
			sql +="null,";
		if(statement[3].trim().length() != 0)
			sql += "N'" + statement[3] + "', ";
		else
			sql +="null,";
		if(statement[4].trim().length() != 0)
			sql += "N'" + statement[4] + "', ";
		else
			sql +="null,";
		if(statement[5].trim().length() != 0)
			sql +=  "N'" + statement[5] + "',";
		else
			sql +="null,";
		sql +=  IsSysGenerated + "," + KBLevel
				+ ", " + companyID + ", " + orgID + ")";

		/*
		 * db.openDB(); PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - addRecord - " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		String[] UserInfo = U.getUserDetail(pkUser);

		try {
			EV.addRecord("Insert", "Key Behaviour", statement[0], UserInfo[2],
					UserInfo[11], UserInfo[10]);
		} catch (SQLException SE) {
			System.err.println("KeyBehaviour.java - addRecord - " + SE);
		}

		return bIsAdded;
	}

	/**
	 * Add a new record to the KeyBehaviour table.
	 * 
	 * Parameters: fkCompetency - foreign key of CompetencyID. statement - the
	 * Key Behaviour statement. KBLevel - the level of Competency that this KB
	 * belongs to.
	 * 
	 * statement2 - the statement in foreign language lang - language code of
	 * foreign language : 1 Indonesian, 2 Thai, 3 Korean, 4 Traditional Chinese,
	 * 5 Simplified Chinese
	 * 
	 * Returns: a boolean that represents the success of inserting to the
	 * database.
	 */
	public boolean addRecord(int fkCompetency, String statement,
			String statement2, int lang, int KBLevel, int companyID, int orgID,
			int pkUser, int userType) throws SQLException, Exception {
		int IsSysGenerated = 0;

		// if(companyID == 1 && orgID == 1)
		if (userType == 1)
			IsSysGenerated = 1;
		// added "N" by alvis on 22-Sep-09 to allow chinese support

		String sql = "";
		if (lang == 4 || lang == 5) {
			sql = "Insert into KeyBehaviour (FKCompetency, KeyBehaviour, KeyBehaviour4, KeyBehaviour5, IsSystemGenerated, KBLevel, FKCompanyID, ";
			sql = sql + "FKOrganizationID) values (" + fkCompetency + ",N'"
					+ statement + "', N'" + statement2 + "', N'" + statement2
					+ "', " + IsSysGenerated + "," + KBLevel + ", " + companyID
					+ ", " + orgID + ")";
		} else {
			sql = "Insert into KeyBehaviour (FKCompetency, KeyBehaviour, KeyBehaviour"
					+ lang + ", IsSystemGenerated, KBLevel, FKCompanyID, ";
			sql = sql + "FKOrganizationID) values (" + fkCompetency + ",N'"
					+ statement + "', N'" + statement2 + "', " + IsSysGenerated
					+ "," + KBLevel + ", " + companyID + ", " + orgID + ")";
		}

		/*
		 * db.openDB(); PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - addRecord - " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		String[] UserInfo = U.getUserDetail(pkUser);

		try {
			EV.addRecord("Insert", "Key Behaviour", statement, UserInfo[2],
					UserInfo[11], UserInfo[10]);
		} catch (SQLException SE) {
			System.err.println("KeyBehaviour.java - addRecord - " + SE);
		}

		return bIsAdded;
	}

	/**
	 * Edit a record in the Key Behaviour table (KeyBehaviour).
	 * 
	 * Parameters: fkCompetency - the foreign key of Competency to determine
	 * which record to be edited. pkKB - the primary key of Key Behaviour.
	 * statement - the KB description. KBLevel - level of Competency that the KB
	 * belongs to.
	 * 
	 * Returns: a boolean that represents the success of editing to the
	 * database.
	 */
	public boolean editRecord(int fkCompetency, int pkKB, String statement,
			int KBLevel, int pkUser) throws SQLException, Exception {
		String oldStatement = KBStatement(pkKB);
		// added "N" by alvis on 22-Sep-09 to allow chinese support
		String sql = "Update KeyBehaviour Set KeyBehaviour = N'" + statement
				+ "', FKCompetency = " + fkCompetency + ", KBLevel = "
				+ KBLevel + " where PKKeyBehaviour = " + pkKB;

		/*
		 * db.openDB(); PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */

		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsUpdated = true;

		}

		catch (Exception E) {
			System.err.println("KeyBehaviour.java - editRecord- " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		String[] UserInfo = U.getUserDetail(pkUser);
		try {
			EV.addRecord("Update", "Key Behaviour", "(" + oldStatement
					+ ") - (" + statement + ")", UserInfo[2], UserInfo[11],
					UserInfo[10]);
		} catch (SQLException SE) {
			System.err.println("KeyBehaviour.java - editRecord- " + SE);
		}

		return bIsUpdated;
	}

	public boolean editRecord(int fkCompetency, int pkKB, String[] statement,
			int KBLevel, int pkUser) throws SQLException, Exception {
		String oldStatement = KBStatement(pkKB);
		// added "N" by alvis on 22-Sep-09 to allow chinese support
		String sql = "Update KeyBehaviour Set KeyBehaviour = N'" + statement[0]+"',";
		if(statement[1].trim().length() != 0)
			sql += " KeyBehaviour1 = N'" + statement[1]+"',";
		if(statement[2].trim().length() != 0)
			sql += " KeyBehaviour2 = N'" + statement[2]+"',";
		if(statement[3].trim().length() != 0)
			sql += " KeyBehaviour3 = N'" + statement[3]+"',";
		if(statement[4].trim().length() != 0)
			sql += " KeyBehaviour4 = N'" + statement[4]+"',";
		if(statement[5].trim().length() != 0)
			sql += " KeyBehaviour5 = N'" + statement[5]+"',";
		sql += " FKCompetency = "
				+ fkCompetency + ", KBLevel = " + KBLevel
				+ " where PKKeyBehaviour = " + pkKB;

		/*
		 * db.openDB(); PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */

		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsUpdated = true;

		}

		catch (Exception E) {
			System.err.println("KeyBehaviour.java - editRecord- " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		String[] UserInfo = U.getUserDetail(pkUser);
		try {
			EV.addRecord("Update", "Key Behaviour", "(" + oldStatement
					+ ") - (" + statement + ")", UserInfo[2], UserInfo[11],
					UserInfo[10]);
		} catch (SQLException SE) {
			System.err.println("KeyBehaviour.java - editRecord- " + SE);
		}

		return bIsUpdated;
	}

	/**
	 * Delete an existing record from the Key Behaviour table.
	 * 
	 * Parameters: pkKB - the primary key of Key Behaviour to determine which
	 * record to be deleted.
	 * 
	 * Returns: a boolean that represents the success of deletion process.
	 */
	public boolean deleteRecord(int pkKB, int pkUser) throws SQLException,
			Exception {
		String statement = KBStatement(pkKB);

		String sql = "Delete from KeyBehaviour where PKKeyBehaviour = " + pkKB;

		/*
		 * db.openDB(); PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */

		Connection con = null;
		Statement st = null;

		boolean bIsDeleted = false;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - deleteRecord - " + E);
		}

		finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		String[] UserInfo = U.getUserDetail(pkUser);
		try {
			EV.addRecord("Delete", "Key Behaviour", statement, UserInfo[2],
					UserInfo[11], UserInfo[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}

		return bIsDeleted;
	}

	/**
	 * Retrieves all records from Key Behaviour table.
	 */
	// @karen not complete
	public Vector getAllRecord() throws SQLException, Exception {
		String query = "Select * from KeyBehaviour order by KeyBehaviour";
		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 * 
		 * return rs;
		 */
		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				voKeyBehaviour vo = new voKeyBehaviour();
				vo.setFKCompanyID(rs.getInt("FKCompanyID"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setFKOrganizationID(rs.getInt("FKOrganizationID"));
				vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
				vo.setKBLevel(rs.getInt("KBLevel"));
				vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
				vo.setKeyBehaviourID(rs.getInt("KeyBehaviourID"));
				vo.setPKKeyBehaviour(rs.getInt("PKKeyBehaviour"));

				v.add(vo);

			}

		} catch (SQLException SE) {
			System.out.println("KeyBehaviour.java -getAllRecord - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;
	}

	/**
	 * Retrieves all Key Behaviours based on Competency, KBLevel, Company and
	 * Organization ID.
	 */

	// @karen Not complete
	public Vector FilterKBList(int FKComp, int KBLevel, int companyID, int orgID)
			throws SQLException, Exception {
		String query = "";

		if (KBLevel != 0 && FKComp != 0) {
			query = query
					+ "Select PKKeyBehaviour, KeyBehaviour, Description from KeyBehaviour ";
			query = query
					+ " inner join tblOrigin on KeyBehaviour.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "where (FKCompetency = " + FKComp;
			query = query + " and KBLevel = " + KBLevel;
			query = query + " and FKOrganizationID = " + orgID
					+ " and FKCompanyID = " + companyID;
			query = query + ") or (FKCompetency = " + FKComp
					+ " and KBLevel = " + KBLevel
					+ " and IsSystemGenerated = 1) order by ";
		} else if (KBLevel == 0 && FKComp != 0) {
			query = query
					+ "Select PKKeyBehaviour, KeyBehaviour, Description from KeyBehaviour ";
			query = query
					+ " inner join tblOrigin on KeyBehaviour.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "where (FKCompetency = " + FKComp;
			query = query + " and FKOrganizationID = " + orgID
					+ " and FKCompanyID = " + companyID;
			query = query + ") or (FKCompetency = " + FKComp
					+ " and IsSystemGenerated = 1) order by ";
		} else if (KBLevel != 0 && FKComp == 0) {
			query = query
					+ "Select PKKeyBehaviour, KeyBehaviour, Description from KeyBehaviour ";
			query = query
					+ " inner join tblOrigin on KeyBehaviour.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "where (KBLevel = " + KBLevel;
			query = query + " and FKOrganizationID = " + orgID
					+ " and FKCompanyID = " + companyID;
			query = query + ") or (KBLevel = " + KBLevel
					+ " and IsSystemGenerated = 1) order by ";
		} else {
			query = query
					+ "Select PKKeyBehaviour, KeyBehaviour, Description from KeyBehaviour ";
			query = query
					+ " inner join tblOrigin on KeyBehaviour.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "where (FKOrganizationID = " + orgID
					+ " and FKCompanyID = " + companyID;
			query = query + ") or (IsSystemGenerated = 1) order by ";
		}

		if (SortType == 1)
			query = query + "KeyBehaviour";
		else
			query = query + "IsSystemGenerated";

		if (Toggle[SortType - 1] == 1)
			query = query + " DESC";
		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 * 
		 * return rs;
		 */
		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				voKeyBehaviour vo = new voKeyBehaviour();

				vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
				vo.setDescription(rs.getString("Description"));
				vo.setPKKeyBehaviour(rs.getInt("PKKeyBehaviour"));

				v.add(vo);

			}

		} catch (SQLException SE) {
			System.out.println("KeyBehaviour.java - FilterKBList - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;
	}

	/**
	 * Retrieves all Key Behaviours based on Competency, KBLevel, Company and
	 * Organization ID.
	 */
	// @karen not complete
	public Vector getKBList(int FKComp, int KBLevel, int companyID, int orgID)
			throws SQLException, Exception {

		Vector v = new Vector();

		String query = "";

		if (KBLevel != 0 && FKComp != 0) {
			query = query
					+ "Select PKKeyBehaviour, KeyBehaviour, Description from KeyBehaviour ";
			query = query
					+ " inner join tblOrigin on KeyBehaviour.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "where (FKCompetency = " + FKComp;
			query = query + " and KBLevel = " + KBLevel;
			query = query + " and FKOrganizationID = " + orgID
					+ " and FKCompanyID = " + companyID;
			query = query + ") or (FKCompetency = " + FKComp
					+ " and KBLevel = " + KBLevel
					+ " and IsSystemGenerated = 1) order by ";
		} else if (KBLevel == 0 && FKComp != 0) {
			query = query
					+ "Select PKKeyBehaviour, KeyBehaviour, Description from KeyBehaviour ";
			query = query
					+ " inner join tblOrigin on KeyBehaviour.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "where (FKCompetency = " + FKComp;
			query = query + " and FKOrganizationID = " + orgID
					+ " and FKCompanyID = " + companyID;
			query = query + ") or (FKCompetency = " + FKComp
					+ " and IsSystemGenerated = 1) order by ";
		} else if (KBLevel != 0 && FKComp == 0) {
			query = query
					+ "Select PKKeyBehaviour, KeyBehaviour, Description from KeyBehaviour ";
			query = query
					+ " inner join tblOrigin on KeyBehaviour.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "where (KBLevel = " + KBLevel;
			query = query + " and FKOrganizationID = " + orgID
					+ " and FKCompanyID = " + companyID;
			query = query + ") or (KBLevel = " + KBLevel
					+ " and IsSystemGenerated = 1) order by ";
		} else {
			query = query
					+ "Select PKKeyBehaviour, KeyBehaviour, Description from KeyBehaviour ";
			query = query
					+ " inner join tblOrigin on KeyBehaviour.IsSystemGenerated = tblOrigin.PKIsSystemGenerated ";
			query = query + "where (FKOrganizationID = " + orgID
					+ " and FKCompanyID = " + companyID;
			query = query + ") or (IsSystemGenerated = 1) order by ";
		}

		if (SortType == 1)
			query = query + "KeyBehaviour";
		else
			query = query + "IsSystemGenerated";

		if (Toggle[SortType - 1] == 1)
			query = query + " DESC";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				voKeyBehaviour voKB = new voKeyBehaviour();
				voKB.setKeyBehaviourID(rs.getInt("PKKeyBehaviour"));
				voKB.setKeyBehaviour(rs.getString("KeyBehaviour"));
				voKB.setDescription(rs.getString("Description"));

				v.add(voKB);
			}

		} catch (Exception E) {

			System.err.println("KeyBehaviour.java - getKBList - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	/**
	 * Get total records from Key Behaviour table.
	 */
	public int getTotalRecord() throws SQLException, Exception {
		String query = "Select count(*) from KeyBehaviour";
		int record = 0;
		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 * 
		 * if(rs.next()) return rs.getInt(1);
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				record = rs.getInt(1);
			}

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - getTotalRecord - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return record;
	}

	/**
	 * Check the existance of the particular Competency in the Key Behaviour
	 * table. Returns: 0 = NOT Exist 1 = Exist
	 */
	public int CheckCompetencyExist(int pkComp) throws SQLException, Exception {
		int exist = 0;

		String query = "Select * from KeyBehaviour where FKCompetency = "
				+ pkComp;

		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 * 
		 * if(rs.next()) exist = 1;
		 * 
		 * rs.close();
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				exist = 1;
			}

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - getTotalRecord - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return exist;
	}

	/**
	 * Check the existance of the particular Key Behaviour in the database.
	 * Returns: 0 = NOT Exist 1 = Exist
	 */
	public int CheckKBExist(String KBName, int FKComp, int KBLevel, int compID,
			int orgID) throws SQLException, Exception {
		int exist = 0;
		// added "N" by alvis on 22-Sep-09 to allow chinese support
		String query = "SELECT * FROM KeyBehaviour  ";
		query = query + "WHERE FKCompetency = " + FKComp + " AND ";
		query = query + "KeyBehaviour = N'" + KBName + "' and KBLevel = "
				+ KBLevel + " and ((FKCompanyID = ";
		query = query + compID + " and FKOrganizationID = " + orgID
				+ ") or (FKCompanyID = 1 and FKOrganizationID = 1))";

		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 * 
		 * if(rs.next()) exist = 1;
		 * 
		 * rs.close();
		 */

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				exist = rs.getInt(1);
			}

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - CheckKBExist - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return exist;
	}

	/**
	 * Get the total Competency assigned to this particular Key Behaviour.
	 */
	public int totalKB(int pkKB) throws SQLException, Exception {
		int exist = 0;

		String query = "SELECT count(*) as total FROM KeyBehaviour WHERE ";
		query = query
				+ "FKCompetency = (select FKCompetency from KeyBehaviour where PKKeyBehaviour = "
				+ pkKB + ")";
		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				exist = rs.getInt(1);

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - totalKB - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return exist;
	}

	/**
	 * Check whether the Key Behaviour belonged to System Generated of User
	 * Generated.
	 */
	public int CheckSysLibKB(int KBID) throws SQLException, Exception {
		int pkKB = 0;

		String query = "SELECT IsSystemGenerated FROM KeyBehaviour  ";
		query = query + "WHERE PKKeyBehaviour = " + KBID;
		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				pkKB = rs.getInt(1);

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - CheckSysLibKB - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return pkKB;
	}

	/**
	 * Retrieve all the Key Behaviours under the particular Competency.
	 */
	public Vector getRecord(int pkComp, int compID, int orgID)
			throws SQLException, Exception {
		String query = "SELECT * from KeyBehaviour where (FKCompetency = "
				+ pkComp;
		query = query + " and IsSystemGenerated = 1)";
		query = query + " or (FKCompetency = " + pkComp + " and FKCompanyID = "
				+ compID;
		query = query + " and FKOrganizationID = " + orgID + ")";
		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 * 
		 * return rs;
		 */
		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				voKeyBehaviour vo = new voKeyBehaviour();
				vo.setPKKeyBehaviour(rs.getInt("PKKeyBehaviour"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
				vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
				vo.setKBLevel(rs.getInt("KBLevel"));
				vo.setFKCompanyID(rs.getInt("FKCompanyID"));
				vo.setFKOrganizationID(rs.getInt("FKOrganizationID"));
				/*
				 * Remove by Thant Thura Myo
				 */
				// vo.setKeyBehaviourID(rs.getInt("KeyBehaviourID"));
				v.add(vo);

			}

		} catch (SQLException SE) {
			System.out.println("KeyBehaviour.java -getRecord - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;

	}

	public voKeyBehaviour getRecord(int iPkKB) throws SQLException, Exception {
		String query = "Select * from KeyBehaviour where PKKeyBehaviour = "
				+ iPkKB;

		voKeyBehaviour vo = new voKeyBehaviour();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {

				vo.setFKCompanyID(rs.getInt("FKCompanyID"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setFKOrganizationID(rs.getInt("FKOrganizationID"));
				vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
				vo.setKBLevel(rs.getInt("KBLevel"));
				vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
				for(int n = 1; n < 6; n++){
					vo.setKeyBehaviour(n, rs.getString("KeyBehaviour"+n));
				}
				vo.setPKKeyBehaviour(rs.getInt("PKKeyBehaviour"));

			}

		} catch (SQLException SE) {
			System.out.println("KeyBehaviour.java -getRecord - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return vo;

	}

	/**
	 * Get the Key Behaviour statement based on Key Behaviour ID.
	 */
	public String KBStatement(int pkKB) throws SQLException, Exception {
		String KB = "";

		String query = "SELECT * from KeyBehaviour where PKKeyBehaviour = "
				+ pkKB;
		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs
		 * = stmt.executeQuery(query);
		 */

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				KB = rs.getString("KeyBehaviour");

		} catch (Exception E) {
			System.err.println("KeyBehaviour.java - KBStatement - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return KB;
	}

	/**
	 * get KB list based on the competency ID
	 * 
	 * @param CompID
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Vector getKBList(int CompID) throws SQLException, Exception {

		String query = "SELECT * FROM KeyBehaviour WHERE FKCompetency ="
				+ CompID + " ORDER BY KeyBehaviour";

		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				voKeyBehaviour vo = new voKeyBehaviour();

				vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
				vo.setPKKeyBehaviour(rs.getInt("PKKeyBehaviour"));
				vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
				v.add(vo);

			}

		} catch (SQLException SE) {
			System.out.println("KeyBehaviour.java - getKBList - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;
	}

}