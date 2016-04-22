package servlets.pengajuan;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class PrepPengajuanCuti
 */
@WebServlet("/PrepPengajuanCuti")
public class PrepPengajuanCuti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepPengajuanCuti() {
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
			//System.out.println("pengajuan cuti");
			String npmhs = request.getParameter("npm");
			String smawl = Getter.getSmawlCivitas(npmhs);
			Vector vListThsms = Tool.returnTokensListThsmsTpAntara(smawl, Checker.getThsmsKrs());
			//get status aktif pe thsms
			SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
			Vector vThsmsStmhs = sdb.getStatusMhsPerThsms(vListThsms, npmhs);
			session.setAttribute("vThsmsStmhs", vThsmsStmhs);
			/*cek statusnya saat ini
			 * 1. BILA ADA KRS BERARTI AKTIF
			 * 2. BILA TIDAK ADA CEK TABLE TRLSM
			 */
			//ListIterator li = vThsmsStmhs.listIterator();
			//while(li.hasNext()) {
			//	System.out.println((String)li.next());
			//}
			String target = Constants.getRootWeb()+"/InnerFrame/Pengajuan/cuti/dash_cuti.jsp";
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);

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
