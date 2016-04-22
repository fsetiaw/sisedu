package servlets.update.param;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.Param.UpdateDbParam;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class EditParamByCopying
 */
@WebServlet("/EditParamByCopying")
public class EditParamByCopying extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditParamByCopying() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("updat param");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			String targetObjId = request.getParameter("targetObjId");
			String based_obj_id = request.getParameter("based_obj");
			
			String nu_own_inbox_id = request.getParameter("nu_own_inbox_id");
			String nu_tu_id = request.getParameter("nu_tu_id");
			String nu_baa_id = request.getParameter("nu_baa_id");
			String nu_bak_id = request.getParameter("nu_bak_id");
			String nu_mhs_id = request.getParameter("nu_mhs_id");
			String kode_kmp = request.getParameter("kode_kmp");
			String sis_kul = request.getParameter("sis_kul");
			String nu_scp_kmp = request.getParameter("nu_scp_kmp");
			//System.out.println(targetObjId+","+based_obj);
			//System.out.println(nu_own_inbox_id);
			//System.out.println(nu_tu_id);
			//System.out.println(nu_baa_id);
			//System.out.println(nu_bak_id);
			//System.out.println(nu_mhs_id);
			//System.out.println(kode_kmp);
			//System.out.println(sis_kul);
			UpdateDbParam udb = new UpdateDbParam(isu.getNpm());
			//target = obj id yg mo dirubah
			//based = obj id sumber copyan
			udb.copyObjParam(Long.parseLong(targetObjId),Long.parseLong(based_obj_id),nu_own_inbox_id,nu_tu_id,nu_baa_id,nu_bak_id,nu_mhs_id,nu_scp_kmp,kode_kmp,sis_kul);
			
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
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
