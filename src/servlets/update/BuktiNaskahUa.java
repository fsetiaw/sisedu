package servlets.update;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.keu.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * Servlet implementation class BuktiNaskahUa
 */
@WebServlet("/BuktiNaskahUa")
public class BuktiNaskahUa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuktiNaskahUa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("bukti naskah UA");
		//String status_update = "";
		HttpSession session = request.getSession(true);
		String status_update = (String)session.getAttribute("status_update");
		session.removeAttribute("status_update");
		Vector vPengajuanLangsungApproval = null;
		vPengajuanLangsungApproval = (Vector)session.getAttribute("goto_approval");
 		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
 		session.removeAttribute("goto_approval");
 		if(vPengajuanLangsungApproval==null) {
 			//tidak ada proses approval, hanya pengajuan
 			String fieldAndValue = (String)session.getAttribute("fieldAndValue");
 	 		String nmmhs = ""; 
 	 		String objId = ""; 
 	 		String obj_lvl = ""; 
 	 		String cmd = ""; 
 	 		String npmhs = ""; 
 	 		String kdpst = ""; 
 	 		    
 	 			
 	 		
 	 		    
 	 		StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
 	 		while(st.hasMoreTokens()) {
 	 			String fieldNmm = st.nextToken();
 	 			//System.out.println("fieldNmm=="+fieldNmm);
 	 		    String fieldval = st.nextToken();
 	 		   
 	 		    if(fieldNmm.contains("nmmhs")) {
 	 		    	nmmhs = new String(fieldval); 
 	 		    }
 	 		    else if(fieldNmm.contains("idobj")) {
 	 	    		objId = new String(fieldval); 
 	 	    	}
 	 	    	else if(fieldNmm.contains("objlv")) {
 	 	    		obj_lvl = new String(fieldval); 
 	 	    	}
 	 	    	else if(fieldNmm.contains("cmd")) {
 	 	    		cmd = new String(fieldval); 
 	 	    	}
 	 	    	else if(fieldNmm.contains("npmhs")) {
 	 	    		npmhs = new String(fieldval); 
 	 	    	}
 	 	    	else if(fieldNmm.contains("kdpst")) {
 	 	    		kdpst = new String(fieldval); 
 	 	    	}
 	 	    	
 	 		    
 	 	    }
 	 		
 			
 			
 			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect_v2.jsp"; 
 			String uri = request.getRequestURI(); 
 			String url = PathFinder.getPath(uri, target); 
 			//get.notifications
 			request.getRequestDispatcher(url+"?msg="+status_update+" <br/> Harap Menunggu&redirectTo=get.notifications&timeout=1").forward(request,response);
 			//request.getRequestDispatcher("get.profile?id_obj="+objId+"&nmm="+nmmhs+"&npm="+npmhs+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
 		}
 		else {
	 			//tandem dengan approval, krn pengaju juga approvee
 			ListIterator lip = vPengajuanLangsungApproval.listIterator();
			String brs = (String)lip.next();
			//System.out.println(brs);
			StringTokenizer st = new StringTokenizer(brs,"`");
			//(role+"`"+id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj+"`"++"`"+tkn_id_approvee+"`"+tkn_show_approvee+""+nmmhs);
			String your_role_ = st.nextToken();
			String id_ = st.nextToken();
			String thsms_ = st.nextToken();
			String kdpst_ = st.nextToken();
			String npmhs_ = st.nextToken();
			String status_akhir_ = st.nextToken();
			String skedul_date_ = st.nextToken();
			String realisasi_date_ = st.nextToken();
			String kdkmk_ = st.nextToken();
			String file_name_ = st.nextToken();
			String updtm_ = st.nextToken();
			String skedul_time_ = st.nextToken();
			String judul_ = st.nextToken();
			String show_owner_ = st.nextToken();
			//String show_approvee_ = st.nextToken();
			String show_monitoree_ = st.nextToken();
			String idobj_ = st.nextToken();
			String tkn_id_approvee_ = st.nextToken();
			String tkn_show_approvee_ = st.nextToken();
			//boolean show_ = true;
			//if(tkn_show_approvee_.contains("/"+validUsr.getIdObj()+"-no")) {
			//	show_ = false;
			//}
			//if(tkn_id_approvee_.contains("/"+validUsr.getIdObj()+"-")) {
			//boolean tindakan_ = true;
			//}
			String nmmhs_ = st.nextToken();
			String rule_tkn_approvee_id_ = st.nextToken();
			String urutan_ = st.nextToken();
			String tkn_approvee_nickname_ = st.nextToken();
			//done+"`"+time+"`"+riwayat)
			String done_so_hidden_ = st.nextToken();
			String realisasi_time_ = st.nextToken();
			String riwayat_penolakan_ = st.nextToken();
			long idobj = Checker.getObjectId(npmhs_);
			request.getRequestDispatcher("process.approvalPengajuanUa?your_role="+your_role_+"&id="+id_+"&thsms="+thsms_+"&kdpst="+kdpst_+"&npmhs="+npmhs_+"&status_akhir="+status_akhir_+"&skedul_date="+skedul_date_+"&realisasi_date="+realisasi_date_+"&kdkmk="+kdkmk_+"&file_name="+file_name_+"&updtm="+updtm_+"&skedul_time="+skedul_time_+"&judul="+judul_+"&show_owner="+show_owner_+"&tkn_show_approvee="+tkn_show_approvee_+"&tkn_id_approvee="+ tkn_id_approvee_+"&show_monitoree="+ show_monitoree_+"&idobj="+ idobj+"&nmmhs="+ nmmhs_+"&rule_tkn_approvee_id="+rule_tkn_approvee_id_+"&urutan="+urutan_+"&tkn_approvee_nickname="+tkn_approvee_nickname_+"&approval=Terima Pengajuan").forward(request,response);
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
