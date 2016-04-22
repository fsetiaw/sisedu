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
 * Servlet implementation class GetAbsensiKelas
 */
@WebServlet("/GetAbsensiKelas")
public class GetAbsensiKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAbsensiKelas() {
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
			String targetKdpst = null;
			String shiftKelas = "";
			Vector vMhsContainer = new Vector();
			Vector vMhsUnHeregContainment = new Vector();
			String targetThsms =  request.getParameter("targetThsms");
			//System.out.println("targetThsms="+targetThsms);
			
			String target_kelas_info = request.getParameter("kelasInfo");
			//System.out.println("target_kelas_info="+target_kelas_info);
			if(target_kelas_info!=null && !Checker.isStringNullOrEmpty(target_kelas_info)) {
				SearchDbTrnlm sdb = new SearchDbTrnlm(isu.getNpm());
				
				ListIterator lim = vMhsContainer.listIterator();
				if(target_kelas_info.contains("||")) {
					//System.out.println("kelas gabungan");
					//System.out.println("target_kelas_info0="+target_kelas_info);
					StringTokenizer st = new StringTokenizer(target_kelas_info,"||");
					while(st.hasMoreTokens()) {
						String brs = st.nextToken();
						StringTokenizer st1 = new StringTokenizer(brs,"`");
						String kode_gabung = ""+st1.nextToken();
						String nakmk = ""+st1.nextToken();
						String nopll = ""+st1.nextToken();
						String shift = ""+st1.nextToken();
						String idkur = ""+st1.nextToken();
						String idkmk = ""+st1.nextToken();
						String npmdos = ""+st1.nextToken();
						String nmmdos = ""+st1.nextToken();
						String kdkmk = ""+st1.nextToken();
						String cancel = ""+st1.nextToken();
						String kdpst = ""+st1.nextToken();
						String kodeKampus = ""+st1.nextToken();
						String listNpm = ""+st1.nextToken();
						StringTokenizer st2 = new StringTokenizer(listNpm,",");
						//ListIterator liv = vMhsContainer.listIterator();
						while(st2.hasMoreTokens()) {
							String npm = st2.nextToken();
							lim.add(npm);
						}
						
						//vMhsContainer = sdb.getListMhs(vMhsContainer, targetThsms, idkmk, shift); 
						//try {
						//	vMhsContainer = Tool.removeDuplicateFromVector(vMhsContainer);
						//}
						//catch(Exception e) {
							
						//}
						//System.out.println("vMhsContainer size = "+vMhsContainer.size());
						//System.out.println("vMhsContainer size = "+vMhsContainer);
						targetKdpst=""+kdpst;
					}  
				}
				else {
					//System.out.println("kelas mandiri11");
					//System.out.println("target_kelas_info="+target_kelas_info);
					StringTokenizer st = new StringTokenizer(target_kelas_info,"`");
					String kode_gabung = ""+st.nextToken();
					String nakmk = ""+st.nextToken();
					String nopll = ""+st.nextToken();
					String shift = ""+st.nextToken();
					String idkur = ""+st.nextToken();
					String idkmk = ""+st.nextToken();
					String npmdos = ""+st.nextToken();
					String nmmdos = ""+st.nextToken();
					String kdkmk = ""+st.nextToken();
					String cancel = ""+st.nextToken();
					String kdpst = ""+st.nextToken();
					String kodeKampus = ""+st.nextToken();
					String listNpm = ""+st.nextToken();
					StringTokenizer st2 = new StringTokenizer(listNpm,",");
					//ListIterator liv = vMhsContainer.listIterator();
					while(st2.hasMoreTokens()) {
						String npm = st2.nextToken();
						lim.add(npm);
					}
					/*
					vMhsContainer = sdb.getListMhs(vMhsContainer, targetThsms, idkmk, shift);
					try {
						vMhsContainer = Tool.removeDuplicateFromVector(vMhsContainer);
					}
					catch(Exception e) {
						
					}
					*/
					//System.out.println("vMhsContainer size = "+vMhsContainer.size());
					//System.out.println("vMhsContainer size = "+vMhsContainer);
					targetKdpst=""+kdpst;
				}
				try {
					vMhsContainer = Tool.removeDuplicateFromVector(vMhsContainer);	
				}
				catch(Exception e) {}
				
				vMhsContainer = sdb.getInfoMhsTambahan(vMhsContainer);
				//filter hanya mhs yg terdaftar saja dengan pasing vMhsUnHeregContainment
				//System.out.println("vMhsUnHeregContainment="+vMhsUnHeregContainment.size());
				//System.out.println("vMhsContainer="+vMhsContainer.size());
				sdb.filterHanyaMhsYgSdhDaftarUlang(vMhsContainer, targetThsms,vMhsUnHeregContainment);
				//System.out.println("vMhsUnHeregContainment="+vMhsUnHeregContainment.size());
				//System.out.println("vMhsContainer="+vMhsContainer.size());
				Collections.sort(vMhsContainer);
				
				session.setAttribute("vMhsUnHeregContainment", vMhsUnHeregContainment);
				session.setAttribute("vMhsContainer", vMhsContainer);
				session.setAttribute("target_kelas_info", target_kelas_info);
			}
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Absensi/daftarAbsensiKelas.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff+"?atMenu=absensi").forward(request,response);
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
