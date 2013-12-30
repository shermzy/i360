package CP_Classes.vo;

import java.util.Vector;

/** 
 * Change Log
 * ==========
 * Date        By				Method(s)            					Change(s) 
 * ============================================================================================================================================
 * 24/05/12	  Albert		   Constructor							Added new data members: PKDepartmentList
 * 																	
 * 24/05/12	  Albert		  Setter and getter methods				Added setter and getter methods for the new data member
 * 
 */

public class voDepartment {

		
	private int	iPKDepartment = 0;
	private int	iFKDivision = 0;
	private String	sDepartmentName = "";
	private String	sDepartmentCode = "";
	private String	sLocation = "";
	private int	iFKOrganization = 0;
	private Vector<Integer> PKDepartmentList = new Vector<Integer>();
	
	public Vector<Integer> getPKDepartmentList(){
		return PKDepartmentList;
	}
	public void setPKDepartmentList(Vector<Integer> pkDeptList){
		PKDepartmentList = pkDeptList;
	}
	public String getDepartmentCode() {
		return sDepartmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		sDepartmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return sDepartmentName;
	}
	public void setDepartmentName(String departmentName) {
		sDepartmentName = departmentName;
	}
	public int getFKDivision() {
		return iFKDivision;
	}
	public void setFKDivision(int division) {
		iFKDivision = division;
	}
	public int getFKOrganization() {
		return iFKOrganization;
	}
	public void setFKOrganization(int organization) {
		iFKOrganization = organization;
	}
	public String getLocation() {
		return sLocation;
	}
	public void setLocation(String location) {
		sLocation = location;
	}
	public int getPKDepartment() {
		return iPKDepartment;
	}
	public void setPKDepartment(int department) {
		iPKDepartment = department;
	}
	
	
}
