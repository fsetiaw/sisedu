package servlets.update.command;

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
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateTableCommand_v1
 */
@WebServlet("/UpdateTableCommand_v1")
public class UpdateTableCommand_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTableCommand_v1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("commando");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		response.sendRedirect(Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String[]cmd_code = request.getParameterValues("cmd_code");
			String[]cmd_keter = request.getParameterValues("cmd_keter");
			String[]use_by = request.getParameterValues("use_by");
			String[]cmd_dependecy = request.getParameterValues("cmd_dependecy");
			String[]hak_akses_scope_kmp = request.getParameterValues("hak_akses_scope_kmp");
			String[]obj_lvl_allow_to_set = request.getParameterValues("obj_lvl_allow_to_set");
			String submit = request.getParameter("submit");
			System.out.println("submit="+submit);
			if(submit!=null && submit.equalsIgnoreCase("update")) {

				//1. buat token transaction 
				String token = isu.getTokenTransaction();
				//2. send to REST : 
				JSONArray jsoa = null;
				try {
					//prep variable agar tidak break url rest system
					token = isu.prepVariableBeforCombineToUrlRest(token); 
					if(cmd_code!=null && cmd_code.length>0) {
						for(int i=0;i<cmd_code.length;i++) {
						//for(int i=0;i<1;i++) {
							//System.out.println("cmd_code="+cmd_code[i]+"`"+cmd_keter[i]+"`"+use_by[i]+"`"+cmd_dependecy[i]+"`"+hak_akses_scope_kmp[i]+"`"+obj_lvl_allow_to_set[i]);
							String tkn_inp_val =cmd_code[i]+"`"+cmd_keter[i]+"`"+use_by[i]+"`"+cmd_dependecy[i]+"`"+hak_akses_scope_kmp[i]+"`"+obj_lvl_allow_to_set[i];
							tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
							//System.out.println("cmd_code tkn_inp_val="+tkn_inp_val);
							jsoa = isu.sendParamToRestUpdateUrl("/v1/cmd/update/from/formUpdate",token, tkn_inp_val); 
						}	
					}
					if(jsoa==null) {
						//redirect to tamplete "INPUT GAGAL"
						String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormCmd&atMenu=cmd0").forward(request,response);
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
							request.getRequestDispatcher(url+"?msg=Update Berhasil<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormCmd&atMenu=cmd0").forward(request,response);
						}
						else {
						//redirect to tamplete "INPUT GAGAL"
							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormCmd&atMenu=cmd0").forward(request,response);
						}
					}		
				}
				catch(Exception e) {
				e.printStackTrace();
				}
			}
			else if(submit!=null && submit.equalsIgnoreCase("insert")) {
				System.out.println("insert now");
				String inp_cmd_code = request.getParameter("inp_cmd_code");		
				if(Checker.isStringNullOrEmpty(inp_cmd_code)) {
					inp_cmd_code = "null";
				}
				String inp_cmd_keter = request.getParameter("inp_cmd_keter");
				if(Checker.isStringNullOrEmpty(inp_cmd_keter)) {
					inp_cmd_keter="null";
				}
				String inp_cmd_dependency = request.getParameter("inp_cmd_dependency");
				if(Checker.isStringNullOrEmpty(inp_cmd_dependency)) {
					inp_cmd_dependency="null";
				}
				String inp_cmd_use_by  = request.getParameter("inp_cmd_use_by");
				if(Checker.isStringNullOrEmpty(inp_cmd_use_by)) {
					inp_cmd_use_by="null";
				}
				String inp_cmd_pilihan_value = request.getParameter("inp_cmd_pilihan_value");
				if(Checker.isStringNullOrEmpty(inp_cmd_pilihan_value)) {
					inp_cmd_pilihan_value="null";
				}
				String inp_cmd_scope_kmp = request.getParameter("inp_cmd_scope_kmp");
				if(Checker.isStringNullOrEmpty(inp_cmd_scope_kmp)) {
					inp_cmd_scope_kmp="null";
				}
				String inp_cmd_obj_lvl_pengampu = request.getParameter("inp_cmd_obj_lvl_pengampu");
				if(Checker.isStringNullOrEmpty(inp_cmd_obj_lvl_pengampu)) {
					inp_cmd_obj_lvl_pengampu="null";
				}
				//1. buat token transaction 
				String token = isu.getTokenTransaction();
				//2. send to REST : 
				JSONArray jsoa = null;
				try {
					token = isu.prepVariableBeforCombineToUrlRest(token); 
					String tkn_inp_val =inp_cmd_code+"`"+inp_cmd_keter+"`"+inp_cmd_dependency+"`"+inp_cmd_pilihan_value+"`"+inp_cmd_scope_kmp+"`"+inp_cmd_obj_lvl_pengampu+"`"+inp_cmd_use_by;
					tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
					//System.out.println("cmd_code tkn_inp_val="+tkn_inp_val);
					jsoa = isu.sendParamToRestUpdateUrl("/v1/cmd/update/from/formInsert",token, tkn_inp_val); 
					
					if(jsoa==null) {
						//redirect to tamplete "INPUT GAGAL"
						String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormCmd&atMenu=cmd0").forward(request,response);
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
							request.getRequestDispatcher(url+"?msg=Update Berhasil<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormCmd&atMenu=cmd0").forward(request,response);
						}
						else {
						//redirect to tamplete "INPUT GAGAL"
							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nuFwdPage=prep.prepFormCmd&atMenu=cmd0").forward(request,response);
						}
					}
						
				}
				catch(Exception e) {
				e.printStackTrace();
				}
			}
			
			
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletRes ponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
