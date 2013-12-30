<%@ page import = "java.sql.*" %>
<%//By Hemilda 23/09/2008 fixed the page import after add UTF-8%>
<%@ page import = "CP_Classes.vo.voCompetency" %>
<%@ page import = "java.util.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Key Behaviour</title>
        <meta http-equiv="Content-Type" content="text/html">
        <style type="text/css">
            <!--
            body {
                background-color: #eaebf4;
            }
            -->
        </style>
<%//System.out.println(">>Re-starting from top");%>
    </head>
    <%--Changed by Ha on 20/5/08: change CompQuery to Comp--%>
    <body>
    <jsp:useBean id="Database" class="CP_Classes.Database" scope="session" />
    <jsp:useBean id="KB" class="CP_Classes.KeyBehaviour" scope="session" />
    <jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session" />
    <jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
    <jsp:useBean id="trans" class="CP_Classes.Translate" scope="session" />
        
    <script language="javascript">
        function confirmAdd(form)
        {
                //\\Added by Ha 10/06/08 to disallow user to continue 
                //\\ if competency is not selected

                var CompList = parseInt( form.CompList.options[form.CompList.selectedIndex].value );
                var KBLevel = parseInt( form.KBLevel.options[form.KBLevel.selectedIndex].value );
                
//alert("CompList = " + CompList +  "; KBLevel = " + KBLevel);
                
                // Competency is selected
                if (form.CompList.value !=0)
                {
                    // Statement is NOT empty
                    if(form.Statement.value != "") {
                        
                        // Confirm if want to add key behaviour
                        if(confirm("<%=trans.tslt("Add Key Behaviour")%>?") ) {
                                        
                        form.action = "AddKB.jsp?add=1&CompList=" + CompList + "&KBLevel=" + KBLevel;
                        form.method = "post";
                        form.submit();	
                                        
                        return true;
                        }else {

                            // User chose cancel when asked for confirmation
                            return false;
                        }
                    } else {
                
                        // Statement is empty, prompt to try again
                        alert("<%=trans.tslt("Please enter Statement")%>");		
                        form.Statement.focus();
                        return false;
                    }
            }
            else
            {
                // Compentency NOT selected, prompt to try again
                alert("<%=trans.tslt("Please select Competency")%>");		
                form.Statement.focus();
                return false;
            }
                
            return true;
            
        } // End confirmAdd()
        //Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
        //void function cancelAdd()
        function cancelAdd()
        {
            window.close();
        }
    </script>
        <%
        
String username=(String)session.getAttribute("username");

