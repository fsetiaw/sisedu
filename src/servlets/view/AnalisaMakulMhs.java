package servlets.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import beans.dbase.SearchDb;
import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Servlet implementation class AnalisaMakulMhs
 */
@WebServlet("/AnalisaMakulMhs")
public class AnalisaMakulMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnalisaMakulMhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.print("<html>");
		out.print("<head>");
		out.print("</head>");
		out.print("<body>");
		out.print("<h2 style=\"font-align:center\">Proses Selesai</h2>");
		out.print("<h3 style=\"font-align:center\">Anda Akan Dialihkan</h3>");
		out.print("</body>");
		out.print("</html>");
		//System.out.println("analisa");
		//String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		//String kdpst_nmpst = "74201,HUKUM";
		//StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		//String kdpst = st.nextToken();
		//String nmpst = st.nextToken();
		//cek parameter thsmsNow
		String thsmsNow = request.getParameter("thsmsNow");
		if(Checker.isStringNullOrEmpty(thsmsNow)) {
			thsmsNow = Checker.getThsmsNow();
		}
		String targetThsmsForecast = Tool.returnNextThsmsGiven(thsmsNow);
		/*
		System.out.println("analisa1");
		//get list thsms utk option, replace default thsms dgn thsmsNow
		Vector vListThsms = Constants.getListThsms();
		if(vListThsms!=null && vListThsms.size()>0) {
			ListIterator li = vListThsms.listIterator();
			li.next();
			li.set(thsmsNow);
		}	
		*/
		//System.out.println("analisa2");
		//cek mhs aktiv pd thsmsNow
		SearchDb sdb = new SearchDb();
		Vector vKdpst = sdb.getListKdpst();
		ListIterator liKdpst = vKdpst.listIterator();
		while(liKdpst.hasNext()) {
			String kdpst = (String)liKdpst.next();
			Vector vNpmhs = sdb.getNpmhsMhsAktifGiven(kdpst, thsmsNow);
			//System.out.println("analisa3");
		//hitung perkiraan mhs untuk makul berdasar mhs aktif
			Vector v = sdb.getPerkiraanJumlahMhsPerMakul(kdpst, targetThsmsForecast,vNpmhs);
			sdb.getPerkiraanJumlahMhsPerMakul(kdpst, targetThsmsForecast,vNpmhs);
			//System.out.println("analisa4");
			UpdateDb udb = new UpdateDb();
			udb.updateForecast_1(v,targetThsmsForecast);
		}
		//request.setAttribute("forecastMakulMhs", v);
		//request.setAttribute("targetForecastThsms",Tool.returnNextThsmsGiven(thsmsNow));
		String target = Constants.getRootWeb()+"/InnerFrame/StoredProcedure/dashStoredProces.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		//request.getRequestDispatcher(url).forward(request,response);
		response.sendRedirect(response.encodeRedirectURL(target));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//doGet(request,response);
	}

}
