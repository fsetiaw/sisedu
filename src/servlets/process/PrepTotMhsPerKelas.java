package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.daftarUlang.SearchDbInfoDaftarUlangTable;
import beans.dbase.makul.SearchDbMk;
import beans.dbase.trnlm.SearchDbTrnlm;
import beans.login.InitSessionUsr;

import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;
/**
 * Servlet implementation class PrepTotMhsPerKelas
 */
@WebServlet("/PrepTotMhsPerKelas")
public class PrepTotMhsPerKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepTotMhsPerKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("puazsa");
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
			String kdpst_nmpst = request.getParameter("kdpst_nmpst");
			String atMenu = request.getParameter("atMenu");
			//System.out.println(kdpst_nmpst);
			String kdpst = null;
			String nmpst = null;
			String thsms = request.getParameter("target_thsms");
			Vector v = null;
			Vector vListNpmOnTrnlm = null;
			if(kdpst_nmpst!=null) {
				StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
				kdpst = st.nextToken();
				nmpst = st.nextToken();
				SearchDbMk sdb = new SearchDbMk();
				
				if(thsms==null || thsms.equalsIgnoreCase("null")) {
					thsms = Checker.getThsmsPmb();
				}
				//v = sdb.getTotMhsPerMakul(thsms,kdpst);
				//System.out.println("tthsms="+thsms);
				v = sdb.getTotMhsPerMakul_v2(thsms,kdpst);
				SearchDbTrnlm  sdt = new SearchDbTrnlm(isu.getNpm());
				vListNpmOnTrnlm =  sdt.getListNpmhsOnTrnlm(thsms, kdpst);
				SearchDbInfoDaftarUlangTable sdd = new SearchDbInfoDaftarUlangTable(isu.getNpm());
				String thsms_lalu = Tool.returnPrevThsmsGivenTpAntara(thsms);//thsms semester lalu
				//System.out.println("thsms = "+thsms);
				//System.out.println("thsms lalu = "+thsms_lalu);
				Vector vApprovedHeregistrasi = sdd.getListMhsYgSudahDaftarUlangAndApproved(thsms_lalu, kdpst);
				request.setAttribute("v1v2", v);
				request.setAttribute("vListNpmOnTrnlm", vListNpmOnTrnlm);
				request.setAttribute("vApprovedHeregistrasi", vApprovedHeregistrasi);
				/*
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					System.out.println(brs);
				}
				*/
				//request.setAttribute("target_thsms", thsms);
				String target = Constants.getRootWeb()+"/InnerFrame/Summary/viewTotMhsPerMakul.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?target_thsms="+thsms+"&atMenu="+atMenu).forward(request,response);
				
			}
		}		//ListIterator li = v.list
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
