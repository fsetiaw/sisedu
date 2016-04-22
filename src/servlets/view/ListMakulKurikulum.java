package servlets.view;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;

import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.*;
/**
 * Servlet implementation class ListMakulKurikulum
 */
@WebServlet("/ListMakulKurikulum")
public class ListMakulKurikulum extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public ListMakulKurikulum() {
//        super();
        // TODO Auto-generated constructor stub
//    }
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
		// TODO Auto-generated method stub
	try {	
		//System.out.println("iam in list makur");
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		String idkur = ""+request.getParameter("idkur");
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		String kdpst = st.nextToken();
		String nmpst = "";
		String update = request.getParameter("update1");
		String[]kdkmkInclude = request.getParameterValues("kdkmkInclude");
		String[]makulKelulusan = request.getParameterValues("finalMk");
		String[]jenisMakul = request.getParameterValues("jenisMakul");
		//System.out.println("update = "+update);
		con = ds.getConnection();
		SearchDb sdb = new SearchDb(con);
		//bila update
		if(update!=null && update.equalsIgnoreCase("yes")) {
			/*
			 * delete prev record and add new to table makur
			 */
			
			
			//System.out.println("idkur = "+idkur);
			
			if(kdkmkInclude!=null && kdkmkInclude.length>0) {
				//System.out.println("kdkmkInclude = "+kdkmkInclude.length);
				//System.out.println("makulKelulusan = "+makulKelulusan.length);
		
				
				Vector vIdSmsMk = new Vector();
				ListIterator linfo = vIdSmsMk.listIterator();
				for(int i=0; i<kdkmkInclude.length;i++) {
					st = new StringTokenizer(kdkmkInclude[i],",");
					String idkmk = st.nextToken();
					String kdkmk = st.nextToken();
					String nakmk = st.nextToken();
					String skstm = st.nextToken();
					String skspr = st.nextToken();
					String skslp = st.nextToken();
					String kdwpl = st.nextToken();
					String jenis = st.nextToken();
					//String sms = ""+request.getParameter(kdkmk);
					String sms = ""+request.getParameter(kdkmk+"-"+idkmk);
					//System.out.println("ini = "+kdkmk+" "+nakmk+" "+sms);
					linfo.add(idkur);
					linfo.add(idkmk);
					linfo.add(sms);
					//linfo.add(kmk);
				}
				//System.out.println("1");
				UpdateDb udb = new UpdateDb();
				
				udb.updateMakur(vIdSmsMk);
				
				//update makulKelulusab
				if(makulKelulusan!=null && makulKelulusan.length>0) {
					//System.out.println("makulKelulusan ="+makulKelulusan.length);
					vIdSmsMk = new Vector();
					linfo = vIdSmsMk.listIterator();
					for(int i=0; i<makulKelulusan.length;i++) {
						st = new StringTokenizer(makulKelulusan[i],",");
						String idkmk = st.nextToken();
						String kdkmk = st.nextToken();
						String nakmk = st.nextToken();
						String skstm = st.nextToken();
						String skspr = st.nextToken();
						String skslp = st.nextToken();
						String kdwpl = st.nextToken();
						String jenis = st.nextToken();
						String sms = ""+request.getParameter(kdkmk);
						//System.out.println("ini = "+kdkmk+" "+nakmk+" "+sms);
						linfo.add(idkur);
						linfo.add(idkmk);
						linfo.add(sms);
						//linfo.add(kmk);
					}
				}
				else {
					System.out.println("makulKelulusan = true");
				}
				if(makulKelulusan!=null && makulKelulusan.length>0) {
					udb.updateMakurMkKelulusan(vIdSmsMk);
				}
				
				//proses ini harus yg terakhir karena sifatnya overide process diatas
				if(jenisMakul!=null && jenisMakul.length>0) {
					//<option value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=kode %>"
					udb.updateJenisMakul(jenisMakul);
					//System.out.println("jenisMakul null");
				}

				//System.out.println("3");
				//SearchDb sdb = new SearchDb(con);
				Vector vInfoMakur = sdb.getListInfotMakur(kdpst);
				request.setAttribute("vInfoMakur", vInfoMakur);
				String callerPage = request.getParameter("callerPage");
				String target = Constants.getRootWeb()+"/InnerFrame/Akademik/listMakur.jsp";
				String uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
				String url = PathFinder.getPath(uri, target);
				//System.out.println("ff_callerPahe="+callerPage);
				request.getRequestDispatcher(url+"?idkur=null&callerPage="+callerPage).forward(request,response);
			}
			else {
				/*
				 * tidak ada mk yg dicek pada cekbox (empty)
				 * - delete all record utk idkur terkait
				 */
				UpdateDb udb = new UpdateDb();
				udb.deleteFromMakur(idkur);
				//SearchDb sdb = new SearchDb(con);
				Vector vInfoMakur = sdb.getListInfotMakur(kdpst);
				request.setAttribute("vInfoMakur", vInfoMakur);
				String callerPage = request.getParameter("callerPage");
				String target = Constants.getRootWeb()+"/InnerFrame/Akademik/listMakur.jsp";
				String uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
				String url = PathFinder.getPath(uri, target);
				//System.out.println("ff_callerPahe="+callerPage);
				request.getRequestDispatcher(url+"?idkur=null&callerPage="+callerPage).forward(request,response);
			}
			
		}
		else {
			//bila bukan update 
			while(st.hasMoreTokens()) {
				nmpst = nmpst+st.nextToken();
				if(st.hasMoreTokens()) {
					nmpst = nmpst+" ";
				}
			}
			Vector vInfoMakur = null;
			//SearchDb  sdb = new SearchDb(con);
			if(idkur==null || idkur.equalsIgnoreCase("null")) {
				//bila idkur != null berarti dari form edit
				vInfoMakur = sdb.getListInfotMakur(kdpst);
				//System.out.println("kdkur==null");
			}
			else {
				//idkur = ""+request.getParameter("idkur");
				vInfoMakur = sdb.getListInfotMakur(kdpst,idkur);
				Vector vInfoKelasAktif = sdb.getListMatakuliahAktif(kdpst);
				request.setAttribute("vInfoKelasAktif", vInfoKelasAktif);
				//System.out.println("sini kdkur!=null");
				//System.out.println("kdkur="+idkur);
			}
			request.setAttribute("vInfoMakur", vInfoMakur);
			String callerPage = request.getParameter("callerPage");
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/listMakur.jsp";
			String uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
			String url = PathFinder.getPath(uri, target);
			//System.out.println("ff_callerPahe="+callerPage);
			//request.getRequestDispatcher(url+"?callerPage="+callerPage).forward(request,response);
			request.getRequestDispatcher(url).forward(request,response);
		}
	}
	catch (Exception ex) {
		ex.printStackTrace();
	}
	finally {
    	if (con!=null) {
    		try {
    			con.close();
    		}
    		catch (Exception ignore) {
        		System.out.println(ignore);
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
