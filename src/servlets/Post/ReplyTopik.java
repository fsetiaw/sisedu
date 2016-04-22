package servlets.Post;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.UpdateDb;
import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Servlet implementation class ReplyTopik
 */
@WebServlet("/ReplyTopik")
public class ReplyTopik extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyTopik() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("replyTopik");
		
		//Vector vSubTopik = (Vector) session.getAttribute("vSubTopik");
		String topikInfo = request.getParameter("topikInfo");
		String isiReply = request.getParameter("isiReply");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String usrObjNickName = isu.getObjNickNameGivenObjId();
		//System.out.println("topikInfo2="+topikInfo);
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
		//System.out.println("isiReply="+isiReply);
		
		UpdateDb udb = new UpdateDb(isu.getNpm());
		int i = udb.postSubTopikBetweenMhsAndBiro(Long.valueOf(idTopik).longValue(), isiReply, npmhsCreator, nmmhsCreator, usrObjNickName, creatorObjNickName, targetObjNickName);
		SearchDb sdb = new SearchDb(isu.getNpm());
		Vector vSubTopik = sdb.getSubtopik(Long.valueOf(idTopik).longValue());
		
		request.setAttribute("vSubTopik", vSubTopik);
		String target = Constants.getRootWeb()+"/InnerFrame/ContactUs/tampleteReply.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url).forward(request,response);
		//request.getRequestDispatcher(url+"?refreshTampleteReply=true").forward(request,response);
		//request.getRequestDispatcher(url+"?targetObjNickName="+listTargetObjNickName+"&refreshTampleteTopic="+refreshTampleteTopic).forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
