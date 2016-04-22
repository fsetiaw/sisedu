package servlets.Get;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.makul.*;
import beans.dbase.trnlm.SearchDbTrnlm;

import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.*;
/**
 * Servlet implementation class GetListKelasAtClassPool
 */
@WebServlet("/GetListKelasAtClassPool")
public class GetListKelasAtClassPool extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListKelasAtClassPool() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("list CPu");
		String thsms=Checker.getThsmsBukaKelas();
		String cp = request.getParameter("callerPage");
		String cmd = ""+request.getParameter("cmd");
		String atMenu = ""+request.getParameter("atMenu");
		String fwdPage = ""+request.getParameter("fwdPage");
		String nuFwdPage = ""+request.getParameter("nuFwdPage");
		String scope = request.getParameter("scope");
		//System.out.println("scope="+scope);
		String scopeType = ""+request.getParameter("scopeType");
		//System.out.println("scopeType="+scopeType);
		HttpSession session = request.getSession(true);
		Vector vScope = null;
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(scopeType.equalsIgnoreCase("prodyOnly")) {
			vScope = isu.getScopeUpd7des2012ProdiOnly(scope);	
		}
		else {
			vScope = isu.getScopeUpd7des2012(scope);
		}
		
		//ListIterator li5 = vScope.listIterator();
		//while(li5.hasNext()) {
		//	String brs = (String)li5.next();
		//	System.out.println("brs=="+brs);
		//}
		//brs==101 20201 MHS_TEKNIK_ELEKTRO 101 C
		
		SearchDbMk sdb = new SearchDbMk(isu.getNpm());
		Vector v  = sdb.getListKelasAtCp_v1(thsms,vScope);
		
		
		//System.out.println(v.size());
		
		if(cmd.equalsIgnoreCase("gabungKelasFak")||cmd.equalsIgnoreCase("batalKelasFak")) {
			ListIterator li = v.listIterator();
			Vector vNon = (Vector)li.next();
			
			//ListIterator li5 = vNon.listIterator();
			//while(li5.hasNext()) {
			//	String brs = (String)li5.next();
				//system.out.println("vNon=="+brs);
			//}
			
			Vector vGroup = (Vector)li.next();
			/*
			li5 = vGroup.listIterator();
			while(li5.hasNext()) {
				String brs = (String)li5.next();
				System.out.println("vGroup=="+brs);
			}
			*/
			SearchDbTrnlm sdt = new SearchDbTrnlm(isu.getNpm());
			//system.out.println("vNon before="+vNon.size());
			if(vNon!=null && vNon.size()>0) {
				vNon = sdt.addJumMhs_ver1(vNon);
				//vNon = sdt.addJumMhsYgSudahHeregistrasi_v1(vNon);
			}
			//system.out.println("vNon after="+vNon.size());
			//system.out.println("vGroup befor="+vGroup.size());
			if(vGroup!=null && vGroup.size()>0) {
				vGroup = sdt.addJumMhs_ver1(vGroup);
				//system.out.println("vNon after 1="+vNon.size());
				//vGroup = sdt.addJumMhsYgSudahHeregistrasi_v1(vGroup);
				//system.out.println("vNon after 2="+vNon.size());
			}
			//system.out.println("vGroup after="+vGroup.size());
			
			//process kode grouping, di kode ulang bila lebih dr 1npm yg memberikan kode
			//vGroup = sdb.cekAndRenameGroupKode(vGroup);
			//ListIterator lii = vGroup.listIterator();
			//while(lii.hasNext()) {
			//	String brsi =(String)lii.next();
			//	System.out.println(brsi);
			//}
			/*
			 */
			String listMkNonKelompok = "";
			String listMkKelompok = "";
			li = vNon.listIterator();
			boolean first = true;
			while(li.hasNext()) {
				String brs = (String)li.next();
				//system.out.println("aneh="+brs);
				
				if(first) {
					first = false;
					listMkNonKelompok = brs;
					
				}
				else {
					listMkNonKelompok = listMkNonKelompok + brs;			
				}
				if(li.hasNext()) {
					listMkNonKelompok = listMkNonKelompok + "$";
				}
			}
			
			li = vGroup.listIterator();
			first = true;
			while(li.hasNext()) {
				String brs = (String)li.next();
				if(first) {
					first = false;
					listMkKelompok = brs;
					
				}
				else {
					listMkKelompok = listMkKelompok + brs;			
				}
				if(li.hasNext()) {
					listMkKelompok = listMkKelompok + "$";
				}
			}
			//System.out.println("listMkNonKelompok="+listMkNonKelompok);
			listMkNonKelompok=listMkNonKelompok.replaceAll("&", "tandaDan");
			listMkKelompok=listMkKelompok.replaceAll("&", "tandaDan");
			session.setAttribute("listMkNonKelompok", listMkNonKelompok);
			session.setAttribute("listMkKelompok", listMkKelompok);
			
		//	listMkNonKelompok=listMkNonKelompok.replaceAll("", "tandaDan");
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/formPenggabunganKelas.jsp";
			if(cmd.equalsIgnoreCase("batalKelasFak")) {
				target = Constants.getRootWeb()+"/InnerFrame/Akademik/formPembatalanKelas.jsp";
			}
			
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url+"?thsmsTarget="+thsms+"&listMkNonKelompok="+listMkNonKelompok+"&listMkKelompok="+listMkKelompok).forward(request,response);
			request.getRequestDispatcher(url+"?thsmsTarget="+thsms).forward(request,response);
		}
		else {
			if(cmd.equalsIgnoreCase("ubahDosenAjar")) {
				//v
				//lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1);
				Vector vGab = new Vector();
				ListIterator lig = vGab.listIterator();
				ListIterator li = v.listIterator();
				Vector vNon = (Vector)li.next();
				Vector vGroup = (Vector)li.next();
				vGab = prepVectorPengajuanGuru(vNon,vGroup);
				//add info is kelas empty
				AddHocFunction adf = new AddHocFunction();
				vGab = adf.addInfoKelasEmpty(vGab);
				if(vGab!=null && vGab.size()>0) {
					String listScopeKdpstAjar ="";
					SearchDbDsn sdbd = new SearchDbDsn(isu.getNpm());
					if(vScope!=null && vScope.size()>0) {
						ListIterator lisc = vScope.listIterator();
						while(lisc.hasNext()) {
							String brs = (String)lisc.next();
							StringTokenizer st = new StringTokenizer(brs);
							st.nextToken();
							String kdpst = st.nextToken();
							listScopeKdpstAjar =listScopeKdpstAjar +kdpst;
							if(lisc.hasNext()) {
								listScopeKdpstAjar=listScopeKdpstAjar+"$";
							}
						}
						//Vector vListDsn = sdbd.getListInfoDosen_v1(listScopeKdpstAjar);
						Vector vListDsn = sdbd.getListDosenAktif();
						//li.add(id_obj+"||"+npm+"||"+nmm);
						if(vListDsn==null) {
							vListDsn = new Vector();
						}
						session.setAttribute("vListDsn", vListDsn);
					}
					//list
				}
				else {
					vGab = new Vector();
				}
				session.setAttribute("vGab", vGab);
				
				String target = Constants.getRootWeb()+"/InnerFrame/Akademik/formPerubahanDosenAjar.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url).forward(request,response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

	protected Vector prepVectorPengajuanGuru(Vector vNon,Vector vGroup) {
		/*
		 * updated add cuid 
		 */
		
		Vector vf = new Vector();
		ListIterator lif = vf.listIterator();
		if(vNon!=null && vNon.size()>0) {
			ListIterator li = vNon.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"$");
				String kodeGabungan=st.nextToken();
				String idkmk1=st.nextToken();
				String idkur1=st.nextToken();
				String kdkmk1=st.nextToken();
				String nakmk1=st.nextToken();
				String thsms1=st.nextToken();
				String kdpst1=st.nextToken();
				String shift1=st.nextToken();
				String norutKlsPll1=st.nextToken();
				String initNpmInput1=st.nextToken();
				String latestNpmUpdate1=st.nextToken();
				String latesStatusInfo1=st.nextToken();
				String currAvailStatus1=st.nextToken();
				String locked1=st.nextToken();
				String npmdos1=st.nextToken();
				String nodos1=st.nextToken();
				String npmasdos1=st.nextToken();
				String noasdos1=st.nextToken();
				String canceled1=st.nextToken();
				String kodeKelas1=st.nextToken();
				String kodeRuang1=st.nextToken();
				String kodeGedung1=st.nextToken();
				String kodeKampus1=st.nextToken();
				String tknHrTime1=st.nextToken();
				String nmdos1=st.nextToken();
				String nmasdos1=st.nextToken();
				String enrolled1=st.nextToken();
				String maxEnrolled1=st.nextToken();
				String minEnrolled1=st.nextToken();
				String subKeterKdkmk1=st.nextToken();
				String initReqTime1=st.nextToken();
				String tknNpmApr1=st.nextToken();
				String tknAprTime1=st.nextToken();
				String targetTtmhs1=st.nextToken();
				String passed1=st.nextToken();
				String rejected1=st.nextToken();
				String konsen1=st.nextToken();
				String nmpst1=st.nextToken();
				String cuid1=st.nextToken();
				if(canceled1.equalsIgnoreCase("false")) {
					lif.add(nakmk1+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1+"$"+kodeGabungan+"$"+cuid1);
					//lif.add(brs);
				}
			}
		}	
		if(vGroup!=null && vGroup.size()>0) {
			ListIterator li = vGroup.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"$");
				String kodeGabungan=st.nextToken();
				String idkmk1=st.nextToken();
				String idkur1=st.nextToken();
				String kdkmk1=st.nextToken();
				String nakmk1=st.nextToken();
				String thsms1=st.nextToken();
				String kdpst1=st.nextToken();
				String shift1=st.nextToken();
				String norutKlsPll1=st.nextToken();
				String initNpmInput1=st.nextToken();
				String latestNpmUpdate1=st.nextToken();
				String latesStatusInfo1=st.nextToken();
				String currAvailStatus1=st.nextToken();
				String locked1=st.nextToken();
				String npmdos1=st.nextToken();
				String nodos1=st.nextToken();
				String npmasdos1=st.nextToken();
				String noasdos1=st.nextToken();
				String canceled1=st.nextToken();
				String kodeKelas1=st.nextToken();
				String kodeRuang1=st.nextToken();
				String kodeGedung1=st.nextToken();
				String kodeKampus1=st.nextToken();
				String tknHrTime1=st.nextToken();
				String nmdos1=st.nextToken();
				String nmasdos1=st.nextToken();
				String enrolled1=st.nextToken();
				String maxEnrolled1=st.nextToken();
				String minEnrolled1=st.nextToken();
				String subKeterKdkmk1=st.nextToken();
				String initReqTime1=st.nextToken();
				String tknNpmApr1=st.nextToken();
				String tknAprTime1=st.nextToken();
				String targetTtmhs1=st.nextToken();
				String passed1=st.nextToken();
				String rejected1=st.nextToken();
				String konsen1=st.nextToken();
				String nmpst1=st.nextToken();
				String cuid1=st.nextToken();
				if(canceled1.equalsIgnoreCase("false")) {
					lif.add(nakmk1+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1+"$"+kodeGabungan+"$"+cuid1);
				}
			}
		}
		Collections.sort(vf);
		return vf;
	}
}
