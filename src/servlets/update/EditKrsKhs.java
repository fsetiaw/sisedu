package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.dbase.UpdateDb;

/**
 * Servlet implementation class EditKrsKhs
 */
@WebServlet("/EditKrsKhs")
public class EditKrsKhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditKrsKhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String thsms = request.getParameter("thsms");
		//System.out.println("thsms edit krs = "+thsms);
    	String kdkmk = request.getParameter("kdkmk");
    	String nakmk = request.getParameter("nakmk");
    	String nlakh = request.getParameter("nlakh");
    	String bobot = request.getParameter("bobot");
    	String sksmk = request.getParameter("sksmk");
    	String cmd =  request.getParameter("cmd");
    	String krs_pindahan =  request.getParameter("krs_pindahan");
    	//bobot = getBobotGivenNlakh(String thsms,String kdpst,String nlakh)
    	UpdateDb udb = new UpdateDb();
    	if(krs_pindahan.equalsIgnoreCase("null")) {
    		//udb.updateMataKuliahKrsKhs(thsms,kdpst,npm,kdkmk,nlakh,bobot);
    		udb.updateMataKuliahKrsKhs(thsms,kdpst,npm,kdkmk,nlakh);
    		//System.out.println("krs_notmal");
    	}
    	else {
    		if(krs_pindahan.equalsIgnoreCase("krs_pindahan")) {
    			//System.out.println("krs_pindahan");
    			udb.updateMataKuliahKrsKhs(thsms,kdpst,npm,kdkmk,nlakh,bobot,krs_pindahan);
    			//udb.updateMataKuliahKrsKhs(thsms,kdpst,npm,kdkmk,nlakh,krs_pindahan);
    		}
    	}
		request.getRequestDispatcher("get.histKrs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
