package CP_Classes;

import excel.*; 
import java.io.*; 
 
public class JCreateExpenseTemplate { 
 
  public JCreateExpenseTemplate() { 
  } 
 
  public static void main(String[] args) { 
    try { 
      JCreateExpenseTemplate myInstance = new JCreateExpenseTemplate(); 
      String strHostName      = "127.0.0.1"; 
      String strUserID        = null; 
      String strPassword      = null; 
      String strMSOfficeHost  = "127.0.0.1"; 
 
      // set command line parameters 
      if (args.length == 4) { 
        strHostName     = args[0]; 
        strUserID       = args[1]; 
        strPassword     = args[2]; 
        strMSOfficeHost = args[3]; 
      } else if (args.length > 0) { 
        System.out.println( 
            "Usage:  java JCreateExpenseTemplate [hostname] [username] [password] [excelhost]"); 
        System.exit(1); 
      } 
      String strExcelFilename = "C:\\temp"; 
      try { 
        File f = new File(""); 
        strExcelFilename = f.getAbsolutePath(); 
      } catch (Exception ex) { 
      } 
            
      strExcelFilename = "Expense.xls"; 
 
      myInstance.createExpenseTemplate( 
                                      strHostName, 
                                      strUserID, 
                                      strPassword, 
                                      strMSOfficeHost, 
                                      strExcelFilename, 
                                      "Template", 
                                      "Intrinsyc Software, Inc", 
                                      XlSaveAsAccessMode.xlShared, 
                                      true, 
                                      false 
                                      ); 
    } catch(Exception e) { 
      e.printStackTrace(); 
    } finally { 
      // Release all remote objects that haven't already been garbage collected. 
      com.linar.jintegra.Cleaner.releaseAll(); 
    } 
  } 
 
