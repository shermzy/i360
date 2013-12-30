package CP_Classes.vo;

/**
 * This class defines the Error Log Vector.
 * @author Chun Pong
 * Created Date: 26 May 2008
 * Last Updated on: 26 May 2008
 * Last Updated by: Chun Pong
 */
public class voErrorLog {
	private String sCategory;
	private String sCategoryDetails;
	private String sCause;
	private String sRecordContent;
	
	public String getCategory() {
		return sCategory;
	}
	public void setCategory(String category) {
		sCategory = category;
	}
	public String getCategoryDetails() {
		return sCategoryDetails;
	}
	public void setCategoryDetails(String categoryDetails) {
		sCategoryDetails = categoryDetails;
	}
	public String getCause() {
		return sCause;
	}
	public void setCause(String cause) {
		sCause = cause;
	}
	public String getRecordContent() {
		return sRecordContent;
	}
	public void setRecordContent(String recordContent) {
		sRecordContent = recordContent;
	}
} //End voErrorLog Class
