package servlets.pengajuan;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.pengajuan.cuti.SearchDbCuti;
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
public class PrepPengajuanCuti_baru extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepPengajuanCuti_baru() {
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
			//System.out.println("pengajuan cuti baru");
			String npmhs = request.getParameter("npm");
			boolean show = false;
			if(npmhs==null || Checker.isStringNullOrEmpty(npmhs)) {
				//yg manggil dr home = notification
				npmhs = isu.getNpm();
				show = true;
			}
			String kdpst = request.getParameter("kdpst");
			String smawl = Getter.getSmawlCivitas(npmhs);
			String target_thsms = request.getParameter("target_thsms");
			//Vector vListThsms = Tool.returnTokensListThsmsTpAntara(smawl, Checker.getThsmsKrs());
			//get status aktif pe thsms
			//SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
			//Vector vThsmsStmhs = sdb.getStatusMhsPerThsms(vListThsms, npmhs);
			//session.setAttribute("vThsmsStmhs", vThsmsStmhs);
			/*cek statusnya saat ini
			 * 1. BILA ADA KRS BERARTI AKTIF
			 * 2. BILA TIDAK ADA CEK TABLE TRLSM
			 */
			//ListIterator li = vThsmsStmhs.listIterator();
			//while(li.hasNext()) {
			//	System.out.println((String)li.next());
			//}
			
			/*
			 * yg manggil ini dari inner menu mahasiswa
			 * untuk memperlihatkan apakah ada riwayat pengajuan cuti utk thsms terkait
			 * jadi yg bisa liat adalah target_obj_id dan yg ada scopenya mencakup
			 * kdpst mhs terkait
			 */
			//get scope cuti
			boolean boleh_liat_berdasarkan_scope = false;
			Vector vScope = isu.getScopeUpd11Jan2016ProdiOnly("cuti");
			ListIterator li = vScope.listIterator();
			while(li.hasNext() && !boleh_liat_berdasarkan_scope) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs);
				if(st.countTokens()==1) {
					//own
					if(npmhs.equalsIgnoreCase(isu.getNpm())) {
						boleh_liat_berdasarkan_scope = true;
					}
				}
				else {
					st.nextToken();
					String scope_kdpst = st.nextToken();
					if(scope_kdpst.equalsIgnoreCase(kdpst)) {
						boleh_liat_berdasarkan_scope = true;
					}	
				}
				
				//System.out.println("scope-"+brs);
			}
			SearchDbCuti sdc = new SearchDbCuti(isu.getNpm());
			Vector v = null;
			boolean am_i_stu = Checker.am_i_stu(session);
			//if(am_i_stu) {
			v = sdc.getStatusCutiRequest_v1(target_thsms, npmhs, show, am_i_stu, boleh_liat_berdasarkan_scope, isu.returnScopeProdiOnlySortByKampusWithListIdobj("cuti") );	
			//}
			//else {
			//	v = sdc.getStatusCutiRequest(target_thsms, false);	
			//}
			
			//System.out.println("v sini");
			request.setAttribute("vReqStat", v);
			String target = Constants.getRootWeb()+"/InnerFrame/Pengajuan/cuti/dash_cuti_baru.jsp";
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?backToHome="+show+"&smawl="+smawl).forward(request,response);
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
