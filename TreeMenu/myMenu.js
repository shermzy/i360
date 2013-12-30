/******************************************************************************
* This file defines the tree menu with it's items and submenus.               *
******************************************************************************/

// User-defined tree menu data.

var treeMenu           = new TreeMenu();  // This is the main menu.
var treeMenuName       = "myMenu_1.0";    // Make this unique for each tree menu.
var treeMenuDays       = 7;               // Number of days to keep the cookie.
var treeMenuFrame      = "menuFrame";     // Name of the menu frame.
var treeMenuImgDir     = "graphics/"      // Path to graphics directory.
var treeMenuBackground = "";              // Background image for menu frame.   
var treeMenuBgColor    = "#ffffff";       // Color for menu frame background.   
var treeMenuFgColor    = "#000000";       // Color for menu item text.
var treeMenuHiBg       = "#008080";       // Color for selected item background.
var treeMenuHiFg       = "#ffffff";       // Color for selected item text.
var treeMenuFont       = 
      "MS Sans Serif,Arial,Helvetica";    // Text font face.
var treeMenuFontSize   = 1;               // Text font size.
var treeMenuRoot       = "Site Menu";     // Text for the menu root.
var treeMenuFolders    = 0;               // Sets display of '+' and '-' icons.
var treeMenuAltText    = true;            // Use menu item text for icon image ALT text.

// Define the items for the top-level of the tree menu.

treeMenu.addItem(new TreeMenuItem("mkt"));
treeMenu.addItem(new TreeMenuItem("Dynamic HTML"));
treeMenu.addItem(new TreeMenuItem("Java"));
treeMenu.addItem(new TreeMenuItem("JavaScript"));
treeMenu.addItem(new TreeMenuItem("About This Site", "/about.html", "_blank"));
treeMenu.addItem(new TreeMenuItem("Contact Brain Jar", "/contact.asp", "_blank"));
treeMenu.addItem(new TreeMenuItem("What's New", "/new.html", "_blank"));

// ASP submenu.

var asp = new TreeMenu();
asp.addItem(new TreeMenuItem("andrea sim"));
asp.addItem(new TreeMenuItem("Sites"));
treeMenu.items[0].makeSubmenu(asp);

// ASP Code Examples submenu.

var asp_ex = new TreeMenu();
asp_ex.addItem(new TreeMenuItem("Lindsey Lum", "/asp/fileops.html", "mainFrame"));
asp_ex.addItem(new TreeMenuItem("ASP Form Mail", "/asp/formmail.html", "mainFrame"));
asp_ex.addItem(new TreeMenuItem("Calendar", "/asp/calendar.asp", "mainFrame"));
asp_ex.addItem(new TreeMenuItem("Cookies in ASP", "/asp/cookies.asp", "mainFrame"));
asp_ex.addItem(new TreeMenuItem("Directory Listing", "/asp/dirlist.asp", "mainFrame"));
asp_ex.addItem(new TreeMenuItem("Environmental Variables", "/asp/environment.asp", "mainFrame"));
asp_ex.addItem(new TreeMenuItem("Nearest Location", "/asp/nearest.html", "mainFrame"));
asp_ex.addItem(new TreeMenuItem("Server-Side Includes", "/asp/includes.asp", "mainFrame"));
asp.items[0].makeSubmenu(asp_ex);

// ASP Sites sub menu.

var asp_sites = new TreeMenu();
asp_sites.addItem(new TreeMenuItem("The ASP Resource Index", "http://www.aspin.com", "_blank"));
asp_sites.addItem(new TreeMenuItem("ActiveServerPages.com", "http://www.activeserverpages.com", "_blank"));
asp_sites.addItem(new TreeMenuItem("ASP 101", "http://www.asp101.com", "_blank"));
asp_sites.addItem(new TreeMenuItem("LearnASP.com", "http://www.learnasp.com/", "_blank"));
asp.items[1].makeSubmenu(asp_sites);

// DHTML submenu.

var dhtml = new TreeMenu();
dhtml.addItem(new TreeMenuItem("Code Examples"));
dhtml.addItem(new TreeMenuItem("References"));
dhtml.addItem(new TreeMenuItem("Sites"));
treeMenu.items[1].makeSubmenu(dhtml);

// DHTML Code Examples sub-submenu.

var dhtml_ex = new TreeMenu();
dhtml_ex.addItem(new TreeMenuItem("Ants", "/dhtml/ants.html", "mainFrame"));
dhtml_ex.addItem(new TreeMenuItem("Bats", "/dhtml/bats.html", "mainFrame"));
dhtml_ex.addItem(new TreeMenuItem("DHTML Library", "/dhtml/dhtmllib.html", "_blank"));
dhtml_ex.addItem(new TreeMenuItem("DHTML Scroller", "/dhtml/scroller.html", "_blank"));
dhtml_ex.addItem(new TreeMenuItem("Draggable Layers", "/dhtml/drag.html", "mainFrame"));
dhtml_ex.addItem(new TreeMenuItem("Floating Logo", "/dhtml/logo.html", "_blank"));
dhtml_ex.addItem(new TreeMenuItem("Image Loader Bar", "/dhtml/loadbar.html", "mainFrame"));
dhtml_ex.addItem(new TreeMenuItem("Link Hover Effect in Netscape", "/dhtml/hover.html", "mainFrame"));
dhtml_ex.addItem(new TreeMenuItem("Mouse Tracker", "/dhtml/mouse.html", "mainFrame"));
dhtml_ex.addItem(new TreeMenuItem("Navigation Bar", "/dhtml/navbar/navbar.html", "_blank"));
dhtml_ex.addItem(new TreeMenuItem("Pop-Up Menus", "/dhtml/popupmenu/popupmenu.html", "_blank"));
dhtml_ex.addItem(new TreeMenuItem("Site Archive", "/dhtml/archive/index.html", "_blank"));
dhtml_ex.addItem(new TreeMenuItem("Tool Tips", "/dhtml/tooltips.html", "mainFrame"));
dhtml_ex.addItem(new TreeMenuItem("Web Ouija", "/dhtml/ouija.html", "_blank"));
dhtml.items[0].makeSubmenu(dhtml_ex);

