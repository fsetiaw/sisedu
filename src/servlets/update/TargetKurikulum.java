package servlets.update;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.dbase.SearchDb;
import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class TargetKurikulum
 */
@WebServlet("/TargetKurikulum")
public class TargetKurikulum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public TargetKurikulum() {
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
		String callerPage=request.getParameter("callerPage");
		String target_angkatan = request.getParameter("target_angkatan");
		String target_mahasiswa = request.getParameter("target_mhs");
		//SearchDb sdb = new SearchDb();
		UpdateDb udb = new UpdateDb();
		/*
		 * cek form parameter dari listKelas.jsp
		 * bila parameter valid, proses apa insert atau update kelas 
		 */
		String idkur = request.getParameter("idkur");
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		String kdpst = null, nmpst = null;
		if(kdpst_nmpst!=null) {
			StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
			kdpst = st.nextToken();
			nmpst = st.nextToken();
		}
		udb.updateTargetKurikulum(idkur, target_angkatan, target_mahasiswa);
		String target = Constants.getRootWeb()+"/InnerFrame/Akademik/editTargetKurikulum.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		response.sendRedirect(url_ff+"?kdpst_nmpst="+kdpst_nmpst);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
