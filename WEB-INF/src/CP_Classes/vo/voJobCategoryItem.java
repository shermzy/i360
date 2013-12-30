package CP_Classes.vo;

public class voJobCategoryItem {
	
	private int	iPKJobCategoryItem = 0;
	private int	iFKJobCategory	= 0;
	private int	iFKCompetency	= 0;
	
	public int getFKCompetency() {
		return iFKCompetency;
	}
	public void setFKCompetency(int competency) {
		iFKCompetency = competency;
	}
	public int getFKJobCategory() {
		return iFKJobCategory;
	}
	public void setFKJobCategory(int jobCategory) {
		iFKJobCategory = jobCategory;
	}
	public int getPKJobCategoryItem() {
		return iPKJobCategoryItem;
	}
	public void setPKJobCategoryItem(int jobCategoryItem) {
		iPKJobCategoryItem = jobCategoryItem;
	}
	
	
	
}
