package CP_Classes.vo;

public class votblTableIDs {
	
	
	private int	ilNext_id	= 0 ;
	private String	sszTable_nm	= "" ;
	private String	ssLastUpdate_id	= "" ;
	private String	sdtLastUpdate_dt	= "" ;
	private int	iiConcurrency_id	= 0 ;
	
	public String getDtLastUpdate_dt() {
		return sdtLastUpdate_dt;
	}
	public void setDtLastUpdate_dt(String dtLastUpdate_dt) {
		this.sdtLastUpdate_dt = dtLastUpdate_dt;
	}
	public int getIConcurrency_id() {
		return iiConcurrency_id;
	}
	public void setIConcurrency_id(int concurrency_id) {
		iiConcurrency_id = concurrency_id;
	}
	public int getLNext_id() {
		return ilNext_id;
	}
	public void setLNext_id(int next_id) {
		ilNext_id = next_id;
	}
	public String getSLastUpdate_id() {
		return ssLastUpdate_id;
	}
	public void setSLastUpdate_id(String lastUpdate_id) {
		ssLastUpdate_id = lastUpdate_id;
	}
	public String getSzTable_nm() {
		return sszTable_nm;
	}
	public void setSzTable_nm(String szTable_nm) {
		this.sszTable_nm = szTable_nm;
	}
	
	

}
