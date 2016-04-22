package servlets.view;
import beans.dbase.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;
import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.setting.*;
import beans.tools.PathFinder;
/**
 * Servlet implementation class ListKuitansi
 */
@WebServlet("/ListKuitansi")
public class ListKuitansi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public ListKuitansi() {
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
		SearchDb sdb = null;
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		String filterTgl = request.getParameter("filterTgl");
		boolean hasPrev = false;
		boolean hasNext = false;
		if(Integer.valueOf(x).intValue()>0) {
			hasPrev=true;
		}
		
		/*
		 * cek tipe hak akses tipe list menu
		 */
		Vector vList=new Vector();
		//Vector vGen = isu.getScope("showListKuiAll");
		Vector vGen1 = isu.getScopeObjIdProdiOnly("showListKuiAll");
		if(vGen1==null) {
			/*
			 * cek othe type akses lit menu lainnya bila diperlukan
			 * contoh:
			 * Vector vGen = isu.getScope("showListKuiDayly");
			 */
		}
		else {
			//Vector vGen1 = isu.getScopeObjIdProdiOnly("showListKuiAll");
			//ListIterator lir = vGen1.listIterator();
			//while(lir.hasNext()) {
			//	System.out.println("vGen1="+(String)lir.next());
			//}
			
			if(vGen1.size()>0) {
				String thn = request.getParameter("tahun");
				System.out.println("filterTgl = "+filterTgl);
				String bulan = request.getParameter("bulan");
				System.out.println("bulan = "+bulan);
				sdb = new SearchDb();
				if(thn==null && bulan==null) {
					System.out.println("kesinin");
					//vList = sdb.getListKuitansi(vGen, "currentYear",Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
					vList = sdb.getListKuitansiBasedOnIdObj(vGen1, "currentYear",Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
					if(vList.size()>Integer.valueOf(y).intValue()-1) {
						hasNext = true;
					}
					//System.out.println("vList="+vList.size());
				}
				else {
					if(thn!=null && bulan==null) {
						try {
							int i = Integer.valueOf(thn).intValue();
							//vList = sdb.getListKuitansi(vGen,thn,"01",Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
							vList = sdb.getListKuitansiBasedOnIdObj(vGen1,thn,"01",Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
							if(vList.size()>Integer.valueOf(y).intValue()-1) {
								hasNext = true;
							}
						}
						catch (Exception e) {
							//vList = sdb.getListKuitansi(vGen, "currentYear",Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
							vList = sdb.getListKuitansiBasedOnIdObj(vGen1, "currentYear",Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
							if(vList.size()>Integer.valueOf(y).intValue()-1) {
								hasNext = true;
							}
						}
						
					}	
					else {
						if(thn!=null && bulan!=null) {
							try {
								int i = Integer.valueOf(thn).intValue();
								i = Integer.valueOf(bulan).intValue();
								//vList = sdb.getListKuitansi(vGen,thn,bulan,Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
								vList = sdb.getListKuitansiBasedOnIdObj(vGen1,thn,bulan,Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
								if(vList.size()>Integer.valueOf(y).intValue()-1) {
									hasNext = true;
								}
							}
							catch (Exception e) {
								//vList = sdb.getListKuitansi(vGen, "currentYear",Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
								vList = sdb.getListKuitansiBasedOnIdObj(vGen1, "currentYear",Integer.valueOf(x).intValue(),Integer.valueOf(y).intValue(),filterTgl);
								if(vList.size()>Integer.valueOf(y).intValue()-1) {
									hasNext = true;
								}
							}
						}
					}
				}
				request.setAttribute("vListKui", vList);
				String target = Constants.getRootWeb()+"/InnerFrame/Summary/Keu/listKuitansi.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url_ff+"?hasPrev="+hasPrev+"&hasNext="+hasNext).forward(request,response);
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
