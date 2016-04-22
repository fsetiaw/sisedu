package servlets.update.kartu_ujian_rules;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.kartu_ujian_rules.SearchDbKartuUjianRules;
import beans.dbase.kartu_ujian_rules.UpdateDbKartuUjianRules;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateKartuUjianRules
 */
@WebServlet("/UpdateKartuUjianRules")
public class UpdateKartuUjianRules extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateKartuUjianRules() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			
			String thsms_base = request.getParameter("thsms_base");
			String[]kdpst = request.getParameterValues("kdpst");
			
			String[]tipe_ujian = request.getParameterValues("tipe_ujian");
			
			String[]tkn_ver = request.getParameterValues("tkn_ver");
			
			String[]urutkah = request.getParameterValues("urutkah");
			
			String[]kode_kmp = request.getParameterValues("kode_kmp");
			
			String submit = request.getParameter("submit");
			
			UpdateDbKartuUjianRules udb = new UpdateDbKartuUjianRules(isu.getNpm());
			
			if(submit!=null) {
				String thsms = Checker.getThsmsNow();
				udb.updateKartuUjianRules(submit, thsms, kode_kmp, kdpst, tkn_ver, urutkah, tipe_ujian, thsms_base);
			}
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/KartuUjian/dashKartuUjian.jsp";
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher("prep.prepParamKartuUjianRules").forward(request,response);

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
