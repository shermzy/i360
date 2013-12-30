package com.NURVSoftware.ThaiCharacters;

import java.sql.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Map;
import java.util.Calendar;
import java.io.*;

/**
 * <p>Title: Unicode Result Set</p>
 * <p>Description: This is a result set that is designed to allow a result set
 *   from any query to extract unicode characters. This can be cast over any
 *   result set and from there all of the result set's functions are availible.
 *   Additionally, a "getResultSet" function allows the parent to be retrieved
 *   again. If you have any questions reguarding any non-documented functions
 *   of this class, see the ResultSet interface documentation.
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: NURVSoftware.com</p>
 * @author Jeremy Norman
 * @version 1.0
 * @see ResultSet
 */
public class UnicodeResultSet implements ResultSet {
  ResultSet base;

  /**
   * This will create the UnicodeResultSet. Any result set (from a unicode/thai
   * translator or even a personal query) can be used to create this object.
   * @param underlyingData ResultSet
   */
  public UnicodeResultSet(ResultSet underlyingData) {
    if (underlyingData == null) throw new RuntimeException(
      "A UnicodeResultSet can not be instantiated on a null ResultSet"
      );
    base = underlyingData;
  }

  /**
   * This returns the string in the specified column. If the column contains
   * unicode characters a unicode string will be returned. Otherwise, the string
   * returned will contain normal characters.
   * @param column int
   * @throws SQLException
   * @return String
   */
  public String getString(int column) throws SQLException {
    return base.getString(column);
  }

  /**
   * This is the same as the getString(column) call except a string is used
   * to reference the column.
   * @param columnName String
   * @throws SQLException
   * @return String
   * @see getString(column)
   */
  public String getString(String columnName) throws SQLException {
    return getString(base.findColumn(columnName));
  }

  /**
   * Returns the result set that was used to initialize this object.
   * @return ResultSet
   */
  public ResultSet getResultSet() {
    return base;
  }

  // BASE FUNCTIONS
  // These functions are just base functions of the Result set that this object
  // provides. These are implemented to allow easy access to the information
  // in the original result set.
  public int getConcurrency() throws SQLException {
    return base.getConcurrency();
  }

  public int getFetchDirection() throws SQLException {
    return base.getFetchDirection();
  }

  public int getFetchSize() throws SQLException {
    return base.getFetchSize();
  }

  public int getRow() throws SQLException {
    return base.getRow();
  }

  public int getType() throws SQLException {
    return base.getType();
  }

  public void afterLast() throws SQLException {
    base.afterLast();
  }

  public void beforeFirst() throws SQLException {
    base.beforeFirst();
  }
  public void cancelRowUpdates() throws SQLException {
    base.cancelRowUpdates();
  }

  public void clearWarnings() throws SQLException {
    base.clearWarnings();
  }

  public void close() throws SQLException {
    base.close();
  }

  public void deleteRow() throws SQLException {
    base.deleteRow();
  }

  public void insertRow() throws SQLException {
    base.insertRow();
  }

  public void moveToCurrentRow() throws SQLException {
    base.moveToCurrentRow();
  }

  public void moveToInsertRow() throws SQLException {
    base.moveToInsertRow();
  }

  public void refreshRow() throws SQLException {
    base.refreshRow();
  }

  public void updateRow() throws SQLException {
    base.updateRow();
  }

  public boolean first() throws SQLException {
    return base.first();
  }

  public boolean isAfterLast() throws SQLException {
    return base.isAfterLast();
  }

  public boolean isBeforeFirst() throws SQLException {
    return base.isBeforeFirst();
  }

  public boolean isFirst() throws SQLException {
    return base.isFirst();
  }

  public boolean isLast() throws SQLException {
    return base.isLast();
  }

  public boolean last() throws SQLException {
    return base.last();
  }

  public boolean next() throws SQLException {
    return base.next();
  }

  public boolean previous() throws SQLException {
    return base.previous();
  }

  public boolean rowDeleted() throws SQLException {
    return base.rowDeleted();
  }

  public boolean rowInserted() throws SQLException {
    return base.rowInserted();
  }

  public boolean rowUpdated() throws SQLException {
    return base.rowUpdated();
  }

  public boolean wasNull() throws SQLException {
    return base.wasNull();
  }

  public byte getByte(int columnIndex) throws SQLException {
    return base.getByte(columnIndex);
  }

  public double getDouble(int columnIndex) throws SQLException {
    return base.getDouble(columnIndex);
  }

