package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.makul.UpdateDbMk;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class BatalKelas
 */
@WebServlet("/BatalKelas")
public class BatalKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatalKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("batal kelas");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String [] listKelasBatal = request.getParameterValues("kelasBatal");
			if(listKelasBatal!=null && listKelasBatal.length>0) {
				UpdateDbMk udb = new UpdateDbMk(isu.getNpm());
				udb.dropKelasDariCp(listKelasBatal);
			}
			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/TransisionalNotificationPage.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			url=url+"?nuFwdPage=InnerFrame/Akademik/dashPengajuan.jsp&msg=Update Data Selesai, Harap Menunggu Anda Sedang Dialihkan";
			
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
