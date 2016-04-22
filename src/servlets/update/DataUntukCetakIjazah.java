package servlets.update;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Vector;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Converter;
import beans.tools.PathFinder;
import beans.dbase.*;
/**
 * Servlet implementation class DataUntukCetakIjazah
 */
@WebServlet("/DataUntukCetakIjazah")
public class DataUntukCetakIjazah extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataUntukCetakIjazah() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String cmd =  request.getParameter("cmd");

		//System.out.println("masuk vop");
		//update ada di 2 tmp = ProfileCivitas.java && HistoryBakCivitas.java
		int norut = isu.isAllowTo("vop");
		Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"vop",kdpst);
		ListIterator li = v.listIterator();
		String v_obj_lvl=(String)li.next();
		request.setAttribute("v_obj_lvl", v_obj_lvl);
		String v_id_kotaku=(String)li.next();
		request.setAttribute("v_id_kotaku", v_id_kotaku);
		String v_id_obj=(String)li.next();
		request.setAttribute("v_id_obj", v_id_obj);
		String v_kdpti=(String)li.next();
		request.setAttribute("v_kdpti", v_kdpti);
		String v_kdjen=(String)li.next();
		request.setAttribute("v_kdjen", v_kdjen);
		String v_kdpst=(String)li.next();
		request.setAttribute("v_kdpst", v_kdpst);
		String v_npmhs=(String)li.next();
		request.setAttribute("v_npmhs", v_npmhs);
		String v_nimhs=(String)li.next();
		request.setAttribute("v_nimhs", v_nimhs);
		String v_nmmhs=(String)li.next();
		request.setAttribute("v_nmmhs", v_nmmhs);
		String v_shift=(String)li.next();
		request.setAttribute("v_shift", v_shift);
		String v_tplhr=(String)li.next();
		request.setAttribute("v_tplhr", v_tplhr);
		String v_tglhr=(String)li.next();
		request.setAttribute("v_tglhr", v_tglhr);
		String v_kdjek=(String)li.next();
		request.setAttribute("v_kdjek", v_kdjek);
		String v_tahun=(String)li.next();
		request.setAttribute("v_tahun", v_tahun);
		String v_smawl=(String)li.next();
		request.setAttribute("v_smawl", v_smawl);
		String v_btstu=(String)li.next();
		request.setAttribute("v_btstu", v_btstu);
		String v_assma=(String)li.next();
		request.setAttribute("v_assma", v_assma);
		String v_tgmsk=(String)li.next();
		request.setAttribute("v_tgmsk", v_tgmsk);
		String v_tglls=(String)li.next();
		request.setAttribute("v_tglls", v_tglls);
		String v_stmhs=(String)li.next();
		request.setAttribute("v_stmhs", v_stmhs);
		String v_stpid=(String)li.next();
		request.setAttribute("v_stpid", v_stpid);
		String v_sksdi=(String)li.next();
		request.setAttribute("v_sksdi", v_sksdi);
		String v_asnim=(String)li.next();
		request.setAttribute("v_asnim", v_asnim);
		String v_aspti=(String)li.next();
		request.setAttribute("v_aspti", v_aspti);
		String v_asjen=(String)li.next();
		request.setAttribute("v_asjen", v_asjen);
		String v_aspst=(String)li.next();
		request.setAttribute("v_aspst", v_aspst);
		String v_bistu=(String)li.next();
		request.setAttribute("v_bistu", v_bistu);
		String v_peksb=(String)li.next();
		request.setAttribute("v_peksb", v_peksb);
		String v_nmpek=(String)li.next();
		request.setAttribute("v_nmpek", v_nmpek);
		String v_ptpek=(String)li.next();
		request.setAttribute("v_ptpek", v_ptpek);
		String v_pspek=(String)li.next();
		request.setAttribute("v_pspek", v_pspek);
		String v_noprm=(String)li.next();
		request.setAttribute("v_noprm", v_noprm);
		String v_nokp1=(String)li.next();
		request.setAttribute("v_nokp1", v_nokp1);
		String v_nokp2=(String)li.next();
		request.setAttribute("v_nokp2", v_nokp2);
		String v_nokp3=(String)li.next();
		request.setAttribute("v_nokp3", v_nokp3);
		String v_nokp4=(String)li.next();
		request.setAttribute("v_nokp4", v_nokp4);
		String v_sttus=(String)li.next();
		request.setAttribute("v_sttus", v_sttus);
		String v_email=(String)li.next();
		request.setAttribute("v_email", v_email);
		String v_nohpe=(String)li.next();
		request.setAttribute("v_nohpe", v_nohpe);
   		String v_almrm=(String)li.next();
		request.setAttribute("v_almrm", v_almrm);
		String v_kotrm=(String)li.next();
		request.setAttribute("v_kotrm", v_kotrm);
   		String v_posrm=(String)li.next();
		request.setAttribute("v_posrm", v_posrm);
   		String v_telrm=(String)li.next();
		request.setAttribute("v_telrm", v_telrm);
   		String v_almkt=(String)li.next();
		request.setAttribute("v_almkt", v_almkt);
		String v_kotkt=(String)li.next();
		request.setAttribute("v_kotkt", v_kotkt);
   		String v_poskt=(String)li.next();
		request.setAttribute("v_poskt", v_poskt);
   		String v_telkt=(String)li.next();
		request.setAttribute("v_telkt", v_telkt);
   		String v_jbtkt=(String)li.next();
		request.setAttribute("v_jbtkt", v_jbtkt);
   		String v_bidkt=(String)li.next();
		request.setAttribute("v_bidkt", v_bidkt);
   		String v_jenkt=(String)li.next();
		request.setAttribute("v_jenkt", v_jenkt);
   		String v_nmmsp=(String)li.next();
		request.setAttribute("v_nmmsp", v_nmmsp);
   		String v_almsp=(String)li.next();
		request.setAttribute("v_almsp", v_almsp);
   		String v_possp=(String)li.next();
		request.setAttribute("v_possp", v_possp);
		String v_kotsp=(String)li.next();
		request.setAttribute("v_kotsp", v_kotsp);
		String v_negsp=(String)li.next();
		request.setAttribute("v_negsp", v_negsp);
   		String v_telsp=(String)li.next();
		request.setAttribute("v_telsp", v_telsp);
		String v_neglh=(String)li.next();
		request.setAttribute("v_neglh", v_neglh);
		String v_agama=(String)li.next();
		request.setAttribute("v_agama", v_agama);
				
		request.setAttribute("v_profile", v);
		request.setAttribute("atr_name", "atr_val");
		//System.out.println(v_npmhs);
		//System.out.println(v_obj_lvl);

		String nmmija = request.getParameter("nmmija"); 
		String gelarija = request.getParameter("gelarija");
		String nimija = request.getParameter("nimija");
		String tplhrija = request.getParameter("tplhrija");
		String tgl = request.getParameter("tgl");
		String bln = request.getParameter("bln");
		String thn = request.getParameter("thn");
		String tglhrija = thn+"-"+bln+"-"+tgl;
		//String tglhrija = request.getParameter("tglhrija");
		String noseriija = request.getParameter("noseriija");
		String noserinirl = request.getParameter("noserinirl");
		//System.out.println("nimija="+nimija);
		//StringTokenizer st = new StringTokenizer(tglhrija);
		//String tgl = st.nextToken();
		//String nama_bulan = st.nextToken();
		//int bln = Converter.convertNamaBulanToInt(nama_bulan);
		//String thn = st.nextToken();
		java.sql.Date tglhr=java.sql.Date.valueOf(thn+"-"+bln+"-"+tgl);
		
		if(isu.isUsrAllowTo("allowEditIjazah", v_npmhs, v_obj_lvl)) {
			//System.out.println("updateDb");
			UpdateDb udb = new UpdateDb();
			int i = udb.updateDataIjazah(v_kdpst,v_npmhs,nmmija,nimija,gelarija,tplhrija,tglhrija,noserinirl,noseriija);
			
			//System.out.println("i="+i);
			if(i>0) {
				udb.updateMsmhsTpTglhr(v_npmhs,tplhrija,tglhr);
				if(isu.isUsrAllowTo("allowCetakIjazah", v_npmhs, v_obj_lvl)) {
					//System.out.println("allow cetak");
					//if allow cetak forward ke halaman download excel
					String target = "goto.validasiCetakIjazah2";
					//String uri = request.getRequestURI();
					//String url_ff = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(target).forward(request,response);
				}
				else {
					//if cuma boleh edit kembali ke hal muka mhs
					//System.out.println("not allow cetak");
					String target = "get.profile?npm="+v_npmhs+"&cmd=dashboard&nim="+v_nimhs+"&stmhs="+v_stmhs+"&nmm="+v_nmmhs;
					//String uri = request.getRequestURI();
					//String url_ff = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(target).forward(request,response);
					
				}
			}
			else {
				//forward gagal update
				//System.out.println("gagal update");
				String target = Constants.getRootWeb()+"/ErrorPage/gagalInsertData.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url_ff).forward(request,response);
			}
		}
		else {
			String target = Constants.getRootWeb()+"/ErrorPage/authorization.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff).forward(request,response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("update data ijazah");
		doGet(request,response);
	}

}
