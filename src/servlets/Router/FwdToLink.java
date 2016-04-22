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
public class FwdToLink extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FwdToLink() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			/*
			<form accept-charset="UTF-8" action="http://forlap.dikti.go.id/login" method="POST">
			<h4 class="title-block"><span>Login Sistem<span></h4>
										Silahkan masukkan username dan password Anda untuk masuk ke dalam sistem.
			<br/><br/>
			<input type="hidden" placeholder="Username" class="input-block-level" value="031031" name="username" autocomplete="off">
			<input type="hidden" placeholder="Password" class="input-block-level" value="54ty494m4" name="password">
			<button type="submit" class="btn btn-primary">Masuk</button>
		</form>
		<META http-equiv="refresh" content="5;URL=http://www.indiana.edu/~account/new-directory"> 
		*/
			String linkTo=request.getParameter("linkTo");
			System.out.println("linkTo="+linkTo);
			String target=null;
			if(linkTo!=null && !Checker.isStringNullOrEmpty(linkTo)) {
				if(linkTo.equalsIgnoreCase("forlap")) {
					target = new String(Constants.getRootWeb()+"/InnerFrame/linkPage/forlapLink.jsp");
				}
				else if(linkTo.equalsIgnoreCase("feeder_part1")) {
					target = new String(Constants.getRootWeb()+"/InnerFrame/linkPage/FeederLink_part1.jsp");
				}
				else if(linkTo.equalsIgnoreCase("feeder_part2")) {
					target = new String(Constants.getRootWeb()+"/InnerFrame/linkPage/FeederLink_part2.jsp");
				}
				else if(linkTo.equalsIgnoreCase("simlit")) {
					target = new String(Constants.getRootWeb()+"/InnerFrame/linkPage/simlitabnas.jsp");
				}
				else if(linkTo.equalsIgnoreCase("moodle")) {
					target = new String(Constants.getRootWeb()+"/InnerFrame/linkPage/moodleLink.jsp");
					//request.getRequestDispatcher("http://192.168.1.103/moodle/login/index.php").forward(request,response);
					//response.sendRedirect("http://192.168.1.103/moodle/login/index.php");
					
				}
				else if(linkTo.equalsIgnoreCase("lms")) {
					target = new String(Constants.getRootWeb()+"/InnerFrame/linkPage/lms.jsp");
				}
				else {
					response.sendRedirect( Constants .getRootWeb()+ "/ErrorPage/ErrorPageTamplete.jsp?errMsg=Link Tidak DiTemukan");
				}
			}
			else {
				response.sendRedirect( Constants .getRootWeb()+ "/ErrorPage/ErrorPageTamplete.jsp?errMsg=Link Tidak DiTemukan");
			}
			System.out.println("link is ");
			//if(!linkTo.equalsIgnoreCase("moodle")) {
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			System.out.println("target pind="+url+","+target);
			request.getRequestDispatcher(url).forward(request,response);
			//}
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
