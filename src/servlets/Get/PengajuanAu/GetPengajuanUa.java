package servlets.Get.PengajuanAu;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.notification.SearchDbMainNotification;
import beans.dbase.pengajuan.ua.SearchDbUa;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GetPengajuanUa
 */
@WebServlet("/GetPengajuanUa")
public class GetPengajuanUa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPengajuanUa() {
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
			Vector vUa = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn("ua");

			Vector vUaa = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn("uaa");
			////System.out.println("point 1");
			if((vUa!=null && vUa.size()>0)||(vUaa!=null && vUaa.size()>0)) {
				////System.out.println("point 2");
				SearchDbUa sdm = new SearchDbUa(isu.getNpm());
				Vector vPengajuan = sdm.getPengajuanUa(vUa, vUaa, isu.getIdObj());
				if(vPengajuan!=null && vPengajuan.size()>0) {
					//cek apakah anda approvee atau yg laen
					vPengajuan = sdm.getYourRoleAtasPengajuan(vPengajuan);
					if(vPengajuan!=null && vPengajuan.size()>0) {
						//add info nama
						vPengajuan = sdm.addInfoNmmhs(vPengajuan);
						vPengajuan = sdm.addInfoPengajuanRules(vPengajuan);
					}
				}
				////System.out.println("vPengajuan size="+vPengajuan.size());
				session.setAttribute("vPengajuan", vPengajuan);
			}
			
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/UA/dashUjianAkhir.jsp"; 
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
		doGet(request, response);
	}

}
