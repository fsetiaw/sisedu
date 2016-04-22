package servlets.view;

import java.io.IOException;
import beans.dbase.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.setting.Constants;
import beans.tools.PathFinder;
import java.util.Vector;
import java.util.StringTokenizer;
/**
 * Servlet implementation class ListKelas
 */
@WebServlet("/ListKelas")
public class ListKurikulum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public ListKurikulum() {
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
		System.out.println("listKurikulum");
		String callerPage=request.getParameter("callerPage");
		//System.out.println("cp = "+callerPage);
		SearchDb sdb = new SearchDb();
		UpdateDb udb = new UpdateDb();
		/*
		 * cek form parameter dari listKelas.jsp
		 * bila parameter valid, proses apa insert atau update kelas 
		 */
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		String kdpst = null, nmpst = null;
		if(kdpst_nmpst!=null) {
			StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
			kdpst = st.nextToken();
			nmpst = st.nextToken();
		}
		
		
		System.out.println("kurikulum page = "+kdpst+" "+nmpst);
		
		String kdkur = request.getParameter("kdkur");
		if(kdkur!=null) {
			StringTokenizer st = new StringTokenizer(kdkur);
			if(st.countTokens()<1) {
				kdkur = null;
			}
		}
	
		String thsms1 = request.getParameter("thsms1");
		if(thsms1!=null) {
			StringTokenizer st = new StringTokenizer(thsms1);
			if(st.countTokens()<1) {
				thsms1 = null;
			}
		}
		String thsms2 = request.getParameter("thsms2");
		if(thsms2!=null) {
			StringTokenizer st = new StringTokenizer(thsms2);
			if(st.countTokens()<1) {
				thsms2 = null;
			}
		}
		String konsen = request.getParameter("konsen");
		/*
		String kdwpl = request.getParameter("kdwpl");
		String jenis = request.getParameter("jenis");
		String skstm = request.getParameter("skstm");
		if(skstm==null) {
			skstm="0";
		}
		String skspr = request.getParameter("skspr");
		if(skspr==null) {
			skspr="0";
		}
		String skslp = request.getParameter("skslp");
		if(skslp==null) {
			skslp="0";
		}
		String sttmk = request.getParameter("sttmk");
		String nodos = request.getParameter("nodos");
		*/
		//String idkmk_ = request.getParameter("idkmk_");//parameter dari tabel edit
		String idkur = request.getParameter("idkur");//parameter dari form 
		/*
		 * cek id idkmk exist, if exist update, if not insert & update proses
		 */
		
		if(idkur!=null && kdkur!=null) {
			StringTokenizer st = new StringTokenizer(kdkur);
			//StringTokenizer st1 = new StringTokenizer(nakmk);
			if(st.countTokens()>0) {
				if(idkur==null || idkur.equalsIgnoreCase("null")) {
					/*
					 * insert
					 */
					System.out.println("insert kurikulum");
					String id = udb.insertNewKurikulum(kdpst,kdkur,thsms1,thsms2,konsen);
					//System.out.println("idkmk insert="+id);
					//udb.updateMakul(id,kdpst,kdkmk,nakmk,kdwpl,jenis,skstm,skspr,skslp,sttmk,nodos);
				}
				else {
					int id = Integer.valueOf(idkur).intValue();
					System.out.println("update kurikulum");
					udb.updateKurikulum(""+id,kdpst,kdkur,thsms1,thsms2,konsen);
					//udb.deletePrevThanInserNewMakul(kdpst,kdkmk);
				}			 
			}	
		}
		
		//2nd proses, get vector kelas utk kdpst trsb
		Vector vkrklm = sdb.getListKurikulumProdi(kdpst);
		request.setAttribute("vkrklm", vkrklm);
		//System.out.println("kdkmk = "+kdkmk);
		//get vector list kelas lalu ff ke 
		String target = Constants.getRootWeb()+"/InnerFrame/Akademik/listKurikulum.jsp";
		String uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
		String url = PathFinder.getPath(uri, target);
		//System.out.println("ff_callerPahe="+callerPage);
		request.getRequestDispatcher(url).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
