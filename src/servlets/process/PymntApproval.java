package servlets.process;

import java.io.IOException;
import beans.dbase.keu.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

import java.util.ListIterator;
import java.util.Vector;
import java.util.StringTokenizer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
/**
 * Servlet implementation class PymntApproval
 */
@WebServlet("/PymntApproval")
public class PymntApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PymntApproval() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String target = null;
		//System.out.println("doget pymntApproval");
		HttpSession session = request.getSession(true);
		Vector vReqAprKeu= (Vector)session.getAttribute("vReqAprKeu");  
		JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");
		String submitButtonValue = (String)session.getAttribute("submitButtonValue");
		//validatedTransDate
		String validatedTransDate = (String)session.getAttribute("validatedTransDate");
		session.removeAttribute("validatedTransDate");
		//System.out.println("validatedTransDate @ ptmmntApppeo="+validatedTransDate);
		session.setAttribute("submitButtonValue",submitButtonValue);
		String field_name = (String)session.getAttribute("fieldAndValue");
		String []targetAkun = (String[]) session.getAttribute("targetAkun");
		
		String sumberDana = (String) session.getAttribute("sumberDana");
		session.removeAttribute("targetAkun");
		session.removeAttribute("sumberDana");
		String idsumberDana = null;
		String namaSumberDana  = null;
		if(sumberDana!=null && !Checker.isStringNullOrEmpty(sumberDana)) {
			StringTokenizer st = new StringTokenizer(sumberDana,",");
			
			idsumberDana = st.nextToken();
			namaSumberDana  = st.nextToken();
		}
		
		//System.out.println("targetAkun lengt = "+targetAkun.length);
		//System.out.println("submitButtonValue lengt = "+submitButtonValue);
		for(int i=0;targetAkun!=null && i<targetAkun.length;i++) {
			//System.out.println(targetAkun[i]);
		}
		//String noKuiReq_Int_Wajib = 
		//System.out.println("field_name="+field_name);
		//System.out.println("somebutton="+submitButtonValue);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		UpdateDbKeu udb = new UpdateDbKeu(isu.getNpm());
		/*
		 * sebelum update harus dicek dulu status akhirnya, krn bermain dengan session variable
		 * jadi kalo dah ada yg duluan update, ngga perlu update (multiplr approval operator case)
		*/ 
		if(submitButtonValue.equalsIgnoreCase("tolak")) {
			String keterDetail_besaran = udb.tolakBuktiPembayaranJsonStyle(field_name, jsoaPymntReq);
			
			if(keterDetail_besaran!=null) {
				StringTokenizer st = new StringTokenizer(keterDetail_besaran,"||");
				if(st.countTokens()>1) {
					String keter = st.nextToken();//ngga kepake
					String besaran = st.nextToken();//ngga kepake
					
					boolean match = false;
					st = new StringTokenizer(field_name,"||");
					//get id target
		    		String element = null;
					String value = null;
					String content = null;
					String alasanPenolakan = null;
					String targetNpmhs = null;
					String targetKdpst = null;
					String tglSetorBank = null;
		    		while(st.hasMoreTokens()) {
		    			element = st.nextToken();
		    			String tmpValue = st.nextToken();
		    			//if(element.contains("kuiidReqested")) {
		    			if(element.contains("noKuiReq")) {
		    				//match = true;
		    				value = ""+tmpValue;
		    			}
		    			if(element.contains("msgContent")) {
		    				content = ""+tmpValue;
		    			}
		    			if(element.contains("targetNpmhs")) {
		    				targetNpmhs = ""+tmpValue;
		    			}
		    			if(element.contains("targetKdpst")) {
		    				targetKdpst = ""+tmpValue;
		    			}
		    			if(element.contains("Alasan-Penolakan")) {
		    				alasanPenolakan = ""+tmpValue;
		    			}
		    			if(element.contains("Tgl-Setor_Date_Wajib")) {
		    				tglSetorBank = ""+tmpValue;
		    			}
		    			
		    		}
		    		//System.out.println("tglSetorBank @ pymntApproval.java = "+tglSetorBank);
		    		//send pesan telah ditolak utk mhs, ,
		    		String isCreatorPetugas = null;
		    		String operNickname = isu.getObjNickNameGivenObjId();
		    		//dibikin umum jadi kalo kepala bak dirubah ke bak aja
		    		String operNicknameHapusKepala = null;
		    		if(operNickname!=null) {
		    			st = new StringTokenizer(operNickname,",");
		    			operNicknameHapusKepala = st.nextToken();
		    			if(operNicknameHapusKepala!=null && !Checker.isStringNullOrEmpty(operNicknameHapusKepala)) {
		    				operNicknameHapusKepala = operNicknameHapusKepala.replace("KEPALA", "");
		    				st = new StringTokenizer(operNicknameHapusKepala);
		    				operNicknameHapusKepala = "";
		    				while(st.hasMoreTokens()) {
		    					operNicknameHapusKepala = operNicknameHapusKepala+st.nextToken();
		    					if(st.hasMoreTokens()) {
		    						operNicknameHapusKepala=operNicknameHapusKepala+" ";
		    					}
		    				}
		    			}
		    		}
		    		//deprecated
		    		//kayaknya salah - ini mah selalu operator
		    		if(operNickname.contains("OPERATOR")) {
		    			isCreatorPetugas = "true";
		    		}
		    		else {
		    			isCreatorPetugas = "false";
		    		}
		    		//proses diatas di ganti ma ini
		    		try {
		    			if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
			    			JSONObject job = jsoaPymntReq.getJSONObject(0);
			    			String NpmYgInputTransaksi = job.getString("OPNPMPYMNT");
			    			String targetKdpstTransaksi = job.getString("KDPSTPYMNT");
			    			JSONArray joa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/npm/"+NpmYgInputTransaksi+"/nickname");
			    			if(joa!=null && joa.length()>0) {
			    				JSONObject jobTmp = joa.getJSONObject(0);
			    				operNickname = jobTmp.getString("OBJ_NICKNAME");
			    				//System.out.println("operNickname="+operNickname);
			    				if(operNickname.contains("OPERATOR")) {
					    			isCreatorPetugas = "true";
					    		}
					    		else {
					    			isCreatorPetugas = "false";
					    		}
			    				//System.out.println("isCreatorPetugas="+isCreatorPetugas);
			    			}
			    		}	
		    		}
		    		catch(JSONException je) {
		    			je.printStackTrace();
		    		}
		    		
		    		
		    		String targetObjNickName = udb.getObjNicknameGivenNpmhs(targetKdpst, targetNpmhs);
		    		/*
		    		 * post berita - pada pengirim, karena bisa jadi yg input adalah operator
		    		 */
		    		if(Boolean.parseBoolean(isCreatorPetugas)) {
		    			//System.out.println("yes petugas");
		    			String approvee = Constants.getDefaultPymntReqApprovee(Checker.getKdjen(targetKdpst));
		    			operNicknameHapusKepala = ""+operNicknameHapusKepala;
		    			targetObjNickName = Constants.getDefaultTargetNicknameBilaPembayaranDiinputOlehOperator(Converter.getKdjen(targetKdpst));
		    			udb.postMainTopicTargetedIndividualNpmhs(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITOLAK, Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, null, "true", isCreatorPetugas);
		    		}
		    		else {
		    			udb.postMainTopicTargetedIndividualNpmhs(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITOLAK, dikarenakan "+alasanPenolakan+", Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, targetNpmhs, "true", isCreatorPetugas);
		    		}
		    		match = false;
		    		//ganti ke json stye

		    		JSONArray jsoa = new JSONArray();
		    		if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
		    			for(int i=0;i<jsoaPymntReq.length();i++) {
		    				try {
		    					JSONObject joba = jsoaPymntReq.getJSONObject(i);
		    					st = new StringTokenizer(field_name,"||");
								String id = "";
								while(st.hasMoreTokens()) {
									String elemenNama = st.nextToken();
									//if(elemenNama.contains("kuiidReqested")) {
									if(elemenNama.contains("noKuiReq")) {	
										id = st.nextToken();
									}
								}	
								//if(id.equalsIgnoreCase(value)) {
								if(!joba.getString("NORUTPYMNT").equalsIgnoreCase(id)) {
									//li.remove();
									jsoa.put(joba);
									//match = true;
								}
		    				
		    				}
		    				catch(JSONException e) {
		    					e.printStackTrace();
		    				}
		    				
		    			}
		    		}
					//if(vReqAprKeu!=null && vReqAprKeu.size()>0) {
					//	target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp";
					//	session.removeAttribute("vReqAprKeu");
					//	session.setAttribute("vReqAprKeu", vReqAprKeu);
					//}
					if(jsoa!=null && jsoa.length()>0) {
						target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp";
						session.removeAttribute("jsoaPymntReq");
						session.setAttribute("jsoaPymntReq", jsoa);
					}
					else {
						target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
						session.removeAttribute("jsoaPymntReq");
						jsoaPymntReq = new JSONArray();
						session.setAttribute("jsoaPymntReq", jsoaPymntReq);
						//session.removeAttribute("vReqAprKeu");
						//vReqAprKeu = new Vector();
						//session.setAttribute("vReqAprKeu", vReqAprKeu);

					}	
				}
			}
		}
		else {
			//kalo terima dibikin mark as read aja deh kalo operator
			if(submitButtonValue.equalsIgnoreCase("terima")) {
				//int i = udb.terimaBuktiPembayaran(field_name, vReqAprKeu);
				int j = 0;
				//perlaihan dalam rangka penambahan sumberdana
				//jadi kalo sumber dana null berearati pake fungsi yg lama
				if(idsumberDana!=null && !Checker.isStringNullOrEmpty(idsumberDana)) {
					j = udb.terimaBuktiPembayaranJsonStyle(field_name, jsoaPymntReq, targetAkun, validatedTransDate, idsumberDana);
				}
				else {
					j = udb.terimaBuktiPembayaranJsonStyle(field_name, jsoaPymntReq, targetAkun, validatedTransDate);
				}
				//jika update sukses maka remove element dgn reqidTerkait  dari vReqAprKeu
				
				if(j>0) {
					
					boolean match = false;
					StringTokenizer st = new StringTokenizer(field_name,"||");
					//get id target
		    		String element = null;
					String value = null;
					String content = null;
					String targetNpmhs = null;
					String targetKdpst = null;
		    		while(st.hasMoreTokens()) {
		    			element = st.nextToken();
		    			String tmpValue = st.nextToken();
		    			if(element.contains("noKuiReq")) {
		    				//match = true;
		    				value = ""+tmpValue;
		    			}
		    			if(element.contains("msgContent")) {
		    				content = ""+tmpValue;
		    			}
		    			if(element.contains("targetNpmhs")) {
		    				targetNpmhs = ""+tmpValue;
		    			}
		    			if(element.contains("targetKdpst")) {
		    				targetKdpst = ""+tmpValue;
		    			}
		    		}
		    		
		    		//send pesan telah diterima utk mhs, ,
		    		String isCreatorPetugas = null;
		    		String operNickname = isu.getObjNickNameGivenObjId();
		    		//dibikin umum jadi kalo kepala bak dirubah ke bak aja
		    		/*
		    		 * depricated dibawah akan di overidoe dengan sistim default approvee
		    		 */
		    		String operNicknameHapusKepala = null;
		    		if(operNickname!=null) {
		    			st = new StringTokenizer(operNickname,",");
		    			operNicknameHapusKepala = st.nextToken();
		    			if(operNicknameHapusKepala!=null && !Checker.isStringNullOrEmpty(operNicknameHapusKepala)) {
		    				operNicknameHapusKepala = operNicknameHapusKepala.replace("KEPALA", "");
		    				st = new StringTokenizer(operNicknameHapusKepala);
		    				operNicknameHapusKepala = "";
		    				while(st.hasMoreTokens()) {
		    					operNicknameHapusKepala = operNicknameHapusKepala+st.nextToken();
		    					if(st.hasMoreTokens()) {
		    						operNicknameHapusKepala=operNicknameHapusKepala+" ";
		    					}
		    				}
		    			}
		    		}
		    		
		    		//depricated
		    		if(operNickname.contains("OPERATOR")) {
		    			isCreatorPetugas = "true";
		    		}
		    		else {
		    			isCreatorPetugas = "false";
		    		}
		    		//proses diatas di ganti ma ini
		    		try {
		    			if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
			    			JSONObject job = jsoaPymntReq.getJSONObject(0);
			    			String NpmYgInputTransaksi = job.getString("OPNPMPYMNT");
			    			String targetKdpstTransaksi = job.getString("KDPSTPYMNT");
			    			JSONArray joa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/npm/"+NpmYgInputTransaksi+"/nickname");
			    			if(joa!=null && joa.length()>0) {
			    				JSONObject jobTmp = joa.getJSONObject(0);
			    				operNickname = jobTmp.getString("OBJ_NICKNAME");
			    				//System.out.println("operNickname="+operNickname);
			    				if(operNickname.contains("OPERATOR")) {
					    			isCreatorPetugas = "true";
					    		}
					    		else {
					    			isCreatorPetugas = "false";
					    		}
			    				//System.out.println("isCreatorPetugas="+isCreatorPetugas);
			    			}
			    		}	
		    		}
		    		catch(JSONException je) {
		    			je.printStackTrace();
		    		}
		    		String targetObjNickName = udb.getObjNicknameGivenNpmhs(targetKdpst, targetNpmhs);
		    		/*
		    		 * post berita - pada pengirim, karena bisa jadi yg input adalah operator
		    		 */
		    		if(Boolean.parseBoolean(isCreatorPetugas)) {
		    			//System.out.println("yes petugas");
		    			String approvee = Constants.getDefaultPymntReqApprovee(Checker.getKdjen(targetKdpst));
		    			operNicknameHapusKepala = ""+operNicknameHapusKepala;
		    			targetObjNickName = Constants.getDefaultTargetNicknameBilaPembayaranDiinputOlehOperator(Converter.getKdjen(targetKdpst));
		    			if(targetObjNickName.contains("OPERATOR")) {
		    				//make marked as read at target
		    				udb.postMainTopicTargetedIndividualNpmhsMarkedAsRead(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITERIMA, Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, null, "true", isCreatorPetugas);
		    			}
		    			else {
		    				udb.postMainTopicTargetedIndividualNpmhs(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITERIMA, Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, null, "true", isCreatorPetugas);
		    		
		    			}
		    		}	
		    		else {
		    			udb.postMainTopicTargetedIndividualNpmhs(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITERIMA, Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, targetNpmhs, "true", isCreatorPetugas);
		    		}
		    		match = false;
		    		// ganti ke JSON STYLE

		    		JSONArray jsoa = new JSONArray();
		    		if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
		    			for(int i=0;i<jsoaPymntReq.length();i++) {
		    				try {
		    					JSONObject joba = jsoaPymntReq.getJSONObject(i);
		    					st = new StringTokenizer(field_name,"||");
								String id = "";
								while(st.hasMoreTokens()) {
									String elemenNama = st.nextToken();
									//if(elemenNama.contains("kuiidReqested")) {
									if(elemenNama.contains("noKuiReq")) {	
										id = st.nextToken();
									}
								}	
								//if(id.equalsIgnoreCase(value)) {
								if(!joba.getString("NORUTPYMNT").equalsIgnoreCase(id)) {
									//li.remove();
									jsoa.put(joba);
									//match = true;
								}
		    				
		    				}
		    				catch(JSONException e) {
		    					e.printStackTrace();
		    				}
		    				
		    			}
		    		}
		    		if(jsoa!=null && jsoa.length()>0) {
						target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp";
						session.removeAttribute("jsoaPymntReq");
						session.setAttribute("jsoaPymntReq", jsoa);
					}
					else {
						target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
						session.removeAttribute("jsoaPymntReq");
						jsoaPymntReq = new JSONArray();
						session.setAttribute("jsoaPymntReq", jsoaPymntReq);
					}	
				}
			}
		}
		//bis di proses, redirect:
		if(target==null || Checker.isStringNullOrEmpty(target)) {
			target = Constants.getRootWeb()+"/get.notifications";
		}
		//target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url_ff).forward(request,response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
