package CP_Classes.vo;

import java.util.Vector;

/** 
 * Change Log
 * ==========
 * Date        By				Method(s)            					Change(s) 
 * ============================================================================================================================================
 * 24/05/12	  Albert		   Constructor							Added new data members: PKDivisionIDList, 
 * 																	PKDepartmentIDList, PKGroupIDList
 * 24/05/12	  Albert		  Setter and getter methods				Added setter and getter methods for the new data members
 * 
 */


public class voGroup {
	

	private int	iPKGroup	= 0;
	private String	sGroupName	= "";
	private int	iFKOrganization	= 0;
	private int iPKDivision=0;
	private int iPKDepartment=0;
	private String sDepartmentName="";
	private String sDivisionName="";
	private Vector<Integer> PKGroupList = new Vector<Integer>();
	private Vector<Integer> PKDivisionList = new Vector<Integer>();
	private Vector<Integer> PKDepartmentList = new Vector<Integer>();

	public Vector<Integer> getPKDivisionList(){
		return PKDivisionList;
	}
	public void setPKDivisionList(Vector<Integer> pkDivList){
		PKDivisionList = pkDivList;
	}
	public Vector<Integer> getPKDepartmentList(){
		return PKDepartmentList;
	}
	public void setPKDepartmentList(Vector<Integer> pkDeptList){
		PKDepartmentList = pkDeptList;
	}
	public Vector<Integer> getPKGroupList(){
		return PKGroupList;
	}
	public void setPKGroupList(Vector<Integer> pkGroupList){
		PKGroupList = pkGroupList;
	}
	public int getFKOrganization() {
		return iFKOrganization;
	}
	public void setFKOrganization(int organization) {
		iFKOrganization = organization;
	}
	public String getGroupName() {
		return sGroupName;
	}
	public void setGroupName(String groupName) {
		sGroupName = groupName;
	}
	public int getPKGroup() {
		return iPKGroup;
	}
	public void setPKGroup(int group) {
		iPKGroup = group;
	}
	public int getPKDivision() {
		return iPKDivision;
	}
	public void setPKDivision(int division) {
		iPKDivision = division;
	}
	public String getDepartmentName() {
		return sDepartmentName;
	}
	public void setDepartmentName(String departmentName) {
		sDepartmentName = departmentName;
	}
	public String getDivisionName() {
		return sDivisionName;
	}
	public void setDivisionName(String divisionName) {
		sDivisionName = divisionName;
	}
	public int getPKDepartment() {
		return iPKDepartment;
	}
	public void setPKDepartment(int department) {
		iPKDepartment = department;
	}
	
	
}