  /** 
  * This function creates an Excel expense template on either local machine 
  *      or remote machine (By DCOM) which runs Microsoft Office. 
  * @param strDomainName - [String] the domain name or host name for DCOM 
  *      authentication, null means localhost. You may input host 
  *      name for the machine installing and running Microsoft 
  *      Office. This parameter will be ignored when strUserID is null. 
  * @param strUserID - [String] the user ID for the domain name or host name 
  *      for DCOM authentication, null means the parameters of 
  *      strDomainName and strUserPassword will be ignored, i.e. the 
  *      sample will run at the same machine as the 
  *      machine installing and running Microsoft Office. 
  * @param strUserPassword - [String] the password for the domain name or host name 
  *      for DCOM authentication, null means empty password. This 
  *      parameter will be ignored when strUserID is null. 
  * @param strMSOfficeHost - [String] the host name for the host running 
  *      Microsoft Office in for DCOM authentication, 
  *      null means localhost. This parameter will be ignored when 
  *      strUserID is null. 
  * @param strWorkbookName - [String] The template file name of the new workbook 
  * @param strWorksheetName - [String] The worksheet name of template workbook 
  * @param strCompanyName - [String] The company name displayed on the 
  *      expense tempalte. 
  * @param iOpeningMode - [int] The access mode when new template file is saved. 
  *      Can be one of the following constants: XlSaveAsAccessMode.xlShared 
  *      (shared list), XlSaveAsAccessMode.xlExclusive (exclusive mode), 
  *      or XlSaveAsAccessMode.xlNoChange (don't change the access mode). 
  * @param bExcelAppVisible - [boolean] Indicates if showing the running Microsoft 
  *      Office as visible window when this application is running. If it 
  *      is false, Microsoft Office will run in the background. 
  * @param bExcelAppDisplayAlerts - [boolean] Indicates if display alert 
  *      dialog box when necessary in running this application. If this 
  *      is true, the alert dialog box will be displayed and 
  *      require user's interaction. 
  */ 
  public void createExpenseTemplate( 
                            String strDomainName, 
                            String strUserID, 
                            String strUserPassword, 
                            String strMSOfficeHost, 
                            String strWorkbookName, 
                            String strWorksheetName, 
                            String strCompanyName, 
                            int iOpeningMode, 
                            boolean bExcelAppVisible, 
                            boolean bExcelAppDisplayAlerts) { 
    try { 
      int i = 0; 
      int iTemp = 0; 
      Integer iTemp2 = null; 
      double dTemp = 0; 
      Double dTemp2 = null; 
      Application xlApp = null; 
      Workbooks workbooks = null; 
      Workbook wb = null; 
      Sheets worksheets = null; 
      Worksheet ws = null; 
      Range rg = null; 
      Font ft = null; 
      Borders bds = null; 
      Border bd = null; 
 
      ////////////////////////////////////////////// 
      // DCOM authentication 
      if( strUserID != null ) { 
        if (strDomainName == null) { 
          strDomainName = "127.0.0.1"; 
        } 
        if (strUserPassword == null) { 
          strUserPassword = ""; 
        } 
        com.linar.jintegra.AuthInfo.setDefault( 
                                              strDomainName, 
                                              strUserID, 
                                              strUserPassword); 
      } 
 
      ////////////////////////////////////////////// 
      // operating Application object 
      if (strMSOfficeHost != null) { 
        xlApp = new Application( strMSOfficeHost ); 
      } else { 
        xlApp = new Application(); 
      } 
      
      xlApp.setDefaultFilePath("C:\\temp");
 
      xlApp.setVisible( bExcelAppVisible ); 
      xlApp.setDisplayAlerts( bExcelAppDisplayAlerts ); 
 
      ///////////////////////////////////////////// 
      // operating Workbook object 
      workbooks = xlApp.getWorkbooks(); 
 
      wb=workbooks.add( null ); 
 
      ///////////////////////////////////////////// 
      // Operating Worksheets and Worksheet objects 
      worksheets = wb.getWorksheets(); 
 
      iTemp = worksheets.getCount(); 
 
      iTemp2 = new Integer( 1 ); 
      while( iTemp > 1 ) { 
        ws = new Worksheet( worksheets.getItem( iTemp2 ) ); 
        ws.delete(); 
        iTemp--; 
      } 
      ws = new Worksheet( worksheets.getItem( iTemp2 ) ); 
      ws.setName( strWorksheetName ); 
 
      ///////////////////////////////////////////// 
      // Operating Range object 
 
      // basic width 
      rg = ws.getRange( "B:B", null ); 
      rg.clearContents(); 
 
      dTemp2 = (Double)rg.getColumnWidth(); 
      dTemp = dTemp2.doubleValue(); 
      dTemp *= 4; 
      dTemp2 = new Double( dTemp ); 
      rg.setColumnWidth( dTemp2 ); 
 
      // title 
      rg = ws.getRange( "D1", null ); 
      drawTitle( 
                "EXPENSE REPORT", 
                rg, 
                1.2, 
                true); 
 
      // light grids 
      rg = ws.getRange( "A11:J28,F29:J29", null ); 
      bds = rg.getBorders(); 
      bds.setLineStyle( new Integer( XlLineStyle.xlContinuous ) ); 
 
      // heavy borders 
      rg = ws.getRange( "A10:J10,C29:J29,J30:J32", null ); 
      bds = rg.getBorders(); 
      bds.setLineStyle( new Integer( XlLineStyle.xlContinuous ) ); 
 
      Integer iWeight = (Integer)bds.getWeight(); 
      i = iWeight.intValue(); 
      i++; 
      iWeight = new Integer( i ); 
      bds.setWeight( iWeight ); 
 
      rg = ws.getRange( "A11:A28", null ); 
      bds = rg.getBorders(); 
      bds.setLineStyle( new Integer( XlLineStyle.xlContinuous ) ); 
 
      bd = bds.getItem( XlBordersIndex.xlEdgeLeft ); 
      bd.setWeight( iWeight ); 
 
      rg = ws.getRange( "J11:J28", null ); 
      bds = rg.getBorders(); 
      bds.setLineStyle( new Integer( XlLineStyle.xlContinuous ) ); 
 
      bd = bds.getItem( XlBordersIndex.xlEdgeRight ); 
      bd.setWeight( iWeight ); 
 
      rg = ws.getRange( "A28:B28", null ); 
      bds = rg.getBorders(); 
      bds.setLineStyle( new Integer( XlLineStyle.xlContinuous ) ); 
 
      bd = bds.getItem( XlBordersIndex.xlEdgeBottom ); 
      bd.setWeight( iWeight ); 
 
      // basic values 
      rg = ws.getRange( "A10:J10", null ); 
      rg.setHorizontalAlignment( new Integer( XlHAlign.xlHAlignCenter ) ); 
      Object[] newValue = { 
                "Date", "Description", "Entertain", "Air", 
                "Hotel", "Meals", "Transport", "Fuel", "Other", "Total" 
      }; 
      rg.setValue2(newValue);
 
      rg = ws.getRange( "B29", null ); 
      rg.setHorizontalAlignment( new Integer( XlHAlign.xlHAlignRight ) ); 
      rg.setValue2("TOTALS" ); 
 
      rg = ws.getRange( "I30:I32", null ); 
      rg.setHorizontalAlignment( new Integer( XlHAlign.xlHAlignRight ) ); 
      Object[][] newValue2 = { 
        { "Total" }, 
        { "Cash received from Company (ADVANCE)" }, 
        { "Balance owing (to)/from Company" } 
      }; 
      rg.setValue2(newValue2);
 
      // formula 
      setExpenseTotalFormula2( 
                              ws, 
                              'C', 
                              'I' ); 
 
      setExpenseTotalFormula( 
                            ws, 
                            11, 
                            28); 
 
      rg = ws.getRange( "J29", null ); 
      rg.setFormula( "=SUM(C29:I29)" ); 
 
      rg = ws.getRange( "J30", null ); 
      rg.setFormula( "=SUM(C29:I29)" ); 
 
      rg = ws.getRange( "J32", null ); 
      rg.setFormula( "=J30-J31" ); 
 
      // Other information 
      rg = ws.getRange( "A2", null ); 
      rg.setValue2(strCompanyName ); 
      rg = ws.getRange( "A4", null ); 
      rg.setValue2("DATE" ); 
      rg = ws.getRange( "C4", null ); 
      rg.setValue2("PERIOD COVERED" ); 
      rg = ws.getRange( "F4", null ); 
      rg.setValue2("TO" ); 
      rg = ws.getRange( "I4", null ); 
      rg.setValue2("CURRENCY" ); 
      rg = ws.getRange( "A6", null ); 
      rg.setValue2("NAME" ); 
      rg = ws.getRange( "E6", null ); 
      rg.setValue2("EMPLOYEE NUMBER" ); 
      rg = ws.getRange( "H6", null ); 
      rg.setValue2("DEPARTMENT" ); 
      rg = ws.getRange( "A8", null ); 
      rg.setValue2("PURPOSE OF TRIP & DESTINATION" ); 
      rg = ws.getRange( "A30", null ); 
      rg.setValue2("[SIGNATURE]" ); 
      rg = ws.getRange( "A31", null ); 
      rg.setValue2("Employee" ); 
      rg = ws.getRange( "A33", null ); 
      rg.setValue2("Dept" ); 
      rg = ws.getRange( "A35", null ); 
      rg.setValue2("Acct Dept" ); 
 
      rg = ws.getRange( "A2", null ); 
      ft = rg.getFont(); 
      ft.setItalic( new Boolean( true ) ); 
 
      rg = ws.getRange( "A4,C4,F4,I4,A6,E6,H6,A8,A30,A31,A33,A35", null ); 
      ft = rg.getFont(); 
      ft.setBold( new Boolean( true ) ); 
 
      rg = ws.getRange( "F4", null ); 
      rg.setHorizontalAlignment( new Integer( XlHAlign.xlHAlignCenter ) ); 
 
      rg = ws.getRange( "I4,E6,H6", null ); 
      rg.setHorizontalAlignment( new Integer( XlHAlign.xlHAlignRight ) ); 
 
      rg = ws.getRange( "B4,E4,G4,J4,B6,F6,I6,J6,C8:J8,B31,B33,B35", null ); 
      drawBottomBorders( 
                        rg, 
                        1); 
      rg.setHorizontalAlignment( new Integer( XlHAlign.xlHAlignLeft ) ); 
 
      ///////////////////////////////////////////////////////// 
      // print setting 
      PageSetup ps=ws.getPageSetup(); 
 
      ps.setOrientation(XlPageOrientation.xlLandscape); 
 
      ///////////////////////////////////////////////////////// 
      // save 
      wb.setSaved( true ); 
      //wb.saveAs( strWorkbookName, null, null, null, null, null, iOpeningMode, null, null, null, null, null); 

 	wb.saveCopyAs(strWorkbookName);
 
        // clear up 
      //wb.close( new Boolean( true ), null, null ); 
      //workbooks.close(); 
      //xlApp.quit(); 
    } catch(Exception e) { 
			e.printStackTrace(); 
    } finally { 
    } 
  } 
 
