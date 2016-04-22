package servlets.process.daftarUlang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.mhs.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class DaftarUlangByOp
 */
@WebServlet("/DaftarUlangByOp")
public class DaftarUlangByOp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DaftarUlangByOp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("daftar Ulang");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String id_obj = request.getParameter("id_obj");
		String nmmhs = request.getParameter("nmmhs");
		String npmhs = request.getParameter("npmhs");
		String obj_lvl= request.getParameter("obj_lvl");
		String kdpst=request.getParameter("kdpst");
		UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
		udb.updateDaftarUlangTableByOperator(kdpst, npmhs);
		request.getRequestDispatcher("get.profile?id_obj="+id_obj+"&nmm="+nmmhs+"&npm="+npmhs+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=dashboard").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
