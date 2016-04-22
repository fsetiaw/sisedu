package servlets.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Servlet;
import java.io.*;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.dbase.pengajuan.ua.UpdateDbUa;
import beans.login.InitSessionUsr;
//import com.missiondata.fileupload.*;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.PathFinder;
import listeners.*;
/**
 * Servlet implementation class ValidateAndUploadFileTamplete
 */
@WebServlet("/ValidateAndUploadFileTamplete")
public class ValidateAndUploadFileTamplete extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
    String msg = "";
    HttpSession session = null;
    String submitButtonValue = "";
    boolean valid = true;
    boolean upload_file = false;
    String field_name = "";
    String validatedTransDate = "";
    String fwdPageIfValid = "test.jsp";
    String kdpst = "";
	String npmhs = "";
	String nmmhs = "";
	String idobj = "";
	String objlv = "";
	String cmd = "";
	boolean multipart = false;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateAndUploadFileTamplete() {
        super();
        
        // TODO Auto-generated constructor stub
    }

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		String fieldAndValue = (String)session.getAttribute("fieldAndValue");
    		session.removeAttribute("fieldAndValue");
    		System.out.println("is multipart22");
    		//String target_npm = (String)session.getAttribute("target_npm");
    		session.removeAttribute("target_npm");
    		//System.out.println("target_npm="+target_npm);
        	ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            List fileItems = upload.parseRequest(request);
            Iterator iter = fileItems.iterator();
            //System.out.println("iter size = "+ iter. )
            System.out.println("process form field" );
            	
            int i = 0;

            while (iter.hasNext()) {
            	i++;
            
            	//System.out.println(i);
            	//FileItem itemFile = null;
                FileItem item = (FileItem) iter.next();
                String fieldName = item.getFieldName();
                System.out.println(i+".fieldName1="+fieldName);
                //String 
               
                if (item.isFormField()) {
                	/*
                	 * tidak perlu diproses karena field non file valuenya diambil via session
                	 *  System.out.println("fieldAndValue="+fieldAndValue);
                	 *  ini dilakukan karen hendak merubah nama file yg diupload
                	 */
                	
                }
                else {
                //	itemFile = item;
                    
                    /*
                     * variable dibawah ini harus disesuaikan dengan form inputnya
                     * fieldAndValue=||Judul-Naskah-Ujian_String_Wajib||sdfad||npmhs_String_Opt||6500102100019||Tipe-Ujian-Akhir_Selection_Wajib||12`SHP1`SIDANG HASIL PENELITIAN I
                     */
                	
                	UpdateDbUa udb = new UpdateDbUa();
                	udb.uploadPengajuanUa(fieldAndValue,item);
                	
                	/*
                	 * variable buat redirect
                	 * fd.append("idobj_String_Opt", document.getElementById('idobj_String_Opt').value );
						fd.append("nmmhs_String_Opt", document.getElementById('nmmhs_String_Opt').value );
						fd.append("objlv_String_Opt", document.getElementById('objlv_String_Opt').value );
						fd.append("cmd_String_Opt", document.getElementById('cmd_String_Opt').value );
                	 */
                	
                	System.out.println("fieldAndValue2="+fieldAndValue);
                	StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
                    while(st.hasMoreTokens()) {
                    	String fieldNmm = st.nextToken();
                    	String fieldval = st.nextToken();
                    	if(fieldNmm.contains("nmmhs")) {
                    		nmmhs = new String(fieldval); 
                    	}
                    	else if(fieldNmm.contains("idobj")) {
                    		idobj = new String(fieldval); 
                    	}
                    	else if(fieldNmm.contains("objlv")) {
                    		objlv = new String(fieldval); 
                    	}
                    	else if(fieldNmm.contains("cmd")) {
                    		cmd = new String(fieldval); 
                    	}
                    	else if(fieldNmm.contains("npmhs")) {
                    		npmhs = new String(fieldval); 
                    	}
                    	else if(fieldNmm.contains("kdpst")) {
                    		kdpst = new String(fieldval); 
                    	}
                    }
                   

                }
                System.out.println("id_obj="+idobj);
    			System.out.println("nmm="+nmmhs);
    			System.out.println("npm="+npmhs);
    			System.out.println("obj_lvl="+objlv);
    			System.out.println("kdpst="+kdpst);
    			System.out.println("cmd="+cmd);
    			 System.out.println("gooo");
    			//if(valid) {
 	    			//session.setAttribute("fieldAndValue", field_name);
    			//	fwd = true;
    			//doGet(request,response); 
 	    		//}
 	    		//else {
 	    			
 	    		//}	
                    //response.sendRedirect("get.profile?id_obj="+idobj+"&nmm="+nmmhs+"&npm="+npmhs+"&obj_lvl="+objlv+"&kdpst="+kdpst+"&cmd="+cmd);
           
            }
            //if(valid) {
            //	fwd = true;
            //	System.out.println("go fwd");
            	doGet(request,response);
            //}	
            
           

    		
    	}
    	catch(FileUploadException e) {
    		e.printStackTrace();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("doGet ValidateAndUploadFileTamplete");
		//System.out.println("fwdPageIfValid ="+fwdPageIfValid);
		System.out.println("msg ="+msg);
		System.out.println("field_name ="+field_name);
		System.out.println("valid ="+valid);
		if(!multipart) {
		//System.out.println("fwd ="+fwd);
		//if(valid) {
		if(false) {
			//System.out.println("valid ="+valid);
			//msg="";
			msg = "<meta http-equiv=\"refresh\" content=\"0; url="+fwdPageIfValid+"?somebutton="+submitButtonValue+" \" >";
	    }
		System.out.println("msg2 ="+msg);
	    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
	    response.getWriter().write(msg); 
	    msg="";
		}
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("validate form doPusr");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
			//kode here
			
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			System.out.println("isMultipart="+isMultipart);
			if (!isMultipart) {
			//if(false) {
	        	//System.out.println("not multipart22");
	        	session = request.getSession(true);
	    		valid = true;
	    		field_name = "";
	    		Enumeration attrs =  request.getParameterNames();
	    		System.out.println("attrs = "+attrs.hasMoreElements());

	    		while(attrs.hasMoreElements()) {
	    			boolean validasiTest = true;
	    			boolean dependencyMode = false;
	    			String dependencyCol = null;
	    			String dependencyStatus = null;
	    			String elementName = (String)attrs.nextElement();
	    			String elementValue = request.getParameter(elementName);
					if(Checker.isStringNullOrEmpty(elementValue)) {
						elementValue = "null";
					}
					if(elementName.contains("fwdPageIfValid")) {
						fwdPageIfValid = ""+elementValue;
					}
					field_name=field_name+"||"+elementName+"||"+elementValue;
					
					/*
					 * UPDATED elementName terdiri dari :
					 * Besaran_Double_Opt_Biaya-Jaket-Almamater_Opt
					 * nanamCol_type_status_NamaColumnDependecy_statusBaru
					 */
					//System.out.println("elementName = "+elementName);
					StringTokenizer st = new StringTokenizer(elementName,"_");
					String namaElement = st.nextToken().replace("-", " ");
					String dataTypeElement = st.nextToken();
					String StatusElement = st.nextToken();
					/*
					 * check fpr dependency
					 */
					if(st.hasMoreTokens()) {
						//System.out.println("dependecyMode");
						dependencyMode = true;
						dependencyCol = st.nextToken();
						dependencyCol = dependencyCol.replace("-", " ");
						dependencyStatus = st.nextToken();
						boolean matchCol = false;
						Enumeration attrTmp =  request.getParameterNames();
						//System.out.println("attrTmp.hasMoreElements()="+attrTmp.hasMoreElements());
						while(attrTmp.hasMoreElements()&&!matchCol) {
							String elementNameTmp = (String)attrTmp.nextElement();
							StringTokenizer st2 = new StringTokenizer(elementNameTmp,"_");
							String namaElementTmp = st2.nextToken().replace("-", " ");
							String dataTypeElementTmp = st2.nextToken();
							String StatusElementTmp = st2.nextToken();
							//System.out.println(namaElementTmp+" vs "+dependencyCol);
							//if(elementNameTmp!=null && elementNameTmp.toUpperCase().contains(dependencyCol.toUpperCase())) {
							if(namaElementTmp!=null && namaElementTmp.equalsIgnoreCase(dependencyCol)) {
								matchCol = true;
								String elementValueTmp = request.getParameter(elementNameTmp);
								//System.out.println("matchCol="+matchCol);
								//System.out.println("elementValueTmp="+elementValueTmp);
								if(!Checker.isStringNullOrEmpty(elementValueTmp)) {
									//value col dipendenci tidak null jadi merubah status_element sesuai dependenci
									StatusElement = ""+dependencyStatus;
								}
								else {
									//tidak ada perubahan status
								}
								//System.out.println("StatusElement="+StatusElement);
								//System.out.println("dependencyStatus="+dependencyStatus);
							}
						}
					}
					
					if(StatusElement.contains("Wajib")) {
						String alasan_err = "";
						if(dataTypeElement.contains("Double")) {
							st = new StringTokenizer(elementName,"_");
							validasiTest = validasiAngka(elementValue);
							alasan_err="Harap diisi hanya dengan angka.<br/>";
						}
						else if(dataTypeElement.contains("Tgl")||dataTypeElement.contains("Date")) {
							st = new StringTokenizer(elementName,"_");
							validasiTest = validasiDate(elementValue);
							alasan_err="Harap diisi sesuai dengan format yang ditentukan (tgl/bln/tahun).<br/>";
						}
						else if(dataTypeElement.contains("Int")||dataTypeElement.contains("Integer")) {
							st = new StringTokenizer(elementName,"_");
							validasiTest = validasiInt(elementValue);
						}
						else if(dataTypeElement.contains("Hape")||dataTypeElement.contains("Telp")) {
							st = new StringTokenizer(elementName,"_");
							validasiTest = validasiLong(elementValue);
							alasan_err="Harap diisi dengan hanya menggunakan angka.<br/>";
						}	
						else if(dataTypeElement.contains("Email")) {
							st = new StringTokenizer(elementName,"_");
							validasiTest = validasiEmail(elementValue);
						}
						else if(dataTypeElement.contains("huruf")||dataTypeElement.contains("Huruf")) {
							st = new StringTokenizer(elementName,"_");
							validasiTest = validasiHuruf(elementValue);
							alasan_err="Harap diisi dan hanya dengan huruf.<br/>";
						}
						else if(dataTypeElement.contains("String")) {
							st = new StringTokenizer(elementName,"_");
							validasiTest = validasiString(elementValue);
							alasan_err="Harap diisi.<br/>";
						}
						else if(dataTypeElement.contains("Selection")) {
							st = new StringTokenizer(elementName,"_");
							validasiTest = validasiSelection(elementValue);
							alasan_err="Harap dipilih.<br/>";
						}
						if(!validasiTest) {
							valid = false;
							if(msg==null) {
								msg = "'"+ namaElement +"', "+ alasan_err;
							}
							else {
								msg = msg +"'"+ namaElement +"', "+ alasan_err;
							}
						}
					}
	    		}


	    		if(valid) {
	    			session.setAttribute("fieldAndValue", field_name);
	    			msg = "";
	    			//upload_file = true;
	    		}
	    		//else {
	    		doGet(request,response);
	    		//}	

	    		
	        }
	        // multipart form
	        else {
	        	
	        	multipart = true;
	        	doGet(request,response);
	        	            
	        }
			
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			
			//response.sendRedirect("test.jsp");
			//request.getRequestDispatcher("get.profile?id_obj="+idobj+"&nmm="+nmmhs+"&npm="+npmhs+"&obj_lvl="+objlv+"&kdpst="+kdpst+"&cmd="+cmd).forward(request,response);
			
		}
			
	} 
	
	public boolean validasiAngka(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			try {
				Double.valueOf(value).doubleValue();
			}
			catch(Exception e) {
				valid = false;
			}
		}
		return valid;
	}
	
	public boolean validasiInt(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			try {
				Integer.valueOf(value).intValue();
			}
			catch(Exception e) {
				valid = false;
			}
		}	
		return valid;
	}
	
	public boolean validasiSelection(String value) {
		boolean valid = true;
		if(value==null || Checker.isStringNullOrEmpty(value)) {
			valid = false;
		}
		return valid;
	}
	
	public boolean validasiLong(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			try {
				Long.valueOf(value).longValue();
			}
			catch(Exception e) {
				valid = false;
			}
		}	
		return valid;
	}
	
	public boolean validasiEmail(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			if(!value.contains("@")&&!value.contains(".")&&value.length()<7) {
				valid = false;
			}
		}	
		return valid;
	}	
	
	public boolean validasiDate(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			try {
				StringTokenizer st = null;
				if(value.contains("/")) {
					st = new StringTokenizer(value,"/");
				}
				else {
					st = new StringTokenizer(value,"-");
				}
				//System.out.println("cont token = "+st.countTokens());
				if(st.countTokens()!=3) {
					valid = false;
				}
				else {
					
					String tgl = st.nextToken();
					String bln = st.nextToken();
					String tahun = st.nextToken();
					
					if(tgl.length()!=4 && tahun.length()!=4) {
						valid = false; //minimal hrs ada 4 digit utk menandakan tahun
					}
					else {
						//cek apa urutnya sesuai tgl/bln/tahun
						if(tgl.length()==4) {
							String tmp = ""+tahun;
							tahun = ""+tgl;
							tgl = ""+tmp;
						}
						String tmp = tahun+"-"+bln+"-"+tgl;
						System.out.println("validating date = "+tmp);
						java.sql.Date.valueOf(tmp);
					}
				}
				
			}
			catch(Exception e) {
				valid = false;
			}
		}	
		return valid;
	}
	
	public boolean validasiHuruf(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else if(value==null || Checker.isStringNullOrEmpty(value)) {
				valid = false;
		}
		else {
			char[] chars = value.toCharArray();
			for (char c : chars) {
			
				if(!Character.isLetter(c) && !Character.isSpaceChar(c)) {
					valid = false;
				}
			}
		}	
	    return valid;
	}
	
	public boolean validasiString(String value) {
		boolean valid = true;
		//System.out.println("validasiString="+value);
		if(value==null || Checker.isStringNullOrEmpty(value)) {
			valid = false;
		}
		
	    return valid;
	}
}
