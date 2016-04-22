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
public class UploadFileGenericTamplete_v1 extends HttpServlet {
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

	  }
	
	/*
	 *processRequest balik ke doGet pada versi ini, kenapa harus dipisah aneh !!!!!
	 */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            //response.setContentType("text/html;charset=UTF-8");
            
    }
        
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	//System.out.println("bener masuk doGet upload1_v1");
        	PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(true);
    		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");

    		String target = Constants.getRootWeb()+"/InnerFrame/upAndDownloadFile.jsp";		
			String uri = request.getRequestURI();
			////System.out.println(target+" / "+uri);
			String url = PathFinder.getPath(uri, target);
			
			/*
			 * varible untuk upload bahan ajar
			 */
			String atMenu = null;
			String target_kdkmk = null;
			String target_npm = null;
			String target_nakmk = null;
			String target_kdpst = null;
			String target_idkur = null;
			String intendedFileName = null;
			String tipe_bahan_ajar = null;
			String kdpst_usr = null;
			String target_obj_lvl = null;
			String target_nmm = null;
			String target_id_obj = null;
			

			//----------end var bahan ajar ------------
			
			
			//String uploadToFolder = (String) session.getAttribute("saveToFolder");
			//if(uploadToFolder==null || uploadToFolder.equalsIgnoreCase("null")) {
				////System.out.println("uploadToFolder is null ");
			//String uploadToFolder = request.getParameter("saveToFolder");
			//System.out.println("uploadToFolder1="+uploadToFolder);
			//}
			//session.removeAttribute("saveToFolder");
			
			
			
            try {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                if (!isMultipart) {
                	////System.out.println("not multipart");
                }
                // multipart form
                else {
                	////System.out.println("is multipart");
                    ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                    List fileItems = upload.parseRequest(request);
                    Iterator iter = fileItems.iterator();
                    
                    int i = 0;

                    while (iter.hasNext()) {
                    	i++;
                    	//System.out.println(i);
                        FileItem item = (FileItem) iter.next();
                        String fieldName = item.getFieldName();
                        //System.out.println("fieldName="+fieldName);
                        if (item.isFormField()) {
                        	//===process upload bahanajar
                        	if(fieldName.equalsIgnoreCase("intendedFileName"))  {
                        		intendedFileName = new String(item.getString());	
                        		//System.out.println("intendedFileName1="+intendedFileName);
                        	}
                        	else if(fieldName.equalsIgnoreCase("target_kdkmk"))  {
                        		target_kdkmk = new String(item.getString());
                        	}
                        	else if(fieldName.equalsIgnoreCase("target_kdpst"))  {
                        		target_kdpst = new String(item.getString());
                        	} 
                        	else if(fieldName.equalsIgnoreCase("target_idkur"))  {
                        		target_idkur = new String(item.getString());
                        	} 
                        	else if(fieldName.equalsIgnoreCase("atMenu"))  {//kemungkinan jadi var umum
                        		atMenu = new String(item.getString());
                        	}
                        	else if(fieldName.equalsIgnoreCase("tipe_bahan_ajar"))  {//kemungkinan jadi var umum
                        		tipe_bahan_ajar = new String(item.getString());
                        	}
                        	else if(fieldName.equalsIgnoreCase("target_nakmk"))  {//kemungkinan jadi var umum
                        		target_nakmk = new String(item.getString());
                        	}
                        	else if(fieldName.equalsIgnoreCase("target_npm"))  {//kemungkinan jadi var umum
                        		target_npm = new String(item.getString());
                        	}
                        	else if(fieldName.equalsIgnoreCase("kdpst_usr"))  {//kemungkinan jadi var umum
                        		kdpst_usr = new String(item.getString());
                        	}
                        	else if(fieldName.equalsIgnoreCase("target_obj_lvl"))  {//kemungkinan jadi var umum
                        		target_obj_lvl = new String(item.getString());
                        	}
                        	else if(fieldName.equalsIgnoreCase("target_nmm"))  {//kemungkinan jadi var umum
                        		target_nmm = new String(item.getString());
                        	}
                        	else if(fieldName.equalsIgnoreCase("target_id_obj"))  {//kemungkinan jadi var umum
                        		target_id_obj = new String(item.getString());
                        	}
                       
                        	//===end process upload bahanajar
                        	
                			////System.out.println("intendedFileName is  "+ intendedFileName);
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
                            			////System.out.println("fullNameFile="+fullNameFile);
                                        fileName = st.nextToken();
                                        //fileName = fileName.toLowerCase();
                                        String ext = st.nextToken();
                                        ext = ext.toLowerCase();
                                        File fileTo = null;
                                        String nuFileName = fileName+"."+ext;
                                        String tmp = AskSystem.getCurrentTimeInString();
                                        tmp  = tmp.replace(".", "_");
                                        if(intendedFileName!=null &&! Checker.isStringNullOrEmpty(intendedFileName)) {
                                        	nuFileName = intendedFileName+"."+ext;
                                        }
                                        //System.out.println("intendedFileName="+intendedFileName);
                                        //fileTo = new File(uploadToFolder + nuFileName+"."+ext);
                                        //fileTo = new File(uploadToFolder + nuFileName);
                                        /*
                                         * target upload folder
                                         */
                                        //uploadToFolder = uploadToFolder+"/"+isu.getNpm();
                                        if(target_npm==null || Checker.isStringNullOrEmpty(target_npm)) {
                                        	target_npm = isu.getNpm();
                                        }
                                        String target_path = Checker.getBahanAjarTargetPath(tipe_bahan_ajar, target_kdpst, target_kdkmk, target_npm,target_idkur);
                                        //String target_path = Checker.getBahanAjarTargetPath(tipe_bahan_ajar, target_kdpst, target_kdkmk, isu.getNpm(),target_idkur);
                                        //uploadToFolder = uploadToFolder+"/"+target_kdpst+"/"+target_kdkmk+"/"+tipe_bahan_ajar+"/"+isu.getNpm();
                                        String uploadToFolder = new String(target_path);
                                        //System.out.println("uploadToFolder2="+uploadToFolder);
                                        fileTo = new File(uploadToFolder);
                                        fileTo.mkdirs();
                                        fileTo = new File(uploadToFolder +"/"+ nuFileName);
                                        //session.setAttribute("nuFileName", nuFileName);

                            			item.write(fileTo);
                            			
                            			String fwdTo="index.jsp";
                            			/*
                            			out.println("<html>");
                            			out.println("<head>");
                            			out.println("</head>");
                            			out.println("<body>");
                            			out.println("Upload File Berhasil");
                            			out.print("<META HTTP-EQUIV=\"refresh\" CONTENT=\"2; URL="+Constants.getRootWeb()+"/"+fwdTo+"?atMenu=none>");
                            			out.println("</body>");
                            			out.println("</html>");
                            			*/
                            			
                            			if(atMenu!=null && atMenu.equalsIgnoreCase("mba")) {
                            				target = "prep.bahanAjarGivenMk?kdpst_mask="+kdpst_usr+"&obj_lvl="+target_obj_lvl+"&npm="+target_npm+"&nmm="+target_nmm+"&id_obj="+target_id_obj+"&atMenu=mba&cmd=mba&kdkmk="+target_kdkmk+"&kdpst="+target_kdpst+"&idkur="+target_idkur+"&nakmk="+target_nakmk;
                            				//System.out.println("target = "+target);
                            				//String nu_target= "prep.bahanAjarGivenMk?&atMenu=mba&cmd=mba&nakmk=<%=nakmk %>&kdkmk=<%=kdkmk %>&kdpst=<%=kdpst %>&idkur=<%=idkur %>"><%=nakmk %></a></B> </label>
                            				request.getRequestDispatcher(target).forward(request,response);
                            			}
                            			
                            			//String target1 = Constants.getRootWeb()+"/"+fwdTo; 
                            			//String uri1 = request.getRequestURI(); 
                            			//String url_ff = PathFinder.getPath(uri1, target1);
                            			////System.out.println("urlff="+url_ff);
                            			// 
                            			
                            		}	
                            	}
                            }
                            
                        }
                    }
                }
               // 
            } catch (Exception ex) {
            	System.out.println("error ex disini");

            	
                ex.printStackTrace();
            } finally {
            	///out.close();
                
            }
        }
        
        
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	doGet(request,response);
        }
}
