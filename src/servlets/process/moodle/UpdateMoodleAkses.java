package servlets.process.moodle;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.moodle.UpdateDbMoodle;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateMoodleAkses
 */
@WebServlet("/UpdateMoodleAkses")
public class UpdateMoodleAkses extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMoodleAkses() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
			//kode here
			//SearchDbMoodle sdm = new SearchDbMoodle(isu.getNpm());
			//Vector v_role = sdm.listRole();
			//request.setAttribute("v_role", v_role);
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String objId = request.getParameter("objId");
			String obj_lvl = request.getParameter("obj_lvl");
			String kdpst = request.getParameter("kdpst");
			String backTo = request.getParameter("backTo");	
			String obj_nick = Checker.getObjNickname(npm);
			boolean is_target_mhs = false;
			if(obj_nick.contains("MHS")) {
				is_target_mhs = true;
			}
			//System.out.println("ininp="+npm);
			UpdateDbMoodle udbm = new UpdateDbMoodle(isu.getNpm());
			//System.out.println("ininp2=");
			int i = udbm.updateUsrTable(npm);
			//System.out.println("update="+i);
			if(i<1) {
				udbm.insertUsrTable(npm, nmm, obj_nick);
			}
			//System.out.println("done");
			String target = Constants.getRootWeb()+"/InnerFrame/moodle/moodle_form.jsp";
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);

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
