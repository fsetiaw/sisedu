package servlets.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

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

import beans.login.InitSessionUsr;
import beans.login.Verificator;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.*;
import beans.folder.FolderManagement;
/**
 * Servlet implementation class HistoryKrsKhs
 */
@WebServlet("/HistoryKrsKhs")
public class PrepKonversiKrsKhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public HistoryKrsKhs() {
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
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    */
	  }
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * based dari viewKrsKhs jadi makilum kalo banyak unnecessary steps
		 */
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//dibawah ini adalah info target bukan operator
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String cmd =  request.getParameter("cmd");
		SearchDb sdb = new SearchDb();
		int norut = isu.isAllowTo("requestKonversiMakul");
		if(norut>0) {
			String idkur = sdb.getIndividualKurikulum(kdpst, npm);
			if(idkur!=null && !Checker.isStringNullOrEmpty(idkur)) {
				Vector v = sdb.getMatakuliahYgBisaDiSetarakan(kdpst, npm);
				ListIterator li = v.listIterator();
				//while(li.hasNext()) {
				//	String baris = (String)li.next();
				//	System.out.println(baris);
				//}
				request.setAttribute("v", v);
				
				String target = Constants.getRootWeb()+"/InnerFrame/KonversiKrsKhs.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				//request.getRequestDispatcher(url+"?msg=Harap Tentukan Kurikulum Untuk "+nmm+" Terlebih Dahulu").forward(request,response);
				request.getRequestDispatcher(url_ff).forward(request,response);
			}
			else {
				String target = Constants.getRootWeb()+"/ErrorPage/NoKurikulum.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url_ff).forward(request,response);
			}
		}
		else {
			String target = Constants.getRootWeb()+"/ErrorPage/NoDataOrRight.jsp";
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
