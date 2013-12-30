/*
 * JSP generated by Resin Professional 4.0.36 (built Fri, 26 Apr 2013 03:33:09 PDT)
 */

package _jsp;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class _surveyrating_220_0edit__jsp extends com.caucho.jsp.JavaPage
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
    out.write(_jsp_string1, 0, _jsp_string1.length);
    CP_Classes.SurveyRating SRT;
    synchronized (pageContext.getSession()) {
      SRT = (CP_Classes.SurveyRating) pageContext.getSession().getAttribute("SRT");
      if (SRT == null) {
        SRT = new CP_Classes.SurveyRating();
        pageContext.getSession().setAttribute("SRT", SRT);
      }
    }
    out.write(_jsp_string2, 0, _jsp_string2.length);
    CP_Classes.Create_Edit_Survey CE_Survey;
    synchronized (pageContext.getSession()) {
      CE_Survey = (CP_Classes.Create_Edit_Survey) pageContext.getSession().getAttribute("CE_Survey");
      if (CE_Survey == null) {
        CE_Survey = new CP_Classes.Create_Edit_Survey();
        pageContext.getSession().setAttribute("CE_Survey", CE_Survey);
      }
    }
    out.write(_jsp_string2, 0, _jsp_string2.length);
    CP_Classes.Translate trans;
    synchronized (pageContext.getSession()) {
      trans = (CP_Classes.Translate) pageContext.getSession().getAttribute("trans");
      if (trans == null) {
        trans = new CP_Classes.Translate();
        pageContext.getSession().setAttribute("trans", trans);
      }
    }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    // by lydia Date 05/09/2008 Fix jsp file to support Thai language 
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((trans.tslt("Please enter Survey Rating Name")));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    

