package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepForListMhsTamplete
 */
@WebServlet("/PrepForListMhsTamplete")
public class PrepForListMhsTamplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepForListMhsTamplete() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String listNpmhs = request.getParameter("listNpmhs");
			if(listNpmhs!=null && !Checker.isStringNullOrEmpty(listNpmhs)) {
				SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
				listNpmhs=sdb.getNmmhsDanProdiGiven(listNpmhs);
			}
			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Mhs/ListInfoMhsType2.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff+"?listNpmhs="+listNpmhs).forward(request,response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
