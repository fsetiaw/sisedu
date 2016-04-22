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
import beans.dbase.dosen.*;
import beans.dbase.onlineTest.SearchOnlineTestDb;
import beans.tmp.*;
/**
 * Servlet implementation class Civitas
 */
@WebServlet("/CivitasGuru")
public class CivitasGuru extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public CivitasGuru() {
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
		System.out.println("CIVITAS-GURU.JAVA");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String fieldAndValue = (String)session.getAttribute("fieldAndValue");
		System.out.println("fieldAndValue=>>"+fieldAndValue);
		/*
		 * declare variable yg ada di form asal fieldAndValue
		 */
		String obj_id = null;
		String kdpst = null;
		String fwdpage = null;//StringfwdPageIfValid_String_Opt
		//ignore this var, String nuInpDsnForm_Huruf_Opt" value="nuInpDsnForm" />
		String gelar_depan=null;//Gelar-Depan_Huruf_Opt" style="width:20%"/>
		String nama_lengkap=null;//Nama-Lengkap_Huruf_Wajib" style="width:55%" placeholder="Nama Lengkap" />
		String gelar_belakang=null;//Gelar-Belakang_Huruf_Opt" style="width:20%" placeholder="Gelar Belakang" /></td>
		String kdjek=null;//kdjek_Huruf_Opt" value="p">Wanita &nbsp&nbsp
		String ika=null;//Ikatan-Kerja_Huruf_Opt" style="width:98%">
		String kdpst_base=null;//Prodi-Homebase_Huruf_Opt" style="width:98%">
		String tipe_nodos_dikti=null;//nidnn_Huruf_Opt" value="nip">NIP &nbsp&nbsp
		String nodos_dikti=null;//No-Dosen_Huruf_Opt" style="width:98%" placeholder="Nomor dosen (NIP/NIS/NPP/NIK) bila ada"/>
		String objid_kdpst_civitas = null;//objid-kdpst_Huruf_Opt
		/*
		 * assign value masing2 variable
		 */
		if(fieldAndValue!=null && !Checker.isStringNullOrEmpty(fieldAndValue)) {
			StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
			while(st.hasMoreTokens()) {
				String fieldName = st.nextToken();
				String fieldValu = st.nextToken();
				if( fieldValu!=null && !Checker.isStringNullOrEmpty(fieldValu)) {
					if(fieldName.equalsIgnoreCase("StringfwdPageIfValid_String_Opt")) {
						fwdpage = fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("objid-kdpst_Huruf_Opt")) {
						objid_kdpst_civitas= fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("Gelar-Depan_Huruf_Opt")) {
						gelar_depan= fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("Nama-Lengkap_Huruf_Wajib")) {
						nama_lengkap = fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("Gelar-Belakang_Huruf_Opt")) {
						gelar_belakang= fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("kdjek_Huruf_Opt")) {
						kdjek = fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("Ikatan-Kerja_Huruf_Opt")) {
						ika = fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("nidnn_Huruf_Opt")) {
						tipe_nodos_dikti= fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("Prodi-Homebase_Huruf_Opt")) {
						kdpst_base= fieldValu;
					}
					else if(fieldName.equalsIgnoreCase("No-Dosen_Huruf_Opt")) {
						nodos_dikti= fieldValu;
					}
				}
			}
			System.out.println("objid_kdpst_civitas="+objid_kdpst_civitas);
			if(objid_kdpst_civitas!=null && !Checker.isStringNullOrEmpty(objid_kdpst_civitas)) {
				st = new StringTokenizer(objid_kdpst_civitas,"-");
				obj_id = st.nextToken();
				kdpst = st.nextToken();
			}
			System.out.println("obj_id,kdpst="+obj_id+","+kdpst);
			/*
			 * insert dengan method insert civitas original
			 */
			String smawl = Checker.getThsmsNow();//smawl pas input data ini
			UpdateDbDsn udb = new UpdateDbDsn(isu.getNpm());
			String nim=null;
			String agama=null;
			String tplhr=null;
			String nglhr=null;
			String tglhr=null;
			String email=null;
			String noHp=null;
			String aspti=null;
			String shiftKls=null;
			
			
			String nodos=null; //harus diupdate dengan msdos
			String nidnn=null;
			
			String npmhs = udb.insertCivitasSimpleGivenSmawl(smawl,obj_id,kdpst, nama_lengkap, nim, kdjek, "B", agama, tplhr, nglhr, tglhr,email,noHp,aspti,shiftKls);
			System.out.println("npmhs=="+npmhs);
			/*
			 * insert EXT_CIVITAS_DATA_DOSEN
			 */
			
			String status="A";
			String pt_s1=null;
			String jur_s1=null;
			String kdpst_s1=null;
			String tkn_bid_ahli_s1=null;
			String noija_s1=null;
			String file_ija_s1=null;
			String jdl_ta_s1=null;
			String pt_s2=null;
			String jur_s2=null;
			String kdpst_s2=null;
			String tkn_bid_ahli_s2=null;
			String noija_s2=null;
			String file_ija_s2=null;
			String jdl_ta_s2=null;
			String pt_s3=null;
			String jur_s3=null;
			String kdpst_s3=null;
			String tkn_bid_ahli_s3=null;
			String noija_s3=null;
			String file_ija_s3=null;
			String jdl_ta_s3=null;
			String pt_prof=null;
			String jur_prof=null;
			String kdpst_prof=null;
			String tkn_bid_ahli_prof=null;
			String noija_prof=null;
			String file_ija_prof=null;
			String jdl_ta_prof=null;
			String tot_kum=null;
			String jbt_akdmk_dikti=null;
			String jbt_akdmk_local=null;
			String jbt_struk=null;
			String tgl_sta=null;
			String tgl_out=null;
			String serdos="false";
			
			String pt_home=null;
			if(kdpst_base!=null && !Checker.isStringNullOrEmpty(kdpst_base) ) {
				pt_home = Constants.getKdpti();
			}
			String prodi_home=kdpst_base;
			String email_inst=null;
			String pangkat_gol=null;
			int k = udb.insertTableEXT_CIVITAS_DATA_DOSEN(kdpst,npmhs,nodos_dikti,gelar_depan,gelar_belakang,nidnn,tipe_nodos_dikti,nodos_dikti,status,pt_s1,jur_s1,kdpst_s1,tkn_bid_ahli_s1,noija_s1,file_ija_s1,jdl_ta_s1,pt_s2,jur_s2,kdpst_s2,tkn_bid_ahli_s2,noija_s2,file_ija_s2,jdl_ta_s2,pt_s3,jur_s3,kdpst_s3,tkn_bid_ahli_s3,noija_s3,file_ija_s3,jdl_ta_s3,pt_prof,jur_prof,kdpst_prof,tkn_bid_ahli_prof,noija_prof,file_ija_prof,jdl_ta_prof,tot_kum,jbt_akdmk_dikti,jbt_akdmk_local,jbt_struk,ika,tgl_sta,tgl_out,serdos,pt_home,prodi_home,email_inst,pangkat_gol);
			System.out.println("k=="+k);
		}
		String target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
//	response.sendRedirect(url_ff);
		request.getRequestDispatcher(url_ff).forward(request,response);
		
		//String[]listUjian = request.getParameterValues("listUjian");
		//String dipilihDariList = request.getParameter("dipilihDariList");
		//String namadsn = ""+request.getParameter("namadsn");
		//System.out.println("list ujian");
		//String tknUjian = null;//
		/*
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
		 * router - pengaliha seperti input dosen
		 */
		//input dosen
		//System.out.println("namadsn==>"+namadsn);
		//System.out.println("dipilihDariList=="+dipilihDariList);
		/*
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
		/*
			System.out.println("nu input dosen form");
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
					request.getRequestDispatcher(url_ff).forward(request,response);
				}
			}
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
