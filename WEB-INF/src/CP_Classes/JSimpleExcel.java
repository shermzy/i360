package CP_Classes;

/**
 * @(#)JSimpleExcel.java        1.00 20 September 2001
 * <p>
 * Copyright (c) 1994-2002 Intrinsyc Software, Inc.
 * 10th Floor, 700 West Pender Street, Vancouver, BC, V6C 1G8, Canada.
 * All rights reserved.
 * <p>
 * This software describes how to use basic functions in Excel JAPI.
 * It is a companion of Microsoft Excel 2000 Java Programmer's Guide.
 * <p>
 * JSimpleExcel contains basic usage of Microsoft Excel spreadsheet in Java
 * by using J-Integra.
 * @version 	1.00 20 September 2001
 */

import excel.*;
import java.util.Date;

public class JSimpleExcel {

  public static void main(java.lang.String[] args) {
  //	public void test() {

    try {
      // Create an instance of Excel.Application.

        Application app     = new Application();
        app.setVisible(true);   // Nice to see what is happening

        // register an AppListener with Excel
        AppListener appListener = new AppListener();
        app.addAppEventsListener(appListener);

        // Use Excel objects to get at a range in the displayed Worksheet
        Workbooks workbooks = app.getWorkbooks();
        Workbook workbook   = workbooks.add(null);
        Sheets worksheets   = workbook.getWorksheets();
        Worksheet sheet     = new Worksheet(worksheets.add(null, null, null, null));
        Range range         = sheet.getRange("A1:C3", null);

        // New contents for the range -- notice the standard Java types
        Object[][] newValue = {
            { "defe",            new Boolean(false), new Double(98765.0/12345.0)},
            { new Date(),        new Integer(5454),  new Float(22.0/7.0)        },
            { new Boolean(true), "dffe",             new Date()                 }
                                    };

        
        range.setValue2(newValue); // Update the spreadsheet

        Thread.sleep(10000);  // Sleep 10 seconds
        // maybe you want to change a value in the spreadsheet

        // unregister an AppListener with Excel
        app.removeAppEventsListener(appListener);

        // Get the new content of the range
        Object[][] values = (Object[][])range.getValue2();

        // Print them out.  Again, the values are standard Java types
        for(int i = 0; i < values.length; i++) {
          for(int j = 0; j < values[i].length; j++) {
            System.out.print(values[i][j] + "\t");
          }
          System.out.println();
        }

        // False means don't prompt to save changes
        //workbook.close(new Boolean(false), null, null);
        //app.quit();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // Release all remote objects that haven't already been garbage collected.
        com.linar.jintegra.Cleaner.releaseAll();
    }
  }
}

class AppListener extends excel.AppEventsAdapter {
  boolean cancelRightClick = true;
  public void newWorkbook(AppEventsNewWorkbookEvent theEvent) {
    System.out.println("newWorkbook Event");
  }
  public void sheetSelectionChange(AppEventsSheetSelectionChangeEvent theEvent) {
    System.out.println("sheetSelectionChange Event");
  }
  public void sheetBeforeRightClick(AppEventsSheetBeforeRightClickEvent theEvent) {
    System.out.println("sheetBeforeRightClick Event.  Cancelling?: " + cancelRightClick);
    theEvent.setCancel(cancelRightClick);
    cancelRightClick = !cancelRightClick;
  }
}

