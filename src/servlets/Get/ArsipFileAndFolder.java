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
import beans.tools.PathFinder;

/**
 * Servlet implementation class ArsipFileAndFolder
 */
@WebServlet("/ArsipFileAndFolder")
public class ArsipFileAndFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArsipFileAndFolder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("arsip");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			SearchDbArsip sdb = new SearchDbArsip(isu.getNpm());
			//String objNickname = isu.getObjNickNameGivenObjId();
			//Vector v = sdb.getAllowableFileFolder(objNickname);
			int objid = isu.getIdObj();
			Vector v = sdb.getAllowableFileFolderByObjid(objid);
			//ListIterator li = v.listIterator();
			//while(li.hasNext()) {
			//	String brs = (String)li.next();
			//	System.out.println("---"+brs);
			//}
			//System.out.println("v arsip size = "+v.size() );
			//session.setAttribute("v_nm_alm_access", v);
			session.setAttribute("v_nm_alm_access", v);
			/*
			System.out.println("objNickname="+objNickname);
			File f = new File("/home/usg/USG/ARSIP");
			/*
			 * harus ada scope foolder disini
			 */
			/*
			File[] listFile = f.listFiles();
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			if(listFile!=null) {
				for(int k=0;k<listFile.length;k++) {
					li.add(listFile[k].getName()+"`"+k);
				}
				Collections.sort(v);
			}
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
			*/
			//System.out.println("listFile="+listFile.length);
			//session.setAttribute("listFileFolder", sortedListFile);
			String target = Constants.getRootWeb()+"/InnerFrame/Arsip/dashArsip.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff).forward(request,response);
			/*
			if(listFile!=null && listFile.length>0) {
				for(int i=0;i<listFile.length;i++) {
					if (listFile[i].isDirectory()) {
			            System.out.println("directory:"+listFile[i].getName());
			        } else {
			            System.out.println("     file:"+listFile[i].getName());;
			        }
					//System.out.println(listFile[i].getName());
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
