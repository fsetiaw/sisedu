package servlets.Files;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CreateNewFolder_v1
 */
@WebServlet("/CreateNewFolder_v1")
public class CreateNewFolder_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String foldername;
	String alm;
	String keter;
	String hak; 
	String root_dir;
	String folder_type;
	String list_tipe_personal_folder;
	String list_folder_at_root_dir;
	String list_hidden_folder;
	String id_obj;
	String nmm;
	String npm;
	String obj_lvl;
	String kdpst;
	String cmd;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewFolder_v1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//get.folderContent_v1?&root_dir=<%=root_dir %>&keter=<%=nameKeterDir %>&alm=<%=backToDir %>&hak=<%=hakAksesUsrUtkFolderIni %>"><img style="border:0;" src="<%=Constants.getRootWeb() %>/css/folderIcon/folder_previous_grey.png" alt="Prev Dir" ></a>
	String msg = "<meta http-equiv=\"refresh\" content=\"0; url=get.folderContent_v1?cmd="+cmd+"&kdpst="+kdpst+"&obj_lvl="+obj_lvl+"&npm="+npm+"&nmm="+nmm+"&id_obj="+id_obj+"&list_hidden_folder="+list_hidden_folder+"&list_folder_at_root_dir="+list_folder_at_root_dir+"&list_tipe_personal_folder="+list_tipe_personal_folder+"&hidden_folder=true&keter="+keter+"&alm="+alm+"&hak="+hak+"&root_dir="+root_dir+"\" >";
	//request.getRequestDispatcher("get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak).forward(request,response);
	
    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
    response.getWriter().write(msg); 
    msg="";
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			
			id_obj = request.getParameter("id_obj");
			nmm = request.getParameter("nmm");
			npm = request.getParameter("npm");
			obj_lvl = request.getParameter("obj_lvl");
			kdpst = request.getParameter("kdpst");
			cmd = request.getParameter("cmd");
			//System.out.println("create folder ini V1");
			//System.out.println("npm folder ini V1="+npm);
			foldername = request.getParameter("foldername");
			folder_type = request.getParameter("folder_type");
			foldername = foldername.replace(" ", "_");
			list_tipe_personal_folder=""+request.getParameter("list_tipe_personal_folder");
			list_folder_at_root_dir=""+request.getParameter("list_folder_at_root_dir");
			list_hidden_folder=""+request.getParameter("list_hidden_folder");
			//System.out.println("create foldername="+alm+"/"+foldername);
			//System.out.println("create type="+folder_type);
			alm = request.getParameter("alm");
			keter = request.getParameter("keter");
			hak = request.getParameter("hak");
			//hak = hak.replace("`", "-");
			//karena untuk bisa buat folder di hidden folder only yg ada akses maka otomatis hak aksesnya juga fiull
			hak = "r-e-i-d";
			root_dir = request.getParameter("root_dir");//masalah kalo dipajke
			//System.out.println(foldername);
			//System.out.println(alm);
			File dir = new File(alm+"/"+foldername);
			//try {
			dir.mkdir();
				//System.out.println("created");
			//}
			//catch (Exception e) {
			//	System.out.println("error");
			//}
			//request.getRequestDispatcher("get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak).forward(request,response);
		}
		doGet(request,response);
	}

}