  public float getFloat(int columnIndex) throws SQLException {
    return base.getFloat(columnIndex);
  }

  public int getInt(int columnIndex) throws SQLException {
    return base.getInt(columnIndex);
  }

  public long getLong(int columnIndex) throws SQLException {
    return base.getLong(columnIndex);
  }

  public short getShort(int columnIndex) throws SQLException {
    return base.getShort(columnIndex);
  }

  public void setFetchDirection(int direction) throws SQLException {
    base.setFetchDirection(direction);
  }

  public void setFetchSize(int rows) throws SQLException {
    base.setFetchSize(rows);
  }

  public void updateNull(int columnIndex) throws SQLException {
    base.updateNull(columnIndex);
  }

  public boolean absolute(int row) throws SQLException {
    return base.absolute(row);
  }

  public boolean getBoolean(int columnIndex) throws SQLException {
    return base.getBoolean(columnIndex);
  }

  public boolean relative(int rows) throws SQLException {
    return base.relative(rows);
  }

  public byte[] getBytes(int columnIndex) throws SQLException {
    return base.getBytes(columnIndex);
  }

  public void updateByte(int columnIndex, byte x) throws SQLException {
    base.updateByte(columnIndex,x);
  }

  public void updateDouble(int columnIndex, double x) throws SQLException {
    base.updateDouble(columnIndex,x);
  }

  public void updateFloat(int columnIndex, float x) throws SQLException {
    base.updateFloat(columnIndex,x);
  }

  public void updateInt(int columnIndex, int x) throws SQLException {
    base.updateInt(columnIndex,x);
  }

  public void updateLong(int columnIndex, long x) throws SQLException {
    base.updateLong(columnIndex,x);
  }

  public void updateShort(int columnIndex, short x) throws SQLException {
    base.updateShort(columnIndex,x);
  }

  public void updateBoolean(int columnIndex, boolean x) throws SQLException {
    base.updateBoolean(columnIndex,x);
  }

