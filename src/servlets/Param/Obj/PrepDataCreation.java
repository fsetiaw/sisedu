package servlets.Param.Obj;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.obj.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Servlet implementation class PrepDataCreation
 */
@WebServlet("/PrepDataCreation")
public class PrepDataCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepDataCreation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("iam in");
		String scope=request.getParameter("scope");
		String atMenu=request.getParameter("atMenu");
		//System.out.println("iam in");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		SearchDbObj sdb = new SearchDbObj(isu.getNpm());
		Vector vs = isu.getScopeUpd7des2012(scope);
		vs = sdb.getObjNicknameFromScopeUpd7des2012(vs);
		System.out.println("vs size="+vs.size());
		//Collections.sort(vs);
		Vector vSort = new Vector();
		ListIterator lis = vSort.listIterator();
		ListIterator li = null;
		if(vs!=null && vs.size()>0) {
			li = vs.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"$");
				String idObj = st.nextToken();
				String kdpst = st.nextToken();
				String ketObj = st.nextToken();
				String objLvl = st.nextToken();
				String kdjen = st.nextToken();
				String niknm = st.nextToken();
				System.out.println("-vSort size="+vSort.size());
				if(vSort.size()<1) {
					lis.add(objLvl+"$"+kdpst+"$"+ketObj+"$"+idObj+"$"+kdjen+"$"+niknm);
					System.out.println("pertama="+brs);
				}
				else {
					int i = 0;
					lis = vSort.listIterator();
					boolean stop = false;
					while(lis.hasNext() && !stop) {
						String brs1 = (String)lis.next();
						st = new StringTokenizer(brs1,"$");
						String objLvl1 = st.nextToken();
						String kdpst1 = st.nextToken();
						String ketObj1 = st.nextToken();
						String idObj1 = st.nextToken();
						String kdjen1 = st.nextToken();
						String niknm1 = st.nextToken();
						System.out.println(objLvl+" vs "+objLvl1+"="+objLvl.compareToIgnoreCase(objLvl1));
						//if(objLvl.compareToIgnoreCase(objLvl1)<0) {
						if(Integer.valueOf(objLvl).intValue() < Integer.valueOf(objLvl1).intValue()) {
							vSort.insertElementAt(objLvl+"$"+kdpst+"$"+ketObj+"$"+idObj+"$"+kdjen+"$"+niknm,i);
							stop = true;
							System.out.println("stop1="+i);
						}
						else {
							if(Integer.valueOf(objLvl).intValue() >= Integer.valueOf(objLvl1).intValue()) {
							//if(objLvl.compareToIgnoreCase(objLvl1)>=0) {
								if(!lis.hasNext()) {
									vSort.insertElementAt(objLvl+"$"+kdpst+"$"+ketObj+"$"+idObj+"$"+kdjen+"$"+niknm,i+1);
									stop = true;
									System.out.println("stop2="+(i+1));
								}
							}
						}
						i++;
					}
				}
			}
			request.setAttribute("vSortExistingObj",vSort );
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/Obj/formCreation.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff+"?atMenu="+atMenu).forward(request,response);
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
