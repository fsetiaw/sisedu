package servlets.Router;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.keu.*;
import beans.login.InitSessionUsr;
import beans.tools.Checker;

import java.util.StringTokenizer;
/**
 * Servlet implementation class RedirectAfterGenericFileUpload
 */
@WebServlet("/RedirectAfterGenericFileUpload")
public class RedirectAfterGenericFileUpload_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectAfterGenericFileUpload_v1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("RedirectAfterGenericFileUpload");
		HttpSession session = request.getSession(true);
		 
 		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
 		String dataForRouterAfterUpload = (String)session.getAttribute("dataForRouterAfterUpload");
 		//System.out.println("dataForRouterAfterUpload="+dataForRouterAfterUpload);
 		session.removeAttribute("dataForRouterAfterUpload");
 		
 		StringTokenizer st = new StringTokenizer(dataForRouterAfterUpload,"`");
 		//session.setAttribute("dataForRouterAfterUpload", root_dir+"`"+keter+"`"+hakAksesUsrUtkFolderIni+"`"+cur_dir+"`"+cmd+"`"+scope+"`"+callerPage+"`"+atMenu+"`"+scopeType+"`"+objId+"`"+nmm+"`"+npm+"`"+obj_lvl+"`"+kdpst);
 		
 		String root_dir = st.nextToken();
 		String keter = st.nextToken();
 		String hak = st.nextToken();
 		String alm = st.nextToken();
 		String cmd = st.nextToken();
 		String scope = st.nextToken();
 		String callerPage = st.nextToken();
 		String atMenu = st.nextToken();
 		String scopeType = st.nextToken();
 		String id_obj = st.nextToken();
 		String nmm = st.nextToken();
 		String npm = st.nextToken();
 		String obj_lvl = st.nextToken();
 		String kdpst = st.nextToken();
 		String list_tipe_personal_folder = st.nextToken();
 		list_tipe_personal_folder=list_tipe_personal_folder.replace("-", "`");
 		String list_folder_at_root_dir = st.nextToken();
 		list_folder_at_root_dir=list_folder_at_root_dir.replace("-", "`");
 		String list_hidden_folder = st.nextToken();
 		list_hidden_folder=list_hidden_folder.replace("-", "`");
 		String hidden_folder=st.nextToken();
		//session.removeAttribute("nuFileName");
		//System.out.println("namaFileSetoran="+namaFileSetoran);
 		//id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=arsip&root_dir=<%=root_dir %>&keter=<%=nameKeterDir %>&alm=<%=backToDir %>&hak=<%=hakAksesUsrUtkFolderIni %>
		request.getRequestDispatcher("get.folderContent_v1?hidden_folder="+hidden_folder+"&list_hidden_folder="+list_hidden_folder+"&list_folder_at_root_dir="+list_folder_at_root_dir+"&list_tipe_personal_folder="+list_tipe_personal_folder+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=arsip&root_dir="+root_dir+"&keter="+keter+"&alm="+alm+"&hak="+hak).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
