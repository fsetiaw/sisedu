package servlets.pengajuan;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.pengajuan.cuti.SearchDbCuti;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ListPengajuanCuti
 */
@WebServlet("/ListPengajuanCuti")
public class ListPengajuanCuti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPengajuanCuti() {
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
		/*
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			//System.out.println("list cuti");
			Vector v = null;
			SearchDbCuti sdc = new SearchDbCuti(isu.getNpm());
			String target_thsms = Checker.getThsmsHeregistrasi();
			boolean show = true;
			String target = Constants.getRootWeb()+"/InnerFrame/Pengajuan/cuti/dash_cuti_baru.jsp"; 
			if(isu.getObjNickNameGivenObjId().contains("MHS")) {
				
				//kalo dipake untuk notification boolean show = true
				//kalo liat riwayat !show
				
				v = sdc.getStatusCutiRequest(target_thsms, isu.getNpm(), show);
				
			}
			else {
				v = sdc.getStatusCutiRequest(target_thsms, show);
				//karena fungsi ini memanggil
			}
			
			request.setAttribute("vReqStat", v);
			
			
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);

		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