  public void updateBytes(int columnIndex, byte[] x) throws SQLException {
    base.updateBytes(columnIndex,x);
  }

  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    return base.getAsciiStream(columnIndex);
  }

  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    return base.getBinaryStream(columnIndex);
  }

  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    return base.getUnicodeStream(columnIndex);
  }

  public void updateAsciiStream(int columnIndex, InputStream x, int length) throws
      SQLException {
    base.updateAsciiStream(columnIndex,x,length);
  }

  public void updateBinaryStream(int columnIndex, InputStream x, int length) throws
      SQLException {
    base.updateBinaryStream(columnIndex,x,length);
  }

  public Reader getCharacterStream(int columnIndex) throws SQLException {
    return base.getCharacterStream(columnIndex);
  }

  public void updateCharacterStream(int columnIndex, Reader x, int length) throws
      SQLException {
    base.updateCharacterStream(columnIndex,x,length);
  }

  public Object getObject(int columnIndex) throws SQLException {
    return base.getObject(columnIndex);
  }

  public void updateObject(int columnIndex, Object x) throws SQLException {
    base.updateObject(columnIndex,x);
  }

  public void updateObject(int columnIndex, Object x, int scale) throws
      SQLException {
    base.updateObject(columnIndex,x,scale);
  }

  public String getCursorName() throws SQLException {
    return base.getCursorName();
  }

  public void updateString(int columnIndex, String x) throws SQLException {
    base.updateString(columnIndex,x);
  }

  public byte getByte(String columnName) throws SQLException {
    return base.getByte(columnName);
  }

  public double getDouble(String columnName) throws SQLException {
    return base.getDouble(columnName);
  }

  public float getFloat(String columnName) throws SQLException {
    return base.getFloat(columnName);
  }

  public int findColumn(String columnName) throws SQLException {
    return base.findColumn(columnName);
  }

  public int getInt(String columnName) throws SQLException {
    return base.getInt(columnName);
  }

  public long getLong(String columnName) throws SQLException {
    return base.getLong(columnName);
  }

  public short getShort(String columnName) throws SQLException {
    return base.getShort(columnName);
  }

  public void updateNull(String columnName) throws SQLException {
    base.updateNull(columnName);
  }

  public boolean getBoolean(String columnName) throws SQLException {
    return base.getBoolean(columnName);
  }

  public byte[] getBytes(String columnName) throws SQLException {
    return base.getBytes(columnName);
  }

  public void updateByte(String columnName, byte x) throws SQLException {
    base.updateByte(columnName,x);
  }

  public void updateDouble(String columnName, double x) throws SQLException {
    base.updateDouble(columnName,x);
  }

  public void updateFloat(String columnName, float x) throws SQLException {
    base.updateFloat(columnName,x);
  }

  public void updateInt(String columnName, int x) throws SQLException {
    base.updateInt(columnName,x);
  }

  public void updateLong(String columnName, long x) throws SQLException {
    base.updateLong(columnName,x);
  }

  public void updateShort(String columnName, short x) throws SQLException {
    base.updateShort(columnName,x);
  }

  public void updateBoolean(String columnName, boolean x) throws SQLException {
    base.updateBoolean(columnName,x);
  }

  public void updateBytes(String columnName, byte[] x) throws SQLException {
    base.updateBytes(columnName,x);
  }

  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    return base.getBigDecimal(columnIndex);
  }

  public BigDecimal getBigDecimal(int columnIndex, int scale) throws
      SQLException {
    return base.getBigDecimal(columnIndex,scale);
  }

  public void updateBigDecimal(int columnIndex, BigDecimal x) throws
      SQLException {
    base.updateBigDecimal(columnIndex,x);
  }

  public URL getURL(int columnIndex) throws SQLException {
    return base.getURL(columnIndex);
  }

  public Array getArray(int i) throws SQLException {
    return base.getArray(i);
  }

  public void updateArray(int columnIndex, Array x) throws SQLException {
    base.updateArray(columnIndex,x);
  }

  public Blob getBlob(int i) throws SQLException {
    return base.getBlob(i);
  }

  public void updateBlob(int columnIndex, Blob x) throws SQLException {
    base.updateBlob(columnIndex,x);
  }

  public Clob getClob(int i) throws SQLException {
    return base.getClob(i);
  }

  public void updateClob(int columnIndex, Clob x) throws SQLException {
    base.updateClob(columnIndex,x);
  }

  public Date getDate(int columnIndex) throws SQLException {
    return base.getDate(columnIndex);
  }

  public void updateDate(int columnIndex, Date x) throws SQLException {
    base.updateDate(columnIndex,x);
  }

  public Ref getRef(int i) throws SQLException {
    return base.getRef(i);
  }

  public void updateRef(int columnIndex, Ref x) throws SQLException {
    base.updateRef(columnIndex,x);
  }

  public ResultSetMetaData getMetaData() throws SQLException {
    return base.getMetaData();
  }

  public SQLWarning getWarnings() throws SQLException {
    return base.getWarnings();
  }

  public Statement getStatement() throws SQLException {
    return base.getStatement();
  }

  public Time getTime(int columnIndex) throws SQLException {
    return base.getTime(columnIndex);
  }

  public void updateTime(int columnIndex, Time x) throws SQLException {
    base.updateTime(columnIndex,x);
  }

  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    return base.getTimestamp(columnIndex);
  }

  public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
    base.updateTimestamp(columnIndex,x);
  }

  public InputStream getAsciiStream(String columnName) throws SQLException {
    return base.getAsciiStream(columnName);
  }

  public InputStream getBinaryStream(String columnName) throws SQLException {
    return base.getBinaryStream(columnName);
  }

  public InputStream getUnicodeStream(String columnName) throws SQLException {
    return base.getUnicodeStream(columnName);
  }

  public void updateAsciiStream(String columnName, InputStream x, int length) throws
      SQLException {
    base.updateAsciiStream(columnName,x,length);
  }

  public void updateBinaryStream(String columnName, InputStream x, int length) throws
      SQLException {
    base.updateBinaryStream(columnName,x,length);
  }

  public Reader getCharacterStream(String columnName) throws SQLException {
    return base.getCharacterStream(columnName);
  }

  public void updateCharacterStream(String columnName, Reader reader,
                                    int length) throws SQLException {
    base.updateCharacterStream(columnName,reader,length);
  }

  public Object getObject(String columnName) throws SQLException {
    return base.getObject(columnName);
  }

  public void updateObject(String columnName, Object x) throws SQLException {
    base.updateObject(columnName,x);
  }

  public void updateObject(String columnName, Object x, int scale) throws
      SQLException {
    base.updateObject(columnName,x,scale);
  }

  public Object getObject(int i, Map map) throws SQLException {
    return base.getObject(i,map);
  }

  public void updateString(String columnName, String x) throws SQLException {
    base.updateString(columnName,x);
  }

  public BigDecimal getBigDecimal(String columnName) throws SQLException {
    return base.getBigDecimal(columnName);
  }

  public BigDecimal getBigDecimal(String columnName, int scale) throws
      SQLException {
    return base.getBigDecimal(columnName,scale);
  }

  public void updateBigDecimal(String columnName, BigDecimal x) throws
      SQLException {
    base.updateBigDecimal(columnName,x);
  }

  public URL getURL(String columnName) throws SQLException {
    return base.getURL(columnName);
  }

  public Array getArray(String colName) throws SQLException {
    return base.getArray(colName);
  }

  public void updateArray(String columnName, Array x) throws SQLException {
    base.updateArray(columnName,x);
  }

  public Blob getBlob(String colName) throws SQLException {
    return base.getBlob(colName);
  }

  public void updateBlob(String columnName, Blob x) throws SQLException {
    base.updateBlob(columnName,x);
  }

  public Clob getClob(String colName) throws SQLException {
    return base.getClob(colName);
  }

  public void updateClob(String columnName, Clob x) throws SQLException {
    base.updateClob(columnName,x);
  }

  public Date getDate(String columnName) throws SQLException {
    return base.getDate(columnName);
  }

  public void updateDate(String columnName, Date x) throws SQLException {
    base.updateDate(columnName,x);
  }

  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    return base.getDate(columnIndex,cal);
  }

  public Ref getRef(String colName) throws SQLException {
    return base.getRef(colName);
  }

  public void updateRef(String columnName, Ref x) throws SQLException {
    base.updateRef(columnName,x);
  }

  public Time getTime(String columnName) throws SQLException {
    return base.getTime(columnName);
  }

  public void updateTime(String columnName, Time x) throws SQLException {
    base.updateTime(columnName,x);
  }

  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    return base.getTime(columnIndex,cal);
  }

  public Timestamp getTimestamp(String columnName) throws SQLException {
    return base.getTimestamp(columnName);
  }

  public void updateTimestamp(String columnName, Timestamp x) throws
      SQLException {
    base.updateTimestamp(columnName,x);
  }

  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws
      SQLException {
    return base.getTimestamp(columnIndex,cal);
  }

  public Object getObject(String colName, Map map) throws SQLException {
    return base.getObject(colName,map);
  }

  public Date getDate(String columnName, Calendar cal) throws SQLException {
    return base.getDate(columnName,cal);
  }

  public Time getTime(String columnName, Calendar cal) throws SQLException {
    return base.getTime(columnName,cal);
  }

  public Timestamp getTimestamp(String columnName, Calendar cal) throws
      SQLException {
    return base.getTimestamp(columnName,cal);
  }

