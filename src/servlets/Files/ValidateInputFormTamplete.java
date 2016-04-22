package servlets.Files;

import java.io.IOException;
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
//import com.missiondata.fileupload.*;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import listeners.*;
/**
 * Servlet implementation class ValidateInputFormTamplete
 */
@WebServlet("/ValidateInputFormTamplete")
public class ValidateInputFormTamplete extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
    String msg = "";
    
    String submitButtonValue = "";
    boolean valid = true;
    String field_name = "";
    String validatedTransDate = "";
    String fwdPageIfValid = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateInputFormTamplete() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		////System.out.println("doGet ValidateInputFormTamplete");
		////System.out.println("fwdPageIfValid ="+fwdPageIfValid);
		////System.out.println("msg ="+msg);
		////System.out.println("valid ="+valid);
		if(valid) {
			msg = "<meta http-equiv=\"refresh\" content=\"0; url="+fwdPageIfValid+"?somebutton="+submitButtonValue+" \" >";
	    }
		
	    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
	    response.getWriter().write(msg); 
	    msg="";
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//System.out.println("validate form doPusr tamplete");
		//StringTokenizer st = null;
		/*
		 * perhatikan huruf pertama = huruf besar
		 */
		//wajib ada field "elementValue" untuk forward value
		//format = Nama_Tipe-data_Status
		//Status = Wajib,Opt
		//Tipe-data = Int,Double,String,TimeStamp,Email,Date,Time,Huruf,Angka,Hape,Telp
		HttpSession session = request.getSession(true);
		valid = true;
		field_name = "";
		Enumeration attrs =  request.getParameterNames();
		//String []targetAkun = request.getParameterValues("namaAkun_String_Wajib");
		//if(targetAkun!=null && targetAkun.length>0) {
		//	session.setAttribute("targetAkun", targetAkun);
		//}
		//String sumberDana = request.getParameter("Sumber-Dana_Huruf_Opt");
		//session.setAttribute("sumberDana", sumberDana);
		
		//button value
		//String submitButtonValue = request.getParameter("somebutton_String_Opt");//value tolaj
		//String validatedTransDate = request.getParameter("Tgl-Setor_Date_Wajib");//nu trans date yg sudah diverifikasi
		////System.out.println("validatedTransDate @ ValidateInputFormTamplete.java="+validatedTransDate);
		//if(submitButtonValue==null || Checker.isStringNullOrEmpty(submitButtonValue)) {
		//	submitButtonValue = request.getParameter("somebutton1_String_Opt");//value terima
		//}
		//session.setAttribute("submitButtonValue", submitButtonValue);
		//session.setAttribute("validatedTransDate", validatedTransDate);
		//String somebutotonValue =null;
		while(attrs.hasMoreElements()) {
			//boolean validasiTest = true;
			//boolean dependencyMode = false;
			//String dependencyCol = null;
			//String dependencyStatus = null;
			String elementName = (String)attrs.nextElement();
			//khusus button aja 
			//if(elementName.equalsIgnoreCase("somebutton")) {
			if(false) {
				submitButtonValue=request.getParameter("somebutton");
				
			}
			else {
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
				////System.out.println("elementName = "+elementName);
				StringTokenizer st = new StringTokenizer(elementName,"_");
				String namaElement = st.nextToken().replace("-", " ");
				String dataTypeElement = st.nextToken();
				String StatusElement = st.nextToken();
				boolean validasiTest = true;
				/*
				 * check fpr dependency
				 */
				
				
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
			}//end else
		}


		
		if(valid) {
			session.setAttribute("fieldAndValue", field_name);
		}
		doGet(request,response);
			
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
	
	public boolean validasiSelection(String value) {
		boolean valid = true;
		if(value==null || Checker.isStringNullOrEmpty(value)) {
			valid = false;
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
				////System.out.println("cont token = "+st.countTokens());
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
						//System.out.println("validating date = "+tmp);
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
		////System.out.println("validasiString="+value);
		if(value==null || Checker.isStringNullOrEmpty(value)) {
			valid = false;
		}
		
	    return valid;
	}
}
