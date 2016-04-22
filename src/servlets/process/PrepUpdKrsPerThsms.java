package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.setting.Constants;
import beans.tools.Converter;
import beans.tools.Tool;
import beans.tools.PathFinder;

import java.util.Vector;
import java.util.ListIterator;
/**
 * Servlet implementation class PrepUpdKrsPerThsms
 */
@WebServlet("/PrepUpdKrsPerThsms")
public class PrepUpdKrsPerThsms extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepUpdKrsPerThsms() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		//System.out.println("prep uupda krs thsms");
		String id_obj = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl = request.getParameter("obj_lvl");
		String thsmsKrs = request.getParameter("thsmsKrs");
		Vector vHistoKrs = (Vector) session.getAttribute("vHistoKrs");
		ListIterator li = null;
		if(vHistoKrs!=null) {
			li = vHistoKrs.listIterator();
		}
		session.removeAttribute("vHistoKrs");
		//System.out.println(id_obj+","+nmm+","+npm+","+kdpst+","+obj_lvl+","+thsmsKrs);
		//System.out.println("vhist "+vHistoKrs.size());
		Vector vf = new Vector();
		ListIterator lif = vf.listIterator();
		if(li!=null) {
			while(li.hasNext()) {
				String thsms=(String)li.next();
				String kdkmk=(String)li.next();
				String nakmk=(String)li.next();
				nakmk = Tool.gantiSpecialChar(nakmk);
				String nlakh=(String)li.next();
				String bobot=(String)li.next();
				String sksmk=(String)li.next();
				String kelas=(String)li.next();
				String sksem=(String)li.next();
				String nlips=(String)li.next();
				String skstt=(String)li.next();
				String nlipk=(String)li.next();
				String shift=(String)li.next();
				String krsdown=(String)li.next();
				String khsdown=(String)li.next();
				String bakprove=(String)li.next();
				String paprove=(String)li.next();
				String note=(String)li.next();
				String lock=(String)li.next();
				String baukprove=(String)li.next();
				
				String idkmk =(String)li.next();
				String addReq =(String)li.next();
				String drpReq  =(String)li.next();
				String npmPa =(String)li.next();
				String npmBak =(String)li.next();
				String npmBaa =(String)li.next();
				String npmBauk =(String)li.next();
				String baaProve =(String)li.next();
				String ktuProve =(String)li.next();
				String dknProve =(String)li.next();
				String npmKtu =(String)li.next();
				String npmDekan =(String)li.next();
				String lockMhs =(String)li.next();
				String kodeKampus =(String)li.next();
				if(thsms.equalsIgnoreCase(thsmsKrs)) {
					lif.add(thsms+"#&"+kdkmk+"#&"+nakmk+"#&"+nlakh+"#&"+bobot+"#&"+sksmk+"#&"+kelas+"#&"+sksem+"#&"+nlips+"#&"+skstt+"#&"+nlipk+"#&"+shift+"#&"+krsdown+"#&"+khsdown+"#&"+bakprove+"#&"+paprove+"#&"+note);
					//System.out.println(thsms+"#&"+kdkmk+"#&"+nakmk+"#&"+nlakh+"#&"+bobot+"#&"+sksmk+"#&"+kelas+"#&"+sksem+"#&"+nlips+"#&"+skstt+"#&"+nlipk+"#&"+shift+"#&"+krsdown+"#&"+khsdown+"#&"+bakprove+"#&"+paprove+"#&"+note);
				}
			}	
			request.setAttribute("vhistkrs", vf);
		}
		String forceTo = "get.histKrs?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=editKrs";
		session.setAttribute("forceBackTo", forceTo);
		String target = Constants.getRootWeb()+"/InnerFrame/krsPerThsmsPreviewEdit.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url+"?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=editKrs").forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
