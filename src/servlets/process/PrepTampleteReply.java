package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepTampleteReply
 */
@WebServlet("/PrepTampleteReply")
public class PrepTampleteReply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepTampleteReply() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("prepTampleteReply");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		
		String topikInfo = request.getParameter("topikInfo");
		//System.out.println("topikInfo=="+topikInfo);
		Vector vSubTopik = null;
		if(topikInfo!=null && !Checker.isStringNullOrEmpty(topikInfo)) {
			
			
			SearchDb sdb = new SearchDb(isu.getNpm());
			
			StringTokenizer st = new StringTokenizer(topikInfo,"||");
			String idTopik = st.nextToken();
			String content = st.nextToken();
			String npmhsCreator = st.nextToken();
			String nmmhsCreator = st.nextToken();
			String creatorObjId = st.nextToken();
			String creatorObjNickName = st.nextToken();
			String targetKdpst = st.nextToken();
			String targetNpmhs = st.nextToken();
			String targetSmawl = st.nextToken();
			String targetObjId = st.nextToken();
			String targetObjNickName = st.nextToken();
			//st.nextToken();
			String targetGroupId = st.nextToken();
			String groupPwd = st.nextToken();
			String showOnlyToGroup = st.nextToken();
			String deletedByCreator = st.nextToken();
			String hiddenAtCreator = st.nextToken();
			String pinnedAtCreator = st.nextToken();
			String markedAsReadAtCreator = st.nextToken();
			String deletedAtTarget = st.nextToken();
			String hiddenAtTarget = st.nextToken();
			String pinnedAtTartget = st.nextToken();
			String markedAsReadAtTarget = st.nextToken();
			String creatorAsAnonymous = st.nextToken();
			String cretorIsPetugas = st.nextToken();
			String updtm = st.nextToken();
			//System.out.println("idTopik22="+idTopik);
			vSubTopik = sdb.getSubtopik(Integer.valueOf(idTopik).intValue());
			
		}
		if(vSubTopik==null) {
			vSubTopik = new Vector();
		}
		request.setAttribute("vSubTopik", vSubTopik);
		String target = Constants.getRootWeb()+"/InnerFrame/ContactUs/tampleteReply.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url+"?refreshTampleteTopic=oke").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
