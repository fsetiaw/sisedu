package servlets.update.trnlm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class KelasKrsAdjustment
 */
@WebServlet("/KelasKrsAdjustment")
public class KelasKrsAdjustment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KelasKrsAdjustment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("upd");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			
			String id_obj=""+request.getParameter("id_obj");
			String nmm=""+request.getParameter("nmm");
			String npm=""+request.getParameter("npm");
			String kdpst=""+request.getParameter("kdpst");
			String obj_lvl=""+request.getParameter("obj_lvl");
			String thsms_krs=""+request.getParameter("thsmsKrs");
			String[]kls_info=request.getParameterValues("kls_info");
			UpdateDbTrnlm udb = new UpdateDbTrnlm(isu.getNpm());
			udb.kelasKrsAdjustmen(kls_info,npm);
			
			request.getRequestDispatcher("get.histKrs?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=editKrs").forward(request,response);
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
