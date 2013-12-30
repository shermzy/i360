package CP_Classes;

import javax.servlet.* ;
import javax.servlet.http.* ;

public class ClearCache implements Filter
{
	public void init() {}
	public void init(FilterConfig config) throws javax.servlet.ServletException {}
	public void init(ServletConfig config) throws ServletException {}
	
	public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException    
	{
	    //HttpServletRequest req = (HttpServletRequest)request ;
	    HttpServletResponse res = (HttpServletResponse)response ;
		
	    res.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
	    res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    res.addHeader("Cache-Control", "post-check=0, pre-check=0");
	    res.setHeader("Pragma", "no-cache");
	    chain.doFilter(request, response);
	}
	public void destroy() {}
	
}