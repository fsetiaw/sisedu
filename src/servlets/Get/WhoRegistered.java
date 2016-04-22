package servlets.Get;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Vector;
import java.util.ListIterator;

import beans.dbase.daftarUlang.SearchDbInfoDaftarUlangTable;
import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

import java.util.StringTokenizer;
/**
 * Servlet implementation class WhoRegistered
 */
@WebServlet("/WhoRegistered")
public class WhoRegistered extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WhoRegistered() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("who registered");
		
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			
			Vector vSc = isu.getScopeUpd7des2012("hasHeregitrasiMenu");
			String target_thsms = request.getParameter("target_thsms");
			String thsms_her = null;
			if(target_thsms==null || Checker.isStringNullOrEmpty(target_thsms)) {
				thsms_her = new String(Checker.getThsmsHeregistrasi());
			}
			else {
				thsms_her = new String(target_thsms);
			}
			
			SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
			//String listInfoMhs = sdb.getListMhsTrnlm(thsms_her);
			String listInfoMhs = sdb.getListMhsDaftarUlang(thsms_her);
			UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm()); 
			udb.updateTabelDaftarUlang(listInfoMhs, thsms_her);
			SearchDbInfoDaftarUlangTable sdbu = new SearchDbInfoDaftarUlangTable(isu.getNpm());
			String  finalList = sdbu.getInfoDaftarUlangFilterByScope(thsms_her,vSc);
			String  listApprovee = sdbu.getDistinctNicknameDaftarUlangApprovee(thsms_her);
			int totApproval=0;
			String listObjApproval = "";
			if(finalList!=null) {
				StringTokenizer st = new StringTokenizer(finalList,"$");
				if(st.hasMoreTokens()) {
					String nmpst=st.nextToken();
					String kdpst=st.nextToken();
					String npmhs=st.nextToken();
					String nimhs=st.nextToken();
					String nmmhs=st.nextToken();
				
					String smawl=st.nextToken();
					String stpid=st.nextToken();
					String tglAju=st.nextToken();
					String tknApr=st.nextToken();
					String tknVerObj=st.nextToken();
					st = new StringTokenizer(tknVerObj,",");
					listObjApproval = ""+tknVerObj;
					totApproval = st.countTokens();
					String urutan=st.nextToken();
				}	
			}
			request.setAttribute("finalList", finalList);
			request.setAttribute("totApproval", totApproval+"");
			request.setAttribute("listObjApproval", listObjApproval);
			request.setAttribute("listApprovee", listApprovee);
			String target = Constants.getRootWeb()+"/InnerFrame/DaftarUlang/indexDaftarUlang.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?thsms_regis="+thsms_her).forward(request,response);

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
