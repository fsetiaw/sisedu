package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.StringTokenizer;
import beans.dbase.dosen.UpdateDbDsn;
/**
 * Servlet implementation class DosenAjar
 */
@WebServlet("/DosenAjar")
public class DosenAjar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DosenAjar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[]infoDsnAjar = request.getParameterValues("infoDos");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//System.out.println("infoDsnAjar.lengt="+infoDsnAjar.length);
		if(infoDsnAjar!=null && infoDsnAjar.length>0) {
			UpdateDbDsn udb = new UpdateDbDsn(isu.getNpm());
			udb.updateDosenAjarCpThsmsKrs(infoDsnAjar);
		}
		
		
		String target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		url=url+"?nuFwdPage=get.listKelasClassPool&scope=reqUbahDsnAjar&callerPage=dashPengajuan.jsp&cmd=ubahDosenAjar&atMenu=ubahDosenAjar&scopeType=prodyOnly";
		
		request.getRequestDispatcher(url).forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
