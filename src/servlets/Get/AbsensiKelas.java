package servlets.Get;

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

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class AbsensiKelas
 */
@WebServlet("/AbsensiKelas")
public class AbsensiKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AbsensiKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String tknScopeKampus=null;
			//System.out.println("start");
			SearchDbClassPoll sdb = new SearchDbClassPoll(isu.getNpm());
			String targetThsms = request.getParameter("targetThsms");
			if(targetThsms==null || Checker.isStringNullOrEmpty(targetThsms)) {
				targetThsms=Checker.getThsmsNow();
			}
			Vector vScope = isu.getScopeUpd7des2012ProdiOnly("viewAbsen");
			tknScopeKampus = isu.getScopeKampus("viewAbsen");
			//tambahkan nanti kalo tkn scope kampus nya null, maka pesan
			//msg - tidak memiliki hak akses kampus
			ListIterator li = vScope.listIterator();
			Vector vListKelasPerProdi = new Vector();
			ListIterator li1 = vListKelasPerProdi.listIterator();
			Vector vKdpst = new Vector();
			ListIterator liKdpst = vKdpst.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs);
				st.nextToken();
				String kdpst = st.nextToken();
				liKdpst.add(kdpst);
				//System.out.println("kdpst = "+kdpst);
			}
			try {
				/*
				 * karena ada beberapa lokasi sehingga 1 kdpst bisa terpanggil >1 alhasil akan ditampilkan dobel
				 */
				vKdpst = Tool.removeDuplicateFromVector(vKdpst);	
			}
			catch(Exception e) {
				System.out.println("AbsensiKelas.java line 74");
			}
			System.out.println("vKdpst="+vKdpst.size());
			liKdpst = vKdpst.listIterator();
			while(liKdpst.hasNext()) {
				//System.out.println(kdpst);
				String kdpst = (String)liKdpst.next();
				/*
				 * filter scope absen berdasar kampus - scope "viewAbsen" 
				 */
				
				
				Vector vTmp = sdb.getDistinctClassPerKdpst(targetThsms, kdpst, tknScopeKampus);
				//System.out.println("Kdpst="+kdpst);
				//System.out.println("vTmp="+vTmp.size());
				li1.add(vTmp);
			}
			/*
			li1 = vListKelasPerProdi.listIterator();
			while(li1.hasNext()) {
				Vector vTmp = (Vector) li1.next();
				if(vTmp!=null && vTmp.size()>0) {
					ListIterator liTmp = vTmp.listIterator();
					System.out.println("=============================");
					while(liTmp.hasNext()) {
						String brs = (String)liTmp.next();
						System.out.println(brs);
					}
				}
			}
			*
			*/
			//System.out.println("sampe2");
			session.setAttribute("vListKelasPerProdi", vListKelasPerProdi);
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Absensi/dashAbsensi.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?targetThsms="+targetThsms+"&tknScopeKampus="+tknScopeKampus).forward(request,response);
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
