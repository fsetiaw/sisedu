package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.ListIterator;

import beans.setting.Constants;
import beans.tools.*;
/**
 * Servlet implementation class PrepFormApprovalBukaKelas
 */
@WebServlet("/PrepFormApprovalBukaKelas")
public class PrepFormApprovalBukaKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepFormApprovalBukaKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("prepFormreqBukaKelas");
		HttpSession session = request.getSession(true);
		Vector vBk = (Vector) session.getAttribute("vBukaKelas");
		Vector vTmp = new Vector();
		ListIterator liTmp = vTmp.listIterator();
		String targetInfo = request.getParameter("infoTarget");
		//System.out.println("infoTarget=="+targetInfo);
		//System.out.println("size0 = "+vBk.size());
		boolean lanjutProcessNoDuplicate = false;
		if(vBk!=null && vBk.size()>0 && targetInfo!=null && !Checker.isStringNullOrEmpty(targetInfo)) {
			//re arrange biar di sort berdasarkan nmkdkmk
			//System.out.println("size0 = "+vBk.size());
			vBk = Checker.addNakmkAndSemesToVbk(vBk);
			Collections.sort(vBk);
			
			//System.out.println("size = "+vBk.size());
			//System.out.println(targetInfo);
			StringTokenizer st = new StringTokenizer(targetInfo,"||");
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdjen = st.nextToken();
			String needAprBy = st.nextToken();
			String right = st.nextToken();
			String tknApr = st.nextToken();
			boolean approvee =  Boolean.valueOf(st.nextToken()).booleanValue();
			
			String nakmk1 = null;
			String smsmk1 = null;
			String cmd1 = null;
			String idkur1 = null;
			String idkmk1 = null;
			String thsms1 = null;
			String kdpst1 = null;
			String shift1 = null;
			String noKlsPll1 = null;
			String initNpmInput1 = null;
			String latestNpmUpdate1 = null;
			String latestStatusInfo1 = null;
			String locked1 = null;
			String npmdos1 = null;
			String nodos1 = null;
			String npmasdos1 = null;
			String noasdos1 = null;
			String cancel = null;
			String kodeKelas1 = null;
			String kodeRuang1 = null;
			String kodeGedung1 = null;
			String kodeKampus1 = null;
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
			String konsen1 = null;
			ListIterator liBk = vBk.listIterator();
			
			while(liBk.hasNext()) {
				//boolean match = false;
				String brs = (String)liBk.next();
				//System.out.println(">"+brs);
				StringTokenizer st1 = new StringTokenizer(brs,"||");
				//System.out.println("--st1.tokens="+st1.countTokens());
				/*
				 * updaed hari ini
				 */
				//aprBukaKelas1||35||237||20131||62201||EKSEKUTIF||1||0000712100001||0000712100001||null||false||null||0000000026||null||null||false||null||null||null||null||null||Rae Putri||null||0||0||0||null||2013-12-19 14:16:32.0||null||null||13||false||false
				nakmk1 = st1.nextToken();
				smsmk1 = st1.nextToken();
				cmd1 = st1.nextToken();
				idkur1 = st1.nextToken();
				idkmk1 = st1.nextToken();
				thsms1 = st1.nextToken();
				kdpst1 = st1.nextToken();
				shift1 = st1.nextToken();
				noKlsPll1 = st1.nextToken();
				initNpmInput1 = st1.nextToken();
				latestNpmUpdate1 = st1.nextToken();
				latestStatusInfo1 = st1.nextToken();
				locked1 = st1.nextToken();
				npmdos1 = st1.nextToken();
				nodos1 = st1.nextToken();
				npmasdos1 = st1.nextToken();
				noasdos1 = st1.nextToken();
				cancel = st1.nextToken();
				kodeKelas1 = st1.nextToken();
				kodeRuang1 = st1.nextToken();
				kodeGedung1 = st1.nextToken();
				kodeKampus1 = st1.nextToken();
				tknHrTime1 = st1.nextToken();
				nmmdos1 = st1.nextToken();
				nmmasdos1 = st1.nextToken();
				enrolled1 = st1.nextToken();
				maxEnrol1 = st1.nextToken();
				minEnrol1 = st1.nextToken();
				subKeterMk1 = st1.nextToken();
				initReqTime1 = st1.nextToken();
				tknNpmApproval1 = st1.nextToken();
				tknApprovalTime1 = st1.nextToken();
				targetTotMhs1 = st1.nextToken();
				passed1 = st1.nextToken();
				rejected1 = st1.nextToken();
				konsen1 = st1.nextToken();
				//if(!cmd1.equalsIgnoreCase(right) || !kdpst1.equalsIgnoreCase(kdpst)) {
				if(kdpst1.equalsIgnoreCase(kdpst)) {
				//	liBk.remove();
					if(!cmd1.equalsIgnoreCase(right)) {
						liTmp.add(brs);
						lanjutProcessNoDuplicate = true;
					}
					else {
						liTmp.add(brs);
						request.setAttribute("needAprBy", needAprBy);
					}
				}
				
			}
			
			//removing duplicate bila user punya lebih dari satu right like 'BukaKelas' 
			if(lanjutProcessNoDuplicate) {
				liTmp = vTmp.listIterator();
				while(liTmp.hasNext()) {
				 	String brs = (String)liTmp.next();
					StringTokenizer st1 = new StringTokenizer(brs,"||");
					nakmk1 = st1.nextToken();
					smsmk1 = st1.nextToken();
					cmd1 = st1.nextToken();
					//overight cmd1 = right
					cmd1 = ""+right;
					idkur1 = st1.nextToken();
					idkmk1 = st1.nextToken();
					thsms1 = st1.nextToken();
					kdpst1 = st1.nextToken();
					shift1 = st1.nextToken();
					noKlsPll1 = st1.nextToken();
					initNpmInput1 = st1.nextToken();
					latestNpmUpdate1 = st1.nextToken();
					latestStatusInfo1 = st1.nextToken();
					locked1 = st1.nextToken();
					npmdos1 = st1.nextToken();
					nodos1 = st1.nextToken();
					npmasdos1 = st1.nextToken();
					noasdos1 = st1.nextToken();
					cancel = st1.nextToken();
					kodeKelas1 = st1.nextToken();
					kodeRuang1 = st1.nextToken();
					kodeGedung1 = st1.nextToken();
					kodeKampus1 = st1.nextToken();
					tknHrTime1 = st1.nextToken();
					nmmdos1 = st1.nextToken();
					nmmasdos1 = st1.nextToken();
					enrolled1 = st1.nextToken();
					maxEnrol1 = st1.nextToken();
					minEnrol1 = st1.nextToken();
					subKeterMk1 = st1.nextToken();
					initReqTime1 = st1.nextToken();
					tknNpmApproval1 = st1.nextToken();
					tknApprovalTime1 = st1.nextToken();
					targetTotMhs1 = st1.nextToken();
					passed1 = st1.nextToken();
					rejected1 = st1.nextToken();
					konsen1 = st1.nextToken();
					liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
				}	
			}
			//System.out.println("size1vTmp = "+vTmp.size());
			try {
				vTmp=Tool.removeDuplicateFromVector(vTmp);
			}
			catch(Exception e) {
				System.out.println(e);
			}
			
			
			vTmp=AddHocFunction.addInfoKdkmk(vTmp);
			//System.out.println("size1 = "+vBk.size());
			//System.out.println("size1vTmp = "+vTmp.size());
			//liBk = vBk.listIterator();
			session.setAttribute("vTmp", vTmp);
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/formApprovalBukaKelas.jsp";
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
		doGet(request,response);
	}

}
