package servlets.Files;

import java.io.IOException;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ListIterator;
import java.util.Vector;
import beans.dbase.*;
import beans.setting.*;
import beans.tools.PathFinder;
import beans.folder.file.*;
import beans.login.InitSessionUsr;
/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class IjazahDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String filePath;

    // Actions ------------------------------------------------------------------------------------
	
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public KuiDownload() {
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
        // Get requested file by path info.
        //String requestedFile = request.getPathInfo();
		//response.setContentType("text/html");
	    //PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String cmd =  request.getParameter("cmd");

		//System.out.println("masuk vop");
		//update ada di 2 tmp = ProfileCivitas.java && HistoryBakCivitas.java
		int norut = isu.isAllowTo("vop");
		Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"vop",kdpst);
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
		
		System.out.println("download ijazah");
		String dbschema = isu.getDbSchema();
		SearchDb sdb = new SearchDb();
		Vector v_ija = sdb.getDataUntukDicetakDiIjazah(v_npmhs);
		//String requestedFile = request.getPathInfo();
		FileManagement fm = new FileManagement(dbschema);
		fm.prepMasterIjazahMhs(v_kdpst,v_ija);
		//fm.prepMasterKuitansiPembayaranMhs(npm,nim,nmm,kuiid, norut, tgkui, tgtrs, keter, payee, amont, pymtp, noacc, opnpm, opnmm, setor, nonpm, voidd, dbschema);
		//System.out.println("requestedFile = "+requestedFile);
        // Check if file is actually supplied to the request URI.
		/*
        if (requestedFile == null) {
            // Do your thing if the file is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
		*/
        // Decode the file name (might contain spaces and on) and prepare file object.
        //File file = new File(filePath, URLDecoder.decode(requestedFile, "UTF-8"));
		//File file = new File(filePath);
		StringTokenizer st = new StringTokenizer(v_nmmhs);
		String nmfile = "";
		while(st.hasMoreTokens()) {
			nmfile = nmfile+st.nextToken();
			if(st.hasMoreTokens()) {
				nmfile=nmfile+"_";
			}
		}
		File file = new File(Constants.getTmpFile()+"/ijazah_"+nmfile+".xlsx");
        //System.out.println("target path = "+Constants.getTmpFile()+"/"+kuiid+".xlsx");
        // Check if file actually exists in filesystem.
        if (!file.exists()) {
        	 //System.out.println("file!exist");
        	// Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        //System.out.println("20");
        // Get content type by filename.
        String contentType = getServletContext().getMimeType(file.getName());
        //System.out.println("21");
        // If content type is unknown, then set the default value.
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        // To add new content types, add new mime-mapping entry in web.xml.
        if (contentType == null) {
        	 //System.out.println("22");
            contentType = "application/octet-stream";
        }
        //System.out.println("22");
        // Init servlet response.
        response.reset(); 
        //System.out.println("23");
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        //System.out.println("24");
        response.setContentType(contentType);
        //System.out.println("25");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        //System.out.println("26");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        //System.out.println("27");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
        	 //System.out.println("28");
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            //System.out.println("29");
            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            
            //System.out.println("30");
        } finally {
        	
            // Gently close streams.
            close(output);
            close(input);
           
            
        }
        //set un editable & un printable
      	UpdateDb udb = new UpdateDb();
      	udb.setIjazahEditableAndCetakableToFalse(v_npmhs);
        //response.setContentType("text/html");
	    //PrintWriter out = response.getWriter();
        //System.out.println("done");
        //String target = Constants.getRootWeb()+"/TransitionPage/SuksesDownloadForm.jsp";
        //String uri = request.getRequestURI();
        //String url_ff = PathFinder.getPath(uri, target);
        //String target = "get.profile?id_obj="+v_id_obj+"&obj_lvl"+v_obj_lvl+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&kdpst="+v_kdpst+"&cmd=dashboard";
		//request.getRequestDispatcher(url_ff+"?id_obj="+v_id_obj+"&obj_lvl"+v_obj_lvl+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&kdpst="+v_kdpst+"&cmd=dashboard").forward(request,response);
        //target = url_ff+"?id_obj="+v_id_obj+"&obj_lvl"+v_obj_lvl+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&kdpst="+v_kdpst+"&cmd=dashboard";
		//response.setHeader("Refresh", "0; URL="+target); 
		//System.out.println("done1");
        //doPost(request,response);
        
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
                System.out.println("closing");
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
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
