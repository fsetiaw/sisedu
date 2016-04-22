package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.StringTokenizer;
import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
/**
 * Servlet implementation class ApprovalBukaKelas
 */
@WebServlet("/ApprovalBukaKelas")
public class ApprovalBukaKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalBukaKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("approval bk");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String brs = (String)session.getAttribute("brs");
		//System.out.println("brs=="+brs);
		StringTokenizer st1 = new StringTokenizer(brs,"||");
		String nakmk1 = st1.nextToken();
		String smsmk1 = st1.nextToken();
		String cmd1 = st1.nextToken();
		String idkur1 = st1.nextToken();
		String idkmk1 = st1.nextToken();
		String thsms1 = st1.nextToken();
		String kdpst1 = st1.nextToken();
		String shift1 = st1.nextToken();
		String noKlsPll1 = st1.nextToken();
		String initNpmInput1 = st1.nextToken();
		String latestNpmUpdate1 = st1.nextToken();
		String latestStatusInfo1 = st1.nextToken();
		String locked1 = st1.nextToken();
		String npmdos1 = st1.nextToken();
		String nodos1 = st1.nextToken();
		String npmasdos1 = st1.nextToken();
		String noasdos1 = st1.nextToken();
		String cancel = st1.nextToken();
		String kodeKelas1 = st1.nextToken();
		String kodeRuang1 = st1.nextToken();
		String kodeGedung1 = st1.nextToken();
		String kodeKampus1 = st1.nextToken();
		String tknHrTime1 = st1.nextToken();
		String nmmdos1 = st1.nextToken();
		String nmmasdos1 = st1.nextToken();
		String enrolled1 = st1.nextToken();
		String maxEnrol1 = st1.nextToken();
		String minEnrol1 = st1.nextToken();
		String subKeterMk1 = st1.nextToken();
		String initReqTime1 = st1.nextToken();
		String tknNpmApproval1 = st1.nextToken();
		String tknApprovalTime1 = st1.nextToken();
		String targetTotMhs1 = st1.nextToken();
		String passed1 = st1.nextToken();
		String rejected1 = st1.nextToken();
		
		
		
		String submit = request.getParameter("submit");
		String thsms = request.getParameter("thsmsPmb");
		String kdpst = request.getParameter("kdpst");
		String alasan = request.getParameter("alasan");
		String needAprBy = request.getParameter("needAprBy");
		String infoTarget = request.getParameter("infoTarget");
		StringTokenizer st = new StringTokenizer(infoTarget,"||");
		kdpst = st.nextToken();
		String nmpst = st.nextToken();
		String kdjen = st.nextToken();
		needAprBy = st.nextToken();
		String right = st.nextToken();
		String tknApr = st.nextToken();
		boolean approvee =  Boolean.valueOf(st.nextToken()).booleanValue();
		//System.out.println("infoTarget="+infoTarget);
		//System.out.println("brs="+brs);
		/*
		 * 1.
		 */
		if(submit.equalsIgnoreCase("tolak")) {
			//System.out.println("ditolak oleh "+needAprBy);
			UpdateDb udb = new UpdateDb();
			int i = 0;
			i = udb.tolakRequestBukaKelas(thsms,kdpst, needAprBy,alasan);
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/progressBarTolakBukaKelas.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?pb=yes&msg=Update Status Berhasil, Harap Menunggu Anda Sedang Dialihkan").forward(request,response);
			//System.out.println("i="+i);
		}
		else {
			UpdateDb udb = new UpdateDb(isu.getNpm());
			int i = 0;
			i = udb.grantedRequestBukaKelas(thsms,kdpst,needAprBy,alasan,tknApr);
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/progressBarTolakBukaKelas.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?pb=yes&msg=Update Status Berhasil, Harap Menunggu Anda Sedang Dialihkan").forward(request,response);
		}
		//System.out.println(submit+","+kdpst+","+thsms+","+alasan);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
