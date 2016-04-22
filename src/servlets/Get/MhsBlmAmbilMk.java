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
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;
import beans.dbase.petaMkDanMhs.SearchDb;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Collections;
/**
 * Servlet implementation class MhsBlmAmbilMk
 */
@WebServlet("/MhsBlmAmbilMk")
public class MhsBlmAmbilMk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MhsBlmAmbilMk() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("mhs per mk");
		//String targetMkSmsKe = request.getParameter("mkSmsKe");
		//if(Checker.isStringNullOrEmpty(targetMkSmsKe)) {
		//	targetMkSmsKe = "1";
		//}
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String cmd = ""+request.getParameter("cmd");
		String norutKrklm = ""+request.getParameter("norutKrklm");
		if(Checker.isStringNullOrEmpty(norutKrklm)) {
			norutKrklm = "1";
		}
		int norut = Integer.valueOf(norutKrklm).intValue();
		String atMenu = ""+request.getParameter("atMenu");
		String scope = ""+request.getParameter("scope");
		String kdpst_nmpst = ""+request.getParameter("kdpst_nmpst");
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		String kdpst = st.nextToken();
		String nmpst = st.nextToken();
		String kdjen = Checker.getKdjen(kdpst);
		String thsmsPengajuanBukaKelas = Checker.getThsmsBukaKelas();
		String prevThsms = Tool.returnPrevThsmsGiven(thsmsPengajuanBukaKelas);
		/*
		 * prevThsms bisa turun ke kelas antare 20131A - berarti cek di 20131A & 20131
		 */
		//cek distinct mhs aktif di prev sms antara dan reguler
		SearchDb sdb = new SearchDb(isu.getNpm());
		Vector vListMhs = sdb.getInfoNpmhsMhsAktifPrevSms(kdpst, prevThsms);
		//li.set(npmhs+","+smawl+","+shift+","+krklm);
		
		String listShift = Checker.getListShift(kdjen);
		request.setAttribute("listShift", listShift);
		//uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
		/*
		 * cek ada brp kurikulum
		 */
		Vector vKur = new Vector();
		
		ListIterator li = vListMhs.listIterator();
		while(li.hasNext()) {
			String brs = (String)li.next();
			st = new StringTokenizer(brs,",");
			String npmhs = st.nextToken();
			String smawl = st.nextToken();
			String shift = st.nextToken();
			String idkur = st.nextToken();
			boolean match = false;
			ListIterator liKur = vKur.listIterator();
			while(liKur.hasNext() && !match) {
				String idKur = (String)liKur.next();
				if(idKur.equalsIgnoreCase(idkur)) {
					match = true;
				}
			}
			if(!match) {
				liKur.add(idkur);
			}
			//System.out.println(brs);	
		}
		System.out.println(listShift);
		ListIterator liKur = vKur.listIterator();
		String lisKrklm = "";
		while(liKur.hasNext()) {
			String idKur = (String)liKur.next();
			lisKrklm=lisKrklm+idKur;
			if(liKur.hasNext()) {
				lisKrklm = lisKrklm+",";
			}
			System.out.println("-"+idKur);
		}
		request.setAttribute("lisKrklm", lisKrklm);
		//==================================================
		//kalo mo pake session
		String targetKrklmId = "";
		liKur = vKur.listIterator();
		if(vKur.size()>0) {
			for(int i=0;i<norut;i++) {
				targetKrklmId = (String)liKur.next();
			}
		}
		
		System.out.println("targetKrklmId="+targetKrklmId);
		
		//filter mhs targetkrklm only
		Vector vListMhsForShow = new Vector();
		ListIterator li1 = vListMhsForShow.listIterator();
		li = vListMhs.listIterator();
		while(li.hasNext()) {
			String brs = (String)li.next();
			if(brs.substring(brs.length()-5, brs.length()).contains(targetKrklmId)) {
				li1.add(brs);
			}
		}
		System.out.println("1vListMhs size="+vListMhs.size());
		System.out.println("1vListMhsForShow size="+vListMhsForShow.size());
		sdb = new SearchDb(isu.getNpm());
		//Vector vListMakul = sdb.getListMatakuliahDalamKurikulum(kdpst,targetKrklmId,targetMkSmsKe);
		Vector vListMakul = sdb.getListMatakuliahDalamKurikulum(kdpst,targetKrklmId);
		System.out.println("1vListMakul size="+vListMakul.size());
		
		
		
		Vector v = sdb.getInfoMhsYgBelumAmbilMk(vListMakul,vListMhsForShow,listShift,kdpst);
		session.setAttribute("vInfoMhsYgBelumAmbilMk", v);
		/*
		li = v.listIterator();
		while(li.hasNext()) {
			String infoMk = (String)li.next();
			Vector vNotTaken = (Vector)li.next();//sorted shift
			Vector vNotTakenClone = (Vector)li.next();//sorted smawl
			Vector vNotLulus = (Vector)li.next();//sorted shift
			Vector vNotLulusClone = (Vector)li.next();
			
			System.out.println("info Mk, "+infoMk);
				
			li1 = vNotTakenClone.listIterator();
			while(li1.hasNext()) {
				String brs =(String)li1.next();
				System.out.println("not lulus, "+brs);
			}
		}
		*/
		
		String target = Constants.getRootWeb()+"/InnerFrame/Summary/viewTotMhsBelumLulusMakul.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url_ff+"?kdpst="+kdpst+"&nmpst="+nmpst).forward(request,response);
		
		//System.out.println("vListMakul size="+vListMakul.size());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
