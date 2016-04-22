package servlets.view;

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
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.dbase.*;
import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.folder.FolderManagement;
/**
 * Servlet implementation class HistoryKrsKhs
 */
@WebServlet("/HistoryKrsKhs")
public class HistoryKrsKhs extends HttpServlet {
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
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    //HttpSession session = request.getSession(true);
			//InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
			//dibawah ini adalah info target bukan operator
			String objId = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String kdpst = request.getParameter("kdpst");
			String obj_lvl =  request.getParameter("obj_lvl");
			String cmd =  request.getParameter("cmd");
			String validApprovee = request.getParameter("validApprovee"); // FORCED SHOW - spt KRS APPROVAL
			//String force_show = ""+request.getParameter("force_show");
			
			//System.out.println("objId="+objId);
			//System.out.println("nmm="+nmm);
			//System.out.println("npm="+npm);
			//System.out.println("kdpst33="+kdpst);
			//System.out.println("33="+Checker.getSistemPerkuliahan(objId, kdpst));
			//System.out.println("cmd="+cmd);
			//System.out.println("validApprovee="+validApprovee);
		
			if(Checker.getSistemPerkuliahan(objId, kdpst).equalsIgnoreCase("semester")) {
				if(validApprovee==null || Checker.isStringNullOrEmpty(validApprovee)) {
					validApprovee = new String("false");
				}
				//System.out.println("validApprovee@Hist="+validApprovee);
				//System.out.println("HistoryKrsKhs.java");
				//System.out.println("info operator ="+nmm+" "+npm);
				//System.out.println("profileCivitas "+objId+" "+npm+" "+nmm+" "+obj_lvl+" isu npm = "+isu.getNpm());
				
				/*update 20 juni 2014 ganti is allow to */
				/*
				 * ini diupdate sehbungan pengajuan krs yg targetnya perorangan namun bila
				 * target perorangan tidak mempunyai akses untuk s,vop,viewKrs
				 */
				int norut=0;
				//ini berlaku utk operator
				if(isu.isUsrAllowTo("s", npm, obj_lvl) && isu.getObjNickNameGivenObjId().contains("OPERATOR")) { 
					norut = isu.isAllowTo("viewKrs");
				}
				else if(isu.getObjNickNameGivenObjId().contains("MHS")) {
					norut = isu.isAllowTo("viewKrs");
				}
				
				
				int norut_upd_shift = isu.isAllowTo("updShift");
				
				//System.out.println("norut@Hist="+norut);
				//System.out.println("norut_upd_shift@Hist="+norut_upd_shift);
				//System.out.println("obj_lvl ="+obj_lvl);
				
				//System.out.println("norut ="+norut);
				//System.out.println("start1");
				if(norut>0 || validApprovee.equalsIgnoreCase("true")) {
					//System.out.println("alow view krs");
					FolderManagement folder = new FolderManagement(isu.getDbSchema(),kdpst,npm);
					folder.cekAndCreateFolderIfNotExist();
					Vector v = null;
					if(validApprovee.equalsIgnoreCase("true")) {
						v = isu.tryGetInfo_forced(npm,"viewKrs",kdpst);
					}
					else {
						v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"viewKrs",kdpst);
					}
					
					if(v==null) {
						v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"cetakKrs",kdpst);
					}
					//if(v!=null || v.size()==0) {
					if(v==null) {
						//System.out.println("v size = 0");
						String target = Constants.getRootWeb()+"/ErrorPage/NoDataOrRight.jsp";
						//System.out.println(target);
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
					//	//System.out.println("start2");
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
				//		//System.out.println("start3");
						/*
						 * deprecated: filternya udah ada di HistKrsKhs.jsp, pindah ke bagian else 
						 * krn msg masih dibutuhkan di HistKrsKhs.jsp
						 */
						//if(idkur==null || idkur.equalsIgnoreCase("null")) {
						if(cmd!=null&&cmd.equalsIgnoreCase("editKrs")&&norut_upd_shift>0&&(v_shift==null||v_shift.equalsIgnoreCase("null")||v_shift.equalsIgnoreCase("n/a"))) {
							//kurikulum mahasiswa belum ditentukan
							//System.out.println("siap forward");
							//System.out.println("edit shift");
							target = Constants.getRootWeb()+"/InnerFrame/formUpdShift.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
							
						}
						else {
							/*
							 * get mata kuliah tranfered (trnlp)
							 */
							//Vector vTrnlp = sdb.getListMatakuliahTrnlp(v_kdpst, v_npmhs);
							//System.out.println("okezone");
							//System.out.println("start5");
							//System.out.println(cmd);
							Vector vTrnlp = sdb.getListMatakuliahTrnlp(kdpst, npm);
							request.setAttribute("vTrnlp", vTrnlp);
							/*
							 * get history krs / khs
							 */
							//Vector vhist = sdb.getHistoryKrsKhs(v_kdpst, v_npmhs, idkur) ;
							Vector vhist = sdb.getHistoryKrsKhs(kdpst, npm, idkur) ; //deprecated function
							String tknPaInfo = sdb.getHistoryNpmPa(kdpst, npm);
							String currentPa = sdb.getCurrrentPa(kdpst, npm);
							String infoKrsNotificationAtPmb = sdb.getInfoNotificationAtPmb(npm);
							//System.out.println("infoKrsNotificationAtPmb="+infoKrsNotificationAtPmb);
							request.setAttribute("vHistKrsKhs", vhist);
							request.setAttribute("tknPaInfo", tknPaInfo);
							request.setAttribute("currentPa", currentPa);
							request.setAttribute("infoKrsNotificationAtPmb",infoKrsNotificationAtPmb);
							/*
							 * cmd update trakm
							 */
							//UpdateDb udb = new UpdateDb();
							//udb.updateIndividualTrakm(v_kdpst, v_npmhs);
							if(cmd.equalsIgnoreCase("histKrs")||cmd.equalsIgnoreCase("cetakKrs")||cmd.equalsIgnoreCase("cetakKhs")) {
								target = Constants.getRootWeb()+"/InnerFrame/HistKrsKhs.jsp";
							}
							else if(cmd.equalsIgnoreCase("editKrs")) {
									//updated des 2015
									/*
									 * untuk edit kelas sesuai dengan cuid
									 * 
									 */
								request.removeAttribute("vHistKrsKhs");
								vhist = sdb.getHistoryKrsKhs_v1(kdpst, npm, idkur) ;
								request.setAttribute("vHistKrsKhs", vhist);
								String thsms_krs = Checker.getThsmsKrs();
								
								String tkn_allow_shift_kelas = Checker.getAllowedShiftPilihKelas(thsms_krs, npm, kdpst);
								//System.out.println("tkn_allow_shift_kelas="+tkn_allow_shift_kelas);
								AddHocFunction.modifHistKrsForMenuEdit(vhist,tkn_allow_shift_kelas);
								target = Constants.getRootWeb()+"/InnerFrame/EditKrsKhs.jsp";
							}	
							else if(cmd.equalsIgnoreCase("vba")) {
								target = Constants.getRootWeb()+"/InnerFrame/viewBahanAjar.jsp";
							}
							//||cmd.equalsIgnoreCase("vba")
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							if((idkur==null || idkur.equalsIgnoreCase("null"))) {
								//System.out.println("url = "+url);
								request.getRequestDispatcher(url+"?msg=Harap Tentukan Kurikulum Untuk "+nmm+" Terlebih Dahulu").forward(request,response);
							}
							else {
								//request.getRequestDispatcher(url+"?validApprovee="+validApprovee).forward(request,response);
								//System.out.println(url+"  -- kesini target ?validApprovee="+validApprovee);
								request.getRequestDispatcher(url+"?validApprovee="+validApprovee).forward(request,response);
							}	
						}
					}
				}
				else {
					String target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTamplete.jsp";
					String uri = request.getRequestURI();
					String url_ff = PathFinder.getPath(uri, target);
					String forceBackTo = "/InnerFrame/Notifications/krsNotification.jsp";
					request.getRequestDispatcher(url_ff+"?errMsg=Anda Tidak Memiliki Hak Akses Untuk Melihat Data Civitas Terkait&forceBackTo="+forceBackTo).forward(request,response);
				}	
			}
			else if(Checker.getSistemPerkuliahan(objId, kdpst).equalsIgnoreCase("CONTINUE")) {
				//continus sistem BAN BERJALAN
				/*
				 * krs sudah terinput sesuai KO
				 */
				//System.out.println("ban berjalan");
				//1. cek apakah user boleh view  krs
				//System.out.println("ban berjalan npm="+npm);
				if(isu.isUsrAllowTo_updated("viewKrs", npm)) {
					//System.out.println("ban berjalan 1");
					//2. cek apakah kurikulum sudah di assign ke mahasiswa ini
					SearchDb sdb = new SearchDb();
					String idKur = sdb.getIndividualKurikulum(kdpst, npm);
					if(idKur==null || Checker.isStringNullOrEmpty(idKur)) {
						//forward tidak memiliki akses
						String target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTamplete.jsp";
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
						String forceBackTo = "/InnerFrame/Notifications/krsNotification.jsp";
						request.getRequestDispatcher(url_ff+"?errMsg=Kurikulum Mahasiswa belum Ditentukan, Harap Hubungi TU&forceBackTo="+forceBackTo).forward(request,response);
					}
					else {
						//3.get info profile == diatas juga sama
						//Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"viewKrs",kdpst);
						Vector v = isu.tryGetInfo(npm,"viewKrs",kdpst);
						if(v==null) {
							v = isu.tryGetInfo(npm,"cetakKrs",kdpst);
						}
						//if(v!=null || v.size()==0) {
						if(v==null) {
							//System.out.println("v size = 0");
							String target = Constants.getRootWeb()+"/ErrorPage/NoDataOrRight.jsp";
							//System.out.println(target);
							String uri = request.getRequestURI();
							//System.out.println(uri);
							String url_ff = PathFinder.getPath(uri, target);
							//System.out.println(url_ff);
							
							request.getRequestDispatcher(url_ff).forward(request,response);
						}
						else {
							//System.out.println("v-1 size > 0");
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
						
							//if(cmd!=null&&cmd.equalsIgnoreCase("editKrs")&&norut_upd_shift>0&&(v_shift==null||v_shift.equalsIgnoreCase("null")||v_shift.equalsIgnoreCase("n/a"))) {
							//tidak perlu cek shift untuk sistem roda berjalan
							if(false) {
								//kurikulum mahasiswa belum ditentukan
								//System.out.println("siap forward");
								//System.out.println("edit shift");
								target = Constants.getRootWeb()+"/InnerFrame/formUpdShift.jsp";
								String uri = request.getRequestURI();
								String url = PathFinder.getPath(uri, target);
								request.getRequestDispatcher(url+"?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
								
							}
							else {
								/*
								 * get mata kuliah tranfered (trnlp)
								 */
								//Vector vTrnlp = sdb.getListMatakuliahTrnlp(v_kdpst, v_npmhs);
								//System.out.println("okezone");
								//System.out.println("start5");
								//System.out.println(cmd);
								Vector vTrnlp = sdb.getListMatakuliahTrnlp(kdpst, npm);
								//if(vTrnlp!=null) {
									//System.out.println("vTrnlp size = "+vTrnlp.size());
								//}
								//else {
									//System.out.println("vTrnlp is null");	
								//}
								
								request.setAttribute("vTrnlp", vTrnlp);
								/*
								 * get history krs / khs
								 */
								//Vector vhist = sdb.getHistoryKrsKhs(v_kdpst, v_npmhs, idkur) ;
								/*
								 * npm == v_npmhs terserah mo pake yg mana
								 */
								//System.out.println("v_npmhs="+v_npmhs);
								Vector vTrlsm = sdb.getRiwayatTrlsm(v_npmhs);
								if(vTrlsm!=null) {
									//System.out.println("vTrlsm size = "+vTrlsm.size());
								}
								else {
									//System.out.println("vTrlsm is null");	
								}
								Vector vTrnlm = sdb.getHistoryKrsKhs_v1(v_kdpst, v_npmhs, idKur) ;
								//if(vTrnlm!=null) {
									//System.out.println("vTrnlm size = "+vTrnlm.size());
								//	ListIterator lit = vTrnlm.listIterator();
								//	for(int i=0;i<vTrnlm.size();i++) {
									//System.out.println(i+1+"."+(String)lit.next());
								//	}
								//}
								//else {
									//System.out.println("vTrnlm is null");	
								//}
								Vector vMkKur = sdb.getListMatakuliahDalamKurikulum_v1(v_kdpst, idKur);
								if(vMkKur!=null) {
									//System.out.println("vMkKur size = "+vMkKur.size());
								}
								else {
									//System.out.println("vMkKur is null");	
								}
								//System.out.println("1");	
								String tknPaInfo = sdb.getHistoryNpmPa(kdpst, npm);
								//System.out.println("2");
								String currentPa = sdb.getCurrrentPa(kdpst, npm);
								//System.out.println("3");
								String kode_kmp = Getter.getDomisiliKampus(v_npmhs);
								//System.out.println("4");
								String infoKrsNotificationAtPmb = sdb.getInfoNotificationAtPmb(npm);
								//System.out.println("5");
								Vector vNeedToBeInsert = sdb.continuSistemAdjustment(npm,vTrlsm,vTrnlp,vTrnlm,vMkKur,v_smawl,currentPa,kode_kmp);
								//System.out.println("6");
								
								if(vNeedToBeInsert!=null && vNeedToBeInsert.size()>0) {
									UpdateDbTrnlm udb = new UpdateDbTrnlm(isu.getNpm());
									
									if(vTrlsm!=null && vTrlsm.size()>0) {
										udb.deleteKrs(vTrlsm, v_npmhs);
									}
									udb.insertKrs(vNeedToBeInsert,v_npmhs, v_kdpst);
									//udb.insertKrs_v1(vNeedToBeInsert,v_npmhs, v_kdpst);
									if(vTrlsm!=null && vTrlsm.size()>0) {//memang delete sebelum dan sesudah
										udb.deleteKrs(vTrlsm, v_npmhs);
									}
								}
								else {
									UpdateDbTrnlm udb = new UpdateDbTrnlm(isu.getNpm());
									if(vTrlsm!=null && vTrlsm.size()>0) {
										udb.deleteKrs(vTrlsm, v_npmhs);
									}
								}
								
								Vector vhist = sdb.getHistoryKrsKhs_v1(v_kdpst, v_npmhs, idKur);
								String kodeKmp = Getter.getDomisiliKampus(v_npmhs);
								String thsms_krs = Checker.getThsmsKrs();
								SearchDbClassPoll sdc = new SearchDbClassPoll(isu.getNpm());
								Vector vCp = sdc.getDistinctClassPerKdpst_v1(thsms_krs, v_kdpst, kodeKmp);
								//System.out.println("vCp0="+vCp.size());
								//System.out.println("infoKrsNotificationAtPmb="+infoKrsNotificationAtPmb);
								request.setAttribute("vHistKrsKhs", vhist);
								request.setAttribute("vCp", vCp);
								request.setAttribute("tknPaInfo", tknPaInfo);
								request.setAttribute("currentPa", currentPa);
								request.setAttribute("infoKrsNotificationAtPmb",infoKrsNotificationAtPmb);
								/*
								 * cmd update trakm
								 */
								//UpdateDb udb = new UpdateDb();
								//udb.updateIndividualTrakm(v_kdpst, v_npmhs);
								//if(cmd.equalsIgnoreCase("histKrs")||cmd.equalsIgnoreCase("cetakKrs")||cmd.equalsIgnoreCase("cetakKhs")) {
									target = Constants.getRootWeb()+"/InnerFrame/HistKrsKhsContinuSistem.jsp";
								//}
								//belum ada pilihan lain
								/*
								else if(cmd.equalsIgnoreCase("editKrs")) {
										//mungkin deprecated - krn untuk edit nilai ada menu baru
									target = Constants.getRootWeb()+"/InnerFrame/EditKrsKhs.jsp";
								}	
								else if(cmd.equalsIgnoreCase("vba")) {
									target = Constants.getRootWeb()+"/InnerFrame/viewBahanAjar.jsp";
								}
								*/
								//||cmd.equalsIgnoreCase("vba")
								String uri = request.getRequestURI();
								String url = PathFinder.getPath(uri, target);
								request.getRequestDispatcher(url+"?continuSys=true").forward(request,response);
									
							}
						}
						
					}
				}
				else {
					//forward tidak memiliki akses
					//System.out.println("ban berjalan 2");
					String target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTamplete.jsp";
					String uri = request.getRequestURI();
					String url_ff = PathFinder.getPath(uri, target);
					String forceBackTo = "/InnerFrame/Notifications/krsNotification.jsp";
					request.getRequestDispatcher(url_ff+"?errMsg=Anda Tidak Memiliki Hak Akses Untuk Melihat Data Civitas Terkait&forceBackTo="+forceBackTo).forward(request,response);
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
