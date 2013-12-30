package CP_Classes.TimerSchedule;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import CP_Classes.Create_Edit_Survey;
import CP_Classes.SurveyResult;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblSurvey;


public class ScheduleTask
{

    Timer timer;

    public void TimerRunning()
    {

        timer = new Timer();
        timer.schedule(new RemindTask(),
             		 0,        //initial delay
                     3600*1000);  //interval,  1000=1sec, so it runs every hour
    }
    
   

    class RemindTask extends TimerTask
    {

        public void run()
        {
        	
        	//for calculation purposes, no need to check for dates.
        	//for email puprose, will need to check for dates.

        	Schedule S = new Schedule();
        	HashMap HM = S.getAllSchedules();
        
        	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        	String DATE_FORMAT = "HH";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
            sdf.setTimeZone(TimeZone.getDefault());   //get timezone

        	
            //System.out.println("Current hour: " + sdf.format(cal.getTime())); //prints out current hour(0-24) for debug purpose
            int currenthour = Integer.parseInt(sdf.format(cal.getTime())); //get the current hour(0-24)
            Integer hour = new Integer(currenthour);
            //System.out.println("Contain "+HM.containsKey(hour));
            if (HM.containsKey(hour) )
            {
            	System.out.println("Time :"+cal.getTime());
            	 System.out.println("Current hour: " + sdf.format(cal.getTime()));
            	//this is where you call the javabean or whatever program that will run every x sec/min/hour interval
            	//System.out.println("program ran");
                // do calculation
            	System.out.println( HM.get(hour));
                Vector v = (Vector)HM.get(hour);
                 
                Date timeStamp = new java.util.Date();
                SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        		
      			String toDay  =  dFormat.format(timeStamp);
 				
                for(int i=0; i<v.size(); i++) {
                	//System.out.println("Elements "+v.elementAt(i));
                	if(v.elementAt(i).equals("Calculation") || v.elementAt(i).equals("Job Status")) {
                		// do calculation
                		System.out.println("Do the : "+v.elementAt(i));
                		SurveyResult SR = new SurveyResult();
                		Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
                		Vector rs_SurveyDetail = new Vector();
                		 if (v.elementAt(i).equals("Calculation"))
                		 {
                			 //By Hemilda Date 17/08/2008 get list of survey that have open status and for all org
                			 rs_SurveyDetail = CE_Survey.getRecord_Survey();	
                		 }else if (v.elementAt(i).equals("Job Status")){
//                			By Hemilda Date 17/08/2008 get list of survey will all status and for all org
                			 rs_SurveyDetail = CE_Survey.getRecord_Survey(0, 0, 0);	
                		 }
                		//while(rs_SurveyDetail.next())
                		for(int j=0; j<rs_SurveyDetail.size(); j++)
                		{
                			votblSurvey voSurv=(votblSurvey)rs_SurveyDetail.elementAt(j);
                			int iSurveyID = voSurv.getSurveyID();
                			
                			//String sSurveyName = voSurv.getSurveyName();
                			String sOpenedDate = voSurv.getDateOpened();
                			String sDeadlineDate = voSurv.getDeadlineSubmission();
                			int iSurveyStatus = voSurv.getSurveyStatus();
                			String sOrganisationName = voSurv.getOrganizationName();
                			//System.out.println("Company : "+voSurv.getCompanyName());		
                			//System.out.println("Organisations : "+voSurv.getOrganizationName());
                			//System.out.println("Survey Name : "+voSurv.getSurveyName());
               				// 1 = open, 2 = closed, 3 = Not Commissioned
              					
               				if(v.elementAt(i).equals("Job Status")) {
               					
               					//if the opened date is today and the survey status is not open
               					// then set it to open
               					if(sOpenedDate.equals(toDay) && iSurveyStatus != 1) {
               						//System.out.println("");
                        			//System.out.println("Company : "+voSurv.getCompanyName());		
                        			//System.out.println("Organisations : "+voSurv.getOrganizationName());
                        			//System.out.println("Survey Name : "+voSurv.getSurveyName());
                        			
               						CE_Survey.updateSurveyStatus(iSurveyID, 1, sOrganisationName);
               					}
               					
               					//if the deadline date is today and the survey status is not closed
               					//then set it to close
               					if(sDeadlineDate.equals(toDay) && iSurveyStatus != 2) {
               						//System.out.println("");
                        			//System.out.println("Company : "+voSurv.getCompanyName());		
                        			//System.out.println("Organisations : "+voSurv.getOrganizationName());
                        			//System.out.println("Survey Name : "+voSurv.getSurveyName());

               						CE_Survey.updateSurveyStatus(iSurveyID, 2, sOrganisationName);
                   				}
	                				
	                				
                			} else {
            					// get the list of targets in the survey
                				Vector targetID = SR.TargetID(iSurveyID, 0, 0, 0);
                				
                				
                				for(int k=0; k<targetID.size(); k++) {
                					voUser voUsr = (voUser)targetID.elementAt(k);
                					int targetLoginId = voUsr.getTargetLoginID();
                					//Changed by Ha 26/06/08 to calculate only when all raters have completed their questionnaire
                					//There is no need to call method update status because in the calculateStatus there is part calling
                					//update status already
                 					if (SR.isAllRaterRated(iSurveyID, 0, targetLoginId))
                					{
                 						//System.out.println("");
                            			//System.out.println("Company : "+voSurv.getCompanyName());		
                            			//System.out.println("Organisations : "+voSurv.getOrganizationName());
                            			//System.out.println("Survey Name : "+voSurv.getSurveyName());

                 						//System.out.println("All rater have done this survey");
                						// do calculation and update the calculation status
                						SR.CalculateStatus(targetLoginId, iSurveyID, 0, 0, 0, 0);
                					
                						// if all the raters assigned to the target have been calculated
                						// set the AdminCalc Status to 0 which means it is SystemCalc
                						//SR.updateAdminStatusSystemCalc(targetLoginId, iSurveyID, 0, 0, 0);
                					}
                				}
                			}
                		}
                	 }
                 }
                 
            }
            System.out.println("Calculation & Job Status have finished");
			
		}//end of run()
    }//end of class RemindTask

    public static void main(String args[])
    {
    	
      ScheduleTask a = new ScheduleTask();
      a.TimerRunning();
    
    	/*Schedule S = new Schedule();
    	HashMap HM = S.getAllSchedules();
    	
    	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    	String DATE_FORMAT = "HH";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());   //get timezone
      
        System.out.println("Current hour: " + sdf.format(cal.getTime())); //prints out current hour(0-24) for debug purpose
        int currenthour = Integer.parseInt(sdf.format(cal.getTime())); //get the current hour(0-24)
        Integer hour = new Integer(currenthour);
        
        if (HM.containsKey(hour)) 
        {
        	//this is where you call the javabean or whatever program that will run every x sec/min/hour interval
        	System.out.println("program ran");
            // do calculation
            Vector v = (Vector)HM.get(hour);
            System.out.println(v.size());
            System.out.println(HM.get(new Integer(currenthour)));
        }*/
    	
    }
    
    
}
