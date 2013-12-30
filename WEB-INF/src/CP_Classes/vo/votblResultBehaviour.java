package CP_Classes.vo;

public class votblResultBehaviour {
	
	private int	iAssignmentID	 = 0 ;
	private int	iKeyBehaviourID	 = 0 ;
	private int	iRatingTaskID	 = 0 ;
	private int	iResult	 = 0 ;
	private String	sEntryDate	 = "" ;
	
	public int getAssignmentID() {
		return iAssignmentID;
	}
	public void setAssignmentID(int assignmentID) {
		iAssignmentID = assignmentID;
	}
	public String getEntryDate() {
		return sEntryDate;
	}
	public void setEntryDate(String entryDate) {
		sEntryDate = entryDate;
	}
	public int getKeyBehaviourID() {
		return iKeyBehaviourID;
	}
	public void setKeyBehaviourID(int keyBehaviourID) {
		iKeyBehaviourID = keyBehaviourID;
	}
	public int getRatingTaskID() {
		return iRatingTaskID;
	}
	public void setRatingTaskID(int ratingTaskID) {
		iRatingTaskID = ratingTaskID;
	}
	public int getResult() {
		return iResult;
	}
	public void setResult(int result) {
		iResult = result;
	}
	

}
