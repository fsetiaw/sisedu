package servlets.pengajuan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.topik.UpdateDbTopik;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class BatalCuti
 */
@WebServlet("/BatalCuti")
public class BatalCuti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatalCuti() {
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
			//System.out.println("mas");
			String id_obj= request.getParameter("id_obj");
			String nmm= request.getParameter("nmm");
			String npm= request.getParameter("npm");
			String obj_lvl= request.getParameter("obj_lvl");
			String kdpst= request.getParameter("kdpst");
			String cmd= request.getParameter("cmd");
			String target_thsms = request.getParameter("target_thsms");
			UpdateDbTopik udt = new UpdateDbTopik(isu.getNpm());
			udt.cancelPengajuan(npm, target_thsms,"CUTI",isu.getIdObj());
			String target = "go.moCuti?target_thsms="+target_thsms+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst +"&cmd=cuti";
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(target).forward(request,response);
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
