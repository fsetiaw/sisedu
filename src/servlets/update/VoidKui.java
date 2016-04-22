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

/**
 * Servlet implementation class VoidKui
 */
@WebServlet("/VoidKui")
public class VoidKui extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoidKui() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("void kui");

		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");

        String id_obj = request.getParameter("id_obj");
        String obj_lvl = request.getParameter("obj_lvl");
        String kdpst = request.getParameter("kdpst");
		String npmhs = request.getParameter("npm");
		String nmmhs = request.getParameter("nmm");
		String kuiid = request.getParameter("kuiid");
		String void_keter = request.getParameter("void_keter");
		//String keter = request.getParameter("keter");
		String opnpm = isu.getNpm();
		String opnmm = isu.getFullname();
		
		//System.out.println(kuiid+","+npmhs+","+void_keter);
		UpdateDb udb = new UpdateDb();
		udb.voidKui(kuiid,opnpm,opnmm,void_keter);
		request.getRequestDispatcher("get.histPymnt?id_obj="+id_obj+"&nmm="+nmmhs+"&npm="+npmhs+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
