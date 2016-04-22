package servlets.Get;

import java.io.IOException;
import java.util.StringTokenizer;
import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Vector;
import java.util.ListIterator;
/**
 * Servlet implementation class ListKurikulum
 */
@WebServlet("/ListKurikulum")
public class ListKurikulum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListKurikulum() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("prep kurikulum");
		String cmd = ""+request.getParameter("cmd");
		String atMenu = ""+request.getParameter("atMenu");
		String scope = ""+request.getParameter("scope");
		String kelasTambahan = ""+request.getParameter("kelasTambahan");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//session.setAttribute("kelasTambahan", kelasTambahan);
		////System.out.println("@ListKur servlet="+cmd+",atMenu="+atMenu+",scope="+scope);
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		//System.out.println("kdpst_nmpst="+kdpst_nmpst);
		request.setAttribute("kdpst_nmpst", kdpst_nmpst);
		String kdpst = null;
		String nmpst = null;
		String kodeKampus = null;
		if(kdpst_nmpst!=null) {
			StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
			kdpst = st.nextToken();
			nmpst = st.nextToken();
			kodeKampus = st.nextToken();
		}
		//String cmd = request.getParameter("cmd");
		Vector vKur = null;
		////System.out.println("vkdpst ="+kdpst);
		////System.out.println("2");
		String thsms_now = Checker.getThsmsNow();
		////System.out.println("3");
		SearchDb sdb = new SearchDb();
		////System.out.println("4");
		if(cmd.equalsIgnoreCase("mba")) {
			//aktif kurikulum only
			////System.out.println("5");
			vKur = sdb.getListKurikulumInDetailAktifOnly(kdpst, thsms_now);
			//vKur = sdb.getListKurikulumInDetail(kdpst);	
			////System.out.println("6");
		}
		else {
			vKur = sdb.getListKurikulumInDetail(kdpst);	
		}
		////System.out.println("7");
		//System.out.println("vKur size="+vKur.size());
		
		request.setAttribute("vListKurikulum", vKur);
		if(vKur!=null && vKur.size()>1) {
			////System.out.println("vKur>1");
			String target = Constants.getRootWeb()+"/InnerFrame/setTargetKrklm.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			////System.out.println("0.cmd="+cmd+",atMenu="+atMenu+",scope="+scope);
			request.getRequestDispatcher(url+"?cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope+"&kelasTambahan="+kelasTambahan+"&kodeKampus="+kodeKampus).forward(request,response);
			//request.getRequestDispatcher("go.prepFormBahanAjar?infoKur="+infoKur+"&cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope+"&kelasTambahan="+kelasTambahan+"&kodeKampus="+kodeKampus).forward(request,response);		
		}
		else {
			if(vKur==null || vKur.size()<1) {
				//get.listScope?scope=mba&callerPage=${pageContext.request.contextPath}/InnerFrame/Akademik/indexAkademik.jsp&atMenu=mba&cmd=mba&scopeType=prodiOnly" target="_self" class="active">BAHAN<span>AJAR</b></span></a></li>
				String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect_v1.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?msg=Tidak Ada Kurikulum yg Aktif,<br>Harap Hubungi TU Prodi<br>Harap menunggu sebentar anda sedang dialihkan&redirectTo=get.listScope&paramNeeded=scope``mba`callerPage``${pageContext.request.contextPath}/InnerFrame/Akademik/indexAkademik.jsp`atMenu``mba`cmd``mba`scopeType``prodiOnly").forward(request,response);
				////System.out.println("1.cmd="+cmd+",atMenu="+atMenu+",scope="+scope);
			}
			else {
				//vKur.size()== 1 kurikulum doang)
				
				ListIterator liSc = vKur.listIterator();
				if(liSc.hasNext()) {
					String baris = (String)liSc.next();
					////System.out.println("baris path="+baris);
					StringTokenizer st = new StringTokenizer(baris,"#&");
					//lif.add(idkur+"#&"+nmkur+"#&"+stkur+"#&"+start+"#&"+ended+"#&"+targt+"#&"+skstt+"#&"+smstt);
					String idkur = st.nextToken();
					String nmkur = st.nextToken();
					String stkur = st.nextToken();
					String start = st.nextToken();
					if(start.equalsIgnoreCase("null")) {
						start = "N/A";
					}
					String ended = st.nextToken();
					if(ended.equalsIgnoreCase("null")) {
						ended = "N/A";
					}
					String targt = st.nextToken();
					String skstt = st.nextToken();
					String smstt = st.nextToken();
					
				    String infoKur = idkur+"##"+nmkur+"##"+skstt+"##"+smstt+"##"+start+"##"+ended+"##"+targt ;
					
				    ////System.out.println("0.infoKur="+infoKur);
				    ////System.out.println("2.cmd="+cmd+",atMenu="+atMenu+",scope="+scope);
				    if(cmd.equalsIgnoreCase("mba")) {
				    	////System.out.println("alhamdulillah");
				    	/*
				    	String id_obj_mask = ""+request.getParameter("id_obj_mask");
				    	String nmm_mask = ""+request.getParameter("nmm_mask");
				    	String npm_mask = ""+request.getParameter("npm_mask");
				    	String obj_lvl_mask = ""+request.getParameter("obj_lvl_mask");
				    	String kdpst_mask = ""+request.getParameter("kdpst_mask");
				    	*/

				    	request.getRequestDispatcher("go.prepFormBahanAjar?infoKur="+infoKur+"&cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope+"&kelasTambahan="+kelasTambahan+"&kodeKampus="+kodeKampus).forward(request,response);
				    	//request.getRequestDispatcher("go.prepFormBahanAjar?kdpst_mask="+kdpst_mask+"&obj_lvl_mask="+obj_lvl_mask+"&npm_mask="+npm_mask+"&nmm_mask="+nmm_mask+"&id_obj_mask="+id_obj_mask+"&infoKur="+infoKur+"&cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope+"&kelasTambahan="+kelasTambahan+"&kodeKampus="+kodeKampus).forward(request,response);
				    }
				    else {
				    	request.getRequestDispatcher("go.prepKurikulumForViewing?infoKur="+infoKur+"&cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope+"&kelasTambahan="+kelasTambahan+"&kodeKampus="+kodeKampus).forward(request,response);
				    }
				    ////System.out.println("sudah benar");
				    
				}	
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
