package servlets.process.kartu_ujian_rules;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.kartu_ujian_rules.SearchDbKartuUjianRules;
import beans.dbase.mhs.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepKartuUjian
 */
@WebServlet("/PrepKartuUjian")
public class PrepKartuUjian extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepKartuUjian() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			//System.out.println("pererp");
			Vector vScope_cmd = (Vector)session.getAttribute("vScope_cmd");
			session.removeAttribute("vScope_cmd");
			//122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
			if(vScope_cmd!=null && vScope_cmd.size()>0) {
				//System.out.println("pererp vScope_cmd sizze = "+vScope_cmd.size());
				SearchDbKartuUjianRules sdb = new SearchDbKartuUjianRules(isu.getNpm());
				vScope_cmd = sdb.getRules(vScope_cmd);
				//System.out.println("pererp vScope_cmd sizze2 = "+vScope_cmd.size());
				session.setAttribute("vScope_cmd",vScope_cmd);
			}
			
			
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/KartuUjian/dashKartuUjian.jsp";
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);

		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
