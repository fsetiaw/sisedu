package servlets.update.classPoll;

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

import beans.dbase.classPoll.UpdateDbClassPoll;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdDataCpRules
 */
@WebServlet("/UpdDataCpRules")
public class UpdDataCpRules extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdDataCpRules() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		    response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			//System.out.println("iyadeh666");
			JSONArray jsoa = null;
			String button = request.getParameter("submit");
			String base_thsms = request.getParameter("base_thsms");
			String thsms_buka_kelas = request.getParameter("thsms_buka_kelas");
			//System.out.println("base_thsms=="+base_thsms);
			//System.out.println("thsms_buka_kelas=="+thsms_buka_kelas);
			if(button!=null) {
				if(button.equalsIgnoreCase("COPY")) {
					//System.out.println("masik button copy");
					String tkn_inp_val = "";
					//String currentTime = ""+AskSystem.getCurrentTimestamp();
					tkn_inp_val = tkn_inp_val+"`"+base_thsms+"`"+thsms_buka_kelas;
							

					String token = isu.getTokenTransaction();
					
					try {
						//prep variable agar tidak break url rest system
						token = isu.prepVariableBeforCombineToUrlRest(token); 
						tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
						jsoa =  isu.sendParamToRestUpdateUrl("/v1/class_pool/update/rules/copy",token, tkn_inp_val);	
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
							//String atMenu = request.getParameter("atMenu");
							//jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/info_akses/"+isu.getIdObj()+"/paramCalendar");
							//request.setAttribute("jsoa", jsoa);
							
							//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/KalenderAkademik/form_class_pool_rules.jsp";
							//String uri = request.getRequestURI();
							//String url = PathFinder.getPath(uri, target);
							//request.getRequestDispatcher(url+"?atMenu="+atMenu+"&thsmsBukaKelas="+thsms_buka_kls).forward(request,response);
							
							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?msg=Update Berhasil<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormKalender&atMenu=kalAka").forward(request,response);
						}
						else {
							//redirect to tamplete "INPUT GAGAL"
							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormKalender&atMenu=kalAka").forward(request,response);
						}
					
					}
				}
				else if(button.equalsIgnoreCase("UPDATE")) {
					//System.out.println("masik button update");
					String tkn_inp_val = "";
					//String currentTime = ""+AskSystem.getCurrentTimestamp();
					
					String[]kdpst_val = request.getParameterValues("kdpst_val");		
					String[]verificator_val = request.getParameterValues("verificator_val");
					String[]urut_val = request.getParameterValues("urutan_val");
					String[]kode_kmp_val = request.getParameterValues("kode_kmp_val");
					
					tkn_inp_val = new String(); 
					tkn_inp_val	= ""+thsms_buka_kelas;
					//String currentTime = ""+AskSystem.getCurrentTimestamp();
					
					//System.out.println("thsms_buka_kelas="+thsms_buka_kelas);
					//System.out.println("kdpst_val="+kdpst_val[0]);
					for(int i=0;i<kdpst_val.length;i++) {
						
						tkn_inp_val = tkn_inp_val+"`"+kdpst_val[i]+"`"+verificator_val[i]+"`"+urut_val[i]+"`"+kode_kmp_val[i];
						//System.out.println(kdpst_val[i]+"`"+verificator_val[i]+"`"+urut_val[i]+"`"+kode_kmp_val[i]);
					}
					
					String token = isu.getTokenTransaction();
					
					try {
						//prep variable agar tidak break url rest system
						token = isu.prepVariableBeforCombineToUrlRest(token); 
						tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
						jsoa =  isu.sendParamToRestUpdateUrl("/v1/class_pool/update/rules",token, tkn_inp_val);	
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					//System.out.println("jsoa="+jsoa.toString());
					
					if(jsoa==null) {
						//System.out.println("msk1");
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
							//System.out.println("msk2");
							//redirect berhasil
							//redirect ke form claas pool rules
							//String atMenu = request.getParameter("atMenu");
							//jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/info_akses/"+isu.getIdObj()+"/paramCalendar");
							//request.setAttribute("jsoa", jsoa);
							
							//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/KalenderAkademik/form_class_pool_rules.jsp";
							//String uri = request.getRequestURI();
							//String url = PathFinder.getPath(uri, target);
							//request.getRequestDispatcher(url+"?atMenu="+atMenu+"&thsmsBukaKelas="+thsms_buka_kls).forward(request,response);
							
							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?msg=Update Berhasil<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormKalender&atMenu=kalAka").forward(request,response);
						}
						else {
							//System.out.println("msk3");
							//redirect to tamplete "INPUT GAGAL"
							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormKalender&atMenu=kalAka").forward(request,response);
						}
					
					}
					
				}
			}
			else {
				
			}
			//System.out.println("button=="+button);
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/PraKuliah/KelasKuliah/formParamPengajuanKelas.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
//		url+"?atMenu=kelasKuliah&scopeKampusCmd=paramKlsKuliah"
			//request.getRequestDispatcher(url+"?atMenu=kelasKuliah&scopeKampusCmd=paramKlsKuliah").forward(request,response);
			
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
