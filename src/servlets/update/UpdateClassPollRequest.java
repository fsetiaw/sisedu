package servlets.update;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.tools.Checker;

/**
 * Servlet implementation class UpdateClassPollRequest
 */
@WebServlet("/UpdateClassPollRequest")
public class UpdateClassPollRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateClassPollRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("upudate class pool");
		String infoKur=request.getParameter("infoKur");
		//System.out.println("infoKur="+infoKur);
		StringTokenizer stt = new StringTokenizer(infoKur,"||");
		String idkur = stt.nextToken();
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String kelasTambahan = (String) session.getAttribute("kelasTambahan");
		String kodeKampus = (String) session.getAttribute("kodeKampus");
		session.removeAttribute("kelasTambahan");
		session.removeAttribute("kodeKampus");
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		String cmd = request.getParameter("cmd");
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		String kdpst = st.nextToken();
		String nmpst = st.nextToken();
		String[]infoKelasDosen = (String[])request.getParameterValues("infoKelasDosen");
		if(infoKelasDosen==null) {
			infoKelasDosen = (String[])request.getAttribute("infoKelasDosen");
			if(infoKelasDosen==null) {
				infoKelasDosen = (String[])session.getAttribute("infoKelasDosen");
			}
		}
		String[]infoKelasMhs = (String[])request.getParameterValues("infoKelasMhs");
		if(infoKelasMhs==null) {
			infoKelasMhs = (String[])request.getAttribute("infoKelasMhs");
			if(infoKelasMhs==null) {
				infoKelasMhs = (String[])session.getAttribute("infoKelasMhs");
			}
		}
		for(int i=0;i<infoKelasDosen.length;i++) {
			String brs = infoKelasDosen[i];
			//System.out.println("-"+infoKelasDosen[i]);
			if(brs.contains("yesketer")) {
				infoKelasDosen[i]=infoKelasDosen[i]+"||"+infoKelasMhs[i];
			}
			//System.out.println("-"+infoKelasDosen[i]);
		}
		if(cmd!=null && cmd.equalsIgnoreCase("noketer")) {
			infoKelasDosen = (String[])request.getAttribute("infoKelasDosen");
			if(infoKelasDosen==null) {
				infoKelasDosen = (String[])session.getAttribute("infoKelasDosen");
			}	
		}
		boolean allowBukaKelas = Checker.isAllowRequestBukaKelas(kdpst);
		if(allowBukaKelas) {
		//if(false) {
			//String thsmsPmb = Checker.getThsmsPmb();		
			if(infoKelasDosen!=null && infoKelasDosen.length>0) {
				//insert class_pool
				UpdateDb udb = new UpdateDb(isu.getNpm());
				//udb.insertRequestBukaKelas(infoKelasDosen,infoKelasMhs,kdpst,idkur);
				udb.insertRequestBukaKelas(infoKelasDosen,infoKelasMhs,kdpst,idkur,kelasTambahan,kodeKampus);
				request.getRequestDispatcher("dashPengajuan.jsp?pb=yes&msg=Proses Pengajuan Buka Kelas Perkuliahan Telah Selesai, Harap Menunggu Anda Sedang Dialihkan").forward(request,response);
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
