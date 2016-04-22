package servlets.update;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.setting.Constants;

/**
 * Servlet implementation class UsrPwd
 */
@WebServlet("/UsrPwd")
public class UsrPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public UsrPwd() {
//        super();
        // TODO Auto-generated constructor stub
//    }
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		/*
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	      e.printStackTrace();
	    }
	    */
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String sukses = (String)session.getAttribute("sukses");
	    out.println("<!DOCTYPE html>");
	    out.println("<head></head>");
	    out.println("<body>");
	    if(sukses.equalsIgnoreCase("true")) {
	    	out.println("<h2>update berhasil....redirecting</h2>");
	    }
	    else {
	    	out.println("<h2>update gagal....redirecting</h2>");
	    }
	    out.println("</body>");
	    out.println("</html>");
	    session.removeAttribute("validUsr");
	    session.invalidate();
	    out.print("<meta http-equiv=\"refresh\" content=\"2;url="+Constants.getRootWeb()+"\">");
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
