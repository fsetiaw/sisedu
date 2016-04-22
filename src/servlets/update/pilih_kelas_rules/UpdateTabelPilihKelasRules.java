package servlets.update.pilih_kelas_rules;

import java.io.IOException;

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
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateTabelPilihKelasRules
 */
@WebServlet("/UpdateTabelPilihKelasRules")
public class UpdateTabelPilihKelasRules extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTabelPilihKelasRules() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(true);
		JSONArray jsoa = null;
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			//parameter yg diterima jgn sampe null value
			String thsms_base = ""+request.getParameter("thsms_base");
			String thsms_target = Checker.getThsmsKrs();
			String submit = request.getParameter("submit");
			/*
			 * UPDATE
			 */
			if(submit.equalsIgnoreCase("update")) {
				System.out.println("update");
				String []target_id_obj = request.getParameterValues("id_obj_mhs");
				String []target_kdpst = request.getParameterValues("kdpst");
				if(target_id_obj!=null && target_id_obj.length>0) {
					//1. delete prev recor sebelum insert dgn yg baru
					String token = isu.getTokenTransaction();
					token = isu.prepVariableBeforCombineToUrlRest(token); 
					try {
						jsoa = isu.sendParamToRestUpdateUrl("/v1/pilih_kelas_rules/update/delete_prev_records",token,thsms_target);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					//System.out.println("jsoa deleted="+jsoa.toString());
					for(int i=0;i<target_id_obj.length;i++) {
					//for(int i=0;i<1;i++) {
						//System.out.println("target_id_obj["+i+"]="+target_id_obj[i]);
						String all_shift = ""+request.getParameter("all_shift_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(all_shift)) {
							all_shift = "null";
						}
						String tkn_shift = ""+request.getParameter("tkn_shift_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(tkn_shift)) {
							tkn_shift = "null";
						}
						String list_npm_shift = ""+request.getParameter("list_npm_shift_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(list_npm_shift)) {
							list_npm_shift = "null";
						}
						String all_prodi = ""+request.getParameter("all_prodi_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(all_prodi)) {
							all_prodi = "null";
						}
						String tkn_prodi = ""+request.getParameter("tkn_prodi_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(tkn_prodi)) {
							tkn_prodi = "null";
						}
						String list_npm_prodi = ""+request.getParameter("list_npm_prodi_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(list_npm_prodi)) {
							list_npm_prodi = "null";
						}
						
						String all_fak = ""+request.getParameter("all_fak_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(all_fak)) {
							all_fak = "null";
						}
						String tkn_fak = ""+request.getParameter("tkn_fak_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(tkn_fak)) {
							tkn_fak = "null";
						}
						String list_npm_fak = ""+request.getParameter("list_npm_fak_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(list_npm_fak)) {
							list_npm_fak = "null";
						}
						
						String all_kmp = ""+request.getParameter("all_kmp_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(all_kmp)) {
							all_kmp = "null";
						}
						String tkn_kmp = ""+request.getParameter("tkn_kmp_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(tkn_kmp)) {
							tkn_kmp = "null";
						}
						String list_npm_kmp = ""+request.getParameter("list_npm_kmp_"+target_id_obj[i]);
						if(Checker.isStringNullOrEmpty(list_npm_kmp)) {
							list_npm_kmp = "null";
						}
						//String list_objid_kdpst =  request.getParameter("list_objid_"+target_id_obj[i]);
						
						String[] list_objid_kdpst =  request.getParameterValues("list_objid_"+target_id_obj[i]);
						String tmp = "";
						for(int k=0;k<list_objid_kdpst.length;k++) {
							tmp = tmp+"`"+list_objid_kdpst[k];
						}
						//System.out.println("ini list_objid_kdpst="+list_objid_kdpst.length);
						//System.out.println("ini="+tmp);

						String tkn_inp_val = "";
						tkn_inp_val = tkn_inp_val+"`"+thsms_target+"`"+target_kdpst[i]+"`"+all_shift+"`"+tkn_shift+"`"+list_npm_shift
								+"`"+all_prodi+"`"+tkn_prodi+"`"+list_npm_prodi
								+"`"+all_fak+"`"+tkn_fak+"`"+list_npm_fak
								+"`"+all_kmp+"`"+tkn_kmp+"`"+list_npm_kmp
								+"`"+target_id_obj[i]+tmp;
								//+"`"+list_objid_kdpst;
						
						//tkn_inp_val=
						
						//`20151`57302`true`REGULER PAGI`31111`true`all`all`true`null`all`true`all`all`118-PST`null
						//`20151`57302`no`null`null`no`null`null`no`null`null`no`null`
						
						token = isu.getTokenTransaction();
						//System.out.println("tkn_inp_val="+tkn_inp_val);
						while(tkn_inp_val.contains("``")) {
							tkn_inp_val = tkn_inp_val.replace("``", "`null`");	
						}
						
						/*
						 * udah ngga mungkun diakhiri oleh ` krn ada tambahan var tmp
						 */
						
						//if(tkn_inp_val.endsWith("`")) {
						//	tkn_inp_val = tkn_inp_val+"null";
						//}
						//System.out.println("-tkn_inp_val="+tkn_inp_val);
						try {
							//	prep variable agar tidak break url rest system
							token = isu.prepVariableBeforCombineToUrlRest(token); 
							//System.out.println("token="+token);
							//System.out.println("tkn_inp_val="+tkn_inp_val);
							tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
							//System.out.println("tkn_inp_val="+tkn_inp_val);
							jsoa = isu.sendParamToRestUpdateUrl("/v1/pilih_kelas_rules/update/from/updateForm",token, tkn_inp_val);
							//System.out.println("return="+jsoa.toString());
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}	
				}
				
				request.getRequestDispatcher("prep.prepParamCakupanKelas?atMenu=ckls").forward(request,response);
				
			}
			/*
			 * COPY
			 */
			else if(submit.equalsIgnoreCase("copy")) {
				System.out.println("copy");
				int i=0;
				String tkn_inp_val = "";
				//tkn_inp_val = tkn_inp_val+"`"+thsms_base+"`"+Checker.getThsmsKrs();
				tkn_inp_val = tkn_inp_val+"`"+thsms_base+"`"+thsms_target;
				String token = isu.getTokenTransaction();
				
				try {
					//	prep variable agar tidak break url rest system
					token = isu.prepVariableBeforCombineToUrlRest(token); 
					tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
					jsoa = isu.sendParamToRestUpdateUrl("/v1/pilih_kelas_rules/update/copy",token, tkn_inp_val); 
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				//check status update dan redirect
				if(jsoa==null) {
					//redirect to tamplete "INPUT GAGAL"
					String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepParamCakupanKelas&atMenu=ckls").forward(request,response);
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
						jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/info_akses/"+isu.getIdObj()+"/ckls");
						request.setAttribute("jsoa", jsoa);

						String target = Constants.getRootWeb()+"/InnerFrame/Parameter/PilihKelas/dashPilihKelas.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?atMenu="+atMenu).forward(request,response);
					}
					else {
						//redirect to tamplete "INPUT GAGAL"
						String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepParamCakupanKelas&atMenu=ckls").forward(request,response);
					}
				}
			}
			/*
			if(Checker.isStringNullOrEmpty(tgl_orientasi)) {
				tgl_orientasi= "null";
			} 

			int i=0;
			String tkn_inp_val = "";
			//tkn_inp_val = tkn_inp_val+"`"+thsms+"`";
			String token = isu.getTokenTransaction();


			try {
				//	prep variable agar tidak break url rest system
				token = isu.prepVariableBeforCombineToUrlRest(token); 
				tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
				jsoa = isu.sendParamToRestUpdateUrl("/v1/../formSettingParam",token, tkn_inp_val); 
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			*/
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
