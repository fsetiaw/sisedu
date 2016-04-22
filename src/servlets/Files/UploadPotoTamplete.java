package servlets.Files;

import beans.sistem.*;
import beans.folder.file.*;
import java.io.FileInputStream;
import beans.tools.*;
import java.io.IOException;

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
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
/**
 * Servlet implementation class UploadFileWebDev
 */
@WebServlet("/UploadFileWebDev")
public class UploadPotoTamplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public UploadFileWebDev() {
//        super();
        // TODO Auto-generated constructor stub
//    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(true);
    		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
    		String id_obj = request.getParameter("id_obj");
    		String nmm = request.getParameter("nmm");
    		String npm = request.getParameter("npm");
    		String obj_lvl = request.getParameter("obj_lvl");
    		String kdpst = request.getParameter("kdpst");
    		String cmd = request.getParameter("cmd");
    		String fwdPage = request.getParameter("fwdPg");
    		System.out.println("npm = "+npm);
            // get your absolute path
            //System.out.println("1");
            //String target = Constants.getRootWeb()+"/InnerFrame/Download/indexDownload.jsp";
    		String target = Constants.getRootWeb()+"/"+fwdPage;		
			String uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			String url = PathFinder.getPath(uri, target);
			url = url+"?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=upDownFile";
            //String uploadToIfImages = Constants.getUrlLoginHtmlImages()+"/";
            //String uploadToIfLoginHtml = Constants.getUrlLoginHtml()+"/";
			String uploadToBuktiBayaranFolder = Constants.getFolderBuktiBayaran()+"/";
            try {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                // no multipart form
                if (!isMultipart) {
                }
                else {
                	System.out.println("1");
                    ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                    System.out.println("2");
                    List fileItems = upload.parseRequest(request);
                    System.out.println("3");
                    Iterator iter = fileItems.iterator();
                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                        }
                        else {
                            String fullNameFile = item.getName();
                            String fullNameFileLc = fullNameFile.toLowerCase();
                            String fileName=null;
                            StringTokenizer st = new StringTokenizer(fullNameFile);
                           	System.out.println("fileseizs="+item.getSize());
                           	System.out.println("fileseizs="+item.getFieldName());
                      		System.out.println("fileseizs="+item.getName());
                       		st = new StringTokenizer(fullNameFile,".");
                            fileName = st.nextToken();
                            fileName = fileName.toLowerCase();
                            String ext = st.nextToken();
                            ext = ext.toLowerCase();
                            File fileTo = null;
                            fileTo = new File(uploadToBuktiBayaranFolder + npm+"."+ext);
                            item.write(fileTo);
                      		FileManagement fm = new FileManagement();
                        }
                    }
                    
                }
            } catch (Exception ex) {
            	System.out.println("error ex");

            	
                ex.printStackTrace();
            } finally {
                out.close();
                
            }
        }
        
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	//System.out.println("masuk doGet upload");
            //processRequest(request, response);
            doPost(request,response);
        }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	response.setContentType("text/html");
    		PrintWriter out = response.getWriter();
    		HttpSession session = request.getSession(true);
    		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
    		String id_obj = request.getParameter("id_obj");
    		String nmm = request.getParameter("nmm");
    		String npm = request.getParameter("npm");
    		String obj_lvl = request.getParameter("obj_lvl");
    		String kdpst = request.getParameter("kdpst");
    		String cmd = request.getParameter("cmd");
    		String fwdPage = request.getParameter("fwdPg");
    		System.out.println("npm = "+npm);
    		String target = Constants.getRootWeb()+"/"+fwdPage;		
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			url = url+"?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=upDownFile";
			String uploadToBuktiBayaranFolder = Constants.getFolderBuktiBayaran()+"/";

			
			out.println("Hello<br/>");

    		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
    		if (!isMultipartContent) {
    			out.println("You are not trying to upload<br/>");
    			return;
    		}
    		out.println("You are trying to upload<br/>");
    		out.println("<html><body>");
    	    out.println("<script type=\"text/javascript\">");
    	    out.println("var popwin = window.open(\"progress.jsp\")");
    	    //out.println("setTimeout(function(){ popwin.close(); window.location.href='pageB.jsp';},5000)");
    	    out.println("</script>");
    	    out.println("</body></html>");
    		FileItemFactory factory = new DiskFileItemFactory();
    		ServletFileUpload upload = new ServletFileUpload(factory);
//    		upload.setSizeMax(MAX_UPLOAD_IN_MEGS * 1024 * 1024);
    		
    		TestProgressListener testProgressListener = new TestProgressListener();
    		upload.setProgressListener(testProgressListener);

    		//HttpSession session = request.getSession();
    		session.setAttribute("testProgressListener", testProgressListener);
    		out.print(testProgressListener.getMessage());
    		out.println("<iframe id=\"tmpframe\" src=\"progress.jsp\" seamless=\"seamless\" width=\"100%\" name=\"tmpframe\"></iframe>");
    		try {
    			List<FileItem> fields = upload.parseRequest(request);
    			out.println("Number of fields: " + fields.size() + "<br/><br/>");
    			Iterator<FileItem> it = fields.iterator();
    			if (!it.hasNext()) {
    				out.println("No fields found");
    				return;
    			}
    			//out.println("<table border=\"1\">");
    			while (it.hasNext()) {
    				//out.println("<tr>");
    				FileItem fileItem = it.next();
    				boolean isFormField = fileItem.isFormField();
    				if (isFormField) {
    					//out.println("<td>regular form field</td><td>FIELD NAME: " + fileItem.getFieldName() + 
    							//"<br/>STRING: " + fileItem.getString()
    							//);
    					//out.println("</td>");
    				} else {
    					//out.println("<td>file form field</td><td>FIELD NAME: " + fileItem.getFieldName() +
//    							"<br/>STRING: " + fileItem.getString() +
    							//"<br/>NAME: " + fileItem.getName() +
    							//"<br/>CONTENT TYPE: " + fileItem.getContentType() +
    							//"<br/>SIZE (BYTES): " + fileItem.getSize() +
    							//"<br/>TO STRING: " + fileItem.toString()
    							//);
    					//out.println("</td>");
    				}
    				//out.println("</tr>");
    			}
    			//out.println("</table>");
    			
    			out.print(testProgressListener.getMessage());
    		} catch (FileUploadException e) {
    			out.println("Error: " + e.getMessage());
    			e.printStackTrace();
    		}
        }
}
