package servlets.tamplete.form_pilihan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import java.util.ListIterator;
import beans.dbase.SearchDb;
import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PilihanObjectAvailableFoUsr
 */
@WebServlet("/PilihanObjectAvailableFoUsr")
public class PilihanObjectAvailableFoUsr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PilihanObjectAvailableFoUsr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * ini dijadikan tamplete untuk servlet yg mengarah pada form tamplete selection
		 */
		
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String forward_to = request.getParameter("fwdTo");
			String back_to = request.getParameter("backTo");
			String at_menu = request.getParameter("atMenu");
			String scope_param = request.getParameter("scope");
			
			
			Vector v = isu.getScopeUpd7des2012(scope_param);
			ListIterator li = v.listIterator();
			
			request.setAttribute("vList", v);
			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/select/selectObj.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?scope="+scope_param+"&atMenu="+at_menu+"&backTo="+back_to+"&fwd="+forward_to).forward(request,response);
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
