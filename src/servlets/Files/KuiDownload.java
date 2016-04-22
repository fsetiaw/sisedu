package servlets.Files;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import beans.setting.*;
import beans.folder.file.*;
/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class KuiDownload extends HttpServlet {
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
	  }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        // Get requested file by path info.
        //String requestedFile = request.getPathInfo();
		String npm = request.getParameter("npm");
		String nim = request.getParameter("nim");
		String nmm = request.getParameter("nmm");
		String kuiid = request.getParameter("kuiid");
		String norut = request.getParameter("norut");
		String tgkui = request.getParameter("tgkui");
		String tgtrs = request.getParameter("tgtrs");
		String keter = request.getParameter("keter");
		String payee = request.getParameter("payee");
		String amont = request.getParameter("amont");
		String pymtp = request.getParameter("pymtp");
		String noacc = request.getParameter("noacc");
		String opnpm = request.getParameter("opnpm");
		String opnmm = request.getParameter("opnmm");
		String setor = request.getParameter("setor");
		String nonpm = request.getParameter("nonpm");
		String voidd = request.getParameter("voidd");
		String dbschema = request.getParameter("schema");
		//String requestedFile = request.getPathInfo();
		FileManagement fm = new FileManagement(dbschema);
		if(keter.equalsIgnoreCase(Constants.getListKeterKasirPmb())) {
			fm.prepMasterKuitansiPmbMhs(npm,nim,nmm,kuiid, norut, tgkui, tgtrs, keter, payee, amont, pymtp, noacc, opnpm, opnmm, setor, nonpm, voidd, dbschema);
		}
		else {
			fm.prepMasterKuitansiPembayaranMhs(npm,nim,nmm,kuiid, norut, tgkui, tgtrs, keter, payee, amont, pymtp, noacc, opnpm, opnmm, setor, nonpm, voidd, dbschema);
		}
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
		File file = new File(Constants.getTmpFile()+"/"+kuiid+".xlsx");
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
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
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
