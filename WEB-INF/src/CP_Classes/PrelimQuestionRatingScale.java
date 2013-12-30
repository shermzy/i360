package CP_Classes;

import java.util.*;

public class PrelimQuestionRatingScale {

	private int prelimRatingScaleId; 
	private Vector<String> rating;
	
	public PrelimQuestionRatingScale(int prelimRatingScaleId, Vector<String> rating)
	{
		this.prelimRatingScaleId= prelimRatingScaleId; 
		this.rating = rating;
	}
	
	public int getPrelimRatingScaleId()
	{
		return prelimRatingScaleId;
	}
	
	public Vector<String> getRating()
	{
		return rating;
	}
	
	

}
