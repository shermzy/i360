package CP_Classes.vo;

public class voCompetencyQuestion {

	private int	iPKCompetencyQuestion = 0; 	 
	private int	iFKRScale = 0;	
	private int	iFKCompetency = 0;	
	private String	sCompetencyQuestionDesc = "";
	private int	iCompetencyQuestionEnum = 0;
	
	public String getCompetencyQuestionDesc() 
	{
		return sCompetencyQuestionDesc;
	}
	public void setCompetencyQuestionDesc(String competencyQuestionDesc) {
		sCompetencyQuestionDesc = competencyQuestionDesc;
	}
	public int getCompetencyQuestionEnum() {
		return iCompetencyQuestionEnum;
	}
	public void setCompetencyQuestionEnum(int competencyQuestionEnum) {
		iCompetencyQuestionEnum = competencyQuestionEnum;
	}
	public int getFKCompetency() {
		return iFKCompetency;
	}
	public void setFKCompetency(int competency) {
		iFKCompetency = competency;
	}
	public int getFKRScale() {
		return iFKRScale;
	}
	public void setFKRScale(int scale) {
		iFKRScale = scale;
	}
	public int getPKCompetencyQuestion() {
		return iPKCompetencyQuestion;
	}
	public void setPKCompetencyQuestion(int competencyQuestion) {
		iPKCompetencyQuestion = competencyQuestion;
	}	
	
}
