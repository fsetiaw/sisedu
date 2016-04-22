package servlets.Router;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Vector;
import java.util.ListIterator;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.SearchDb;;
/**
 * Servlet implementation class DataPindahan
 */
@WebServlet("/DataPindahan")
public class DataPindahan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataPindahan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * step2
		 * 1. jika pindahan local dan blm punya riwayat - goto input form
		 * 2. jika pindahan luar dan blm punya riwayat - goto input form
		 * 3. jika sudah punya riwayat - go to view mode
		 * 
		 */
		Vector v_trnlp = new Vector();
		Vector v_trnlm = new Vector();
		SearchDb sdb = new SearchDb();
		System.out.println("iam in");
		String id_obj=request.getParameter("id_obj");
		String nmm=request.getParameter("nmm");
		String npm=request.getParameter("npm");
		String obj_lvl=request.getParameter("obj_lvl");
		String kdpst=request.getParameter("kdpst");
		String aspti=request.getParameter("aspti");//bisa pake yg v_
		String aspst=request.getParameter("aspst");//bisa pake yg v_
		String asnim=request.getParameter("v_asnim");//bisa pake yg v_
		String v_obj_lvl=request.getParameter("v_obj_lvl");//bisa pake yg v_
		
		String fwdPg=request.getParameter("fwdPg");
		String cmd=request.getParameter("cmd");
		//System.out.println("fwdPg="+fwdPg);
		//System.out.println("data mhs pindahan = "+id_obj+","+nmm+","+npm+","+obj_lvl+","+kdpst+","+aspti+","+aspst+","+asnim);
		
		if(aspti!=null&&aspti.equalsIgnoreCase(beans.setting.Constants.getKdpti())) {
			//jika lulusan universitas sendiri
			//then cek apa ada asnim
			if(!beans.tools.Checker.isStringNullOrEmpty(asnim)) {
				//punya asnim
				//then cek apa ada data di trnlp
				v_trnlp=sdb.getListMatakuliahPtAsal(kdpst,npm); //semua makul bkn transferred only
				if(v_trnlp!=null&&v_trnlp.size()>0) {
					//do nothing - sudah ada rekord di trnlp jadi gunakan data trnlp
				}
				else {
					//blm ada record di trnlp - maka gunakan record di trnlm @asnim
					v_trnlm=sdb.getHistoryKrsKhs(kdpst, asnim);
				}
				
			}
			else {
				//ngga punya nim
				//then cek apa sdh ada recordnya di trnlp
				v_trnlp=sdb.getListMatakuliahPtAsal(kdpst,npm);
			}
		}
		else {
			//bukan lulusan PT sendiri
			//then cek apa sdh ada recordnya di trnlp
			v_trnlp=sdb.getListMatakuliahPtAsal(kdpst,npm);
		}
		if(cmd.equalsIgnoreCase("penyetaraan")) {
			//proses mk kurikulum
			//1.get kurikulum id untuk mahasiswa ini 
			String idkur = sdb.getIndividualKurikulum(kdpst, npm);
			if(!Checker.isStringNullOrEmpty(idkur)) {
				Vector v_listMakul = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
				request.setAttribute("v_listMakul",v_listMakul);
			}	
		}
		String target=""+fwdPg;
		request.setAttribute("v_trnlp", v_trnlp);
		request.setAttribute("v_trnlm", v_trnlm);
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		System.out.println("target pind="+url+","+target);
		request.getRequestDispatcher(url).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
