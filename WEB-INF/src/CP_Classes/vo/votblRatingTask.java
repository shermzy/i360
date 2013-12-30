package CP_Classes.vo;

public class votblRatingTask {
	

	private int	iRatingTaskID = 0 ;
	private String	sRatingCode = "" ;
	private String	sRatingTask = "" ;
	private String	sScaleType = "" ;
	
	public String getRatingCode() {
		return sRatingCode;
	}
	public void setRatingCode(String ratingCode) {
		sRatingCode = ratingCode;
	}
	public String getRatingTask() {
		return sRatingTask;
	}
	public void setRatingTask(String ratingTask) {
		sRatingTask = ratingTask;
	}
	public int getRatingTaskID() {
		return iRatingTaskID;
	}
	public void setRatingTaskID(int ratingTaskID) {
		iRatingTaskID = ratingTaskID;
	}
	public String getScaleType() {
		return sScaleType;
	}
	public void setScaleType(String scaleType) {
		sScaleType = scaleType;
	}

	
}
