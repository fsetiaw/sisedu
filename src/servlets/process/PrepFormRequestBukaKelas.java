package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

import java.util.Vector;
import java.util.StringTokenizer;
import java.util.ListIterator;
/**
 * Servlet implementation class PrepFormRequestBukaKelas
 */
@WebServlet("/PrepFormRequestBukaKelas")
public class PrepFormRequestBukaKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepFormRequestBukaKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		String kdpst = st.nextToken();
		String nmpst = st.nextToken();
		String kelasTambahan = request.getParameter("kelasTambahan");
		String kodeKampus = request.getParameter("kodeKampus");
		if(kelasTambahan==null || Checker.isStringNullOrEmpty(kelasTambahan)) {
			kelasTambahan="no";
		}
		//cek apa masih boleh request kelas
		
		if(Checker.isAllowRequestBukaKelas(kdpst)) {
			st = new StringTokenizer(kdpst_nmpst,",");
			String kdjen = Checker.getKdjen(st.nextToken());
			//String thsmsPmb = Checker.getThsmsPmb();
			Vector vListMakul = (Vector)request.getAttribute("vListMakul");
			if(vListMakul==null) {
				vListMakul = (Vector)session.getAttribute("vListMakul");
			}
			String tknShift = Checker.getListShift(kdjen);
		
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/formPengajuanBukaKelas.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?tknShift="+tknShift+"&kdjen="+kdjen+"&kelasTambahan="+kelasTambahan+"&kodeKampus="+kodeKampus).forward(request,response);
		}
		else {
			//tidak boleh buka kelas
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashPengajuan.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?errMsg=SAAT INI PROSES PENGAJUKAN PEMBUKAAN KELAS PERKULIAHAN TELAH DITUTUP").forward(request,response);
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
