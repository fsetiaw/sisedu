package servlets.process;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.*;

import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Servlet implementation class StatusReqBukaKelas
 */
@WebServlet("/StatusReqBukaKelas")
public class StatusReqBukaKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusReqBukaKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("req buka kelas");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String listKdpstBk = request.getParameter("listKdpstBk");
		String infoKdpstNoPengajuan = request.getParameter("infoKdpstNoPengajuan");
		Vector vBk = (Vector) session.getAttribute("vBukaKelas");
		//System.out.println("1-vBk="+vBk.size());
		Vector vSum = new Vector();
		ListIterator liSum = vSum.listIterator();
		Vector vKampus = new Vector();
		ListIterator liKam = vKampus.listIterator();
		//process masing2 status / kdpst
		//aprBukaKelas5||35||255||20131||62201||REGULER MALAM||1||0000712100001||0000712100001||null||false||null||0000000005||null||null||false||null||null||null||null||null||Andry Sutaryo||null||0||0||0||null||2013-12-19 14:16:32.0||null||null||30||false||false
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
		/*
		 * get kampus yg ada di class pool
		 */
		StringTokenizer st = new StringTokenizer(listKdpstBk,",");
		/*
		 * listKdpstBk = list distinct kdpst yang ada di CLASS_POOL TAble
		 */
		while(st.hasMoreTokens()) {
			String kdpst = st.nextToken();
			ListIterator liBk = vBk.listIterator();
			boolean match = false;
			StringTokenizer st1=null;
			
			while(liBk.hasNext() && !match) {
				String brs = (String)liBk.next();
				//>reqBukaKelas||52||503||20141||26201||REGULER MALAM||1||0000713100002||0000713100002||null||false||0001114100034||null||null||null||false||null||null||null||PST||null||DICK ACHMADI||null||0||0||0||null||2014-08-12 17:03:24.0||null||null||11||false||false
				
				st1 = new StringTokenizer(brs,"||");
				
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
				liKam.add(kodeKampus1);
				boolean flag = false;
				if(kdpst1.equalsIgnoreCase("88888")&&!flag) {
					//System.out.println(">"+brs);
					flag=true;
				}
				
			}
		}
		
		st = new StringTokenizer(listKdpstBk,",");
		/*
		 * listKdpstBk = list distinct kdpst yang ada di CLASS_POOL TAble
		 */
		while(st.hasMoreTokens()) {
			String kdpst = st.nextToken();
			//System.out.println("kdpst top="+kdpst);
			ListIterator liBk = vBk.listIterator();
			boolean match = false;
			StringTokenizer st1=null;
			
			while(liBk.hasNext() && !match) {
				String brs = (String)liBk.next();
				//>reqBukaKelas||52||503||20141||26201||REGULER MALAM||1||0000713100002||0000713100002||null||false||0001114100034||null||null||null||false||null||null||null||PST||null||DICK ACHMADI||null||0||0||0||null||2014-08-12 17:03:24.0||null||null||11||false||false
				//System.out.println(">"+brs);
				st1 = new StringTokenizer(brs,"||");
				
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
				if(kdpst.equalsIgnoreCase(kdpst1)) {
					match = true;
				}
			}
			
			//System.out.println("kdpst="+kdpst);
			String rules = Checker.getRuleBukaKelas(kdpst);
			//System.out.println("rls="+rules);
			st1 = new StringTokenizer(rules,"||");
			String thsmsPmb=st1.nextToken();//thsms buka kls
			st1.nextToken();//kdpst
			String tknApprover=st1.nextToken();
			StringTokenizer st2 = new StringTokenizer(tknApprover,",");
			//System.out.println("tknApprover="+tknApprover);
			String totApprover=st1.nextToken();
			//System.out.println("totApprover="+totApprover);
			String needApproval = null; 
			String rightCmd = "";
			if(locked1.equalsIgnoreCase("true")) {
				//butuh paling akhir biar bisa unlock juga
				//System.out.println("msk1");
				rightCmd = "aprBukaKelas"+totApprover;
				needApproval = st2.nextToken()+"||"+rightCmd;
			}
			else {
				if(tknNpmApproval1==null || Checker.isStringNullOrEmpty(tknNpmApproval1) || tknNpmApproval1.contains("null")) {
				//1st approval
					//System.out.println("msk2");
					rightCmd = "aprBukaKelas1";
					needApproval = st2.nextToken()+"||"+rightCmd;
				}
				else {
					//System.out.println("msk3");
					//System.out.println("tknNpmApproval1="+tknNpmApproval1);
					st1 = new StringTokenizer(tknNpmApproval1,",");
					//System.out.println("tknNpmApproval1="+tknNpmApproval1);
					int needAprNo = st1.countTokens()+1; //
					//System.out.println("needAprNo="+needAprNo);
					for(int i=0;i<needAprNo;i++) {
						rightCmd = "aprBukaKelas"+needAprNo;
						needApproval = st2.nextToken()+"||"+rightCmd;
					}
				}	
			}
			String infoKdpst = Converter.getDetailKdpst(kdpst);
			infoKdpst = infoKdpst.replace("#&", "||");
			//System.out.println("needApproval="+needApproval);
			boolean boleh = isu.isUsrAllowTo(rightCmd,kdpst);
			
			needApproval = kdpst+"||"+infoKdpst+"||"+needApproval+"||"+tknApprover+"||"+boleh;
			liSum.add(needApproval);
			
			//System.out.println("rules="+rules);
			//System.out.println("needApproval="+needApproval);
		}
		try {
			vKampus = Tool.removeDuplicateFromVector(vKampus);
			Collections.sort(vKampus);
			vKampus = Checker.getNickNameKampus(vKampus);
		}
		catch(Exception e) {}
		request.setAttribute("vSum", vSum);
		request.setAttribute("vKampus", vKampus);
		request.setAttribute("listKdpstBk", listKdpstBk);
		String target = Constants.getRootWeb()+"/InnerFrame/Notifications/bukaKelasNotification.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url+"?thsmsBukaKelas="+Checker.getThsmsBukaKelas()+"&infoKdpstNoPengajuan="+infoKdpstNoPengajuan).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
