  package servlets.Get;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.onlineTest.*;
import java.util.Vector;
import java.util.ListIterator;
import beans.tools.PathFinder;
import beans.login.InitSessionUsr;
import beans.setting.Constants;;
;/**
 * Servlet implementation class ListOnlineTest
 */
@WebServlet("/ListOnlineTest")
public class ListOnlineTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListOnlineTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("list test");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//cek yg e-tes diwajibkan dari EXT_CIVITAS
		OnlineTest ot = new OnlineTest();
		//searh online test yg jadi peserta
		Vector v = ot.getOnlineTestTerdaftarUntuk(isu.getNpm());
		//search onnline test dimana dpt menjadi operator
		Vector v1 = ot.getOnlineTestTerdaftarUntukOperator(isu.getNpm());
		//System.out.print("v1 siaee=");
		//System.out.print(v1.size());
		request.setAttribute("vListTest", v);
		request.setAttribute("vListTestForOpr", v1);
		String target = Constants.getRootWeb()+"/InnerFrame/MhsSection/dashListTest.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
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
