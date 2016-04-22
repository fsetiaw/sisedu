package servlets.ujian;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import java.util.ListIterator;
import beans.login.InitSessionUsr;

/**
 * Servlet implementation class GetAccsessibleOnlineTest
 */
@WebServlet("/GetAccsessibleOnlineTest")
public class GetAccsessibleOnlineTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAccsessibleOnlineTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("follow me");
		/*
		 * ada 2 macam akses : fullAksesJadwalOnlineTest  && editOnlyJadwalOnlineTest
		 */
		//step 1 cek hak askses yg dimilki
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		Vector v = isu.getScopeUpd7des2012("fullAksesJadwalOnlineTest");
		if(v!=null) {
			//full akses
			ListIterator li = v.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				//StringTokenizer
				System.out.println(brs);
			}
			
		}
		else {
			//edit only
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
