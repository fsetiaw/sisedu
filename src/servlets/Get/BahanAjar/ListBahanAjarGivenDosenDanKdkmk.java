package servlets.Get.BahanAjar;

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

import beans.dbase.bahan_ajar.SearchDbBahanAjar;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ListBahanAjarGivenDosenDanKdkmk
 * @param <E>
 */
@WebServlet("/ListBahanAjarGivenDosenDanKdkmk")
public class ListBahanAjarGivenDosenDanKdkmk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListBahanAjarGivenDosenDanKdkmk() {
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
		//System.out.println("on in");
		if(isu==null) { 
			//System.out.println("kok isu null");
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
			//kode here
			//1. get distinct tipe bahan ajar & alamat
			//get.listBahanAjarDosen?
			String atMenu= request.getParameter("atMenu");
			String nakmk= request.getParameter("nakmk");
			String kdkmk= request.getParameter("kdkmk");
			String kdpst= request.getParameter("kdpst");
			String idkur= request.getParameter("idkur");
			String shift = request.getParameter("shift");
			String target_kategori = request.getParameter("target_kategori");
			String unique_id  = request.getParameter("unique_id");	
			
			SearchDbBahanAjar sdb = new SearchDbBahanAjar(isu.getNpm());
			
			
			/*
			boolean readOnlySapGbpp = true;
			boolean addTipe = false;
			boolean addBahan = false;
			if(isu.isUsrAllowTo("sap", kdpst)) {
	    		//showSapGbpp = true;
	    		if(!isu.isHakAksesReadOnly("sap")){
	    			readOnlySapGbpp = false;
	    		}
	    	}

	    	if(isu.isUsrAllowTo("TBA", kdpst)) {
	    		addTipe = true;
	    		addBahan = true;
	    	}
	    	else if(isu.isHakAksesReadOnly("mba")){
	    		addBahan = false;
	    	}
			*/
			//kdpst,kdkmk,idkur,null,isu);
			Vector v = sdb.getKategoriBahanAjarAktifOnlySesuaiScopeForAssignment(kdpst,kdkmk,idkur,null,isu);
			session.setAttribute("vListKategori", v);
			
			//2.get bahan ajar yang sudah di assign dari CLASS_POOL_BAHAN_AJAR
			Vector vAss = sdb.getAssignedBahanAjar(Integer.parseInt(unique_id));
			session.setAttribute("vAssigned", vAss);
			
			if(target_kategori!=null && !Checker.isStringNullOrEmpty(target_kategori)) {
			
				//System.out.println("target_kategori="+target_kategori);
				//target_kategori=MATERI`/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/kdpst/kdkmk/MATERI/npmDosen
				StringTokenizer st = new StringTokenizer(target_kategori,"`");
				String cat = st.nextToken();				
				String path = st.nextToken();
				String npmDosen = isu.getNpm();
				if(cat.equalsIgnoreCase("MATERI")) {
					path = path.replace("kdpst", kdpst);
					path = path.replace("kdkmk", kdkmk);
					path = path.replace("npmDosen", npmDosen);
					getListFileInFolder(path, "listFileMateri", session);	
				}
				
				//System.out.println("path="+path);
				
				
			}
			else {
			/* 
			if(v==null || v.size()<1) {
				//redirect ke belum ada kategori bahan ajar
			}
			else {
				//2. get file yang ada directori tersebut
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					
				}
			}
			*/
			///ToUnivSatyagama/WebContent/InnerFrame/Prakuliah/BahanAjar/formPengajuanBahanAjarPerMk.jsp
			//3
			}	
			String target = Constants.getRootWeb()+"/InnerFrame/Prakuliah/BahanAjar/formPengajuanBahanAjarPerMk.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
			
		}

		//System.out.println("cek avail bahan ajar");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	protected void getListFileInFolder(String folder_path, String returnSessionAttributeName,HttpSession session) {
		File f = new File(folder_path);

		File[] listFile = f.listFiles();
		Vector v = new Vector();
		ListIterator li = v.listIterator();
		if(listFile!=null) {
			for(int k=0;k<listFile.length;k++) {
				li.add(listFile[k].getName()+"`"+k);
				//System.out.println("listFile= "+listFile[k].getName());
			}
			Collections.sort(v);
			
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
			session.setAttribute(returnSessionAttributeName, sortedListFile);
		}
		else {
			session.setAttribute(returnSessionAttributeName, null);
		}
		
		
		
		////System.out.println("listFile="+listFile.length);
		
	}
}
