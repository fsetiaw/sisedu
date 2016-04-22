package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.dbase.mhs.SearchDbInfoMhs;
import java.util.StringTokenizer;
/**
 * Servlet implementation class NuProfileGetter
 */
@WebServlet("/NuProfileGetter")
public class NuProfileGetter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NuProfileGetter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("nu profile getter");
		//forward nya ke history krs
		String npmhs = request.getParameter("npmhs");
		String kdpst = request.getParameter("kdpst");
		String cmd = request.getParameter("cmd");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
		String infoForGetProfile = sdb.prepForGetProfileMhs(npmhs,kdpst);
		//System.out.println(infoForGetProfile);
		StringTokenizer st = new StringTokenizer(infoForGetProfile,"||");
		String nmmhs = st.nextToken();
		String nimhs = st.nextToken();
		String stmhs = st.nextToken();
		String id_obj = st.nextToken();
		String obj_lvl = st.nextToken();
		String url = "get.histKrs?backTo=history&cmd="+cmd+"&npm="+npmhs+"&nim="+nimhs+"&stmhs="+stmhs+"&kdpst="+kdpst+"&id_obj="+id_obj+"&obj_lvl="+obj_lvl+"&nmm"+nmmhs;
		request.getRequestDispatcher(url).forward(request,response);
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
