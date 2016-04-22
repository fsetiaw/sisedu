package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.tools.Checker;
import java.util.StringTokenizer;
/**
 * Servlet implementation class ValidasiFormBukaKelasTahap2
 */
@WebServlet("/ValidasiFormBukaKelasTahap2")
public class ValidasiFormBukaKelasTahap2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 String msg = "";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidasiFormBukaKelasTahap2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("tahap21");
		
		HttpSession session = request.getSession(true);
		String infoKur=request.getParameter("infoKur");
		infoKur=infoKur.replace("#&", "||");
		String kdpst_nmpst=request.getParameter("kdpst_nmpst");
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		String kdpst = st.nextToken();
		String nmpst = st.nextToken();
		String kdjen=request.getParameter("kdjen");
		String kelasTambahan=request.getParameter("kelasTambahan");
		String kodeKampus=request.getParameter("kodeKampus");
		String [] klsInfo = (String[])session.getAttribute("klsInfo");
		String [] totKls = (String[])session.getAttribute("totKls");
		String[] infoKelasDosen = request.getParameterValues("infoKelasDosen");
		String[] infoKelasMhs = request.getParameterValues("infoKelasMhs");
		boolean missing_dosen = false;
		boolean totMhsZero = false;
		//for(int i=0;i<infoKelasDosen.length && !missing_dosen && !totMhsZero;i++) {
		for(int i=0;i<infoKelasDosen.length ;i++) {
			//System.out.println(infoKelasDosen[i]);
			if(infoKelasDosen[i].contains("tba")) {	
				missing_dosen = true;
			}
			
			if(Checker.isStringNullOrEmpty(infoKelasMhs[i])) {
				infoKelasMhs[i]="0";
			}	
			if(Integer.valueOf(infoKelasMhs[i]).intValue()<1) {
				totMhsZero=true;
			}
		}
		if(missing_dosen && !totMhsZero) {
			
			//goback
			//request.getRequestDispatcher("formPengajuanBukaKelasTahap2.jsp?infoKur="+infoKur+"&kdpst_nmpst="+kdpst_nmpst+"&kdjen="+kdjen+"&errmsg=* harap mengisi seluruh dosen pengajar").forward(request,response);
			msg = "Dosen Pengajar Belum Lengkap.";
			//response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		    //response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		    //response.getWriter().write(msg); 
		    //msg="";
		}
		else if(!missing_dosen && totMhsZero) {
			
				//goback
				//request.getRequestDispatcher("formPengajuanBukaKelasTahap2.jsp?infoKur="+infoKur+"&kdpst_nmpst="+kdpst_nmpst+"&kdjen="+kdjen+"&errmsg=* Harapa diisi perkiraan jumlah mahasiswa").forward(request,response);
			msg = "Perkiraan Jumlah Mahasiswa ada yg 0 mhs.";
			//response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		    //response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		    //response.getWriter().write(msg); 
		    //msg="";
		}
		else if(missing_dosen && totMhsZero) {
			msg = "Dosen Pengajar Belum Lengkap & Perkiraan Jumlah Mahasiswa ada yg 0 mhs.";
			//response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		    //response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		    //response.getWriter().write(msg); 
		    //msg="";	
					//goback
					//request.getRequestDispatcher("formPengajuanBukaKelasTahap2.jsp?infoKur="+infoKur+"&kdpst_nmpst="+kdpst_nmpst+"&kdjen="+kdjen+"&errmsg=* harap mengisi seluruh dosen pengajar dan perkiraan jumlah mahasiswa").forward(request,response);
		}
		else {

			session.removeAttribute("klsInfo");
			session.removeAttribute("totKls");
			/* 
			 for(int i=0;i<infoKelasDosen.length;i++) {
			 	String tmp  = infoKelasDosen[i];
				System.out.println(tmp);
			
			}
			*/
		
			
			//cek apa ada keterKelas tambahan
			infoKelasDosen = Checker.getOptKeterMakul(infoKelasDosen,infoKelasMhs, kdpst);
			boolean yesketer = false;
			for(int i=0;i<infoKelasDosen.length&&!yesketer;i++) {
				String tmp  = infoKelasDosen[i];
						//System.out.println("+"+infoKelasDosen[i]);
				if(tmp.contains("yesketer")) {
					yesketer=true;
				}
			//	else {
			//add keter infoKelasDosen tambah ||noketer||null shda dilakukan di getOptKeterMakul
			//	infoKelasDosen[i]=infoKelasDosen[i]+"||noketer||null||"+infoKelasMhs[i];
			//}
			}
			session.setAttribute("kelasTambahan",kelasTambahan);
			session.setAttribute("kodeKampus",kodeKampus);
			session.setAttribute("infoKelasDosen",infoKelasDosen);
			session.setAttribute("infoKelasMhs",infoKelasMhs);
			if(yesketer) {
				//System.out.println("yesketeroooo");
				//request.setAttribute("infoKelasDosen",infoKelasDosen);
				//request.getRequestDispatcher("formPengajuanBukaKelasTahapOpt1.jsp?infoKur="+infoKur+"&kdpst_nmpst="+kdpst_nmpst+"&kdjen="+kdjen+"&errmsg=-").forward(request,response);
				//if(valid) {
				msg = "<meta http-equiv=\"refresh\" content=\"0; url=formPengajuanBukaKelasTahapOpt1.jsp?infoKur="+infoKur+"&kdpst_nmpst="+kdpst_nmpst+"&kdjen="+kdjen+"&errmsg=-\" >";
			   
				//response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
			    //response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
			    //response.getWriter().write(msg); 
			    //msg="";					//}
			}
			else {
				//System.out.println("noketer");
				session.setAttribute("infoKelasDosen", infoKelasDosen);
				//request.getRequestDispatcher("process.updClassPoolTable?infoKur="+infoKur+"&kdpst_nmpst="+kdpst_nmpst+"&kdjen="+kdjen+"&cmd=noketer").forward(request,response);
				msg = "<meta http-equiv=\"refresh\" content=\"0; url=process.updClassPoolTable?infoKur="+infoKur+"&kdpst_nmpst="+kdpst_nmpst+"&kdjen="+kdjen+"&cmd=noketer\" >";
			}
			//request.getRequestDispatcher("formPengajuanBukaKelasTahap3.jsp?infoKur="+infoKur+"&kdpst_nmpst="+kdpst_nmpst+"&kdjen="+kdjen+"&errmsg=* harap diisi seluruh dosen pengajar").forward(request,response);	
		}	
		doGet(request,response);	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request,response);
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
	    response.getWriter().write(msg); 
	    msg="";
	}

}
