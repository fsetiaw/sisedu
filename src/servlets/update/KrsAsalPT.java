package servlets.update;

import java.io.IOException;
import java.util.Vector;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.UpdateDb;
/**
 * Servlet implementation class KrsAsalPT
 */
@WebServlet("/KrsAsalPT")
public class KrsAsalPT extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KrsAsalPT() {
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
		String id_obj = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String obj_lvl = request.getParameter("obj_lvl");
		String kdpst = request.getParameter("kdpst");
		String aspti = request.getParameter("aspti");
		String aspst = request.getParameter("aspst");
		String cmd = request.getParameter("cmd");
		String fwdTo = request.getParameter("fwdTo");
		String fwdPg = request.getParameter("fwdPg");
		Vector v_upd = new Vector();
		ListIterator li_upd = v_upd.listIterator();
		if(isu.isUsrAllowTo("insSksdi", npm, obj_lvl)) {
			String[]nakmkasal = request.getParameterValues("nakmkasal");
			String[]kdkmkasal = request.getParameterValues("kdkmkasal");
			String[]nlakhasal = request.getParameterValues("nlakhasal");
			String[]bobotasal = request.getParameterValues("bobotasal");
			String[]sksmkasal = request.getParameterValues("sksmkasal");
			for(int j=0;j<nakmkasal.length;j++) {
				//filter hanya seluruh variable harus diisi yg akan diinput
				if(!Checker.isStringNullOrEmpty(nakmkasal[j])&&!Checker.isStringNullOrEmpty(kdkmkasal[j])&&!Checker.isStringNullOrEmpty(nlakhasal[j])&&!Checker.isStringNullOrEmpty(bobotasal[j])&&!Checker.isStringNullOrEmpty(sksmkasal[j])) {
					li_upd.add(nakmkasal[j]+"#"+kdkmkasal[j]+"#"+nlakhasal[j]+"#"+bobotasal[j]+"#"+sksmkasal[j]);
				}
			}
			UpdateDb udb = new UpdateDb();
			udb.updKrsAsalPT(kdpst,npm,v_upd);
			String target = Constants.getRootWeb()+"/InnerFrame/indexMhsPindahan.jsp";
		//	String uri = request.getRequestURI();
		//	String url = PathFinder.getPath(uri, target);
		//	System.out.println("ini "+url+","+id_obj+","+nmm);
			//request.getRequestDispatcher(url).forward(request,response);
			request.getRequestDispatcher("toNonJsp.defaultVprofileRoute?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&aspti="+aspti+"&aspst="+aspst+"&cmd=none&fwdTo=toNonJsp.gatewayMhsPindahan&fwdPg="+target).forward(request,response);
			
		}
		else {
			//forward to no authority
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
