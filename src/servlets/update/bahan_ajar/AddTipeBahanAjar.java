package servlets.update.bahan_ajar;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.bahan_ajar.UpdateDbBahanAjar;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class AddTipeBahanAjar
 */
@WebServlet("/AddTipeBahanAjar")
public class AddTipeBahanAjar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTipeBahanAjar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("add tipe bhn ajar");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			response.getWriter().append("Served at: ").append(request.getContextPath());
			String target_kdkmk = request.getParameter("target_kdkmk");
			String target_nakmk = request.getParameter("target_nakmk");
			String target_kdpst = request.getParameter("target_kdpst");
			String target_idkur = request.getParameter("target_idkur");
			String atMenu = request.getParameter("atMenu");
			String nu_tipe_bahan_ajar = request.getParameter("nu_tipe_bahan_ajar");
	    	String kode_jen = request.getParameter("kode_jen");
	    	String kode_prodi = request.getParameter("kode_prodi");
	    	String kode_kampus =  request.getParameter("kode_kampus");
	    	String path_bahan_ajar =  request.getParameter("kode_kampus");
	    	UpdateDbBahanAjar udb = new UpdateDbBahanAjar(isu.getNpm());
	    	udb.insertTipeBahanAjarBaru(nu_tipe_bahan_ajar, kode_prodi, kode_jen, kode_kampus, path_bahan_ajar);
	    	
	    	
	    	System.out.println(kode_jen+"-"+kode_prodi+"-"+kode_kampus);
	    	String target = "prep.bahanAjarGivenMk?atMenu=mba&kdkmk="+target_kdkmk+"&kdpst="+target_kdpst+"&idkur="+target_idkur+"&nakmk="+target_nakmk;
			request.getRequestDispatcher(target).forward(request,response);
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);


		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
