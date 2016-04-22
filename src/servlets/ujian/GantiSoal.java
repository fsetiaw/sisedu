package servlets.ujian;

import java.io.IOException;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.dbase.onlineTest.*;
import beans.setting.Constants;
import beans.tools.PathFinder;
/**
 * Servlet implementation class GantiSoal
 */
@WebServlet("/GantiSoal")
public class GantiSoal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GantiSoal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idJdwlTest = request.getParameter("idJdwlTest");
		String idCivJdwlBridge = request.getParameter("idCivJdwlBridge");
		String idOnlineTest = request.getParameter("idOnlineTest");
		System.out.println("idJdwlTest="+idJdwlTest);
		//get no id soal berdasar norut

		
		
		System.out.println("ganti soal");
		String RealDateTimeStart=request.getParameter("RealDateTimeStart");
		System.out.println("ganti soal RealDateTimeStart="+RealDateTimeStart);
		String atSoal=request.getParameter("atSoal"); //norut soal
		String atChapter=request.getParameter("atChapter");//norut chapter
		String tokenKodeGroupAndListSoal=request.getParameter("tokenKodeGroupAndListSoal");
		String idSoal = request.getParameter("idSoal");
		System.out.println("**"+atSoal+","+atChapter+","+tokenKodeGroupAndListSoal);
		System.out.println("idSoal="+idSoal);
		//get no id soal berdasar norut
		int norutChapterCounter = 0;
		int norutSoalCounter = 0;
		int id_Soal = 0;
		boolean matchChapter = false;
		
		StringTokenizer st = null;
		if(tokenKodeGroupAndListSoal.contains("$$")) {
			st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
		}
		else {
			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
		}
		if(idSoal==null || idSoal.equalsIgnoreCase("null")) { //ngga ada idsoal berarti panggilan dari tab chapter menu
			/*
			 * untuk panggilan dari tab chapter = no soal berarti soal pertama untuk chapter itu (first token)
			 * part ini: hanya untuk mendapatkan idSoal untuk atChapter pemganngggil / terkait
			 */
			while(st.hasMoreTokens()&&!matchChapter) {
				norutChapterCounter++;
				String brs = st.nextToken();
				System.out.println("brs="+brs);
				if(norutChapterCounter==Integer.valueOf(atChapter).intValue()) {
					matchChapter = true;
					//untuk soal 
					//boolean matchSoal = false;
					//norutSoalCounter = 0;
					StringTokenizer st1 = new StringTokenizer(brs,",");
					String kodeChapterIgnore = st1.nextToken();
					//while(st1.hasMoreTokens() && !matchSoal) {
					//norutSoalCounter++;
					idSoal = st1.nextToken();
					id_Soal = Integer.valueOf(idSoal).intValue();
					String norutSoalIgnore = st1.nextToken();//norut soal sudah disort di ONLINE_TEST.java
					//if(norutSoalCounter==Integer.valueOf(atSoal).intValue()) {
					//	matchSoal=true;
					//}
				}
			}
		}
		else {
			//process degn idsoal
			id_Soal = Integer.valueOf(idSoal).intValue();
		}
		System.out.println("id soal="+id_Soal);

		//get token soal sesuai atSoal
		GetSoalUjian gsu = new GetSoalUjian();
		String tknSoal = gsu.getSoal(id_Soal);
		System.out.println("token soal ="+tknSoal);
		/*
		 * ngga boleh passing param yg ada char %,+
		 */
		tknSoal = tknSoal.replace("%","o/o");
		tknSoal=tknSoal.replace("+","tandaTambah");
		//get jawaban
		SearchOnlineTestDb otdb = new SearchOnlineTestDb();
		String jawaban = otdb.getJawabanMhs(""+idCivJdwlBridge,""+idOnlineTest,""+id_Soal,RealDateTimeStart);
		System.out.println("2.jaawaban="+jawaban);
		System.out.println("3.RealDateTimeStart="+RealDateTimeStart);
		String target = Constants.getRootWeb()+"/InnerFrame/MhsSection/Ujian/tampleteSoalUjian.jsp";
		//String target = Constants.getRootWeb()+"/indexAtTampleteUjian.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		//get Status
		String status_cancel_done_inprogress_pause = otdb.getStatusUjian(idJdwlTest);
		System.out.println("tokenKodeGroupAndListSoal=="+tokenKodeGroupAndListSoal);
		//request.getRequestDispatcher(url_ff+"?id="+id_Soal+"&tknSoal="+tknSoal+"&atSoal="+atSoal+"&atChapter="+atChapter+"&tokenKodeGroupAndListSoal="+tokenKodeGroupAndListSoal).forward(request,response);
		
		
		/*
		 * ngga boleh passing param yg ada char % 
		 */
		request.getRequestDispatcher(url_ff+"?status_cancel_done_inprogress_pause="+status_cancel_done_inprogress_pause+"&jawaban="+jawaban+"&idOnlineTest="+idOnlineTest+"&idCivJdwlBridge="+idCivJdwlBridge+"&idJdwlTest="+idJdwlTest+"&id="+id_Soal+"&tknSoal="+tknSoal+"&atSoal="+atSoal+"&atChapter="+atChapter+"&tokenKodeGroupAndListSoal="+tokenKodeGroupAndListSoal+"&RealDateTimeStart="+RealDateTimeStart).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
