package servlets.update;

import java.io.IOException;
import java.io.PrintWriter;

import beans.dbase.*;
import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.mhs.*;
import beans.folder.FolderManagement;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

import java.util.LinkedHashSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Servlet implementation class KrsKhs
 */
@WebServlet("/KrsKhs")
public class KrsKhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
//    Connection con;
//    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public KrsKhs() {
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
		//String npmhs = request.getParameter("npmhs");
		//String kdpst = request.getParameter("kdpst");
		//HttpSession session = request.getSession(true);
		//System.out.println("krskhs.java");
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
		//System.out.println("obj_lvl="+obj_lvl);
		String cmd =  request.getParameter("cmd");
		String targetThsms = request.getParameter("targetThsms");
		/*
		 * insKrsPmbThsmsOnly - depricated - ganti insKrsThsmsKrsOnly
		 */
		//deprecated
		if(isu.isAllowTo("insKrsPmbThsmsOnly")>0) {
			//System.out.println("insekrspmbonly");
			targetThsms = Checker.getThsmsPmb();
		}	
		//overiding 
		if(isu.isAllowTo("insKrsThsmsKrsOnly")>0) {
			//System.out.println("insekrspmbonly");
			targetThsms = Checker.getThsmsKrs();
		}	
		
 		int norut = isu.isAllowTo("viewKrs");
		int norut_upd_shift = isu.isAllowTo("updShift");

		if(isu.isUsrAllowTo("viewKrs", npm, obj_lvl)) {
			//System.out.println("alow view krs");
			FolderManagement folder = new FolderManagement(isu.getDbSchema(),kdpst,npm);
			folder.cekAndCreateFolderIfNotExist();
			Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"viewKrs",kdpst);
			if(v!=null && v.size()==0) {
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
				
				//System.out.println("krskhs");
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
		
				Vector vHistKrsKhs = (Vector)session.getAttribute("vHistKrsKhsForEdit");
				Vector vTrnlp =(Vector) session.getAttribute("vTrnlpForEdit");
				
				/*
				 * cek apa sudah punya pembimbing
				 */
				SearchDbInfoMhs sdm = new SearchDbInfoMhs(isu.getNpm());
				String curPa = sdm.getCurrrentPa(v_kdpst, v_npmhs);
				/*
				 * cek apa sdh ditentukan kurikulumnya
				 */
				String krklm = sdm.getInfoKrklm(v_kdpst, v_npmhs);
				boolean wajibHeregistrasiForInsKrs = Checker.wajibDaftarUlangUntukIsiKrs();
				String pesan_daftar_ulang = null;
				if(wajibHeregistrasiForInsKrs) {
					pesan_daftar_ulang = Checker.sudahDaftarUlang(v_kdpst, npm);
				}	
				//System.out.println("pesan_daftar_ulang="+pesan_daftar_ulang);
				boolean mhsNoShift = false;
				if((v_shift==null||v_shift.equalsIgnoreCase("null")||v_shift.equalsIgnoreCase("n/a"))&&isu.getNpm().equalsIgnoreCase(v_npmhs)) {
					mhsNoShift = true;
				}
				//1. cek apahaka SHIFT MHS sudah diisi
				if((cmd!=null&&cmd.equalsIgnoreCase("insertKrs")&&norut_upd_shift>0&&(v_shift==null||v_shift.equalsIgnoreCase("null")||v_shift.equalsIgnoreCase("n/a")))||(mhsNoShift)) {
					//for operator use
					//kurikulum mahasiswa belum ditentukan
					//System.out.println("siap forward");
					//System.out.println("edit shift");
					if(mhsNoShift) {
						target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=SHIFT MAHASISWA BELUM DI UPDATE, HARAP HUBUNGI TATA USAHA FAKULTAS").forward(request,response);
					}
					else {
						target = Constants.getRootWeb()+"/InnerFrame/formUpdShift.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
					}
				}
				else if(curPa.contains("null")) {
					//pastikan pembimbing akademik sudah diisi
					//System.out.println("no pembimbing");
					target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=PEMBIMBING AKADEMIK BELUM DITENTUKAN, HARAP HUBUNGI TATA USAHA FAKULTAS").forward(request,response);
					
				}
				else if(Checker.isStringNullOrEmpty(krklm)){
					target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=KURIKULUM BELUM DITENTUKAN, HARAP HUBUNGI TATA USAHA FAKULTAS").forward(request,response);
				}
				else if(pesan_daftar_ulang!=null) {
					target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg="+pesan_daftar_ulang).forward(request,response);
				}
				else {
					//cek apa targetThsms sudah diisi
					if(targetThsms==null || Checker.isStringNullOrEmpty(targetThsms)) {
						//selalu terisi krn otomatis ngecek thsmspmb / thsmskrs
						//InnerFrame/Tamplete/setTargetThsmsBasedOnNpmhs.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" >INSERT DATA<span>KRS BARU</span></a></li -->
						
						
						/* update pemisahan proses khusus proces insert krs 
						 * khusus untuk cmd=insertKrs (bagi mhs dan operator)
						 * thsms=thsmsKrs , kecuali ada white list
						 */
						if(cmd!=null && cmd.equalsIgnoreCase("insertKrs")) {
							//target = Constants.getRootWeb()+"/InnerFrame/Tamplete/setTargetThsmsBasedOnNpmhs.jsp";
							//String uri = request.getRequestURI();
							//String url = PathFinder.getPath(uri, target);
							
							String thsms_krs = Checker.getThsmsKrs();
							String tkn_thsms_whiteList = Checker.getThsmsKrsWhiteList(kdpst, npm);
							if(tkn_thsms_whiteList==null || Checker.isStringNullOrEmpty(tkn_thsms_whiteList) || isu.getObjNickNameGivenObjId().contains("MHS")) {
								/*
								 * 1. seluruh mhs target thsms = thsms krs (tdk mungkin menngunaan white list)
								 * 2. kalo ngga ada white-list berarti target thsms = thsms krs
								 */
								request.getRequestDispatcher("go.updateKrsKhs?targetThsms="+thsms_krs+"&id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);	
							}
							else {
								if(!tkn_thsms_whiteList.contains(thsms_krs)) {
									tkn_thsms_whiteList=tkn_thsms_whiteList+","+thsms_krs;
								}
								Vector v1 = new Vector();
								ListIterator li1 = v1.listIterator();
								StringTokenizer st = new StringTokenizer(tkn_thsms_whiteList,",");
								while(st.hasMoreTokens()) {
									String thsms = st.nextToken();
									li1.add(thsms);
								}
								Collections.sort(v1);
								li1 = v1.listIterator();
								tkn_thsms_whiteList = "";
								while(li1.hasNext()) {
									String thsms = (String)li1.next();
									tkn_thsms_whiteList = tkn_thsms_whiteList+thsms;
									if(li1.hasNext()) {
										tkn_thsms_whiteList=tkn_thsms_whiteList+",";
									}
								}
								target = Constants.getRootWeb()+"/InnerFrame/Tamplete/setTargetThsmsBasedOnNpmhs.jsp";
								String uri = request.getRequestURI();
								String url = PathFinder.getPath(uri, target);
								//System.out.println("masuk sinsi");
								request.getRequestDispatcher(url+"?tkn_thsms_whiteList="+tkn_thsms_whiteList+"&id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
							}
						}
						else {
							//System.out.println("ini cmd="+cmd);
							target = Constants.getRootWeb()+"/InnerFrame/Tamplete/setTargetThsmsBasedOnNpmhs.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url+"?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
						}
					}
					else {
					/*
					 * pastikan untuk target thsms trnlm tidak locked, kalau locked tidak dapat diedit
					 * ---cek apaka target thsms di lock
					 */
						//System.out.println("bener kesinsij");
					//System.out.println("hsrudnys masuk else");
						SearchDb sdb = new SearchDb();
						boolean locked = sdb.cekStatusLockUnlockTrnlm(targetThsms,npm);
						if(locked) {
							target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							//String strBackTo = "get.histKrstandaTanyaid_obj=tandaKurungBukaobjIdtandaKurungTutuptandaDannmm=tandaKurungBukanmmtandaKurungTutuptandaDannpm=tandaKurungBukanpmtandaKurungTutuptandaDanobj_lvl=tandaKurungBukaobjLvltandaKurungTutuptandaDankdpst=tandaKurungBukakdpsttandaKurungTutuptandaDancmd=tandaKurungBukahistKrstandaKurungTutup";
						
							request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=STATUS KRS "+targetThsms+" TERKUNCI, HARAP HUBUNGI TATA USAHA FAKULTAS").forward(request,response);
						}
						else {
							//System.out.println("terus masuk sini");
							if(Checker.getObjName(Integer.valueOf(isu.getIdObj()).intValue()).contains("MHS") && !Checker.isMhsAllowPengajuanKrs()) {
								//jika usr mahasisea dan belum saatnya melakukan pengajuan berdasar kalendar
								target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
								String uri = request.getRequestURI();
								String url = PathFinder.getPath(uri, target);
								request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=STATUS PENGAJUAN KRS "+targetThsms+" TERKUNCI<br/>" +
										" PERIODE PENGAJUAN KRS BELUM DIBUKA / SUDAH DITUTUP&tipe=msgonly").forward(request,response);
							}
							else {
							//cek lockmhs
								locked = sdb.cekStatusLockMhsTrnlm(targetThsms,npm);
								if(locked) {
									target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
									String uri = request.getRequestURI();
									String url = PathFinder.getPath(uri, target);
									request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=STATUS KRS "+targetThsms+" TERKUNCI<br/>" +
										" SILAHKAN AJUKAN PERMOHONAN KEPADA PEMBIMBING AKADEMIK").forward(request,response);
								}
							//else { //pindah ke atas
								//jika usr mahasisea dan belum saatnya melakukan pengajuan berdasar kalendar
								//if(Checker.getObjName(Integer.valueOf(isu.getIdObj()).intValue()).contains("MHS") && !Checker.isMhsAllowPengajuanKrs()) {
									//jika usr mahasisea dan belum saatnya melakukan pengajuan berdasar kalendar
							//		target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
							//		String uri = request.getRequestURI();
							//		String url = PathFinder.getPath(uri, target);
							//		request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=STATUS PENGAJUAN KRS "+targetThsms+" TERKUNCI<br/>" +
							//				" PERIODE PENGAJUAN KRS BELUM DIBUKA&tipe=msgonly").forward(request,response);
							//	}
								else {
									/*
									 * kalo operator insert krs
									 */
									//System.out.println("======sini======");
									String idkur = sdb.getIndividualKurikulum(kdpst, npm);
									v = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
									/*
									 * v = list mata kuliah dalam kurikulum
									 */
									//	get kdkmk
									Vector vSdh = new Vector();
									Vector vBlm = new Vector();
									ListIterator liS = vSdh.listIterator();
									ListIterator liB = vBlm.listIterator();
									int i=0;
									if(v!=null && v.size()>0) {
										li = v.listIterator();
										while(li.hasNext()) {
											i++;
											
											String idkmk = (String)li.next();
											String kdkmk = (String)li.next();
											String nakmk = (String)li.next();
											String skstm = (String)li.next();
											String skspr = (String)li.next();
											String skslp = (String)li.next();
											String kdwpl = (String)li.next();
											String jenis = (String)li.next();
											String stkmk = (String)li.next();
											String nodos = (String)li.next();
											String semes = (String)li.next();
										
											//		cek apa ada di trnlp
											boolean match = false;
											/*
											 * cek apakah matakuliah @v sudah ada di TRNLP 
											 * jika match msukan ke Vsudah
											 */
											if(vTrnlp!=null && vTrnlp.size()>0) {
												ListIterator lip = vTrnlp.listIterator();
												while(lip.hasNext() && !match) {
													String thsmsp = (String)lip.next();
													String kdkmkp = (String)lip.next();
													String nakmkp = (String)lip.next();
													String nlakhp = (String)lip.next();
													String bobotp = (String)lip.next();
													String kdaslp = (String)lip.next();
													String nmaslp = (String)lip.next();
													String sksmkp = (String)lip.next();
													String totSksTransferedp = (String)lip.next();
													String sksas = (String)lip.next();
													String transferred = (String)lip.next();
													//			//System.out.println("trans ="+kdkmk+" vs "+kdkmkp);
													if(kdkmk.equalsIgnoreCase(kdkmkp)) {
														match = true;
														liS.add(idkmk+","+thsmsp+","+kdkmkp+","+nakmkp.replace(",","tandaKoma")+","+nlakhp+","+bobotp);
														//			li.remove();
													}
												}
											}	
											if(!match) {
												/*
												 * jika tiddak ada di trnlp cek apa ada di trnlm
												 * jika match add ke vSudah
												 */
												if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {
													ListIterator liHist = vHistKrsKhs.listIterator();
													while(liHist.hasNext() && !match) {
														String thsmsh=(String)liHist.next();
														String kdkmkh=(String)liHist.next();
														String nakmkh=(String)liHist.next();
														String nlakhh=(String)liHist.next();
														String boboth=(String)liHist.next();
														String sksmkh=(String)liHist.next();
														String kelash=(String)liHist.next();
														String sksemh=(String)liHist.next();
														String nlipsh=(String)liHist.next();
														String skstth=(String)liHist.next();
														String nlipkh=(String)liHist.next();
														String shift=(String)liHist.next();
														String krsdown=(String)liHist.next();
														String khsdown=(String)liHist.next();
														String bakprove=(String)liHist.next();
														String paprove=(String)liHist.next();
														String note=(String)liHist.next();
														String lock=(String)liHist.next();
														String baukprove=(String)liHist.next();
													
														String idkmk_ =(String)liHist.next();
														String addReq =(String)liHist.next();
														String drpReq  =(String)liHist.next();
														String npmPa =(String)liHist.next();
														String npmBak =(String)liHist.next();
														String npmBaa =(String)liHist.next();
														String npmBauk =(String)liHist.next();
														String baaProve =(String)liHist.next();
														String ktuProve =(String)liHist.next();
														String dknProve =(String)liHist.next();
														String npmKtu =(String)liHist.next();
														String npmDekan =(String)liHist.next();
														String lockMhs =(String)liHist.next();
														String kodeKampus =(String)liHist.next();
														//			//System.out.println("history ="+kdkmk+" vs "+kdkmkh);
														if(kdkmk.equalsIgnoreCase(kdkmkh)) {
															match = true;
															liS.add(idkmk+","+thsmsh+","+kdkmkh+","+nakmk.replace(",","tandaKoma")+","+nlakhh+","+boboth);
															//			li.remove();
														}	
													}
												}
											}
											//	//System.out.println("match3="+match);
											if(!match) {
												/*
												 * bila tidak ada di trnlp dan trnlm maka add vBlm
												 */
												nakmk = nakmk.replace(",","tandaKoma");
												//System.out.println("nakmk="+nakmk);
												liB.add(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes);
												//System.out.println("lib.add="+idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes);
											}
											else {
												//	//System.out.println("match");
											}
										}
										vBlm = new Vector(new LinkedHashSet(vBlm));
										//	//System.out.println("1.vBln="+vBlm.size());
										vSdh = new Vector(new LinkedHashSet(vSdh));
										request.setAttribute("vBlm", vBlm);
										request.setAttribute("vSdh", vSdh);
									
										/*
										 * updated: 
										 * get list kelas dari kelas_pool 
										 * modify vBlm & vSdh add token avail
										 */
										//System.out.println("-==targetThsms="+targetThsms);
										
										/*
										 * getListMakulDalamClassPoolVer3 upated fix penggabungan kelas agar tetap tampil dalam pilihan kelas
										 * logic : otomatis kalo bagi shift kelas yg ditutup boleh ngambil bila digabungkan
										 */
										//SearchDbClassPoll sdc = new SearchDbClassPoll(isu.getNpm());
										/*
										 * karena ini digunakan untuk insert krs, maka scope tknScopeKampus = kode_kmp dosmisili mhs terkait
										 */
										
										String tknScopeKampus = Getter.getDomisiliKampus(npm);
										Vector vCp = sdb.getListMakulDalamClassPoolVer2(targetThsms, kdpst);
									    //vCp tmp = idkmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+nakmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kode_gabung+"`"+kodeKampus+"`"+cuid;
										
										
										//ver3
										//System.out.println("-==done="+targetThsms);
										//getListMakulDalamClassPoolVer2
										String tmp = "";
										if(vCp!=null && vCp.size()>0) {
											if(vBlm!=null && vBlm.size()>0) {
												ListIterator liBlm = vBlm.listIterator();
												while(liBlm.hasNext()) {
													String brs = (String)liBlm.next();
													StringTokenizer st=new StringTokenizer(brs,",");
													String idkmk=st.nextToken();
													String kdkmk=st.nextToken();
													String nakmk=st.nextToken();
													//nakmk = nakmk.replace("tandaKoma", ",");
													String skstm=st.nextToken();
													String skspr=st.nextToken();
													String skslp=st.nextToken();
													String semes=st.nextToken();
													//		boolean match = false;
													String idkmkCp = "";
													String shiftCp = "";
													ListIterator liCp = vCp.listIterator();
													//		boolean
													while(liCp.hasNext()) {
														String brs1 = (String)liCp.next();
														//vCp tmp = idkmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+nakmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kode_gabung+"`"+kodeKampus+"`"+cuid;
														
														//System.out.println("brs1="+brs1);
														if(brs1.startsWith("idkmk")) {
															if(!idkmkCp.equalsIgnoreCase(idkmk)) {
																tmp = "";
															}	
															StringTokenizer st1 = new StringTokenizer(brs1,",");
															st1.nextToken();
															idkmkCp = st1.nextToken();
														}
														else {
															if(brs1.startsWith("shift")) {
																StringTokenizer st1 = new StringTokenizer(brs1,",");
																st1.nextToken();
																shiftCp = st1.nextToken();
																Vector vKls = (Vector)liCp.next();
																ListIterator liKls = vKls.listIterator();
																boolean match = false;
																while(liKls.hasNext()) {
																	if(idkmkCp.equalsIgnoreCase(idkmk)) {
																		match = true;
																		brs1 = (String)liKls.next();
																		if(Checker.isStringNullOrEmpty(tmp)) {
																			tmp = brs1;
																		}
																		else {
																			tmp = tmp +","+ brs1;
																		}
																	}	
																	else {
																		liKls.next();
																	}
																	//		//System.out.println(idkmk+" "+shiftCp+" "+brs1);
																}
																if(match) {
																	liBlm.set(brs+","+tmp);
																	//System.out.println("liBlm.set="+brs+","+tmp);
																}	
															}
														}
													}
												}
											}
									
											if(vSdh!=null && vSdh.size()>0) {
												ListIterator liSdh = vSdh.listIterator();
												while(liSdh.hasNext()) {
													//		boolean match = false;
													String brs = (String)liSdh.next();
													StringTokenizer st=new StringTokenizer(brs,",");
													//		liS.add(idkmk+","+thsmsp+","+kdkmkp+","+nakmkp+","+nlakhp+","+bobotp);
													String idkmk=st.nextToken();
													String thsms=st.nextToken();
													String kdkmk=st.nextToken();
													String nakmk=st.nextToken();
													//nakmk = nakmk.replace("tandaKoma", ",");
													String nlakh=st.nextToken();
													String bobot=st.nextToken();
													//				ListIterator liCp = vCp.listIterator();
													String idkmkCp = "";
													String shiftCp = "";
													ListIterator liCp = vCp.listIterator();
													while(liCp.hasNext()) {
														String brs1 = (String)liCp.next();
														if(brs1.startsWith("idkmk")) {
															if(!idkmkCp.equalsIgnoreCase(idkmk)) {
																tmp = "";
															}	
															StringTokenizer st1 = new StringTokenizer(brs1,",");
															st1.nextToken();
															idkmkCp = st1.nextToken();
														}
														else {
															if(brs1.startsWith("shift")) {
																StringTokenizer st1 = new StringTokenizer(brs1,",");
																st1.nextToken();
																shiftCp = st1.nextToken();
																Vector vKls = (Vector)liCp.next();
																ListIterator liKls = vKls.listIterator();
															
																//boolean first = true;
																boolean match = false;
																while(liKls.hasNext()) {
																	if(idkmkCp.equalsIgnoreCase(idkmk)) {
																		match = true;
																		brs1 = (String)liKls.next();
																		if(Checker.isStringNullOrEmpty(tmp)) {
																			tmp = brs1;
																		}
																		else {
																			tmp = tmp +","+ brs1;
																		}
																	}	
																	else {
																		liKls.next();
																	}
																
																}
																if(match) {
																	liSdh.set(brs+","+tmp);
																}	
															}
														}
													}	
												
												}	
											}
											/*
											 * tambah info keter mk detail + unique id
											 */
											
											SearchDbClassPoll sdb1 = new SearchDbClassPoll(isu.getNpm());
											if(vBlm!=null && vBlm.size()>0) {
												vBlm = sdb1.adhockAddInfoMkToVblm(vBlm, targetThsms, kdpst);	
											}
											if(vSdh!=null && vSdh.size()>0) {
												//System.out.println("vsudahlah");
												vSdh = sdb1.adhockAddInfoMkToVsdh(vSdh, targetThsms, kdpst);	
												//System.out.println("vsudahlah selesai");
											}
											//ListIterator liBlm = vBlm.listIterator();
											//while(liBlm.hasNext()) {
											//	//System.out.println((String)liBlm.next());
											//}
											
											/*
											 * gek apakah ATRUAN PEMILIHAN KELAS = 2 tipe only
											 */
											String thsms_krs = Checker.getThsmsKrs();
											boolean whiteListMode = false;
											/*
											 * whiteListMode diatas misleading
											 * ini utk menentukan thsms yg bukan thsms krs
											 */
											if(targetThsms!=null && !Checker.isStringNullOrEmpty(targetThsms) && !targetThsms.equalsIgnoreCase(thsms_krs)) {
												whiteListMode = true;//urusan thsms bukan shift
											}
											sdb = new SearchDb(isu.getNpm());
											/*
											 * table PILIH_KELAS_RULES adalah kondisi spesial
											 * contoh kondisi spesial : ALL_KAMPUS = true ,
											 * untuk ALL_SHIFT redundan krn dibuatnya jadul 
											 */
											//boolean sesuaiShift = sdb.pilihKelasHrsSesuaiShiftnya(kdpst,thsms_krs,v_npmhs);
											/*
											 * var sesuaiShift depricated ganti jadi allShift
											 * 
											 * ini proses jadul dimana shift untuk kelas yang di offer prodinya sendiri
											 * di filter pada tahapan berikutnya (redirect)
											 */
											boolean sesuaiShift = true;
											if(sdb.bolehPilihKelasAllShift(kdpst,thsms_krs,v_npmhs)) {
												sesuaiShift = false;
											}
											
											//boolean sesuaiShift = sdb.bolehPilihKelasAllShift(kdpst,thsms_krs,v_npmhs);
											//System.out.println("sesuaiShift--="+sesuaiShift);
											//------------------------------
											
											//get makul setara
											Vector vSetara =  sdb.getMakulYangAdaMakulSetara(kdpst, npm );
											
											/*
											 * proses filter untuk kelas yang setara yang di offer oleh prodi lainnya
											 * bussiness prosessnya :
											 * if all kampus = boleh seluruh kampus - default
											 * kalo !aalll_kampus && notWhitelist - berarti sesuai domisili msg2
											 * whitelist value = all , atao tkn Npm
											 * kalo masuk whitelist cek tkn 
											 * if not cek tknya masing2 && hrs msuk ke whitelist
											 */
											String npm_default_shift = Checker.getShiftMhs(v_npmhs);
											long npm_id_obj = Checker.getObjectId(v_npmhs);
											String npm_default_kampus = Getter.getDomisiliKampus(v_npmhs);											/*
											
											StringTokenizer st = null;
											/*
											 * untuk saat ini all shift dipake untuk kelas setara
											 * jadi  sesuai shift masih blum disentuh krn digunakan untuk proses pilih kelas yga
											 * dibuka oleh prodi itu sendiri, tapi tetep deprecated
											 * jadi proses dibawah ini adalah filterisasi untuk mk setara yang diadakan
											 * oleh prpdi lainnya dan boleh diambil
											 * mahasiswa
											 */
											
											//kalo ngga ada vsetara mngga usah repot msk sini
											//boolean allShift = false;
											//boolean allProdi = false;
											Vector vSeataraYgDibuka = null;
											//System.out.println("vsetara sizee= null");
											if(vSetara!=null && vSetara.size()>0) {
												//System.out.println("vsetara sizee= "+vSetara.size());
												vSeataraYgDibuka = sdb.getMakulSetaraYgDibuka(kdpst, v_npmhs, targetThsms, vSetara);
												//System.out.println("vSeataraYgDibuka sizee= "+vSeataraYgDibuka.size());
												//kalo vsetara ngga ada yg dibuka ngga usah repot masuk sini
												if(vSeataraYgDibuka!=null && vSeataraYgDibuka.size()>0) {
													
													ListIterator lit = null;
													//System.out.println("vSeataraYgDibuka size start = "+vSeataraYgDibuka.size());
													/*
													 * mulai di filer kelas mana yang boleh diambil berdasarkan shift, kampus,dst:
													 * utk shift : pilihannya boleh seluruh shift atau tidak
													 * untuk yang lainnya berdasarkan token
													 */
													//fileter (1) berdasarkan shift
													boolean allShift = sdb.bolehPilihKelasAllShift(kdpst,thsms_krs,v_npmhs);
													//System.out.println("allShift="+allShift);
													if(!allShift) {
														lit = vSeataraYgDibuka.listIterator();
														while(lit.hasNext()) {
															String brs = (String)lit.next();
															//bar = li.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul);
															//hilangkan yang bukan shiftnya
															if(!brs.contains(Checker.getShiftMhs(npm))) {
																lit.remove();
															}
														}	
													}
													//System.out.println("vSeataraYgDibuka size after shift = "+vSeataraYgDibuka.size());
													//fileter (2) berdasarkan prodi
													
													String  value = sdb.bolehPilihKelasAllProdi(kdpst,thsms_krs,v_npmhs);
													if(value==null || Checker.isStringNullOrEmpty(value)) {
														//System.out.println("ERROR TABEL PILIH KELAS BELUM DIISI");	
													}
													
													StringTokenizer st = new StringTokenizer(value);
													boolean allProdi = Boolean.parseBoolean(st.nextToken());
													if(allProdi) {
														String tkn_prodi = st.nextToken();
														
														//System.out.println("satatus value = "+allProdi+" "+tkn_prodi);
														lit = vSeataraYgDibuka.listIterator();
														while(lit.hasNext()) {
															String brs = (String)lit.next();
															//bar = li.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul);
															//hilangkan yang bukan shiftnya
															boolean match = false;
															st = new StringTokenizer(tkn_prodi,",");
															while(st.hasMoreTokens() && !match) {
																String kdpst_allowed = st.nextToken();
																if(brs.contains(kdpst_allowed)) {
																	/*
																	 * ke depannya utkk akurasi bisa dibuat mpe ke token kdpst nya jadi akurat
																	 */
																	match = true;
																}
															}
															if(!match) {
																lit.remove();
															}
														}
														
													}
													else {
														//System.out.println("satatus value = "+allProdi);
														//kalo !allprodi = cuma prodinya sendiri aja
													}
													//System.out.println("vSeataraYgDibuka size after prodi = "+vSeataraYgDibuka.size());
													//fileter (3) berdasarkan fak
													value = sdb.bolehPilihKelasAllFakultas(kdpst,thsms_krs,v_npmhs);
													st = new StringTokenizer(value);
													boolean allFakultas = Boolean.parseBoolean(st.nextToken());
													if(allFakultas) {
														String tkn_fak = st.nextToken();
														String tkn_prodi = Getter.getListProdiDariLainFakultas(tkn_fak);
														//System.out.println("satatus value = "+allFakultas+" "+tkn_fak);
														//System.out.println("satatus value = "+tkn_prodi);
														lit = vSeataraYgDibuka.listIterator();
														while(lit.hasNext()) {
															String brs = (String)lit.next();
															//bar = li.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul);
															//hilangkan yang bukan shiftnya
															boolean match = false;
															st = new StringTokenizer(tkn_fak,",");
															while(st.hasMoreTokens() && !match) {
																String kdpst_allowed = st.nextToken();
																if(brs.contains(kdpst_allowed)) {
																	/*
																	 * ke depannya utkk akurasi bisa dibuat mpe ke token kdpst nya jadi akurat
																	 */
																	match = true;
																}
															}
															if(!match) {
																lit.remove();
															}
														}
														
													}
													else {
														//System.out.println("satatus value = "+allProdi);
													}
													//System.out.println("vSeataraYgDibuka size after fak = "+vSeataraYgDibuka.size());
													
													
													//fileter (4) berdasarkan kampus
													value = sdb.bolehPilihKelasAllKampus(kdpst,thsms_krs,v_npmhs);
													st = new StringTokenizer(value);
													boolean allKampus = Boolean.parseBoolean(st.nextToken());
													if(allKampus) {
														String tkn_kmp = st.nextToken();
														lit = vSeataraYgDibuka.listIterator();
														while(lit.hasNext()) {
															String brs = (String)lit.next();
															//bar = li.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul);
															//hilangkan yang bukan shiftnya
															boolean match = false;
															st = new StringTokenizer(tkn_kmp,",");
															while(st.hasMoreTokens() && !match) {
																String kmp_allowed = st.nextToken();
																if(brs.contains(kmp_allowed)) {
																	/*
																	 * ke depannya utkk akurasi bisa dibuat mpe ke token kdpst nya jadi akurat
																	 */
																	match = true;
																}
															}
															if(!match) {
																lit.remove();
															}
														}
														
													}
													else {
														//System.out.println("satatus value = "+allKampus);
													}
													//System.out.println("vSeataraYgDibuka size after kmp = "+vSeataraYgDibuka.size());

												}	
											}
											
											
											/*
											 * end update 3Maret2015 -
											 */
											//boolean semuaShift = sdb.pilihKelasSemuaShift(kdpst,thsms_krs);
											//boolean shiftAlter = sdb.pilihKelasSemuaShiftJikaTidakAdaDiShiftnya(kdpst);
											
											request.setAttribute("vCp", vCp);
											if(vSeataraYgDibuka==null) {
												vSeataraYgDibuka = new Vector();
											}
											
											request.setAttribute("vSeataraYgDibuka", vSeataraYgDibuka);
											target = Constants.getRootWeb()+"/InnerFrame/Akademik/updFormKrsCp.jsp";
											String uri = request.getRequestURI();
											String url = PathFinder.getPath(uri, target);
											//	//System.out.println("cmd fwd="+cmd);
											//request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&fwdTo=/InnerFrame/Akademik/updFormKrsCp.jsp&cmd="+cmd+"&targetThsms="+targetThsms+"&sesuaiShift="+sesuaiShift+"&semuaShift="+semuaShift+"&shiftAlter="+shiftAlter).forward(request,response);
											//request.getRequestDispatcher(url+"?whiteListMode="+whiteListMode+"&objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&fwdTo=/InnerFrame/Akademik/updFormKrsCp.jsp&cmd="+cmd+"&targetThsms="+targetThsms+"&sesuaiShift="+sesuaiShift+"&semuaShift="+semuaShift).forward(request,response);
											
											request.getRequestDispatcher(url+"?whiteListMode="+whiteListMode+"&objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&fwdTo=/InnerFrame/Akademik/updFormKrsCp.jsp&cmd="+cmd+"&targetThsms="+targetThsms+"&sesuaiShift="+sesuaiShift).forward(request,response);
										}
										else {
											/*
											 * 	old style - tanpa class  pool
											 *  dan hanya operator yang boleh kalo mahasiswa harus pake cp
											 *  UPDATED:
											 *  harus via white list baru bisa pake old style
											 */
											//System.out.println("old - style");
											if(Checker.getObjName(Integer.valueOf(isu.getIdObj()).intValue()).contains("MHS")) {
												//jika usr mahasisea dan belum saatnya melakukan pengajuan berdasar kalendar
												target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
												String uri = request.getRequestURI();
												String url = PathFinder.getPath(uri, target);
												request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=STATUS PENGAJUAN KRS "+targetThsms+" TERKUNCI<br/>" +
														" BELUM ADA PILIHAN KELAS YANG TESEDIA&tipe=msgonly").forward(request,response);
											}
											else {
												//hanya operator yang bisa - tanpa class pool
												//mulai saat ini input ths,s krs harus via class pool
												if(targetThsms.equalsIgnoreCase(Checker.getThsmsKrs())) {
													target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTampleteTabStyle.jsp";
													String uri = request.getRequestURI();
													String url = PathFinder.getPath(uri, target);
													request.getRequestDispatcher(url+"?objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&backTo="+null+"&msg=STATUS PENGAJUAN KRS "+targetThsms+" TERKUNCI<br/>" +
															" BELUM ADA PILIHAN / PENGAJUAN KELAS&tipe=msgonly").forward(request,response);
												}
												else {
													/*
													 * kalo masuk sini harusnya must be white list - krn @ setTargetThsmsBasedOnNpmhs.jsp kita filter thsms whitelist aja yg bisa mpe sini 
													 */
													request.setAttribute("vCp", vCp);
													target = Constants.getRootWeb()+"/InnerFrame/Akademik/updFormKrs.jsp";
													String uri = request.getRequestURI();
													String url = PathFinder.getPath(uri, target);
													request.getRequestDispatcher(url+"?whitelist=true&kdpst_nmpst="+kdpst+","+npm+"&cmd="+cmd+"&targetThsms="+targetThsms).forward(request,response);
												}	
											}	
										}
									}	
									else {
										//kurikulum belum ditentukan
										//System.out.println("masukin kesini??");
										target = Constants.getRootWeb()+"/InnerFrame/Akademik/updFormKrs.jsp";
										String uri = request.getRequestURI();
										String url = PathFinder.getPath(uri, target);
										request.getRequestDispatcher(url+"?kdpst_nmpst="+kdpst+","+npm+"&cmd="+cmd+"&msg=Harap Tentukan Kurikulum Untuk "+nmm+" Terlebih Dahulu").forward(request,response);
									}
								}
							}
						}
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
