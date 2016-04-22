package servlets.view;

import java.io.IOException;
import beans.dbase.mhs.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;

import java.util.Vector;
import java.util.ListIterator;
/**
 * Servlet implementation class ListKandidatKartuUjian
 */
@WebServlet("/ListKandidatKartuUjian")
public class ListKandidatKartuUjian extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListKandidatKartuUjian() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		////System.out.println("ktm session");
		String no_urut_utk_jump_scroll = "";
		HttpSession session = request.getSession(true);
		String url_ff = "";
		String thsms_aktif = Checker.getThsmsNow();
		//String listTipeUjian = request.getParameter("listTipeUjian");
		String listTipeUjian = request.getParameter("listTipeUjian");
		if(listTipeUjian==null) {
			listTipeUjian=(String)session.getAttribute("listTipeUjian");
		}
		listTipeUjian = listTipeUjian.replace("tandaPagar", "#");
		////System.out.println("listTipeUjian="+listTipeUjian);
		try {
		//HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
		String targetUjian = request.getParameter("atMenu");
		no_urut_utk_jump_scroll = request.getParameter("no_urut_utk_jump_scroll");
		Vector vScope = isu.getScopeUpd7des2012ReturnDistinctKdpst("viewWhoRegister");
		////System.out.println("0");
		Vector vListKandidat = sdb.getListMhsYgSdhHeregistrasi(thsms_aktif,vScope);
		
		//contoh hasil nya vListKandidat
		//vListKandidat=93402||9340214200002||0000313200001#OPERATOR BAA#2015-02-06 12:27:28.057#0000512100003#OPERATOR KEPALA BAK#2015-02-13 14:39:54.676||OPERATOR BAA,OPERATOR KEPALA BAK||UTS||0000512100003||Siap Cetak
		
		
		
		vListKandidat = Tool.removeDuplicateFromVector(vListKandidat);
		/*
		 * ListIterator li = vListKandidat.listIterator();
		while(li.hasNext()) {
			String brs = (String)li.next();
			if(brs.contains("8888811100001")) {
				//System.out.println("brs=="+brs);	
			}
			
		}
		*/
		vListKandidat = sdb.getRuleKartuUjian(thsms_aktif, targetUjian,vListKandidat);
		/*
		li = vListKandidat.listIterator();
		while(li.hasNext()) {
			String brs = (String)li.next();
			if(brs.contains("8888811100001")) {
				//System.out.println("sip=="+brs);	
			}
			////System.out.println("sip=="+(String)li.next());
		}
		*/
		//System.out.println("3");
		//System.out.println(vListKandidat.size());
		vListKandidat = Tool.removeDuplicateFromVector(vListKandidat);
		//System.out.println("4");
		//System.out.println(vListKandidat.size());
		vListKandidat = sdb.getTotPembayaran(vListKandidat);
		//System.out.println("5");
		//System.out.println(vListKandidat.size());
		vListKandidat = Tool.removeDuplicateFromVector(vListKandidat);
		//System.out.println("6");
		vListKandidat = sdb.getInfoProfileMhs(vListKandidat);
		//System.out.println("7");
		//System.out.println(vListKandidat.size());
		vListKandidat = Tool.removeDuplicateFromVector(vListKandidat);
		//System.out.println("8");
		//System.out.println(vListKandidat.size());
		vListKandidat = sdb.prosesStatusApprovalKartuUjian(targetUjian,vListKandidat);
		//System.out.println(vListKandidat.size());
		session.setAttribute("vInfoListKandidat", vListKandidat);
		if(isu.isAllowTo("cetakKartuUjian")>0) {
			request.setAttribute("cetakMod", "true");
		}
		//request.setAttribute("thsms_aktif", thsms_aktif);
		String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/KartuUjian/showListKandidate.jsp";
		String uri = request.getRequestURI();
		url_ff = PathFinder.getPath(uri, target);
		}
		catch(Exception e) {
			System.out.println("ada error ListKandidatKrtuUjin");
			//System.out.println(e);
		}
		listTipeUjian = listTipeUjian.replace("#", "tandaPagar");
		request.getRequestDispatcher(url_ff+"?thsms_aktif="+thsms_aktif+"&listTipeUjian="+listTipeUjian+"&no_urut_utk_jump_scroll="+no_urut_utk_jump_scroll).forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
