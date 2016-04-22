package servlets.Files.personal;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class PrepPersonalArsipFileAndFolder
 */
@WebServlet("/PrepPersonalArsipFileAndFolder")
public class PrepPersonalArsipFileAndFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepPersonalArsipFileAndFolder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			//System.out.println("prep folder");
			String target_npm = request.getParameter("npm");
			String command_scope = request.getParameter("scope"); //==command
			String list_tipe_personal_folder = Getter.getListPersonalFolder();
			StringTokenizer st = null;
			/*
			 * HANYA DITAMPILKAN BILA USR ADA HAK AKSES UNTUK LIAT HIDDEN FOLDER hdf		
			 */
			String list_hidden_folder = Getter.getListHiddenFolder(); 
			if(list_hidden_folder==null || Checker.isStringNullOrEmpty(list_hidden_folder)) {
				list_hidden_folder = "";
			}
			
			String root_individual_folder = Checker.getRootFolderIndividualMhs(target_npm);
			File fileTo =  null; 
			boolean akses_to_hidden = false;
			if(isu.isUsrAllowTo_updated("hfd", target_npm)) {
				akses_to_hidden = true;
			}
			//get hidden folder
			//if(isu.isUsrAllowTo_updated("hfd", target_npm)) {
			if(akses_to_hidden) {
				if(list_hidden_folder!=null) {
					st = new StringTokenizer(list_hidden_folder,"`");
					while(st.hasMoreTokens()) {
						String nm_folder = st.nextToken();
						fileTo = new File(root_individual_folder+"/"+nm_folder);
			            fileTo.mkdirs();
					}
				}
			}
			//System.out.println("root_individual_folder="+root_individual_folder);
			
			
			
			if(list_tipe_personal_folder!=null) {
				st = new StringTokenizer(list_tipe_personal_folder,"`");
				while(st.hasMoreTokens()) {
					String nm_folder = st.nextToken();
					fileTo = new File(root_individual_folder+"/"+nm_folder);
		            fileTo.mkdirs();
				}
			}
			//create hidden folder
			
			File f = new File(root_individual_folder);

			File[] listFile = f.listFiles();
			//reset list_tipe_personal_folder karena ditambah folder readonly
			//list_tipe_personal_folder = "";
			
			
			String list_folder_at_root_dir = ""; 
			//boolean first = true;
			if(listFile!=null) {
				for(int k=0;k<listFile.length;k++) {
					if(listFile[k].isDirectory()) {
						list_folder_at_root_dir = list_folder_at_root_dir+listFile[k].getName()+"`";
					}
				}
			//	if(akses_to_hidden) {
			//		list_folder_at_root_dir = list_folder_at_root_dir+"`"+list_hidden_folder;	
			//	}
				
				list_folder_at_root_dir=Tool.sortListTkn(list_folder_at_root_dir);
			}
			//System.out.println("list_folder_at_root_dir="+list_folder_at_root_dir);
			String target = Constants.getRootWeb()+"/InnerFrame/Arsip/Perorangan/tampletIndexArsip.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?list_hidden_folder="+list_hidden_folder+"&list_folder_at_root_dir="+list_folder_at_root_dir+"&list_tipe_personal_folder="+list_tipe_personal_folder+"&root_individual_folder="+root_individual_folder).forward(request,response);

		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
