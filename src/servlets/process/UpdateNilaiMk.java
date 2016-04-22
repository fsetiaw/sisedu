package servlets.process;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateNilaiMk
 */
@WebServlet("/UpdateNilaiMk")
public class UpdateNilaiMk extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNilaiMk() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

        
        
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			//System.out.println("sampe");
			String thsms = request.getParameter("thsms");
			String idkmk = request.getParameter("idkmk");
			String uniqueId = request.getParameter("uniqueId");
			String kdkmk = request.getParameter("kdkmk");
			String nakmk = request.getParameter("nakmk");
			String shiftKelas = request.getParameter("shiftKelas");
			String nmmdos= request.getParameter("nmmdos");
			String npmdos= request.getParameter("npmdos");
			String[]info = request.getParameterValues("info");
			String[]npm_nlakh_bobot = request.getParameterValues("nlakh");
			String syDsnNya = ""+request.getParameter("syDsnNya");
			
			//System.out.println("info="+info.length);
			//System.out.println("nlakh="+npm_nlakh_bobot.length);
			UpdateDbTrnlm udb;
			if(info!=null && info.length>0) {
				udb = new UpdateDbTrnlm(isu.getNpm());
				for(int i=0;i<info.length;i++) {
					udb.updateNilaiPerKelas(thsms, kdkmk, Integer.parseInt(idkmk), npm_nlakh_bobot, npmdos, syDsnNya,uniqueId);
				}
				
			}
			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			String paramNeeded = "thsms="+thsms+"`idkmk="+idkmk+"`uniqueId="+uniqueId+"`kdkmk="+kdkmk+"`nakmk="+nakmk+"`shift="+shiftKelas+"`bolehEdit=true`nmmdos="+nmmdos+"`npmdos="+npmdos;
			String redirectTo = "get.listMhsUtkPenilaian";
			//System.out.println("redirectTo="+redirectTo);
			request.getRequestDispatcher(url+"?redirectTo="+redirectTo+"&paramNeeded="+paramNeeded).forward(request,response);
			//request.getRequestDispatcher("get.listMhsUtkPenilaian?thsms="+thsms+"&idkmk="+idkmk+"&uniqueId="+uniqueId+"&kdkmk="+kdkmk+"&nakmk="+nakmk+"&shift="+shiftKelas+"&bolehEdit=true").forward(request,response);
			//<a href="get.listMhsUtkPenilaian?thsms=<%=thsms_inp_nilai %>&idkmk=<%=idkmk %>&uniqueId=<%=unique_id %>&kdkmk=<%=kdkmk %>&nakmk=<%=nakmk %>&shift=<%=shift %>&noKlsPll=<%=noKlsPll %>&kode_kelas=<%=kode_kelas %>&kode_gedung=<%=kode_gedung %>&kode_kampus=<%=kode_kampus %>&kode_gabung_kls=<%=kode_gabung_kls %>&kode_gabung_kmp=<%=kode_gabung_kmp %>&skstm=<%=skstm %>&skspr=<%=skspr %>&skslp=<%=skslp %>&bolehEdit=<%=bolehEdit%>"><%= kdkmk+" - "+nakmk%></a>
			
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
