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
public class UploadFileTamplete_v_old extends HttpServlet {
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
    		
    		//session.setAttribute("infoTarget", objId+"$"+nmm+"$"+npm+"$"+kdpst+"$"+obj_lvl+"$"+tglTransaksiBank+"$"+amnt);
    		/*
    		String infoTarget = (String)session.getAttribute("infoTarget");
    		session.removeAttribute("infoTarget");
    		StringTokenizer st = new StringTokenizer(infoTarget,"$");
    		String id_obj = st.nextToken();
    		String nmm = st.nextToken();
    		String npm = st.nextToken();
    		String kdpst = st.nextToken();
    		String obj_lvl = st.nextToken();
    		String tglTransaksiBank = st.nextToken();
    		String amnt = st.nextToken();
    		System.out.println("npm = "+npm+" "+tglTransaksiBank+" "+amnt);
            // get your absolute path
            //System.out.println("1");
            //String target = Constants.getRootWeb()+"/InnerFrame/Download/indexDownload.jsp";
             * 
             */
    		
    		/*
    		* deklare all form variable here !!!
    		*/
    		String tipeForm = null;
    		String fwdPg=null;
    		//=============form 2=============================
    		String namaPenyetor = null;
    		String besaran = null;
    		String tglTrans = null;
    		String angsuranKe = null;
    		String gelombangKe = null;
    		//============end form2======================

    		String objId = null;
    		String nmm = null;
    		String npm = null;
    		String kdpst = null;
    		String obj_lvl = null;
    		//System.out.println("tipeForm->"+tipeForm);
    		//System.out.println("nmm->"+nmm);
    		//System.out.println("npm->"+npm);


    		String targett = Constants.getRootWeb()+"/get.histPymnt";
    		String fieldAndValue = (String)session.getAttribute("fieldAndValue");
    		if(fieldAndValue!=null) {
    			StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
    			while(st.hasMoreTokens()) {
    				String fieldName = st.nextToken();
    				String fieldValue = st.nextToken();
    				if(fieldName.equalsIgnoreCase("idObj_Int_Opt")) {
    					objId = ""+fieldValue;
    				}
    				else {
    					if(fieldName.equalsIgnoreCase("StringfwdPageIfValid_String_Opt")) {
    						fwdPg = ""+fieldValue;
    					}
    					else {
    						if(fieldName.equalsIgnoreCase("objLvl_Int_Opt")) {
    							obj_lvl = ""+fieldValue;
    						}
    						else {
    							if(fieldName.equalsIgnoreCase("kdpst_String_Opt")) {
    								kdpst = ""+fieldValue;
    							}
    							else {
    								if(fieldName.equalsIgnoreCase("nmm_String_Opt")) {
    									nmm = ""+fieldValue;
    								}
    								else {
    									if(fieldName.equalsIgnoreCase("npm_String_Opt")) {
    										npm = ""+fieldValue;
    									}
    									else {
    										if(fieldName.equalsIgnoreCase("tipeForm_String_Opt")) {
    											tipeForm = ""+fieldValue;
    										}
    										else {
    											if(fieldName.equalsIgnoreCase("Nama-Penyetor_Huruf_Opt")) {
    												namaPenyetor = ""+fieldValue;
    											}
    											else {
    												if(fieldName.equalsIgnoreCase("Besaran_Double_Wajib")) {
    													besaran = ""+fieldValue;
    												}
    												else {
    													if(fieldName.equalsIgnoreCase("Tgl-Transaksi_Date_Wajib")) {
    														tglTrans = ""+fieldValue;
    													}
    													else {
    														if(fieldName.equalsIgnoreCase("Angsuran-DPP-Ke_Int_Wajib")) {
    															angsuranKe = ""+fieldValue;
    														}
    														else {
    															if(fieldName.equalsIgnoreCase("Gelombang_Int_Wajib")) {
    																gelombangKe = ""+fieldValue;
    															}
    														}
    													}
    												}
    											}
    										}
    									}
    								}
    							}
    						}
    					}	
    				}
    			}
    		}
    		
