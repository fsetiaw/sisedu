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
import beans.tools.Checker;
import beans.tools.PathFinder;
import java.util.Vector;
import java.util.StringTokenizer;
/**
 * Servlet implementation class ListKelas
 */
@WebServlet("/ListKelas")
public class ListKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public ListKelas() {
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
		//System.out.println("listKelas");
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
		//System.out.println("1");
		if(kdpst_nmpst!=null) {
			StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
			kdpst = st.nextToken();
			nmpst = st.nextToken();
		}
		//System.out.println("2");
		String kdkmk = request.getParameter("kdkmk");
		if(kdkmk!=null) {
			StringTokenizer st = new StringTokenizer(kdkmk);
			if(st.countTokens()<1) {
				kdkmk = null;
			}
		}
		//System.out.println("3");
		String nakmk = request.getParameter("nakmk");
		if(nakmk!=null) {
			StringTokenizer st = new StringTokenizer(nakmk);
			if(st.countTokens()<1) {
				nakmk = null;
			}
		}
		else {
			nakmk="Harap data di-update";
		}
		//nakmk=nakmk.replace("$", ",");
		//System.out.println(nakmk);
		//System.out.println("4");
		String kdwpl = request.getParameter("kdwpl");
		//System.out.println("5");
		String jenis = request.getParameter("jenis");
		//System.out.println("6");
		String skstm = request.getParameter("skstm");
		//System.out.println("7");
		if(skstm==null) {
			skstm="0";
		}
		//System.out.println("8");
		String skspr = request.getParameter("skspr");
		if(skspr==null) {
			skspr="0";
		}
		//System.out.println("9");
		String skslp = request.getParameter("skslp");
		if(skslp==null) {
			skslp="0";
		}
		//System.out.println("10");
		String sttmk = request.getParameter("sttmk");
		String nodos = request.getParameter("nodos");
		//String idkmk_ = request.getParameter("idkmk_");//parameter dari tabel edit
		String idkmk = request.getParameter("idkmk");//parameter dari form 
		
		/*
		 * cek id idkmk exist, if exist update, if not insert & update proses
		 */

		if(kdpst!=null && kdkmk!=null && nakmk!=null) {
			StringTokenizer st = new StringTokenizer(kdkmk);
			StringTokenizer st1 = new StringTokenizer(nakmk);
			if(st.countTokens()>0 && st1.countTokens()>0) {
			//delete prev rec kemudian baru add
					
					//System.out.println("update");
					//System.out.println("update idkmk ="+idkmk);
					if(idkmk==null || idkmk.equalsIgnoreCase("null")) {
						/*
						 * insert
						*/
						//System.out.println("insert");
						String id = udb.insertNewMakul(kdpst,kdkmk);
						//System.out.println("idkmk insert="+id);
						udb.updateMakul(id,kdpst,kdkmk,nakmk,kdwpl,jenis,skstm,skspr,skslp,sttmk,nodos);
					}
					else {
						int id = Integer.valueOf(idkmk).intValue();
						//System.out.println("idkmk update="+id);
						udb.updateMakul(""+id,kdpst,kdkmk,nakmk,kdwpl,jenis,skstm,skspr,skslp,sttmk,nodos);
					//udb.deletePrevThanInserNewMakul(kdpst,kdkmk);
					}			 
			}	
		}
		
		//2nd proses, get vector kelas utk kdpst trsb
		Vector vMakul = sdb.getListMakulProdi(kdpst);
		
		request.setAttribute("vMakul", vMakul);
		//System.out.println("kdkmk = "+kdkmk);
		//get vector list kelas lalu ff ke 
		String target = Constants.getRootWeb()+"/InnerFrame/Akademik/listKelas.jsp";
		//String target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashAkademi.jsp";
		String uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
		String url = PathFinder.getPath(uri, target);
		//System.out.println("ff_callerPahe="+callerPage);
		
		request.getRequestDispatcher(url+"?callerPage="+callerPage).forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
