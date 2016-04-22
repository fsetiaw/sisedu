package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.setting.Constants;
import beans.tools.*;
/**
 * Servlet Filter implementation class UsrPwd
 */
@WebFilter("/UsrPwd")
public class UsrPwd implements Filter {

    /**
     * Default constructor. 
     */
    public UsrPwd() {
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
		boolean avail = false;
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
		String uname = request.getParameter("uname");
		String pwd1 = request.getParameter("pwd1");
		String pwd2 = request.getParameter("pwd2");
		System.out.println(uname+" "+pwd1+" "+pwd2);
		String msg = null;
		if(pwd1.length()<5) {
			msg = "password harus minimal berisi 5 char";
		}
		else {
			if(!pwd1.equals(pwd2)) {
				msg = "password tidak sama";
			}
			else {
				boolean char_err = false;
				for(int i=0;i<uname.length();i++) {
					if(!char_err) {
						int k = Character.getNumericValue(uname.charAt(i));
						if(k<0 || k>35) {
							char_err=true;
						}
					}	
				}
				for(int i=0;i<pwd1.length();i++) {
					if(!char_err) {
						int k = Character.getNumericValue(pwd1.charAt(i));
						if(k<0 || k>35) {
							char_err=true;
						}
					}
				}
				if(char_err) {
					msg = "hanya boleh menggunakan huruf atau angka";
				}
			}	
		}
		if(msg!=null) {
			System.out.println("1");
			res.sendRedirect("editUsrPwd.jsp?msg="+msg);
			System.out.println("2");
		}
		else {
			//cek user validation
			avail = Checker.isUsrNameAvailable(uname, validUsr.getNpm()) ;
			if(!avail) {
				msg = "username = "+uname+" sudah ada yang menggunakan, harap coba username yang lain.";
				res.sendRedirect("editUsrPwd.jsp?msg="+msg);
			}
			else {
				//valid
				boolean sukses = validUsr.updateUserPwd(uname,  pwd1);
				session.setAttribute("sukses", ""+sukses);
				chain.doFilter(request, response);
				//if(sukses) {
				//	System.out.println("berhasil update pwd");
				//}
				//else {
				//	System.out.println("gagal update pwd");
				//}
			}

		}
		System.out.println(msg);
		System.out.println(avail);
		// pass the request along the filter chain
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