  /** 
   * Helper function to create the title of Expense template. 
   * 
   * @param strTitle  - [String] title text string 
   * @param rgTarget  - [Range] the Range object where you will write 
   *      the title string. 
   * @param dAddedRowHeightFactor - [double] The distance between 
   *      default row height and expected row height. 
   * @param bSetBold  - [boolean] indicates if set title 
   *      text string to bold. 
   */ 
  public static void drawTitle( 
                              String strTitle, 
                              Range rgTarget, 
                              double dAddedRowHeightFactor, 
                              boolean bSetBold) { 
    try { 
      double dTemp = 0; 
      Double dTemp2 = null; 
 
      rgTarget.clearContents(); 
 
      dTemp2 = ( Double )rgTarget.getRowHeight(); 
      dTemp = dTemp2.doubleValue(); 
      dTemp *= dAddedRowHeightFactor; 
      dTemp2 = new Double( dTemp ); 
      rgTarget.setRowHeight( dTemp2 ); 
 
      Font ft = rgTarget.getFont(); 
      ft.setBold( new Boolean( bSetBold ) ); 
 
      dTemp2 = ( Double )ft.getSize(); 
      dTemp = dTemp2.doubleValue(); 
      dTemp++; 
      dTemp2 = new Double( dTemp ); 
      ft.setSize( dTemp2 ); 
 
      rgTarget.setValue2(strTitle ); 
    } catch(Exception e) { 
      e.printStackTrace(); 
    } finally { 
    } 
  } 
 
