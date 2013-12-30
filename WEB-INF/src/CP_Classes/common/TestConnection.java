package CP_Classes.common;

import java.sql.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class TestConnection {
	public static void main(String[] args) {
		// Neue DB und los geht's :)
		DB db = new DB();
		db.dbConnect("jdbc:sqlserver://localhost:1433;DatabaseName=360cp;", "360cp",
				"raffles");
	}
}

class DB {
	public void dbConnect(String db_connect_string, String db_userid,
			String db_password) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(db_connect_string,
					db_userid, db_password);
			System.out.println("connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
