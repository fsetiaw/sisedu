package servlets.process;

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
import beans.tools.PathFinder;

/**
 * Servlet implementation class HapusTopic
 */
@WebServlet("/HapusTopic")
public class HapusTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HapusTopic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hide topik");
		String idTopik = request.getParameter("idTopik");
		String npmhsCreator = request.getParameter("npmhsCreator");
		String targetObjNickName = request.getParameter("targetObjNickName");
		System.out.println("idTopik=="+idTopik);
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(npmhsCreator.equalsIgnoreCase(isu.getNpm())) {
			UpdateDb udb = new UpdateDb(isu.getNpm());
			udb.hapusTopik(Long.valueOf(idTopik).longValue(),npmhsCreator);
		}	
		String target = Constants.getRootWeb()+"/InnerFrame/ContactUs/tampleteTopic.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url+"?atMenu=baa&targetObjNickName="+targetObjNickName).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
