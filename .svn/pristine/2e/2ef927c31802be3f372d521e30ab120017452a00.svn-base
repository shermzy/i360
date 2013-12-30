package CP_Classes;

import java.util.*;

public class AdditionalQuestionBean {

	int questionCount =0 ;
	ArrayList<Integer> AL = new ArrayList<Integer>();
	
	public AdditionalQuestionBean()
	{
		
	}
	
	public void addQuestionToAnswerCountList()
	{
		AL.add(0);
	}
	
	public void resetAnswerCountList()
	{
		AL = new ArrayList<Integer>();
	}
	
	public ArrayList<Integer> getAnswerCountList()
	{
		return AL;
	}
	
	public int getAnswerCount(int pos)
	{
		return AL.get(pos);
	}
	
	public void addAnswer(int pos){
		if(AL.size()>pos)
		{
			AL.set(pos, AL.get(pos)+1);
		}
	}
	
	public void setAnswerCount(int pos, int count)
	{
		AL.set(pos, count);
	}
	
	public void setQuestionCount(int questionCount)
	{
		this.questionCount = questionCount;
	}
	
	public int getQuestionCount()
	{
		return questionCount;
	}
	
}
