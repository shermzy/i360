package com.NURVSoftware.ThaiCharacters;

import java.sql.*;

/**
 * <p>Title: Unicode Translator</p>
 * <p>Description: This is a class written in order to allow a program to
 *   save information into a database using unicode. More Specifically,
 *   this was designed for SQL Server 2000, but could be expanded to write
 *   binary chunks to other servers.
 * </p>
 * <p> <STRONG> This requires that the SQL Server 2000 JDBC Driver SP3 is
 *   installed on the client. Without the required files, this driver can
 *   not operate. </strong>
 * </p>
 * <p> To use this, any column that is queried for Thai (or other unicode
 * characters) must be of type nchar, nvarchar, or ntext. Although you can
 * query a normal type (char, varchar, text), the program can not accurately
 * save unicode into these types.
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: NURVSoftware.com</p>
 * @author Jeremy Norman
 * @version 1.0
 */
public class UnicodeTranslator {
  Connection base;

  /**
   * Creates the translator object
   * @param databaseConnection Connection - database connection
   *       that the translator is created on top of
   */
  public UnicodeTranslator(Connection databaseConnection) {
    if (databaseConnection == null)
      throw new RuntimeException(
                       "Unable to initialize a translator object without"
                       + " an active connection object"
                       );
    base = databaseConnection;
  }

  /**
   * This will select the specified field from the specified table. It will
   * select every single row since it lacks a where clause. If more than one
   * field is required, then change the field to * or a list of fields.
   * <br><strong>Example:</strong>
   * <br>Translator.selectAll("users","username");
   * <br>Translator.selectAll("users","username, password");
   * <br>Translator.selectAll("users","*");
   * @param table String - the table to search
   * @param field String - the field(s) to return
   * @throws SQLException
   * @return UnicodeResultSet - results returned
   */
  public UnicodeResultSet selectAll(String table, String field)
      throws SQLException {

    PreparedStatement stmt = base.prepareStatement("SELECT " + field + " FROM " + table);
//    stmt.setString(1,field);
//    stmt.setString(2,table);
    ResultSet results = stmt.executeQuery();

    return new UnicodeResultSet(results);
  }

  /**
   * This will select the specified field from the specified table when the
   * contents of the whereClause is executed. This is the same as a select
   * statement structured as follows "SELECT [field] FROM [table] WHERE [whereClause]".
   * The field can also be edited to include all field or s alist of fields
   * <br><Strong>Example:</strong>
   * <br>select("users","description","UserID > 0");
   * <blockquote>Equals: SELECT description FROM users WHERE UserID > 0
   * </blockquote>
   * <br>select("users","username, description","UserID = 10");
   * <br>select("users","*","UserID < 9");
   * @param table String - table to select from
   * @param field String - field(s) to select from
   * @param whereClause String
   * @throws SQLException
   * @return UnicodeResultSet
   */
  public UnicodeResultSet select(String table, String field, String whereClause)
      throws SQLException {

    PreparedStatement stmt = base.prepareStatement("SELECT " + field + " FROM " + table + " WHERE " + whereClause);
//    stmt.setString(1,field);
//    stmt.setString(2,table);
//    stmt.setString(3,whereClause);
    ResultSet results = stmt.executeQuery();

    return new UnicodeResultSet(results);
  }

  /**
   * This allows a selection of all fields inside the specified table when
   * the specified field equals the specfied value (equals). This is designed to
   * Allow a program to test for equality in Unicode characters.
   * This is the same as calling "SELECT * FROM [table] WHERE [field] = '[equals]'".
   * <BR><B>Example</B>
   * <BR>selectWhere("users","username","john");
   * @param table String
   * @param field String
   * @param equals String
   * @throws SQLException
   * @return UnicodeResultSet
   */
  public UnicodeResultSet selectWhere(String table, String field, String equals)
      throws SQLException {

    PreparedStatement stmt = base.prepareStatement("SELECT * FROM " + table + " WHERE " + field + " = ?");
//    stmt.setString(1,table);
//    stmt.setString(2,field);
    stmt.setString(1,equals);
    ResultSet results = stmt.executeQuery();

    return new UnicodeResultSet(results);
  }

