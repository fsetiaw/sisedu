package servlets.Cetak;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ListIterator;
import java.util.Vector;
import java.util.StringTokenizer;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.*;
import beans.folder.FolderManagement;
/**
 * Servlet implementation class EditKelulusan
 */
@WebServlet("/EditKelulusan")
public class PrintIjazah extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintIjazah() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("iam in");
		//get info ijzah 
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String cmd =  request.getParameter("cmd");

		//System.out.println("masuk vop");
		//update ada di 2 tmp = ProfileCivitas.java && HistoryBakCivitas.java
		int norut = isu.isAllowTo("vop");
		Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"vop",kdpst);
		ListIterator li = v.listIterator();
		String v_obj_lvl=(String)li.next();
		request.setAttribute("v_obj_lvl", v_obj_lvl);
		String v_id_kotaku=(String)li.next();
		request.setAttribute("v_id_kotaku", v_id_kotaku);
		String v_id_obj=(String)li.next();
		request.setAttribute("v_id_obj", v_id_obj);
		String v_kdpti=(String)li.next();
		request.setAttribute("v_kdpti", v_kdpti);
		String v_kdjen=(String)li.next();
		request.setAttribute("v_kdjen", v_kdjen);
		String v_kdpst=(String)li.next();
		request.setAttribute("v_kdpst", v_kdpst);
		String v_npmhs=(String)li.next();
		request.setAttribute("v_npmhs", v_npmhs);
		String v_nimhs=(String)li.next();
		request.setAttribute("v_nimhs", v_nimhs);
		String v_nmmhs=(String)li.next();
		request.setAttribute("v_nmmhs", v_nmmhs);
		String v_shift=(String)li.next();
		request.setAttribute("v_shift", v_shift);
		String v_tplhr=(String)li.next();
		request.setAttribute("v_tplhr", v_tplhr);
		String v_tglhr=(String)li.next();
		request.setAttribute("v_tglhr", v_tglhr);
		String v_kdjek=(String)li.next();
		request.setAttribute("v_kdjek", v_kdjek);
		String v_tahun=(String)li.next();
		request.setAttribute("v_tahun", v_tahun);
		String v_smawl=(String)li.next();
		request.setAttribute("v_smawl", v_smawl);
		String v_btstu=(String)li.next();
		request.setAttribute("v_btstu", v_btstu);
		String v_assma=(String)li.next();
		request.setAttribute("v_assma", v_assma);
		String v_tgmsk=(String)li.next();
		request.setAttribute("v_tgmsk", v_tgmsk);
		String v_tglls=(String)li.next();
		request.setAttribute("v_tglls", v_tglls);
		String v_stmhs=(String)li.next();
		request.setAttribute("v_stmhs", v_stmhs);
		String v_stpid=(String)li.next();
		request.setAttribute("v_stpid", v_stpid);
		String v_sksdi=(String)li.next();
		request.setAttribute("v_sksdi", v_sksdi);
		String v_asnim=(String)li.next();
		request.setAttribute("v_asnim", v_asnim);
		String v_aspti=(String)li.next();
		request.setAttribute("v_aspti", v_aspti);
		String v_asjen=(String)li.next();
		request.setAttribute("v_asjen", v_asjen);
		String v_aspst=(String)li.next();
		request.setAttribute("v_aspst", v_aspst);
		String v_bistu=(String)li.next();
		request.setAttribute("v_bistu", v_bistu);
		String v_peksb=(String)li.next();
		request.setAttribute("v_peksb", v_peksb);
		String v_nmpek=(String)li.next();
		request.setAttribute("v_nmpek", v_nmpek);
		String v_ptpek=(String)li.next();
		request.setAttribute("v_ptpek", v_ptpek);
		String v_pspek=(String)li.next();
		request.setAttribute("v_pspek", v_pspek);
		String v_noprm=(String)li.next();
		request.setAttribute("v_noprm", v_noprm);
		String v_nokp1=(String)li.next();
		request.setAttribute("v_nokp1", v_nokp1);
		String v_nokp2=(String)li.next();
		request.setAttribute("v_nokp2", v_nokp2);
		String v_nokp3=(String)li.next();
		request.setAttribute("v_nokp3", v_nokp3);
		String v_nokp4=(String)li.next();
		request.setAttribute("v_nokp4", v_nokp4);
		String v_sttus=(String)li.next();
		request.setAttribute("v_sttus", v_sttus);
		String v_email=(String)li.next();
		request.setAttribute("v_email", v_email);
		String v_nohpe=(String)li.next();
		request.setAttribute("v_nohpe", v_nohpe);
   		String v_almrm=(String)li.next();
		request.setAttribute("v_almrm", v_almrm);
		String v_kotrm=(String)li.next();
		request.setAttribute("v_kotrm", v_kotrm);
   		String v_posrm=(String)li.next();
		request.setAttribute("v_posrm", v_posrm);
   		String v_telrm=(String)li.next();
		request.setAttribute("v_telrm", v_telrm);
   		String v_almkt=(String)li.next();
		request.setAttribute("v_almkt", v_almkt);
		String v_kotkt=(String)li.next();
		request.setAttribute("v_kotkt", v_kotkt);
   		String v_poskt=(String)li.next();
		request.setAttribute("v_poskt", v_poskt);
   		String v_telkt=(String)li.next();
		request.setAttribute("v_telkt", v_telkt);
   		String v_jbtkt=(String)li.next();
		request.setAttribute("v_jbtkt", v_jbtkt);
   		String v_bidkt=(String)li.next();
		request.setAttribute("v_bidkt", v_bidkt);
   		String v_jenkt=(String)li.next();
		request.setAttribute("v_jenkt", v_jenkt);
   		String v_nmmsp=(String)li.next();
		request.setAttribute("v_nmmsp", v_nmmsp);
   		String v_almsp=(String)li.next();
		request.setAttribute("v_almsp", v_almsp);
   		String v_possp=(String)li.next();
		request.setAttribute("v_possp", v_possp);
		String v_kotsp=(String)li.next();
		request.setAttribute("v_kotsp", v_kotsp);
		String v_negsp=(String)li.next();
		request.setAttribute("v_negsp", v_negsp);
   		String v_telsp=(String)li.next();
		request.setAttribute("v_telsp", v_telsp);
		String v_neglh=(String)li.next();
		request.setAttribute("v_neglh", v_neglh);
		String v_agama=(String)li.next();
		request.setAttribute("v_agama", v_agama);
				
		request.setAttribute("v_profile", v);
		request.setAttribute("atr_name", "atr_val");
		String target="";

		SearchDb sdb = new SearchDb();
		Vector vInfoIja = sdb.getInfoIjazah(kdpst, npm);
		
		
		String id = "";
 		String nonirl="";
		String noija ="";
		String noskr ="";
 		String tglre ="";
 		String nmmija="";
 		String nimija="";
 		String tptglhr="";
 		String tgctk ="";
 		String tgctkstr="";
 		String status="";
 		String note ="";
 		String pemeriksa="";
 		String pencetak="";
 		String diserahkan="";
 		String penerima="";
 		String tgterima="";
 		String editable_ = "";
 		String cetakable_ = "";
		String tplhr = "";
		String tglhr ="";
		boolean editable = false;
	 	boolean cetakable =  false;
	 	//boolean allowEditIja = false;
		//if sudah ada data ijazah
 		if(vInfoIja!=null && vInfoIja.size()>0) {
 			li = vInfoIja.listIterator();
 			id = (String)li.next();
 	 		nonirl= (String)li.next();
 			noija = (String)li.next();
 	 		noskr = (String)li.next();
 	 		tglre = (String)li.next();
 	 		nmmija= (String)li.next();
 	 		nimija= (String)li.next();
 	 		tptglhr= (String)li.next();
 	 		StringTokenizer st = new StringTokenizer(tptglhr,",");
 	 		if(st.countTokens()>1) {
 	 			tplhr = st.nextToken();
 	 			tglhr = st.nextToken();
 	 		}
 	 		tgctk = (String)li.next();
 	 		tgctkstr= (String)li.next();
 	 		status= (String)li.next();
 	 		note = (String)li.next();
 	 		pemeriksa= (String)li.next();
 	 		pencetak= (String)li.next();
 	 		diserahkan= (String)li.next();
 	 		penerima= (String)li.next();
 	 		tgterima= (String)li.next();
 	 		editable_ = (String)li.next();
 	 		cetakable_ = (String)li.next();
 	 		
 	 		editable = Boolean.valueOf(editable_).booleanValue();
 	 		cetakable =  Boolean.valueOf(cetakable_).booleanValue();

 		}
 		else {
 			editable = true;
 			cetakable = true;
 		}
 		String availNoIja = null;
 		String availNoNirl = null;
 		if(cetakable && Checker.isStringNullOrEmpty(noija)) {
 			//siapkan noija
 			//berhubung saat migrasi default cetakable maka, noija disiapkan bila var noija = null
 			
 			availNoIja = sdb.prepNoIja(v_kdpst);
 			//norut_ijazah = Integer.valueOf(norut_ijazah_terakhir).intValue()+1;
 			
 			availNoNirl = sdb.prepNoNirl(v_kdpst);
 			//norut_nirl = Integer.valueOf(norut_nirl_terakhir).intValue()+1;
 		}
 		String nmjen = sdb.getNamaJenjang(v_kdpst);
 		String nmpst = sdb.getNmpst(v_kdpst);
 		request.setAttribute("nmjen",""+nmjen);
 		request.setAttribute("nmpst",""+nmpst);
 		request.setAttribute("availNoIja", ""+availNoIja);
 		request.setAttribute("availNoNirl", ""+availNoNirl);
 		request.setAttribute("vInfoIja", vInfoIja);
 		request.setAttribute("editable",""+editable);
 		request.setAttribute("cetakable",""+cetakable);
		target = Constants.getRootWeb()+"/InnerFrame/indexCetakIjazah.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url_ff+"?cmd=percetakan").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

}
