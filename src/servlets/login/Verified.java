package servlets.login;
import beans.*;
import beans.dbase.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;
import beans.login.Verificator;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.PathFinder;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Servlet implementation class Verified
 */
@WebServlet("/Verified")
//public class Verified extends ConnectorDb {
public class Verified extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
 //   public Verified() {
 //       super();
        // TODO Auto-generated constructor stub
 //   }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		//envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	    	e.printStackTrace();
	    }
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stuba
		UpdateDb udb = new UpdateDb();
		InitSessionUsr isu = null;
		HttpSession session = request.getSession(true);
		String usrname = request.getParameter("usr");
		String usrpwd = request.getParameter("pwd");
		//System.out.println("usr/pwd="+usrname+"/"+usrpwd);
		udb.insertLogMe(-1, "", "login", "usr/pwd ="+usrname+"/"+usrpwd+" mencoba untuk login",request.getRemoteAddr());
		session.removeAttribute("validUsr");
		String info=null;
		try {
			con = ds.getConnection();
			Verificator ve=new Verificator(con);
			String status_maintenance = ve.verifiedIsServerUnderMaintenanceAndIsUsrAllow(usrname, usrpwd);
			if(status_maintenance.equalsIgnoreCase("maintenance")) {
				//forward to maintenance pahe
				String target = Constants.getRootWeb()+"/ErrorPage/underMaintenance.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url_ff+"?backTo=www.satyagama.ac.id&errMsg=MOHON MAAF, SERVER DALAM PERAWATAN").forward(request,response);
			}
			else {
				info = ve.verifiedUsrPwd(usrname, usrpwd);
				if(info!=null) {
					StringTokenizer st = new StringTokenizer(info);
					String id = st.nextToken();
					String schema = st.nextToken();
					String force_redirect = st.nextToken();
					
					if((isu = ve.connectToSchema_v1(schema,id))!=null) {
						SearchDb sdb = new SearchDb();
						String isuKdpst=isu.getKdpst();
						String isuNpmhs=isu.getNpm();
						String isuKdjek=isu.getKdjek();
						String isuObjNickname = isu.getObjNickNameGivenObjId();
						String ttlog_tmlog = sdb.getInfoLoginHistory(isuKdpst,isuNpmhs);
						st = new StringTokenizer(ttlog_tmlog,",");
						int ttlog = Integer.valueOf(st.nextToken()).intValue();
						int tmlog = Integer.valueOf(st.nextToken()).intValue();
						String thsms_now = Checker.getThsmsNow();
						/*
						 * kenapa menggunakan thsms now bukan thsm_heregistrasi
						 * karena disini kita untuk menentukan akses ke sistem pada thsms_now
						 * thsms_her unutk menentukan daftar ulang for upcomming thsms
						 */
						
						String psn = Checker.sudahDaftarUlang(isuKdpst, isuNpmhs, thsms_now); //fungsi ini juga update stmhsmhmhs
						String curr_stmhsmsmhs = AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(isuKdpst, isuNpmhs);
						session.setAttribute("curr_stmhsmsmhs", curr_stmhsmsmhs);
						//System.out.println("curr_stmhs1="+curr_stmhsmsmhs);
						if(psn==null) {
							session.setAttribute("sdu", thsms_now);
							
						}
						ttlog = ttlog+1;
						udb.updateLogHistory(isuKdpst,isuNpmhs,ttlog,tmlog);
						udb.updateMyCurPa(isuKdpst, isuNpmhs);
						session.setAttribute("validUsr",isu);
						session.setAttribute("ObjGenderAndNickname",isuKdjek+"__"+isuObjNickname);
						session.setAttribute("nmpw", usrname+"||"+usrpwd);
						if(force_redirect!=null && !Checker.isStringNullOrEmpty(force_redirect)) {
							response.sendRedirect(force_redirect);
						}
						else {
							String target = Constants.getRootWeb()+"/index.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							//response.sendRedirect("index.jsp");
							response.sendRedirect(url);
						}	
					}
					else {
						//response.sendRedirect("login.html");
						response.sendRedirect("http://www.satyagama.ac.id");
					
					}
				}
				else {
					//response.sendRedirect("login.html");
					response.sendRedirect("http://www.satyagama.ac.id");
				}
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
        	if (con!=null) {
        		try {
        			con.close();
        		}
        		catch (Exception ignore) {
            		//System.out.println(ignore);
        		}
        	}
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		// TODO Auto-generated method stub
	}

}
