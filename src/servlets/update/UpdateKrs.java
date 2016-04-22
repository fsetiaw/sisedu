package servlets.update;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.*;
/**
 * Servlet implementation class UpdateKrs
 */
@WebServlet("/UpdateKrs")
public class UpdateKrs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateKrs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("sampe");
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//dibawah ini adalah info target bukan operator
		String TargetThsmsKrs=request.getParameter("thsms");
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		//System.out.println("obj_lvl="+obj_lvl);
		String cmd =  request.getParameter("cmd");
		String[]idkmk = request.getParameterValues("makul");
		
		
		//System.out.println("linfo UpdateKrs = "+TargetThsmsKrs+","+npm+","+kdpst+","+obj_lvl);
		if(idkmk!=null) {
			//System.out.println("idkmk!=null");
			Vector vInfo = new Vector();
			ListIterator linfo = vInfo.listIterator();
			for(int i=0;i<idkmk.length;i++) {
				if(idkmk[i]!=null&&!idkmk[i].equalsIgnoreCase("null")) {
					//idmakul tidak boleh null = belum melakukan seleksi
					linfo.add(TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
				}
				//System.out.println("linfo = "+TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
			}
			//System.out.println("vInfo = "+vInfo.size());
			UpdateDb udb = new UpdateDb();
			//System.out.println("update krs");
			if(cmd!= null && cmd.equalsIgnoreCase("addMakul")) {
				udb.addMakulKeKrs(vInfo,npm);
				
			}
			else {
				//System.out.println("kesinitoh");
				udb.updateKrs(vInfo,npm);
				//update krs_whietlist - remove target Thsms dari white list
				udb.removeTargetThsmsFromWhiteList(TargetThsmsKrs, kdpst, npm);
			}	
			//System.out.println("update krs2");
		}else {
			//no update redirect
			System.out.println("its null");
		}
		//System.out.println("sampe1");
		//String target = Constants.getRootWeb()+"/InnerFrame/HistKrsKhs.jsp";
		//String uri = request.getRequestURI();
		//String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher("get.histKrs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs").forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
		
	}

}
