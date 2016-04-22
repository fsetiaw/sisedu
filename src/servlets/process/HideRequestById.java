package servlets.process;

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
 * Servlet implementation class HideRequestById
 */
@WebServlet("/HideRequestById")
public class HideRequestById extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HideRequestById() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestId = request.getParameter("requestId");
		String hiddenAt = request.getParameter("hiddenAt");
		UpdateDb udb = new UpdateDb();
		udb.hideKrsNotificationById(hiddenAt,requestId);
		
		request.getRequestDispatcher("get.notifications").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