    		String target = Constants.getRootWeb()+"/InnerFrame/upAndDownloadFile.jsp";		
			String uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			String url = PathFinder.getPath(uri, target);
			url = url+"?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=upDownFile";
            //String uploadToIfImages = Constants.getUrlLoginHtmlImages()+"/";
            //String uploadToIfLoginHtml = Constants.getUrlLoginHtml()+"/";
			String uploadToFolder = Constants.getFolderBuktiBayaran()+"/"+npm+"/";;
            try {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                if (!isMultipart) {
                	//String target = Constants.getRootWeb()+"/AlertPage/UploadFile/emptyFile.jsp";
            		//String uri = request.getRequestURI();
            		//System.out.println(target+" / "+uri);
            		//String url = PathFinder.getPath(uri, target);
            		//response.sendRedirect(url);
                	
                	//System.out.println("2");
                    ///out.println("<html>");
                    ///out.println("<head>");
                    ///out.println("<title>Servlet UploadServlet</title>");
                    ///out.println("</head>");
                    ///out.println("<body>");
                    ///out.println("<h1>No Multipart Files(???)</h1>");
                    ///out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                    ///out.println("</body>");
                    ///out.println("</html>");
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
                    //System.out.println("3b");
                    // Process the uploaded items
                    Iterator iter = fileItems.iterator();
                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                    	//System.out.println("4");
                        // a regular form field
                        if (item.isFormField()) {
                        	//System.out.println("4a");
                        }
                        
                        // upload field
                        else {
                        	//System.out.println("6");
                            //String fileName = item.getName();
                            String fullNameFile = item.getName();
                            String fullNameFileLc = fullNameFile.toLowerCase();
                            String fileName=null;
                            
                            //System.out.println("6b");
                            //long pjg = fileTo.length();
                            //System.out.println("file size = "+item.getSize());
                            StringTokenizer st = new StringTokenizer(fullNameFile);
                            if(st.countTokens()<1) {
                            	///out.println("<html>");
                        		///out.println("<head>");
                        		///out.println("<title>Servlet UploadServlet</title>");
                        		///out.println("</head>");
                        		///out.println("<body>");
                        		///out.println("<h1>Pilih file terlebih dahulu</h1>");
                        		///out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                        		///out.println("</body>");
                        		///out.println("</html>");
                            }
                            else {
                            	//FileInputStream fis = new FileInputStream(fileTo);
                            	if(item.getSize()<1) {
                            	//empty file
                            		///out.println("<html>");
                            		///out.println("<head>");
                            		///out.println("<title>Servlet UploadServlet</title>");
                            		///out.println("</head>");
                            		///out.println("<body>");
                            		///out.println("<h1> File Gagal diUpload, Filse Size = 0 mb (kosong)</h1>");
                            		///out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                            		///out.println("</body>");
                            		///out.println("</html>");
                            	}
                            	else {
                            		//if(item.getSize()>2000000) {
                            		if(false) {
                            			///out.println("<html>");
                                        ///out.println("<head>");
                            			///out.println("<title>Servlet UploadServlet</title>");
                            			///out.println("</head>");
                            			///out.println("<body>");
                                        //diatur di setMaxSize diatas
                            			///out.println("<h1> File Gagal diUpload, Filse Size > 2mb</h1>");
                            			///out.println("<a href=\""+url+"\">klik untuk kembali</a>");
                            			///out.println("</body>");
                            			///out.println("</html>");
                            		}
                            		else {
                            			st = new StringTokenizer(fullNameFile,".");
                            			//System.out.println("fullNameFile="+fullNameFile);
                                        fileName = st.nextToken();
                                        fileName = fileName.toLowerCase();
                                        //System.out.println("fileName="+fileName);
                                        //System.out.println("npm="+npm);
                                        //System.out.println("fullNameFile="+fullNameFile);
                                        String ext = st.nextToken();
                                        ext = ext.toLowerCase();
                                        File fileTo = null;
                                        String tmp = AskSystem.getCurrentTimeInString();
                                        tmp  = tmp.replace(".", "_");
                                        String nuFileName = npm+"_"+tmp+"."+ext;
                                        //fileTo = new File(uploadToFolder + nuFileName+"."+ext);
                                        //fileTo = new File(uploadToFolder + nuFileName);
                                        fileTo = new File(uploadToFolder);
                                        fileTo.mkdirs();
                                        fileTo = new File(uploadToFolder + nuFileName);
                                        session.setAttribute("nuFileName", nuFileName);
 /*                                   	
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
*/                                        
                                        //File fileTo = new File(uploadTo + fileName);
                            			item.write(fileTo);
                            			//FileManagement fm = new FileManagement();
                            			//System.out.println("6c");
                            			//out.println("<html>");
                            			//out.println("<head>");
                            			//out.println("<meta http-equiv=\"refresh\" content=\"2;url=http://www.google.co.id/\">");
                            			//out.println("<title>Servlet UploadServlet</title>");
                            			//out.println("</head>");
                            			//out.println("<body>");
                            		//out.println("<h1> success write to " + fileTo.getAbsolutePath() + "</h1>");
                            			out.println("Upload File Berhasil");
                            			//out.println(fm.prosesUploadFile(fileName)+"<br/>");
                            			//out.println(" harap tunggu, sedang proses isi file ");
                            			//out.println("<br/>"+url+"<br/>");
                            			//out.println("<a href=\""+url+"\">selesai</a>");
                            			//out.println("</body>");
                            			//out.println("</html>");
                            			//target = Constants.getRootWeb()+"/InnerFrame/Akademik/listKelas.jsp";
                            			//uri = request.getRequestURI();
                            			//url = PathFinder.getPath(uri, target);
                            			//System.out.println("ff_callerPahe="+callerPage);
                            			
                            			
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
        	System.out.println("masuk doGet upload");
        	//HttpSession session = request.getSession(true);
        	//String infoTarget = (String)session.getAttribute("infoTarget");
        	String id_obj=request.getParameter("id_obj");
        	String nmm=request.getParameter("nmm");
        	String npm=request.getParameter("npm");
        	String obj_lvl=request.getParameter("obj_lvl");
        	String kdpst=request.getParameter("kdpst");
        	String atMenu=request.getParameter("atMenu");
    		//session.removeAttribute("infoTarget");
    		//StringTokenizer st = new StringTokenizer(infoTarget,"$");
    		//String id_obj = st.nextToken();
    		//String nmm = st.nextToken();
    		//String npm = st.nextToken();
    		//String kdpst = st.nextToken();
    		//String obj_lvl = st.nextToken();
    		//String tglTransaksiBank = st.nextToken();
    		//String amnt = st.nextToken();
    		//System.out.println("npm = "+npm+" "+tglTransaksiBank+" "+amnt);
        	
            processRequest(request, response);
            //System.out.println("mpe");
            //request.getRequestDispatcher("get.histPymnt?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran").forward(request,response);
            //System.out.println("mpe2");
        }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	//System.out.println("masuk dopost upload");
        	doGet(request,response);
            //processRequest(request, response);
        }
}
