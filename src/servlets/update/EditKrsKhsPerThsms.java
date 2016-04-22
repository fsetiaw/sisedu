package servlets.update;

import java.io.IOException;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.dbase.UpdateDb;
import beans.tools.Checker;

/**
 * Servlet implementation class EditKrsKhs
 */
@WebServlet("/EditKrsKhs")
public class EditKrsKhsPerThsms extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditKrsKhsPerThsms() {
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
		String[]kdkmk = request.getParameterValues("kdkmk");
		String[]nlakh = request.getParameterValues("nlakh");
		String[]shift = request.getParameterValues("shift");
		
		//System.out.println("thsms edit krs per thsms = "+thsms);
		//System.out.println("nlakh[] = "+nlakh);
		//if(nlakh!=null) {
		//	System.out.println("nlakh size= "+nlakh.length);
		//}
		//if(shift!=null) {
		//	System.out.println("shift size= "+shift.length);
		//}
		//if(kdkmk!=null) {
		//	System.out.println("kdkmk size= "+kdkmk.length);
		//}
		//for(int i=0;i<nlakh.length;i++) {
		//	System.out.println(thsms+","+nlakh[i]+","+nlakh[i]+","+shift[i]);
		//}
    	//String kdkmk = request.getParameter("kdkmk");
    	//String nakmk = request.getParameter("nakmk");
    	//String nlakh = request.getParameter("nlakh");
    	//String bobot = request.getParameter("bobot");
    	//String sksmk = request.getParameter("sksmk");
    	//String cmd =  request.getParameter("cmd");
    	String krs_pindahan =  request.getParameter("krs_pindahan");
		Vector v1 = new Vector();
		ListIterator li1 = v1.listIterator();
		if(kdkmk!=null&&nlakh!=null&&shift!=null) {
			for(int i=0;i<kdkmk.length;i++) {
				if(!Checker.isStringNullOrEmpty(kdkmk[i])&&!Checker.isStringNullOrEmpty(nlakh[i])&&!Checker.isStringNullOrEmpty(shift[i])){
					li1.add(kdkmk[i]+"#&"+nlakh[i]+"#&"+shift[i]);
					
				}
			}
			UpdateDb udb = new UpdateDb();
			udb.updateMataKuliahKrsKhsPerThsms(thsms,kdpst,npm,v1);
		}
		//request.getRequestDispatcher("get.histKrs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs").forward(request,response);
		request.getRequestDispatcher("get.histKrs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=editKrs").forward(request,response);
    	/*
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
		
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
