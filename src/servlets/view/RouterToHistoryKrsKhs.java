package servlets.view;

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
import beans.tools.*;
/**
 * Servlet implementation class RouterToHistoryKrsKhs
 */
@WebServlet("/RouterToHistoryKrsKhs")
public class RouterToHistoryKrsKhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RouterToHistoryKrsKhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("router krs");
		HttpSession session = request.getSession(true);
		String npm_target = request.getParameter("kword");
		String validApprovee = request.getParameter("validApprovee");
		if(validApprovee==null || Checker.isStringNullOrEmpty(validApprovee)) {
			validApprovee = new String("false");
		}
		System.out.println("validApprovee@Router="+validApprovee);
		/*
		 * 	get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=histKrs" target="_self" >DATA<span>KRS/KHS</span></a></li>
		 */
		String id_obj=null;
		String nmmhs = null;
		String kdpst=null;
		String obj_lvl=null;
		
		JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/mhs/"+npm_target+"/prime_var");
		//System.out.println("jsoa =======");
		//System.out.println(jsoa.toString());
		if(jsoa!=null && jsoa.length()>0) {
			try {
				JSONObject job = jsoa.getJSONObject(0);
				id_obj = job.getString("ID_OBJ");
				nmmhs = job.getString("NMMHSMSMHS");
				kdpst = job.getString("KDPSTMSMHS");
				obj_lvl = job.getString("OBJ_LEVEL");		
			}
			catch(JSONException je) {
				je.printStackTrace();
			}
		}
		else {
			System.out.println("@RouterToHistoryKrsKhs,--- proses setelah list request krs approval, hrsnya tidak bole kosong sampe sini");
		}
		session.setAttribute("forceBackTo","get.notifications");
		session.setAttribute("forceGoTo","get.notifications");
		//String target = "get.histKrs?validApprovee="+validApprovee+"&id_obj="+id_obj+"&nmm="+nmmhs+"&npm="+npm_target+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs";
		//String target = "get.histKrs?id_obj="+id_obj+"&nmm="+nmmhs+"&npm="+npm_target+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs";
		String target = "get.histKrs?id_obj="+id_obj+"&nmm="+nmmhs+"&npm="+npm_target+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs&validApprovee=true";
		//String uri = request.getRequestURI();
		//String url_ff = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(target).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
