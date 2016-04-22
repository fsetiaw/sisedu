package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.dbase.*;
import beans.setting.Constants;
import beans.tools.PathFinder;
/**
 * Servlet implementation class EditKrsKhs
 */
@WebServlet("/EditKrsKhs")
public class HapusKrsKhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HapusKrsKhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("hapus yuk");
		//String msg = request.getParameter("msg");
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String thsms = request.getParameter("thsms");
    	String kdkmk = request.getParameter("kdkmk");
    	String nakmk = request.getParameter("nakmk");
    	String nlakh = request.getParameter("nlakh");
    	String bobot = request.getParameter("bobot");
    	String sksmk = request.getParameter("sksmk");
    	String krs_pindahan = request.getParameter("krs_pindahan");
    	UpdateDb udb = new UpdateDb();
    	if(krs_pindahan.equalsIgnoreCase("krs_pindahan")) {
    		udb.deleteRecordTrnlp(kdkmk,kdpst,npm);
    	}
    	else {
    		udb.deleteRecordTrnlm(thsms,kdkmk,kdpst,npm);	
    	}
    	
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
