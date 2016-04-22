package servlets.process;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.Beasiswa.SearchDbBeasiswa;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepFormAddPaketBeasiswa
 */
@WebServlet("/PrepFormAddPaketBeasiswa")
public class PrepFormAddPaketBeasiswa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepFormAddPaketBeasiswa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		System.out.println("oke");
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String keterScope = request.getParameter("scope");
			String scopeKampus = isu.getScopeKampus(keterScope);
			if(scopeKampus==null || Checker.isStringNullOrEmpty(scopeKampus)) {
				response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/ErrorPageTamplete.jsp?errMsg=Anda Tidak Memiliki Akses Kampus");
			}
			else {
				SearchDbBeasiswa sdb = new SearchDbBeasiswa(isu.getNpm());
				String jenisPaket_kampus = sdb.getListJenisBeasiswaSesuaiScopeKampus(scopeKampus);
				if(jenisPaket_kampus!=null && !Checker.isStringNullOrEmpty(jenisPaket_kampus)) {
					String target = Constants.getRootWeb()+"/InnerFrame/Keu/Beasiswa/formAddPaketBeasiswa.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?jenisPaket_kampus="+jenisPaket_kampus+"&formType=addPaketBea&scopeKampus="+scopeKampus).forward(request,response);
				}
				else {
					response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/ErrorPageTamplete.jsp?errMsg=Belum Ada Data Paket Beasiswa pada Kampus Tujuan");
				}
			}
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
