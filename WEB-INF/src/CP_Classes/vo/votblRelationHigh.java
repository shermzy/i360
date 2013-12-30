package CP_Classes.vo;

public class votblRelationHigh {
	

	private int	iRelationID = 0 ;
	private String	sRelationHigh = "" ;
	private int iRTRelation = 0;
	
	public String getRelationHigh() {
		return sRelationHigh;
	}
	public void setRelationHigh(String relationHigh) {
		sRelationHigh = relationHigh;
	}
	public int getRelationID() {
		return iRelationID;
	}
	public void setRelationID(int relationID) {
		iRelationID = relationID;
	}
	public void setRTRelation(int iRTRelation) {
		this.iRTRelation = iRTRelation;
	}
	public int getRTRelation() {
		return iRTRelation;
	}

	
}
