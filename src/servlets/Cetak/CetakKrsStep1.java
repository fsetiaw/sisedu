package servlets.Cetak;

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
import beans.tools.PathFinder;
import beans.dbase.*;
import beans.folder.FolderManagement;
/**
 * Servlet implementation class CetakKrsStep1
 * master tampletnya sama dengan HistoryKrsKhs
 */
@WebServlet("/CetakKrsStep1")
public class CetakKrsStep1 extends HttpServlet {
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
		 * servlet ini dipake utk viewKrs dan cetakKrs
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
		//System.out.println("info operator ="+nmm+" "+npm);
		//System.out.println("profileCivitas "+objId+" "+npm+" "+nmm+" "+obj_lvl+" isu npm = "+isu.getNpm());
		int norut = isu.isAllowTo("viewKrs");
		//System.out.println("obj_lvl ="+obj_lvl);
		
		//System.out.println("norut ="+norut);
		//System.out.println("start1");
		if(norut>0) {
			//System.out.println("alow view krs");
			FolderManagement folder = new FolderManagement(isu.getDbSchema(),kdpst,npm);
			folder.cekAndCreateFolderIfNotExist();
			Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"viewKrs",kdpst);
			if(v==null) {
				v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"cetakKrs",kdpst);
			}
			//if(v!=null || v.size()==0) {
			if(v==null) {
				//System.out.println("v size = 0");
				String target = Constants.getRootWeb()+"/ErrorPage/NoDataOrRight.jsp";
				////System.out.println(target);
				String uri = request.getRequestURI();
				//System.out.println(uri);
				String url_ff = PathFinder.getPath(uri, target);
				//System.out.println(url_ff);
				
				request.getRequestDispatcher(url_ff).forward(request,response);
			}
			else {
				//System.out.println("v size > 0");
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
			//	System.out.println("start2");
				//String cmd =  request.getParameter("cmd");
				/*
				 * step1 = cek apakah mhs sdh ada kurkulumnya
				 */
				
				//System.out.println("start2 try");
				//con = ds.getConnection();
				SearchDb sdb = new SearchDb();
				//System.out.println(v_kdpst+"vs"+kdpst);
				//System.out.println(v_npmhs+"vs"+npm);
				//String idkur = sdb.getIndividualKurikulum(v_kdpst, v_npmhs);
				String idkur = sdb.getIndividualKurikulum(kdpst, npm);
				//System.out.println("dikur ="+idkur);
		//		System.out.println("start3");
				/*
				 * deprecated: filternya udah ada di HistKrsKhs.jsp, pindah ke bagian else 
				 * krn msg masih dibutuhkan di HistKrsKhs.jsp
				 */
				//if(idkur==null || idkur.equalsIgnoreCase("null")) {
				if(false) {
					//kurikulum mahasiswa belum ditentukan
					//System.out.println("siap forward");
	//				System.out.println("start4");
					target = Constants.getRootWeb()+"/InnerFrame/Cetak/Krs/CetakKrsStep1.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?msg=Harap Tentukan Kurikulum Untuk "+nmm+" Terlebih Dahulu").forward(request,response);
				}
				else {
					/*
					 * get mata kuliah tranfered (trnlp)
					 */
					//Vector vTrnlp = sdb.getListMatakuliahTrnlp(v_kdpst, v_npmhs);
					//System.out.println("okezone");
					System.out.println("start5");
					Vector vTrnlp = sdb.getListMatakuliahTrnlp(kdpst, npm);
					request.setAttribute("vTrnlp", vTrnlp);
					/*
					 * get history krs / khs
					 */
					//Vector vhist = sdb.getHistoryKrsKhs(v_kdpst, v_npmhs, idkur) ;
					Vector vhist = sdb.getHistoryKrsKhs(kdpst, npm, idkur) ;
					//System.out.println("vHist size="+vhist.size());
					request.setAttribute("vHistKrsKhs", vhist);
					
					/*
					 * cmd update trakm
					 */
					//UpdateDb udb = new UpdateDb();
					//udb.updateIndividualTrakm(v_kdpst, v_npmhs);
					target = Constants.getRootWeb()+"/InnerFrame/Cetak/Krs/CetakKrsStep1.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					//if((idkur==null || idkur.equalsIgnoreCase("null"))) {
					//	request.getRequestDispatcher(url+"?msg=Harap Tentukan Kurikulum Untuk "+nmm+" Terlebih Dahulu").forward(request,response);
					//}
					//else {
						request.getRequestDispatcher(url).forward(request,response);
					//}	
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