  /** 
  * Helper function to draw the bottom borders of Expense template. 
  * 
  * @param rgTarget  - [Range] The Range object represents the area 
  *      where the bottom borders will be drawn. 
  * @param iAddedWeight - [int] The distance between default line 
  *      weight and expected line weight. 
  */ 
  public static void drawBottomBorders( 
                                Range rgTarget, 
                                int iAddedWeight) { 
    try { 
      Borders bds = rgTarget.getBorders(); 
      Integer iLineStyle = new Integer( XlLineStyle.xlContinuous ); 
 
      Integer iWeight = (Integer)bds.getWeight(); 
      int i = iWeight.intValue(); 
      i += iAddedWeight; 
      iWeight = new Integer(i); 
 
      Border bd = bds.getItem( XlBordersIndex.xlEdgeBottom ); 
      bd.setLineStyle( iLineStyle ); 
      bd.setWeight( iWeight ); 
    } catch(Exception e) { 
      e.printStackTrace(); 
    } finally { 
    } 
  } 
 
  /** 
  * Helper function to set up formula for a group of ranges 
  * 
  * @param ws        - [Worksheet] The worksheet object 
  * @param iStartRow - [int] starting raw for setting up formula 
  * @param iEndRow   - [int] ending raw for setting up formula 
  */ 
  public static void setExpenseTotalFormula( 
                                            Worksheet ws, 
                                            int iStartRow, 
                                            int iEndRow) { 
    try { 
      String strRange = null; 
      String strFormula = null; 
      Range rgTarget = null; 
      int i = 0; 
 
      for( i = iStartRow; i <= iEndRow; i++ ) { 
        strRange = "J" + i; 
        strFormula = "=SUM(C" + i + ":I" + i + ")"; 
        rgTarget = ws.getRange( strRange, null ); 
        rgTarget.setFormula( strFormula ); 
      } 
    } catch( Exception e ) { 
      e.printStackTrace(); 
    } finally { 
    } 
  } 
 
  /** 
  * Helper function to set up formula for a group of ranges 
  * 
  * @param ws            - [Worksheet] The worksheet object 
  * @param cStartColumn  - [char] starting column for setting up formula 
  * @param cEndColumn    - [char] ending column for setting up formula 
  */ 
  public static void setExpenseTotalFormula2( 
                                            Worksheet ws, 
                                            char cStartColumn, 
                                            char cEndColumn) { 
    try { 
      String strRange = null; 
      String strFormula = null; 
      Range rgTarget = null; 
      char i = 0; 
 
      for( i = cStartColumn; i <= cEndColumn; i++ ) { 
        strRange = i + "29"; 
        strFormula = "=SUM(" + i + "11:" + i + "28)"; 
        rgTarget = ws.getRange( strRange, null ); 
        rgTarget.setFormula( strFormula ); 
      } 
    } catch(Exception e) { 
      e.printStackTrace(); 
    } finally { 
    } 
  } 
} 

