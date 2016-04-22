package servlets.update;

import java.io.IOException;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Tool;
import beans.dbase.UpdateDb;
/**
 * Servlet implementation class MakulPenyetaraan
 */
@WebServlet("/MakulPenyetaraan")
public class MakulPenyetaraan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakulPenyetaraan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("penyetaraan");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String id_obj = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String obj_lvl = request.getParameter("obj_lvl");
		String kdpst = request.getParameter("kdpst");
		String aspti = request.getParameter("aspti");
		String aspst = request.getParameter("aspst");
		String cmd = request.getParameter("cmd");
		System.out.println("cmd ="+cmd);
		String fwdTo = request.getParameter("fwdTo");
		System.out.println("fwd ="+fwdTo);
		String fwdPg = request.getParameter("fwdPg");
		System.out.println("fwdPg ="+fwdPg);
		Vector v = new Vector();
		String comment="PROSES UPDATE TELAH SELESAI";
		ListIterator li = v.listIterator();
		String [] listPenyetaraan = request.getParameterValues("listMakul");
		if(listPenyetaraan!=null) {
			//cek bila ada penyetaraan makul yg dobel
			for(int i=0;i<listPenyetaraan.length;i++) {
				StringTokenizer st = new StringTokenizer(listPenyetaraan[i],"#");
				String tmp = st.nextToken();
				if(!tmp.equalsIgnoreCase("0")) {
					li.add(tmp);
				}
				//System.out.println(listPenyetaraan[i]);
			}
			Vector v1 = new Vector();
			ListIterator li1 = v1.listIterator();
			Collections.sort(v);
			li = v.listIterator();
			String prev = "";
			String now = "";
			if(li.hasNext()) {
				prev = (String)li.next();
				while(li.hasNext()) {
					now = (String)li.next();
					if(prev.equalsIgnoreCase(now)) {
						li1.add(prev);
					}
					prev=""+now;
				}
			}
			try {
				v1 = Tool.removeDuplicateFromVector(v1);
			}
			catch(Exception e) {
				System.out.println("makul_penyetaraan.java");
			}
			if(v1!=null && v1.size()>0) {
				li1 = v1.listIterator();
				while(li1.hasNext()) {
					String dobel = (String)li1.next();
					boolean first = true;
					int j=0;
					for(int i=0;i<listPenyetaraan.length;i++) {
						
						if(listPenyetaraan[i].startsWith(dobel)) {
							
							StringTokenizer st = new StringTokenizer(listPenyetaraan[i],"#");
							String kdkmk1 = st.nextToken();
							String kdasl = st.nextToken();
							String nakmk1 = st.nextToken();
							if(first) {
								first = false;
								j++;
								comment = "Matakuliah "+nakmk1+" digunakan lebih dari 1 kali<br/>";
								comment = comment + j+". Digunakan pada kode matakuliah asal "+kdasl+"<br/>";
								System.out.println("Matakuliah "+nakmk1+" digunakan lebih dari 1 kali");
								System.out.println(j+". Digunakan pada kode matakuliah asal "+kdasl);
							}
							else {
								j++;
								comment = comment + j+". Digunakan untuk kode matakuliah asal "+kdasl+"<br/>";
								System.out.println(j+". Digunakan untuk kode matakuliah asal "+kdasl);
							}
							//set value yg dobel = 0 ; agar tidak diinput
							listPenyetaraan[i]="0";
						}
					}
				}
			}
			UpdateDb udb = new UpdateDb();
			udb.updateMataKuliahPenyetaraanTrnlp(kdpst,npm,listPenyetaraan);
			request.setAttribute("comment", comment);
			String target = Constants.getRootWeb()+"/InnerFrame/formPenyetaraan.jsp";
			request.getRequestDispatcher("toNonJsp.defaultVprofileRoute?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&aspti="+aspti+"&aspst="+aspst+"&cmd=penyetaraan&fwdTo=toNonJsp.gatewayMhsPindahan&fwdPg="+target).forward(request,response);
			
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
