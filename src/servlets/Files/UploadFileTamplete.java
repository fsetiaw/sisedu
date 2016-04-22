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
public class UploadFileTamplete extends HttpServlet {
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
    		//=============form 1=============================
    		String tglTransCash = null;
    		String biayaSelect1 = null;
    		String biayaSelect2 = null;
    		String biayaSelect3 = null;
    		String biayaSelect4 = null;
    		String biayaSelect5 = null;
    		String biayaSelect6 = null;
    		String biayaSelect7 = null;
    		String biayaSelect8 = null;
    		String biayaSelect9 = null;
    		String select1 = null;
    		String select2 = null;
    		String select3 = null;
    		String select4 = null;
    		String select5 = null;
    		String select6 = null;
    		String select7 = null;
    		String select8 = null;
    		String select9 = null;
    		//=============end form 1=============================
    		//=============form 2=============================
    		String namaPenyetor = null;
    		String besaran = null;
    		String tglTrans = null;
    		String angsuranKe = null;
    		String gelombangKe = null;
    		String biayaJaket = null;
    		//============end form2======================
    		//=============form 3=============================
    		String bppKe = null;
    		String besaranBpp = null;
    		String pendaftaranSmsKe = null;
    		String besaranHeregistrasi = null;
    		String totSks = null;
    		String sksSmsKe = null;
    		String biayaSks = null;
    		String biayaBinaan = null;
    		String biayaDkm = null;
    		String biayaPraktik = null;
    		String biayaBimbinganSkripsi = null;
    		String biayaUjianSkripsi = null;
    		String biayaSumbanganBuku = null;
    		String biayaJurnal = null;
    		String biayaIjazah = null;
    		String biayaWisuda = null;
    		String biayaKp = null; 
    		String biayaAdmBank = null; //untuk beasiswa s1
    		//=============end form 3=============================
    		String objId = null;
    		String nmm = null;
    		String npm = null;
    		String kdpst = null;
    		String obj_lvl = null;
    		//System.out.println("tipeForm->"+tipeForm);
    		//System.out.println("nmm->"+nmm);
    		//System.out.println("npm->"+npm);

