package servlets.Post;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.UpdateDb;
/**
 * Servlet implementation class MainTopic
 */
@WebServlet("/MainTopic")
public class MainTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainTopic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("post main topik");
		int i = 0;
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		boolean refreshTampleteTopic = true;
		String mainTopik = ""+request.getParameter("mainTopic");
		//System.out.println("topic- = "+mainTopik);
		String targetObjNickName = ""+request.getParameter("targetObjNickName");
		//System.out.println("targetObjNickName- = "+targetObjNickName);
		String objNicknameSenderAs = ""+request.getParameter("objNicknameSenderAs");
		//System.out.println("objNicknameSenderAs- = "+objNicknameSenderAs);
		String listTargetObjNickName = ""+request.getParameter("listTargetObjNickName");
		//System.out.println("listTargetObjNickName- = "+listTargetObjNickName);
		if(Checker.isStringNullOrEmpty(mainTopik)) {
			refreshTampleteTopic = false;
		}
		
		if(refreshTampleteTopic) {
			//update post
			UpdateDb udb = new UpdateDb(isu.getNpm());
			if(Checker.isStringNullOrEmpty("objNicknameSenderAs")) 
			{
				i = udb.postMainTopicByTargetObjectNickName(mainTopik, isu.getObjLevel(), isu.getIdObj(), isu.getObjNickNameGivenObjId(),targetObjNickName);
			}
			else 
			{
				i = udb.postMainTopicByTargetObjectNickName(mainTopik, isu.getObjLevel(), isu.getIdObj(), objNicknameSenderAs,targetObjNickName);
			}
		}	
		
		String target = Constants.getRootWeb()+"/InnerFrame/ContactUs/tampleteMainPost.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url+"?targetObjNickName="+listTargetObjNickName+"&refreshTampleteTopic="+refreshTampleteTopic).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
