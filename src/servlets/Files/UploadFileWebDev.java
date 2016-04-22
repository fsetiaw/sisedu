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
public class UploadFileWebDev extends HttpServlet {
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
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(true);
    		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
            // get your absolute path
            //System.out.println("1");
            String target = Constants.getRootWeb()+"/InnerFrame/Download/indexDownload.jsp";
			String uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			String url = PathFinder.getPath(uri, target);
            String uploadToIfImages = Constants.getUrlLoginHtmlImages()+"/";
            String uploadToIfLoginHtml = Constants.getUrlLoginHtml()+"/";
            try {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                // no multipart form
                if (!isMultipart) {
                	//String target = Constants.getRootWeb()+"/AlertPage/UploadFile/emptyFile.jsp";
            		//String uri = request.getRequestURI();
            		//System.out.println(target+" / "+uri);
            		//String url = PathFinder.getPath(uri, target);
            		//response.sendRedirect(url);
                	
                	//System.out.println("2");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet UploadServlet</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>No Multipart Files(???)</h1>");
                    out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                    out.println("</body>");
                    out.println("</html>");
                }
                // multipart form
                else {
                	//System.out.println("3");
                    // Create a new file upload handler
                    ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                    //upload.setSizeMax(2000000);
                    System.out.println("3a");
                    // parse requests
                    List fileItems = upload.parseRequest(request);
                    System.out.println("3b");
                    // Process the uploaded items
                    Iterator iter = fileItems.iterator();
                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                    	System.out.println("4");
                        // a regular form field
                        if (item.isFormField()) {
                        }
                        
                        // upload field
                        else {
                        	System.out.println("6");
                            //String fileName = item.getName();
                            String fullNameFile = item.getName();
                            String fullNameFileLc = fullNameFile.toLowerCase();
                            String fileName=null;
                            
                            System.out.println("6b");
                            //long pjg = fileTo.length();
                            System.out.println("file size = "+item.getSize());
                            StringTokenizer st = new StringTokenizer(fullNameFile);
                            if(st.countTokens()<1) {
                            	out.println("<html>");
                        		out.println("<head>");
                        		out.println("<title>Servlet UploadServlet</title>");
                        		out.println("</head>");
                        		out.println("<body>");
                        		out.println("<h1>Pilih file terlebih dahulu</h1>");
                        		out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                        		out.println("</body>");
                        		out.println("</html>");
                            }
                            else {
                            	//FileInputStream fis = new FileInputStream(fileTo);
                            	if(item.getSize()<1) {
                            	//empty file
                            		out.println("<html>");
                            		out.println("<head>");
                            		out.println("<title>Servlet UploadServlet</title>");
                            		out.println("</head>");
                            		out.println("<body>");
                            		out.println("<h1> File Gagal diUpload, Filse Size = 0 mb (kosong)</h1>");
                            		out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                            		out.println("</body>");
                            		out.println("</html>");
                            	}
                            	else {
                            		if(item.getSize()>2000000) {
                            			out.println("<html>");
                                        out.println("<head>");
                                        out.println("<title>Servlet UploadServlet</title>");
                                        out.println("</head>");
                                        out.println("<body>");
                                        //diatur di setMaxSize diatas
                                        out.println("<h1> File Gagal diUpload, Filse Size > 2mb</h1>");
                                        out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                                        out.println("</body>");
                                        out.println("</html>");
                            		}
                            		else {
                            			st = new StringTokenizer(fullNameFile,".");
                                        fileName = st.nextToken();
                                        fileName = fileName.toLowerCase();
                                        String ext = st.nextToken();
                                        ext = ext.toLowerCase();
                                        //fileName = fileName+"-"+AskSystem.getCurrentTimeInString()+"_"+isu.getIdUser()+"."+ext;
                                        //String idUser = ""+isu.getIdUser();
                                        //System.out.println("6a");
                                        //System.out.println("filename="+fileName);
                                        //System.out.println("uploadto=");
                                        File fileTo = null;
                                        CharSequence cs1 = ".html";
                                        CharSequence cs2 = ".php";
                                        CharSequence cs3 = ".jsp";
                                        CharSequence cs4 = ".css";
                                        CharSequence cs5 = ".htm";
                                        CharSequence csImg1 = ".gif";
                                        CharSequence csImg2 = ".jpg";
                                        CharSequence csImg3 = ".png";
                                        System.out.println("filename = "+fileName);
                                        System.out.println("ext = "+ext);
                                        //fileName = fileName+"."+ext;
                                    
                                        if(fullNameFileLc.contains(cs1)||fullNameFileLc.contains(cs2)||fullNameFileLc.contains(cs3)||fullNameFileLc.contains(cs4)||fullNameFileLc.contains(cs5)) {
                                        	fileTo = new File(uploadToIfLoginHtml + fullNameFile);
                                        	System.out.println(uploadToIfLoginHtml);
                                        }
                                        else {
                                        	if(fullNameFileLc.contains(csImg1)||fullNameFileLc.contains(csImg2)||fullNameFileLc.contains(csImg3)) {
                                        		fileTo = new File(uploadToIfImages + fullNameFile);
                                        		System.out.println(uploadToIfImages);
                                        	}	
                                        }
                                        //File fileTo = new File(uploadTo + fileName);
                            			item.write(fileTo);
                            			FileManagement fm = new FileManagement();
                            			System.out.println("6c");
                            			//out.println("<html>");
                            			//out.println("<head>");
                            			//out.println("<meta http-equiv=\"refresh\" content=\"2;url=http://www.google.co.id/\">");
                            			//out.println("<title>Servlet UploadServlet</title>");
                            			//out.println("</head>");
                            			//out.println("<body>");
                            		//out.println("<h1> success write to " + fileTo.getAbsolutePath() + "</h1>");
                            			out.println("<h1> Upload File Berhasil </h1>");
                            			out.println(fm.prosesUploadFile(fileName)+"<br/>");
                            			out.println(" harap tunggu, sedang proses isi file ");
                            			//out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                            			//out.println("</body>");
                            			//out.println("</html>");
                            		}	
                            	}
                            }	
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
        	System.out.println("masuk doGet upload");
            processRequest(request, response);
        }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	//System.out.println("masuk dopost upload");
        	doGet(request,response);
            //processRequest(request, response);
        }
}
