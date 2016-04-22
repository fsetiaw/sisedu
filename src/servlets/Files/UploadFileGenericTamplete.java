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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
/**
 * Servlet implementation class UploadFileWebDev
 */
@WebServlet("/UploadFileWebDev")
public class UploadFileGenericTamplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(true);
    		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
    		//System.out.println("betul kesini");
    		//String target = Constants.getRootWeb()+"/InnerFrame/upAndDownloadFile.jsp";		
			//String uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			//String url = PathFinder.getPath(uri, target);
			//url = url+"?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=upDownFile";
            //String uploadToIfImages = Constants.getUrlLoginHtmlImages()+"/";
            //String uploadToIfLoginHtml = Constants.getUrlLoginHtml()+"/";
			//String uploadToFolder = Constants.getFolderBuktiBayaran()+"/tmp/";;
			String uploadToFolder = (String) session.getAttribute("saveToFolder");
			if(uploadToFolder==null || uploadToFolder.equalsIgnoreCase("null")) {
				//System.out.println("uploadToFolder is null ");
				uploadToFolder = request.getParameter("saveToFolder");
				
			}
			//System.out.println("uploadToFolder = "+uploadToFolder);
            session.removeAttribute("saveToFolder");
            try {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                if (!isMultipart) {
                	//System.out.println("not multipart");
                }
                // multipart form
                else {
                	//System.out.println("is multipart");
                    // Create a new file upload handler
                    ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                    //upload.setSizeMax(2000000);
                    //System.out.println("3a");
                    // parse requests
                    List fileItems = upload.parseRequest(request);
                    //System.out.println("3b");
                    // Process the uploaded items
                    Iterator iter = fileItems.iterator();
                    
                    int i = 0;

                    while (iter.hasNext()) {
                    	i++;
                    	//System.out.println(i);
                        FileItem item = (FileItem) iter.next();
                    	//System.out.println(item.getFieldName());
                    	//System.out.println(item.getString());
                    	//System.out.println(item.toString());
                        // a regular form field
                        
                        if (item.isFormField()) {
                        	//System.out.println("4a");
       
                        }
                        else {
                        	// upload field
                            
                        	//System.out.println("6");
                            //String fileName = item.getName();
                            String fullNameFile = item.getName();
                            //System.out.println("fullNameFile="+fullNameFile);
                            //System.out.println("uploadToFolder="+uploadToFolder);
                            //String fullNameFileLc = fullNameFile.toLowerCase();
                            String fileName=null;
                            StringTokenizer st = new StringTokenizer(fullNameFile);
                            
                            if(st.countTokens()<1) {
                            }
                            else {
                            	if(item.getSize()<1) {
                            	//empty file
                            	}
                            	else {
                            		//if(item.getSize()>2000000) {
                            		if(false) {
                            		}
                            		else {
                            			st = new StringTokenizer(fullNameFile,".");
                            			//System.out.println("fullNameFile="+fullNameFile);
                                        fileName = st.nextToken();
                                        //fileName = fileName.toLowerCase();
                                        String ext = st.nextToken();
                                        ext = ext.toLowerCase();
                                        File fileTo = null;
                                        String tmp = AskSystem.getCurrentTimeInString();
                                        tmp  = tmp.replace(".", "_");
                                        String nuFileName = fileName+"."+ext;
                                        //fileTo = new File(uploadToFolder + nuFileName+"."+ext);
                                        //fileTo = new File(uploadToFolder + nuFileName);
                                        fileTo = new File(uploadToFolder);
                                        fileTo.mkdirs();
                                        fileTo = new File(uploadToFolder +"/"+ nuFileName);
                                        session.setAttribute("nuFileName", nuFileName);

                            			item.write(fileTo);

                            			out.println("Upload File Berhasil");
                            			
                            		}	
                            	}
                            }
                            
                        }
                    }
                }
               // 
            } catch (Exception ex) {
            	System.out.println("error ex");

            	
                ex.printStackTrace();
            } finally {
            	///out.close();
                
            }
        }
        
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	//System.out.println("masuk doGet upload1");
        	//HttpSession session = request.getSession(true);
        	//String infoTarget = (String)session.getAttribute("infoTarget");
        	//String id_obj=request.getParameter("id_obj");
        	//String nmm=request.getParameter("nmm");
        	//String npm=request.getParameter("npm");
        	//String obj_lvl=request.getParameter("obj_lvl");
        	//String kdpst=request.getParameter("kdpst");
        	//String atMenu=request.getParameter("atMenu");

        	
            processRequest(request, response);
        }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	doGet(request,response);
        }
}