String username=(String)session.getAttribute("username");
  if (!logchk.isUsable(username)) 
  {
    out.write(_jsp_string6, 0, _jsp_string6.length);
      } 
  else 
  {
  
  if(request.getParameter("edit") != null)
  {
  		String RatName = request.getParameter("txtRatName");
  		String RTDesc = request.getParameter("txtRatDesc");
  		String RTDisplayCode = request.getParameter("txtRatDisCode");
  		boolean bIsEdited = CE_Survey.editRating(CE_Survey.getSurvey_ID(),CE_Survey.get_SurvRating(),RatName, RTDesc, RTDisplayCode);
		
		if(bIsEdited) {

    out.write(_jsp_string7, 0, _jsp_string7.length);
    
		}
  }
  

    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((trans.tslt("Rating Task Name")));
    out.write(_jsp_string9, 0, _jsp_string9.length);
    
		
		String RatName=" ";
		
		RatName = SRT.getRatingTaskName(CE_Survey.getSurvey_ID(), CE_Survey.get_SurvRating());
		
		String RatDesc = "";
		
		// Add text box to change rating task description and rating code. 
		RatDesc = SRT.getRatingTaskDesc(CE_Survey.getSurvey_ID(), CE_Survey.get_SurvRating());
		if(RatDesc == null)
			RatDesc = "";
		String RTDisplayCode = "";
		RTDisplayCode = SRT.getRTDisplayCode(CE_Survey.getSurvey_ID(), CE_Survey.get_SurvRating());
		if(RTDisplayCode == null)
			RTDisplayCode = "";
		
    out.write(_jsp_string10, 0, _jsp_string10.length);
    out.print((RatName));
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((trans.tslt("Rating Task Description")));
    out.write(_jsp_string12, 0, _jsp_string12.length);
    out.print((RatDesc));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((trans.tslt("Rating Task Display Code")));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((RTDisplayCode));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    out.print((trans.tslt("Cancel")));
    out.write(_jsp_string16, 0, _jsp_string16.length);
    out.print((trans.tslt("Save")));
    out.write(_jsp_string17, 0, _jsp_string17.length);
    	}	
    out.write(_jsp_string18, 0, _jsp_string18.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("SurveyRating _Edit.jsp"), 1495114803996964202L, false);
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

  private final static char []_jsp_string5;
  private final static char []_jsp_string15;
  private final static char []_jsp_string9;
  private final static char []_jsp_string12;
  private final static char []_jsp_string18;
  private final static char []_jsp_string13;
  private final static char []_jsp_string14;
  private final static char []_jsp_string4;
  private final static char []_jsp_string7;
  private final static char []_jsp_string8;
  private final static char []_jsp_string16;
  private final static char []_jsp_string11;
  private final static char []_jsp_string10;
  private final static char []_jsp_string2;
  private final static char []_jsp_string17;
  private final static char []_jsp_string6;
  private final static char []_jsp_string1;
  private final static char []_jsp_string3;
  private final static char []_jsp_string0;
  static {
    _jsp_string5 = "\");\r\n	}\r\n}\r\n</SCRIPT>\r\n<body bgcolor=\"#FFFFCC\">\r\n".toCharArray();
    _jsp_string15 = "\"></td>\r\n		</tr>\r\n	</tr>\r\n	<tr>\r\n		<td width=\"178\">&nbsp;</td>\r\n		<td>&nbsp;</td>\r\n	</tr>\r\n	<tr>\r\n		<td width=\"178\"><input type=\"button\" value=\"".toCharArray();
    _jsp_string9 = ":</font></b></td>\r\n		<td>\r\n		<font size=\"2\">\r\n		".toCharArray();
    _jsp_string12 = ":</font></b></td>\r\n		<td><input type=\"text\" name=\"txtRatDesc\" size=\"58\" value=\"".toCharArray();
    _jsp_string18 = "\r\n</body>\r\n</html>".toCharArray();
    _jsp_string13 = "\"></td>\r\n		</tr>\r\n		<tr>\r\n		<td width=\"178\"><b><font face=\"Arial\" size=\"2\">".toCharArray();
    _jsp_string14 = ":</font></b></td>\r\n		<td><input type=\"text\" name=\"txtRatDisCode\" size=\"10\" value=\"".toCharArray();
    _jsp_string4 = "\r\n</head>\r\n<SCRIPT LANGUAGE=JAVASCRIPT>\r\nfunction edit(form, field, RTDesc, RTDisCode)\r\n{\r\n	if(field.value != null)\r\n	{\r\n		// Edited by Eric Lu 22/5/08\r\n		// Displays confirm box to edit rating name\r\n		if (confirm(\"Save the changes?\")) {\r\n			form.action=\"SurveyRating _Edit.jsp?edit=1\";\r\n			form.method=\"post\";\r\n			form.submit();\r\n		}\r\n	}\r\n	else\r\n	{\r\n		alert(\"".toCharArray();
    _jsp_string7 = "		\r\n		<script>\r\n			alert(\"Edited successfully\");\r\n			window.close();\r\n			opener.location.href ='SurveyRating.jsp';\r\n		</script>\r\n".toCharArray();
    _jsp_string8 = "\r\n<form name=\"SurveyRating\" action=\"SurveyRating.jsp\" method=\"post\">\r\n<table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n	<tr>\r\n		<td width=\"178\"><b><font face=\"Arial\" size=\"2\">".toCharArray();
    _jsp_string16 = "\" name=\"btnCancel\" onclick=\"window.close()\"></td>\r\n		<td>\r\n		<input type=\"button\" value=\"".toCharArray();
    _jsp_string11 = "\"></td>\r\n		<tr>\r\n		<td width=\"178\"><b><font face=\"Arial\" size=\"2\">".toCharArray();
    _jsp_string10 = "\r\n		</font>\r\n		<input type=\"text\" name=\"txtRatName\" size=\"58\" value=\"".toCharArray();
    _jsp_string2 = "\r\n".toCharArray();
    _jsp_string17 = "\" name=\"btnEdit\" onclick=\"edit(this.form,this.form.txtRatName, this.form.txtRatDesc, this.form.txtRatDisCode)\" style=\"float: right\"></td>\r\n	</tr>\r\n</table>\r\n</form>\r\n".toCharArray();
    _jsp_string6 = " <font size=\"2\">\r\n   \r\n	<script>\r\n	parent.location.href = \"index.jsp\";\r\n	</script>\r\n".toCharArray();
    _jsp_string1 = "                 \r\n".toCharArray();
    _jsp_string3 = "\r\n\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html\">\r\n\r\n".toCharArray();
    _jsp_string0 = "  \r\n\r\n".toCharArray();
  }
}