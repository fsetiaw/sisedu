package servlets.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import java.util.ListIterator;

import beans.folder.FolderManagement;
import beans.folder.file.FileManagement;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.keu.*;

/**
 * Servlet implementation class DataCivitas
 */
@WebServlet("/DataCivitas")
public class HistoryBakCivitas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public HistoryBakCivitas() {
//        super();
        // TODO Auto-generated constructor stub
//    }
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		/*
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	      e.printStackTrace();
	    }
	    */
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			//response.setContentType("text/html");
		    //PrintWriter out = response.getWriter();
		    //HttpSession session = request.getSession(true);
			//InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
			String id_obj = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String atMenu = request.getParameter("atMenu");
			String npm = request.getParameter("npm");
			String kdpst = request.getParameter("kdpst");
			String obj_lvl =  request.getParameter("obj_lvl");
			String form_type = request.getParameter("form_type");
			if(form_type !=null && form_type.equalsIgnoreCase("preview")) {
				String target = Constants.getRootWeb()+"/InnerFrame/Preview/payment.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url_ff).forward(request,response);
			}
			else {
				int norut = isu.isAllowTo("vbak");
				if(norut>0) {
					//System.out.println("valueObj Level/npm = "+obj_lvl+","+npm);
					Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"vbak",kdpst);
					if(v==null||v.size()==0) {
						//System.out.println("vnull");
						String target = Constants.getRootWeb()+"/ErrorPage/authorization.jsp";
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url_ff).forward(request,response);
					}
					else {
						//update ada di 2 tmp = ProfileCivitas.java && HistoryBakCivitas.java
						//System.out.println("v not null");
						ListIterator li = v.listIterator();
						Vector v_profile = (Vector)li.next();
						Vector v_hist_bak = (Vector)li.next();
						Vector v_nuHistBak=null;
						if(li.hasNext()) {
							//System.out.println("v_nuHistBak ada");
							v_nuHistBak = (Vector)li.next();
						}
						li = v_profile.listIterator();
						String v_obj_lvl=(String)li.next();
						request.setAttribute("v_obj_lvl", v_obj_lvl);
						String v_id_kotaku=(String)li.next();
						request.setAttribute("v_id_kotaku", v_id_kotaku);
						String v_id_obj=(String)li.next();
						request.setAttribute("v_id_obj", v_id_obj);
						String v_kdpti=(String)li.next();
						request.setAttribute("v_kdpti", v_kdpti);
						String v_kdjen=(String)li.next();
						request.setAttribute("v_kdjen", v_kdjen);
						String v_kdpst=(String)li.next();
						request.setAttribute("v_kdpst", v_kdpst);
						String v_npmhs=(String)li.next();
						request.setAttribute("v_npmhs", v_npmhs);
						String v_nimhs=(String)li.next();
						request.setAttribute("v_nimhs", v_nimhs);
						String v_nmmhs=(String)li.next();
						request.setAttribute("v_nmmhs", v_nmmhs);
						String v_shift=(String)li.next();
						request.setAttribute("v_shift", v_shift);
						String v_tplhr=(String)li.next();
						request.setAttribute("v_tplhr", v_tplhr);
						String v_tglhr=(String)li.next();
						request.setAttribute("v_tglhr", v_tglhr);
						String v_kdjek=(String)li.next();
						request.setAttribute("v_kdjek", v_kdjek);
						String v_tahun=(String)li.next();
						request.setAttribute("v_tahun", v_tahun);
						String v_smawl=(String)li.next();
						request.setAttribute("v_smawl", v_smawl);
						String v_btstu=(String)li.next();
						request.setAttribute("v_btstu", v_btstu);
						String v_assma=(String)li.next();
						request.setAttribute("v_assma", v_assma);
						String v_tgmsk=(String)li.next();
						request.setAttribute("v_tgmsk", v_tgmsk);
						String v_tglls=(String)li.next();
						request.setAttribute("v_tglls", v_tglls);
						String v_stmhs=(String)li.next();
						request.setAttribute("v_stmhs", v_stmhs);
						String v_stpid=(String)li.next();
						request.setAttribute("v_stpid", v_stpid);
						String v_sksdi=(String)li.next();
						request.setAttribute("v_sksdi", v_sksdi);
						String v_asnim=(String)li.next();
						request.setAttribute("v_asnim", v_asnim);
						String v_aspti=(String)li.next();
						request.setAttribute("v_aspti", v_aspti);
						String v_asjen=(String)li.next();
						request.setAttribute("v_asjen", v_asjen);
						String v_aspst=(String)li.next();
						request.setAttribute("v_aspst", v_aspst);
						String v_bistu=(String)li.next();
						request.setAttribute("v_bistu", v_bistu);
						String v_peksb=(String)li.next();
						request.setAttribute("v_peksb", v_peksb);
						String v_nmpek=(String)li.next();
						request.setAttribute("v_nmpek", v_nmpek);
						String v_ptpek=(String)li.next();
						request.setAttribute("v_ptpek", v_ptpek);
						String v_pspek=(String)li.next();
						request.setAttribute("v_pspek", v_pspek);
						String v_noprm=(String)li.next();
						request.setAttribute("v_noprm", v_noprm);
						String v_nokp1=(String)li.next();
						request.setAttribute("v_nokp1", v_nokp1);
						String v_nokp2=(String)li.next();
						request.setAttribute("v_nokp2", v_nokp2);
						String v_nokp3=(String)li.next();
						request.setAttribute("v_nokp3", v_nokp3);
						String v_nokp4=(String)li.next();
						request.setAttribute("v_nokp4", v_nokp4);
						String v_sttus=(String)li.next();
						request.setAttribute("v_sttus", v_sttus);
						String v_email=(String)li.next();
						request.setAttribute("v_email", v_email);
						String v_nohpe=(String)li.next();
						request.setAttribute("v_nohpe", v_nohpe);
		   				String v_almrm=(String)li.next();
						request.setAttribute("v_almrm", v_almrm);
						String v_kotrm=(String)li.next();
						request.setAttribute("v_kotrm", v_kotrm);
		   				String v_posrm=(String)li.next();
						request.setAttribute("v_posrm", v_posrm);
		   				String v_telrm=(String)li.next();
						request.setAttribute("v_telrm", v_telrm);
		   				String v_almkt=(String)li.next();
						request.setAttribute("v_almkt", v_almkt);
						String v_kotkt=(String)li.next();
						request.setAttribute("v_kotkt", v_kotkt);
		   				String v_poskt=(String)li.next();
						request.setAttribute("v_poskt", v_poskt);
		   				String v_telkt=(String)li.next();
						request.setAttribute("v_telkt", v_telkt);
		   				String v_jbtkt=(String)li.next();
						request.setAttribute("v_jbtkt", v_jbtkt);
		   				String v_bidkt=(String)li.next();
						request.setAttribute("v_bidkt", v_bidkt);
		   				String v_jenkt=(String)li.next();
						request.setAttribute("v_jenkt", v_jenkt);
		   				String v_nmmsp=(String)li.next();
						request.setAttribute("v_nmmsp", v_nmmsp);
		   				String v_almsp=(String)li.next();
						request.setAttribute("v_almsp", v_almsp);
		   				String v_possp=(String)li.next();
						request.setAttribute("v_possp", v_possp);
						String v_kotsp=(String)li.next();
						request.setAttribute("v_kotsp", v_kotsp);
						String v_negsp=(String)li.next();
						request.setAttribute("v_negsp", v_negsp);
		   				String v_telsp=(String)li.next();
						request.setAttribute("v_telsp", v_telsp);
						String v_neglh=(String)li.next();
						request.setAttribute("v_neglh", v_neglh);
						String v_agama=(String)li.next();
						request.setAttribute("v_neglh", v_agama);
						
						//utk v_history bak ngga bisa pake sistem setattribute soalnya di jspnya pake do.loop ???? apaan nnih
						
						//request.setAttribute("v_bak", v);
						request.setAttribute("v_profile",v_profile);
						request.setAttribute("v_bak",v_hist_bak);
						if(v_nuHistBak!=null && v_nuHistBak.size()>0) {
							//System.out.println("set attr v_nuHistBak");
							request.setAttribute("v_NuBak",v_nuHistBak);
						}	
						//updated get buktis setoran
						SearchDbKeu sdb = new SearchDbKeu(isu.getNpm());
						Vector vPymntRequest =  sdb.getInfoKeuFromPymntTransit(v_kdpst,v_npmhs);
						request.setAttribute("vPymntRequest",vPymntRequest);
						String target = Constants.getRootWeb()+"/InnerFrame/HistPymnt.jsp";
						//update baru pemecahan menu 'data keuangan'
						if(atMenu!=null && atMenu.equalsIgnoreCase("riwayatBayaran")) {
							
							target = Constants.getRootWeb()+"/InnerFrame/DashPymnt.jsp";
						}
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
						//request.getRequestDispatcher(getServletContext()+"/InnerFrame/HistPymnt.jsp").forward(request,response);
						
						
						request.getRequestDispatcher(url_ff+"?cmd=payment").forward(request,response);
					}
				}
				else {
					String target = Constants.getRootWeb()+"/ErrorPage/authorization.jsp";
					String uri = request.getRequestURI();
					String url_ff = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url_ff).forward(request,response);
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
