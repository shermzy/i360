package CP_Classes.vo;


public class voJobCategory {
   /*
 	* The following fields are all the fields from table JobCategory
 	*
 	**/
 
 	private int PKJobCategory;
 	private String JobCategoryName;
 	private int FKOrganisation;
 	private int IsSystemGenerated;
 	
 	private String sOrigin;
     	
 	/*
 	 * get , set methods
 	 *
 	 **/
 	
	public int getPKJobCategory() 
	{
    	return PKJobCategory;
	}

	public void setPKJobCategory(int PKJobCategory) 
	{
    	this.PKJobCategory = PKJobCategory;
	}   
	
	public String getJobCategoryName() 
	{
    	return JobCategoryName;
	}

	public void setJobCategoryName(String JobCategoryName) 
	{
    	this.JobCategoryName = JobCategoryName;
	}

	public void setIsSystemGenerated(int isSystemGenerated) {
		IsSystemGenerated = isSystemGenerated;
	}

	public int getIsSystemGenerated() {
		return IsSystemGenerated;
	}

	public void setFKOrganisation(int fKOrganisation) {
		FKOrganisation = fKOrganisation;
	}

	public int getFKOrganisation() {
		return FKOrganisation;
	}

	public void setOrigin(String sOrigin) {
		this.sOrigin = sOrigin;
	}

	public String getOrigin() {
		return sOrigin;
	} 
    	
}
