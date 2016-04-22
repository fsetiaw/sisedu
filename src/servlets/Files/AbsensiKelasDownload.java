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
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;
import beans.folder.file.*;
import beans.folder.file.absen.Kelas;
import beans.login.InitSessionUsr;
/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class AbsensiKelasDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String filePath;

    // Actions ------------------------------------------------------------------------------------
	
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public AbsensiKelasDownload() {
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
	    //HttpSession session = request.getSession(true);
		//InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
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
			System.out.println("siap download");
			Vector vListMhsCetak = (Vector)session.getAttribute("vListMhsCetak");
			/*
			 * String brs = (String)li.next();
		//li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+status);
		st = new StringTokenizer(brs,"`");
		String nmmhs = st.nextToken();
		String npmhs = st.nextToken();
		String kdpst = st.nextToken();
		String smawl = st.nextToken();
		String thsms = st.nextToken();
		String status = st.nextToken();
			 */
			String kelasInfo = request.getParameter("kelasInfo");
			//kelasInfo" value="<%=target_kmp+"`"+kdkmk+"`"+nakmk+"`"+nmmdos+"`"+shift+"`"+unique_id+"`"+idkmk+"`"+idkur+"`"+kdpst+"`"+noKlsPll %>" />
			System.out.println("kelasInfo="+kelasInfo);
			System.out.println("size="+vListMhsCetak.size());
			if((kelasInfo!=null && !Checker.isStringNullOrEmpty(kelasInfo)) && (vListMhsCetak!=null && vListMhsCetak.size()>0)) {
				
			}
			
			
			FileManagement fm = new FileManagement();
			String outfile = fm.prepMasterAbsensiKelas(kelasInfo, vListMhsCetak );
			
			
			File file = new File(outfile);
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
