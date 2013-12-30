package CP_Classes.vo;

public class voCoach {
	
	int pk=0;
	String coachName="";
	String link="";
	String fileName="";
	
	public voCoach() {
		super();
	}

	public voCoach(int pk, String coachName, String link,String fileName) {
		super();
		this.pk = pk;
		this.coachName = coachName;
		this.link = link;
		this.fileName=fileName;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
