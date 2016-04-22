package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;

/**
 * Servlet implementation class RequestPenyetaraan
 */
@WebServlet("/RequestPenyetaraan")
public class RequestPenyetaraan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestPenyetaraan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("sampe request");
		String[]baris = request.getParameterValues("makulAll");
		if(baris!=null && baris.length>0) {
			int len = baris.length;
			for(int i=0;i<len;i++) {
				System.out.println(i+"."+baris[i]);
			}
		}
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//dibawah ini adalah info target bukan operator
		String objId = request.getParameter("id_obj");
		System.out.println(objId);
		String nmm = request.getParameter("nmm");
		System.out.println(nmm);
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String cmd =  request.getParameter("cmd");
		SearchDb sdb = new SearchDb();
		System.out.println(isu.isUsrAllowTo("requestKonversiMakul", npm, objId));
		int norut = isu.isAllowTo("requestKonversiMakul");
		if(norut>0) {
			System.out.println("insert");
			
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
