package servlets.process;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.UpdateDb;
import beans.dbase.pengajuan.ua.SearchDbUa;
import beans.dbase.pengajuan.ua.UpdateDbUa;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
/**
 * Servlet implementation class PengajuanUaApproval
 */
@WebServlet("/PengajuanUaApproval")
public class PengajuanUaApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PengajuanUaApproval() {
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
			System.out.println("approval");
			String submit_button_val = ""+request.getParameter("approval");
			String reason = ""+request.getParameter("reason");
			int idObjOpr = isu.getIdObj();
			//System.out.println("submit_button_val="+submit_button_val);
			//System.out.println("reason="+reason);
			String your_role =""+request.getParameter("your_role");
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
			String rule_tkn_approvee_id =""+request.getParameter("rule_tkn_approvee_id");
			String urutan =""+request.getParameter("urutan");
			String rule_tkn_approvee_nickname =""+request.getParameter("tkn_approvee_nickname");
			Vector vRiwayatPengajuanAfterInsert = new Vector();
			
			boolean isOprMonitoree = isu.isUsrAllowTo_updated("ua", npmhs);
			
			if(submit_button_val.startsWith("Tolak") && Checker.isStringNullOrEmpty(reason)) {
				request.getRequestDispatcher("go.prepFormApprovalUa?your_role="+your_role+"&id="+id+"&thsms="+thsms+"&kdpst="+kdpst+"&npmhs="+npmhs+"&status_akhir="+status_akhir+"&skedul_date="+skedul_date+"&realisasi_date="+realisasi_date+"&kdkmk="+kdkmk+"&file_name="+nmmhs+"&updtm="+updtm+"&skedul_time="+skedul_time+"&judul="+judul+"&show_owner="+show_owner+"&tkn_show_approvee="+tkn_show_approvee+"&tkn_id_approvee="+tkn_id_approvee+"&show_monitoree="+show_monitoree+"&idobj="+idobj+"&nmmhs="+nmmhs+"&err_msg=alasan").forward(request,response);
			}
			else if(submit_button_val.startsWith("Tolak") && !Checker.isStringNullOrEmpty(reason)) {
				UpdateDbUa udb = new UpdateDbUa(isu.getNpm());
				String my_role_nickname = Checker.yourApproveNicknameAtPengajuanUa(Long.parseLong(idobj), isu.getNpm());
				udb.tolakPengajuan(Integer.parseInt(id),reason,my_role_nickname);
				SearchDbUa sdb = new SearchDbUa(isu.getNpm());
				vRiwayatPengajuanAfterInsert = sdb.getRiwayatPengajuanUa(id);
				udb.updateStatusAkhirTabelPengajuanUa(id, vRiwayatPengajuanAfterInsert, rule_tkn_approvee_id, rule_tkn_approvee_nickname);
				
				//udb.setSudahdibaca_v1(id, rule_tkn_approvee_id, tkn_show_approvee, idObjOpr, npmhs, isOprMonitoree);
				
				request.getRequestDispatcher("get.pengajuanUa?atMenu=ua").forward(request,response);
			}
			else if(submit_button_val.startsWith("Terima")) {
				UpdateDbUa udb = new UpdateDbUa(isu.getNpm());
				String my_role_nickname = Checker.yourApproveNicknameAtPengajuanUa(Long.parseLong(idobj), isu.getNpm());
				udb.terimaPengajuan(Integer.parseInt(id),reason,my_role_nickname);
				SearchDbUa sdb = new SearchDbUa(isu.getNpm());
				vRiwayatPengajuanAfterInsert = sdb.getRiwayatPengajuanUa(id);
				udb.updateStatusAkhirTabelPengajuanUa(id, vRiwayatPengajuanAfterInsert, rule_tkn_approvee_id, rule_tkn_approvee_nickname);
				
				//udb.setSudahdibaca_v1(id, rule_tkn_approvee_id, tkn_show_approvee, idObjOpr, npmhs, isOprMonitoree);
				
				request.getRequestDispatcher("get.pengajuanUa?atMenu=ua").forward(request,response);
				//System.out.println("UA Approval ");
				//session.setAttribute("vRiwayatPengajuan", vHist);
			
			}
			
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/UA/formApprovalUa.jsp"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher("get.pengajuanUa?atMenu=ua").forward(request,response);

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
