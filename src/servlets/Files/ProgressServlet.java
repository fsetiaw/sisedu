package servlets.Files;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.folder.file.*;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class ProgressServlet
 */
@WebServlet("/ProgressServlet")
public class ProgressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProgressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(true);
		if (session == null) {
			out.println("Sorry, session is null"); // just to be safe
			return;
		}

		TestProgressListener testProgressListener = (TestProgressListener) session.getAttribute("testProgressListener");
		if (testProgressListener == null) {
			out.println("Progress listener is null");
			return;
		}

		out.println(testProgressListener.getMessage());

	}

}
