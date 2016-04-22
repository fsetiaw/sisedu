package filters;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.login.*;
import beans.setting.Constants;
import beans.tools.PathFinder;
/**
 * Servlet Filter implementation class CekUsrSession
 */
@WebFilter("/CekUsrSession")
public class CekUsrSession implements Filter {

    /**
     * Default constructor. 
     */
    public CekUsrSession() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
		if(validUsr==null) {
			//String target = Constants.getRootWeb()+"/index.jsp";
			//String uri = req.getRequestURI();
			//String url_ff = PathFinder.getPath(uri, target);
			//res.sendRedirect("http://localhost:8080/ToKoTaKu/");
			res.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
			//System.out.println("filter ff = "+Constants.getRootWeb()+"/ErrorPage/noUserSession.jsp");
			//req.setAttribute("jusr_redirected", "true");
		}
		else {
		// pass the request along the filter chain
			chain.doFilter(request, response);
		}	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
