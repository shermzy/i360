package CP_Classes.vo;

public class votblUserRelation {
	
	private int	iUserRelationID	= 0 ;
	private int	iUser1	= 0 ;
	private int	iUser2	= 0 ;
	private int	iRelationType	= 0 ;
	
	public int getRelationType() {
		return iRelationType;
	}
	public void setRelationType(int relationType) {
		iRelationType = relationType;
	}
	public int getUser1() {
		return iUser1;
	}
	public void setUser1(int user1) {
		iUser1 = user1;
	}
	public int getUser2() {
		return iUser2;
	}
	public void setUser2(int user2) {
		iUser2 = user2;
	}
	public int getUserRelationID() {
		return iUserRelationID;
	}
	public void setUserRelationID(int userRelationID) {
		iUserRelationID = userRelationID;
	}
	
	
	
}
