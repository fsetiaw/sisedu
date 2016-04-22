package servlets.input;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.*;
import beans.dbase.onlineTest.SearchOnlineTestDb;
import beans.tmp.*;
/**
 * Servlet implementation class Civitas
 */
@WebServlet("/Civitas")
public class Civitas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public Civitas() {
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
/*
 * bugs :
 * 1. toogle antara baru dan pindahan bikin error
 */
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("CIVITAS.JAVA");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//HttpSession session = request.getSession(true);
		//InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String obj_id = null;
			String kdpst = null;
			String[]listUjian = request.getParameterValues("listUjian");
			String dipilihDariList = request.getParameter("dipilihDariList");
			String namadsn = ""+request.getParameter("namadsn");
			//System.out.println("list ujian");
			String tknUjian = null;
			if(listUjian!=null) {
				tknUjian="";
				for(int i=0;i<listUjian.length;i++) {
					tknUjian = tknUjian+listUjian[i];
					if(i<listUjian.length) {
						tknUjian=tknUjian+"||";
					}
					//System.out.println(i+"."+listUjian[i]);
				}
			}
			if(tknUjian==null) {
				tknUjian=request.getParameter("tknUjian");
			}
			//System.out.println("tknUjian=="+tknUjian);
			String objid_kdpst = request.getParameter("objid_kdpst");
			if(objid_kdpst!=null) {
				StringTokenizer st = new StringTokenizer(objid_kdpst,"-");
				obj_id = st.nextToken();
				kdpst = st.nextToken();
			}	
			
