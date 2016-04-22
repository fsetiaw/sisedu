package servlets.update.cuti;

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

import beans.dbase.cuti.UpdateDbCuti;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdPengajuanCuti
 */
@WebServlet("/UpdPengajuanCuti")
public class UpdPengajuanCuti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdPengajuanCuti() {
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
			System.out.println("ooohohohoho");
			String target_thsms = request.getParameter("target_thsms");
			String submit = request.getParameter("submit");
			String thsms_base = request.getParameter("thsms_base");
			//String thsms_reg = Checker.getThsmsHeregistrasi();
			String tkn_inp_val = null;
			JSONArray jsoa = null;
			if(submit!=null && submit.equalsIgnoreCase("copy")) {
				if(thsms_base!=null && !Checker.isStringNullOrEmpty(thsms_base)) {
					//System.out.println("do copy");
					//tkn_inp_val = "";
					//String token = isu.getTokenTransaction();
					//tkn_inp_val = tkn_inp_val+"`"+thsms_base+"`"+thsms_reg;
					UpdateDbCuti udb = new UpdateDbCuti(isu.getNpm());
					udb.copyCutyRule(target_thsms, thsms_base);
					String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?msg=Updating Data&nuFwdPage=prep.prepParamCuti&atMenu=cutirul").forward(request,response);
					
					/*
					try {
						token = isu.prepVariableBeforCombineToUrlRest(token); 
						tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
						jsoa = isu.sendParamToRestUpdateUrl("/v1/daftar_ulang_rules/update/copy",token, tkn_inp_val); 
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
					if(jsoa==null) {
						//redirect to tamplete "INPUT GAGAL"
						String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepParamReg&atMenu=regrl").forward(request,response);
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

							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?msg=Update Berhasil<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepParamReg&atMenu=regrl").forward(request,response);
						}
						else {
						//redirect to tamplete "INPUT GAGAL"
							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepParamReg&atMenu=regrl").forward(request,response);
						}
					}
					*/					
				}
				else {
					//redirect gagal
					//redirect to tamplete "INPUT GAGAL"
					String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?msg=Update Gagal, BASE THSMS belum diisi<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepParamCuti&atMenu=cutirul").forward(request,response);
				}
			}
			else if(submit!=null && submit.equalsIgnoreCase("update")){
				System.out.println("do update");
				String[]kdpst = request.getParameterValues("kdpst_");
				String[]tkn_verificator = request.getParameterValues("tkn_verificator_");
				String[]urutan = request.getParameterValues("urutan_");
				String[]kode_kampus = request.getParameterValues("kode_kampus_");
				if(kdpst!=null && kdpst.length>0) {
					//for(int i=0;i<kdpst.length;i++) {
						//tkn_inp_val = ""+thsms_reg;
						//String token = isu.getTokenTransaction();
						//tkn_inp_val = tkn_inp_val+"`"+kdpst[i]+"`"+tkn_verificator[i]+"`"+urutan[i]+"`"+kode_kampus[i];
						//System.out.println(tkn_inp_val);
						//USING JSON
						/*
						try {
							token = isu.prepVariableBeforCombineToUrlRest(token); 
							tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
							jsoa = isu.sendParamToRestUpdateUrl("/v1/daftar_ulang_rules/update/formUpdate",token, tkn_inp_val); 
						}
						
						catch(Exception e) {
							e.printStackTrace();
						}
						*/
					UpdateDbCuti udb = new UpdateDbCuti(isu.getNpm());
					udb.updCutiRuleViaForm(target_thsms,kdpst,tkn_verificator,urutan,kode_kampus);
					//}
					
					String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?msg=Updating Data&nuFwdPage=prep.prepParamCuti&atMenu=cutirul").forward(request,response);
					//request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepParamReg&atMenu=regrl").forward(request,response);
				}
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

}
