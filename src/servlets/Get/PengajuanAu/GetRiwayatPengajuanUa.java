package servlets.Get.PengajuanAu;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.notification.SearchDbMainNotification;
import beans.dbase.pengajuan.ua.SearchDbUa;
import beans.dbase.pengajuan.ua.UpdateDbUa;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GetRiwayatPengajuanUa
 */
@WebServlet("/GetRiwayatPengajuanUa")
public class GetRiwayatPengajuanUa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRiwayatPengajuanUa() {
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
			//System.out.println("riwayat");
			String your_role =""+request.getParameter("your_role");
			System.out.println("your_role="+your_role);
			String id =""+request.getParameter("id");
			//System.out.println("id="+id);
			String thsms =""+request.getParameter("thsms");
			String kdpst =""+request.getParameter("kdpst");
			String npmhs =""+request.getParameter("npmhs");
			String status_akhir =""+request.getParameter("status_akhir");
			String skedul_date =""+request.getParameter("skedul_date");
			String realisasi_date =""+request.getParameter("realisasi_date");
			String kdkmk =""+request.getParameter("kdkmk");
			String file_name =""+request.getParameter("file_name");
			String updtm =""+request.getParameter("updtm");
			String skedul_time =""+request.getParameter("skedul_time");
			String judul =""+request.getParameter("judul");
			String show_owner =""+request.getParameter("show_owner");
			//String show_approvee =""+request.getParameter("show_approvee");
			String show_monitoree =""+request.getParameter("show_monitoree");
			String idobj =""+request.getParameter("idobj");
			String tkn_id_approvee = ""+request.getParameter("tkn_id_approvee");
			String tkn_show_approvee = ""+request.getParameter("tkn_show_approvee");
			String nmmhs =""+request.getParameter("nmmhs");
			String rule_tkn_approvee_id = ""+request.getParameter("rule_tkn_approvee_id");
			//System.out.println("@rule_tkn_approvee_id="+rule_tkn_approvee_id);
			String urutan=""+request.getParameter("urutan");
			String tkn_approvee_nickname=""+request.getParameter("tkn_approvee_nickname");
			//update status sudah dibaca
			int idObjOpr = isu.getIdObj();
			boolean isOprMonitoree = isu.isUsrAllowTo_updated("ua", npmhs);
			UpdateDbUa udb = new UpdateDbUa(isu.getNpm());
			//udb.setSudahdibaca(id, tkn_show_approvee, idObjOpr);
			udb.setSudahdibaca_v1(id, rule_tkn_approvee_id, tkn_show_approvee, idObjOpr, npmhs, isOprMonitoree);
			//udb.setSudahdibaca_v1(rule_tkn_approvee_id, tkn_show_appovee, oprObjId, owner, allowUa, allowUaa);
			//get riwayat pengajuan
			SearchDbUa sdb = new SearchDbUa(isu.getNpm());
			Vector vHist = sdb.getRiwayatPengajuanUa(id);
			
			String riwayat_penolakan = sdb.getRiwayatPenolakan(id);
			
			if(vHist==null) {
				vHist = new Vector();
			}
			session.setAttribute("vRiwayatPengajuan", vHist);
			//String who_missing = Getter.getSiapaYgBlumNgasihTindakanPengajuanUa(vHist,rule_tkn_approvee_id,tkn_approvee_nickname);
			
			////System.out.println("who_missing="+who_missing);
			//String info_vote_sdh_diwakilkan = Getter.isTindakanPengajuanUaSdhDiwakilkan(vHist, rule_tkn_approvee_id,  isu.getIdObj());
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/UA/formApprovalUa.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?riwayat_penolakan="+riwayat_penolakan).forward(request,response);

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
