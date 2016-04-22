package servlets.process;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;

import beans.dbase.cuti.SearchDbInfoCuti;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepFormCalenderAkademik
 */
@WebServlet("/PrepFormPengaturanCuti")
public class PrepFormPengaturanCuti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepFormPengaturanCuti() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("prep cuti");
		//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		//response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		//response.setHeader("Expires", "0"); // Proxies.
		//response.setContentType("text/html");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			System.out.println("2");
		//kode here
			//String atMenu = request.getParameter("atMenu");
			//mulai sekarang atMenu value = parameternya ada seperti s,sop,cmd0,dst
			//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/info_akses/"+isu.getIdObj()+"/"+atMenu);
			//request.setAttribute("jsoa", jsoa);
			String scopeCmd = request.getParameter("scopeCmd");
			String hak_akses = new String();
			if(isu.isAllowTo(scopeCmd)>0) {
				hak_akses = isu.getHakAkses(scopeCmd);
			}
			
			boolean editable = false;
			if(hak_akses==null || Checker.isStringNullOrEmpty(hak_akses)) {
				//redirect
			}
			else if(hak_akses.contains("e") || hak_akses.contains("i")) {
				editable = true;
			}
			
			String target_thsms = Checker.getThsmsHeregistrasi(); //target thsms cuti = registrasi
			SearchDbInfoCuti sdb = new SearchDbInfoCuti(isu.getNpm());
			Vector vRule = sdb.getCutiAkademikRules(target_thsms);
			session.setAttribute("vListRule", vRule);
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/Cuti/dashParamCuti.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?hak_akses="+hak_akses+"&editable="+editable+"&target_thsms="+target_thsms).forward(request,response);
			
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
