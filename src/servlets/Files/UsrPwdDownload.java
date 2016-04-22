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
public class UsrPwdDownload extends HttpServlet {
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
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		
		String nmm=request.getParameter("nmm");
		String npm=request.getParameter("npm");
		String usrnmm=request.getParameter("usr");
		String usrpwd=request.getParameter("pwd");
		
		SearchDb sdb = new SearchDb();
		String outNameFile = "usrPwd_"+nmm+"_"+npm;
		System.out.println("outNameFile1="+outNameFile);
		FileManagement fm = new FileManagement();
		//prepMasterUsrPwd(String thsms,String kdpst,String npm,String nmm,String usrKomaPwd,String outNameFile) {
		fm.prepMasterUsrPwd(npm, nmm, usrnmm, usrpwd,outNameFile);
		
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