    		String targett = "get.histPymnt";
    		String fieldAndValue = (String)session.getAttribute("fieldAndValue");
    		if(fieldAndValue!=null) {
    			StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
    			/*
    			 * ingat while(loop) dibawah ini harus sama dengan pada page: 
    			 * listNamaFieldFormPembayaran.jsp
    			 * dan berefec pada f() insertPymntTransitTableForm3WithBukti
    			 */
    			while(st.hasMoreTokens()) {
    				String fieldName = st.nextToken();
    				String fieldValue = st.nextToken();
    				if(fieldName.equalsIgnoreCase("idObj_Int_Opt")) {
    					objId = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("StringfwdPageIfValid_String_Opt")) {
    					fwdPg = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("objLvl_Int_Opt")) {
    					obj_lvl = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("kdpst_String_Opt")) {
    					kdpst = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("nmm_String_Opt")) {
    					nmm = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("npm_String_Opt")) {
    					npm = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("tipeForm_String_Opt")) {
    					tipeForm = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Nama-Penyetor_Huruf_Opt")) {
    					namaPenyetor = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran_Double_Opt_Angsuran-DPP-Ke_Wajib")) {
    					besaran = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Tgl-Transaksi-Bank_Date_Wajib")) {
    					tglTrans = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Tgl-Transaksi-Cash_Date_Wajib")) {
    					tglTransCash = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Angsuran-DPP-Ke_Int_Opt_Besaran_Wajib")) {
    					angsuranKe = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Gelombang_Int_Opt_Besaran_Wajib")) {
    					gelombangKe = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Biaya-Jaket-Almamater_Double_Wajib_Besaran_Opt")) {
    					biayaJaket = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Pembayaran-BPP-Semester-Ke_Int_Opt_Besaran-Biaya-BPP_Wajib")) {
    					bppKe = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Biaya-BPP_Double_Opt_Pembayaran-BPP-Semester-Ke_Wajib")) {
    					besaranBpp = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Pembayaran-Daftar-Ulang-Semester-Ke_Int_Opt_Besaran-Biaya-Daftar-Ulang_Wajib")) {
    					pendaftaranSmsKe = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Biaya-Daftar-Ulang_Double_Opt_Pembayaran-Daftar-Ulang-Semester-Ke_Wajib")) {
    					besaranHeregistrasi = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Tot-SKS-Diambil_Double_Opt_Besaran-Biaya-SKS_Wajib")) {
    					totSks = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Pembayaran-SKS-Semester-Ke_Int_Opt_Besaran-Biaya-SKS_Wajib")) {
    					sksSmsKe = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Biaya-SKS_Double_Opt_Pembayaran-SKS-Semester-Ke_Wajib")) {
    					biayaSks = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Dana-Kemahasiswaan_Double_Opt")) {
    					biayaDkm = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Praktikum_Double_Opt")) {
    					biayaPraktik = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Bimbingan-Skripsi_Double_Opt")) {
    					biayaBimbinganSkripsi = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Ujian-Skripsi_Double_Opt")) {
    					biayaUjianSkripsi = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Sumbangan-Buku_Double_Opt")) {
    					biayaSumbanganBuku = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Jurnal_Double_Opt")) {
    					biayaJurnal = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Ijazah_Double_Opt")) {
    					biayaIjazah = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Wisuda_Double_Opt")) {
    					biayaWisuda = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Pembinaan_Double_Opt")) {
    					biayaBinaan = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Sidang-Kerja-Praktek_Double_Opt")) {
    					biayaKp = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Besaran-Biaya-Administrasi_Double_Opt")) {
    					biayaAdmBank = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-1_Double_Opt")) {
    					biayaSelect1 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-2_Double_Opt")) {
    					biayaSelect2 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-3_Double_Opt")) {
    					biayaSelect3 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-4_Double_Opt")) {
    					biayaSelect4 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-5_Double_Opt")) {
    					biayaSelect5 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-6_Double_Opt")) {
    					biayaSelect6 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-7_Double_Opt")) {
    					biayaSelect7 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-8_Double_Opt")) {
    					biayaSelect8 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("Selection-9_Double_Opt")) {
    					biayaSelect9 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection1_String_Opt")) {
    					select1 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection2_String_Opt")) {
    					select2 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection3_String_Opt")) {
    					select3 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection4_String_Opt")) {
    					select4 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection5_String_Opt")) {
    					select5 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection6_String_Opt")) {
    					select6 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection7_String_Opt")) {
    					select7 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection8_String_Opt")) {
    					select8 = ""+fieldValue;
    				}
    				else if(fieldName.equalsIgnoreCase("selection9_String_Opt")) {
    					select9 = ""+fieldValue;
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
                	target = Constants.getRootWeb()+"/AlertPage/UploadFile/emptyFile.jsp";
            		uri = request.getRequestURI();
            		System.out.println("targeting==>"+uri);
            		url = PathFinder.getPath(uri, target);
            		response.sendRedirect(url);
                	
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
                    //System.out.println("3a");
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
                        	/*
                        	 * masuk kesini bila tidak ada file yg diupload
                        	 * kalo ngga salah ini ngga ngefek
                        	 */
                        	//System.out.println("4a");
                        	target = Constants.getRootWeb()+"/AlertPage/UploadFile/emptyFile.jsp";
                    		uri = request.getRequestURI();
                    		//System.out.println("targeting==>"+uri);
                    		url = PathFinder.getPath(uri, target);
                    		response.sendRedirect(url);
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
                            			//System.out.println("7");
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
        	System.out.println("masuk doGet upload1");
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
            System.out.println("mpe");
            //request.getRequestDispatcher("get.histPymnt?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran").forward(request,response);
            //System.out.println("mpe2");
        }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	System.out.println("masuk dopost upload1");
        	doGet(request,response);
            //processRequest(request, response);
        }
}
