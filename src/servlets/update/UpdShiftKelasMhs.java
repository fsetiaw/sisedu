package servlets.update;

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
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdShiftKelasMhs
 */
@WebServlet("/UpdShiftKelasMhs")
public class UpdShiftKelasMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdShiftKelasMhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("update form shift");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//dibawah ini adalah info target bukan operator
		String shift = request.getParameter("shiftKelas");
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String cmd =  request.getParameter("cmd");
		//System.out.println("update shift "+objId+" "+npm+" "+nmm+" "+obj_lvl+" isu npm = "+isu.getNpm());
		int norut = isu.isAllowTo("viewKrs");
		UpdateDb udb = new UpdateDb();
		udb.updateShiftKelasMhs(kdpst,npm,shift);
		if(cmd.equalsIgnoreCase("editKrs")) {
			request.getRequestDispatcher("get.histKrs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
		}
		else {
			if(cmd.equalsIgnoreCase("insertKrs")) {
				request.getRequestDispatcher("get.updateKrsKhs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
			}
			else {
				if(cmd.equalsIgnoreCase("resetUsrPwd")) {
					request.getRequestDispatcher("go.resetUsrPwd?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
				}
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
