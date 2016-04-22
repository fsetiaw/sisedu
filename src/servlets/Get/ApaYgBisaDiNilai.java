
package servlets.Get;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * DEPRECATED |||| harusnya ngga dipake untuk process penilaian
 */
@WebServlet("/ApaYgBisaDiNilai")
public class ApaYgBisaDiNilai extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApaYgBisaDiNilai() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		//System.out.println("apa yang mo dinilai");
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String npm_user = isu.getNpm();
		String nmm_user = isu.getNmmhs(npm_user);
		//Vector vScopeKdpst = isu.getScopeUpd7des2012ProdiOnly_rev2("inpNilai");
		Vector vScopeKdpst = isu.getScopeUpd7des2012ReturnDistinctKdpst("inpNilai");
		//System.out.println("vScopeKdpst.size="+vScopeKdpst.size());
		/*
		 * untuk scope KDPST, bila own artinya dia dosen jadi hanya kelas yang diajar
		 * untuk ktu kdpst, dst
		 */
		String scopeHakAkses = isu.getHakAkses("inpNilai");
		String scopeKmp = isu.getScopeKampus("inpNilai");
		//System.out.println("scopeHakAkses="+scopeHakAkses);
		System.out.println("scopeKmp1="+scopeKmp);
		Vector  vListKelas = new SearchDbClassPoll(isu.getNpm()).getListKelasYgSedangDiajar(vScopeKdpst,scopeHakAkses, scopeKmp, npm_user);
		//session.setAttribute("vListKelas", vListKelas);
		request.setAttribute("vListKelas", vListKelas);
		System.out.println("vListKelas.size="+vListKelas.size());
		if(vListKelas!=null && vListKelas.size()>0) {
			ListIterator li = vListKelas.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				System.out.println(brs);
			}
		}
		
		//Vector vListKelas = ().getListKelasYgSedangDiajar(npm_user);
		//String thsms_aktif=Checker.getThsmsNow();
		//Vector vScope = isu.getScopeUpd7des2012ReturnDistinctKdpst("hasKartuUjianMenu");
		
		//String listTipeUjian=sdb.getListTipeUjian(thsms_aktif, vScope);//seperated by #
		//session.setAttribute("listTipeUjian", listTipeUjian);
		String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Nilai/dashPenilaian.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url+"?atMenu=inputNilai&scopeHakAkses="+scopeHakAkses+"&scopeKmp="+scopeKmp).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
