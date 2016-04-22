package servlets.process;

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
 * Servlet implementation class PrepFormBahanAjarMk
 */
@WebServlet("/PrepFormBahanAjarMk")
public class PrepFormBahanAjarMk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepFormBahanAjarMk() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("PrepFormBahanAjarPerMk");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//bahanAjarGivenMk?atMenu=mba&nakmk=<%=prev_nakmk %>&kdkmk=<%=prev_kdkmk %>&kdpst=<%=prev_kdpst %>&idkur=<%=idkur %>"><%=prev_nakmk %></a></B> </label>
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here 
			//System.out.println("sampaai");
			String atMenu = request.getParameter("atMenu");
			String nakmk = request.getParameter("nakmk");
			String kdkmk = request.getParameter("kdkmk");
			String kdpst = request.getParameter("kdpst");
			String idkur = request.getParameter("idkur");
			String target_npm_mask = request.getParameter("npm");
			long cuid = -1; //variable ini digunakan oleh mahasiswa
			if(atMenu.equalsIgnoreCase("vba")) {
				//akses mahasiswa blum ada uniqueId
				String target_thsms = request.getParameter("target_thsms");
				cuid = Checker.getClassUID(kdkmk, target_thsms, isu.getNpm());//!!user HRS MHS !!!!
			}
			/*
			 * get folder content silabi & mater
			 * /home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/kdpst/kdkmk/MATERI/npm
			 * 
			 */
			String tkn_tipe_path = "";
			SearchDbBahanAjar sdb = new SearchDbBahanAjar(isu.getNpm());
			if(!isu.getObjNickNameGivenObjId().contains("MHS") && !isu.getObjNickNameGivenObjId().contains("mhs")) {
				
				Vector vList = sdb.getKategoriBahanAjarAktifOnlySesuaiScopeNotForAssignment(kdpst,kdkmk,idkur,null,isu,target_npm_mask);
				if(vList!=null && vList.size()>0) {
					//li.add(tipe+"`"+path+"`"+session_var);
					ListIterator lil = vList.listIterator()	;
					while(lil.hasNext()) {
						String brs = (String)lil.next();
						StringTokenizer st = new StringTokenizer(brs,"`");
						String tipe = st.nextToken();
						String path = st.nextToken();
						String ses_var = st.nextToken();
						//System.out.println("tipe-"+tipe);
						//System.out.println("path-"+path);
						//System.out.println("ses_var-"+ses_var);
						getListFileInFolder(path, ses_var, session);
					}
				}
					
			}
			else {
				//System.out.println("iam heere");
				Vector vListMhs  = sdb.getKategoriBahanAjarForMhsOnly(cuid);
				//if(vListMhs==null) {
					//System.out.println("vListMhs = null");
					
				//}
				//else {
					//System.out.println("vListMhs size="+vListMhs.size());
				//}
				session.setAttribute("vListForMhs",vListMhs);
			}	
			
			
					
			///home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/kdpst/idKur/kdkmk/GBPP
			//File f = new File("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/MATERI/"+isu.getNpm());
			/*
			getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/MATERI/"+isu.getNpm(), "listFileMateri", session);
			getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/SILABI/"+isu.getNpm(), "listFileSilabi", session);
			getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/QUIZ/"+isu.getNpm(), "listFileQuiz", session);
			getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/TUGAS/"+isu.getNpm(), "listFileTugas", session);
			//getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/UJIAN/"+isu.getNpm(), "listFileUjian", session);
			getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/UAS/"+isu.getNpm(), "listFileUas", session);
			getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/UTS/"+isu.getNpm(), "listFileUts", session);
			getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/SAP/"+isu.getNpm(), "listFileSap", session);
			getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/GBPP/"+isu.getNpm(), "listFileGbpp", session);
			 */
			
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/bahan_ajar/formBahanAjarPerMk.jsp";
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response); //tkn_tipe_path = diperlukan bila yg liat mahasiswa
		}
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
