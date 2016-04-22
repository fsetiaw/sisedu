package servlets.Get.ClassPool;

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

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class ListKelasOpened
 */
@WebServlet("/ListKelasOpened")
public class ListKelasOpened extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListKelasOpened() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		//System.out.println("targetThsms="+targetThsms);
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
			//kode here
			//System.out.println("listKelas");
			String targetThsms = request.getParameter("targetThsms");
			if(targetThsms==null || Checker.isStringNullOrEmpty(targetThsms)) {
				targetThsms=Checker.getThsmsNow();
			}
			//isu.getKode_kmp_dom()
			String target_kmp = request.getParameter("target_kmp");
			//if(target_kmp == null || Checker.isStringNullOrEmpty(target_kmp)) {
			//	target_kmp = isu.getKode_kmp_dom();
			//}
			//System.out.println(target_kmp);
			SearchDbClassPoll sdb = new SearchDbClassPoll(isu.getNpm());
			//Vector vListKelasDibuka = sdb.getListOpenedClass(targetThsms);
			String list_scope_obj_id = null;
			Vector v_list_scope_obj_id = isu.getScopeUpd11Jan2016ProdiOnly("viewAbsen"); //format brs 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
			
			//karena untuk cetak absen maka di filter hanya yg ada mhsnya saja
			Vector vListKelasDibuka = sdb.getListOpenedClassYgAdaMhsnyaOnly(targetThsms, v_list_scope_obj_id);
			session.setAttribute("vListKelasDibuka", vListKelasDibuka);
			/*
			Vector vKampus = new Vector();
			ListIterator liKam = vKampus.listIterator();
			String idkur1 = null;
			String idkmk1 = null;
			//String thsms1 = null;
			String kdpst1 = null;
			String shift1 = null;
			String noKlsPll1 = null;
			String initNpmInput1 = null;
			String latestNpmUpdate1 = null;
			String latestStatusInfo1 = null;
			String curr_avail_status = null;
			String locked1 = null;
			String npmdos1 = null;
			String nodos1 = null;
			String npmasdos1 = null;
			String noasdos1 = null;
			String cancel = null;
			String kodeKelas1 = null;
			String kodeRuang1 = null;
			//kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+
			//min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+
			//passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
			String kodeGedung1 = null;
			String kodeKampus1 = null;
			//liKam.add(kodeKampus1);
			String tknHrTime1 = null;
			String nmmdos1 = null;
			String nmmasdos1 = null;
			String enrolled1 = null;
			String maxEnrol1 = null;
			String minEnrol1 = null;
			String subKeterMk1 = null;
			String initReqTime1 = null;
			String tknNpmApproval1 = null;
			String tknApprovalTime1 = null;
			String targetTotMhs1 = null;
			String passed1 = null;
			String rejected1 = null;
			String kode_gabung_kls = null;
			String kode_gabung_kmp = null;
			String unique_id = null;
			String kdkmk = null;
			String nakmk = null;
			String skstm = null;
			String skspr = null;
			String skslp = null;
			
			//System.out.println("vListKelasDibuka"+vListKelasDibuka.size());
			if(vListKelasDibuka!=null && vListKelasDibuka.size()>0) {
				ListIterator li = vListKelasDibuka.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					System.out.println("nar="+brs);
					StringTokenizer st = new StringTokenizer(brs,"`");
					//(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+
					//curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+
					idkur1 = st.nextToken();
					idkmk1 = st.nextToken();
					//thsms1 = st.nextToken();
					kdpst1 = st.nextToken();
					shift1 = st.nextToken();
					noKlsPll1 = st.nextToken();
					initNpmInput1 = st.nextToken();
					latestNpmUpdate1 = st.nextToken();
					latestStatusInfo1 = st.nextToken();
					curr_avail_status = st.nextToken();
					locked1 = st.nextToken();
					npmdos1 = st.nextToken();
					nodos1 = st.nextToken();
					npmasdos1 = st.nextToken();
					noasdos1 = st.nextToken();
					cancel = st.nextToken();
					kodeKelas1 = st.nextToken();
					kodeRuang1 = st.nextToken();
					//kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+
					//min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+
					//passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
					kodeGedung1 = st.nextToken();
					kodeKampus1 = st.nextToken();
					liKam.add(kodeKampus1);
					System.out.println("kodeKampus1="+kodeKampus1);
					/*
					String tknHrTime1 = null;
					String nmmdos1 = null;
					String nmmasdos1 = null;
					String enrolled1 = null;
					String maxEnrol1 = null;
					String minEnrol1 = null;
					String subKeterMk1 = null;
					String initReqTime1 = null;
					String tknNpmApproval1 = null;
					String tknApprovalTime1 = null;
					String targetTotMhs1 = null;
					String passed1 = null;
					String rejected1 = null;
					String kode_gabung_kls = null;
					String kode_gabung_kmp = null;
					String unique_id = null;
					String kdkmk = null;
					String nakmk = null;
					String skstm = null;
					String skspr = null;
					String skslp = null;
					
				}
				
			}
			System.out.println("size vkam = "+vKampus.size());
			try {
				vKampus = Tool.removeDuplicateFromVector(vKampus);
				Collections.sort(vKampus);
				vKampus = Checker.getNickNameKampus(vKampus);
			}
			catch(Exception e) {}
			request.setAttribute("vKampus", vKampus);
			*/
			///ToUnivSatyagama/WebContent/InnerFrame/Perkuliahan/Absensi/ListKelasOpened.jsp
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Absensi/ListKelasOpened.jsp";
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?target_kmp="+target_kmp+"&target_thsms="+targetThsms).forward(request,response);

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
