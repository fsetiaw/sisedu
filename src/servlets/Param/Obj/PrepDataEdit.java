package servlets.Param.Obj;

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

import beans.dbase.obj.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Servlet implementation class PrepDataCreation
 */
@WebServlet("/PrepDataCreation")
public class PrepDataEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepDataEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("iam in");
		String scope=request.getParameter("scope");
		String atMenu=request.getParameter("atMenu");
		//System.out.println("iam in");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		SearchDbObj sdb = new SearchDbObj(isu.getNpm());
		Vector vs = isu.getScopeUpd7des2012(scope);
		
		JSONArray jsoa = sdb.getObjNicknameFromScopeUpd7des2012InJson(vs);
		/*
		try {
			for(int i=0;i<jsoa.length();i++) {
				JSONObject job = jsoa.getJSONObject(i);
				
				//System.out.println("json-"+(i+1)+". "+job.getString("ID_OBJ"));
			}
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		*/
		session.setAttribute("jsoa", jsoa);
		String target = Constants.getRootWeb()+"/InnerFrame/json/tamplete/select/selectObj.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url_ff+"?atMenu="+atMenu).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
