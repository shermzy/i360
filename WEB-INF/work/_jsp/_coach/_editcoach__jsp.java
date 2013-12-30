/*
 * JSP generated by Resin Professional 4.0.36 (built Fri, 26 Apr 2013 03:33:09 PDT)
 */

package _jsp._coach;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import CP_Classes.vo.*;

public class _editcoach__jsp extends com.caucho.jsp.JavaPage
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
    Coach.LoginStatus LoginStatus;
    synchronized (pageContext.getSession()) {
      LoginStatus = (Coach.LoginStatus) pageContext.getSession().getAttribute("LoginStatus");
      if (LoginStatus == null) {
        LoginStatus = new Coach.LoginStatus();
        pageContext.getSession().setAttribute("LoginStatus", LoginStatus);
      }
    }
    out.write(_jsp_string2, 0, _jsp_string2.length);
    Coach.Coach Coach;
    synchronized (pageContext.getSession()) {
      Coach = (Coach.Coach) pageContext.getSession().getAttribute("Coach");
      if (Coach == null) {
        Coach = new Coach.Coach();
        pageContext.getSession().setAttribute("Coach", Coach);
      }
    }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    
	String username = (String) session.getAttribute("username");
	int coachPK=0;
	//init
	if(request.getParameter("editedCoach")!=null){
		coachPK = Integer.valueOf(request.getParameter("editedCoach"));
		LoginStatus.setSelectedCoach(coachPK);
	}
	
	
	if (!logchk.isUsable(username)) {

    out.write(_jsp_string4, 0, _jsp_string4.length);
    
	} 
  else 
  { 
	if(request.getParameter("edit") != null) {
		if(!request.getParameter("Name").equalsIgnoreCase(""))	{
  			String name = request.getParameter("Name");
  			String link = request.getParameter("Link");
  			
  			 Boolean Exist = false;
  		    
  			Vector v = Coach.getAllCoach();
  			for(int i = 0; i < v.size(); i++){
  				voCoach vo = (voCoach)v.elementAt(i);
  				
  				String coachName = vo.getCoachName();
  				//System.out.println("Existing Schedule Name:"+slotGroupName);
  				if(name.trim().equalsIgnoreCase((coachName.trim()))){
  					Exist = true;
  					System.out.println("Same Coach Name");
  				}

  			}
				
				if (!Exist) {
					try {
						System.out.println("coachPK"+LoginStatus.getSelectedCoach());
						System.out.println("name"+name);
						System.out.println("link"+link);
						boolean editsuc =Coach.updateCoach(LoginStatus.getSelectedCoach(), name, link);
						
						System.out.println("editsuc:"+editsuc);
						
						if (editsuc) {
						
    out.write(_jsp_string5, 0, _jsp_string5.length);
     
					}
					else{
					}
				}catch(SQLException SE) {
                     System.out.println(SE);
				}
			} else {			

    out.write(_jsp_string6, 0, _jsp_string6.length);
    			
			}

	}
	}

    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((Coach.getSelectedCoach(LoginStatus.getSelectedCoach()).getCoachName()));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((Coach.getSelectedCoach(LoginStatus.getSelectedCoach()).getLink()));
    out.write(_jsp_string9, 0, _jsp_string9.length);
     } 
    out.write(_jsp_string10, 0, _jsp_string10.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("Coach/EditCoach.jsp"), -5185097860949844406L, false);
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

  private final static char []_jsp_string10;
  private final static char []_jsp_string6;
  private final static char []_jsp_string3;
  private final static char []_jsp_string8;
  private final static char []_jsp_string5;
  private final static char []_jsp_string7;
  private final static char []_jsp_string9;
  private final static char []_jsp_string2;
  private final static char []_jsp_string1;
  private final static char []_jsp_string4;
  private final static char []_jsp_string0;
  static {
    _jsp_string10 = "\r\n</body>\r\n</html>".toCharArray();
    _jsp_string6 = "\r\n	<script>\r\n  		alert(\"Same Coach Exist\");\r\n		window.location.href='EditCoach.jsp';\r\n	</script>\r\n".toCharArray();
    _jsp_string3 = "\r\n<script language = \"javascript\">\r\nfunction confirmEdit(form)\r\n{\r\n		if(form.Name.value != \"\") {\r\n				if (confirm(\"Edit Coach?\")) {\r\n					form.action = \"EditCoach.jsp?edit=1\";\r\n					form.method = \"post\";\r\n					form.submit();\r\n					return true;\r\n			} else\r\n				return false;\r\n		} else {\r\n			if(form.Name.value == \"\") {\r\n				alert(\"Please enter coach name\");\r\n				form.Name.focus();\r\n			}\r\n			return false;\r\n		}\r\n		return true;\r\n	}\r\n\r\n	function cancelEdit() {\r\n		window.close();\r\n	}\r\n</script>\r\n\r\n".toCharArray();
    _jsp_string8 = "\"size=\"30\" maxlength=\"50\">\r\n	  </td>\r\n    </tr>\r\n    <tr>\r\n      <td width=\"70\" height=\"33\">Link</td>\r\n      <td width=\"10\" height=\"33\">&nbsp;</td>\r\n      <td width=\"400\" height=\"33\">\r\n    	<input name=\"Link\" type=\"text\"  style='font-size:10.0pt;font-family:Arial' id=\"Link\" value=\"".toCharArray();
    _jsp_string5 = "\r\n						<script>\r\n						alert(\"Coach edited successfully\");\r\n						opener.location.href = 'Coach.jsp';\r\n						window.close();\r\n						</script>\r\n						".toCharArray();
    _jsp_string7 = "	\r\n\r\n<form name=\"EditCoach\" method=\"post\">\r\n	<p>\r\n		<b><font color=\"#000080\" size=\"3\" face=\"Arial\">Edit Coach</font></b>\r\n	</p>\r\n\r\n  <table border=\"0\" width=\"480\" height=\"141\" font span style='font-size:10.0pt;font-family:Arial'>\r\n    <tr>\r\n      <td width=\"70\" height=\"33\">Name</td>\r\n      <td width=\"10\" height=\"33\">&nbsp;</td>\r\n      <td width=\"400\" height=\"33\">\r\n    	<input name=\"Name\" type=\"text\"  style='font-size:10.0pt;font-family:Arial' id=\"Name\" value=\"".toCharArray();
    _jsp_string9 = "\"size=\"30\" maxlength=\"200\">\r\n	  </td>\r\n    </tr>\r\n    <tr>\r\n      <td width=\"82\" height=\"12\"></td>\r\n      <td width=\"10\" height=\"12\"></td>\r\n      <td width=\"303\" height=\"12\"></td>\r\n    </tr>\r\n   \r\n  </table>\r\n  <blockquote>\r\n    <blockquote>\r\n      <p>\r\n		<font face=\"Arial\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n		</font>		<font face=\"Arial\" span style=\"font-size: 10.0pt; font-family: Arial\">		\r\n	        <input type=\"button\" name=\"Submit\" value=\"Submit\" onClick=\"confirmEdit(this.form)\"></font><font span style='font-family:Arial'>\r\n		</font>\r\n			<font face=\"Arial\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n        </font>\r\n		<font face=\"Arial\" span style=\"font-size: 10.0pt; font-family: Arial\">\r\n			<input name=\"Cancel\" type=\"button\" id=\"Cancel\" value=\"Cancel\" onClick=\"cancelEdit()\">\r\n			</font> </p>\r\n    </blockquote>\r\n  </blockquote>\r\n</form>\r\n".toCharArray();
    _jsp_string2 = "\r\n".toCharArray();
    _jsp_string1 = "    \r\n".toCharArray();
    _jsp_string4 = " <font size=\"2\">\r\n   \r\n<script>\r\nparent.location.href = \"../index.jsp\";\r\n</script>\r\n".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n<html>\r\n<head>\r\n\r\n<title>Edit Coach</title>\r\n\r\n<meta http-equiv=\"Content-Type\" content=\"text/html\">\r\n<style type=\"text/css\">\r\n<!--\r\nbody {\r\n	background-color: #eaebf4;\r\n}\r\n-->\r\n</style></head>\r\n\r\n<body style=\"background-color: #DEE3EF\">\r\n".toCharArray();
  }
}