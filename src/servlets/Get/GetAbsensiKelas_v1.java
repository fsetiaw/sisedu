package servlets.Get;

import java.io.IOException;
import java.util.Collections;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.trnlm.SearchDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class GetAbsensiKelas_v1
 */
@WebServlet("/GetAbsensiKelas_v1")
public class GetAbsensiKelas_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAbsensiKelas_v1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("absensi kelas0");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			
			String kelas_info = request.getParameter("kelasInfo");
			StringTokenizer st = new StringTokenizer(kelas_info,"`");
			String target_kmp = st.nextToken();
			String kdkmk = st.nextToken();
			String nakmk = st.nextToken();
			String nmmdos = st.nextToken();
			String shift = st.nextToken();
			String cuid = st.nextToken();
			SearchDbTrnlm sdb = new SearchDbTrnlm(isu.getNpm());
			Vector vListMhs = sdb.getListMhs(cuid);
			//cek apa boleh cetak
			boolean allow_cetak = false; 
			if(isu.isAllowTo("allowCetakAbsenKls")>0) {
				allow_cetak = true;
			}
			session.setAttribute("vListMhs", vListMhs);
			
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Absensi/daftarAbsensiKelas.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff+"?atMenu=absensi&allow_cetak="+allow_cetak).forward(request,response);
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
