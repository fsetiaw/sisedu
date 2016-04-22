package servlets.update.dosen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;
import beans.dbase.dosen.*;
/**
 * Servlet implementation class UpdateDataDosen
 */
@WebServlet("/UpdateDataDosen")
public class UpdateDataDosen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDataDosen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("upd data dosen");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String kdpstDsn = request.getParameter("kdpst_dsn");
		String npmDsn = request.getParameter("npm_dsn");
		String objLvl = request.getParameter("obj_lvl");
		String nmm = request.getParameter("nmm");
		String idObj = request.getParameter("id_obj");
		System.out.println(kdpstDsn);
		System.out.println(npmDsn);
		System.out.println(objLvl);
		System.out.println(nmm);
		System.out.println(idObj);
		String kdjek = request.getParameter("kdjek");//kode
		String frontTitle = request.getParameter("frontTitle");//kode
		String endTitle = request.getParameter("endTitle");//kode
		String statusDsn = request.getParameter("statusDsn");//kode
		String tipeKtp =  request.getParameter("tipeKtp");//kode
		String noKtp =  request.getParameter("noKtp");//kode
		String noNidnn =  request.getParameter("noNidnn");//kode
		String tipeIdDsn =  request.getParameter("tipeIdDsn");//kode
		String noIdDsn = request.getParameter("noIdDsn");//kode
		String tipeIkaDsn = request.getParameter("tipeIkaDsn");//kode
		String jabStruk = request.getParameter("jabStruk");//kode
		String ptBase = request.getParameter("ptBase");//kode
		String jja_loco = request.getParameter("JJA-local");//kode
		String baseProdi = request.getParameter("baseProdi");//kode
		if(baseProdi!=null && !Checker.isStringNullOrEmpty(baseProdi)) {
			ptBase=""+Constants.getKdpti();
		}
		String jja_dikti = request.getParameter("JJA-dikti");//kode
		String gol = request.getParameter("gol");//kode
		String kum = request.getParameter("kum");//kode
		String serdos = request.getParameter("serdos");//kode
		String tglMsk = request.getParameter("tglMsk");//kode
		String tglOut = request.getParameter("tglOut");//kode
		String aspti1 = request.getParameter("aspti1");//kode
		String aspti2 = request.getParameter("aspti2");//kode
		String gelar1 = request.getParameter("gelar1");//kode
		String gelar2 = request.getParameter("gelar2");//kode
		String kdpst1 = request.getParameter("kdpst1");//kode
		String kdpst2 = request.getParameter("kdpst2");//kode
		String bidil1 = request.getParameter("bidil1");//kode
		String bidil2 = request.getParameter("bidil2");//kode
		String tglls1 = request.getParameter("tglls1");//kode
		String tglls2 = request.getParameter("tglls2");//kode
		String noija1 = request.getParameter("noija1");//kode
		String noija2 = request.getParameter("noija2");//kode
		String aspti3 = request.getParameter("aspti3");//kode
		String aspti4 = request.getParameter("aspti4");//kode
		String gelar3 = request.getParameter("gelar3");//kode
		String gelar4 = request.getParameter("gelar4");//kode
		String kdpst3 = request.getParameter("kdpst3");//kode
		String kdpst4 = request.getParameter("kdpst4");//kode
		String bidil3 = request.getParameter("bidil3");//kode
		String bidil4 = request.getParameter("bidil4");//kode
		String tglls3 = request.getParameter("tglls3");//kode
		String tglls4 = request.getParameter("tglls4");//kode
		String noija3 = request.getParameter("noija3");//kode
		String noija4 = request.getParameter("noija4");//kode
		String riwayat = request.getParameter("note");//kode
		riwayat = Converter.prepForInputTextToDb(riwayat);
		System.out.println("note="+riwayat);
		if(riwayat.contains("\n")) {
			System.out.println("yes,\n");
		}
		
		UpdateDbDsn udd = new UpdateDbDsn(isu.getNpm());
		 										//(kdpstDsn,npmDsn,statusDsn,tipeIkaDsn,  jabStruk, ptBase, jja_loco, baseProdi, jja_dikti, gol, kum, aspti1, aspti2, gelar1, gelar2, kdpst1, kdpst2, jur1, jur2, jur3, jur4,  bidil1, bidil2, tglls1, tglls2, noija1,  noija2, aspti3, aspti4, gelar3, gelar4, kdpst3, kdpst4, bidil3, bidil4, tglls3, tglls4, noija3, noija4,riwayat,fileija1,fileija2,fileija3,fileija4,judul1,judul2,judul3,judul4,tgl_in,tgl_out,statSerdos,email_loco,      frontTitle,endTitle,tipeKtp,noKtp,noNidnn,tipeIdDsn,noIdDsn)
		int i = udd.updateDataTableExtCivitasDosen(kdpstDsn,npmDsn,statusDsn, tipeIkaDsn, jabStruk, ptBase, jja_loco, baseProdi, jja_dikti, gol, kum, aspti1, aspti2, gelar1, gelar2, kdpst1, kdpst2, null, null, null, null, bidil1, bidil2, tglls1, tglls2, noija1, noija2, aspti3, aspti4, gelar3, gelar4, kdpst3, kdpst4, bidil3, bidil4, tglls3, tglls4, noija3, noija4, riwayat, null , null  , null   , null  , null , null, null, null ,tglMsk,tglOut, serdos, "@satyagama.ac.id",frontTitle, endTitle, tipeKtp, noKtp, noNidnn, tipeIdDsn, noIdDsn, kdjek);
		//get.dataDosen?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&atMenu=dataDosen" target="_self" class="active">DATA<span>DOSEN</span></a></li>		

		request.getRequestDispatcher("get.dataDosen?id_obj="+idObj+"&nmm="+nmm+"&npm="+npmDsn+"&obj_lvl="+objLvl+"&kdpst="+kdpstDsn+"&atMenu=dataDosen").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
