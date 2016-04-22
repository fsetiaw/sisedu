package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.tools.PathFinder;
/**
 * Servlet implementation class ResetUsrPwd
 */
@WebServlet("/ResetUsrPwd")
public class ResetUsrPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetUsrPwd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("reseting usrpwd");
		String id_obj = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String obj_lvl = request.getParameter("obj_lvl");
		String kdpst = request.getParameter("kdpst");
		String cmd = request.getParameter("cmd");
		UpdateDb udb = new UpdateDb();
		String tknUsrPwd = udb.resetUsrPwd(kdpst,npm);
		request.setAttribute("tknUsrPwd", tknUsrPwd);
		String target = Constants.getRootWeb()+"/InnerFrame/displayUp.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url+"?objId="+id_obj+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&cmd="+cmd).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
