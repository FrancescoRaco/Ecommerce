package filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UtenteBean;

public class LoginFilter implements Filter {
	
	public void  init(FilterConfig config) throws ServletException {
	      
		// Get init parameter 
//		String testParam = config.getInitParameter("test-param"); 
		
		//Print the init parameter 
//		System.out.println("Test Param: " + testParam); 
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws java.io.IOException, ServletException {

		System.out.println("Inside Login Filter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        UtenteBean utenteBean = null;
        if (session != null) {
        	utenteBean = (UtenteBean) session.getAttribute("utenteBean");
        	if (utenteBean != null && utenteBean.getCodiceFiscale() != null && !utenteBean.getCodiceFiscale().isEmpty() && utenteBean.isLoggedIn()) {
            	response.sendRedirect(request.getContextPath() + "/faces/pages/home.xhtml");
            	return;
            }
        }
        chain.doFilter(req, res);
   }

   public void destroy( ) {
	   /* Called before the Filter instance is removed from service by the web container*/
   }

}
