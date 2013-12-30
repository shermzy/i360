package CP_Classes.TimerSchedule;

import java.sql.*;

import java.util.HashMap;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;

public class Schedule {
	
	/**
	 * Retrieve all the schedules
	 * 
	 * @return all the schedules in HashMap.
	 */
	public HashMap getAllSchedules() 
	{
		
	
		String rel = "SELECT * FROM [tblTimerSchedule] INNER JOIN " ;
		rel  = rel +"[tblTask] ON [tblTimerSchedule].FKTask = [tblTask].PKTask INNER JOIN " ;
		rel  = rel +"[tblTime] ON [tblTimerSchedule].FKTime = [tblTime].PKHour order by Hourly ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		
		HashMap HM = new HashMap();
		//System.out.println("getAllSchedule :"+rel);
		int iHour = 0;
		String iTask = "";
		
		//count will be used as the counter to check if it's the first record.
		int count = 0;
		Vector v = new Vector();
		
		int iTotal = getAllSchedulesCount();
		
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(rel);
			//By Hemilda Date 17/08/2008 edit vector, so can add more than one task for each hourly
			while(rs.next()) {
				int iHourly = rs.getInt("Hourly");
				String sTask = rs.getString("Task");

				
				// if count is 0 then initialise iHour to iHourly value
				// and increment the counter.
				if(count == 0) {
					iHour = iHourly;	
					iTask = sTask;
					v.add(sTask);
				}
				count++;
				// check that the hourly from previous record is the same as the current record
				// add new task if it's the same
				if(iHour == iHourly && count != 0 ) {
					if (!iTask.equalsIgnoreCase(sTask))
						v.add(sTask);
				} 
				
				// if total record is 1 or hourly from previous record is not the same as the current record
				// add the vector to the hashmap as the value and hour as the key
				if(iHour != iHourly || iTotal == 1) {
					
					HM.put(new Integer(iHour), v);
					iHour = iHourly;
					//Changed by Ha 25/06/08 
					//Problem with old code: the reference in the hashmap will be cleared as well
	
					v = new Vector();
					iTask = sTask;
					v.add(sTask);
					//v.clear()
					
					//if the counter is equal to the total number and the counter is more than 1 then
					//add the task.
					if(iTotal == count && count > 1) {
						//v.add(sTask);
						HM.put(new Integer(iHour), v);
					}
				} else if(iTotal == count && count > 1) {
					Integer hour = new Integer(iHour);					
					HM.put(hour, v);
				
					
				}

			}
		}catch(Exception ex){
			System.out.println("Schedule.java - getAllSchedules - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
	
		return HM;
	}
	
	/**
	 * count the schedules available
	 * 
	 * @return total no. of schedules
	 */
	public int getAllSchedulesCount() 
	{

		String rel = "SELECT COUNT(*) FROM [tblTimerSchedule] INNER JOIN " ;
		rel  = rel + "[tblTask] ON [tblTimerSchedule].FKTask = [tblTask].PKTask INNER JOIN " ;
		rel  = rel + "[tblTime] ON [tblTimerSchedule].FKTime = [tblTime].PKHour";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		//System.out.println("getAllSchedulesCount: "+rel);
		int iTotal = 0;
		
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(rel);
			
			if(rs.next()) {
				iTotal = rs.getInt(1);
			}
			
		}catch(Exception ex){
			System.out.println("Schedule.java - getAllSchedules - "+ex.getMessage());
		}
		
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return iTotal;
	}
	
	   public static void main(String args[])
	    {
		   Schedule data = new Schedule();
		  data.getAllSchedules() ;
		   
	    }
	
}