public RowId getRowId(int arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public RowId getRowId(String arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public void updateRowId(int arg0, RowId arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateRowId(String arg0, RowId arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public int getHoldability() throws SQLException {
	// TODO Auto-generated method stub
	return 0;
}

public boolean isClosed() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public void updateNString(int arg0, String arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNString(String arg0, String arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNClob(int arg0, NClob arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNClob(String arg0, NClob arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public NClob getNClob(int arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public NClob getNClob(String arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public SQLXML getSQLXML(int arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public SQLXML getSQLXML(String arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public String getNString(int arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public String getNString(String arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public Reader getNCharacterStream(int arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public Reader getNCharacterStream(String arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public void updateNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateBlob(String arg0, InputStream arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateClob(int arg0, Reader arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateClob(String arg0, Reader arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNClob(int arg0, Reader arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNClob(String arg0, Reader arg1, long arg2) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNCharacterStream(int arg0, Reader arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNCharacterStream(String arg0, Reader arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateAsciiStream(int arg0, InputStream arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateCharacterStream(int arg0, Reader arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateAsciiStream(String arg0, InputStream arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateBinaryStream(String arg0, InputStream arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateCharacterStream(String arg0, Reader arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateBlob(int arg0, InputStream arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateBlob(String arg0, InputStream arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateClob(int arg0, Reader arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateClob(String arg0, Reader arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNClob(int arg0, Reader arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void updateNClob(String arg0, Reader arg1) throws SQLException {
	// TODO Auto-generated method stub
	
}

public Object unwrap(Class arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public boolean isWrapperFor(Class arg0) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}
}
