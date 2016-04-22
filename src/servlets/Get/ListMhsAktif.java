package servlets.Get;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
/**
 * Servlet implementation class ListMhsAktif
 */
@WebServlet("/ListMhsAktif")
public class ListMhsAktif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListMhsAktif() {
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
			
			String target_thsms = request.getParameter("target_thsms");
			System.out.println("get mhs aktif "+target_thsms);
			if(target_thsms==null || Checker.isStringNullOrEmpty(target_thsms)) {
				target_thsms = Checker.getThsmsNow();
			}
			Vector vJsoa = new Vector();
			ListIterator liJsoa = vJsoa.listIterator();
			Vector vTmp = isu.getScopeUpd7des2012ProdiOnly("allowGetMhsAktif");
			if(vTmp!=null && vTmp.size()>0) {
				ListIterator li = vTmp.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs);
					String idObj = st.nextToken();
					String kdpst = st.nextToken();
					String keterObj = st.nextToken();
					String lvl_obj = st.nextToken();
					String kdjen = st.nextToken();
					//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/mhs/aktif/thsms/"+target_thsms+"/kdpst/"+kdpst);
					JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/mhs/aktif/thsms/"+target_thsms+"/kdpst/"+kdpst+"/"+idObj);
					//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/mhs/aktif/thsms/"+target_thsms+"/"+idObj);
					
					if(jsoa!=null && jsoa.length()>0) {
						liJsoa.add(brs);
						liJsoa.add(jsoa);
						//System.out.println(jsoa.toString());
					}
					//System.out.println(brs);
				}
			}
			session.setAttribute("vJsoa", vJsoa);
			String target = Constants.getRootWeb()+"/InnerFrame/Summary/Civitas/Mhs/summaryMhsAktif.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
		}//end else
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
