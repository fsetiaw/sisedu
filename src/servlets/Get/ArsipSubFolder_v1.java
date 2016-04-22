package servlets.Get;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

import beans.dbase.arsip.SearchDbArsip;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ArsipSubFolder
 */
@WebServlet("/ArsipSubFolder")
public class ArsipSubFolder_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArsipSubFolder_v1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("sub arsip");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			//System.out.println("kse");
			String root_dir = ""+request.getParameter("root_dir");
			String keter = ""+request.getParameter("keter");
			String alm = ""+request.getParameter("alm");
			String hak =  ""+request.getParameter("hak");
			String scope = ""+request.getParameter("scope"); 
			String callerPage= ""+request.getParameter("callerPage");
			String atMenu = ""+request.getParameter("atMenu");
			String cmd = ""+request.getParameter("cmd");
			String scopeType=""+request.getParameter("scopeType");
			String id_obj=""+request.getParameter("id_obj");
			String nmm=""+request.getParameter("nmm");
			String npm=""+request.getParameter("npm");
			String obj_lvl=""+request.getParameter("obj_lvl");
			String kdpst = ""+request.getParameter("kdpst");
			String list_tipe_personal_folder=""+request.getParameter("list_tipe_personal_folder");
			String list_folder_at_root_dir=""+request.getParameter("list_folder_at_root_dir");
			String list_hidden_folder=""+request.getParameter("list_hidden_folder");
			String hidden_folder = ""+request.getParameter("hidden_folder");
			if(hidden_folder==null || Checker.isStringNullOrEmpty(hidden_folder)) {
				hidden_folder = "false";
			}
			//System.out.println("npm2="+npm);
			////System.out.println("alm="+alm);
			////System.out.println("hak="+hak);
			//
			
			//Vector v = sdb.getAllowableFileFolder(objNickname);
			//session.setAttribute("v_nm_alm_access", v);
			
			
			//updated 2016 = harus make simple 
			/*
			String objNickname = null;
			String hakAksesUsrUtkFolderIni = null;
			if(hak!=null && !Checker.isStringNullOrEmpty(hak) && hak.contains("-")) {
				SearchDbArsip sdb = new SearchDbArsip(isu.getNpm());
				objNickname = isu.getObjNickNameGivenObjId();
				hakAksesUsrUtkFolderIni = sdb.getHakAksesGiven(hak, objNickname);	
			}
			else {
				hakAksesUsrUtkFolderIni = (String)session.getAttribute("hakAksesUsrUtkFolderIni");
			}
			*/
			////System.out.println("hakAksesUsrUtkFolderIni="+hakAksesUsrUtkFolderIni);
			//if(alm==null) {
			//	System.out.println("alm is null");	
			//}
			//else {
			//	System.out.println("alm="+alm);
			//}
			File f = new File(alm);

			File[] listFile = f.listFiles();
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			if(listFile!=null) {
				for(int k=0;k<listFile.length;k++) {
					li.add(listFile[k].getName()+"`"+k);
				}
				Collections.sort(v);
			}
			//if(listFile==null) {
			//	System.out.println("listFile is null");	
			//}
			//else {
				//System.out.println("listFile="+listFile);
			//}
			
			File[] sortedListFile = new File[listFile.length];
			li = v.listIterator();
			int l = 0;
			while(li.hasNext()) {
				
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String namaFile = st.nextToken();
				String norutFile = st.nextToken();
				sortedListFile[l++] = listFile[Integer.parseInt(norutFile)];
			}
			
			////System.out.println("listFile="+listFile.length);
			session.setAttribute("listFileFolder", sortedListFile);
			//session.setAttribute("hakAksesUsrUtkFolderIni", hakAksesUsrUtkFolderIni);
			String target = Constants.getRootWeb()+"/InnerFrame/Arsip/Perorangan/folderContent_v1.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url_ff+"?cur_dir="+alm+"&keter="+keter).forward(request,response);
			String redirect = response.encodeRedirectURL(url_ff+"?hidden_folder="+hidden_folder+"&list_hidden_folder="+list_hidden_folder+"&list_folder_at_root_dir="+list_folder_at_root_dir+"&list_tipe_personal_folder="+list_tipe_personal_folder+"&root_dir="+root_dir+"&cur_dir="+alm+"&keter="+keter+"&hak="+hak+"&scope="+scope+"&callerPage="+callerPage+"&cmd="+cmd+"&atMenu="+atMenu+"&scopeType="+scopeType+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst);
			//System.out.println("redirect");
			response.sendRedirect(redirect);
			
			/*
			 * String hak =  ""+request.getParameter("hak");
			String scope = ""+request.getParameter("scope"); 
			String callerPage= ""+request.getParameter("callerPage");
			String atMenu = ""+request.getParameter("atMenu");
			String cmd = ""+request.getParameter("cmd");
			String scopeType=""+request.getParameter("scopeType");
			String id_obj=""+request.getParameter("id_obj");
			String nmm=""+request.getParameter("nmm");
			String npm=""+request.getParameter("npm");
			String obj_lvl=""+request.getParameter("obj_lvl");
			String kdpst = ""+request.getParameter("kdpst");
			if(listFile!=null && listFile.length>0) {
				for(int i=0;i<listFile.length;i++) {
					if (listFile[i].isDirectory()) {
			            //System.out.println("directory:"+listFile[i].getName());
			        } else {
			            //System.out.println("     file:"+listFile[i].getName());;
			        }
					////System.out.println(listFile[i].getName());
				}
			    */
			
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