// DHTML References sub-submenu.

var dhtml_refs = new TreeMenu();
dhtml_refs.addItem(new TreeMenuItem("DHTML in Netscape Communicator", "http://developer.netscape.com/docs/manuals/communicator/dynhtml/index.htm", "_blank", "menu_link_ref.gif"));
dhtml_refs.addItem(new TreeMenuItem("MSDN Online Workshop: DHTML, HTML and CSS", "http://msdn.microsoft.com/workshop/author/default.asp", "_blank", "menu_link_ref.gif"));
dhtml.items[1].makeSubmenu(dhtml_refs);

// DHTML Sites sub-submenu.

var dhtml_sites = new TreeMenu();
dhtml_sites.addItem(new TreeMenuItem("Inside Dynamic HTML", "http://www.insideDHTML.com/", "_blank"));
dhtml_sites.addItem(new TreeMenuItem("Netscape Open Studio: DHTML", "http://developer.netscape.com/openstudio/tech/index_frame.html?content=/tech/dynhtml/dynhtml.html", "_blank"));
dhtml_sites.addItem(new TreeMenuItem("Webreference: DHTML", "http://webreference.com/dhtml/", "_blank"));
dhtml.items[2].makeSubmenu(dhtml_sites);

// Java Applets submenu.

var java = new TreeMenu();
java.addItem(new TreeMenuItem("Asteroids", "/java/asteroids", "_blank", "menu_link_java.gif"));
java.addItem(new TreeMenuItem("IMap 1.5", "/java/imap", "_blank", "menu_link_java.gif"));
java.addItem(new TreeMenuItem("IMap 2.0", "/java/imap2", "_blank", "menu_link_java.gif"));
java.addItem(new TreeMenuItem("Link Scroll", "/java/linkscroll", "mainFrame", "menu_link_java.gif"));
java.addItem(new TreeMenuItem("Msg Scroll", "/java/msgscroll", "mainFrame", "menu_link_java.gif"));
java.addItem(new TreeMenuItem("Snake Pit", "/java/snakepit", "_blank", "menu_link_java.gif"));
java.addItem(new TreeMenuItem("Tail Gunner", "/java/tailgunner", "_blank", "menu_link_java.gif"));
treeMenu.items[2].makeSubmenu(java);

// JavaScript submenu.

var js = new TreeMenu();
js.addItem(new TreeMenuItem("Code Examples"));
js.addItem(new TreeMenuItem("References"));
js.addItem(new TreeMenuItem("Sites"));
js.addItem(new TreeMenuItem("Tutorial", "/js/tutorial", "_blank", "menu_link_ref.gif"));
treeMenu.items[3].makeSubmenu(js);

// JavaScript Code Examples submenu.

var js_ex = new TreeMenu();
js_ex.addItem(new TreeMenuItem("Cookies in JavaScript", "/js/cookielib.html", "mainFrame"));
js_ex.addItem(new TreeMenuItem("Dynamic Form", "/js/playoffs.html", "mainFrame"));
js_ex.addItem(new TreeMenuItem("Find in Page", "/js/find.html", "mainFrame"));
js_ex.addItem(new TreeMenuItem("Form Saver", "/js/formsaver.html", "mainFrame"));
js_ex.addItem(new TreeMenuItem("Form Validation", "/js/weekly.html", "mainFrame"));
js_ex.addItem(new TreeMenuItem("JavaScript Crunchinator", "/js/crunch.html", "mainFrame"));
js_ex.addItem(new TreeMenuItem("Window Sizing", "js/sizing.html", "mainFrame"));
js_ex.addItem(new TreeMenuItem("Year 2000 Countdown", "/js/y2k.html", "mainFrame"));

js.items[0].makeSubmenu(js_ex);

// JavaScript References submenu.

var js_refs = new TreeMenu();
js_refs.addItem(new TreeMenuItem("Microsoft: Scripting", "http://www.microsoft.com/scripting/", "_blank", "menu_link_ref.gif"));
js_refs.addItem(new TreeMenuItem("Netscape: JavaScript Documentation", "http://developer.netscape.com/docs/manuals/index.html", "_blank", "menu_link_ref.gif"));
js.items[1].makeSubmenu(js_refs);

// JavaScript Sites submenu.

var js_sites = new TreeMenu();
js_sites.addItem(new TreeMenuItem("Builder.com", "http://builder.cnet.com/Programming/", "_blank"));
js_sites.addItem(new TreeMenuItem("ScriptSearch", "http://www.scriptsearch.com/", "_blank"));
js_sites.addItem(new TreeMenuItem("Webreference: JavaScript", "http://webreference.com/js/", "_blank"));
js.items[2].makeSubmenu(js_sites);
