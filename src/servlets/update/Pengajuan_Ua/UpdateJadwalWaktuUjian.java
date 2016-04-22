package servlets.update.Pengajuan_Ua;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.pengajuan.ua.UpdateDbUa;
import beans.login.InitSessionUsr;
import beans.setting.Constants;

/**
 * Servlet implementation class UpdateJadwalWaktuUjian
 */
@WebServlet("/UpdateJadwalWaktuUjian")
public class UpdateJadwalWaktuUjian extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateJadwalWaktuUjian() {
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
			//boolean sudah_ujian = false; //kalo realisasi tgl dan jam sudah diisi
			//System.out.println("update jadawal");
			String id = ""+request.getParameter("id_ua");
			String sked_dt = ""+request.getParameter("sked_dt");
			String sked_tm = ""+request.getParameter("sked_tm");
			String real_dt = ""+request.getParameter("real_dt");
			String real_tm = ""+request.getParameter("real_tm");
			
			UpdateDbUa udb = new UpdateDbUa(isu.getNpm());
			int update = udb.updateJadwalUa(id, sked_dt, sked_tm, real_dt, real_tm);
			//System.out.println("update = "+update);
			request.getRequestDispatcher("get.pengajuanUa?atMenu=ua").forward(request,response);
			
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
