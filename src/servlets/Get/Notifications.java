package servlets.Get;

import java.io.IOException;
import beans.dbase.SearchDb;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.keu.*;
import beans.dbase.notification.SearchDbMainNotification;
import beans.dbase.status_kehadiran_dosen.SearchDbkehadiranDosen;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

/**
 * Servlet implementation class Notifications
 */
@WebServlet("/Notifications")
public class Notifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notifications() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("notifications");
		//System.out.println("update CP");
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		SearchDb sdb = new SearchDb(isu.getNpm());
		SearchDbMainNotification sdm = new SearchDbMainNotification(isu.getNpm());
		
		
		
		//=====krs notification=========================
		/*
		 *  MOOOOVVEDDDD , SUDAH DIPINDAH
		 
		//updated untuk krs yg diwakilkan
		//  CEK SCOPE : krsPaApproval
		String scope_kampus = isu.getScopeKampus("krsPaApproval");
		Vector vScopeProdi = isu.getScopeUpd7des2012ReturnDistinctKdpst("krsPaApproval");
		String tkn_npm_pa = "";
		String tkn_kdpst = "";
		if(vScopeProdi!=null && vScopeProdi.size()>0) {
			ListIterator lis = vScopeProdi.listIterator();
			while(lis.hasNext()) {
				tkn_kdpst = tkn_kdpst+(String)lis.next()+"`";
			}
			/*
			 *  !!!!!!updated krsPaApproval hanya untuk system perwalian!!!!!
			 *  KASUS SEPERTI PUTRI MEWAKILKAN PA LAIN DALAN SCOPE FAKULTAS MAKA krsPaApproval diisi
			 *  
			 *  TAPI KALO kasiat yg hanya approve anak yang dibimbing only tidak akan masuk keksini tkn_npm_pa = own 
			 
			tkn_npm_pa = Getter.getListPembimbingAkademik(tkn_kdpst);	
			
		}
		/*
		 * jika tkn_npm_pa = maka tkn_npm_pa adalah npm user sendiri
		 
		if(tkn_npm_pa==null || Checker.isStringNullOrEmpty(tkn_npm_pa)) {
			tkn_npm_pa = new String(isu.getNpm());
		}
		//System.out.println("tkn_npm_pa="+tkn_npm_pa);
		//String tknKrsNotifications = sdb.getReqKrsApprovalNotification();
		
		
		/*
		String tknKrsNotifications = sdb.getReqKrsApprovalNotification(tkn_npm_pa,tkn_kdpst,scope_kampus);
		String tknKrsNotificationsForSender = sdb.getNonHiddenReqKrsApprovalNotificationForSender();
		session.setAttribute("tknKrsNotifications", tknKrsNotifications);
		session.setAttribute("tknKrsNotificationsForSender", tknKrsNotificationsForSender);
		*/
		
		//======================end krs notification==-----------------------------
		
		/*
		 * ========================BukaKelas=============================
		 DIPINDAHKAN KE SERVLET SENDIRI
		
		//=====get kdpst yg belum mengajukan bukakelas=====
		String kdjenKdpstNmpstNoPengajuan = sdb.getProdiYgBlmMengajukanBukaKelas();
		//=====end get kdpst yg belum mengajukan bukakelas=====
		
		Vector vBukaKelas = sdb.getRelatedInfoRequestBukaKelas(isu);
		
		if(vBukaKelas!=null) {
			//get list kdpst
			String listKdpst = "";
			ListIterator li = vBukaKelas.listIterator();
			Vector vTmp = new Vector();
			//System.out.println("0.vBukaKelas ="+ vBukaKelas.size());
			ListIterator liTmp = vTmp.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				//System.out.println("bars@notification="+brs);
				StringTokenizer st = new StringTokenizer(brs,"||");
				st.nextToken(); //cmd
				st.nextToken(); //idkur
				st.nextToken(); //idkmk
				st.nextToken(); //thsmsPmb
				String kdpst = st.nextToken();
				liTmp.add(kdpst);
				//System.out.println("* "+brs);
			}
			try {
				vTmp=Tool.removeDuplicateFromVector(vTmp);
				liTmp = vTmp.listIterator();
				boolean first = true;
				while(liTmp.hasNext()) {
					if(first) {
						first = false;
						listKdpst = ""+(String)liTmp.next();
					}
					else {
						listKdpst=listKdpst+(String)liTmp.next();
					}
					if(liTmp.hasNext()) {
						listKdpst=listKdpst+",";
					}
				}
				//System.out.println("listKdpst@notification="+listKdpst);
			}
			catch(Exception e) {
				System.out.println(e);
			}
			//SearchDb sdb = new SearchDb(isu.getNpm());
			String[]aksesInfo = sdb.getObjectAccessLevel();
	    	if(aksesInfo[1].contains("BukaKelas")) {
	    		request.setAttribute("hasBukaKelasCmd", "yes");
	    	}
			session.setAttribute("vBukaKelas", vBukaKelas);
			request.setAttribute("listKdpstBukaKelas", listKdpst);
			request.setAttribute("kdjenKdpstNmpstNoPengajuan", kdjenKdpstNmpstNoPengajuan);
		}
		
		 * ========================END BukaKelas=============================
		 */
		
		/*
		 * ======================cek apa ada pesan baru ======================
		 * saat ini : untuk creator cek via npm (objeckNickName, dll diignore)
		 */
		String usrObjNickname = isu.getObjNickNameGivenObjId();
		//System.out.println("objNickNameUsr-1="+usrObjNickname);
		
		
		Vector vScopeMonitoredNickname = isu.getScopeObjNickname("scopeMonitorInbox");
		String newMsgOnMonitoredInbox = ""+sdb.gotMonitoredNewMsg(usrObjNickname,vScopeMonitoredNickname);
		
		//System.out.println("vScopeMonitoredNickname=="+vScopeMonitoredNickname);
		//System.out.println("vScopeMonitoredNickname.size=="+vScopeMonitoredNickname.size());
		Vector vScopeOwnInbox = isu.getScopeObjNickname("scopeOwnInbox");
		//ListIterator li1 = vScopeOwnInbox.listIterator();
		//while(li1.hasNext()) { //isi tmp1a
		//	String nicname = (String)li1.next();
			//System.out.println("nicname="+nicname);
		//}	
		//System.out.println("vScopeNickname="+vScopeNickname.size());
		
		
		
		
		
		//String newMsgOnOwnInbox = ""+sdb.gotNewMsg(vScopeOwnInbox);
		String newMsgOnOwnInbox = "";
		/*
		 * harus dipikirkan bila vScopeOwnInbox = own , return NPM only
		 */
		if(vScopeOwnInbox!=null && vScopeOwnInbox.size()>0) {
			newMsgOnOwnInbox = ""+sdb.gotNewMsg_v3(usrObjNickname,vScopeOwnInbox);
		}
		
		/*
		 * ======================end ada pesan baru ======================
		 */
		
		
		/*====================get payment approval========================
		//isu.getScopeUpd7des2012ReturnDistinctKdpst(command_code)
		Vector vKeu = isu.getScopeUpd7des2012ReturnDistinctKdpst("pymntApprovee");
		if(vKeu!=null && vKeu.size()>0) {
		//if(isu.isAllowTo("pymntApprovee")>0) {
			SearchDbKeu sdbk = new SearchDbKeu(isu.getNpm());
			//vKeu = sdbk.getPymntApprovalRequest(vKeu);
			JSONArray jsoa = sdbk.getPymntApprovalRequestJsonStyle(vKeu);
			//System.out.println("-----------------");
			//System.out.println(jsoa.toString());
			//System.out.println("-----------------");
			if(jsoa!=null && jsoa.length()>0) {
				session.setAttribute("jsoaPymntReq", jsoa);
			}
			//if(vKeu!=null && vKeu.size()>0) {
			//	session.setAttribute("vReqAprKeu", vKeu);
			//}	
			//System.out.println("vKeu = "+vKeu.size());
		}
		
		
		//====================end payment approval========================*/
		
		//=======================kehadiran dosen=========================
		if(isu.getObjNickNameGivenObjId().contains("MHS")) {
			SearchDbkehadiranDosen sdd = new SearchDbkehadiranDosen(isu.getNpm());
			String info_kehadiran_dosen = "";
			String sistem_perkuliahan = ""+Checker.getSistemPerkuliahan_v1(""+isu.getIdObj()); 
			if(sistem_perkuliahan.equalsIgnoreCase("CONTINUE")) {
				info_kehadiran_dosen = sdd.getNotificationStatusKehadiranDosenMhsBanBerjalan();
			}
			else {
				info_kehadiran_dosen = sdd.getNotificationStatusKehadiranDosen();
			}
			//System.out.println("info_kehadiran="+info_kehadiran_dosen);
			 
			if(info_kehadiran_dosen!=null && !Checker.isStringNullOrEmpty(info_kehadiran_dosen)) {
				request.setAttribute("info_kehadiran_dosen", info_kehadiran_dosen);	
			}
				
		}
		
		//=======================kehadiran dosen=========================
		
		//======================UJIAN AKHIR===============================
		Vector vUa = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn("ua");
		//ListIterator lua = vUa.listIterator();
		//while(lua.hasNext()) {
		//	System.out.println("lua = "+(String)lua.next());
		//}
		Vector vUaa = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn("uaa");
		
		//System.out.println("point 1");
		if((vUa!=null && vUa.size()>0)||(vUaa!=null && vUaa.size()>0)) {
			//System.out.println("point 2");
			//sdm = new SearchDbMainNotification(isu.getNpm());
			String ada_pengajuan = ""+sdm.getPengajuanUa(vUa, vUaa, isu.getIdObj());
			//System.out.println("ada_pengajuan="+ada_pengajuan);
			session.setAttribute("ada_pengajuan", ada_pengajuan);
		}
		//
		//=======================pengajuan daftar ulang=======================
		Vector vScope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj("hasHeregitrasiMenu");
		boolean ada_daftar_ulang = sdm.adaRequestDaftarUlang(vScope_id);
		//System.out.println("ada_daftar_ulang="+ada_daftar_ulang);
		//=====================end pengajuan daftar ulang=======================
		
		//======================END UJIAN AKHIR===============================
		
		
		String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		/*
		 * ganti dengan menggunakan session krn parameter ada limit lengtnya
		 */
		//request.getRequestDispatcher(url+"?tknKrsNotifications="+tknKrsNotifications+"&tknKrsNotificationsForSender="+tknKrsNotificationsForSender+"&newMsgOnMonitoredInbox="+newMsgOnMonitoredInbox+"&newMsgOnOwnInbox="+newMsgOnOwnInbox).forward(request,response);
		
		
		request.getRequestDispatcher(url+"?registrasiReq="+ada_daftar_ulang+"&newMsgOnMonitoredInbox="+newMsgOnMonitoredInbox+"&newMsgOnOwnInbox="+newMsgOnOwnInbox).forward(request,response);
	
		}//end else isu!null
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
