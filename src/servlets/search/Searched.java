package servlets.search;
import beans.dbase.*;
import beans.dbase.Beasiswa.SearchDbBeasiswa;
import beans.login.*;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

import java.util.ListIterator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.ResultSet;
/**
 * Servlet implementation class searched
 */
@WebServlet("/searched")
public class Searched extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public Searched() {
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
			response.setContentType("text/html");
		    //PrintWriter out = response.getWriter();
			//HttpSession session = request.getSession(true);
			//InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
			
			if(isu==null) {
				String target = Constants.getRootWeb()+"/index.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(Constants.getRootWeb()).forward(request,response);
			}
			String kword = request.getParameter("kword");
			if(kword==null ||(kword!=null&&Checker.isStringNullOrEmpty(kword))) {
				kword="";
			}
			else {
				//kword = kword.replaceAll("\\*","%");
				if(kword.startsWith(Constants.getKuiSearchChar())) {
					/*
					 * search kui
					 */
					Vector vRecKui = null;
					Vector vScope = isu.getScope("searchKui");
					
					if(vScope!=null && vScope.size()>0) {
						SearchDb sdb = new SearchDb();
						try {
							//StringTokenizer st = new StringTokenizer(kword,"-");
							//st.nextToken();//replace kuiSearchChar
							//kword = st.nextToken();
							//System.out.println("search kuitansi");
							StringTokenizer st = new StringTokenizer(kword,"-");
					    	st.nextToken();//removing kui- part
					    	kword=st.nextToken();
					    	//System.out.println(kword);
							int i = Integer.valueOf(kword).intValue();
							vRecKui=sdb.searchKui(vScope,kword);
							//System.out.println(vRecKui.size()+"");
							request.setAttribute("v_kui_search_result", vRecKui);
							//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
							String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff).forward(request,response);
						}
						catch(Exception e) {
							//System.out.println("error search kui ="+e);
							Vector v = null;
							request.setAttribute("v_search_result", v);
							//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
							String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff).forward(request,response);
						}
					}
					else {
						/* no akses search,
						 * 
						 */
						Vector v = new Vector();
						request.setAttribute("v_search_result", v);
						//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
						String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url_ff).forward(request,response);
					}
				}
				else {
					/*
					 * search civitas
					 */
					//pilihan menggunakan - prodi
					//bila pencarian kontain '-' format nama-prodi berarti pencarian berdasar prodi
					//itupun bila usr punya wewenang terhadap prodi tertulis
					//System.out.println("getObjNickNameGivenObjId="+isu.getObjNickNameGivenObjId());
					if(!isu.getObjNickNameGivenObjId().contains("MHS")) {
						if(kword.contains("-")) {
							//System.out.println("pencarian berdasar prodi");
							StringTokenizer st = new StringTokenizer(kword,"-");
							
							kword = "%"+st.nextToken()+"%";
							int norut =  isu.isAllowTo("s");
							
							Vector v = isu.searchCivitasProdiBased(norut,kword,st.nextToken());
							SearchDbBeasiswa  sdb = new SearchDbBeasiswa(); 
							Vector vHistBea = sdb.getLastBeasiswa(v);
							request.setAttribute("v_search_result", v);
							request.setAttribute("vHistBea", vHistBea);
							//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
							String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff).forward(request,response);
						}
						else {
							//System.out.println("pencarian berdasar prodi reguler");
							kword = "%"+kword+"%";
							int norut =  isu.isAllowTo("s");
							Vector v = isu.searchCivitas(norut, kword);
							SearchDbBeasiswa  sdb = new SearchDbBeasiswa(); 
							Vector vHistBea = sdb.getLastBeasiswa(v);
							request.setAttribute("v_search_result", v);
							request.setAttribute("vHistBea", vHistBea);
							//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
							String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff).forward(request,response);
						}	
					}
					else {
						//reserved untuk search bagi usr MAHASISWA
					}
						
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
