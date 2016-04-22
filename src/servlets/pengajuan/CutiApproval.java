package servlets.pengajuan;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.topik.UpdateDbTopik;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CutiApproval
 */
@WebServlet("/CutiApproval")
public class CutiApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CutiApproval() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			//System.out.println("cuti approval");
			//data target
			String id_obj = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String obj_lvl = request.getParameter("obj_lvl");
			String kdpst= request.getParameter("kdpst");

			String cmd= request.getParameter("cuti");
			String msg= request.getParameter("msg");
			
			String trans_info = request.getParameter("trans_id");
			String approval = request.getParameter("approval");
			String alasan = request.getParameter("alasan");
			StringTokenizer st = new StringTokenizer(trans_info,"`");
			//System.out.println("approval="+approval);
			//System.out.println(alasan);
			//System.out.println("npm-"+npm);
			String id=st.nextToken();
			String thsms_pengajuan=st.nextToken();
			String tipe_pengajuan=st.nextToken();
			String isi_topik_pengajuan=st.nextToken();
			String tkn_target_objnickname=st.nextToken();
			String tkn_target_objid=st.nextToken();
			String target_npm=st.nextToken();
			String creator_obj_id=st.nextToken();
			String creator_npm=st.nextToken();
			String creator_nmm=st.nextToken();
			String shwow_at_target=st.nextToken();
			String show_at_creator=st.nextToken();
			String updtm=st.nextToken();
			String approved=st.nextToken();
			String locked=st.nextToken();
			String rejected=st.nextToken();
			String creator_kdpst=st.nextToken();
			String target_kdpst=st.nextToken();
			String batal=st.nextToken();
			//System.out.println(id);
			//System.out.println(locked);
			UpdateDbTopik udt = new UpdateDbTopik(isu.getNpm());
			udt.approvalPengajuanCuti(id, isu.getIdObj(), isu.getNpm(), approval, alasan);
			
			boolean all_approved = AddHocFunction.isAllApproved(Long.parseLong(id), "CUTI");
			//System.out.println("all_approved="+all_approved);
			if(all_approved) {
				UpdateDbTrlsm udl = new UpdateDbTrlsm(isu.getNpm());
				//set status mhs jadi cuti
				String[]thsms_stmhs = {thsms_pengajuan+"`C"};
				String[]alasan_cuti = {isi_topik_pengajuan};
				//System.out.println("thsms_stmhs="+thsms_stmhs[0]);
				//System.out.println("alasan_cuti="+alasan_cuti[0]);
				//System.out.println("kdpst = "+kdpst);
				//System.out.println("npm = "+npm);
				udl.updStmhs(creator_kdpst, creator_npm, thsms_stmhs, alasan_cuti);
			}
			AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(creator_kdpst,creator_npm);
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher("go.moCuti?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst+"&cmd=cuti&msg=upd").forward(request,response);
			//request.getRequestDispatcher(url).forward(request,response);

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
