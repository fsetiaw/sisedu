package servlets.view;

import java.io.IOException;
import java.io.PrintWriter;
import beans.folder.FolderManagement;
import beans.folder.file.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.util.Vector;
import java.util.ListIterator;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class DataCivitas
 */
@WebServlet("/DataCivitas")
public class GeneralInnerMenuMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public ProfileCivitas() {
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
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String cmd =  request.getParameter("cmd");
		//System.out.println("profileCivitas "+objId+" "+npm+" "+nmm+" "+obj_lvl+" isu npm = "+isu.getNpm());
		int norut = isu.isAllowTo("vop");
		//System.out.println("obj_lvl ="+obj_lvl);
		
		//System.out.println("norut ="+norut);
		
		if(norut>0) {
			FolderManagement folder = new FolderManagement(isu.getDbSchema(),kdpst,npm);
			folder.cekAndCreateFolderIfNotExist();
			Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"vop",kdpst);
			//System.out.println("v1-size="+v.size());
			if(v.size()==0) {
				//System.out.println("masuk no vop");
				String target = Constants.getRootWeb()+"/ErrorPage/NoData//System.outp";
				//System.out.println(target);
				String uri = request.getRequestURI();
				//System.out.println(uri);
				String url_ff = PathFinder.getPath(uri, target);
				//System.out.println(url_ff);
				
				request.getRequestDispatcher(url_ff).forward(request,response);
			}
			else {
				//System.out.println("masuk vop");
				//update ada di 2 tmp = ProfileCivitas.java && HistoryBakCivitas.java
				ListIterator li = v.listIterator();
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
				request.setAttribute("v_agama", v_agama);
				
				request.setAttribute("v_profile", v);
				request.setAttribute("atr_name", "atr_val");
				String target="";
				if(cmd!=null && cmd.equalsIgnoreCase("edit")) {
					target = Constants.getRootWeb()+"/InnerFrame/Edit/editProfile.jsp";
					String uri = request.getRequestURI();
					String url_ff = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url_ff+"?targetPage=editProfile").forward(request,response);
				}
				else {
					if(cmd!=null && cmd.equalsIgnoreCase("dashboard")) {
						target = Constants.getRootWeb()+"/InnerFrame/dashHomeMhs.jsp";
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url_ff+"?targetPage=dashHomePage").forward(request,response);
					}
					else {
						if(cmd!=null && (cmd.equalsIgnoreCase("viewProfile") || cmd.equalsIgnoreCase("updated"))) {
							target = Constants.getRootWeb()+"/InnerFrame/profile.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff+"?targetPage=profile").forward(request,response);
						}		
					}
				}
				
				//String uri = request.getRequestURI();
				//String url_ff = PathFinder.getPath(uri, target);
				
				//request.getRequestDispatcher(url_ff).forward(request,response);
				//request.getRequestDispatcher("profile.jsp").forward(request,response);
			}
		}
		else {
			String target = Constants.getRootWeb()+"/ErrorPage/authorization.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff).forward(request,response);
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
