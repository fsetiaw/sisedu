package servlets.process;

import java.io.IOException;
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
import java.util.StringTokenizer;
/**
 * Servlet implementation class RequestUnlockKrsForm
 */
@WebServlet("/RequestUnlockKrsForm")
public class RequestUnlockKrsForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestUnlockKrsForm() {
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
		String alasan = request.getParameter("alasan");
		String objId= request.getParameter("objId");
		String nmm= request.getParameter("nmm");
		String npm= request.getParameter("npm");
		String kdpst= request.getParameter("kdpst");
		String objLvl= request.getParameter("objLvl");
		if(alasan==null || Checker.isStringNullOrEmpty(alasan)) {	
			//cek alasan harus diisi
			System.out.println("masuk");
			String target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+objLvl+"&kdpst="+kdpst+"&alasan=Harap diisi alasan untuk pembukaan kunci / mengajukan KRS baru").forward(request,response);
		}
		else {
			UpdateDb udb = new UpdateDb(isu.getNpm());
			udb.requestUnlockUpdateKrs(alasan);
			request.getRequestDispatcher("get.notifications").forward(request,response);
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
