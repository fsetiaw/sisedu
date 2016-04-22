package servlets.process;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.*;
import beans.tools.*;
/**
 * Servlet implementation class PrepDashboardContactPmb
 */
@WebServlet("/PrepDashboardContactPmb")
public class PrepDashboardContactPmb extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepDashboardContactPmb() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("prepContactPmb");
		String target = Constants.getRootWeb()+"/InnerFrame/ContactUs/dashContactUsPmb.jsp";
		String unreadInfo = request.getParameter("unreadInfo");
		System.out.println("unreadInfo=="+unreadInfo);
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		/*
		 * cek scope list target object yang bisa anda kirim 
		 */
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		/*
		 * dibawah ini = list nickname pmb yg boleh dikontak usr 
		 */
		Vector vTargetObjNickname = isu.getScopeObjNickname("allowContactPMB");
		String[]listObjNicknamePmb = Constants.getListObjNicknamePmb();
		ListIterator li = vTargetObjNickname.listIterator();
		
		while(li.hasNext()) {
			String usrAllowContactPMBnickname = (String) li.next();
			boolean match = false;
			for(int i=0;i<listObjNicknamePmb.length && !match;i++) {
				if(usrAllowContactPMBnickname.equalsIgnoreCase(listObjNicknamePmb[i])) {
					match = true;
				}
			}
			if(!match) {
				/*
				 * buang obj nickname pmb yg tidak masuk ke scope usr
				 */
				li.remove();
			}
		}
		/*
		 * overiding targetObjNickname dari step sebelm ini contactSubMeno 0
		 */
		String targetObjNickName = null;
		if(vTargetObjNickname!=null && vTargetObjNickname.size()>0) {
			targetObjNickName="";
			li = vTargetObjNickname.listIterator();
			while(li.hasNext()) {
				String nick = (String)li.next();
				targetObjNickName=targetObjNickName+nick;
				if(li.hasNext()) {
					targetObjNickName=targetObjNickName+"__";
				}
			}
		}
		//targetObjNickName
		
		if(unreadInfo!=null && !Checker.isStringNullOrEmpty(unreadInfo)) {
			url = url+"?unreadInfo="+unreadInfo;
			if(targetObjNickName!=null) {
				url = url+"&targetObjNickName="+targetObjNickName;
			}
		}
		else {
			if(targetObjNickName!=null) {
				url = url+"?targetObjNickName="+targetObjNickName;
			}
		}

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