if (!logchk.isUsable(username)) {
    
    // Log in failed
    %> <font size="2"><script> parent.location.href = "index.jsp"; </script> <%  

} else  {
    
    // Login success
    
    /*-------------------------------------------------------------------end login modification 1--------------------------------------*/
    
    int orgID = logchk.getOrg();	
    int compID = logchk.getCompany();
    int pkUser = logchk.getPKUser();
    int userType = logchk.getUserType();	// 1= super admin

    String CompName;
    int KBLevel=0, recordAdded = 0;
    int isComp = KB.getIsComp(); // if it is from Competency
//System.out.println(">>Global level>>isComp = " + isComp);
    int fkComp = 0;

    CompName = "";
    int fkcomp1 = 0;
    
    // Added by DeZ, 24/06/08, fixed problem with getting stuck while adding key behavior immediately after adding competency
    if( isComp == 1 ) { fkcomp1 = Comp.getPKComp();}

    boolean bisAdded = false;
    Vector CompResult = Comp.FilterRecord(compID, orgID);
    isComp = KB.getIsComp();

    fkComp = Comp.getPKComp();		
    KBLevel = KB.getKBLevel();
    CompName = Comp.CompetencyName(fkComp);

//System.out.println(">>comp = " + request.getParameter("comp"));

    if(request.getParameter("comp") != null) {
        isComp = 1;
        KB.setIsComp(isComp);
        fkComp = Comp.getPKComp(); // get competency ID just added in from Competency
        
        fkcomp1 = Comp.getPKComp();
        
        CompName = Comp.CompetencyName(fkComp); // get competency name just added in from Competency
        Comp.setPKComp(fkComp);
        KB.setAdded(2);
    } // end if comp

//System.out.println(">>add = " + request.getParameter("add"));
     
    if(request.getParameter("add") != null) 
    {
        // Prepare data then add key behavior
        if(request.getParameter("Statement") != null) {
            
            int KBLevel1 = 0;
            
            // Prepare Competency id
            if(isComp == 1) { fkcomp1 = Comp.getPKComp();}
            else { fkcomp1 = Integer.parseInt(request.getParameter("CompList"));}

            // Prepare KB Level
            KBLevel1 = Integer.parseInt(request.getParameter("KBLevel"));
            KB.setKBLevel(KBLevel1);
            recordAdded = KB.getAdded();
            
            // Prepare KB Statement data
            String KBStatement = request.getParameter("Statement");
            KBStatement = Database.SQLFixer(KBStatement);
            
            // Check if Key Behavior already exists in database
            int exist = KB.CheckKBExist(KBStatement, fkcomp1, KBLevel1, compID, orgID);
            if(exist == 0) 
            {
                // Key Behavior not inside database, proceed to add
                  try {
                          // Adding Key Behavior
                          boolean add = KB.addRecord(fkcomp1, KBStatement, KBLevel1, compID, orgID, pkUser, userType);

                          if(recordAdded > 0 && isComp == 1) 
                          {						
                                  KB.setAdded(recordAdded - 1);
                                  CompName = Comp.CompetencyName(fkcomp1);								
                                %> <script>				
                                window.location.href = 'AddKB.jsp';
                                </script> 
                            <%--Changed by Ha 16/05/08 pop up "Add successfully" when can add, and change the
                                                                    condition when pop up the warning message Record exists when boolean variable return fasle
                            not when they go into the catch block of the exception --%> <%				
                          } else {
                               KB.setIsComp(0);
                               KB.setKBLevel(KBLevel1);
                               Comp.setPKComp(fkcomp1);
                               isComp = 0;
                               
                               if(add) {
                                  %> <script>
                                  alert("<%=trans.tslt("Added successfully")%>");
                                  window.close();				  		
                                  opener.location.href = 'KeyBehaviour.jsp';
                                  </script> <%	
                               } else if (add == false)	{
                                  %><script>
                                  alert("<%=trans.tslt("Record exists")%>");
                                  //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
                                  //window.location.href('AddKB.jsp');
                                  window.location.href='AddKB.jsp';
                                  </script> <%
                               }
                            }
                  }catch(Exception SE) {
                        if(recordAdded > 0 && isComp == 1) {
                            %><script>													
                            window.location.href = 'AddKB.jsp';
                            </script> <%				
                        } else {
                            %><script>
                            window.close();
                            opener.location.href = 'KeyBehaviour.jsp';
                            </script> <%									
                        }
                  } // End catch
            }else {
                %> <script>
                alert("<%=trans.tslt("Record exists")%>");
                //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
                //window.location.href('AddKB.jsp');
                window.location.href='AddKB.jsp';
                </script> <%					
            }
        }
    }  // end if add
%>
        <form name="AddKB" method="post">
            <table border="0" width="585" height="165" style='font-size: 10.0pt; font-family: Arial'>
                <tr>
                    <td width="77" height="33"><%= trans.tslt("Level") %></td>
                    <td width="16" height="33">&nbsp;</td>
                    <td width="478" height="33">
                        <select name="KBLevel">
                            <font span style='font-size: 10.0pt; font-family: Arial'><%
                            for(int KBLevel1 = 1; KBLevel1 <= 10; KBLevel1++) {
                                    if(KBLevel == KBLevel1) {
                            %>
                            <option value=<%=KBLevel%> selected><%=KBLevel%>
                            <%      } else { %>
                                        <option value=<%=KBLevel1%>><%=KBLevel1%> <%
                                    }
                            } // End for loop
                            %>
                            </font>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td height="12"></td>
                    <td height="12"></td>
                    <td height="12"></td>
                </tr>
                <tr>
                    <td height="12"><%= trans.tslt("Competency") %></td>
                    <td height="12"></td>
                    <td height="12">
                        <%
                        if(isComp == 1) {
                        %> <select name="CompList" disabled>
                               <font style='font-size: 10.0pt; font-family: Arial'>
                               <option value=<%=fkcomp1%> selected><%=CompName%>
                           </select><%
                        } else { %>
                        <select name="CompList">
                            <option value=0>Please select one <%
                            /********************
                             * Edited by James 30 Oct 2007
                             ************************/
                            for(int i=0; i<CompResult.size(); i++) {
         
                                voCompetency voC = (voCompetency)CompResult.elementAt(i);

                                //	int fkComp1 = CompResult.getInt(1);
                                //	CompName = CompResult.getString(2);
                                int fkComp1=voC.getPKCompetency();
                                CompName=voC.getCompetencyName();
                                
                                if(fkComp == fkComp1) {
                                %>
                                <option value=<%=fkComp1%> selected><%=CompName%> <%
                                } else { %>
                                <option value=<%=fkComp1%>><%=CompName%> <%
                                }
                            } // End for loop
                        }
                                    %> </font>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="77" height="12"></td>
                    <td width="16" height="12"></td>
                    <td width="478" height="12"></td>
                </tr>
                <tr>
                    <td width="77" height="84"><%= trans.tslt("Statement") %></td>
                    <td width="16" height="84">&nbsp;</td>
                    <td width="478" height="84">
                      <textarea style='font-family: Arial; font-size: 10.0pt' name="Statement" cols="50" rows="5" id="textarea"></textarea></td>
                </tr>
            </table>
            <blockquote>
                <blockquote>
                    <p>&nbsp;&nbsp;
                    <input type="button" name="Submit" value="<%= trans.tslt("Submit") %>" 
                    onClick=" return confirmAdd(this.form)">&nbsp;&nbsp;&nbsp;&nbsp;
                        
                        <% 
                        if(isComp == 1) {
                        %><input name="Cancel" type="button" id="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelAdd(this.form)" disabled>
                        <%
                        } else {
                        %><input name="Cancel" type="button" id="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelAdd(this.form)"><%
                        }
                        %>
                    </p>
                </blockquote>
            </blockquote>
        </form>
        <% } %>
    </body>
</html>