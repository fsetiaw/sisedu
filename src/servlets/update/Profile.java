package servlets.update;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;
import beans.dbase.*;
import beans.login.InitSessionUsr;
import beans.tools.Checker;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/UpdateProfile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public Profile() {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("update profile");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String v_id_obj_v_obj_lvl = request.getParameter("v_id_obj_v_obj_lvl");
		StringTokenizer st = new StringTokenizer(v_id_obj_v_obj_lvl,",");
		//String v_id_obj=request.getParameter("v_id_obj");
		//String v_obj_lvl=request.getParameter("v_obj_lvl");
		String v_id_obj=st.nextToken();
		String v_obj_lvl=st.nextToken();
		String v_kdpst=request.getParameter("v_kdpst");
		String v_shift=request.getParameter("shiftKelas");
		String v_npmhs=request.getParameter("v_npmhs");
		String v_nimhs=request.getParameter("v_nimhs");
		String v_nmmhs=request.getParameter("v_nmmhs");
		String v_almrm=request.getParameter("v_almrm");
		String v_kdjek=request.getParameter("v_kdjek");
		String v_sttus=request.getParameter("v_sttus");
		String v_email=request.getParameter("v_email");
		String v_nohpe=request.getParameter("v_nohpe");
		String v_tplhr=request.getParameter("v_tplhr");
		String v_telrm=request.getParameter("v_telrm");
		String v_neglh=request.getParameter("v_neglh");
		String v_posrm=request.getParameter("v_posrm");
		String v_tglhr=request.getParameter("v_tglhr");
		String v_kotrm=request.getParameter("v_kotrm");
		String v_nmpek=request.getParameter("v_nmpek");
		String v_almkt=request.getParameter("v_almkt");
		String v_jbtkt=request.getParameter("v_jbtkt");
		String v_bidkt=request.getParameter("v_bidkt");
		String v_jenkt=request.getParameter("v_jenkt");
		String v_kotkt=request.getParameter("v_kotkt");
		String v_telkt=request.getParameter("v_telkt");
		String v_poskt=request.getParameter("v_poskt");
		String v_nmmsp=request.getParameter("v_nmmsp");
		String v_telsp=request.getParameter("v_telsp");
		String v_smawl=request.getParameter("v_smawl");
		String v_btstu=request.getParameter("v_btstu");
		String v_almsp=request.getParameter("v_almsp");
		String v_stpid=request.getParameter("v_stpid");
		String v_possp=request.getParameter("v_possp");
		String v_kotsp=request.getParameter("v_kotsp");
		String v_negsp=request.getParameter("v_negsp");
		String v_agama=request.getParameter("v_agama");
		UpdateDb db = new UpdateDb();
		//idkurA+"__"+nmkurA+"__"+sksttA+"__"+smsttA+"__selected__...dst;
		String krklm = request.getParameter("krklm");
		String dosenPa = request.getParameter("dosenPA");   
		//System.out.println("kurkulum="+krklm);
		
		
		//update or insert jadi harus duluan
		db.updateProfile(v_id_obj,v_kdpst,v_npmhs,v_nimhs,v_nmmhs,v_smawl,v_almrm,v_kdjek,v_sttus,v_email,v_nohpe,v_tplhr,v_telrm,v_neglh,v_posrm,v_tglhr,v_kotrm,v_nmpek,v_almkt,v_jbtkt,v_bidkt,v_jenkt,v_kotkt,v_telkt,v_poskt,v_nmmsp,v_telsp,v_almsp,v_stpid,v_possp,v_kotsp,v_negsp,v_agama,v_shift);
		//update kurikulum target
		if(krklm!=null && !Checker.isStringNullOrEmpty(krklm)) {
			st = new StringTokenizer(krklm,"__");
			if(st.countTokens()>0) {
				UpdateDb udb = new UpdateDb(isu.getNpm());
				String idkur = st.nextToken();
						//String nmkur = st.nextToken();
						//String skttt = st.nextToken();
						//String smstt = st.nextToken();
						
				udb.addNpmhsAsTargetKurikulum(idkur,v_kdpst, v_npmhs);
			}
		}
		//update dosen pa
		if(dosenPa!=null && !Checker.isStringNullOrEmpty(dosenPa)) {
			st = new StringTokenizer(dosenPa,"||");
			String id_obj=st.nextToken();
			String npmpa=st.nextToken();
			String nmmpa=st.nextToken();
			UpdateDb udb = new UpdateDb(isu.getNpm());
			udb.updateDataDosenPembimbing(v_kdpst, v_npmhs, npmpa, nmmpa);
		}
		
		response.sendRedirect("get.profile?id_obj="+v_id_obj+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&obj_lvl="+v_obj_lvl+"&kdpst="+v_kdpst+"&cmd=updated");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
