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
public class StatusKartuUjian extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusKartuUjian() {
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
		String no_urut_utk_jump_scroll = request.getParameter("no_urut_utk_jump_scroll");
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
		//System.out.println("tknApprKartuUjian ori =="+tknApprKartuUjian);
		String tknRulesApproveeKartu = st.nextToken();//OPERATOR KEPALA BAK||
		String tot=st.nextToken();//1.91E7||
		String nimhs=st.nextToken();//10710250006||
		String nmmhs=st.nextToken();//HOTMAN H SIMATUPANG||
		String shift=st.nextToken();//REGULER MALAM||
		String smawl=st.nextToken();//20101||
		String stpid=st.nextToken();//B||
		String gel=st.nextToken();//null||
		String status=st.nextToken();//status akhir target ujian, jadi digunakan untuk menampilkan tombol cetak bila status siap cetak
		String nuTknKartuUjianValue="";
		String nuTknApprKartuUjianValue=""; 
		String nuTknStatusValue="";
		//System.out.println("barisan == "+brs);
		
		if(verdict.equalsIgnoreCase("tolak")) {
			//cek prev record
			//if(tknKartuUjian==null || Checker.isStringNullOrEmpty(tknKartuUjian) || (tknKartuUjian!=null && tknKartuUjian.toUpperCase().contains(targetUjian.toUpperCase()))) {
				//belum ada record tambahakan
			/*
			 * proses if-else dibawah ini adalah untukk mengisi / update data:
			 * tknUjianValue = "UTS,UAS,dll" 
			 * tknApprovee = nim operator approvee
			 * tknStatusTiapUjian =  status siap cetak, tolak dsb	
			 */
			if(tknKartuUjian==null || Checker.isStringNullOrEmpty(tknKartuUjian)) {
				//belum ada record 
				//jadi ini adalah first recoed, biasanya untuk ujian pertama
				//seperti UTS
				tknKartuUjian = ""+targetUjian.toUpperCase();
				nuTknKartuUjianValue=""+tknKartuUjian;
				nuTknApprKartuUjianValue=""+isu.getNpm();
				//nuTknStatusValue=nuTknStatusValue+"Ditolak$"+alasanTolak;
				nuTknStatusValue=nuTknStatusValue+"Ditolak$"+alasanTolak;
			}
			else if(tknKartuUjian!=null && tknKartuUjian.toUpperCase().contains(targetUjian.toUpperCase())) {
				//sudah ada, maka cari norutnya
				/*
				 * dalam kondisi ini harusnya kita akan meniban / overide value bila sudah ada atau update value terbaru
				 * 
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
				nuTknKartuUjianValue = ""+tknKartuUjian; //ngga berubah hanya cek norut saja, jadi value
				//nya ditiban dengan value yg lama
				String tmpApprove = "";
				st = new StringTokenizer(tknApprKartuUjian,"#");//pemisah koma?? atau pager nih?? pager kayaknya
				int tokens = st.countTokens();
				if(tokens>=i) {
					//sudah ada status sebelumnya
					for(int k=1;k<=tokens;k++) {
						tmpApprove = st.nextToken();
						if(k == i) {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+isu.getNpm();;
						}
						else {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+tmpApprove;
						}
						if(k<tokens) {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+"#";
						}	
					}
					
				}
				else if(tokens<i){
					tmpApprove = "";
					//i-1 krn kita mo loop terus sampe pas posisi untuk input nilai sekarang
					for(int k=1;k<=i-1;k++) {
						if(st.hasMoreTokens()) {
							tmpApprove = st.nextToken();
						}
						else {
							tmpApprove = "null";
						}
						
						nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+tmpApprove+"#";
					}
					nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+isu.getNpm();
				}
    				
				//tkn status
				/*
				 * sama dengan proses diatas hanya saja untuk tkn status
				 */
				String infoStat  = "";
				st = new StringTokenizer(tknStatus,"#");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
				tokens = st.countTokens();
				if(tokens>=i) {
					//sudah ada status sebelumnya
					for(int k=1;k<=tokens;k++) {
						infoStat  = st.nextToken();
						if(k == i) {
							if(k==1) {
								nuTknStatusValue=nuTknStatusValue+"Ditolak";
							}
							else {
								if(nuTknStatusValue.endsWith("#")) {
									nuTknStatusValue=nuTknStatusValue+"Ditolak";
								}
								else {
									nuTknStatusValue=nuTknStatusValue+"#Ditolak";
								}
								
							}	
							if(alasanTolak!=null && !Checker.isStringNullOrEmpty(alasanTolak)) {
								nuTknStatusValue=nuTknStatusValue+"$"+alasanTolak;
							}
						}
						else {
							nuTknStatusValue=nuTknStatusValue+infoStat;
						}
						if(k<tokens) {
							nuTknStatusValue=nuTknStatusValue+"#";
						}	
					}
					
				}
				else if(tokens<i){
					//infoStat="";
					//i-1 krn kita mo loop terus sampe pas posisi untuk input nilai sekarang
					for(int k=1;k<=i-1;k++) {
						if(st.hasMoreTokens()) {
							infoStat = st.nextToken();
						}
						else {
							infoStat = "null";
						}
						nuTknStatusValue=nuTknStatusValue+infoStat+"#";
							
					}
					nuTknStatusValue=nuTknStatusValue+"Ditolak";	
					
					
					if(alasanTolak!=null && !Checker.isStringNullOrEmpty(alasanTolak)) {
						nuTknStatusValue=nuTknStatusValue+"$"+alasanTolak;
					}
				}
			}	
			else {
				//tknKartuUjian !=null tapi blum contain target ujian
				nuTknKartuUjianValue=tknKartuUjian+","+targetUjian;
				nuTknApprKartuUjianValue = tknApprKartuUjian+"#"+isu.getNpm();
				nuTknStatusValue=nuTknStatusValue+"Ditolak";
				if(alasanTolak!=null && !Checker.isStringNullOrEmpty(alasanTolak)) {
					nuTknStatusValue=nuTknStatusValue+"$"+alasanTolak;
				}
				nuTknStatusValue = tknStatus +"#"+ nuTknStatusValue;
			}
		}
		else {			
			//UNTUK STATUS DITERIMA
			if(tknKartuUjian==null || Checker.isStringNullOrEmpty(tknKartuUjian)) {
				//belum ada record 
				//jadi ini adalah first recoed, biasanya untuk ujian pertama
				//seperti UTS
				tknKartuUjian = ""+targetUjian.toUpperCase();
				nuTknKartuUjianValue=""+tknKartuUjian;
				nuTknApprKartuUjianValue=""+isu.getNpm();
				nuTknStatusValue=nuTknStatusValue+"Siap Cetak";
			}
			else if(tknKartuUjian!=null && tknKartuUjian.toUpperCase().contains(targetUjian.toUpperCase())) {
			
				//sudah ada, maka cari norutnya
				/*
				 * dalam kondisi ini harusnya kita akan meniban / overide value bila sudah ada atau update value terbaru
				 * 
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
				nuTknKartuUjianValue = ""+tknKartuUjian; //ngga berubah hanya cek norut saja, jadi value
				
				String tmpApprove = "";
				st = new StringTokenizer(tknApprKartuUjian,"#");//pemisah koma?? atau pager nih?? pager kayaknya
				int tokens = st.countTokens();
				if(tokens>=i) {
					//sudah ada status sebelumnya
					for(int k=1;k<=tokens;k++) {
						tmpApprove = st.nextToken();
						if(k == i) {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+isu.getNpm();;
						}
						else {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+tmpApprove;
						}
						if(k<tokens) {
							nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+"#";
						}	
					}
					
				}
				else if(tokens<i){
					tmpApprove = "";
					//i-1 krn kita mo loop terus sampe pas posisi untuk input nilai sekarang
					for(int k=1;k<=i-1;k++) {
						if(st.hasMoreTokens()) {
							tmpApprove = st.nextToken();
						}
						else {
							tmpApprove = "null";
						}
						
						nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+tmpApprove+"#";
					}
					nuTknApprKartuUjianValue=nuTknApprKartuUjianValue+isu.getNpm();
				}
				
				//tkn status
				/*
				 * sama dengan proses diatas hanya saja untuk tkn status
				 */
				//System.out.println("status="+status);
				//st = new StringTokenizer(status,"#");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
				
				String infoStat  = "";
				st = new StringTokenizer(tknStatus,"#");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
				tokens = st.countTokens();
				if(tokens>=i) {
					//sudah ada status sebelumnya
					for(int k=1;k<=tokens;k++) {
						infoStat  = st.nextToken();
						if(k == i) {
							if(k==1) {
								nuTknStatusValue=nuTknStatusValue+"Siap Cetak";
							}
							else {
								if(nuTknStatusValue.endsWith("#")) {
									nuTknStatusValue=nuTknStatusValue+"Siap Cetak";
								}
								else {
									nuTknStatusValue=nuTknStatusValue+"#Siap Cetak";
								}
								
							}	
						}
						else {
							nuTknStatusValue=nuTknStatusValue+infoStat;
						}
						if(k<tokens) {
							nuTknStatusValue=nuTknStatusValue+"#";
						}	
					}
					
				}
				else if(tokens<i){
					//infoStat="";
					//i-1 krn kita mo loop terus sampe pas posisi untuk input nilai sekarang
					for(int k=1;k<=i-1;k++) {
						if(st.hasMoreTokens()) {
							infoStat = st.nextToken();
						}
						else {
							infoStat = "null";
						}
						nuTknStatusValue=nuTknStatusValue+infoStat+"#";
							
					}
					nuTknStatusValue=nuTknStatusValue+"Siap Cetak";
				}
			}	
			else {
				nuTknKartuUjianValue=tknKartuUjian+","+targetUjian;
				nuTknApprKartuUjianValue = tknApprKartuUjian+"#"+isu.getNpm();
				nuTknStatusValue=nuTknStatusValue+"Siap Cetak";
				nuTknStatusValue = tknStatus +"#"+ nuTknStatusValue;				
			}
		}
		
		UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
		//System.out.println("~"+thsms+","+kdpst+","+npmhs+","+targetUjian+","+nuTknKartuUjianValue+","+nuTknApprKartuUjianValue+","+nuTknStatusValue);
		int i = udb.updateStatusKartuUjian(thsms ,kdpst, npmhs, targetUjian, nuTknKartuUjianValue, 	nuTknApprKartuUjianValue, nuTknStatusValue);
		//udb.updateStatusKartuUjian(thsms ,kdpst, npmhs, targetUjian, nuTknKartuUjianValue, 	nuTknApprKartuUjianValue, nuTknStatusValue);
		//<a href="view.listCalonDptKartuUjian?atMenu=<%=namaTest.toUpperCase() %>&listTipeUjian=<%=listTipeUjian %>
		//String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/KartuUjian/showListKandidate.jsp";
		//System.out.println("updated i = "+nmmhs+" => "+i);
		request.getRequestDispatcher("view.listCalonDptKartuUjian?atMenu="+targetUjian+"&listTipeUjian="+listTipeUjian+"&no_urut_utk_jump_scroll="+no_urut_utk_jump_scroll).forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
