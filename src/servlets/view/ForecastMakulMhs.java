package servlets.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.*;

import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Servlet implementation class ForecastMakulMhs
 */
@WebServlet("/ForecastMakulMhs")
public class ForecastMakulMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForecastMakulMhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("forecast");
		
		Vector v = new Vector();
		ListIterator li = v.listIterator();
		Vector vf = new Vector();
		ListIterator lif = vf.listIterator();
		SearchDb sdb = new SearchDb();
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		Vector vMkMhs = isu.getScopeUpd7des2012("viewForecastMhsPerMk");
		if(vMkMhs!=null && vMkMhs.size()>0) {
			ListIterator liMkMhs = vMkMhs.listIterator();
			while(liMkMhs.hasNext()) {
				String baris = (String)liMkMhs.next();
				StringTokenizer st = new StringTokenizer(baris);
				String id_obj = st.nextToken();
				String kdpst = st.nextToken();
				String keter = st.nextToken();
				keter = keter.replaceAll("_", " ");
				String obj_lvl = st.nextToken();
				Vector vtmp = sdb.getInfoFromForecast_1(kdpst);
				//System.out.println("vtmp.size ="+vtmp.size());
				if(vtmp.size()>0) {
					li.add(kdpst+","+keter);
					li.add(vtmp);
				}	
			}
			if(v.size()>0) {
				Vector v1 = new Vector();
				ListIterator li1 = v1.listIterator();
				li = v.listIterator();
				while(li.hasNext()) {
					String baris1 = (String)li.next();
					//li.add(thsmsForcast+"#"+kdpst+"#"+idkmk+"#"+kdkmk+"#"+nakmk+"#"+sksmk+"#"+npmhs);
					Vector vInfoForecast1 = (Vector)li.next();
					ListIterator linfo = vInfoForecast1.listIterator();
					while(linfo.hasNext()) {
					//get info npmhs
						String  baris = (String)linfo.next();
						//System.out.println("brs ="+baris);
						StringTokenizer st = new StringTokenizer(baris,"#");
						String thsmsForcast = st.nextToken();
						String kdpst = st.nextToken();
						String idkmk = st.nextToken();
						String kdkmk = st.nextToken();
						String nakmk = st.nextToken();
						String sksmk = st.nextToken();
						String tokenNpmhs = st.nextToken();
						Vector vTmp = new Vector();
						ListIterator liTmp = vTmp.listIterator();
						st = new StringTokenizer(tokenNpmhs,",");
						while(st.hasMoreTokens()) {
							String npmhs = st.nextToken();
							String infoMhs = sdb.getInfoSmawlKdpstShiftNmmMhsGiven(npmhs);
							liTmp.add(infoMhs);
						}
						Collections.sort(vTmp);
						li1.add(baris1);
						//if(kdpst.equalsIgnoreCase("74201")) {
						//	System.out.println((kdpst+"#"+idkmk+"#"+kdkmk+"#"+nakmk+"#"+sksmk));
						//}
						li1.add(kdpst+"#"+idkmk+"#"+kdkmk+"#"+nakmk+"#"+sksmk);
						li1.add(vTmp);
					}	
				}
				
				//buat rebgkuman jumlah per angkatam
				Vector v2 = new Vector();
				ListIterator li2 = v2.listIterator();
				li1 = v1.listIterator();
				while(li1.hasNext()) {
					String baris1 = (String)li1.next();
					li2.add(baris1);
					String baris2 = (String)li1.next();
					li2.add(baris2);
				//	System.out.println(baris1);
				//	System.out.println(baris2);
					Vector vInfoMhs = (Vector)li1.next();
					li2.add(vInfoMhs);
				//	System.out.println("vsize="+vInfoMhs.size());
					Vector vInfoAng = sdb.buatRangkumanJumlahMhsPerAngkatanGiven(vInfoMhs);
					li2.add(vInfoAng);
				//	ListIterator liang = vInfoAng.listIterator();
				//	while(liang.hasNext()) {
				//		String brs = (String)liang.next();
				//		System.out.println(brs);
				//	}
				}
				vf = v2;
			}// end if(v.size()>0)
			else {
				//redirect to no forecast data
				//return vf size 0
			}
		}
		else {
			//redirect to no permit
			//return vf size 0
		}
		request.setAttribute("vf", vf);
		String target = Constants.getRootWeb()+"/InnerFrame/Analisa/analisaMakulMhs.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url_ff).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
