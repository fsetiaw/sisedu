package servlets.input;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.dbase.*;
import beans.setting.Constants;
import beans.tools.PathFinder;

import java.io.PrintWriter;
/**
 * Servlet implementation class InputPymntTable
 */
@WebServlet("/InputPymntTable")
public class PymntTable extends HttpServlet {
	private static final long serialVersionUID = 1L;   
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public PymntTable() {
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
		PrintWriter out = response.getWriter();
		String opnpm = request.getParameter("opnpm");
		String opnmm = request.getParameter("opnmm");
		String form_type=request.getParameter("form_type");
		String objId=request.getParameter("id_obj");
		String obj_lvl=request.getParameter("obj_lvl");
		String kdpst=request.getParameter("kdpst");
		String inp_nmm=request.getParameter("inp_nmm");
		String inp_npm=request.getParameter("inp_npm");
		String accnt=request.getParameter("accnt");
		String inp_tgtrs=request.getParameter("inp_tgtrs");
		String inp_payee=request.getParameter("inp_payee");
		String inp_amnt=request.getParameter("inp_amnt");
		String inp_bukti=request.getParameter("inp_bukti");
		String stamps = request.getParameter("stamps");
		String inp_keter = request.getParameter("inp_keter");
		//String tipeTransaksi = request.getParameter("tipeTransaksi");
		//System.out.println("opnm ="+opnpm);
		UpdateDb db = new UpdateDb();
		boolean sukses = db.insertPymntTable(kdpst, inp_npm, inp_tgtrs, inp_keter, inp_payee, inp_amnt, "BELUM DIGUNAKAN", accnt, opnpm, opnmm, stamps);
		if(sukses) {
			//request.getRequestDispatcher("../get.histPymnt?objId="+objId+"&nmm="+inp_nmm+"&npm="+inp_npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst).forward(request,response);
			//response.sendRedirect("get.histPymnt?objId="+objId+"&nmm="+inp_nmm+"&npm="+inp_npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst);
			//out.println("<html>");
			//out.println("<body>");
			//out.println("<meta HTTP-EQUIV=\"REFRESH\" CONTENT=\"0; URL=javascript:window.open(\'get.histPymnt?objId="+objId+"&nmm="+inp_nmm+"&npm="+inp_npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"\',\'_self\');\">");
			//out.println("okay deh");
			//out.println("</body>");
			//out.println("</html>");
			String target = Constants.getRootWeb()+"/index.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			System.out.println("insert page ="+target);
			System.out.println("insert page ="+uri);
			System.out.println("insert page ="+url_ff);
			
			response.sendRedirect("get.histPymnt?id_obj="+objId+"&nmm="+inp_nmm+"&npm="+inp_npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst);
			//response.sendRedirect(url_ff+"?id_obj="+objId+"&nmm="+inp_nmm+"&npm="+inp_npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst);
		}	
		else {
			String target = Constants.getRootWeb()+"/ErrorPage/gagalInsertData.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			response.sendRedirect(url_ff+"?id_obj="+objId+"&nmm="+inp_nmm+"&npm="+inp_npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst);
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
