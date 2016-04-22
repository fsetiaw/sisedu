package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.*;
import beans.login.InitSessionUsr;
/**
 * Servlet implementation class HapusMakul
 */
@WebServlet("/HapusMakul")
public class HapusKurikulum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public HapusMakul() {
//        super();
        // TODO Auto-generated constructor stub
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String idkur_ = request.getParameter("idkur_");
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		UpdateDb udb = new UpdateDb();
		udb.deleteKurikulum(Integer.valueOf(idkur_).intValue(),request.getRemoteAddr(),isu);
		System.out.println("hapus = "+idkur_);
		System.out.println(kdpst_nmpst);
		request.getRequestDispatcher("get.listKurikulum?kdpst_nmpst="+kdpst_nmpst).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
