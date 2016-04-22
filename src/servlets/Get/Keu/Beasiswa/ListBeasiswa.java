package servlets.Get.Keu.Beasiswa;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.Beasiswa.SearchDbBeasiswa;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ListBeasiswa
 */
@WebServlet("/ListBeasiswa")
public class ListBeasiswa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListBeasiswa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("beasiswa");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String atMenu = ""+request.getParameter("atMenu");
			//System.out.println("atMenu1="+atMenu);
			//request.setAttribute("atMenu", atMenu);
			SearchDbBeasiswa sdb = new SearchDbBeasiswa(isu.getNpm());
			
			Vector v = sdb.getListPaketBeasiswa();
			System.out.println("v size= "+v);
			session.setAttribute("vListNamaPaketBea", v);
			String target = Constants.getRootWeb()+"/InnerFrame/Keu/Beasiswa/listPaketBeasiswa.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?atMenu="+atMenu).forward(request,response);
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
