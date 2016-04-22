package servlets.Get.Object;

import java.io.IOException;

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

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;


/**
 * Servlet implementation class InfoObjectBasedOnObjId
 */
@WebServlet("/InfoObjectBasedOnObjId")
public class InfoObjectBasedOnObjId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoObjectBasedOnObjId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("get inof object");
		
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String scope = request.getParameter("scope");
			String targetObjId = request.getParameter("targetObj");
			//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/"+isu.getIdObj()+"/hak_akses/"+scope);
			JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/"+targetObjId);
			JSONObject job = null;
			try {
				job  =  jsoa.getJSONObject(0);
				session.setAttribute("job", job);
				//request.setAttribute("targetObjId", targetObjId);
				System.out.println("job="+job.toString());
			}
			catch (JSONException e) {
				System.out.println("ada error disini");
			}
			String target = Constants.getRootWeb()+"/InnerFrame/json/form/edit/object/formEditHakAksesObjek_ver2.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			//System.out.println("0.cmd="+cmd+",atMenu="+atMenu+",scope="+scope);
			request.getRequestDispatcher(url+"?targetObjId="+targetObjId).forward(request,response);
			//JSONObject job = jsoa.getJSONObject(0);
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