			/*
			 * router - pengalihan seperti input dosen
			 */
			//input dosen
			//System.out.println("namadsn==>"+namadsn);
			//System.out.println("dipilihDariList=="+dipilihDariList);
			String nuInpDsnForm = (String)request.getParameter("nuInpDsnForm_Huruf_Opt");
			if(nuInpDsnForm!=null && nuInpDsnForm.equalsIgnoreCase("nuInpDsnForm")) {
				//nu input form for dosen
				/*
				 * front_title
				 * full_name
				 * end_title
				 * pt_base
				 * prodi_base
				 * serdos -radio
				 * tipe_ika_dosen
				 */
				//System.out.println("nu input dosen form");
			}
			else if(kdpst.equalsIgnoreCase(Constants.getKdpstDosen()) && namadsn!=null && !Checker.isStringNullOrEmpty(namadsn) && !namadsn.contains("list") && !namadsn.contains("belumada") && (dipilihDariList!=null && dipilihDariList.equalsIgnoreCase("true"))) {
				//PROCESS INPUT / UPDATE DOSEN
				//Vector vScope = isu.getScopeUpd7des2012ReturnDistinctKdpst("addDosen");
				//khusus untuk input dosen - scope iciv scope tidak dapat dipakai - tapi scope search
				Vector vScope = isu.getScopeUpd7des2012ReturnDistinctKdpst("s");
				ListIterator lisc = vScope.listIterator();
				while(lisc.hasNext()) {
					String brs = (String)lisc.next();
					//System.out.println(".."+brs);
				}
				//StringTokenizer st = new StringTokenizer(namadsn,"__");
				//if(st.countTokens()==1) {
					//input dari text form nama dosen == insert process
				//System.out.println("dingle");
				UpdateDb udb = new UpdateDb(isu.getNpm());
				udb.updateMsdos(namadsn,vScope);
				String target = Constants.getRootWeb()+"/InnerFrame/PMB/preProsesInsertCivitas.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?pesan=suksesdosen").forward(request,response);
				//}
				//else {
				//	System.out.println("double");
				//}
			}
			else {
				//System.out.println("alter");
				String smawl = request.getParameter("smawl");
				//System.out.println("civitass smawl = "+smawl);
				String nama = request.getParameter("nama");
				String nim = request.getParameter("nim");
				//System.out.println("nim = "+nim);
				String shiftKls = request.getParameter("shiftKls");
				//System.out.println("shiftKls = "+shiftKls);
				String kdjek = request.getParameter("kdjek");
				String stpid = request.getParameter("stpid");
				String agama = request.getParameter("agama");
				String tplhr = request.getParameter("tplhr");
		
				String nglhr = request.getParameter("nglhr");
				String tglhr = request.getParameter("tglhr");
				String yy=null,dd=null,mm=null;
				if(tglhr!=null) {
					tglhr = tglhr.replace("/","-");
					StringTokenizer st = new StringTokenizer(tglhr,"-");
					dd = st.nextToken();
					if(dd.length()==1) {
						dd = "0"+dd;
					}
					mm = st.nextToken();
					if(mm.length()==1) {
						mm = "0"+mm;
					}
					yy = st.nextToken();
					tglhr=yy+"-"+mm+"-"+dd;
				}	
				//		System.out.println("tanggal lahir ="+tglhr);
				String email = request.getParameter("email");
				String aspti = request.getParameter("aspti");
				if(aspti!=null) {
					aspti = aspti.toUpperCase();
					if(aspti.contains("UNIV")&&aspti.contains("SATYAGAMA")) {
						aspti = "031031";
					}
					else {
						aspti = "null";
					}
				}
				else {
					aspti = "null";
				}
				//System.out.println("aspti civitas ="+aspti);
				String noHp = request.getParameter("hp");
				//System.out.println(" civitas hp="+noHp);
				String pesan=null;
				//	jika dari callerPage=preProsesInsertCivitas
				String callerPage = request.getParameter("callerPage");
			
				boolean pass = true;
				if(callerPage!=null && callerPage.equalsIgnoreCase("preProsesInsertCivitas")) {
					
					if(yy!=null && yy.length()<4) {
						pesan=pesan+"PASTIKAN FORMAT TANGGAL = DD/MM/YYYY <br/>";
						pesan = pesan.replace("null", "");
						String target = Constants.getRootWeb()+"/InnerFrame/PMB/preProsesInsertCivitas.jsp";
						String uri = request.getRequestURI();
					//	System.out.println(target+" / "+uri);
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?pesan="+pesan).forward(request,response);
						pass=false;
					}
					else {
						try {
							//	if(yy!=null&&mm!=null&&dd!=null) {
							java.sql.Date dt = java.sql.Date.valueOf(yy+"-"+mm+"-"+dd);
					//		}	
						}
						catch(Exception e) {
							pesan = pesan +"PASTIKAN FORMAT TANGGAL = DD/MM/YYYY <br/>";
							pesan = pesan.replace("null", "");
							String target = Constants.getRootWeb()+"/InnerFrame/PMB/preProsesInsertCivitas.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?pesan="+pesan).forward(request,response);
							pass=false;
						}
					}
				//System.out.println("pass="+pass);
					if(pass) {
						UpdateDb db = new UpdateDb();
				//	System.out.println("pass dan value tknUjian="+tknUjian);
					//input dosen
						//if(kdpst.equalsIgnoreCase(Constants.getKdpstDosen()) && namadsn!=null && !namadsn.equalsIgnoreCase("list")) {
						//	System.out.println("proses update data dosen");
						//}
						//else {
						//System.out.println("way1");
						//System.out.println(" civitas hp1="+noHp);
						String npm = db.insertCivitasSimpleGivenSmawl(smawl,obj_id,kdpst, nama, nim, kdjek, stpid, agama, tplhr, nglhr, tglhr,email,noHp,aspti,shiftKls);
						if(npm!=null && !Checker.isStringNullOrEmpty(npm)) {	
							db.resetUsrPwd(kdpst,npm);
							db.insertCivitasJadwalBridgeMhsBaru(tknUjian,kdpst,npm);
							String target = Constants.getRootWeb()+"/InnerFrame/PMB/preProsesInsertCivitas.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?pesan=sukses").forward(request,response);
						}
						else {
							String target = Constants.getRootWeb()+"/InnerFrame/PMB/preProsesInsertCivitas.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?pesan=gagal").forward(request,response);
						}
						//}	
					}
				}
				else {
					if(!Checker.isStringNullOrEmpty(obj_id) && !Checker.isStringNullOrEmpty(kdpst) && !Checker.isStringNullOrEmpty(nama) && !Checker.isStringNullOrEmpty(tplhr) && !Checker.isStringNullOrEmpty(tglhr) && !Checker.isStringNullOrEmpty(email) && !Checker.isStringNullOrEmpty(noHp)) {
					//System.out.println("proses civitas");
						String target = Constants.getRootWeb()+"/InnerFrame/PMB/preProsesInsertCivitas.jsp";
				//		String target = Constants.getRootWeb()+"/InnerFrame/setTargetKdpst.jsp";
						String uri = request.getRequestURI();
				//		//System.out.println(target+" / "+uri);
						String urlff = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(urlff).forward(request,response);
					
					}
					else {
						//	get avail test
						SearchOnlineTestDb sot = new SearchOnlineTestDb();
						String tknInfoUjian = sot.getListUjianThatReusableAndScheduledAfterToday();
						request.setAttribute("tknInfoUjian", tknInfoUjian);
						String target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
				//	response.sendRedirect(url_ff);
						request.getRequestDispatcher(url_ff+"?atMenu=insCiv").forward(request,response);
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
