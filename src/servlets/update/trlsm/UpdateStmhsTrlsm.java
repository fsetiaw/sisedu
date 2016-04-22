package servlets.update.trlsm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateStmhsTrlsm
 */
@WebServlet("/UpdateStmhsTrlsm")
public class UpdateStmhsTrlsm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateStmhsTrlsm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			String id_obj=request.getParameter("id_obj");
			String nmm=request.getParameter("nmm");
			String npm=request.getParameter("npm");
			String obj_lvl=request.getParameter("obj_lvl");
			String kdpst=request.getParameter("kdpst");
			String cuti=request.getParameter("cuti");
			/*
			 * DISINI KITA HANYA UPDATE STATUS DI TRLSM
			 * DAN CIVITAS
			 * 
			 * HARUS DIPIKIRKAN BILA TERJADI KONFLIK STATUS, BILA ACTIF DI TRNLM TP DI TRLSM SEBALIKNYA
			 */
			UpdateDbTrlsm udb = new UpdateDbTrlsm(isu.getNpm());
			String[]thsms_stmhs = request.getParameterValues("thsms_stmhs");
			//System.out.println("kdpst= "+kdpst);
			udb.updStmhs(kdpst,npm, thsms_stmhs);
			
			
			
			//System.out.println("update stmhs");
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher("go.prepAjuanCuti?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst +"&cmd=cuti&msg=upd").forward(request,response);
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
