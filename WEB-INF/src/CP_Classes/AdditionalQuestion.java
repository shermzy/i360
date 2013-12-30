package CP_Classes;

public class AdditionalQuestion implements Comparable{
	
	private int addQnId;
	private int fkSurveyId;
	private String question; 
	
	public AdditionalQuestion(int addQnId, int fkSurveyId, String question)
	{
		this.addQnId = addQnId;
		this.fkSurveyId = fkSurveyId; 
		this.question = question;
		
	}
	
	public int getAddQnId()
	{
		return addQnId;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	/**
	 * @author xukun
	 * @param o
	 * @return
	 */
	public int compareTo(Object o){
		return this.addQnId - ((AdditionalQuestion)o).getAddQnId();
	}

}