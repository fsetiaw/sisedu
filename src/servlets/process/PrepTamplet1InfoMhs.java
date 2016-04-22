package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.mhs.SearchDbInfoMhs;
/**
 * Servlet implementation class PrepTamplet1InfoMhs
 */
@WebServlet("/PrepTamplet1InfoMhs")
public class PrepTamplet1InfoMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepTamplet1InfoMhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("inini");
		String listNpm=request.getParameter("listNpm");
		//value listNpm di overide di sini
		//System.out.println(listNpm);
		SearchDbInfoMhs sdb = new SearchDbInfoMhs();
		String kdpst=request.getParameter("kdpst");
		String nmpst=request.getParameter("nmpst");
		String listNpmNama = sdb.getNmmhsGiven(listNpm,kdpst);
		String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Mhs/ListInfoMhsType1.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url+"?listNpm="+listNpmNama).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
