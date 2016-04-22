package servlets.process;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.ProcessDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepFormBahanAjar
 */
@WebServlet("/PrepFormBahanAjar")
public class PrepFormBahanAjar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepFormBahanAjar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println("servlet bahan ajar");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here 
			String kdpst_nmpst = request.getParameter("kdpst_nmpst");
			String infoKur = request.getParameter("infoKur");
			String atMenu = request.getParameter("atMenu");
			String cmd = request.getParameter("cmd");
			String scope = request.getParameter("scope");
			String kelasTambahan = request.getParameter("kelasTambahan");
			String kodeKampus = request.getParameter("kodeKampus");
			String idkur =null,nmkur=null,skstt=null,smstt=null,start=null,ended=null;
			if(infoKur!=null) {
				StringTokenizer st = null;
				if(infoKur.contains("##")) {
					st = new StringTokenizer(infoKur,"##");
				}
				else {
					st = new StringTokenizer(infoKur,"#&");
				}	
				idkur = st.nextToken();
			}
			////System.out.println("++atMenu="+atMenu);
			ProcessDb pdb = new ProcessDb();
			Vector v = pdb.prepKurikulumForViewingUpd1(idkur);
			request.setAttribute("vListMakul", v);
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/bahan_ajar/formBahanAjar.jsp";
		
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope+"&kelasTambahan="+kelasTambahan+"&kodeKampus="+kodeKampus).forward(request,response);
			
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
