package servlets.update;

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
import beans.tools.PathFinder;
import beans.dbase.mhs.*;

import java.util.StringTokenizer;
/**
 * Servlet implementation class StatusKartuUjian
 */
@WebServlet("/StatusKartuUjian")
public class StatusKartuUjian_prev_ver_1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusKartuUjian_prev_ver_1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("kartu ujian");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String thsms = request.getParameter("thsms_aktif");
		String brs = request.getParameter("brs");
		String verdict = request.getParameter("verdict");
		String targetUjian = request.getParameter("targetUjian");
		String alasanTolak = request.getParameter("alasan");
		//String listTipeUjian= request.getParameter("alalistTipeUjiansan");
		String listTipeUjian= request.getParameter("listTipeUjian");
		
		//System.out.println("brs2="+brs);
		StringTokenizer st = new StringTokenizer(brs,"||");
		String kdpst=st.nextToken();//20201||
		String npmhs=st.nextToken();//2020100000112||
		String tknApr=st.nextToken();//0000512100003#OPERATOR KEPALA BAK#2014-03-13 12:01:24.107#0000313200001#OPERATOR BAA#2014-03-15 09:52:34.247||
		String tknVer=st.nextToken();//OPERATOR BAA,OPERATOR KEPALA BAK||
		String tknKartuUjian = st.nextToken();//null||
		String tknApprKartuUjian = st.nextToken();//null||
		String tknStatus = st.nextToken();//null||
		String tknRulesApproveeKartu = st.nextToken();//OPERATOR KEPALA BAK||
		String tot=st.nextToken();//1.91E7||
		String nimhs=st.nextToken();//10710250006||
		String nmmhs=st.nextToken();//HOTMAN H SIMATUPANG||
		String shift=st.nextToken();//REGULER MALAM||
		String smawl=st.nextToken();//20101||
		String stpid=st.nextToken();//B||
		String gel=st.nextToken();//null||
		String status=st.nextToken();//Menunggu Validasi
		String nuTknKartuUjianValue="";
		String nuTknApprKartuUjianValue=""; 
		String nuTknStatusValue="";
		System.out.println("barisan == "+brs);
		
		if(verdict.equalsIgnoreCase("tolak")) {
			//cek prev record
			//if(tknKartuUjian==null || Checker.isStringNullOrEmpty(tknKartuUjian) || (tknKartuUjian!=null && tknKartuUjian.toUpperCase().contains(targetUjian.toUpperCase()))) {
				//belum ada record tambahakan
				
			if(tknKartuUjian==null || Checker.isStringNullOrEmpty(tknKartuUjian)) {
				//belum ada record 
				tknKartuUjian = ""+targetUjian.toUpperCase();
				nuTknKartuUjianValue=""+tknKartuUjian;
				nuTknApprKartuUjianValue=""+isu.getNpm();
				nuTknStatusValue=nuTknStatusValue+"Ditolak$"+alasanTolak;
			}
			else if(tknKartuUjian!=null && tknKartuUjian.toUpperCase().contains(targetUjian.toUpperCase())) {
				//sudah ada, maka cari norutnya
				/*
				 * dalam kondisi ini harusnya kita akan meniban / overide value bila sudah ada atau update value terbaru
				 */
				//String tknKartuUjianValue = "";
				int i=0;boolean match=false,first=true;
				st = new StringTokenizer(tknKartuUjian,",");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
				while(st.hasMoreTokens() && !match) {
					i++;
					String namaUjian = st.nextToken();
					if(namaUjian.equalsIgnoreCase(targetUjian)) {
						match = true;
					}
				}
				//replace sesuai norut diata
				//tknApprKartuUjian
				/*
				 * ada kemungkinana error dimana tknKartuUjian ada value tapi tknApprKartuUjian tidak ada valuenya
				 */
				nuTknKartuUjianValue = ""+tknKartuUjian; //ngga berubah hanya cek norut saja
				st = new StringTokenizer(tknApprKartuUjian,"#");//pemisah koma?? atau pager nih?? 
				/*
				 * sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah *****ngga jelas nih*****
				 * tknApprKartuUjian harusnya berisi NPM operator approvee, plus pemisah yg ngga jelas , ato #
				 */
				for(int j=0;st.hasMoreTokens();j++) {
					String infoOpr = st.nextToken();
					if(j==i-1) {
						nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+isu.getNpm();
					}
					else {
						nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+infoOpr;
					}
					if(st.hasMoreTokens()) {
						nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+"#";
					}
				}
				//tkn status
				st = new StringTokenizer(status,"#");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
				for(int j=0;st.hasMoreTokens();j++) {
					String infoStat = st.nextToken();
					if(j==i-1) {
						nuTknStatusValue=nuTknStatusValue+"Ditolak";
						if(alasanTolak!=null && Checker.isStringNullOrEmpty(alasanTolak)) {
							nuTknStatusValue=nuTknStatusValue+"$"+alasanTolak;
						}
					}
					else {
						nuTknStatusValue=nuTknStatusValue+infoStat;
					}
					if(st.hasMoreTokens()) {
						nuTknStatusValue=nuTknStatusValue+"#";
					}
				}
			}	
			else {
				//tknKartuUjian !=null tapi blum contain target ujian
				tknKartuUjian=tknKartuUjian+","+targetUjian;
				nuTknApprKartuUjianValue = tknApprKartuUjian+"#"+nuTknApprKartuUjianValue;
				nuTknStatusValue = nuTknStatusValue +"#"+ status;
			}
		}
		/*
		else {
			//cek prev record
			//System.out.println("terima");
			//System.out.println("tknKartuUjian="+tknKartuUjian);
			if(tknKartuUjian==null || Checker.isStringNullOrEmpty(tknKartuUjian) || (tknKartuUjian!=null && tknKartuUjian.toUpperCase().contains(targetUjian.toUpperCase()))) {
				//belum ada record tambahakan
				
				if(tknKartuUjian==null || Checker.isStringNullOrEmpty(tknKartuUjian)) {
					tknKartuUjian = ""+targetUjian.toUpperCase();
					nuTknKartuUjianValue=""+tknKartuUjian;
					nuTknApprKartuUjianValue=""+isu.getNpm();
					nuTknStatusValue=nuTknStatusValue+"Siap Cetak";
					//System.out.println("nuTknKartuUjianValue="+nuTknKartuUjianValue);
					//System.out.println("nuTknApprKartuUjianValue="+nuTknApprKartuUjianValue);
					//System.out.println("nuTknStatusValue="+nuTknStatusValue);
				}
				else {
					//System.out.println("sdh ada nilai");
					//sudah ada, maka cari norutnya
					//String tknKartuUjianValue = "";
					nuTknKartuUjianValue = ""+tknKartuUjian; //ngga berubah hanya cek norut saja
					int i=0;boolean match=false,first=true;
					st = new StringTokenizer(tknKartuUjian,",");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
					while(st.hasMoreTokens() && !match) {
						i++;
						String namaUjian = st.nextToken();
						if(namaUjian.equalsIgnoreCase(targetUjian)) {
							match = true;
						}
					}
					//System.out.println("norut 1");
					//replace sesuai norut diata
					//tknApprKartuUjian
					st = new StringTokenizer(tknApprKartuUjian,"#");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
					for(int j=0;st.hasMoreTokens();j++) {
						String infoOpr = st.nextToken();
						if(j==i-1) {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+isu.getNpm();
						}
						else {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+infoOpr;
						}
						if(st.hasMoreTokens()) {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+"#";
						}
					}
					//tkn status
					st = new StringTokenizer(status,"#");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
					for(int j=0;st.hasMoreTokens();j++) {
						String infoStat = st.nextToken();
						if(j==i-1) {
							nuTknStatusValue=nuTknStatusValue+"Siap Cetak";
						}
						else {
							nuTknStatusValue=nuTknStatusValue+infoStat;
						}
						if(st.hasMoreTokens()) {
							nuTknStatusValue=nuTknStatusValue+"#";
						}
					}
						
				}
			}	
		}
		
		UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
		System.out.println("~"+thsms+","+kdpst+","+npmhs+","+targetUjian+","+nuTknKartuUjianValue+","+nuTknApprKartuUjianValue+","+nuTknStatusValue);
		int i = udb.updateStatusKartuUjian(thsms ,kdpst, npmhs, targetUjian, nuTknKartuUjianValue, 	nuTknApprKartuUjianValue, nuTknStatusValue);
		//udb.updateStatusKartuUjian(thsms ,kdpst, npmhs, targetUjian, nuTknKartuUjianValue, 	nuTknApprKartuUjianValue, nuTknStatusValue);
		//<a href="view.listCalonDptKartuUjian?atMenu=<%=namaTest.toUpperCase() %>&listTipeUjian=<%=listTipeUjian %>
		//String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/KartuUjian/showListKandidate.jsp";
		System.out.println("updated i = "+nmmhs+" => "+i);
		request.getRequestDispatcher("view.listCalonDptKartuUjian?atMenu="+targetUjian+"&listTipeUjian="+listTipeUjian).forward(request,response);
		//System.out.println("i="+i);
		 * *
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
