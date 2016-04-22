package servlets.Files;

import beans.sistem.*;
import beans.dbase.pengajuan.ua.SearchDbUa;
import beans.dbase.pengajuan.ua.UpdateDbUa;
import beans.folder.file.*;

import java.io.FileInputStream;

import beans.tools.*;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.jdbc.pool.DataSource;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
/**
 * Servlet implementation class UploadFileWebDev
 */
@WebServlet("/UploadFileWebDev_v1")
public class UploadFileTamplete_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String idobj;
	String nmmhs;
	String npmhs;
	String objlv;
	String kdpst;
	String cmd;
	String nuFileName;
	String fieldAndValue;;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public UploadFileWebDev() {
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            //response.setContentType("text/html;charset=UTF-8");
            
    		
    		try {
        		//fieldAndValue = (String)session.getAttribute("fieldAndValue");
        		//session.removeAttribute("fieldAndValue");
        		//System.out.println("is multipart22");
        		//String target_npm = (String)session.getAttribute("target_npm");
        		//session.removeAttribute("target_npm");
        		////System.out.println("target_npm="+target_npm);
            	ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                List fileItems = upload.parseRequest(request);
                Iterator iter = fileItems.iterator();
                ////System.out.println("iter size = "+ iter. )
                //System.out.println("process form field" );
                	
                int i = 0;

                while (iter.hasNext()) {
                	i++;
                
                	////System.out.println(i);
                	//FileItem itemFile = null;
                    FileItem item = (FileItem) iter.next();
                    //String fieldName = item.getFieldName();
                    ////System.out.println(i+".fieldName1="+fieldName);
                    //String 
                   
                    if (item.isFormField()) {
                    	/*
                    	 * tidak perlu diproses karena field non file valuenya diambil via session
                    	 *  //System.out.println("fieldAndValue="+fieldAndValue);
                    	 *  ini dilakukan karen hendak merubah nama file yg diupload
                    	 */
                    	String target = Constants.getRootWeb()+"/AlertPage/UploadFile/emptyFile.jsp";
                		String uri = request.getRequestURI();
                		////System.out.println("targeting==>"+uri);
                		String url = PathFinder.getPath(uri, target);
                		response.sendRedirect(url);
                    }
                    else {
                    //	itemFile = item;
                        
                        /*
                         * variable dibawah ini harus disesuaikan dengan form inputnya
                         * fieldAndValue=||Judul-Naskah-Ujian_String_Wajib||sdfad||npmhs_String_Opt||6500102100019||Tipe-Ujian-Akhir_Selection_Wajib||12`SHP1`SIDANG HASIL PENELITIAN I
                         */
                    	
                    	//UpdateDbUa udb = new UpdateDbUa();
                    	//udb.uploadPengajuanUa(fieldAndValue,item);
                    	String thsms_now = Checker.getThsmsNow();
                    	String nmmhs = "";
                    	String judul = "";
                        npmhs = "";
                        String tipe_ua = "";
                        String idkmk = "";
                        String nakmk = "";
                        String kdkmk = "";
                        String kdpst = "";
                    	
                    		
                        StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
                        while(st.hasMoreTokens()) {
                            String fieldNmm = st.nextToken();
                            String fieldval = st.nextToken();
                            if(fieldNmm.contains("Judul")) {
                            	judul = new String(fieldval); 
                            }
                            else if(fieldNmm.contains("npmhs")) {
                            	npmhs = new String(fieldval); 
                            }
                            else if(fieldNmm.contains("Tipe")) {
                            	tipe_ua = new String(fieldval); 
                            }
                            else if(fieldNmm.contains("kdpst")) {
                            	kdpst = new String(fieldval); 
                            }
                        }
                        st = new StringTokenizer(tipe_ua,"`");
                        while(st.hasMoreTokens()) {
                           	idkmk = st.nextToken();
                           	kdkmk = st.nextToken();
                           	kdkmk = kdkmk.replace(" ", "_");
                           	nakmk = st.nextToken();
                            	
                        }
                            ////System.out.println(i+".fieldName2="+fieldName);
                            
                        long millis = System.currentTimeMillis();
                            
                        String fullNameFile = item.getName();
                        String fileName="";
                        String fileExt="";
                        st = new StringTokenizer(fullNameFile,"."); 
                        int count_token = st.countTokens();
                        for(int j=1; j<count_token; j++) { //untuk file yg namanya ada titik pula
                            	fileName = fileName+st.nextToken();
                        }
                            
                            
                            /*
                             * disini nama original file tidak dibutuhkan dan diganti dengan millis;
                             */
                            
                        fileExt = st.nextToken();
                        fileExt = fileExt.toLowerCase();
                        File fileTo = null;
                            //String namaFile = kdkmk+"_"+millis;
                        nuFileName = kdkmk+"_"+millis+"."+fileExt;
                        String root_folder_distinc_mhs = Checker.getRootFolderIndividualMhs(npmhs);
                        String target_folder = root_folder_distinc_mhs+"/UA/"+kdkmk;
                        //System.out.println("root_folder_distinc_mhs="+root_folder_distinc_mhs) ;
                        //System.out.println("nuFileName="+nuFileName) ;
                        fileTo = new File(target_folder);
                        fileTo.mkdirs();
                        fileTo = new File(target_folder +"/"+ nuFileName);
                        item.write(fileTo);
                    	/*
                    	 * variable buat redirect
                    	 * fd.append("idobj_String_Opt", document.getElementById('idobj_String_Opt').value );
    						fd.append("nmmhs_String_Opt", document.getElementById('nmmhs_String_Opt').value );
    						fd.append("objlv_String_Opt", document.getElementById('objlv_String_Opt').value );
    						fd.append("cmd_String_Opt", document.getElementById('cmd_String_Opt').value );
                    	 */
                        /*
                    	st = new StringTokenizer(fieldAndValue,"||");
                        while(st.hasMoreTokens()) {
                        	String fieldNmm = st.nextToken();
                        	String fieldval = st.nextToken();
                        	if(fieldNmm.contains("nmmhs")) {
                        		nmmhs = new String(fieldval); 
                        	}
                        	else if(fieldNmm.contains("idobj")) {
                        		idobj = new String(fieldval); 
                        	}
                        	else if(fieldNmm.contains("objlv")) {
                        		objlv = new String(fieldval); 
                        	}
                        	else if(fieldNmm.contains("cmd")) {
                        		cmd = new String(fieldval); 
                        	}
                        	else if(fieldNmm.contains("npmhs")) {
                        		npmhs = new String(fieldval); 
                        	}
                        	else if(fieldNmm.contains("kdpst")) {
                        		kdpst = new String(fieldval); 
                        	}
                        }
                        */
                    }
                }    
        		
        	}
        	catch(FileUploadException e) {
        		e.printStackTrace();
        	}
        	catch(Exception e) {
        		e.printStackTrace();
 
            } finally {
            	///out.close();
            }    
        
        }
        
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	//System.out.println("masuk doGet upload1_v1");
        	//HttpSession session = request.getSession(true);
        	//String infoTarget = (String)session.getAttribute("infoTarget");
        	//String id_obj=request.getParameter("id_obj");
        	//String nmm=request.getParameter("nmm");
        	//String npm=request.getParameter("npm");
        	//String obj_lvl=request.getParameter("obj_lvl");
        	//String kdpst=request.getParameter("kdpst");
        	//String atMenu=request.getParameter("atMenu");
    		//session.removeAttribute("infoTarget");
    		//StringTokenizer st = new StringTokenizer(infoTarget,"$");
    		//String id_obj = st.nextToken();
    		//String nmm = st.nextToken();
    		//String npm = st.nextToken();
    		//String kdpst = st.nextToken();
    		//String obj_lvl = st.nextToken();
    		//String tglTransaksiBank = st.nextToken();
    		//String amnt = st.nextToken();
    		////System.out.println("npm = "+npm+" "+tglTransaksiBank+" "+amnt);
        	PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(true);
    		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
        	fieldAndValue = (String)session.getAttribute("fieldAndValue");
            processRequest(request, response);//bagian upload file
        	//String fieldAndValue = (String)session.getAttribute("fieldAndValue");
            long idobj = Checker.getObjectId(npmhs);
            //System.out.println("idobj long="+idobj);
            //System.out.println("idobj="+idobj);
           
            boolean isOprApprovee = isu.isUsrAllowTo_updated("UAA", npmhs);
            //System.out.println("isOprApprovee="+isOprApprovee);
        	UpdateDbUa udb = new UpdateDbUa();//bagian update info ke table
        	String msg = udb.uploadInfoPengajuanAtDb(nuFileName,fieldAndValue,idobj);
        	//System.out.println("pesannya="+msg);
        	StringTokenizer st = new StringTokenizer(msg,"`");
        	String id_pengajuan_ua_just_updated = null;
        	if(st.countTokens()>1) {
        		id_pengajuan_ua_just_updated = new String(st.nextToken());
        		msg = st.nextToken();
        	}
        	//System.out.println("id_pengajuan_ua_just_updated="+id_pengajuan_ua_just_updated);
        	//System.out.println("pesannya="+msg);
        	session.setAttribute("status_update", msg);
        	
        	SearchDbUa sdm = new SearchDbUa();
        	if(isOprApprovee && id_pengajuan_ua_just_updated!=null) {
        		
        		Vector vPengajuanLangsungApproval = sdm.getPengajuanUa(Long.parseLong(id_pengajuan_ua_just_updated));
        		vPengajuanLangsungApproval = sdm.getYourRoleAtasPengajuan(vPengajuanLangsungApproval);
        		vPengajuanLangsungApproval = sdm.addInfoNmmhs(vPengajuanLangsungApproval);
        		vPengajuanLangsungApproval = sdm.addInfoPengajuanRules(vPengajuanLangsungApproval);
				
				session.setAttribute("goto_approval", vPengajuanLangsungApproval);
        		//request.getRequestDispatcher("process.approvalPengajuanUa?your_role="+your_role_+"&id="+id_+"&thsms="+thsms_+"&kdpst="+kdpst_+"&npmhs="+npmhs_+"&status_akhir="+status_akhir_+"&skedul_date="+skedul_date_+"&realisasi_date="+realisasi_date_+"&kdkmk="+kdkmk_+"&file_name="+file_name_+"&updtm="+updtm_+"&skedul_time="+skedul_time_+"&judul="+judul_+"&show_owner="+show_owner_+"&tkn_show_approvee="+tkn_show_approvee_+"&tkn_id_approvee="+ tkn_id_approvee_+"&show_monitoree="+ show_monitoree_+"&idobj="+ idobj+"&nmmhs="+ nmmhs_+"&rule_tkn_approvee_id="+rule_tkn_approvee_id_+"&urutan="+urutan_+"&tkn_approvee_nickname="+tkn_approvee_nickname_+"&approval=Terima Pengajuan").forward(request,response);
				/*
				 * REDIRECT DILAKUKAN DI JSP = UPLOAD.COMPLETER
				 */
        	}
        	else {
        		/*
				 * REDIRECT DILAKUKAN DI JSP = UPLOAD.COMPLETER
				 */
            
        	}
        }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	//System.out.println("masuk dopost upload1_v1");
        	doGet(request,response);
            //processRequest(request, response);
        }
}
