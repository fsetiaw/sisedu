package servlets.Files;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import beans.setting.*;
import beans.tools.PathFinder;
import beans.folder.file.*;
import java.util.StringTokenizer;
/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class DownloadForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String filePath;

    // Actions ------------------------------------------------------------------------------------
	
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public DownloadForm() {
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
        //String requestedFile = request.getPath&&Info();
		/*
		 * proses dari download upload page
		 */
		System.out.println("download form");
		String form_spmi = request.getParameter("spmi_form");
		System.out.println("form = "+form_spmi);
		String formType = request.getParameter("tipeForm");
		String kdpst_keter = request.getParameter("kdpst_keter");
		if(formType==null && kdpst_keter==null && form_spmi==null) {
			String target = Constants.getRootWeb()+"/InnerFrame/Download/indexDownload.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
		//response.sendRedirect(url_ff);
			request.getRequestDispatcher(url_ff).forward(request,response);
		}
		else {
			if(form_spmi==null) {
				StringTokenizer st = new StringTokenizer(kdpst_keter,"_");
				String kdpst = st.nextToken();
				String keter = "";
				while(st.hasMoreTokens()) {
					keter = keter+st.nextToken();
					if(st.hasMoreTokens()) {
						keter = keter+" ";
					}
				}
				String dbschema = request.getParameter("schema");
			//	String requestedFile = request.getPathInfo();
				FileManagement fm = new FileManagement(dbschema);
				
				String namafile = fm.getFormKurikulum(kdpst, keter);
				File file = new File(namafile);
			//	System.out.println("target path = "+Constants.getTmpFile()+"/form_kurikulum_"+kdpst+".xlsx");
			// 	Check if file actually exists in filesystem.
				if (!file.exists()) {
            // 	Do your thing if the file appears to be non-existing.
            // 	Throw an exception, or send 404, or show default/warning page, or just ignore it.
					response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
					return;
				}
				
			// Get content type by filename.
				String contentType = getServletContext().getMimeType(file.getName());
				
			// 	If content type is unknown, then set the default value.
			// 	For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
			// 	To add new content types, add new mime-mapping entry in web.xml.
				if (contentType == null) {
					contentType = "application/octet-stream";
				}
				
			// 	Init servlet response.
				response.reset();
				response.setBufferSize(DEFAULT_BUFFER_SIZE);
				response.setContentType(contentType);
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

				// Prepare streams.
				BufferedInputStream input = null;
				BufferedOutputStream output = null;
				
				try {
            // 	Open streams.
					input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
					output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
					
            // 	Write file contents to response.
					byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
					int length;
					while ((length = input.read(buffer)) > 0) {
						output.write(buffer, 0, length);
					}
				} finally {
            // 	Gently close streams.
					close(output);
					close(input);
				
				}
			}
			else {
				/*
				 * proses dari spmi download form page
				 */
				boolean file_found = false;
				File file = new File(form_spmi+".xlsx");
				if(file.exists()) {
					file_found = true;
				}
				else {
					file = new File(form_spmi+".xls");
					if(file.exists()) {
						file_found = true;
					}
					else {
						file = new File(form_spmi+".doc");
						if(file.exists()) {
							file_found = true;
						}
						else {
							file = new File(form_spmi+".docx");
							if(file.exists()) {
								file_found = true;
							}
						}
					}
				}
				//File file1 = new File(form_spmi+".xls");
				//System.out.println(form_spmi+".xlsx");
				//System.out.println("target path = "+Constants.getTmpFile()+"/form_kurikulum_"+kdpst+".xlsx");
				//Check if file actually exists in filesystem.
					
				if(file_found) {
					System.out.println("file ="+file.getName());
					//Get content type by filename.
					String contentType = getServletContext().getMimeType(file.getName());
					
					// If content type is unknown, then set the default value.
					// 	For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
					// 	To add new content types, add new mime-mapping entry in web.xml.
					if (contentType == null) {
						contentType = "application/octet-stream";
					}
					
					// 	Init servlet response.
					response.reset();
					response.setBufferSize(DEFAULT_BUFFER_SIZE);
					response.setContentType(contentType);
					response.setHeader("Content-Length", String.valueOf(file.length()));
					response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
					// Prepare streams.
					BufferedInputStream input = null;
					BufferedOutputStream output = null;
				
					try {
						// 	Open streams.
						input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
						output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
					
						// 	Write file contents to response.
						byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
						int length;
						while ((length = input.read(buffer)) > 0) {
							output.write(buffer, 0, length);
						}
					} finally {
						// 	Gently close streams.
						close(output);
						close(input);
				
					}
				}
				/*
				else {
					if(file1.exists()) {
						System.out.println("file1 ="+file1.getName());
						//Get content type by filename.
						String contentType = getServletContext().getMimeType(file1.getName());
					
						// If content type is unknown, then set the default value.
						// 	For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
						// 	To add new content types, add new mime-mapping entry in web.xml.
						if (contentType == null) {
							contentType = "application/octet-stream";
						}
					
						// 	Init servlet response.
						response.reset();
						response.setBufferSize(DEFAULT_BUFFER_SIZE);
						response.setContentType(contentType);
						response.setHeader("Content-Length", String.valueOf(file1.length()));
						response.setHeader("Content-Disposition", "attachment; filename=\"" + file1.getName() + "\"");
						// Prepare streams.
						BufferedInputStream input = null;
						BufferedOutputStream output = null;
						
						try {
							// 	Open streams.
							input = new BufferedInputStream(new FileInputStream(file1), DEFAULT_BUFFER_SIZE);
							output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
						
							// 	Write file contents to response.
							byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
							int length;
							while ((length = input.read(buffer)) > 0) {
								output.write(buffer, 0, length);
							}
						} finally {
							// 	Gently close streams.
							close(output);
							close(input);
						}
					}
					else {
						if(!file.exists() && !file1.exists()) {
				            // 	Do your thing if the file appears to be non-existing.
				            // 	Throw an exception, or send 404, or show default/warning page, or just ignore it.
							System.out.println("nofile");
							response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
							return;
						}
					}
				}
				*/
			}
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
		//response.setContentType("text/html");
		//String target = Constants.getRootWeb()+"/InnerFrame/Download/indexDownload.jsp";
		//String uri = request.getRequestURI();
		//String url_ff = PathFinder.getPath(uri, target);
		//response.sendRedirect(url_ff);
	}

}
