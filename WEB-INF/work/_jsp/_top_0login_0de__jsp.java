/*
 * JSP generated by Resin Professional 4.0.36 (built Fri, 26 Apr 2013 03:33:09 PDT)
 */

package _jsp;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import CP_Classes.vo.*;

public class _top_0login_0de__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  private boolean _caucho_isNotModified;
  private com.caucho.jsp.PageManager _jsp_pageManager;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    com.caucho.jsp.PageContextImpl pageContext = _jsp_pageManager.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);

    TagState _jsp_state = null;

    try {
      _jspService(request, response, pageContext, _jsp_application, session, _jsp_state);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_pageManager.freePageContext(pageContext);
    }
  }
  
  private void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response,
              com.caucho.jsp.PageContextImpl pageContext,
              javax.servlet.ServletContext application,
              javax.servlet.http.HttpSession session,
              TagState _jsp_state)
    throws Throwable
  {
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.jsp.tagext.JspTag _jsp_parent_tag = null;
    com.caucho.jsp.PageContextImpl _jsp_parentContext = pageContext;
    response.setContentType("text/html");
    response.setCharacterEncoding("utf-8");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    CP_Classes.Login logchk;
    synchronized (pageContext.getSession()) {
      logchk = (CP_Classes.Login) pageContext.getSession().getAttribute("logchk");
      if (logchk == null) {
        logchk = new CP_Classes.Login();
        pageContext.getSession().setAttribute("logchk", logchk);
      }
    }
    out.write(_jsp_string0, 0, _jsp_string0.length);
    CP_Classes.Database db;
    synchronized (pageContext.getSession()) {
      db = (CP_Classes.Database) pageContext.getSession().getAttribute("db");
      if (db == null) {
        db = new CP_Classes.Database();
        pageContext.getSession().setAttribute("db", db);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.EventViewer ev;
    synchronized (pageContext.getSession()) {
      ev = (CP_Classes.EventViewer) pageContext.getSession().getAttribute("ev");
      if (ev == null) {
        ev = new CP_Classes.EventViewer();
        pageContext.getSession().setAttribute("ev", ev);
      }
    }
    out.write(_jsp_string2, 0, _jsp_string2.length);
    CP_Classes.Setting server;
    synchronized (pageContext.getSession()) {
      server = (CP_Classes.Setting) pageContext.getSession().getAttribute("server");
      if (server == null) {
        server = new CP_Classes.Setting();
        pageContext.getSession().setAttribute("server", server);
      }
    }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    CP_Classes.User_Jenty user_jenty;
    synchronized (pageContext.getSession()) {
      user_jenty = (CP_Classes.User_Jenty) pageContext.getSession().getAttribute("user_jenty");
      if (user_jenty == null) {
        user_jenty = new CP_Classes.User_Jenty();
        pageContext.getSession().setAttribute("user_jenty", user_jenty);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.AssignTarget_Rater assignTR;
    synchronized (pageContext.getSession()) {
      assignTR = (CP_Classes.AssignTarget_Rater) pageContext.getSession().getAttribute("assignTR");
      if (assignTR == null) {
        assignTR = new CP_Classes.AssignTarget_Rater();
        pageContext.getSession().setAttribute("assignTR", assignTR);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.Create_Edit_Survey CE_Survey;
    synchronized (pageContext.getSession()) {
      CE_Survey = (CP_Classes.Create_Edit_Survey) pageContext.getSession().getAttribute("CE_Survey");
      if (CE_Survey == null) {
        CE_Survey = new CP_Classes.Create_Edit_Survey();
        pageContext.getSession().setAttribute("CE_Survey", CE_Survey);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.User user;
    synchronized (pageContext.getSession()) {
      user = (CP_Classes.User) pageContext.getSession().getAttribute("user");
      if (user == null) {
        user = new CP_Classes.User();
        pageContext.getSession().setAttribute("user", user);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.DevelopmentGuide DG;
    synchronized (pageContext.getSession()) {
      DG = (CP_Classes.DevelopmentGuide) pageContext.getSession().getAttribute("DG");
      if (DG == null) {
        DG = new CP_Classes.DevelopmentGuide();
        pageContext.getSession().setAttribute("DG", DG);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.RaterRelation RR;
    synchronized (pageContext.getSession()) {
      RR = (CP_Classes.RaterRelation) pageContext.getSession().getAttribute("RR");
      if (RR == null) {
        RR = new CP_Classes.RaterRelation();
        pageContext.getSession().setAttribute("RR", RR);
      }
    }
    out.write(_jsp_string4, 0, _jsp_string4.length);
    CP_Classes.Calculation C;
    synchronized (pageContext.getSession()) {
      C = (CP_Classes.Calculation) pageContext.getSession().getAttribute("C");
      if (C == null) {
        C = new CP_Classes.Calculation();
        pageContext.getSession().setAttribute("C", C);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.Competency Comp;
    synchronized (pageContext.getSession()) {
      Comp = (CP_Classes.Competency) pageContext.getSession().getAttribute("Comp");
      if (Comp == null) {
        Comp = new CP_Classes.Competency();
        pageContext.getSession().setAttribute("Comp", Comp);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.DevelopmentActivities DRA;
    synchronized (pageContext.getSession()) {
      DRA = (CP_Classes.DevelopmentActivities) pageContext.getSession().getAttribute("DRA");
      if (DRA == null) {
        DRA = new CP_Classes.DevelopmentActivities();
        pageContext.getSession().setAttribute("DRA", DRA);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.DevelopmentResources DRARes;
    synchronized (pageContext.getSession()) {
      DRARes = (CP_Classes.DevelopmentResources) pageContext.getSession().getAttribute("DRARes");
      if (DRARes == null) {
        DRARes = new CP_Classes.DevelopmentResources();
        pageContext.getSession().setAttribute("DRARes", DRARes);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.KeyBehaviour KB;
    synchronized (pageContext.getSession()) {
      KB = (CP_Classes.KeyBehaviour) pageContext.getSession().getAttribute("KB");
      if (KB == null) {
        KB = new CP_Classes.KeyBehaviour();
        pageContext.getSession().setAttribute("KB", KB);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.Questionnaire Q;
    synchronized (pageContext.getSession()) {
      Q = (CP_Classes.Questionnaire) pageContext.getSession().getAttribute("Q");
      if (Q == null) {
        Q = new CP_Classes.Questionnaire();
        pageContext.getSession().setAttribute("Q", Q);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.QuestionnaireReport QR;
    synchronized (pageContext.getSession()) {
      QR = (CP_Classes.QuestionnaireReport) pageContext.getSession().getAttribute("QR");
      if (QR == null) {
        QR = new CP_Classes.QuestionnaireReport();
        pageContext.getSession().setAttribute("QR", QR);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.RatersDataEntry RDE;
    synchronized (pageContext.getSession()) {
      RDE = (CP_Classes.RatersDataEntry) pageContext.getSession().getAttribute("RDE");
      if (RDE == null) {
        RDE = new CP_Classes.RatersDataEntry();
        pageContext.getSession().setAttribute("RDE", RDE);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.RatingScale RS;
    synchronized (pageContext.getSession()) {
      RS = (CP_Classes.RatingScale) pageContext.getSession().getAttribute("RS");
      if (RS == null) {
        RS = new CP_Classes.RatingScale();
        pageContext.getSession().setAttribute("RS", RS);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.SurveyResult SR;
    synchronized (pageContext.getSession()) {
      SR = (CP_Classes.SurveyResult) pageContext.getSession().getAttribute("SR");
      if (SR == null) {
        SR = new CP_Classes.SurveyResult();
        pageContext.getSession().setAttribute("SR", SR);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.Translate trans;
    synchronized (pageContext.getSession()) {
      trans = (CP_Classes.Translate) pageContext.getSession().getAttribute("trans");
      if (trans == null) {
        trans = new CP_Classes.Translate();
        pageContext.getSession().setAttribute("trans", trans);
      }
    }
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.Organization Org;
    synchronized (pageContext.getSession()) {
      Org = (CP_Classes.Organization) pageContext.getSession().getAttribute("Org");
      if (Org == null) {
        Org = new CP_Classes.Organization();
        pageContext.getSession().setAttribute("Org", Org);
      }
    }
    out.write(_jsp_string5, 0, _jsp_string5.length);
    // by lydia Date 05/09/2008 Fix jsp file to support Thai language 
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((trans.tslt("If you are using a pop-up blocker, please disable it in order for all the screens to be displayed correctly")));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    

response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");
  
  if (!logchk.isUsable(username)) 
  {
    out.write(_jsp_string8, 0, _jsp_string8.length);
    pageContext.forward("Login.jsp");if (true) return;
    out.write(_jsp_string1, 0, _jsp_string1.length);
      } 
  else 
  { 

if(request.getParameter("logoff") != null)
{	

String [] UserInfo = user.getUserDetail(logchk.getPKUser(), 1);
// add to tblEvent
try {
	ev.addRecord("Logout", "Logout", "Logout from i360", UserInfo[2], UserInfo[11], UserInfo[10]);
}catch(SQLException SE) {}


session.removeAttribute("username");  
String value =" "; 

logchk.setPKUser(0);
logchk.setOrg(0);
logchk.setCompany(0);
logchk.setUserType(0);

user.set_selectedUser(0);
user.set_LoginName(value);

assignTR.setGroupID(0);
assignTR.setTargetID(0);
assignTR.set_selectedTargetID(0);
assignTR.set_selectedRaterID(0);
assignTR.set_NameSequence(0);
assignTR.set_selectedAssID(0);

CE_Survey.setJobPos_ID(0);
CE_Survey.setSurveyStatus(0);
CE_Survey.setPurpose(0);
CE_Survey.set_survOrg(0);
CE_Survey.setSurvey_ID(0);
CE_Survey.setCompetencyLevel(0);
CE_Survey.set_SurvRating(0);
CE_Survey.set_GroupID(0);
CE_Survey.set_CompLevel(0);

DG.setSurvey_ID(0);
DG.setType(0);

RR.setRelHigh(0);
RR.setRelSpec(0);


C.setSurveyID(0);
C.setGroupSection(0);
C.setTargetID(0);
C.setRaterID(0);

Comp.setComp("");
Comp.setOrgID(0);
Comp.setPKComp(0);

DRA.setFKCom(0);
DRARes.setFKComp(0);
DRARes.setResType(0);

ev.setSortType(0);
ev.setCompName("");
ev.setOrgName("");

KB.setFKComp(0);
KB.setIsComp(0);
KB.setAdded(0);
KB.setKBLevel(0);

Q.setJobPost("");
Q.setName("");
Q.setTotalComp(0);
Q.setTotalCurrComp(0);
Q.setAssignmentID(0);
Q.setFutureJob("");
Q.setTimeFrame("");
Q.setSurveyLevel(0);
Q.setChecked(0);
Q.setCurrID(0);

QR.setSurveyID(0);
QR.setJobPostID(0);
QR.setDivisionID(0);
QR.setDepartmentID(0);
QR.setGroupID(0);
QR.setTargetID(0);
QR.setRaterID(0);
QR.setPageLoad(0);

RDE.setSurveyID(0);
RDE.setGroupID(0);
RDE.setTargetID(0);
RDE.setJobPost("");
RDE.setRaterID(0);
RDE.setPageLoad(0);

RS.setRS(0);
RS.setScaleID(0);
RS.setRSType("");

SR.setSurveyID(0);
SR.setGroupID(0);
SR.setTargetID(0);
SR.setRaterID(0);
SR.setSurveyLevel(0);
SR.setAssignmentID(0);
	
	
    out.write(_jsp_string9, 0, _jsp_string9.length);
    pageContext.forward("index.jsp");if (true) return;
    out.write(_jsp_string1, 0, _jsp_string1.length);
    	}	
    out.write(_jsp_string10, 0, _jsp_string10.length);
    
				
/*
*Changes: Added a logo to be displayed on top of the pcc logo
*Reasons: To allow organization logo to be displayed
*Updated By: Liu Taichen	
*Updated On: 31 May 2012
*/
					
					String orgLogo = "";
					votblOrganization vo_Org = Org.getOrganization(logchk.getOrg());
					orgLogo = "Logo/" + vo_Org.getOrganizationLogo();
					
					
					if(vo_Org.getOrganizationLogo() != null){
					if (!vo_Org.getOrganizationLogo().equals("")) {//for organizations other than moe
						if(vo_Org.getPKOrganization() != 77){
			
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((orgLogo));
    out.write(_jsp_string12, 0, _jsp_string12.length);
    } 
						//if the organization is moe
						else{ 
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((orgLogo));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    
				}}
					}
			
    out.write(_jsp_string15, 0, _jsp_string15.length);
    
		/*
		*Changes: Display the PCC logo instead of the MOE logo on the banner
		*Reasons: To prevent MOE logo from showing
		*Updated By: Liu Taichen	
		*Updated On: 31 May 2012
		*/
		
		
    out.write(_jsp_string16, 0, _jsp_string16.length);
    
			/* TOYOTA ENV */
			//if(server.getCompanySetting() == 3)
			if(logchk.getOrgCode() != null && (logchk.getOrgCode().equals("KPRBC") || logchk.getOrgCode().equals("DEMO")) )
			{
    out.write(_jsp_string17, 0, _jsp_string17.length);
    }
			else
			{
    out.write(_jsp_string17, 0, _jsp_string17.length);
    }
			/* END TOYOTA ENV */
		
    out.write(_jsp_string18, 0, _jsp_string18.length);
    out.print((username.toLowerCase()));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    
			/* TOYOTA ENV */
			//if(server.getCompanySetting() == 3)
			if(logchk.getOrgCode() != null && (logchk.getOrgCode().equals("KPRBC") || logchk.getOrgCode().equals("DEMO")) )
			{
    out.write(_jsp_string20, 0, _jsp_string20.length);
    }
			else
			{
    out.write(_jsp_string20, 0, _jsp_string20.length);
    }
			/* END TOYOTA ENV */
		
    out.write(_jsp_string21, 0, _jsp_string21.length);
    	}	
    out.write(_jsp_string22, 0, _jsp_string22.length);
  }

  private com.caucho.make.DependencyContainer _caucho_depends
    = new com.caucho.make.DependencyContainer();

  public java.util.ArrayList<com.caucho.vfs.Dependency> _caucho_getDependList()
  {
    return _caucho_depends.getDependencies();
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    _caucho_depends.add(depend);
  }

  protected void _caucho_setNeverModified(boolean isNotModified)
  {
    _caucho_isNotModified = true;
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;

    if (_caucho_isNotModified)
      return false;

    if (com.caucho.server.util.CauchoSystem.getVersionId() != -7791540776389363938L)
      return true;

    return _caucho_depends.isModified();
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
    TagState tagState;
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("Top_Login_DE.jsp"), -7625124988514140611L, false);
    _caucho_depends.add(depend);
  }

  final static class TagState {

    void release()
    {
    }
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void caucho_init(ServletConfig config)
  {
    try {
      com.caucho.server.webapp.WebApp webApp
        = (com.caucho.server.webapp.WebApp) config.getServletContext();
      init(config);
      if (com.caucho.jsp.JspManager.getCheckInterval() >= 0)
        _caucho_depends.setCheckInterval(com.caucho.jsp.JspManager.getCheckInterval());
      _jsp_pageManager = webApp.getJspApplicationContext().getPageManager();
      com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
      com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.InitPageContextImpl(webApp, this);
    } catch (Exception e) {
      throw com.caucho.config.ConfigException.create(e);
    }
  }

  private final static char []_jsp_string9;
  private final static char []_jsp_string11;
  private final static char []_jsp_string6;
  private final static char []_jsp_string13;
  private final static char []_jsp_string12;
  private final static char []_jsp_string16;
  private final static char []_jsp_string19;
  private final static char []_jsp_string5;
  private final static char []_jsp_string22;
  private final static char []_jsp_string20;
  private final static char []_jsp_string18;
  private final static char []_jsp_string14;
  private final static char []_jsp_string8;
  private final static char []_jsp_string15;
  private final static char []_jsp_string2;
  private final static char []_jsp_string7;
  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  private final static char []_jsp_string21;
  private final static char []_jsp_string3;
  private final static char []_jsp_string17;
  private final static char []_jsp_string10;
  private final static char []_jsp_string4;
  static {
    _jsp_string9 = "\r\n	".toCharArray();
    _jsp_string11 = "\r\n			<IMG\r\n				STYLE=\"position: absolute; TOP: 3px; LEFT: 438px; WIDTH: 155px; HEIGHT: 136px\"\r\n				SRC=".toCharArray();
    _jsp_string6 = "\r\n</head>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\nfunction logout(form)\r\n{\r\n	window.document.Top_Login.action = \"Top_Login.jsp?logoff=1\";\r\n	window.document.Top_Login.submit();\r\n}\r\n\r\n//Edited by Xuehai, 25 May 2011. Enable running on Chrome&Firefox\r\n//void function printAlert()\r\nfunction printAlert()\r\n{\r\n	alert(\"".toCharArray();
    _jsp_string13 = "\r\n				<IMG\r\n				STYLE=\"position: absolute; TOP: 21px; LEFT: 360px; WIDTH: 233px; HEIGHT: 99px\"\r\n				SRC=".toCharArray();
    _jsp_string12 = ">\r\n				\r\n				".toCharArray();
    _jsp_string16 = "\r\n			<IMG SRC=\"images/360_05_blanklogo.jpg\" WIDTH=399 HEIGHT=132 ALT=\"\"></TD>\r\n		<TD COLSPAN=3 ROWSPAN=6>\r\n		".toCharArray();
    _jsp_string19 = "\" readonly></td>\r\n        </tr>\r\n      </table> </TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=17 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD COLSPAN=3 ROWSPAN=2>\r\n		".toCharArray();
    _jsp_string5 = "\r\n<HTML>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html\">\r\n\r\n".toCharArray();
    _jsp_string22 = "\r\n</BODY>\r\n\r\n</HTML>".toCharArray();
    _jsp_string20 = "\r\n				<IMG SRC=\"images/360_13.jpg\" WIDTH=207 HEIGHT=12 ALT=\"\"></TD>\r\n			".toCharArray();
    _jsp_string18 = "\r\n			\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=69 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD COLSPAN=2>\r\n			<IMG SRC=\"images/360_07.jpg\" WIDTH=156 HEIGHT=4 ALT=\"\"></TD>\r\n		<TD ROWSPAN=3>\r\n			<IMG SRC=\"images/360_08.jpg\" WIDTH=28 HEIGHT=31 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=4 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		\r\n    <TD COLSPAN=2 background=\"images/360_09.jpg\"><table width=\"156\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" background=\"images/360_09.jpg\">\r\n        <tr> \r\n          <td width=\"57\"><IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=25 ALT=\"\"></td>\r\n          <td width=\"10\"><img src=\"images/spacer.gif\" width=\"5\" height=\"5\"> </td>\r\n          <td width=\"89\"><IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=25 ALT=\"\"></td>\r\n        </tr>\r\n      </table> </TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=25 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD COLSPAN=2 ROWSPAN=2>\r\n			<IMG SRC=\"images/360_10.jpg\" WIDTH=156 HEIGHT=5 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=2 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD ROWSPAN=4><!-- Edited by Xuehai, 27 May 2011, change 'cursor:hand' to 'cursor:pointer' to be compatible on diff browers -->\r\n			<IMG SRC=\"images/logout.jpg\"  style=\"cursor:pointer\" ALT=\"\" WIDTH=28 HEIGHT=32 border=\"0\" name=\"btnLogin\" onclick=\"logout(this.form)\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=3 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		\r\n    <TD COLSPAN=2 ROWSPAN=2 valign=\"top\" background=\"images/360_12.jpg\"><table width=\"156\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" background=\"images/360_12.jpg\">\r\n        <tr> \r\n          <td width=\"57\"><div align=\"right\"><font size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\"><strong>\r\n			Log In:</strong></font></div></td>\r\n          <td width=\"10\"><img src=\"images/spacer.gif\" width=\"5\" height=\"5\"> </td>\r\n          <td width=\"89\"><input type=\"text\" name=\"txtUsername\" style='width:85px;height:20px;' value=\"".toCharArray();
    _jsp_string14 = ">\r\n			".toCharArray();
    _jsp_string8 = " <font size=\"2\">\r\n   \r\n    	".toCharArray();
    _jsp_string15 = "\r\n\r\n\r\n<TABLE WIDTH=800 BORDER=0 CELLPADDING=0 CELLSPACING=0>\r\n	<TR>\r\n		<TD width=\"10\" ROWSPAN=19>\r\n			<IMG SRC=\"images/360_01.jpg\" WIDTH=10 HEIGHT=581 ALT=\"\"></TD>\r\n		\r\n	</TR>\r\n	<TR>\r\n		<TD COLSPAN=2>\r\n			<IMG SRC=\"images/360_03.jpg\" WIDTH=156 HEIGHT=69 ALT=\"\"></TD>\r\n		<TD width=\"37\">\r\n			<IMG SRC=\"images/360_04.jpg\" WIDTH=28 HEIGHT=69 ALT=\"\"></TD>\r\n		<TD COLSPAN=4 ROWSPAN=8>\r\n		".toCharArray();
    _jsp_string2 = "\r\n                 \r\n".toCharArray();
    _jsp_string7 = ".\\n\\nTo do this, navigate to Tools -> Internet Options. On the Privacy tab uncheck the 'Block pop-ups' option.\");\r\n}\r\n\r\nfunction close()\r\n{\r\n	window.document.Top_Login.action = \"Top_Login.jsp?logoff=1\";\r\n	window.document.Top_Login.submit();\r\n}\r\n\r\n\r\n</script>\r\n<HEAD>\r\n<TITLE>360 interface revise</TITLE>\r\n<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html\">\r\n<base target=\"_parent\">\r\n</HEAD>\r\n<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 onLoad=\"printAlert()\" onUnload=\"close()\">\r\n\r\n".toCharArray();
    _jsp_string1 = "\r\n".toCharArray();
    _jsp_string0 = "  \r\n".toCharArray();
    _jsp_string21 = "\r\n			\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=8 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD COLSPAN=2>\r\n			<IMG SRC=\"images/360_14.jpg\" WIDTH=156 HEIGHT=4 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=4 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD COLSPAN=9>\r\n			<IMG SRC=\"images/360_15.jpg\" WIDTH=789 HEIGHT=7 ALT=\"\"></TD>\r\n		<TD width=\"1\">\r\n			<IMG SRC=\"images/360_16.jpg\" WIDTH=1 HEIGHT=7 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=7 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		\r\n    <TD COLSPAN=2 height=\"34\">&nbsp; \r\n  \r\n    </TD>\r\n		<TD COLSPAN=2 height=\"34\">&nbsp;\r\n			</TD>\r\n		<TD width=\"108\" height=\"34\">&nbsp;\r\n			</TD>\r\n		<TD width=\"89\" height=\"34\">&nbsp;\r\n			</TD>\r\n		<TD width=\"87\" height=\"34\">&nbsp;\r\n			</TD>\r\n		<TD width=\"88\" height=\"34\">&nbsp;\r\n			</TD>\r\n		<TD COLSPAN=2 height=\"34\">&nbsp;\r\n			</TD>\r\n		<TD height=\"34\">\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=33 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=31 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=56 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=50 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=50 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=49 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=53 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=48 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=78 ALT=\"\"></TD>\r\n	</TR>\r\n	<TR>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=10 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=129 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=27 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=28 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD width=\"115\">\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=115 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=108 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=89 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=87 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=88 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD width=\"118\">\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=118 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD>\r\n			<IMG SRC=\"images/spacer.gif\" WIDTH=1 HEIGHT=1 ALT=\"\"></TD>\r\n		<TD></TD>\r\n	</TR>\r\n</TABLE>\r\n\r\n<!-- End ImageReady Slices -->\r\n<!-- ImageReady Slices (360 interface revise.psd) -->\r\n<div id=\"Layer1\" style=\"position:absolute; width:188px; height:79px; z-index:1; left: 48px; top: 179px; visibility: hidden; overflow: auto;\"> \r\n  <table width=\"211\" border=\"0\">\r\n    <tr> \r\n      <td width=\"205\" bgcolor=\"#00CCFF\" class=\"style1\"><font size=\"2\" face=\"Verdana, Arial, Helvetica, sans-serif\"><span class=\"style6\">\r\n		Set up</span></font></td>\r\n    </tr>\r\n    <tr> \r\n      <td bgcolor=\"#006699\" class=\"style1\"><font color=\"#FFFFFF\" size=\"2\" face=\"Verdana, Arial, Helvetica, sans-serif\"><span class=\"style8\">\r\n		System</span></font></td>\r\n    </tr>\r\n    <tr> \r\n      <td bgcolor=\"#00CCFF\" class=\"style1\"><font size=\"2\" face=\"Verdana, Arial, Helvetica, sans-serif\"><span class=\"style6\">\r\n		Rater&#39;s Demographic Entry </span></font></td>\r\n    </tr>\r\n    <tr> \r\n      <td bgcolor=\"#006699\" class=\"style1\"><font color=\"#FFFFFF\" size=\"2\" face=\"Verdana, Arial, Helvetica, sans-serif\"><span class=\"style8\">\r\n		Event viewer </span></font></td>\r\n    </tr>\r\n  </table>\r\n</div>\r\n<map name=\"Map11\">\r\n  <area shape=\"rect\" coords=\"2,5,31,26\" href=\"#\">\r\n</map>\r\n\r\n</form>\r\n".toCharArray();
    _jsp_string3 = "   \r\n".toCharArray();
    _jsp_string17 = "\r\n				<IMG SRC=\"images/360_06.jpg\" WIDTH=207 HEIGHT=120 ALT=\"\"></TD>\r\n			".toCharArray();
    _jsp_string10 = "	\r\n\r\n<form name=\"Top_Login\" action=\"Top_login.jsp\" method=\"post\">\r\n".toCharArray();
    _jsp_string4 = "\r\n\r\n".toCharArray();
  }
}
