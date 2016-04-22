package servlets.update;

import java.io.IOException;
import java.util.StringTokenizer;

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
 * Servlet implementation class ThsmsKrsWhitelist
 */
@WebServlet("/ThsmsKrsWhitelist")
public class ThsmsKrsWhitelist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThsmsKrsWhitelist() {
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
		JSONArray jsoa = null;
		if(isu==null) {
			response.sendRedirect(Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			//System.out.println("oke");
			String id_obj = request.getParameter("id_obj"); 
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String obj_lvl = request.getParameter("obj_lvl");
			String kdpst = request.getParameter("kdpst");
			String cmd = request.getParameter("cmd");
			String tkn_thsms_whitelist_koma = request.getParameter("tkn_thsms_whitelist_koma");
			StringTokenizer st = new StringTokenizer(tkn_thsms_whitelist_koma);
			tkn_thsms_whitelist_koma = "";
			while(st.hasMoreTokens()) {
				tkn_thsms_whitelist_koma = tkn_thsms_whitelist_koma+st.nextToken();
			}
			tkn_thsms_whitelist_koma = tkn_thsms_whitelist_koma.replace(" ", "");
			if(Checker.isStringNullOrEmpty(tkn_thsms_whitelist_koma)) {
				tkn_thsms_whitelist_koma = "null";
			}
			String token = isu.getTokenTransaction();
			String tkn_inp_val = "";
			tkn_inp_val = tkn_inp_val+"`"+kdpst+"`"+npm+"`"+tkn_thsms_whitelist_koma;
			try {
				//prep variable agar tidak break url rest system
				token = isu.prepVariableBeforCombineToUrlRest(token); 
				tkn_inp_val = isu.prepVariableBeforCombineToUrlRest(tkn_inp_val); 
				jsoa = isu.sendParamToRestUpdateUrl("/v1/krs_whitelist/update/from/krs_whitelist_form",token, tkn_inp_val);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			//System.out.println("tkn_thsms_whitelist_koma="+tkn_thsms_whitelist_koma);
			//System.out.println("kdpst="+kdpst);
			//System.out.println("npmhs="+npm);

			if(jsoa==null) {
				//redirect to tamplete "INPUT GAGAL"
				String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?nuFwdPage=get.profile&msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&=id_obj"+id_obj+"&obj_lvl="+obj_lvl+"&cmd=dashboard").forward(request,response);
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
					//"
					request.getRequestDispatcher(url+"?nuFwdPage=get.profile&msg=Update Berhasil<br>Harap Menunggu Anda Sedang Dialihkan&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&=id_obj"+id_obj+"&obj_lvl="+obj_lvl+"&cmd=dashboard").forward(request,response);
				}
				else {
					//redirect to tamplete "INPUT GAGAL"
					String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?nuFwdPage=get.profile&msg=Update Gagal<br>Harap Menunggu Anda Sedang Dialihkan&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&=id_obj"+id_obj+"&obj_lvl="+obj_lvl+"&cmd=dashboard").forward(request,response);
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
