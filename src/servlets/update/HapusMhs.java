package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;;
/**
 * Servlet implementation class HapusMhs
 */
@WebServlet("/HapusMhs")
public class HapusMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HapusMhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("hapus");
		
		HttpSession session = request.getSession(true);
		String npmhs = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		System.out.println("npm&kdpst="+npmhs+"-"+kdpst);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		UpdateDb udb = new UpdateDb();
		udb.deleteMhs(kdpst,npmhs);
		String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
