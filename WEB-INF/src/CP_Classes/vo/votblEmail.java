package CP_Classes.vo;


/**
* Modified by Roger 13 June 2008
* 
* - Add org Id to tblEmail table
* 
**/
public class votblEmail {

	private int	iEmailID	= 0 ;
	private String	sSenderEmail	= "" ;
	private String	sReceiverEmail	= "" ;
	private String	sHeader	= "" ;
	private String	sContent	= "" ;
	private String sLog = "";
	private int orgId = 0;
	
	/**
	 * Added by Xuehai 23 Jun 2011. Return the first len of the string 
	 */
	public String toShort(String msg, int len){
		if(msg == null || msg.length()<len){
			return msg;
		}
		return msg.substring(0, len) + "...";
	}
	
	public String getContent() {
		return sContent;
	}
	public void setContent(String content) {
		sContent = content;
	}
	public int getEmailID() {
		return iEmailID;
	}
	public void setEmailID(int emailID) {
		iEmailID = emailID;
	}
	public String getHeader() {
		return sHeader;
	}
	public void setHeader(String header) {
		sHeader = header;
	}
	public String getReceiverEmail() {
		return sReceiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		sReceiverEmail = receiverEmail;
	}
	public String getSenderEmail() {
		return sSenderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		sSenderEmail = senderEmail;
	}
	public String getLog() {
		return sLog==null?"":sLog;
	}
	public void setLog(String log) {
		sLog = log;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	

}
