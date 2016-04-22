package servlets.Overview;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class PrepInfoReqBukaKelas
 */
@WebServlet("/PrepInfoReqBukaKelas")
public class PrepInfoReqBukaKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepInfoReqBukaKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			System.out.println("prep me");;
			SearchDb sdb = new SearchDb(isu.getNpm());
			/*
			 * ========================BukaKelas=============================
			 */
			
			//=====get kdpst yg belum mengajukan bukakelas=====
			String kdjenKdpstNmpstNoPengajuan = sdb.getProdiYgBlmMengajukanBukaKelas();
			//=====end get kdpst yg belum mengajukan bukakelas=====
			
			Vector vBukaKelas = sdb.getRelatedInfoRequestBukaKelas(isu);
			String listKdpst = "";
			if(vBukaKelas!=null) {
				//get list kdpst
				
				ListIterator li = vBukaKelas.listIterator();
				Vector vTmp = new Vector();
				//System.out.println("0.vBukaKelas ="+ vBukaKelas.size());
				ListIterator liTmp = vTmp.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("bars@notification="+brs);
					StringTokenizer st = new StringTokenizer(brs,"||");
					st.nextToken(); //cmd
					st.nextToken(); //idkur
					st.nextToken(); //idkmk
					st.nextToken(); //thsmsPmb
					String kdpst = st.nextToken();
					liTmp.add(kdpst);
					//System.out.println("* "+brs);
				}
				try {
					vTmp=Tool.removeDuplicateFromVector(vTmp);
					liTmp = vTmp.listIterator();
					boolean first = true;
					while(liTmp.hasNext()) {
						if(first) {
							first = false;
							listKdpst = ""+(String)liTmp.next();
						}
						else {
							listKdpst=listKdpst+(String)liTmp.next();
						}
						if(liTmp.hasNext()) {
							listKdpst=listKdpst+",";
						}
					}
					//System.out.println("listKdpst@notification="+listKdpst);
				}
				catch(Exception e) {
					System.out.println(e);
				}
				//SearchDb sdb = new SearchDb(isu.getNpm());
				String[]aksesInfo = sdb.getObjectAccessLevel();
		    	if(aksesInfo[1].contains("BukaKelas")) {
		    		request.setAttribute("hasBukaKelasCmd", "yes");
		    	}
				session.setAttribute("vBukaKelas", vBukaKelas);
				request.setAttribute("listKdpstBukaKelas", listKdpst);
				request.setAttribute("kdjenKdpstNmpstNoPengajuan", kdjenKdpstNmpstNoPengajuan);
			}
			/*
			 * ========================END BukaKelas=============================
			 */
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher("process.statusRequestBukaKelas?listKdpstBk="+listKdpst+"&infoKdpstNoPengajuan="+kdjenKdpstNmpstNoPengajuan).forward(request,response);

		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
