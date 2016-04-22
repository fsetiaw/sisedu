package servlets.ujian;

import java.io.IOException;
import java.util.StringTokenizer;
//import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.onlineTest.*;
import beans.login.InitSessionUsr;
import beans.tools.*;
/**
 * Servlet implementation class JawabSoalUjian
 */
@WebServlet("/JawabSoalUjian")
public class JawabSoalUjian extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JawabSoalUjian() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("javawb soal");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String RealDateTimeStart=request.getParameter("RealDateTimeStart");
		String totalSoalUjian=request.getParameter("totalSoalUjian");
		String idOnlineTest=request.getParameter("idOnlineTest");
		String idCivJdwlBridge=request.getParameter("idCivJdwlBridge");
		String idJdwlTest=request.getParameter("idJdwlTest");
		String idSoal=request.getParameter("idSoal");
		String jawaban=request.getParameter("jawaban");
		String atSoal = request.getParameter("atSoal");
		//System.out.println("idOSal="+idSoal);
		//System.out.println("idJdwlTest="+idJdwlTest);
		//System.out.println("idCivJdwlBridge="+idCivJdwlBridge);
		//System.out.println(isu.getNpm()+","+isu.getFullname());
		String tokenKodeGroupAndListSoal=request.getParameter("tokenKodeGroupAndListSoal");
		//String infoSoal = ToolSoalUjian.gotoDataNextSoal(tokenKodeGroupAndListSoal,idSoal);
		//System.out.println("1.infoSoal="+infoSoal);
		//StringTokenizer stmp = new StringTokenizer(infoSoal,",");
		//String id_soal = stmp.nextToken();
		//String atChapt = stmp.nextToken();
		//System.out.println(tokenKodeGroupAndListSoal);
		System.out.println("jawaban="+jawaban);
		UpdateOnlineTestDb otdb = new UpdateOnlineTestDb();
		int i = otdb.insJawabanMhs( jawaban,idCivJdwlBridge,idOnlineTest, idSoal, RealDateTimeStart);
		//tmp_idSoal+","+atChapter;
		String infoNextSoal = ToolSoalUjian.gotoDataNextSoal(tokenKodeGroupAndListSoal,idSoal);
		StringTokenizer st = new StringTokenizer(infoNextSoal,",");
		String next_idSoal = st.nextToken();
		String next_atChapter = st.nextToken();
		//System.out.println("(-)"+next_idSoal+","+next_atChapter);
		String nextAtSoal = ""+(Integer.valueOf(atSoal).intValue()+1);
		if(totalSoalUjian.equalsIgnoreCase(atSoal)) {
			//jika di no akhir
			nextAtSoal="1";
		}
		
		request.getRequestDispatcher("go.gantiSoal?idOnlineTest="+idOnlineTest+"&idCivJdwlBridge="+idCivJdwlBridge+"&idJdwlTest="+idJdwlTest+"&idSoal="+next_idSoal+"&atSoal="+nextAtSoal+"&atChapter="+next_atChapter+"&tokenKodeGroupAndListSoal="+tokenKodeGroupAndListSoal+"&RealDateTimeStart="+RealDateTimeStart).forward(request,response);
		//request.getRequestDispatcher(url_ff+"?jawaban="+jawaban+"&idOnlineTest="+idOnlineTest+"&idCivJdwlBridge="+idCivJdwlBridge+"&idJdwlTest="+idJdwlTest+"&id="+id_Soal+"&tknSoal="+tknSoal+"&atSoal="+atSoal+"&atChapter="+atChapter+"&tokenKodeGroupAndListSoal="+tokenKodeGroupAndListSoal).forward(request,response);
		//go.gantiSoal?idOnlineTest=<%=idOnlineTest %>&idCivJdwlBridge=<%=idCivJdwlBridge %>&idJdwlTest=<%=idJdwlTest %>&idSoal=<%= id_soal%>&atSoal=<%= Integer.valueOf(tmpNorutNav).intValue()-1%>&atChapter=<%= atChapt%>&tokenKodeGroupAndListSoal=<%=tokenKodeGroupAndListSoal%>">></a></td>
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
