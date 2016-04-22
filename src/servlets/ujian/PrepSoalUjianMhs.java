package servlets.ujian;

import java.io.IOException;
import beans.dbase.onlineTest.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import beans.dbase.onlineTest.*;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.PathFinder;
import java.util.ListIterator;
import java.util.Vector;
/**
 * Servlet implementation class PrepSoalUjianMhs
 */
@WebServlet("/PrepSoalUjianMhs")
public class PrepSoalUjianMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepSoalUjianMhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("prep soal ujian");
		String RealDateTimeStart = request.getParameter("RealDateTimeStart");
		String nmmtest = request.getParameter("nmmtest");
		String keterTest = request.getParameter("keterTest");
		String totalSoal = request.getParameter("totalSoal");
		String totalWaktu = request.getParameter("totalWaktu");
		String passingGrade = request.getParameter("passingGrade");
		
		String idJdwlTest = request.getParameter("idJdwlTest");
		String idCivJdwlBridge = request.getParameter("idCivJdwlBridge");
		String idOnlineTest = request.getParameter("idOnlineTest");
		String room = request.getParameter("room");
		String ipAllow = request.getParameter("ipAllow");
		String ipClient = AskSystem.getClientIp(request);
		//cek allow ip address
		boolean blockByIp = true;
		if(ipAllow.equalsIgnoreCase("0.0.0.0")) {
			blockByIp = false;
		}
		else {
			if(AskSystem.isItSameIp(ipClient, ipAllow)) {
				blockByIp = false;
			}
		}
		if(!blockByIp) {
			//System.out.println("ip client"+ipClient);
			//System.out.println(idJdwlTest+","+idOnlineTest+","+room+","+ipAllow);
			OnlineTest ot = new OnlineTest();
			String tokenKodeGroupAndListSoal = ot.prepSoalUjian(idOnlineTest);
			SearchOnlineTestDb otdb = new SearchOnlineTestDb();
			
			
			//request.setAttribute("vGroupSoal", v);
			//String target = Constants.getRootWeb()+"/InnerFrame/MhsSection/Ujian/halMukaUjian.jsp";
			//String target = "go.getSoal";
			//String uri = request.getRequestURI();
			//String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher("go.getSoal?nmmtest="+nmmtest+"&keterTest="+keterTest+"&totalSoal="+totalSoal+"&totalWaktu="+totalWaktu+"&passingGrade="+passingGrade+"&idOnlineTest="+idOnlineTest+"&idCivJdwlBridge="+idCivJdwlBridge+"&atSoal=1&atChapter=1&idJdwlTest="+idJdwlTest+"&tokenKodeGroupAndListSoal="+tokenKodeGroupAndListSoal+"&RealDateTimeStart="+RealDateTimeStart).forward(request,response);

		}
		else {
			String target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTamplete.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff+"?errMsg=UJIAN TIDAK DAPAT DIAMBIL MELALUI KOMPUTER INI&backTo=go.cekOnlineTest&atMenu=listTest").forward(request,response);
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