  /**
   * This allows the program to update a database. This calls
   * "UPDATE [table] SET [field] = [value] WHERE [whereClause]"
   * @param table String - table to update
   * @param field String - field to change
   * @param value String - unicode string change the field to
   * @param whereClause String - the conditions of the update
   * @throws SQLException
   */
  public void update(String table, String field, String value, String whereClause)
      throws SQLException {
    PreparedStatement stmt = base.prepareStatement("UPDATE " + table + " SET " + field + " = ? WHERE " + whereClause);
//    stmt.setString(1,table);
//    stmt.setString(2,field);
    stmt.setString(1,value);
//    stmt.setString(4,whereClause);
    stmt.execute();
  }



//  public void updateValue(String table, String field, String value, String whereClause)
//      throws SQLException {
//    PreparedStatement stmt = base.prepareStatement("UPDATE ? SET ? = ? WHERE ?");
//    stmt.setString(1,table);
//    stmt.setString(2,field);
//    stmt.setString(3,value);
//    stmt.setString(4,whereClause);
//    stmt.execute();
//  }

  /**
   * This is designed to allow powerful update queries to be made. This
   * creates a fully custom SQL update to be made by specifying the update
   * or any other non-query SQL. It is exactly the same as calling
   * <br>[SQL] WHERE [field] = '[value]'
   * <br> As always, value can be a unicode string.
   * <br><b>Example:</b>
   * <br>Translator.execute("UPDATE users SET username='bob'","username","george");
   * @param SQL String
   * @param field String
   * @param value String
   * @throws SQLException
   */
  public void execute(String SQL, String field, String value) throws
      SQLException {
    PreparedStatement stmt = base.prepareStatement(SQL + " WHERE " + field + " = ?");
//    stmt.setString(1,field);
    stmt.setString(1,value);
    stmt.execute();
  }

  /**
   * This function allows complex query requests to be created as long as the
   * only where request is based on unicode. This simply calls the equivilent
   * of "[SQL] WHERE [field] = '[value]'" As always, the value can be unicode.
   * <br><b>Example:</b>
   * <br>Translator.executeQuery("SELECT * FROM users","username","Copper");
   * <blockquote>Calls: SELECT * FROM users WHERE username = 'Copper'
   * </blockquote>
   * @param SQL String - SQL to query
   * @param field String - field being tested
   * @param value String - value that must be equal
   * @throws SQLException
   * @return UnicodeResultSet
   */
  public UnicodeResultSet executeQuery(String SQL, String field, String value) throws
      SQLException {
    PreparedStatement stmt = base.prepareStatement(SQL + " WHERE " + field + " = ?");
//    stmt.setString(1,field);
    stmt.setString(1,value);

    ResultSet results = stmt.executeQuery();

    return new UnicodeResultSet(results);
  }

  /**
   * This allows extremely advanced updates or any other SQL to be designed.
   * Inside the SQL simply put a question mark (?) and then put the strings
   * that should replace them must be placed in an array. Then the question
   * marks will be replaced by the unicode strings in order.
   * <br><b>Example:</b>
   * <br>Translator.executeAdvanced(
   * <blockquote>"UPDATE users SET username = ?, password = ? WHERE username = ?",
   *         <br>new String[] {"George","wordpass","Fred"}
   *         <br>);
   * </blockquote>
   * @param SQL String
   * @param Unicode String[]
   * @throws SQLException
   */
  public void executeAdvanced(String SQL, String[] Unicode) throws
      SQLException {
    PreparedStatement stmt = base.prepareStatement(SQL);
    for(int i = 0; i < Unicode.length; i++) {
      stmt.setString(i + 1, Unicode[i]);
    }
    stmt.execute();
  }

  /**
   * This allowes extremely complex SELECT (or other query statements that return
   * results) to be executed. Simple write all of the SQL in the SQL parameter
   * and replace Unicode Characters with a "?". Then put all of the unicode
   * strings in the unicode array in the order of the question marks.
   * <br><b>Example:</b>
   * <br>UnicodeResultSet urs = Translator.executeAdvancedQuery(
   * <blockquote>"SELECT * FROM users WHERE username = ? AND ID > 15",
   * <br>new String[] {"Ming"}
   * <br>);
   * </blockquote>
   * @param SQL String
   * @param Unicode String[]
   * @throws SQLException
   * @return UnicodeResultSet
   */
  public UnicodeResultSet executeAdvancedQuery(String SQL, String[] Unicode) throws
      SQLException {

    PreparedStatement stmt = base.prepareStatement(SQL);
//    stmt.setString(1,Unicode[0]);
    for (int i = 0; i < Unicode.length; i++) {
      stmt.setString(i + 1, Unicode[i]);
    }
    ResultSet results = stmt.executeQuery();

    return new UnicodeResultSet(results);
  }

}
