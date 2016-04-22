package servlets.ujian;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.onlineTest.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
/**
 * Servlet implementation class ProcessRequestControlPengawasUjian
 */
@WebServlet("/ProcessRequestControlPengawasUjian")
public class ProcessRequestControlPengawasUjian extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessRequestControlPengawasUjian() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String stat=request.getParameter("stat");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String idJadwalTest = (String)request.getParameter("idJadwalTest");
		String note = request.getParameter("note");
		System.out.println("ProcessRequestControlPengawasUjian");
		String command = request.getParameter("command");
		System.out.println("command="+command);
		System.out.println("idJadwalTest="+idJadwalTest);
		String demo = request.getParameter("demo");
		System.out.println("demo = "+demo);
		UpdateOnlineTestDb  ot = new UpdateOnlineTestDb();
		if(command.equalsIgnoreCase("stop")) {
			if(demo!=null&&demo.equalsIgnoreCase("stop")) {
				System.out.println("stat="+stat);
				stat = ot.stopUjian(idJadwalTest,isu.getNpm(),isu.getFullname());
			}
			else {
				//ignore - balik ke dashPengawasUjian
			}
		}
		else {
			if(command.equalsIgnoreCase("play")) {
				System.out.println("play");
				stat = ot.startUjian(idJadwalTest,isu.getNpm(),isu.getFullname());
				System.out.println(note);
			}
			else {
				if(command.equalsIgnoreCase("pause")) {
					stat = ot.pauseUjian(idJadwalTest,isu.getNpm(),isu.getFullname());
					System.out.println("pause");
					System.out.println(note);
				}
				else {
					if(command.equalsIgnoreCase("replay")) {
						stat = ot.replayUjian(idJadwalTest,isu.getNpm(),isu.getFullname());
						System.out.println("pause");
						System.out.println(note);
					}	
				}
			}
		}
		String catatan = ot.updateNoteUjian(idJadwalTest,note);
		request.setAttribute("catatan", catatan);
		String target = target = Constants.getRootWeb()+"/InnerFrame/Ujian/dashPengawasUjian.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		if(stat.equalsIgnoreCase("UJIAN SELESAI")) {
			request.getRequestDispatcher("go.cekOnlineTest?atMenu=listTest").forward(request,response);
		}
		else {
			request.getRequestDispatcher(url+"?stat="+stat).forward(request,response);
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
