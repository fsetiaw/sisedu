package servlets.update.kalender;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateKalender
 */
@WebServlet("/UpdateKalender")
public class UpdateKalender extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateKalender() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("update kalender servlet");
		HttpSession session = request.getSession(true);
		JSONArray jsoa = null;
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String tgl_orientasi = ""+request.getParameter("tgl_orientasi");
			if(Checker.isStringNullOrEmpty(tgl_orientasi)) {
				tgl_orientasi= "null";
			}
			String tgl_kuliah_sta = ""+request.getParameter("tgl_kuliah_sta");
			if(Checker.isStringNullOrEmpty(tgl_kuliah_sta)) {
				tgl_kuliah_sta= "null";
			}
			String tgl_uts_sta = ""+request.getParameter("tgl_uts_sta");
			if(Checker.isStringNullOrEmpty(tgl_uts_sta)) {
				tgl_uts_sta= "null";
			}
			String tgl_uas_sta = ""+request.getParameter("tgl_uas_sta");
			if(Checker.isStringNullOrEmpty(tgl_uas_sta)) {
				tgl_uas_sta= "null";
			}
			String tgl_kuliah_end = ""+request.getParameter("tgl_kuliah_end");
			if(Checker.isStringNullOrEmpty(tgl_kuliah_end)) {
				tgl_kuliah_end= "null";
			}
	   	   	
	   	   	String opr_allow_ins_krs = ""+request.getParameter("opr_allow_ins_krs");
	   	   	if(Checker.isStringNullOrEmpty(opr_allow_ins_krs)) {
	   	   	opr_allow_ins_krs= "null";
			}
	   		String list_tgl_pmb_per_gel = ""+request.getParameter("list_tgl_pmb_per_gel");
	   		if(Checker.isStringNullOrEmpty(list_tgl_pmb_per_gel)) {
	   			list_tgl_pmb_per_gel= "null";
			}
	   		String list_tgl_saring_per_gel = ""+request.getParameter("list_tgl_saring_per_gel");
	   		if(Checker.isStringNullOrEmpty(list_tgl_saring_per_gel)) {
	   			list_tgl_saring_per_gel= "null";
			}
	   		String list_tgl_hereg_per_gel = ""+request.getParameter("list_tgl_hereg_per_gel");
	   		if(Checker.isStringNullOrEmpty(list_tgl_hereg_per_gel)) {
	   			list_tgl_hereg_per_gel= "null";
			}
	   	   	String range_thsms_sta = ""+request.getParameter("range_thsms_sta");
	   	   	if(Checker.isStringNullOrEmpty(list_tgl_hereg_per_gel)) {
	   	   	list_tgl_hereg_per_gel= "null";
			}
	   	   	String range_thsms_fokus = ""+request.getParameter("range_thsms_fokus");
	   	   	if(Checker.isStringNullOrEmpty(range_thsms_fokus)) {
	   	   		range_thsms_fokus= "null";
			}
	   	   	String range_thsms_end = ""+request.getParameter("range_thsms_end");
	   	   	if(Checker.isStringNullOrEmpty(range_thsms_end)) {
	   	   	range_thsms_end= "null";
			}
			String kdpst = ""+request.getParameter("kdpst");
			if(Checker.isStringNullOrEmpty(kdpst)) {
				kdpst= "null";
			}
			String id = ""+request.getParameter("id");
			if(Checker.isStringNullOrEmpty(id)) {
				id= "null";
			}
			String thsms = ""+request.getParameter("thsms");
			if(Checker.isStringNullOrEmpty(thsms)) {
				thsms= "null";
			}
			String thsms_pmb = ""+request.getParameter("thsms_pmb");
			if(Checker.isStringNullOrEmpty(thsms_pmb)) {
				thsms_pmb= "null";
			}
			String thsms_reg = ""+request.getParameter("thsms_reg");
			if(Checker.isStringNullOrEmpty(thsms_reg)) {
				thsms_reg= "null";
			}
			String thsms_buka_kls = ""+request.getParameter("thsms_buka_kls");
			if(Checker.isStringNullOrEmpty(thsms_buka_kls)) {
				thsms_buka_kls= "null";
			}
			String thsms_nilai = ""+request.getParameter("thsms_nilai");
			if(Checker.isStringNullOrEmpty(thsms_nilai)) {
				thsms_nilai= "null";
			}
			String oper_allow_edit_nilai = ""+request.getParameter("oper_allow_edit_nilai");
			if(Checker.isStringNullOrEmpty(oper_allow_edit_nilai)) {
				oper_allow_edit_nilai= "null";
			}
			String allow_edit_nilai_all_thsms = ""+request.getParameter("allow_edit_nilai_all_thsms");
			if(Checker.isStringNullOrEmpty(allow_edit_nilai_all_thsms)) {
				allow_edit_nilai_all_thsms= "null";
			}
			String thsms_krs = ""+request.getParameter("thsms_krs");
			if(Checker.isStringNullOrEmpty(thsms_krs)) {
				thsms_krs= "null";
			}
			String tgl_sta_krs = ""+request.getParameter("tgl_sta_krs");
			if(Checker.isStringNullOrEmpty(tgl_sta_krs)) {
				tgl_sta_krs= "null";
			}
			String tgl_end_krs = ""+request.getParameter("tgl_end_krs");
			if(Checker.isStringNullOrEmpty(tgl_end_krs)) {
				tgl_end_krs= "null";
			}
			String mhs_allow_input_krs = ""+request.getParameter("mhs_allow_input_krs");
			if(Checker.isStringNullOrEmpty(mhs_allow_input_krs)) {
				mhs_allow_input_krs= "null";
			}
			String heregistrasi_for_ins_krs = ""+request.getParameter("heregistrasi_for_ins_krs");
			if(Checker.isStringNullOrEmpty(heregistrasi_for_ins_krs)) {
				heregistrasi_for_ins_krs= "null";
			}
			String under_maintenance = ""+request.getParameter("under_maintenance");
			if(Checker.isStringNullOrEmpty(under_maintenance)) {
				under_maintenance= "null";
			}
			String npm_allow_under_maintenance = ""+request.getParameter("npm_allow_under_maintenance");
			if(Checker.isStringNullOrEmpty(npm_allow_under_maintenance)) {
				npm_allow_under_maintenance= "null";
			}
			int i=0;
			String tkn_inp_val = "";
			//String currentTime = ""+AskSystem.getCurrentTimestamp();
			tkn_inp_val = tkn_inp_val+"`"+thsms+"`"+thsms_pmb+"`"+thsms_reg+"`"+thsms_buka_kls+"`"+thsms_nilai+"`"+oper_allow_edit_nilai+"`"+allow_edit_nilai_all_thsms+"`"
					+"`"+thsms_krs+"`"+tgl_sta_krs+"`"+tgl_end_krs+"`"+mhs_allow_input_krs+"`"+under_maintenance+"`"+npm_allow_under_maintenance+"`"+kdpst+"`"
					+id+"`"+tgl_orientasi+"`"+tgl_kuliah_sta+"`"+tgl_uts_sta+"`"+tgl_uas_sta+"`"+tgl_kuliah_end+"`"+opr_allow_ins_krs+"`"+list_tgl_pmb_per_gel+"`"
					+list_tgl_saring_per_gel+"`"+list_tgl_hereg_per_gel+"`"+range_thsms_sta+"`"+range_thsms_fokus+"`"+range_thsms_end+"`"+heregistrasi_for_ins_krs;
					

			String token = isu.getTokenTransaction();
			
			try {
				//prep variable agar tidak break url rest system
				token = isu.prepVariableBeforCombineToUrlRest(token); 
				tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
				jsoa =  isu.sendParamToRestUpdateUrl("/v1/kalender/update/from/formSettingParam",token, tkn_inp_val);	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			if(jsoa==null) {
				//redirect to tamplete "INPUT GAGAL"
				String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormKalender&atMenu=kalAka").forward(request,response);
			}
			else {
				
				String value_update = "0";
				try {
					JSONObject job = jsoa.getJSONObject(0);
					value_update = (String)job.get("UPDATE_STATUS"); 
				}
				catch(JSONException e) {}//ignore
				if(Integer.parseInt(value_update)>0) {
					//redirect berhasil
					//redirect ke form claas pool rules
					String atMenu = request.getParameter("atMenu");
					jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/info_akses/"+isu.getIdObj()+"/paramCalendar");
					request.setAttribute("jsoa", jsoa);
					
					String target = Constants.getRootWeb()+"/InnerFrame/Parameter/KalenderAkademik/form_class_pool_rules.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?atMenu="+atMenu+"&thsmsBukaKelas="+thsms_buka_kls).forward(request,response);
					
					//String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
					//String uri = request.getRequestURI();
					//String url = PathFinder.getPath(uri, target);
					//request.getRequestDispatcher(url+"?msg=Update Berhasil<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormKalender&atMenu=kalAka").forward(request,response);
				}
				else {
					//redirect to tamplete "INPUT GAGAL"
					String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormKalender&atMenu=kalAka").forward(request,response);
				}
			
			}
			//JSONArray jsoa = Getter.readJsonArrayFromUrl("/")
			
		}

		//System.out.println("jsoa="+jsoa.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
