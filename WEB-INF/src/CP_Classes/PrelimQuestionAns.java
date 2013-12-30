package CP_Classes;

public class PrelimQuestionAns {

	private int prelimQnAnsId; 
	private int fkPrelimQnId;
	private int fkAssignmentId;
	private int fkRaterId;
	private String answer;
	
	public PrelimQuestionAns(int prelimQnAnsId, int fkPrelimQnId, String answer, int fkAssingmentId, int fkRaterId)
	{
		this.prelimQnAnsId= prelimQnAnsId; 
		this.fkPrelimQnId = fkPrelimQnId;
		this.answer = answer;
		this.fkAssignmentId = fkAssignmentId; 
		this.fkRaterId = fkRaterId;
	}
	
	public int getPrelimQnAnsID()
	{
		return prelimQnAnsId;
	}
	
	public String getAnswer()
	{
		return answer;
	}
	
	

}
