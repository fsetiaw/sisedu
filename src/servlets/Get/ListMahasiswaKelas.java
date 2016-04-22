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

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.SearchDb;
import beans.dbase.tbbnl.SearchDbTbbnl;
import beans.dbase.trnlm.*;
/**
 * Servlet implementation class ListMahasiswaKelas
 */
@WebServlet("/ListMahasiswaKelas")
public class ListMahasiswaKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListMahasiswaKelas() {
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
			//?thsms=<%=thsms_inp_nilai %>&idkmk=<%=idkmk %>&uniqueId=<%=unique_id %>&kdkmk=<%=kdkmk %>&nakmk=<%=nakmk %>
			//&shift=<%=shift %>&kode_kelas=<%=kode_kelas %>&kode_gedung=<%=kode_gedung %>&kode_kampus=<%=kode_kampus %>
			//&kode_gabung_kls=<%=kode_gabung_kls %>&kode_gabung_kmp=<%=kode_gabung_kmp %>&bolehEdit=<%=bolehEdit%>">
			
			String thsms= request.getParameter("thsms");
			String idkmk = request.getParameter("idkmk");
			String uniqueId= request.getParameter("uniqueId");
			String kdkmk=  request.getParameter("kdkmk");
			String nakmk = request.getParameter("nakmk");
			String shift= request.getParameter("shift");
			/*
			 * Variable dibawah ini bila mulai digunakan, 
			 * harap di pass ke next berikutnya sampai uservlet
			 * process.UpdateNilaiMk.java, karen redirectnya menngunakan param tsb
			 */
			String noKlsPll = request.getParameter("noKlsPll");
			String kode_kelas = request.getParameter("kode_kelas");
			String kode_gedung = request.getParameter("kode_gedung");
			String kode_kampus = request.getParameter("kode_kampus");
			String kode_gabung_kls = request.getParameter("kode_gabung_kls");
			String kode_gabung_kmp = request.getParameter("kode_gabung_kmp");
			String bolehEdit= request.getParameter("bolehEdit");
			String nmmdos= request.getParameter("nmmdos");
			String npmdos= request.getParameter("npmdos");
			
			//System.out.println(thsms);
			//System.out.println(idkmk);
			//System.out.println(uniqueId);
			//System.out.println(kdkmk);
			//System.out.println(nakmk);
			//System.out.println(shift);
			//System.out.println("uniqueId="+uniqueId);
			SearchDbTrnlm sdb = new SearchDbTrnlm(isu.getNpm());
			Vector vListNpmhsAndInfoNilai = sdb.getListMhsDanCurrNilaiMknya(thsms, idkmk, uniqueId, kdkmk, nakmk, shift);
			//System.out.println("vListNpmhsAndInfoNilai="+vListNpmhsAndInfoNilai);
			if(vListNpmhsAndInfoNilai!=null && vListNpmhsAndInfoNilai.size()>0) {
				SearchDb sdb1 = new SearchDb();
				vListNpmhsAndInfoNilai = sdb1.getInfoCivitasForTampleteListMhs(vListNpmhsAndInfoNilai);
				if(vListNpmhsAndInfoNilai!=null && vListNpmhsAndInfoNilai.size()>0) {
					/* get bobot nilai yg berlaku untuk setiap mhs,
					 * kenapa tidak menggunakan kdpst? karena 1 kelas bisa berisi mhs dari faakultas lain
					 */
					SearchDbTbbnl sdt = new SearchDbTbbnl(isu.getNpm());
					vListNpmhsAndInfoNilai = sdt.getInfoTabelNilaiYgBerlakuPerKdpst(vListNpmhsAndInfoNilai);
					ListIterator li = vListNpmhsAndInfoNilai.listIterator();
					while(li.hasNext()) {
						String infoMhs = (String)li.next();
						//System.out.println("info="+infoMhs);
					}
				}
				else {
					vListNpmhsAndInfoNilai = new Vector();
				}
			}
			else {
				vListNpmhsAndInfoNilai = new Vector();
			}
			
			
			request.setAttribute("vListNpmhsAndInfoNilai", vListNpmhsAndInfoNilai);
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Nilai/formPenilaianAkhir.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url+"?thsms="+thsms+"&idkmk="+idkmk+"&uniqueId="+uniqueId+"&kdkmk="+kdkmk+"&nakmk="+nakmk+"&shift="+shift+"&bolehEdit="+bolehEdit).forward(request,response);
			request.getRequestDispatcher(url).forward(request,response);
			
			
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
