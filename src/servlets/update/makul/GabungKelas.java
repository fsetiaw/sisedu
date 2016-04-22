package servlets.update.makul;

import java.io.IOException;
import beans.dbase.makul.*;
import beans.login.InitSessionUsr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.tools.Checker;

import java.util.Arrays;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Vector;
//import java.util.StringTokenizer;
import java.util.StringTokenizer;
/**
 * Servlet implementation class GabungKelas
 */
@WebServlet("/GabungKelas")
public class GabungKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GabungKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("gabung");HttpSession session = request.getSession(true);
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		
		String thsmsTarget = request.getParameter("thsmsTarget");
		String perintah = request.getParameter("perintah");
		String[]kelasInti = request.getParameterValues("kelasInti");
		String[]kodeGroup = request.getParameterValues("kodeGroup");
		String[]infoGroup = request.getParameterValues("infoGroup");
		//System.out.println(infoGroup.length);
		//System.out.println(kodeGroup.length);
		//System.out.println(kelasInti.length);
		
		//System.out.println(perintah);
		Vector vScope = isu.getScopeUpd7des2012ProdiOnly("reqGabungKelasFak");
		UpdateDbMk udb = new UpdateDbMk(isu.getNpm());
		Vector v = new Vector();
		ListIterator li = v.listIterator();
		if(perintah!=null && !perintah.contains("AJUKAN")) {
			if(kodeGroup!=null && kelasInti!=null && kodeGroup.length>0 && kelasInti.length>0) {
				//gabungkan
				//process kalo yg kodeGroupnya sudah ada di db
				System.out.println("in 1");
				for(int i=0;i<infoGroup.length;i++) {
					StringTokenizer st = new StringTokenizer(infoGroup[i],"$");
					String kdpst = st.nextToken();
					String idkmk = st.nextToken();
					String shift = st.nextToken();
					String norut = st.nextToken();
					String idkur = st.nextToken();
					String cuid = st.nextToken();
					
					boolean match=false;
					for(int j=0;j<kelasInti.length && !match;j++) {
						if(infoGroup[i].equalsIgnoreCase(kelasInti[j]) && (kodeGroup[i]!=null && !Checker.isStringNullOrEmpty(kodeGroup[i]))) {
							match=true;
							infoGroup[i]=kodeGroup[i]+"#"+infoGroup[i]+"#inti";
							li.add(infoGroup[i]);
						}
					}
					if(!match) {
						if(kodeGroup[i]!=null && !Checker.isStringNullOrEmpty(kodeGroup[i])) {
							infoGroup[i]=kodeGroup[i]+"#"+infoGroup[i]+"#null";
							li.add(infoGroup[i]);
						}
						else {
							infoGroup[i]="null#"+infoGroup[i]+"#null";
						}
						
					}

				}	
				//li = v.listIterator();
				//while(li.hasNext()) {
				//	String brs = (String)li.next();
				//	System.out.println(brs);
				//}
				Collections.sort(v);
				int norutAkhir = udb.getNoUrutKodeTerakhir(thsmsTarget);
				//System.out.println("norutAkhir="+norutAkhir);
				udb.resetClassPoolGrouping(vScope, thsmsTarget);
				udb.resetTrnlmPenggabungan(vScope,thsmsTarget);
				udb.updateClassPoolGrouping(v,thsmsTarget,norutAkhir);
				//ubah  kode cuid di trnlm dari yg ditutup ke 
				udb.updateCuidTrnlm_v1();
				request.getRequestDispatcher("get.listScope?nuFwdPage=get.listKelasClassPool&scope=reqGabungKelasFak&callerPage=dashPengajuan.jsp&cmd=gabungKelasFak&atMenu=gabungKelasFak&scopeType=prodyOnly").forward(request,response);
				
			}
			else {
				//error must hava kodgroup sama kelas inti
				//System.out.println("in 2");
				udb.resetClassPoolGrouping(vScope, thsmsTarget);
				udb.resetTrnlmPenggabungan(vScope,thsmsTarget);
				request.getRequestDispatcher("get.listScope?nuFwdPage=get.listKelasClassPool&scope=reqGabungKelasFak&callerPage=dashPengajuan.jsp&cmd=gabungKelasFak&atMenu=gabungKelasFak&scopeType=prodyOnly").forward(request,response);
			}
		}
		else {
			//pengajuan
			//kalo dah di prove maka di lock
			//System.out.println("in 3");
			if(isu.isAllowTo("reqGabungKelasFak")>0) {
				//UpdateDbMk udb = new UpdateDbMk(isu.getNpm());
				//Vector vScope = isu.getScopeUpd7des2012ProdiOnly("reqGabungKelasFak");
				udb.ajukanPenggabunganKelas(thsmsTarget,vScope);
			}
			else {
				//eooro no AKSES
			}
			//Vector vScope = isu.getScopeUpd7des2012("reqBukaKelas");
			//SearchDbMk sdb = new SearchDbMk(isu.getNpm());
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
