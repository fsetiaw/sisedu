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
import beans.tools.Converter;
import beans.tools.PathFinder;
import beans.folder.file.*;
import beans.login.InitSessionUsr;
/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class KrsDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String filePath;

    // Actions ------------------------------------------------------------------------------------
	
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public KrsDownload() {
//        super();
        // TODO Auto-generated constructor stub
//    }
    
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		/*
	    */
	  }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			//HttpSession session = request.getSession(true);
			//InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
			SearchDb sdb = new SearchDb();
			
			
			String thsms = request.getParameter("thsms");
			thsms = Converter.convertThsmsValueOnly(thsms);
			String objId = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String kdpst = request.getParameter("kdpst");
			String obj_lvl =  request.getParameter("obj_lvl");
			String cmd =  request.getParameter("cmd");
			String tkn_info_krs = request.getParameter("tkn_krs");
			Vector vHistKrsKhsForEdit = (Vector)session.getAttribute("vHistKrsKhsForEdit");
			//System.out.println("vHistKrsKhsForEdit@KrsDownload.java="+vHistKrsKhsForEdit.size());
			//Vector vHistTmp = (Vector) session.getAttribute("vHistTmp"); 
			//System.out.println("vHistTmp@KrsDownload.java="+vHistTmp.size());
			session.removeAttribute("vHistTmp");
			//System.out.println("krs file download");
			//System.out.println("npm "+npm);
			//System.out.println("kdpst "+kdpst);
			//System.out.println("thsms "+thsms);
			String outNameFile = "krs_"+thsms+"_"+npm;
			Vector vUpd = new Vector();
			ListIterator li1 = vUpd.listIterator();
			if(vHistKrsKhsForEdit!=null) {
				ListIterator li = vHistKrsKhsForEdit.listIterator();
				while(li.hasNext()) {
					String thsmsi = (String)li.next();
					//System.out.println("thsmsi="+thsmsi);
					thsmsi = Converter.convertThsmsValueOnly(thsmsi);
					String kdkmki = (String)li.next();
					//System.out.println("thsmsi="+thsmsi);
					String nakmki = (String)li.next();
					//System.out.println("nakmki="+nakmki);
					String nlakhi = (String)li.next();
					//System.out.println("nlakhi="+nlakhi);
					String boboti = (String)li.next();
					//System.out.println("boboti="+boboti);
					String sksmki = (String)li.next();
					//System.out.println("sksmki="+sksmki);
					String kelasi = (String)li.next();
					//System.out.println("kelasi="+kelasi);
					String sksemi = (String)li.next();
					//System.out.println("sksemi="+sksemi);
					String nlipsi = (String)li.next();
					//System.out.println("nlipsi="+nlipsi);
					String skstti = (String)li.next();
					//System.out.println("skstti="+skstti);
					String nlipki = (String)li.next();
					//System.out.println("nlipki="+nlipki);
					String prev_shift = (String)li.next();
					//System.out.println("prev_shift="+prev_shift);
					String prev_krsdown = (String)li.next();
					//System.out.println("prev_krsdown="+prev_krsdown);
					String prev_khsdown = (String)li.next();
					//System.out.println("prev_khsdown="+prev_khsdown);
					String prev_bakprove = (String)li.next();
					//System.out.println("prev_bakprove="+prev_bakprove);
					String prev_paprove = (String)li.next();
					//System.out.println("prev_paprove="+prev_paprove);
					String prev_note = (String)li.next();
					//System.out.println("prev_note="+prev_note);
					String prev_lock = (String)li.next();
					//System.out.println("prev_lock="+prev_lock);
					String prev_baukprove = (String)li.next();
					//System.out.println("prev_baukprove="+prev_baukprove);
					//tambahan harus sync dengan SeacrhDb.getHistoryKrsKhs()
					String idkmk = (String)li.next();
					String addReq = (String)li.next();
					String drpReq  = (String)li.next();
					String npmPa = (String)li.next();
					String npmBak = (String)li.next();
					String npmBaa = (String)li.next();
					String npmBauk = (String)li.next();
					String baaProve = (String)li.next();
					String ktuProve = (String)li.next();
					String dknProve = (String)li.next();
					String npmKtu = (String)li.next();
					String npmDekan = (String)li.next();
					String lockMhs = (String)li.next();
					String kodeKmp = (String)li.next();
					
					if(thsms.equalsIgnoreCase(thsmsi)) {
						li1.add(thsmsi+"#&"+kdkmki+"#&"+nakmki+"#&"+nlakhi+"#&"+boboti+"#&"+sksmki+"#&"+kelasi+"#&"+sksemi+"#&"+nlipsi+"#&"+skstti+"#&"+nlipki);
					}
				}
				//li1 = vUpd.listIterator();
				
			}	
			else {	
				//System.out.println("vHistKrsKhs size = 0");
				//forward to
			}	
			FileManagement fm = new FileManagement();
			fm.prepMasterKrsMhs(thsms,kdpst, npm, nmm, vUpd,outNameFile);
			
			File file = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
	        if (!file.exists()) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
	            return;
	        }
	        String contentType = getServletContext().getMimeType(file.getName());
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }
	        response.reset(); 
	        response.setBufferSize(DEFAULT_BUFFER_SIZE);
	        response.setContentType(contentType);
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

	        // Prepare streams.
	        BufferedInputStream input = null;
	        BufferedOutputStream output = null;
	        try {
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
		}        
    }

  

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
