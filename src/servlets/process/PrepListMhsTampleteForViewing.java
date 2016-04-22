package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import java.util.ListIterator;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.SearchDb;
/**
 * Servlet implementation class PrepListMhsTampleteForViewing
 */
@WebServlet("/PrepListMhsTampleteForViewing")
public class PrepListMhsTampleteForViewing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepListMhsTampleteForViewing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("list mhs tamplete");
		//HttpSession session = request.getSession(true);
		//InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String listNpm = request.getParameter("listNpm");
		Vector v = null;
		SearchDb sdb = new SearchDb();
		v = sdb.getInfoCivitasForTampleteListMhs(listNpm);
		request.setAttribute("vListInfo", v);
		System.out.println(listNpm);
		String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/listMhs.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url_ff).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
