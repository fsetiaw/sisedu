package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
/**
 * Servlet implementation class KrsApproval
 */
@WebServlet("/KrsApproval")
public class KrsApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KrsApproval() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String submitButtonValue = request.getParameter("statusApproval");
		String catatan = request.getParameter("catatan");
		if(catatan!=null) {
			catatan = catatan.replace(",", "$");
		}	
		String approval = request.getParameter("approval");
		String note = request.getParameter("note");
		String berita = request.getParameter("beritaNotif");
		String kategori = request.getParameter("kategoriNotif");
		String thsms = request.getParameter("thsms");
		String id_obj = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl = request.getParameter("obj_lvl");
		System.out.println("=================]");
		System.out.println(thsms+","+nmm+","+npm+" "+approval+" "+isu.getNpm()+" = "+submitButtonValue+","+catatan);
		if(submitButtonValue.equalsIgnoreCase("Reject")) {
			approval = "reject";
		}
		else {
			if(submitButtonValue.equalsIgnoreCase("Reject Buka Kunci")) {
				approval = "reject_unlock";
			}
		}
		
		UpdateDb udb = new UpdateDb(isu.getNpm());
		//int i = udb.processKrsApproval(approval,thsms,npm); deprecated
		int i = udb.processKrsApproval(approval,thsms,npm,isu.getNpm(),isu.getNmmhs(isu.getNpm()));//tambah param usrNpm meperbolehkan wakil approval
		
		if(i>0) {
			udb.paReplyRequestApprovalKrsNotificationTable(thsms,npm, nmm, approval,catatan,berita,kategori);
			//String target = "get.histKrs?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs";
			//request.getRequestDispatcher(target).forward(request,response);
		}
		else {
			System.out.println("krsApproval.jsp : gagal toggle approval");
		}
		String target = "get.histKrs?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs";
		String forceGoTo = (String)session.getAttribute("forceGoTo");
		if(forceGoTo==null || Checker.isStringNullOrEmpty(forceGoTo)) {
			request.getRequestDispatcher(target).forward(request,response);	
		}
		else {
			request.getRequestDispatcher(forceGoTo).forward(request,response);
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
