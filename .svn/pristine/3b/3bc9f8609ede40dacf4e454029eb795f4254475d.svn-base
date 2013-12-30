package CP_Classes;
import java.sql.*;
import java.util.*;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;

public class Translate
{
	//Added new hashtable to store the translated languages
	private Hashtable htArr [];
	public Hashtable ht = null;
	private Database db;
	private Setting setting;
	
	public Translate()
	{
		db = new Database();
		setting = new Setting();
		ht = new Hashtable();
		
		/*Chun Yeong 1 Aug 2011
		 *Initialized size 2, 
		 *0 is for Indonesian
		 *1 is for Thai
		 *2 is for Korean
		 *3 is for Chinese(Simplified)
		 *4 is for Chinese(Traditional)
		*/
		htArr = new Hashtable [5]; 
	}
	
	/**
	 * Checks the hashtable which stores the translated words from the table
	 * @param language
	 * @param inputWord
	 * @return the translated word needed
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public String tslt(int language, String inputWord)
    {	    	
		//System.out.println("Hash table length : "+htArr.length);
    	String wordCopy = inputWord.toUpperCase().trim();
    	String temp = inputWord;
    	    	
    	if(language != 0) {
    		temp = (String)htArr[language-1].get(wordCopy);
	    	if (temp != null && !temp.equals(""))
				temp = (String)htArr[language-1].get(wordCopy);
			//Else return the english words
			else
				temp = inputWord;
    	}
    	
    	return temp;

    	//return inputWord;
    }
	
	/**
	 * To populate the hashtable
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public void populateHashtable(){

		for(int i=1; i<=htArr.length; i++)
			try {
				htArr[i-1]  = getTranslation(i);
			}  catch (Exception e) {
				e.printStackTrace();
			}			
	}
	
	/**
	 * To retrieve the translated words from the database. If no translated word is found, the English word is returned.
	 * @param language
	 * @return a hashtable which contains a list of translated words
	 * @throws SQLException
	 * @throws Exception
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public Hashtable getTranslation(int language) throws SQLException, Exception
 	{
 		Hashtable ht = new Hashtable();
 		Connection con=null;
 		Statement stmt = null;
 		ResultSet rs=null;
 		
 		try{
 			con = ConnectionBean.getConnection();
 			// Create a result set containing all data from Translation table
 			stmt = con.createStatement();
        	rs = stmt.executeQuery("SELECT * FROM TB_Translation");
		
        	String lang = "";
        	if(language > 0)
        		lang = Integer.toString(language);
        
        	while (rs.next()) 
        	{        	
        		String eng = rs.getString("EngText").toUpperCase().trim();
        		String oth = eng;
        		if(rs.getString("Text" + lang) != null)
        			oth = rs.getString("Text" + lang).trim();
        	
            	//Get the data from the row using the column index
            	ht.put(eng,oth);          
        	}
 		} catch(Exception E){
 			System.err.println("Translate.java - getTranslation - " + E);
 		} finally{
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(stmt); //Close statement
	        ConnectionBean.close(con); //Close connection
 		}
       return ht;
    }
	
	public Hashtable getTranslation() throws SQLException, Exception
 	{
 		String sql = "SELECT * FROM TB_Translation";
		
 		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
           
        	while (rs.next()) 
            {
                // Get the data from the row using the column index
        		// Change IndoText to Text1 to accommodate to the database changes, Chun Yeong 13 Jul 2011
                ht.put(rs.getString("EngText").toUpperCase(), rs.getString("Text1"));
                
            }
       
        }
        catch(Exception E) 
        {
            System.err.println("Translate.java - getTranslation - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
		
        
        return ht;
        
    }
    
    public String tslt(String inputWord)
    {	
    	//---Thi spart is commented off for MineBea which encountered the out of memory error
    	/*String wordCopy = inputWord.toUpperCase();
    	String temp = (String)ht.get(wordCopy);
    	
    	//If setting is not english language
    	if (setting.LangVer == 2)
    	{	
    		//If the translated word exist, return it
    		if (temp != null){
    			temp = (String)ht.get(wordCopy);
    		}
    		//Else return the english words
    		else{
    			temp = inputWord;
    		}
    	}
    	//If setting is english, return the string
    	else{
    		temp = inputWord;
    	}
    	return temp;*/
    	
    	return inputWord;
    }
    
    /**
     * Translate English word to other language
     * @param inputWord
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public String tsltEng(String inputWord) throws SQLException, Exception
    {	
    	String sTranslated = "";
    	// Change IndoText to Text1 to accommodate to the database changes, Chun Yeong 13 Jul 2011
    	String sSQL = "SELECT Text1 FROM TB_Translation WHERE EngText = '" + inputWord.trim() + "'";
        
        Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sSQL);
           
			if (rs.next()) 
			{
				// Change IndoText to Text1 to accommodate to the database changes, Chun Yeong 13 Jul 2011
				sTranslated = rs.getString("Text1");
			}

        }
        catch(Exception E) 
        {
            System.err.println("Translate.java - tsltEng - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
        return sTranslated;
    }
}