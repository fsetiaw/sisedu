package servlets.Bridge;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class MsgFromTamu_v1
 */
@WebServlet("/MsgFromTamu_v1")
public class MsgFromTamu_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MsgFromTamu_v1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("briggeMe");
		String email = request.getParameter("email");
		String nama =  request.getParameter("nama");
		String phone =  request.getParameter("phone");
		String pesan =  request.getParameter("pesan");

		//System.out.println("email="+email);
		//System.out.println("nama="+nama);
		//System.out.println("pesan="+pesan);
		if(false) {
			
		}
		else {
			UpdateDb udb = new UpdateDb();
			udb.postMainTopicByTamuPasca(nama, email, phone, pesan);
			String target = Constants.getRootWeb()+"/bridge/postSukses.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
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
